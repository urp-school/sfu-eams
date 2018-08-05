//$Id: TeacherEduDegree.java,v 1.1 2006/08/02 00:53:07 duanth Exp $
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
 * chenweixiong              2005-12-30         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.stat;

import java.util.ArrayList;
import java.util.List;

import com.shufe.model.system.baseinfo.Department;

public class TeacherEduDegree {
	private Department department = new Department(); 	 //部门
	private List valueList = new ArrayList();			//值集合
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
	 * @return Returns the valueList.
	 */
	public List getValueList() {
		return valueList;
	}
	/**
	 * @param valueList The valueList to set.
	 */
	public void setValueList(List valueList) {
		this.valueList = valueList;
	}
	
	
	
	
	
}
