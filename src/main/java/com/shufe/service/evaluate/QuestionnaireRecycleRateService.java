//$Id: QuestionnaireRecycleRateService.java,v 1.6 2007/01/11 08:55:33 cwx Exp $
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
 * chenweixiong              2005-10-25         Created
 *  
 ********************************************************************************/

package com.shufe.service.evaluate;

import java.util.Collection;
import java.util.List;

/**
 * @author hj 2005-10-25 QuestionnaireRecycleRateService.java has been created
 */
public interface QuestionnaireRecycleRateService {


	/**
	 * 得到一个评教总人次，根椐用户所负责的教学部门串，当前教学日历，学生类别串
	 * 
	 * @param collegeId
	 * @param teachCalendar
	 * @param studentTypId
	 * @return
	 */
	public List getTotleEvaluateAmount(String collegeIds,
			Collection teachCalendarId, String studentTypId);

	/**
	 * 得到已经评教的总人次，根椐用户所负责的教学部门串，当前教学日历，学生类别串.
	 * 
	 * @param collegeIdSeq
	 * @param studentTypeIdSeq
	 * @param teachCalendars TODO
	 * @return TODO
	 */
	public List getHasEvlautaeDatas(String collegeIdSeq, String studentTypeIdSeq, Collection teachCalendars);

	/**
	 * @deprecated
	 * 根据部门Id串，学生类别Id串得到回收率对象list
	 * 
	 * @param deparmentIds
	 * @param studentTypeIds
	 * @param teachCalendars
	 * @param now
	 * @return
	 */
	public List getRecycleRateObject(String deparmentIds, String studentTypeIds, Collection teachCalendars);

	/**
	 * 根据学院Id和学生类别得到实评人数
	 * 
	 * @param collegeIdSeq
	 * @param stdTypeIdSeq
	 * @param teachCalendars TODO
	 * @return
	 */
	public List getResultNumbers(String collegeIdSeq, String stdTypeIdSeq, Collection teachCalendars);

	/**
	 * 得到该学院的该学生类别的总评人数
	 * 
	 * @param collegeId
	 * @param studentTypeId
	 * @return
	 */
	public List getTotleNumber(String collegeId, String studentTypeId,
			Collection teachCalendars);

	/**
	 * 根据部门ids得到hui收率里面的学生类别list
	 * 
	 * @param departmentIds
	 * @return
	 */
	public List getStudentTypeLists(String departmentIds);


	/**
	 * 根据学生类别串得到教学日历的集合串.
	 * 
	 * @param studentTypeIds
	 * @return
	 */
	public List getCurrentTeachCalendarList(String studentTypeIds);

	/**
	 * 根据courseTake 得到统计回收率的列表.
	 * @param departmentIds
	 * @param studentTypeIds
	 * @param teachCalendarSet
	 * @return
	 */
	public List getAllRateByCourseTask(String departmentIds,
			String studentTypeIds, Collection teachCalendarSet);
	
	/**
	 * 根据说负责的所有的部门id串，具体的一个部门，所负责的学生类别串，教学日历表 得到关于不门的具体的教学任务，评教人数，总评人数，列取出来.
	 * @param departmentIds
	 * @param studentTypeIds
	 * @param teachCalendarSet
	 * @return
	 */
	public List getDetailInfoOfTask(String departmentIds,String departmentId,String studentTypeIds,Collection teachCalendarSet,String studentOrTeacher);
}
