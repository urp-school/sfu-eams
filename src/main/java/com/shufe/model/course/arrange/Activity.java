// $Id: Activity.java,v 1.3 2007/01/17 15:06:30 duanth Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-11-8         Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学活动
 * 
 * @author chaostone 2005-11-22
 */
public class Activity extends LongIdObject implements Serializable {
    
    private static final long serialVersionUID = 2498530728105897805L;
    
    protected Classroom room = new Classroom();// 教室/考场/活动场地
    
    protected TimeUnit time = new TimeUnit();// 时间安排
    
    // 默认构造函数
    public Activity() {
        
    }
    
    /**
     * @param room
     * @param time
     */
    public Activity(Classroom room, TimeUnit time) {
        this.room = room;
        this.time = time;
    }
    
    public Classroom getRoom() {
        
        return room;
    }
    
    public void setRoom(Classroom room) {
        
        this.room = room;
    }
    
    public TimeUnit getTime() {
        
        return time;
    }
    
    public void setTime(TimeUnit time) {
        
        this.time = time;
    }
    
    /**
     * 第一次活动时间<br>
     * 细致到具体时间
     * 
     * @param calendar
     * @return
     */
    public Date getFirstActivityTime(TeachCalendar calendar) {
        if (null != time) {
            java.util.Date date = time.getFirstDay();
            if (null == date)
                return null;
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            CourseUnit unit = calendar.getTimeSetting().getCourseUnit(
                    time.getStartUnit().intValue());
            if (null != unit) {
                gc.set(Calendar.HOUR_OF_DAY, unit.getStartTime().intValue() / 100);
                gc.set(Calendar.MINUTE, unit.getStartTime().intValue() % 100);
            }
            return new Date(gc.getTimeInMillis());
        } else
            return null;
    }
    
    public String getUnits() {
        String units = "";
        for (int i = time.getStartUnit().intValue(); i <= time.getEndUnit().intValue(); i++) {
            units += time.getWeekId().intValue() + (i < 10 ? "0" + i : "" + i) + " ";
        }
        return units.trim();
    }
    
    public java.util.Date getStartDay() {
        if (null != this.time.getYear() && null != this.time.getValidWeeks()
                && null != this.time.getWeekId()) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(Calendar.YEAR, this.time.getYear().intValue());
            calendar.set(Calendar.WEEK_OF_YEAR, this.time.getValidWeeks().lastIndexOf('1') + 1);
            calendar.set(Calendar.DAY_OF_WEEK, (this.time.getWeekId().intValue() + 1) % 7);
            if (null != this.time.getStartTime()) {
                calendar.set(Calendar.HOUR_OF_DAY, this.time.getStartTime().intValue() / 100);
                calendar.set(Calendar.MINUTE, this.time.getStartTime().intValue() % 100);
            }
            return calendar.getTime();
        }
        return null;
    }
    
    public java.util.Date getEndDay() {
        if (null != this.time.getYear() && null != this.time.getValidWeeks()
                && null != this.time.getWeekId()) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(Calendar.YEAR, this.time.getYear().intValue());
            calendar.set(Calendar.WEEK_OF_YEAR, this.time.getValidWeeks().lastIndexOf('1') + 1);
            calendar.set(Calendar.DAY_OF_WEEK, (this.time.getWeekId().intValue() + 1) % 7);
            if (null != this.time.getEndTime()) {
                calendar.set(Calendar.HOUR_OF_DAY, this.time.getEndTime().intValue() / 100);
                calendar.set(Calendar.MINUTE, this.time.getEndTime().intValue() % 100);
            }
            return calendar.getTime();
        }
        return null;
    }
    
}
