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
 * chaostone             2006-3-8            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.selector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.model.Constants;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

public class CollegeSelector extends RestrictionSupportAction {
    
    private DepartmentService departmentService;
    
    /**
     * load user can access depertment at this module
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withAuthority(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Results.addObject("depertmentSet", baseInfoService.getBaseInfos(Department.class));
        return this.forward(mapping, request, "success");
    }
    
    /**
     * 列出所有部门
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withoutAuthority(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Results.addObject("depertmentSet", departmentService.getColleges());
        return this.forward(mapping, request, "success");
    }
    
    /**
     * 查询开科院系
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
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        Department depart = (Department) RequestUtil.populate(request, Department.class,
                Constants.DEPARTMENT);
        depart.setIsCollege(Boolean.TRUE);
        if (StringUtils.isEmpty(pageNo))
            request.setAttribute(Constants.DEPARTMENT_LIST, departmentService
                    .getDepartments(depart));
        else
            Results.addPagination(Constants.DEPARTMENT_LIST, departmentService.getDepartments(
                    depart, NumberUtils.toInt(pageNo), NumberUtils.toInt(pageSize)));
        return forward(mapping, request, "list");
    }
    
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
}
