//$Id: CreditConstraintService.java,v 1.11 2006/12/20 05:48:50 duanth Exp $
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
 * chaostone             2005-12-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.election;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.shufe.dao.course.election.CreditConstraintDAO;
import com.shufe.model.course.election.CreditConstraint;
import com.shufe.model.course.election.CreditInitParams;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.DataRealmLimit;
import com.shufe.web.OutputProcessObserver;

/**
 * 选课学分限制服务接口
 * 
 * @author chaostone 2005-12-11
 */
public interface CreditConstraintService {

	/**
	 * 查询学生个人选课学分限制
	 * 
	 * @param creditConstrant
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Collection getStdCreditConstraints(List conditions,
			DataRealmLimit limit, List orderList);

	/**
	 * 通过学生、教学日历、查询选课学分限制<br>
	 * for ajax
	 * 
	 * @param calendarId
	 * @param std
	 * @return
	 */
	public StdCreditConstraint getPersonCreditConstraints(Long constraintId);

	/**
	 * 
	 * @param ids
	 * @param value
	 * @param isCeil
	 * @param isUpdateStdCascade
	 */
	public void updateSpecialityCreditConstraints(Serializable[] ids,
			Float value, boolean isCeil, boolean isUpdateStdCascade);

	/**
	 * 更新学分限制上下限
	 * 
	 * @param ids
	 * @param ceil
	 * @param floor
	 * @param isUpdateStdCascade
	 */
	public void updateSpecialityCreditConstraints(Serializable[] ids,
			Float ceil, Float floor, boolean isUpdateStdCascade);

	/**
	 * 更新学分限制下限
	 * 
	 * @param creditConstraintClass
	 * @param ids
	 * @param value
	 */
	public void updateStdCreditConstraints(Serializable[] ids, Float value,
			boolean isCeil);

	/**
	 * 更新学分限制上下限
	 * 
	 * @param creditConstraintClass
	 * @param ids
	 * @param ceil
	 * @param floor
	 */
	public void updateStdCreditConstraints(Serializable[] ids, Float ceil,
			Float floor);

	/**
	 * 查询学生学分限制
	 * 
	 * @param stdId
	 * @param calendars
	 * @return
	 */
	public List getStdCreditConstraints(Student std, Collection calendars);

	public StdCreditConstraint getStdCreditConstraint(Long stdId,
			TeachCalendar calendar);

	/**
	 * 初始化专业学分和学生学分
	 * 
	 * @param calendar
	 * @param common
	 */
	public void initCreditConstraint(TeachCommon common,
			TeachCalendar calendar, OutputProcessObserver observer);

	/**
	 * 指定(权限范围)学生类别和部门列表，<br>
	 * 依据所有有效培养计划生成专业选课学分和选课学生学分上限下限<br>
	 * 
	 * @param stdTypes
	 * @param departs
	 * @param calendar
	 * @param observer
	 */
	public void initCreditConstraint(String stdTypeIds, String departIds,
			TeachCalendar calendar, OutputProcessObserver observer);

	/**
	 * 初始化指定专业的学分
	 * 
	 * @param specialityConstraintIds
	 * @param calendar
	 */
	public void initCreditConstraint(Long[] specialityConstraintIds,
			TeachCalendar calendar);

	/**
	 * 针对指定学期的学生选分限制，统计已选学分和奖励学分,平均绩点
	 * 
	 * @param stdCreditConstraint
	 * @param observer
	 */
	public void statStdCreditConstraint(
			StdCreditConstraint stdCreditConstraint, CreditInitParams params,
			OutputProcessObserver observer);

	/**
	 * 统计选定学生的已选学分和奖励学分,平均绩点
	 * 
	 * @param stdCreditConstraintIds
	 */
	public void statStdCreditConstraint(Long[] stdCreditConstraintIds,
			CreditInitParams params);

	/**
	 * 查询没有学分上限的学生名单
	 * 
	 * @param std
	 * @param calendar
	 * @param limit
	 * @param orderList
	 * @return
	 */
	public Collection getStdWithoutCredit(Student std, TeachCalendar calendar,
			DataRealmLimit limit, List orderList);

	/**
	 * 批量设置学生的学分上限
	 * 
	 * @param std
	 * @param calendar
	 * @param limit
	 * @param credit
	 */
	public void batchAddCredit(Student std, DataRealm realm,
			CreditConstraint credit);

	/**
	 * 批量设置学生的学分上限
	 * 
	 * @param stdIds
	 * @param calendar
	 * @param credit
	 */
	public void batchAddCredit(Collection stdIds, CreditConstraint credit);

	public void setCreditDAO(CreditConstraintDAO creditDAO);

	public void setCalendarService(TeachCalendarService calendarService);

	public void setTeachPlanService(TeachPlanService teachPlanService);
}
