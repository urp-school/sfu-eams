//$Id: OrPredicateWrapper.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.OrPredicate;

public class OrPredicateWrapper implements EvaluateValueRemember, Predicate {
	OrPredicate orPredicate;

	public OrPredicateWrapper(OrPredicate orPredicate) {
		this.orPredicate = orPredicate;
	}

	public Object getEvaluateValue(Object obj) {
		String value = "";
		for (int i = 0; i < orPredicate.getPredicates().length; i++)
			if (orPredicate.getPredicates()[i] instanceof EvaluateValueRemember) {
				value += ((EvaluateValueRemember) orPredicate.getPredicates()[i])
						.getEvaluateValue(obj).toString()
						+ ",";
			}
		return value;
	}

	public boolean evaluate(Object arg0) {
		return orPredicate.evaluate(arg0);
	}

}
