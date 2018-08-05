//$Id: MonitorTeacher.java,v 1.1 2008-3-11 下午06:05:05 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-3-11         	Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.exam;

import org.apache.commons.lang.ObjectUtils;

import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;

public class MonitorTeacher {
    
    private Teacher teacher;
    
    private TeachTask task;
    
    private Classroom room;
    
    public boolean equals(Object obj) {
        MonitorTeacher emTeacher = (MonitorTeacher) obj;
        return (this.teacher.getId().equals(emTeacher.getTeacher().getId())
                && this.task.getId().equals(emTeacher.getTask().getId()) && ObjectUtils.equals(
                this.room, emTeacher.getRoom()));
    }
    
    public Classroom getRoom() {
        return room;
    }
    
    public void setRoom(Classroom room) {
        this.room = room;
    }
    
    public TeachTask getTask() {
        return task;
    }
    
    public void setTask(TeachTask task) {
        this.task = task;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public String getTeacherName() {
        return teacher.getName();
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
