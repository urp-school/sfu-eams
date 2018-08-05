//$Id: ConfigRationWorkload.java,v 1.1 2006/08/02 00:52:47 duanth Exp $
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
 * chenweixiong              2005-11-16         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.ration;

import java.sql.Date;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;

public class RationWorkloadConfig extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9069429165278581463L;

	private Department department = new Department(); // 院系

	private RationWorkload rationWorkload = new RationWorkload(); // 对应的院系老师的额定工作量

	private TeachCalendar teachCalendar = new TeachCalendar();

	private Date configTime; // 配置时间

	/**
	 * @return Returns the configTime.
	 */
	public Date getConfigTime() {
		return configTime;
	}

	/**
	 * @param configTime
	 *            The configTime to set.
	 */
	public void setConfigTime(Date configTime) {
		this.configTime = configTime;
	}

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
	 * @return Returns the rationWorkload.
	 */
	public RationWorkload getRationWorkload() {
		return rationWorkload;
	}

	/**
	 * @param rationWorkload
	 *            The rationWorkload to set.
	 */
	public void setRationWorkload(RationWorkload rationWorkload) {
		this.rationWorkload = rationWorkload;
	}

	/**
	 * @return Returns the teachCalendar.
	 */
	public TeachCalendar getTeachCalendar() {
		return teachCalendar;
	}

	/**
	 * @param teachCalendar
	 *            The teachCalendar to set.
	 */
	public void setTeachCalendar(TeachCalendar teachCalendar) {
		this.teachCalendar = teachCalendar;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RationWorkloadConfig)) {
			return false;
		}
		RationWorkloadConfig rhs = (RationWorkloadConfig) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.teachCalendar.getId(), rhs.teachCalendar.getId()).append(
				this.rationWorkload.getId(), rhs.rationWorkload.getId())
				.append(this.department.getId(), rhs.department.getId())
				.isEquals();
	}

}
