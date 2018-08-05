//$Id: NotEmptyPropertySelector.java,v 1.1 2006/10/16 00:41:47 duanth Exp $
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
 * chaostone             2005-9-19         Created
 *  
 ********************************************************************************/

package com.shufe.dao.util;

import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.Type;

public class NotEmptyPropertySelector implements PropertySelector {

	private static final long serialVersionUID = 2265767236729495415L;

	/**
	 * @see org.hibernate.criterion.Example.PropertySelector#include(java.lang.Object,
	 *      java.lang.String, org.hibernate.type.Type)
	 */
	public boolean include(Object object, String propertyName, Type type) {
		if (object == null)
			return false;
		if ((object instanceof Number) && ((Number) object).longValue() == 0)
			return false;
		if ("".equals(object))
			return false;
		return true;
	}

}
