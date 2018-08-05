//$Id: TeachWorkloadDAO.java,v 1.11 2006/12/19 13:08:42 duanth Exp $
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
import java.util.Map;

import org.hibernate.Criteria;

import com.shufe.dao.BasicDAO;
import com.shufe.model.workload.course.TeachWorkload;

public interface TeachWorkloadDAO extends BasicDAO {

	/**
	 * @see com.shufe.dao.workload.StatisticDAO#getStatistics(java.lang.String,
	 *      java.lang.Long)
	 */
	public abstract List getTeachWorkloads(Long teacherId, Long teachTaskId);

	/**
	 * 
	 * @param teachWorkload
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @return
	 */
	public List getTeachWorkloadAffirmedTaskIds(
			TeachWorkload teachWorkload, String stdTypeIdSeq,
			String departIdSeq, String calendarIdSeq);
	
	public List getIdListBycondition(TeachWorkload teachWorkload,String departmentSeq,
			String stdTypeSeq);
	/**
	 * @see com.shufe.dao.workload.StatisticDAO#getExampleStatistics(java.lang.Object,
	 *      java.lang.Class, java.lang.String)
	 */
	public abstract Criteria getExampleTeachWorkloads(
			TeachWorkload teachWorkload, String stdTypeIdSeq,
			String departIdSeq, String calendarIdSeq, String age1,
			String age2);

	/**
	 * 根据教师Id,学生类别Id,学年度,学期来得到授课工作量统计
	 * 
	 * @param teacherId
	 * @param studentTypeId
	 * @param year
	 * @param term
	 * @return
	 */
	public abstract List getTeachWorkloads(Long teacherId, Long studentTypeId,
			String year, String term);
	/**
	 * 确认教学工作量类型（affirmType
	 * 包括teacherAffirm，collegeAffirm，payReward，calcWorkload）
	 * 
	 * @param teachWorkloadIds
	 */
	public abstract void affirmType(String affirmType,
			String teachWorkloadIds, boolean b);

	/**
	 * 全部确认
	 * 
	 * @param departmentIds
	 * @param typeName
	 * @param b
	 */
	public abstract void updateAffirmAll(String departmentIds, String typeName,
			boolean b);

	/**
	 * 根据学生类别集合,得到教学日历list
	 * 
	 * @param studentTypeColl
	 * @return
	 */
	public abstract List getTeachCalendarOfTeacher(Collection studentTypeColl);

	/**
	 * 统计教学日历的时候得到当前的该老师的，面对的学生是相应院系地教学日历.
	 * @param departIdSeq
	 * @param teachCalendarIdSeq
	 * @param teacherId
	 * 
	 * @return
	 */
	public abstract List getTasksOfTeacherByChargeDepart(
			String stdTypeSeq, String departIdSeq,String teachCalendarIdSeq);

	/**
	 * 批量删除教学工作量
	 * @param departmentSeq
	 * @param stdTypeSeq
	 * @param teachCalendarCollect
	 */
	public void batchDeleteTeachWorkload(String departmentSeq, String stdTypeSeq,String teachCalendarIds);
	
	/**根据条件批量删除教学工作量,条件都是seq的 in条件
	 * @param conditionsSeq
	 */
	public void batchDeleteBySeq(Map conditionsSeq);
	
	/**
	 * @param taskCollection
	 */
	public void batchDeleteByTaskCollection(Collection taskCollection);
}