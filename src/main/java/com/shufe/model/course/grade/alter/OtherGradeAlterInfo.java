//$Id: OtherGradeAlterInfo.java,v 1.1 2007-2-22 下午05:57:53 chaostone Exp $
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
 *chaostone      2007-2-22         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.grade.alter;

import java.util.Date;

import com.ekingstar.security.User;
import com.shufe.model.course.grade.other.OtherGrade;
/**
 * 其他成绩修改信息
 * @author chaostone
 *
 */
public class OtherGradeAlterInfo implements GradeAlterInfo {
	private Long id;

	/**
	 * 被修改的成绩
	 */
	private OtherGrade grade;

	/**
	 * 修改前成绩
	 */
	private Float scoreBefore;

	/**
	 * 修改后成绩
	 */
	private Float scoreAfter;

	/**
	 * 修改于
	 */
	private Date modifyAt;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 修改人
	 */
	private User modifyBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

	public User getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(User modifyBy) {
		this.modifyBy = modifyBy;
	}

	public OtherGrade getGrade() {
		return grade;
	}

	public void setGrade(OtherGrade otherGrade) {
		this.grade = otherGrade;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Float getScoreAfter() {
		return scoreAfter;
	}

	public void setScoreAfter(Float scoreAfter) {
		this.scoreAfter = scoreAfter;
	}

	public Float getScoreBefore() {
		return scoreBefore;
	}

	public void setScoreBefore(Float scoreBefore) {
		this.scoreBefore = scoreBefore;
	}

}
