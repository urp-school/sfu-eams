package com.shufe.web.action.system.rule;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.beanfuse.rule.model.BusinessRule;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.web.action.common.DispatchBasicAction;

public class RuleAction extends DispatchBasicAction {
 
    /**
     * 主页面
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
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(BusinessRule.class, "rule");
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "rules", utilService.search(query));
        return forward(request);
    }
    /**
     * 添加/修改规则
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String ruleId = request
                .getParameter("ruleId");
        if (!"".equals(ruleId)) {
            /*BusinessRule rule = (BusinessRule) utilService
                    .load(BusinessRule.class, Long.valueOf(ruleId));*/
            EntityQuery query = new EntityQuery(BusinessRule.class, "rule");
            query.add(new Condition(" rule.id=:ruleId", Long.valueOf(ruleId)));
            List ruleList = (List)utilService.search(query);
            request.setAttribute("rule", ruleList.get(0));
        }
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        BusinessRule businessRule = (BusinessRule) populate(request, BusinessRule.class, "rule");
        if (null == businessRule.getId()) {
            businessRule.setCreatedAt(new Date(System.currentTimeMillis()));
            businessRule.setUpdatedAt(new Date(System.currentTimeMillis()));
        } else {
            businessRule.setUpdatedAt(new Date(System.currentTimeMillis()));
        }
        utilService.saveOrUpdate(businessRule);
        return redirect(request, new Action("", "search"), "info.save.success");
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String ruleIdSeq = request.getParameter("ruleIds");
        Long[] ruleIds = SeqStringUtil.transformToLong(ruleIdSeq);
        utilService.remove(BusinessRule.class, "id", ruleIds);
        return redirect(request, new Action("", "search"), "info.delete.success");
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
        Long ruleId = getLong(request, "ruleId");
        EntityQuery query = new EntityQuery(BusinessRule.class, "rule");
        query.add(new Condition(" rule.id=:ruleId", ruleId));
        List ruleList = (List)utilService.search(query);
        request.setAttribute("ruleList", ruleList);
        return forward(request);
    }
}
