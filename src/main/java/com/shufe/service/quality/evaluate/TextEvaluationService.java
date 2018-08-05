//$Id: TextEvaluationService.java,v 1.7 2007/01/09 07:55:04 cwx Exp $
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
 * chenweixiong              2005-10-21         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.evaluate;

import java.util.List;

import com.shufe.dao.quality.evaluate.TextEvaluationDAO;

/**
 * @author hj 2005-10-21 TextEvaluationService.java has been created
 */
public interface TextEvaluationService {

	public void setTextEvaluationDAO(TextEvaluationDAO textEvaluationDAO);

	/**
	 * 根据学生的学号得到所有的文字评教的对象列表.
	 * 
	 * @param studentId
	 * @return
	 */
	public List getTextEvaluatesOfStd(Long studentId);

	/**
	 * 批量确认或取消确认
	 * 
	 *@author 鄂州蚊子 
	 *@param textEvaluationIds
	 *@param isAffirm
	 */
	public void updateForAffirm(Long[] textEvaluationIds, Boolean isAffirm);
}
