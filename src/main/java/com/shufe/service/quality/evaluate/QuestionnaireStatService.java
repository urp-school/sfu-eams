//$Id: QuestionnaireStatService.java,v 1.1 2007-3-19 上午11:30:59 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.evaluate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ekingstar.security.User;

public interface QuestionnaireStatService {

	/**
	 * 统计评教结果
	 * 
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 *            开课院系id
	 * @param calendarIdSeq
	 * @param user
	 *            负责统计的用户
	 */
	public void statEvaluateResult(String stdTypeIdSeq, String departIdSeq,
			String calendarIdSeq, User user);

	/**
	 * 批量删除统计好的评教统计信息
	 * 
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @return
	 */
	public int batchDeleteQuestionnaireStat(String stdTypeIdSeq,
			String departIdSeq, String calendarIdSeq);

	/**
	 * 得到分类好的评教的详细信息
	 * 
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @return
	 */
	public List getQuestionTypeStats(String stdTypeIdSeq, String departIdSeq,
			String calendarIdSeq);

	/**
	 * 得到指定学期和权限内的问题类别
	 * 
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param teachCalendars
	 * @return
	 */
	public List getQuestionTypes(String stdTypeIdSeq, String departIdSeq,
			Collection teachCalendars);

	/**
	 * 得到指定特定的条件内的关系
	 * 
	 * @param optionNameMap
	 * @param departmentIds
	 * @param studentTypeIds
	 * @param teachCalendarId
	 * @return
	 */
	public Map getDataByDepartAndRelated(String stdTypeIdSeq,
			String departIdSeq, Long calendarIdSeq, Map optionNameMap);

	/**
	 * 得到特定的饼图数据
	 * 
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @param optionNameMap
	 * @return
	 */
	public Map getDatasOfPieChar(String stdTypeIdSeq, Long departId,
			Long calendarId, Map optionNameMap);
}
