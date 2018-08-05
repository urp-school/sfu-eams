//$Id: ThesisStatisticDAO.java,v 1.2 2006/12/19 13:08:40 duanth Exp $
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

package com.shufe.dao.degree.thesis.thesisStatistic;

import java.util.List;
import java.util.Set;

import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;

public interface ThesisStatisticDAO {

	/**
	 * 根据条件 以及权限 得到一个学生的list串
	 * 部门，学生 以及对应的人数.
	 * @param student
	 * @param departmentIdSeq
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getstdGroupInfo(Student student,String departmentIdSeq,String stdTypeIdSeq);
    
	
    /**
     * 根据条件以及权限得到一个已经开题的学生list串
     * 字段包括 部门,学生 以及对应的人数
     * @param thesisManage
     * @param departmentIdSeq
     * @param stdTypeIdSeq
     * @return
     */
    public List getTOpicOpenGroupInfo(ThesisManage thesisManage,String departmentIdSeq,String stdTypeIdSeq);
    
    
    public List getThesisAndTeachers(String stdTypeIdSeq, Set stdTypes, String requireYear);
}
