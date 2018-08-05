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

import java.util.Iterator;

import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.election.StdCountLimitedPolicy;

/**
 * 依照教学任务的排课教室容量的最小值设置人数上限
 * 
 * @author chaostone
 * 
 */
public class MaxStdCountByRoomConfigPolicy implements StdCountLimitedPolicy {

	private Integer stdCountLimited = null;

	public void updateStdCountLimited(TeachTask task) {
		int maxStdCount = Integer.MAX_VALUE;
		for (Iterator iter = task.getArrangeInfo().getActivities().iterator(); iter
				.hasNext();) {
			CourseActivity activity = (CourseActivity) iter.next();
			if (null != activity.getRoom()
					&& activity.getRoom().getCapacityOfCourse().intValue() < maxStdCount)
				maxStdCount = activity.getRoom().getCapacityOfCourse()
						.intValue();
		}
		if (maxStdCount != Integer.MAX_VALUE) {
			task.getElectInfo().setMaxStdCount(new Integer(maxStdCount));
			stdCountLimited = new Integer(maxStdCount);
		} else {
			stdCountLimited = null;
		}
	}

	/**
	 * @see com.shufe.service.course.election.StdCountLimitedPolicy#getStdCountLimited()
	 */
	public Integer getStdCountLimited() {
		return stdCountLimited;
	}

}
