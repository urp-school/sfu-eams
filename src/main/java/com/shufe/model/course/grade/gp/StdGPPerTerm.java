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
 * chaostone             2007-1-9            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.gp;

import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 每学期绩点
 * 
 * @author chaostone
 * 
 */
public class StdGPPerTerm {

	TeachCalendar calendar;

	Float GPA; // 平均绩点

	Float GA;// 平均分

	Float credits;// 总学分

	Integer count; // 总成绩数量

	public StdGPPerTerm() {
		this.credits = new Float(0);
		this.GA = new Float(0);
		this.count = new Integer(0);
		this.GPA = new Float(0);
	}

	public StdGPPerTerm(TeachCalendar calendar, Float GPA) {
		this.calendar = calendar;
		this.GPA = GPA;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Float getGPA() {
		return GPA;
	}

	public void setGPA(Float gpa) {
		GPA = gpa;
	}

	public Float getGA() {
		return GA;
	}

	public void setGA(Float ga) {
		GA = ga;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Float getCredits() {
		return credits;
	}

	public void setCredits(Float credits) {
		this.credits = credits;
	}

}
