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
 * chaostone             2006-3-31            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.plan;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.Constants;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.plan.TeachPlanArrangeAlteration;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.course.plan.PlanCourseService;
import com.shufe.util.RequestUtil;

/**
 * 课程组内课程的维护响应类
 * 
 * @author chaostone
 * 
 */
public class PlanCourseAction extends PlanUpdateAction {
    
    /**
     * 添加培养计划中的课程
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        getTeachPlanAndGroupFromReqeust(request);
        
        TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(
                TeachPlanArrangeAlteration.EDIT);
        TeachPlan tPlan = (TeachPlan) plan.clone();
        Collection c11 = new ArrayList();
        c11.addAll(tPlan.getPlanCourses());
        tPlan.setIsConfirm(plan.getIsConfirm());
        tPlan.setStd(plan.getStd());
        alteration.setBeforePlan(tPlan);
        
        alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
        alteration.setAlterationBy(getUser(request));
        alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());
        
        PlanCourse planCourse = new PlanCourse();
        RequestUtil.populate(request, planCourse, Constants.PLANCOURSE);
        PlanCourse tPlanCourse = new PlanCourse();
        if (null == planCourse.getId()) {
            PropertyUtils.copyProperties(tPlanCourse, planCourse);
        } else {
            PropertyUtils.copyProperties(tPlanCourse, (PlanCourse) utilService.load(
                    PlanCourse.class, planCourse.getId()));
        }
        // EntityUtils.evictEmptyProperty(planCourse);
        // re calculate credit per term
        Boolean autoStatCredit = getBoolean(request, "autoStatCredit");
        boolean autoStat = (null != autoStatCredit) ? autoStatCredit.booleanValue() : false;
        
        String extra = "&courseGroup.id=" + group.getId() + "&teachPlan.id=" + plan.getId();
        
        if (planCourse.isPO()) {
            planCourseService.updatePlanCourse(planCourse, plan, autoStat);
        } else {
            if (plan.hasCourse(planCourse.getCourse())) {
                return redirect(request, new Action(CourseGroupAction.class, "edit"),
                        "teachPlan.duplicate.course", extra);
            }
            planCourseService.addPlanCourse(planCourse, plan, autoStat);
        }
        Collection c22 = new ArrayList();
        c22.addAll(plan.getPlanCourses());
        
        Collection c11c22 = CollectionUtils.subtract(c11, c22);
        Collection c22c11 = CollectionUtils.subtract(c22, c11);
        
        boolean hasC11 = CollectionUtils.isNotEmpty(c11c22);
        boolean hasC22 = CollectionUtils.isNotEmpty(c22c11);
        boolean isNotEquals = !planCourseService.isEquals(tPlanCourse, planCourse);
        
        StringBuffer content = new StringBuffer();
        content.append(TeachPlanArrangeAlteration.PLANCOURSES + ":");
        Course course = (Course) utilService.load(Course.class, planCourse.getCourse().getId());
        content.append(course.getName() + "=" + course.getCode());
        content.append(",,"
                + (isNotEquals && !hasC11 && hasC22 ? TeachPlanArrangeAlteration.BEEN_ADDED
                        : TeachPlanArrangeAlteration.BEEN_MODIFYED) + ";");
        alteration.setDescription(content.toString());
        
        alteration.setIsModifyCourse(new Boolean(isNotEquals || c11.size() != c22.size() || hasC11
                || hasC22));
        alteration.setAfterPlan(plan);
        utilService.saveOrUpdate(alteration);
        
        return redirect(request, new Action(CourseGroupAction.class, "edit"), "info.save.success",
                extra);
    }
    
    /**
     * 删除培养计划中的课程
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getTeachPlanAndGroupFromReqeust(request);

		TeachPlan tPlan = (TeachPlan) plan.clone();
		Collection c11 = new ArrayList();
		c11.addAll(tPlan.getPlanCourses());
		tPlan.setIsConfirm(plan.getIsConfirm());
		tPlan.setStd(plan.getStd());

		Collection c22 = new ArrayList();
		c22.addAll(plan.getPlanCourses());

		// Long planCourseId = getLong(request, "planCourse.id");
		String[] planCourseIds = get(request, "planCourse.id").split(",");
		for (String planCourseIdstr : planCourseIds) {
			Long planCourseId = Long.parseLong(planCourseIdstr);

			TeachPlanArrangeAlteration alteration = new TeachPlanArrangeAlteration(TeachPlanArrangeAlteration.EDIT);
			alteration.setBeforePlan(tPlan);
			alteration.getBeforePlanInfo().setIsConfirm(plan.getIsConfirm());
			alteration.setAlterationBy(getUser(request));
			alteration.setAlterationFrom(request.getRequestURI() + "?" + request.getQueryString());

			PlanCourse planCourse = (PlanCourse) utilService.load(PlanCourse.class, planCourseId);

			// re calculate credit per term
			Boolean autoStatCredit = getBoolean(request, "autoStatCredit");
			planCourseService.removePlanCourse(planCourse, plan,
					(null != autoStatCredit) ? autoStatCredit.booleanValue() : true);

			StringBuffer content = new StringBuffer();
			content.append(TeachPlanArrangeAlteration.PLANCOURSES + ":");
			content.append(planCourse.getCourse().getName() + "=" + planCourse.getCourse().getCode());
			content.append(",," + TeachPlanArrangeAlteration.BEEN_REMOVED + ";");
			alteration.setDescription(content.toString());
			alteration.setIsModifyCourse(new Boolean(c11.size() != c22.size()));
			alteration.setAfterPlan(plan);
			utilService.saveOrUpdate(alteration);
		}

		String extra = "&courseGroup.id=" + group.getId() + "&teachPlan.id=" + plan.getId();
		return redirect(request, new Action(CourseGroupAction.class, "edit"), "info.delete.success", extra);
	}
    
    /**
     * @param planCourseService
     *            The planCourseService to set.
     */
    public void setPlanCourseService(PlanCourseService planCourseService) {
        this.planCourseService = planCourseService;
    }
    
    private void getTeachPlanAndGroupFromReqeust(HttpServletRequest request) {
        Long planId = getLong(request, Constants.TEACHPLAN_KEY);
        Long groupId = getLong(request, "planCourse.courseGroup.id");
        if (null == groupId) {
            groupId = getLong(request, "courseGroup.id");
        }
        if (null == planId || null == groupId) {
            throw new PojoNotExistException("plan id or planCourse id");
        }
        plan = (TeachPlan) utilService.load(TeachPlan.class, planId);
        group = (CourseGroup) utilService.load(CourseGroup.class, groupId);
    }
}
