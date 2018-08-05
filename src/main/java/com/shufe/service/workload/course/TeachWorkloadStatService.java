//$Id: TeachWorkloadStatService.java,v 1.17 2007/01/21 10:47:21 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-28         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.course;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.shufe.dao.workload.course.TeachWorkloadStatDAO;
import com.shufe.model.system.calendar.TeachCalendar;

public interface TeachWorkloadStatService {

	/**
	 * @param studentTypeIds
	 * @param dataRealIdSeq
	 *            TODO
	 * @return
	 */
	public String getAllSdtTypeContainSelf(String studentTypeIds,
			String dataRealIdSeq);

	/**
	 * 根据条件得到教师总人数
	 * @param departIdSeq TODO
	 * @param stdTypeIdSeq TODO
	 * @param teacherTypeId
	 * @param teachCalendar
	 * @param deparmentId
	 * @param teacherTitleId
	 * @param studentTypeId
	 * @return
	 */
	public Map getTeacherNumberByConditions(String departIdSeq, String stdTypeIdSeq,
			Long teacherTypeId, TeachCalendar teachCalendar);

	/**
	 * 根据条件统计得到总工作量
	 * @param deparIdSeq TODO
	 * @param stdTypeIdSeq TODO
	 * @param teacherTypeId
	 * @param teachCalendar
	 * @param deparmentId
	 * @param teacherTitleId
	 * @param studentTypeId
	 * @return
	 */
	public Map getTotalWorkloadByConditions(String deparIdSeq, String stdTypeIdSeq,
			Long teacherTypeId, TeachCalendar teachCalendar);

	/**
	 * 根据教学日历和所给予的学生类别得到统计的关于教师职称的报表.
	 * 
	 * @param teachCalendar
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getNumberAndWorkloadOfTitle(TeachCalendar teachCalendar,
			String stdTypeIdSeq);

	/**
	 * @param teachCalendar
	 * @param studentTypeIds
	 * @param departmentId
	 * @param b
	 * @return
	 */
	public List getPeopleOfDepartment(TeachCalendar teachCalendar,
			String studentTypeIds, String departmentId);

	/**
	 * 根据部门id和对应的课程类别id得到相应的课程类别的数目.
	 * 
	 * @param teachCalendar
	 * @param stdTypeIdSeq
	 * @param departmentId
	 * @param courseTypeId
	 * @return
	 */
	public Map getCourseTypesOfDepartment(TeachCalendar teachCalendar,
			String stdTypeIdSeq, String collegeIdSeq);

	/**
	 * 得到一个部门所有的教师
	 * 
	 * @param registerTeacherMap
	 *            TODO
	 * @param studentTypeIds
	 * @param departmentId
	 */
	public void getRegisterTeacherOfDepartment(String departmentIds,
			Map registerTeacherMap);

	/**
	 * 根据教学日历，学年度学期得到相应的教师人数.
	 * 
	 * @param teachCalendar
	 * @param studentTypeId
	 * @param teacherTitleId
	 * @return
	 */
	public Map getTitleAndStdTypesNo(TeachCalendar teachCalendar,
			String departIdSeq, String stdIdSeq);

	/**
	 * 根据教学日历,学生类别串,以及教师所述部门,教师类别代码得到相应的工作量.
	 * 
	 * @param teachCalendar
	 * @param studentTypeIds
	 * @param isCaculate
	 *            TODO
	 * @param departmentId
	 * @param teacherTypeId
	 * @return
	 */
	public Map getWorkloadByDepartAndTeacherType(TeachCalendar teachCalendar,
			String studentTypeIds, String departmentIdSeq, Boolean isCaculate);

	/**
	 * 根据开课院系list和学生类别idc以及选择的学年度，学期得到相应的list列表.
	 * 
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param studentTypeId
	 * @param year
	 * @param term
	 * @return
	 */
	public Map getWorkloadByDataRealm(String departIdSeq, String stdTypeIdSeq,
			TeachCalendar teachCalendar);

	/**
	 * 根据开课院系list和学生类别idc以及多个教学日历得到相应的list列表
	 * 
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param teachCalendars
	 * @return
	 */
	public Map getWorkloadByDataRealm(String departIdSeq, String stdTypeIdSeq,
			Collection teachCalendars);

	/**
	 * 根据权限得到某个学院的教师 教学日历的统计
	 * 
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param teachCalendars
	 * @return
	 */
	public Map getWorkloadByTeacherAndCalendar(String departIdSeq,
			String stdTypeIdSeq, Collection teachCalendars);

	/**
	 * 根据部门idc 以及教学日历id得到相关的关于部门和对应工作量和教师人数.
	 * 
	 * @param collegeList
	 * @param teachCalendarId
	 * @return
	 */
	public List getTotalAndRegisterWorkload(List collegeList,
			Long teachCalendarId);

	/**
	 * 根据tempList和tempFlag 以及教学日历id,教师年龄得到关于授课的教师的在编教师的数量.
	 * 
	 * @param propertyName
	 * @param calendarId
	 * @param age
	 * @param stdTypeIdSeq
	 *            TODO
	 * @return
	 */
	public Map getEduDegreeAndTitle(String departmentId, String propertyName,
			Long calendarId, Integer age, String stdTypeIdSeq);

	/**
	 * 根据tempList 和tempFlag 以及教师年龄得到关于在校教师的在编数量.
	 * 
	 * @param propertyName
	 * @param age
	 * @return
	 */
	public Map getEduDegreeAndTitleInTeacher(String propertyName,
			String departmentIds, Integer age);

	/**
	 * 根据部门和学生类别c以及对应的教学日历id得到一个关于部门的在编教师和非在编教师的统计数据.
	 * 
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @param teachCalendar
	 * @return
	 */
	public List getAvgCourseNo(String departmentIdSeq, String stdTypeIdSeq,
			TeachCalendar teachCalendar);

	/**
	 * 根据条件得到工作量数据里面授课工作量教师人数，在编授课工作量，授课总工作量，在编授课人数
	 * 
	 * @param teachCalendar
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return TODO
	 */
	public List getRegisterWorkloadOfDepartment(TeachCalendar teachCalendar,
			String studentTypeIds, String departmentIds);

	/**
	 * 得到工作量某个属性的list
	 * 
	 * @param propertyOfTeachWorkload
	 * @param teachCalendarId
	 * @return
	 */
	public List getPropertyOfTeachWorkload(String propertyOfTeachWorkload,
			Long teachCalendarId);

	public void setTeachWorkloadStatDAO(
			TeachWorkloadStatDAO teachWorkloadStatDAO);
}
