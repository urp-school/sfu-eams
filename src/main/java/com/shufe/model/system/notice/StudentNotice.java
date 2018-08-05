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
 * chaostone             2006-9-10            Created
 *  
 ********************************************************************************/
package com.shufe.model.system.notice;

import java.util.HashSet;
import java.util.Set;

/**
 * 面向学生的公告
 * 
 * @author chaostone
 * 
 */
public class StudentNotice extends Notice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1299954158603829565L;

	/** 面向的学生类别 */
	private Set studentTypes = new HashSet();

	/** 面向的部门 */
	private Set departs = new HashSet();

	/**
	 * @return Returns the departs.
	 */
	public Set getDeparts() {
		return departs;
	}

	/**
	 * @param departs
	 *            The departs to set.
	 */
	public void setDeparts(Set departs) {
		this.departs = departs;
	}

	/**
	 * @return Returns the studentTypes.
	 */
	public Set getStudentTypes() {
		return studentTypes;
	}

	/**
	 * @param studentTypes
	 *            The studentTypes to set.
	 */
	public void setStudentTypes(Set studentTypes) {
		this.studentTypes = studentTypes;
	}

}
