//$Id: DegreeInfo.java,v 1.1 2007-5-8 16:05:10 Administrator Exp $
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
import com.ekingstar.eams.system.basecode.industry.CorporationKind;
import com.ekingstar.eams.system.basecode.industry.School;
import com.ekingstar.eams.system.basecode.industry.StudyType;

/**
 * 学生的学历学位信息
 * 
 * @author Administrator
 * 
 */
public class DegreeInfo extends LongIdObject {

	private static final long serialVersionUID = -2487455125600727182L;

	/** 本科部分 */
	private UndergraduateDegreeInfo undergraduate;

	/** 硕士 */
	private MasterDegreeInfo master;

	/** 同等学力 */
	private EquivalentDegreeInfo equivalent;

	/** 培养单位 */
	private School school;

	/** 学习方式 */
	private StudyType studyType;

	/** 分配单位类别 */
	private CorporationKind corporationKind;

	/** 是否按一级学科授予 */
	private Boolean isFirstSubject;

	/** 毕业日期 学位授予日期 */
	private String graduateOn;

	/** 毕业证书编号 */
	private String certificateNo;

	/** 专利数 */
	private Integer patentNum;

	/**
	 * @return Returns the master.
	 */
	public MasterDegreeInfo getMaster() {
		return master;
	}

	/**
	 * @param master
	 *            The master to set.
	 */
	public void setMaster(MasterDegreeInfo master) {
		this.master = master;
	}

	/**
	 * @return Returns the undergraduate.
	 */
	public UndergraduateDegreeInfo getUndergraduate() {
		return undergraduate;
	}

	/**
	 * @param undergraduate
	 *            The undergraduate to set.
	 */
	public void setUndergraduate(UndergraduateDegreeInfo undergraduate) {
		this.undergraduate = undergraduate;
	}

	/**
	 * @return Returns the equivalent.
	 */
	public EquivalentDegreeInfo getEquivalent() {
		return equivalent;
	}

	/**
	 * @param equivalent
	 *            The equivalent to set.
	 */
	public void setEquivalent(EquivalentDegreeInfo equivalent) {
		this.equivalent = equivalent;
	}

	/**
	 * @return Returns the studyType.
	 */
	public StudyType getStudyType() {
		return studyType;
	}

	/**
	 * @param studyType
	 *            The studyType to set.
	 */
	public void setStudyType(StudyType studyType) {
		this.studyType = studyType;
	}

	/**
	 * @return Returns the corporation.
	 */
	public CorporationKind getCorporationKind() {
		return corporationKind;
	}

	/**
	 * @param corporation
	 *            The corporation to set.
	 */
	public void setCorporationKind(CorporationKind corporation) {
		this.corporationKind = corporation;
	}

	/**
	 * @return Returns the isFirstSubject.
	 */
	public Boolean getIsFirstSubject() {
		return isFirstSubject;
	}

	/**
	 * @param isFirstSubject
	 *            The isFirstSubject to set.
	 */
	public void setIsFirstSubject(Boolean isFirstSubject) {
		this.isFirstSubject = isFirstSubject;
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

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getGraduateOn() {
		return graduateOn;
	}

	public void setGraduateOn(String graduateOn) {
		this.graduateOn = graduateOn;
	}

	public Integer getPatentNum() {
		return patentNum;
	}

	public void setPatentNum(Integer patentNum) {
		this.patentNum = patentNum;
	}

}
