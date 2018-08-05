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
 * 塞外狂人             2006-8-8            Created
 *  
 ********************************************************************************/
package com.shufe.model.degree.thesis.topicOpen;

import java.sql.Date;

import com.ekingstar.commons.model.Component;

/**
 * 论文工作计划表
 * 
 * @author 塞外狂人
 * 
 */
public class ThesisPlan implements Component {
	private Date researchTime; // 研究阶段
	private Date thesisWriteTime; // 论文写作
	private Date thesisFinishTime; // 论文完成时间
	private Date rehearseAnswerTime; // 预答辩时间
	private Date biBlindTime; // 双盲时间
	private Date thesisAnswerTime; // 论文答辩时间
	private String thesisTopicArranged;// 论文题目（调整后）

	public String getThesisTopicArranged() {
		return thesisTopicArranged;
	}

	public void setThesisTopicArranged(String thesisTopicArranged) {
		this.thesisTopicArranged = thesisTopicArranged;
	}

	/**
	 * @return Returns the biBlindTime.
	 */
	public Date getBiBlindTime() {
		return biBlindTime;
	}

	/**
	 * @param biBlindTime
	 *            The biBlindTime to set.
	 */
	public void setBiBlindTime(Date biBlindTime) {
		this.biBlindTime = biBlindTime;
	}

	/**
	 * @return Returns the rehearseAnswerTime.
	 */
	public Date getRehearseAnswerTime() {
		return rehearseAnswerTime;
	}

	/**
	 * @param rehearseAnswerTime
	 *            The rehearseAnswerTime to set.
	 */
	public void setRehearseAnswerTime(Date rehearseAnswerTime) {
		this.rehearseAnswerTime = rehearseAnswerTime;
	}

	/**
	 * @return Returns the researchTime.
	 */
	public Date getResearchTime() {
		return researchTime;
	}

	/**
	 * @param researchTime
	 *            The researchTime to set.
	 */
	public void setResearchTime(Date researchTime) {
		this.researchTime = researchTime;
	}

	/**
	 * @return Returns the thesisAnswerTime.
	 */
	public Date getThesisAnswerTime() {
		return thesisAnswerTime;
	}

	/**
	 * @param thesisAnswerTime
	 *            The thesisAnswerTime to set.
	 */
	public void setThesisAnswerTime(Date thesisAnswerTime) {
		this.thesisAnswerTime = thesisAnswerTime;
	}

	/**
	 * @return Returns the thesisFinishTime.
	 */
	public Date getThesisFinishTime() {
		return thesisFinishTime;
	}

	/**
	 * @param thesisFinishTime
	 *            The thesisFinishTime to set.
	 */
	public void setThesisFinishTime(Date thesisFinishTime) {
		this.thesisFinishTime = thesisFinishTime;
	}

	/**
	 * @return Returns the thesisWriteTime.
	 */
	public Date getThesisWriteTime() {
		return thesisWriteTime;
	}

	/**
	 * @param thesisWriteTime
	 *            The thesisWriteTime to set.
	 */
	public void setThesisWriteTime(Date thesisWriteTime) {
		this.thesisWriteTime = thesisWriteTime;
	}

}
