//$Id: StudentCalendarCreditFee.java,v 1.1 2007 六月 19 16:48:09 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007 六月 19         Created
 *  
 ********************************************************************************/

package com.shufe.model.fee.stat;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;

public class CreditFeeStat extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2848121169595997825L;

	private Student student = new Student();

	private String year;

	private String term;

	private Integer credits;

	private Float payed;

	private Float creditFee;

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term
	 *            the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return the credits
	 */
	public Integer getCredits() {
		return credits;
	}

	/**
	 * @param credits
	 *            the credits to set
	 */
	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	/**
	 * @return the payed
	 */
	public Float getPayed() {
		return payed;
	}

	/**
	 * @param payed
	 *            the payed to set
	 */
	public void setPayed(Float payed) {
		this.payed = payed;
	}

	/**
	 * @return the perTermFee
	 */
	public Float getCreditFee() {
		return creditFee;
	}

	/**
	 * @param perTermFee
	 *            the perTermFee to set
	 */
	public void setCreditFee(Float perTermFee) {
		this.creditFee = perTermFee;
	}
}
