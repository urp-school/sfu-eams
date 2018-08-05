//$Id: ThesisManageService.java,v 1.8 2006/12/06 03:29:09 cwx Exp $
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

package com.shufe.service.degree.thesis;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.thesis.ThesisManageDAO;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;

public interface ThesisManageService {

	/**
	 * @param thesisManageDAO
	 */
	public void setThesisManageDAO(ThesisManageDAO thesisManageDAO);

	/**
	 * @param thesisManage
	 * @return
	 */
	public List getThesisManages(ThesisManage thesisManage);
	
	/**根据学生得到一个论文管理对象
	 * @param student
	 * @return
	 */
	public ThesisManage getThesisManage(Student student);
	
	/**
	 * @param studentId
	 * @return
	 */
	public ThesisManage getThesisManage(Long studentId);
	
	/**
	 * @param thesisManage
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getThesisPagis(ThesisManage thesisManage,
			String departmentSeq, String stdTypeSeq, int pageNo,
			int pageSize);
	
	/**
	 *根据条件和权限得到一个指定属性非空的论文list对象
	 * @param thesisManage
	 * @param deparmentSeq
	 * @param stdTypeSeq
	 * @param notNulls
	 * @return
	 */
	public List getThesissNullNotNull(ThesisManage thesisManage,
			String deparmentSeq, String stdTypeSeq, String notNulls,String nulls);
	
	/**
	 *根据条件得到一个指定属性非空的论文分页对象.
	 * @param thesisManage
	 * @param deparmentSeq
	 * @param stdTypeSeq
	 * @param pageNo
	 * @param pageSize
	 * @param notNulls
	 * @return
	 */
	public Pagination getThesissPaginaByNullNotNull(
			ThesisManage thesisManage, String deparmentSeq, String stdTypeSeq,
			int pageNo, int pageSize, String notNulls,String nulls,String orderString);
	
	/**根据条件得到投影的指定的字段串
	 * @param thesisManage
	 * @param departmentSeq
	 * @param stdTypeSeq
	 * @param projectionNames
	 * @return
	 */
	public List getProjectionConditions(ThesisManage thesisManage,
			String departmentSeq, String stdTypeSeq,String projectionNames);
	/**
	 * 根据条件的论文管理对象
	 * @param thesisManage
	 * @param departmentSeq
	 * @param stdTypeSeq
	 * @return
	 */
	public List getThesisManages(ThesisManage thesisManage,
			String departmentSeq, String stdTypeSeq);
}

