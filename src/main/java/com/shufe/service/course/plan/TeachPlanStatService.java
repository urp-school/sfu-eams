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
package com.shufe.service.course.plan;

import java.util.List;

import com.shufe.dao.course.plan.TeachPlanStatDAO;
import com.shufe.model.system.security.DataRealm;

public interface TeachPlanStatService {

	public List statByDepart(DataRealm realm, String enrollTurn);

	public List statByStdType(DataRealm realm, String enrollTurn);

	public List getEnrollTurns(DataRealm realm);

	public void setTeachPlanStatDAO(TeachPlanStatDAO teachPlanStatDAO);
}
