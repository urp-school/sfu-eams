//$Id: ExamTakeDAO.java,v 1.1 2007-2-15 下午04:06:30 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-2-15         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.arrange.exam;

import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 考试名单存取类
 * 
 * @author chaostone
 * 
 */
public interface ExamTakeDAO extends BasicDAO {
	/**
	 * 统计参加某类考试的需要的最小场次以及人数
	 * 
	 * @param calendar
	 * @param examType
	 * @return［turn,takeCount］返回结果按照turn进行升序排列
	 */
	public List statTakeCountWithTurn(TeachCalendar calendar, List examTypes);

	/**
	 * 统计在某个学期，参加各门课程的人数(指定考试性质)
	 * 
	 * @param calendar
	 * @param examTypes
	 * @return [course,takeCount]
	 */
	public List statTakeCountInCourse(TeachCalendar calendar, List examTypes);
}
