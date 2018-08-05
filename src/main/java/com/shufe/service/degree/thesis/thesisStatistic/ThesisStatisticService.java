//$Id: ThesisStatisticService.java,v 1.1 2006/11/28 09:19:18 cwx Exp $
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

package com.shufe.service.degree.thesis.thesisStatistic;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shufe.dao.degree.thesis.thesisStatistic.ThesisStatisticDAO;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;

public interface ThesisStatisticService {
	
	public void setThesisStatisticDAO(ThesisStatisticDAO thesisStatisticDAO);

	public List getstdGroupInfo(Student student, String departmentIdSeq,
			String stdTypeIdSeq);

	public List getTOpicOpenGroupInfo(ThesisManage thesisManage,
			String departmentIdSeq, String stdTypeIdSeq);
	
	public Map getThesisAndTeacherMap(String studentTypeIdSeq,Set stdTypes, String requireYear);
}
