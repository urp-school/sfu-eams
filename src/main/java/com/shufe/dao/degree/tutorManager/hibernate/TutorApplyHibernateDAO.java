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
 * 塞外狂人             2006-9-5            Created
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
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.tutorManager.TutorApplyDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.tutorManager.tutor.TutorApply;

public class TutorApplyHibernateDAO extends BasicHibernateDAO implements
		TutorApplyDAO {

	/**
	 * 得到
	 * @param tutorApply
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	private Criteria getExampleTutorApply(TutorApply tutorApply,
			String departIdSeq) {
		Criteria criteria = getSession().createCriteria(TutorApply.class);
		List criterions = CriterionUtils.getEntityCriterions(tutorApply);
		if (null != tutorApply.getTeacher()) {
			criterions.addAll(CriterionUtils.getEntityCriterions("teacher.",
					tutorApply.getTeacher()));
		}
		criteria.createAlias("teacher", "teacher");
		criteria.add(Restrictions.in("teacher.department.id", SeqStringUtil
				.transformToLong(departIdSeq)));
		for (Iterator iter = criterions.iterator(); iter.hasNext();) {
			Criterion element = (Criterion) iter.next();
			criteria.add(element);
		}
		return criteria;
	}
	
	/**
	 * 根据条件得到导师申请的list;
	 * @param tutorApply
	 * @param departIdSeq
	 * @return
	 */
	public List getTutorApplyList(TutorApply tutorApply, String departIdSeq) {
		if (null == tutorApply || StringUtils.isBlank(departIdSeq)) {
			return Collections.EMPTY_LIST;
		}
		Criteria criteria = getExampleTutorApply(tutorApply, departIdSeq);
		criteria.addOrder(Order.desc("applyTime"));
		return criteria.list();
	}
	
	/**
	 * 根据所给予的条件得到分页对象
	 * @param tutorApply
	 * @param departIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getTutorApplyPagi(TutorApply tutorApply,
			String departIdSeq, int pageNo, int pageSize) {
		if (null == tutorApply || StringUtils.isBlank(departIdSeq)) {
			Result result = new Result(0, Collections.EMPTY_LIST);
			Pagination pagination = new Pagination(pageNo, pageSize, result);
			return pagination;
		}
		Criteria criteria = getExampleTutorApply(tutorApply, departIdSeq);
		criteria.addOrder(Order.desc("applyTime"));
		return dynaSearch(criteria, pageNo, pageSize);
	}
}

