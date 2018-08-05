package com.shufe.web.action.system.rule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.beanfuse.rule.model.BusinessRule;
import org.beanfuse.rule.model.RuleParameter;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.web.action.common.DispatchBasicAction;

public class RuleParameterAction extends DispatchBasicAction {
    
    // 查看所选项目的 成员列表
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long ruleId = getLong(request, "ruleId");
        if ((!"".equals(ruleId)) && (null != ruleId)) {
            EntityQuery query = new EntityQuery(BusinessRule.class, "rule");
            query.add(new Condition(" rule.id=:ruleId", ruleId));
            List ruleList = (List)utilService.search(query);
            BusinessRule rule = (BusinessRule) ruleList.get(0);
            addCollection(request, "ruleParams", rule.getParams());
        }
        request.setAttribute("ruleId", ruleId);
        return forward(request);
    }
    
    // 增加 或者 更新
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long ruleId = getLong(request, "ruleId");
        request.setAttribute("ruleId", ruleId);
        String paramId = request.getParameter("paramId");
        EntityQuery rulParamQuery = new EntityQuery(RuleParameter.class, "ruleParam");
        rulParamQuery.add(new Condition(" ruleParam.rule.id=:ruleId", ruleId));
        if (!"".equals(paramId)){
            rulParamQuery.add(new Condition(" ruleParam.id<>:paramId", paramId));   
        }
        request.setAttribute("ruleParams", utilService.search(new EntityQuery(RuleParameter.class, "ruleParam"))); 
        
        if (!"".equals(paramId)) {
            EntityQuery query = new EntityQuery(RuleParameter.class, "param");
            query.add(new Condition(" param.id=:paramId", Long.valueOf(paramId)));
            List ruleList = (List)utilService.search(query);
            request.setAttribute("param", ruleList.get(0));
        }
        return forward(request);
    }
    
    // 保存
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long ruleId = getLong(request, "ruleId");
        EntityQuery query = new EntityQuery(BusinessRule.class, "rule");
        query.add(new Condition(" rule.id=:ruleId", ruleId));
        List ruleList = (List)utilService.search(query);
        BusinessRule businessRule = (BusinessRule)ruleList.get(0);
        RuleParameter ruleParameter = (RuleParameter) populate(request, RuleParameter.class,
                "param");
        if("".equals(request.getParameter("param.parent.id"))){
            ruleParameter.setParent(null);  
        }
        ruleParameter.setRule(businessRule);
        utilService.saveOrUpdate(ruleParameter);
        return redirect(request, new Action("", "search"), "info.save.success",
                new String[] { "ruleId" });
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String paramIdSeq = request.getParameter("paramIds");
        Long[] paramIds = SeqStringUtil.transformToLong(paramIdSeq);
        utilService.remove(RuleParameter.class, "id", paramIds);
        return redirect(request, new Action("", "search"), "info.save.success",
                new String[] { "ruleId" });
    }
}
