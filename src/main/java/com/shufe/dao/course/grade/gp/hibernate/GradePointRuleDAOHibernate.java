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
 * chaostone             2007-1-8            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.grade.gp.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.grade.gp.GradePointRuleDAO;
import com.shufe.model.course.grade.gp.GradePointRule;

public class GradePointRuleDAOHibernate extends BasicHibernateDAO implements
		GradePointRuleDAO {

	public GradePointRule getGradePointRule(StudentType stdType,
			MarkStyle markStyle) {
		String hql = "from GradePointRule rule where (rule.stdType=:stdType or rule.stdType is null) and rule.markStyle=:markStyle";
		Query query = getSession().createQuery(hql);
		query.setParameter("stdType", stdType);
		query.setParameter("markStyle", markStyle);
		query.setCacheable(true);
		List rs = query.list();
		if (!rs.isEmpty()) {
			return (GradePointRule) rs.get(0);
		} else {
			return null;
		}
	}

	public GradePointRule getDefaultGradePointRule(MarkStyle markStyle) {
		String hql = "from GradePointRule rule where rule.stdType is null and rule.markStyle=:markStyle";
		Query query = getSession().createQuery(hql);
		query.setParameter("markStyle", markStyle);
		query.setCacheable(true);
		List rs = query.list();
		if (!rs.isEmpty()) {
			return (GradePointRule) rs.get(0);
		} else {
			return null;
		}
	}

}
