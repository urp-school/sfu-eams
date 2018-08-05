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
 * chaostone             2006-11-28            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam.comparator;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.shufe.model.course.task.TeachTask;

/**
 * 排考中同一个ExamArrangeGroup内的教学任务的安排顺序<br>
 * 如果要求按照校区，按照校区代码升序,后再先按照教师排序,在按照人数排序
 * @author chaostone
 * 
 */
public class ExamTeachTaskComparator implements Comparator {

	boolean orderBySchoolDistrict = true;

	public ExamTeachTaskComparator(boolean orderBySchoolDistrict) {
		this.orderBySchoolDistrict = orderBySchoolDistrict;
	}

	public int compare(Object arg0, Object arg1) {
		TeachTask task0 = (TeachTask) arg1;
		TeachTask task1 = (TeachTask) arg0;
		if (orderBySchoolDistrict) {
			int comp = new CompareToBuilder().append(task1.getArrangeInfo().getSchoolDistrict(),
					task0.getArrangeInfo().getSchoolDistrict()).toComparison();
			if (comp != 0)
				return comp;
		}
		return new CompareToBuilder().append(task0.getArrangeInfo().getTeacherNames(),
				task1.getArrangeInfo().getTeacherNames()).append(
				task0.getTeachClass().getStdCount(), task1.getTeachClass().getStdCount())
				.toComparison();

	}

}
