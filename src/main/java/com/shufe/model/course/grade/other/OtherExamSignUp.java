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
 * 塞外狂人             2006-9-23            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.other;

import java.io.Serializable;
import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 其他校内外考试报名记录
 * 
 * @author 塞外狂人,chaostone
 * 
 */
public class OtherExamSignUp extends LongIdObject implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 教学日历
	 */
	private TeachCalendar calendar = new TeachCalendar();

	/**
	 * 学生
	 */
	private Student std = new Student();

	/**
	 * 种类
	 */
	private OtherExamCategory category = new OtherExamCategory();

	/**
	 * 报名时间
	 */
	private Date signUpAt;

	public OtherExamSignUp() {
		super();
	}

	public OtherExamSignUp(TeachCalendar calendar, Student std,
			OtherExamCategory category, Date signUpAt) {
		super();
		this.calendar = calendar;
		this.std = std;
		this.category = category;
		this.signUpAt = signUpAt;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public OtherExamCategory getCategory() {
		return category;
	}

	public void setCategory(OtherExamCategory category) {
		this.category = category;
	}

	public Date getSignUpAt() {
		return signUpAt;
	}

	public void setSignUpAt(Date signUpAt) {
		this.signUpAt = signUpAt;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}
}
