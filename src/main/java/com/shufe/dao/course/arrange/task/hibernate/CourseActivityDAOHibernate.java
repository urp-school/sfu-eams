//$Id: CourseActivityDAOHibernate.java,v 1.1 2006/11/09 11:22:02 duanth Exp $
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

package com.shufe.dao.course.arrange.task.hibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.ekingstar.commons.model.EntityUtils;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.arrange.task.CourseActivityDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.model.course.task.TeachTask;

public class CourseActivityDAOHibernate extends BasicHibernateDAO implements
		CourseActivityDAO {

	private TeachTaskDAO teachTaskDAO;

	/**
	 * @see com.shufe.dao.course.arrange.task.CourseActivityDAO#saveActivities(java.util.Collection)
	 */
	public void saveActivities(Collection tasks) {
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			this.update(task);
		}
	}

	public void removeActivities(Collection tasks) {
		String deleteHQL = "delete from CourseActivity where  task in (:tasks) ";
		Query query = getSession().createQuery(deleteHQL);
		query.setParameterList("tasks", tasks);
		query.executeUpdate();
		List taskIdList = EntityUtils.extractIds(tasks);
		Long[] taskIds = new Long[taskIdList.size()];
		taskIdList.toArray(taskIds);
		teachTaskDAO.updateTeachTaskByIds("arrangeInfo.isArrangeComplete",
				Boolean.FALSE, taskIds);
		for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
			getSession().refresh(iterator.next());
		}
	}

	public void removeActivities(Long[] taskIds) {
		String deleteHQL = "delete from CourseActivity where task.id in (:taskIds)";
		Query query = getSession().createQuery(deleteHQL);
		query.setParameterList("taskIds", taskIds);
		query.executeUpdate();
		teachTaskDAO.updateTeachTaskByIds("arrangeInfo.isArrangeComplete",
				Boolean.FALSE, taskIds);
		for (int i = 0; i < taskIds.length; i++) {
			getSession().refresh(teachTaskDAO.getTeachTask(taskIds[i]));
		}
	}

	public TeachTaskDAO getTeachTaskDAO() {
		return teachTaskDAO;
	}

	public void setTeachTaskDAO(TeachTaskDAO teachTaskDAO) {
		this.teachTaskDAO = teachTaskDAO;
	}

}
