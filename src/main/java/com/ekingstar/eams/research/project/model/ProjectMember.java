package com.ekingstar.eams.research.project.model;

import java.sql.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;

public class ProjectMember extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4843638112873236107L;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private Gender gender;
	/**
	 * 出生年月
	 */
	private Date birthday;
	/**
	 * 建设项目
	 */
	private TeachProject teachProject;
	
	/** 职称 */
	private TeacherTitle teacherTitle = new TeacherTitle();
	
	/** 个人简介 */
	private String resume;
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public TeacherTitle getTeacherTitle() {
		return teacherTitle;
	}
	public void setTeacherTitle(TeacherTitle teacherTitle) {
		this.teacherTitle = teacherTitle;
	}
	public boolean equals(Object object) {
		if (!(object instanceof ProjectMember)) {
			return false;
		}
		ProjectMember rhs = (ProjectMember) object;
		return new EqualsBuilder().append(this.id, rhs.getId()).isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(-1686949871, 1096576027).append(this.id).toHashCode();
	}
	public TeachProject getTeachProject() {
		return teachProject;
	}
	public void setTeachProject(TeachProject teachProject) {
		this.teachProject = teachProject;
	}

}
