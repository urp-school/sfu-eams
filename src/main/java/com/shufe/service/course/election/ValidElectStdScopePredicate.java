//$Id: ValidElectStdScopePredicate.java,v 1.2 2006/08/31 12:06:10 duanth Exp $
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
 * chaostone             2005-12-9         Created
 *  
 ********************************************************************************/
package com.shufe.service.course.election;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.shufe.model.course.election.ElectStdScope;

/**
 * 
 * @author Chaostone
 * 
 */
public class ValidElectStdScopePredicate implements Predicate {

	public boolean evaluate(Object arg0) {
		ElectStdScope scope = (ElectStdScope) arg0;
		if ((null == scope.getStartNo()) ^ (null == scope.getEndNo()))
			return false;
		if (null == scope.getStartNo() && null == scope.getEndNo()) {
			if (StringUtils.isBlank(scope.getStdTypeIds()))
				return false;
			if (null == scope.getTask().getId())
				return false;
		}
		return true;
	}

	public static ValidElectStdScopePredicate INSTANCE = new ValidElectStdScopePredicate();

	public static ValidElectStdScopePredicate getInstance() {
		return INSTANCE;
	}
}
