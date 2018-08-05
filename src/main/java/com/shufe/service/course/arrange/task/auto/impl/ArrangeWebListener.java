//$Id: ArrangeWebListener.java,v 1.4 2006/12/06 07:03:29 duanth Exp $
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
 * chaostone             2005-11-16         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto.impl;

import java.io.PrintWriter;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shufe.service.course.arrange.task.CourseActivityDigestor;
import com.shufe.service.course.arrange.task.auto.Arrange;
import com.shufe.service.course.arrange.task.auto.ArrangeListener;
import com.shufe.service.course.arrange.task.auto.GeneralArrangeFailure;

public class ArrangeWebListener implements ArrangeListener {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected PrintWriter writer;

	protected MessageResources resourses;

	protected Locale locale;

	public ArrangeWebListener(PrintWriter writer) {
		this.writer = writer;
		try {
			writer.flush();
		} catch (Exception e) {
		}
	}

	public void startArrange(Arrange arrange) {
		try {
			StringBuffer buf = new StringBuffer(50);
			buf.append("<br>").append(messageOf("info.arrange.start")).append(
					"[");
			buf.append(arrange.getName()).append("]&nbsp;");
			writer.println(buf.toString());
			writer.flush();
		} catch (Exception e) {
			logger.debug("[ArrangeWebListener]system error occured");
		}
	}

	public void notify(String msg) {
		writer.print("<br>");
		writer.println(msg);
		writer.flush();
	}

	public void endArrange(Arrange arrange) {
		try {
			StringBuffer buf = new StringBuffer(25);
			if (null != arrange.getCurTask()) {
				buf.append(CourseActivityDigestor.digest(arrange.getCurTask(),
						resourses, locale));
			}
			writer.println(buf.toString());
			writer.flush();
		} catch (Exception e) {
			logger.debug("[ArrangeWebListener]system error occured");
		}
	}

	public void addFailure(Arrange arrange, GeneralArrangeFailure g) {
		try {
			StringBuffer buf = new StringBuffer(50);
			buf.append(messageOf("common.failure")).append("<br>").append(
					messageOf("common.cause")).append(":").append(
					messageOf(g.getFailureKey()));
			writer.println(buf.toString());
			writer.flush();
		} catch (Exception e) {
			logger.debug("[ArrangeWebListener]system error occured");
		}
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.ArrangeListener#notifyEnd()
	 */
	public void notifyEnd() {
		writer.println("<br>" + messageOf("info.arrange.completed"));
		writer.write("</body></html>");
		writer.flush();
		writer.close();
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.ArrangeListener#notifyStart()
	 */
	public void notifyStart() {
		writer
				.write("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"></head>");
		writer
				.write("<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"scripts/common/Common.js\" ></script>");
		writer
				.write("<BODY LEFTMARGIN='0' TOPMARGIN='0' style='font-size:12px' onload='f_frameStyleResize(self)'>");

		writer.println(messageOf("info.arrange.validate"));
		writer.println(messageOf("common.success"));
		writer.flush();

	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageResources getResourses() {
		return resourses;
	}

	public void setResourses(MessageResources resourses) {
		this.resourses = resourses;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public String messageOf(String key) {
		if (StringUtils.isNotEmpty(key))
			return resourses.getMessage(locale, key);
		else
			return "";
	}
}
