//$Id: RationWorkload.java,v 1.1 2006/08/02 00:52:47 duanth Exp $
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
 * chenweixiong              2005-11-15         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.ration;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Department;

/**
 * 额定工作量
 * 
 * @author chenweixiong
 * 
 */
public class RationWorkload extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5160321440642940944L;

	private String rationCn; // 额定公文名

	private String rationEn; // 额定英文名

	private Integer value; // 额定工作量

	private Department department = new Department(); // 创建部门

	private String remark; // 备注

	/**
	 * @return Returns the department.
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            The department to set.
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return Returns the rationCn.
	 */
	public String getRationCn() {
		return rationCn;
	}

	/**
	 * @param rationCn
	 *            The rationCn to set.
	 */
	public void setRationCn(String rationCn) {
		this.rationCn = rationCn;
	}

	/**
	 * @return Returns the rationEn.
	 */
	public String getRationEn() {
		return rationEn;
	}

	/**
	 * @param rationEn
	 *            The rationEn to set.
	 */
	public void setRationEn(String rationEn) {
		this.rationEn = rationEn;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return Returns the value.
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(Integer value) {
		this.value = value;
	}
}
