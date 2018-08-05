//$Id: WorkloadInfo.java,v 1.2 2006/12/13 10:30:02 cwx Exp $
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
 * chenweixiong              2005-11-23         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload;

public class WorkloadInfo {

	private Float classNumberOfWeek; // 周课时

	private Integer weeks; // 上课周数

	private Integer totleCourses; // 总课时

	private Float totleWorkload; // 总工作量

	private Boolean payReward; // 支付报酬

	private Boolean calcWorkload; // 计工作量

	private String remark; // 备注

	/**
	 * @return Returns the calcWorkload.
	 */
	public Boolean getCalcWorkload() {
		return calcWorkload;
	}

	/**
	 * @param calcWorkload
	 *            The calcWorkload to set.
	 */
	public void setCalcWorkload(Boolean calcWorkload) {
		this.calcWorkload = calcWorkload;
	}

	/**
	 * @return Returns the classNumberOfWeek.
	 */
	public Float getClassNumberOfWeek() {
		return classNumberOfWeek;
	}

	/**
	 * @param classNumberOfWeek
	 *            The classNumberOfWeek to set.
	 */
	public void setClassNumberOfWeek(Float classNumberOfWeek) {
		this.classNumberOfWeek = classNumberOfWeek;
	}

	/**
	 * @return Returns the payReward.
	 */
	public Boolean getPayReward() {
		return payReward;
	}

	/**
	 * @param payReward
	 *            The payReward to set.
	 */
	public void setPayReward(Boolean payReward) {
		this.payReward = payReward;
	}

	/**
	 * @return Returns the totleCourses.
	 */
	public Integer getTotleCourses() {
		return totleCourses;
	}

	/**
	 * @param totleCourses
	 *            The totleCourses to set.
	 */
	public void setTotleCourses(Integer totleCourses) {
		this.totleCourses = totleCourses;
	}

	/**
	 * @return Returns the totleWorkload.
	 */
	public Float getTotleWorkload() {
		return totleWorkload;
	}

	/**
	 * @param totleWorkload
	 *            The totleWorkload to set.
	 */
	public void setTotleWorkload(Float totleWorkload) {
		this.totleWorkload = totleWorkload;
	}

	/**
	 * @return Returns the weeks.
	 */
	public Integer getWeeks() {
		return weeks;
	}

	/**
	 * @param weeks
	 *            The weeks to set.
	 */
	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
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
}
