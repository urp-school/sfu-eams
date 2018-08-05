//$Id: EvaluationCriteriaItem.java,v 1.1 2007-6-2 下午12:23:15 chaostone Exp $
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
 *chaostone      2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 评价名称对应项<br>
 * [min,max),评价项属于前闭后开
 * 
 * @author chaostone
 * 
 */
public class EvaluationCriteriaItem extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7078505642261777895L;

	/** 最小分值 */
	private Float min;

	/** 最大分值 */
	private Float max;

	/** 对应的评价名称 */
	private String name;

	/** 评价 */
	private EvaluationCriteria criteria;

	/**
	 * 给定分数是否符合该评价项
	 * 
	 * @param score
	 * @return
	 */
	public boolean inScope(float score) {
		if (score >= min.floatValue() && score < max.floatValue())
			return true;
		else
			return false;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EvaluationCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(EvaluationCriteria criteria) {
		this.criteria = criteria;
	}

}
