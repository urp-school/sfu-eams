// $Id: Activity.java,v 1.3 2007/01/17 15:06:30 duanth Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-11-8         Created
 *  
 ********************************************************************************/
package com.ekingstar.eams.course.arrange;

import com.ekingstar.commons.model.LongIdEntity;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学活动
 * 
 * @author chaostone 2005-11-22
 */
public interface TeachActivity extends LongIdEntity {

	/**
	 * 教学任务
	 * 
	 * @return
	 */
	public TeachTask getTask();

	/**
	 * 学年学期
	 * 
	 * @return
	 */
	public TeachCalendar getCalendar();

	/**
	 * 教师
	 * 
	 * @return
	 */
	public Teacher getTeacher();

	/**
	 * 得到占用情况
	 * 
	 * @return
	 */
	public Occupy getOccupy();
}
