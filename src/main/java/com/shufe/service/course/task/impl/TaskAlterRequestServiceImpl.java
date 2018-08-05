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
 * YANGDONG             2005-11-9         Created
 *  
 ********************************************************************************/
package com.shufe.service.course.task.impl;

import java.sql.Date;
import java.util.List;

import com.shufe.dao.course.task.TaskAlterRequestDAO;
import com.shufe.model.course.task.TaskAlterRequest;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.task.TaskAlterReqestService;

public class TaskAlterRequestServiceImpl extends BasicService implements
		TaskAlterReqestService {

	private TaskAlterRequestDAO taskAlterRequestDAO = null;

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.shufe.service.course.TaskAlterRequestService#setTaskAlterRequestDAO(com.shufe.dao.course.TaskAlterRequestDAO)
	 */
	public void setTaskAlterRequestDAO(TaskAlterRequestDAO taskAlterRequestDAO) {
		this.taskAlterRequestDAO = taskAlterRequestDAO;

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.shufe.service.course.TaskAlterRequestService#getTaskAlterRequest(java.lang.String)
	 */
	public TaskAlterRequest getTaskAlterRequest(Long id) {
		return taskAlterRequestDAO.getTaskAlterRequest(id);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.shufe.service.course.TaskAlterRequestService#saveTaskAlterRequest(com.shufe.model.course.TaskAlterRequest)
	 */
	public void save(TaskAlterRequest taskAlterRequest) {
		if (!taskAlterRequest.isPO()) {
			taskAlterRequest.setAvailability("Y");
			taskAlterRequest.setTime(new Date(System.currentTimeMillis()));
		}
		utilService.saveOrUpdate(taskAlterRequest);
	}

	public List getTaskAlterRequests(TeachCalendar calendar, Teacher teacher) {
		return taskAlterRequestDAO.getTaskAlterRequests(calendar, teacher);
	}


}
