//$Id: AnnotateDAOHibernate.java,v 1.2 2006/12/19 13:08:42 duanth Exp $
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
 * chenweixiong         2006-11-09          Created
 * zq                   2007-09-17          见下面的 FIXME 处
 ********************************************************************************/

package com.shufe.dao.degree.thesis.annotate.hibernate;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.thesis.annotate.AnnotateDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook;

public class AnnotateDAOHibernate extends BasicHibernateDAO implements AnnotateDAO {
    
    private void updateAnnotateOfDoubleBlind(Boolean isAffirm, String thesisManageSeq) {
        String hqlStr = "update Annotate set isDoubleBlind=" + isAffirm
                + " where id in(select annotate.id from ThesisManage where id in ("
                + thesisManageSeq + "))";
        if (StringUtils.isNotEmpty(thesisManageSeq)) {
            Query query = getSession().createQuery(hqlStr);
            query.executeUpdate();
        }
    }
    
    public void batchUpdateAnnotateDoubleBlind(Boolean isAffirm, String thesisManageSeq) {
        int maxTop = 0;
        int stringLength = thesisManageSeq.length();
        int splitlength = thesisManageSeq.split(",").length;
        if (StringUtils.isNotEmpty(thesisManageSeq)) {
            maxTop = stringLength / splitlength;
        }
        while (thesisManageSeq.split(",").length >= 1000) {
            String tempseq = thesisManageSeq.substring(0, thesisManageSeq
                    .indexOf(",", maxTop * 500));
            
            updateAnnotateOfDoubleBlind(isAffirm, tempseq);
            thesisManageSeq = thesisManageSeq
                    .substring(thesisManageSeq.indexOf(",", maxTop * 500) + 1);
        }
        updateAnnotateOfDoubleBlind(isAffirm, thesisManageSeq);
    }
    
