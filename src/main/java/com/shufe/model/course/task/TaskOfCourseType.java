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
 * chaostone             2006-10-16            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.task;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.system.baseinfo.AdminClass;

/**
 * 
 * 对应计划中没有课程的课程组"生成"的"任务".<br>
 * 由于计划中的类似"限定选修模块课"等课程组,仅仅规定了课程的开课学期和学分,周课时.<br>
 * 其中并没有任何课程可言,但是为了知道计划对应的班级每学期应该上哪些选修课以备开课,<br>
 * 故此采用该类以代表,但并不存储.<br>
 * 
 * 他和教学任务共同组成了班级的"开课情况表"<br>
 * 在此意义上,TeachTask 可以称之为TaskOfCourse<br>
 * 
 * @author chaostone
 * 
 */
public class TaskOfCourseType {

	private CourseType courseType;

	private AdminClass adminClass;

	private Float credits;

	public TaskOfCourseType() {
		super();
	}

	public TaskOfCourseType(CourseType courseType, AdminClass adminClass,
			Float credit) {
		this.courseType = courseType;
		this.adminClass = adminClass;
		this.credits = credit;
	}

	public AdminClass getAdminClass() {
		return adminClass;
	}

	public void setAdminClass(AdminClass adminClass) {
		this.adminClass = adminClass;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	public Float getCredits() {
		return credits;
	}

	public void setCredits(Float credit) {
		this.credits = credit;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ((null == courseType) ? "null" : courseType.getName()) + " "
				+ ((null == adminClass) ? "null" : adminClass.getName());
	}
}
