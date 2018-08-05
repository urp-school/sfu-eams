//$Id: AnnotateDAO.java,v 1.3 2006/12/19 13:08:42 duanth Exp $
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
 * chenweixiong              2006-11-9         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.thesis.annotate;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.thesis.annotate.ThesisAnnotateBook;

public interface AnnotateDAO {

	public void batchUpdateAnnotateDoubleBlind(Boolean isAffirm,
			String thesisManageSeq);
	
	
	/**
	 * 得到评阅书list对象.
	 * @param thesisAnnotateBook
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getAnnotateBookList(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq, String flag);
	
	/**
	 * 对评阅书分页
	 * @param thesisAnnotateBook
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @param flag
	 * @return
	 */
	public Pagination getAnnotateBookPagination(ThesisAnnotateBook thesisAnnotateBook,
			String departmentIdSeq, String stdTypeIdSeq,int pageNo,int pageSize, String flag);
	/**
	 * 得到所有的成绩
	 * @param startNo TODO
	 * @param endNo TODO
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 */
	public List getAllMarkOfAnnotates(String startNo,
			String endNo, String departmentIdSeq, String stdTypeIdSeq);
	
	public List getStdNumOfLevel(ThesisAnnotateBook thesisAnnotateBook,
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
	
	
	public void batchUpdateDeleteBookNo(String thesisManageIdSeq);
}
