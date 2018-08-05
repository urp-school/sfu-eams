package com.shufe.service.course.arrange.task.auto.impl;

import java.util.Collection;
import java.util.Iterator;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.commons.lang.StringUtil;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.arrange.task.ArrangeParams;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.auto.Arrange;
import com.shufe.service.course.arrange.task.auto.TimeAllocator;

/**
 * TimeAllocator implementation.
 * 
 * @author chaostone 2005-11-24
 */
public class TimeAllocatorImpl implements TimeAllocator {
    
    private ArrangeParams params = new ArrangeParams();
    
    private TeachResourceDAO ResourceDAO;
    
    /**
     * @see TimeAllocator#allocTime(Arrange)
     */
    public TimeUnit[] allocTimes(Arrange arrange) {
        // modify 2005-11-30
        return allocTimes(arrange, arrange.getArrangeInfo().getWeekUnits().intValue()
                / arrange.getArrangeInfo().getCourseUnits().intValue());
    }
    
    /**
     * @see TimeAllocator#allocTime(Arrange)
     */
    public TimeUnit allocTime(Arrange arrange) {
        return allocTimes(arrange, 1)[0];
    }
    
    /**
     * Generator a TimeUnit object according index in occupy string.<br>
     * Do not change the state of occupy .
     * 
     * @param index
     * @param arrange
     * @return
     */
    private TimeUnit[] genTimeUnit(int index, Arrange arrange) {
        TeachCalendar calendar = arrange.getCalendar();
        ArrangeInfo arrangeInfo = arrange.getArrangeInfo();
        
        TimeUnit times[] = TimeUnitUtil.buildTimeUnits(calendar.getStartYear(), calendar
                .getWeekStart().intValue(), arrangeInfo.getWeekStart().intValue(), arrangeInfo
                .getWeekStart().intValue()
                + arrangeInfo.getWeeks().intValue() - 1, arrangeInfo.getWeekCycle().intValue());
        for (int i = 0; i < times.length; i++) {
            times[i].setStartUnit(new Integer(index % TeachCalendar.MAXUNITS + 1));
            times[i].setEndUnit(new Integer(times[i].getStartUnit().intValue()
                    + arrangeInfo.getCourseUnits().intValue() - 1));
            // times[i].setWeekCycle(arrangeInfo.getWeekCycle());
            times[i].setWeekId(new Integer(index / TeachCalendar.MAXUNITS + 1));
        }
        return times;
    }
    
    /**
     * check teacher and adminClasses's availablity if needed. If teacher is null,detection is
     * ommited.
     * 
     * @param time
     * @param arrange
     * @return
     */
    private boolean checkTimeAvailable(TimeUnit[] times, Arrange arrange) {
        boolean satisfies = true;
        for (int i = 0; i < times.length; i++) {
            TimeUnit time = (TimeUnit) times[i];
            // 教师冲突
            if (arrange.getSuite().getParams().getDetectCollision()[ArrangeParams.TEACHER]) {
                Collection teachers = arrange.getTeachers();
                if (!teachers.isEmpty()) {
                    satisfies &= !ResourceDAO.isTeachersOccupied(time, teachers);
                }
                if (!satisfies)
                    break;
            }
            
            // 行政班冲突
            if (arrange.getSuite().getParams().getDetectCollision()[ArrangeParams.CLASS]) {
                for (Iterator iter = arrange.getAdminClasses().iterator(); iter.hasNext();) {
                    AdminClass adminClass = (AdminClass) iter.next();
                    satisfies &= !ResourceDAO.isAdminClassOccupied(time, adminClass.getId());
                    if (!satisfies)
                        break;
                }
            }
        }
        return satisfies;
    }
    
