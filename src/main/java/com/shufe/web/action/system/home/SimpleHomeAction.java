/*
 * 创建日期 2005-9-15
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.shufe.web.action.system.home;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.security.Authentication;
import com.ekingstar.security.AuthenticationException;
import com.ekingstar.security.monitor.SsoAuthentication;
import com.neusoft.education.tp.sso.client.CASReceipt;
import com.neusoft.education.tp.sso.client.filter.CASFilter;
import com.shufe.model.system.message.SystemMessage;

/**
 * 加载用户登陆信息
 * 
 * @author duyaming
 * @author chaostone
 * 
 */
public class SimpleHomeAction extends DefaultHomeAction {

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	  System.out.println(request.getSession().getMaxInactiveInterval());
		Long userId = getUserId(request.getSession());
		if (null == userId) {
			CASReceipt receipt = (CASReceipt) request.getSession()
					.getAttribute(CASFilter.SSO_FILTER_RECEIPT);
			String ssoName = (null == receipt) ? null : receipt.getUserName();
			if (null != ssoName) {
				try{
				securityMonitor.authenticate(new SsoAuthentication(), request);
				}catch(Exception e){
				}
			}
			userId = getUserId(request.getSession());
			if (null == userId)
				throw new AuthenticationException("without login");
		}
		Long category = getLong(request, Authentication.USER_CATEGORYID);
		Long curCategory = (Long) request.getSession().getAttribute(
				Authentication.USER_CATEGORYID);
		if (null == category || category.intValue() == 0) {
			category = curCategory;
		}
		// 身份发生了变化
		if (!category.equals(curCategory)) {
			securityMonitor.getSessionController().changeCategory(
					request.getSession().getId(), category);
			request.getSession().setAttribute(Authentication.USER_CATEGORYID,
					category);
		}
		request.setAttribute("date", new Date(System.currentTimeMillis()));
		request.setAttribute("newMessageCount", new Integer(
				systemMessageService.getMessageCount(userId,
						SystemMessage.newly)));
		request.setAttribute("user", getUser(request.getSession()));
		return forward(request);
	}
}
