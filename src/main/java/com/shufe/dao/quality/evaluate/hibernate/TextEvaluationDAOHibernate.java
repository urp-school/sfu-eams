//$Id: TextEvaluationDAOHibernate.java,v 1.8 2007/01/09 07:55:33 cwx Exp $
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
 * chenweixiong              2005-10-21         Created
 *  
 ********************************************************************************/

package com.shufe.dao.quality.evaluate.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.quality.evaluate.TextEvaluationDAO;
import com.shufe.model.quality.evaluate.TextEvaluation;

/**
 * @author hj 2005-10-21 TextEvaluationDAO.java has been created
 */
public class TextEvaluationDAOHibernate extends BasicHibernateDAO implements
		TextEvaluationDAO {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.evaluate.hibernate.Tdf#getTextEvaluationsOfStudent(java.lang.String)
	 */
	public List getTextEvaluatesOfStd(Long studentId) {
		Criteria criteria = getSession().createCriteria(TextEvaluation.class);
		if (NotZeroNumberPredicate.INSTANCE.evaluate(studentId)) {
			criteria.add(Restrictions.eq("std.id", studentId));
		}
		criteria.createAlias("calendar", "calendar");
		criteria.addOrder(Order.desc("calendar.year")).addOrder(
				Order.asc("calendar.term"));
		return criteria.list();
	}

	public void updateForAffirm(Long[] textEvaluationIds, Boolean isAffirm) {
		this
				.getSession()
				.createQuery(
						"update TextEvaluation textEvaluation set textEvaluation.isAffirm=:isAffirm where  textEvaluation.id in (:textEvaluationIds)")
				.setBoolean("isAffirm", isAffirm.booleanValue())
				.setParameterList("textEvaluationIds", textEvaluationIds)
				.executeUpdate();
	}
}
