package com.shufe.web.action.system.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.utils.web.CookieUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 加载用户主界面
 * 
 * @author duyaming
 * @author chaostone
 * 
 */
public class HomeAction extends DispatchBasicAction {
	// 默认版
	public final static String DEFAULT = "default";
	// 简约版
	public final static String SIMPLE = "simple";

	public static final String FACADE = "system.facade";

	public ActionForward home(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String facade = CookieUtils.getCookieValue(request, HomeAction.FACADE);
		if (StringUtils.isEmpty(facade)) {
			facade = (String) getSystemConfig().getConfigItemValue(
					HomeAction.FACADE);
		}
		if (StringUtils.isEmpty(facade)) {
			facade = DEFAULT;
		}
		if (facade.equals(DEFAULT))
			return forward(request,
					new Action(DefaultHomeAction.class, "index"));
		else if (facade.equals(SIMPLE)) {
			return forward(request, new Action(SimpleHomeAction.class, "index"));
		} else {
			return null;
		}
	}

}
