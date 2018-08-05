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
 * chaostone             2007-1-6            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.grade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.service.BaseCodeService;
import com.shufe.model.course.grade.CourseGradeComparator;
import com.shufe.model.course.grade.report.TeachClassGrade;
import com.shufe.model.course.grade.stat.CourseSegStat;
import com.shufe.model.course.grade.stat.TaskSegStat;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.GradeService;
import com.shufe.util.RequestUtil;

/**
 * 成绩action帮助类,主要用户教师用户,管理员用户公用的一些服务<br>
 * 1)统计分析<br>
 * 2)打印成绩单<br>
 * 3)显示成绩详情<br>
 * 
 * @author chaostone
 * 
 */
public class TeachClassGradeHelper {
	BaseCodeService baseCodeService;

	GradeService gradeService;

	public TeachClassGradeHelper(BaseCodeService baseCodeService, GradeService gradeService) {
		this.baseCodeService = baseCodeService;
		this.gradeService = gradeService;
	}

	/**
	 * 按照任务分段统计<br>
	 * 最大支持7段
	 * 
	 * @param taskIdSeq
	 * @param gradeTypeIds
	 * @param request
	 * @throws Exception
	 */
	public void statTask(String taskIdSeq, Long[] gradeTypeIds, HttpServletRequest request)
			throws Exception {
		List gradeTypes = new ArrayList();
		for (int i = 0; i < gradeTypeIds.length; i++) {
			gradeTypes.add(baseCodeService.getCode(GradeType.class, gradeTypeIds[i]));
		}
		TaskSegStat segStat = new TaskSegStat(7);
		RequestUtil.populate(request, segStat, "segStat");
		segStat.buildScoreSegments();
		request.setAttribute("segStat", segStat);
		request.setAttribute("courseStats", gradeService.statTask(taskIdSeq, segStat
				.getScoreSegments(), gradeTypes, null));
	}

	/**
	 * 按照课程分段统计<br>
	 * 最大支持7段
	 * 
	 * @param taskIdSeq
	 * @param gradeTypeIds
	 * @param request
	 * @throws Exception
	 */
	public void statCourse(List courses, TeachCalendar calendar, Long[] gradeTypeIds,
			HttpServletRequest request) throws Exception {
		List gradeTypes = new ArrayList();
		for (int i = 0; i < gradeTypeIds.length; i++) {
			gradeTypes.add(baseCodeService.getCode(GradeType.class, gradeTypeIds[i]));
		}
		CourseSegStat segStat = new CourseSegStat(7);
		RequestUtil.populate(request, segStat, "segStat");
		segStat.buildScoreSegments();
		request.setAttribute("segStat", segStat);
		request.setAttribute("courseStats", gradeService.statCourse(courses, segStat
				.getScoreSegments(), gradeTypes, calendar));
	}

	/**
	 * 打印成绩单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void report(List tasks, Long[] gradeTypeIds, HttpServletRequest request)
			throws Exception {
		List gradeTypes = null;
		if (null != gradeTypeIds) {
			gradeTypes = new ArrayList();
			for (int i = 0; i < gradeTypeIds.length; i++) {
				gradeTypes.add(baseCodeService.getCode(GradeType.class, gradeTypeIds[i]));
			}
		}
		GradeType GA = (GradeType) baseCodeService.getCode(GradeType.class, GradeType.GA);
		List reports = new ArrayList();
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			TeachTask task = (TeachTask) iter.next();
			if (null == gradeTypeIds) {
				gradeTypes = gradeService.getGradeTypes(task.getGradeState());
				if (!gradeTypes.contains(GA)) {
					gradeTypes.add(GA);
				}
			}
			reports.add(new TeachClassGrade(gradeTypes, task, gradeService.getCourseGrades(task)));
		}
		request.setAttribute("USUAL", GradeType.USUAL);
		request.setAttribute("REEXAM", CourseTakeType.REEXAM);
		request.setAttribute("reports", reports);
	}

	/**
	 * 查看单个教学任务所有成绩信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void info(TeachTask task, HttpServletRequest request) throws Exception {
		List grades = gradeService.getCourseGrades(task);
		List gradeTypes = gradeService.getGradeTypesOfExam(task);
		gradeTypes.add(baseCodeService.getCode(GradeType.class, GradeType.GA));
		gradeTypes.add(baseCodeService.getCode(GradeType.class, GradeType.FINAL));

		List orders = OrderUtils.parser(request.getParameter("orderBy"));
		if (!orders.isEmpty()) {
			Order order = (Order) orders.get(0);
			Collections.sort(grades, new CourseGradeComparator(order.getProperty(), order
					.getDirection() == Order.ASC, gradeTypes));
		}
		request.setAttribute("gradeTypes", gradeTypes);
		request.setAttribute("grades", grades);
		request.setAttribute("task", task);
		String uri = request.getRequestURI();
		request.setAttribute("action", uri.substring(uri.lastIndexOf("/") + 1));
	}
}
