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
 * chaostone             2006-12-26            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.other;

import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.ekingstar.security.User;
import com.shufe.model.course.grade.AbstractGrade;

/**
 * 其他考试成绩
 * 
 * @author chaostone
 * 
 */
public class OtherGrade extends AbstractGrade {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5754977126994936591L;

	public void updateScore(Float score, User who) {
		setScore(score);
	}

	/**
	 * 考试类别
	 */
	private OtherExamCategory category = new OtherExamCategory();

	public OtherExamCategory getCategory() {
		return category;
	}

	public void setCategory(OtherExamCategory category) {
		this.category = category;
	}

}
