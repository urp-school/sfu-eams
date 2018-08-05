//$Id: ThesisStore.java,v 1.1 2007-4-13 10:45:24 Administrator Exp $
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
 * chenweixiong              2007-4-13         Created
 *  
 ********************************************************************************/

package com.shufe.model.degree.thesis;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 这个类主要用来记录各个论文类下面的下载时所用到的信息。
 * 
 * @author Administrator
 * 
 */
public class ThesisStore extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1801763606627586336L;
	protected String downloadName;
	protected String displayName;
	protected Date finishOn;

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDownloadName() {
		return downloadName;
	}

	public void setDownloadName(String downloadName) {
		this.downloadName = downloadName;
	}

	public Date getFinishOn() {
		return finishOn;
	}

	public void setFinishOn(Date finishOn) {
		this.finishOn = finishOn;
	}
}
