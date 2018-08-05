//$Id: PreAnswerDAOHibernate.java,v 1.1 2007/01/24 14:16:43 cwx Exp $
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
 * chenweixiong         2006-12-11          Created
 * zq                   2007-09-17          修改了getExampleOfPreAnsWer()方法
 * zq                   2007-09-18          修改了getExampleOfPreAnsWer()方法
 ********************************************************************************/

package com.shufe.dao.degree.thesis.preAnswer.hibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.thesis.preAnswer.PreAnswerDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.PreAnswer;

public class PreAnswerDAOHibernate extends BasicHibernateDAO implements PreAnswerDAO {
    
    public void updateTimeAndAddress(String preAnswerIdSeq, String answerTime, String anwerAddress) {
        
        String hqlString = "update PreAnswer set answerTime=:aTime,answerAddress=:aAddress where"
                + "         id in(:aIds)";
        if (StringUtils.isNotEmpty(preAnswerIdSeq)) {
            Query query = getSession().createQuery(hqlString);
            query.setDate("aTime", Date.valueOf(answerTime));
            query.setString("aAddress", anwerAddress);
            query.setParameterList("aIds", SeqStringUtil.transformToLong(preAnswerIdSeq));
            query.executeUpdate();
        }
    }
    
    /**
     * 批量设置时间和地点
     * 
     * @see com.shufe.dao.degree.thesis.preAnswer.PreAnswerDAO#batchUpdateTimeAndAddress(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void batchUpdateTimeAndAddress(String preAnswerIdSeq, String answerTime,
            String anwerAddress) {
        int maxTop = 0;
        int stringLength = preAnswerIdSeq.length();
        int splitlength = preAnswerIdSeq.split(",").length;
        if (StringUtils.isNotEmpty(preAnswerIdSeq)) {
            maxTop = stringLength / splitlength;
        }
        while (preAnswerIdSeq.split(",").length > 1000) {
            String tempseq = preAnswerIdSeq.substring(0, preAnswerIdSeq.indexOf(",", maxTop * 500));
            updateTimeAndAddress(tempseq, answerTime, anwerAddress);
            preAnswerIdSeq = preAnswerIdSeq
                    .substring(preAnswerIdSeq.indexOf(",", maxTop * 500) + 1);
        }
        updateTimeAndAddress(preAnswerIdSeq, answerTime, anwerAddress);
    }
    
    /**
     * 教师所带的学生不会超过1000个.
     * 
     * @see com.shufe.dao.degree.thesis.preAnswer.PreAnswerDAO#batchUpdateTutorAffirm(java.lang.String,
     *      java.lang.Boolean)
     */
    public void updateTutorAffirm(String preAnswerIdSeq, Boolean flag) {
        String hqlString = "update PreAnswer set isTutorAffirm=" + flag + " where id in("
                + preAnswerIdSeq + ")";
        if (StringUtils.isNotEmpty(preAnswerIdSeq)) {
            Query query = getSession().createQuery(hqlString);
            query.executeUpdate();
        }
    }
    
    /*
     * 批量设置教师确认字段
     * 
     * @see com.shufe.dao.thesis.preAnswer.PreAnswerDAO#batchUpdateTutorAffirm(java.lang.String,
     *      java.lang.Boolean)
     */
    public void batchUpdateTutorAffirm(String preAnswerIdSeq, Boolean flag) {
        int maxTop = 0;
        int stringLength = preAnswerIdSeq.length();
        int splitlength = preAnswerIdSeq.split(",").length;
        if (StringUtils.isNotEmpty(preAnswerIdSeq)) {
            maxTop = stringLength / splitlength;
        }
        while (preAnswerIdSeq.split(",").length > 1000) {
            String tempseq = preAnswerIdSeq.substring(0, preAnswerIdSeq.indexOf(",", maxTop * 500));
            updateTutorAffirm(tempseq, flag);
            preAnswerIdSeq = preAnswerIdSeq
                    .substring(preAnswerIdSeq.indexOf(",", maxTop * 500) + 1);
        }
        updateTutorAffirm(preAnswerIdSeq, flag);
    }
    
