//$Id: SpecialityAlerationAction.java,v 1.1 2008-5-4 上午11:01:04 杨晔 Exp $
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
 * @author 杨晔
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * 杨晔                  2008-5-4         	Created
 *  
 ********************************************************************************/

/**
 * 
 */
package com.shufe.web.action.std.alteration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.std.changeMajor.ChangeMajorApplication;
import com.ekingstar.eams.std.changeMajor.ChangeMajorPlan;
import com.ekingstar.eams.std.changeMajor.MajorPlan;
import com.ekingstar.eams.std.changeMajor.service.ChangeMajorApplicationService;
import com.ekingstar.eams.std.changeMajor.service.ChangeMajorPlanService;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.time.TeachCalendar;
import com.shufe.model.std.Student;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 转专业审核
 * 
 */
public class SpecialityAlerationAuditAction extends CalendarRestrictionSupportAction {
    
    public ChangeMajorApplicationService changeMajorApplicationService;
    
    public ChangeMajorPlanService changeMajorPlanService;
    
    private GradePointService gradePointService;
    
    public void setChangeMajorPlanService(ChangeMajorPlanService changeMajorPlanService) {
        this.changeMajorPlanService = changeMajorPlanService;
    }
    
    public void setChangeMajorApplicationService(
            ChangeMajorApplicationService changeMajorApplicationService) {
        this.changeMajorApplicationService = changeMajorApplicationService;
    }
    
    /**
     * 查询学生申请列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward planInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(ChangeMajorPlan.class, "plan");
        Long calendarId = getLong(request, "calendar.id");
        if (null != calendarId) {
            TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(calendarId);
            query.add(new Condition("plan.calendar =:calendar", teachCalendar));
        }
        populateConditions(request, query);
        Collection plans = utilService.search(query);
        if (!plans.isEmpty()) {
            request.setAttribute("plan", plans.iterator().next());
        }
        return forward(request);
    }
    
    public ActionForward applicationList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long majorPlanId = getLong(request, "majorPlanId");
        MajorPlan mpc = (MajorPlan) utilService.get(MajorPlan.class, majorPlanId);
        EntityQuery query = new EntityQuery(ChangeMajorApplication.class, "application");
        query.add(new Condition("application.majorPlan.id =:id", mpc.getId()));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        query.setLimit(getPageLimit(request));
        request.setAttribute("majorPlan", mpc);
        addCollection(request, "applications", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 审核
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
        String applicationIdSeq = get(request, "applicationIds");
        Integer state = getInteger(request, "state");
        List applications = utilService.load(ChangeMajorApplication.class, "id", SeqStringUtil
                .transformToLong(applicationIdSeq));
        for (Iterator iter = applications.iterator(); iter.hasNext();) {
            ChangeMajorApplication app = (ChangeMajorApplication) iter.next();
            app.setState(state);
        }
        utilService.saveOrUpdate(applications);
        return redirect(request, "applicationList", "info.action.success");
    }
    
    public ActionForward editPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChangeMajorPlan plan = (ChangeMajorPlan) populateEntity(request, ChangeMajorPlan.class,
                "plan");
        if (null == plan.getCalendar()) {
            plan
                    .setCalendar(teachCalendarService.getTeachCalendar(getLong(request,
                            "calendar.id")));
        }
        request.setAttribute("plan", plan);
        return forward(request);
    }
    
    public ActionForward savePlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChangeMajorPlan plan = (ChangeMajorPlan) populateEntity(request, ChangeMajorPlan.class,
                "plan");
        utilService.saveOrUpdate(ChangeMajorPlan.class.getName(), plan);
        return redirect(request, "editPlan", "info.save.success", "&plan.id=" + plan.getEntityId());
    }
    
    public ActionForward editNewPlanCount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChangeMajorPlan plan = (ChangeMajorPlan) populateEntity(request, ChangeMajorPlan.class,
                "plan");
        request.setAttribute("majorFields", changeMajorPlanService.getAddibleAspects(plan));
        request.setAttribute("plan", plan);
        return forward(request);
    }
    
    public ActionForward editPlanCount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChangeMajorPlan plan = (ChangeMajorPlan) populateEntity(request, ChangeMajorPlan.class,
                "plan");
        request.setAttribute("planCounts", plan.getMajorPlans());
        request.setAttribute("plan", plan);
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        Long[] ids = SeqStringUtil.transformToLong(get(request, "majorPlanIds"));
        EntityQuery query = new EntityQuery(ChangeMajorApplication.class, "application");
        query.add(new Condition("application.majorPlan.id in (:ids)", ids));
        return utilService.search(query);
    }
    
    public ActionForward savePlanCount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int count = getInteger(request, "count").intValue();
        Long planId = getLong(request, "plan.id");
        List tobeSave = new ArrayList();
        ChangeMajorPlan plan = (ChangeMajorPlan) utilService.load(ChangeMajorPlan.class, planId);
        for (int i = 0; i < count; i++) {
            MajorPlan planCount = (MajorPlan) populateEntity(request, MajorPlan.class, "planCount"
                    + i);
            planCount.setPlan(plan);
            if (null != planCount.getPlanCount()) {
                tobeSave.add(planCount);
            } else {
                if (planCount.isPO()) {
                    plan.getMajorPlans().remove(planCount);
                }
            }
        }
        utilService.saveOrUpdate(MajorPlan.class.getName(), tobeSave);
        utilService.saveOrUpdate(plan);
        return redirect(request, "planInfo", "info.save.success", "&plan.id=" + planId);
    }
    
    public ActionForward refurbishGPA(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] applicationIds = SeqStringUtil.transformToLong(get(request, "applicationIds"));
        List applicationList = (List) utilService.load(ChangeMajorApplication.class, "id",
                applicationIds);
        for (Iterator it = applicationList.iterator(); it.hasNext();) {
            ChangeMajorApplication application = (ChangeMajorApplication) it.next();
            application.setStdGpa(gradePointService.statStdGPA((Student) application.getStd(),
                    null, new MajorType(MajorType.FIRST), Boolean.TRUE, Boolean.TRUE));
        }
        utilService.saveOrUpdate(applicationList);
        return redirect(request, "applicationList", "info.action.success");
    }
    
    public GradePointService getGradePointService() {
        return gradePointService;
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
}
