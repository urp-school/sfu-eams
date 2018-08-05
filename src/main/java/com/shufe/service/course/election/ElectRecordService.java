//$Id: ElectRecordService.java,v 1.5 2006/12/26 03:47:20 duanth Exp $
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
 * chaostone             2006-1-4         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.election;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.course.election.ElectRecordDAO;
import com.shufe.model.course.election.ElectRecord;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;

/**
 * 学生选课结果管理、筛选服务
 * 
 * @author chaostone
 * 
 */
public interface ElectRecordService {
	/**
	 * 查询学生的选课结果
	 * 
	 * @param record
	 * @return
	 */
	public Pagination getElectRecords(ElectRecord record, int pageNo,
			int pageSize);

	/**
	 * 查询学生的选课结果
	 * 
	 * @param record
	 * @return
	 */
	public List getElectRecords(ElectRecord record);

	/**
	 * 查询学生选择教学任务的选课纪录
	 * 
	 * @param task
	 * @param std
	 * @return
	 */
	public List getElectRecords(TeachTask task, Student std);

	/**
	 * 为单个教学任务设置选课的具体学生
	 * 
	 * @param task
	 * @param pitchOnStdIds
	 * @param allStdIds
	 */
	public void setCourseTakeFor(TeachTask task, String pitchOnStdIds,
			String allStdIds);

	/**
	 * 
	 * @param electRecordDAO
	 */
	public void setElectRecordDAO(ElectRecordDAO electRecordDAO);
}
