//$Id: TopicOpenHibernateDAO.java,v 1.4 2007/01/26 10:02:13 cwx Exp $
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
 * hc                   2005-12-02          Created
 * zq                   2007-09-17          修改了doGetNoOpenedCriter()方法
 * zq                   2007-09-18          doGetNoOpenedCriter()方法中的学生类别，
 *                                          改为调用父类的stdTypeSearch()方法
 ********************************************************************************/

package com.shufe.dao.degree.thesis.topicOpen.hibernate;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.thesis.topicOpen.TopicOpenDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.std.Student;

public class TopicOpenHibernateDAO extends BasicHibernateDAO implements TopicOpenDAO {
    
    public void updateThesisOpen(Long topicOpenId, Boolean validate, Boolean tutor) {
        String topicOpenSeq = String.valueOf(topicOpenId);
        updateBatchThesisOpen(topicOpenSeq, validate, tutor);
    }
    
    public void updateBatchThesisOpen(String topicOpenSeq, Boolean validate, Boolean tutor) {
        String queryStr = "update TopicOpen set ";
        if (tutor.booleanValue()) {
            queryStr += " tutorAffirm=";
        } else {
            queryStr += " departmentAffirm=";
        }
        queryStr += validate.toString();
        queryStr += " where id in (" + topicOpenSeq + ")";
        if (StringUtils.isNotEmpty(topicOpenSeq)) {
            Query query = getSession().createQuery(queryStr);
            query.executeUpdate();
        }
        
    }
    
    public Pagination getStdsNoOpened(Student student, String departmentIdSeq, String stdTypeIdSeq,
            int pageNo, int pageSize) {
        Criteria criteria = doGetNoOpenedCriter(student, departmentIdSeq, stdTypeIdSeq);
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    /**
     * @param student
     * @param departmentIdSeq
     * @param stdTypeIdSeq
     * @return
     */
    private Criteria doGetNoOpenedCriter(Student student, String departmentIdSeq,
            String stdTypeIdSeq) {
        Criteria criteria = getSession().createCriteria(Student.class);
        if (StringUtils.isNotEmpty(departmentIdSeq)) {
            criteria.add(Restrictions.in("department.id", SeqStringUtil
                    .transformToLong(departmentIdSeq)));
        }
        
        stdTypeIdSeq = intersectStdTypeIdSeq(student.getType(), stdTypeIdSeq);
        if (StringUtils.isNotEmpty(stdTypeIdSeq)) {
            criteria.add(Restrictions.in("type.id", SeqStringUtil.transformToLong(stdTypeIdSeq)));
        }
        
        List criterions = CriterionUtils.getEntityCriterions("", student, new String[] { "type" });
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion criterion = (Criterion) iter.next();
            criteria.add(criterion);
        }
        criteria
                .add(Restrictions
                        .sqlRestriction("not exists(select * from Lw_lwgl_T xslw where {alias}.id=xslw.xsxxid and xslw.lwktid is not null)"));
        return criteria;
    }
    
    /**
     * 得到未开题的学生
     * 
     * @param student
     * @param departmentIdSeq
     * @param stdTypeIdSeq
     * @return
     */
    public List doGetNoOpenedList(Student student, String departmentIdSeq, String stdTypeIdSeq) {
        Criteria criteria = doGetNoOpenedCriter(student, departmentIdSeq, stdTypeIdSeq);
        return criteria.list();
    }
    
    /**
     * 这个批量执行里面所有的都是等于 其中实体的属性都是id Long型
     * 
     * @param entityName.
     * @param propertys
     * @param values
     * @param IdSeq
     */
    public void batchUpdateProperty(String entityName, String[] propertys, Object[] values,
            String IdSeq) {
        String updateString = "update " + entityName + " set ";
        for (int i = 0; i < propertys.length; i++) {
            if (i == (propertys.length - 1)) {
                updateString += propertys[i] + "=" + values[i] + " where id in(" + IdSeq + ")";
            } else {
                updateString += propertys[i] + "=" + values[i] + ",";
            }
        }
        if (StringUtils.isNotBlank(IdSeq)) {
            Query query = getSession().createQuery(updateString);
            query.executeUpdate();
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.thesis.topicOpen.TopicOpenDAO#batchUpdateThesisOpen(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void batchUpdate(String[] propertys, Object[] values, String selectSeq) {
        int maxTop = 0;
        int stringLength = selectSeq.length();
        int splitlength = selectSeq.split(",").length;
        if (StringUtils.isNotEmpty(selectSeq)) {
            maxTop = stringLength / splitlength;
        }
        while (selectSeq.split(",").length >= 1000) {
            String tempseq = selectSeq.substring(0, selectSeq.indexOf(",", maxTop * 500));
            batchUpdateProperty("TopicOpen", propertys, values, tempseq);
            selectSeq = selectSeq.substring(selectSeq.indexOf(",", maxTop * 500) + 1);
        }
        batchUpdateProperty("TopicOpen", propertys, values, selectSeq);
    }
    
    public void bactchUpdateAddressAndOpenOn(Long[] topicOpenIds, String address, Date openOn) {
        String sql = "update LW_KT_T set XSKTDD=:address,XSKTSJ=:openOn where id in(:ids)";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("address", address);
        query.setParameter("openOn", openOn);
        query.setParameterList("ids", topicOpenIds);
        query.executeUpdate();
    }
    
}
