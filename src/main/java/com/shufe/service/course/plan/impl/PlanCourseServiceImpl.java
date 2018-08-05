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
 * chaostone             2006-3-27            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.plan.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;

import com.ekingstar.commons.model.EntityUtils;
import com.shufe.dao.course.plan.PlanCourseDAO;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.service.BasicService;
import com.shufe.service.course.plan.CourseGroupService;
import com.shufe.service.course.plan.PlanCourseService;

public class PlanCourseServiceImpl extends BasicService implements PlanCourseService {
    
    private PlanCourseDAO planCourseDAO;
    
    private CourseGroupService courseGroupService;
    
    public PlanCourse getPlanCourse(Long id) {
        return planCourseDAO.getPlanCourse(id);
    }
    
    /**
     * @see com.shufe.service.course.plan.PlanCourseService#addPlanCourse(com.shufe.model.course.plan.PlanCourse,
     *      com.shufe.model.course.plan.TeachPlan, boolean)
     */
    public void addPlanCourse(PlanCourse planCourse, TeachPlan plan, boolean needReCalculateCredit) {
        CourseGroup myGroup = courseGroupService
                .getCourseGroup(planCourse.getCourseGroup().getId());
        
        EntityUtils.evictEmptyProperty(planCourse);
        myGroup.addPlanCourse(planCourse);
        if (needReCalculateCredit) {
            myGroup.statCreditAndHour(plan.getTermsCount().intValue());
        }
        
        courseGroupService.saveCourseGroup(myGroup);
    }
    
    public List getDegreeCourseList(String EnrollYear,Long deparmentId,Long firstMajorId,Long firstAspectId){
        List degreeCourseList = new ArrayList();
        if(null==firstAspectId){
            degreeCourseList = utilService
            .searchHQLQuery("from PlanCourse pc where pc.isDegreeCourse = 1 and exists (from CourseGroup cg where cg.id=pc.courseGroup.id " +
                    "and exists (from TeachPlan tp join tp.courseGroups as tcg where cg.id=tcg.id and tp.enrollTurn = '" +EnrollYear+
                    "' and tp.department.id = "+deparmentId+" and tp.speciality.id = "+firstMajorId+"))");
        }else{
            degreeCourseList = utilService
            .searchHQLQuery("from PlanCourse pc where pc.isDegreeCourse = 1 and exists (from CourseGroup cg where cg.id=pc.courseGroup.id " +
                    "and exists (from TeachPlan tp join tp.courseGroups as tcg where cg.id=tcg.id and tp.enrollTurn = '" +EnrollYear+
                    "' and tp.department.id = "+deparmentId+" and tp.speciality.id = "+firstMajorId+" and tp.aspect.id =" +
                    firstAspectId+"))");
        }
       
        return degreeCourseList;
    }
    
    /**
     * @see com.shufe.service.course.plan.CourseGroupService#removePlanCourse(com.shufe.model.course.plan.PlanCourse,
     *      com.shufe.model.course.plan.TeachPlan, boolean)
     */
    public void removePlanCourse(PlanCourse planCourse, TeachPlan plan,
            boolean needReCalculateCredit) {
        PlanCourse removed = (PlanCourse) utilService.load(PlanCourse.class, planCourse.getId());
        CourseGroup myGroup = removed.getCourseGroup();
        myGroup.removePlanCourse(removed);
        if (needReCalculateCredit) {
            myGroup.statCreditAndHour(plan.getTermsCount().intValue());
        }
        courseGroupService.saveCourseGroup(myGroup);
    }
    
    /**
     * @see com.shufe.service.course.plan.PlanCourseService#updatePlanCourse(com.shufe.model.course.plan.PlanCourse,
     *      com.shufe.model.course.plan.TeachPlan, boolean)
     */
    public void updatePlanCourse(PlanCourse planCourse, TeachPlan plan,
            boolean needReCalculateCredit) {
        PlanCourse savedPlanCourse = (PlanCourse) utilService.load(PlanCourse.class, planCourse
                .getId());
        CourseGroup group = savedPlanCourse.getCourseGroup();
        
        EntityUtils.merge(savedPlanCourse, planCourse);
        EntityUtils.evictEmptyProperty(savedPlanCourse);
        
        // 计算保存
        if (needReCalculateCredit) {
            group.statCreditAndHour(plan.getTermsCount().intValue());
        }
        courseGroupService.saveCourseGroup(group);
    }
    
    /**
     * @param planCourseDAO
     *            The planCourseDAO to set.
     */
    public void setPlanCourseDAO(PlanCourseDAO planCourseDAO) {
        this.planCourseDAO = planCourseDAO;
    }
    
    /**
     * @param courseGroupService
     *            The courseGroupService to set.
     */
    public void setCourseGroupService(CourseGroupService courseGroupService) {
        this.courseGroupService = courseGroupService;
    }
    
    public CourseGroupService getCourseGroupService() {
        return courseGroupService;
    }
    
    public boolean isEquals(PlanCourse planCourse1, PlanCourse planCourse2) {
        boolean result = ObjectUtils.equals(planCourse1, planCourse2);
        if (null != planCourse1 && null != planCourse2) {
            try {
                Map map1 = PropertyUtils.describe(planCourse1);
                Map map2 = PropertyUtils.describe(planCourse2);
                for (Iterator it = map1.keySet().iterator(); it.hasNext();) {
                    Object key = (Object) it.next();
                    if (!ObjectUtils.equals(map1.get(key), map2.get(key))) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        
        return result;
    }
    
}
