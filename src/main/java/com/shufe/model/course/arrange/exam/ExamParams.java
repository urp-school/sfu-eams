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
 * chaostone             2006-11-6            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 排考参数
 * 
 * @author chaostone
 * 
 */
public class ExamParams {

	public static final int NONE = 0;

	public static final int CLASS_COLLISION = 1;

	public static final int STD_COLLISION = 2;

	public static final int COURSE_WEEK_MODE = 1;

	public static final int COMMON_WEEK_MODE = 2;

	/** 教学任务所在学期 */
	private TeachCalendar fromCalendar;

	/** 考试安排到的学年学期 */
	private TeachCalendar toCalendar;

	/** 起始教学周模式 */
	private int startWeekMode;

	/**
	 * 教学周使用模式 ＠see TimeIterator#CYCLE,DIFFRENT
	 */
	private int weekMode;

	/** 从教学日历的第几周开始排考/或者排课结束的第几周，具体一句startWeekMode */
	private int fromWeek;

	/** 排到第几周 */
	private int lastWeek;

	/** examTimes */
	private List examTimes;

	/** 可用教室 */
	private List rooms = new ArrayList();

	/** 考试类别 */
	private ExamType examType;

	/** 每场补缓考人数上限 */
	private int stdPerTurn = 0;

	/** 可以安排在同一考场的课程代码集合,多个集合用;分割 */
	private String groupCourseNos;

	/** 是否允许在同一个教室中容纳多个教学任务的学生进行考试 */
	private boolean canCombineTask;

	/** 冲突检测项1班级，2学生,0表示不进行冲突检测 */
	private int detectCollision;

	/** 每个排考组内冲突人数的比例下限 */
	private float collisionRatio;

	/** 按照教学任务上课校区，安排考试教室 */
	private boolean sameDistrictWithCourse;
	/** 单个教室利用比 下限 */
	private float minRoomOccupyRatio;

	/** 同组教室利用里 下限 */
	private float minGroupOccupyRatio;

	public ExamParams() {
	}

	public void sortExamTimes() {
		Collections.sort(examTimes);
	}

	public float getMinRoomOccupyRatio() {
		return minRoomOccupyRatio;
	}

	public void setMinRoomOccupyRatio(float minRatio) {
		this.minRoomOccupyRatio = minRatio;
	}

	public List getRooms() {
		return rooms;
	}

	public void setRooms(List rooms) {
		this.rooms = rooms;
	}

	public int getWeekMode() {
		return weekMode;
	}

	public void setWeekMode(int weekmode) {
		this.weekMode = weekmode;
	}

	public int getLastWeek() {
		return lastWeek;
	}

	public void setLastWeek(int lastWeek) {
		this.lastWeek = lastWeek;
	}

	public List getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(List examTimes) {
		this.examTimes = examTimes;
	}

	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public float getMinGroupOccupyRatio() {
		return minGroupOccupyRatio;
	}

	public void setMinGroupOccupyRatio(float minGroupOccupyRatio) {
		this.minGroupOccupyRatio = minGroupOccupyRatio;
	}

	public boolean isCanCombineTask() {
		return canCombineTask;
	}

	public void setCanCombineTask(boolean canCombineTask) {
		this.canCombineTask = canCombineTask;
	}

	public int getDetectCollision() {
		return detectCollision;
	}

	public void setDetectCollision(int detectCollision) {
		this.detectCollision = detectCollision;
	}

	public int getStartWeekMode() {
		return startWeekMode;
	}

	public void setStartWeekMode(int startWeekMode) {
		this.startWeekMode = startWeekMode;
	}

	public int getFromWeek() {
		return fromWeek;
	}

	public void setFromWeek(int fromWeek) {
		this.fromWeek = fromWeek;
	}

	public TeachCalendar getFromCalendar() {
		return fromCalendar;
	}

	public void setFromCalendar(TeachCalendar fromCalendar) {
		this.fromCalendar = fromCalendar;
	}

	public TeachCalendar getToCalendar() {
		return toCalendar;
	}

	public void setToCalendar(TeachCalendar toCalendar) {
		this.toCalendar = toCalendar;
	}

	public int getStdPerTurn() {
		return stdPerTurn;
	}

	public void setStdPerTurn(int stdPerTurn) {
		this.stdPerTurn = stdPerTurn;
	}

	public String getGroupCourseNos() {
		return groupCourseNos;
	}

	public void setGroupCourseNos(String groupCourseNos) {
		this.groupCourseNos = groupCourseNos;
	}

	public float getCollisionRatio() {
		return collisionRatio;
	}

	public void setCollisionRatio(float collisionRatio) {
		this.collisionRatio = collisionRatio;
	}

	public boolean isSameDistrictWithCourse() {
		return sameDistrictWithCourse;
	}

	public void setSameDistrictWithCourse(boolean sameDistrictWithCourse) {
		this.sameDistrictWithCourse = sameDistrictWithCourse;
	}

}
