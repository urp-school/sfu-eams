//$Id: ModulusDAOHibernate.java,v 1.4 2006/12/19 13:08:41 duanth Exp $
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
 * chenweixiong         2005-11-17          Created
 * zq                   2007-09-14          在下面的 FIXME 处要添加一个大类查询的语句，
 *                                          由于不是用EntityQuery来做的，所以暂无法添
 *                                          加该语句；
 ********************************************************************************/

package com.shufe.dao.workload.hibernate;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.dao.workload.ModulusDAO;
import com.shufe.model.workload.course.TeachModulus;

public class ModulusDAOHibernate extends BasicHibernateDAO implements ModulusDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.workload.ModulusDAO12#getModuls(java.lang.Class,
	 *      java.lang.String)
	 */
	public Criteria getModuls(Object depencyObject, String studentTypeIds) {
		Criteria criteria = getSession().createCriteria(depencyObject.getClass());
		List criterions = CriterionUtils.getEntityCriterions(depencyObject);
		for (Iterator iter = criterions.iterator(); iter.hasNext();) {
			Criterion element = (Criterion) iter.next();
			if (element.toString().indexOf("minPeople") > -1) {
				continue;
			}
			criteria.add(element);
		}
		// FIXME here
		if (StringUtils.isNotEmpty(studentTypeIds)) {
			criteria.add(Restrictions.in("studentType.id", SeqStringUtil
					.transformToLong(studentTypeIds)));
		} else {
			criteria.add(Restrictions.eq("studentType.id", new Long(0)));
		}
		if (depencyObject.getClass().equals(TeachModulus.class)) {
			TeachModulus teachModulus = (TeachModulus) depencyObject;
			if (null != teachModulus.getMinPeople()
					&& !new Integer(0).equals(teachModulus.getMinPeople())) {
				criteria.add(Restrictions.le("minPeople", teachModulus.getMinPeople()));
				criteria.add(Restrictions.gt("maxPeople", teachModulus.getMinPeople()));
			}
		}
		return criteria;
	}

	/**
	 * @see com.shufe.dao.workload.ModulusDAO#getModulus(java.lang.Object,
	 *      java.lang.String)
	 */
	public List getModulus(Object modulus, String stdTypeIdSeq) {
		Criteria criteria = getModuls(modulus, stdTypeIdSeq);
		criteria.addOrder(Order.asc("department.id"));
		if (modulus.getClass().equals(TeachModulus.class)) {
			criteria.addOrder(Order.asc("courseCategory.id"));
		}
		criteria.addOrder(Order.asc("modulusValue"));
		return criteria.list();
	}

	/**
	 * 分页对象
	 * 
	 * @see com.shufe.dao.workload.ModulusDAO#getPagiOfModulus(java.lang.Object,
	 *      java.lang.String, int, int)
	 */
	public Pagination getPagiOfModulus(Object modulus, String stdTypeIdSeq, int pageNo,
			int pageSize) {
		Criteria criteria = getModuls(modulus, stdTypeIdSeq);
		return dynaSearch(criteria, pageNo, pageSize);
	}

	/**
	 * 得到系数的属性值
	 * 
	 * @see com.shufe.dao.workload.ModulusDAO#getPropertyOfModulus(java.lang.Object,
	 *      java.lang.String, java.lang.String)
	 */
	public List getPropertyOfModulus(Object mdulus, String stdTypeIdSeq, String properties) {
		Criteria criteria = getModuls(mdulus, stdTypeIdSeq);
		String[] property = StringUtils.split(properties, ",");
		ProjectionList projectionList = Projections.projectionList();
		for (int i = 0; i < property.length; i++) {
			projectionList.add(Projections.groupProperty(property[i]));
		}
		criteria.setProjection(projectionList);
		return criteria.list();
	}

}
