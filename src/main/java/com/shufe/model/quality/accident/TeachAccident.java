//$Id: TeachAccident.java,v 1.1 2006/10/24 11:09:43 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-9         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.accident;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.HandleType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 教学事故
 * 
 * @author chaostone
 * 
 */
public class TeachAccident extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8853288227392153650L;

	/** 对应的教学任务 */
	private TeachTask task;

	/** 发生时间 */
	private Date occurAt;

	/** 教师 */
	private Teacher teacher = new Teacher();

	/** 事故描述 */
	private String description;

	/** 备注 */
	private String remark;

	/** 修改时间 */
	private Date modifyAt;

	/** 事故处理类别 */
	private HandleType handleType = new HandleType();


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return Returns the teacher.
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher
	 *            The teacher to set.
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	/**
	 * @return Returns the handleType.
	 */
	public HandleType getHandleType() {
		return handleType;
	}

	/**
	 * @param handleType
	 *            The handleType to set.
	 */
	public void setHandleType(HandleType handleType) {
		this.handleType = handleType;
	}

	public TeachTask getTask() {
		return task;
	}

	public void setTask(TeachTask task) {
		this.task = task;
	}

	public void setOccurAt(Date occurAt) {
		this.occurAt = occurAt;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

	public Date getOccurAt() {
		return occurAt;
	}

	public String getDescription() {
		return description;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

}
