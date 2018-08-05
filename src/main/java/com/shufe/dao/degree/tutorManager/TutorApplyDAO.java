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
 * 塞外狂人             2006-9-5            Created
 *  
 ********************************************************************************/
package com.shufe.dao.degree.tutorManager;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.tutorManager.tutor.TutorApply;


public interface TutorApplyDAO {

	/**
	 * 根据条件得到查询的结果
	 * @param tutorApply
	 * @param departIdSeq
	 * @return
	 */
	public List getTutorApplyList(TutorApply tutorApply, String departIdSeq);
	
	
	/**
	 * 根据条件得到查询的分页对象
	 * @param tutorApply
	 * @param departIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getTutorApplyPagi(TutorApply tutorApply,
			String departIdSeq, int pageNo, int pageSize);

}

