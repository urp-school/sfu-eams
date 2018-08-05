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
 * 塞外狂人             2006-8-9            Created
 *  
 ********************************************************************************/
package com.shufe.model.degree.thesis.annotate;

import com.ekingstar.commons.model.Component;

/**
 * 博士学位论文专家评价意见表
 * 
 * @author 塞外狂人
 * 
 */
public class EvaluateIdea implements Component {
	/** 保存A、B、C、D */
	private String topicMeaning;// 选题意义
	private String lteratureSumUp;// 文献综述
	private String researchHarvest;// 研究成果
	private String operationLevel;// 业务水平
	private String composeAbility;// 写作能力与学风
	private Float mark;// 分数
	private String learningLevel;// 论文是否达到授予博士学位的学术水平

	public String getComposeAbility() {
		return composeAbility;
	}

	public void setComposeAbility(String composeAbility) {
		this.composeAbility = composeAbility;
	}

	public String getLteratureSumUp() {
		return lteratureSumUp;
	}

	public void setLteratureSumUp(String lteratureSumUp) {
		this.lteratureSumUp = lteratureSumUp;
	}

	public Float getMark() {
		return mark;
	}

	public void setMark(Float mark) {
		this.mark = mark;
	}

	public String getOperationLevel() {
		return operationLevel;
	}

	public void setOperationLevel(String operationLevel) {
		this.operationLevel = operationLevel;
	}

	public String getResearchHarvest() {
		return researchHarvest;
	}

	public void setResearchHarvest(String researchHarvest) {
		this.researchHarvest = researchHarvest;
	}

	public String getTopicMeaning() {
		return topicMeaning;
	}

	public void setTopicMeaning(String topicMeaning) {
		this.topicMeaning = topicMeaning;
	}

	/*
	 * public String getEvaluateIdea() { return evaluateIdea; }
	 * 
	 * public void setEvaluateIdea(String evaluateIdea) { this.evaluateIdea =
	 * evaluateIdea; } }
	 */

	public String getLearningLevel() {
		return learningLevel;
	}

	public void setLearningLevel(String learningLevel) {
		this.learningLevel = learningLevel;
	}
}
