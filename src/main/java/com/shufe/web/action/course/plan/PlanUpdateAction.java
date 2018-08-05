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
 * chaostone             2006-10-29            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.plan;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.std.Student;
import com.shufe.service.course.plan.CourseGroupService;
import com.shufe.service.course.plan.PlanCourseService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.web.action.common.RestrictionSupportAction;
/**
 * 培养计划修改
 * @author chaostone
 *
 */
public class PlanUpdateAction extends RestrictionSupportAction {
	CourseGroup group;

	TeachPlan plan;

	PlanCourseService planCourseService;

	CourseGroupService courseGroupService;

	TeachPlanService teachPlanService;

	/**
	 * 如果界面上以专业计划显示为个人计划，那么点击修改时，要生成个人计划。
	 * 
	 * @param request
	 * @return
	 */
	protected boolean keepTeachPlanUnique(HttpServletRequest request) {
		Long teachPlanId = getLong(request, "teachPlan.id");
		String stdCode = request.getParameter("std.code");
		ActionMessages actionMessages = new ActionMessages();
		if (null == teachPlanId) {
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("error.parameters.needed"));
			addErrors(request, actionMessages);
			return false;
		}
		plan = (TeachPlan) utilService.get(TeachPlan.class, teachPlanId);
		if (null == courseGroupService) {
			courseGroupService = planCourseService.getCourseGroupService();
		}
		if (null == teachPlanService) {
			teachPlanService = courseGroupService.getTeachPlanService();
		}
		// 如果是为学生服务
		if (StringUtils.isNotEmpty(stdCode)) {
			if (null != plan.getStd()) {
				if (!StringUtils.equals(stdCode, plan.getStd().getCode())) {
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("error.parameters.illegal"));
					addErrors(request, actionMessages);
					return false;
				}
			} else {
				// 按需生成个人计划
				Collection plans = teachPlanService.genTeachPlanForStd(
						utilService.load(Student.class, "code", stdCode),
						plan);
				if (plans.isEmpty() || plans.size() > 1) {
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("error.parameters.illegal",
									stdCode));
					addErrors(request, actionMessages);
					return false;
				} else {
					plan = (TeachPlan) plans.iterator().next();
				}
			}
		}
		return true;
	}

	public PlanCourseService getPlanCourseService() {
		return planCourseService;
	}

	public void setPlanCourseService(PlanCourseService planCourseService) {
		this.planCourseService = planCourseService;
	}

	public CourseGroupService getCourseGroupService() {
		return courseGroupService;
	}

	public void setCourseGroupService(CourseGroupService courseGroupService) {
		this.courseGroupService = courseGroupService;
	}

	public void setTeachPlanService(TeachPlanService teachPlanService) {
		this.teachPlanService = teachPlanService;
	}

	public TeachPlanService getTeachPlanService() {
		return teachPlanService;
	}

	public CourseGroup getGroup() {
		return group;
	}

	public TeachPlan getPlan() {
		return plan;
	}

}
