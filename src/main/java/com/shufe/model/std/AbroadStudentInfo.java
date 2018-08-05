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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/

package com.shufe.model.std;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.HSKDegree;
import com.ekingstar.eams.system.basecode.industry.PassportType;
import com.ekingstar.eams.system.basecode.industry.VisaType;

/**
 * 留学生信息
 */
public class AbroadStudentInfo extends LongIdObject {

	private static final long serialVersionUID = 1477853582842718370L;
	/** CSC编号 */
	private String CSCNo;

	/** 留学生HSK等级 */
	HSKDegree HSKDegree = new HSKDegree();

	/** 护照编号 */
	private String passportNo;

	/** 护照到期时间 */
	private Date passportDeadline;

	/** 护照类别 */
	private PassportType passportType = new PassportType();

	/** 签证编号 */
	private String visaNo;

	/** 签证到期时间 */
	private Date visaDeadline;

	/** 签证类别 */
	private VisaType visaType = new VisaType();

	/** 居住许可证编号 */
	private String resideCaedNo;

	/** 居住许可证到期时间 */
	private Date resideCaedDeadline;

	/**
	 * @return 返回 hSKDegree.
	 */
	public HSKDegree getHSKDegree() {
		return HSKDegree;
	}

	/**
	 * @param degree
	 *            要设置的 hSKDegree.
	 */
	public void setHSKDegree(HSKDegree degree) {
		HSKDegree = degree;
	}

	/**
	 * @return 返回 passportDeadline.
	 */
	public Date getPassportDeadline() {
		return passportDeadline;
	}

	/**
	 * @param passportDeadline
	 *            要设置的 passportDeadline.
	 */
	public void setPassportDeadline(Date passportDeadline) {
		this.passportDeadline = passportDeadline;
	}

	/**
	 * @return 返回 passportNo.
	 */
	public String getPassportNo() {
		return passportNo;
	}

	/**
	 * @param passportNo
	 *            要设置的 passportNo.
	 */
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	/**
	 * @return 返回 resideCaedDeadline.
	 */
	public Date getResideCaedDeadline() {
		return resideCaedDeadline;
	}

	/**
	 * @param resideCaedDeadline
	 *            要设置的 resideCaedDeadline.
	 */
	public void setResideCaedDeadline(Date resideCaedDeadline) {
		this.resideCaedDeadline = resideCaedDeadline;
	}

	/**
	 * @return 返回 resideCaedNo.
	 */
	public String getResideCaedNo() {
		return resideCaedNo;
	}

	/**
	 * @param resideCaedNo
	 *            要设置的 resideCaedNo.
	 */
	public void setResideCaedNo(String resideCaedNo) {
		this.resideCaedNo = resideCaedNo;
	}

	/**
	 * @return 返回 visaDeadline.
	 */
	public Date getVisaDeadline() {
		return visaDeadline;
	}

	/**
	 * @param visaDeadline
	 *            要设置的 visaDeadline.
	 */
	public void setVisaDeadline(Date visaDeadline) {
		this.visaDeadline = visaDeadline;
	}

	/**
	 * @return 返回 visaNo.
	 */
	public String getVisaNo() {
		return visaNo;
	}

	/**
	 * @param visaNo
	 *            要设置的 visaNo.
	 */
	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}

	/**
	 * @return 返回 passportType.
	 */
	public PassportType getPassportType() {
		return passportType;
	}

	/**
	 * @param passportType
	 *            要设置的 passportType.
	 */
	public void setPassportType(PassportType passportType) {
		this.passportType = passportType;
	}

	/**
	 * @return 返回 visaType.
	 */
	public VisaType getVisaType() {
		return visaType;
	}

	/**
	 * @param visaType
	 *            要设置的 visaType.
	 */
	public void setVisaType(VisaType visaType) {
		this.visaType = visaType;
	}

	/**
	 * @return 返回 cSCNo.
	 */
	public String getCSCNo() {
		return CSCNo;
	}

	/**
	 * @param no
	 *            要设置的 cSCNo.
	 */
	public void setCSCNo(String no) {
		CSCNo = no;
	}

	// /**
	// * @return 返回 stdNo.
	// */
	// public String getStdNo() {
	// return stdNo;
	// }
	//
	// /**
	// * @param stdNo 要设置的 stdNo.
	// */
	// public void setStdNo(String stdNo) {
	// this.stdNo = stdNo;
	// }

}
