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
 * chaostone             2006-3-25            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.plan.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ekingstar.eams.system.basecode.industry.HSKDegree;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.plan.PlanCourseDAO;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.system.baseinfo.Course;

/**
 * 培养计划课程组内的课程数据存取实现
 * 
 * @author chaostone
 * 
 */
public class PlanCourseDAOHibernate extends BasicHibernateDAO implements PlanCourseDAO {
    
    public List getPlanCourseByTerm(Long teachPlanId, Integer term) {
        Map params = new HashMap();
        params.put("term", new Double(Math.pow(2, term)).intValue());
        params.put("teachPlanId", teachPlanId);
        return utilDao.searchNamedQuery("getPlanCourseByTerm", params);
    }
    
    /**
     * @see com.shufe.dao.course.plan.PlanCourseDAO#getPlanCourse(java.lang.Long)
     */
    public PlanCourse getPlanCourse(Long id) {
        PlanCourse planCourse = (PlanCourse) get(PlanCourse.class, id);
        initialize(planCourse.getCourse());
        initialize(planCourse.getTeachDepart());
        PlanCourse newOne = new PlanCourse();
        newOne.setId(planCourse.getId());
        newOne.setCourse(planCourse.getCourse());
        newOne.setTeachDepart(planCourse.getTeachDepart());
        newOne.setTerms(planCourse.getTerms());
        newOne.setRemark(planCourse.getRemark());
        
        newOne.setCourseGroup(null);
        return newOne;
    }
    
}
