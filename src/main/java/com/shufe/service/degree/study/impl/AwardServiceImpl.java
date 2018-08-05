//$Id: AwardServiceImpl.java,v 1.1 2007-3-5 18:00:44 Administrator Exp $
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

package com.shufe.service.degree.study.impl;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.study.AwardDAO;
import com.shufe.model.degree.study.StudyAward;
import com.shufe.service.BasicService;
import com.shufe.service.degree.study.AwardService;

public class AwardServiceImpl extends BasicService implements AwardService {

	private AwardDAO  awardDAO;
	/**
	 * @param awardDAO The awardDAO to set.
	 */
	public void setAwardDAO(AwardDAO awardDAO) {
		this.awardDAO = awardDAO;
	}
	public List getAwards(StudyAward award, String departIdSeq, String stdTypeIdSeq) {
		return awardDAO.getAwards(award, departIdSeq, stdTypeIdSeq);
	}
	public Pagination getPagiAward(StudyAward award, String departIdSeq,
			String stdTypeIdSeq, int pageNo, int pageSize) {
		return awardDAO.getPagiAward(award, departIdSeq, stdTypeIdSeq, pageNo, pageSize);
	}
	public List getPropertyAward(StudyAward award, String departIdSeq,
			String stdTypeIdSeq, String properties) {
		return awardDAO.getPropertyAward(award, departIdSeq, stdTypeIdSeq, properties);
	}
}
