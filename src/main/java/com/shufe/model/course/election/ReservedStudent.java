package com.shufe.model.course.election;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 筛选保留的学生范围
 * 
 * @author chaostone
 * 
 */
public class ReservedStudent extends LongIdObject {

	private TeachCalendar calendar;

	private String grade;

	private Speciality major;

	private SpecialityAspect majorField;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Speciality getMajor() {
		return major;
	}

	public void setMajor(Speciality major) {
		this.major = major;
	}

	public SpecialityAspect getMajorField() {
		return majorField;
	}

	public void setMajorField(SpecialityAspect majorField) {
		this.majorField = majorField;
	}

	
	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * 给定学生是否在描述范围内
	 * 
	 * @param student
	 * @return
	 */
	public boolean contains(Student student) {
		if (null != grade) {
			if (!grade.equals(student.getGrade()))
				return false;
		}
		if (null != major) {
			if (!major.equals(student.getFirstMajor())) {
				return false;
			}
		}
		if (null != majorField) {
			if (!majorField.equals(student.getFirstAspect())) {
				return false;
			}
		}
		return true;
	}

}
