//$Id: WorkloadByTeacherTypeAndDepart.java,v 1.1 2006/08/02 00:53:07 duanth Exp $
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
 * chenweixiong              2006-2-20         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.stat;

import java.util.ArrayList;
import java.util.List;

import com.shufe.model.system.baseinfo.Department;

public class WorkloadByTeacherTypeAndDepart {

	private Department department = new Department();
	private List teacherTypeValueList = new ArrayList();
	private Float totleWorkloadValue;
	private Float hasPaidWorkloadValue;
	
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
	 * @return Returns the hasPaidWorkloadValue.
	 */
	public Float getHasPaidWorkloadValue() {
		return hasPaidWorkloadValue;
	}
	/**
	 * @param hasPaidWorkloadValue The hasPaidWorkloadValue to set.
	 */
	public void setHasPaidWorkloadValue(Float hasPaidWorkloadValue) {
		this.hasPaidWorkloadValue = hasPaidWorkloadValue;
	}
	/**
	 * @return Returns the teacherTypeValueList.
	 */
	public List getTeacherTypeValueList() {
		return teacherTypeValueList;
	}
	/**
	 * @param teacherTypeValueList The teacherTypeValueList to set.
	 */
	public void setTeacherTypeValueList(List teacherTypeValueList) {
		this.teacherTypeValueList = teacherTypeValueList;
	}
	/**
	 * @return Returns the totleWorkloadValue.
	 */
	public Float getTotleWorkloadValue() {
		return totleWorkloadValue;
	}
	/**
	 * @param totleWorkloadValue The totleWorkloadValue to set.
	 */
	public void setTotleWorkloadValue(Float totleWorkloadValue) {
		this.totleWorkloadValue = totleWorkloadValue;
	}
	
	
}
