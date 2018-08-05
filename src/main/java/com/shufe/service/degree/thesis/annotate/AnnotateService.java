//$Id: AnnotateService.java,v 1.2 2006/12/06 03:29:09 cwx Exp $
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
 * chenweixiong              2006-11-7         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis.annotate;

import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook;

public interface AnnotateService {
	
	public void batchUpdateAnnotateDoubleBlind(Boolean isAffirm,String thesisManageSeq);
	
	/**
	 * @param thesisAnnotateBook
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @param flag
	 * @return
	 */
	public Pagination getAnnotateBookPagination(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq, int pageNo,
			int pageSize, String flag);
	
	/**
	 * 根据条件来批量更新学生的平均成绩
	 * @param startNo TODO
	 * @param endNo TODO
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 */
	public void batchUpdateAvgMark(String startNo,
			String endNo, String departmentIdSeq, String stdTypeIdSeq);
	
	/**
	 * 根据条件得到所有的论文评阅表
	 * @param thesisAnnotateBook
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @param flag TODO
	 * @return
	 */
	public List getAnnotateBooks(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq, String flag);
	
	/**
	 * @param thesisAnnotateBook
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public Map getStdNumOfLevel(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq);
	/**
	 * 平均成绩
	 * @param thesisAnnotateBook
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getStdNumOfAvgMark(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq);
	/**
	 * 成绩
	 * @param thesisAnnotateBook
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getStdNumOfMark(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq);
	
	/**
	 * 批量删除论文评阅的编号
	 * @param thesisManageIdSeq
	 */
	public void batchUpdateDeleteBookNo(String thesisManageIdSeq);
}
