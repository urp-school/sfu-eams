//$Id: UndergraduateDegreeInfo.java,v 1.1 2007-5-8 16:15:57 Administrator Exp $
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
 * chenweixiong              2007-5-8         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.degree;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.School;
import com.ekingstar.eams.system.basecode.state.SubjectCategory;

/**
 * 本科学位信息
 * 
 * @author chaostone
 * 
 */
public class UndergraduateDegreeInfo extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1505388764203219562L;

	/** 本科毕业院校 学士学位授予单位 */
	private School school;

	/** 大学毕业专业 */
	private String specialityName;

	/** 学科门类 */
	private SubjectCategory subjectCategory;

	/** 毕业时间 */
	private String graduateOn;

	/**
	 * @return Returns the graduateOn.
	 */
	public String getGraduateOn() {
		return graduateOn;
	}

	/**
	 * @param graduateOn
	 *            The graduateOn to set.
	 */
	public void setGraduateOn(String graduateOn) {
		this.graduateOn = graduateOn;
	}

	/**
	 * @return Returns the school.
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @param school
	 *            The school to set.
	 */
	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * @return Returns the specialityName.
	 */
	public String getSpecialityName() {
		return specialityName;
	}

	/**
	 * @param specialityName
	 *            The specialityName to set.
	 */
	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	/**
	 * @return Returns the subjectCategory.
	 */
	public SubjectCategory getSubjectCategory() {
		return subjectCategory;
	}

	/**
	 * @param subjectCategory
	 *            The subjectCategory to set.
	 */
	public void setSubjectCategory(SubjectCategory subjectCategory) {
		this.subjectCategory = subjectCategory;
	}

}
