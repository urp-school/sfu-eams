//$Id: AuditStandardSearchAction.java,v 1.1 2008-4-9 下午02:08:07 zhouqi Exp $
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
 * zhouqi              2008-4-9         	Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.graduation.audit.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.basecode.industry.AuditType;
import com.shufe.service.graduate.AuditStandardService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * @author zhouqi
 */
public class AuditStandardSearchAction extends RestrictionSupportAction {
    
    protected AuditStandardService auditStandardService;
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 检索毕业审核标准
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        
        DynaActionForm dynaForm = (DynaActionForm) form;
        AuditStandard auditStandard = (AuditStandard) RequestUtil.populate(request,
                AuditStandard.class, "auditStandard");
        int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
        int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();
        Pagination auditStandardList = auditStandardService.searchAuditStandard(auditStandard,
                pageNo, pageSize, departmentIds, studentTypeIds);
        addOldPage(request, "auditStandardList", auditStandardList);
        Results.addObject("auditTypeList", utilService.loadAll(AuditType.class));
        
        return forward(request);
    }
    
    public void setAuditStandardService(AuditStandardService auditStandardService) {
        this.auditStandardService = auditStandardService;
    }
}
