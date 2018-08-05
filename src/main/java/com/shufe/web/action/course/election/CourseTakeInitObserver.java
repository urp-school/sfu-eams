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
 * chaostone             2006-1-9            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.election;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import org.apache.struts.util.MessageResources;

import com.shufe.service.course.election.CourseTakeInitMessage;
import com.shufe.web.OutputWebObserver;

public class CourseTakeInitObserver extends OutputWebObserver {
	public CourseTakeInitObserver() {
		super();
	}

	public CourseTakeInitObserver(PrintWriter writer,
			MessageResources resourses, Locale locale) {
		super(writer, resourses, locale);
	}

	public void notifyStart() throws IOException {
		writer.println("<html><body>");
		writer.println(messageOf("info.courseTakeInit.start"));
		writer.flush();
	}

	public void notifyFinish() throws IOException {
		writer.println(messageOf("info.courseTakeInit.finish"));
		writer.println("</body></html>");
		writer.flush();
	}

	public String message(Object msgObj) {
		CourseTakeInitMessage message = (CourseTakeInitMessage) msgObj;
		return messageOf(message.getKey())
				+ message.getMessage(resourses, locale) + "<br>";
	}
}
