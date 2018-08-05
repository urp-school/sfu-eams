//$Id: AwardService.java,v 1.1 2007-3-5 17:57:02 Administrator Exp $
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

package com.shufe.service.degree.study;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.model.degree.study.StudyAward;

public interface AwardService {

	/**
	 * @param award
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getAwards(StudyAward award,String departIdSeq,String stdTypeIdSeq);
	
	/**
	 * @param award
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPagiAward(StudyAward award,String departIdSeq,String stdTypeIdSeq,int pageNo,int pageSize);
	
	/**
	 * @param award
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param properties
	 * @return
	 */
	public List getPropertyAward(StudyAward award,String departIdSeq,String stdTypeIdSeq,String properties);
}
