// $Id: MailService.java,v 1.1 2006/09/28 14:59:00 duanth Exp $
package com.shufe.service.system.mail;

import javax.mail.MessagingException;

public interface MailService {

	/**
	 * 发送邮件
	 * 
	 * @param title
	 * @param body
	 * @param to
	 * @return 是否发送成功
	 */
	public boolean sendSimpleMail(String title, String body, String to);

	public void sendMimeMail(String title, String body, String to)
			throws MessagingException;
}
