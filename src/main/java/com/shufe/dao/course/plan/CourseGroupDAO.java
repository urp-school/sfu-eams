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
package com.shufe.dao.course.plan;

import java.util.List;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.TeachPlan;

/**
 * 培养计划课程组信息存取类
 * 
 * @author chaostone
 * 
 */
public interface CourseGroupDAO {

	/**
	 * 根据课程组id查询课程组
	 * 
	 * @param id
	 * @return
	 */
	CourseGroup getCourseGroup(Long id);

	/**
	 * 保存新建的课程组/更新已有的课程组
	 * 
	 * @param group
	 */
	void saveCourseGroup(CourseGroup group);

	/**
	 * 更新课程组
	 * 
	 * @param group
	 */
	void updateCourseGroup(CourseGroup group);

	/**
	 * 删除课程组
	 * 
	 * @param groupId
	 */
	void removeCourseGroup(Long groupId);

	/**
	 * 查找某类课程类别对应专业培养计划中专属于学生的课程组
	 * 
	 * @param courseType
	 * @param specialityPlan
	 * @return
	 */
	List getCourseGroupsOfStd(CourseGroup group, TeachPlan specialityPlan);

	/**
	 * 返回指定计划中，指定课程的课程类别
	 * 
	 * @param planId
	 * @param courseId
	 * @return 不存在该课程，则返回null
	 */
	public CourseType getCourseType(Long planId, Long courseId);
}
