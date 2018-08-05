//$Id: DispatchBasicAction.java,v 1.46 2007/01/17 01:09:31 duanth Exp $
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
 * duyaming                                 Created
 * chaostone            2005-10-22          add forwardError and try catch all
 *                                          exception in excute
 * chenweixiong         2005-04             add getRedirectForward 
 * chaostone            2005-04             add getRedirectForwardWithParams
 * chaostone            2006-11-07          增加getSystemSetting
 * zq                   2007-09-18          把execute()的catch (Exception e3)中第一
 *                                          个error(...)方法，改成logHelper.info(...)
 ********************************************************************************/

package com.shufe.web.action.common;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ekingstar.common.detail.DetailObject;
import net.ekingstar.common.detail.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.web.struts.DispatchActionSupport;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.Model;
import com.ekingstar.commons.model.type.EntityType;
import com.ekingstar.commons.mvc.struts.action.BaseAction;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.mvc.struts.misc.StrutsMessageResource;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.Page;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.transfer.ExcelTools;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.transfer.exporter.DefaultEntityExporter;
import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;
import com.ekingstar.commons.transfer.exporter.Exporter;
import com.ekingstar.commons.transfer.exporter.ItemExporter;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.transfer.exporter.TemplateExporter;
import com.ekingstar.commons.transfer.exporter.writer.ExcelItemWriter;
import com.ekingstar.commons.transfer.exporter.writer.ExcelTemplateWriter;
import com.ekingstar.commons.transfer.exporter.writer.TextItemWriter;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.commons.web.dispatch.Conventions;
import com.ekingstar.eams.system.basecode.service.BaseCodeService;
import com.ekingstar.eams.system.baseinfo.service.BaseInfoService;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.time.CalendarNotExistException;
import com.ekingstar.security.Authentication;
import com.ekingstar.security.AuthenticationException;
import com.ekingstar.security.Resource;
import com.ekingstar.security.User;
import com.ekingstar.security.service.AuthorityService;
import com.shufe.model.quality.evaluate.EvaluateStatics;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.model.system.file.FilePath;
import com.shufe.service.course.grade.GradeService;
import com.shufe.util.RequestUtil;
import com.shufe.web.Constants;
import com.shufe.web.OutputProcessObserver;
import com.shufe.web.exception.DepartAuthInsufficientException;
import com.shufe.web.exception.StdTypeAuthInsufficientException;
import com.shufe.web.helper.LogHelper;

/**
 * 基于action方法的基本action.
 * 
 * @author dell,chaostone
 */
public abstract class DispatchBasicAction extends DispatchActionSupport implements Constants {
    
    protected DetailObject Results = new DetailObject();
    
    protected UtilService utilService;
    
    protected AuthorityService authorityService;
    
    protected BaseCodeService baseCodeService;
    
    protected BaseInfoService baseInfoService;
    
    protected GradeService gradeService;
    
    /** 系统日志 */
    protected LogHelper logHelper;
    
    protected static final String errorForward = "error";
    
    /**
     * 从request的参数或者cookie中(参数优先)取得分页信息
     * 
     * @param request
     * @return
     */
    public PageLimit getPageLimit(HttpServletRequest request) {
        return QueryRequestSupport.getPageLimit(request);
    }
    
