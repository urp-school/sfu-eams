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
 * chaostone             2006-11-21            Created
 *  
 ********************************************************************************/
package com.shufe.model.system.project;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 需求
 * 
 * @author chaostone
 * 
 */
public class Requirement extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5072515287492886520L;

	private String content;

	private String module;

	private String fromUser;

	private String stackHolders;

	private String developers;

	private Integer priority;

	private String suggestSolution;

	private Date planCompleteOn;

	private Date actualCompleteOn;

	private Date createdOn;

	private Date lastModifiedOn;

	private Integer type;

	private String background;

	private Integer status;

	private Float workload;

	private String remark;

	public Date getActualCompleteOn() {
		return actualCompleteOn;
	}

	public void setActualCompleteOn(Date actualCompleteOn) {
		this.actualCompleteOn = actualCompleteOn;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Date getPlanCompleteOn() {
		return planCompleteOn;
	}

	public void setPlanCompleteOn(Date planCompleteOn) {
		this.planCompleteOn = planCompleteOn;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStackHolders() {
		return stackHolders;
	}

	public void setStackHolders(String stackHolders) {
		this.stackHolders = stackHolders;
	}

	public String getSuggestSolution() {
		return suggestSolution;
	}

	public void setSuggestSolution(String suggestScheme) {
		this.suggestSolution = suggestScheme;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date updatedOn) {
		this.lastModifiedOn = updatedOn;
	}

	public Float getWorkload() {
		return workload;
	}

	public void setWorkload(Float workload) {
		this.workload = workload;
	}

}
