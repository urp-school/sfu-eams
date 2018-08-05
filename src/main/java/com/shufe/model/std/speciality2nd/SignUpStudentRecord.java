//$Id: SignUpRecord.java,v 1.1 2007-3-26 下午10:19:29 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-26         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.speciality2nd;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 双专业学生报名详细记录
 * 
 * @author chaostone
 * 
 */
public class SignUpStudentRecord extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 753737282960702058L;

	/** 报名的专业 */
	private SignUpSpecialitySetting specialitySetting;

	/** 志愿级别 */
	private Integer rank;

	/** 是否录取 */
	private Boolean status;

	/** 学生报名数据 */
	private SignUpStudent signUpStd;

	public SignUpStudentRecord() {
		super();
	}

	public SignUpStudentRecord(SignUpSpecialitySetting specialitySetting,
			Integer rank) {
		super();
		this.specialitySetting = specialitySetting;
		this.rank = rank;
		this.status = Boolean.FALSE;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer signUplevel) {
		this.rank = signUplevel;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public SignUpStudent getSignUpStd() {
		return signUpStd;
	}

	public void setSignUpStd(SignUpStudent studentSignUp) {
		this.signUpStd = studentSignUp;
	}

	public SignUpSpecialitySetting getSpecialitySetting() {
		return specialitySetting;
	}

	public void setSpecialitySetting(SignUpSpecialitySetting specialitySetting) {
		this.specialitySetting = specialitySetting;
	}

}
