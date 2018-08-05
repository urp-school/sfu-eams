//$Id: Question.java,v 1.1 2007-6-2 上午10:34:56 chaostone Exp $
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
import com.shufe.model.system.baseinfo.Department;

/**
 * 评教问题
 * 
 * @author chaostone
 * 
 */
public class Question extends LongIdObject implements Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2689463042864016371L;

	/** 问题内容 */
	private String content;

	/** 问题类型 */
	private QuestionType type = new QuestionType();

	/** 分值 */
	private Float score;

	/** 问题所对应的使用部门 */
	private Department department = new Department();

	/** 优先级 */
	private Integer priority;

	/** 注释 */
	private String remark;

	/** 创建时间 */
	private Timestamp createAt;

	/** 使用状态 */
	private Boolean state;

	
	public Question() {
		super();
	}
	public Question(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String context) {
		this.content = context;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Question myClass = (Question) object;
		return new CompareToBuilder().append(myClass.getType().getPriority(),
				getType().getPriority()).append(myClass.getPriority(),
				getPriority()).toComparison();
	}

}
