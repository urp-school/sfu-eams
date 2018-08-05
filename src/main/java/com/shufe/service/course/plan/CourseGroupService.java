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
 * chaostone             2006-3-26            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.plan;

import java.util.Collection;
import java.util.List;

import com.shufe.dao.course.plan.CourseGroupDAO;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.TeachPlan;

/**
 * 培养计划课程组实现类 所有对专业培养计划的操作()学生的
 * 
 * @author chaostone
 * 
 */
public interface CourseGroupService {
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
	 * 删除课程组<br>
	 * 如果删除的课程组市专业培养计划中的课程组，<br>
	 * 则级联删除存在相同课程性质的学生培养计划中的课程组.
	 * 
	 * @param groupId
	 */
	void removeCourseGroup(Long groupId);

	/**
	 * 更新已有的课程组.<br>
	 * <p>
	 * 1)如果plan是专业培养计划,该操作会级联更新到学生的个人培养计划.
	 * 2)如果该课程组已经持久化（现在仅是更新）,则plan应为该组关联的计划之一.<br>
	 * 
	 * 2.1)检查该培养计划,如果是学生的培养计划,<br>
	 * 则为其他关联的培养计划（无论是专业培养计划还是学生的）<br>
	 * 克隆出一个相同组，并建立其于新组的关联<br>
	 * 
	 * 2.2)检查该培养计划,如果是专业的培养计划,<br>
	 * 则为其他关联的专业培养计划培养计划（不是还是学生的）<br>
	 * 克隆出一个相同组，并建立其于新组的关联<br>
	 * 
	 * 3)在持久化该组的信息
	 * <p>
	 * 
	 * @param group
	 * @param plan
	 */
	void updateCourseGroup(CourseGroup group, TeachPlan plan);

	/**
	 * 添家课程组.<br>
	 * 如果添加的课程组市专业培养计划中的课程组，<br>
	 * 则级联添加存在相同课程性质的学生培养计划中的课程组.
	 * 
	 * @param group
	 * @param plan
	 */
	void addCourseGroup(CourseGroup group, TeachPlan plan);

	/**
	 * 从培养计划中删除该组与该组的关联<br>
	 * 如果没有培养计划关联该组,删除该组.<br>
	 * 
	 * @param groupId
	 * @param planId
	 */
	void removeCourseGroup(Long groupId, Long planId);

	/**
	 * 为计划,克隆(替换)课程组<br>
	 * 如果plans的任一个培养计划在课程组中对应的培养计划中,<br>
	 * 则使之关联到新克隆生成的课程组(仅仅克隆一个),并与原来的组脱离关系.<br>
	 * 使得每一个培养计划最后关联的是新克隆的组.<br>
	 * 课程组中形成深拷贝.<br>
	 * 1)拷贝的课程组其id为null<br>
	 * 2)拷贝的课程组中没有关联的培养计划.<br>
	 * 3)拷贝的课程组内的培养计划课程<code>PlanCourse</code>中的id属性也为null<br>
	 * 4)培养计划课程的先修课程也是深拷贝(不是直接应用被拷贝的set).<br>
	 * 会为自动保存替换后的课程组并建立它和目标培养计划的关联.
	 * group--->cloned -->plan1...plan2
	 * 
	 * @param group
	 * @param excludedPlans
	 * @return
	 */
	public CourseGroup cloneGroupFor(CourseGroup group, Collection plans);

	/**
	 * 使得改组对于该计划而言,是独立占用的.<br>
	 * 返回可能克隆的新组.
	 * @param group
	 * @param groupPlan
	 */
	public CourseGroup keepUniqueGroupFor(CourseGroup group, TeachPlan groupPlan);

	/**
	 * 培养计划存取接口
	 * 
	 * @param teachPlanDAO
	 */
	public void setTeachPlanService(TeachPlanService teachPlanService);

	public TeachPlanService getTeachPlanService();

	/**
	 * 课程组存取接口
	 * 
	 * @param courseGroupDAO
	 */
	public void setCourseGroupDAO(CourseGroupDAO courseGroupDAO);

	/**
	 * 查找某类课程类别对应专业培养计划中专属于学生的课程组
	 * 
	 * @param courseType
	 * @param specialityPlan
	 * @return
	 */
	List getCourseGroupsOfStd(CourseGroup group, TeachPlan specialityPlan);
}
