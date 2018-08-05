//$Id: CreditConstraint.java,v 1.2 2006/10/12 12:20:00 duanth Exp $
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
 * chaostone             2005-12-9         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.election;

import java.io.Serializable;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 选课学分限制
 * @author chaostone 2005-12-9
 */
public class CreditConstraint extends LongIdObject implements Serializable {

	private static final long serialVersionUID = 6763813672438837820L;
	
	/**学年学期*/
	private TeachCalendar calendar = new TeachCalendar();

	/**学分上限*/
	private Float maxCredit;

	/**学分下限*/
	private Float minCredit;

	/**
	 * @return Returns the calendar.
	 */
	public TeachCalendar getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar
	 *            The calendar to set.
	 */
	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * @return Returns the maxCredit.
	 */
	public Float getMaxCredit() {
		return maxCredit;
	}

	/**
	 * @param maxCredit
	 *            The maxCredit to set.
	 */
	public void setMaxCredit(Float maxCredit) {
		this.maxCredit = maxCredit;
	}

	/**
	 * @return Returns the minCredit.
	 */
	public Float getMinCredit() {
		return minCredit;
	}

	/**
	 * @param minCredit
	 *            The minCredit to set.
	 */
	public void setMinCredit(Float minCredit) {
		this.minCredit = minCredit;
	}

}