    /*
     * 根据条件得到论文评阅书对象.
     * 
     * @see com.shufe.dao.thesis.annotate.AnnotateDAO#getAnnotateBooks(com.shufe.model.thesis.ThesisManage,
     *      java.lang.String, java.lang.String)
     */
    public Criteria getAnnotateBooks(ThesisAnnotateBook thesisAnnotateBook, String departmentIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = getSession().createCriteria(ThesisAnnotateBook.class);
        List criterions = CriterionUtils.getEntityCriterions(thesisAnnotateBook);
        ThesisManage thesisManage = thesisAnnotateBook.getThesisManage();
        if (null != thesisManage) {
            criterions.addAll(CriterionUtils.getEntityCriterions("thesisManage.", thesisManage));
            if (null != thesisManage.getStudent()) {
                List criterionStds = CriterionUtils.getEntityCriterions("student.", thesisManage
                        .getStudent(), new String[] { "type" });
                criterions.addAll(criterionStds);
                stdTypeIdSeq = intersectStdTypeIdSeq(thesisManage.getStudent().getType(),
                        stdTypeIdSeq);
            }
        }
        
        // 加载学生的部门和学生类别
        if (StringUtils.isNotEmpty(stdTypeIdSeq) || StringUtils.isNotEmpty(departmentIdSeq)) {
            criteria.createCriteria("thesisManage", "thesisManage");
            criteria.createAlias("thesisManage.student", "student");
        }
        if (StringUtils.isNotEmpty(stdTypeIdSeq)) {
            Long[] stdTypeIds = SeqStringUtil.transformToLong(stdTypeIdSeq);
            criteria.add(Restrictions.in("student.type.id", stdTypeIds));
        }
        if (StringUtils.isNotEmpty(departmentIdSeq)) {
            Long[] deparmentIds = SeqStringUtil.transformToLong(departmentIdSeq);
            criteria.add(Restrictions.in("student.department.id", deparmentIds));
        }
        criterions.add(Restrictions.isNotNull("thesisManage"));
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion criterion = (Criterion) iter.next();
            criteria.add(criterion);
        }
        return criteria;
    }
    
    public List getAnnotateBookList(ThesisAnnotateBook thesisAnnotateBook, String departmentIdSeq,
            String stdTypeIdSeq, String flag) {
        Criteria criteria = getAnnotateBooks(thesisAnnotateBook, departmentIdSeq, stdTypeIdSeq);
        if ("0".equals(flag)) {
            criteria.add(Restrictions.isNull("evaluateIdea.mark"));
        } else if ("1".equals(flag)) {
            criteria.add(Restrictions.isNotNull("evaluateIdea.mark"));
        }
        criteria.addOrder(Order.asc("serial"));
        return criteria.list();
    }
    
    public Pagination getAnnotateBookPagination(ThesisAnnotateBook thesisAnnotateBook,
            String departmentIdSeq, String stdTypeIdSeq, int pageNo, int pageSize, String flag) {
        Criteria criteria = getAnnotateBooks(thesisAnnotateBook, departmentIdSeq, stdTypeIdSeq);
        if ("0".equals(flag)) {
            criteria.add(Restrictions.isNull("evaluateIdea.mark"));
        } else if ("1".equals(flag)) {
            criteria.add(Restrictions.isNotNull("evaluateIdea.mark"));
        }
        criteria.addOrder(Order.asc("serial"));
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.dao.degree.thesis.annotate.AnnotateDAO#getAllMarkOfAnnotates(String, String,
     *      java.lang.String, java.lang.String)
     */
    public List getAllMarkOfAnnotates(String startNo, String endNo, String departmentIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = getSession().createCriteria(ThesisAnnotateBook.class);
        if (StringUtils.isNotEmpty(stdTypeIdSeq) || StringUtils.isNotEmpty(departmentIdSeq)) {
            criteria.createCriteria("thesisManage", "thesisManage");
            criteria.createAlias("thesisManage.student", "student");
        }
        if (StringUtils.isNotEmpty(stdTypeIdSeq)) {
            Long[] stdTypeIds = SeqStringUtil.transformToLong(stdTypeIdSeq);
            criteria.add(Restrictions.in("student.type.id", stdTypeIds));
        }
        if (StringUtils.isNotEmpty(departmentIdSeq)) {
            Long[] deparmentIds = SeqStringUtil.transformToLong(departmentIdSeq);
            criteria.add(Restrictions.in("student.department.id", deparmentIds));
        }
        criteria.add(Restrictions.between("serial", startNo, endNo));
        criteria.setProjection(Projections.projectionList().add(
                Projections.sum("evaluateIdea.mark")).add(
                Projections.groupProperty("thesisManage.annotate")));
        return criteria.list();
    }
    
    /**
     * 得到中总共的学生
     * 
     * @see com.shufe.dao.degree.thesis.annotate.AnnotateDAO#getStdNumOfLevel(com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook,
     *      java.lang.String, java.lang.String)
     */
    public List getStdNumOfLevel(ThesisAnnotateBook thesisAnnotateBook, String departmentIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = getAnnotateBooks(thesisAnnotateBook, departmentIdSeq, stdTypeIdSeq);
        criteria.setProjection(Projections.projectionList().add(
                Projections.countDistinct("thesisManage")).add(Projections.rowCount()).add(
                Projections.groupProperty("evaluateIdea.learningLevel")));
        return criteria.list();
    }
    
    /**
     * 得到分数求和得到 关于 分数和 分数个数, 默认规则 大于等于最小值 小于最大值
     * 
     * @param thesisAnnotateBook
     * @param departmentIdSeq
     * @param stdTypeIdSeq
     * @return
     */
    public List getStdNumOfAvgMark(ThesisAnnotateBook thesisAnnotateBook, String departmentIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = getAnnotateBooks(thesisAnnotateBook, departmentIdSeq, stdTypeIdSeq);
        criteria.setProjection(Projections.projectionList().add(
                Projections.sum("evaluateIdea.mark")).add(Projections.rowCount()).add(
                Projections.groupProperty("thesisManage")));
        return criteria.list();
    }
    
    /**
     * 得到不为空的成绩
     * 
     * @param thesisAnnotateBook
     * @param departmentIdSeq
     * @param stdTypeIdSeq
     * @return
     */
    public List getStdNumOfMark(ThesisAnnotateBook thesisAnnotateBook, String departmentIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = getAnnotateBooks(thesisAnnotateBook, departmentIdSeq, stdTypeIdSeq);
        criteria.add(Restrictions.isNotNull("evaluateIdea.mark"));
        criteria.setProjection(Projections.property("evaluateIdea.mark"));
        return criteria.list();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.degree.thesis.annotate.AnnotateDAO#batchUpdateDeleteBookNo(java.lang.String)
     */
    public void batchUpdateDeleteBookNo(String thesisManageIdSeq) {
        String hqlString = "delete from ThesisAnnotateBook where thesisManage.id in("
                + thesisManageIdSeq + ")";
        if (StringUtils.isNotBlank(thesisManageIdSeq)) {
            Query query = getSession().createQuery(hqlString);
            query.executeUpdate();
        }
    }
}
