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
 * chaostone             2006-9-1            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.plan.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.plan.TeachPlanStatDAO;
import com.shufe.model.system.security.DataRealm;

public class TeachPlanStatDAOHibernate extends BasicHibernateDAO implements
		TeachPlanStatDAO {

	public List statByDepart(DataRealm realm, String enrollTurn) {
		String statHql = "select new com.shufe.service.util.stat.CountItem(count(*), plan.department) "
				+ " from TeachPlan as plan"
				+ " where plan.enrollTurn=  :enrollTurn "
				+ " and plan.department.id in (:departIds)"
				+ " and plan.stdType.id in (:stdTypeIds)"
				+ " group by  plan.department.id";
		Query query = getSession().createQuery(statHql);
		query.setParameterList("departIds", SeqStringUtil.transformToLong(realm
				.getDepartmentIdSeq()));
		query.setParameterList("stdTypeIds", SeqStringUtil
				.transformToLong(realm.getStudentTypeIdSeq()));
		query.setParameter("enrollTurn", enrollTurn);

		return query.list();
	}

	public List statByStdType(DataRealm realm, String enrollTurn) {

		String statHql = "select new com.shufe.service.util.stat.CountItem(count(*), plan.stdType) "
				+ " from TeachPlan as plan"
				+ " where plan.enrollTurn=  :enrollTurn "
				+ " and plan.department.id in (:departIds)"
				+ " and plan.stdType.id in (:stdTypeIds)"
				+ " group by  plan.stdType.id";
		Query query = getSession().createQuery(statHql);
		query.setParameterList("departIds", SeqStringUtil.transformToLong(realm
				.getDepartmentIdSeq()));
		query.setParameterList("stdTypeIds", SeqStringUtil
				.transformToLong(realm.getStudentTypeIdSeq()));
		query.setParameter("enrollTurn", enrollTurn);

		return query.list();
	}

	public List getEnrollTurns(DataRealm realm) {
		String statHql = "select distinct plan.enrollTurn from TeachPlan as plan "
				+ " where plan.department.id in (:departIds) "
				+ " and plan.stdType.id in(:stdTypeIds)"
				+ " order by  plan.enrollTurn desc";
		Query query = getSession().createQuery(statHql);
		query.setParameterList("departIds", SeqStringUtil.transformToLong(realm
				.getDepartmentIdSeq()));
		query.setParameterList("stdTypeIds", SeqStringUtil
				.transformToLong(realm.getStudentTypeIdSeq()));
		return query.list();

	}

}
