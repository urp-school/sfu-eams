//$Id: TeacherStatService.java,v 1.1 2007-4-3 下午03:57:17 chaostone Exp $
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
 *chaostone      2007-4-3         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo.stat;

import java.util.List;

import com.shufe.model.system.security.DataRealm;

public interface TeacherStatService {

	public List statByDegree(DataRealm dataRelm);

	public List statByEduDegree(DataRealm dataRelm);

	public List statByTitle(DataRealm dataRelm);

	public List statByGraduateSchool(DataRealm dataRelm);
}
