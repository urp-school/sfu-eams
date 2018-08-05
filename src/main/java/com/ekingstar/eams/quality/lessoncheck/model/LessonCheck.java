// $Id: Activity.java,v 1.3 2008/05/22 10:13:30 maple Exp $
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
 * chaostone             2008/05/22         Created
 *  
 ********************************************************************************/
package com.ekingstar.eams.quality.lessoncheck.model;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.LessonCheckType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 督导组听课 对应数据表是 JXRW_DDZTK_T
 * 
 * @author chaostone 2008-5-22
 */
public class LessonCheck extends LongIdObject{
    
    private static final long serialVersionUID = -8368765912318736247L;
    
    /** 教学日历* */
    private TeachCalendar calendar;
    
    /** 教学任务* */
    private TeachTask task;
    
    /** 上课时间* */
    private Date checkAt;
    
    /** 上课地点* */
    private Classroom checkRoom;
    
    /** 听课类别* */
    private LessonCheckType lessonCheckType;
    
    /** 听课对象* */
    private String checkers;
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Date getCheckAt() {
        return checkAt;
    }
    
    public void setCheckAt(Date checkAt) {
        this.checkAt = checkAt;
    }
    
    public String getCheckers() {
        return checkers;
    }
    
    public void setCheckers(String checkers) {
        this.checkers = checkers;
    }
    
    
    public LessonCheckType getLessonCheckType() {
        return lessonCheckType;
    }
    
    public void setLessonCheckType(LessonCheckType lessonCheckType) {
        this.lessonCheckType = lessonCheckType;
    }
    
    public TeachTask getTask() {
        return task;
    }
    
    public void setTask(TeachTask task) {
        this.task = task;
    }

    public Classroom getCheckRoom() {
        return checkRoom;
    }

    public void setCheckRoom(Classroom checkRoom) {
        this.checkRoom = checkRoom;
    }
    
}
