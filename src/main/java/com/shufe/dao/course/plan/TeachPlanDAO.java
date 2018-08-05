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

import java.util.Collection;
import java.util.List;

import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.dao.BasicDAO;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.util.DataRealmLimit;

/**
 * 培养计划信息存取类
 * 
 * @author chaostone
 * 
 */
public interface TeachPlanDAO extends BasicDAO {

	/**
	 * 根据培养计划id查询培养计划
	 * 
	 * @param id
	 * @return
	 */
	public TeachPlan getTeachPlan(Long id);

	/**
	 * 获得指定id的培养计划
	 * 
	 * @param planIdSeq
	 * @return
	 */
	public List getTeachPlans(Long[] planIds);

	/**
	 * 查找培养计划
	 * 
	 * @param plan
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Collection getTeachPlans(TeachPlan plan, DataRealmLimit limit,
			List sortList, Boolean isSpecialityPlan, Boolean isExactly);

	/**
	 * 查找有个人培养计划的学生名单
	 * 
	 * @return
	 */
	public List getStdHasPlan(TeachPlan plan);

	/**
	 * 查找对于固定学期培养计划中要求的学分值.
	 * 
	 * @param plan
	 * @param term
	 *            [1..maxTerm]
	 * @return
	 */
	public Float getCreditByTerm(TeachPlan plan, int term);

	/**
	 * 查找已经某个培养计划使用的课程类别
	 * 
	 * @param plan
	 * @return
	 */
	public List getUsedCourseTypes(TeachPlan plan);

	/**
	 * 保存新的培养计划
	 * 
	 * @param plan
	 * @return
	 */
	void saveTeachPlan(TeachPlan plan);

	/**
	 * 更新培养计划
	 * 
	 * @param plan
	 */
	void updateTeachPlan(TeachPlan plan);

	/**
	 * 删除培养计划
	 */
	void removeTeachPlan(Long planId);

	/**
	 * 统计培养计划的总学分
	 * 
	 * @param planId
	 * @return
	 */
	public float statOverallCredit(Long planId);

	/**
	 * 统计培养计划的总学分
	 * 
	 * @param planId
	 * @return
	 */
	public int statOverallCreditHour(Long planId);

	/**
	 * 汇总各个开课院系在指定学期内的开设的不同课程
	 * 
	 * @param calendar
	 * @return department,course,credit,weekHour,creditHour
	 */
	public Collection statDepartCourse(TeachCalendar calendar, List stdTypes,
			List departs, PageLimit limit);

	/**
	 * 汇总各个开课院系在指定学期内的开设的不同课程数量
	 * 
	 * @param calendar
	 * @return department,count
	 */
	public Collection statDepartCourseCount(TeachCalendar calendar,
			List stdTypes, List departs);
	
	/**
	 * 查询指定所在年级和专业的 培养计划
	 * 
	 * @param speciality
	 * @param stdType
	 * @param enrollTurn
	 * @return
	 */
	public List getTeachPlan(Speciality speciality, String stdTypeCode, String enrollTurn);

}
