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
 * chaostone             2006-2-5            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.election;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import com.shufe.model.course.task.TeachTask;
import com.shufe.web.OutputMessage;

public class CourseTakeInitMessage extends OutputMessage {
	TeachTask task;

	public CourseTakeInitMessage(String key, TeachTask task) {
		this.key = key;
		this.task = task;
	}

	public CourseTakeInitMessage(String key, TeachTask task, String message) {
		this.key = key;
		this.task = task;
		this.message = message;
	}

	public String getMessage(MessageResources resourses, Locale locale) {
		StringBuffer msgBuf = new StringBuffer(50);
		msgBuf.append(resourses.getMessage(locale, key));
		msgBuf.append("[").append(resourses.getMessage(locale, "attr.taskNo"))
				.append(":").append(task.getSeqNo()).append("]").append(
						task.getTeachClass().getName()).append(":");
		if (StringUtils.isNotEmpty(getMessage()))
			msgBuf.append(getMessage());
		return msgBuf.toString();
	}

}
