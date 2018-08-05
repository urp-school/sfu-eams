//$Id: InstructWorkloadDAOHibernate.java,v 1.3 2006/08/25 06:48:40 cwx Exp $
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

package com.shufe.dao.workload.instruct.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.dao.workload.instruct.InstructWorkloadDAO;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.model.workload.instruct.InstructWorkload;

public class InstructWorkloadDAOHibernate extends BasicHibernateDAO implements
		InstructWorkloadDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.workload.InstructWorkloadDAO12#getExampleInstructWorkloads(com.shufe.model.workload.InstructWorkload,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public Criteria getExampleInstructWorkloads(
			InstructWorkload instructWorkload, String departmentIds,
			String age1, String age2) {
		Criteria criteria = this.getSession().createCriteria(
				InstructWorkload.class);
		Long[] departmentId = SeqStringUtil.transformToLong(departmentIds);
		criteria.add(Restrictions
				.in("teacherInfo.teachDepart.id", departmentId));
		// 添加所有不为空的条件
		List criterions = CriterionUtils.getEntityCriterions(instructWorkload);
		for (Iterator iter = criterions.iterator(); iter.hasNext();)
			criteria.add((Criterion) iter.next());
		// 年龄的条件
		if (StringUtils.isNotEmpty(age1)) {
			Integer age11 = Integer.valueOf(age1);
			criteria.add(Restrictions.ge("teacherInfo.teacherAge", age11));
		}
		if (StringUtils.isNotEmpty(age2)) {
			Integer age22 = Integer.valueOf(age2);
			criteria.add(Restrictions.le("teacherInfo.teacherAge", age22));
		}
		// 添加查询学年度学期的条件
		criteria.createAlias("teachCalendar", "teachCalendar");
		if (instructWorkload.getTeachCalendar() != null) {
			if (StringUtils.isNotEmpty(instructWorkload.getTeachCalendar()
					.getYear())) {
				criteria.add(Restrictions.eq("teachCalendar.year",
						instructWorkload.getTeachCalendar().getYear()));
			}
			if (StringUtils.isNotEmpty(instructWorkload.getTeachCalendar()
					.getTerm())) {
				criteria.add(Restrictions.eq("teachCalendar.term",
						instructWorkload.getTeachCalendar().getTerm()));
			}
		}
		// 排序
		criteria.addOrder(Order.desc("teachCalendar.year")).addOrder(
				Order.asc("teacherInfo.teachDepart.id")).addOrder(
				Order.asc("teacherInfo.teacherName"));
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.workload.InstructWorkloadDAO12#affirmType(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	public void affirmType(String affirmType, String teachWorkloadIds, boolean b) {
		String strHql = "update InstructWorkload set " + affirmType + "=" + b
				+ " where id in (" + teachWorkloadIds + ")";
		if (StringUtils.isNotBlank(teachWorkloadIds)) {
			Query queryHql = getSession().createQuery(strHql);
			queryHql.executeUpdate();
		}
	}

	/**
	 * @see com.shufe.dao.workload.InstructWorkloadDAO12#updateAffirmAll(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	public void updateAffirmAll(String stdTypeIds, String typeName, boolean b) {
		String updateHql = "update InstructWorkload set " + typeName + "=" + b
				+ " where studentType.id in(" + stdTypeIds + ")";
		if (StringUtils.isNotBlank(stdTypeIds)) {
			Query query = getSession().createQuery(updateHql);
			query.executeUpdate();
		}
	}

	/**
	 * @see com.shufe.dao.workload.instruct.InstructWorkloadDAO#deleteByCondition(java.lang.String,
	 *      java.lang.Long)
	 */
	public int batchRemove(TeachCalendar calendar, DataRealm realm,
			String modulusType) {
		String deleteHql = "delete from InstructWorkload where id"
				+ " in(select id from InstructWorkload w where w.studentType.id in(:stdTypeIds)"
				+ " and w.teacherInfo.teachDepart.id in(:departIds) and w.teachCalendar.id=:calendarId"
				+ " and w.modulus.itemType=:modulusType)";
		Query query = getSession().createQuery(deleteHql);
		query.setParameterList("stdTypeIds", SeqStringUtil
				.transformToLong(realm.getStudentTypeIdSeq()));
		query.setParameterList("departIds", SeqStringUtil.transformToLong(realm
				.getDepartmentIdSeq()));
		query.setParameter("modulusType", modulusType);
		query.setParameter("calendarId", calendar.getId());
		return query.executeUpdate();
	}
}
