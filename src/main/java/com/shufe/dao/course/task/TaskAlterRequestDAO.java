//$Id: TaskAlterRequestDAO.java,v 1.1 2006/08/02 00:53:13 duanth Exp $
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
 * yangdong             2005-11-9         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.task;

import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.task.TaskAlterRequest;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author Administrator
 * 
 */
public interface TaskAlterRequestDAO extends BasicDAO {

	/**
	 * 根据id查找对应的教学任务更改
	 * 
	 * @param taskAlterRequestId
	 * @return
	 */
	public TaskAlterRequest getTaskAlterRequest(Long taskAlterRequestId);

	/**
	 * 查询教师的教学变更列表
	 * 
	 * @param calendar
	 * @param teacher
	 * @return
	 */
	public List getTaskAlterRequests(TeachCalendar calendar, Teacher teacher);

}
