//$Id: ArrangeTaskValidatorImpl.java,v 1.6 2007/01/17 15:06:30 duanth Exp $
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
package com.shufe.service.course.arrange.task.auto.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.NotNullPredicate;

import com.ekingstar.commons.collection.predicates.ValidNumScopePredicate;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.task.ArrangeParams;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.auto.ArrangeTaskValidator;
import com.shufe.service.course.arrange.task.auto.ArrangeValidateMessage;
import com.shufe.service.course.arrange.task.auto.ArrangeValidateMessages;
import com.shufe.service.course.arrange.task.predicate.BeanPredicateWrapper;
import com.shufe.service.course.arrange.task.predicate.EvaluateValueRemember;
import com.shufe.service.course.arrange.task.predicate.RoomCapacityPredicate;
import com.shufe.service.course.arrange.task.predicate.RoomMeetRoomConfigPredicate;
import com.shufe.service.course.arrange.task.predicate.SuggestTimePredicate;
import com.shufe.service.course.arrange.task.predicate.TaskWithRegularWeekUnitsPredicate;

/**
 * 验证教学任务和任务课程组是否满足自动排课条件
 * 
 * <pre>
 *          1)有课程
 *          2)开始周在（1-20）之间
 *          3)占用周分布须在（1-3）从单周、双周到连续周 
 *          4)周课次在（1-42）之间
 *          5)每次课的小节数在(1-12)之间 
 *          6)占用周数（1-52）之间 
 *          7)教室设备配置情况 (类型)
 *          8)教学日历（id）
 *          9)教学日历的年份 
 *          10)教学日历的起始周
 *          11)计划人数不能超过1000
 *          12)验证教学任务周课时是否为小节数的整倍数
 * </pre>
 * 
 * @author chaostone
 * 
 */
public class ArrangeTaskValidatorImpl implements ArrangeTaskValidator {

	public static HashMap taskPredicates = new HashMap(13);

	/**
	 * 建议教室
	 */
	public static HashMap suggestRoomPredicates = new HashMap();

	/**
	 * 建议时间
	 */
	public static HashMap suggestTimePredicate = new HashMap();
	static {
		// 验证课程
		taskPredicates.put("error.teachTask.course.needed",
				new BeanPredicateWrapper("course", NotNullPredicate
						.getInstance()));
		// 验证起始周[1..20]
		taskPredicates.put("error.teachTask.arrangeInfo.weekStart.notInScope",
				new BeanPredicateWrapper("arrangeInfo.weekStart",
						new ValidNumScopePredicate(1, 20)));
		// 验证教学周占用周期[1..3]
		taskPredicates.put("error.teachTask.arrangeInfo.weekCycle.random",
				new BeanPredicateWrapper("arrangeInfo.weekCycle",
						new ValidNumScopePredicate(TimeUnit.CONTINUELY,
								TimeUnit.EVEN)));
		// 验证周课时[1..7*12]
		taskPredicates.put("error.teachTask.arrangeInfo.weekUnits.notInScope",
				new BeanPredicateWrapper("arrangeInfo.weekUnits",
						new ValidNumScopePredicate(1, WeekInfo.MAX
								* TeachCalendar.MAXUNITS)));
		// 验证小节数[1..4]
		taskPredicates.put(
				"error.teachTask.arrangeInfo.courseUnits.notInScope",
				new BeanPredicateWrapper("arrangeInfo.courseUnits",
						new ValidNumScopePredicate(CourseUnit.MINUNITS,
								CourseUnit.MAXUNITS)));
		// 验证周课时是否为小节的整倍数
		taskPredicates.put("error.teachTask.arrangeInfo.weekUnits.notRegular",
				new TaskWithRegularWeekUnitsPredicate());

		// 验证上课周数[1..53]
		taskPredicates.put("error.teachTask.arrangeInfo.weeks.notInScope",
				new BeanPredicateWrapper("arrangeInfo.weeks",
						new ValidNumScopePredicate(1,
								TeachCalendar.OVERALLWEEKS)));

		// 验证教室设备配置-not null
		taskPredicates.put("error.teachTask.requirement.roomConfigType.needed",
				new BeanPredicateWrapper("requirement.roomConfigType.id",
						NotNullPredicate.getInstance()));

		// 验证教学日历
		taskPredicates.put("error.teachTask.calendar.needed",
				new BeanPredicateWrapper("calendar.id", NotNullPredicate
						.getInstance()));
		// 验证教学日历中的学年度
		taskPredicates.put("error.teachTask.calendar.year.needed",
				new BeanPredicateWrapper("calendar.year", NotNullPredicate
						.getInstance()));
		// 验证教学日历中的起始周
		taskPredicates.put("error.teachTask.calendar.weekStart.needed",
				new BeanPredicateWrapper("calendar.weekStart", NotNullPredicate
						.getInstance()));
		// 验证教学班中的计划人数
		taskPredicates.put(
				"error.teachTask.teachClass.planStdCount.notInScope",
				new BeanPredicateWrapper("teachClass.planStdCount",
						new ValidNumScopePredicate(0, 1000)));
		// 验证教室[0..1]教室要么为空，要么只有一个
		// TODO
		// suggestRoomPredicates.put(
		// "error.teachTask.arrangeInfo.roomIds.needOnlyOne",
		// new BeanPredicateWrapper("arrangeInfo.suggest.rooms",
		// new CollectionHasUpto1ElementPredicate()));
		// 验证教室设备和建议教室相符合
		suggestRoomPredicates.put(
				"error.teachTask.arrangeInfo.rooms.notMeetRequiement",
				new RoomMeetRoomConfigPredicate());
		// 验证建议教室的容量是否符合计划人数
		suggestRoomPredicates.put(
				"error.teachTask.arrangeInfo.room.notEnoughCapacity",
				new RoomCapacityPredicate());
		// 验证建议时间
		suggestTimePredicate.put(
				"error.teachTask.arrangeInfo.suggestTime.notSuitable",
				new SuggestTimePredicate());

	}

