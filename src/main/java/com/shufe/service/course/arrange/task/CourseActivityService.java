//$Id: CourseActivityService.java,v 1.2 2006/12/19 10:07:07 duanth Exp $
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
package com.shufe.service.course.arrange.task;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import com.ekingstar.eams.system.time.CourseUnit;
import com.shufe.dao.course.arrange.task.CourseActivityDAO;

public interface CourseActivityService {
	
	public void setCourseActivityDAO(CourseActivityDAO activityDAO);

	/**
	 * 存储一批教学任务中的教学活动
	 * 
	 * @param tasks
	 */
	public void saveActivities(Collection tasks);

	/**
	 * 批量删除排课结果
	 * 
	 * @param tasks
	 */
	public void removeActivities(Collection tasks);

	/**
	 * 批量删除排课结果
	 * 
	 * @param taskIds
	 */
	public void removeActivities(Long[] taskIds);
	
	/**
	 * 返回指定教学任务和指定日期的所有上课节次信息.
	 * @param taskId
	 * @param date
	 * @return
	 * @see CourseUnit
	 */
	public List getCourseUnits(Long taskId,Date date);
}
