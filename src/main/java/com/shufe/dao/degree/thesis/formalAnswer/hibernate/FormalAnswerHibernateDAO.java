//$Id: FormalAnswerHibernateDAO.java,v 1.1 2007-1-31 14:40:50 Administrator Exp $
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
 * chenweixiong              2007-1-31         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.thesis.formalAnswer.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.thesis.formalAnswer.FormalAnswerDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.thesis.answer.FormalAnswer;

public class FormalAnswerHibernateDAO extends BasicHibernateDAO implements FormalAnswerDAO {

	/**
	 * @param formalAnswer
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	private Criteria getExampleOfFormalAnswer(FormalAnswer formalAnswer, String departmentIdSeq,
			String stdTypeIdSeq) {
		Criteria criteria = getSession().createCriteria(FormalAnswer.class);
		List criteriions = CriterionUtils.getEntityCriterions(formalAnswer);
		if (null != formalAnswer.getThesisManage()) {
			criteria.createAlias("thesisManage", "thesisManage");
			criteriions.addAll(CriterionUtils.getEntityCriterions("thesisManage.", formalAnswer
					.getThesisManage()));
		}
		if (null != formalAnswer.getStudent()) {
			criteriions.addAll(CriterionUtils.getEntityCriterions("student.", formalAnswer
					.getStudent()));
		}
		criteria.createAlias("student", "student");
		criteria.add(Restrictions
				.in("student.type.id", SeqStringUtil.transformToLong(stdTypeIdSeq)));
		criteria.add(Restrictions.in("student.department.id", SeqStringUtil
				.transformToLong(departmentIdSeq)));
		for (Iterator iter = criteriions.iterator(); iter.hasNext();) {
			Criterion element = (Criterion) iter.next();
			criteria.add(element);
		}
		return criteria;
	}

	/**
	 * 得到正式答辩的List列表
	 * 
	 * @param formalAnswer
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getFormalAnswers(FormalAnswer formalAnswer, String departmentIdSeq,
			String stdTypeIdSeq) {
		if (StringUtils.isBlank(departmentIdSeq) && StringUtils.isBlank(stdTypeIdSeq)) {
			return Collections.EMPTY_LIST;
		}
		Criteria criteria = getExampleOfFormalAnswer(formalAnswer, departmentIdSeq, stdTypeIdSeq);
		return criteria.list();
	}

	/**
	 * 得到正式答辩的分页列表
	 * 
	 * @param formalAnswer
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public Pagination getFormalAnswerPagination(FormalAnswer formalAnswer, String departmentIdSeq,
			String stdTypeIdSeq, int pageNo, int pageSize) {
		if (StringUtils.isBlank(departmentIdSeq) && StringUtils.isBlank(stdTypeIdSeq)) {
			Result result = new Result(0, new ArrayList());
			Pagination pagination = new Pagination(result);
			return pagination;
		}
		Criteria criteria = getExampleOfFormalAnswer(formalAnswer, departmentIdSeq, stdTypeIdSeq);
		return dynaSearch(criteria, pageNo, pageSize);
	}

	/**
	 * 批量更新对象数据
	 * 
	 * @see com.shufe.dao.degree.thesis.formalAnswer.FormalAnswerDAO#batchUpdate(java.lang.String[],
	 *      java.lang.Object[], java.lang.String)
	 */
	public void batchUpdate(String[] propertys, Object[] values, String formalAnswerIdSeq) {
		int maxTop = 0;
		int stringLength = formalAnswerIdSeq.length();
		int splitlength = formalAnswerIdSeq.split(",").length;
		if (StringUtils.isNotEmpty(formalAnswerIdSeq)) {
			maxTop = stringLength / splitlength;
		}
		while (formalAnswerIdSeq.split(",").length >= 1000) {
			String tempseq = formalAnswerIdSeq.substring(0, formalAnswerIdSeq.indexOf(",",
					maxTop * 500));
			updateFormalAnswer(propertys, values, tempseq);
			formalAnswerIdSeq = formalAnswerIdSeq.substring(formalAnswerIdSeq.indexOf(",",
					maxTop * 500) + 1);
		}
		updateFormalAnswer(propertys, values, formalAnswerIdSeq);
	}

	/**
	 * 更新论文答辩的数据 局限只能是都是等号
	 * 
	 * @param propertys
	 * @param values
	 * @param formalAnswerIdSeq
	 */
	public void updateFormalAnswer(String[] propertys, Object[] values, String formalAnswerIdSeq) {
		String hqlStr = "update FormalAnswer set ";
		for (int i = 0; i < propertys.length; i++) {
			if (i == propertys.length - 1) {
				hqlStr += propertys[i] + "=:" + propertys[i].replace('.', '_');
			} else {
				hqlStr += propertys[i] + "=:" + propertys[i].replace('.', '_') + ",";
			}
		}
		hqlStr += " where id in(" + formalAnswerIdSeq + ")";
		if (StringUtils.isNotBlank(formalAnswerIdSeq)) {
			Query query = getSession().createQuery(hqlStr);
			for (int i = 0; i < propertys.length; i++) {
				query.setParameter(propertys[i].replace('.', '_'), values[i]);
			}
			query.executeUpdate();
		}
	}

	/**
	 * TODO 目前只支持直接属性
	 * 
	 * @see com.shufe.dao.degree.thesis.formalAnswer.FormalAnswerDAO#getPropertys(com.shufe.model.degree.thesis.answer.FormalAnswer,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getPropertys(FormalAnswer formalAnswer, String departmentIdSeq,
			String stdTypeIdSeq, String propertyName) {
		Criteria criteria = getExampleOfFormalAnswer(formalAnswer, departmentIdSeq, stdTypeIdSeq);
		String[] propertys = StringUtils.split(propertyName, ",");
		ProjectionList projectionList = Projections.projectionList();
		for (int i = 0; i < propertys.length; i++) {
			projectionList.add(Projections.property(propertys[i]));
		}
		criteria.setProjection(projectionList);
		return criteria.list();
	}
}
