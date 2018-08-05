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
 * chaostone             2006-5-29            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.predicate;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.StringUtil;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 检验任务的建议时间是否符合任务的节次和周课时安排
 * 
 * @author chaostone
 * 
 */
public class SuggestTimePredicate implements EvaluateValueRemember, Predicate {

	public Object getEvaluateValue(Object obj) {
		TeachTask task = (TeachTask) obj;
		return task.getArrangeInfo().getSuggest().getTime().abbreviate();
	}

	public boolean evaluate(Object object) {
		TeachTask task = (TeachTask) object;
		if (null != task.getArrangeInfo().getSuggest().getTime()) {
			AvailableTime time = task.getArrangeInfo().getSuggest().getTime();
			if (StringUtil.countChar(time.getAvailable(), '0') > (WeekInfo.MAX
					* TeachCalendar.MAXUNITS - task.getArrangeInfo().getWeekUnits()
					.intValue()))
				return false;
			int hasUnits = StringUtil.countStr(time.getAvailable(), StringUtils
					.repeat("1", task.getArrangeInfo().getCourseUnits()
							.intValue()));
			int needUnits = task.getArrangeInfo().getWeekUnits().intValue()
					/ task.getArrangeInfo().getCourseUnits().intValue();
			if (hasUnits < needUnits)
				return false;
		}
		return true;
	}
}
