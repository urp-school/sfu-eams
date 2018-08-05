//$Id: TaskElectInfo.java,v 1.5 2006/12/25 09:43:40 duanth Exp $
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
 * chaostone             2005-10-21         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.task;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ekingstar.commons.model.Component;
import com.ekingstar.eams.system.basecode.industry.HSKDegree;

/**
 * 教学任务中的选课要求信息和选课情况<br>
 * 规定了是否参选、允许退选以及人数和范围的限制。<br>
 * 该任务的退课名单不再该选课对象中，参见TeachClass.withdraws<br>
 * 
 * @author chaostone 2005-10-21
 */
public class TaskElectInfo implements Component, Serializable {

	private static final long serialVersionUID = -2765305354358409624L;

	/**
	 * 是否参与选课
	 */
	private Boolean isElectable;

	/**
	 * 是否可以退课
	 */
	private Boolean isCancelable;

	/**
	 * 选课人数上限
	 */
	private Integer maxStdCount;

	/**
	 * 选课人数下限
	 */
	private Integer minStdCount;

	/**
	 * 汉语水平考试等级
	 */
	private HSKDegree HSKDegree = new HSKDegree();

	/**
	 * 先修课程
	 */
	private Set prerequisteCourses = new HashSet();

	/**
	 * 选课学生范围
	 */
	private Set electScopes = new HashSet();

	/**
	 * 选课学生
	 */
	private Set electResults = new HashSet();

	public TaskElectInfo() {
	}

	/**
	 * 不对选课范围和选课纪录进行复制<br>
	 * 
	 * @see java.lang.Object#clone()
	 * 
	 */
	public Object clone() {
		TaskElectInfo info = new TaskElectInfo();
		info.setIsElectable(getIsElectable());
		info.setMaxStdCount(getMaxStdCount());
		info.setMinStdCount(getMinStdCount());
		info.setIsCancelable(getIsCancelable());
		info.setHSKDegree(getHSKDegree());
		info.getPrerequisteCourses().addAll(getPrerequisteCourses());
		return info;
	}

	public static TaskElectInfo getDefault() {
		TaskElectInfo taskElectInfo = new TaskElectInfo();
		taskElectInfo.minStdCount = new Integer(0);
		taskElectInfo.isElectable = Boolean.FALSE;
		taskElectInfo.maxStdCount = new Integer(0);
		taskElectInfo.isCancelable = Boolean.TRUE;
		return taskElectInfo;
	}

	public HSKDegree getHSKDegree() {
		return HSKDegree;
	}

	public void setHSKDegree(HSKDegree degree) {
		HSKDegree = degree;
	}

	public Set getPrerequisteCourses() {
		return prerequisteCourses;
	}

	public void setPrerequisteCourses(Set prerequisteCourses) {
		this.prerequisteCourses = prerequisteCourses;
	}

	/**
	 * @return Returns the electResults.
	 */
	public Set getElectResults() {
		return electResults;
	}

	/**
	 * @param electResults
	 *            The electResults to set.
	 */
	public void setElectResults(Set electResults) {
		this.electResults = electResults;
	}

	/**
	 * @return Returns the isElectable.
	 */
	public Boolean getIsElectable() {
		return isElectable;
	}

	/**
	 * @param isElectable
	 *            The isElectable to set.
	 */
	public void setIsElectable(Boolean isElectable) {
		this.isElectable = isElectable;
	}

	/**
	 * @return Returns the maxStdCount.
	 */
	public Integer getMaxStdCount() {
		return maxStdCount;
	}

	/**
	 * @param maxStdCount
	 *            The maxStdCount to set.
	 */
	public void setMaxStdCount(Integer maxStdCount) {
		this.maxStdCount = maxStdCount;
	}

	/**
	 * @return Returns the minStdCount.
	 */
	public Integer getMinStdCount() {
		return minStdCount;
	}

	/**
	 * @param minStdCount
	 *            The minStdCount to set.
	 */
	public void setMinStdCount(Integer minStdCount) {
		this.minStdCount = minStdCount;
	}

	/**
	 * @return Returns the electScopses.
	 */
	public Set getElectScopes() {
		return electScopes;
	}

	/**
	 * @param electScopses
	 *            The electScopses to set.
	 */
	public void setElectScopes(Set electScopses) {
		this.electScopes = electScopses;
	}

	public Boolean getIsCancelable() {
		return isCancelable;
	}

	public void setIsCancelable(Boolean isCancelable) {
		this.isCancelable = isCancelable;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("minStdCount", this.minStdCount).append("maxStdCount",
						this.maxStdCount).toString();
	}
}
