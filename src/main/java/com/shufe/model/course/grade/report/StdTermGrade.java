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
 * chaostone             2007-1-3            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.report;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 学生每学期成绩
 * 
 * @author chaostone
 * 
 */
public class StdTermGrade extends StdGrade {
	private TeachCalendar calendar;

	private Float awardedCredit;

	public StdTermGrade() {
		super();
	}

	public StdTermGrade(Student std, List grades, Comparator cmp, Integer printGradeType) {
		super(std, grades, cmp, printGradeType);
	}

	public Float getElectedCredit() {
		if (null == grades || grades.isEmpty())
			return new Float(0);
		float credits = 0;
		for (Iterator iter = grades.iterator(); iter.hasNext();) {
			CourseGrade courseGrade = (CourseGrade) iter.next();
			credits += courseGrade.getCredit().floatValue();
		}
		return new Float(credits);
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Float getAwardedCredit() {
		return awardedCredit;
	}

	public void setAwardedCredit(Float awardCredit) {
		this.awardedCredit = awardCredit;
	}

}
