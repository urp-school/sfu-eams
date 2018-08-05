//$Id: WorkloadButtonService.java,v 1.7 2007/01/10 06:17:24 cwx Exp $
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

package com.shufe.service.workload.course;

import java.util.Collection;
import java.util.List;

import com.shufe.dao.workload.course.WorkloadOpenSwitchDAO;
import com.shufe.model.workload.course.WorkloadOpenSwitch;

public interface WorkloadOpenSwitchService {

	/**
	 * 根据教学日历id得到一个开关对象
	 * 
	 * @param teachCalendarId
	 * @return
	 */
	public WorkloadOpenSwitch getWorkloadByCalendarId(Long teachCalendarId);

	/**
	 * 根据教学日历idseq得到一个开关对象的list
	 * 
	 * @param teachCalendarIdSeq
	 * @return
	 */
	public List getWorkloadsByCalendarIdSeq(String teachCalendarIdSeq);

	/**
	 * 根据学生类别idseq得到开放着的开关对象list
	 * 
	 * @param StdTypeIdSeq
	 * @return
	 */
	public List getCalendarOpenedByStdTypeIdSeq(String stdTypeIdSeq);

	/**
	 * 根据学生类别串得到相关的所有的开关对象list
	 * 
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getWorloadByStdTypeIdSeq(String stdTypeIdSeq);

	/**
	 * 根据学生类别串得到所有的当前和历史学期的教学任务.
	 * 
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getCalendarsOfCurrentAndHistory(String stdTypeIdSeq);

	/**
	 * 根据教学日历集合得到开放的教学日历。
	 * 
	 * @param calendars
	 * @return
	 */
	public List getCalendarOpenedByCalendars(Collection calendars);

	public void setWorkloadOpenSwitchDAO(
			WorkloadOpenSwitchDAO workloadOpenSwitchDAO);

}
