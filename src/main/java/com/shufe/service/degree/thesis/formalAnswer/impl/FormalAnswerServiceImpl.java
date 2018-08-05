//$Id: FormalAnswerServiceImpl.java,v 1.1 2007-1-31 15:27:17 Administrator Exp $
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
 * chenweixiong              2007-1-31         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis.formalAnswer.impl;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.thesis.formalAnswer.FormalAnswerDAO;
import com.shufe.model.degree.thesis.answer.FormalAnswer;
import com.shufe.service.BasicService;
import com.shufe.service.degree.thesis.formalAnswer.FormalAnswerService;

public class FormalAnswerServiceImpl extends BasicService implements
		FormalAnswerService {

	private FormalAnswerDAO formalAnswerDAO;
	
	/**
	 * @param formalAnswerDAO The formalAnswerDAO to set.
	 */
	public void setFormalAnswerDAO(FormalAnswerDAO formalAnswerDAO) {
		this.formalAnswerDAO = formalAnswerDAO;
	}

	/**
	 * @see com.shufe.service.degree.thesis.formalAnswer.FormalAnswerService#getFormalAnswers(com.shufe.model.degree.thesis.answer.FormalAnswer,
	 *      java.lang.String, java.lang.String)
	 */
	public List getFormalAnswers(FormalAnswer formalAnswer,
			String departmentIdSeq, String stdTypeIdSeq) {
		return formalAnswerDAO.getFormalAnswers(formalAnswer, departmentIdSeq,
				stdTypeIdSeq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.thesis.formalAnswer.FormalAnswerService#getFormalAnswerPagination(com.shufe.model.thesis.answer.FormalAnswer,
	 *      java.lang.String, java.lang.String, int, int)
	 */
	public Pagination getFormalAnswerPagination(FormalAnswer formalAnswer,
			String departmentIdSeq, String stdTypeIdSeq, int pageNo,
			int pageSize) {
		return formalAnswerDAO.getFormalAnswerPagination(formalAnswer,
				departmentIdSeq, stdTypeIdSeq, pageNo, pageSize);
	}

	/**
	 * @see com.shufe.service.degree.thesis.formalAnswer.FormalAnswerService#batchUpdate(java.lang.String[], java.lang.Object[], java.lang.String)
	 */
	public void batchUpdate(String[] propertys, Object[] values, String formalAnswerIdSeq) {
		formalAnswerDAO.batchUpdate(propertys, values, formalAnswerIdSeq);
	}

	/* (non-Javadoc)
	 * @see com.shufe.service.thesis.formalAnswer.FormalAnswerService#getPropertys(com.shufe.model.thesis.answer.FormalAnswer, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getPropertys(FormalAnswer formalAnswer, String departmentIdSeq, String stdTypeIdSeq, String propertyNames) {
		// TODO Auto-generated method stub
		return formalAnswerDAO.getPropertys(formalAnswer, departmentIdSeq, stdTypeIdSeq, propertyNames);
	}
	
	
}
