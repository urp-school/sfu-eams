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
 * chaostone             2006-5-27            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.predicate;

import java.util.Iterator;

import org.apache.commons.collections.Predicate;

import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * 检查教室是否满足教学任务的计划人数要求
 * 
 * @author chaostone
 * 
 */
public class RoomCapacityPredicate implements Predicate, EvaluateValueRemember {

	public boolean evaluate(Object object) {
		TeachTask task = (TeachTask) object;
		for (Iterator iter = task.getArrangeInfo().getSuggest().getRooms()
				.iterator(); iter.hasNext();) {
			Classroom room = (Classroom) iter.next();
			if (room.getCapacityOfCourse().intValue() < task.getTeachClass()
					.getPlanStdCount().intValue()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see com.shufe.service.course.arrange.predicate.EvaluateValueRemember#getEvaluateValue(java.lang.Object)
	 */
	public Object getEvaluateValue(Object obj) {
		StringBuffer buf = new StringBuffer();
		TeachTask task = (TeachTask) obj;
		buf.append(task.getTeachClass().getPlanStdCount()).append(":");
		for (Iterator iter = task.getArrangeInfo().getSuggest().getRooms()
				.iterator(); iter.hasNext();) {
			Classroom room = (Classroom) iter.next();
			if (room.getCapacityOfCourse().intValue() < task.getTeachClass()
					.getPlanStdCount().intValue()) {
				buf.append(room.getName()).append("(").append(
						room.getCapacityOfCourse()).append(") ");
			}
		}
		return buf;
	}

}
