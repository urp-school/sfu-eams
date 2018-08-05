//$Id: CourseTableCheckService.java,v 1.1 2007-3-20 下午08:48:28 chaostone Exp $
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
 *chaostone      2007-3-20         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task;

import java.util.List;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;

/**
 * 课表核对服务
 * 
 * @author chaostone
 * 
 */
public interface CourseTableCheckService {
	/**
	 * 统计课表核对情况
	 * 
	 * @param calendar
	 * @param realm
	 * @param attr
	 * @param clazz
	 * @param majorType
	 *            专业类别
	 * @return
	 */
	List statCheckByDepart(TeachCalendar calendar, DataRealm realm,
			MajorType majorType);

	List statCheckBy(TeachCalendar calendar, DataRealm realm, String attr,
			Class clazz);
}
