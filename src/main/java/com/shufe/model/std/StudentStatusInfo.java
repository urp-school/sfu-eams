// $Id: StudentStatusInfo.java,v 1.12 2006/12/19 13:08:40 duanth Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/***********************************************************************************************************************
 * @author pippo MODIFICATION DESCRIPTION Name Date Description ============ ============ ============ pippo 2005-10-31
 *         Created
 **********************************************************************************************************************/

package com.shufe.model.std;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.EducationMode;
import com.ekingstar.eams.system.basecode.industry.EnrollMode;
import com.ekingstar.eams.system.basecode.industry.FeeOrigin;
import com.ekingstar.eams.system.basecode.industry.LeaveSchoolCause;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;

/**
 * 入校招生信息
 * 
 * @author dell,yangdong,liuzhuoshan
 */
public class StudentStatusInfo extends LongIdObject implements Serializable {

	private static final long serialVersionUID = 2521740066272501519L;

	public static List notNullPropertyList = new ArrayList();
	static {
		notNullPropertyList.add("status");
		Collections.unmodifiableList(notNullPropertyList);
	}

	/** 原毕业学校 */
	private String graduateSchool;

	/** 入学前最后学历 */
	private EduDegree educationBeforEnroll = new EduDegree();

	/** 入学前最后学历毕业时间 */
	private Date educationBeforEnrollDate;

	/** 入学前最后学历专业 */
	private String educationBeforEnrollSpeciality;

	/** 入学前最后学位 */
	private Degree degreeBeforEnroll;

	/** 入学前最后学位授予时间 */
	private Date degreeBeforEnrollDate;

	/** 入学前最后学位授予单位 */
	private String degreeBeforEnrollOrganization;

	/** 入学院系代码 */
	private Department enrollDepartment;

	/** 入学专业代码 */
	private Speciality enrollSpeciality;

	/** 入学方式 */
	private EnrollMode enrollMode = new EnrollMode();

	/** 培养方式 */
	private EducationMode educationMode = new EducationMode();

	/** 入校时间 */
	private Date enrollDate;

	/** 生源地址 */
	private String originalAddress;

	/** 离校时间 */
	private Date leaveDate;

	/** 离校去向 */
	private String gotoWhere;

	/** 离校原因 */
	private LeaveSchoolCause leaveSchoolCause = new LeaveSchoolCause();

	/** 准考证号 */
	private String examNumber;
    
	/** 该生奖惩纪要 */
	private String rewardsAndPunishment;

	/** 费用来源 */
	private FeeOrigin feeOrigin;

	/** 推荐单位 */
	private String recommendOrganization;

	/** 报到时间 */
	private Date registerDate;

	/** 学历和社会经历从中学起 */
	private Set experienceSet;

	/**
	 * @return Returns the educationMode.
	 */
	public EducationMode getEducationMode() {
		return educationMode;
	}

	/**
	 * @param educationMode
	 *            The educationMode to set.
	 */
	public void setEducationMode(EducationMode educationMode) {
		this.educationMode = educationMode;
	}

	/**
	 * @return Returns the educationBeforEnroll.
	 */
	public EduDegree getEducationBeforEnroll() {
		return educationBeforEnroll;
	}

	/**
	 * @param educationBeforEnroll
	 *            The educationBeforEnroll to set.
	 */
	public void setEducationBeforEnroll(EduDegree educationBeforEnroll) {
		this.educationBeforEnroll = educationBeforEnroll;
	}

	/**
	 * @return Returns the enrollDate.
	 */
	public Date getEnrollDate() {
		return enrollDate;
	}

	/**
	 * @param enrollDate
	 *            The enrollDate to set.
	 */
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	/**
	 * @return Returns the enrollMode.
	 */
	public EnrollMode getEnrollMode() {
		return enrollMode;
	}

	/**
	 * @param enrollMode
	 *            The enrollMode to set.
	 */
	public void setEnrollMode(EnrollMode enrollMode) {
		this.enrollMode = enrollMode;
	}

	/**
	 * @return Returns the examNumber.
	 */
	public String getExamNumber() {
		return examNumber;
	}

	/**
	 * @param examNumber
	 *            The examNumber to set.
	 */
	public void setExamNumber(String examNumber) {
		this.examNumber = examNumber;
	}

	/**
	 * @return Returns the gotoWhere.
	 */
	public String getGotoWhere() {
		return gotoWhere;
	}

	/**
	 * @param gotoWhere
	 *            The gotoWhere to set.
	 */
	public void setGotoWhere(String gotoWhere) {
		this.gotoWhere = gotoWhere;
	}

	/**
	 * @return Returns the graduateSchool.
	 */
	public String getGraduateSchool() {
		return graduateSchool;
	}

	/**
	 * @param graduateSchool
	 *            The graduateSchool to set.
	 */
	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	/**
	 * @return Returns the leaveDate.
	 */
	public Date getLeaveDate() {
		return leaveDate;
	}

	/**
	 * @param leaveDate
	 *            The leaveDate to set.
	 */
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	/**
	 * @return Returns the leaveSchoolCause.
	 */
	public LeaveSchoolCause getLeaveSchoolCause() {
		return leaveSchoolCause;
	}

