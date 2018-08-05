//$Id: ProjectAward.java,v 1.1 2007-3-6 15:59:51 Administrator Exp $
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
 * chenweixiong              2007-3-6         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;
/***
 * 
 * @author chaostone
 *
 */
public class ProjectAward extends StudyAward {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502366979999442580L;
	private Project project = new Project();

	public StudyProduct getStudyProduct() {
		return project;
	}
	public void setStudyProduct(StudyProduct product) {
		this.project = (Project) product;
	}

	/**
	 * @return Returns the project.
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project The project to set.
	 */
	public void setProject(Project project) {
		this.project = project;
	}

}
