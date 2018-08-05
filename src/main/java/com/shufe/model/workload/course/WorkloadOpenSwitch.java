//$Id: WorkloadButton.java,v 1.3 2006/11/16 05:02:25 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-19         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.course;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 工作量查询开关
 * 
 * @author chenweixiong
 * 
 */
public class WorkloadOpenSwitch extends LongIdObject {
	private static final long serialVersionUID = 6038587118701782916L;

	/** 是否开放 */
	private Boolean isOpen;

	/** 教学日历 */
	private TeachCalendar teachCalendar = new TeachCalendar();

	/** 开放时间 */
	private Date openTime;

	public WorkloadOpenSwitch() {

	}

	public WorkloadOpenSwitch(Long teachCalendarId) {
		this.getTeachCalendar().setId(teachCalendarId);
		this.openTime = new Date(System.currentTimeMillis());
	}

	/**
	 * @return Returns the isOpen.
	 */
	public Boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen
	 *            The isOpen to set.
	 */
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * @return Returns the openTime.
	 */
	public Date getOpenTime() {
		return openTime;
	}

	/**
	 * @param openTime
	 *            The openTime to set.
	 */
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	/**
	 * @return Returns the teachCalendar.
	 */
	public TeachCalendar getTeachCalendar() {
		return teachCalendar;
	}

	/**
	 * @param teachCalendar
	 *            The teachCalendar to set.
	 */
	public void setTeachCalendar(TeachCalendar teachCalendar) {
		this.teachCalendar = teachCalendar;
	}

	public boolean isSaveObject() {
		boolean flag = false;
		if (null != this.id && !new Long(0).equals(this.id)) {
			flag = true;
		}
		return flag;
	}

}
