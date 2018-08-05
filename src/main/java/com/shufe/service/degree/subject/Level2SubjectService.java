//$Id: Level2SubjectService.java,v 1.4 2007/01/19 05:08:17 cwx Exp $
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
 * @author lzs
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-8-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.subject;

import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.degree.subject.Level2SubjectDAO;
import com.shufe.model.degree.subject.Level2Subject;

public interface Level2SubjectService {

	Pagination getLevel2Subjects(Level2Subject subject, int pageSize,
			int pageNo);

	List getLevel2Subjects(Level2Subject subject);

	public void setLevel2SubjectDAO(Level2SubjectDAO level2SubjectDAO);
	
	/**
	 * 根据2级学科对象得到专业的部门列表
	 * @param level2Subject
	 * @return
	 * @author cwx
	 */
	public List getDepartsOfSpeciality(Level2Subject level2Subject);
	
	/**
	 * @param level2Subject
	 * @return
	 * @author cwx
	 */
	public Map getAllStatisticDataByCollege(Level2Subject level2Subject);
	
	/**
	 * @param level2Subject
	 * @param propertys
	 * @param isCount
	 * @return
	 */
	public List getPropertyOfSubject(Level2Subject level2Subject, String propertys, Boolean isCount);
}
