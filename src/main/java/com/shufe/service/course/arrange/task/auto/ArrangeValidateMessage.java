//$Id: ArrangeValidateMessage.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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
 * chaostone             2005-12-20         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 教学任务安排验证消息类
 * 
 * @author chaostone
 * 
 */
public class ArrangeValidateMessage implements Serializable {

	private static final long serialVersionUID = 4135512849632437894L;

	private Serializable id;

	private String info;

	private String key;

	private String message;

	private boolean resourse;

	public ArrangeValidateMessage(Serializable id, String info, String key,
			String message) {
		this.id = id;
		this.info = info;
		this.key = key;
		this.message = message;
		this.resourse = true;
	}

	public ArrangeValidateMessage(Serializable id, String info, String key,
			String message, boolean resourse) {
		this.id = id;
		this.info = info;
		this.key = key;
		this.message = message;
		this.resourse = resourse;
	}

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"id", this.id).append("key", this.key)
				.append("info", this.info).append("message", this.message)
				.toString();
	}

	public boolean isResourse() {
		return resourse;
	}

	public void setResourse(boolean resourse) {
		this.resourse = resourse;
	}

}
