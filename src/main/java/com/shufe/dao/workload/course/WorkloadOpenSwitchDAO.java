//$Id: WorkloadButtonDAO.java,v 1.7 2007/01/10 06:17:46 cwx Exp $
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
 * chenweixiong              2006-8-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.course;

import java.util.Collection;
import java.util.List;

import com.shufe.dao.BasicDAO;

public interface WorkloadOpenSwitchDAO extends BasicDAO {

	/**
	 * 根据学生类别串得到所有的工作量开关
	 * @param stdSeq
	 * @return
	 */
	public List getWorkloadButtonsByStdSeq(String stdTypeSeq);
	
	/**
	 * 根据教学日历串得到所有的工作量开关对象.
	 * @param teachCalendarIdSeq
	 * @return
	 */
	public List getWorkloadButtonByCalendarSeq(String teachCalendarIdSeq);
	
	/**根据学生类别的seq  得到所有的开放开放的教学日历.
	 * @return
	 */
	public List getCalendarsOfOpenByStdSeq(String stdTypeSeq);
	
	/**
	 * @param calendars
	 * @return
	 */
	public List getCalendarOpenedByCalendars(Collection calendars);
}