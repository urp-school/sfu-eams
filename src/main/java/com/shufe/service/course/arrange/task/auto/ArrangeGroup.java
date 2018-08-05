//$Id: ArrangeGroup.java,v 1.6 2006/11/14 07:31:31 duanth Exp $
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.commons.lang.StringUtil;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.task.ArrangeParams;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.course.arrange.task.auto.impl.RoomAllocFailure;
import com.shufe.service.course.arrange.task.auto.impl.TimeAllocFailure;

/**
 * 安排在同一时间内的一组课程
 * 
 * @author chaostone 2005-11-10
 */
public class ArrangeGroup extends AbstractArrangeGroup {
	/** 任务要求 */
	private TaskRequirement requirement;

	/**
	 * 当前正在安排的教学任务
	 */
	private TeachTask curTask;


	public ArrangeGroup() {
	}

	/**
	 * 默认初始化函数
	 * 
	 * @param group
	 */
	public ArrangeGroup(TaskGroup group) {
        super(group);
	}

	public void arrange(ArrangeResult result) {
		arrangeGroup(result);
	}


	public void arrangeGroup(ArrangeResult result) {
		if (null == tasks || tasks.isEmpty())
			return;
		result.startArrange(this);
		// 获得该教学任务的可用时间,合并各个教师的可用时间
		unitOccupy = BitStringUtil.convertToBoolStr(availableTime.getAvailable());
		if (suite.getParams().getConsiderAvailTime()[ArrangeParams.TEACHER]) {
			for (Iterator iter = getTeachers().iterator(); iter.hasNext();) {
				Teacher teacher = (Teacher) iter.next();
				if (null != teacher.getAvailableTime()
						&& StringUtils.isNotEmpty(teacher.getAvailableTime().getAvailable())) {
					if (StringUtil.countChar(getAvailUnitBitMap(), '1') < getArrangeInfo()
							.getWeekUnits().intValue())
						throw new TimeAllocFailure("teacher " + teacher.getName()
								+ "'s available time is not enough ");

					unitOccupy = BitStringUtil.and(unitOccupy, teacher.getAvailableTime()
							.getAvailable());
				}
			}
		}
		TimeUnit[] times = null;
		while (true) {
			// 统一分配时间,不能使用建议时间.
			times = suite.allocTimes(this);
			if (null == times[0]) {
				result.addFailure(this, new TimeAllocFailure("info.arrange.time.notFreeTime",
						"cannot allocator time for taskGroup:" + group));
				return;
			}
			int arranged = 0;
			// 针对每一个教学任务分配教室
			for (Iterator iter = tasks.iterator(); iter.hasNext();) {
				TeachTask task = (TeachTask) iter.next();
				setName(task.getSeqNo() + ":" + task.getCourse().getName());
				curTask = task;
				result.startArrange(this);
				Classroom example = new Classroom();
				example.setConfigType(task.getRequirement().getRoomConfigType());
				example.setCapacityOfCourse(task.getTeachClass().getPlanStdCount());
				Classroom[] rooms = null;
				Collection roomScope = null;
				// 优先分配建议/组内教室
				if (suite.getParams().getBySuggest()[ArrangeParams.ROOM]
						&& task.getArrangeInfo().getSuggest().hasRooms()) {
					roomScope = task.getArrangeInfo().getSuggest().getRooms();
				} else if (!group.getSuggest().getRooms().isEmpty()) {
					roomScope = group.getSuggest().getRooms();
				}
				if (null != roomScope && !roomScope.isEmpty()) {
					if (suite.getParams().getDetectCollision()[ArrangeParams.ROOM])
						rooms = suite.getRoomAllocator().alloc(times, roomScope, example,
								suite.getParams().getConsiderAvailTime()[ArrangeParams.ROOM]);
					else
						rooms = suite.getRoomAllocator().allocIgnoreOccupy(times, roomScope,
								example,
								suite.getParams().getConsiderAvailTime()[ArrangeParams.ROOM]);
				}
				// 否则分配组外的教室
				if (null == rooms || null == rooms[0]) {
					try {
						rooms = suite.allocRooms(times, example);
					} catch (RoomAllocFailure e) {
						result.addFailure(this, e);
					}
				}
				if (null == rooms || null == rooms[0]) {
					break;
				}
				// 准备
				if (null != task.getArrangeInfo().getActivities()) {
					task.getArrangeInfo().getActivities().clear();
				}
				task.getArrangeInfo().setActivities(new HashSet());
				task.getArrangeInfo().setIsArrangeComplete(Boolean.FALSE);
				// 对于多个教师上同一个序号的课,要进行拆分.
				ArrangeInfo[] infos = ArrangeUtil.splitArrangeInfo(task.getArrangeInfo());
				for (int i = 0; i < infos.length; i++) {
					/** generator activities */
					ArrayList activities = new ArrayList();
					for (int j = 0; j < times.length; j++) {
						CourseActivity activity = new CourseActivity(false);
						if (rooms.length == 1)
							activity.setRoom(rooms[0]);
						else
							activity.setRoom(rooms[j]);
						activity.setTeacher((Teacher) infos[i].getTeachers().iterator().next());
						times[j].initTime(task.getCalendar().getTimeSetting());
						activity.setTime(times[j]);
						activity.setTask(task);
						activity.setCalendar(task.getCalendar());
						activities.add(activity);
					}
					task.getArrangeInfo().getActivities().addAll(activities);
				}
				// 善后处理
				arranged++;
				task.getArrangeInfo().setIsArrangeComplete(Boolean.TRUE);
				result.endArrange(this);
			}
			if (arranged == tasks.size())
				break;
		}
		setCurTask(null);
		result.endArrange(this);
	}

	public int compareTo(Object o) {
		return 0;
	}

	public String toString() {
		return "name:" + getName() + "\n" + "groupId" + group.getId();

	}

	public TaskRequirement getRequirement() {
		return requirement;
	}

	public TeachTask getCurTask() {
		return curTask;
	}

	public void setCurTask(TeachTask task) {
		this.curTask = task;
	}

}
