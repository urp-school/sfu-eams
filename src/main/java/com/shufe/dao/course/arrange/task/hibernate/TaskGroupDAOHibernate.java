//$Id: TaskGroupDAOHibernate.java,v 1.2 2006/11/10 01:10:42 duanth Exp $
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
 * chaostone             2005-11-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.arrange.task.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.arrange.task.TaskGroupDAO;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 课程组数据存取实现
 * 
 * @author chaostone 2005-11-18
 */
public class TaskGroupDAOHibernate extends BasicHibernateDAO implements
		TaskGroupDAO {

	/**
	 * @see com.shufe.dao.course.arrange.task.TaskGroupDAO#getTaskGroup(java.io.Serializable)
	 */
	public TaskGroup getTaskGroup(Serializable id) {
		TaskGroup group = (TaskGroup) getHibernateTemplate().get(
				TaskGroup.class, id);
		if (null == group)
			group = (TaskGroup) load(TaskGroup.class, id);
		return group;
	}

	/**
	 * @see com.shufe.dao.course.arrange.task.TaskGroupDAO#getTaskGroups(java.lang.String[])
	 */
	public List getTaskGroups(Long[] groupIds) {
		Criteria criteria = getSession().createCriteria(TaskGroup.class);
		criteria.add(Restrictions.in("id", groupIds));
		return criteria.list();
	}

	/**
	 * @see com.shufe.dao.course.arrange.task.TaskGroupDAO#getTaskGroups(java.lang.String[],
	 *      com.shufe.model.system.calendar.TeachCalendar)
	 */
	public List getTaskGroups(Long[] teachDepartIds, TeachCalendar calendar) {
		Map params = new HashMap();
		params.put("teachDepartIds", teachDepartIds);
		params.put("calendarId", calendar.getId());
		return find("getTaskGroups", params);
	}

	/**
	 * @see com.shufe.dao.course.arrange.task.TaskGroupDAO#getTaskCountOfGroup(Collection)
	 */
	public Integer getTaskCountOfGroup(Collection groups) {
		Map params = new HashMap();
		params.put("groups", groups);
		return new Integer(((Number) find("getDirectTaskCountOfGroups", params)
				.iterator().next()).intValue());
	}

	/**
	 * I want to user Criteria performance a dynamic query. but hibernate doen't
	 * support distint on criteria Now!! so I assemble hql manually.
	 */
	public Pagination getTaskGroups(TaskGroup group, Long[] stdTypeIds,
			Long[] teachDepartIds, TeachCalendar calendar, int pageNo,
			int pageSize, Boolean isCompleted) {
		String hql = "select distinct taskGroup from TaskGroup"
				+ " as taskGroup join taskGroup.directTasks  as task "
				+ " where task.calendar.id =:calendarId ";

		Map params = new HashMap();
		params.put("calendarId", calendar.getId());
		if (null != stdTypeIds && stdTypeIds.length > 0) {
			hql += " and task.teachClass.stdType.id in (:stdTypes)";
			params.put("stdTypes", stdTypeIds);
		}
		if (null != teachDepartIds && teachDepartIds.length > 0) {
			hql += " and task.arrangeInfo.teachDepart.id in (:teachDepartIds)";
			params.put("teachDepartIds", teachDepartIds);
		}

		if (null != group) {
			if (null != group.getId() && group.getId().intValue() != 0) {
				hql += " and taskGroup.id = :groupId";
				params.put("groupId", group.getId());
			}
			if (StringUtils.isNotEmpty(group.getName())) {
				hql += " and taskGroup.name like :groupName";
				params.put("groupName", "%" + group.getName() + "%");
			}
			if (null != group.getPriority()
					&& group.getPriority().intValue() != 0) {
				hql += " and taskGroup.priority <= :priority";
				params.put("priority", group.getPriority());
			}
			if (null != group.getIsSameTime()) {
				hql += " and taskGroup.isSameTime = :isSameTime";
				params.put("isSameTime", group.getIsSameTime());
			}
		}
		if (null != isCompleted) {
			hql += " and task.arrangeInfo.isArrangeComplete = :isCompleted";
			params.put("isCompleted", isCompleted);
		}
		hql += " order by taskGroup.priority desc, taskGroup.name asc";

		return search(hql, params, pageNo, pageSize);
	}

	/**
	 * @see com.shufe.dao.course.arrange.task.TaskGroupDAO#removeTaskGroup(java.io.Serializable)
	 */
	public void removeTaskGroup(Serializable id) {
		remove(TaskGroup.class, id);

	}

	/**
	 * @see com.shufe.dao.course.arrange.task.TaskGroupDAO#removeTaskGroup(com.shufe.model.course.arrange.task.TaskGroup)
	 */
	public void removeTaskGroup(TaskGroup group) {
		remove(group);
	}

	/**
	 * @see com.shufe.dao.course.arrange.task.TaskGroupDAO#saveTaskGroup(com.shufe.model.course.arrange.task.TaskGroup)
	 */
	public void saveTaskGroup(TaskGroup group) {
		save(group);
	}

}
