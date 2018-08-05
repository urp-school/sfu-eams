//$Id: StudyMeeting.java,v 1.1 2007-4-3 17:00:07 Administrator Exp $
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
 * chenweixiong              2007-4-3         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

import java.util.Date;

import com.ekingstar.eams.system.basecode.industry.MeetingType;

/**
 * 参与的学术会议
 * 
 * @author chaostone
 * 
 */
public class StudyMeeting extends StudyProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6213420133272033037L;

	/** 会议类别 */
	private MeetingType meetingType = new MeetingType();

	/** 获邀论文题目 */
	private String topicName;

	/** 会议时间 */
	private Date meetingOn;

	/** 会议地点 */
	private String meetingAddress;

	/** 举办单位 */
	private String openDepart;

	/**
	 * @return Returns the meetingAddress.
	 */
	public String getMeetingAddress() {
		return meetingAddress;
	}

	/**
	 * @param meetingAddress
	 *            The meetingAddress to set.
	 */
	public void setMeetingAddress(String meetingAddress) {
		this.meetingAddress = meetingAddress;
	}

	/**
	 * @return Returns the meetingTime.
	 */
	public Date getMeetingOn() {
		return meetingOn;
	}

	/**
	 * @param meetingOn
	 *            The meetingOn to set.
	 */
	public void setMeetingOn(Date meetingOn) {
		this.meetingOn = meetingOn;
	}

	/**
	 * @return Returns the meetingType.
	 */
	public MeetingType getMeetingType() {
		return meetingType;
	}

	/**
	 * @param meetingType
	 *            The meetingType to set.
	 */
	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	/**
	 * @return Returns the openDepart.
	 */
	public String getOpenDepart() {
		return openDepart;
	}

	/**
	 * @param openDepart
	 *            The openDepart to set.
	 */
	public void setOpenDepart(String openDepart) {
		this.openDepart = openDepart;
	}

	/**
	 * @return Returns the topicName.
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * @param topicName
	 *            The topicName to set.
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

}
