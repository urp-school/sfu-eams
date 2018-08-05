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
 * chaostone             2006-6-18            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.model.Entity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.web.action.course.arrange.task.CourseTableAction;

/**
 * 课程表
 * 
 * @author chaostone
 * @see CourseTableAction
 */
public class CourseTable {
	// 常用课表类型
	public static final String CLASS = "class";

	public static final String STD = "std";

	public static final String TEACHER = "teacher";

	public static final String ROOM = "room";

	public static final Map resourceClass = new HashMap();
	static {
		resourceClass.put(CLASS, AdminClass.class);
		resourceClass.put(STD, Student.class);
		resourceClass.put(TEACHER, Teacher.class);
		resourceClass.put(ROOM, Classroom.class);
	}

	String kind;

	Entity resource;

	List tasks;

	List tasksGroups = null;

	List activities = new ArrayList();

	Float credits = null;

	public CourseTable(Entity resource, String kind) {
		this.kind = kind;
		this.resource = resource;
	}

	/**
	 * 该课程表中的对应学分总和
	 * 
	 * @return
	 */
	public Float getCredits() {
		if (null == credits) {
			if (null == tasks) {
				return null;
			} else {
				float credits = 0;
				for (Iterator iter = tasks.iterator(); iter.hasNext();) {
					TeachTask task = (TeachTask) iter.next();
					credits += task.getCourse().getCredits().floatValue();
				}
				return new Float(credits);
			}
		} else {
			return credits;
		}
	}

	public void extractTaskFromActivity() {
		if (null == activities) {
			tasks = Collections.EMPTY_LIST;
			return;
		}
		tasks = new ArrayList();
		HashSet taskSet = new HashSet();
		for (Iterator iter = activities.iterator(); iter.hasNext();) {
			TeachTask taskInActivity = ((CourseActivity) iter.next()).getTask();
			if (!taskSet.contains(taskInActivity)) {
				taskSet.add(taskInActivity);
			}
		}
		tasks = new ArrayList(taskSet);
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public List getActivities() {
		return activities;
	}

	public void setActivities(List activities) {
		this.activities = activities;
	}

	public Entity getResource() {
		return resource;
	}

	public void setResource(Entity resource) {
		this.resource = resource;
	}

	public List getTasks() {
		return tasks;
	}

	public void setTasks(List tasks) {
		this.tasks = tasks;
	}

	public void addActivities(Collection activities) {
		this.activities.addAll(activities);
	}

	public List getTasksGroups() {
		return tasksGroups;
	}

	public void setTasksGroups(List tasksGroups) {
		this.tasksGroups = tasksGroups;
	}

	public void setCredits(Float credits) {
		this.credits = credits;
	}

	public static Class getResourceClass(String kind) {
		Class clazz = (Class) resourceClass.get(kind);
		if (null != clazz)
			return clazz;
		else
			throw new RuntimeException("not supported Resource type:" + kind);
	}
}
