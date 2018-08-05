package com.shufe.model.course.grade;

import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.security.User;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

public class MoralGrade extends AbstractGrade {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1625912046204811539L;
	public void updateScore(Float score, User who) {

	}

	public MoralGrade() {
		super();
	}

	public MoralGrade(Student std, TeachCalendar calendar, MarkStyle markStyle) {
		this.std=std;
		this.calendar=calendar;
		this.markStyle=markStyle;
	}
	/**
	 * 更新是否及格的状态
	 * 
	 * @see MarkStyle#isPass(Float)
	 */
	public void updateScore(Float score) {
		this.score=score;
		setIsPass(markStyle.isPass(score));
	}
}
