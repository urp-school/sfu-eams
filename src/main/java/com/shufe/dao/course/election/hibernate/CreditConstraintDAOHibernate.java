//$Id: CreditConstraintDAOHibernate.java,v 1.18 2006/12/24 08:57:21 duanth Exp $
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
 * chaostone             2005-12-11         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.election.hibernate;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.ConditionUtils;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.election.CreditConstraintDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.course.election.CreditConstraint;
import com.shufe.model.course.election.CreditInitParams;
import com.shufe.model.course.election.SpecialityCreditConstraint;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.util.DataRealmLimit;

public class CreditConstraintDAOHibernate extends BasicHibernateDAO implements CreditConstraintDAO {

	/**
	 * @see com.shufe.dao.course.election.CreditConstraintDAO#getCreditConstraint(java.io.Serializable,
	 *      java.lang.Class)
	 */
	public CreditConstraint getCreditConstraint(Serializable id, Class constraintClass) {
		CreditConstraint constraint = (CreditConstraint) getHibernateTemplate().get(
				constraintClass, id);
		if (null == constraint)
			constraint = (CreditConstraint) load(constraintClass, id);
		return constraint;
	}

	/**
	 * @see com.shufe.dao.course.election.CreditConstraintDAO#getCreditConstraint(java.io.Serializable[],
	 *      java.lang.Class)
	 */
	public List getCreditConstraints(Serializable[] ids, Class constraintClass) {
		String hql = "";
		if (constraintClass.equals(SpecialityCreditConstraint.class))
			hql = "from SpecialityCreditConstraint where id in (:ids)";
		else
			hql = "from StdCreditConstraint where id in (:ids)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}

	public Collection getStdCreditConstraints(List conditions, DataRealmLimit limit, List orders) {
		String hql = "from StdCreditConstraint where";
		if (null != limit) {
			if (null != limit.getDataRealm()) {
				if (StringUtils.isNotEmpty(limit.getDataRealm().getStudentTypeIdSeq())) {
					conditions.add(new Condition("std.type.id in (:stdTypeIds) ", SeqStringUtil
							.transformToLong(limit.getDataRealm().getStudentTypeIdSeq())));
				}
				if (StringUtils.isNotEmpty(limit.getDataRealm().getDepartmentIdSeq())) {
					conditions.add(new Condition("std.department.id in (:departIds) ",
							SeqStringUtil
									.transformToLong(limit.getDataRealm().getDepartmentIdSeq())));
				}
			}
		}
		hql += ConditionUtils.toQueryString(conditions);

		hql += OrderUtils.toSortString(orders);
		Query query = getSession().createQuery(hql);

		if (null != limit && null != limit.getPageLimit())
			return this.search(query, ConditionUtils.getParamMap(conditions), limit.getPageLimit());
		else
			return query.list();
	}

	public List getStdCreditConstraints(StdCreditConstraint creditConstrant) {
		return buildCriteria(creditConstrant).list();
	}

	private Criteria buildCriteria(StdCreditConstraint creditConstrant) {
		Criteria criteria = getSession().createCriteria(StdCreditConstraint.class);
		List criterions = CriterionUtils.getEntityCriterions(creditConstrant,
				new String[] { "std" });
		for (Iterator iter = criterions.iterator(); iter.hasNext();)
			criteria.add((Criterion) iter.next());
		if (null != creditConstrant) {
			List stdCriterions = CriterionUtils.getEntityCriterions("std.", creditConstrant
					.getStd());
			if (stdCriterions.size() > 0) {
				criteria.createAlias("std", "std");
				for (Iterator iter = stdCriterions.iterator(); iter.hasNext();)
					criteria.add((Criterion) iter.next());
			}
		}
		return criteria;
	}

	public List getStdIdHasConstraint(StdCreditConstraint constraint) {
		return buildCriteria(constraint).setProjection(Projections.property("std.id")).list();
	}

	/**
	 * @see CreditConstraintDAO#getSpecialityCreditConstraints(SpecialityCreditConstraint)
	 */
	public List getSpecialityCreditConstraints(SpecialityCreditConstraint creditConstrant) {
		Criteria criteria = getSession().createCriteria(SpecialityCreditConstraint.class);
		List criterions = CriterionUtils.getEntityCriterions(creditConstrant, new String[] {
				"speciality", "aspect" });
		for (Iterator iter = criterions.iterator(); iter.hasNext();)
			criteria.add((Criterion) iter.next());
		if (null == creditConstrant.getSpeciality())
			criteria.add(Restrictions.isNull("speciality"));
		if (null == creditConstrant.getAspect())
			criteria.add(Restrictions.isNull("aspect"));
		return criteria.list();
	}

	/**
	 * @see CreditConstraintDAO#getSpecialityCreditConstraints(TeachCommon)
	 */
	public List getSpecialityCreditConstraints(TeachCommon common) {
		Criteria criteria = getSession().createCriteria(SpecialityCreditConstraint.class);
		List criterions = CriterionUtils.getEntityCriterions(common);
		for (Iterator iter = criterions.iterator(); iter.hasNext();)
			criteria.add((Criterion) iter.next());
		if (null == common.getSpeciality())
			criteria.add(Restrictions.isNull("speciality"));
		if (null == common.getAspect())
			criteria.add(Restrictions.isNull("aspect"));
		return criteria.list();
	}

	/**
	 * 查询学生学分限制
	 * 
	 * @param stdId
	 * @param calendars
	 * @return
	 */
	public List getStdCreditConstraints(Student std, Collection calendars) {
		String hql = "from StdCreditConstraint where std =:std and calendar in(:calendars)";
		Query query = getSession().createQuery(hql);
		query.setParameter("std", std);
		query.setParameterList("calendars", calendars);
		return query.list();
	}

	public StdCreditConstraint getStdCreditConstraint(Long stdId, TeachCalendar calendar) {
		Query query = getSession().getNamedQuery("getSingleStdCreditConstraint");
		query.setCacheMode(CacheMode.GET);
		query.setParameter("stdId", stdId);
		query.setParameter("calendarId", calendar.getId());
		List constraints = query.list();
		if (constraints.size() > 0)
			return (StdCreditConstraint) constraints.get(0);
		else
			return null;
	}

	/**
	 * @see com.shufe.dao.course.election.CreditConstraintDAO#getStdCreditConstraints(java.io.Serializable[])
	 */
	public List getStdCreditConstraintsBySpeciality(Serializable[] specialityCreditConstraintIds) {
		Map params = new HashMap();
		params.put("specialityCreditConstraintIds", specialityCreditConstraintIds);
		return find("getStdCreditConstriantBySpeciality", params);
	}

	public void initCreditConstraint(TeachCommon common, TeachCalendar calendar, Float credit) {
		Connection con = getSession().connection();
		String strProcedure = "{? = call init_creditConstraint(?,?,?,?,?,?,?,?)}";
		try {
			CallableStatement cstmt = con.prepareCall(strProcedure);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, common.getEnrollTurn());
			cstmt.setLong(3, common.getStdType().getId().longValue());
			cstmt.setLong(4, common.getCalendar().getId().longValue());

			cstmt.setFloat(5, credit.floatValue());
			cstmt.setFloat(6, 0);
			cstmt.setLong(7, common.getDepart().getId().longValue());

			if (null != common.getSpeciality())
				cstmt.setLong(8, common.getSpeciality().getId().longValue());
			else
				cstmt.setLong(8, 0);
			if (null != common.getAspect())
				cstmt.setLong(9, common.getAspect().getId().longValue());
			else
				cstmt.setLong(9, 0);
			cstmt.executeUpdate();
			con.commit();
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("the SQLException is {}", e);
			try {
				con.rollback();
			} catch (Exception e1) {
			}
			throw new RuntimeException("execproc is failed" + "in init_creditConstraint");
		}
	}

