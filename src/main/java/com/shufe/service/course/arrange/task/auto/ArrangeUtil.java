//$Id: ArrangeUtil.java,v 1.7 2006/12/06 07:46:47 duanth Exp $
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
 * chaostone             2005-11-10         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.commons.lang.StringUtil;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.task.ArrangeParams;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.course.arrange.task.auto.impl.TimeAllocFailure;

/**
 * arrange utility.
 * 
 * @see ArrangeGroup
 * @see ArrangeCase
 * @author chaostone 2005-11-25
 */
public class ArrangeUtil {
	protected static final Logger logger = LoggerFactory.getLogger(ArrangeUtil.class);
	/**
	 * 安排单门课程,有单个教师.<br>
	 * arrange a single Task in given suite and timeUnits.
	 * 
	 * @param times
	 * @param task
	 * @throws GeneralArrangeFailure
	 * @throws TimeAllocFailure
	 * @throws ResourceAllocFailure
	 */
	public static List arrangeActivities(ArrangeCase arrange,
			ArrangeInfo arrangeInfo, TaskRequirement requirement,
			ArrangeResult rs) throws GeneralArrangeFailure {
		ArrangeSuite suite = arrange.getSuite();
		ArrangeParams params = arrange.getSuite().getParams();

		// 检查教师可以使用的时间
		Teacher teacher = null;
		if (params.getConsiderAvailTime()[ArrangeParams.TEACHER]) {
			if (arrangeInfo.isSingleTeacher()) {
				teacher = (Teacher) arrangeInfo.getTeachers().iterator().next();
				if (null != teacher.getAvailableTime()
						&& StringUtils.isNotEmpty(teacher.getAvailableTime()
								.getAvailable())) {
					if (StringUtil.countChar(arrange.getAvailUnitBitMap(), '1') < arrangeInfo
							.getWeekUnits().intValue())
						throw new TimeAllocFailure("teacher "
								+ teacher.getName()
								+ "'s available time is not enough ");

					arrange.setUnitOccupy(BitStringUtil.and(arrange
							.getAvailUnitBitMap(), teacher.getAvailableTime()
							.getAvailable()));
				}
			}
		}
		rs.notify("分配时间/教室..");
		Classroom example = new Classroom();
		example.setConfigType(requirement.getRoomConfigType());
		example.setCapacityOfCourse(requirement.getCapacityOfCourse());
		TimeUnit[] times = null;
		Classroom[] rooms = null;

		while (true) {
			times = suite.allocTimes(arrange);
			if (logger.isDebugEnabled()) {
				logger.debug("allocate time for " + arrange.getName()
						+ "! Result: " + ArrayUtils.toString(times));
			}
			for (int i = 0; i < times.length; i++) {
				if (null == times[i])
					throw new TimeAllocFailure("info.arrange.time.notFreeTime",
							"cannot allocator time");
			}
			rs.notify("分配时间:" + ArrayUtils.toString(times));
			rooms = null;
			// 如果有建议教室,则使用建议教室
			if (params.getBySuggest()[ArrangeParams.ROOM]
					&& arrangeInfo.getSuggest().hasRooms()) {
				if (logger.isDebugEnabled()) {
					logger.debug("arrange suggest rooms :"
							+ arrangeInfo.getSuggest().getRooms());
				}
				rs.notify("使用建议教室");
				// 如果不检测教室冲突,则将第一个教室分配给所有时间段
				if (!params.getDetectCollision()[ArrangeParams.ROOM]) {
					rooms = new Classroom[times.length];
					Classroom aroom = (Classroom) arrangeInfo.getSuggest()
							.getRooms().iterator().next();
					for (int i = 0; i < rooms.length; i++) {
						rooms[i] = new Classroom();
						rooms[i] = aroom;
					}
				} else {
					rooms = suite.getRoomAllocator().alloc(times,
							arrangeInfo.getSuggest().getRooms(), example,
							params.getConsiderAvailTime()[ArrangeParams.ROOM]);
					if (logger.isDebugEnabled()) {
						logger.debug("allocate room from suggest :"
								+ ArrayUtils.toString(rooms));
					}
				}
			} else {
				rooms = suite.allocRooms(times, example);
			}
			rs.notify("该时间段没有教室,尝试分配其他时间.");
			// 没有教室可用的情况下,重新分配其他时间段.
			if (null == rooms[0]) {
				continue;
			} else {
				// 如果是具部分配成功(部分满足),则尝试着分配新的时间段和教室
				for (int i = 0; i < rooms.length; i++) {
					while (null == rooms[i]) {
						TimeUnit newTime = suite.getTimeAllocator().allocTime(
								arrange);
						if (null == newTime)
							throw new ResourceAllocFailure(
									"info.arrange.time.notFreeTime");
						rooms[i] = suite.allocRoom(newTime, example);
						if (null != rooms[i])
							times[i] = newTime;
					}
				}
			}
			// 如果最后一个都分配成功,则跳出循环
			if (null != rooms[rooms.length - 1])
				break;
		}
		rs.notify("成功分配教室:" + ArrayUtils.toString(rooms));
		
		/** generator activities */
		ArrayList activities = new ArrayList();
		for (int i = 0; i < times.length; i++) {
			CourseActivity activity = new CourseActivity(false);
			if (rooms.length == 1)
				activity.setRoom(rooms[0]);
			else
				activity.setRoom(rooms[i]);
			activity.setTeacher(teacher);
			activity.setTime(times[i]);
			activity.setCalendar(arrange.getCalendar());
			times[i].initTime(arrange.getTask().getCalendar().getTimeSetting());
			activities.add(activity);
		}
		return activities;
	}

