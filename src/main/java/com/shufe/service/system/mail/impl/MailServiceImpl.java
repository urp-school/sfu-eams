// $Id: MailServiceImpl.java,v 1.2 2007/01/23 01:13:33 duanth Exp $
package com.shufe.service.system.mail.impl;

import java.io.ByteArrayInputStream;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.shufe.service.system.mail.MailService;

public class MailServiceImpl implements MailService {

	private JavaMailSender mailSender;

	private SimpleMailMessage templateMessage;

	/**
	 * @see com.shufe.service.system.mail.MailService#sendSimpleMail(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public boolean sendSimpleMail(String title, String body, String to)
			throws MailSendException {
		boolean flag = true;
		SimpleMailMessage message = new SimpleMailMessage(templateMessage);
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setSubject(title);
		message.setText(body);
		message.setTo(to);
		try {
			/* mailSender.send(message); */
			mailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * @see com.shufe.service.system.mail.MailService#sendSimpleMail(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void sendMimeMail(String title, String body, String to)
			throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setSubject(title);
		message.setText(body);
		message.setFrom(new InternetAddress(templateMessage.getFrom()));
		message.setRecipients(RecipientType.TO, InternetAddress.parse(to));

		message.setDataHandler(new javax.activation.DataHandler(
				new StringDataSource(body, "text/html")));
		mailSender.send(message);
	}

	/**
	 * @param mailSender
	 *            The mailSender to set.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * @param templateMessage
	 *            The templateMessage to set.
	 */
	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

}

class StringDataSource implements javax.activation.DataSource {
	private java.lang.String data;

	private java.lang.String type;

	public StringDataSource(String data, String type) {
		this.data = data;
		this.type = type;
	}

	public java.io.InputStream getInputStream() throws java.io.IOException {
		return new ByteArrayInputStream(data.getBytes());
	}

	public java.io.OutputStream getOutputStream() throws java.io.IOException {
		throw new java.io.IOException("it does not support this method now!");
	}

	public java.lang.String getContentType() {
		return type;
	}

	public java.lang.String getName() {
		return " mymail ";
	}
}
