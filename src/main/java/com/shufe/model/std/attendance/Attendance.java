//$Id: RecordDetail.java,v 1.8 2007/01/26 03:17:42 yd Exp $
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
 * pippo             2005-12-1         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.attendance;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 单条考勤记录<br>
 * （对应一条学生考勤记录的一条考勤记录详情，如考勤时间、出勤状态等）
 */
public class Attendance extends LongIdObject implements Cloneable {

	private static final long serialVersionUID = 5150851766374647749L;

	/** 对应考勤记录 */
	private TaskAttendance taskAttendance;

	/** 考勤开始日期 */
	private Date begin;

	/** 考勤结束时间 */
	private Date end;

	/** 考勤起始小节编号 */
	private Integer beginUnit;

	/** 考勤结束小节编号 */
	private Integer endUnit;

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Integer getBeginUnit() {
		return beginUnit;
	}

	public void setBeginUnit(Integer beginUnit) {
		this.beginUnit = beginUnit;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Integer getEndUnit() {
		return endUnit;
	}

	public void setEndUnit(Integer endUnit) {
		this.endUnit = endUnit;
	}

	public TaskAttendance getTaskAttendance() {
		return taskAttendance;
	}

	public void setTaskAttendance(TaskAttendance taskAttendance) {
		this.taskAttendance = taskAttendance;
	}

}
