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
 * chaostone             2006-6-3            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.election.impl;

import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.election.StdCountLimitedPolicy;

/**
 * 依据任务的计划人数设置选课人数上限
 * 
 * @author chaostone
 * 
 */
public class MaxStdCountByPlanPolicy implements StdCountLimitedPolicy {

	private Integer stdCountLimited = null;

	/**
	 * @see com.shufe.service.course.election.StdCountLimitedPolicy#updateStdCountLimited(com.shufe.model.course.task.TeachTask)
	 */
	public void updateStdCountLimited(TeachTask task) {
		task.getElectInfo().setMaxStdCount(
				task.getTeachClass().getPlanStdCount());
		stdCountLimited = task.getTeachClass().getPlanStdCount();
	}

	/**
	 * @see com.shufe.service.course.election.StdCountLimitedPolicy#getStdCountLimited()
	 */
	public Integer getStdCountLimited() {
		return stdCountLimited;
	}

}
