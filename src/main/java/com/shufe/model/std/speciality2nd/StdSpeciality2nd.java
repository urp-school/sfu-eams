//$Id: SecondStudent.java,v 1.1 2007-6-25 下午07:47:27 chaostone Exp $
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
 * chenweixiong              2007-6-25         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.speciality2nd;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 双专业学生信息
 * 
 * @author chaostone
 * 
 */
public class StdSpeciality2nd extends LongIdObject {
	private static final long serialVersionUID = -4257610641021003109L;

	/** 第二专业代码 */
	private Speciality major;

	/** 第二专业方向代码 */
	private SpecialityAspect aspect;

	/** 第二专业是否就读 */
	private Boolean isStudy;

	/** 第二专业是否写论文 */
	private Boolean isThesisNeed;

	/** 二专业导师 */
	private Teacher tutor = new Teacher();
	
	/** 双专业毕业审核状态(是否审核过) */
	private Boolean graduateAuditStatus;
	
	public Speciality getMajor() {
		return major;
	}

	public void setMajor(Speciality major) {
		this.major = major;
	}

	public SpecialityAspect getAspect() {
		return aspect;
	}

	public void setAspect(SpecialityAspect aspect) {
		this.aspect = aspect;
	}

	public Boolean getIsStudy() {
		return isStudy;
	}

	public void setIsStudy(Boolean isStudy) {
		this.isStudy = isStudy;
	}

	public Boolean getIsThesisNeed() {
		return isThesisNeed;
	}

	public void setIsThesisNeed(Boolean isThesisNeed) {
		this.isThesisNeed = isThesisNeed;
	}

	public Teacher getTutor() {
		return tutor;
	}

	public void setTutor(Teacher tutor) {
		this.tutor = tutor;
	}

	public Boolean getGraduateAuditStatus() {
		return graduateAuditStatus;
	}

	public void setGraduateAuditStatus(Boolean graduateAuditStatus) {
		this.graduateAuditStatus = graduateAuditStatus;
	}

}
