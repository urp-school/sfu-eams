//$Id: CourseTakeOfClassPredicate.java,v 1.1 2006/11/09 11:22:29 duanth Exp $
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
 * chaostone             2005-12-10         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task;

import org.apache.commons.collections.Predicate;

import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.system.baseinfo.AdminClass;

/**
 * 验证课程修读信息中的学生是否指定的班级内. 包括学生的第一专业和第二专业班级.
 * 
 * @author chaostone 2005-12-10
 */
public class CourseTakeOfClassPredicate implements Predicate {

	private AdminClass adminClass;

	public CourseTakeOfClassPredicate(AdminClass adminClass) {
		this.adminClass = adminClass;
	}

	public boolean evaluate(Object arg0) {
		CourseTake courseTake = (CourseTake) arg0;
		return courseTake.getStudent().getAdminClasses().contains(adminClass);
	}

}
