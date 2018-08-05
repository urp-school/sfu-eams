//$Id: Message.java,v 1.6 2006/10/12 12:04:25 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-10-19         Created
 *  
 ********************************************************************************/
package com.shufe.model.system.message;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.MessageType;
import com.ekingstar.security.User;

/**
 * 系统消息
 * 
 * @author dell
 */
public class Message extends LongIdObject implements Cloneable {
	static final int invalidateTime = 3600 * 24 * 15 * 1000;// half of month

	private static final long serialVersionUID = -300894932475862133L;

	/** 标题 */
	private String title;

	/** 内容 */
	private String body;

	/** 发送人 */
	private User sender;

	/** 消息类别 */
	private MessageType type = new MessageType();

	/** 消息创建日期 */
	private Date createAt;

	/** 激活日期 */
	private Date activeOn;

	/** 失效日期 */
	private Date invalidateOn;

	/** 接受用户 */
	private Set receiptors = new HashSet();

	public Message() {
	}

	public static Message getDefaultMessage(User sender) {
		Message msg = new Message();
		msg.getType().setId(MessageType.COMMON);
		msg.setSender(sender);
		msg.setActiveOn(new Date(System.currentTimeMillis()));
		msg.setCreateAt(new Date(System.currentTimeMillis()));
		msg.setInvalidateOn(new Date(System.currentTimeMillis()
				+ invalidateTime));
		return msg;
	}

	/**
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            The body to set.
	 */
	public void setBody(String messageBody) {
		this.body = messageBody;
	}

	/**
	 * @return Returns the type.
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(MessageType messageType) {
		this.type = messageType;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            The title to set.
	 */
	public void setTitle(String messageTitle) {
		this.title = messageTitle;
	}

	public Date getActiveOn() {
		return activeOn;
	}

	public void setActiveOn(Date activeOn) {
		this.activeOn = activeOn;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getInvalidateOn() {
		return invalidateOn;
	}

	public void setInvalidateOn(Date invalidateOn) {
		this.invalidateOn = invalidateOn;
	}

	public Set getReceiptors() {
		return receiptors;
	}

	public void setReceiptors(Set receiptors) {
		this.receiptors = receiptors;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object colen() throws CloneNotSupportedException {
		return super.clone();
	}
}
