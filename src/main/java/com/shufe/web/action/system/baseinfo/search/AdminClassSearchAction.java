//$Id: AdminClassSearchAction.java,v 1.1 2008-4-8 下午02:56:44 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-4-8         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo.search;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.web.action.system.baseinfo.BaseInfoAction;

/**
 * @author zhouqi
 */
public class AdminClassSearchAction extends BaseInfoAction {
    
    protected AdminClassService adminClassService;
    
    /**
     * 班级管理主界面
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
        prepare(request);
        return forward(request);
    }
    
    /**
     * 准备数据
     */
    protected void prepare(HttpServletRequest request) {
        addCollection(request, "departmentList", getTeachDeparts(request));
        addCollection(request, "stdTypeList", getStdTypes(request));
    }
    
    /**
     * 查找信息action.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        addCollection(request, "adminClasses", baseInfoSearchHelper.searchAdminClass(request));
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = baseInfoSearchHelper.buildAdminClassQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    /**
     * 查看班级信息
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
        Long adminClassId = getLong(request, "adminClass.id");
        if (null == adminClassId) {
            adminClassId = getLong(request, "adminClassId");
        }
        
        if (null == adminClassId)
            return forward(mapping, request, new String[] { "entity.adminClass",
                    "error.model.notExist" }, "error");
        AdminClass adminClass = adminClassService.getAdminClass(adminClassId);
        addEntity(request, adminClass);
        return forward(request);
    }
    
    /**
     * @param adminClassService The adminClassService to set.
     */
    public void setAdminClassService(AdminClassService adminClassService) {
        this.adminClassService = adminClassService;
    }
}
