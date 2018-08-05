//$Id: WorkloadButtonServiceImpl.java,v 1.6 2007/01/10 06:17:24 cwx Exp $
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

package com.shufe.service.workload.course.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.shufe.dao.workload.course.WorkloadOpenSwitchDAO;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.course.WorkloadOpenSwitch;
import com.shufe.service.BasicService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.service.workload.course.WorkloadOpenSwitchService;

public class WorkloadOpenSwitchServiceImpl extends BasicService implements
		WorkloadOpenSwitchService {

	private WorkloadOpenSwitchDAO workloadOpenSwitchDAO;

	private TeachCalendarService teachCalendarService;

	public void setWorkloadOpenSwitchDAO(
			WorkloadOpenSwitchDAO workloadOpenSwitchDAO) {
		this.workloadOpenSwitchDAO = workloadOpenSwitchDAO;
	}

	public void setTeachCalendarService(
			TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.workload.WorkloadButtonService#getCalendarsOfCurrentAndHistory(java.lang.String)
	 */
	public List getCalendarsOfCurrentAndHistory(String stdTypeIdSeq) {
		List conditionList = new ArrayList();
		List teachCalendars = teachCalendarService
				.getTeachCalendars(stdTypeIdSeq);
		Date date = new Date(System.currentTimeMillis());
		for (Iterator iter = teachCalendars.iterator(); iter.hasNext();) {
			TeachCalendar teachCalendar = (TeachCalendar) iter.next();
			if (teachCalendar.getStart().before(date))
				conditionList.add(teachCalendar);
		}
		return conditionList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.workload.WorkloadButtonService#getWorkloadByCalendarId(java.lang.Long)
	 */
	public WorkloadOpenSwitch getWorkloadByCalendarId(Long teachCalendarId) {
		List workloadButtons = workloadOpenSwitchDAO
				.getWorkloadButtonByCalendarSeq(String.valueOf(teachCalendarId));
		return workloadButtons.size() > 0 ? (WorkloadOpenSwitch) workloadButtons
				.get(0)
				: new WorkloadOpenSwitch();
	}

	/**
	 * @see com.shufe.service.workload.course.WorkloadOpenSwitchService#getWorkloadsByCalendarIdSeq(java.lang.String)
	 */
	public List getWorkloadsByCalendarIdSeq(String teachCalendarIdSeq) {
		return workloadOpenSwitchDAO
				.getWorkloadButtonByCalendarSeq(teachCalendarIdSeq);
	}

	/**
	 * @see com.shufe.service.workload.course.WorkloadOpenSwitchService#getWorloadByStdTypeIdSeq(java.lang.String)
	 */
	public List getWorloadByStdTypeIdSeq(String stdTypeIdSeq) {
		return workloadOpenSwitchDAO.getWorkloadButtonsByStdSeq(stdTypeIdSeq);
	}

	/**
	 * @see com.shufe.service.workload.course.WorkloadOpenSwitchService#getworkloadsOpensByStdTypeIdSeq(java.lang.String)
	 */
	public List getCalendarOpenedByStdTypeIdSeq(String stdTypeIdSeq) {
		return workloadOpenSwitchDAO.getCalendarsOfOpenByStdSeq(stdTypeIdSeq);
	}

	public List getCalendarOpenedByCalendars(Collection calendars) {
		return workloadOpenSwitchDAO.getCalendarOpenedByCalendars(calendars);
	}

}
