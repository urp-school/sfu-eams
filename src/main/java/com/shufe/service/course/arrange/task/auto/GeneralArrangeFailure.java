//$Id: GeneralArrangeFailure.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

public class GeneralArrangeFailure extends RuntimeException {

	private static final long serialVersionUID = -8923542589088619505L;

	protected String failureKey;

	public GeneralArrangeFailure() {
		super();
	}

	public GeneralArrangeFailure(String message) {
		super(message);
	}

	public GeneralArrangeFailure(String failureKey, String message) {
		super(message);
		this.failureKey = failureKey;
	}

	/**
	 * @return Returns the failureKey.
	 */
	public String getFailureKey() {
		return failureKey;
	}

	/**
	 * @param failureKey The failureKey to set.
	 */
	public void setFailureKey(String failureKey) {
		this.failureKey = failureKey;
	}

}
