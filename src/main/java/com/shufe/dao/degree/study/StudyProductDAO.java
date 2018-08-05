//$Id: StudyThesisDAO.java,v 1.1 2007-3-5 13:01:32 Administrator Exp $
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
 * chenweixiong              2007-3-5         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.study;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.BasicDAO;

public interface StudyProductDAO extends BasicDAO {
	
	/**
	 * 得到学生论文列表
	 * @param studyThesis
	 * @param departIdSeq
	 * @param stdTypeSeq
	 * @return
	 */
	public List getStudyProducts(Object studyProduct,String departIdSeq,String stdTypeSeq);
	
	/**
	 * 学生论文的分页列表
	 * @param studyThesis
	 * @param departIdSeq
	 * @param stdTypeSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getStudyProductPagi(Object studyProduct,String departIdSeq,String stdTypeSeq,int pageNo,int pageSize);
	
	
	/**
	 * 得到论文的属性列表
	 * @param departIdSeq
	 * @param stdTypeSeq
	 * @param properties
	 * @param isDistinctStdNo TODO
	 * @param studyThesis
	 * @return
	 */
	public List getStdPropertyProduct(Object studyProduct,String departIdSeq,String stdTypeSeq,String properties, boolean isDistinctStdNo);
	
	/**
	 * 批量更新科研信息
	 * @param entityName
	 * @param properties
	 * @param values
	 * @param idSeq
	 */
	public void batchUpdate(String entityName,String[] properties,Object[] values,String idSeq);
	
	/**
	 * 批量删除信息
	 * @param entityName
	 * @param idSeq
	 */
	public void batchDelete(String entityName,String idSeq);
}
