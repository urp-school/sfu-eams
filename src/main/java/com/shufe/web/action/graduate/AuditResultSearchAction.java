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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/

package com.shufe.web.action.graduate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.PlanAuditResult;

public class AuditResultSearchAction extends AuditResultSupportAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        initSearchBar(form, request);
        initBaseCodes("studentStateList", StudentState.class);
        EntityQuery query = new EntityQuery(AuditStandard.class, "AuditStandard");
        addCollection(request, "auditStandardList", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        SinglePage rs = (SinglePage) utilService.search(query);
        Pagination stds = new Pagination(query.getLimit().getPageNo(), query.getLimit()
                .getPageSize(), new Result(rs.getTotal(), (List) rs.getPageDatas()));
        addOldPage(request, "studentList", stds);
        Map<String,PlanAuditResult> resultMap = new HashMap<String,PlanAuditResult>();
		if (!stds.getItems().isEmpty()) {
			EntityQuery query2 = new EntityQuery(PlanAuditResult.class, "r");
			query2.add(new Condition("r.std in(:stds)", stds.getItems()));
			Collection resultList = utilService.search(query2);
			for (Object obj : resultList) {
				PlanAuditResult r = (PlanAuditResult) obj;
				resultMap.put(r.getStd().getId().toString(), r);
			}
		}
        request.setAttribute("resultMap", resultMap);
        Long auditStandardId = getLong(request, "auditStandardId");
        request.setAttribute("auditStandardId", auditStandardId);
        return this.forward(request);
    }
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        initSearchBar(form, request);
        initBaseCodes("studentStateList", StudentState.class);
        return this.forward(request);
    }
    
    protected MajorType getMajorType() {
        return new MajorType(MajorType.FIRST);
    }
    
}
