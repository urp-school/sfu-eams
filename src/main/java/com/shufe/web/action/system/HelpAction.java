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
 * chaostone             2006-7-7            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.system;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 帮助系统响应类
 * 
 * @author chaostone
 * 
 */
public class HelpAction extends DispatchBasicAction {

	protected static final String helpPath = "/WEB-INF/help/";

	protected static final String urlPostfix = ".html";

	protected static final String cn = "zh_CN";

	protected static final String en = "en_US";

	public ActionForward help(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String module = request.getParameter("helpId");
		if (StringUtils.isEmpty(module)) {
			return forward(request,"adminHome");
		}
		StringBuffer buf = new StringBuffer();
		buf.append(helpPath);
		Locale locale = (Locale) request.getSession().getAttribute(
				Globals.LOCALE_KEY);
		String localName = locale.getLanguage();
		if (null == localName) {
			localName = "zh_CN";
		}
		if (localName.indexOf("zh") != -1) {
			buf.append(cn);
		} else {
			buf.append(en);
		}
		buf.append("/");
		if (module.indexOf("/") == -1) {
			buf.append(module.replace('.', '/'));
			buf.append("/index");
		} else {
			String controller = module.substring(0, module.indexOf("/"));
			buf.append(controller.replace('.', '/'));
			buf.append(module.substring(module.indexOf("/")));
		}
		buf.append(urlPostfix);
		log.debug("HELP system: " + buf.toString());
		return forward(request,"adminHome");
	}

	public ActionForward stdHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return forward(request);
	}

	public ActionForward teacherHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return forward(request);
	}
}
