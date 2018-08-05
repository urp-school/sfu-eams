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
 * chaostone             2006-7-28            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.election;

/**
 * 学分初始化参数
 * 
 * @author chaostone
 * 
 */
public class CreditInitParams {
	// 教学日历
	Long calendarId;

	// 上一个学期的教学日历
	Long preCalendarId;

	String overlappedCalendarIds;
	// 是否初始化以修学分
	Boolean initElectedCredit = Boolean.FALSE;

	// 是否初始化奖励学分
	Boolean initAwardCredit = Boolean.FALSE;

	// 是否初始化平均绩点
	Boolean initGPA = Boolean.FALSE;

	public Boolean getInitAwardCredit() {
		return initAwardCredit;
	}

	public void setInitAwardCredit(Boolean initAwardCredit) {
		this.initAwardCredit = initAwardCredit;
	}

	public Boolean getInitElectedCredit() {
		return initElectedCredit;
	}

	public void setInitElectedCredit(Boolean initElectedCredit) {
		this.initElectedCredit = initElectedCredit;
	}

	public Boolean getInitGPA() {
		return initGPA;
	}

	public void setInitGPA(Boolean initGPA) {
		this.initGPA = initGPA;
	}

	public Long getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(Long calendarId) {
		this.calendarId = calendarId;
	}

	public Long getPreCalendarId() {
		return preCalendarId;
	}

	public void setPreCalendarId(Long preCalendarId) {
		this.preCalendarId = preCalendarId;
	}

	public String getOverlappedCalendarIds() {
		return overlappedCalendarIds;
	}

	public void setOverlappedCalendarIds(String overlappedCalendarIds) {
		this.overlappedCalendarIds = overlappedCalendarIds;
	}


}
