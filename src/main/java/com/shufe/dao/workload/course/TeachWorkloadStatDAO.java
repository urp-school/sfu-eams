//$Id: TeachWorkloadStatDAO.java,v 1.14 2007/01/21 10:47:21 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-8-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.course;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;

import com.shufe.dao.BasicDAO;
import com.shufe.model.system.calendar.TeachCalendar;

public interface TeachWorkloadStatDAO extends BasicDAO {

	/**
	 * 根据开课院系idc和学生类别idc以及对应的学生类别的相应的信息得到关于开课院系和学生的列表串.
	 * 
	 * @param departmentIdSeq
	 * @param studentTypeIdSeq
	 * @param studentTypeId
	 * @param year
	 * @param term
	 * @return
	 */
	public abstract List getWorkloadByDataRealm(String departmentIdSeq,
			String studentTypeIdSeq, TeachCalendar teachCalendar);
	
	/**
	 * 根据多个教学日历得到开课院系和学生的列表
	 * @param departmentIdSeq
	 * @param studentTypeIdSeq
	 * @param teachCalendars
	 * @return
	 */
	public abstract List getWorkloadByDataRealm(String departmentIdSeq,
			String studentTypeIdSeq, Collection teachCalendars);

	/**
	 * 根据权限得到某个学院的教师教学日历的数据
	 * @param departmentIdSeq
	 * @param studentTypeIdSeq
	 * @param teachCalendars
	 * @return
	 */
	public List getWorkloadOfTeacherAndCalendar(String departmentIdSeq,
			String studentTypeIdSeq, Collection teachCalendars);
	/**
	 * 根据部门list和教学日历id得到关于这个collegelist以及教学日历id的列表.
	 * 
	 * @param collegeList
	 * @param teachCalendarId
	 * @return
	 */
	public abstract List getTotalAndRegisterWorkload(List collegeList,
			Long teachCalendarId);

	/**
	 * 根据结果分别取教师职称和学历的list.
	 * @param propertyName
	 * @param calendarId
	 * @param age
	 * @param stdTypeIdSeq TODO
	 * @return
	 */
	public abstract List getEduDegreeAndTitle(String deaprtmentIds,
			String propertyName, Long calendarId, Integer age, String stdTypeIdSeq);

	/**
	 * 根据tempList 和教师年龄查询教师的个数.
	 * @param propertyName
	 * @param age
	 * @param calendarId
	 * @return
	 */
	public abstract List getEduDegreeAndTitleInTeacher(String propertyName,
			String departmentIds, Integer age);

	
	/**
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param teacherTypeId
	 * @param teachCalendar
	 * @return
	 */
	public abstract List getTeacherNumberByConditions(String departIdSeq,
			String stdTypeIdSeq, Long teacherTypeId, TeachCalendar teachCalendar);

	/**
	 * 根据查询条件计算总工作量.
	 * @param departIdSeq TODO
	 * @param stdTypeIdSeq TODO
	 * @param teacherTypeId
	 * @param teachCalendar
	 * @param deparmentId
	 * @param teacherTitleId
	 * @param studentTypeId
	 * @return
	 */
	public abstract List getTotalWorkloadByConditions(
			String departIdSeq, String stdTypeIdSeq, Long teacherTypeId,
			TeachCalendar teachCalendar);

	/**
	 * @param departIdSeq
	 * @param teachCalendar
	 * @return
	 */
	public abstract List getAvgCourseNoOfWokload(String departIdSeq,
			String stdTypeIdSeq, TeachCalendar teachCalendar);

	/**
	 * 根据所给予的学生类别和教学日历得到关于教师职称对应的工作量的报表.
	 * 
	 * @param teachCalendar
	 * @param stdTypeIdSeq
	 * @return
	 */
	public abstract List getNumberAndWorkloadOfTitle(
			TeachCalendar teachCalendar, String stdTypeIdSeq);

	/**
	 * 某一个部门的的在编教师
	 * @param studentTypeIds
	 * @param departmentId
	 * 
	 * @return
	 */
	public abstract List getRegisterPeopleOfDepartment(
			String departmentIds);

	/**
	 * 在工作量统计表里面本部门在册教师的人数. 如果boolean值为真的话，表示计算的是注册教师
	 * 
	 * @param teachCalendar
	 * @param studentTypeIds
	 * @param departmentId
	 * @param b
	 * @return
	 */
	public abstract List getPeopleOfDepartment(TeachCalendar teachCalendar,
			String studentTypeIds, String departmentIds);

	/**
	 * @param teachCalendar
	 * @param studentTypeIds
	 * @param departmentIds
	 * @return
	 */
	public abstract List getWorkloadOfDepartment(TeachCalendar teachCalendar,
			String studentTypeIds, String departmentIds);

	/**
	 * 根据部门和课程类别代码c 以及教学日历，学生类别idc得到一个list列表.
	 * 
	 * @param teachCalendar
	 * @param studentTypeIds
	 * @param departmentId
	 * @param courseTypeId
	 * @return
	 */
	public abstract List getCourseNumberOfDepartment(
			TeachCalendar teachCalendar, String studentTypeIds,
			String collegeIdSeq);

	/**
	 * 根据教师职称和学生类别得到一个汇总的和.
	 * 
	 * @param teachCalendar
	 * @param studentTypeId
	 * @param teacherTitleId
	 * @return
	 */
	public abstract List getTitleAndStdTypes(TeachCalendar teachCalendar,
			String deprtIdSeq, String stdTypeIdSeq);

	/**
	 * 根据学生类别 学年度学期 和教师类别以及教师所属部门得到相应的工作量.
	 * 
	 * @param teachCalendar
	 * @param studentTypeIds
	 * @param isCaculate TODO
	 * @param teacherTypeId
	 * @return
	 */
	public abstract List getWorkloadOfTeacherTypeAndDepart(
			TeachCalendar teachCalendar, String studentTypeIds,
			String departIdSeq, Boolean isCaculate);

	/**
	 * 产生报表的一个过渡方法.
	 * 
	 * @param teachCalendar
	 * @param studentTypeIds
	 * @param isCaculate TODO
	 * @param criteria
	 */
	public abstract Criteria transMethod(TeachCalendar teachCalendar,
			String studentTypeIds, Boolean isCaculate);
	
	/**
	 * 得到工作量的一个distinct属性
	 * @param propertyOfTeacher
	 * @param teachCalendarId
	 * @return
	 */
	public List getPropertyOfTeachWorkload(String propertyOfTeacher,Long teachCalendarId);

}