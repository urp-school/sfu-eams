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

import com.ekingstar.commons.model.Component;
/**
 * 论文开题－内容表
 * @author 塞外狂人
 *
 */
public class Content  implements Component{

	private static final long serialVersionUID = 7830814872269389946L;
	private String keyWords; //论文主题词
	private String meaning;//选题的理论意义与实用价值
	private String researchActuality;//国内外研究现状
	private String thinkingAndMethod;//研究的基本思路与方法
	private String innovate;//论文的创新点
	private String expectHarvest;//预期成果
	private String researchArea;//研究内容范围
	private Float aboutThesisNumber; //论文大约字数
	
	public String getExpectHarvest() {
		return expectHarvest;
	}
	public void setExpectHarvest(String expectHarvest) {
		this.expectHarvest = expectHarvest;
	}

	public String getInnovate() {
		return innovate;
	}
	public void setInnovate(String innovate) {
		this.innovate = innovate;
	}
	public String getResearchActuality() {
		return researchActuality;
	}
	public void setResearchActuality(String researchActuality) {
		this.researchActuality = researchActuality;
	}
	public String getResearchArea() {
		return researchArea;
	}
	public void setResearchArea(String researchArea) {
		this.researchArea = researchArea;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String selectTopicMeaning) {
		this.meaning = selectTopicMeaning;
	}
	public String getThinkingAndMethod() {
		return thinkingAndMethod;
	}
	public void setThinkingAndMethod(String thinkingAndMethod) {
		this.thinkingAndMethod = thinkingAndMethod;
	}
	/**
	 * @return Returns the thesisKeyWord.
	 */
	public String getKeyWords() {
		return keyWords;
	}
	/**
	 * @param thesisKeyWord The thesisKeyWord to set.
	 */
	public void setKeyWords(String thesisKeyWord) {
		this.keyWords = thesisKeyWord;
	}
	/**
	 * @return Returns the aboutThesisNumber.
	 */
	public Float getAboutThesisNumber() {
		return aboutThesisNumber;
	}
	/**
	 * @param aboutThesisNumber The aboutThesisNumber to set.
	 */
	public void setAboutThesisNumber(Float aboutThesisNumber) {
		this.aboutThesisNumber = aboutThesisNumber;
	}
	
	
}

