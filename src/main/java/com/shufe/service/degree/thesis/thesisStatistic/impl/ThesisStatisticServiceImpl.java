//$Id: ThesisStatisticServiceImpl.java,v 1.1 2006/11/28 09:19:38 cwx Exp $
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
 * chenweixiong              2006-11-28         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis.thesisStatistic.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.shufe.dao.degree.thesis.thesisStatistic.ThesisStatisticDAO;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;
import com.shufe.service.BasicService;
import com.shufe.service.degree.thesis.thesisStatistic.ThesisStatisticService;
import com.shufe.service.util.stat.StatUtils;

public class ThesisStatisticServiceImpl extends BasicService implements
		ThesisStatisticService {
	private ThesisStatisticDAO thesisStatisticDAO;

	public void setThesisStatisticDAO(ThesisStatisticDAO thesisStatisticDAO) {
		this.thesisStatisticDAO = thesisStatisticDAO;
	}

	public List getstdGroupInfo(Student student, String departmentIdSeq,
			String stdTypeIdSeq) {
		return thesisStatisticDAO.getstdGroupInfo(student, departmentIdSeq,
				stdTypeIdSeq);
	}

	public List getTOpicOpenGroupInfo(ThesisManage thesisManage,
			String departmentIdSeq, String stdTypeIdSeq) {
		return thesisStatisticDAO.getTOpicOpenGroupInfo(thesisManage,
				departmentIdSeq, stdTypeIdSeq);
	}
	/* (non-Javadoc)
	 * @see com.shufe.service.degree.thesis.thesisStatistic.ThesisStatisticService#getThesisAndTeacherMap(java.lang.String, java.util.Set, java.lang.String)
	 */
	public Map getThesisAndTeacherMap(String stdTypeIdSeq, Set stdTypes, String requireYear) {
		Map requestMap = new HashMap();
		Collection requireMents = thesisStatisticDAO.getThesisAndTeachers(
				stdTypeIdSeq, stdTypes, requireYear);
		Set titles = new HashSet();
		for (Iterator iter = requireMents.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Integer thesisNum = (Integer) element[0];
			Integer teacherTitls = (Integer) element[1];
			String enrollYear = (String) element[2];
			TeacherTitle title = (TeacherTitle) element[3];
			enrollYear = enrollYear.substring(0, enrollYear.indexOf("-"));
			titles.add(title);
			StatUtils.setValueToMap(enrollYear + "-0",
					thesisNum, "integer", requestMap);
			StatUtils.setValueToMap("0-0", thesisNum,
					"integer", requestMap);
			if (null == title) {
				StatUtils.setValueToMap(enrollYear
						+ "-teacher-null", teacherTitls, "integer", requestMap);
				StatUtils.setValueToMap("0-teacher-null",
						teacherTitls, "integer", requestMap);
			} else {
				StatUtils.setValueToMap(enrollYear
						+ "-teacher-" + title.getId(), teacherTitls, "integer",
						requestMap);
				StatUtils.setValueToMap("0-teacher-"
						+ title.getId(), teacherTitls, "integer", requestMap);
			}
			StatUtils.setValueToMap(enrollYear
					+ "-teacher-0", teacherTitls, "integer", requestMap);
			StatUtils.setValueToMap("0-teacher-0",
					teacherTitls, "integer", requestMap);
		}
		requestMap.put("titles", titles);
		return requestMap;
	}

}
