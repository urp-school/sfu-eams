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
 * chaostone             2006-4-2            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.plan;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.plan.TeachPlanArrangeAlteration;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 培养计划确认管理
 * 
 * @author chaostone
 * 
 */
public class TeachPlanConfirmAction extends RestrictionSupportAction {
    
    private TeachPlanService teachPlanService;
    
    /**
     * 培养计划确认管理主界面
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
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 培养计划确认和取消确认
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String planIdSeq = request.getParameter("teachPlanIdSeq");
        if (StringUtils.isEmpty(planIdSeq)) {
            return forwardError(mapping, request, "error.model.ids.needed");
        } else {
            Boolean isConfirm = getBoolean(request, "isConfirm");
            if (null == isConfirm) {
                return forwardError(mapping, request, "error.parameters.needed");
            }
            String stdTypeDataRealm = getStdTypeIdSeq(request);
            String departDataRealm = getDepartmentIdSeq(request);
            List plans = teachPlanService.getTeachPlans(planIdSeq);
            Collection objects = new ArrayList();
            for (Iterator iter = plans.iterator(); iter.hasNext();) {
                TeachPlan plan = (TeachPlan) iter.next();
                if (!DataAuthorityUtil.isInDataRealm("TeachPlan", plan, stdTypeDataRealm,
                        departDataRealm)) {
                    return forwardError(mapping, request, "error.dataRealm.insufficient");
                }
                
                TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                        TeachPlanArrangeAlteration.EDIT);
                alteration.setBeforePlan((TeachPlan) plan.clone());
                alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
                
                plan.setIsConfirm(isConfirm);
                plan.setModifyAt(new Date(System.currentTimeMillis()));
                
                alteration.setAlterationBy(getUser(request));
                alteration.setAlterationFrom(request.getRequestURI() + "?"
                        + request.getQueryString());
                alteration.setAfterPlan(plan);
                objects.add(alteration);
            }
            objects.addAll(plans);
            utilService.saveOrUpdate(objects);
            return redirect(request, new Action(TeachPlanSearchAction.class, "search"),
                    "info.save.success");
        }
    }
    
    /**
     * @param teachPlanService
     *            The teachPlanService to set.
     */
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
}
