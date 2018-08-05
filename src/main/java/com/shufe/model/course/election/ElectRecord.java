//$Id: ElectRecord.java,v 1.2 2006/10/12 12:20:00 duanth Exp $
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
 * hc                   2005-9-26           Created
 * chaostone            2005-10-16          modified
 *  
 ********************************************************************************/

package com.shufe.model.course.election;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;

/**
 * 选课记录
 * 
 * @author hc,chaostone 2005-10-16
 */
public class ElectRecord extends LongIdObject {

	private static final long serialVersionUID = 6604649416106632981L;

	/** 教学任务 */
	private TeachTask task = new TeachTask();

	/** 学生 */
	private Student student = new Student();

	/** 选课轮次 */
	private Integer turn;

	/** 是否选中 */
	private Boolean isPitchOn;

	/** 选课时间(包括具体的时分秒) */
	private Date electTime;

	/** 修读类别 */
	private CourseTakeType courseTakeType = new CourseTakeType();

	public ElectRecord() {
	}

	public ElectRecord(TeachTask task, Student student, Integer turn,
			CourseTakeType courseTakeType) {
		this.task = task;
		this.student = student;
		this.turn = turn;
		this.courseTakeType = courseTakeType;
		this.electTime = new Date(System.currentTimeMillis());
		this.isPitchOn = Boolean.FALSE;
	}

	/**
	 * @return Returns the electTime.
	 */
	public Date getElectTime() {
		return electTime;
	}

	/**
	 * @param electTime
	 *            The electTime to set.
	 */
	public void setElectTime(Date electTime) {
		this.electTime = electTime;
	}

	/**
	 * @return Returns the isPitchOn.
	 */
	public Boolean getIsPitchOn() {
		return isPitchOn;
	}

	/**
	 * @param isPitchOn
	 *            The isPitchOn to set.
	 */
	public void setIsPitchOn(Boolean isPitchOn) {
		this.isPitchOn = isPitchOn;
	}

	/**
	 * @return Returns the student.
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            The student to set.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return Returns the teachTask.
	 */
	public TeachTask getTask() {
		return task;
	}

	/**
	 * @param teachTask
	 *            The teachTask to set.
	 */
	public void setTask(TeachTask teachTask) {
		this.task = teachTask;
	}

	/**
	 * @return Returns the turn.
	 */
	public Integer getTurn() {
		return turn;
	}

	/**
	 * @param turn
	 *            The turn to set.
	 */
	public void setTurn(Integer turn) {
		this.turn = turn;
	}

	public CourseTakeType getCourseTakeType() {
		return courseTakeType;
	}

	public void setCourseTakeType(CourseTakeType courseTakeType) {
		this.courseTakeType = courseTakeType;
	}
}
