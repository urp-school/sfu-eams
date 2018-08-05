//$Id: BookRequireStatService.java,v 1.1 2007-3-13 上午10:41:28 chaostone Exp $
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
 *chaostone      2007-3-13         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.textbook;

import java.util.List;

import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;

public interface BookRequireStatService {

	/**
	 * 统计需求数量
	 * @param calendar
	 * @param realm
	 * @param attr
	 * @param clazz
	 * @return
	 */
	List statRequireBy(TeachCalendar calendar, DataRealm realm, String attr,
			Class clazz);

	/**
	 * 统计确认量
	 * @param calendar
	 * @param realm
	 * @param attr
	 * @return
	 */
	List statCheckBy(TeachCalendar calendar, DataRealm realm, String attr,
			Class clazz);
}