	/**
	 * @param leaveSchoolCause
	 *            The leaveSchoolCause to set.
	 */
	public void setLeaveSchoolCause(LeaveSchoolCause leaveSchoolCause) {
		this.leaveSchoolCause = leaveSchoolCause;
	}

	/**
	 * @return Returns the originalAddress.
	 */
	public String getOriginalAddress() {
		return originalAddress;
	}

	/**
	 * @param originalAddress
	 *            The originalAddress to set.
	 */
	public void setOriginalAddress(String originalAddress) {
		this.originalAddress = originalAddress;
	}

	/**
	 * @return 返回 degreeBeforEnroll.
	 */
	public Degree getDegreeBeforEnroll() {
		return degreeBeforEnroll;
	}

	/**
	 * @param degreeBeforEnroll
	 *            要设置的 degreeBeforEnroll.
	 */
	public void setDegreeBeforEnroll(Degree degreeBeforEnroll) {
		this.degreeBeforEnroll = degreeBeforEnroll;
	}

	/**
	 * @return 返回 degreeBeforEnrollDate.
	 */
	public Date getDegreeBeforEnrollDate() {
		return degreeBeforEnrollDate;
	}

	/**
	 * @param degreeBeforEnrollDate
	 *            要设置的 degreeBeforEnrollDate.
	 */
	public void setDegreeBeforEnrollDate(Date degreeBeforEnrollDate) {
		this.degreeBeforEnrollDate = degreeBeforEnrollDate;
	}

	/**
	 * @return 返回 degreeBeforEnrollOrganization.
	 */
	public String getDegreeBeforEnrollOrganization() {
		return degreeBeforEnrollOrganization;
	}

	/**
	 * @param degreeBeforEnrollOrganization
	 *            要设置的 degreeBeforEnrollOrganization.
	 */
	public void setDegreeBeforEnrollOrganization(
			String degreeBeforEnrollOrganization) {
		this.degreeBeforEnrollOrganization = degreeBeforEnrollOrganization;
	}

	/**
	 * @return 返回 educationBeforEnrollDate.
	 */
	public Date getEducationBeforEnrollDate() {
		return educationBeforEnrollDate;
	}

	/**
	 * @param educationBeforEnrollDate
	 *            要设置的 educationBeforEnrollDate.
	 */
	public void setEducationBeforEnrollDate(Date educationBeforEnrollDate) {
		this.educationBeforEnrollDate = educationBeforEnrollDate;
	}

	/**
	 * @return 返回 educationBeforEnrollSpeciality.
	 */
	public String getEducationBeforEnrollSpeciality() {
		return educationBeforEnrollSpeciality;
	}

	/**
	 * @param educationBeforEnrollSpeciality
	 *            要设置的 educationBeforEnrollSpeciality.
	 */
	public void setEducationBeforEnrollSpeciality(
			String educationBeforEnrollSpeciality) {
		this.educationBeforEnrollSpeciality = educationBeforEnrollSpeciality;
	}

	/**
	 * @return 返回 experienceSet.
	 */
	public Set getExperienceSet() {
		return experienceSet;
	}

	/**
	 * @param experienceSet
	 *            要设置的 experienceSet.
	 */
	public void setExperienceSet(Set experienceSet) {
		this.experienceSet = experienceSet;
	}

	/**
	 * @return 返回 rewardsAndPunishment.
	 */
	public String getRewardsAndPunishment() {
		return rewardsAndPunishment;
	}

	/**
	 * @param rewardsAndPunishment
	 *            要设置的 rewardsAndPunishment.
	 */
	public void setRewardsAndPunishment(String rewardsAndPunishment) {
		this.rewardsAndPunishment = rewardsAndPunishment;
	}

	/**
	 * @return 返回 feeOrigin.
	 */
	public FeeOrigin getFeeOrigin() {
		return feeOrigin;
	}

	/**
	 * @param feeOrigin
	 *            要设置的 feeOrigin.
	 */
	public void setFeeOrigin(FeeOrigin feeOrigin) {
		this.feeOrigin = feeOrigin;
	}

	/**
	 * @return 返回 recommendOrganization.
	 */
	public String getRecommendOrganization() {
		return recommendOrganization;
	}

	/**
	 * @param recommendOrganization
	 *            要设置的 recommendOrganization.
	 */
	public void setRecommendOrganization(String recommendOrganization) {
		this.recommendOrganization = recommendOrganization;
	}

	/**
	 * @return 返回 registerDate.
	 */
	public Date getRegisterDate() {
		return registerDate;
	}

	/**
	 * @param registerDate
	 *            要设置的 registerDate.
	 */
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * @return 返回 enrollSpeciality.
	 */
	public Speciality getEnrollSpeciality() {
		return enrollSpeciality;
	}

	/**
	 * @param enrollSpeciality
	 *            要设置的 enrollSpeciality.
	 */
	public void setEnrollSpeciality(Speciality enrollSpeciality) {
		this.enrollSpeciality = enrollSpeciality;
	}

	/**
	 * @return 返回 enrollDepartment.
	 */
	public Department getEnrollDepartment() {
		return enrollDepartment;
	}

	/**
	 * @param enrollDepartment
	 *            要设置的 enrollDepartment.
	 */
	public void setEnrollDepartment(Department enrollDepartment) {
		this.enrollDepartment = enrollDepartment;
	}
}
