//$Id: AwardHibernateDAO.java,v 1.1 2007-3-5 17:40:23 Administrator Exp $
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
 * chenweixiong              2007-3-5         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.study.hibernate;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.study.AwardDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.study.StudyAward;

public class AwardHibernateDAO extends BasicHibernateDAO implements AwardDAO  {

	private Criteria getExampleAward(StudyAward award, String departIdSeq, String stdTypeIdSeq){
		Criteria criteria = getSession().createCriteria(StudyAward.class);
		List criterions = CriterionUtils.getEntityCriterions(award);
		criteria.createAlias("student", "student");
		if(StringUtils.isNotBlank(departIdSeq)){
			criteria.add(Restrictions.in("student.department.id", SeqStringUtil.transformToLong(departIdSeq)));
		}
		if(StringUtils.isNotBlank(stdTypeIdSeq)){
			criteria.add(Restrictions.in("student.type.id", SeqStringUtil.transformToLong(stdTypeIdSeq)));
		}
		for (Iterator iter = criterions.iterator(); iter.hasNext();) {
			Criterion element = (Criterion) iter.next();
			criteria.add(element);
		}
		return criteria;
	}
	
	public List getAwards(StudyAward award, String departIdSeq, String stdTypeIdSeq) {
		Criteria criteria = getExampleAward(award, departIdSeq, stdTypeIdSeq);
		return criteria.list();
	}

	public Pagination getPagiAward(StudyAward award, String departIdSeq, String stdTypeIdSeq, int pageNo, int pageSize) {
		Criteria criteria = getExampleAward(award, departIdSeq, stdTypeIdSeq);
		return dynaSearch(criteria, pageNo, pageSize);
	}

	public List getPropertyAward(StudyAward award, String departIdSeq, String stdTypeIdSeq, String properties) {
		Criteria criteria = getExampleAward(award, departIdSeq,
				stdTypeIdSeq);
		ProjectionList projectionList = Projections.projectionList();
		String[] property = StringUtils.split(properties,",");
		for (int i = 0; i < property.length; i++) {
			projectionList.add(Projections.groupProperty(property[i]));
		}
		criteria.setProjection(projectionList);
		return criteria.list();
	}

	
}
