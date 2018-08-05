//$Id: PreAnswerDAO.java,v 1.1 2007/01/24 14:16:43 cwx Exp $
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
 * chenweixiong              2006-12-11         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.thesis.preAnswer;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.PreAnswer;



public interface PreAnswerDAO {

	/**
	 * 批量设置时间和地点
	 * @param answerIdSeq
	 * @param answerTime
	 * @param anwerAddress
	 */
	public void batchUpdateTimeAndAddress(String answerIdSeq,
			String answerTime, String anwerAddress);

	/**
	 * 批量设置导师确认字段
	 * @param preAnswerIdSeq
	 * @param flag
	 */
	public void batchUpdateTutorAffirm(String preAnswerIdSeq, Boolean flag);
	/**
	 * @param preAnswer
	 * @param departmentSeq
	 * @param stdTypeSeq
	 * @return
	 */
	
	public List  getPreAnswers(PreAnswer preAnswer,
			String departIdSeq, String stdTypeIdSeq);
	
	/**
	 * 得到当前属性
	 * @param preAnswer
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param property
	 * @return
	 */
	public List getPropertyList(PreAnswer preAnswer, String departIdSeq,
			String stdTypeIdSeq, String property);
	
	/**
	 * 得到未申请预答辩的学生名单
	 * @param thesisManage
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPaginationOfNoApply(ThesisManage thesisManage,
			String departIdSeq, String stdTypeIdSeq, int pageNo,
			int pageSize);
	
	/**
	 * 得到未申请预答辩的学生名单
	 * @param thesisManage
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getStdsOfNoApply(ThesisManage thesisManage,
			String departIdSeq, String stdTypeIdSeq);
}
