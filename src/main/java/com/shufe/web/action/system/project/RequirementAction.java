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
 * chaostone             2006-11-21            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.project;

import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.system.project.Requirement;
import com.shufe.model.system.project.RequirementPropertyExtractor;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 系统需求响应类
 * 
 * @author chaostone
 */
public class RequirementAction extends DispatchBasicAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 查询
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
        request.setAttribute("usingPagination", Boolean.TRUE);
        addCollection(request, "requires", getExportDatas(request));
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long requireId = getLong(request, "requirement.id");
        if (ValidEntityKeyPredicate.INSTANCE.evaluate(requireId)) {
            request.setAttribute("require", utilService.get(Requirement.class, requireId));
        } else {
            request.setAttribute("require", RequestUtil.populate(request, Requirement.class,
                    "require", true));
        }
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long requireId = getLong(request, "require.id");
        Requirement requirement = new Requirement();
        if (null != requireId) {
            requirement = (Requirement) utilService.get(Requirement.class, requireId);
        } else {
            requirement.setCreatedOn(new Date(System.currentTimeMillis()));
        }
        requirement.setLastModifiedOn(new Date(System.currentTimeMillis()));
        populate(getParams(request, "require"), requirement);
        utilService.saveOrUpdate(requirement);
        if (null != request.getParameter("addAnother")) {
            return redirect(request, "edit", "info.save.success");
        } else {
            return redirect(request, "search", "info.save.success");
        }
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String requireIds = request.getParameter("requireIds");
        Collection requires = utilService.load(Requirement.class, "id", SeqStringUtil
                .transformToLong(requireIds));
        for (Iterator iter = requires.iterator(); iter.hasNext();) {
            Requirement element = (Requirement) iter.next();
            utilService.remove(element);
        }
        return redirect(request, "search", "info.save.success");
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long requireId = getLong(request, "requirement.id");
        request.setAttribute("require", utilService.get(Requirement.class, requireId));
        return forward(request);
    }
    
    /**
     * 打印需求确认单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "requires", getExportDatas(request));
        return forward(request);
    }
    
    /**
     * 打印预览（全部）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward preview(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        query.getOrders().clear();
        query.addOrder(OrderUtils.parser("require.status, require.module"));
        query.setLimit(null);
        addCollection(request, "requires", utilService.search(query));
        
        query.setQueryStr(null);
        query.getOrders().clear();
        query.setSelect("distinct require.status");
        List list = (List) utilService.search(query);
        request.setAttribute("requireStatue", list);
        return forward(request);
    }
    
    public ActionForward workload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requireIds = request.getParameter("requireIds");
        Collection requires = utilService.load(Requirement.class, "id", SeqStringUtil
                .transformToLong(requireIds));
        float workload = 0;
        for (Iterator iter = requires.iterator(); iter.hasNext();) {
            Requirement element = (Requirement) iter.next();
            if (null != element.getWorkload()) {
                workload += element.getWorkload().floatValue();
            }
        }
        request.setAttribute("requires", requires);
        request.setAttribute("workload", new Float(workload));
        return forward(request);
    }
    
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        return new RequirementPropertyExtractor();
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = buildQuery(request);
        return utilService.search(entityQuery);
    }
    
    /**
     * 组建查询条件
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Requirement.class, "require");
        String requireIdSeq = request.getParameter("requireIds");
        if (StringUtils.isNotEmpty(requireIdSeq)) {
            entityQuery
                    .add(new Condition("instr('," + requireIdSeq + ",',','||require.id||',')>0"));
        } else {
            QueryRequestSupport.populateConditions(request, entityQuery);
            if (null != request.getAttribute("usingPagination")) {
                entityQuery.setLimit(getPageLimit(request));
            }
            entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
            Date planCompleteOnFrom = (Date) RequestUtils.get(request, Date.class,
                    "planCompleteOn.from");
            if (null != planCompleteOnFrom) {
                entityQuery.add(new Condition("require.planCompleteOn >= :planCompleteOnFrom",
                        planCompleteOnFrom));
            }
            Date actualCompleteOnFrom = (Date) RequestUtils.get(request, Date.class,
                    "actualCompleteOn.from");
            if (null != actualCompleteOnFrom) {
                entityQuery.add(new Condition("require.actualCompleteOn >= :actualCompleteOnFrom",
                        actualCompleteOnFrom));
            }
            Date createdOnFrom = (Date) RequestUtils.get(request, Date.class, "createdOn.from");
            if (null != createdOnFrom) {
                entityQuery
                        .add(new Condition("require.createdOn >= :createdOnFrom", createdOnFrom));
            }
            Date planCompleteOnTo = (Date) RequestUtils.get(request, Date.class,
                    "planCompleteOn.to");
            if (null != planCompleteOnTo) {
                entityQuery.add(new Condition("require.planCompleteOn <= :planCompleteOnTo",
                        planCompleteOnTo));
            }
            Date actualCompleteOnTo = (Date) RequestUtils.get(request, Date.class,
                    "actualCompleteOn.to");
            if (null != actualCompleteOnTo) {
                entityQuery.add(new Condition("require.actualCompleteOn <= :actualCompleteOnTo",
                        actualCompleteOnTo));
            }
            Date createdOnTo = (Date) RequestUtils.get(request, Date.class, "createdOn.to");
            if (null != createdOnTo) {
                entityQuery.add(new Condition("require.createdOn <= :createdOnTo", createdOnTo));
            }
        }
        return entityQuery;
    }
    
}
