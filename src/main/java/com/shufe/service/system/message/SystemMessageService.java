// $Id: SystemMessageService.java,v 1.7 2006/12/21 12:29:19 duanth Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/***********************************************************************************************************************
 * @author pippo MODIFICATION DESCRIPTION Name Date Description ============ ============ ============ pippo 2005-10-19
 *         Created
 **********************************************************************************************************************/
package com.shufe.service.system.message;

import java.util.List;

import com.ekingstar.security.User;
import com.shufe.dao.system.message.SystemMessageDAO;
import com.shufe.model.system.message.Message;
import com.shufe.model.system.message.SystemMessage;

public interface SystemMessageService {
	/**
	 * 给发送消息
	 * 
	 * @see SystemMessage.toUser,toStd,toTeacher
	 * @param message
	 * @param receiptors
	 * @param toWho
	 */
	public void sendMessage(Message message, List receiptors);

	public SystemMessage genSystemMessage(User user);

	/**
	 * 接受者消息某类消息的数量
	 * 
	 * @param recipient
	 * @param status
	 * @return
	 */
	public int getMessageCount(Long recipientId, Integer status);

	/**
	 * 系统消息存取类
	 * 
	 * @param systemMessageDAO
	 */
	public void setSystemMessageDAO(SystemMessageDAO systemMessageDAO);
}
