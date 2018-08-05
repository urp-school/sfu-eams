//$Id: DepartmentByRate.java,v 1.1 2006/08/02 00:53:07 duanth Exp $
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
 * chenweixiong              2006-2-16         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.stat;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.shufe.model.system.baseinfo.Department;

public class DepartmentByRate {
	private Department department = new Department();
	private Integer registerPeople=new Integer(0); //注册的教师
	private Integer teachPeople = new Integer(0); //教课的所有的教师
	private Float registerWorkload =new Float(0); // 注册教师的工作量
	private Float teachWorkload =new Float(0); //教课的教师的工作量
	

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
	 * @return Returns the registerPeople.
	 */
	public Integer getRegisterPeople() {
		return registerPeople;
	}
	/**
	 * @param registerPeople The registerPeople to set.
	 */
	public void setRegisterPeople(Integer registerPeople) {
		this.registerPeople = registerPeople;
	}
	/**
	 * @return Returns the registerWorkload.
	 */
	public Float getRegisterWorkload() {
		return registerWorkload;
	}
	/**
	 * @param registerWorkload The registerWorkload to set.
	 */
	public void setRegisterWorkload(Float registerWorkload) {
		this.registerWorkload = registerWorkload;
	}
	/**
	 * @return Returns the teachPeople.
	 */
	public Integer getTeachPeople() {
		return teachPeople;
	}
	/**
	 * @param teachPeople The teachPeople to set.
	 */
	public void setTeachPeople(Integer teachPeople) {
		this.teachPeople = teachPeople;
	}
	/**
	 * @return Returns the teachWorkload.
	 */
	public Float getTeachWorkload() {
		return teachWorkload;
	}
	/**
	 * @param teachWorkload The teachWorkload to set.
	 */
	public void setTeachWorkload(Float teachWorkload) {
		this.teachWorkload = teachWorkload;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DepartmentByRate)) {
			return false;
		}
		DepartmentByRate rhs = (DepartmentByRate) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(this.department.getId(), rhs.department.getId())
				.isEquals();
	}
	
	

}
