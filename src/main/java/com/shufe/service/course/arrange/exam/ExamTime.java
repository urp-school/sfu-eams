//$Id: ExamTime.java,v 1.1 2007-7-29 下午12:47:03 chaostone Exp $
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
 * chenweixiong              2007-7-29         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.exam;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.exam.ExamTurn;

/**
 * 排考中使用的排考时间
 * 
 * @author chaostone
 * 
 */
public class ExamTime implements Comparable {
	/** 时间点 */
	private TimeUnit unit;
	/** 周次 */
	private int startWeek;
	/** 周几 */
	private WeekInfo week;
	/** 考试轮次 */
	private ExamTurn turn;
	/** 优先级 */
	private int priority;

	public ExamTime(TimeUnit unit, int startWeek, WeekInfo week, ExamTurn turn) {
		super();
		this.unit = unit;
		this.startWeek = startWeek;
		this.week = week;
		this.turn = turn;
	}

	//周次\优先级\场次\周几
	public int compareTo(Object object) {
		ExamTime myClass = (ExamTime) object;
		return new CompareToBuilder().append(getStartWeek(), myClass.getStartWeek()).append(
				getPriority(), myClass.getPriority()).append(getTurn(), myClass.getTurn())
				.append(getWeek().getId(), myClass.getWeek().getId())
				.toComparison();
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public int getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(int startWeek) {
		this.startWeek = startWeek;
	}

	public WeekInfo getWeek() {
		return week;
	}

	public void setWeek(WeekInfo week) {
		this.week = week;
	}

	public ExamTurn getTurn() {
		return turn;
	}

	public void setTurn(ExamTurn turn) {
		this.turn = turn;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
