//$Id: Annotate.java,v 1.6 2006/12/19 13:08:42 duanth Exp $
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
 * Name                 Date                Description 
 * ============         ============        ============
 * hc             2005-12-6         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis.annotate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.shufe.model.degree.thesis.ThesisStore;

/**
 * 学位论文评阅报告
 * 
 * @author hc
 * 
 */
public class Annotate extends ThesisStore implements Serializable {

	private static final long serialVersionUID = -8407228406172270691L;

	/** 自评表 */
	private SelfAnnotate selfAnnotate = new SelfAnnotate();

	/** 发表论文 */
	private Set publishThesisSet = new HashSet();

	/** 专家评阅书 */
	private Set annotateBooks = new HashSet();

	/** 论文平均成绩 */
	private Float avgMark;

	/** 院系确认 */
	private Boolean departmentValidate;

	/** 导师确认 */
	private Boolean tutorValidate;

	/** 双盲是否选中 */
	private Boolean isDoubleBlind;

	/** 备注 */
	private String remark;

	public Boolean getIsDoubleBlind() {
		return isDoubleBlind;
	}

	public void setIsDoubleBlind(Boolean isDoubleBlind) {
		this.isDoubleBlind = isDoubleBlind;
	}

	public Boolean getDepartmentValidate() {
		return departmentValidate;
	}

	public void setDepartmentValidate(Boolean departmentValidate) {
		this.departmentValidate = departmentValidate;
	}

	public SelfAnnotate getSelfAnnotate() {
		return selfAnnotate;
	}

	public void setSelfAnnotate(SelfAnnotate selfAnnotate) {
		this.selfAnnotate = selfAnnotate;
	}

	public Boolean getTutorValidate() {
		return tutorValidate;
	}

	public void setTutorValidate(Boolean tutorValidate) {
		this.tutorValidate = tutorValidate;
	}

	/**
	 * @return Returns the publishThesisSet.
	 */
	public Set getPublishThesisSet() {
		return publishThesisSet;
	}

	/**
	 * @param publishThesisSet
	 *            The publishThesisSet to set.
	 */
	public void setPublishThesisSet(Set publishThesisSet) {
		this.publishThesisSet = publishThesisSet;
	}

	/**
	 * @return Returns the annotateBooks.
	 */
	public Set getAnnotateBooks() {
		return annotateBooks;
	}

	/**
	 * @param annotateBooks
	 *            The annotateBooks to set.
	 */
	public void setAnnotateBooks(Set annotateBooks) {
		this.annotateBooks = annotateBooks;
	}

	/**
	 * 检查对象的主键对象.
	 * 
	 * @return
	 */
	public boolean checkId() {
		boolean flag = Boolean.FALSE.booleanValue();
		if (null != this.getId() && !new Long(0).equals(this.getId())) {
			flag = Boolean.TRUE.booleanValue();
		}
		return flag;
	}

	/**
	 * @return Returns the avgMark.
	 */
	public Float getAvgMark() {
		return avgMark;
	}

	/**
	 * @param avgMark
	 *            The avgMark to set.
	 */
	public void setAvgMark(Float avgMark) {
		this.avgMark = avgMark;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 计算一个论文评阅的总分
	 */
	public void calcAvgScore() {
		float total = 0;
		for (Iterator iter = this.getAnnotateBooks().iterator(); iter.hasNext();) {
			ThesisAnnotateBook element = (ThesisAnnotateBook) iter.next();
			if (null != element.getEvaluateIdea()
					&& null != element.getEvaluateIdea().getMark()) {
				total += element.getEvaluateIdea().getMark().floatValue();
			}
		}
		if (this.getAnnotateBooks().size() > 0) {
			total = total / getAnnotateBooks().size();
		}
		this.setAvgMark(new Float(total));
	}
}
