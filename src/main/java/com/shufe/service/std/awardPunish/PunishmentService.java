//$Id: PunishmentService.java,v 1.1 2007-5-29 上午10:29:12 chaostone Exp $
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
 *chaostone      2007-5-29         Created
 *  
 ********************************************************************************/

package com.shufe.service.std.awardPunish;

import com.shufe.model.std.Student;
import com.shufe.model.std.awardPunish.Punishment;

public interface PunishmentService {

	public Punishment getWorstPunishment(Student std);

}
