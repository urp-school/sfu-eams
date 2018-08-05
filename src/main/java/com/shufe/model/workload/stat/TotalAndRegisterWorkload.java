//$Id: TotalAndRegisterWorkload.java,v 1.2 2006/12/19 13:08:42 duanth Exp $
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
 * chenweixiong              2006-6-13         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.stat;

import com.shufe.model.system.baseinfo.Department;

public class TotalAndRegisterWorkload {
	
	private Department department = new Department(); //部门id
	private Float totalWorkload = new Float(0); //院系教师总工作量.
	private Integer totalPeople = new Integer(0); //院系教师授课总人数.
	private Float totalRegisterWorkload = new Float(0); //院系注册教师总工作量
	private Integer totalRegisterPeople = new Integer(0); //院系注册教师授课总人数.
	
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
	 * @return Returns the totalPeople.
	 */
	public Integer getTotalPeople() {
		return totalPeople;
	}
	/**
	 * @param totalPeople The totalPeople to set.
	 */
	public void setTotalPeople(Integer totalPeople) {
		this.totalPeople = totalPeople;
	}
	/**
	 * @return Returns the totalRegisterPeople.
	 */
	public Integer getTotalRegisterPeople() {
		return totalRegisterPeople;
	}
	/**
	 * @param totalRegisterPeople The totalRegisterPeople to set.
	 */
	public void setTotalRegisterPeople(Integer totalRegisterPeople) {
		this.totalRegisterPeople = totalRegisterPeople;
	}
	/**
	 * @return Returns the totalRegisterWorkload.
	 */
	public Float getTotalRegisterWorkload() {
		return totalRegisterWorkload;
	}
	/**
	 * @param totalRegisterWorkload The totalRegisterWorkload to set.
	 */
	public void setTotalRegisterWorkload(Float totalRegisterWorkload) {
		this.totalRegisterWorkload = totalRegisterWorkload;
	}
	/**
	 * @return Returns the totalWorkload.
	 */
	public Float getTotalWorkload() {
		return totalWorkload;
	}
	/**
	 * @param totalWorkload The totalWorkload to set.
	 */
	public void setTotalWorkload(Float totalWorkload) {
		this.totalWorkload = totalWorkload;
	}
	
	
	

}
