//$Id: InstructWorkloadDAO.java,v 1.4 2006/08/25 06:48:40 cwx Exp $
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
 * chenweixiong              2006-8-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.instruct;

import org.hibernate.Criteria;

import com.shufe.dao.BasicDAO;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.model.workload.instruct.InstructWorkload;

public interface InstructWorkloadDAO extends BasicDAO {

	/**
	 * 根据页面得到的条件，模糊查询院系统计
	 * 
	 * @param object
	 * @param departmentIds
	 * @param age1
	 * @param age2
	 * @return
	 */
	public abstract Criteria getExampleInstructWorkloads(InstructWorkload instructWorkload,
			String departmentIds, String age1, String age2);

	/**
	 * 确认教学工作量类型（affirmType 包括teacherAffirm，payReward，calcWorkload）
	 * 
	 * @param teachWorkloadIds
	 */
	public abstract void affirmType(String affirmType, String teachWorkloadIds, boolean b);

	/**
	 * 全部确认
	 * 
	 * @param stdTypeIds
	 * @param typeName
	 * @param b
	 */
	public abstract void updateAffirmAll(String stdTypeIds, String typeName, boolean b);

	/**
	 * 根据教师带队的学生类别串和教学日历id删除条件对应的实习工作量数据
	 * 
	 * @param stdTypeIdSeq
	 * @param calendarId
	 * @return TODO
	 */
	public int batchRemove(TeachCalendar calendar, DataRealm realm, String modulusType);

}