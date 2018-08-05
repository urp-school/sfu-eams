//$Id: AdminClass.java,v 1.2 2006/12/19 10:07:07 duanth Exp $
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
 * chaostone             2005-9-12         Created
 *  
 ********************************************************************************/
package com.shufe.model.system.baseinfo;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.baseinfo.model.BaseInfo;

/**
 * 学生行政班级信息
 * 
 * @author chaostone 2005-9-12
 */
public class AdminClass extends BaseInfo implements Serializable {
	private static final long serialVersionUID = 6467000522483394459L;

	/** 所在年级,形式为yyyy-p */
	private String enrollYear;

	/** 学生类别 */
	private StudentType stdType;

	/** 院系 */
	private Department department = new Department();

	/** 专业 */
	private Speciality speciality = new Speciality();

	/** 专业方向 */
	private SpecialityAspect aspect = new SpecialityAspect();

	/** 建班年月 */
	private Date dateEstablished;

	/** 学制 */
	private Integer eduLength;

	/** 计划人数 */
	private Integer planStdCount;

	/** 实际在校人数 */
	private Integer actualStdCount;

	/** 学籍有效的学生人数 */
	private Integer stdCount;

	/** 辅导员 */
	private Teacher instructor;

	/** 学生列表 */
	private Set students = new HashSet();

	public AdminClass() {
	}

	public AdminClass(Long id) {
		super(id);
	}

	/**
	 * @return Returns the stdType.
	 */
	public StudentType getStdType() {
		return stdType;
	}

	/**
	 * @param stdType
	 *            The stdType to set.
	 */
	public void setStdType(StudentType stdType) {
		this.stdType = stdType;
	}

	/**
	 * @hibernate.property column="XZ"
	 * @return
	 */
	public Integer getEduLength() {
		return eduLength;
	}

	public void setEduLength(Integer eduLength) {
		this.eduLength = eduLength;
	}

	/**
	 * @hibernate.property column="RXNY"
	 * @return
	 */
	public String getEnrollYear() {
		return enrollYear;
	}

	public void setEnrollYear(String enrollYear) {
		this.enrollYear = enrollYear;
	}


	public Teacher getInstructor() {
		return instructor;
	}

	public void setInstructor(Teacher instructor) {
		this.instructor = instructor;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return Returns the actualStdCount.
	 */
	public Integer getActualStdCount() {
		return actualStdCount;
	}

	/**
	 * @param actualStdCount
	 *            The actualStdCount to set.
	 */
	public void setActualStdCount(Integer actualStdCount) {
		this.actualStdCount = actualStdCount;
	}

	/**
	 * @return Returns the planStdCount.
	 */
	public Integer getPlanStdCount() {
		return planStdCount;
	}

	/**
	 * @param planStdCount
	 *            The planStdCount to set.
	 */
	public void setPlanStdCount(Integer planStdCount) {
		this.planStdCount = planStdCount;
	}

	/**
	 * @hibernate.property column="JBNY"
	 * @return
	 */
	public Date getDateEstablished() {
		return dateEstablished;
	}

	public void setDateEstablished(Date setUpDate) {
		this.dateEstablished = setUpDate;
	}

	/**
	 * @hibernate.many-to-one column="ZYDM" not-null="false"
	 * @return
	 */
	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	/**
	 * @hibernate.many-to-one column="ZYFXDM" not-null="false"
	 * @return
	 */
	public SpecialityAspect getAspect() {
		return aspect;
	}

	public void setAspect(SpecialityAspect specialityAspect) {
		this.aspect = specialityAspect;
	}

	/**
	 * @return Returns the stdCount.
	 */
	public Integer getStdCount() {
		return stdCount;
	}

	/**
	 * @param stdCount
	 *            The stdCount to set.
	 */
	public void setStdCount(Integer stdCount) {
		this.stdCount = stdCount;
	}

	/**
	 * @return 返回 students.
	 */
	public Set getStudents() {
		return students;
	}

	/**
	 * @param students
	 *            要设置的 students.
	 */
	public void setStudents(Set students) {
		this.students = students;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AdminClass)) {
			return false;
		}
		AdminClass rhs = (AdminClass) object;
		return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(547046601, -323178581).append(this.getId())
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.getId()).append(
				"name", this.getName()).toString();
	}

	/**
	 * 查询班级内的计划内空余人数
	 * 
	 * @return planStdCount -actualStdCount
	 */
	public int freeCapacity() {
		if (null != getPlanStdCount() && null != getActualStdCount()) {
			return getPlanStdCount().intValue()
					- getActualStdCount().intValue();
		} else {
			return 0;
		}
	}
}