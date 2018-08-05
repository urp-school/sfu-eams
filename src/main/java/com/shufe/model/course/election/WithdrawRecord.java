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
 * chaostone             2006-7-19            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.election;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;

/**
 * 退课记录
 * 
 * @author chaostone
 * 
 */
public class WithdrawRecord extends LongIdObject {
	private static final long serialVersionUID = 6604649416106632981L;
	/** 教学任务 */
	private TeachTask task = new TeachTask();

	/** 学生 */
	private Student student = new Student();

	/** 选课时间(包括具体的时分秒) */
	private Date time;

	/** 修读类别 */
	private CourseTakeType courseTakeType = new CourseTakeType();

	/** 退课人 */
	private String who;

	public WithdrawRecord() {
	}

	public WithdrawRecord(TeachTask task, Student student,
			CourseTakeType courseTakeType) {
		this.task = task;
		this.student = student;
		this.courseTakeType = courseTakeType;
		this.time = new Date(System.currentTimeMillis());
	}

	public WithdrawRecord(CourseTake take) {
		this.task = take.getTask();
		this.student = take.getStudent();
		this.courseTakeType = take.getCourseTakeType();
		this.time = new Date(System.currentTimeMillis());
	}

	public WithdrawRecord(CourseTake take, String who) {
		this(take);
		this.who = who;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date withdrawTime) {
		this.time = withdrawTime;
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

	public CourseTakeType getCourseTakeType() {
		return courseTakeType;
	}

	public void setCourseTakeType(CourseTakeType courseTakeType) {
		this.courseTakeType = courseTakeType;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

}
