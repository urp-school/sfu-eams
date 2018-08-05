//$Id: FineCourseServiceImpl.java,v 1.2 2006/10/24 11:56:19 duanth Exp $
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
 * hc             2005-11-19         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.fineCourse.impl;

import com.shufe.dao.quality.fineCourse.FineCourseDAO;
import com.shufe.model.quality.fineCourse.FineCourse;
import com.shufe.service.BasicService;
import com.shufe.service.quality.fineCourse.FineCourseService;

public class FineCourseServiceImpl extends BasicService implements
		FineCourseService {
	private FineCourseDAO fineCourseDAO;


	public void saveOrUpdate(FineCourse fineCourse) {
		fineCourseDAO.saveOrUpdate(fineCourse);
	}

	/**
	 * @param fineCourseDAO
	 *            The refinementCourseDAO to set.
	 */
	public void setFineCourseDAO(FineCourseDAO fineCourseDAO) {
		this.fineCourseDAO = fineCourseDAO;
	}
}
