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
 * chaostone             2006-1-11            Created
 *  
 ********************************************************************************/
package com.shufe.web;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

/**
 * 输出消息
 * 
 * @author chaostone
 * 
 */
public class OutputMessage {
	protected String key;

	protected String message;

	public OutputMessage() {
	}

	public OutputMessage(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public OutputMessage(String key, String message, String engMessage) {
		this.key = key;
		this.message = message;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * to be overrided by subclass
	 * 
	 * @param resourses
	 * @param locale
	 * @return
	 */
	public String getMessage(MessageResources resourses, Locale locale) {
		if (StringUtils.isNotEmpty(key))
			return resourses.getMessage(locale, key);
		else
			return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
