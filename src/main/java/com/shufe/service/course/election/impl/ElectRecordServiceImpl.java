//$Id: ElectRecordServiceImpl.java,v 1.14 2006/12/26 03:47:19 duanth Exp $
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

package com.shufe.service.course.election.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import com.shufe.dao.course.election.ElectRecordDAO;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.election.ElectRecord;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.course.election.ElectRecordService;

/**
 * 学生选课结果管理、筛选服务实现
 * 
 * @author chaostone
 * 
 */
public class ElectRecordServiceImpl extends BasicService implements
		ElectRecordService {
	private ElectRecordDAO electRecordDAO;

	public Pagination getElectRecords(ElectRecord record, int pageNo,
			int pageSize) {
		if (pageNo > 0 && pageSize > 0 && null != record)
			return electRecordDAO.getElectRecords(record, pageNo, pageSize);
		else
			return new Pagination(new Result(0, Collections.EMPTY_LIST));
	}

	/**
	 * @see ElectRecordService#setCourseTakeFor(TeachTask, String)
	 */
	public void setCourseTakeFor(TeachTask task, String pitchOnStdIds,
			String allStdIds) {
		Set courseTakeSet = task.getTeachClass().getCourseTakes();

		List tobeRemoved = new ArrayList();
		List tobeAdded = new ArrayList();
		Map courseTakeMap = new HashMap();
		for (Iterator iter = courseTakeSet.iterator(); iter.hasNext();)
			courseTakeMap.put(((CourseTake) iter.next()).getStudent(),
					Boolean.TRUE);

		for (Iterator iter = task.getElectInfo().getElectResults().iterator(); iter
				.hasNext();) {
			ElectRecord record = (ElectRecord) iter.next();
			if (allStdIds.indexOf(record.getStudent().getId().toString()) == -1)
				continue;
			if (pitchOnStdIds.indexOf(record.getStudent().getId().toString()) != -1) {
				if (null == courseTakeMap.get(record.getStudent()))
					tobeAdded.add(new CourseTake(record));
				if (record.getIsPitchOn().equals(Boolean.FALSE))
					record.setIsPitchOn(Boolean.TRUE);
			} else {
				if (null != courseTakeMap.get(record.getStudent()))
					tobeRemoved.add(new CourseTake(record));
				if (record.getIsPitchOn().equals(Boolean.TRUE))
					record.setIsPitchOn(Boolean.FALSE);
			}
		}
		courseTakeSet.addAll(tobeAdded);
		courseTakeSet.removeAll(tobeRemoved);

		task.getTeachClass().setStdCount(
				new Integer(task.getTeachClass().getCourseTakes().size()));
		utilDao.saveOrUpdate(task);
	}

	public List getElectRecords(ElectRecord record) {
		if (null != record)
			return electRecordDAO.getElectRecords(record);
		else
			return Collections.EMPTY_LIST;
	}

	public List getElectRecords(TeachTask task, Student std) {
		ElectRecord record = new ElectRecord();
		record.setTask(task);
		record.setStudent(std);
		return getElectRecords(record);
	}
	public void setElectRecordDAO(ElectRecordDAO electRecordDAO) {
		this.electRecordDAO = electRecordDAO;

	}
}
