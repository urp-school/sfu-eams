//$Id: TeachTaskStatDAO.java,v 1.1 2007-5-9 下午02:37:36 chaostone Exp $
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
 *chaostone      2007-5-9         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.task;

import java.util.List;

public interface TeachTaskStatDAO {

	/**
	 * calendarId,titleId,count
	 * 
	 * @param calendars
	 * @return
	 */
	public List statTeacherTitle(List calendars);
}
