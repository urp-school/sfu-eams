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
 * chaostone             2006-8-17            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.textbook;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.course.task.TeachTask;

/**
 * 教材需求
 * 
 * @author chaostone
 * 
 */
public class BookRequirement extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1440355910895625108L;

	/** 教材 */
	private Textbook textbook = new Textbook();

	/** 数量 */
	private Integer countForStd;

	/** 教师用书数量 */
	private Integer countForTeacher;

	/** 任务 */
	private TeachTask task = new TeachTask();

	/** 是否通过审核 */
	private Boolean checked;

	/** 备注 */
	private String remark;

	public BookRequirement(TeachTask task, Textbook textbook) {
		this.textbook = textbook;
		this.task = task;
		this.countForStd = task.getTeachClass().getStdCount();
		this.checked = Boolean.FALSE;
		this.countForTeacher = new Integer(0);
	}

	public BookRequirement() {
		super();
	}

	public Integer getCountForStd() {
		return countForStd;
	}

	public void setCountForStd(Integer count) {
		this.countForStd = count;
	}

	public TeachTask getTask() {
		return task;
	}

	public void setTask(TeachTask task) {
		this.task = task;
	}

	public Textbook getTextbook() {
		return textbook;
	}

	public void setTextbook(Textbook textbook) {
		this.textbook = textbook;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getCountForTeacher() {
		return countForTeacher;
	}

	public void setCountForTeacher(Integer countForTeacher) {
		this.countForTeacher = countForTeacher;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
