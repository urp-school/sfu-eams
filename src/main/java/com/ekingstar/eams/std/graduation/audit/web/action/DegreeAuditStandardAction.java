// $Id: DegreeAuditStandardAction.java,v 1.1 2007-4-9 上午10:56:38 chaostone Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is
 * intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source,
 * or other redistribution of this source is not permitted without written
 * permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/*******************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description
 * ============         ============        ============
 * chaostone            2007-04-09          Created
 * zq                   2007-09-18          重载buildQuery()方法
 ******************************************************************************/

package com.ekingstar.eams.std.graduation.audit.web.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.beanfuse.rule.model.BusinessRule;
import org.beanfuse.rule.model.RuleConfig;
import org.beanfuse.rule.model.RuleConfigParam;
import org.beanfuse.rule.model.RuleParameter;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.ekingstar.eams.system.basecode.industry.PunishmentType;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * 学位审核标准响应类
 * 
 * @author chaostone
 */
public class DegreeAuditStandardAction extends RestrictionExampleTemplateAction {
    
    protected void indexSetting(HttpServletRequest request) {
        setDataRealm(request, hasStdType);
    }
    
    /**
     * 增加/修改标准第一步
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward ruleConfig_1st(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long standardId = getLong(request, "standardId");
        if (null != standardId) {
            request
                    .setAttribute("standard", utilService
                            .get(DegreeAuditStandard.class, standardId));
        }
        request.setAttribute("stdTypes", getStdTypes(request));
        return forward(request);
    }
    
    /**
     * 增加/修改标准第二步
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward ruleConfig_2nd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DegreeAuditStandard degreeAuditStandard = (DegreeAuditStandard) populateEntity(request,
                entityClass, "standard");
        EntityQuery query = new EntityQuery(BusinessRule.class, "rule");
        query.add(new Condition("rule.business = :business", "degreeAudit"));
        query.add(new Condition("rule.enabled = :enabled", Boolean.TRUE));
        List ruleList = (List) utilService.search(query);
        if (null == degreeAuditStandard.getId()) {
            for (Iterator iter = ruleList.iterator(); iter.hasNext();) {
                BusinessRule rule = (BusinessRule) iter.next();
                RuleConfig ruleConfig = new RuleConfig();
                ruleConfig.setRule(rule);
                ruleConfig.setEnabled(Boolean.FALSE.booleanValue());
                ruleConfig.setCreatedAt(new Date(System.currentTimeMillis()));
                ruleConfig.setUpdatedAt(new Date(System.currentTimeMillis()));
                
                for (Iterator iterator = rule.getParams().iterator(); iterator.hasNext();) {
                    RuleParameter ruleParam = (RuleParameter) iterator.next();
                    RuleConfigParam ruleConfigParam = new RuleConfigParam();
                    ruleConfigParam.setParam(ruleParam);
                    ruleConfigParam.setRuleConfig(ruleConfig);
                    ruleConfigParam.setValue("");
                    ruleConfig.getParams().add(ruleConfigParam);              
                }
                utilService.saveOrUpdate(ruleConfig);
                degreeAuditStandard.getRuleConfigs().add(ruleConfig);
            }
        }else{
        	if(degreeAuditStandard.getRuleConfigs().size()==0){
        		for (Iterator iter = ruleList.iterator(); iter.hasNext();) {
                    BusinessRule rule = (BusinessRule) iter.next();
                    RuleConfig ruleConfig = new RuleConfig();
                    ruleConfig.setRule(rule);
                    ruleConfig.setEnabled(Boolean.FALSE.booleanValue());
                    ruleConfig.setCreatedAt(new Date(System.currentTimeMillis()));
                    ruleConfig.setUpdatedAt(new Date(System.currentTimeMillis()));
                    
                    for (Iterator iterator = rule.getParams().iterator(); iterator.hasNext();) {
                        RuleParameter ruleParam = (RuleParameter) iterator.next();
                        RuleConfigParam ruleConfigParam = new RuleConfigParam();
                        ruleConfigParam.setParam(ruleParam);
                        ruleConfigParam.setRuleConfig(ruleConfig);
                        ruleConfigParam.setValue("");
                        ruleConfig.getParams().add(ruleConfigParam);              
                    }
                    utilService.saveOrUpdate(ruleConfig);
                    degreeAuditStandard.getRuleConfigs().add(ruleConfig);
                }
        	}
        }
        utilService.saveOrUpdate(degreeAuditStandard);
        request.setAttribute("standard", degreeAuditStandard);
        return forward(request);
    }
    
    /**
     * 增加/修改标准第三步
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward ruleConfig_3rd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long standardId = getLong(request, "standard.id");
        String ruleConfigIdSeq = "," + (String)request.getParameter("ruleConfigIds") + ",";
        DegreeAuditStandard degreeAuditStandard = (DegreeAuditStandard) utilService.get(
                DegreeAuditStandard.class, standardId);
        for (Iterator iter = degreeAuditStandard.getRuleConfigs().iterator(); iter.hasNext();) {
            RuleConfig ruleConfig = (RuleConfig) iter.next();
            if (StringUtils.contains(ruleConfigIdSeq, "," + ruleConfig.getId().toString() + ",")) {
                ruleConfig.setEnabled(Boolean.TRUE.booleanValue());
            } else {
                ruleConfig.setEnabled(Boolean.FALSE.booleanValue());
            }
            ruleConfig.setUpdatedAt(new Date(System.currentTimeMillis()));
            utilService.saveOrUpdate(ruleConfig);
        }
        utilService.saveOrUpdate(degreeAuditStandard);
        request.setAttribute("otherExamCategories", baseCodeService
                .getCodes(OtherExamCategory.class));
        request.setAttribute("punishTypes", baseCodeService.getCodes(PunishmentType.class));
        request.setAttribute("standard", degreeAuditStandard);
        return forward(request);
    }
    
    /**
     * 保存参数值
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveRuleConfigParam(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long standardId = getLong(request, "standard.id");
        DegreeAuditStandard degreeAuditStandard = (DegreeAuditStandard) utilService.get(
                DegreeAuditStandard.class, standardId);
        for (Iterator iter = degreeAuditStandard.getRuleConfigs().iterator(); iter.hasNext();) {
            RuleConfig ruleConfig = (RuleConfig) iter.next();
            for (Iterator iterator = ruleConfig.getParams().iterator(); iterator.hasNext();) {
                RuleConfigParam param = (RuleConfigParam) iterator.next();
                String paramValue = request.getParameter("value_" + param.getId());        
                param.setValue(paramValue);
                ruleConfig.getParams().add(param);
            }
            utilService.saveOrUpdate(ruleConfig);
        }
        utilService.saveOrUpdate(degreeAuditStandard);
        return redirect(request, "search", "info.save.success");
    }
    
    protected void editSetting(HttpServletRequest request, Entity entity) {
        request.setAttribute("stdTypes", getStdTypes(request));
        request.setAttribute("otherExamCategories", baseCodeService
                .getCodes(OtherExamCategory.class));
        request.setAttribute("punishTypes", baseCodeService.getCodes(PunishmentType.class));
    }
    
    /**
     * 重构学生查询条件
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(DegreeAuditStandard.class, "standard");
        populateConditions(request, query, "standard.stdType.id");
        DataRealmUtils.addDataRealms(query, new String[] { "standard.stdType.id", "" },
                getDataRealmsWith(getLong(request, "standard.stdType.id"), request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        return query;
    }
    
    /**
     * 查看标准信息
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
        Long entityId = getLong(request, entityName + "Id");
        Entity entity = (Entity) utilService.get(entityClass, entityId);
        request.setAttribute(entityName, entity);
        request.setAttribute("punishTypes", baseCodeService.getCodes(PunishmentType.class));
        request.setAttribute("otherExamCategories", baseCodeService
                .getCodes(OtherExamCategory.class));
        return forward(request);
    }
    
}
