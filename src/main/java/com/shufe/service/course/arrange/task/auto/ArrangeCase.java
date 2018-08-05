//$Id: ArrangeCase.java,v 1.6 2006/12/06 07:46:47 duanth Exp $
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
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import java.util.Collection;
import java.util.Collections;

import com.ekingstar.commons.lang.BitStringUtil;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 单门课程的安排<br>
 * 安排的时间安排在availableTime设置.
 * 
 * @author chaostone
 * 
 */
public class ArrangeCase implements Arrange {

	/**
	 * 安排对应的教学任务
	 */
	private TeachTask task;

	/**
	 * 安排的优先级
	 */
	private int priority = defaultPriority;

	/**
	 * 所在的排课组
	 */
	private ArrangeSuite suite;

	/**
	 * 可用的时间
	 */
	private AvailableTime availableTime;

	/**
	 * 对于可用时间的占用
	 */
	private String availUnitBitMap;

	private ArrangeInfo arrangeInfo;

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#arrange()
	 */
	public void arrange(ArrangeResult result) {
		result.arrange(this);
	}

	public ArrangeCase() {
	}

	public ArrangeCase(TeachTask task) {
		this.task = task;
	}

	public void arrangeBare(ArrangeResult rs) throws GeneralArrangeFailure {
		availUnitBitMap = BitStringUtil.convertToBoolStr(availableTime
				.getAvailable());
		/**
		 * donot forget set task's requirement although it's CapacityOfCourse is
		 * wasn't stored in db.
		 */
		task.getRequirement().setCapacityOfCourse(
				task.getTeachClass().getPlanStdCount());
		arrangeInfo = task.getArrangeInfo();
		task.addActivities(ArrangeUtil.arrangeSingleCase(this, rs));
		ArrangeUtil.updateTeachTaskState(task);
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#countArrangeCase()
	 */
	public int countArrangeCases() {
		return 1;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return task.getSeqNo() + ":" + task.getCourse().getName();
	}

	/**
	 * @return Returns the task.
	 */
	public TeachTask getTask() {
		return task;
	}

	/**
	 * @param task
	 *            The task to set.
	 */
	public void setTask(TeachTask task) {
		this.task = task;
	}

	/**
	 * @return Returns the priority.
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            The priority to set.
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return Returns the parentSuite.
	 */
	public ArrangeSuite getSuite() {
		return suite;
	}

	/**
	 * @param parentSuite
	 *            The parentSuite to set.
	 */
	public void setSuite(ArrangeSuite parentSuite) {
		this.suite = parentSuite;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getTasks()
	 */
	public Collection getTasks() {
		return Collections.singletonList(task);
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		return ((Arrange) o).getPriority() - getPriority();

	}

	/**
	 * @return Returns the availableTime.
	 */
	public AvailableTime getAvailableTime() {
		ArrangeSuite parentSuite = getSuite();
		while (null == availableTime) {
			availableTime = parentSuite.getAvailableTime();
			parentSuite = parentSuite.getSuite();
		}
		return availableTime;
	}

	/**
	 * @param availableTime
	 *            The availableTime to set.
	 */
	public void setAvailableTime(AvailableTime availableTime) {
		this.availableTime = availableTime;
	}

	/**
	 * @return Returns the timeOccupy.
	 */
	public String getAvailUnitBitMap() {
		if (null == availUnitBitMap)
			availUnitBitMap = BitStringUtil.convertToBoolStr(availableTime
					.getAvailable());
		return availUnitBitMap;
	}

	/**
	 * @param timeOccupy
	 *            The timeOccupy to set.
	 */
	public void setUnitOccupy(String timeOccupy) {
		this.availUnitBitMap = timeOccupy;
	}

	public ArrangeInfo getArrangeInfo() {
		return arrangeInfo;
	}

	/**
	 * @param arrangeInfo
	 *            The arrangeInfo to set.
	 */
	public void setArrangeInfo(ArrangeInfo arrangeInfo) {
		this.arrangeInfo = arrangeInfo;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getAdminClasses()
	 */
	public Collection getAdminClasses() {
		return task.getTeachClass().getAdminClasses();
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getTeachers()
	 */
	public Collection getTeachers() {
		return task.getArrangeInfo().getTeachers();
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getCalendar()
	 */
	public TeachCalendar getCalendar() {
		return task.getCalendar();
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getRequirement()
	 */
	public TaskRequirement getRequirement() {
		return task.getRequirement();
	}

	/**
	 * 
	 */
	public TeachTask getCurTask() {
		return task;
	}

}
