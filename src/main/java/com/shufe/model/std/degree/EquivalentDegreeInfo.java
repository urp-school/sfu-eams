//$Id: EquivalentDegreeInfo.java,v 1.1 2007-5-8 16:38:21 Administrator Exp $
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

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 同等学力 学历学位信息
 */
public class EquivalentDegreeInfo extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3097202166940340820L;

	/** 申请编号 */
	private String applyNo;

	/** 申请时间 */
	private Date applyOn;

	/** 行政职务 */
	private String adminDuty;

	/** 技能职务 */
	private String specialDuty;

	/** 工作年限 */
	private String workTime;

	/** 推荐人1 */
	private String recommender1;

	/** 推荐人1工作单位 */
	private String recommender1Company;

	/** 推荐人1专业职务 */
	private String recommender1SpecialDuty;

	/** 推荐人2 */
	private String recommender2;

	/** 推荐人2工作单位 */
	private String recommender2Company;

	/** 推荐人2专业职务 */
	private String recommender2SpecialDuty;

	/** 综合考试名称 */
	private String exam;

	/** 综合考试分数 */
	private String examScore;

	/** 综合考试日期 */
	private String examOn;

	/** 综合考试证书号 */
	private String examCertificateNo;

	/** 外语统考名称 */
	private String language;

	/** 外语统考分数 */
	private String languageScore;

	/** 外语统考日期 */
	private String languageOn;

	/** 外语统考证书号 */
	private String languageCertificateNo;

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return Returns the adminDuty.
	 */
	public String getAdminDuty() {
		return adminDuty;
	}

	/**
	 * @param adminDuty
	 *            The adminDuty to set.
	 */
	public void setAdminDuty(String adminDuty) {
		this.adminDuty = adminDuty;
	}

	/**
	 * @return Returns the applyNo.
	 */
	public String getApplyNo() {
		return applyNo;
	}

	/**
	 * @param applyNo
	 *            The applyNo to set.
	 */
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	/**
	 * @return Returns the applyOn.
	 */
	public Date getApplyOn() {
		return applyOn;
	}

	/**
	 * @param applyOn
	 *            The applyOn to set.
	 */
	public void setApplyOn(Date applyOn) {
		this.applyOn = applyOn;
	}

	/**
	 * @return Returns the exam.
	 */
	public String getExam() {
		return exam;
	}

	/**
	 * @param exam
	 *            The exam to set.
	 */
	public void setExam(String exam) {
		this.exam = exam;
	}

	/**
	 * @return Returns the examCertificateNo.
	 */
	public String getExamCertificateNo() {
		return examCertificateNo;
	}

	/**
	 * @param examCertificateNo
	 *            The examCertificateNo to set.
	 */
	public void setExamCertificateNo(String examCertificateNo) {
		this.examCertificateNo = examCertificateNo;
	}

	/**
	 * @return Returns the examOn.
	 */
	public String getExamOn() {
		return examOn;
	}

	/**
	 * @param examOn
	 *            The examOn to set.
	 */
	public void setExamOn(String examOn) {
		this.examOn = examOn;
	}

	/**
	 * @return Returns the examScore.
	 */
	public String getExamScore() {
		return examScore;
	}

	/**
	 * @param examScore
	 *            The examScore to set.
	 */
	public void setExamScore(String examScore) {
		this.examScore = examScore;
	}

	/**
	 * @return Returns the language.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            The language to set.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return Returns the languageCertificateNo.
	 */
	public String getLanguageCertificateNo() {
		return languageCertificateNo;
	}

	/**
	 * @param languageCertificateNo
	 *            The languageCertificateNo to set.
	 */
	public void setLanguageCertificateNo(String languageCertificateNo) {
		this.languageCertificateNo = languageCertificateNo;
	}

	/**
	 * @return Returns the languageOn.
	 */
	public String getLanguageOn() {
		return languageOn;
	}

	/**
	 * @param languageOn
	 *            The languageOn to set.
	 */
	public void setLanguageOn(String languageOn) {
		this.languageOn = languageOn;
	}

	/**
	 * @return Returns the languageScore.
	 */
	public String getLanguageScore() {
		return languageScore;
	}

	/**
	 * @param languageScore
	 *            The languageScore to set.
	 */
	public void setLanguageScore(String languageScore) {
		this.languageScore = languageScore;
	}

	/**
	 * @return Returns the recommender1.
	 */
	public String getRecommender1() {
		return recommender1;
	}

	/**
	 * @param recommender1
	 *            The recommender1 to set.
	 */
	public void setRecommender1(String recommender1) {
		this.recommender1 = recommender1;
	}

	/**
	 * @return Returns the recommender1Company.
	 */
	public String getRecommender1Company() {
		return recommender1Company;
	}

	/**
	 * @param recommender1Company
	 *            The recommender1Company to set.
	 */
	public void setRecommender1Company(String recommender1Company) {
		this.recommender1Company = recommender1Company;
	}

	/**
	 * @return Returns the recommender1SpecialDuty.
	 */
	public String getRecommender1SpecialDuty() {
		return recommender1SpecialDuty;
	}

	/**
	 * @param recommender1SpecialDuty
	 *            The recommender1SpecialDuty to set.
	 */
	public void setRecommender1SpecialDuty(String recommender1SpecialDuty) {
		this.recommender1SpecialDuty = recommender1SpecialDuty;
	}

	/**
	 * @return Returns the recommender2.
	 */
	public String getRecommender2() {
		return recommender2;
	}

	/**
	 * @param recommender2
	 *            The recommender2 to set.
	 */
	public void setRecommender2(String recommender2) {
		this.recommender2 = recommender2;
	}

	/**
	 * @return Returns the recommender2Company.
	 */
	public String getRecommender2Company() {
		return recommender2Company;
	}

	/**
	 * @param recommender2Company
	 *            The recommender2Company to set.
	 */
	public void setRecommender2Company(String recommender2Company) {
		this.recommender2Company = recommender2Company;
	}

	/**
	 * @return Returns the recommender2SpecialDuty.
	 */
	public String getRecommender2SpecialDuty() {
		return recommender2SpecialDuty;
	}

	/**
	 * @param recommender2SpecialDuty
	 *            The recommender2SpecialDuty to set.
	 */
	public void setRecommender2SpecialDuty(String recommender2SpecialDuty) {
		this.recommender2SpecialDuty = recommender2SpecialDuty;
	}

	/**
	 * @return Returns the specialDuty.
	 */
	public String getSpecialDuty() {
		return specialDuty;
	}

	/**
	 * @param specialDuty
	 *            The specialDuty to set.
	 */
	public void setSpecialDuty(String specialDuty) {
		this.specialDuty = specialDuty;
	}

	/**
	 * @return Returns the workTime.
	 */
	public String getWorkTime() {
		return workTime;
	}

	/**
	 * @param workTime
	 *            The workTime to set.
	 */
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

}
