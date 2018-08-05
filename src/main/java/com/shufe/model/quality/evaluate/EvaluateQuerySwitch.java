//$Id: QuerySwitch.java,v 1.1 2007-6-4 9:22:45 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-6-4         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.calendar.TeachCalendar;

public class EvaluateQuerySwitch extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8048795256537988296L;

	/** 是否开放 */
	private Boolean isOpen;

	/** 开放时间 */
	private Date openAt;

	/** 教学日历 */
	private TeachCalendar teachCalendar = new TeachCalendar();

	public EvaluateQuerySwitch() {
	}

	public EvaluateQuerySwitch(TeachCalendar teachCalendar) {
		this.isOpen = Boolean.FALSE;
		this.teachCalendar = teachCalendar;
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
	public Date getOpenAt() {
		return openAt;
	}

	/**
	 * @param openTime
	 *            The openTime to set.
	 */
	public void setOpenAt(Date openTime) {
		this.openAt = openTime;
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

}
