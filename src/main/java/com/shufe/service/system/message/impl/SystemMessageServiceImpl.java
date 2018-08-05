// $Id: SystemMessageServiceImpl.java,v 1.9 2006/12/21 12:29:19 duanth Exp $
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
package com.shufe.service.system.message.impl;

import java.util.Iterator;
import java.util.List;

import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.User;
import com.shufe.dao.system.message.SystemMessageDAO;
import com.shufe.model.system.message.Message;
import com.shufe.model.system.message.StudentMessage;
import com.shufe.model.system.message.SystemMessage;
import com.shufe.model.system.message.TeacherMessage;
import com.shufe.service.BasicService;
import com.shufe.service.system.message.SystemMessageService;

/**
 * @author chaostone
 */
public class SystemMessageServiceImpl extends BasicService implements
		SystemMessageService {

	private SystemMessageDAO systemMessageDAO;

	/**
	 * 
	 */
	public void sendMessage(Message message, List receiptors) {
		utilService.saveOrUpdate(message);
		for (Iterator iter = receiptors.iterator(); iter.hasNext();) {
			User user = (User) iter.next();
			SystemMessage systemMessage = genSystemMessage(user);
			systemMessage.setRecipient(user);
			systemMessage.setStatus(SystemMessage.newly);
			systemMessage.setMessage(message);
			utilService.saveOrUpdate(systemMessage);
		}
	}

	public SystemMessage genSystemMessage(User user) {
		SystemMessage systemMessage = null;
		Class messageClass = null;
		if (user.isCategory(EamsRole.STD_USER)) {
			messageClass = StudentMessage.class;
		} else if (user.isCategory(EamsRole.TEACHER_USER)) {
			messageClass = TeacherMessage.class;
		} else {
			messageClass = SystemMessage.class;
		}
		try {
			systemMessage = (SystemMessage) messageClass.newInstance();
			return systemMessage;
		} catch (Exception e) {
			return new SystemMessage();
		}
	}

	public int getMessageCount(Long recipientId, Integer status) {
		return systemMessageDAO.getMessageCount(recipientId, status);
	}

	public void setSystemMessageDAO(SystemMessageDAO systemMessageDAO) {
		this.systemMessageDAO = systemMessageDAO;
	}
}
