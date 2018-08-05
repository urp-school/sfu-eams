//$Id: TaskWithRegularWeekUnitsPredicate.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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
 * chaostone             2005-12-20         Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.predicate;

import org.apache.commons.collections.Predicate;

import com.shufe.model.course.task.TeachTask;

/**
 * 验证教学任务周课时是否为小节数的整倍数
 * 
 * @author chaostone
 * 
 */
public class TaskWithRegularWeekUnitsPredicate implements Predicate,
		EvaluateValueRemember {

	public boolean evaluate(Object arg0) {
		TeachTask task = (TeachTask) arg0;
		Float weekUnits = task.getArrangeInfo().getWeekUnits();
		Integer units = task.getArrangeInfo().getCourseUnits();
		if (null == weekUnits || null == units)
			return false;
		else
			return weekUnits.floatValue() % units.intValue() == 0;
	}

	public Object getEvaluateValue(Object obj) {
		TeachTask task = (TeachTask) obj;
		Float weekUnits = task.getArrangeInfo().getWeekUnits();
		Integer units = task.getArrangeInfo().getCourseUnits();
		return (null == weekUnits) ? "" : weekUnits.toString() + ","
				+ ((null == units) ? "" : units.toString());
	}

}
