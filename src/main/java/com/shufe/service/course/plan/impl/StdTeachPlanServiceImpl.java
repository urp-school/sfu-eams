//$Id: StdTeachPlanServiceImpl.java,v 1.1 2007-8-26 上午11:46:39 chaostone Exp $
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
 * chenweixiong              2007-8-26         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.plan.impl;

import java.util.Collections;
import java.util.List;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;
import com.shufe.service.course.plan.StdTeachPlanService;
import com.shufe.service.course.plan.TeachPlanService;

public class StdTeachPlanServiceImpl implements StdTeachPlanService {

	TeachPlanService teachPlanService;

	/**
	 * 查询学生个人培养计划<br>
	 * 如果没有个人计划,则生成
	 */
	public TeachPlan getTeachPlan(Student std, MajorType majorType,
			Boolean forceGen) {
		if (null == majorType)
			majorType = new MajorType(MajorType.FIRST);
		TeachPlan plan = teachPlanService.getTeachPlan(std, new Boolean(
				MajorType.FIRST.equals(majorType.getId())), null);
		if (null == plan)
			return null;
		if (plan.isStdTeachPlan())
			return plan;
		else {
			if (null == forceGen)
				forceGen = Boolean.FALSE;
			if (Boolean.FALSE.equals(forceGen)) {
				return plan;
			} else {
				List newPlans = teachPlanService.genTeachPlanForStd(Collections
						.singleton(std), plan);
				if (!newPlans.isEmpty())
					return (TeachPlan) newPlans.get(0);
				else
					return null;
			}
		}
	}

	public void setTeachPlanService(TeachPlanService teachPlanService) {
		this.teachPlanService = teachPlanService;
	}

}
