//$Id: DefaultExamActivityGenerator.java,v 1.1 2007-7-28 下午05:12:09 chaostone Exp $
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
 * chenweixiong              2007-7-28         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.exam.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.arrange.exam.ExamActivityGenerator;

public class DefaultExamActivityGenerator implements ExamActivityGenerator {

	private TimeUnit unit;

	public List generate(TeachTask task, ExamType examType, int count) {
		Collection examActivities = task.getArrangeInfo().getExamActivities(examType);
		List activities = new ArrayList(count);
		if (CollectionUtils.isEmpty(examActivities)) {
			for (int i = 0; i < count; i++) {
				ExamActivity examActivity = new ExamActivity();
				examActivity.setTask(task);
				task.getArrangeInfo().getExamActivities().add(examActivity);
				examActivity.setTime(unit);
				examActivity.setRoom(null);
				examActivity.setExamType(examType);
				examActivity.setDepartment(task.getArrangeInfo().getTeachDepart());
				examActivity.setCalendar(task.getCalendar());
				examActivity.getExamMonitor().setExaminer(null);
				examActivity.getExamMonitor().setInvigilator(null);
				examActivity.getExamMonitor().setDepart(task.getTeachClass().getDepart());
				activities.add(examActivity);
			}
		} else {
			activities.addAll(examActivities);
			int reminder = count - activities.size();
			if (reminder > 0) {
				ExamActivity first = (ExamActivity) examActivities.iterator().next();
				for (int i = 0; i < reminder; i++) {
					ExamActivity cloned = (ExamActivity) first.clone();
					task.getArrangeInfo().getExamActivities().add(cloned);
					activities.add(cloned);
				}
			}
		}
		return activities;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

}
