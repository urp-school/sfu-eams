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
 * chaostone             2006-8-10            Created
 *  
 ********************************************************************************/
package com.shufe.dao.degree.subject;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.BasicDAO;
import com.shufe.model.degree.subject.Level2Subject;

public interface Level2SubjectDAO extends BasicDAO {
	public Pagination getLevel2Subjects(Level2Subject subject, int pageSize,
			int pageNo);

	public List getLevel2Subjects(Level2Subject subject);
	
	
	/**
	 * @param level2Subject
	 * @return
	 * @author cwx
	 */
	public List getDepartsOfSpeciality(Level2Subject level2Subject);
	
	
	/**
	 * 得到专业的学科的属性
	 * @param level2Subject
	 * @param propertys 
	 * @param isCount 是否求和 count(*)
	 * @return
	 * @author cwx
	 */
	public List getPropertyOfSubject(Level2Subject level2Subject, String propertys, Boolean isCount);
}
