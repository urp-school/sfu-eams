//$Id: QuestionnaireRecycleRateDAO.java,v 1.7 2007/01/11 08:58:16 cwx Exp $
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
 * chenweixiong              2006-8-17         Created
 *  
 ********************************************************************************/

package com.shufe.dao.evaluate;

import java.util.Collection;
import java.util.List;

import com.shufe.dao.BasicDAO;

public interface QuestionnaireRecycleRateDAO extends BasicDAO {


	public List getTotlePersonAmount(String collegeIds,
			Collection teachCalendarCollection, String studentTypeIds);
	
	
	public List getHasEvlautaeData(String collegeIds, String studentTypeIds, Collection teachCalendars);
	
	public List getTotleNumber(String collegeIds, String studentTypeIds,
			Collection teachCalendars);
	
	public List getStatisticResults(String collegeIdSeq, String stdTypeIdSeq, Collection teachCalendars);
	/**
	 * 根据部门Id串，和学生类别Id串 得到回收率的列表
	 * 
	 * @param deparmtentIds
	 * @param studentTypeIds
	 * @param teachCalenders TODO
	 * @param now
	 * @return
	 */
	public abstract List getRecycleRateObjects(String deparmtentIds,
			String studentTypeIds, Collection teachCalenders);

	/**
	 * 得到统计结果里面的所有的学生类别
	 * 
	 * @param departmentIds
	 * @return
	 */
	public abstract List getStudentTypeLists(String departmentIds);

	/**
	 * 根据相应的条件 部门所负责的idc的问题
	 * @param departmentId
	 * @param teachCalendarSet
	 * @return
	 */
	public abstract List getAllRateByCourseTask(String departmentIds,
			String studentTypeIds, Collection teachCalendarSet);

	/**
	 * 得到具体的统计详细的信息 得到教学任务 教学任务的评教总人数，已经评教的总人数，以及对应的回收率.
	 * @param departmentIds
	 * @param departmentId
	 * @param studentTypeIds
	 * @param teachCalendarSet
	 * @param studentOrTeacher
	 * @return
	 */
	public abstract List getDetailInfoOfTask(String departmentIds,
			String departmentId, String studentTypeIds,
			Collection teachCalendarSet, String studentOrTeacher);

}