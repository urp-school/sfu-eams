/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-6-26            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.resource;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.Arrangement;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 时间占用表
 * 
 * <pre>
 *     用来判断时间冲突,主要用于选课\学生考试.
 *     可以同时记录多个年份的时间占用情况。
 * </pre>
 * 
 * @author chaostone
 * 
 */
public class TimeTable  implements Serializable {
    
    /**
     * year->([key,value] 对应[unitIndex,occupyTime])
     */
    private Map yearOccupyMap;
    
    public TimeTable() {
        this.yearOccupyMap = new HashMap();
    }
    
    public TimeTable(List arrangements) {
        this.yearOccupyMap = new HashMap();
        for (Iterator iter = arrangements.iterator(); iter.hasNext();) {
            Arrangement arrangement = (Arrangement) iter.next();
            addArrangement(arrangement);
        }
    }
    
    public void addArrangement(Arrangement arrangement) {
        addActivities(arrangement.getArrangeInfo().getActivities());
    }
    
    public void addActivities(Collection activities) {
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();
            Map occupyMap = (Map) yearOccupyMap.get(activity.getTime().getYear());
            if (null == occupyMap) {
                occupyMap = new HashMap();
                yearOccupyMap.put(activity.getTime().getYear(), occupyMap);
            }
            // 记录每一个小节
            TimeUnit time = activity.getTime();
            for (int i = 0; i < time.getUnitCount(); i++) {
                Integer unitIndex = new Integer(time.getWeekId().intValue()
                        * TeachCalendar.MAXUNITS + time.getStartUnit().intValue() + i);
                Long weeksNum = (Long) occupyMap.get(unitIndex);
                // add it
                if (null == weeksNum) {
                    occupyMap.put(unitIndex, time.getValidWeeksNum());
                } else {
                    occupyMap.put(unitIndex, new Long(time.getValidWeeksNum().longValue()
                            | weeksNum.longValue()));
                }
            }
        }
    }
    
    /**
     * 删除一个安排的时间占用
     * 
     * @param arrangement
     */
    public void removeArrangement(Arrangement arrangement) {
        for (Iterator iter = arrangement.getArrangeInfo().getActivities().iterator(); iter
                .hasNext();) {
            Activity activity = (Activity) iter.next();
            TimeUnit time = activity.getTime();
            Map occupyMap = (Map) yearOccupyMap.get(activity.getTime().getYear());
            if (null == occupyMap) {
                continue;
            }
            // for every unit
            for (int i = 0; i < time.getUnitCount(); i++) {
                Integer unitIndex = new Integer(time.getWeekId().intValue()
                        * TeachCalendar.MAXUNITS + time.getStartUnit().intValue() + i);
                Long weeksNum = (Long) occupyMap.get(unitIndex);
                // abort the iterator
                if (null == weeksNum) {
                    continue;
                } else {
                    if (weeksNum.equals(time.getValidWeeksNum())) {
                        occupyMap.put(unitIndex, null);
                    } else {
                        occupyMap.put(unitIndex, new Long(time.getValidWeeksNum().longValue()
                                ^ weeksNum.longValue()));
                    }
                }
            }
        }
    }
    
    /**
     * 判断一个教学任务的排课活动是否冲突
     * 
     * @param arrangement
     * @return
     */
    public boolean isTimeConflict(Arrangement arrangement) {
        return isTimeConflict(arrangement.getArrangeInfo().getActivities());
    }
    
    /**
     * 判断给定的教学活动是否与既有的活动冲突<br>
     * 不判断给定活动内部的冲突.
     * 
     * @param activities
     * @return
     */
    public boolean isTimeConflict(Collection activities) {
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();
            TimeUnit time = activity.getTime();
            Map occupyMap = (Map) yearOccupyMap.get(activity.getTime().getYear());
            if (null == occupyMap) {
                continue;
            }
            // for every unit
            for (int i = 0; i < time.getUnitCount(); i++) {
                Integer unitIndex = new Integer(time.getWeekId().intValue()
                        * TeachCalendar.MAXUNITS + time.getStartUnit().intValue() + i);
                Long weeksNum = (Long) occupyMap.get(unitIndex);
                if (null == weeksNum) {
                    continue;
                } else {
                    if ((weeksNum.longValue() & activity.getTime().getValidWeeksNum().longValue()) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}