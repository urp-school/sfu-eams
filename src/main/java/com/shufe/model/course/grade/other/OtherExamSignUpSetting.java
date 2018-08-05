//$Id: SchoolOutsideExam.java,v 1.5 2006/12/10 02:03:12 duanth Exp $
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
 * @author hc
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * hc              2005-9-5                   校外考试种类维护
 *  
 ********************************************************************************/

package com.shufe.model.course.grade.other;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;

/**
 * 其他考试报名控制类
 * 
 * @author hc,chaostone
 * 
 */
public class OtherExamSignUpSetting extends LongIdObject {
	private static final long serialVersionUID = -3625902334782342380L;

	/**
	 * 考试类别
	 */
	private OtherExamCategory examCategory;

	/**
	 * 如果报名此考试需要完成的其他考试
	 */
	private OtherExamCategory superCategory;

	/**
	 * 报名开始时间
	 */
	private Date startAt;

	/**
	 * 报名截止时间
	 */
	private Date endAt;

	/**
	 * 是否开放
	 */
	private Boolean isOpen;

	/**
	 * 成绩有效期(单位：月)
	 */
	private Integer availMonth;

	/**
	 * 免受学生类别
	 */
	private Set freeStdTypes = new HashSet();

	public Integer getAvailMonth() {
		return availMonth;
	}

	public void setAvailMonth(Integer availMonth) {
		this.availMonth = availMonth;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public OtherExamCategory getExamCategory() {
		return examCategory;
	}

	public void setExamCategory(OtherExamCategory examCategory) {
		this.examCategory = examCategory;
	}

	public Set getFreeStdTypes() {
		return freeStdTypes;
	}

	public void setFreeStdTypes(Set freeStdType) {
		this.freeStdTypes = freeStdType;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public OtherExamCategory getSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(OtherExamCategory superCategory) {
		this.superCategory = superCategory;
	}

	public boolean inSignUpTime() {
		if (null != getStartAt() && null != getEndAt()) {
			long now = System.currentTimeMillis();
			if (now >= getStartAt().getTime() && now <= getEndAt().getTime())
				return true;
			else
				return false;
		} else
			return false;
	}
}
