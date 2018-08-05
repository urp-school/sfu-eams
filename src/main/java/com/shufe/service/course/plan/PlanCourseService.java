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
package com.shufe.service.course.plan;

import java.util.List;

import com.shufe.dao.course.plan.PlanCourseDAO;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;

public interface PlanCourseService {
    
    public PlanCourse getPlanCourse(Long id);
    
    /**
     * 删除培养计划中的课程<br>
     * 如果是专业培养计划，级联删除对应专业的学生个人培养计划<br>
     * 对该课程所在课程组的更动,是否传播到其他培养计划，依照saveCourseGroup所示<br>
     * 
     * @param planCourse
     * @param plan
     * @param needReCalculateCredit
     */
    public void removePlanCourse(PlanCourse planCourse, TeachPlan plan,
            boolean needReCalculateCredit);
    
    /**
     * 添加培养计划中的课程<br>
     * 如果是专业培养计划，级联添加对应专业的学生个人培养计划<br>
     * 
     * @param planCourse
     * @param plan
     * @param needReCalculateCredit
     */
    public void addPlanCourse(PlanCourse planCourse, TeachPlan plan, boolean needReCalculateCredit);
    
    /**
     * 更新培养计划中的课程<br>
     * 如果是专业培养计划，级联更新对应专业的学生个人培养计划<br>
     * 
     * @param planCourse
     * @param plan
     * @param needReCalculateCredit
     */
    public void updatePlanCourse(PlanCourse planCourse, TeachPlan plan,
            boolean needReCalculateCredit);
    
    public void setPlanCourseDAO(PlanCourseDAO planCourseDAO);
    
    public void setCourseGroupService(CourseGroupService courseGroupService);
    
    public CourseGroupService getCourseGroupService();
    /**
     * 根據學生對應信息查詢出該學生培養計劃的的學位課程
     * @param EnrollYear
     * @param deparmentId
     * @param firstMajorId
     * @param firstAspectId
     * @return
     */
    public List getDegreeCourseList(String EnrollYear,Long deparmentId,Long firstMajorId,Long firstAspectId);
    
    /**
     * 比较
     * 
     * @param planCourse1
     * @param planCourse2
     * @return
     */
    public boolean isEquals(PlanCourse planCourse1, PlanCourse planCourse2);
}
