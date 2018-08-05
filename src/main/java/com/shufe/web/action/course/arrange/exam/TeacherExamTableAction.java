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
 * chaostone             2006-12-14            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamActivityService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarSupportAction;

/**
 * 教师查看课程排考安排
 * 
 * @author chaostone
 * 
 */
public class TeacherExamTableAction extends CalendarSupportAction {
	private TeachTaskService teachTaskService;

	private ExamActivityService examActivityService;

	/**
	 * 教师查看排考安排主界面
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
		Teacher teacher = getTeacherFromSession(request.getSession());
		List stdTypeList = teachTaskService.getStdTypesForTeacher(teacher);
		if (stdTypeList.isEmpty())
			return forward(mapping, request, "error.teacher.noTask", "error");
		request.setAttribute("studentType", stdTypeList.get(0));
		addCollection(request, "stdTypeList", stdTypeList);
		setCalendar(request, (StudentType) stdTypeList.iterator().next());

		addCollection(request, "examTypes", baseCodeService.getCodes(ExamType.class));
		return forward(request);
	}

	/**
	 * 所有课程对应的排考安排
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examAtivities(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
				"calendar.id"));
		Integer kind = getInteger(request, "kind");
		if (null == kind || kind.intValue() > 4 || kind.intValue() < 1) {
			kind = new Integer(1);
		}
		Teacher teacher = getTeacherFromSession(request.getSession());
		request.setAttribute("teacher", teacher);
		List examActivities = new ArrayList(examActivityService.getExamActivities(calendar,
				teacher, kind.intValue()));
		Collections.sort(examActivities, new PropertyComparator("date"));
		addCollection(request, "examActivities", examActivities);
		return forward(request);
	}

	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	public void setExamActivityService(ExamActivityService examActivityService) {
		this.examActivityService = examActivityService;
	}

}
