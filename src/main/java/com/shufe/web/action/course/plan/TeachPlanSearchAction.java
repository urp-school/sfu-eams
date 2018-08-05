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
 * chaostone             2006-3-26            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.plan;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.Constants;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 培养计划查询
 * 
 * @author chaostone
 * 
 */
public class TeachPlanSearchAction extends RestrictionSupportAction {
    
    protected TeachPlanService teachPlanService;
    
    /**
     * 查询单个培养计划的信息
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
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        if (null == planId) {
            return forwardError(mapping, request, "error.model.id.needed");
        } else {
            TeachPlan plan = teachPlanService.getTeachPlan(planId);
            request.setAttribute(Constants.TEACHPLAN, plan);
        }
        Boolean toXLS = getBoolean(request, "toXLS");
        if (Boolean.TRUE.equals(toXLS)) {
            response.setContentType("application/vnd.ms-excel;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename=plan.xls");
        }
        request.setAttribute("school", getSystemConfig().getSchool());
        return forward(request);
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdPlanInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student std = this.getStudentFromSession(request.getSession());
        request.setAttribute(Constants.TEACHPLAN_LIST, teachPlanService.getTeachPlans(std));
        request.setAttribute("school", getSystemConfig().getSchool());
        return forward(request);
    }
    
    /**
     * 培养计划查询主界面
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
        request.setAttribute("stdTypeList", baseInfoService.getBaseInfos(StudentType.class));
        request.setAttribute(Constants.DEPARTMENT_LIST, departmentService.getColleges());
        return forward(request);
    }
    
    /**
     * 返回查询列表
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
        addCollection(request, "teachPlans", utilService.search(buildQuery(request)));
        return forward(request);
    }
    
    /**
     * 组建培养计划查询条件
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(TeachPlan.class, "teachPlan");
        populateConditions(request, query, "teachPlan.stdType.id,teachPlan.speciality.majorType.id");
        // 没有专业的算作第一专业
        Long majorTypeId = getLong(request, "teachPlan.speciality.majorType.id");
        if (null != majorTypeId) {
            if (MajorType.SECOND.equals(majorTypeId)) {
                query.add(new Condition("teachPlan.speciality.majorType.id=:majorTypeId",
                        majorTypeId));
            } else {
                query
                        .add(new Condition(
                                "teachPlan.speciality is null or teachPlan.speciality.majorType.id=:majorTypeId",
                                majorTypeId));
            }
        }
        String planType = request.getParameter("type");
        if (StringUtils.isNotEmpty(planType) && planType.equals("std")) {
            query.add(new Condition("teachPlan.std is not null"));
        } else {
            query.add(new Condition("teachPlan.std is null"));
        }
        Boolean withAuthority = getBoolean(request, "withAuthority");
        query.setLimit(getPageLimit(request));
        // 针对学生类别进行跨类别查询
        Long stdTypeId = getLong(request, "teachPlan.stdType.id");
        if (withAuthority != null && withAuthority.equals(Boolean.TRUE)) {
            DataRealmUtils.addDataRealms(query, new String[] { "teachPlan.stdType.id",
                    "teachPlan.department.id" }, getDataRealmsWith(stdTypeId, request));
        } else {
            if (null != stdTypeId) {
                restrictionHelper.addStdTypeTreeDataRealm(query, "teachPlan.stdType.id", request,
                        null);
            }
        }
        
        List orders = OrderUtils.parser(request.getParameter("orderBy"));
        if (orders.isEmpty()) {
            orders = new ArrayList();
            orders.add(new Order("teachPlan.enrollTurn desc"));
            orders.add(new Order("teachPlan.stdType"));
            orders.add(new Order("teachPlan.department"));
            orders.add(new Order("teachPlan.speciality"));
            orders.add(new Order("teachPlan.aspect"));
        }
        query.join("left", "teachPlan.speciality", "speciality");
        
        query.addOrder(orders);
        return query;
    }
    
    /**
     * @param teachPlanService
     *            The teachPlanService to set.
     */
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
}
