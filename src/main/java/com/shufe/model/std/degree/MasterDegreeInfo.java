//$Id: MasterDegreeInfo.java,v 1.1 2007-5-8 16:17:26 Administrator Exp $
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
 * 硕士学历学位信息<br>
 * 主要用于博士生
 * 
 * @author Administrator
 * 
 */
public class MasterDegreeInfo extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3484454825564828849L;

	/** 硕士毕业学校 */
	private School school;

	/** 毕业日期 学位授予日期 */
	private String graduateOn;

	/** 毕业专业 */
	private String specialityName;

	/** 毕业专业代码 */
	private String specialityCode;

	/** 学科门类 */
	private SubjectCategory subjectCategory;

	/** 硕士毕业证书编号 */
	private String certificateNo;

	/**
	 * @return Returns the certificateNo.
	 */
	public String getCertificateNo() {
		return certificateNo;
	}

	/**
	 * @param certificateNo
	 *            The certificateNo to set.
	 */
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

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

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	public SubjectCategory getSubjectCategory() {
		return subjectCategory;
	}

	public void setSubjectCategory(SubjectCategory subjectCategory) {
		this.subjectCategory = subjectCategory;
	}

	public String getSpecialityCode() {
		return specialityCode;
	}

	public void setSpecialityCode(String specialityCode) {
		this.specialityCode = specialityCode;
	}

}
