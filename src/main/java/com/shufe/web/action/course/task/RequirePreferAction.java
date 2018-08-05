//$Id: RequirePreferAction.java,v 1.5 2007/01/04 00:53:39 duanth Exp $
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
 * chaostone             2005-12-18         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.task;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.BookType;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.basecode.industry.Press;
import com.ekingstar.eams.system.basecode.industry.TeachLangType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.User;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.Constants;
import com.shufe.model.course.task.RequirePrefer;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.course.textbook.BookRequirement;
import com.shufe.model.course.textbook.TextBookConfig;
import com.shufe.model.course.textbook.Textbook;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.RequirePreferService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.baseinfo.TeacherService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.DispatchBasicAction;

public class RequirePreferAction extends DispatchBasicAction {

	private TeachCalendarService teachCalendarService;

	private TeachTaskService teachTaskService;

	private RequirePreferService preferenceService;

	private TeacherService teacherService;

	public static String inPreference = "inPreference";

	public static String inTask = "inTask";

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUser(request.getSession());
		Teacher teacher = teacherService.getTeacherByNO(user.getName());
		List stdTypeList = teachTaskService.getStdTypesForTeacher(teacher);
		if (stdTypeList.isEmpty()) {
			return forward(mapping, request, "error.teacher.noTask", "error");
		}
		request.setAttribute("stdTypeList", stdTypeList);
		Long stdTypeId = getLong(request, "calendar.studentType.id");
		if (null != stdTypeId) {
			request.setAttribute("studentType", utilService.get(StudentType.class, stdTypeId));
		} else {
			request.setAttribute("studentType", stdTypeList.get(0));
		}
		TeachCalendar calendar = getCalendar(request);
		if (null == calendar) {
			calendar = teachCalendarService.getTeachCalendar((StudentType) stdTypeList.iterator()
					.next());
		}
		request.setAttribute(Constants.CALENDAR, calendar);
		request.setAttribute(Constants.TEACHER, teacher);
		return forward(request);
	}

	/**
	 * 从页面的条件中查询对应的数据日历数据
	 * 
	 * @param request
	 * @return
	 */
	private TeachCalendar getCalendar(HttpServletRequest request) {
		TeachCalendar calendar = (TeachCalendar) RequestUtil.populate(request, TeachCalendar.class,
				Constants.CALENDAR);
		if (null != calendar.getId() && 0 != calendar.getId().intValue())
			return teachCalendarService.getTeachCalendar(calendar.getId());
		else if (null != calendar.getStudentType() && calendar.getStudentType().isPO()) {
			return teachCalendarService.getTeachCalendar(calendar.getStudentType(), calendar
					.getYear(), calendar.getTerm());
		} else {
			return null;
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Teacher teacher = getTeacherFromSession(request.getSession());
		TeachCalendar calendar = getCalendar(request);
		request.setAttribute(Constants.TEACHTASK_LIST, teachTaskService.getTeachTasksByCategory(
				teacher.getId(), TeachTaskFilterCategory.TEACHER, calendar));
		request.setAttribute(Constants.CALENDAR, calendar);
		return forward(request);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward preferList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long teacherId = getLong(request, Constants.TEACHER_KEY);
		if (null == teacherId) {
			User user = getUser(request.getSession());
			Teacher teacher = teacherService.getTeacherByNO(user.getName());
			teacherId = teacher.getId();
		}
		request.setAttribute(Constants.REQUIREMENTPREFERENCE_LIST, preferenceService
				.getPreferences(new RequirePrefer(new Teacher(teacherId), null)));
		return forward(request);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String type = request.getParameter("requirementType");
		if (StringUtils.isEmpty(type))
			return forward(mapping, request, "error.requirementType.unknown", "error");
		if (type.equals(inPreference)) {
			String preferenceId = request.getParameter(Constants.REQUIREMENTPREFERENCE_KEY);
			request.setAttribute(Constants.REQUIREMENTPREFERENCE, preferenceService
					.getPreference(Long.valueOf(preferenceId)));
		} else {
			String taskId = request.getParameter(Constants.TEACHTASK_KEY);
			request.setAttribute(Constants.TEACHTASK, teachTaskService.getTeachTask(Long
					.valueOf(taskId)));
		}
		request.setAttribute("configTypeList", baseCodeService.getCodes(ClassroomType.class));
		initBaseCodes(request, "teachLangTypes", TeachLangType.class);
		return forward(request);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTextbook(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("requirementType");
		if (type.equals(inPreference)) {
			Long requirePreferId = getLong(request, "requirePrefer.id");
			RequirePrefer prefer = (RequirePrefer) utilService.get(RequirePrefer.class,
					requirePreferId);
			addEntity(request, prefer);
		} else {
			Long taskId = getLong(request, "task.id");
			TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
			addEntity(request, task);
		}
		addCollection(request, "presses", baseCodeService.getCodes(Press.class));
		addCollection(request, "bookTypes", baseCodeService.getCodes(BookType.class));
		return forward(request);
	}

	/**
	 * 保存教科书设置,但不增加教材需求
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTextbook(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Textbook book = (Textbook) RequestUtil.populate(request, Textbook.class, "book");
		if (!book.isPO()) {
			utilService.saveOrUpdate(book);
		}
		String forward = request.getParameter("forward");
		String type = request.getParameter("requirementType");
		if (type.equals(inPreference)) {
			Long requirePreferId = getLong(request, "requirePrefer.id");
			RequirePrefer prefer = (RequirePrefer) utilService.get(RequirePrefer.class,
					requirePreferId);
			prefer.getRequire().getTextbooks().add(book);
			utilService.saveOrUpdate(prefer);
			return redirect(request, forward, "info.save.success");
		} else {
			Long taskId = getLong(request, "task.id");
			TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
			if (!task.getRequirement().getTextbooks().contains(book)) {
				task.getRequirement().getTextbooks().add(book);
				utilService.saveOrUpdate(task);
			}
			if (Boolean.TRUE.equals(TextBookConfig.ADD_BOOKREQUIRE_WHEN_TEACHERADBOOK)) {
				// 如果教材需求中没有该教材,则生成(默认人数是教学中的人数)
				EntityQuery entityQuery = new EntityQuery(BookRequirement.class, "bookRequire");
				entityQuery.add(new Condition("bookRequire.task.id =" + taskId
						+ " and bookRequire.textbook.id=" + book.getId()));
				Collection bookRequires = utilService.search(entityQuery);

				if (bookRequires.isEmpty()) {
					BookRequirement requirement = new BookRequirement();
					requirement.setTextbook(book);
					requirement.setTask(task);
                    //task.getTeachClass().getStdCount() removed by duantihua
					requirement.setCountForStd(new Integer(0));
					requirement.setCountForTeacher(new Integer(0));
					utilService.saveOrUpdate(requirement);
				}
			}
			return redirect(request, forward, "info.save.success", "&calendar.id="
					+ task.getCalendar().getId());
		}
	}

	/**
	 * 保存教科书设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTextbook(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("requirementType");
		String forward = request.getParameter("forward");
		Long bookId = getLong(request, "book.id");
		Textbook book = (Textbook) utilService.get(Textbook.class, bookId);
		if (type.equals(inPreference)) {
			Long preferenceId = getLong(request, "requirePrefer.id");
			RequirePrefer prefer = (RequirePrefer) utilService.get(RequirePrefer.class,
					preferenceId);
			prefer.getRequire().getTextbooks().remove(book);
			utilService.saveOrUpdate(prefer);
			return redirect(request, forward, "info.save.success");
		} else {
			Long taskId = getLong(request, "task.id");
			TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
			EntityQuery entityQuery = new EntityQuery(BookRequirement.class, "bookRequire");
			entityQuery.add(new Condition("bookRequire.task.id =" + taskId
					+ "  and bookRequire.textbook.id=" + bookId));
			Collection bookRequires = utilService.search(entityQuery);
			if (!bookRequires.isEmpty()) {
				BookRequirement bookRequirement = (BookRequirement) bookRequires.iterator().next();
				if (bookRequirement.getCountForStd().intValue() > 0){
					return redirect(request, forward, "error.deleteBookRequirement.exists",
							"&calendar.id=" + task.getCalendar().getId());
                }else{
                    utilService.remove(bookRequires);
                }
			}
			task.getRequirement().getTextbooks().remove(book);
			utilService.saveOrUpdate(task);
			return redirect(request, forward, "info.delete.success", "&calendar.id="
					+ task.getCalendar().getId());
		}

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveRequirement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("requirementType");
		if (StringUtils.isEmpty(type))
			return forward(mapping, request, "error.requirementType.unknown", "error");
		TaskRequirement require = (TaskRequirement) RequestUtil.populate(request,
				TaskRequirement.class, "require");
		EntityUtils.evictEmptyProperty(require);

		if (type.equals(inPreference)) {
			RequirePrefer preference = preferenceService.getPreference(Long.valueOf(request
					.getParameter(Constants.REQUIREMENTPREFERENCE_KEY)));
			EntityUtils.merge(preference.getRequire(), require);
			preferenceService.updatePreference(preference);
			preference.getRequire().setRoomConfigType(
					(ClassroomType) baseCodeService.getCode(ClassroomType.class, preference
							.getRequire().getRoomConfigType().getId()));
			return redirect(request, "preferList", "info.save.success");
		} else if (type.equals("inTask")) {
			String taskId = request.getParameter(Constants.TEACHTASK_KEY);
			TeachTask task = teachTaskService.getTeachTask(Long.valueOf(taskId));
			EntityUtils.merge(task.getRequirement(), require);
			teachTaskService.updateTeachTask(task);
			// update roomConfigtype for next jump list display
			task.getRequirement().setRoomConfigType(
					(ClassroomType) baseCodeService.getCode(ClassroomType.class, task
							.getRequirement().getRoomConfigType().getId()));
			if (null != request.getParameter("saveAsPreference"))
				preferenceService.savePreferenceForTask(task);
			return redirect(request, "search", "info.save.success", "&calendar.id="
					+ task.getCalendar().getId());
		}
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setPreferForTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskIdSeq = request.getParameter(Constants.TEACHTASK_KEYSEQ);
		if (StringUtils.isEmpty(taskIdSeq))
			return forward(mapping, request, "error.teachTask.ids.needed", "error");
		preferenceService.setPreferenceFor(taskIdSeq);
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"prompt.requirementPreference.update.success"));
		// saveMessages(request, messages);
		addErrors(request, messages);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws Exception
	 */
	public ActionForward retrievePreferenceForTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter(Constants.TEACHTASK_KEY);
		TeachTask task = teachTaskService.getTeachTask(Long.valueOf(taskId));

		request.setAttribute(Constants.TEACHTASK, task);
		request.setAttribute("configTypeList", baseCodeService.getCodes(ClassroomType.class));
		Teacher teacher = getTeacherFromSession(request.getSession());
		if (null == teacher)
			return forwardError(mapping, request, "error.parameters.illegal");
		RequirePrefer preference = preferenceService.getPreference(teacher, task.getCourse());
		if (null == preference) {
			preference = new RequirePrefer(teacher, task.getCourse());
			preference.setRequire((TaskRequirement) task.getRequirement().clone());
			preferenceService.savePreference(preference);
		} else {
			preferenceService.setPreferenceFor(Collections.singletonList(task));
		}
		return redirect(request, new Action("", "edit"), "info.save.success", new String[] {
				"requirementType", "task" });
	}

	/**
	 * @param teachCalendarService
	 *            The teachCalendarService to set.
	 */
	public void setTeachCalendarService(TeachCalendarService calendarService) {
		this.teachCalendarService = calendarService;
	}

	/**
	 * @param preferenceService
	 *            The preferenceService to set.
	 */
	public void setPreferenceService(RequirePreferService preferenceService) {
		this.preferenceService = preferenceService;
	}

	/**
	 * @param teachTaskService
	 *            The teachTaskService to set.
	 */
	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	/**
	 * @param teacherService
	 *            The teacherService to set.
	 */
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

}
