//$Id: PunishmentServiceImpl.java,v 1.1 2007-5-29 上午10:34:38 chaostone Exp $
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

package com.shufe.service.std.awardPunish.impl;

import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.shufe.model.std.Student;
import com.shufe.model.std.awardPunish.Punishment;
import com.shufe.service.BasicService;
import com.shufe.service.std.awardPunish.PunishmentService;

public class PunishmentServiceImpl extends BasicService implements
		PunishmentService {

	public Punishment getWorstPunishment(Student std) {
		EntityQuery query = new EntityQuery(Punishment.class, "punish");
		query.add(new Condition("punish.std.id = "+ std.getId()));
		query.addOrder(new Order("punish.type.level asc"));
		List rs = (List) utilDao.search(query);
		return (rs.isEmpty()) ? null : ((Punishment) rs.get(0));
	}
}
