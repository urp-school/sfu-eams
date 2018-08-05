package com.shufe.service.course.arrange.task.impl;

//$Id: CourseActivityServiceImpl.java,v 1.4 2006/11/24 13:14:32 duanth Exp $
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ekingstar.eams.system.time.CourseUnit;
import com.shufe.dao.course.arrange.task.CourseActivityDAO;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.task.CourseActivityService;

/**
 * 排课活动服务实现类
 * 
 * @author Administrator
 * 
 */
public class CourseActivityServiceImpl extends BasicService implements
		CourseActivityService {

	private CourseActivityDAO courseActivityDAO;

	public void saveActivities(Collection tasks) {
		if (!tasks.isEmpty())
			courseActivityDAO.saveActivities(tasks);
	}

	public void removeActivities(Collection tasks) {
		if (!tasks.isEmpty())
			courseActivityDAO.removeActivities(tasks);
	}

	public void removeActivities(Long[] taskIds) {
		if (taskIds.length > 0)
			courseActivityDAO.removeActivities(taskIds);
	}

	public void setCourseActivityDAO(CourseActivityDAO courseActivityDAO) {
		this.courseActivityDAO = courseActivityDAO;
	}

	/**
	 * 
	 */
	public List getCourseUnits(Long taskId, Date date) {
		TeachTask task = (TeachTask) utilDao.get(TeachTask.class, taskId);
		if (null == task) {
			return Collections.EMPTY_LIST;
		} else {
			TeachCalendar calendar = task.getCalendar();

			if (!calendar.contains(date)) {
				return Collections.EMPTY_LIST;
			}
			boolean units[] = new boolean[calendar.getTimeSetting()
					.getCourseUnits().size()];

			for (Iterator iter = task.getArrangeInfo().getActivities()
					.iterator(); iter.hasNext();) {
				CourseActivity activity = (CourseActivity) iter.next();
				if (activity.getTime().contains(date)) {
					for (int i = activity.getTime().getStartUnit().intValue(); i < activity
							.getTime().getEndUnit().intValue() + 1; i++) {
						units[i - 1] = true;
					}
				}
			}

			List courseUnits = new ArrayList();
			for (Iterator iter = calendar.getTimeSetting().getCourseUnits()
					.iterator(); iter.hasNext();) {
				CourseUnit courseUnit = (CourseUnit) iter.next();
				if (units[courseUnit.getIndex().intValue() - 1])
					courseUnits.add(courseUnit);
			}
			Collections.sort(courseUnits);
			return courseUnits;
		}
	}

}
