//$Id: ClassroomInDataRealmPredicate.java,v 1.1 2006/08/02 00:53:09 duanth Exp $
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
 * chaostone             2005-12-3         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo.impl;

import java.util.Iterator;
import java.util.Set;

import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.util.DataAuthorityPredicate;

/**
 * 判定教室是否在用户权限范围内
 * 
 * @author chaostone
 * 
 */
public class ClassroomInDataRealmPredicate extends DataAuthorityPredicate {

	public boolean evaluate(Object arg0) {
		Classroom room = (Classroom) arg0;
		Set departs = room.getDepartments();
		if (departs.isEmpty())
			return true;
		for (Iterator iter = departs.iterator(); iter.hasNext();) {
			Department depart = (Department) iter.next();
			if (departDataRealm.indexOf(String.valueOf(depart.getId())) != -1)
				return true;
		}
		return false;
	}

}
