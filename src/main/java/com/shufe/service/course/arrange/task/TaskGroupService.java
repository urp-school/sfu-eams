//$Id: TaskGroupService.java,v 1.1 2006/11/09 11:22:29 duanth Exp $
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
 * chaostone             2005-11-16         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.course.arrange.task.TaskGroupDAO;
import com.shufe.model.course.arrange.task.AddTaskOptions;
import com.shufe.model.course.arrange.task.RemoveTaskOptions;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 课程组管理服务类
 * 
 * @author chaostone 2005-11-16
 */
public interface TaskGroupService {
	/**
	 * 查找指定id的课程组
	 * 
	 * @param id
	 * @return
	 */
	public TaskGroup getTaskGroup(Serializable id);

	/**
	 * 查询指定id的排课组
	 * 
	 * @param groupIdSeq
	 * @return
	 */
	public List getTaskGroups(String groupIdSeq);

	/**
	 * 获得指定学年度学期的课程组
	 * 
	 * @param departIdSeq
	 * @param calendar
	 * @return
	 */
	public List getTaskGroups(String departIdSeq, TeachCalendar calendar);

	/**
	 * 获得一个排课组内的教学任务数量
	 * 
	 * @param group
	 * @return
	 */
	public Integer getTaskCountOfGroup(TaskGroup group);

	/**
	 * 获得一个排课组内的教学任务数量
	 * 
	 * @param groupId
	 * @return
	 */
	public Integer getTaskCountOfGroup(Serializable groupId);

	/**
	 * 保存课程组，持久化所关联的所有信息，例如关联的班级，教室等.
	 * 
	 * @param group
	 */
	public void saveTaskGroup(TaskGroup group);

	/**
	 * 更新排课组
	 * 
	 * @param group
	 */
	public void updateTaskGroup(TaskGroup group);

	/**
	 * 解散指定的id的排课组，
	 * 
	 * @param id
	 */
	public void removeTaskGroup(Serializable id);

	/**
	 * 删除排课组内指定的任务，如果出现不在该组内的课程则忽略<br>
	 * 默认情况下，不去除任务的建议时间、建议教室
	 * 
	 * @param group
	 * @param tasks
	 */
	public void removeTaskFormGroup(TaskGroup group, Collection tasks);

	/**
	 * 删除排课组内指定的任务，如果出现不在该组内的课程则忽略
	 * 
	 * @param group
	 * @param tasks
	 * @param removeSuggestRoom
	 * @param removeSuggestTime
	 */
	public void removeTaskFormGroup(TaskGroup group, Collection tasks,
			RemoveTaskOptions options);

	/**
	 * 解散课程组，
	 * 
	 * @param group
	 */
	public void removeTaskGroup(TaskGroup group);

	/**
	 * 设置课程组存取对象
	 * 
	 * @param taskDAO
	 */
	public void setTaskGroupDAO(TaskGroupDAO groupDAO);

	/**
	 * 添加教学任务到课程组内<br>
	 * 默认不将任务的建议时间和建议教室添加到组内.
	 * 
	 * @param group
	 * @param tasks
	 */
	public void addTasks(TaskGroup group, Collection tasks);

	/**
	 * 添加教学任务到课程组内
	 * 
	 * @param group
	 * @param tasks
	 * @param options
	 *            添加时的参数
	 */
	public void addTasks(TaskGroup group, Collection tasks,
			AddTaskOptions options);

	/**
	 * get TaskGroups of disignated pageNo and pageSize.
	 * 
	 * @param group
	 *            set parameter of group.<br>
	 *            1) id accurate select without any string matchmode,null will
	 *            be ommited.<br>
	 *            2) name using matchmode.anyway<br>
	 *            3) priority using less than semantic <br>
	 *            4) isSameTime using eq,The null case is ommited. <br>
	 * @param teachDepartIds
	 *            look up taskGroup which contain task of teachDepartIds.
	 * @param calendar
	 *            the param of task.calendar.id in taskGoup
	 * @param pageNo
	 * @param pageSize
	 * @param isGP
	 *            isGPGroup
	 * @param isCompleted
	 *            Is the Group has IsCompeleted Task. The null case if ommited.
	 * @return
	 */
	public Pagination getTaskGroups(TaskGroup group, Long[] stdTypes,
			Long[] teachDepartIds, TeachCalendar calendar, int pageNo,
			int pageSize, Boolean isCompleted);

	/**
	 * 
	 * @param group
	 * @param stdTypeIdSeq
	 * @param teachDepartIdSeq
	 * @param calendar
	 * @param pageNo
	 * @param pageSize
	 * @param isGP
	 * @param isCompleted
	 * @return
	 */
	public Pagination getTaskGroups(TaskGroup group, String stdTypeIdSeq,
			String teachDepartIdSeq, TeachCalendar calendar, int pageNo,
			int pageSize, Boolean isCompleted);
}
