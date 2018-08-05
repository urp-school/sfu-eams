//$Id: QuestionType.java,v 1.1 2007-6-2 上午10:35:09 chaostone Exp $
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

import java.sql.Timestamp;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 问题类型
 * 
 * @author cwx,chaostone
 * 
 */
public class QuestionType extends LongIdObject implements Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1400848840971531310L;

	/** 中文名称 */
	private String name;

	/** 英文名称 */
	private String engName;

	/** 创建时间 */
	private Timestamp createAt;

	/** 备注 */
	private String remark;

	/** 优先级 ,越大越靠前 */
	private Integer priority;

	/** 状态 */
	private Boolean state;

	public QuestionType() {
		super();
	}

	public QuestionType(Long id) {
		super(id);
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public int compareTo(Object object) {
		QuestionType myClass = (QuestionType) object;
		return new CompareToBuilder().append(myClass.getPriority(),
				this.getPriority()).toComparison();
	}
}
