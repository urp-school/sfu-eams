//$Id: Level2SubjectServiceImpl.java,v 1.4 2007/01/19 05:08:17 cwx Exp $
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

package com.shufe.service.degree.subject.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import com.ekingstar.eams.system.baseinfo.Department;
import com.shufe.dao.degree.subject.Level2SubjectDAO;
import com.shufe.model.degree.subject.Level2Subject;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.service.BasicService;
import com.shufe.service.degree.subject.Level2SubjectService;
import com.shufe.service.util.stat.StatUtils;

public class Level2SubjectServiceImpl extends BasicService implements
       Level2SubjectService {
	private Level2SubjectDAO level2SubjectDAO;
	/**
	 * 
	 */
	public Pagination getLevel2Subjects(Level2Subject subject, int pageSize, int pageNo) {
		
		if (pageSize < 0 || pageNo < 0)
			return new Pagination(new Result(0, Collections.EMPTY_LIST));
		else
			return level2SubjectDAO.getLevel2Subjects(subject, pageSize,
					pageNo);
	}

	public List getLevel2Subjects(Level2Subject subject) {
		return level2SubjectDAO.getLevel2Subjects(subject);
	}

	/* 
     *
	 */
	public void setLevel2SubjectDAO(Level2SubjectDAO level2SubjectDAO) {
		
		this.level2SubjectDAO = level2SubjectDAO;
		
	}
	
	/**
	 * @see com.shufe.service.degree.subject.Level2SubjectService#getDepartsOfSpeciality(com.shufe.model.degree.subject.Level2Subject)
	 */
	public List getDepartsOfSpeciality(Level2Subject level2Subject) {
		return level2SubjectDAO.getDepartsOfSpeciality(level2Subject);
	}

	/**
	 * @see com.shufe.service.degree.subject.Level2SubjectService#getAllStatisticDataByCollege(Level2Subject)
	 */
	public Map getAllStatisticDataByCollege(Level2Subject level2Subject) {
		List level2Subjects = getLevel2Subjects(level2Subject);
		Map returnMap = new HashMap();
		int countDoctor = 0;
		int countMaster = 0;
		for (Iterator iter = level2Subjects.iterator(); iter.hasNext();) {
			Level2Subject subject2 = (Level2Subject) iter.next();
			Speciality speciality = subject2.getSpeciality();
			Department department = speciality.getDepartment();
			if (subject2.getForDoctor().booleanValue()) {
				StatUtils.setValueToMap(department.getId()
						+ "-doctor", subject2, "list", returnMap);
				countDoctor++;
			}
			if (subject2.getForMaster().booleanValue()) {
				StatUtils.setValueToMap(department.getId()
						+ "-master", subject2, "list", returnMap);
				countMaster++;
			}
		}
		returnMap.put("doctor", new Integer(countDoctor));
		returnMap.put("master", new Integer(countMaster));
		return returnMap;
	}

	/**
	 * 
	 * @see com.shufe.service.degree.subject.Level2SubjectService#getPropertyOfSubject(com.shufe.model.degree.subject.Level2Subject, java.lang.String, java.lang.Boolean)
	 */
	public List getPropertyOfSubject(Level2Subject level2Subject, String propertys, Boolean isCount) {
		return level2SubjectDAO.getPropertyOfSubject(level2Subject, propertys, isCount);
	}


	
}
