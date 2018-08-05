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
 * chaostone             2006-8-10            Created
 *  
 ********************************************************************************/
package com.shufe.dao.degree.subject.hibernate;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.subject.Level1SubjectDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.subject.Level1Subject;

public class Level1SubjectDAOHibernate extends BasicHibernateDAO implements
		Level1SubjectDAO {

	public Pagination getLevel1Subject(Level1Subject subject, int pageNo,
			int pageSize) {

		Criteria criteria = getSession().createCriteria(Level1Subject.class);
		if (null != subject && null != subject.getCategory()) {
			List categoryCriterions = CriterionUtils.getEntityCriterions(
					"category.", subject.getCategory());
			if (!categoryCriterions.isEmpty()) {
				Criteria categoryCriteria = criteria.createCriteria("category",
						"category");
				for (Iterator iter = categoryCriterions.iterator(); iter
						.hasNext();) {
					categoryCriteria.add((Criterion) iter.next());
				}
			}
		}
		List criterions = CriterionUtils.getEntityCriterions(subject);
		for (Iterator iter = criterions.iterator(); iter.hasNext();) {
			Criterion criterion = (Criterion) iter.next();
			criteria.add(criterion);
		}
		return dynaSearch(criteria, pageNo, pageSize);
	}

}
