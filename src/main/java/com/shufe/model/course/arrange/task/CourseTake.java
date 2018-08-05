//$Id: CourseTake.java,v 1.8 2007/01/24 11:42:51 duanth Exp $
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
 * chaostone             2005-12-2         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.election.ElectRecord;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;

/**
 * 学生修读课程信息<br>
 * 同一个task的构成在一个教学班中
 * 
 * @author chaostone 2005-12-2
 */
public class CourseTake extends LongIdObject implements Cloneable, Comparable {

	private static final long serialVersionUID = -4305006607087691491L;
	/** 教学任务 */
	private TeachTask task;

	/** 学生 */
	private Student student;

	/** 是否该课程已经评教完成 */
	public Boolean isCourseEvaluated = Boolean.FALSE;

	/** 修读类别 */
	private CourseTakeType courseTakeType;

	/** 课程成绩 */
	private CourseGrade courseGrade;

	/** 历次考记录 */
	private Map examTakes = new HashMap();

	/** 备注 */
	private String remark;

	public Map getExamTakes() {
		return examTakes;
	}

	public void setExamTakes(Map examTakes) {
		this.examTakes = examTakes;
	}

	public ExamTake getExamTake(ExamType examType) {
		if (null == getExamTakes())
			return null;
		else {
			if (examType.getId().equals(ExamType.DELAY_AGAIN)) {
				ExamTake take = (ExamTake) getExamTakes().get(ExamType.DELAY_AGAIN);
				if(null==take){
					take = (ExamTake) getExamTakes().get(ExamType.AGAIN);
				}
				if (null == take) {
					return (ExamTake) getExamTakes().get(ExamType.DELAY);
				} else {
					return take;
				}
			}
			return (ExamTake) getExamTakes().get(examType.getId());
		}
	}

	/**
	 * 没有排考的返回TRUE
	 * 
	 * @param examType
	 * @return
	 */
	public Boolean isAttendExam(ExamType examType) {
		ExamTake examTake = getExamTake(examType);
		if (null != examTake) {
			return Boolean.valueOf(Boolean.TRUE.equals(examTake.getExamStatus().getIsAttend()));
		} else {
			return Boolean.TRUE;
		}
	}

	public CourseTake() {
		task = new TeachTask();
		student = new Student();
	}

	public CourseTake(Long takeId) {
		setId(takeId);
	}

	public CourseTake(TeachTask task, Student student, CourseTakeType courseTakeType) {
		this.task = task;
		this.student = student;
		this.courseTakeType = courseTakeType;
		this.isCourseEvaluated = Boolean.FALSE;
	}

	/**
	 * 从选课记录中构造一个上课信息
	 * 
	 * @param recode
	 */
	public CourseTake(ElectRecord recode) {
		this.task = recode.getTask();
		this.student = recode.getStudent();
		this.courseTakeType = recode.getCourseTakeType();
		this.isCourseEvaluated = Boolean.FALSE;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		CourseTake take = new CourseTake();
		take.setCourseTakeType(getCourseTakeType());
		take.setStudent(getStudent());
		// 新的纪录没有评教
		take.setIsCourseEvaluated(Boolean.FALSE);
		return take;
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the courseTakeType.
	 */
	public CourseTakeType getCourseTakeType() {
		return courseTakeType;
	}

	/**
	 * @param courseTakeType
	 *            The courseTakeType to set.
	 */
	public void setCourseTakeType(CourseTakeType courseTakeType) {
		this.courseTakeType = courseTakeType;
	}

	/**
	 * @return Returns the student.
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            The student to set.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return Returns the task.
	 */
	public TeachTask getTask() {
		return task;
	}

	/**
	 * @param task
	 *            The task to set.
	 */
	public void setTask(TeachTask task) {
		this.task = task;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CourseTake)) {
			return false;
		}
		// FIXME 去掉教学任务的判断，实际上像一个新的教学任务添加学生时会报错
		CourseTake rhs = (CourseTake) object;
		return new EqualsBuilder().append(getTask(), rhs.getTask()).append(getStudent().getId(),
				rhs.getStudent().getId()).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1219136969, -1215718665).append(getStudent().getId())
				.toHashCode();
	}

	/**
	 * @return 返回 courseType.
	 */
	/*public CourseType getCourseType() {
		return courseType;
	}

	*//**
	 * @param courseType
	 *            要设置的 courseType.
	 *//*
	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}*/

	/**
	 * @return Returns the isCourseEvaluated.
	 */
	public Boolean getIsCourseEvaluated() {
		return isCourseEvaluated;
	}

	/**
	 * @param isCourseEvaluated
	 *            The isCourseEvaluated to set.
	 */
	public void setIsCourseEvaluated(Boolean isCourseEvaluated) {
		this.isCourseEvaluated = isCourseEvaluated;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		CourseTake myClass = (CourseTake) object;
		return new CompareToBuilder().append(this.student.getCode(), myClass.student.getCode())
				.toComparison();
	}

	/**
	 * 查询课程安排的文字描述
	 * 
	 * @return
	 */
	public String getArrangeInfo() {
		return getTask().getArrangeInfo().digest(getTask().getCalendar());
	}

	public CourseGrade getCourseGrade() {
		return courseGrade;
	}

	public void setCourseGrade(CourseGrade courseGrade) {
		this.courseGrade = courseGrade;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
