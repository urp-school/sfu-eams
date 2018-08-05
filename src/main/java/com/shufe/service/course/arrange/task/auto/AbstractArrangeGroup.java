//$Id: AbstractArrangeGroup.java May 8, 2008 11:21:10 AM chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2008. All rights reserved.
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
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      May 8, 2008  Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.auto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;

public abstract class AbstractArrangeGroup implements Arrange {
	protected String name;
	/**
	 * 安排的优先级
	 */
	protected int priority = defaultPriority;
	/**
	 * 所要安排的教学任务
	 */
	protected List tasks = new ArrayList();
	/**
	 * 所在的排课组
	 */
	protected ArrangeSuite suite;
	/**
	 * 可用的时间
	 */
	protected AvailableTime availableTime;

	/**
	 * 所要排的课程组
	 */
	protected TaskGroup group;
	
	/**
	 * 该组的安排参数
	 */
	protected ArrangeInfo arrangeInfo;
    /**
     * 对于可用时间的占用
     */
    protected String unitOccupy;
    
	public AbstractArrangeGroup() {
        super();
    }

    public AbstractArrangeGroup(TaskGroup group) {
        this.group = group;
        this.availableTime = group.getSuggest().getTime();
        tasks.addAll(CollectionUtils.select(group.getTaskList(), new Predicate() {
            public boolean evaluate(Object arg0) {
                return !Boolean.TRUE.equals(((TeachTask) arg0).getArrangeInfo()
                        .getIsArrangeComplete());
            }
        }));
        setAvailableTime(group.getSuggest().getTime());
        setPriority(group.getPriority().intValue());
    }

    public int countArrangeCases() {
		return tasks.size();
	}

	public String getName() {
		if (null == name)
			return group.getName();
		else
			return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public ArrangeInfo getArrangeInfo() {
		if (null == arrangeInfo)
			return ((TeachTask) (tasks.iterator().next())).getArrangeInfo();
		else
			return arrangeInfo;
	}

	public void setArrangeInfo(ArrangeInfo arrangeInfo) {
		this.arrangeInfo = arrangeInfo;
	}
	public Collection getAdminClasses() {
		return group.getAdminClasses();
	}

	public Collection getTasks() {
		return tasks;
	}

	public void setTasks(List tasks) {
		this.tasks = tasks;
	}

	public ArrangeSuite getSuite() {
		return suite;
	}

	public void setSuite(ArrangeSuite suite) {
		this.suite = suite;
	}

	public AvailableTime getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(AvailableTime availableTime) {
		this.availableTime = availableTime;
	}

	public TaskGroup getGroup() {
		return group;
	}

	public void setGroup(TaskGroup group) {
		this.group = group;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
    public TeachCalendar getCalendar(){
          return ((TeachTask) (tasks.iterator().next())).getCalendar();
    }
    public String getAvailUnitBitMap() {
        return unitOccupy;
    }

    public void setUnitOccupy(String unitOccupy) {
        this.unitOccupy = unitOccupy;
    }
    public Collection getTeachers() {
        List teachers = new LinkedList();

        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            teachers.addAll(task.getArrangeInfo().getTeachers());
        }
        return teachers;
    }
 
}
