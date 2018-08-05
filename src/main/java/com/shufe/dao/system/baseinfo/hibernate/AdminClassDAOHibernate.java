//$Id: AdminClassDAOHibernate.java,v 1.5 2006/09/14 02:33:54 yd Exp $
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
 * chaostone             2005-9-15         Created
 *  
 ********************************************************************************/

package com.shufe.dao.system.baseinfo.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.hibernate.HibernateQuerySupport;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.baseinfo.AdminClassDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.system.baseinfo.AdminClass;

public class AdminClassDAOHibernate extends BasicHibernateDAO implements
		AdminClassDAO {

	/**
	 * @see AdminClassDAO#getAdminClass(String)
	 */
	public AdminClass getAdminClass(Long id) {
		return (AdminClass) load(AdminClass.class, id);
	}

	/**
	 * @see AdminClassDAO#getAdminClasss(AdminClass)
	 */
	public List getAdminClasses(AdminClass adminClass) {
		Long[] stdTypeIds = null;
		if (null != adminClass
				&& ValidEntityPredicate.INSTANCE.evaluate(adminClass
						.getStdType())) {
			StudentType stdType = (StudentType) utilDao.get(StudentType.class,
					adminClass.getStdType().getId());
			List stdTypes = stdType.getDescendants();
			stdTypes.add(stdType);
			stdTypeIds = new Long[stdTypes.size()];
			for (int k = 0; k < stdTypes.size(); k++) {
				StudentType one = (StudentType) stdTypes.get(k);
				stdTypeIds[k] = one.getId();
			}
		}
		return buildExampleCriteria(adminClass, stdTypeIds, null).list();
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.AdminClassDAO#getAdminClasses(java.lang.String[])
	 */
	public List getAdminClassesById(Long[] classIds) {
		return buildExampleCriteria(null, null, null).add(
				Restrictions.in("id", classIds)).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.system.baseinfo.AdminClassDAODWRFacade#getAdminClassNamesExactly(java.lang.String,
	 *      java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public List getAdminClassNamesExactly(String enrollTurn, Long stdTypeId,
			Long departId, Long specialityId, Long aspectId) {
		HashMap params = new HashMap();
		String hql = "select d.id, d.name from AdminClass as d"
				+ " where d.state=true";
		if (!StringUtils.isEmpty(enrollTurn)) {
			params.put("enrollTurn", enrollTurn);
			hql += " and d.enrollYear=:enrollTurn";
		}
		if (null != stdTypeId) {
			params.put("stdTypeId", stdTypeId);
			hql += " and d.stdType.id=:stdTypeId";
		}
		if (null != departId) {
			params.put("departId", departId);
			hql += " and d.department.id=:departId";
		}
		if (null != specialityId) {
			params.put("specialityId", specialityId);
			hql += " and d.speciality.id=:specialityId";
		} else {
			hql += " and d.speciality is null";
		}

		if (null != aspectId) {
			params.put("aspectId", aspectId);
			hql += " and d.aspect.id=:aspectId";
		} else {
			hql += " and d.aspect is null and d.state=false";
		}
		Query query = getSession().createQuery(hql);
		HibernateQuerySupport.setParameter(query, params);
		return query.list();
	}
	
	public List getAdminClassIdAndNames(String enrollTurn, String stdTypeId,
			String departId, String specialityId, String aspectId) {
		String hql = "select d.id, d.name from AdminClass as d"
				+ " where d.state=true";
		if (StringUtils.isNotBlank(enrollTurn)) {
			hql += " and d.enrollYear like '%"+enrollTurn.trim()+"%'";
		}
		if (StringUtils.isNotBlank(stdTypeId)) {
			hql += " and d.stdType.id="+stdTypeId;
		}
		if (StringUtils.isNotBlank(departId)) {
			hql += " and d.department.id="+departId;
		}
		if (StringUtils.isNotBlank(specialityId)) {
			hql += " and d.speciality.id="+specialityId;
		}
		if (StringUtils.isNotBlank(aspectId)) {
			hql += " and d.aspect.id="+aspectId;
		}
		hql+=" order by d.enrollYear desc";
		return getSession().createQuery(hql).list();
	}

	/**
	 * @see AdminClassDAO#removeAdminClass(String)
	 */
	public void removeAdminClass(Long id) {
		remove(AdminClass.class, id);
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.AdminClassDAO#getAdminClassesExactly(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List getAdminClassesExactly(AdminClass adminClass) {
		Criteria criteria = buildExampleCriteria(adminClass, null, null);
		if (!ValidEntityPredicate.INSTANCE.evaluate(adminClass.getSpeciality()))
			criteria.add(Restrictions.isNull("speciality.id"));
		if (!ValidEntityPredicate.INSTANCE.evaluate(adminClass.getAspect()))
			criteria.add(Restrictions.isNull("aspect.id"));
		return criteria.list();
	}

	/**
	 * @see com.shufe.dao.system.baseinfo.AdminClassDAO#getAdminClasses(com.shufe.model.system.baseinfo.AdminClass,
	 *      java.lang.String[], java.lang.String[], int, int)
	 */
	public Pagination getAdminClasses(AdminClass adminClass, Long[] stdTypeIds,
			Long[] departIds, int pageNo, int pageSize) {
		Criteria criteria = buildExampleCriteria(adminClass, stdTypeIds,
				departIds);
		return dynaSearch(criteria, pageNo, pageSize);
	}

	public List getAdminClasses(AdminClass adminClass, Long[] stdTypeIds,
			Long[] departIds) {
		return buildExampleCriteria(adminClass, stdTypeIds, departIds).list();
	}

	/**
	 * @param adminClass
	 * @param departIds
	 * @param stdTypeIds
	 * @return
	 */
	private Criteria buildExampleCriteria(AdminClass adminClass,
			Long[] stdTypeIds, Long[] departIds) {
		Criteria criteria = getSession().createCriteria(AdminClass.class);
		List criterions = CriterionUtils.getEntityCriterions(adminClass,
				new String[] { "speciality", "stdType" });
		for (Iterator iter = criterions.iterator(); iter.hasNext();)
			criteria.add((Criterion) iter.next());
		if (null != adminClass) {
			if (ValidEntityPredicate.INSTANCE.evaluate(adminClass.getStdType())) {
				if (null == stdTypeIds) {
					stdTypeIds = new Long[] { adminClass.getStdType().getId() };
				} else {
					String tmp = intersectStdTypeIdSeq(adminClass.getStdType(),
							SeqStringUtil.transformToSeq(stdTypeIds, ","));
					stdTypeIds = SeqStringUtil.transformToLong(tmp);
				}
			}
		}
		if (null != departIds)
			criteria.add(Restrictions.in("department.id", departIds));
		if (null != stdTypeIds && stdTypeIds.length != 0)
			criteria.add(Restrictions.in("stdType.id", stdTypeIds));

		// 主要用于查询二专业的班级
		if (null != adminClass && null != adminClass.getSpeciality()) {
			List specialityCriterions = CriterionUtils.getEntityCriterions(
					"speciality.", adminClass.getSpeciality());
			if (!specialityCriterions.isEmpty()) {
				Criteria specialityCriteria = criteria.createCriteria(
						"speciality", "speciality");
				for (Iterator iter = specialityCriterions.iterator(); iter
						.hasNext();) {
					specialityCriteria.add((Criterion) iter.next());
				}
			}
		}
		criteria.add(Restrictions.eq("state", true));
		return criteria;
	}

	public int updateActualStdCount(Long adminClassId) {
		int stdCount = 0;
		Connection con = getSession().connection();
		String strProcedure = "{? = call update_classactualstdcount(?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = con.prepareCall(strProcedure);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setLong(2, adminClassId.longValue());
			cstmt.execute();
			stdCount = cstmt.getInt(1);
			con.commit();
			cstmt.close();
		} catch (SQLException e) {
			try {
				if (null != cstmt)
					cstmt.close();
				con.rollback();
			} catch (Exception e1) {
				info("execproduct is failed" + "in update_classactualstdcount"
						+ ExceptionUtils.getStackTrace(e));
				throw new RuntimeException(e1.getMessage());
			}
		}
		return stdCount;
	}

	public int updateStdCount(Long adminClassId) {
		int stdCount = 0;
		Connection con = getSession().connection();
		String strProcedure = "{? = call update_classstdcount(?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = con.prepareCall(strProcedure);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setLong(2, adminClassId.longValue());
			cstmt.execute();
			stdCount = cstmt.getInt(1);
			con.commit();
			cstmt.close();
		} catch (SQLException e) {
			try {
				if (null != cstmt)
					cstmt.close();
				con.rollback();
			} catch (Exception e1) {
				info("execproduct is failed" + "in update_classstdcount"
						+ ExceptionUtils.getStackTrace(e));
				throw new RuntimeException(e1.getMessage());
			}
		}
		return stdCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.dao.system.baseinfo.AdminClassDAO#getAdminClassIdsByAdminClass(com.shufe.model.system.baseinfo.AdminClass,
	 *      java.lang.String, java.lang.String)
	 */
	public List getAdminClassIds(AdminClass adminClass, String departmentIds,
			String stdTypeIds) {
		adminClass.setState(Boolean.TRUE);
		Long[] departmentId = SeqStringUtil.transformToLong(departmentIds);
		Long[] stdTypeId = SeqStringUtil.transformToLong(stdTypeIds);
		Criteria criteria = buildExampleCriteria(adminClass, stdTypeId,
				departmentId);
		criteria.setProjection(Projections.property("id"));
		return criteria.list();
	}

	public Long[] getAdminClassIds(String enrollYear, Long stdTypeId,
			Long departmentId, Long specialityId, Long aspectId) {
		EntityQuery query = new EntityQuery(AdminClass.class, "adminClass");
		if (StringUtils.isEmpty(enrollYear)) {
			return null;
		}
		query.add(new Condition("adminClass.enrollYear = (:enrollYear)",
				enrollYear));
		if (null == stdTypeId || stdTypeId.intValue() == 0) {
			return null;
		}
		query.add(new Condition("adminClass.stdType.id = (:stdTypeId)",
				stdTypeId));
		if (null == departmentId || departmentId.intValue() == 0) {
			return null;
		}
		query.add(new Condition("adminClass.department.id = (:departmentId)",
				departmentId));
		if (null == specialityId || specialityId.intValue() == 0) {
			query.add(new Condition("adminClass.speciality.id is null"));
		} else {
			query
					.add(new Condition(
							"adminClass.speciality.id = (:specialityId)",
							specialityId));
		}
		if (null == aspectId || aspectId.intValue() == 0) {
			query.add(new Condition("adminClass.aspect.id is null"));
		} else {
			query.add(new Condition("adminClass.aspect.id = (:aspectId)",
					aspectId));
		}
		Collection adminClasses = utilDao.search(query);
		Long[] ids = new Long[adminClasses.size()];
		int i = 0;
		for (Iterator it = adminClasses.iterator(); it.hasNext();) {
			AdminClass adminClass = (AdminClass) it.next();
			ids[i++] = adminClass.getId();
		}
		return ids;
	}
}
