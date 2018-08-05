//$Id: FeeDetailDAOHibernate.java,v 1.9 2006/12/19 10:09:10 duanth Exp $
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
 * chenweixiong              2005-11-6         Created
 *  
 ********************************************************************************/

package com.shufe.dao.fee.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.fee.FeeDetailDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author chenweixiong ,chaostone 收费明细数据存取实现
 */
public class FeeDetailDAOHibernate extends BasicHibernateDAO implements FeeDetailDAO {

    public List getFeeDetails(FeeDetail feeDetail) {
        Criteria criteria = getSession().createCriteria(FeeDetail.class);
        List criterions = CriterionUtils.getEntityCriterions(feeDetail);
        for (Iterator iter = criterions.iterator(); iter.hasNext();)
            criteria.add((Criterion) iter.next());
        criteria.createAlias("calendar", "calendar");
        criteria.addOrder(Order.desc("calendar.year")).addOrder(Order.asc("calendar.term"));
        return criteria.list();
    }

    /**
     * 根据学生号，学生姓名，教学日历得到一个收费描述的具体信息
     * 
     * @param studentId
     * @param studentName
     * @param techCalendar
     * 
     * @return
     */
    private Criteria getFeeDetailCriteria(FeeDetail feeDetail, String departIdSeq,
            String orderByName) {
        Criteria criteria = getSession().createCriteria(FeeDetail.class);
        // 添加学生条件
        List stdCriterions = null;
        if (null != feeDetail.getStd()) {
            stdCriterions = CriterionUtils.getEntityCriterions("std.", feeDetail.getStd());
            if (!stdCriterions.isEmpty()) {
                Criteria stdCriteria = criteria.createCriteria("std", "std");
                for (Iterator iter = stdCriterions.iterator(); iter.hasNext();) {
                    Criterion criterion = (Criterion) iter.next();
                    stdCriteria.add(criterion);
                }
            }
        }
        List criterions = CriterionUtils.getEntityCriterions(feeDetail, new String[] { "std" });
        for (Iterator iter = criterions.iterator(); iter.hasNext();)
            criteria.add((Criterion) iter.next());

        Long[] departmentId = SeqStringUtil.transformToLong(departIdSeq);
        criteria.add(Restrictions.in("depart.id", departmentId));
        if (StringUtils.isNotEmpty(orderByName)) {
            String[] orderName = StringUtils.split(orderByName, ",");
            Set createdAlias = new HashSet();
            if (null != stdCriterions) {
                createdAlias.add("std");
            }
            for (int i = 0; i < orderName.length; i++) {
                if (orderName[i].indexOf(".") > 0) {
                    String alias = orderName[i].substring(0, orderName[i].indexOf("."));
                    if (!createdAlias.contains(alias)) {
                        createdAlias.add(alias);
                        criteria.createAlias(alias, alias);
                    }
                }
                if (orderName[i].indexOf(" ") != -1) {
                    if (orderName[i].substring(orderName[i].indexOf(" ") + 1).equals("desc"))
                        criteria.addOrder(Order.desc(orderName[i].substring(0, orderName[i]
                                .indexOf(" "))));
                    else
                        criteria.addOrder(Order.asc(orderName[i].substring(0, orderName[i]
                                .indexOf(" "))));
                } else {
                    criteria.addOrder(Order.asc(orderName[i]));
                }
            }
        }
        return criteria;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.fee.FeeDetailService#getDetailInfoPagi(java.lang.String,
     *      com.shufe.model.fee.FeeDetail, boolean, boolean, boolean)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.fee.hibernate.FeeDetailDAO#getFeeDetails(com.shufe.model.fee.FeeDetail,
     *      java.lang.String, java.lang.String, int, int)
     */
    public Pagination getFeeDetails(FeeDetail feeDetail, String departIdSeq, String orderbyName,
            int pageNo, int pageSize) {
        return dynaSearch(getFeeDetailCriteria(feeDetail, departIdSeq, orderbyName), pageNo,
                pageSize);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.service.fee.FeeDetailService#getDetailInfoList(java.lang.String,
     *      com.shufe.model.fee.FeeDetail, java.lang.String)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.fee.hibernate.FeeDetailDAO#getFeeDetails(com.shufe.model.fee.FeeDetail,
     *      java.lang.String, java.lang.String)
     */
    public List getFeeDetails(FeeDetail feeDetailInfo, String departIdSeq, String orderByName) {
        return getFeeDetailCriteria(feeDetailInfo, departIdSeq, orderByName).list();
    }

    /**
     * 根据条件得到查询的信息.
     * 
     * @param feeDetail
     * @param feeTypeList
     * @param orderByName
     * 
     * @return
     */
    private Criteria getFeeDetailOfTypesCriteria(FeeDetail feeDetail, List feeTypeList,
            String orderByName) {
        Criteria criteria = getSession().createCriteria(FeeDetail.class);
        List criterions = CriterionUtils.getEntityCriterions(feeDetail);
        for (Iterator iter = criterions.iterator(); iter.hasNext();)
            criteria.add((Criterion) iter.next());

        String sqlstr = "";
        String[] alainColumn = new String[feeTypeList.size()];
        Type[] types = new Type[feeTypeList.size()];
        String groupString = "XH";
        for (int i = 0; i < feeTypeList.size(); i++) {
            FeeType feeType = (FeeType) feeTypeList.get(i);
            sqlstr += "sum(case when SFLXID = " + feeType.getId() + " then RMB else 0 end) as type"
                    + i + "_,";
            alainColumn[i] = "type" + i + "_";
            types[i] = Hibernate.FLOAT;
        }
        if (sqlstr.length() > 0) {
            sqlstr = sqlstr.substring(0, sqlstr.length() - 1);
        }
        if (StringUtils.isNotEmpty(orderByName)) {
            groupString = groupString + "," + orderByName;
        }
        ProjectionList projectionList = Projections.projectionList();
        if (sqlstr.length() > 0) {
            projectionList.add(Projections.property("student"));
            projectionList.add(Projections.sqlGroupProjection(sqlstr, groupString, alainColumn,
                    types));
        } else {
            projectionList.add(Projections.groupProperty("student"));
        }
        projectionList.add(Projections.groupProperty("year"))
                .add(Projections.groupProperty("term"));
        criteria.setProjection(projectionList);
        if (StringUtils.isNotEmpty(orderByName)) {
            String[] orderName = orderByName.split(",");
            for (int i = 0; i < orderName.length; i++) {
                criteria.addOrder(Order.asc(orderName[i]));
            }
        }
        criteria.addOrder(Order.desc("year")).addOrder(Order.asc("term"));
        return criteria;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.fee.hibernate.FeeDetailDAO#getFeeDetailOfTypes(com.shufe.model.fee.FeeDetail,
     *      java.util.List, java.lang.String, int, int)
     */
    public Pagination getFeeDetailOfTypes(FeeDetail feeDetail, List feeTypeList,
            String orderByName, int pageNo, int pageSize) {
        return dynaSearch(getFeeDetailOfTypesCriteria(feeDetail, feeTypeList, orderByName),
                pageNo, pageSize);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.fee.hibernate.FeeDetailDAO#getStdSelectCourses(java.lang.String,
     *      com.shufe.model.course.CourseTake,
     *      com.shufe.model.system.calendar.TeachCalendar)
     */
    public Criteria getStdSelectCourses(String departmentIds, CourseTake courseTake,
            TeachCalendar teachCalenar) {
        Criteria criteria = getSession().createCriteria(CourseTake.class);
        criteria.createAlias("task", "task");
        Long[] departmentId = SeqStringUtil.transformToLong(departmentIds);
        criteria.add(Restrictions.in("task.arrangeInfo.teachDepart.id", departmentId));
        if (courseTake != null) {
            List criterionOfStudent = CriterionUtils.getEntityCriterions("student.", courseTake
                    .getStudent());
            if (criterionOfStudent.size() > 0) {
                criteria.createAlias("student", "student");
                for (Iterator iter = criterionOfStudent.iterator(); iter.hasNext();)
                    criteria.add((Criterion) iter.next());
            }
        }
        if (teachCalenar != null && teachCalenar.getId() != null) {
            criteria.add(Restrictions.eq("task.calendar.id", teachCalenar.getId()));
        }
        criteria.addOrder(Order.asc("student.id"));
        return criteria;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.fee.hibernate.FeeDetailDAO#getDetailInfos(java.lang.String,
     *      java.lang.Long)
     */
    public List getDetailInfos(Long stdId, Long teachCalendarId) {
        Criteria criteria = getSession().createCriteria(FeeDetail.class);
        if (NotZeroNumberPredicate.INSTANCE.evaluate(stdId)) {
            criteria.add(Expression.eq("student.id", stdId));
        }
        if (teachCalendarId != null) {
            criteria.add(Expression.eq("calendar.id", teachCalendarId));
        }
        criteria.createAlias("type", "type").createAlias("currencyCategory", "currencyCategory");
        criteria.setProjection(Projections.projectionList().add(Projections.sum("payed")).add(
                Projections.sum("toRMB")).add(Projections.groupProperty("type.id")).add(
                Projections.groupProperty("currencyCategory.id")));
        return criteria.list();
    }
}
