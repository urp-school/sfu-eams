//$Id: QuestionnaireRecycleRateDAOHibernate.java,v 1.12 2007/01/11 08:58:16 cwx Exp $
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
 * chenweixiong              2005-10-25         Created
 *  
 ********************************************************************************/

package com.shufe.dao.evaluate.hibernate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.evaluate.QuestionnaireRecycleRate;
import com.shufe.model.quality.evaluate.EvaluateResult;

/**
 * @author hj 2005-10-25 QuestionnaireRecycleRateDAO.java has been created
 */
public class QuestionnaireRecycleRateDAOHibernate extends BasicHibernateDAO implements
        QuestionnaireRecycleRateDAO {
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO12#getTotlePersonAmount(java.lang.String,
     *      java.util.Collection, java.lang.String)
     */
    public List getTotlePersonAmount(String collegeIds, Collection teachCalendarCollection,
            String studentTypeIds) {
        if (StringUtils.isEmpty(collegeIds) || StringUtils.isEmpty(studentTypeIds)
                || null == teachCalendarCollection) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(CourseTake.class);
        criteria.createAlias("task", "task");
        criteria.createAlias("student", "student");
        criteria.add(Restrictions.in("student.department.id", SeqStringUtil
                .transformToLong(collegeIds)));
        criteria.add(Restrictions.in("student.type.id", SeqStringUtil
                .transformToLong(studentTypeIds)));
        criteria.add(Restrictions.in("task.calendar", teachCalendarCollection));
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty("student.department.id")).add(
                Projections.groupProperty("student.type.id")));
        
        criteria.addOrder(Order.asc("student.department.id"))
                .addOrder(Order.asc("student.type.id"));
        return criteria.list();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO12#getHasEvlautaeAmount(java.lang.String,
     *      java.lang.String) 2007-6-17 EvaluateResults ->EvaluateResult by duan
     */
    public List getHasEvlautaeData(String collegeIds, String studentTypeIds,
            Collection teachCalendars) {
        if (StringUtils.isBlank(collegeIds) || StringUtils.isBlank(studentTypeIds)
                || null == teachCalendars || teachCalendars.size() < 1) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(EvaluateResult.class);
        criteria.add(Restrictions.in("stdDepart.id", SeqStringUtil.transformToLong(collegeIds)));
        criteria.add(Restrictions.in("stdType.id", SeqStringUtil.transformToLong(studentTypeIds)));
        criteria.add(Restrictions.in("teachCalendar", teachCalendars));
        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(
                Projections.groupProperty("stdDepart.id")).add(
                Projections.groupProperty("stdType.id")).add(Projections.groupProperty("student")));
        return criteria.list();
    }
    
    public Criteria getRecycleRateExample(String deparmtentIds, String studentTypeIds,
            Collection teachCalenders) {
        Criteria criteria = getSession().createCriteria(QuestionnaireRecycleRate.class);
        criteria
                .add(Restrictions.in("department.id", SeqStringUtil.transformToLong(deparmtentIds)));
        criteria.add(Restrictions.in("studentType.id", SeqStringUtil
                .transformToLong(studentTypeIds)));
        criteria.add(Restrictions.in("teachCalendar", teachCalenders));
        criteria.addOrder(Order.asc("department.id")).addOrder(Order.asc("studentType.id"));
        return criteria;
    }
    
    /**
     * @see com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO12#getRecycleRateObjects(java.lang.String,
     *      java.lang.String)
     */
    public List getRecycleRateObjects(String deparmtentIds, String studentTypeIds,
            Collection teachCalenders) {
        if (StringUtils.isEmpty(deparmtentIds) || StringUtils.isEmpty(studentTypeIds)
                || null == teachCalenders || teachCalenders.size() < 1) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getRecycleRateExample(deparmtentIds, studentTypeIds, teachCalenders);
        return criteria.list();
    }
    
    /**
     * @see com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO12#getStatisticResults(java.lang.String,
     *      java.lang.String)
     */
    public List getStatisticResults(String collegeIdSeq, String stdTypeIdSeq,
            Collection teachCalendars) {
        if (StringUtils.isBlank(collegeIdSeq) || StringUtils.isBlank(stdTypeIdSeq)
                || null == teachCalendars || teachCalendars.size() < 1) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(EvaluateResult.class);
        criteria.add(Restrictions.in("studDepart.id", SeqStringUtil.transformToLong(collegeIdSeq)));
        criteria.add(Restrictions.in("stdType.id", SeqStringUtil.transformToLong(stdTypeIdSeq)));
        criteria.add(Restrictions.in("teachCalendar", teachCalendars));
        criteria.setProjection(Projections.projectionList().add(
                Projections.countDistinct("student"))
                .add(Projections.groupProperty("stdDepart.id")).add(
                        Projections.groupProperty("stdType.id")));
        return criteria.list();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO12#getTotleNumber(java.lang.String,
     *      java.lang.String, java.util.Collection)
     */
    public List getTotleNumber(String collegeIds, String studentTypeIds, Collection teachCalendars) {
        if (StringUtils.isBlank(collegeIds) || StringUtils.isBlank(studentTypeIds)
                || null == teachCalendars || teachCalendars.size() < 1) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(CourseTake.class);
        criteria.createAlias("task", "task");
        criteria.createAlias("student", "student");
        criteria.add(Restrictions.in("student.department.id", SeqStringUtil
                .transformToLong(collegeIds)));
        criteria.add(Restrictions.in("student.type.id", SeqStringUtil
                .transformToLong(studentTypeIds)));
        criteria.add(Restrictions.in("task.calendar", teachCalendars));
        criteria.setProjection(Projections.projectionList().add(
                Projections.countDistinct("student")).add(
                Projections.groupProperty("student.department.id")).add(
                Projections.groupProperty("student.type.id")));
        criteria.addOrder(Order.asc("student.department.id"))
                .addOrder(Order.asc("student.type.id"));
        return criteria.list();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO12#getStudentTypeLists(java.lang.String)
     */
    public List getStudentTypeLists(String departmentIds) {
        if (StringUtils.isEmpty(departmentIds)) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(QuestionnaireRecycleRate.class);
        criteria
                .add(Restrictions.in("department.id", SeqStringUtil.transformToLong(departmentIds)));
        criteria.setProjection(Projections.groupProperty("studentType"));
        return criteria.list();
    }
    
    /**
     * @see com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO12#getAllRateByCourseTask(java.lang.String,
     *      java.lang.String, java.util.Collection)
     */
    public List getAllRateByCourseTask(String departmentIds, String studentTypeIds,
            Collection teachCalendarSet) {
        if (StringUtils.isBlank(departmentIds) || StringUtils.isBlank(studentTypeIds)
                || null == teachCalendarSet || teachCalendarSet.size() < 1) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(CourseTake.class);
        criteria.createAlias("task", "task");
        criteria.createAlias("student", "student");
        criteria.add(Restrictions.isNotNull("task.questionnaire"));
        criteria.add(Restrictions.in("task.arrangeInfo.teachDepart.id", SeqStringUtil
                .transformToLong(departmentIds)));
        criteria.add(Restrictions.in("student.type.id", SeqStringUtil
                .transformToLong(studentTypeIds)));
        criteria.add(Restrictions.in("task.calendar", teachCalendarSet));
        criteria
                .setProjection(Projections
                        .projectionList()
                        .add(
                                Projections
                                        .sqlProjection(
                                                "sum(case when sfpjwc = 1 then 1 else 0 end) as pjrc,"
                                                        + "count(distinct(jxrwid||'-'||xsid)) as pjzrc,"
                                                        + "case when count(distinct(jxrwid || '-' || xsid)) = 0 then 0 else sum(case when sfpjwc = 1 then 1 else 0 end) / count(distinct(jxrwid || '-' || xsid)) * 100 end as bl",
                                                new String[] { "pjrc", "pjzrc", "bl" }, new Type[] {
                                                        Hibernate.INTEGER, Hibernate.INTEGER,
                                                        Hibernate.FLOAT }))
                        .add(
                                Projections
                                        .sqlProjection(
                                                "count (distinct(case when sfpjwc = 1 then xsid end)) as sprs,"
                                                        + "count(distinct xsid) as spzrs,"
                                                        + "case when count(distinct xsid) = 0 then 0 else count(distinct(case when sfpjwc = 1 then xsid end)) / count(distinct xsid) * 100 end as spbl",
                                                new String[] { "sprs", "spzrs", "spbl" },
                                                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER,
                                                        Hibernate.FLOAT })).add(
                                Projections.groupProperty("task.arrangeInfo.teachDepart")).add(
                                Projections.groupProperty("student.type")).add(
                                Projections.groupProperty("task.calendar")));
        return criteria.list();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.evaluate.QuestionnaireRecycleRateDAO12#getDetailInfoOfTask(java.lang.String,
     *      java.lang.String, java.lang.String, java.util.Collection, java.lang.String)
     */
    public List getDetailInfoOfTask(String departmentIds, String departmentId,
            String studentTypeIds, Collection teachCalendarSet, String studentOrTeacher) {
        if (StringUtils.isEmpty(departmentIds) || StringUtils.isEmpty(studentTypeIds)
                || null == teachCalendarSet) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getSession().createCriteria(CourseTake.class);
        criteria.createAlias("task", "task");
        String propertyString = "";
        if (studentOrTeacher.equals("student")) {
            propertyString = "task.teachClass.depart.id";
        } else {
            propertyString = "task.arrangeInfo.teachDepart.id";
        }
        if (StringUtils.isNotEmpty(departmentIds)) {
            Long[] departmentIdc = SeqStringUtil.transformToLong(departmentIds);
            criteria.add(Restrictions.in(propertyString, departmentIdc));
        }
        if (StringUtils.isNotEmpty(departmentId)) {
            Long departmentIdsingle = Long.valueOf(departmentId);
            criteria.add(Restrictions.eq(propertyString, departmentIdsingle));
        }
        if (StringUtils.isNotEmpty(studentTypeIds)) {
            Long[] studentTypeId = SeqStringUtil.transformToLong(studentTypeIds);
            criteria.add(Restrictions.in("task.teachClass.stdType.id", studentTypeId));
        }
        if (teachCalendarSet != null) {
            criteria.add(Restrictions.in("task.calendar", teachCalendarSet));
        }
        criteria.add(Restrictions.isNotNull("task.questionnaire"));
        criteria
                .setProjection(Projections
                        .projectionList()
                        .add(
                                Projections
                                        .sqlProjection(
                                                "count (distinct(case when sfpjwc = 1 then xsid end)) as sprs,"
                                                        + "count(distinct xsid) as spzrc,"
                                                        + "count (distinct(case when sfpjwc = 1 then xsid end))/count(distinct xsid)*100 as spbl",
                                                new String[] { "sprs", "spzrc", "spbl" },
                                                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER,
                                                        Hibernate.FLOAT })).add(
                                Projections.groupProperty("task")).add(
                                Projections.groupProperty(propertyString)).add(
                                Projections.groupProperty("task.teachClass.stdType")));
        criteria.addOrder(Order.asc(propertyString)).addOrder(
                Order.asc("task.teachClass.stdType.id"));
        return criteria.list();
    }
}
