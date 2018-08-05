//$Id: TaskInDepartment.java,v 1.1 2009-4-3 上午10:31:20 zhouqi Exp $
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
 * zhouqi              2009-4-3             Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.task;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.Department;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 排课任务归属院系
 * 
 * @author zhouqi
 * 
 */
public class TaskInDepartment extends LongIdObject {
    
    private static final long serialVersionUID = 6532196541294547962L;
    
    private TeachCalendar calendar;
    
    private Department department;
    
    private Set tasks;
    
    public void addTask(TeachTask task) {
        if (CollectionUtils.isEmpty(tasks)) {
            tasks = new HashSet();
        }
        tasks.add(task);
    }
    
    public void addAllTask(Collection tasks) {
        if (CollectionUtils.isEmpty(this.tasks)) {
            this.tasks = new HashSet();
        }
        this.tasks.addAll(tasks);
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public Set getTasks() {
        return tasks;
    }
    
    public void setTasks(Set tasks) {
        this.tasks = tasks;
    }
}
