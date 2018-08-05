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
 * chaostone             2006-3-24            Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.plan.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.query.hibernate.HibernateQuerySupport;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.plan.CourseGroupDAO;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.TeachPlan;

/**
 * 课程组信息存取实现类
 * 
 * @author chaostone
 */

public class CourseGroupDAOHibernate extends BasicHibernateDAO implements CourseGroupDAO {
    
    /**
     * @see com.shufe.dao.course.plan.CourseGroupDAO#getCourseGroup(java.lang.Long)
     */
    public CourseGroup getCourseGroup(Long groupId) {
        return (CourseGroup) get(CourseGroup.class, groupId);
    }
    
    /**
     * @see com.shufe.dao.course.plan.CourseGroupDAO#removeCourseGroup(java.lang.Long)
     */
    public void removeCourseGroup(Long groupId) {
        remove(CourseGroup.class, groupId);
    }
    
    /**
     * @see com.shufe.dao.course.plan.CourseGroupDAO#saveCourseGroup(com.shufe.model.course.plan.CourseGroup)
     */
    public void saveCourseGroup(CourseGroup group) {
        saveOrUpdate(group);
    }
    
    /**
     * @see com.shufe.dao.course.plan.CourseGroupDAO#updateCourseGroup(com.shufe.model.course.plan.CourseGroup)
     */
    public void updateCourseGroup(CourseGroup group) {
        update(group);
    }
    
    /**
     * @see com.shufe.dao.course.plan.CourseGroupDAO#getCourseGroupsOfStd(CourseType,
     *      com.shufe.model.course.plan.TeachPlan)
     */
    public List getCourseGroupsOfStd(CourseGroup group, TeachPlan specialityPlan) {
        Criteria criteria = getSession().createCriteria(CourseGroup.class);
        criteria.add(Restrictions.eq("courseType", group.getCourseType()));
        criteria.add(Restrictions.ne("id", group.getId()));
        Criteria planCriteria = criteria.createCriteria("teachPlans");
        planCriteria.add(Restrictions.eq("enrollTurn", specialityPlan.getEnrollTurn())).add(
                Restrictions.eq("stdType", specialityPlan.getStdType())).add(
                Restrictions.eq("department", specialityPlan.getDepartment()));
        
        if (null != specialityPlan.getSpeciality() && specialityPlan.getSpeciality().isPO())
            planCriteria.add(Restrictions.eq("speciality", specialityPlan.getSpeciality()));
        else
            planCriteria.add(Restrictions.isNull("speciality"));
        if (null != specialityPlan.getAspect() && specialityPlan.getAspect().isPO())
            planCriteria.add(Restrictions.eq("aspect", specialityPlan.getAspect()));
        else
            planCriteria.add(Restrictions.isNull("aspect"));
        
        planCriteria.add(Restrictions.isNotNull("std"));
        return criteria.list();
    }
    
    public CourseType getCourseType(Long planId, Long courseId) {
        String hql = "select courseGroup.courseType"
                + " from CourseGroup as courseGroup "
                + " join courseGroup.planCourses as planCourse "
                + " where courseGroup.teachPlan.id=:teachPlanId and planCourse.course.id=:courseId";
        Map params = new HashMap();
        params.put("teachPlanId", planId);
        params.put("courseId", courseId);
        Query query = getSession().createQuery(hql);
        HibernateQuerySupport.setParameter(query, params);
        List rs = query.list();
        
        return (CourseType) (rs.isEmpty() ? null : rs.get(0));
    }
    
}
