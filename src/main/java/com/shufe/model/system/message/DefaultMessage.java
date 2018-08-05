//$Id: DefaultMessage.java,v 1.1 2006/08/25 07:40:53 duanth Exp $
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

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;

/**
 * 常规消息
 * 
 * @author dell,chaostone
 */
public class DefaultMessage extends LongIdObject{

	private static final long serialVersionUID = -5173001178708876151L;

	private User creator;

	private Message message = new Message();

	protected Date createAt = new Date(System.currentTimeMillis());

	private String remark;

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

}
