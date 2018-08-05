//$Id: DepartmentRegisterTeacher.java,v 1.1 2006/08/02 00:53:08 duanth Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-2-17         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.stat;

import com.shufe.model.system.baseinfo.Department;

public class DepartmentRegisterTeacher {

	private Department department = new Department();
	private Integer actualTeachTeacher = new Integer(0);
	private Integer registerTeacherNumber=new Integer(0);
	private Integer allTeacherOfDepartment=new Integer(0);
	
	
	/**
	 * @return Returns the actualTeachTeacher.
	 */
	public Integer getActualTeachTeacher() {
		return actualTeachTeacher;
	}
	/**
	 * @param actualTeachTeacher The actualTeachTeacher to set.
	 */
	public void setActualTeachTeacher(Integer actualTeachTeacher) {
		this.actualTeachTeacher = actualTeachTeacher;
	}
	/**
	 * @return Returns the allTeacherOfDepartment.
	 */
	public Integer getAllTeacherOfDepartment() {
		return allTeacherOfDepartment;
	}
	/**
	 * @param allTeacherOfDepartment The allTeacherOfDepartment to set.
	 */
	public void setAllTeacherOfDepartment(Integer allTeacherOfDepartment) {
		this.allTeacherOfDepartment = allTeacherOfDepartment;
	}
	/**
	 * @return Returns the department.
	 */
	public Department getDepartment() {
		return department;
	}
	/**
	 * @param department The department to set.
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}
	/**
	 * @return Returns the registerTeacherNumber.
	 */
	public Integer getRegisterTeacherNumber() {
		return registerTeacherNumber;
	}
	/**
	 * @param registerTeacherNumber The registerTeacherNumber to set.
	 */
	public void setRegisterTeacherNumber(Integer registerTeacherNumber) {
		this.registerTeacherNumber = registerTeacherNumber;
	}
	
	
	
}
