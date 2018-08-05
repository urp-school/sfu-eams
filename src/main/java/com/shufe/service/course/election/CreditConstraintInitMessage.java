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
 * chaostone             2006-1-7            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.election;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import com.shufe.model.course.task.TeachCommon;
import com.shufe.web.OutputMessage;

/**
 * 选课初始化消息
 * 
 * @author chaostone
 * 
 */
public class CreditConstraintInitMessage extends OutputMessage {
	TeachCommon common;

	Float credit;

	public CreditConstraintInitMessage(String key, TeachCommon common, Float credit) {
		this.key = key;
		this.common = common;
		this.credit = credit;
	}

	public String getMessage(MessageResources resourses, Locale locale) {
		StringBuffer msgBuf = new StringBuffer(50);
		msgBuf.append(resourses.getMessage(locale, key));
		if (null != credit)
			msgBuf.append(" ").append(credit).append(" ");
		msgBuf.append("[").append(common.getEnrollTurn());
		if (locale.getLanguage().indexOf("en") != -1) {
			if (StringUtils.isEmpty(common.getStdType().getEngName()))
				msgBuf.append(common.getStdType().getName());
			else
				msgBuf.append(common.getStdType().getEngName());
			msgBuf.append("    ");
			if (StringUtils.isEmpty(common.getDepart().getEngName()))
				msgBuf.append(common.getDepart().getName());
			else
				msgBuf.append(common.getDepart().getEngName());
			msgBuf.append("    ");
			if (null != common.getSpeciality()) {
				if (StringUtils.isEmpty(common.getSpeciality().getEngName()))

					msgBuf.append(common.getSpeciality().getName());
				else
					msgBuf.append(common.getSpeciality().getEngName());
			}
			msgBuf.append("    ");
			if (null != common.getAspect()) {
				if (StringUtils.isEmpty(common.getAspect().getEngName()))
					msgBuf.append(common.getAspect().getName());
				else
					msgBuf.append(common.getAspect().getEngName());
			}
		} else {
			msgBuf.append(common.getStdType().getName()).append("    ");
			msgBuf.append(common.getDepart().getName()).append("    ");
			if (null != common.getSpeciality())
				msgBuf.append(common.getSpeciality().getName()).append("    ");
			if (null != common.getAspect())
				msgBuf.append(common.getAspect().getName());
		}
		msgBuf.append("]");

		return msgBuf.toString();
	}

	public String getMessage() {
		return common.toString();
	}

}