	public StdCreditConstraint statStdCreditConstraint(Long stdId, CreditInitParams params) {
		StdCreditConstraint constraint = new StdCreditConstraint();
		Connection con = getSession().connection();
		try {
			if (null != params.getInitElectedCredit()
					&& params.getInitElectedCredit().equals(Boolean.TRUE)) {
				CallableStatement cstmt = con.prepareCall("{? = call update_creditElected(?,?,?)}");
				cstmt.registerOutParameter(1, Types.FLOAT);
				cstmt.setLong(2, stdId.longValue());
				cstmt.setLong(3, params.getCalendarId().longValue());
				cstmt.setString(4, params.getOverlappedCalendarIds());
				cstmt.executeUpdate();
				constraint.setElectedCredit(new Float(cstmt.getFloat(1)));
				cstmt.close();
			}
			if (null != params.getInitAwardCredit()
					&& params.getInitAwardCredit().equals(Boolean.TRUE)) {
				CallableStatement cstmt = con.prepareCall("{? = call update_creditAwarded(?,?,?)}");
				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.setLong(2, stdId.longValue());
				cstmt.setLong(3, params.getCalendarId().longValue());
				if (null == params.getPreCalendarId()) {
					cstmt.setLong(4, 0);
				} else {
					cstmt.setLong(4, params.getPreCalendarId().longValue());
				}
				cstmt.executeUpdate();
				constraint.setAwardedCredit(new Float(cstmt.getFloat(1)));
				cstmt.close();
			}
			if (null != params.getInitGPA() && params.getInitGPA().equals(Boolean.TRUE)) {
				CallableStatement cstmt = con.prepareCall("{? = call update_GPA(?,?)}");
				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.setLong(2, stdId.longValue());
				cstmt.setLong(3, params.getCalendarId().longValue());
				cstmt.executeUpdate();
				constraint.setGPA(new Float(cstmt.getFloat(1)));
				cstmt.close();
			}
			con.commit();
		} catch (SQLException e) {
			logger.debug("the SQLException is {}", e);
			try {
				con.rollback();
			} catch (Exception e1) {
			}
			throw new RuntimeException("exec proc failed" + "in updateStdCreditConstraint"
					+ ExceptionUtils.getStackTrace(e));
		}
		return constraint;
	}

