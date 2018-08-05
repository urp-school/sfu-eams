//$Id: ThesisManageDAO.java,v 1.8 2006/12/19 13:08:41 duanth Exp $
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
 * chenweixiong              2006-10-19         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.thesis;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;

import com.shufe.model.degree.thesis.ThesisManage;

public interface ThesisManageDAO {
	/**
	 * 根据条件得到一个criteria
	 * 
	 * @param thesisManage
	 * @param deparmentSeq
	 * @param stdTypeSeq
	 * @return
	 */
	public Criteria getThesisManageCriteria(ThesisManage thesisManage, String deparmentSeq,
			String stdTypeSeq);

	/**
	 * 根据条件得到一个criteria 同时过滤空与非空的属性
	 * 
	 * @param thesisManage
	 * @param deparmentSeq
	 * @param stdTypeSeq
	 * @param notNulls
	 * @param nulls
	 * @return
	 */
	public Criteria getThesisManageAttributeNotNull(ThesisManage thesisManage, String deparmentSeq,
			String stdTypeSeq, String notNulls, String nulls);

	/**
	 * @param thesisManage
	 * @param departmentSeq
	 * @param stdTypeSeq
	 * @return
	 */
	public List getThesisManages(ThesisManage thesisManage, String departmentSeq, String stdTypeSeq);

	/**
	 * 根据条件得到分页对象.
	 * 
	 * @param thesisManage
	 * @return
	 */
	public Pagination getThesisManagePagis(ThesisManage thesisManage, String deparmentSeq,
			String stdTypeSeq, int pageNo, int pageSize);

	/**
	 * 根据条件得到一个指定属性非空的论文分页对象.
	 * 
	 * @param thesisManage
	 * @param deparmentSeq
	 * @param stdTypeSeq
	 * @param pageNo
	 * @param pageSize
	 * @param notNulls
	 * @return
	 */
	public Pagination getThesisManagePaginaForNotNull(ThesisManage thesisManage,
			String deparmentSeq, String stdTypeSeq, int pageNo, int pageSize, String notNulls,
			String nulls, String orderString);

	/**
	 * 根据条件和权限得到一个指定属性非空的论文list对象
	 * 
	 * @param thesisManage
	 * @param deparmentSeq
	 * @param stdTypeSeq
	 * @param notNulls
	 * @return
	 */
	public List getThesisManagesNotNull(ThesisManage thesisManage, String deparmentSeq,
			String stdTypeSeq, String notNulls, String nulls);

	/**
	 * 根据条件得对应的投影条件的list
	 * 
	 * @param thesisManage
	 * @param departmentSeq
	 * @param stdTypeSeq
	 * @return
	 */
	public List getProjectionConditions(ThesisManage thesisManage, String departmentSeq,
			String stdTypeSeq, String projectionNames);
}
