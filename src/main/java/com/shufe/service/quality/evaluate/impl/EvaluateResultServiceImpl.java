//$Id: EvaluateResultServiceImpl.java,v 1.1 2007-6-2 20:05:32 Administrator Exp $
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
 * chenweixiong              2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.evaluate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.quality.evaluate.EvaluateResult;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.quality.evaluate.EvaluateResultService;

public class EvaluateResultServiceImpl extends BasicService implements EvaluateResultService {

	public List getTaskIdAndTeacherIdOfResult(Student student, Collection teachCalendars) {
		List results = new ArrayList();
		if (null == student || null == teachCalendars) {
			return Collections.EMPTY_LIST;
		}
		EntityQuery entityQuery = new EntityQuery(EvaluateResult.class, "result");
		entityQuery.join("result.task", "task");
		entityQuery.add(new Condition("result.student=:student", student));
		entityQuery.add(new Condition("result.teachCalendar in(:calendars)", teachCalendars));
		entityQuery
				.setSelect("select result.task.id,result.teacher.id,task.requirement.evaluateByTeacher");
		results.addAll(utilService.search(entityQuery));
		return results;
	}

	/**
	 * @see com.shufe.service.quality.evaluate.EvaluateResultService#getResultByStdIdAndTaskId(java.lang.Long,
	 *      java.lang.Long, Long)
	 */
	public EvaluateResult getResultByStdIdAndTaskId(Long stdId, Long taskId, Long teacherId) {
		EntityQuery query = new EntityQuery(EvaluateResult.class, "result");
		query.add(new Condition("result.student.id=:stdId", stdId));
		query.add(new Condition("result.task.id=:taskId", taskId));
		if (null != teacherId) {
			query.add(new Condition("result.teacher.id=:teacherId", teacherId));
		} else {
			query.add(new Condition("result.teacher is null"));
		}
		Collection results = utilService.search(query);
		return results.size() > 0 ? (EvaluateResult) results.iterator().next() : null;
	}

	/**
	 * 批量删除
	 */
	public void batchDeleteEvaluateDatas(String departIdSeq, String stdTypeIdSeq,
			String calendarIdSeq) {
		Map valueMap = new HashMap();
		valueMap.put("departmentIds", SeqStringUtil.transformToLong(departIdSeq));
		valueMap.put("stdTypeIds", SeqStringUtil.transformToLong(stdTypeIdSeq));
		valueMap.put("teachCalendarIds", SeqStringUtil.transformToLong(calendarIdSeq));
		utilDao.executeUpdateNamedQuery("removeQuestionResult", valueMap);
		utilDao.executeUpdateNamedQuery("removeEvaluateResult", valueMap);
	}
}
