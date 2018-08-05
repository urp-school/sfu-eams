//$Id: QuestionnaireStatHibernateDAO.java,v 1.1 2007-3-19 上午11:47:12 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.dao.quality.evaluate.hibernate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.quality.evaluate.QuestionnaireStatDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.quality.evaluate.QuestionTypeStat;
import com.shufe.model.quality.evaluate.QuestionnaireStat;

public class QuestionnaireStatDAOHibernate extends BasicHibernateDAO implements
        QuestionnaireStatDAO {

    public int batchDeleteQuestionnaireStat(String stdTypeIdSeq,
            String departIdSeq, String calendarIdSeq) {
        // 删除问卷统计的子项目前先删除它的子项目
        String condition = " where depart.id in(" + departIdSeq
                + ") and stdType.id in(" + stdTypeIdSeq
                + ") and calendar.id in(" + calendarIdSeq + ")";
        //
        int a = 0;
        String beforeString = "delete QuestionTypeStat where questionnaireStat.id in(select id from QuestionnaireStat"
                + condition + ")";
        String deleteString = "delete QuestionnaireStat " + condition;
        if (StringUtils.isNotBlank(departIdSeq)
                && StringUtils.isNotBlank(stdTypeIdSeq)
                && StringUtils.isNotBlank(calendarIdSeq)) {
            Query before = getSession().createQuery(beforeString);
            before.executeUpdate();
            Query query = getSession().createQuery(deleteString);
            a = query.executeUpdate();
        }
        return a;
    }

    public List getQuestionTypeStats(String stdTypeIdSeq, String departIdSeq,
            String calendarIdSeq, Boolean statState) {
        if (StringUtils.isBlank(departIdSeq)
                || StringUtils.isBlank(stdTypeIdSeq)
                || StringUtils.isBlank(calendarIdSeq)) {
            return Collections.EMPTY_LIST;
        }
        String hql = "select sum(questionResult.question.score),sum(questionResult.score),count(distinct result.student.id),"
                + "result.teacher.id,result.task.id,questionResult.questionType.id"
                + " from EvaluateResult result join result.questionResultSet as questionResult"
                + " where result.statState="
                + (Boolean.TRUE.equals(statState) ? "true" : "false")
                + " and result.department.id in(:departIds) and result.stdType.id in(:stdTypeIds)"
                + " and result.teachCalendar.id in(:calendarIds) "
                + "group by result.teacher.id,result.task.id,questionResult.questionType.id"
                + " order by result.teacher.id,result.task.id,questionResult.questionType.id";
        EntityQuery query = new EntityQuery(hql);
        Map params = new HashMap();
        params.put("departIds", SeqStringUtil.transformToLong(departIdSeq));
        params.put("stdTypeIds", SeqStringUtil.transformToLong(stdTypeIdSeq));
        params.put("calendarIds", SeqStringUtil.transformToLong(calendarIdSeq));
        query.setParams(params);
        return (List) utilDao.search(query);
    }

    public List getQuestionTypes(String stdTypeIdSeq, String departIdSeq,
            Collection teachCalendars) {
        Criteria criteria = getSession().createCriteria(QuestionTypeStat.class);
        criteria.createAlias("questionnaireStat", "questionnaireStat");
        criteria.add(Restrictions.in("questionnaireStat.stdType.id",
                SeqStringUtil.transformToLong(stdTypeIdSeq)));
        criteria.add(Restrictions.in("questionnaireStat.depart.id",
                SeqStringUtil.transformToLong(departIdSeq)));
        if (null != teachCalendars && !teachCalendars.isEmpty()) {
            criteria.add(Restrictions.in("questionnaireStat.calendar",
                    teachCalendars));
        }
        criteria.setProjection(Projections.groupProperty("type"));
        return criteria.list();
    }

    private Criteria getQuestionnaireStats(String stdTypeIdSeq,
            String departIdSeq, String calendarIdSeq,
            QuestionnaireStat questionnaireStat) {
        Criteria criteria = getSession()
                .createCriteria(QuestionnaireStat.class);
        List criterions = CriterionUtils.getEntityCriterions(questionnaireStat);
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion element = (Criterion) iter.next();
            criteria.add(element);
        }
        if (StringUtils.isNotBlank(departIdSeq)) {
            criteria.add(Restrictions.in("depart.id", SeqStringUtil
                    .transformToLong(departIdSeq)));
        }
        if (StringUtils.isNotBlank(stdTypeIdSeq)) {
            criteria.add(Restrictions.in("stdType.id", SeqStringUtil
                    .transformToLong(stdTypeIdSeq)));
        }
        if (StringUtils.isNotBlank(calendarIdSeq)) {
            criteria.add(Restrictions.in("calendar.id", SeqStringUtil
                    .transformToLong(calendarIdSeq)));
        }
        return criteria;
    }

    /**
     * 得到部门所关联的数据list
     * 
     * @see com.shufe.dao.quality.evaluate.QuestionnaireStatDAO#getDataByDepartAndRelated(java.lang.String,
     *      java.lang.String, java.lang.Long)
     */
    public List getDataByDepartAndRelated(String stdTypeIdSeq,
            String departIdSeq, Long calendarIdSeq) {
        Criteria criteria = getQuestionnaireStats(stdTypeIdSeq, departIdSeq,
                String.valueOf(calendarIdSeq), null);
        criteria.setProjection(Projections.projectionList().add(
                Projections.groupProperty("score")).add(
                Projections.groupProperty("depart")).add(
                Projections.groupProperty("teacher.id")).add(
                Projections.groupProperty("task.id")));
        criteria.addOrder(Order.asc("depart.id"));
        return criteria.list();
    }

    /**
     * @see com.shufe.dao.quality.evaluate.QuestionnaireStatDAO#getDatasForPieChart(java.lang.String,
     *      java.lang.Long, java.lang.Long)
     */
    public List getDatasForPieChart(String stdTypeIdSeq, Long departId,
            Long calendarId) {
        Criteria criteria = getQuestionnaireStats(stdTypeIdSeq, String
                .valueOf(departId), String.valueOf(calendarId), null);
        criteria.setProjection(Projections.projectionList().add(
                Projections.groupProperty("score")).add(
                Projections.groupProperty("teacher")).add(
                Projections.groupProperty("task")));
        return criteria.list();
    }

}
