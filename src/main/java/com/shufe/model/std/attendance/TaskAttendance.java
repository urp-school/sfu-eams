//$Id: DutyRecord.java,v 1.13 2007/01/26 03:17:42 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-30         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.attendance;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;

/**
 * 学生任务考勤信息<br>
 * （对应一个学生，一个教学任务的考勤记录，如考勤次数，各状态出勤次数、出勤率、旷课率等）
 */
public class TaskAttendance implements Serializable, Cloneable {

	/** 退课修课类别（数据库中无此类别，仅为考勤检测和展示修读类别而添加） */
	public static final CourseTakeType WITHDRAWCOURSETAKETYPE = new CourseTakeType();
	static {
		WITHDRAWCOURSETAKETYPE.setId(new Long(-1));
		WITHDRAWCOURSETAKETYPE.setCode("null");
		WITHDRAWCOURSETAKETYPE.setName("退课");
		WITHDRAWCOURSETAKETYPE.setEngName("Withdraw");
	}

	private static final long serialVersionUID = 2034903220526520783L;

	private Long id;

	/** 对应学生 */
	private Student std;

	/** 对应教学任务 */
	private TeachTask task;

	/** 出勤次数 */
	private Integer present;

	/** 旷课次数 */
	private Integer absent;

	/** 迟到次数 */
	private Integer late;

	/** 早退次数 */
	private Integer leaveEarly;

	/** 请假次数 */
	private Integer leave;

	/** 记考勤次数 */
	private Integer total;

	/** 记录考勤详细信息 */
	private Set attendances = new HashSet(0);

	public Integer getAbsent() {
		return absent;
	}

	public void setAbsent(Integer absent) {
		this.absent = absent;
	}

	public Float getAbsentRatio() {
		return null;
	}


	public Set getAttendances() {
		return attendances;
	}

	public void setAttendances(Set attendances) {
		this.attendances = attendances;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLate() {
		return late;
	}

	public void setLate(Integer late) {
		this.late = late;
	}

	public Integer getLeave() {
		return leave;
	}

	public void setLeave(Integer leave) {
		this.leave = leave;
	}

	public Integer getLeaveEarly() {
		return leaveEarly;
	}

	public void setLeaveEarly(Integer leaveEarly) {
		this.leaveEarly = leaveEarly;
	}

	public Integer getPresent() {
		return present;
	}

	public void setPresent(Integer present) {
		this.present = present;
	}

	public Float getPresentRatio() {
		return null;
	}


	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}

	public TeachTask getTask() {
		return task;
	}

	public void setTask(TeachTask task) {
		this.task = task;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
