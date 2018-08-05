//$Id: SpecialityAlerationApplicatonAction.java,v 1.1 2008-5-5 上午09:35:14 杨晔 Exp $
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
 * 杨晔              2008-5-5         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.alteration;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.std.changeMajor.ChangeMajorApplication;
import com.ekingstar.eams.std.changeMajor.ChangeMajorPlan;
import com.ekingstar.eams.std.changeMajor.service.ChangeMajorApplicationService;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.std.Student;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 转专业申请
 * 
 */
public class SpecialityAlerationApplicatonAction extends CalendarRestrictionSupportAction {
    
    private ChangeMajorApplicationService changeMajorApplicationService;
    
    private GradePointService gradePointService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "plans", utilService.search(new EntityQuery(ChangeMajorPlan.class,
                "plan")));
        Student std = getStudentFromSession(request.getSession());
        EntityQuery query = new EntityQuery(ChangeMajorApplication.class, "application");
        query.add(new Condition("application.std.id=:stdId", std.getId()));
        query.setLimit(getPageLimit(request));
        addCollection(request, "applications", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long applicationId = getLong(request, "applicationId");
        ChangeMajorPlan plan = null;
        if (null != applicationId) {
            ChangeMajorApplication application = (ChangeMajorApplication) utilService.load(
                    ChangeMajorApplication.class, applicationId);
            request.setAttribute("application", application);
            plan = application.getMajorPlan().getPlan();
        } else {
            Long planId = getLong(request, "planId");
            plan = (ChangeMajorPlan) utilService.load(ChangeMajorPlan.class, planId);
        }
        request.setAttribute("student", getStudentFromSession(request.getSession()));
        request.setAttribute("plan", plan);
        return forward(request);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ChangeMajorApplication application = (ChangeMajorApplication) populateEntity(request,
                ChangeMajorApplication.class, "application");
        ChangeMajorPlan plan = (ChangeMajorPlan) utilService.load(ChangeMajorPlan.class, getLong(
                request, "plan.id"));
        if (!plan.isInTime()) {
            return redirect(request, "index", "info.action.failure");
        }
        if (application.isVO()) {
            Student std = getStudentFromSession(request.getSession());
            EntityQuery query = new EntityQuery(ChangeMajorApplication.class, "application");
            query.add(new Condition("application.std.id=:stdId", std.getId()));
            query.add(new Condition("application.state<>:state", new Integer(5)));
            query.setSelect("count(*)");
            List rs = (List) utilService.search(query);
            if (!rs.isEmpty()) {
                Number count = (Number) rs.get(0);
                if (count.intValue() > 0) {
                    return redirect(request, "index", "info.save.failure");
                }
            }
            application.setStd(std);
            application.setApplyAt(new Date());
            application.setGrade(std.getGrade());
            application.setState(new Integer(0));
            application.setCalendar(plan.getCalendar());
            application.setStdGpa(gradePointService.statStdGPA(std, null, new MajorType(
                    MajorType.FIRST), Boolean.TRUE, Boolean.TRUE));
        }
        utilService.saveOrUpdate(ChangeMajorApplication.class.getName(), application);
        return redirect(request, "index", "info.save.success");
    }
    
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long applicationId = getLong(request, "applicationId");
        ChangeMajorApplication application = (ChangeMajorApplication) utilService.load(
                ChangeMajorApplication.class, applicationId);
        if (changeMajorApplicationService.cancelApplication(application)) {
            return redirect(request, "index", "info.action.success");
        } else {
            return redirect(request, "index", "info.action.failure");
        }
    }
    
    public void setChangeMajorApplicationService(
            ChangeMajorApplicationService changeMajorApplicationService) {
        this.changeMajorApplicationService = changeMajorApplicationService;
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
}
