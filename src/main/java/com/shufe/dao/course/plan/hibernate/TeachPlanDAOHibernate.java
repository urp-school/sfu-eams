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
 * chaostone            2006-03-24          Created
 * zq                   2007-09-18          FIXME  下面FIXME处很不好改
 ********************************************************************************/
package com.shufe.dao.course.plan.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.IntRange;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.collection.transformers.PropertyTransformer;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.SqlQuery;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.plan.TeachPlanDAO;
import com.shufe.dao.system.calendar.TeachCalendarDAO;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.util.DataRealmLimit;

/**
 * 培养计划数据存取访问类
 * 
 * @author chaostone
 */
public class TeachPlanDAOHibernate extends BasicHibernateDAO implements TeachPlanDAO {

  private TeachCalendarDAO teachCalendarDAO;

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#getTeachPlan(java.lang.Long)
   */
  public TeachPlan getTeachPlan(Long id) {
    return (TeachPlan) get(TeachPlan.class, id);
  }

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#getTeachPlans(java.lang.Long[])
   */
  public List getTeachPlans(Long[] planIds) {
    Criteria criteria = getSession().createCriteria(TeachPlan.class);
    criteria.add(Restrictions.in("id", planIds));
    return criteria.list();
  }

  public Collection getTeachPlans(TeachPlan plan, DataRealmLimit limit, List sortList,
      Boolean isSpecialityPlan, Boolean isExactly) {
    Criteria criteria = getTeachPlanCriteria(plan, sortList, isSpecialityPlan, isExactly);
    if (null != limit) {
      if (StringUtils.isNotEmpty(limit.getDataRealm().getStudentTypeIdSeq())) {
        criteria.add(Restrictions.in("stdType.id",
            SeqStringUtil.transformToLong(limit.getDataRealm().getStudentTypeIdSeq())));
      }
      if (StringUtils.isNotEmpty(limit.getDataRealm().getDepartmentIdSeq())) {
        criteria.add(Restrictions.in("department.id",
            SeqStringUtil.transformToLong(limit.getDataRealm().getDepartmentIdSeq())));
      }
    }
    criteria.setCacheable(true);
    if (null != limit && null != limit.getPageLimit()) {
      return dynaSearch(criteria, limit.getPageLimit());
    } else return criteria.list();
  }

