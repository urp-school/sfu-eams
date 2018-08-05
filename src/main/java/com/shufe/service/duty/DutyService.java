//$Id: DutyService.java,v 1.14 2006/12/30 12:06:14 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-12-1         Created
 *  
 ********************************************************************************/

package com.shufe.service.duty;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;

import com.shufe.model.duty.RecordDetail;

/**
 * @author dell
 */
public interface DutyService {

	/**
	 * 批量新增考勤记录
	 * @param teachTaskId
	 * @param studentIdArray
	 */
	public void batchSaveDutyRecord(Long teachTaskId, Long[] studentIdArray);

	/**
	 * 批量修改学生对因某一教学任务的考勤记录
	 * @param teachTaskId
	 * @param recordDetail
	 * @param stdNo(所有学生学号)
	 * @param dutyStatusMap(学生出勤状态)
	 */
	public void batchUpdateDutyRecord(Long teachTaskId, Long[] studentIdArray, Map dutyStatusMap, RecordDetail recordDetail);
	
	/**
	 * 批量录入学生对因某一教学任务的考勤纪录
	 * @param teachTaskId
	 * @param studentIdArray
	 * @param dutyStatusMap
	 * @param recordDetail
	 */
	public void batchInputDutyRecord(Long teachTaskId, Long[] studentIdArray, Map dutyStatusMap, RecordDetail recordDetail);

	/**
	 * 批量计算出勤率
	 * @param teachTaskId
	 * @param studentIds
	 */
	public void batchUpdateDutyRecordRatio(Long teachTaskId, Long[] studentIds);

	/**
	 * 批量修改学生详细考勤记录
	 * @param recordId
	 * @param recordDetailId
	 */
	public void batchUpdateDutyRecordDetail(Long recordId, String[] recordDetailId);
	
	/**
	 * 批量修改学生详细考勤记录
	 * @param dutyStatusMap
	 */
	public void batchUpdateDutyRecordDetail(Map dutyStatusMap);
	
	/**
	 * 批量删除学生详细考勤记录
	 * @param recordId
	 * @param recordDetailId
	 */
	/*public void batchDeleteDutyRecordDetail(Long teachTaskId, String dutyDate, String timeBegin, String timeEnd);*/

	/**
	 * 教学日历所有学期
	 * @return List
	 */
	public List loadTeachCalendarTerm();
	
	/**
	 * 列出一门课的每次考勤
	 * @param teachTaskId
	 * @return
	 */
	public List loadDutyRecordDetailsByTeachTaskId(Long teachTaskId);
	
	/**
	 * 列出指定课程指定时间的所有学生考勤
	 * @param teachTaskId
	 * @param dutyDate
	 * @param beginUnitId
	 * @param endUnitId
	 * @return
	 */
	public List loadStudentDutyRecordDetails(Long teachTaskId, String dutyDate, Long beginUnitId, Long endUnitId);
	
	/**
	 * 更新指定课程指定时间的所有学生考勤
	 * @param teachTaskId
	 * @param oldDutyDate
	 * @param oldTimeBegin
	 * @param oldTimeEnd
	 * @param allRecordStudent
	 * @param dutyDate
	 * @param timeBegin
	 * @param timeEnd
	 * @param dutyStatusMap
	 * @return
	 */
	/*public void updateStudentDutyRecordDetails(Long teachTaskId, String oldDutyDate, String oldTimeBegin, String oldTimeEnd, String allRecordStudent, String dutyDate, String timeBegin, String timeEnd, Map dutyStatusMap);*/
	
	/**
	 * 检测指定课程指定时间的指定的一些学生的考勤纪录的存在性
	 * @param stdIds
	 * @param teachTaskId
	 * @param dutyDate
	 * @param beginUnitIdString
	 * @param endUnitIdString
	 * @return
	 */
	public boolean checkDutyRecordsIfExists(String stdIds, Long teachTaskId, String dutyDate, String beginUnitIdString, String endUnitIdString);
	
	/**
	 * 获得指定课程指定时间的指定学生的考勤纪录id
	 * @param stdIds
	 * @param teachTaskId
	 * @param dutyDate
	 * @param timeBegin
	 * @param timeEnd
	 * @return
	 */
	public Long getDutyRecordIdIfExists(String stdIds, Long teachTaskId, String dutyDate, String timeBegin, String timeEnd);
	
	
	/**
	 * 统计所给学生在指定日期内的考勤纪录
	 * @param studentIdArray
	 * @param calendarList 可以为空list
	 * @param dateBegin
	 * @param dateEnd
	 * @param isCancelStat
	 * @param predicate
	 * @return
	 */
	public Map dutyRecordStatistics(Long[] studentIdArray, List calendarList, String dateBegin, String dateEnd, boolean isCancelStat, Predicate predicate);
	
	
	/** 
	 * 获得指定学生在指定教学日历中的考勤记录
	 * @param studentIdArray
	 * @param calendarList
	 * @return
	 */
	public List getDutyRecordOfStd(Long[] studentIdArray, List calendarList);
	
	/**
	 * @param teachTaskId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public Map getCourseUnitStatusMap(Long teachTaskId, Date beginDate, Date endDate);
	
	/**
	 * 保存指定学生、任务、日期、节次的考勤记录
	 * @param studentId
	 * @param teachTaskId
	 * @param day
	 * @param beginUnitId
	 * @param endUnitId
	 * @param dutyStatusId
	 */
	public void saveRecordDetail(Long studentId, Long teachTaskId, java.sql.Date day, Long beginUnitId, Long endUnitId, Long dutyStatusId);
	
	/**
	 * 保存或更新指定学生、任务、日期、节次的考勤记录
	 * @param studentId
	 * @param teachTaskId
	 * @param day
	 * @param beginUnitId
	 * @param endUnitId
	 * @param dutyStatusId
	 */
	public void updateOrSaveRecordDetail(Long studentId, Long teachTaskId, java.sql.Date day, Long beginUnitId, Long endUnitId, Long dutyStatusId);
	
	/**
	 * 删除指定学生、任务、日期、节次的考勤记录
	 * @param studentId
	 * @param teachTaskId
	 * @param day
	 * @param beginUnitId
	 * @param endUnitId
	 */
	public void deleteRecordDetail(Long studentId, Long teachTaskId, java.sql.Date day, Long beginUnitId, Long endUnitId );
	
	/**
	 * 删除指定考勤记录
	 * @param recordDetailId
	 */
	public void deleteRecordDetail(Long recordDetailId);
	
	/**
	 * 更新指定考勤记录
	 * @param recordDetailId
	 * @param dutyStatusId
	 */
	public void updateRecordDetail(Long recordDetailId, Long dutyStatusId);
	
	/**
	 * 搜索指定学生在指定日期段内的考勤记录
	 * @param stdIds
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List searchRecordDetail(Long[] stdIds, java.util.Date beginDate, java.util.Date endDate);

}
