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
 * chaostone             2006-8-25            Created
 *  
 ********************************************************************************/
package com.shufe.model.system.message;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;

public class SystemMessage extends LongIdObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -692377019456209711L;

	public static final Integer newly = new Integer(1);

	public static final Integer readed = new Integer(2);

	public static final Integer inTrash = new Integer(3);

	public static final int toStd = 2;

	public static final int toTeacher = 3;

	public static final int toUser = 1;

	/** 消息主题 */
	private Message message;

	/** 接受者 */
	private User recipient;

	/** 消息状态 */
	private Integer status;

	public Message getMessage() {
		return message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

}
