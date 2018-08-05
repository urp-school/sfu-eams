//$Id: SelfAnnotate.java,v 1.3 2006/11/22 10:25:54 cwx Exp $
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
 * chenweixiong              2006-11-7         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.annotate;

import com.ekingstar.commons.model.Component;

/**
 * 自评表
 * 
 * @author chaostone
 * 
 */
public class SelfAnnotate implements Component {

	/** 创新点1 */
	private String innovateOne;

	/** 创新点2 */
	private String innovateTwo;

	/** 创新点3 */
	private String innovateThree;

	/** 论文不足 */
	private String thesisLack;// 
	/*
	 * 专家对创新点的结论 其中创新点用a,b,c,d表示
	 */
	/** 创新点1专家结论 */
	private String innovateOneTeacher;

	/** 创新点2专家结论 */
	private String innovateTwoTeacher;

	/** 创新点3专家结论 */
	private String innovateThreeTeacher;
	/** 结论 */
	private String lackTeacher;// 不足A(确切)、B（较确切）、C（一般）、D（不确切）

	/**
	 * @return Returns the innovateOne.
	 */
	public String getInnovateOne() {
		return innovateOne;
	}

	/**
	 * @param innovateOne
	 *            The innovateOne to set.
	 */
	public void setInnovateOne(String innovateOne) {
		this.innovateOne = innovateOne;
	}

	/**
	 * @return Returns the innovateThree.
	 */
	public String getInnovateThree() {
		return innovateThree;
	}

	/**
	 * @param innovateThree
	 *            The innovateThree to set.
	 */
	public void setInnovateThree(String innovateThree) {
		this.innovateThree = innovateThree;
	}

	/**
	 * @return Returns the innovateTwo.
	 */
	public String getInnovateTwo() {
		return innovateTwo;
	}

	/**
	 * @param innovateTwo
	 *            The innovateTwo to set.
	 */
	public void setInnovateTwo(String innovateTwo) {
		this.innovateTwo = innovateTwo;
	}

	/**
	 * @return Returns the thesisLack.
	 */
	public String getThesisLack() {
		return thesisLack;
	}

	/**
	 * @param thesisLack
	 *            The thesisLack to set.
	 */
	public void setThesisLack(String thesisLack) {
		this.thesisLack = thesisLack;
	}

	/**
	 * @return Returns the innovateOneTeacher.
	 */
	public String getInnovateOneTeacher() {
		return innovateOneTeacher;
	}

	/**
	 * @param innovateOneTeacher
	 *            The innovateOneTeacher to set.
	 */
	public void setInnovateOneTeacher(String innovateOneTeacher) {
		this.innovateOneTeacher = innovateOneTeacher;
	}

	/**
	 * @return Returns the innovateThreeTeacher.
	 */
	public String getInnovateThreeTeacher() {
		return innovateThreeTeacher;
	}

	/**
	 * @param innovateThreeTeacher
	 *            The innovateThreeTeacher to set.
	 */
	public void setInnovateThreeTeacher(String innovateThreeTeacher) {
		this.innovateThreeTeacher = innovateThreeTeacher;
	}

	/**
	 * @return Returns the innovateTwoTeacher.
	 */
	public String getInnovateTwoTeacher() {
		return innovateTwoTeacher;
	}

	/**
	 * @param innovateTwoTeacher
	 *            The innovateTwoTeacher to set.
	 */
	public void setInnovateTwoTeacher(String innovateTwoTeacher) {
		this.innovateTwoTeacher = innovateTwoTeacher;
	}

	/**
	 * @return Returns the lackTeacher.
	 */
	public String getLackTeacher() {
		return lackTeacher;
	}

	/**
	 * @param lackTeacher
	 *            The lackTeacher to set.
	 */
	public void setLackTeacher(String lackTeacher) {
		this.lackTeacher = lackTeacher;
	}

}
