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
 * chaostone             2006-4-22            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.math.NumberUtils;

import com.shufe.model.course.arrange.task.CourseTake;

public class CourseTakeOfStdNoPredicate implements Predicate {

	boolean isOdd = true;

	public CourseTakeOfStdNoPredicate(boolean isOdd) {
		this.isOdd = isOdd;
	}

	public boolean evaluate(Object object) {
		CourseTake take = (CourseTake) object;
		int num = NumberUtils.toInt(take.getStudent().getCode().substring(
				take.getStudent().getCode().length() - 1));
		if (isOdd) {
			if (num % 2 == 0)
				return false;
			else
				return true;
		} else {
			if (num % 2 == 0)
				return true;
			else
				return false;
		}
	}

}
