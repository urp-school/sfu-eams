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
 * chaostone             2005-11-9         Created
 *  
 ********************************************************************************/
package com.shufe.service.course.task;

import java.util.List;

import com.shufe.dao.course.task.TaskAlterRequestDAO;
import com.shufe.model.course.task.TaskAlterRequest;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

public interface TaskAlterReqestService {

	/**
	 * 设置教学任务更改对象
	 * 
	 * @param taskAlterRequestDAO
	 */
	public void setTaskAlterRequestDAO(TaskAlterRequestDAO taskAlterRequestDAO);

	/**
	 * 根据指定的教学任务更改代码，返回教学任务更改详细信息.
	 * 
	 * @param id
	 * @return
	 */
	public TaskAlterRequest getTaskAlterRequest(Long id);
	/**
	 * 保存新的教学任务更改申请
	 * 
	 * @param taskAlterRequest
	 */
	public void save(TaskAlterRequest taskAlterRequest);

	public List getTaskAlterRequests(TeachCalendar calendar, Teacher teacher);

}
