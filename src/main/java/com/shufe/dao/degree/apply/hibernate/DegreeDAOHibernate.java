//$Id: DegreeDAOHibernate.java,v 1.2 2006/11/04 10:01:20 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-10-26         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.apply.hibernate;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.apply.DegreeDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.apply.DegreeApply;

public class DegreeDAOHibernate extends BasicHibernateDAO implements DegreeDAO {

	private Criteria getDegreeApplys(DegreeApply degreeApply,
			Long[] departmentId, Long[] stdTypeId) {
		Criteria criteria = getSession().createCriteria(degreeApply.getClass());
		List criterions = CriterionUtils.getEntityCriterions(degreeApply);
		if (null != degreeApply.getStudent()
				&& (null == degreeApply.getStudent().getId() || new Long(0)
						.equals(degreeApply.getStudent().getId()))) {
			List stdCriterions = CriterionUtils.getEntityCriterions("student.",
					degreeApply.getStudent());
			if (null != departmentId && departmentId.length > 0) {
				criteria.add(Restrictions.in("student.department.id",
						departmentId));
			}
			if (null != stdTypeId && stdTypeId.length > 0) {
				criteria.add(Restrictions.in("student.type.id", stdTypeId));
			}
			if ((null != stdTypeId && stdTypeId.length > 0)
					|| null != departmentId && departmentId.length > 0
					|| stdCriterions.size() > 0) {
				criteria.createAlias("student", "student");
				criterions.addAll(stdCriterions);
			}
		}
		for (Iterator iter = criterions.iterator(); iter.hasNext();) {
			Criterion element = (Criterion) iter.next();
			criteria.add(element);
		}
		return criteria;
	}
	
	/**
	 * @param degreeApply
	 * @param departmentIds
	 * @param stdTypeIds
	 * @return
	 */
	public List getDegreeApplyList(DegreeApply degreeApply,
			Long[] departmentIds, Long[] stdTypeIds) {
		return getDegreeApplys(degreeApply,departmentIds,stdTypeIds).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.degree.DegreeDAO#getPaginas(com.shufe.model.degree.DegreeApply,
	 *      int, int)
	 */
	public Pagination getPaginas(DegreeApply degreeApply, Long[] stdTypeIdSeq,
			Long[] departmentSeq, int pageNo, int pageSize) {
		return dynaSearch(getDegreeApplys(degreeApply,departmentSeq, stdTypeIdSeq), pageNo,
				pageSize);
	}

	/** 确认学位申请
	 * @see com.shufe.dao.degree.apply.DegreeDAO#affirmById(java.lang.String, java.lang.Boolean)
	 */
	public void affirmById(String idSeq, Boolean affirm) {
		String hqlString = "update DegreeApply set isAgree=" + affirm
				+ " where id in(" + idSeq + ")";
		if (StringUtils.isNotEmpty(idSeq)) {
			Query query = getSession().createQuery(hqlString);
			query.executeUpdate();
		}
	}

}
