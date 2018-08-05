//$Id: StdCreditConstraint.java,v 1.3 2006/12/14 13:41:02 duanth Exp $
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
 * chaostone             2005-12-9         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.election;

import com.shufe.model.std.Student;

/**
 * 学生个人学分上限
 * 
 * @author chaostone 2005-12-9
 */
public class StdCreditConstraint extends CreditConstraint {

	private static final long serialVersionUID = -6627564288570998553L;

	/** 学生对象 */
	private Student std = new Student();

	/** 已选学分 */
	private Float electedCredit;

	/** 奖励学分 */
	private Float awardedCredit;

	/** 平均绩点 */
	private Float GPA;

	public StdCreditConstraint() {
		super();
	}

	/**
	 * @return Returns the std.
	 */
	public Student getStd() {
		return std;
	}

	/**
	 * @param std
	 *            The std to set.
	 */
	public void setStd(Student std) {
		this.std = std;
	}

	/**
	 * @return Returns the electedCredit.
	 */
	public Float getElectedCredit() {
		return electedCredit;
	}

	/**
	 * @param electedCredit
	 *            The electedCredit to set.
	 */
	public void setElectedCredit(Float electedCredit) {
		this.electedCredit = electedCredit;
	}

	/**
	 * 增加学分
	 * 
	 * @param credit
	 */
	public void addElectedCredit(Float credit) {
		if (null == getElectedCredit()) {
			setElectedCredit(credit);
		} else {
			setElectedCredit(new Float(credit.floatValue()
					+ getElectedCredit().floatValue()));
		}
	}

	/**
	 * 减去学分，学分下限不能低于0
	 * 
	 * @param removedCredit
	 */
	public void removeElectedCredit(Float removedCredit) {
		float credit = (null != getElectedCredit()) ? getElectedCredit()
				.floatValue() : 0;
		credit -= removedCredit.floatValue();
		if (credit < 0)
			credit = 0;
		setElectedCredit(new Float(credit));
	}

	public boolean checkElectable(Float credit) {
		return (((getElectedCredit() == null) ? 0 : getElectedCredit()
				.floatValue()) + credit.floatValue()) <= (((getAwardedCredit() == null) ? 0
				: getAwardedCredit().intValue()) + ((getMaxCredit() == null) ? 0
				: getMaxCredit().intValue()));
	}

	public Float getAwardedCredit() {
		return awardedCredit;
	}

	public void setAwardedCredit(Float awardedCredit) {
		this.awardedCredit = awardedCredit;
	}

	public Float getGPA() {
		return GPA;
	}

	public void setGPA(Float GPA) {
		this.GPA = GPA;
	}

}
