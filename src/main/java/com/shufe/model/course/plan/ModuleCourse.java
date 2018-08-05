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
 * chaostone             2006-11-6            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.plan;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.system.baseinfo.Course;

/**
 * 
 * 模块课<br>
 * 对应培养计划中,没有写明课程安排,但是实际上有具体隐含课程的类别.<br>
 * 暂时没有使用
 * 
 * @author chaostone
 * 
 */
public class ModuleCourse extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4418600832658819446L;

	private Course course;

	private CourseType courseType;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}
}
