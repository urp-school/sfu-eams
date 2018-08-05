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
 * chaostone             2006-8-25            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.system.message;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.MessageType;
import com.ekingstar.eams.system.basecode.service.BaseCodeService;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.User;
import com.ekingstar.security.model.UserCategory;
import com.ekingstar.security.service.UserService;
import com.shufe.model.std.Student;
import com.shufe.model.system.message.Message;
import com.shufe.model.system.message.SystemMessage;
import com.shufe.service.system.message.SystemMessageService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.BaseInfoSearchHelper;

public class SystemMessageAction extends RestrictionSupportAction {
    
    protected BaseCodeService baseCodeService;
    
    protected SystemMessageService messageService;
    
    protected UserService userService;
    
    protected BaseInfoSearchHelper baseInfoSearchHelper;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        User user = getUser(request.getSession());
        request.setAttribute("user", user);
        Boolean isAdmin = Boolean.FALSE;
        Set categories = user.getCategories();
        if (null != categories) {
            Iterator it = categories.iterator();
            while (it.hasNext()) {
                UserCategory temp = (UserCategory) it.next();
                if (temp.getId().equals(EamsRole.MANAGER_USER)) {
                    isAdmin = Boolean.TRUE;
                    break;
                }
            }
        }
        request.setAttribute("isAdmin", isAdmin);
        return forward(request);
    }
    
    /**
     * 消息详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        SystemMessage systemMessage = (SystemMessage) getEntity(request, SystemMessage.class,
                "systemMessage");
        if (null != systemMessage) {
            if (systemMessage.getRecipient().equals(getUser(request.getSession()))) {
                systemMessage.setStatus(SystemMessage.readed);
                utilService.saveOrUpdate(systemMessage);
            }
            request.setAttribute("systemMessage", systemMessage);
            return forward(request);
        }
        return forwardError(mapping, request, "error.model.notExist");
    }
    
    /**
     * 收件箱
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newly(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        query.add(Condition.eq("systemMessage.status", SystemMessage.newly));
        query.add(Condition.eq("systemMessage.recipient.id", getUserId(request.getSession())));
        addCollection(request, "systemMessages", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 新邮件提示
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newMessageNotification(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        query.add(Condition.eq("systemMessage.status", SystemMessage.newly));
        query.add(Condition.eq("systemMessage.recipient.id", getUserId(request.getSession())));
        addCollection(request, "systemMessages", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 发件箱
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward sendbox(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        query.add(Condition.eq("systemMessage.message.sender.id", getUserId(request.getSession())));
        addCollection(request, "systemMessages", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward readed(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        query.add(Condition.eq("systemMessage.status", SystemMessage.readed));
        query.add(Condition.eq("systemMessage.recipient.id", getUserId(request.getSession())));
        addCollection(request, "systemMessages", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 垃圾箱
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward trash(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        query.add(Condition.eq("systemMessage.status", SystemMessage.inTrash));
        query.add(Condition.eq("systemMessage.recipient.id", getUserId(request.getSession())));
        addCollection(request, "systemMessages", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward setStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String messageIds = request.getParameter("systemMessageIds");
        if (StringUtils.isEmpty(messageIds)) {
            return forwardError(mapping, request, "error.parameters.needed");
        } else {
            Integer status = getInteger(request, "status");
            if (null == status)
                return forwardError(mapping, request, "error.parameters.needed");
            if (status.intValue() < SystemMessage.newly.intValue()
                    || status.intValue() > SystemMessage.inTrash.intValue()) {
                return forwardError(mapping, request, "error.parameters.illegal");
            }
            List messages = utilService.load(SystemMessage.class, "id", SeqStringUtil
                    .transformToLong(messageIds));
            Long userId = getUserId(request.getSession());
            for (Iterator iter = messages.iterator(); iter.hasNext();) {
                SystemMessage message = (SystemMessage) iter.next();
                if (message.getRecipient().getId().equals(userId)) {
                    message.setStatus(status);
                    utilService.saveOrUpdate(message);
                    
                }
            }
        }
        String setFrom = request.getParameter("setFrom");
        if (null == setFrom) {
            setFrom = "newly";
        }
        return redirect(request, setFrom, "info.set.success");
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Boolean removeForever = getBoolean(request, "removeForever");
        if (null == removeForever) {
            removeForever = Boolean.FALSE;
        }
        String removeFrom = request.getParameter("removeFrom");
        String messageIds = request.getParameter("systemMessageIds");
        if (StringUtils.isEmpty(messageIds)) {
            return forwardError(mapping, request, "error.parameters.needed");
        } else {
            List messages = utilService.load(SystemMessage.class, "id", SeqStringUtil
                    .transformToLong(messageIds));
            Long userId = getUserId(request.getSession());
            for (Iterator iter = messages.iterator(); iter.hasNext();) {
                SystemMessage message = (SystemMessage) iter.next();
                if (Boolean.TRUE.equals(removeForever)) {
                    if ((message.getRecipient().getId().equals(userId))
                            || (message.getMessage().getSender().getId().equals(userId))) {
                        utilService.remove(message);
                    }
                } else {
                    if (!message.getStatus().equals(SystemMessage.inTrash)) {
                        message.setStatus(SystemMessage.inTrash);
                        utilService.saveOrUpdate(message);
                    } else {
                        utilService.remove(message);
                    }
                }
            }
        }
        
        if (null == removeFrom) {
            removeFrom = "newly";
        }
        return redirect(request, removeFrom, "info.delete.success");
    }
    
    public ActionForward newMessage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String who = request.getParameter("who");
        List messageTypes = baseCodeService.getCodes(MessageType.class);
        addCollection(request, "messageTypes", messageTypes);
        addEntity(request, Message.getDefaultMessage(getUser(request.getSession())));
        request.setAttribute("sender", getUser(request.getSession()));
        if (StringUtils.isEmpty(who))
            return forwardError(mapping, request, "error.parameters.needed");
        return forward(request);
    }
    
    /**
     * 恢复消息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String id = request.getParameter("systemMessage.id");
        SystemMessage systemMessage = (SystemMessage) utilService.load(SystemMessage.class, Long
                .valueOf(id));
        return forward(request, new Action(this.getClass(), "quickSend", "&receiptorIds="
                + systemMessage.getMessage().getSender().getName()));
    }
    
    /**
     * 快速发送
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward quickSend(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String receiptorIds = request.getParameter("receiptorIds");
        if (StringUtils.isEmpty(receiptorIds))
            return forwardError(mapping, request, "error.parameters.needed");
        List recipients = utilService
                .load(User.class, "name", StringUtils.split(receiptorIds, ","));
        if (recipients.isEmpty())
            return forwardError(mapping, request, "error.model.notExist");
        addCollection(request, "recipients", recipients);
        Message msg = Message.getDefaultMessage(getUser(request.getSession()));
        RequestUtil.populate(request, msg, true);
        addEntity(request, msg);
        
        List messageTypes = baseCodeService.getCodes(MessageType.class);
        addCollection(request, "messageTypes", messageTypes);
        request.setAttribute("user", getUser(request.getSession()));
        return forward(request);
    }
    
    /**
     * 跳到消息界面 并且发送消息.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward sendTo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String receiptorIds = request.getParameter("receiptorIds");
        if (StringUtils.isEmpty(receiptorIds))
            return forwardError(mapping, request, "error.parameters.needed");
        List recipients = utilService
                .load(User.class, "name", StringUtils.split(receiptorIds, ","));
        Message message = (Message) populate(request, Message.class);
        message.setCreateAt(new Date(System.currentTimeMillis()));
        message.setSender(getUser(request.getSession()));
        messageService.sendMessage(message, recipients);
        if (StringUtils.isNotEmpty(request.getParameter("quickSend"))) {
            // 提示成功信息
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.send.success"));
            saveErrors(request, messages);
            return mapping.findForward("actionResult");
        } else {
            return redirect(request, "sendbox", "info.send.success");
        }
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = new EntityQuery(Student.class, "std");
        QueryRequestSupport.populateConditions(request, entityQuery);
        String adminClassName = request.getParameter("adminClass.name");
        if (StringUtils.isNotEmpty(adminClassName)) {
            entityQuery.join("std.adminClasses", "adminClass");
            entityQuery.add(new Condition(" adminClass.name like :className", "%" + adminClassName
                    + "%"));
        }
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "stds", utilService.search(entityQuery));
        
        return forward(request);
    }
    
    public ActionForward teacherList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "teachers", baseInfoSearchHelper.searchTeacher(request));
        return forward(request);
    }
    
    public ActionForward userList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(User.class, "user");
        populateConditions(request, query);
        query.add(new Condition("user.state=1"));
        query.setLimit(getPageLimit(request));
        addCollection(request, "users", utilService.search(query));
        return forward(request);
    }
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(SystemMessage.class, "systemMessage");
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    /**
     * @param baseCodeService
     *            The baseCodeService to set.
     */
    public void setBaseCodeService(BaseCodeService baseCodeService) {
        this.baseCodeService = baseCodeService;
    }
    
    /**
     * @param messageService
     *            The messageService to set.
     */
    public void setMessageService(SystemMessageService messageService) {
        this.messageService = messageService;
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
        this.baseInfoSearchHelper = baseInfoSearchHelper;
    }
}
