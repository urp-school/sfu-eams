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
 * chaostone             2006-11-7            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam.comparator;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.shufe.model.system.baseinfo.Classroom;

/**
 * 排考教室比较器<br>
 * 校区\教学楼\楼层\教室名称
 * 
 * @author chaostone
 * 
 */
public class ExamClassroomComparator implements Comparator {

	/**
	 * 校区\教学楼\楼层\教室名称
	 */
	public int compare(Object arg0, Object arg1) {
		Classroom room0 = (Classroom) arg0;
		Classroom room1 = (Classroom) arg1;
		int compare = new CompareToBuilder().append(room0.getSchoolDistrict(),
				room1.getSchoolDistrict()).toComparison();
		if (compare != 0)
			return compare;
		compare = new CompareToBuilder().append(room0.getBuilding(), room1.getBuilding())
				.toComparison();
		if (compare != 0)
			return compare;
		compare = new CompareToBuilder().append(room0.getFloor(), room1.getFloor()).toComparison();
		if (compare != 0)
			return compare;
		if (null != room0.getFloor()) {
			if (room0.getFloor().intValue() % 2 == 1) {
				return new CompareToBuilder().append(room0.getName(), room1.getName())
						.toComparison();
			} else {
				return new CompareToBuilder().append(room1.getName(), room0.getName())
						.toComparison();
			}
		} else {
			return 0;
		}
	}

}
