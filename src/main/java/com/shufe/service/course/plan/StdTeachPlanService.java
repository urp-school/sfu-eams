//$Id: StdTeachPlanService.java,v 1.1 2007-8-26 上午11:45:20 chaostone Exp $
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

package com.shufe.service.course.plan;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;

public interface StdTeachPlanService {

	/**
	 * 
	 * 查找学生的培养计划<br>
	 * (默认是一专业培养计划)<br>
	 * 如果没有个人计划,则根据forceGen决定是否生成, 否则返回专业计划.
	 * 
	 * @param std
	 * @param majorType
	 * @param forceGen
	 *            默认为false
	 * @return 不成功条件下,返回null
	 */
	public TeachPlan getTeachPlan(Student std, MajorType majorType,
			Boolean forceGen);
}
