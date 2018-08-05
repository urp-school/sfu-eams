//$Id: DegreeAuditAction.java,v 1.1 2007-4-11 下午09:35:56 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-11         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.graduation.audit.web.action;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.shufe.model.std.Student;
import com.shufe.service.std.graduation.audit.AuditResultImportListener;
import com.shufe.service.system.calendar.TeachCalendarService;

/**
 * 学位审核响应类
 * 
 * @author chaostone
 */
public class DegreeAuditAction extends AuditResultSearchAction {
    
    protected TeachCalendarService teachCalendarService;
    
    /**
     * 进行学位审核
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        DegreeAuditStandard standard = (DegreeAuditStandard) utilService.get(
                DegreeAuditStandard.class, getLong(request, "standard.id"));
        
        String stdIds = request.getParameter("stdIds");
        
        if (StringUtils.isNotEmpty(stdIds)) {
            List stds = utilService
                    .load(Student.class, "id", SeqStringUtil.transformToLong(stdIds));
            degreeAuditService.audit(stds, standard);
        } else {
            String auditResultIds = request.getParameter("auditResultIds");
            List auditResults = utilService.load(AuditResult.class, "id", SeqStringUtil
                    .transformToLong(auditResultIds));
            degreeAuditService.reAudit(auditResults, standard);
        }
        return redirect(request, "search", "info.action.success");
    }
    
    /**
     * 导入学生学历和学位证书号码
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, AuditResult.class,
                tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new AuditResultImportListener(utilService.getUtilDao()));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "index", "info.import.success");
        }
    }
    
    /**
     * 导出未通过审核的学生列表
     * 
     * @see com.ekingstar.eams.std.graduation.audit.web.action.AuditResultSearchAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        Boolean isAudit = RequestUtils.getBoolean(request, "isAudit");
        if (isAudit == null) {
            isAudit = Boolean.FALSE;
        }
        EntityQuery query = buildQuery(request, isAudit);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
}
