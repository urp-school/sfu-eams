//$Id: EvaluateStdAction.java,v 1.1 2007-6-2 19:49:43 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.security.User;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.quality.evaluate.EvaluateResult;
import com.shufe.model.quality.evaluate.EvaluateSwitch;
import com.shufe.model.quality.evaluate.Option;
import com.shufe.model.quality.evaluate.Question;
import com.shufe.model.quality.evaluate.QuestionResult;
import com.shufe.model.quality.evaluate.TextEvaluation;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.quality.evaluate.EvaluateResultService;
import com.shufe.service.quality.evaluate.EvaluateSwitchService;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.common.RestrictionSupportAction;

public class EvaluateStdAction extends RestrictionSupportAction {

	private TeachCalendarService teachCalendarService;

	private TeachTaskService teachTaskService;

	private EvaluateResultService evaluateResultService;

	private EvaluateSwitchService evaluateSwitchService;

	private StudentService studentService;

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		User user = getUser(request.getSession());
		Student std = studentService.getStudent(user.getName());
		if (null == std)
			return forward(mapping, request, "error.std.stdNo.needed", "error");
		TeachCalendar calendar = null;
		String year = get(request, "calendar.year");
		String term = get(request, "calendar.term");
		if (StringUtils.isEmpty(year)) {
			calendar = teachCalendarService.getTeachCalendar(std.getType());
		} else {
			calendar = teachCalendarService.getTeachCalendar(std.getType().getId(), year, term);
		}
		if (null == calendar)
			return forward(mapping, request, "error.calendar.notMaded", "error");

