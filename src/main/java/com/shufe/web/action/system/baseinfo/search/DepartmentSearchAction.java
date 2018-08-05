//$Id: DepartmentSearchAction.java,v 1.1 2008-4-8 上午08:54:11 zhouqi Exp $
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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.web.action.system.baseinfo.BaseInfoAction;

/**
 * 部门、系统查询
 * 
 * @author zhouqi
 */
public class DepartmentSearchAction extends BaseInfoAction {
    
    protected DepartmentService departmentService;
    
    /**
     * 部门管理主界面
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
        return forward(request);
    }
    
    /**
     * 部门、系统浏览
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        addCollection(request, "departments", utilService.search(baseInfoSearchHelper
                .buildDepartmentQuery(request)));
        return forward(request);
    }
    
    /**
     * 列表导出
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = baseInfoSearchHelper.buildDepartmentQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    /**
     * 查看某部门的详细信息
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
        String departmentId = request.getParameter("department.id");
        if (StringUtils.isEmpty(departmentId)) {
            departmentId = get(request, "departmentId");
        }
        if (StringUtils.isEmpty(departmentId)) {
            return forward(mapping, request, new String[] { "entity.department",
                    "error.model.id.needed" }, "error");
        }
        Department department = departmentService.getDepartment(Long.valueOf(departmentId));
        addEntity(request, department);
        return forward(request);
    }
    
    /**
     * @param departmentService The departmentService to set.
     */
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}
