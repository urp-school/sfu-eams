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
 * 塞外狂人             2006-8-12            Created
 *  
 ********************************************************************************/
package com.shufe.model.degree.tutorManager;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * 助教管理
 * 
 * @author 塞外狂人
 * 
 */
public class Assistant extends LongIdObject {

	private static final long serialVersionUID = 2503022149514969256L;

	/** 学生 */
	private Student std = new Student();

	/** 导师 */
	private Teacher tutor = new Teacher();

	/** 开始时间 */
	private Date startTime;

	/** 结束时间 */
	private Date endTime;

	/** 所做工作 */
	private String job;

	/** 学时 */
	private Float period;

	/** 导师确认 */
	private Boolean isConfirm;

	public Boolean getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Float getPeriod() {
		return period;
	}

	public void setPeriod(Float period) {
		this.period = period;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}

	public Teacher getTutor() {
		return tutor;
	}

	public void setTutor(Teacher tutor) {
		this.tutor = tutor;
	}

}