	/**
	 * 验证教学任务是否满足自动排课的要求
	 */
	public ArrangeValidateMessages validateTask(TeachTask task,
			ArrangeParams params) {
		ArrangeValidateMessages msgs = new ArrangeValidateMessages();
		validateTask(task, msgs, params);
		return msgs;
	}

	/**
	 * 验证课程组是否满足自动排课要求
	 */

	public ArrangeValidateMessages validateTaskGroup(TaskGroup group,
			ArrangeParams params) {
		ArrangeValidateMessages msgs = new ArrangeValidateMessages();
		List tasks = group.getTaskList();
		for (Iterator iterator = tasks.iterator(); iterator.hasNext();)
			validateTask((TeachTask) iterator.next(), msgs, params);

		Iterator iter = tasks.iterator();
		/* 验证同一时间排课的课程组是否在周课时和每节课的小节数以及教学日历上是否一致. */
		if (group.getIsSameTime().equals(Boolean.TRUE)) {
			TeachTask task = (TeachTask) iter.next();
			TeachCalendar calendar = task.getCalendar();
			Float weekUnits = task.getArrangeInfo().getWeekUnits();
			Integer courseUnits = task.getArrangeInfo().getCourseUnits();
			for (; iter.hasNext();) {
				TeachTask one = (TeachTask) iter.next();

				if (!one.getArrangeInfo().getWeekUnits().equals(weekUnits)
						|| !one.getArrangeInfo().getCourseUnits().equals(
								courseUnits)
						|| !one.getCalendar().equals(calendar)) {
					msgs.add(ArrangeValidateMessages.TASKGROUP,
							new ArrangeValidateMessage(group.getId(), group
									.getName(), "error.taskGroup.notSameTime",
									""));
					break;
				}
			}
		}

		return msgs;
	}

	private void validateTask(TeachTask task, ArrangeValidateMessages msgs,
			ArrangeParams params) {
		validateTaskUsing(taskPredicates, task, msgs);
		if (params.getBySuggest()[ArrangeParams.ROOM]) {
			validateTaskUsing(suggestRoomPredicates, task, msgs);
		}
		if (params.getBySuggest()[ArrangeParams.TIME]) {
			validateTaskUsing(suggestTimePredicate, task, msgs);
		}
	}

	private void validateTaskUsing(HashMap predicateMap, TeachTask task,
			ArrangeValidateMessages msgs) {
		Set keys = predicateMap.keySet();
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			String errorKey = (String) iter.next();
			Predicate predicate = (Predicate) predicateMap.get(errorKey);
			if (!predicate.evaluate(task))
				msgs.add(ArrangeValidateMessages.TASK,
						new ArrangeValidateMessage(task.getSeqNo(), task
								.getCourse().getName(), errorKey,
								((EvaluateValueRemember) predicate)
										.getEvaluateValue(task).toString()));

		}
	}
}
