package com.ekingstar.security.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.ekingstar.security.AuthenticationException;
import com.ekingstar.security.User;
import com.ekingstar.security.service.UserService;
import com.ekingstar.security.utils.EncryptUtil;

public class PasswordAction extends SecurityBaseAction {
    
    private UserService userService;
    
    private MailSender mailSender;
    
    private SimpleMailMessage message;
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }
    
    public ActionForward editUserAccount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = null;
        try {
            user = getUser(request);
        } catch (AuthenticationException e) {
            user = userService.get(getLong(request, "user.id"));
        }
        request.setAttribute("user", user);
        return forward(request);
    }
    
    public ActionForward saveOrUpdateAccount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateAccount(mapping, request);
    }
    
    public ActionForward resetPassword(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public ActionForward changePassword(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("user", getUser(request));
        return forward(request);
    }
    
    public ActionForward updateUserAccount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateAccount(mapping, request);
    }
    
    public ActionForward saveChange(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateAccount(mapping, request);
    }
    
    private ActionForward updateAccount(ActionMapping mapping, HttpServletRequest request) {
        User user = null;
        try {
            user = getUser(request);
        } catch (AuthenticationException e) {
            user = userService.get(getLong(request, "user.id"));
        }
        user.setEmail(get(request, "email"));
        user.setPassword(get(request, "password"));
        this.utilService.saveOrUpdate(user);
        ActionMessages msgs = new ActionMessages();
        msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("attr.updateSuccess"));
        addErrors(request, msgs);
        return mapping.findForward("actionResult");
    }
    
    public ActionForward sendPassword(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = getUser(request);
        String longinName = user.getName();
        String password = RandomStringUtils.randomNumeric(6);
        user.setRemark(password);
        user.setPassword(EncryptUtil.encodePassword(password));
        MessageResources messageResources = getResources(request);
        String title = messageResources.getMessage("user.password.sendmail.title");
        
        List<Object> values = new ArrayList<Object>();
        values.add(longinName);
        values.add(password);
        String body = messageResources.getMessage("user.password.sendmail.body", values.toArray());
        try {
            SimpleMailMessage msg = new SimpleMailMessage(this.message);
            msg.setTo(user.getEmail());
            msg.setSubject(title);
            msg.setText(body.toString());
            this.mailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("reset password error for user:" + user.getName() + " with email :" + user
                    .getEmail());
            return goErrorWithMessage(request, "error.email.sendError");
        }
        
        this.utilService.saveOrUpdate(user);
        return forward(request, "sendResult");
    }
    
    private ActionForward goErrorWithMessage(HttpServletRequest request, String key) {
        ActionMessage msg = new ActionMessage(key);
        ActionMessages msgs = new ActionMessages();
        msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", msg);
        addErrors(request, msgs);
        return forward(request, "resetPassword");
    }
}