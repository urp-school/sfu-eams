//$Id: BeanPredicateWrapper.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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
 * chaostone             2005-12-20         Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.predicate;

import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;

public class BeanPredicateWrapper extends BeanPredicate implements
		EvaluateValueRemember {

	public BeanPredicateWrapper(String arg0, Predicate arg1) {
		super(arg0, arg1);
	}

	public Object getEvaluateValue(Object obj) {
		try {
			Object value = PropertyUtils.getProperty(obj, this
					.getPropertyName());
			return (value == null) ? "" : value.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
