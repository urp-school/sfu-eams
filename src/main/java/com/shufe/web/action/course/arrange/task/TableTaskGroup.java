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
 * chaostone             2006-10-14            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.course.task.TeachTask;

public class TableTaskGroup implements Comparable {

	public TableTaskGroup() {
		super();
	}

	public TableTaskGroup(CourseType type) {
		this.type = type;
		credit = new Float(0);
		actualCredit = new Float(0);
	}

	CourseType type;

	List tasks = new ArrayList();

	Float credit;

	Float actualCredit;

	public CourseType getType() {
		return type;
	}

	public void setType(CourseType type) {
		this.type = type;
	}

	public Float getActualCredit() {
		return actualCredit;
	}

	public void setActualCredit(Float actualCredit) {
		this.actualCredit = actualCredit;
	}

	public Float getCredit() {
		return credit;
	}

	public void setCredit(Float credit) {
		this.credit = credit;
	}

	public List getTasks() {
		return tasks;
	}

	public void setTasks(List tasks) {
		this.tasks = tasks;
	}

	public void addTask(TeachTask task) {
		tasks.add(task);
		if (null == actualCredit) {
			actualCredit = task.getCourse().getCredits();
		} else {
			actualCredit = new Float(actualCredit.floatValue()
					+ task.getCourse().getCredits().floatValue());
		}
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		TableTaskGroup myClass = (TableTaskGroup) object;
		return new CompareToBuilder().append(this.type.getPriority(),
				myClass.type.getPriority()).toComparison();
	}

}
