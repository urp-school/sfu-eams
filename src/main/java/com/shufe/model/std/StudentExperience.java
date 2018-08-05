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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.model.std;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 学历和社会经历（从中学起）
 */

public class StudentExperience extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1304064902998152276L;

	/* 学生学籍 */
	private StudentStatusInfo studentStatusInfo;

	/* 经历描述 */
	private String experienceDescription;

	/**
	 * @return 返回 experienceDescription.
	 */
	public String getExperienceDescription() {
		return experienceDescription;
	}

	/**
	 * @param experienceDescription
	 *            要设置的 experienceDescription.
	 */
	public void setExperienceDescription(String experienceDescription) {
		this.experienceDescription = experienceDescription;
	}

	public StudentStatusInfo getStudentStatusInfo() {
		return studentStatusInfo;
	}

	public void setStudentStatusInfo(StudentStatusInfo studentStatusInfo) {
		this.studentStatusInfo = studentStatusInfo;
	}

}
