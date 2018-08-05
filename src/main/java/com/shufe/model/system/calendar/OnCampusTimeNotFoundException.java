//$Id: OnCampusTimeNotFoundException.java,v 1.1 2006/08/02 00:52:53 duanth Exp $
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
 * chaostone             2005-11-1         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.calendar;

public class OnCampusTimeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5873885065160445852L;

	public OnCampusTimeNotFoundException(String msg) {
		super(msg);
	}
}
