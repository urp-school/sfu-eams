//$Id: TimeUnitIterator.java,v 1.1 2007-7-29 上午11:43:13 chaostone Exp $
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

package com.shufe.service.course.arrange.exam.impl.time;

import java.util.Iterator;
import java.util.List;

import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.shufe.model.course.arrange.exam.ExamTurn;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamTime;

/**
 * 时间单元迭代器
 * 
 * @author chaostone
 * 
 */
public class TimeIterator implements Iterator {

	public static final int CYCLE = 1;
	public static final int DIFFERENT = 2;
	private TeachCalendar calendar;
	private int startWeek;
	private int lastWeek;
	private List examTimes;
	private int mode;
	// examTimes中下一个要使用的场次
	private int timeIndex = 0;
	private TimeUnit unit;

	/**
	 * 构造场次时间迭代器
	 * 
	 * @param calendar
	 * @param startWeek
	 * @param lastWeek
	 * @param mode
	 * @param examTimes
	 *            已经按照周次、周几和场次优先级进行了排序
	 */
	public TimeIterator(TeachCalendar calendar, int startWeek, int lastWeek, int mode,
			List examTimes) {
		super();
		this.calendar = calendar;
		this.startWeek = startWeek;
		this.lastWeek = lastWeek;
		this.examTimes = examTimes;
		this.mode=mode;
	}

	public boolean hasNext() {
		if (mode == CYCLE) {
			return (startWeek <= lastWeek && timeIndex < examTimes.size());
		} else {
			return (timeIndex < examTimes.size());
		}
	}

	public Object next() {
		// 生成unit
		if (unit == null) {
			TimeUnit[] units = TimeUnitUtil.buildTimeUnits(calendar.getStartYear(), calendar
					.getWeekStart().intValue(), startWeek, startWeek, TimeUnit.CONTINUELY);
			unit = units[0];
		}
		// 根据examTime 生成TimeUnit
		ExamTime examTime = (ExamTime) examTimes.get(timeIndex);
		ExamTurn examTurn = examTime.getTurn();
		Integer[] unitIndexes = calendar.getTimeSetting().getUnitIndexes(examTurn.getBeginTime(),
				examTurn.getEndTime());
		unit.setWeekId(new Integer(examTime.getWeek().getId().intValue()));
		unit.setStartUnit(unitIndexes[0]);
		unit.setEndUnit(unitIndexes[1]);
		unit.setStartTime(examTurn.getBeginTime());
		unit.setEndTime(examTurn.getEndTime());

		ExamTime time = new ExamTime(unit, startWeek, examTime.getWeek(), examTurn);
		// 步进
		timeIndex++;
		if (mode == CYCLE) {
			if (timeIndex == examTimes.size()) {
				startWeek++;
				unit = null;
				timeIndex = 0;
			}
		} else if (mode == DIFFERENT) {
			if (timeIndex < examTimes.size()) {
				ExamTime nextExamTime = (ExamTime) examTimes.get(timeIndex);
				if (nextExamTime.getStartWeek() > startWeek) {
					startWeek = nextExamTime.getStartWeek();
					unit = null;
				}
			}
		} else {
			throw new RuntimeException("not supported mode");
		}
		return time;
	}

	public void remove() {

	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public int getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(int startWeek) {
		this.startWeek = startWeek;
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

	public int getMode() {
		return mode;
	}

	public int getTimeIndex() {
		return timeIndex;
	}

}
