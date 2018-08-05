//$Id: Questionnaire.java,v 1.1 2007-6-2 上午10:34:03 chaostone Exp $
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
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Department;

/**
 * 评教问卷
 * 
 * @author cwx,chaostone
 * 
 */
public class Questionnaire extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1753412086214741804L;

	/** 问卷标题 */
	private String title;

	/** 简单描述 */
	private String description;

	/** 相关联的问题 */
	private Set questions = new HashSet();

	/** 选项组 */
	private OptionGroup optionGroup = new OptionGroup();

	/** 创建部门 */
	private Department depart = new Department();

	/** 备注 */
	private String remark;

	/** 创建时间 */
	private Timestamp createAt;

	/** 创建者 */
	private String createBy;

	/**使用状态*/
	private Boolean state;
	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department department) {
		this.depart = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String describe) {
		this.description = describe;
	}

	public Set getQuestions() {
		return questions;
	}

	public void setQuestions(Set questions) {
		this.questions = questions;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public OptionGroup getOptionGroup() {
		return optionGroup;
	}

	public void setOptionGroup(OptionGroup optionGroup) {
		this.optionGroup = optionGroup;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public void setQuestionIds(String questionIds){
		Set questions = new HashSet();
		Long[] questionId =SeqStringUtil.transformToLong(questionIds);
		for (int i = 0; i < questionId.length; i++) {
			questions.add(new Question(questionId[i]));
		}
		this.setQuestions(questions);
	}
}
