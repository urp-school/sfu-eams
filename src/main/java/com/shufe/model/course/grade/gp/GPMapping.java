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
 * chaostone             2007-1-8            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.gp;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;

/**
 * 绩点对照表
 * 
 * @author chaostone
 * 
 */
public class GPMapping extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2954241809521401256L;

	private Boolean containMax;// 是否包含上限

	private Boolean containMin;// 是否包含下限

	private Float maxScore; // 上限

	private Float minScore; // 下限

	private Float gp;// 绩点

	private GradePointRule rule = new GradePointRule();

	public String getMaxScoreDisplay() {
		if (null != getRule()) {
			return ConverterFactory.getConverter().convert(maxScore, rule.getMarkStyle());
		} else
			return "";
	}

	public String getMinScoreDisplay() {
		if (null != getRule()) {
			return ConverterFactory.getConverter().convert(minScore, rule.getMarkStyle());
		} else
			return "";
	}

	public boolean inScope(Float score) {
		return (maxScore.compareTo(score) > -1 && minScore.compareTo(score) < 1);
	}

	public Float getGp() {
		return gp;
	}

	public void setGp(Float gp) {
		this.gp = gp;
	}

	public Float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}

	public Float getMinScore() {
		return minScore;
	}

	public void setMinScore(Float minScore) {
		this.minScore = minScore;
	}

	public GradePointRule getRule() {
		return rule;
	}

	public void setRule(GradePointRule rule) {
		this.rule = rule;
	}

	public Boolean getContainMax() {
		return containMax;
	}

	public void setContainMax(Boolean containMax) {
		this.containMax = containMax;
	}

	public Boolean getContainMin() {
		return containMin;
	}

	public void setContainMin(Boolean containMin) {
		this.containMin = containMin;
	}

}
