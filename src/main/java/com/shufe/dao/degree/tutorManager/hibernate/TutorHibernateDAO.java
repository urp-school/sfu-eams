//$Id: TutorHibernateDAO.java,v 1.3 2007/01/19 09:34:31 cwx Exp $
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
 * hc             2005-11-24         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.tutorManager.hibernate;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.tutorManager.TutorDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.system.baseinfo.Tutor;

public class TutorHibernateDAO extends BasicHibernateDAO implements TutorDAO{

	/**
	 * 得到关于教师对象的tutor的一个查询example
	 * @param tutor
	 * @param departmentIdSeq
	 * @return
	 */
	private Criteria getExampleTutor(Tutor tutor, String departIdSeq) {
		Criteria criteria = getSession().createCriteria(Tutor.class);
		List critions = CriterionUtils.getEntityCriterions(tutor);
		if (StringUtils.isNotBlank(departIdSeq)) {
			criteria.add(Restrictions.in("department.id", SeqStringUtil
					.transformToLong(departIdSeq)));
		}
		for (Iterator iter = critions.iterator(); iter.hasNext();)
			criteria.add((Criterion) iter.next());
		return criteria;
	}
	
	/**
	 * 根据条件得到导师列表
	 * @param tutor
	 * @param departmentIdSeq
	 * @return
	 */
	public List getTutorList(Tutor tutor,String departmentIdSeq){
		if(null==tutor){
			return Collections.EMPTY_LIST;
		}
		Criteria criteria = getExampleTutor(tutor, departmentIdSeq);
		criteria.addOrder(Order.asc("department.id"));
		return criteria.list();
	}
	
	/**
	 * 根据条件得到导师的分页对象
	 * @param tutor
	 * @param departmentIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getTutorPagi(Tutor tutor, String departmentIdSeq,
			int pageNo, int pageSize) {
		if (null == tutor) {
			Result result = new Result(0, Collections.EMPTY_LIST);
			return new Pagination(pageNo, pageSize, result);
		}
		Criteria criteria = getExampleTutor(tutor, departmentIdSeq);
		criteria.addOrder(Order.asc("department.id"));
		return dynaSearch(criteria, pageNo, pageSize);
	}
	/**
	 * 根据条件得到group属性的集合list
	 * 仅仅指对象的直接属性集合
	 * @see com.shufe.dao.degree.tutorManager.TutorDAO#getPropertyOfTutor(com.shufe.model.system.baseinfo.Tutor, java.lang.String, java.lang.String, Boolean)
	 */
	public List getPropertyOfTutor(Tutor tutor, String departIdSeq, String propertys, Boolean isCount) {
		Criteria criteria = getExampleTutor(tutor, departIdSeq);
		ProjectionList projectionsList = Projections.projectionList();
		String[] property = StringUtils.split(propertys, ",");
		for (int i = 0; i < property.length; i++) {
			projectionsList.add(Projections.groupProperty(property[i]));
		}
		if(isCount.booleanValue()){
			projectionsList.add(Projections.rowCount());
		}
		criteria.setProjection(projectionsList);
		return criteria.list();
	}
}
