//$Id: TaskGroupDAO.java,v 1.1 2006/11/09 11:22:02 duanth Exp $
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
 * chaostone             2005-11-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.arrange.task;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 课程组数据存取接口
 * 
 * @author chaostone 2005-11-18
 */
public interface TaskGroupDAO extends BasicDAO {
	/**
	 * 查找指定id的课程组
	 * 
	 * @param id
	 * @return
	 */
	public TaskGroup getTaskGroup(Serializable id);

	/**
	 * 返回指定id的排课组
	 * 
	 * @param groupIds
	 * @return
	 */
	public List getTaskGroups(Long[] groupIds);

	/**
	 * 获得指定日期内的
	 * 
	 * @param departIds
	 * @param calendar
	 * @return
	 */
	public List getTaskGroups(Long[] departIds, TeachCalendar calendar);

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
	 *            5) order by first priority desc,then name asc .
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
	 * 获得一组排课组的"直接教学任务"的总数量.<br>
	 * "直接教学任务"@see TaskGroup#directTasks
	 * 
	 * @param groups
	 * @return
	 */
	public Integer getTaskCountOfGroup(Collection groups);

	/**
	 * 保存课程组，持久化所关联的所有信息，例如关联的班级，教室等.
	 * 
	 * @param group
	 */
	public void saveTaskGroup(TaskGroup group);

	/**
	 * 解散指定的id的课程组，
	 * 
	 * @param id
	 */
	public void removeTaskGroup(Serializable id);

	/**
	 * 解散课程组，
	 * 
	 * @param group
	 */
	public void removeTaskGroup(TaskGroup group);
}
