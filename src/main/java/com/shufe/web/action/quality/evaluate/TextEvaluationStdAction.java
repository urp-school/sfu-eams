//$Id: TextEvaluationAction.java,v 1.15 2007/01/09 07:54:47 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-10-21         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.quality.evaluate.TextEvaluation;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * @author hj 2005-10-21 TextEvaluationAction.java has been created
 */
public class TextEvaluationStdAction extends RestrictionSupportAction {

	private TeachTaskService teachTaskService;

	private TeachCalendarService teachCalendarService;

	private StudentService studentService;

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String stdCode = getUser(request.getSession())
				.getName();
		Student student = studentService.getStudent(stdCode);
		TeachCalendar teachCalendar = teachCalendarService
				.getCurTeachCalendar(student.getType());
		if (null == teachCalendar) {
			teachCalendar = teachCalendarService.getTeachCalendar(student
					.getType());
		}
		List curCalendarList = teachCalendarService
				.getTeachCalendarsOfOverlapped(teachCalendar);
		List teachTaskList = teachTaskService.getTeachTasksByCategory(student
				.getId(), TeachTaskFilterCategory.STD, curCalendarList);
		addCollection(request, "taskList", teachTaskList);
		return forward(request);
	}

	/**
	 * 学生添加文字评教
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TextEvaluation textEvaluation = (TextEvaluation) populateEntity(
				request, TextEvaluation.class, "textEvaluation");
		String stdNo = getUser(request.getSession()).getName();
		Student student = studentService.getStudent(stdNo);
		textEvaluation.setStd(student);
		textEvaluation.setIsAffirm(new Boolean(false));
		// 设置是否是课程评教
		if (!textEvaluation.isPO()) {
			textEvaluation.setIsForCourse(Boolean
					.valueOf(!ValidEntityPredicate.INSTANCE
							.evaluate(textEvaluation.getTeacher())));
			TeachTask task = teachTaskService.getTeachTask(textEvaluation
					.getTask().getId());
			textEvaluation.setCalendar(task.getCalendar());
		}
		EntityUtils.evictEmptyProperty(textEvaluation);
		textEvaluation
				.setEvaluationAt(new Timestamp(System.currentTimeMillis()));
		utilService.saveOrUpdate(textEvaluation);
		return redirect(request, "index", "info.save.addSuccess");
	}

	/**
	 * 更新学生的文字评教内容
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String textEvaluationId = request.getParameter("textEvaluationId");
		if (StringUtils.isEmpty(textEvaluationId)) {
			return forward(request, new Action(this, "add"));
		}
		TextEvaluation textEvaluation = (TextEvaluation) utilService.load(
				TextEvaluation.class, Long.valueOf(textEvaluationId));
		if (Boolean.TRUE.equals(textEvaluation.getIsAffirm())) {
			return redirect(request, "index",
					"textEvaluation.cannotEditConfirmed");
		} else {
			request.setAttribute("textEvaluation", textEvaluation);
			return forward(request);
		}
	}

	/**
	 * 根据学生登陆Id得到这个学生这个学期的教学任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTeachTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String studentId = getUser(request.getSession())
				.getName();
		Student student = (Student) utilService.load(Student.class,
				studentId);
		TeachCalendar teachCalendar = teachCalendarService
				.getCurTeachCalendar(student.getType());
		List TeachTaskList = teachTaskService.getTeachTasksByCategory(
				studentId, TeachTaskFilterCategory.STD, teachCalendar);
		Results.addList("TeachTaskList", TeachTaskList);
		return forward(mapping, request, "loadTeachTask");
	}

	/**
	 * 根据学生得到学生所有建议的列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String stdCode = getUser(request.getSession())
				.getName();
		EntityQuery query = new EntityQuery(TextEvaluation.class,
				"textEvaluation");
		query.add(new Condition("textEvaluation.std.code =:stdCode", stdCode));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "textEvaluations", utilService.search(query));
		return forward(request);
	}

	/**
	 * @return Returns the studentService.
	 */
	public StudentService getStudentService() {
		return studentService;
	}

	/**
	 * @param studentService
	 *            The studentService to set.
	 */
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	/**
	 * @param teachCalendarService
	 *            The teachCalendarService to set.
	 */
	public void setTeachCalendarService(
			TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

	/**
	 * @param teachTaskService
	 *            The teachTaskService to set.
	 */
	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}
}
