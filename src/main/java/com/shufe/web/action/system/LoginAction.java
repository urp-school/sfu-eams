package com.shufe.web.action.system;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.security.Authentication;
import com.ekingstar.security.model.User;
import com.ekingstar.security.monitor.SecurityMonitor;
import com.ekingstar.security.monitor.UserNamePasswordAuthentication;
import com.ekingstar.security.web.formbean.LoginForm;
import com.shufe.web.action.common.DispatchBasicAction;

public class LoginAction extends DispatchBasicAction {
    
    private SecurityMonitor securityMonitor;
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginForm loginForm = (LoginForm) form;
        String errorMsg = login(request, loginForm);
        if (StringUtils.isNotEmpty(errorMsg)) {
            request.getSession().removeAttribute(Authentication.USERID);
            if (StringUtils.equals("security.error.userUnactive", errorMsg)) {
                User user = (User) utilService.load(User.class, "name", loginForm.getName())
                        .iterator().next();
                addSingleParameter(request, "userRemark", user.getRemark());
            }
            return forward(mapping, request, errorMsg, "failure");
        }
        // 设置Local
        String language = loginForm.getLang();
        if (language.equals("english")) {
            request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.US);
        } else {
            request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.SIMPLIFIED_CHINESE);
        }
        return mapping.findForward(SUCCESS);
    }
    
    protected String login(HttpServletRequest request, LoginForm loginForm) {
        UserNamePasswordAuthentication auth = new UserNamePasswordAuthentication(loginForm
                .getName(), loginForm.getPassword());
        try {
            securityMonitor.authenticate(auth, request);
        } catch (com.ekingstar.security.AuthenticationException e) {
            return e.getMessage();
        }
        return "";
    }
    
    protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
    
    public void setSecurityMonitor(SecurityMonitor securityMonitor) {
        this.securityMonitor = securityMonitor;
    }
    
}
