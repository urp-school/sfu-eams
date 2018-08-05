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
 * 塞外狂人             2006-8-8            Created
 *  
 ********************************************************************************/
package com.shufe.model.degree.thesis.topicOpen;

import java.sql.Date;

import com.ekingstar.commons.model.Component;
/**
 * 开题报告情况
 * 
 * @author 塞外狂人
 * 
 */
public class OpenReport implements Component {
	private Date  openOn; // 开题时间
	private Integer participatorCount;// 与会人数
	private String experts;// 与会专家
	private String opinions;// 主要意见	
	private String  address; // 开题地点

	public String getOpinions() {
		return opinions;
	}
	public void setOpinions(String mostlyIdea) {
		this.opinions = mostlyIdea;
	}
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return Returns the experts.
	 */
	public String getExperts() {
		return experts;
	}
	/**
	 * @param experts The experts to set.
	 */
	public void setExperts(String experts) {
		this.experts = experts;
	}
	/**
	 * @return Returns the openOn.
	 */
	public Date getOpenOn() {
		return openOn;
	}
	/**
	 * @param openOn The openOn to set.
	 */
	public void setOpenOn(Date openOn) {
		this.openOn = openOn;
	}
	/**
	 * @return Returns the participatorCount.
	 */
	public Integer getParticipatorCount() {
		return participatorCount;
	}
	/**
	 * @param participatorCount The participatorCount to set.
	 */
	public void setParticipatorCount(Integer participatorCount) {
		this.participatorCount = participatorCount;
	}
	

}

