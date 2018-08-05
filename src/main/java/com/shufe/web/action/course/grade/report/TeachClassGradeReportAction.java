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
 * chaostone            2007-01-02          Created
 * zq                   2007-09-13          在search()方法中，增加了addStdTypeTreeDataRealm(...)
 *                                          方法，相应的页面添加了addInput(...)方法于<script>...</script>
 *                                          中，但大类能查出，小类却查不出了
 ********************************************************************************/

package com.shufe.web.action.course.grade.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.Constants;
import com.shufe.model.course.grade.report.TeachClassGrade;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.action.course.grade.CollegeGradeAction;
import com.shufe.web.action.course.grade.TeachClassGradeHelper;
import com.shufe.web.action.course.grade.TeacherGradeAction;
import com.shufe.web.action.course.task.TeachTaskAction;

/**
 * 教学班成绩打印
 * 
 * @author chaostone
 */
public class TeachClassGradeReportAction extends CalendarRestrictionSupportAction {

	private TeachTaskService teachTaskService;

	private GradeService gradeService;

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
		setCalendarDataRealm(request, hasStdTypeDepart);
		String stdTypeDataRealm = getStdTypeIdSeq(request);
		String departDataRealm = getDepartmentIdSeq(request);
		addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
				stdTypeDataRealm, departDataRealm, (TeachCalendar) request
						.getAttribute(Constants.CALENDAR)));
		addCollection(request, "teachDepartList", teachTaskService.getTeachDepartsOfTask(
				stdTypeDataRealm, departDataRealm, (TeachCalendar) request
						.getAttribute(Constants.CALENDAR)));
		return forward(request);
	}

	/**
	 * 依据成绩录入情况,查找任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EntityQuery query = new EntityQuery(TeachTask.class, "task");
		GradeType FINAL = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.FINAL);
		query.add(new Condition("bitand(task.gradeState.confirmStatus,"
				+ FINAL.getMark().intValue() + ")=" + FINAL.getMark().intValue()));
		QueryRequestSupport.populateConditions(request, query, "task.teachClass.stdType.id");
		query.add(new Condition("task.calendar.id=" + getLong(request, "calendar.id")));
		Long stdTypeId = getLong(request, "task.teachClass.stdType.id");
		if (null == stdTypeId) {
			stdTypeId = getLong(request, "calendar.studentType.id");
		}
		DataRealmUtils.addDataRealms(query, new String[] { "task.teachClass.stdType.id",
				"task.arrangeInfo.teachDepart.id" }, getDataRealmsWith(stdTypeId, request));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "tasks", utilService.search(query));
		request.setAttribute("FINAL", FINAL);
		return forward(request);
	}

	/**
	 * 打印教学班成绩
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskIdSeq = request.getParameter("taskIds");
		if (StringUtils.isEmpty(taskIdSeq))
			taskIdSeq = request.getParameter("taskId");
		if (StringUtils.isEmpty(taskIdSeq))
			return forwardError(mapping, request, "error.parameters.needed");
		List tasks = teachTaskService.getTeachTasksByIds(taskIdSeq);
		new TeachClassGradeHelper(baseCodeService, gradeService).report(tasks, null, request);
		return forward(request);
	}

	public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return forward(request, new Action(CollegeGradeAction.class, "stat"));
	}

	public ActionForward reportForExam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forward(request, new Action(TeacherGradeAction.class, "reportForExam"));
	}

	/**
	 * 将一个或多个成绩单导入到xls中
	 */
	protected void configExportContext(HttpServletRequest request, Context context) {
		Long[] taskIds = SeqStringUtil.transformToLong(request.getParameter("taskIds"));
		List tasks = utilService.load(TeachTask.class, "id", taskIds);

		Integer stdPerClass = getInteger(request, "stdPerClass");
		if (null == stdPerClass)
			stdPerClass = new Integer(80);
		List reports = new ArrayList();
		GradeType GA = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.GA);
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			List gradeTypes = gradeService.getGradeTypes(task.getGradeState());
			if (!gradeTypes.contains(GA)) {
				gradeTypes.add(GA);
			}
			reports.addAll(TeachClassGrade.buildTaskClassGrade(gradeTypes, task, gradeService
					.getCourseGrades(task), stdPerClass));
		}
		context.put("reports", reports);
		List indexes = new ArrayList();
		for (int i = 0; i < stdPerClass.intValue() / 2; i++) {
			indexes.add(new Integer(i));
		}
		context.put("indexes", indexes);
		context.put("USUAL", baseCodeService.getCode(GradeType.class, GradeType.USUAL));
		context.put("END", baseCodeService.getCode(GradeType.class, GradeType.END));
		context.put("systemConfig", getSystemConfig());
	}
	
	/**
	 * 打印一个或多个教学任务的考勤表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printStdListForDuty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forward(request, new Action(TeachTaskAction.class, "printStdListForDuty"));
	}

	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
}
