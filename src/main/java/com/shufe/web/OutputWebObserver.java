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
 * chaostone             2006-1-6            Created
 *  
 ********************************************************************************/
package com.shufe.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import com.shufe.service.OutputObserver;

/**
 * 回显到web的输出观察者
 * 
 * @author chaostone
 * 
 */
public class OutputWebObserver implements OutputObserver {

	protected PrintWriter writer;

	protected MessageResources resourses;

	protected Locale locale;

	public OutputWebObserver() {
	}

	public OutputWebObserver(PrintWriter writer, MessageResources resourses,
			Locale locale) {
		this.writer = writer;
		this.resourses = resourses;
		this.locale = locale;
	}

	public void outputNotify(int level, OutputMessage msgObj)
			throws IOException {

		switch (level) {
		case 1:
			writer.print(message(msgObj));
			writer.flush();
			break;
		case 2:
			writer.print("<font color=blue>");
			writer.print(message(msgObj));
			writer.print("</font>");
			writer.flush();
			break;
		case 3:
			writer.print("<font color=red>");
			writer.print(message(msgObj));
			writer.print("</font>");
			writer.flush();
			break;
		}
	}

	public void outputNotify(OutputMessage msgObj) throws IOException {
		outputNotify(OutputObserver.good, msgObj);

	}

	public void notifyFinish() throws IOException {
		writer.println("finish");
	}

	public void notifyStart() throws IOException {
		writer.println("start");
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public MessageResources getResourses() {
		return resourses;
	}

	public void setResourses(MessageResources resourses) {
		this.resourses = resourses;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String messageOf(String key) {
		if (StringUtils.isNotEmpty(key))
			return resourses.getMessage(locale, key);
		else
			return "";
	}

	public String messageOf(String key, Object arg0) {
		if (StringUtils.isNotEmpty(key))
			return resourses.getMessage(locale, key, arg0);
		else
			return "";
	}

	public String message(OutputMessage msgObj) {
		return msgObj.getMessage(resourses, locale);
	}
}
