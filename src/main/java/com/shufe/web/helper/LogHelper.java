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
 * zq                   2007-09-18          重载了info()方法
 *  
 ********************************************************************************/
package com.shufe.web.helper;

import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.actions.DispatchAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.security.Authentication;
import com.ekingstar.security.AuthenticationException;
import com.ekingstar.security.User;
import com.shufe.model.system.log.SystemLog;

/**
 * @author zhouqi
 * 
 */
public class LogHelper {
    
    private static final Logger log = LoggerFactory.getLogger(DispatchAction.class);
    
    /** 查询 */
    public static final String SELECT = "SELECT";
    
    /** 插入 */
    public static final String INSERTE = "INSERTE";
    
    /** 更新 */
    public static final String UPDATE = "UPDATE";
    
    /** 删除 */
    public static final String DELETE = "DELETE";
    
    private UtilService utilService;
    
    /**
     * 检查参数
     * 
     * @param request
     * @param actionClass
     * @param id
     * @throws Exception
     */
    private void checkParams(HttpServletRequest request, String content) throws Exception {
        if (request == null || StringUtils.isEmpty(content)) {
            throw new Exception("==> the params of [" + this.getClass().getName()
                    + ".info] of method is exception.");
        }
    }
    
    public User getUserFromSession(HttpSession session) {
        Long userId = (Long) session.getAttribute(Authentication.USERID);
        if (null == userId) {
            throw new AuthenticationException();
        } else {
            return (User) utilService.get(User.class, userId);
        }
    }
    
    /**
     * 记录一个系统日志
     * 
     * @param request
     * @param content
     * @throws Exception
     */
    public void info(HttpServletRequest request, String content) throws Exception {
        checkParams(request, content);
        // 新建一个系统日志
        SystemLog sysLog = new SystemLog();
        sysLog.setUser(getUserFromSession(request.getSession()));
        sysLog.setOperation(StringUtils.abbreviate(content, 200));
        sysLog.setURI(RequestUtils.getRequestAction(request));
        sysLog.setTime(new Timestamp(System.currentTimeMillis()));
        // 操作参数
        StringBuffer params = new StringBuffer("");
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String key = (String) paramNames.nextElement();
            params.append(key).append(" = ");
            params.append(request.getParameter(key)).append("\n");
        }
        sysLog.setParams(StringUtils.abbreviate(params.toString(), 800));
        sysLog.setHost(RequestUtils.getIpAddr(request));
        // 保存
        utilService.saveOrUpdate(sysLog);
    }
    
    public void info(HttpServletRequest request, String content, Exception e) throws Exception {
        info(request, content);
        log.info(content, e);
    }
    
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
}
