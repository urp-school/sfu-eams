// $Id: TaskActivity.java,v 1.3 2006/11/20 07:40:06 duanth Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/***********************************************************************************************************************
 * @author chaostone MODIFICATION DESCRIPTION Name Date Description ============ ============ ============ chaostone
 *         2005-11-22 Created
 **********************************************************************************************************************/
package com.shufe.model.course.arrange;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;

/**
 * 与任务有关的教学活动
 * 
 * @author chaostone 2005-11-22
 */
public class TaskActivity extends Activity implements Serializable {
    
    private static final long serialVersionUID = 2498530728105897805L;
    
    protected TeachTask task = new TeachTask();// 教学任务
    
    protected TeachCalendar calendar = new TeachCalendar();
    
    // 默认构造函数
    public TaskActivity() {
        
    }
    
    /**
     * @return Returns the task.
     */
    public TeachTask getTask() {
        
        return task;
    }
    
    /**
     * @param task
     *            The task to set.
     */
    public void setTask(TeachTask task) {
        
        this.task = task;
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Object clone() throws CloneNotSupportedException {
        TaskActivity activity = new TaskActivity();
        activity.setRoom(room);
        activity.setCalendar(calendar);
        activity.setTask(task);
        activity.setTime((TimeUnit) time.clone());
        return activity;
    }
    
    /**
     * 根据提示的安排周字符串，得到具体的周状态<br>
     * 比如： 1-18
     * 
     * @param validWeeks
     * @param calendar
     * @param arrangeInfo
     * @param resourses
     * @param locale
     * @return
     */
    public String getWeekState() {
        String weekState = TimeUnitUtil.digest(time.getValidWeeks(), calendar.getWeekStart()
                .intValue(), task.getArrangeInfo().getWeekStart().intValue(), task.getArrangeInfo()
                .getEndWeek(), null, null);
        return StringUtils.remove(StringUtils.remove(weekState, "["), "]");
    }
    
    /**
     * 地点
     * 
     * @return
     */
    public String getRoomPlace() {
        Collection activities = new ArrayList();
        activities.add(this);
        return CourseActivityDigestor.digest(calendar, task.getArrangeInfo(), activities, null,
                null, CourseActivityDigestor.room);
    }
    
    /**
     * 提供占用周串，得到该起始周<br>
     * 
     * 返回值-1表示没有占用周，否则为该串起始周
     * 
     * @param weekStringValues
     * @return
     * @throws Exception
     */
    public int getActivityFirstWeek() {
        if (StringUtils.isEmpty(this.time.getValidWeeks())) {
            return -1;
        }
        String LastDay = calendar.getStartYear() + "-12-31";
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(Date.valueOf(LastDay));
        boolean endAtSat = (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
        
        String weekStr= time.getWeekState();
        if(this.time.getYear() != calendar.getStartYear()){
        	if(endAtSat) weekStr += weekStr;
        	else weekStr += weekStr.substring(1);
        }
        for (int i = calendar.getWeekStart().intValue() - 1; i < weekStr.length(); i++) {
            if (weekStr.charAt(i) == '1') {
                return i - calendar.getWeekStart().intValue() + 2;
            }
        }
        return -1;
    }
    
    public int getActivityLastWeek() {
        String LastDay = calendar.getStartYear() + "-12-31";
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(Date.valueOf(LastDay));
        boolean endAtSat = (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
        
        String weekStr= time.getWeekState();
        if(this.time.getYear() != calendar.getStartYear()){
        	if(endAtSat) weekStr += weekStr;
        	else weekStr += weekStr.substring(1);
        }
        for (int i = weekStr.length()-1;i >= calendar.getWeekStart().intValue(); i--) {
            if (weekStr.charAt(i) == '1') {
                return i - calendar.getWeekStart().intValue() + 2;
            }
        }
        return -1;
    }
}