		request.setAttribute("std", std);
		request.setAttribute("studentType", std.getType());
		request.setAttribute("stdTypeList", Collections.singleton(std.getType()));
		request.setAttribute(Constants.CALENDAR, calendar);
		return forward(request);
	}

	/**
	 * 查询指定学期要评教的教学任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Student student = getStudentFromSession(request.getSession());
		TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(getLong(request,
				"calendar.id"));
		if (null == teachCalendar) {
			return forward(request, "calendarError");
		}
		// 得到这个学生这个学期的教学任务
		List curCalendarList = teachCalendarService.getTeachCalendarsOfOverlapped(teachCalendar);
		List teachTaskList = teachTaskService.getTeachTasksByCategory(student.getId(),
				TeachTaskFilterCategory.STD, curCalendarList);
		// 过滤掉没有教师 和没有指定问卷的课程
		for (int i = teachTaskList.size() - 1; i >= 0; i--) {
			TeachTask teachTask = (TeachTask) teachTaskList.get(i);
			if (null == teachTask.getQuestionnaire()) {
				teachTaskList.remove(i);
				continue;
			}
		}
		// 得到学生已经评教的课程和教师信息
		Map evaluateMap = new HashMap();
		Collection evaluatedTaskIds = evaluateResultService.getTaskIdAndTeacherIdOfResult(student,
				curCalendarList);
		for (Iterator iter = evaluatedTaskIds.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Boolean evaluateOfTeacher = (Boolean) element[2];
			if (Boolean.TRUE.equals(evaluateOfTeacher)) {
				evaluateMap.put(element[0] + "_" + element[1], "1");
			} else {
				evaluateMap.put(element[0] + "_" + "0", "1");
			}
		}
		request.setAttribute("evaluateMap", evaluateMap);
		request.setAttribute("teachTaskList", teachTaskList);
		return forward(request);
	}

	/**
	 * 加载问卷页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward loadQuestionnaire(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String teachTaskIdc = request.getParameter("evaluateId");
		String evaluateState = request.getParameter("evaluateState");
		String[] ids = StringUtils.split(teachTaskIdc, ",");
		Long taskId = Long.valueOf(ids[0]); // 第一位是教学任务id
		TeachTask teachTask = (TeachTask) utilService.get(TeachTask.class, taskId);
		EvaluateSwitch evaluateSwitch = evaluateSwitchService.getEvaluateSwitch(teachTask
				.getCalendar(), teachTask.getTeachClass().getStdType(), teachTask.getArrangeInfo()
				.getTeachDepart());
		if (null == evaluateSwitch || !evaluateSwitch.checkOpen(new Date())) {
			saveErrors(request.getSession(), ForwardSupport
					.buildMessages(new String[] { "field.evaluate.noOpenEvaluateError" }));
			return forward(request, "errors");
		}
		Student student = getStudentFromSession(request.getSession());
		request.setAttribute("evaluateState", evaluateState);
		// 确定此次 评教的教师列表
		List teachers = new ArrayList();
		if (Boolean.TRUE.equals(teachTask.getRequirement().getEvaluateByTeacher())) {
			Long teacherId = Long.valueOf(ids[1]);
			Teacher teacher = (Teacher) utilService.load(Teacher.class, teacherId);
			teachers.add(teacher);
		} else {
			teachers.addAll(teachTask.getArrangeInfo().getTeachers());
		}
		// 问题排序
		List questionList = new ArrayList(teachTask.getQuestionnaire().getQuestions());
		Collections.sort(questionList);
		request.setAttribute("questionList", questionList);
		request.setAttribute("teacherList", teachers);
		request.setAttribute("teachTask", teachTask);
		// 根据是评教 还是更新问卷做对比
		if ("evaluate".equals(evaluateState)) {
			request.setAttribute("questionnaire", teachTask.getQuestionnaire());
			CourseTake courseTake = null;
			List courseTasks = utilService.load(CourseTake.class, new String[] {
					"task.id", "student.id" }, new Object[] { taskId, student.getId() });
			if (courseTasks.size() > 0) {
				courseTake = (CourseTake) courseTasks.get(0);
			}
			// 不要评别人的课
			if (null == courseTake) {
				request.setAttribute("message", "error.dataRealm.insufficient");
				return forward(request, "errors");
			}
			// 已经评过了
			if (Boolean.TRUE.equals(courseTake.getIsCourseEvaluated())) {
				request.setAttribute("message", "field.select.multiEvaluateSameCouse");
				return forward(request, "errors");
			}
			// 更新
		} else if ("update".equals(evaluateState)) {
			Long teacherId = null;
			if (Boolean.TRUE.equals(teachTask.getRequirement().getEvaluateByTeacher())) {
				teacherId = Long.valueOf(ids[1]);
			}
			EvaluateResult result = evaluateResultService.getResultByStdIdAndTaskId(
					student.getId(), teachTask.getId(), teacherId);
			if (null == result) {
				request.setAttribute("message", "error.dataRealm.insufficient");
				return forward(request, "errors");
			}
			
			//将已有的问卷结果按照问题组成map
			Map questionResultMap=new HashMap();
			for (Iterator iterator = result.getQuestionResultSet().iterator(); iterator.hasNext();) {
				QuestionResult questionResult = (QuestionResult) iterator.next();
				questionResultMap.put(questionResult.getQuestion().getId().toString(), questionResult.getOption().getId());
			}
			request.setAttribute("evaluateResult", result);
			request.setAttribute("questionResultMap", questionResultMap);
			request.setAttribute("questionnaire", result.getQuestionnaire());

		} else {
			// 非法操作
			request.setAttribute("message", "field.evaluate.errorAction");
			return forward(request, "errors");
		}
		return forward(request);
	}

	/**
	 * 保存操作的评教结果
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
		Long resultId = getLong(request, "result.id");
		TeachTask task = null;
		Teacher teacher = null;
		Student student = getStudentFromSession(request.getSession());
		// 更新评级问卷
		if (null != resultId) {
			EvaluateResult result = (EvaluateResult) utilService.get(EvaluateResult.class,
					resultId);
			task = result.getTask();
			teacher = result.getTeacher();

			Set questionResult = result.getQuestionResultSet();
			for (Iterator iter = questionResult.iterator(); iter.hasNext();) {
				QuestionResult element = (QuestionResult) iter.next();
				Question question = element.getQuestion();
				Long optionId = getLong(request, "select" + question.getId());
				if (!element.getOption().getId().equals(optionId)) {
					Option option = (Option) utilService.get(Option.class, optionId);
					element.setOption(option);
					element.setScore(new Float(question.getScore().floatValue()
							* option.getProportion().floatValue()));
				}
			}
			utilService.saveOrUpdate(questionResult);
		} else {
			// 添加评教信息
			List saveOrUpdateList = new ArrayList();
			task = (TeachTask) utilService.get(TeachTask.class, getLong(request, "taskId"));
			String teacherIdSeq = request.getParameter("teacherIds");
			List teachers = utilService.load(Teacher.class, "id", SeqStringUtil
					.transformToLong(teacherIdSeq));
			// 如果是一个老师则记录该老师，为文字评教做准备
			if (teachers.size() == 1) {
				teacher = (Teacher) teachers.get(0);
			}

			if (null != task.getQuestionnaire() && null != task.getQuestionnaire().getQuestions()) {
				Teacher evaluateTeacher = teacher;
				if (Boolean.FALSE.equals(task.getRequirement().getEvaluateByTeacher())) {
					evaluateTeacher = null;
				}
				EvaluateResult evaluateResult = new EvaluateResult(task, student, evaluateTeacher);
				for (Iterator iter = task.getQuestionnaire().getQuestions().iterator(); iter
						.hasNext();) {
					Question question = (Question) iter.next();
					Option option = (Option) utilService.get(Option.class, getLong(request,
							"select" + question.getId()));
					QuestionResult questionResult = new QuestionResult(question, option);
					evaluateResult.addQuestionResult(questionResult);
				}
				saveOrUpdateList.add(evaluateResult);
			}
			// 更新选课信息表
			// 如果是教师评教 需要考虑 是否多个教师都已经评教完了然后提交
			CourseTake courseTake = (CourseTake) utilService.load(CourseTake.class,
					new String[] { "task.id", "student.id" },
					new Object[] { task.getId(), student.getId() }).iterator().next();
			Boolean isEvaluate = Boolean.TRUE;
			if (Boolean.TRUE.equals(task.getRequirement().getEvaluateByTeacher())) {
				EntityQuery entity = new EntityQuery(EvaluateResult.class, "result");
				entity.add(new Condition("result.task =(:task)", task));
				entity.add(new Condition("result.student=(:student)", student));
				entity.setSelect("select result.teacher");
				Collection tempTeachers = utilService.search(entity);
				if (task.getArrangeInfo().getTeachers().size() != (tempTeachers.size() + 1)) {
					isEvaluate = Boolean.FALSE;
				}
			}
			courseTake.setIsCourseEvaluated(isEvaluate);
			saveOrUpdateList.add(courseTake);

			// FIXME SYS_C0078732
			utilService.saveOrUpdate(saveOrUpdateList);
		}
		// 文字评教
		String textOpinion = get(request, "textOpinion");
		if (StringUtils.isNotBlank(textOpinion)) {
			TextEvaluation textEvaluation = new TextEvaluation(student, task, teacher, textOpinion);
			utilService.saveOrUpdate(textEvaluation);
		}
		return redirect(request, "list", "info.action.success");
	}

	public void setEvaluateResultService(EvaluateResultService evaluateResultService) {
		this.evaluateResultService = evaluateResultService;
	}

	public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	public void setEvaluateSwitchService(EvaluateSwitchService evaluateSwitchService) {
		this.evaluateSwitchService = evaluateSwitchService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

}
