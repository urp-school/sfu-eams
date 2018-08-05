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
 * chaostone             2007-1-28            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.gp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.shufe.model.system.baseinfo.AdminClass;

/**
 * 多个学生的绩点汇总
 * 
 * @author chaostone
 * 
 */
public class MultiStdGP {
	/**
	 * 一般是以班级为单位的
	 */
	AdminClass adminClass;

	List calendars;

	/**
	 * @see StdGP
	 */
	List stdGPs = new ArrayList();

	public List getCalendars() {
		return calendars;
	}

	public void statCalendarsFromStdGP() {
		Set calendarFromStdGP = new HashSet();
		for (Iterator iter = stdGPs.iterator(); iter.hasNext();) {
			StdGP stdGp = (StdGP) iter.next();
			for (Iterator iterator = stdGp.getGPList().iterator(); iterator
					.hasNext();) {
				StdGPPerTerm gpterm = (StdGPPerTerm) iterator.next();
				calendarFromStdGP.add(gpterm.getCalendar());
			}
		}
		calendars = new ArrayList(calendarFromStdGP);
	}

	public void setCalendars(List calendars) {
		this.calendars = calendars;
	}

	public List getStdGPs() {
		return stdGPs;
	}

	public void setStdGPs(List stdGPs) {
		this.stdGPs = stdGPs;
	}

	public AdminClass getAdminClass() {
		return adminClass;
	}

	public void setAdminClass(AdminClass adminClass) {
		this.adminClass = adminClass;
	}

}
