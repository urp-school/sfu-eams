//$Id: QuestionnaireStatDAO.java,v 1.1 2007-3-19 上午11:45:38 chaostone Exp $
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

package com.shufe.dao.quality.evaluate;

import java.util.Collection;
import java.util.List;

public interface QuestionnaireStatDAO {

	public int batchDeleteQuestionnaireStat(String stdTypeIdSeq, String departIdSeq,
			String calendarIdSeq);

	/**
	 * 查询按照问题类别分类的统计数据<br>
	 * 按照老师和教学任务以及问题类别进行分组后的统计数据
	 * 
	 * @param stdTypeIdSeq
	 * @param departIdSeq
	 * @param calendarIdSeq
	 * @param statState
	 * @return 改问题类别总得分，实际得分，人数，教师id,教学任务id,问题类别id
	 */
	public List getQuestionTypeStats(String stdTypeIdSeq, String departIdSeq, String calendarIdSeq,
			Boolean statState);

	public List getQuestionTypes(String stdTypeIdSeq, String departIdSeq, Collection teachCalendars);

	public List getDataByDepartAndRelated(String stdTypeIdSeq, String departIdSeq,
			Long calendarIdSeq);

	/**
	 * 得到饼图的数据
	 * 
	 * @param stdTypeIdSeq
	 * @param departId
	 * @param calendarId
	 * @return
	 */
	public List getDatasForPieChart(String stdTypeIdSeq, Long departId, Long calendarId);
}
