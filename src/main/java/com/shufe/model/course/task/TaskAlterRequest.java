//$Id: TaskAlterRequisition.java,v 1.3 2006/12/07 11:02:10 duanth Exp $
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
 * yangdong             2006-1-4         	Created
 *  
 ********************************************************************************/

package com.shufe.model.course.task;

import java.sql.Date;

import org.apache.commons.lang.ObjectUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 教学任务变更申请<br>
 * 对教学运行中的教学任务进行变更申请，该申请的处理需要管理员进行审批。<br>
 * 申请调课、换课的实际变动需要管理员在课程安排模块进行手工调整。
 * 
 * @author yangdong
 */
public class TaskAlterRequest extends LongIdObject {

	private static final long serialVersionUID = 828477765227607522L;

	/** 未处理 */
	public static final String UNPROCESSED = "Y";

	/** 已批准 */
	public static final String APPROVED = "O";

	/** 不批准* */
	public static final String UNAPPROVED = "N";

	private Teacher teacher = new Teacher();

	private TeachTask task = new TeachTask();

	private String requisition;

	private Date time;

	private String availability;

	public String getRequisition() {
		return requisition;
	}

	public void setRequisition(String requisition) {
		this.requisition = requisition;
	}

	public TeachTask getTask() {
		return task;
	}

	public void setTask(TeachTask task) {
		this.task = task;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public boolean isApproved() {
		return ObjectUtils.equals(getAvailability(), APPROVED);
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
