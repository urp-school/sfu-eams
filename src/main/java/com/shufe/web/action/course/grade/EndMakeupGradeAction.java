//$Id: MakeupGradeAction.java Feb 29, 2008 9:11:09 PM chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2008. All rights reserved.
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
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      Feb 29, 2008  Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointRuleService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 毕业前 大补考成绩打印、管理
 * 
 * @author chaostone
 * 
 */
public class EndMakeupGradeAction extends CalendarRestrictionSupportAction {

	protected GradeService gradeService;

	protected TeachTaskService teachTaskService;

	private GradePointRuleService gradePointRuleService;

	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	/**
	 * 管理信息主页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setCalendarDataRealm(request, hasStdTypeCollege);
		String stdTypeDataRealm = getStdTypeIdSeq(request);
		String departDataRealm = getDepartmentIdSeq(request);

		addCollection(request, "teachDepartList", teachTaskService
				.getTeachDepartsOfTask(stdTypeDataRealm, departDataRealm,
						(TeachCalendar) request
								.getAttribute(Constants.CALENDAR)));
		return forward(request);
	}

	/**
	 * 查询大补考课程列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long calendarId = getLong(request, "calendar.id");
		EntityQuery query = new EntityQuery(ExamTake.class, "examTake");
		populateConditions(request, query);
		query.setSelect("select distinct examTake.task.course,examTake.task.arrangeInfo.teachDepart,examTake.activity.time");
		query.add(new Condition("examTake.examType.id in (:examTypeId)",
				new Object[]{ExamType.DELAY_AGAIN,ExamType.AGAIN,ExamType.DELAY}));
		query.add(new Condition(" examTake.calendar.id=:calendarId",
						calendarId));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "courseList", utilService.search(query));
		return forward(request);

	}
	
	/**
	 * 查询大补考课程 -- 学生成绩录入状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward getExamTakeState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long calendarId = getLong(request, "calendar.id");
		EntityQuery query = new EntityQuery(ExamActivity.class, "activity");
		populateConditions(request, query);
		query
				.setSelect("select distinct activity.task.course,activity.task.arrangeInfo.teachDepart,activity.time");
		query.add(new Condition("activity.examType.id=:examTypeId",
				ExamType.DELAY_AGAIN));
		query
				.add(new Condition(" activity.calendar.id=:calendarId",
						calendarId));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "courseList", utilService.search(query));
		return forward(request);

	}
	

	/**
	 * 登分册
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gradeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String courseIds = request.getParameter("courseIds");
		Long examTypeId = ExamType.DELAY_AGAIN;
		Long calendarId = getLong(request, "calendar.id");
		String courseId[] = StringUtils.split(courseIds, ",");
		List activities = new ArrayList();
		for (int i = 0; i < courseId.length; i++) {
			String params[] = StringUtils.split(courseId[i], "@");
			Set student = new HashSet();
			List activityList = utilService
					.searchHQLQuery("select activity from ExamActivity activity join activity.task teachTask where teachTask.calendar.id="
							+ calendarId
							+ " and teachTask.course.id="
							+ params[0]
							+ " and teachTask.arrangeInfo.teachDepart.id="
							+ params[1]
							+ " and activity.examType.id="
							+ examTypeId);

			for (Iterator iter = activityList.iterator(); iter.hasNext();) {
				ExamActivity exam = (ExamActivity) iter.next();
				for (Iterator iterator = exam.getExamTakes().iterator(); iterator
						.hasNext();) {
					ExamTake element = (ExamTake) iterator.next();
					student.add(element);
				}
			}
			activities.add(student);
		}
		addCollection(request, "activities", activities);
		request.setAttribute("calendar", teachCalendarService
				.getTeachCalendar(calendarId));
		return forward(request);
	}

	private List getExamTakes(String courseIds, Long examTypeId, Long calendarId) {
		String params[] = StringUtils.split(courseIds, "@");
		List examTakeList = new ArrayList();
		List activityList = utilService
				.searchHQLQuery("select activity from ExamActivity activity join activity.task teachTask where teachTask.calendar.id="
						+ calendarId
						+ " and teachTask.course.id="
						+ params[0]
						+ " and teachTask.arrangeInfo.teachDepart.id="
						+ params[1] + " and activity.examType.id=" + examTypeId);

		for (Iterator iter = activityList.iterator(); iter.hasNext();) {
			ExamActivity exam = (ExamActivity) iter.next();
			for (Iterator iterator = exam.getExamTakes().iterator(); iterator
					.hasNext();) {
				ExamTake examTake = (ExamTake) iterator.next();
				examTakeList.add(examTake);
			}
		}
		return examTakeList;
	}

	/**
	 * 添加录入成绩（按代码）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchAddGrade(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getExamTakeAndMakeupExam(request);
		request.setAttribute("MAKEUP", baseCodeService.getCode(ExamType.class,
				ExamType.AGAIN));
		return forward(request);
	}
	/**
	 * 添加录入成绩（按代码）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gradeInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getExamTakeAndMakeupExam(request);
		return forward(request);
	}
	private void getExamTakeAndMakeupExam(HttpServletRequest request) {
		String courseIds = request.getParameter("courseIds");
		Long examTypeId = ExamType.DELAY_AGAIN;
		Long calendarId = getLong(request, "calendar.id");
		List examTakeList = getExamTakes(courseIds, examTypeId, calendarId);
		Map examGradeMap= new HashMap();
		for (Iterator iter = examTakeList.iterator(); iter.hasNext();) {
			ExamTake examTake = (ExamTake) iter.next();
			CourseGrade grade = examTake.getCourseTake().getCourseGrade();
			GradeType gradeType = null;
			if (examTake.getExamType().getId().equals(ExamType.DELAY)) {
				gradeType = new GradeType(GradeType.DELAY);
			} else {
				gradeType = new GradeType(GradeType.MAKEUP);
			}
			if (null!=grade){
				ExamGrade examGrade = grade.getExamGrade(gradeType);
				examGradeMap.put(examTake.getId().toString(), examGrade);
			}
		}
		addCollection(request, "examTakeList", examTakeList);
		request.setAttribute("examGradeMap", examGradeMap);
		request.setAttribute("calendar", teachCalendarService
				.getTeachCalendar(calendarId));
		String params[] = StringUtils.split(courseIds, "@");
		request.setAttribute("course", utilService.load(
				com.shufe.model.system.baseinfo.Course.class, Long
						.valueOf(params[0].toString())));
		request.setAttribute("teachDepart", utilService.load(Department.class,
				Long.valueOf(params[1].toString())));
	}

	/**
	 * 保存批量录入的成绩
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchSaveCourseGrade(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String courseIds = request.getParameter("courseIds");
		Long examTypeId = ExamType.DELAY_AGAIN;
		Long calendarId = getLong(request, "calendar.id");
		List activities = getExamTakes(courseIds, examTypeId, calendarId);
		List grades = new ArrayList();
		for (Iterator iter = activities.iterator(); iter.hasNext();) {
			ExamTake examTake = (ExamTake) iter.next();
			Float score = RequestUtils.getFloat(request, examTake.getId()
					.toString());
			if (null != score) {
				CourseGrade grade = examTake.getCourseTake().getCourseGrade();
				GradeType gradeType = null;
				if (examTake.getExamType().getId().equals(ExamType.DELAY)) {
					gradeType = new GradeType(GradeType.DELAY);
				} else {
					gradeType = new GradeType(GradeType.MAKEUP);
				}
				if (null != grade) {
					ExamGrade examGrade = grade.getExamGrade(gradeType);
					if (null == examGrade) {
						examGrade = new ExamGrade();
						examGrade.setGradeType(gradeType);
						examGrade.setStatus(new Integer(Grade.CONFIRMED));
						examGrade.getExamStatus().setId(ExamStatus.NORMAL);
						examGrade.setScore(score);
						grade.addExamGrade(examGrade);
					}else{
						examGrade.updateScore(score, getUser(request
								.getSession()));
					}
					grade.calcGA().calcScore().updatePass().calcGP(
							gradePointRuleService.getGradePointRule(grade.getStd()
									.getType(), grade.getMarkStyle()));
					grades.add(grade);
				}
			}
		}
		utilService.saveOrUpdate(grades);
		saveErrors(request.getSession(), ForwardSupport
				.buildMessages(new String[] { "info.save.success" }));
		return mapping.findForward("actionResult");
	}

	public void setGradePointRuleService(
			GradePointRuleService gradePointRuleService) {
		this.gradePointRuleService = gradePointRuleService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
}
