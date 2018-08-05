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
 * chaostone             2007-1-4            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.shufe.model.course.task.TeachTask;

/**
 * 教学班成绩打印模型
 * 
 * @author chaostone
 * 
 */
public class TeachClassGrade {
	List gradeTypes;

	TeachTask task;

	List courseGrades;

	/**
	 * 每个学生对应的序号from 0
	 */
	int beginIndex = 0;

	public TeachClassGrade() {
		super();
	}

	public TeachClassGrade(List gradeTypes, TeachTask task, List courseGrades) {
		this.gradeTypes = gradeTypes;
		Collections.sort(gradeTypes,
				new PropertyComparator("priority", true));
		this.task = task;
		this.courseGrades = courseGrades;
		if (!CollectionUtils.isEmpty(courseGrades)) {
			Collections.sort(courseGrades,
					new PropertyComparator("std.code"));
		}
	}

	public static List buildTaskClassGrade(List gradeTypes, TeachTask task,
			List courseGrades, Integer stdPerClass) {
		List classGrades = new ArrayList();
		TeachClassGrade teachClassGrade = new TeachClassGrade(gradeTypes, task,
				courseGrades);
		int begin = 0;
		while (teachClassGrade.getCourseGrades().size() - begin > stdPerClass
				.intValue()) {
			TeachClassGrade other = new TeachClassGrade();
			other.setTask(teachClassGrade.getTask());
			other.setGradeTypes(teachClassGrade.getGradeTypes());
			int end = begin + stdPerClass.intValue();
			if (teachClassGrade.getCourseGrades().size() < begin
					+ stdPerClass.intValue()) {
				end = teachClassGrade.getCourseGrades().size();
			}
			other.setCourseGrades(new ArrayList(teachClassGrade
					.getCourseGrades().subList(begin, end)));
			other.setBeginIndex(begin);
			// teachClassGrade.getCourseGrades().removeAll(other.getCourseGrades());
			classGrades.add(other);
			begin += stdPerClass.intValue();
		}
		//移出其他各组的成绩名单
		for (Iterator iter = classGrades.iterator(); iter.hasNext();) {
			TeachClassGrade element = (TeachClassGrade) iter.next();
			teachClassGrade.getCourseGrades().removeAll(
					element.getCourseGrades());
		}
		teachClassGrade.setBeginIndex(begin);
		classGrades.add(teachClassGrade);
		return classGrades;
	}

	/**
	 * 制作模板报表时使用。
	 * @param index form 1
	 * @return
	 */
	public String indexNo(int index) {
		if (index <= courseGrades.size()) {
			return beginIndex + index + "";
		} else {
			return "";
		}
	}

	public List getCourseGrades() {
		return courseGrades;
	}

	public void setCourseGrades(List courseGrades) {
		this.courseGrades = courseGrades;
	}

	public List getGradeTypes() {
		return gradeTypes;
	}

	public void setGradeTypes(List gradeTypes) {
		this.gradeTypes = gradeTypes;
	}

	public TeachTask getTask() {
		return task;
	}

	public void setTask(TeachTask task) {
		this.task = task;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

}
