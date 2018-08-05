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

import com.ekingstar.eams.system.basecode.state.Gender;
import com.shufe.model.course.arrange.task.CourseTake;

public class CourseTakeOfGenderPredicate implements Predicate {

	private Long gender = Gender.MALE;

	public CourseTakeOfGenderPredicate(Long gender) {
		this.gender = gender;
	}

	/**
	 * 没有性别的学生一律认为是男性
	 */
	public boolean evaluate(Object object) {
		CourseTake take = (CourseTake) object;
		Long targetGender = Gender.MALE;
		if (null != take.getStudent().getBasicInfo().getGender()
				&& null != take.getStudent().getBasicInfo().getGender().getId())
			targetGender = take.getStudent().getBasicInfo().getGender().getId();
		return targetGender.equals(gender);
	}

}
