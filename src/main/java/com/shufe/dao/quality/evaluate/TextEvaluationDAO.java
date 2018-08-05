//$Id: TextEvaluationDAO.java,v 1.7 2007/01/09 07:55:33 cwx Exp $
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
 * chenweixiong              2006-8-17         Created
 *  
 ********************************************************************************/

package com.shufe.dao.quality.evaluate;

import java.util.List;

import com.shufe.dao.BasicDAO;

public interface TextEvaluationDAO extends BasicDAO {
	/**
	 * 根据学生id得到这个学生所有的文字评教意见.
	 * 
	 * @param studentId
	 * @return
	 */
	public List getTextEvaluatesOfStd(Long studentId);

	/**
	 *批量确认或取消确认
	 *
	 *@author 鄂州蚊子 
	 *@param textEvaluationIds
	 *@param isAffirm
	 */
	public void updateForAffirm(Long[] textEvaluationIds, Boolean isAffirm);
}