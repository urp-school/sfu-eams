//$Id: Project.java,v 1.1 2006/08/02 00:53:10 duanth Exp $
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
 * hc             2005-11-29         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

import java.util.Date;

import com.ekingstar.eams.system.basecode.industry.ProjectType;

/**
 * 科研成果--项目
 * 
 * @author hc,cwx,chaostone
 * 
 */
public class Project extends StudyProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1531439899017065948L;

	/** 项目编号 */
	private String projectNo;

	/** 立项单位 */
	private String company;

	/** 立项日期 */
	private Date startOn;

	/** 结项日期 */
	private Date endOn;

	/** 承担任务 */
	private String bearTask;
	
	/** 负责人 */
	private String principal;
	
	/** 项目类别 */
	private ProjectType projectType = new ProjectType();

	public void addAward(ProjectAward projectAward) {
		projectAward.setProject(this);
		this.getAwards().add(projectAward);
	}

	/**
	 * @return Returns the bearTask.
	 */
	public String getBearTask() {
		return bearTask;
	}

	/**
	 * @param bearTask
	 *            The bearTask to set.
	 */
	public void setBearTask(String bearTask) {
		this.bearTask = bearTask;
	}

	/**
	 * @return Returns the company.
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return Returns the endTime.
	 */
	public Date getEndOn() {
		return endOn;
	}

	/**
	 * @param endTime
	 *            The endTime to set.
	 */
	public void setEndOn(Date endOn) {
		this.endOn = endOn;
	}

	/**
	 * @return Returns the principal.
	 */
	public String getPrincipal() {
		return principal;
	}

	/**
	 * @param principal
	 *            The principal to set.
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/**
	 * @return Returns the projectNo.
	 */
	public String getProjectNo() {
		return projectNo;
	}

	/**
	 * @param projectNo
	 *            The projectNo to set.
	 */
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	/**
	 * @return Returns the startTime.
	 */
	public Date getStartOn() {
		return startOn;
	}

	/**
	 * @param startTime
	 *            The startTime to set.
	 */
	public void setStartOn(Date startOn) {
		this.startOn = startOn;
	}

	/**
	 * @return Returns the projectType.
	 */
	public ProjectType getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            The projectType to set.
	 */
	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
}
