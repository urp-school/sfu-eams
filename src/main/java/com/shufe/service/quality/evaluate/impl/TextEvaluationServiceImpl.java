//$Id: TextEvaluationServiceImpl.java,v 1.7 2007/01/09 07:55:04 cwx Exp $
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

package com.shufe.service.quality.evaluate.impl;

import java.util.List;

import com.shufe.dao.quality.evaluate.TextEvaluationDAO;
import com.shufe.service.BasicService;
import com.shufe.service.quality.evaluate.TextEvaluationService;

/**
 * @author hj 2005-10-21 TextEvaluationServiceImpl.java has been created
 */
public class TextEvaluationServiceImpl extends BasicService implements
		TextEvaluationService {

	private TextEvaluationDAO textEvaluationDAO;

	/**
	 * @param textEvaluationDAO
	 *            要设置的 textEvaluationDAO.
	 */
	public void setTextEvaluationDAO(TextEvaluationDAO textEvaluationDAO) {
		this.textEvaluationDAO = textEvaluationDAO;
	}

	/**
	 * @see com.shufe.service.evaluate.TextEvaluationService#getTextEvaluationsOfStudent(java.lang.String)
	 */
	public List getTextEvaluatesOfStd(Long studentId) {
		return textEvaluationDAO.getTextEvaluatesOfStd(studentId);
	}

	public void updateForAffirm(Long[] textEvaluationIds, Boolean isAffirm) {
		textEvaluationDAO.updateForAffirm(textEvaluationIds,isAffirm);
	}
}
