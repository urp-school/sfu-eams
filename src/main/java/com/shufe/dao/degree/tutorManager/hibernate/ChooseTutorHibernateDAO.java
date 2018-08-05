package com.shufe.dao.degree.tutorManager.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.query.limit.SinglePage;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.OldPagination;
import com.shufe.dao.degree.tutorManager.ChooseTutorDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.tutorManager.ChooseTutor;
import com.shufe.model.system.baseinfo.Tutor;

public class ChooseTutorHibernateDAO extends BasicHibernateDAO implements ChooseTutorDAO {

	public Pagination getTutorByStd(Long stdId, int pageNo, int pageSize) {
		String hql = "select ct.tutorSet from ChooseTutor as ct where ct.std.id=:stdId ";
		Query query = getSession().createQuery(hql);
		Map param = new HashMap(1);
		param.put("stdId", stdId);
		return OldPagination.buildOldPagination((SinglePage)utilDao.paginateQuery(query, param, new PageLimit(
				pageNo, pageSize)));
	}

	public ChooseTutor getChooseTutor(Long stdId) {
		String hql = "from ChooseTutor c where c.std.id=:stdId";
		Query query = getSession().createQuery(hql);
		query.setLong("stdId", stdId.longValue());
		return (ChooseTutor) query.uniqueResult();
	}

	public Criteria constructStudentCriteria(ChooseTutor ct, String studentTypeIds,
			String departmentIds, int pageNo, int pageSize) {

		Criteria criteria = getSession().createCriteria(ChooseTutor.class).setProjection(
				Projections.property("std"));
		String[] excludedAttrs = new String[] { "department" };
		List criterions = CriterionUtils.getEntityCriterions(ct, excludedAttrs);
		for (Iterator iter = criterions.iterator(); iter.hasNext();) {
			Criterion criterion = (Criterion) iter.next();
			criteria.add(criterion);
		}
		/*---------添加对学生的模糊查询--------------*/
		Criteria stdCriteria = criteria.createCriteria("std", "std");
		List stdCriterons = CriterionUtils.getEntityCriterions("std.", ct.getStd());
		for (Iterator iter = stdCriterons.iterator(); iter.hasNext();)
			stdCriteria.add((Criterion) iter.next());

		if (null != ct.getTutorSet() && !ct.getTutorSet().isEmpty()) {
			Tutor tutor = (Tutor) ct.getTutorSet().iterator().next();
			List tutorCriterions = CriterionUtils.getEntityCriterions("tutor.", tutor);
			if (!tutorCriterions.isEmpty()) {
				Criteria tutorCriteria = criteria.createCriteria("tutorSet", "tutor");
				for (Iterator iter = tutorCriterions.iterator(); iter.hasNext();) {
					tutorCriteria.add((Criterion) iter.next());
				}
			}
		}
		if (ct.getStd().getDepartment().isVO())
			criteria.add(Restrictions.in("std.department.id", SeqStringUtil
					.transformToLong(departmentIds)));
		if (ct.getStd().getType().isVO()) {
			criteria.add(Restrictions.in("std.type.id", SeqStringUtil
					.transformToLong(studentTypeIds)));
		}
		return criteria;
	}

	public Pagination getTutorByStd(String stdNo, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