    /**
     * @param preAnswer
     * @param departIdSeq
     * @param stdTypeIdSeq
     * @return
     */
    public Criteria getExampleOfPreAnsWer(PreAnswer preAnswer, String departIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = getSession().createCriteria(PreAnswer.class);
        List criterions = CriterionUtils
                .getEntityCriterions(preAnswer, new String[] { "isPassed" });
        if (null != preAnswer.getIsPassed()) {
            criteria.add(Restrictions.eq("preAnswerSet.isPassed", preAnswer.getIsPassed()));
        }
        if (null != preAnswer.getThesisManage() && null != preAnswer.getThesisManage().getStudent()) {
            criteria.createAlias("thesisManage", "thesisManage");
            criteria.createAlias("thesisManage.student", "student");
            
            stdTypeIdSeq = intersectStdTypeIdSeq(
                    preAnswer.getThesisManage().getStudent().getType(), stdTypeIdSeq);
            criteria.add(Restrictions.in("student.type.id", SeqStringUtil
                    .transformToLong(stdTypeIdSeq)));
            criteria.add(Restrictions.in("student.department.id", SeqStringUtil
                    .transformToLong(departIdSeq)));
            List criterionStds = CriterionUtils.getEntityCriterions("student.", preAnswer
                    .getThesisManage().getStudent(), new String[] { "type" });
            criterions.addAll(criterionStds);
        }
        if (null != preAnswer.getThesisManage()
                && ValidEntityPredicate.getInstance().evaluate(
                        preAnswer.getThesisManage().getTutor())) {
            criteria.add(Restrictions.eq("thesisManage.tutor.id", preAnswer.getThesisManage()
                    .getTutor().getId()));
        }
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion temp = (Criterion) iter.next();
            criteria.add(temp);
        }
        return criteria;
    }
    
    /**
     * 得到预答辩的list列表
     * 
     * @param preAnswer
     * @param departIdSeq
     * @param stdTypeIdSeq
     * @return
     */
    public List getPreAnswers(PreAnswer preAnswer, String departIdSeq, String stdTypeIdSeq) {
        if (StringUtils.isBlank(departIdSeq) || StringUtils.isBlank(stdTypeIdSeq)) {
            return Collections.EMPTY_LIST;
        }
        return getExampleOfPreAnsWer(preAnswer, departIdSeq, stdTypeIdSeq).list();
    }
    
    /**
     * TODO 只能是preAnswer的当前属性
     * 
     * @param preAnswer
     * @param departIdSeq
     * @param stdTypeIdSeq
     * @param property
     * @return
     */
    public List getPropertyList(PreAnswer preAnswer, String departIdSeq, String stdTypeIdSeq,
            String property) {
        if (StringUtils.isBlank(property)) {
            return Collections.EMPTY_LIST;
        }
        Criteria criteria = getExampleOfPreAnsWer(preAnswer, departIdSeq, stdTypeIdSeq);
        criteria.setProjection(Projections.property(property));
        return criteria.list();
    }
    
    /**
     * @see com.shufe.dao.degree.thesis.preAnswer.PreAnswerDAO#getPreAnswerNoApply(com.shufe.model.degree.thesis.ThesisManage,
     *      java.lang.String, java.lang.String)
     */
    public Criteria getPreAnswerNoApply(ThesisManage thesisManage, String departIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = getSession().createCriteria(ThesisManage.class);
        criteria.createAlias("student", "student");
        if (ValidEntityPredicate.INSTANCE.evaluate(thesisManage.getTutor())) {
            criteria.add(Restrictions.eq("tutor.id", thesisManage.getTutor().getId()));
        }
        criteria.add(Restrictions.in("student.department.id", SeqStringUtil
                .transformToLong(departIdSeq)));
        criteria.add(Restrictions
                .in("student.type.id", SeqStringUtil.transformToLong(stdTypeIdSeq)));
        List criterions = CriterionUtils.getEntityCriterions("student.", thesisManage.getStudent());
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion criterion = (Criterion) iter.next();
            criteria.add(criterion);
        }
        criteria
                .add(Restrictions
                        .sqlRestriction("not exists(select * from lw_ydb_T lwydb where {alias}.id=lwydb.lwglid)"));
        return criteria;
    }
    
    /**
     * 得到未申请预答辩的学生
     * 
     * @see com.shufe.dao.degree.thesis.preAnswer.PreAnswerDAO#getPaginationOfNoApply(com.shufe.model.degree.thesis.ThesisManage,
     *      java.lang.String, java.lang.String, int, int)
     */
    public Pagination getPaginationOfNoApply(ThesisManage thesisManage, String departIdSeq,
            String stdTypeIdSeq, int pageNo, int pageSize) {
        if (StringUtils.isBlank(departIdSeq) || StringUtils.isBlank(stdTypeIdSeq)) {
            Pagination pagination = new Pagination(new Result(0, new ArrayList()));
            return pagination;
        }
        return dynaSearch(getPreAnswerNoApply(thesisManage, departIdSeq, stdTypeIdSeq), pageNo,
                pageSize);
    }
    
    /**
     * @see com.shufe.dao.degree.thesis.preAnswer.PreAnswerDAO#getStdsOfNoApply(com.shufe.model.degree.thesis.ThesisManage,
     *      java.lang.String, java.lang.String)
     */
    public List getStdsOfNoApply(ThesisManage thesisManage, String departIdSeq, String stdTypeIdSeq) {
        if (StringUtils.isBlank(departIdSeq) || StringUtils.isBlank(stdTypeIdSeq)) {
            return Collections.EMPTY_LIST;
        }
        Criteria notApplyStudent = getPreAnswerNoApply(thesisManage, departIdSeq, stdTypeIdSeq);
        notApplyStudent.setProjection(Projections.property("student"));
        return notApplyStudent.list();
    }
}
