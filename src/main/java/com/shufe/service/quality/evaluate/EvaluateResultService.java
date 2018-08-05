//$Id: EvaluateResultService.java,v 1.1 2007-6-2 20:04:50 Administrator Exp $
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

package com.shufe.service.quality.evaluate;

import java.util.Collection;
import java.util.List;

import com.shufe.model.quality.evaluate.EvaluateResult;
import com.shufe.model.std.Student;

public interface EvaluateResultService {

	public List getTaskIdAndTeacherIdOfResult(Student student,Collection teachCalendars);
	
	/**
	 * 根据一个教学任务id和一个学生id得到该学生对这个教学日历的评价结果
	 * @param stdId
	 * @param taskId
	 * @param teacherId 为null时,查找教师为null的数据
	 * @return
	 */
	public EvaluateResult getResultByStdIdAndTaskId(Long stdId,Long taskId, Long teacherId);
	
	/**
	 * 根据学生类别,开课院系，教学日历 来删除评教详细数据
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param calendarIdSeq
	 */
	public void batchDeleteEvaluateDatas(String departIdSeq,String stdTypeIdSeq,String calendarIdSeq);
}
