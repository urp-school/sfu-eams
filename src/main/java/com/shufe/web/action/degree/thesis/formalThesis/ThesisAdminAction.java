//$Id: ThesisAdminAction.java,v 1.1 2007-5-23 15:27:17 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-5-23         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.formalThesis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import com.shufe.model.degree.thesis.Thesis;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.service.degree.thesis.ThesisManageImportListener;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

public class ThesisAdminAction extends RestrictionSupportAction {
    
    private StdSearchHelper stdSearchHelper;
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "thesiss", utilService.search(buildQuery(request)));
        return forward(request);
    }
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = stdSearchHelper.buildStdQuery(request);
        query.setAlias("thesis");
        query.setSelect("thesis");
        query.setEntityClass(Thesis.class);
        populateConditions(request, query);
        String from = query.getFrom();
        from = "from Thesis  thesis join thesis.student std "
                + StringUtils.substringAfter(from, " std");
        query.setFrom(from);
        return query;
    }
    
    /**
     * 查看详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long thesisId = getLong(request, "thesisId");
        Thesis thesis = (Thesis) utilService.load(Thesis.class, thesisId);
        request.setAttribute("thesis", thesis);
        return forward(request);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward affirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String affirmValue = request.getParameter("affirmValue");
        String thesisIdSeq = request.getParameter("thesisIds");
        Map valueMap = new HashMap();
        valueMap.put("affirm", Boolean.valueOf(affirmValue));
        utilService
                .update(Thesis.class, "id", SeqStringUtil.transformToLong(thesisIdSeq), valueMap);
        return redirect(request, "search", "info.action.success");
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = buildQuery(request);
        entityQuery.setLimit(null);
        return utilService.search(entityQuery);
    }
    
    /**
     * 导入我的论文成绩
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
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, ThesisManage.class,
                tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new ThesisManageImportListener(utilService.getUtilDao()));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success");
        }
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
    
}
