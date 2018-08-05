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
 * chaostone             2006-3-15            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.shufe.model.system.baseinfo.Classroom;
/**
 * 排课中使用的教室比较，先比较容纳上课人数，在比较名称.
 * @author chaostone
 *
 */
public class ClassroomComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		Classroom c1 = (Classroom) o1;
		Classroom c2 = (Classroom) o2;
		return new CompareToBuilder().append(c1.getCapacityOfCourse(),
				c2.getCapacityOfCourse()).append(c1.getName(), c2.getName())
				.toComparison();
	}
}
