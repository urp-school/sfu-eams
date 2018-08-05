//$Id: GraduateHibernateDAO.java,v 1.1 2006/08/02 00:53:15 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-11         Created
 *  
 ********************************************************************************/

package com.shufe.dao.graduate;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.util.NotEmptyPropertySelector;

/**
 * @author dell
 */
public class GraduateHibernateDAO extends BasicHibernateDAO implements GraduateDAO {
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.graduate.GraduateDAO#searchAuditStandard(com.shufe.model.graduate.AuditStandard,
     *      int, int)
     */
    public Pagination searchAuditStandard(AuditStandard auditStandard, int pageNo, int pageSize) {
        return dynaSearch(this.generateAuditStandardCriteria(auditStandard), pageNo, pageSize);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.graduate.GraduateDAO#searchAuditStandardWithNull(com.shufe.model.graduate.AuditStandard)
     */
    public List searchAuditStandardWithNull(AuditStandard auditStandard) {
        return generateAuditStandardCriteriaWithNull(auditStandard).list();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.graduate.GraduateDAO#countAuditStandardWithNull(com.shufe.model.graduate.AuditStandard)
     */
    public int countAuditStandardWithNull(AuditStandard auditStandard) {
        Criteria criteria = this.generateAuditStandardCriteriaWithNull(auditStandard);
        if (auditStandard.getId() != null) {
            criteria.add(Restrictions.sqlRestriction("{alias}.auditStandardId != ?", auditStandard
                    .getId(), Hibernate.LONG));
        }
        return ((Number) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
    }
    
    /**
     * 根据所给毕业审核标准实例 生成动态查询对象
     * 
     * @param auditStandard
     * @return Criteria
     */
    private Criteria generateAuditStandardCriteria(AuditStandard auditStandard) {
        Criteria criteria = getSession().createCriteria(AuditStandard.class);
        criteria.add(Example.create(auditStandard).setPropertySelector(
                new NotEmptyPropertySelector()));
        StudentType studentType = auditStandard.getStudentType();
        if (studentType == null) {
            studentType = (StudentType) EntityUtils.getInstance(StudentType.class);
        }
        if (ValidEntityPredicate.INSTANCE.evaluate(studentType)) {
            criteria.add(Restrictions.eq("studentType.id", studentType.getId()));
        }
        return criteria;
    }
    
    /**
     * 根据所给毕业审核标准实例 生成动态查询对象 如果关联的对象为null 那么添加条件 "=null"
     * 
     * @param auditStandard
     * @return Criteria
     */
    private Criteria generateAuditStandardCriteriaWithNull(AuditStandard auditStandard) {
        Criteria criteria = getSession().createCriteria(AuditStandard.class);
        if (ValidEntityPredicate.INSTANCE.evaluate(auditStandard.getStudentType())) {
            criteria.add(Restrictions.eq("studentType.id", auditStandard.getStudentType().getId()));
        }
        return criteria;
    }
    
    public List searchAuditStandard(AuditStandard auditStandard) {
        return generateAuditStandardCriteria(auditStandard).list();
    }
    
}
