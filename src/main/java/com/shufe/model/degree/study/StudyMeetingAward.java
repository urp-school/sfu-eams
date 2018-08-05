//$Id: StudyMeetingAward.java,v 1.1 2007-4-4 11:51:16 Administrator Exp $
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
 * chenweixiong              2007-4-4         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.study;

public class StudyMeetingAward extends StudyAward {

	/**
	 * 
	 */
	private static final long serialVersionUID = 496381041130082558L;
	private StudyMeeting studyMeeting = new StudyMeeting();

	public StudyProduct getStudyProduct() {
		return studyMeeting;
	}
	public void setStudyProduct(StudyProduct product) {
		this.studyMeeting = (StudyMeeting) product;
	}
	/**
	 * @return Returns the studyMeeting.
	 */
	public StudyMeeting getStudyMeeting() {
		return studyMeeting;
	}

	/**
	 * @param studyMeeting The studyMeeting to set.
	 */
	public void setStudyMeeting(StudyMeeting studyMeeting) {
		this.studyMeeting = studyMeeting;
	}
	
	
	
}
