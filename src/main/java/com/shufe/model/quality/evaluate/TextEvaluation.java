//$Id: TextEvaluation.java,v 1.1 2007-6-18 下午06:57:43 chaostone Exp $
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
 * chenweixiong              2007-6-18         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import java.sql.Timestamp;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 文字评教<br>
 * 学生针对上课期间的教师或课程进行文字评价。
 * 
 * @author chaostone
 * 
 */
public class TextEvaluation extends LongIdObject {
	private static final long serialVersionUID = 5933492655252488694L;

	/** 学生 */
	private Student std;

	/** 教学任务 */
	private TeachTask task;

	/** 教师 */
	private Teacher teacher;

	/** 文字评教内容 */
	private String context;

	/** 教学日历 */
	private TeachCalendar calendar;

	/** 是否课程评教 */
	private Boolean isForCourse;

	/** 是否确认 */
	private Boolean isAffirm;

	/** 评教时间 */
	private Timestamp evaluationAt;

	public TextEvaluation() {
		super();
	}

	public TextEvaluation(Student std, TeachTask task, Teacher teacher, String content) {
		setStd(std);
		setTask(task);
		setCalendar(task.getCalendar());
		setContext(content);
		setEvaluationAt(new Timestamp(System.currentTimeMillis()));
		setTeacher(teacher);
		if (null != teacher) {
			setIsForCourse(Boolean.FALSE);
		} else {
			setIsForCourse(Boolean.TRUE);
		}
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Boolean getIsAffirm() {
		return isAffirm;
	}

	public void setIsAffirm(Boolean isAffirm) {
		this.isAffirm = isAffirm;
	}

	public Boolean getIsForCourse() {
		return isForCourse;
	}

	public void setIsForCourse(Boolean isForCourse) {
		this.isForCourse = isForCourse;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
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

	public Timestamp getEvaluationAt() {
		return evaluationAt;
	}

	public void setEvaluationAt(Timestamp evaluationAt) {
		this.evaluationAt = evaluationAt;
	}

}
