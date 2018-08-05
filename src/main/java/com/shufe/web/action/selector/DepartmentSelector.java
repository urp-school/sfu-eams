package com.shufe.web.action.selector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.model.system.baseinfo.Department;
import com.shufe.web.action.common.RestrictionSupportAction;

public class DepartmentSelector extends RestrictionSupportAction {
    
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
        
        Results.addObject("depertmentSet", utilService.loadAll(Department.class));
        return this.forward(mapping, request, "success");
    }
}
