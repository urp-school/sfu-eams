//$Id: SpecialitySetting.java,v 1.1 2007-3-26 下午10:18:40 chaostone Exp $
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

import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;

/**
 * 双专业报名专业设置
 * 
 * @author chaostone
 * 
 */
public class SignUpSpecialitySetting extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3145947411072811984L;

	/** 第二专业 */
	private Speciality speciality = new Speciality();

	/** 第二专业方向 */
	private SpecialityAspect aspect = new SpecialityAspect();

	/** 报名人数上限 */
	private Integer limit;

	/** 报名人数 */
	private transient Integer total;

	/** 录取人数 */
	private transient Integer matriculated;

	/** 双专业报名组设置 */
	private SignUpSetting setting;

	/** 录取的报名学生对象 */
	private Set matriculatedSignUpStds = new HashSet();

	/** 报名的学生志愿 */
	private Set signUpStdRecords = new HashSet();

	public SignUpSpecialitySetting() {
		super();
	}

	public SignUpSpecialitySetting(Long id) {
		super();
		this.id = id;
	}

	public SignUpSpecialitySetting(Speciality speciality, SpecialityAspect aspect) {
		super();
		this.speciality = speciality;
		this.aspect = aspect;
	}

	public SpecialityAspect getAspect() {
		return aspect;
	}

	public void setAspect(SpecialityAspect aspect) {
		this.aspect = aspect;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getMatriculated() {
		return matriculated;
	}

	public void setMatriculated(Integer matriculated) {
		this.matriculated = matriculated;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public SignUpSetting getSetting() {
		return setting;
	}

	public void setSetting(SignUpSetting setting) {
		this.setting = setting;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public Integer getTotal() {
		return total;
	}

	public Set getMatriculatedSignUpStds() {
		return matriculatedSignUpStds;
	}

	public void setMatriculatedSignUpStds(Set signUpStds) {
		this.matriculatedSignUpStds = signUpStds;
	}

	public Set getSignUpStdRecords() {
		return signUpStdRecords;
	}

	public void setSignUpStdRecords(Set signUpStdRecords) {
		this.signUpStdRecords = signUpStdRecords;
	}

	/**
	 * 查找还有剩余的录取人数
	 * 
	 * @return
	 */
	public int getMatriculateReminder() {
		return getLimit().intValue() - getMatriculatedSignUpStds().size();
	}

	public Boolean getIsSaturated() {
		return Boolean.valueOf((matriculated.intValue() >= limit.intValue()));
	}
}
