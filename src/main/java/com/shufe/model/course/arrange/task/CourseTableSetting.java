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
 * chaostone             2007-1-21            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.task;

import java.util.List;

import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 课程表打印设置
 * 
 * @author chaostone
 * 
 */
public class CourseTableSetting {
	public static final String VERTICAL = "vertical";

	public static final String HORIZONTAL = "horizontal";

	public static final String ALLINONE = "single";

	/**
	 * 缺省每页的课表个数
	 */
	private int tablePerPage = 1;

	/**
	 * 课表中的像素数字大小
	 */
	private int fontSize = 12;

	/**
	 * 课表类型
	 */
	private String style = HORIZONTAL;

	/**
	 * 课表类型
	 */
	private String kind;

	/**
	 * 课程表发生的日历
	 */
	private TeachCalendar calendar;

	/**
	 * 一周内要打印的星期
	 */
	private List weekInfos;

	/**
	 * 是否显示教学日历的中的时间设置
	 */
	private boolean displayCalendarTime;

	/**
	 * 课程表的数据是否局限于给定的日历,否则查找与日历时间重叠的其他日历的课程
	 */
	private boolean forCalendar = true;

	/**
	 * 打印时是否不打印教学任务列表
	 */
	private boolean ignoreTask = false;

	private TimeUnit[] times;

	private String orderBy;

	public CourseTableSetting() {
		super();
	}

	public CourseTableSetting(TeachCalendar calendar) {
		setCalendar(calendar);
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public boolean getDisplayCalendarTime() {
		return displayCalendarTime;
	}

	public void setDisplayCalendarTime(boolean displayCalendarTime) {
		this.displayCalendarTime = displayCalendarTime;
	}

	public List getWeekInfos() {
		return weekInfos;
	}

	public void setWeekInfos(List weeks) {
		this.weekInfos = weeks;
	}

	public boolean getForCalendar() {
		return forCalendar;
	}

	public void setForCalendar(boolean forCalendar) {
		this.forCalendar = forCalendar;
	}

	public boolean getIgnoreTask() {
		return ignoreTask;
	}

	public void setIgnoreTask(boolean ignoreTask) {
		this.ignoreTask = ignoreTask;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public TimeUnit[] getTimes() {
		return times;
	}

	public void setTimes(TimeUnit[] times) {
		this.times = times;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getTablePerPage() {
		return tablePerPage;
	}

	public void setTablePerPage(int tablePerPage) {
		this.tablePerPage = tablePerPage;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
