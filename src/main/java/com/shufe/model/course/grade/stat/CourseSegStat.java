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
 * chaostone             2006-12-27            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.stat;

import java.util.List;

import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;

public class CourseSegStat extends GradeSegStats {

	private Course course;

	private TeachCalendar calendar;
	
	public CourseSegStat(Course course,TeachCalendar calendar,List courseGrades) {
		this.course = course;
		this.calendar=calendar;
		this.courseGrades = courseGrades;
	}

	public CourseSegStat(int segs) {
		super(segs);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}
	
}
