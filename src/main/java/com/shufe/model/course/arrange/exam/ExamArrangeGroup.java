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
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamTakeGenerator;

/**
 * 同一课程代码变为一个排课组
 * 
 * @author chaostone
 * 
 */
public class ExamArrangeGroup {

	/** 教学日历 */
	TeachCalendar calendar;

	/** 组内课程 */
	Course course;

	/** 组内任务 */
	List tasks;

	/** 组内学生人数 */
	int stdCount;

	/** 组内参加考试的学生id */
	Set stdIds;

	/** 组内学生在一个给定集合里的占用图 */
	BitSet stdOccupy = null;

	public ExamArrangeGroup() {
		super();
	}

	public ExamArrangeGroup(TeachCalendar calendar, Course course) {
		super();
		this.calendar = calendar;
		this.course = course;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getStdCount() {
		return stdCount;
	}

	public void setStdCount(int stdCount) {
		this.stdCount = stdCount;
	}

	public List getTasks() {
		return tasks;
	}

	public void setTasks(List tasks) {
		this.tasks = tasks;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public int getCommonStartWeek() {
		int commonStartWeek = 0;
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			if (task.getArrangeInfo().getEndWeek() > commonStartWeek) {
				commonStartWeek = task.getArrangeInfo().getEndWeek();
			}
		}
		return commonStartWeek;
	}

	public List getAdminClasses() {
		List adminClasses = new ArrayList();
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			adminClasses.addAll(task.getTeachClass().getAdminClasses());
		}
		return adminClasses;
	}

	/**
	 * 教学任务按照人数从大到小进行排序
	 * 
	 */
	public void sortTasks(Comparator comparator) {
		Collections.sort(tasks, comparator);
	}

	/**
	 * 统计教学任务人数
	 * 
	 */
	public void statStdCount(ExamTakeGenerator takeGenerator, ExamType examType) {
		this.stdCount = 0;
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			stdCount += takeGenerator.getTakeCount(task, examType);
		}
	}

	/**
	 * 统计教学任务人数
	 * 
	 */
	public Set getStdIds(ExamTakeGenerator takeGenerator, ExamType examType) {
		if (null == stdIds) {
			stdIds = new HashSet();
			for (Iterator iter = tasks.iterator(); iter.hasNext();) {
				TeachTask task = (TeachTask) iter.next();
				stdIds.addAll(takeGenerator.getTakeStdIds(task, examType));
			}
		}
		return stdIds;
	}

	public void buildOccupy(List stdIds, ExamType examType, ExamTakeGenerator takeGenerator) {
		HashSet groupStdIds = new HashSet();
		// 为所有该组的学生建立索引
		for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
			TeachTask task = (TeachTask) iterator.next();
			List takes = takeGenerator.generate(task, examType);
			for (Iterator iterator2 = takes.iterator(); iterator2.hasNext();) {
				ExamTake take = (ExamTake) iterator2.next();
				groupStdIds.add(take.getStd().getId());
			}
		}
		// 初始化bitSet
		stdOccupy = new BitSet(stdIds.size());
		for (int i = 0; i < stdIds.size(); i++) {
			if (groupStdIds.contains(((Object[]) stdIds.get(i))[0])) {
				stdOccupy.set(i);
			}
		}
	}

	public BitSet getStdOccupy() {
		return stdOccupy;
	}

	public void setStdOccupy(BitSet stdOccupy) {
		this.stdOccupy = stdOccupy;
	}

}
