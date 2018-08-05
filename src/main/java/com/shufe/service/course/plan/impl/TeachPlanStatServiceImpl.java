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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-9-1            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.plan.impl;

import java.util.List;

import com.shufe.dao.course.plan.TeachPlanStatDAO;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.course.plan.TeachPlanStatService;

public class TeachPlanStatServiceImpl implements TeachPlanStatService {
	TeachPlanStatDAO teachPlanStatDAO;

	public List statByDepart(DataRealm realm, String enrollTurn) {
		return teachPlanStatDAO.statByDepart(realm, enrollTurn);
	}

	public List statByStdType(DataRealm realm, String enrollTurn) {
		return teachPlanStatDAO.statByStdType(realm, enrollTurn);
	}

	public void setTeachPlanStatDAO(TeachPlanStatDAO teachPlanStatDAO) {
		this.teachPlanStatDAO = teachPlanStatDAO;
	}

	public List getEnrollTurns(DataRealm realm) {
		return teachPlanStatDAO.getEnrollTurns(realm);
	}
}
