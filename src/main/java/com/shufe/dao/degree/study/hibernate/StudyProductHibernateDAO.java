//$Id: StudyThesisHibernateDAO.java,v 1.1 2007-3-5 13:08:52 Administrator Exp $
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
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.study.StudyProductDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.study.StudyProduct;

public class StudyProductHibernateDAO extends BasicHibernateDAO implements
		StudyProductDAO {

	/**
	 * 得到研究论文的对象
	 * @param studyThesis
	 * @param departIdSeq
	 * @param stdTypeSeq
	 * @return
	 */
	private Criteria getExampleStudyProduct(Object studyProduct,
			String departIdSeq, String stdTypeSeq) {
		Criteria criteria = getSession()
				.createCriteria(studyProduct.getClass());
		List criterions = CriterionUtils.getEntityCriterions(studyProduct);
		criteria.createAlias("student", "student");
		if (StringUtils.isNotBlank(departIdSeq)) {
			criteria.add(Restrictions.in("student.department.id", SeqStringUtil
					.transformToLong(departIdSeq)));
		}
		if (StringUtils.isNotBlank(stdTypeSeq)) {
			criteria.add(Restrictions.in("student.type.id", SeqStringUtil
					.transformToLong(stdTypeSeq)));
		}
		criterions.addAll(CriterionUtils.getEntityCriterions("student.",
				((StudyProduct) studyProduct).getStudent()));
		for (Iterator iter = criterions.iterator(); iter.hasNext();) {
			Criterion element = (Criterion) iter.next();
			criteria.add(element);
		}
		return criteria;
	}
	
	
	/**
	 * 得到研究论文的列表
	 * @see com.shufe.dao.degree.study.StudyProductDAO
	 */
	public List getStudyProducts(Object studyProduct, String departIdSeq,
			String stdTypeSeq) {
		Criteria criteria = getExampleStudyProduct(studyProduct, departIdSeq, stdTypeSeq);
		return criteria.list();
	}

	/**
	 * 得到研究论文的分页对象
	 * @see com.shufe.dao.degree.study.StudyProductDAO
	 */
	public Pagination getStudyProductPagi(Object studyProduct,
			String departIdSeq, String stdTypeSeq, int pageNo, int pageSize) {
		Criteria criteria = getExampleStudyProduct(studyProduct, departIdSeq, stdTypeSeq);
		return dynaSearch(criteria, pageNo, pageSize);
	}

	/**
	 * 得到论文的属性值
	 * @see com.shufe.dao.degree.study.StudyProductDAO#getStdPropertyProduct(com.shufe.model.degree.study.StudyThesis, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public List getStdPropertyProduct(Object studyProduct, String departIdSeq,
			String stdTypeSeq, String properties, boolean isDistinctStdNo) {
		Criteria criteria = getExampleStudyProduct(studyProduct, departIdSeq,
				stdTypeSeq);
		ProjectionList projectionList = Projections.projectionList();
		if (isDistinctStdNo) {
			projectionList.add(Projections.countDistinct("student"));
		}
		projectionList.add(Projections.rowCount());
		String[] property = StringUtils.split(properties, ",");
		for (int i = 0; i < property.length; i++) {
			projectionList.add(Projections.groupProperty(property[i]));
		}
		criteria.setProjection(projectionList);
		return criteria.list();
	}


	/**
	 * 更新列表属性
	 * @see com.shufe.dao.degree.study.StudyProductDAO#batchUpdate(java.lang.String, java.lang.String[], java.lang.Object[], java.lang.String)
	 */
	public void batchUpdate(String entityName, String[] properties,
			Object[] values, String idSeq) {
		String updateSql = "update " + StringUtils.capitalize(entityName)
				+ " set ";
		for (int i = 0; i < properties.length; i++) {
			updateSql += properties[i] + "=" + values[i];
			if (i != properties.length - 1) {
				updateSql += ",";
			}
		}
		updateSql += " where id in(:ids)";
		if (StringUtils.isNotBlank(idSeq)) {
			Query query = getSession().createQuery(updateSql);
			query.setParameterList("ids", SeqStringUtil.transformToLong(idSeq));
			query.executeUpdate();
		}
	}

	/**
	 * 删除所列对象的数据
	 * @see com.shufe.dao.degree.study.StudyProductDAO#batchDelete(java.lang.String, java.lang.String)
	 */
	public void batchDelete(String entityName, String idSeq) {
		String deleteSql = "delete " + StringUtils.capitalize(entityName)
				+ " where id in(:ids)";
		if (StringUtils.isNotBlank(idSeq)) {
			Query query = getSession().createQuery(deleteSql);
			query.setParameterList("ids", SeqStringUtil.transformToLong(idSeq));
			query.executeUpdate();
		}
	}
}
