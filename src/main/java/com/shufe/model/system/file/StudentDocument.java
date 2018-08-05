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
 * chaostone             2006-9-29            Created
 *  
 ********************************************************************************/
package com.shufe.model.system.file;

import java.util.HashSet;
import java.util.Set;

public class StudentDocument extends Document {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6623157602805336281L;

	private Set studentTypes = new HashSet();

	private Set departs = new HashSet();

	public Set getDeparts() {
		return departs;
	}

	public void setDeparts(Set departs) {
		this.departs = departs;
	}

	public Set getStudentTypes() {
		return studentTypes;
	}

	public void setStudentTypes(Set studentTypes) {
		this.studentTypes = studentTypes;
	}
	
}
