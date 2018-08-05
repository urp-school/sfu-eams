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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.graduate;

import org.apache.commons.collections.Predicate;

import com.shufe.model.course.grade.CourseGrade;

/**
 * 成绩通过谓词
 */
public class CGPassPredicate implements Predicate {
	
	public static final Predicate INSTANCE = new CGPassPredicate();
	
	public CGPassPredicate() {
		super();
	}

	public static Predicate getInstance() {
        return INSTANCE;
    }
	
	public boolean evaluate(Object object) {
		CourseGrade cg = (CourseGrade) object;
		return Boolean.TRUE.equals(cg.getIsPass());
	}

}