	public Collection getStdWithoutCredit(Student std, TeachCalendar calendar,
			DataRealmLimit limit, List orderList) {
		Criteria criteria = buildNoCreditStdCriteria(std, calendar, limit, orderList);
		if (null != limit && null != limit.getPageLimit()) {
			return dynaSearch(criteria, limit.getPageLimit());
		} else {
			return criteria.list();
		}
	}

	public void batchAddCredit(Student std, DataRealm realm, CreditConstraint credit) {
		DataRealmLimit realmLimit = new DataRealmLimit();
		realmLimit.setDataRealm(realm);
		realmLimit.setPageLimit(null);
		Criteria criteria = buildNoCreditStdCriteria(std, credit.getCalendar(), realmLimit, null);
		criteria.setProjection(Property.forName("id"));
		batchAddCredit(criteria.list(), credit);
	}

	/**
	 * @see #batchAddCredit(Student, DataRealm, CreditConstraint)
	 */
	public void batchAddCredit(Collection stdIds, CreditConstraint credit) {
		int i = 0;
		for (Iterator iter = stdIds.iterator(); iter.hasNext();) {
			Long stdId = (Long) iter.next();
			StdCreditConstraint constraint = new StdCreditConstraint();
			constraint.setCalendar(credit.getCalendar());
			constraint.getStd().setId(stdId);
			constraint.setMaxCredit(credit.getMaxCredit());
			constraint.setMinCredit(credit.getMinCredit());
			utilDao.saveOrUpdate(constraint);
			if (++i % 100 == 0) {
				getSession().flush();
				getSession().clear();
			}
		}
	}

	/**
	 * 构建一个没有学分上限的学生查询<br>
	 * 将不在校和学籍无效的学生剔除
	 * 
	 * @param std
	 * @param calendar
	 * @param limit
	 * @param orderList
	 * @return
	 */
	private Criteria buildNoCreditStdCriteria(Student std, TeachCalendar calendar,
			DataRealmLimit limit, List orderList) {
		Criteria criteria = getSession().createCriteria(Student.class);
		List criterions = CriterionUtils.getEntityCriterions("", std, new String[] { "active",
				"inSchool" }, MatchMode.ANYWHERE, false);
		CriterionUtils.addCriterionsFor(criteria, criterions);
		criteria.add(Restrictions.eq("active", Boolean.TRUE));
		criteria.add(Restrictions.eq("inSchool", Boolean.TRUE));
		criteria.add(Restrictions.sqlRestriction(
				"{alias}.id not in(select xsid from xk_xsxf_t where xsid={alias}.id and jxrlid="
						+ calendar.getId() + ")", new Object[] {}, new Type[] {}));
		addSortListFor(criteria, orderList);
		addDataRealmLimt(criteria, new String[] { "type.id", "department.id" }, limit);
		return criteria;
	}
}
