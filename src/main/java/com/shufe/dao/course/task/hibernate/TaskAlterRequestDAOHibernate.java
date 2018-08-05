//$Id: TaskAlterRequestDAOHibernate.java,v 1.1 2006/08/02 00:52:57 duanth Exp $
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
 * chaostone             2005-10-25         Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.task.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.task.TaskAlterRequestDAO;
import com.shufe.model.course.task.TaskAlterRequest;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author yangdong
 * 
 */
public class TaskAlterRequestDAOHibernate extends BasicHibernateDAO implements
		TaskAlterRequestDAO {
	/**
	 * @see com.shufe.dao.course.task.TaskAlterRequestDAO#getTaskAlterRequestsByCategory(java.lang.String,
	 *      com.shufe.dao.course.TaskAlterRequestFilterCategory,
	 *      com.shufe.model.system.calendar.TeachCalendar)
	 */
	public List getTaskAlterRequests(TeachCalendar calendar, Teacher teacher) {
		String hql = "select request from TaskAlterRequest request where request.task.calendar.id="
				+ calendar.getId()
				+ " and request.teacher.id="
				+ teacher.getId();
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public TaskAlterRequest getTaskAlterRequest(Long taskAlterRequestId) {
		return (TaskAlterRequest) get(TaskAlterRequest.class,
				taskAlterRequestId);
	}

}