    /**
     * alloc timeUnit for arrange with demanding count.
     * 
     * @see com.shufe.service.course.arrange.task.auto.TimeAllocator#allocTimes(com.shufe.service.course.arrange.ArrangeCase)
     */
    private TimeUnit[] allocTimes(Arrange arrange, int allocCount) {
        Object[] timeArrays = new Object[allocCount];
        // check occupy time
        if (arrange.getAvailUnitBitMap().indexOf("1") == -1)
            return new TimeUnit[allocCount];
        ArrangeInfo arrangeInfo = arrange.getArrangeInfo();
        int courseUnits = arrangeInfo.getCourseUnits().intValue();
        if (arrangeInfo.getWeekUnits().intValue() > StringUtil.countChar(arrange
                .getAvailUnitBitMap(), '1'))
            return new TimeUnit[allocCount];
        /*-------------get available time and occupy time------------------*/
        String availBitMap = arrange.getAvailUnitBitMap();
        StringBuffer unitAvail = new StringBuffer(availBitMap);
        StringBuffer priorityAvail = new StringBuffer(BitStringUtil.andWith(arrange
                .getAvailableTime().getAvailable(), availBitMap));
        /*-----------calculate navigateCount-----------------------------*/
        int navigateCount = params.getDensity() / courseUnits;
        
        int curIndex = availBitMap.indexOf('1');
        int i = 0, get = 0;
        
        while (i < navigateCount) {
            if (get == allocCount)
                break;
            int findIndex = -1;
            /*---------find subString of maxPriority in scope [curIndex,end]----*/
            for (int k = 9; k > 0; k--) {
                findIndex = findPriority(priorityAvail, curIndex, AvailableTime.PRIORITIES[k],
                        courseUnits);
                if (findIndex != -1)
                    break;
            }
            /*----if occupy[curIndex,end]  is occupyed,reset curIndex to zero---*/
            if (findIndex == -1) {
                if (curIndex == 0)
                    break;
                else
                    curIndex = 0;
                continue;
            } else {
                /*--------------------occpu this stage of time------------------*/
                for (int m = 0; m < courseUnits; m++) {
                    priorityAvail.setCharAt(findIndex + m, '0');
                    unitAvail.setCharAt(findIndex + m, '0');
                }
                timeArrays[get] = genTimeUnit(findIndex, arrange);
                if (checkTimeAvailable((TimeUnit[]) timeArrays[get], arrange))
                    get++;
            }
            /*----------find next availble time in step of density-------*/
            curIndex = (curIndex + params.getDensity()) % (AvailableTime.MAX_LENGTH);
            i++;
        }
        if (get < allocCount)
            return new TimeUnit[allocCount];
        
        arrange.setUnitOccupy(new String(unitAvail));
        return assemble(timeArrays);
    }
    
    private TimeUnit[] assemble(Object timeArray[]) {
        TimeUnit[] times = null;
        if (((TimeUnit[]) timeArray[0]).length == 2)
            times = new TimeUnit[timeArray.length * 2];
        else
            times = new TimeUnit[timeArray.length];
        
        for (int j = 0; j < timeArray.length; j++) {
            TimeUnit[] subTimes = (TimeUnit[]) timeArray[j];
            if (null != subTimes)
                for (int k = 0; k < subTimes.length; k++) {
                    times[j * subTimes.length + k] = subTimes[k];
                }
        }
        return times;
    }
    
    /**
     * lookup a subString start with priority of length courseUnits from fromIndex in strBuffer.
     * 
     * 
     * @param strBuffer
     * @param fromIndex
     * @param priority
     * @param courseUnits
     * @return
     */
    private int findPriority(StringBuffer strBuffer, int fromIndex, String priority, int courseUnits) {
        if (fromIndex >= strBuffer.length())
            return -1;
        int highest = strBuffer.indexOf(priority, fromIndex);
        if (highest == -1)
            return -1;
        else {
            boolean finded = true;
            int startUnitIndex = highest % TeachCalendar.MAXUNITS;
            
            for (int i = 0; i < courseUnits; i++) {
                if (strBuffer.charAt(highest + i) == '0') {
                    finded = false;
                    break;
                } else {
                    // 如果找的不在同一天,则返回
                    int nextUnitIndex = (highest + i) % TeachCalendar.MAXUNITS;
                    if (nextUnitIndex != 0 && nextUnitIndex < startUnitIndex)
                        break;
                }
            }
            if (finded)
                return highest;
            else
                return findPriority(strBuffer, highest + courseUnits, priority, courseUnits);
        }
    }
    
    /**
     * @return Returns the timeParams.
     */
    public ArrangeParams getTimeParams() {
        return params;
    }
    
    /**
     * @param timeParams
     *            The timeParams to set.
     */
    public void setTimeParams(ArrangeParams timeParams) {
        this.params = timeParams;
    }
    
    /**
     * @param teachResourceDAO
     *            The teachResourceDAO to set.
     */
    public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO) {
        ResourceDAO = teachResourceDAO;
    }
    
}