	/**
	 * 安排单个课程.<br>
	 * 它或者是单个<code>ArrangeCase</code>(一门课).
	 * 
	 * @param arrange
	 * @param times
	 * @return
	 * @throws GeneralArrangeFailure
	 */
	public static List arrangeSingleCase(ArrangeCase arrange, ArrangeResult rs)
			throws GeneralArrangeFailure {
		List activities = new ArrayList();
		ArrangeInfo[] infos = splitArrangeInfo(arrange.getArrangeInfo());
		for (int i = 0; i < infos.length; i++) {
			arrange.setArrangeInfo(infos[i]);
			activities.addAll(arrangeActivities(arrange, infos[i], arrange
					.getRequirement(), rs));
		}
		return activities;
	}

	/**
	 * 对于多个老师均分教学周<br>
	 * 没有或有一个教师的情况不进行分解<br>
	 * 
	 * @param info
	 * @return
	 */
	public static ArrangeInfo[] splitArrangeInfo(ArrangeInfo info) {
		if (info.isSingleTeacher() || !info.hasTeachers()) {
			return new ArrangeInfo[] { info };
		} else {

			int avg = info.getWeeks().intValue() / info.getTeachers().size();
			if (avg < 1) {
				ArrangeInfo[] infos = new ArrangeInfo[] { (ArrangeInfo) info
						.clone() };
				infos[0].getTeachers().clear();
				return infos;
			}

			ArrangeInfo[] infos = new ArrangeInfo[info.getTeachers().size()];
			int i = 0;
			Iterator iter = info.getTeachers().iterator();
			for (; i < info.getTeachers().size() - 1; i++) {
				Teacher teacher = (Teacher) iter.next();
				infos[i] = (ArrangeInfo) info.clone();
				infos[i].getTeachers().clear();
				infos[i].getTeachers().add(teacher);
				infos[i].setWeeks(new Integer(avg));
			}
			int last = info.getWeeks().intValue() - avg * (i);
			infos[i] = (ArrangeInfo) info.clone();
			infos[i].setWeeks(new Integer(last));
			infos[i].getTeachers().clear();
			infos[i].getTeachers().add((Teacher) iter.next());
			return infos;
		}
	}

	/**
	 * update tasks arrangeComplete's state
	 * 
	 * @param task
	 */
	public static void updateTeachTaskState(TeachTask task) {
		task.getArrangeInfo().setIsArrangeComplete(new Boolean(true));
	}
}
