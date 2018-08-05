//$Id: Speciality2ndSignUpSetting.java,v 1.1 2007-3-26 下午10:10:52 chaostone Exp $
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

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 双专业报名设置
 * 
 * @author chaostone
 * 
 */
public class SignUpSetting extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4075859495154420652L;

	/** 名称 */
	private String name;

	/** 要求绩点 */
	private Float minGPA;

	/** 报名开始时间 */
	private Date beginOn;

	/** 报名结束时间 */
	private Date endOn;

	/** 录取的所在年级,形式为yyyy-p */
	private String enrollTurn;

	/** 注册时间 */
	private String registerOn;

	/** 注册地址 */
	private String registerAt;

	/** 对象范围 */
	private Set scopes = new HashSet(0);

	/** 是否开启 */
	private Boolean isOpen;

	/** 志愿数 */
	private Integer choiceCount;

	/** 参数设置所在的学年学期 */
	private TeachCalendar calendar;

	/** 专业设置 */
	private Set specialitySettings = new HashSet(0);

	/** 是否公布录取结果 */
	public Boolean isOpenMatriculateResult = Boolean.FALSE;
    
    /** 是否限制学科门类 */
    public Boolean isRestrictSubjectCategory = Boolean.FALSE;

	public SignUpSetting() {
		super();
	}

	public SignUpSetting(Long id) {
		super();
		this.id = id;
	}

	public Date getBeginOn() {
		return beginOn;
	}

	public void setBeginOn(Date beginOn) {
		this.beginOn = beginOn;
	}

	public Integer getChoiceCount() {
		return choiceCount;
	}

	public void setChoiceCount(Integer choiceCount) {
		this.choiceCount = choiceCount;
	}

	public Date getEndOn() {
		return endOn;
	}

	public void setEndOn(Date endOn) {
		this.endOn = endOn;
	}

	public String getEnrollTurn() {
		return enrollTurn;
	}

	public void setEnrollTurn(String enrollTurn) {
		this.enrollTurn = enrollTurn;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getRegisterOn() {
		return registerOn;
	}

	public void setRegisterOn(String registeOn) {
		this.registerOn = registeOn;
	}

	public String getRegisterAt() {
		return registerAt;
	}

	public void setRegisterAt(String registerAt) {
		this.registerAt = registerAt;
	}

	public Float getMinGPA() {
		return minGPA;
	}

	public void setMinGPA(Float requiredGrade) {
		this.minGPA = requiredGrade;
	}

	public Set getScopes() {
		return scopes;
	}

	public void setScopes(Set scopes) {
		this.scopes = scopes;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public Set getSpecialitySettings() {
		return specialitySettings;
	}

	public void setSpecialitySettings(Set specialitySettings) {
		this.specialitySettings = specialitySettings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDateSuitable() {
		if (null != getBeginOn() && null != getEndOn()) {
			java.util.Date now = DateUtils.truncate(new GregorianCalendar(),
					Calendar.DATE).getTime();
			if (now.before(DateUtils.truncate(getBeginOn(), Calendar.DATE))
					|| now.after(DateUtils.truncate(getEndOn(), Calendar.DATE))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查报名范围和平均绩点
	 * 
	 * @param std
	 * @param GPA
	 * @return
	 */
	public boolean canSignUp(Student std, Float GPA) {
		boolean can = false;
		if (null == getScopes() || getScopes().isEmpty()) {
			can = true;
		} else {
			for (Iterator iter = getScopes().iterator(); iter.hasNext();) {
				SignUpStdScope scope = (SignUpStdScope) iter.next();
				if (scope.inScope(std)) {
					can = true;
					break;
				}
			}
		}
		if (can) {
			if (getMinGPA().compareTo(GPA) < 1)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	public Boolean getIsOpenMatriculateResult() {
		return isOpenMatriculateResult;
	}

	public void setIsOpenMatriculateResult(Boolean isOpenMatriculateResult) {
		this.isOpenMatriculateResult = isOpenMatriculateResult;
	}
    
    public Boolean getIsRestrictSubjectCategory() {
        return isRestrictSubjectCategory;
    }

    public void setIsRestrictSubjectCategory(Boolean isRestrictSubjectCategory) {
        this.isRestrictSubjectCategory = isRestrictSubjectCategory;
    }

}
