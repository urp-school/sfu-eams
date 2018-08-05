//$Id: InputDutyRecord.java,v 1.18 2007/01/23 03:56:37 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-30         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.duty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.collection.predicates.NotEmptyStringPredicate;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.basecode.industry.AttendanceType;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.time.CourseUnit;
import com.shufe.model.course.task.TeachTask;
import com.shufe.service.course.arrange.task.CourseActivityService;
import com.shufe.service.duty.DutyService;
import com.shufe.service.system.calendar.TeachCalendarService;

/**
 * @author dell
 */
public class InputDutyRecord extends DutyRecordSupportAction {

	private DutyService dutyService;

	private CourseActivityService courseActivityService;

	/**
	 * 检索教学任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchTeachTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.searchTeachTask(form, request);
		Results.addObject("courseTypeList", utilService.loadAll(CourseType.class));
		return this.forward(mapping, request, "search");
	}

	/**
	 * 新增考勤记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward inputForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long taskId =getLong(request, "teachTaskId");
		Results.addObject("teachTask", utilService.load(TeachTask.class,taskId));
		Results.addObject("dutyStatusList", utilService.loadAll(AttendanceType.class));
		java.sql.Date today=new java.sql.Date(System.currentTimeMillis());
		List todayUnits=courseActivityService.getCourseUnits(taskId,today );
		addCollection(request, "todayUnits", todayUnits);
		request.setAttribute("today", today);
		return this.forward(mapping, request, "inputForm");
	}

	public ActionForward maintainRecordByTeachTaskForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Long teachTaskId = Long.valueOf(request.getParameter("teachTaskId"));
		if (null == teachTaskId) {
			teachTaskId = (request.getAttribute("teachTaskId") == null) ? (new Long(0))
					: ((Long) request.getAttribute("teachTaskId"));
		}
		TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
		Results.addObject("teachTask", teachTask);

		return this.forward(mapping, request, "maintainRecordByTeachTaskForm");
	}

	/**
	 * 显示课表录入的详细界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward maintainRecordByTeachTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String unitFlagString = request.getParameter("unitFlag");
		boolean uniteFlag = false;
		if (new NotEmptyStringPredicate().evaluate(unitFlagString)) {

		}

		Long teachTaskId = Long.valueOf(request.getParameter("teachTaskId"));
		if (null == teachTaskId) {
			teachTaskId = (request.getAttribute("teachTaskId") == null) ? (new Long(0))
					: ((Long) request.getAttribute("teachTaskId"));
		}
		TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
		String beginDateString = request.getParameter("beginDate");
		String endDateString = request.getParameter("endDate");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;
		if (StringUtils.isNotEmpty(beginDateString)) {
			try {
				beginDate = simpleDateFormat.parse(beginDateString);
			} catch (ParseException e) {
				throw new RuntimeException("beginDate ParseException");
			}
		} else {
			beginDate = teachTask.getCalendar().getStart();
		}
		if (StringUtils.isNotEmpty(endDateString)) {
			try {
				endDate = simpleDateFormat.parse(endDateString);
			} catch (ParseException e) {
				throw new RuntimeException("endDate ParseException");
			}
		} else {
			endDate = teachTask.getCalendar().getFinish();
		}
		Map stdCourseUnitStatusMap = dutyService.getCourseUnitStatusMap(teachTaskId, beginDate,
				endDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		simpleDateFormat.setCalendar(calendar);
		long endTime = calendar.getTimeInMillis();
		calendar.setTime(beginDate);
		simpleDateFormat.setCalendar(calendar);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		long beginTime = calendar.getTimeInMillis();
		long days = (endTime - beginTime) / (24 * 60 * 60 * 1000);
		Map dayMap = new HashMap();
		StringBuffer dateAndcourseUnit = new StringBuffer();
		for (long i = 0; i < days; i++) {
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
					.get(Calendar.DATE) + 1, 0, 0);
			List courseUnitList = courseActivityService.getCourseUnits(teachTaskId,
					new java.sql.Date(calendar.getTime().getTime()));

			if (courseUnitList != null && !courseUnitList.isEmpty()) {
				Collections.sort(courseUnitList, new BeanComparator("index"));
				if (uniteFlag) {

				} else {
					dayMap.put(simpleDateFormat.format(calendar.getTime()), courseUnitList);
					dateAndcourseUnit.append(";");
					dateAndcourseUnit.append(",");
					dateAndcourseUnit.append(simpleDateFormat.format(calendar.getTime()));
					for (Iterator iter = courseUnitList.iterator(); iter.hasNext();) {
						CourseUnit courseUnitElement = (CourseUnit) iter.next();
						dateAndcourseUnit.append(",");
						dateAndcourseUnit.append(courseUnitElement.getId());
					}
					dateAndcourseUnit.append(",");
					dateAndcourseUnit.append(";");
				}
			}
		}

		request.setAttribute("reStudy", CourseTakeType.RESTUDY);
		request.setAttribute("reExam", CourseTakeType.REEXAM);
		Results.addObject("teachTask", teachTask);
		Results.addObject("stdCourseUnitStatusMap", stdCourseUnitStatusMap);
		Results.addObject("dateAndCourseUnitString", dateAndcourseUnit.toString());
		Results.addObject("dayMap", dayMap);

		return this.forward(mapping, request, "maintainRecordByTeachTask");
	}

	/**
	 * 保存按照课表录入的考勤结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doMaintainRecordByTeachTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long teachTaskId = getLong(request, "teachTaskId");
		boolean methodFlag = true;
		if (methodFlag) {
			String[] changeRecordString = StringUtils.split(request
					.getParameter("changeRecordString"), ";");
			for (int i = 0; i < changeRecordString.length; i++) {
				String[] stdRecordStatusArray = StringUtils.split(changeRecordString[i], ",");
				String statusId = request.getParameter(changeRecordString[i]);
				String[] unitIdArray = StringUtils.split(stdRecordStatusArray[2], "-");
				String beginUnitIdString = unitIdArray[0];
				String endUnitIdString = unitIdArray.length > 1 ? unitIdArray[1] : unitIdArray[0];
				if (StringUtils.isEmpty(statusId)) {
					dutyService.deleteRecordDetail(Long.valueOf(stdRecordStatusArray[0]),
							teachTaskId, java.sql.Date.valueOf(stdRecordStatusArray[1]), new Long(
									beginUnitIdString), new Long(endUnitIdString));
				} else {
					dutyService.updateOrSaveRecordDetail(Long.valueOf(stdRecordStatusArray[0]),
							teachTaskId, java.sql.Date.valueOf(stdRecordStatusArray[1]), new Long(
									beginUnitIdString), new Long(endUnitIdString), new Long(
									statusId));
				}
			}
		} else {
			Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
			String dateAndCourseUnitString = request.getParameter("dateAndCourseUnitString");
			String[] dateAndCourseUnitStringArray = StringUtils.split(dateAndCourseUnitString, ";");
			for (int i = 0; i < dateAndCourseUnitStringArray.length; i++) {
				String[] dateAndCourseUnitArray = StringUtils.split(
						dateAndCourseUnitStringArray[i], ",");
				if (dateAndCourseUnitArray.length > 1) {
					String day = dateAndCourseUnitArray[0];
					for (int j = 1; j < dateAndCourseUnitArray.length; j++) {
						String courseUnitIdString = dateAndCourseUnitArray[j];
						for (int k = 0; k < stdIdArray.length; k++) {
							Long stdId = stdIdArray[k];
							String statusId = request.getParameter("," + stdId + "," + day + ","
									+ courseUnitIdString + ",");
							String[] unitIdArray = StringUtils.split(courseUnitIdString, "-");
							String beginUnitIdString = unitIdArray[0];
							String endUnitIdString = unitIdArray.length > 1 ? unitIdArray[1]
									: unitIdArray[0];
							if (StringUtils.isEmpty(statusId)) {
								dutyService.deleteRecordDetail(stdId, teachTaskId, java.sql.Date
										.valueOf(day), new Long(beginUnitIdString), new Long(
										endUnitIdString));
							} else {
								dutyService.updateOrSaveRecordDetail(stdId, teachTaskId,
										java.sql.Date.valueOf(day), new Long(beginUnitIdString),
										new Long(endUnitIdString), new Long(statusId));
							}
						}
					}
				}

			}
		}

		TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
		Results.addObject("teachTask", teachTask);
		return this.forward(mapping, request, "maintainRecordByTeachTaskSuccess");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward input(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List dutyStatusList = utilService.loadAll(AttendanceType.class);
		Map attendanceTypeMap = new HashMap();
		for (Iterator iter = dutyStatusList.iterator(); iter.hasNext();) {
			AttendanceType element = (AttendanceType) iter.next();
			attendanceTypeMap.put(element.getId(), element);
		}
		Long teachTaskId = Long.valueOf(request.getParameter("teachTaskId"));
		String day = request.getParameter("recordDetail.dutyDate");
		String beginUnitIdString = request.getParameter("recordDetail.beginUnit.id");
		String endUnitIdString = request.getParameter("recordDetail.endUnit.id");
		Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdId"));
		CourseUnit beginUnit = (CourseUnit) utilService.load(CourseUnit.class, new Long(
				beginUnitIdString));
		CourseUnit endUnit = (CourseUnit) utilService.load(CourseUnit.class, new Long(
				endUnitIdString));
		int beginUnitIndex = beginUnit.getIndex().intValue();
		int endUnitIndex = endUnit.getIndex().intValue();
		TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
		Set courseUnitSet = teachTask.getCalendar().getTimeSetting().getCourseUnits();
		List courseUnitList = new ArrayList();
		for (Iterator iter = courseUnitSet.iterator(); iter.hasNext();) {
			CourseUnit courseUnitElement = (CourseUnit) iter.next();
			int index = courseUnitElement.getIndex().intValue();
			if (index >= beginUnitIndex && index <= endUnitIndex) {
				courseUnitList.add(courseUnitElement);
			}
		}

		for (int i = 0; i < stdIdArray.length; i++) {
			String dutyStatusStdNo = request.getParameter("dutyStatus" + stdIdArray[i]);
			if (!StringUtils.isEmpty(dutyStatusStdNo)) {
				for (Iterator iter = courseUnitList.iterator(); iter.hasNext();) {
					CourseUnit element = (CourseUnit) iter.next();
					dutyService.updateOrSaveRecordDetail(stdIdArray[i], teachTaskId, java.sql.Date
							.valueOf(day), element.getId(), element.getId(), new Long(
							dutyStatusStdNo));
				}
			}
		}
		return mapping.findForward("inputRedirector");
	}

	public List getCourseUnitList(Map dayMap, HttpServletRequest request) {
		String dateString = (String) dayMap.get("recordDetail.dutyDate");
		String teachTaskIdString = (String) dayMap.get("teachTaskId");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Long teachTaskId = null;
		if (StringUtils.isNotEmpty(dateString)) {
			try {
				date = simpleDateFormat.parse(dateString);
			} catch (ParseException e) {
				throw new RuntimeException("date ParseException");
			}
		} else {
			return Collections.EMPTY_LIST;
		}
		if (StringUtils.isNotEmpty(teachTaskIdString)) {
			teachTaskId = new Long(teachTaskIdString);
		} else {
			return Collections.EMPTY_LIST;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		simpleDateFormat.setCalendar(calendar);
		List courseUnitList = courseActivityService.getCourseUnits(teachTaskId, new java.sql.Date(
				calendar.getTime().getTime()));
		return courseUnitList;
	}

	/**
	 * @param teachCalendarService
	 *            The teachCalendarService to set.
	 */
	public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

	/**
	 * @param dutyService
	 *            The dutyService to set.
	 */
	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	/**
	 * @param activityOfCourseService
	 *            要设置的 activityOfCourseService.
	 */
	public void setCourseActivityService(CourseActivityService courseActivityService) {
		this.courseActivityService = courseActivityService;
	}

}
