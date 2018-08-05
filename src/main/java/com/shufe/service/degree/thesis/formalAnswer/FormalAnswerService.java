//$Id: FormalAnswerService.java,v 1.1 2007-1-31 15:26:19 Administrator Exp $
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

package com.shufe.service.degree.thesis.formalAnswer;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.thesis.answer.FormalAnswer;

public interface FormalAnswerService {

	/**
	 * 得到根据条件的列表对象
	 * @param formalAnswer
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getFormalAnswers(FormalAnswer formalAnswer,
			String departmentIdSeq, String stdTypeIdSeq);

	/**
	 * 得到分页对象
	 * @param formalAnswer
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getFormalAnswerPagination(FormalAnswer formalAnswer,
			String departmentIdSeq, String stdTypeIdSeq, int pageNo,
			int pageSize);
	
	/**
	 * 批量更新数据
	 * @param propertys
	 * @param values
	 * @param formalAnswerIdSeq
	 */
	public void batchUpdate(String[] propertys,Object[] values, String formalAnswerIdSeq);
	
	/**
	 * 得到实体属性
	 * @param formalAnswer
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @param propertyName
	 * @return
	 */
	public List getPropertys(FormalAnswer formalAnswer,
			String departmentIdSeq, String stdTypeIdSeq,String propertyNames);
}
