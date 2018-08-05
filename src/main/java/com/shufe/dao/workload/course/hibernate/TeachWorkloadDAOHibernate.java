//$Id: TeachWorkloadDAOHibernate.java,v 1.17 2007/01/15 09:22:10 cwx Exp $
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
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.course.hibernate;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.dao.workload.course.TeachWorkloadDAO;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.course.TeachWorkload;

/**
 * @author Administrator
 *
 */
public class TeachWorkloadDAOHibernate extends BasicHibernateDAO implements
		TeachWorkloadDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.workload.TeachWorkloadDAO12#getTeachWorkloads(java.lang.Long,
	 *      java.lang.Long)
	 */
	public List getTeachWorkloads(Long teacherId, Long teachTaskId) {
		Criteria criteria = getSession().createCriteria(TeachWorkload.class);
		if (teacherId != null) {
			criteria.add(Restrictions
					.eq("teacherInfo.teacher.id", teacherId));
		}
		if (teachTaskId != null) {
			criteria.add(Restrictions.eq("teachTask.id", teachTaskId));
		}
		return criteria.list();
	}

	/**
	 * 根据教学统计 以及对因的权限得到 一个这个对象的criteia
	 * 
	 * @param teachWorkload
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @return
	 */
	private Criteria getTeachWorkloads(TeachWorkload teachWorkload,
			String stdTypeIdSeq, String departIdSeq, String calendarIdSeq) {
		Criteria criteria = getSession().createCriteria(TeachWorkload.class);
		if(StringUtils.isNotBlank(stdTypeIdSeq)){
			criteria.add(Restrictions.in("studentType.id", SeqStringUtil
					.transformToLong(stdTypeIdSeq)));
			}
		if(StringUtils.isNotBlank(departIdSeq)){
		criteria.add(Restrictions.in("college.id", SeqStringUtil
				.transformToLong(departIdSeq)));
		}
		if (StringUtils.isNotBlank(calendarIdSeq)) {
			criteria.add(Restrictions.in("teachCalendar.id", SeqStringUtil
					.transformToLong(calendarIdSeq)));
		}
		List criterions = CriterionUtils.getEntityCriterions(teachWorkload);
		for (Iterator iter = criterions.iterator(); iter.hasNext();)
			criteria.add((Criterion) iter.next());
		return criteria;
	}

	/**
	 * 根据条件得到所有的教师确认并且院系确认的教学任务
	 * 
	 * @param teachWorkload
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @return
	 */
	public List getTeachWorkloadAffirmedTaskIds(TeachWorkload teachWorkload,
			String stdTypeIdSeq, String departIdSeq, String calendarIdSeq) {
		Criteria criteria = getTeachWorkloads(teachWorkload, stdTypeIdSeq,
				departIdSeq, calendarIdSeq);
		criteria.add(Restrictions.or(Restrictions.eq("teacherAffirm",
				Boolean.TRUE), Restrictions.eq("collegeAffirm", Boolean.TRUE)));
		criteria.setProjection(Projections.property("teachTask.id"));
		return criteria.list();
	}


	/**根据条件得到id串
	 * @see com.shufe.dao.workload.course.TeachWorkloadDAO#getIdListBycondition(com.shufe.model.workload.course.TeachWorkload, java.lang.String, java.lang.String)
	 */
	public List getIdListBycondition(TeachWorkload teachWorkload,
			String departmentSeq, String stdTypeSeq) {
		Criteria criteria = getTeachWorkloads(teachWorkload, stdTypeSeq,
				departmentSeq, null);
		criteria.setProjection(Projections.property("id"));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
	/**
	 * 
	 * @see com.shufe.dao.workload.TeachWorkloadDAO12#getExampleTeachWorkloads(com.shufe.model.workload.course.TeachWorkload,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public Criteria getExampleTeachWorkloads(TeachWorkload teachWorkload,
			String stdTypeIdSeq, String departmentSeq, String calendarIdSeq,
			String age1, String age2) {
		Criteria criteria = getTeachWorkloads(teachWorkload, stdTypeIdSeq,
				departmentSeq, calendarIdSeq);
		if (StringUtils.isNotBlank(teachWorkload.getTeacherInfo()
				.getTeacher().getCode())) {
			criteria.createAlias("teacherInfo.teacher", "teacher");
			criteria.add(Restrictions.like("teacher.code", teachWorkload
					.getTeacherInfo().getTeacher().getCode(),
					MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotEmpty(age1)) {
			Integer age11 = Integer.valueOf(age1);
			criteria.add(Restrictions.ge("teacherInfo.teacherAge", age11));
		}
		if (StringUtils.isNotEmpty(age2)) {
			Integer age22 = Integer.valueOf(age2);
			criteria.add(Restrictions.le("teacherInfo.teacherAge", age22));
		}
		criteria.createAlias("teachCalendar", "teachCalendar");
		if (teachWorkload.getTeachCalendar() != null) {
			if (StringUtils.isNotEmpty(teachWorkload.getTeachCalendar()
					.getYear())) {
				criteria.add(Restrictions.eq("teachCalendar.year",
						teachWorkload.getTeachCalendar().getYear()));
			}
			if (StringUtils.isNotEmpty(teachWorkload.getTeachCalendar()
					.getTerm())) {
				criteria.add(Restrictions.eq("teachCalendar.term",
						teachWorkload.getTeachCalendar().getTerm()));
			}
		}
		criteria.createAlias("college", "college");
		criteria.addOrder(Order.desc("teachCalendar.year")).addOrder(
				Order.asc("teachCalendar.term")).addOrder(
				Order.asc("college.name")).addOrder(
				Order.asc("teacherInfo.teacherName")).addOrder(
				Order.asc("teacherInfo.teacherAge"));
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.workload.TeachWorkloadDAO12#getTeachWorkloads(java.lang.Long,
	 *      java.lang.Long, java.lang.String, java.lang.String)
	 */
	public List getTeachWorkloads(Long teacherId, Long studentTypeId,
			String year, String term) {
		Criteria criteria = this.getSession().createCriteria(
				TeachWorkload.class);
		if (teacherId != null) {
			criteria.add(Restrictions
					.eq("teacherInfo.teacher.id", teacherId));
		}
		if (studentTypeId != null) {
			criteria.add(Restrictions.eq("studentType.id", studentTypeId));
		}
		if (StringUtils.isNotEmpty(year)) {
			criteria.add(Restrictions.eq("teacherInfo.teachCalendar.year",
					year));
		}
		if (StringUtils.isNotEmpty(term)) {
			criteria.add(Restrictions.eq("teacherInfo.teachCalendar.term",
					term));
		}
		return criteria.list();
	}

	/**
	 * 
	 * @see com.shufe.dao.workload.TeachWorkloadDAO12#affirmType(java.lang.String,
	 *      java.lang.String, boolean)
	 *      3885由来：
	 *      1-9 1 10-99 2 100-999 3 一共 1*9+2*90+3*900=2889
	 *      还有999个逗号 一共2889+999=3898  再减去4位共 3894
	 */
	public void affirmType(String affirmType, String teachWorkloadIds,
			boolean b) {
		int maxTop =0;
		int stringLength = teachWorkloadIds.length();
		int splitlength =teachWorkloadIds.split(",").length;
		if(StringUtils.isNotEmpty(teachWorkloadIds)){
			maxTop=stringLength/splitlength;
		}
		while (teachWorkloadIds.split(",").length >= 1000) {
			String tempseq = teachWorkloadIds.substring(0, teachWorkloadIds
					.indexOf(",", maxTop*500));
			batchForAffirmType(affirmType, tempseq, b);
			teachWorkloadIds = teachWorkloadIds.substring(teachWorkloadIds
					.indexOf(",", maxTop*500) + 1);
		}
		batchForAffirmType(affirmType, teachWorkloadIds, b);
	}
	public void batchForAffirmType(String affirmType, String teachWorkloadIds,
			boolean b) {
		String strHql = "update TeachWorkload set " + affirmType + "=" + b
				+ " where id in (" + teachWorkloadIds + ")";
		Query queryHql = getSession().createQuery(strHql);
		if (StringUtils.isNotEmpty(teachWorkloadIds)) {
			queryHql.executeUpdate();
		}
	}

	/**
	 * cwx 修改与9月4日 主要问题： 原来实现是通过把所有的对象查询出来一个一个更新容易出现内存溢出的问题 现在实现通过一条sql语句的拼装来实现
	 * 避免了内存溢出 而且看起来比较简单
	 * 
	 * @see com.shufe.dao.workload.TeachWorkloadDAO12#updateAffirmAll(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	public void updateAffirmAll(String departmentIds, String typeName, boolean b) {
		String updateHql = "update  TeachWorkload set " + typeName + "=" + b;
		if (StringUtils.isNotEmpty(departmentIds)) {
			updateHql += " where teacherInfo.teachDepart.id in("
					+ departmentIds + ")";
			Query queryHql = getSession().createQuery(updateHql);
			queryHql.executeUpdate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.workload.TeachWorkloadDAO12#getTeachCalendarOfTeacher(java.util.Collection)
	 */
	public List getTeachCalendarOfTeacher(Collection studentTypeColl) {
		Criteria criteria = getSession().createCriteria(TeachCalendar.class);
		if (null != studentTypeColl && studentTypeColl.size() > 0) {
			criteria.add(Restrictions.in("studentType", studentTypeColl));
		} else {
			return Collections.EMPTY_LIST;
		}
		return criteria.list();
	}

	/**
	 * @see com.shufe.dao.workload.TeachWorkloadDAO12#getTeachTaskOfTeacherByChargeDepart(java.util.Collection,
	 *      java.lang.String)
	 */
	public List getTasksOfTeacherByChargeDepart(String stdTypeSeq,
			String departIdSeq, String teachCalendarIdSeq) {
		if (StringUtils.isBlank(stdTypeSeq) || StringUtils.isBlank(departIdSeq)
				|| StringUtils.isBlank(teachCalendarIdSeq)) {
			return Collections.EMPTY_LIST;
		}
		Criteria criteria = getSession().createCriteria(TeachTask.class);
		criteria.add(Restrictions.in("calendar.id", SeqStringUtil
				.transformToLong(teachCalendarIdSeq)));
		criteria.add(Restrictions.in("arrangeInfo.teachDepart.id",
				SeqStringUtil.transformToLong(departIdSeq)));
		criteria.add(Restrictions.in("teachClass.stdType.id", SeqStringUtil
				.transformToLong(stdTypeSeq)));
		return criteria.list();
	}

	/**
	 * 根据条件批量删除教学工作量
	 * 
	 * @see com.shufe.dao.workload.course.TeachWorkloadDAO#batchDeleteTeachWorkload(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void batchDeleteTeachWorkload(String departmentSeq,
			String stdTypeSeq, String teachCalendarIds) {
		String stringhql = "delete from TeachWorkload where ";
		if (StringUtils.isNotEmpty(departmentSeq)) {
			stringhql += " college in(" + departmentSeq + ") and";
		}
		if (StringUtils.isNotEmpty(stdTypeSeq)) {
			stringhql += " studentType in(" + stdTypeSeq + ") and";
		}
		if (StringUtils.isNotEmpty(teachCalendarIds)) {
			stringhql += " teachCalendar in(" + teachCalendarIds + ")";
		}
		Query query = getSession().createQuery(stringhql);
		if (StringUtils.isNotEmpty(departmentSeq)
				&& StringUtils.isNotEmpty(stdTypeSeq)) {
			query.executeUpdate();
		}
	}

	/**
	 * 根据条件批量执行删除语句 条件都是
	 * 
	 * @param conditionIns
	 */
	public void batchDeleteBySeq(Map conditionsSeq) {
		String stringhql = "delete from TeachWorkload where ";
		boolean flag = true;
		Set keySet = conditionsSeq.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			if (StringUtils.isEmpty((String) conditionsSeq.get(element))) {
				flag = false;
				break;
			}
			if (iter.hasNext())
				stringhql += element + " in(" + conditionsSeq.get(element)
						+ ") and ";
			else
				stringhql += element + " in(" + conditionsSeq.get(element)
						+ ")";
		}
		if (flag) {
			Query queryStr = getSession().createQuery(stringhql);
			queryStr.executeUpdate();
		}
	}

	/**
	 * @param taskCollection
	 */
	public void batchDeleteByTaskCollection(Collection taskCollection) {
		if (null != taskCollection && taskCollection.size() > 0) {
			List taskIds = EntityUtils.extractIds(taskCollection);
			String stringhql = "delete from TeachWorkload where teachTask.id in (:taskIds)";

			Query query = getSession().createQuery(stringhql);

			int from = 0;
			if (taskIds.size() > 500) {
				while (from < taskIds.size()) {
					int end = from + 500;
					if (end > taskIds.size())
						end = taskIds.size();
					List newTaskIds = taskIds.subList(from, end);
					query.setParameterList("taskIds", newTaskIds);
					query.executeUpdate();
					from += 500;
				}
			} else {

				query.setParameterList("taskIds", EntityUtils
						.extractIds(taskCollection));
				query.executeUpdate();
			}

		}
	}
}
