//$Id: TutorDAO.java,v 1.2 2007/01/19 09:34:31 cwx Exp $
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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * hc             2005-11-24         Created
 *  
 ********************************************************************************/

package com.shufe.dao.degree.tutorManager;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.system.baseinfo.Tutor;

public interface TutorDAO {
	/**
	 * 导师列表
	 * @param tutor
	 * @param departmentIdSeq
	 * @return
	 */
	public List getTutorList(Tutor tutor,String departmentIdSeq);
	/**
	 * 导师分页对象
	 * @param tutor
	 * @param departmentIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getTutorPagi(Tutor tutor, String departmentIdSeq,
			int pageNo, int pageSize);
	
	/**
	 * 得到教师的属性列表
	 * @param tutor
	 * @param departIdSeq
	 * @param propertys
	 * @param isCount TODO
	 * @return
	 */
	public List getPropertyOfTutor(Tutor tutor,String departIdSeq,String propertys, Boolean isCount);

}
