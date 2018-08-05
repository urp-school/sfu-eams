//$Id: StatisticModel.java,v 1.2 2006/12/26 14:11:56 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.course;

import com.ekingstar.commons.model.Component;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.basecode.state.TeacherTitleLevel;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;

public class TeacherInfo implements Component {

	private Teacher teacher = new Teacher(); // 教师

	private String teacherName;

	private TeacherTitle teacherTitle = new TeacherTitle(); // 教师职称

	private EduDegree eduDegree = new EduDegree(); // 教师学历

	private Degree degree = new Degree(); // 教师学位

	private TeacherType teacherType = new TeacherType();// 教职工类别

	private Gender gender = new Gender(); // 性别

	private Integer teacherAge; // 教师年龄

	private Department teachDepart = new Department(); // 教师所属部门

	private TeacherTitleLevel titleLevel = new TeacherTitleLevel(); // 教师职称等级

	/**
	 * @return Returns the eduDegree.
	 */
	public EduDegree getEduDegree() {
		return eduDegree;
	}

	/**
	 * @param eduDegree
	 *            The eduDegree to set.
	 */
	public void setEduDegree(EduDegree eduDegree) {
		this.eduDegree = eduDegree;
	}

	/**
	 * @return Returns the teacher.
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher
	 *            The teacher to set.
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return Returns the teacherAge.
	 */
	public Integer getTeacherAge() {
		return teacherAge;
	}

	/**
	 * @param teacherAge
	 *            The teacherAge to set.
	 */
	public void setTeacherAge(Integer teacherAge) {
		this.teacherAge = teacherAge;
	}

	/**
	 * @return Returns the teacherTitle.
	 */
	public TeacherTitle getTeacherTitle() {
		return teacherTitle;
	}

	/**
	 * @param teacherTitle
	 *            The teacherTitle to set.
	 */
	public void setTeacherTitle(TeacherTitle teacherTitle) {
		this.teacherTitle = teacherTitle;
	}

	/**
	 * @return Returns the teacherType.
	 */
	public TeacherType getTeacherType() {
		return teacherType;
	}

	/**
	 * @param teacherType
	 *            The teacherType to set.
	 */
	public void setTeacherType(TeacherType teacherType) {
		this.teacherType = teacherType;
	}

	/**
	 * @return Returns the gender.
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            The gender to set.
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return Returns the teacherName.
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName
	 *            The teacherName to set.
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * @return Returns the teachDepart.
	 */
	public Department getTeachDepart() {
		return teachDepart;
	}

	/**
	 * @param teachDepart
	 *            The teachDepart to set.
	 */
	public void setTeachDepart(Department teachDepart) {
		this.teachDepart = teachDepart;
	}

	/**
	 * @return Returns the titleLevel.
	 */
	public TeacherTitleLevel getTitleLevel() {
		return titleLevel;
	}

	/**
	 * @param titleLevel
	 *            The titleLevel to set.
	 */
	public void setTitleLevel(TeacherTitleLevel titleLevel) {
		this.titleLevel = titleLevel;
	}

	/**
	 * @return Returns the degree.
	 */
	public Degree getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            The degree to set.
	 */
	public void setDegree(Degree degree) {
		this.degree = degree;
	}

}
