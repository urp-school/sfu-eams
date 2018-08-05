/*
 * 创建日期 2005-9-8
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.shufe.web.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DispatchActionSupport;

import com.ekingstar.commons.utils.web.CookieUtils;
import com.ekingstar.security.Authentication;

/**
 * @author dell,chaostone
 */
public class LogoutAction extends DispatchActionSupport {
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookieByName(request, response, Authentication.PASSWORD);
        request.getSession().invalidate();
        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(request.getParameter("flag"))) {
            return null;
        } else {
            return mapping.findForward("success");
        }
    }
}
