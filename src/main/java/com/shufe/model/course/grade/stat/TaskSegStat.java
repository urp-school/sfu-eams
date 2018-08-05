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
 * chaostone             2006-12-27            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.stat;

import java.util.List;

import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;

public class TaskSegStat extends GradeSegStats {

	TeachTask task;

	Teacher teacher;

	public TaskSegStat(TeachTask task, Teacher teacher, List courseGrades) {
		this.task = task;
		this.teacher = teacher;
		this.courseGrades = courseGrades;
	}

	public TaskSegStat(int segs) {
		super(segs);
	}

	public TeachTask getTask() {
		return task;
	}

	public void setTask(TeachTask task) {
		this.task = task;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
