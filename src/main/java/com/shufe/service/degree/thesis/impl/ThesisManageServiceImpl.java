//$Id: ThesisManageServiceImpl.java,v 1.8 2006/12/19 13:08:42 duanth Exp $
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

package com.shufe.service.degree.thesis.impl;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.dao.degree.thesis.ThesisManageDAO;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.degree.thesis.ThesisManageService;

public class ThesisManageServiceImpl extends BasicService implements ThesisManageService {

	private ThesisManageDAO  thesisManageDAO;


	/**
	 * @param thesisManageDAO The thesisManageDAO to set.
	 */
	public void setThesisManageDAO(ThesisManageDAO thesisManageDAO) {
		this.thesisManageDAO = thesisManageDAO;
	}


	/**
	 * @see com.shufe.service.degree.thesis.ThesisManageService#getThesisManages(com.shufe.model.degree.thesis.ThesisManage)
	 */
	public List getThesisManages(ThesisManage thesisManage) {
		return thesisManageDAO.getThesisManages(thesisManage, null, null);
	}


	public ThesisManage getThesisManage(Student student) {
		ThesisManage thesisManage = new ThesisManage();
		thesisManage.setStudent(student);
		List temList = getThesisManages(thesisManage);
		return temList.size() > 0 ? (ThesisManage) temList.get(0) : null;
	}


	public ThesisManage getThesisManage(Long studentId) {
		List thesisManageList = utilService.load(ThesisManage.class,
				new String[]{"student.id","majorType"}, new Object[]{studentId,new MajorType(MajorType.FIRST)});
		return thesisManageList.size() > 0 ? (ThesisManage) thesisManageList
				.get(0) : null;
	}


	/**分页对象.
	 * @see com.shufe.service.degree.thesis.ThesisManageService#getThesisManagePagis(com.shufe.model.degree.thesis.ThesisManage, int, int)
	 */
	public Pagination getThesisPagis(ThesisManage thesisManage,String departmentSeq,String stdTypeSeq, int pageNo, int pageSize) {
		return thesisManageDAO.getThesisManagePagis(thesisManage,departmentSeq,stdTypeSeq, pageNo, pageSize);
	}


	/* (non-Javadoc)
	 * @see com.shufe.service.thesis.ThesisManageService#getThesisManagesNotNull(com.shufe.model.thesis.ThesisManage, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getThesissNullNotNull(ThesisManage thesisManage, String deparmentSeq, String stdTypeSeq, String notNulls,String nulls) {
		return thesisManageDAO.getThesisManagesNotNull(thesisManage, deparmentSeq, stdTypeSeq, notNulls,nulls);
	}


	/**
	 * @see com.shufe.service.degree.thesis.ThesisManageService#getThesisManagePaginaForNotNull(com.shufe.model.degree.thesis.ThesisManage,
	 *      java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	public Pagination getThesissPaginaByNullNotNull(
			ThesisManage thesisManage, String deparmentSeq, String stdTypeSeq,
			int pageNo, int pageSize, String notNulls,String nulls,String orderString) {
		return thesisManageDAO.getThesisManagePaginaForNotNull(thesisManage,
				deparmentSeq, stdTypeSeq, pageNo, pageSize, notNulls,nulls,orderString);
	}


	/**
	 * @see com.shufe.service.degree.thesis.ThesisManageService#getThesisTopicOpenIds(com.shufe.model.degree.thesis.ThesisManage, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getProjectionConditions(ThesisManage thesisManage, String departmentSeq, String stdTypeSeq, String projectionNames) {
		return thesisManageDAO.getProjectionConditions(thesisManage, departmentSeq, stdTypeSeq, projectionNames);
	}


	/**
	 * @see com.shufe.service.degree.thesis.ThesisManageService#getThesisManages(com.shufe.model.degree.thesis.ThesisManage, java.lang.String, java.lang.String)
	 */
	public List getThesisManages(ThesisManage thesisManage, String departmentSeq, String stdTypeSeq) {
		return thesisManageDAO.getThesisManages(thesisManage, departmentSeq, stdTypeSeq);
	}
}
