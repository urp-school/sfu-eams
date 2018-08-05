//$Id: SystemLogAction.java,v 1.1 2007-6-28 下午12:43:31 chaostone Exp $
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
 * chenweixiong              2007-6-28         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.log;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.system.log.SystemLog;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 系统日志管理
 * 
 * @author chaostone
 * 
 */
public class SystemLogAction extends DispatchBasicAction {
    
    /**
     * 系统日志首页
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setAttribute("logDefaultDate", new Date());
        return forward(request);
    }
    
    /**
     * 获取系统日志列表
     * 
     * @param request
     * @param query
     * @throws ParseException
     */
    protected void displaySystemLog(HttpServletRequest request) throws Exception {
        EntityQuery query = buildQuery(request, true);
        Collection sysLogs = utilService.search(query);
        for (Iterator it = sysLogs.iterator(); it.hasNext();) {
            SystemLog sysLog = (SystemLog) it.next();
            try {
                utilService.initialize(sysLog.getUser());
            } catch (RuntimeException e) {
                sysLog.setUser(null);
            }
        }
        addCollection(request, "sysLogs", sysLogs);
    }
    
    /**
     * 收集查询条件
     * 
     * @param request
     * @param isLimited
     * @return
     */
    private EntityQuery buildQuery(HttpServletRequest request, boolean isLimited) {
        EntityQuery query = new EntityQuery(SystemLog.class, "sysLog");
        populateConditions(request, query);
        java.sql.Date beginDate = RequestUtils.getDate(request, "logBeginDate");
        java.sql.Date endDate = RequestUtils.getDate(request, "logEndDate");
        if (null != beginDate && null != endDate) {
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
            query.add(new Condition("sysLog.time >= (:begin) and sysLog.time <= (:end)", beginDate,
                    c.getTime()));
        }
        query.setLimit(isLimited ? getPageLimit(request) : null);
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = "sysLog.id desc";
        }
        query.setOrders(OrderUtils.parser(orderBy));
        return query;
    }
    
    /**
     * 查询系统日志
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        displaySystemLog(request);
        return forward(request);
    }
    
    /**
     * 查看详细日志
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
        request.setAttribute("sysLog", utilService.load(SystemLog.class, getLong(request,
                "sysLogId")));
        return forward(request);
    }
    
    /**
     * 选定清除日志
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] sysLogIds = SeqStringUtil.transformToLong(request.getParameter("sysLogIds"));
        utilService.remove(SystemLog.class, "id", sysLogIds);
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 清空系统日志表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeAll(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        utilService.remove(utilService.loadAll(SystemLog.class));
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 收集导出的数据
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        return utilService.search(buildQuery(request, false));
    }
    
}
