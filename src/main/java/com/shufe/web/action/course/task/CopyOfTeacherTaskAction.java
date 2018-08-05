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
 * chaostone             2006-8-23            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.Constants;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarSupportAction;

/**
 * 教师的教学任务
 * 
 * @author chaostone
 * 
 */
public class CopyOfTeacherTaskAction extends CalendarSupportAction {
	private TeachTaskService teachTaskService;

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Teacher teacher = getTeacherFromSession(request.getSession());
		List stdTypeList = teachTaskService.getStdTypesForTeacher(teacher);
		if (stdTypeList.isEmpty())
			return forward(mapping, request, "error.teacher.noTask", "error");
		TeachCalendar calendar = setNewCalendar(request);

		request.setAttribute("stdTypeList", stdTypeList);
		request.setAttribute("weekList", WeekInfo.WEEKS);
		request.setAttribute("startWeek", new Integer(1));

		request.setAttribute("endWeek", calendar.getWeeks());
		List tasks = teachTaskService.getTeachTasksByCategory(teacher.getId(),
				TeachTaskFilterCategory.TEACHER, calendar);
		addCollection(request, "taskList", tasks);
		CourseActivityDigestor.setDelimeter("<br>");
		List activityList = new ArrayList();
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			activityList.addAll(task.getArrangeInfo().getActivities());
		}
		request.setAttribute(Constants.TEACHER, teacher);
		request.setAttribute("activityList", activityList);
		return forward(request);
	}

	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	/**
	 * 学生名单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward stdList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long taskId = getLong(request, "teachTask.id");
		if (null == taskId)
			return forwardError(mapping, request, "error.model.id.needed");
		TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
		request.setAttribute(Constants.TEACHTASK, task);
		ArrayList courseTakes = new ArrayList();
		courseTakes.addAll(task.getTeachClass().getCourseTakes());
		request.setAttribute("courseTakes", courseTakes);
		return forward(request);
	}

	/**
	 * 学生考勤名单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printDutyStdList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forward(request, new Action("teachTask", "printStdListForDuty"));
	}

	/**
	 * 教学任务书
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long taskId = getLong(request, "teachTask.id");
		if (null == taskId)
			return forwardError(mapping, request, "error.model.id.needed");
		request.setAttribute(Constants.TEACHTASK, utilService.get(TeachTask.class, taskId));
		return forward(request);
	}

}
