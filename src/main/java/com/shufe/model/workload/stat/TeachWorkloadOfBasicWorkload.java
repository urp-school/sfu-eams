//$Id: TeachWorkloadOfBasicWorkload.java,v 1.1 2006/08/02 00:53:07 duanth Exp $
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

import com.ekingstar.eams.system.basecode.state.TeacherTitle;

public class TeachWorkloadOfBasicWorkload {
	
	private TeacherTitle teacherTitle = new TeacherTitle();
	private  Float workloadNumber;
	private  Integer peopleNumber;
	/**
	 * @return Returns the peopleNumber.
	 */
	public Integer getPeopleNumber() {
		return peopleNumber;
	}
	/**
	 * @param peopleNumber The peopleNumber to set.
	 */
	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	/**
	 * @return Returns the teacherTitle.
	 */
	public TeacherTitle getTeacherTitle() {
		return teacherTitle;
	}
	/**
	 * @param teacherTitle The teacherTitle to set.
	 */
	public void setTeacherTitle(TeacherTitle teacherTitle) {
		this.teacherTitle = teacherTitle;
	}
	/**
	 * @return Returns the workloadNumber.
	 */
	public Float getWorkloadNumber() {
		return workloadNumber;
	}
	/**
	 * @param workloadNumber The workloadNumber to set.
	 */
	public void setWorkloadNumber(Float workloadNumber) {
		this.workloadNumber = workloadNumber;
	}
	
	

}
