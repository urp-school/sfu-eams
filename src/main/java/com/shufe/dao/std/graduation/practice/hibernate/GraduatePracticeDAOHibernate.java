package com.shufe.dao.std.graduation.practice.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.std.graduation.practice.GraduatePracticeDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.std.graduation.practice.GraduatePractice;

public class GraduatePracticeDAOHibernate extends BasicHibernateDAO implements GraduatePracticeDAO {

	/**
	 * 得到练习的模型列表
	 * 
	 * @param graduatePractice
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	private Criteria getExamplePractices(GraduatePractice graduatePractice, String departIdSeq,
			String stdTypeIdSeq) {
		Criteria criteria = getSession().createCriteria(GraduatePractice.class);
		List criterions = CriterionUtils.getEntityCriterions(graduatePractice);
		criteria.createAlias("student", "student");
		if (StringUtils.isNotBlank(departIdSeq)) {
			criteria.add(Restrictions.in("student.department.id", SeqStringUtil
					.transformToLong(departIdSeq)));
		}
		if (StringUtils.isNotBlank(stdTypeIdSeq)) {
			criteria.add(Restrictions.in("student.type.id", SeqStringUtil
					.transformToLong(stdTypeIdSeq)));
		}
		criterions.addAll(CriterionUtils.getEntityCriterions("student.", graduatePractice
				.getStudent()));
		for (Iterator iter = criterions.iterator(); iter.hasNext();) {
			Criterion element = (Criterion) iter.next();
			criteria.add(element);
		}
		return criteria;
	}

	/**
	 * 得到对应的属性的集合
	 * 
	 * @see com.shufe.dao.std.graduation.practice.GraduatePracticeDAO#getPropertyOfPractices(com.shufe.model.std.graduation.practice.GraduatePractice,
	 *      java.lang.String, java.lang.String, String, java.lang.Boolean)
	 */
	public List getPropertyOfPractices(GraduatePractice graduatePractice, String departIdSeq,
			String stdTypeIdSeq, String properties, Boolean isCount) {
		Criteria criteria = getExamplePractices(graduatePractice, departIdSeq, stdTypeIdSeq);
		ProjectionList projectionList = Projections.projectionList();
		if (isCount.booleanValue()) {
			projectionList.add(Projections.rowCount());
		}
		String[] property = StringUtils.split(properties, ",");
		for (int i = 0; i < property.length; i++) {
			projectionList.add(Projections.groupProperty(property[i]));
		}
		criteria.setProjection(projectionList);
		return criteria.list();
	}
}
