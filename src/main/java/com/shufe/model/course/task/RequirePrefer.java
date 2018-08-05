//$Id: RequirePrefer.java,v 1.2 2006/10/12 12:03:40 duanth Exp $
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
 * chaostone             2005-12-18         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.task;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 教师上课的课程要求偏好<br>
 * 该偏好是指任课教师可以将自己上课较为稳定的要求设定为偏好，填写<br>
 * 任务要求时，如果按照偏好批量设置较为省时省力。<br>
 * 
 * @author chaostone 2005-12-18
 */
public class RequirePrefer extends LongIdObject {
	private static final long serialVersionUID = -7335843714667994840L;
	private Teacher teacher = new Teacher();

	private Course course = new Course();

	private TaskRequirement require = new TaskRequirement();

	public RequirePrefer() {
	}

	public RequirePrefer(Teacher teacher, Course course) {
		this.teacher = teacher;
		this.course = course;
	}

	/**
	 * @return Returns the course.
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course
	 *            The course to set.
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return Returns the require.
	 */
	public TaskRequirement getRequire() {
		return require;
	}

	/**
	 * @param require
	 *            The require to set.
	 */
	public void setRequire(TaskRequirement require) {
		this.require = require;
	}

	/**
	 * @return Returns the teacher.
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher
	 *            The teacher to set.
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
