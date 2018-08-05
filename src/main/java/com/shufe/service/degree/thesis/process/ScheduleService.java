//$Id: ScheduleService.java,v 1.1 2007-6-2 12:26:40 Administrator Exp $
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
 * chenweixiong              2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.service.degree.thesis.process;

import com.shufe.model.degree.thesis.process.Schedule;
import com.shufe.model.std.Student;

public interface ScheduleService {

	public Schedule  getScheduleByStd(Student std);
}
