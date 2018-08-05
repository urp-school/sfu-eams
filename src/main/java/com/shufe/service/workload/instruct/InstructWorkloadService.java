//$Id: InstructWorkloadService.java,v 1.4 2006/12/19 13:08:42 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-23         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.instruct;

import java.util.Collection;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;

import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.model.workload.instruct.InstructModulus;
import com.shufe.model.workload.instruct.InstructWorkload;

public interface InstructWorkloadService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.workload.StatisticService#getModulus(java.lang.String,
	 *      java.lang.String, java.lang.Integer)
	 */
	public abstract InstructModulus getInstructModulus(Long studentTypeId,
			Long departmentId);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.workload.StatisticService#findStatisticObjects(org.hibernate.Criteria,
	 *      int, int)
	 */
	public abstract Pagination findStatisticObjects(Criteria criteria,
			int pageNo, int pageSize);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.workload.StatisticService#getStatisticsObjects(java.lang.Object,
	 *      java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public abstract Criteria getStatisticsObjects(
			InstructWorkload instructWorkload, String departmentIds,
			String age1, String age2);

	/**
	 * 关于院系工作量的确认（教师确认，是否计工作量，是否已付报酬）
	 * 
	 * @param affirmType
	 * @param teachWorkloadIds
	 */
	public void affirmType(String affirmType, String teachWorkloadIds, boolean b);

	/**
	 * 全部确认
	 * 
	 * @param stdTypIds
	 * @param typeName
	 * @param b
	 */
	public void affirmALl(String stdTypIds, String typeName, boolean b);

	/**
	 * @param studentTypeIds
	 * @return
	 */
	public Collection getCurrentCalendarByStdType(String studentTypeIds);

	/**
	 * 统计毕业实习的工作量 同时返回 失败和成功的数目
	 * 
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param calendarId
	 */
	public void statWorkload(TeachCalendar calendar, DataRealm realm,
			String modulusType);

}