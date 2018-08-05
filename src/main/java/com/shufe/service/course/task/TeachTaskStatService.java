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
 * chaostone             2006-12-9            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.task;

import java.util.List;

import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.util.stat.StatGroup;

/**
 * 教学任务统计服务
 * 
 * @author chaostone
 * 
 */
public interface TeachTaskStatService {

	/**
	 * 统计班级上课的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statClassTask(TeachCalendar calendar, DataRealm dataRealm);

	/**
	 * 统计教师的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statTeacherTask(TeachCalendar calendar, DataRealm dataRealm);

	/**
	 * 统计课程类别的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statCourseTypeTask(TeachCalendar calendar, DataRealm dataRealm);

	/**
	 * 统计学生类别的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statStdTypeTask(TeachCalendar calendar, DataRealm dataRealm);

	/**
	 * 统计开课院系的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statTeachDepartTask(TeachCalendar calendar, DataRealm dataRealm);

	/**
	 * 统计教师的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statDepartTask(TeachCalendar calendar, DataRealm dataRealm);

	/**
	 * 统计教室的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statRoomTask(TeachCalendar calendar, DataRealm dataRealm);

	/**
	 * 统计开课院系的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statTeachDepartConfirm(TeachCalendar calendar,
			DataRealm dataRealm);

	/**
	 * 统计课程类别的课时,总课时和学分
	 * 
	 * @param calendar
	 * @param limit
	 * @return
	 */
	public List statCourseTypeConfirm(TeachCalendar calendar,
			DataRealm dataRealm);

	/**
	 * 统计双语情况
	 * 
	 * @param calendar
	 * @param dataRealm
	 * @return
	 */
	public StatGroup statBilingual(TeachCalendar calendar, DataRealm dataRealm);

	/**
	 * 统计上课教室类型
	 * 
	 * @param calendar
	 * @param dataRealm
	 * @return
	 */
	public StatGroup statRoomConfigType(TeachCalendar calendar,
			DataRealm dataRealm);

	/**
	 * 统计教学任务中的职称分布
	 * @param calendars
	 * @return
	 */
	public List statTeacherTitle(List calendars);
}
