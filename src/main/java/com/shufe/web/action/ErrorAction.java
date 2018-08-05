/*
 * 创建日期 2005-9-7
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.shufe.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @author dell
 * 
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ErrorAction extends DispatchActionSupport {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionMessages actionMessages = new ActionMessages();

		if (request.getParameter("errorCode") != null
				&& request.getParameter("errorCode").equals(
						"notEnoughAuthority")) {
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("error.notEnoughAuthority"));
		}
		saveErrors(request, actionMessages);
		return mapping.findForward("error");
	}

	protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}