    public Long getUserCategoryId(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute(Authentication.USER_CATEGORYID);
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // FIXME put me in context listener
            if (null == request.getSession().getServletContext().getAttribute(
                    SystemConfig.SYSTEM_CONFIG)) {
                request.getSession().getServletContext().setAttribute(SystemConfig.SYSTEM_CONFIG,
                        SystemConfigLoader.getConfig());
            }
            return super.execute(mapping, form, request, response);
        } catch (DepartAuthInsufficientException e0) {
            error(e0.getMessage());
            addSingleParameter(request, "stackTrace", ExceptionUtils.getStackTrace(e0));
            return forwardError(mapping, request, "error.depart.dataRealm.insufficient");
        } catch (StdTypeAuthInsufficientException e1) {
            error(e1.getMessage());
            addSingleParameter(request, "stackTrace", ExceptionUtils.getStackTrace(e1));
            return forwardError(mapping, request, "error.stdType.dataRealm.insufficient");
        } catch (CalendarNotExistException e2) {
            return forward(request, "/pages/system/calendar/teachCalendar/noTeachCalendar");
        } catch (Exception e3) {
            if (e3 instanceof AuthenticationException) {
                throw e3;
            }
            if (log.isDebugEnabled()) {
                e3.printStackTrace();
            }
            if (getUser(request.getSession()) != null) {
                info(request.getRequestURI() + "--"
                        + StringUtils.abbreviate(ExceptionUtils.getStackTrace(e3), 400), e3);
            } else {
                error("unknown error:" + request.getRequestURI() + "--"
                        + StringUtils.abbreviate(ExceptionUtils.getStackTrace(e3), 400));
            }
            addSingleParameter(request, "stackTrace", ExceptionUtils.getStackTrace(e3));
            return forward(mapping, request, "error.occurred", errorForward);
        }
    }
    
    protected String getMethodName(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, String parameter)
            throws Exception {
        String method = get(request, parameter);
        if (StringUtils.isEmpty(method)) {
            return Conventions.getProfile(this.getClass()).getDefaultMethod();
        }
        return method;
    }
    
    protected SystemConfig getSystemConfig() {
        return SystemConfigLoader.getConfig();
    }
    
    protected ActionForward forward(ActionMapping mapping, HttpServletRequest request,
            String forwardTag, DetailObject detailObject) {
        if (detailObject != null) {
            addSingleParameter(request, DETAIL_RESULT, detailObject.getDetail());
        }
        debug("Action forwarding [" + mapping.findForward(forwardTag) + "]");
        return mapping.findForward(forwardTag);
    }
    
    /**
     * 跳转到指定的资源上.
     * 
     * @param mapping
     * @param request
     * @param forwardTag
     *            struts-config.xml 配置的该action相关的跳转名
     * @return
     */
    protected ActionForward forward(ActionMapping mapping, HttpServletRequest request,
            String forwardTag) {
        if (Results != null) {
            addSingleParameter(request, DETAIL_RESULT, Results.getDetail());
        }
        debug("Action forwarding [" + mapping.findForward(forwardTag) + "]");
        return mapping.findForward(forwardTag);
    }
    
    /**
     * 跳转到指定的错误页面上资源上.
     * 
     * @param mapping
     * @param request
     * @return
     */
    protected ActionForward forwardError(ActionMapping mapping, HttpServletRequest request,
            String errorKey) {
        if (Results != null) {
            request.setAttribute(DETAIL_RESULT, Results.getDetail());
        }
        ActionMessages actionMessages = new ActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
        addErrors(request, actionMessages);
        debug("Action forwarding [" + mapping.findForward(errorForward) + "]");
        return mapping.findForward(errorForward);
    }
    
    /**
     * @param mapping
     * @param request
     * @param errorKeys
     * @return
     */
    protected ActionForward forwardError(ActionMapping mapping, HttpServletRequest request,
            String[] errorKeys) {
        ActionMessages actionMessages = new ActionMessages();
        for (int i = 0; i < errorKeys.length; i++) {
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKeys[i]));
        }
        if (Results != null) {
            request.setAttribute(DETAIL_RESULT, Results.getDetail());
        }
        addErrors(request, actionMessages);
        debug("Action forwarding [" + mapping.findForward(errorForward) + "]");
        return mapping.findForward(errorForward);
    }
    
    /**
     * @param mapping
     * @param request
     * @param errorKey
     * @param forwardTag
     * @return
     */
    protected ActionForward forward(ActionMapping mapping, HttpServletRequest request,
            String errorKey, String forwardTag) {
        ActionMessages actionMessages = new ActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
        addErrors(request, actionMessages);
        return mapping.findForward(forwardTag);
    }
    
    /**
     * @param mapping
     * @param request
     * @param messageKeys
     * @param forwardTag
     * @return
     */
    protected ActionForward getRedirectForward(ActionMapping mapping, HttpServletRequest request,
            String[] messageKeys, String forwardTag) {
        return getRedirectForwardWithParams(mapping, request, messageKeys, null, forwardTag);
    }
    
    /**
     * added by chen wei xiong
     * 
     * @param mapping
     * @param request
     * @param messageKey
     * @param forwardTag
     * @return
     */
    protected ActionForward getRedirectForward(ActionMapping mapping, HttpServletRequest request,
            String messageKey, String forwardTag) {
        return getRedirectForwardWithParams(mapping, request, new String[] { messageKey }, null,
                forwardTag);
    }
    
    /**
     * 重定向跳转到指定的forward,并能添加一定的参数
     * 
     * @param mapping
     * @param request
     * @param messageKey
     * @param params
     *            想要添加的参数,形式如：&param1=dd&param2=dd
     * @param forwardTag
     * @return
     */
    protected ActionForward getRedirectForwardWithParams(ActionMapping mapping,
            HttpServletRequest request, String messageKey, String params, String forwardTag) {
        return getRedirectForwardWithParams(mapping, request, new String[] { messageKey }, params,
                forwardTag);
    }
    
    /**
     * 重定向跳转到指定的forward,并能添加一定的参数
     * 
     * @param mapping
     * @param request
     * @param messageKeys
     * @param params
     * @param forwardTag
     * @return
     */
    protected ActionForward getRedirectForwardWithParams(ActionMapping mapping,
            HttpServletRequest request, String[] messageKeys, String params, String forwardTag) {
        ActionForward actionForward = forward(mapping, request, forwardTag);
        StringBuffer sbf = new StringBuffer(actionForward.getPath());
        if (sbf.lastIndexOf("?") > 0) {
            sbf.append("&messages=");
        } else {
            sbf.append("?messages=");
        }
        for (int i = 0; i < messageKeys.length; i++) {
            sbf.append(messageKeys[i] + ",");
        }
        if (StringUtils.isNotEmpty(params)) {
            sbf.append(params);
        }
        return new ActionForward(sbf.toString(), true);
    }
    
    /**
     * ����
     * 
     * @param mapping
     * @param request
     * @param errorKeys
     * @param forwardTag
     * @return
     */
    protected ActionForward forward(ActionMapping mapping, HttpServletRequest request,
            String[] errorKeys, String forwardTag) {
        ActionMessages actionMessages = new ActionMessages();
        for (int i = 0; i < errorKeys.length; i++) {
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKeys[i]));
        }
        addErrors(request, actionMessages);
        return mapping.findForward(forwardTag);
    }
    
    protected DetailObject getResults() {
        return this.Results;
    }
    
    protected boolean checkToken(javax.servlet.http.HttpServletRequest request,
            ActionMessages actionMessages) {
        if (isTokenValid(request)) {
            resetToken(request);
            return true;
        } else {
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.invaild.token"));
            saveErrors(request, actionMessages);
            return false;
        }
    }
    
    protected User getUser(HttpSession session) {
        return (User) utilService.get(User.class, (Long) session
                .getAttribute(Authentication.USERID));
    }
    
    protected User getUser(HttpServletRequest request) {
        return (User) utilService.get(User.class, (Long) request.getSession().getAttribute(
                Authentication.USERID));
    }
    
    protected static Long getUserId(HttpSession session) {
        return (Long) session.getAttribute(Authentication.USERID);
    }
    
    protected static String getLoginName(HttpSession session) {
        return (String) session.getAttribute(Authentication.LOGINNAME);
    }
    
    protected static String getLoginName(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(Authentication.LOGINNAME);
    }
    
    protected Student getStudentFromSession(HttpSession session) {
        User user = getUser(session);
        if (null == user) {
            return null;
        } else {
            if (user.isCategory(EamsRole.STD_USER)) {
                List stds = utilService.load(Student.class, "code", user.getName());
                if (stds.isEmpty()) {
                    return null;
                } else {
                    return (Student) stds.get(0);
                }
            }
            return null;
        }
    }
    
    protected Teacher getTeacherFromSession(HttpSession session) {
        User user = getUser(session);
        if (null == user) {
            return null;
        } else {
            if (user.isCategory(EamsRole.TEACHER_USER)) {
                List rs = utilService.load(Teacher.class, "code", user.getName());
                if (!rs.isEmpty()) {
                    return (Teacher) rs.get(0);
                } else {
                    return null;
                }
            }
            return null;
        }
    }
    
    protected Tutor getTutorFromSession(HttpSession session) {
        User user = getUser(session);
        if (null == user) {
            return null;
        } else {
            if (user.isCategory(EamsRole.TEACHER_USER)) {
                List rs = utilService.load(Tutor.class, "code", user.getName());
                if (!rs.isEmpty()) {
                    return (Tutor) rs.get(0);
                } else {
                    return null;
                }
            }
            return null;
        }
    }
    
    protected void debug(Object debubObj) {
        if (log.isDebugEnabled()) {
            if (debubObj != null) {
                log.debug(debubObj);
            } else {
                log.debug("the object is null");
            }
        }
    }
    
    protected void debug(Object debubObj, Exception e) {
        if (log.isDebugEnabled()) {
            if (debubObj != null) {
                log.debug(debubObj, e);
            } else {
                log.debug("the object is null");
            }
        }
    }
    
    protected void info(Object infoObj) {
        if (log.isInfoEnabled()) {
            if (infoObj != null) {
                log.info(infoObj);
            } else {
                log.info("the object is null");
            }
        }
    }
    
    protected void info(Object infoObj, Exception e) {
        if (log.isInfoEnabled()) {
            if (infoObj != null) {
                log.info(infoObj, e);
            } else {
                log.info("the object is null");
            }
        }
    }
    
    protected void error(Object errorObj) {
        if (log.isErrorEnabled()) {
            if (errorObj != null) {
                log.error(errorObj);
            } else {
                log.error("the object is null");
            }
        }
    }
    
    protected void error(Object errorObj, Exception e) {
        if (log.isErrorEnabled()) {
            if (errorObj != null) {
                log.error(errorObj, e);
            } else {
                log.error("the object is null");
            }
        }
    }
    
    /**
     * @param utilService
     *            The utilService to set.
     */
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
    
    public void setBaseCodeService(BaseCodeService codeService) {
        this.baseCodeService = codeService;
    }
    
    /**
     * @param request
     * @param response
     * @param list
     * @param objectName
     * @throws Exception
     */
    protected void outputExcelFile(HttpServletRequest request, HttpServletResponse response,
            List list, String objectName) throws Exception {
        outputExcelFile(request, response, list, objectName, new DefaultPropertyExtractor());
        
    }
    
    /**
     * 输出到excel中.
     * 
     * @param request
     * @param response
     * @param list
     *            对象列表
     * @param objectName
     *            对应输出属性文件中的名字
     * @param exporter
     *            属性输出器
     * @throws Exception
     */
    protected void outputExcelFile(HttpServletRequest request, HttpServletResponse response,
            List list, String objectName, PropertyExtractor extracor) throws Exception {
        MessageResources message = getResources(request, "excelconfig");
        String propertyKeys = message.getMessage(objectName + ".keys");
        String propertyShowKeys = message.getMessage(objectName + ".showkeys");
        ExcelTools et = new ExcelTools();
        HSSFWorkbook wb = et.object2Excel(list, propertyKeys, propertyShowKeys, extracor);
        response.setContentType("application/vnd.ms-excel;charset=GBK");
        response.setHeader("Content-Disposition", "attachment;filename=" + objectName + ".xls");
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }
    
    protected OutputProcessObserver getOutputProcessObserver(ActionMapping mapping,
            HttpServletRequest request, HttpServletResponse response, String forwardName,
            Class observerClass) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        ActionForward processDisplay = mapping.findForward(forwardName);
        String path = request.getSession().getServletContext().getRealPath("")
                + processDisplay.getPath();
        OutputProcessObserver observer = (OutputProcessObserver) observerClass.newInstance();
        observer.setResourses(getResources(request));
        observer.setLocale(getLocale(request));
        observer.setWriter(response.getWriter());
        observer.setPath(path);
        observer.outputTemplate();
        return observer;
    }
    
    protected OutputProcessObserver getOutputProcessObserver(ActionMapping mapping,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getOutputProcessObserver(mapping, request, response, "processDisplay",
                OutputProcessObserver.class);
    }
    
    protected OutputProcessObserver getOutputProcessObserver(ActionMapping mapping,
            HttpServletRequest request, HttpServletResponse response, Class observerClass)
            throws Exception {
        return getOutputProcessObserver(mapping, request, response, "processDisplay", observerClass);
    }
    
    public static void addEntity(HttpServletRequest request, Entity obj) {
        request.setAttribute(EntityUtils.getCommandName(obj), obj);
    }
    
    public static void addEntity(HttpServletRequest request, String name, Entity obj) {
        request.setAttribute(name, obj);
    }
    
    /**
     * 在Request中添加集合（带分页机制）的页面传值
     * 
     * @param request
     * @param name
     * @param objs
     */
    public static void addCollection(HttpServletRequest request, String name, Collection objs) {
        if (objs instanceof Page) {
            QueryRequestSupport.addPage(request, objs);
        }
        request.setAttribute(name, objs);
    }
    
    public static void addOldPage(HttpServletRequest request, String name, Pagination page) {
        request.setAttribute(QueryRequestSupport.PAGENO, new Integer(page.getPageNumber()));
        request.setAttribute("previousPageNo", new Integer(page.getPreviousPageNumber()));
        request.setAttribute("nextPageNo", new Integer(page.getNextPageNumber()));
        request.setAttribute("maxPageNo", new Integer(page.getMaxPageNumber()));
        request.setAttribute(QueryRequestSupport.PAGESIZE, new Integer(page.getPageSize()));
        request.setAttribute("thisPageSize", new Integer(page.getItems().size()));
        request.setAttribute("totalSize", new Integer(page.getItemCount()));
        request.setAttribute(name, page.getItems());
    }
    
    /**
     * 跳转到下一个action
     * 
     * @param request
     * @param action
     * @return
     */
    protected ActionForward forward(HttpServletRequest request, Action action) {
        return ForwardSupport.forward(request, action);
    }
    
    protected ActionForward forward(HttpServletRequest request, Action action, String msg) {
        saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { msg }));
        return ForwardSupport.forward(request, action);
    }
    
    protected ActionForward forward(HttpServletRequest request, String pagePath) {
        ActionForward f = ForwardSupport.forward(clazz, request, pagePath);
        if (Results != null) {
            request.setAttribute(DETAIL_RESULT, Results.getDetail());
        }
        return f;
    }
    
    protected ActionForward forward(HttpServletRequest request) {
        ActionForward f = ForwardSupport.forward(clazz, request, (String) null);
        if (Results != null) {
            request.setAttribute(DETAIL_RESULT, Results.getDetail());
        }
        return f;
    }
    
    /***********************************************************************************************
     * 重定向支持<br>
     * *************************************************************************
     */
    protected ActionForward redirect(HttpServletRequest request, String method, String message)
            throws Exception {
        return redirect(request, method, message, null);
    }
    
    protected ActionForward redirect(HttpServletRequest request, Action action, String message)
            throws Exception {
        return redirect(request, action, message, (String) null);
    }
    
    /**
     * 传递参数从request中提取，参数的前缀在prefixes中指定
     * 
     * @param request
     * @param method
     * @param messages
     * @param prefixes
     * @return
     * @throws Exception
     */
    protected ActionForward redirect(HttpServletRequest request, Action action, String message,
            String[] prefixes) throws Exception {
        saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { message }));
        StringBuffer buf = action.getURL(request);
        // 串接参数
        if (null != prefixes && prefixes.length > 0) {
            for (Iterator iter = request.getParameterMap().keySet().iterator(); iter.hasNext();) {
                String key = (String) iter.next();
                if (!StringUtils.equals("params", key)) {
                    for (int i = 0; i < prefixes.length; i++) {
                        if (key.startsWith(prefixes[i])) {
                            String value = request.getParameter(key);
                            if (StringUtils.isNotEmpty(value)) {
                                buf.append("&").append(key).append("=").append(
                                        URLEncoder.encode(value, "UTF-8"));
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (log.isDebugEnabled()) {
            debug("redirect:" + buf.toString());
        }
        return new ActionForward(buf.toString(), true);
    }
    
    /**
     * 从给定的参数重定向
     * 
     * @param request
     * @param method
     * @param message
     * @param parameters
     * @return
     * @throws Exception
     */
    protected ActionForward redirect(HttpServletRequest request, String method, String message,
            String parameters) throws Exception {
        return redirect(request, new Action("", method), message, parameters);
    }
    
    /**
     * 从给定的参数重定向
     * 
     * @param request
     * @param method
     * @param message
     * @param parameters
     * @return
     * @throws Exception
     */
    protected ActionForward redirect(HttpServletRequest request, Action action, String message,
            String parameters) throws Exception {
        action.addParams(parameters);
        saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { message }));
        return new ActionForward(action.getURL(request).toString(), true);
    }
    
    /**
     * 数据输出
     * 
     * @param mapping
     * @param request
     * @param forwardTag
     * @param detailObject
     * @return
     */
    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 查找导出参数
        String format = get(request, "format");
        String fileName = get(request, "fileName");
        String template = get(request, "template");
        if (StringUtils.isBlank(format)) {
            format = Transfer.EXCEL;
        }
        if (StringUtils.isEmpty(fileName)) {
            fileName = "exportResult";
        }
        
        // 配置导出上下文
        Context context = new Context();
        context.getDatas().put("format", format);
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && -1 != agent.indexOf("MSIE")) {
            fileName = URLEncoder.encode(fileName, "UTF8");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
            fileName = MimeUtility.encodeText(fileName, "UTF8", "B");
        }
        context.getDatas().put("exportFile", fileName);
        if (StringUtils.isNotBlank(template)) {
            SystemConfig config = (SystemConfig) request.getSession().getServletContext()
                    .getAttribute(SystemConfig.SYSTEM_CONFIG);
            String defaultPath = request.getSession().getServletContext().getRealPath(
                    FilePath.fileDirectory);
            String filePath = FilePath.getRealPath(config, FilePath.TEMPLATE_DOWNLOAD, defaultPath);
            template = filePath + template;
        }
        context.getDatas().put("templatePath", template);
        configExportContext(request, context);
        
        // 构造合适的输出器
        Exporter exporter = getExporter(request, context);
        // 设置下载项信息
        if (exporter instanceof ItemExporter) {
            ((ItemExporter) exporter).setTitles(StringUtils.split(getExportTitles(request), ","));
            if (format.equals(Transfer.EXCEL)) {
                exporter.setWriter(new ExcelItemWriter(response.getOutputStream()));
            } else if (format.equals(Transfer.TXT)) {
                TextItemWriter writer = new TextItemWriter(response.getOutputStream());
                exporter.setWriter(writer);
            } else {
                throw new RuntimeException("Exporter is not supported for other format:"
                        + exporter.getFormat());
            }
        } else {
            exporter.setWriter(new ExcelTemplateWriter(response.getOutputStream()));
        }
        
        if (format.equals(Transfer.EXCEL)) {
            response.setContentType("application/vnd.ms-excel;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + RequestUtils.encodeAttachName(request, context.getDatas().get("exportFile")
                            .toString()) + ".xls");
        } else if (format.equals(Transfer.TXT)) {
            response.setContentType("application/vnd.text;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + RequestUtils.encodeAttachName(request, context.getDatas().get("exportFile")
                            .toString()) + ".txt");
        } else {
            throw new RuntimeException("Exporter is not supported for other format:"
                    + exporter.getFormat());
        }
        // 进行输出
        exporter.setContext(context);
        exporter.transfer(new TransferResult());
        return null;
    }
    
    protected Exporter getExporter(HttpServletRequest request, Context context) {
        Exporter exporter = null;
        Collection datas = (Collection) context.getDatas().get("items");
        
        boolean isArray = false;
        if (!CollectionUtils.isEmpty(datas)) {
            Object first = datas.iterator().next();
            if (first.getClass().isArray()) {
                isArray = true;
            }
        }
        if (isArray) {
            exporter = new ItemExporter();
        } else {
            String template = (String) context.getDatas().get("templatePath");
            if (StringUtils.isNotBlank(template)) {
                exporter = new TemplateExporter();
            } else {
                exporter = new DefaultEntityExporter();
                ((DefaultEntityExporter) exporter).setAttrs(StringUtils.split(
                        getExportKeys(request), ","));
                ((DefaultEntityExporter) exporter)
                        .setPropertyExtractor(getPropertyExtractor(request));
            }
        }
        return exporter;
    }
    
    protected void configExportContext(HttpServletRequest request, Context context) {
        Collection datas = getExportDatas(request);
        context.getDatas().put("items", datas);
    }
    
    public ActionForward importForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("importAction", request.getRequestURI());
        return forward(request, "/pages/components/importData/form");
    }
    
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        return new DefaultPropertyExtractor(getLocale(request), new StrutsMessageResource(
                getResources(request)));
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        Collection list = null;
        String kind = get(request, "kind");
        String courseSeq = get(request, "courseSeq");
        String courseName = get(request, "courseName");
        String yearfrom = get(request, "yearfrom");
        String termfrom = get(request, "termfrom");
        String yearto = get(request, "yearto");
        String termto = get(request, "termto");
        String searchType = get(request, "searchType");
        Long departmentId = getLong(request, "college.id");
        if ("nomal".equals(kind)) {
            list = gradeService.getWithExamStatus(courseSeq, courseName, yearfrom, termfrom,
                    yearto, termto, searchType, departmentId, null);
        } else if ("nopass".equals(kind)) {
            list = gradeService.getWithExamIsNotPass(courseSeq, courseName, yearfrom, termfrom,
                    yearto, termto, searchType, departmentId, null);
        } else if ("pjtj".equals(kind)) {
            Long calendarId = getLong(request, "calendar.id");
            Long deparmentid = getLong(request, "evaluateStatics.department.id");
            String taskSeqNo = get(request, "evaluateStatics.taskSeqNo");
            courseName = get(request, "evaluateStatics.course.name");
            String teacherName = get(request, "evaluateStatics.teacher.name");
            String teacherCode = get(request, "evaluateStatics.teacher.code");
            EntityQuery entityQuery = new EntityQuery(EvaluateStatics.class, "evaluateStatics");
            entityQuery.add(new Condition("evaluateStatics.calendar.id=" + calendarId));
            if (null != deparmentid) {
                entityQuery.add(new Condition("evaluateStatics.depart.id=" + deparmentid));
            }
            if (null != taskSeqNo && !"".equals(taskSeqNo)) {
                entityQuery.add(new Condition("evaluateStatics.taskSeqNo='" + taskSeqNo + "'"));
            }
            if (null != courseName && !"".equals(courseName)) {
                entityQuery.add(new Condition("evaluateStatics.course.name='" + courseName + "'"));
            }
            if (null != teacherName && !"".equals(teacherName)) {
                entityQuery
                        .add(new Condition("evaluateStatics.teacher.name='" + teacherName + "'"));
            }
            if (null != teacherCode && !"".equals(teacherCode)) {
                entityQuery
                        .add(new Condition("evaluateStatics.teacher.code='" + teacherCode + "'"));
            }
            entityQuery.setOrders(OrderUtils.parser(get(request, "orderBy")));
            list = utilService.search(entityQuery);
        } else {
            list = Collections.EMPTY_LIST;
        }
        return list;
    }
    
    protected String getExportKeys(HttpServletRequest request) {
        String messageKey = request.getParameter("messageKey");
        if (StringUtils.isNotBlank(messageKey)) {
            MessageResources message = getResources(request, "excelconfig");
            return message.getMessage(getLocale(request), messageKey);
        } else {
            return request.getParameter("keys");
        }
    }
    
    protected String getExportTitles(HttpServletRequest request) {
        String messageTitle = request.getParameter("messageTitle");
        if (StringUtils.isNotBlank(messageTitle)) {
            MessageResources message = getResources(request, "excelconfig");
            return message.getMessage(getLocale(request), messageTitle);
        } else {
            return request.getParameter("titles");
        }
    }
    
    // 以下是负责页面到程序之间参数传递使用的静态方法
    // 全部托管给了RequestUtil和RequestUtils
    /**
     * @param request
     * @param prefix
     * @return
     */
    public static Map getParams(HttpServletRequest request, String prefix) {
        return RequestUtils.getParams(request, prefix, null);
    }
    
    /**
     * 返回request中以prefix.开头的参数
     * 
     * @param request
     * @param prefix
     * @param exclusiveAttrNames
     *            要排除的属性串
     * @return
     */
    public static Map getParams(HttpServletRequest request, String prefix, String exclusiveAttrNames) {
        return RequestUtils.getParams(request, prefix, exclusiveAttrNames);
    }
    
    public static Long getLong(HttpServletRequest request, String name) {
        return RequestUtils.getLong(request, name);
    }
    
    public static String get(HttpServletRequest request, String name) {
        return RequestUtils.get(request, name);
    }
    
    public static Object getAttribute(HttpServletRequest request, String name) {
        return request.getAttribute(name);
    }
    
    public static Integer getInteger(HttpServletRequest request, String name) {
        return RequestUtils.getInteger(request, name);
    }
    
    public static Float getFloat(HttpServletRequest request, String name) {
        return RequestUtils.getFloat(request, name);
    }
    
    public static Boolean getBoolean(HttpServletRequest request, String name) {
        return RequestUtils.getBoolean(request, name);
    }
    
    public static void populate(Map params, Entity entity) {
        if (null == entity) {
            throw new RuntimeException("Cannot populate to null.");
        }
        Model.populator.populate(params, entity);
    }
    
    /**
     * @param request
     * @param clazz
     * @return
     */
    public static Object populate(HttpServletRequest request, Class clazz) {
        return BaseAction.populate(request, clazz);
    }
    
    /**
     * @param request
     * @param clazz
     * @param name
     * @return
     */
    public static Object populate(HttpServletRequest request, Class clazz, String name) {
        return BaseAction.populate(request, clazz, name);
    }
    
    /**
     * @deprecated
     * @param request
     * @param object
     * @param name
     * @return
     */
    public static Object populate(HttpServletRequest request, Object object, String name) {
        return RequestUtil.populate(request, object, name);
    }
    
    public static void populateConditions(HttpServletRequest request, EntityQuery entityQuery) {
        QueryRequestSupport.populateConditions(request, entityQuery);
    }
    
    public static void populateConditions(HttpServletRequest request, EntityQuery entityQuery,
            String exclusiveAttrNames) {
        QueryRequestSupport.populateConditions(request, entityQuery, exclusiveAttrNames);
    }
    
    public static int getPageNo(HttpServletRequest request) {
        return QueryRequestSupport.getPageNo(request);
    }
    
    public static int getPageSize(HttpServletRequest request) {
        return QueryRequestSupport.getPageSize(request);
    }
    
    protected Entity populateEntity(HttpServletRequest request, Class entityClass, String entityName) {
        Map params = getParams(request, entityName);
        Long entityId = getLong(request, entityName + ".id");
        Entity entity = null;
        try {
            if (null == entityId) {
                entity = (Entity) populate(request, entityClass, entityName);
                populate(params, entity);
                EntityUtils.evictEmptyProperty(entity);
            } else {
                entity = (Entity) utilService.get(entityClass, entityId);
                populate(params, entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return entity;
    }
    
    protected Entity getEntity(HttpServletRequest request, Class entityClass, String name) {
        Long entityId = getLong(request, name + ".id");
        if (null == entityId) {
            entityId = getLong(request, name + "Id");
        }
        Entity entity = null;
        try {
            EntityType type = Model.context.getEntityType(entityClass);
            if (null == entityId) {
                entity = (Entity) populate(request, entityClass, name);
            } else {
                entity = (Entity) utilService.get(type.getEntityName(), entityId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return entity;
    }
    
    /**
     * 初始化基础信息列表
     * 
     * @param key
     * @param clazz
     * @deprecated
     */
    protected void initBaseCodes(String key, Class clazz) {
        if (StringUtils.isEmpty(key)) {
            key = "keyList";
        }
        Results.addObject(key, baseCodeService.getCodes(clazz));
    }
    
    /**
     * 初始化基础代码列表
     * 
     * @param request
     * @param key
     * @param clazz
     */
    protected void initBaseCodes(HttpServletRequest request, String key, Class clazz) {
        if (StringUtils.isEmpty(key)) {
            key = "keyList";
        }
        addCollection(request, key, baseCodeService.getCodes(clazz));
    }
    
    /**
     * 在Request中添加简单的页面传值。
     * 
     * @param request
     * @param key
     * @param obj
     */
    protected void addSingleParameter(HttpServletRequest request, String key, Object obj) {
        request.setAttribute(key, obj);
    }
    
    protected void initBaseInfos(HttpServletRequest request, String key, Class clazz) {
        addCollection(request, key, baseInfoService.getBaseInfos(clazz));
    }
    
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }
    
    public void setLogHelper(LogHelper logHelper) {
        this.logHelper = logHelper;
    }
    
    public void setBaseInfoService(BaseInfoService baseInfoService) {
        this.baseInfoService = baseInfoService;
    }
    
    public String getResourceName(HttpServletRequest request) {
        return RequestUtils.getRequestAction(request);
    }
    
    public Resource getResource(HttpServletRequest request) {
        return (Resource) authorityService.getResource(getResourceName(request));
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
}
