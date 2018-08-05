//$Id: ThesisManageHibernateDAO.java,v 1.7 2007/01/24 14:16:43 cwx Exp $
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
 * chenweixiong         2006-10-19          Created
 * zq                   2007-09-17          修改了getThesisManageCriteria()方法
 * zq                   2007-09-18          修改了getThesisManageCriteria()方法
 ********************************************************************************/

package com.shufe.dao.degree.thesis.hibernate;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Component;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.thesis.ThesisManageDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook;
import com.shufe.model.degree.thesis.answer.PreAnswer;
import com.shufe.model.degree.thesis.topicOpen.TopicOpen;

public class ThesisManageHibernateDAO extends BasicHibernateDAO implements ThesisManageDAO {
    
    public Criteria getThesisManageCriteria(ThesisManage thesisManage, String deparmentSeq,
            String stdTypeSeq) {
        Criteria criteria = getSession().createCriteria(ThesisManage.class);
        List criterions = CriterionUtils.getEntityCriterions(thesisManage);
        if (null != thesisManage.getStudent()) {
            List criterionStds = CriterionUtils.getEntityCriterions("student.", thesisManage
                    .getStudent(), new String[] { "type" });
            if (StringUtils.isNotEmpty(stdTypeSeq) || StringUtils.isNotEmpty(deparmentSeq)
                    || criterionStds.size() > 0) {
                criteria.createAlias("student", "student");
            }
            
            stdTypeSeq = intersectStdTypeIdSeq(thesisManage.getStudent().getType(), stdTypeSeq);
            
            if (StringUtils.isNotEmpty(stdTypeSeq)) {
                criteria.add(Restrictions.in("student.type.id", SeqStringUtil
                        .transformToLong(stdTypeSeq)));
            }
            if (StringUtils.isNotEmpty(deparmentSeq)) {
                Long[] deparmentIds = SeqStringUtil.transformToLong(deparmentSeq);
                criteria.add(Restrictions.in("student.department.id", deparmentIds));
            }
            
            criterions.addAll(criterionStds);
        }
        if (null != thesisManage.getTopicOpen()) {
            TopicOpen topicOpen = thesisManage.getTopicOpen();
            List criterionTopicOpens = CriterionUtils.getEntityCriterions("topicOpen.", topicOpen,
                    new String[] { "isPassed" });
            if (new Integer(4).equals(topicOpen.getIsPassed())) {
                criteria.add(Restrictions.isNull("topicOpen.isPassed"));
            } else if (null == topicOpen.getIsPassed()
                    || new Integer(0).equals(topicOpen.getIsPassed())) {
            } else {
                criteria.add(Restrictions.eq("topicOpen.isPassed", topicOpen.getIsPassed()));
            }
            if (null != topicOpen.getIsPassed() || criterionTopicOpens.size() > 0) {
                criteria.createAlias("topicOpen", "topicOpen");
            }
            criterions.addAll(criterionTopicOpens);
        }
        if (thesisManage.getPreAnswerSet().iterator().hasNext()) {
            PreAnswer preAnswer = (PreAnswer) thesisManage.getPreAnswerSet().iterator().next();
            List criteriaAnswers = CriterionUtils.getEntityCriterions("preAnswerSet.", preAnswer,
                    new String[] { "isPassed" });
            if (null != preAnswer.getIsPassed()) {
                criteria.add(Restrictions.eq("preAnswerSet.isPassed", preAnswer.getIsPassed()));
            }
            if (null != preAnswer.getIsPassed() || criteriaAnswers.size() > 0) {
                criteria.createAlias("preAnswerSet", "preAnswerSet");
            }
            criterions.addAll(criteriaAnswers);
        }
        if (null != thesisManage.getAnnotate()) {
            List criteriaAnswers = CriterionUtils.getEntityCriterions("annotate.", thesisManage
                    .getAnnotate());
            if (null != thesisManage.getAnnotate().getAnnotateBooks()
                    && thesisManage.getAnnotate().getAnnotateBooks().size() > 0) {
                ThesisAnnotateBook annotateBook = (ThesisAnnotateBook) thesisManage.getAnnotate()
                        .getAnnotateBooks().iterator().next();
                criteria.createAlias("annotate.annotateBooks", "book");
                criteriaAnswers.add(Restrictions.like("book.serial", annotateBook.getSerial(),
                        MatchMode.ANYWHERE));
            }
            if (criteriaAnswers.size() > 0) {
                criteria.createAlias("annotate", "annotate");
            }
            criterions.addAll(criteriaAnswers);
        }
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion element = (Criterion) iter.next();
            criteria.add(element);
        }
        return criteria;
    }
    
    /**
     * 组装一个论文对象里面非空属性的论文criteria
     * 
     * @param thesisManage
     * @param deparmentSeq
     * @param stdTypeSeq
     * @param notNulls
     * @return
     */
    public Criteria getThesisManageAttributeNotNull(ThesisManage thesisManage, String deparmentSeq,
            String stdTypeSeq, String notNulls, String nulls) {
        Criteria criteria = getThesisManageCriteria(thesisManage, deparmentSeq, stdTypeSeq);
        if (StringUtils.isNotEmpty(notNulls)) {
            String[] notNull = StringUtils.split(notNulls, ",");
            for (int i = 0; i < notNull.length; i++) {
                int count = notNull[i].indexOf(".");
                while (count != -1) {
                    String temp = notNull[i].substring(0, count);
                    try {
                        if (!PropertyUtils.getPropertyType(thesisManage, temp).equals(
                                Component.class)) {
                            criteria.createAlias(temp, temp);
                            count = notNull[i].indexOf(".", count + 1);
                        }
                    } catch (Exception e) {
                    }
                }
                criteria.add(Restrictions.isNotNull(notNull[i]));
            }
        }
        if (StringUtils.isNotEmpty(nulls)) {
            String[] nullSeq = StringUtils.split(nulls, ",");
            for (int i = 0; i < nullSeq.length; i++) {
                int count = nullSeq[i].indexOf(".");
                while (count != -1) {
                    String temp = nullSeq[i].substring(0, count);
                    try {
                        if (!PropertyUtils.getPropertyType(thesisManage, temp).equals(
                                Component.class)) {
                            criteria.createAlias(temp, temp);
                            count = nullSeq[i].indexOf(".", count + 1);
                        }
                    } catch (Exception e) {
                    }
                }
                criteria.add(Restrictions.isNull(nullSeq[i]));
            }
        }
        return criteria;
    }
    
    /**
     * @see com.shufe.dao.degree.thesis.ThesisManageDAO#getThesisManages(com.shufe.model.degree.thesis.ThesisManage)
     */
    public List getThesisManages(ThesisManage thesisManage, String deparmentSeq, String stdTypeSeq) {
        Criteria criteria = getThesisManageCriteria(thesisManage, deparmentSeq, stdTypeSeq);
        return criteria.list();
    }
    
    public List getThesisManagesNotNull(ThesisManage thesisManage, String deparmentSeq,
            String stdTypeSeq, String notNulls, String nulls) {
        return getThesisManageAttributeNotNull(thesisManage, deparmentSeq, stdTypeSeq, notNulls,
                nulls).list();
    }
    
    /**
     * @see com.shufe.dao.degree.thesis.ThesisManageDAO#getThesisManagePagis(com.shufe.model.degree.thesis.ThesisManage,
     *      int, int)
     */
    public Pagination getThesisManagePagis(ThesisManage thesisManage, String deparmentSeq,
            String stdTypeSeq, int pageNo, int pageSize) {
        return dynaSearch(getThesisManageCriteria(thesisManage, deparmentSeq, stdTypeSeq), pageNo,
                pageSize);
    }
    
    /**
     * 得到所有制定对象非空的 论文管理对象.
     * 
     * @param thesisManage
     * @param deparmentSeq
     * @param stdTypeSeq
     * @param pageNo
     * @param pageSize
     * @param notNulls
     * @return
     */
    public Pagination getThesisManagePaginaForNotNull(ThesisManage thesisManage,
            String deparmentSeq, String stdTypeSeq, int pageNo, int pageSize, String notNulls,
            String nulls, String orderString) {
        
        Criteria criteria = getThesisManageAttributeNotNull(thesisManage, deparmentSeq, stdTypeSeq,
                notNulls, nulls);
        
        if (StringUtils.isNotBlank(deparmentSeq) && StringUtils.isNotBlank(stdTypeSeq)
                && StringUtils.isNotBlank(orderString)) {
            if (orderString.indexOf("department") != -1) {
                criteria.createCriteria("student.department", "department");
            }
            if (orderString.indexOf("type") != -1) {
                criteria.createCriteria("student.type", "type");
            }
            if (orderString.indexOf("major") != -1) {
                criteria.createCriteria("student.firstMajor", "major");
            }
            List orders = com.ekingstar.commons.query.OrderUtils.parser(orderString);
            for (int i = 0; i < orders.size(); i++) {
                com.ekingstar.commons.query.Order o = (com.ekingstar.commons.query.Order) orders
                        .get(i);
                if (o.getDirection() == com.ekingstar.commons.query.Order.ASC) {
                    criteria.addOrder(Order.asc(o.getProperty()));
                } else {
                    criteria.addOrder(Order.desc(o.getProperty()));
                }
            }
        }
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    /**
     * 根据条件得到相关字段的投影.
     * 
     * @see com.shufe.dao.degree.thesis.ThesisManageDAO#getThesisTopicOpen(com.shufe.model.degree.thesis.ThesisManage,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public List getProjectionConditions(ThesisManage thesisManage, String departmentSeq,
            String stdTypeSeq, String projectionNames) {
        Criteria criteria = getThesisManageCriteria(thesisManage, departmentSeq, stdTypeSeq);
        if (StringUtils.isNotEmpty(projectionNames)) {
            String[] projections = StringUtils.split(projectionNames, ",");
            ProjectionList projects = Projections.projectionList();
            for (int i = 0; i < projections.length; i++) {
                projects.add(Projections.groupProperty(projections[i]));
            }
            criteria.setProjection(projects);
        }
        return criteria.list();
    }
}