  private Criteria getTeachPlanCriteria(TeachPlan plan, List orderList, Boolean isSpeciality,
      Boolean isExactly) {
    Criteria criteria = getSession().createCriteria(TeachPlan.class);
    if (null != plan) {
      List criterions = CriterionUtils.getEntityCriterions(plan);
      for (Iterator iter = criterions.iterator(); iter.hasNext();) {
        Criterion criterion = (Criterion) iter.next();
        // FIXME 学生大类查询
        // stdTypeSearch(plan.getStd(),
        // String.valueOf(plan.getStd().getId()));
        criteria.add(criterion);
      }
      if (null != plan.getSpeciality() && null == plan.getSpeciality().getId()
          && null != plan.getSpeciality().getMajorType()
          && null != plan.getSpeciality().getMajorType().getId()) {
        criteria.createCriteria("speciality", "speciality").createCriteria("majorType", "majorType")
            .add(Restrictions.eq("id", plan.getSpeciality().getMajorType().getId()));
      }

      if (null != isSpeciality) {
        if (isSpeciality.equals(Boolean.FALSE)) {
          if (null != plan.getStd()) {
            List stdRestrictions = new ArrayList();
            if (StringUtils.isNotEmpty(plan.getStd().getCode())) {
              stdRestrictions.add(Restrictions.like("code", plan.getStd().getCode(), MatchMode.START));
            }
            if (StringUtils.isNotEmpty(plan.getStd().getName())) {
              stdRestrictions.add(Restrictions.like("name", plan.getStd().getName(), MatchMode.ANYWHERE));
            }
            if (!stdRestrictions.isEmpty()) {
              Criteria stdCriteria = criteria.createCriteria("std", "std");
              for (Iterator iter = stdRestrictions.iterator(); iter.hasNext();)
                stdCriteria.add((Criterion) iter.next());
            }
          }
          criteria.add(Restrictions.isNotNull("std"));
        } else {
          criteria.add(Restrictions.isNull("std"));
        }
      }
      if (null != isExactly && isExactly.equals(Boolean.TRUE)) {
        if (!ValidEntityPredicate.INSTANCE.evaluate(plan.getSpeciality())) {
          criteria.add(Restrictions.isNull("speciality"));
        }
        if (!ValidEntityPredicate.INSTANCE.evaluate(plan.getAspect())) {
          criteria.add(Restrictions.isNull("aspect"));
        }
      }
    }
    addSortListFor(criteria, orderList);
    return criteria;
  }

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#getStdHasPlan(java.lang.String, java.lang.Long,
   *      java.lang.Long, java.lang.Long, java.lang.Long)
   */
  public List getStdHasPlan(TeachPlan plan) {
    TeachPlan example = new TeachPlan();
    example.setEnrollTurn(plan.getEnrollTurn());
    example.setStdType(plan.getStdType());
    example.setDepartment(plan.getDepartment());
    example.setSpeciality(plan.getSpeciality());
    example.setAspect(plan.getAspect());
    Criteria criteria = getTeachPlanCriteria(example, null, Boolean.FALSE, Boolean.TRUE);
    criteria.setProjection(Projections.property("std"));
    return criteria.list();
  }

  public Float getCreditByTerm(TeachPlan plan, int term) {
    IntRange termRange = new IntRange(1, plan.getTermsCount().intValue());
    if (!termRange.containsInteger(term)) throw new RuntimeException("term out range");
    else {
      return null;
    }
  }

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#statOverallCredit(java.lang.Long)
   */
  public float statOverallCredit(Long planId) {
    TeachPlan plan = getTeachPlan(planId);
    initialize(plan.getCourseGroups());
    return plan.statOverallCredit();
  }

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#statOverallCreditHour(java.lang.Long)
   */
  public int statOverallCreditHour(Long planId) {
    TeachPlan plan = getTeachPlan(planId);
    initialize(plan.getCourseGroups());
    return plan.statOverallCreditHour();
  }

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#getUsedCourseTypes(com.shufe.model.course.plan.TeachPlan)
   */
  public List getUsedCourseTypes(TeachPlan plan) {
    return utilDao.searchNamedQuery("getUsedCourseTypes", new Object[] { plan });
  }

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#deleteTeachPlan(java.lang.Long)
   */
  public void removeTeachPlan(Long planId) {
    TeachPlan plan = getTeachPlan(planId);
    Set courseGroups = new HashSet();
    courseGroups.addAll(plan.getCourseGroups());
    remove(TeachPlan.class, planId);

    for (Iterator iter = courseGroups.iterator(); iter.hasNext();) {
      CourseGroup group = (CourseGroup) iter.next();
      remove(group);
    }
  }

  public Collection statDepartCourse(TeachCalendar calendar, List stdTypes, List departs, PageLimit limit) {
    StringBuffer sql = buildDepartCourseSql(calendar, stdTypes);
    SqlQuery query = new SqlQuery(sql.toString());
    query.setLimit(limit);
    Map params = new HashMap();
    params.put("bmids", CollectionUtils.collect(departs, new PropertyTransformer("id")));
    params.put("xslbids", CollectionUtils.collect(stdTypes, new PropertyTransformer("id")));
    query.setParams(params);
    Collection rs = utilDao.search(query);
    StatHelper.replaceIdWith(rs, new Class[] { Department.class, Course.class }, utilDao);
    return rs;
  }

  public Collection statDepartCourseCount(TeachCalendar calendar, List stdTypes, List departs) {
    StringBuffer sql = new StringBuffer();
    sql.append("select bmid,count(*) from (").append(buildDepartCourseSql(calendar, stdTypes))
        .append(" ) group by bmid");
    SqlQuery query = new SqlQuery(sql.toString());
    Map params = new HashMap();
    params.put("bmids", CollectionUtils.collect(departs, new PropertyTransformer("id")));
    params.put("xslbids", CollectionUtils.collect(stdTypes, new PropertyTransformer("id")));
    query.setParams(params);
    return utilDao.search(query);
  }

  private StringBuffer buildDepartCourseSql(TeachCalendar calendar, List stdTypes) {
    StringBuffer sql = new StringBuffer("select distinct jh.bmid,jhkc.kcid,jhkc.xf,jhkc.xs,jhkc.zks "
        + " from pyjh_t jh,pyjh_kcz_t kcz,pyjh_kczgl_t kczgl,pyjh_kc_t jhkc"
        + " where kcz.id=jhkc.kczid and kczgl.kczid=kcz.id and kczgl.pyjhid=jh.id"
        + " and jh.bmid in(:bmids) and jh.xslbid in(:xslbids)");
    Long[] stdTypeIds = new Long[stdTypes.size()];
    for (int i = 0; i < stdTypes.size(); i++) {
      stdTypeIds[i] = ((StudentType) stdTypes.get(i)).getId();
    }
    List enrollYears = teachCalendarDAO.getActiveEnrollTurns(calendar, stdTypeIds);
    StringBuffer condition = new StringBuffer(" and jhkc.kkxqc=(CASE ");
    TermCalculator calc = new TermCalculator(teachCalendarDAO, calendar);
    for (Iterator iterator = enrollYears.iterator(); iterator.hasNext();) {
      String year = (String) iterator.next();
      try {
        int term = calc.getTerm(calendar.getStudentType(), year, Boolean.TRUE);
        if (term > 0) {
          condition.append("when jh.rxpc='" + year + "' then '" + term + "' ");
        }
      } catch (Exception e) {
      }
    }
    condition.append(" end) ");
    if (!enrollYears.isEmpty()) sql.append(condition);
    return sql;
  }

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#saveTeachPlan(com.shufe.model.course.plan.TeachPlan)
   */
  public void saveTeachPlan(TeachPlan plan) {
    save(plan);
  }

  /**
   * @see com.shufe.dao.course.plan.TeachPlanDAO#updateTeachPlan(com.shufe.model.course.plan.TeachPlan)
   */
  public void updateTeachPlan(TeachPlan plan) {
    update(plan);
  }

  public void setTeachCalendarDAO(TeachCalendarDAO teachCalendarDAO) {
    this.teachCalendarDAO = teachCalendarDAO;
  }

  public List getTeachPlan(Speciality speciality, String stdTypeCode, String enrollTurn) {
    Criteria criteria = getSession().createCriteria(TeachPlan.class);
    criteria.createAlias("stdType", "stdType");
    criteria.add(Restrictions.eq("stdType.code", stdTypeCode));
    criteria.add(Restrictions.eq("speciality", speciality));
    criteria.add(Restrictions.eq("enrollTurn", enrollTurn));
    criteria.add(Restrictions.isNull("std"));
    return criteria.list();
  }

}
