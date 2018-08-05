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
package com.shufe.service.degree.tutorManager.impl;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.tutorManager.TutorApplyDAO;
import com.shufe.model.degree.tutorManager.tutor.TutorApply;
import com.shufe.service.BasicService;
import com.shufe.service.degree.tutorManager.TutorApplyService;

public class TutorApplyServiceImpl extends BasicService implements
		TutorApplyService {

	private TutorApplyDAO tutorApplyDAO;

	public void setTutorApplyDAO(TutorApplyDAO tutorApplyDAO) {
		this.tutorApplyDAO = tutorApplyDAO;
	}

	/* (non-Javadoc)
	 * @see com.shufe.service.degree.tutorManager.TutorApplyService#getTutorApplyList(com.shufe.model.degree.tutorManager.tutor.TutorApply, java.lang.String)
	 */
	public List getTutorApplyList(TutorApply tutorApply, String departIdSeq) {
		return tutorApplyDAO.getTutorApplyList(tutorApply, departIdSeq);
	}

	/* (non-Javadoc)
	 * @see com.shufe.service.degree.tutorManager.TutorApplyService#getTutorApplyPagi(com.shufe.model.degree.tutorManager.tutor.TutorApply, java.lang.String, int, int)
	 */
	public Pagination getTutorApplyPagi(TutorApply tutorApply, String departIdSeq, int pageNo, int pageSize) {
		return tutorApplyDAO.getTutorApplyPagi(tutorApply, departIdSeq, pageNo, pageSize);
	}
}

