package com.ekingstar.eams.quality.lessoncheck.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.quality.lessoncheck.model.LessonCheck;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.LessonCheckType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;
import com.shufe.web.helper.TeachTaskSearchHelper;

/**
 * 督导组听课记录.
 * 
 * @author liujian 2008-5-22
 */
public class LessonCheckAction extends CalendarRestrictionExampleTemplateAction {
	private TeachTaskSearchHelper teachTaskSearchHelper;

	/**
	 * 督导组听课记录显示
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCalendarDataRealm(request, hasStdTypeCollege);
		TeachCalendar calendar = (TeachCalendar) request.getAttribute("calendar");
		if (null != calendar) {
			request.setAttribute("stdTypesOfCalendar", getStdTypesOf(calendar.getStudentType()
					.getId(), request));
		}
		addCollection(request, "courseTypes", baseCodeService.getCodes(CourseType.class));
		addCollection(request, "lessonCheckTypes", baseCodeService.getCodes(LessonCheckType.class));
		return forward(request);
	}

	/**
	 * 根据得到的序号得到该需要更新的对象
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long lessonCheckId = getLong(request, "lessonCheckId");
		LessonCheck lessonCheck = null;
		if (null == lessonCheckId) {
			Long taskId = getLong(request, "lessonCheck.task.id");
			if (null == taskId) {
				return forward(request, new Action(this, "taskList"));
			} else {
				TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
				lessonCheck = new LessonCheck();
				lessonCheck.setTask(task);
			}
		} else {
			lessonCheck = (LessonCheck) utilService.get(LessonCheck.class, lessonCheckId);
		}
		setCalendarDataRealm(request, hasStdTypeCollege);
		TeachCalendar calendar = (TeachCalendar) request.getAttribute("calendar");
		if (null != calendar) {
			request.setAttribute("stdTypesOfCalendar", getStdTypesOf(calendar.getStudentType()
					.getId(), request));
		}
		addCollection(request, "courseTypes", baseCodeService.getCodes(CourseType.class));
		addCollection(request, "lessonCheckTypes", baseCodeService.getCodes(LessonCheckType.class));
		request.setAttribute("lessonCheck", lessonCheck);
		return forward(request);
	}

	/**
	 * 查询教学任务窗口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward taskList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		addCollection(request, "tasks", teachTaskSearchHelper.searchTask(request));
		return forward(request);
	}

	/**
	 * 重写保存方法
	 */
	protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
			throws Exception {
		LessonCheck lessonCheck = (LessonCheck) entity;
		String year = get(request, "calendar.year");
		String term = get(request, "calendar.term");
		lessonCheck.setCalendar(teachCalendarService.getTeachCalendar(getLong(request,
				"studentType.id"), year, term));
		lessonCheck.setCheckRoom((Classroom) utilService.load(Classroom.class, getLong(request,
				"classRoomId")));
		return super.saveAndForwad(request, entity);
	}

	public void setTeachTaskSearchHelper(TeachTaskSearchHelper teachTaskSearchHelper) {
		this.teachTaskSearchHelper = teachTaskSearchHelper;
	}

}
