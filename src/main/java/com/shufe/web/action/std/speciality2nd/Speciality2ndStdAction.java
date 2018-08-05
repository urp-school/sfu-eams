//$Id: Speciality2ndStdAction.java,v 1.1 2007-4-23 下午02:52:39 chaostone Exp $
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
 * Name           	Date          		Description 
 * ============     ============        ============
 * chaostone      	2007-04-23         	Created
 * zq				2007-09-26			删除学生双专业信息中，修改了清除班级的语
 * 										句；把该过程建立一个方法cleanClassStudent()，
 * 										并在saveAndForwad()中加入，关闭studentService.updateStudentAdminClass()
 * 										方法；添加了settingUnstudy()方法
 ********************************************************************************/

package com.shufe.web.action.std.speciality2nd;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseTakeService;
import com.shufe.service.std.StudentService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;
import com.shufe.web.action.system.message.SystemMessageAction;

/**
 * 双专业学生信息维护
 * 
 * @author chaostone
 */
public class Speciality2ndStdAction extends
		CalendarRestrictionExampleTemplateAction {

	private StudentService studentService;

	private CourseTakeService courseTakeService;

	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(entityClass, getEntityName());
		populateConditions(request, query, "student.type.id");
		query.add(new Condition(getEntityName() + ".secondMajor is not null"));

		String adminClassName = get(request, "adminClass.name");
		if (StringUtils.isNotEmpty(adminClassName)) {
			query.join(getEntityName() + ".adminClasses", "adminClass");
			query.add(Condition.like("adminClass.name", adminClassName));
		}
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		DataRealmUtils.addDataRealms(query, new String[] { "student.type.id",
				"student.secondMajor.department.id" }, getDataRealmsWith(
				getLong(request, "student.type.id"), request));
		return query;
	}

	protected void indexSetting(HttpServletRequest request) {
		setDataRealm(request, hasStdTypeCollege);
	}

	protected void editSetting(HttpServletRequest request, Entity entity) {
		EntityQuery query = new EntityQuery(Speciality.class, "speciality");
		query.add(new Condition(
				"speciality.majorType.id="+MajorType.SECOND+" and state=true"));
		addCollection(request, "secondSpecialityList", utilService
				.search(query));
	}

	protected ActionForward saveAndForwad(HttpServletRequest request,
			Entity entity) throws Exception {
		Student std = (Student) entity;
		if (!ValidEntityPredicate.INSTANCE.evaluate(std.getSecondMajor())) {
			return forward(request, new Action(this, "edit"));
		}
		utilService.saveOrUpdate(std);
		studentService.updateStudentAdminClass(std.getId(), SeqStringUtil
				.transformToLong(request.getParameter("adminClassIds")),
				Boolean.TRUE);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 删除学生双专业信息
	 */
	protected ActionForward removeAndForwad(HttpServletRequest request,
			Collection entities) throws Exception {
		for (Iterator iter = entities.iterator(); iter.hasNext();) {
			Student std = (Student) iter.next();
			std.setSecondMajor(null);
			std.setSecondAspect(null);
			std.setIsSecondMajorStudy(null);
			std.setIsSecondMajorThesisNeed(null);
			utilService.saveOrUpdate(std);
			studentService.updateStudentAdminClass(std.getId(),
					Collections.EMPTY_LIST, Boolean.TRUE);
		}
		return redirect(request, "search", "info.delete.success");
	}

	/**
	 * 转入“设为不就读”页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward settingUnstudyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCalendarDataRealm(request, hasStdTypeCollege);
		Long[] stdIds = SeqStringUtil.transformToLong(request
				.getParameter("studentIds"));

		// 列出双专业学生,及其对应选过的双专业课程
		EntityQuery queryCourseTake = new EntityQuery(CourseTake.class, "take");
		Long calendarId = getLong(request, "calendar.id");
		if (calendarId == null) {
			calendarId = ((TeachCalendar) request
					.getAttribute(Constants.CALENDAR)).getId();
		}
		queryCourseTake.add(new Condition(
				"take.task.calendar.id = (:calendarId)", calendarId));
		queryCourseTake.add(new Condition(
				"take.task.course.category.id = courseCategoryId",
				CourseCategory.MAJOR2));
		queryCourseTake.add(new Condition("take.student.id in (:stdIds)",
				stdIds));
		addCollection(request, "stdCourseTakes", utilService
				.search(queryCourseTake));

		// 列出双专业学生，与其所在的班级
		EntityQuery queryAdminClass = new EntityQuery(Student.class, "student");
		queryAdminClass.add(new Condition("student.id in (:stdIds)", stdIds));
		queryAdminClass.join("student.adminClasses", "adminClass");
		queryAdminClass.add(new Condition(
				"adminClass.speciality.majorType.id =:l ", MajorType.SECOND));
		addCollection(request, "students", utilService.search(queryAdminClass));
		return forward(request);
	}

	/**
	 * 把双专业学生就读状态设为不就读
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward settingUnstudy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String takeIds = get(request, "takeIds");
		Boolean isRemove = getBoolean(request, "isRemove");

		// 选课
		List takes = null;
		if (StringUtils.isNotEmpty(takeIds)) {
			takes = utilService.load(CourseTake.class, "id", SeqStringUtil
					.transformToLong(takeIds));
			courseTakeService.withdraw(takes, getUser(request.getSession()));
		}

		Long[] ids = SeqStringUtil.transformToLong(request
				.getParameter("studentIds"));
		for (int i = 0; i < ids.length; i++) {
			Student std = (Student) utilService.load(Student.class, ids[i]);
			std.setIsSecondMajorStudy(Boolean.FALSE);
			std.setIsSecondMajorThesisNeed(Boolean.FALSE);
			utilService.saveOrUpdate(std);
			if (Boolean.TRUE.equals(isRemove)) {
				studentService.updateStudentAdminClass(std.getId(),
						Collections.EMPTY_LIST, Boolean.TRUE);
			}
		}
		return redirect(request, new Action("", "search"),
				"info.action.success");
	}

	/**
	 * 向上课学生发送消息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long[] studentIds = SeqStringUtil.transformToLong(request
				.getParameter("studentIds"));
		EntityQuery query = new EntityQuery(Student.class, "std");
		query.add(new Condition("std.id in (:ids)", studentIds));
		List students = (List) utilService.search(query);
		String stdCodes = "";

		for (Iterator it = students.iterator(); it.hasNext();) {
			Student std = (Student) it.next();
			stdCodes += std.getCode() + ",";
		}

		return forward(request, new Action(SystemMessageAction.class,
				"quickSend", "&receiptorIds=" + stdCodes));
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setCourseTakeService(CourseTakeService courseTakeService) {
		this.courseTakeService = courseTakeService;
	}
}
