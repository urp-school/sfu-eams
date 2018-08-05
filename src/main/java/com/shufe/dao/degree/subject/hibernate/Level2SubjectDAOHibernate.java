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
 * chaostone             2006-8-10            Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.subject.hibernate;

import java.util.Iterator;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.subject.Level2SubjectDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.degree.subject.Level2Subject;

public class Level2SubjectDAOHibernate extends BasicHibernateDAO implements Level2SubjectDAO {
    
    /**
     * 
     */
    public Pagination getLevel2Subjects(Level2Subject subject, int pageSize, int pageNo) {
        Criteria criteria = buildLevel2subjectCriteria(subject);
        if (null == subject || null == subject.getSpeciality()) {
            criteria.createAlias("speciality", "speciality");
            criteria.addOrder(Order.asc("speciality.code"));
        }
        return dynaSearch(criteria, pageNo, pageSize);
    }
    
    private Criteria buildLevel2subjectCriteria(Level2Subject subject) {
        Criteria criteria = getSession().createCriteria(Level2Subject.class);
        if (null != subject && null != subject.getLevel1Subject()) {
            subject.getLevel1Subject().setCode(null);
            List categoryCriterions = CriterionUtils.getEntityCriterions("level1Subject.", subject
                    .getLevel1Subject());
            if (!categoryCriterions.isEmpty()) {
                Criteria categoryCriteria = criteria.createAlias("level1Subject", "level1Subject");
                for (Iterator iter = categoryCriterions.iterator(); iter.hasNext();) {
                    categoryCriteria.add((Criterion) iter.next());
                }
            }
        }
        if (null != subject && null != subject.getSpeciality()) {
            subject.getSpeciality().setCode(null);
            List categoryCriterions = CriterionUtils.getEntityCriterions("speciality.", subject
                    .getSpeciality());
            if (!categoryCriterions.isEmpty()) {
                Criteria categoryCriteria = criteria.createAlias("speciality", "speciality");
                for (Iterator iter = categoryCriterions.iterator(); iter.hasNext();) {
                    categoryCriteria.add((Criterion) iter.next());
                }
            }
        }
        
        List criterions = CriterionUtils.getEntityCriterions(subject);
        for (Iterator iter = criterions.iterator(); iter.hasNext();) {
            Criterion criterion = (Criterion) iter.next();
            criteria.add(criterion);
        }
        return criteria;
    }
    
    public List getLevel2Subjects(Level2Subject subject) {
        Criteria criteria = buildLevel2subjectCriteria(subject);
        criteria.createAlias("level1Subject", "level1Subject");
        criteria.createAlias("level1Subject.category", "category");
        criteria.addOrder(Order.asc("category.code"));
        return criteria.list();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.subject.Level2SubjectDAO#getDepartsOfSpeciality(com.shufe.model.subject.Level2Subject)
     */
    public List getDepartsOfSpeciality(Level2Subject level2Subject) {
        Criteria criteria = buildLevel2subjectCriteria(level2Subject);
        criteria.createCriteria("speciality", "speciality");
        criteria.setProjection(Projections.groupProperty("speciality.department"));
        return criteria.list();
    }
    
    /**
     * 只支持直接属性
     * 
     * @see com.shufe.dao.degree.subject.Level2SubjectDAO#getPropertyOfSubject(com.shufe.model.degree.subject.Level2Subject,
     *      java.lang.String, java.lang.Boolean)
     */
    public List getPropertyOfSubject(Level2Subject level2Subject, String propertys, Boolean isCount) {
        Criteria criteria = buildLevel2subjectCriteria(level2Subject);
        ProjectionList projectionList = Projections.projectionList();
        String[] property = StringUtils.split(propertys, ",");
        for (int i = 0; i < property.length; i++) {
            projectionList.add(Property.forName(property[i]));
        }
        if (isCount.booleanValue()) {
            projectionList.add(Projections.rowCount());
        }
        criteria.setProjection(projectionList);
        return criteria.list();
    }
    
}
