//$Id: StudentDetailWithAudit.java,v 1.3 2006/12/19 13:08:41 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-16         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.graduation.audit.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.GraduateState;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.PunishmentType;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.shufe.model.std.Student;

/**
 * 毕业审核(含学位审核)结果
 * 
 * @author dell,chaostone
 */
public class AuditResult extends LongIdObject {

	private static final long serialVersionUID = 4409774121935270743L;

	/** 对应学生 */
	private Student std;

	/** 专业类别 */
	private MajorType majorType;

	/** 重修学分 */
	private Float restudyCredits;

	/** 出勤率 */
	private Float attendanceRatio;

	/** 培养计划是否完成 */
	private Boolean isCompletePlan;
	
	/**是否通过学位课程*/
	private Boolean isDegreeCoursePass;

	
    public Boolean getIsDegreeCoursePass() {
        return isDegreeCoursePass;
    }

    
    public void setIsDegreeCoursePass(Boolean isDegreeCoursePass) {
        this.isDegreeCoursePass = isDegreeCoursePass;
    }

    /** 平均绩点 */
	private Float GPA;

	/** 论文成绩 */
	private Float thesisScore;

	/** 所受处分 */
	private PunishmentType punishmentType;

	/** 外语水平 */
	private Set languageGrades;

	/** 计算机水平 */
	private Set computerGrades;

	/** 授予学位(通过的授予学位) */
	private Degree degree;

	/** 学位证书 */
	private String certificateNo;

	/** 毕业情况 */
	private GraduateState graduateState;

	/** 学历证书号 */
	private String diplomaNo;
    
    /** 电子注册号 */
    private String registrationNo;

	/** 是否通过 */
	private Boolean isPass;

	/** 审核日期 */
	private Date auditAt;

	/** 是否确认 */
	private Boolean isAffirm;

	/** 通过博士综合考试 */
	private Boolean isPassDoctorComprehensiveExam;

	/** 核心期刊的论文数（或者折合数） */
	private Integer thesisInCoreMagazine;

	/** 总学分 */
	private Float credits;

	/** 备注 */
	private String remark;
    
    /** 审核信息 */
    private Map degreeAuditInfos = new HashMap();

    public Map getDegreeAuditInfos() {
        return degreeAuditInfos;
    }

    public void setDegreeAuditInfos(Map degreeAuditInfos) {
        this.degreeAuditInfos = degreeAuditInfos;
    }

    public String getDiplomaNo() {
		return diplomaNo;
	}

	public void setDiplomaNo(String diplomaNo) {
		this.diplomaNo = diplomaNo;
	}

	public AuditResult() {
		super();
	}

	public AuditResult(Student std, MajorType majorType) {
		this.std = std;
		this.majorType = majorType;
		isCompletePlan = Boolean.FALSE;
	}

	public Float getGPA() {
		return GPA;
	}

	public void setGPA(Float averageGrade) {
		this.GPA = averageGrade;
	}

	public Float getThesisScore() {
		return thesisScore;
	}

	public void setThesisScore(Float discourseGrade) {
		this.thesisScore = discourseGrade;
	}

	public Float getAttendanceRatio() {
		return attendanceRatio;
	}

	public void setAttendanceRatio(Float dutyRation) {
		this.attendanceRatio = dutyRation;
	}

	public Boolean getIsCompletePlan() {
		return isCompletePlan;
	}

	public void setIsCompletePlan(Boolean isTeachPlanCompleted) {
		this.isCompletePlan = isTeachPlanCompleted;
	}

	public Float getRestudyCredits() {
		return restudyCredits;
	}

	public void setRestudyCredits(Float repeatCreditHour) {
		this.restudyCredits = repeatCreditHour;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student student) {
		this.std = student;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public Set getComputerGrades() {
		return computerGrades;
	}

	public void setComputerGrades(Set computerGrades) {
		this.computerGrades = computerGrades;
	}

	public Set getLanguageGrades() {
		return languageGrades;
	}

	public void setLanguageGrades(Set languageGrades) {
		this.languageGrades = languageGrades;
	}

	public PunishmentType getPunishmentType() {
		return punishmentType;
	}

	public void setPunishmentType(PunishmentType punishmentType) {
		this.punishmentType = punishmentType;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Boolean getIsPassDoctorComprehensiveExam() {
		return isPassDoctorComprehensiveExam;
	}

	public void setIsPassDoctorComprehensiveExam(
			Boolean isPassDoctorComprehensiveExam) {
		this.isPassDoctorComprehensiveExam = isPassDoctorComprehensiveExam;
	}

	public Integer getThesisInCoreMagazine() {
		return thesisInCoreMagazine;
	}

	public void setThesisInCoreMagazine(Integer thesisInCoreMagazine) {
		this.thesisInCoreMagazine = thesisInCoreMagazine;
	}

	public GraduateState getGraduateState() {
		return graduateState;
	}

	public void setGraduateState(GraduateState graduateState) {
		this.graduateState = graduateState;
	}

	public Float getCredits() {
		return credits;
	}

	public void setCredits(Float credits) {
		this.credits = credits;
	}

	public Date getAuditAt() {
		return auditAt;
	}

	public void setAuditAt(Date auditAt) {
		this.auditAt = auditAt;
	}

	public Boolean getIsAffirm() {
		return isAffirm;
	}

	public void setIsAffirm(Boolean isAffirm) {
		this.isAffirm = isAffirm;
	}

	public MajorType getMajorType() {
		return majorType;
	}

	public void setMajorType(MajorType majorType) {
		this.majorType = majorType;
	}


    
    public String getRegistrationNo() {
        return registrationNo;
    }


    
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

}
