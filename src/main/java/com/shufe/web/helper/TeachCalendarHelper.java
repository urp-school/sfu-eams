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
 * chaostone             2006-12-30            Created
 *  
 ********************************************************************************/
package com.shufe.web.helper;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.commons.utils.web.CookieUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.CalendarNotExistException;
import com.ekingstar.eams.system.time.TeachCalendarScheme;
import com.shufe.model.Constants;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.RequestUtil;

public class TeachCalendarHelper {

	public TeachCalendarHelper() {
		super();
	}

	public TeachCalendarHelper(TeachCalendarService teachCalendarService,
			UtilService utilService) {
		setTeachCalendarService(teachCalendarService);
		setUtilService(utilService);
	}

	TeachCalendarService teachCalendarService;

	UtilService utilService;

	/**
	 * 设置学年学期<br>
	 * 如果参数中有calendar.id则设置该学年学期，否则默认采用第一方案中的当前学期
	 * 
	 * @param request
	 * @return TODO
	 */
	public TeachCalendar setNewCalendar(HttpServletRequest request) {
		List schemes = utilService.loadAll(TeachCalendarScheme.class);
		TeachCalendarScheme scheme = (TeachCalendarScheme) schemes.get(0);
		request.setAttribute("calendarSchemes", schemes);
		Long calendarId = RequestUtils.getLong(request, "calendar.id");
		TeachCalendar calendar = null;
		if (null == calendarId) {
			calendar = (TeachCalendar) scheme.getCalendars().get(0);
		} else {
			calendar = teachCalendarService.getTeachCalendar(calendarId);
		}
		request.setAttribute(Constants.CALENDAR, calendar);
		return calendar;
	}

	/**
	 * 
	 * 加载时间日历 根据学生类别查找最近的教学日历.<br>
	 * 如果cookie中保留,则采用cookie中的日历.<br>
	 * 系统检查cookie中的学生类别是否在权限范围,主要检查"calendar.studentType.id"的值,<br>
	 * 是否在request.getAttribute("stdTypeList")中<br>
	 * 
	 * @param request
	 * @param stdType
	 *            建议使用的学生类别
	 */
	public void setCalendar(HttpServletRequest request, StudentType stdType) {
		// 从request的参数中查询
		TeachCalendar calendar = null;
		List stdTypeList = (List) request.getAttribute("stdTypeList");
		TeachCalendar example = (TeachCalendar) RequestUtil.populate(request,
				TeachCalendar.class, Constants.CALENDAR);
		if (null != example.getStudentType()
				&& StringUtils.isNotEmpty(example.getYear())
				&& StringUtils.isNotEmpty(example.getTerm())) {
			calendar = teachCalendarService.getTeachCalendar(example
					.getStudentType(), example.getYear(), example.getTerm());
		}

		// 查找日历中的学生类别
		String stdTypeId = request.getParameter("calendar.studentType.id");
		if (StringUtils.isEmpty(stdTypeId)) {
			if (null == stdType) {
				stdType = (StudentType) stdTypeList.get(0);
			}
		} else {
			stdType = (StudentType) utilService.get(StudentType.class, Long
					.valueOf(stdTypeId));
		}

		// 如果参数中没有,试图从cookie中获得
		if (null == calendar) {
			stdTypeId = CookieUtils.getCookieValue(request,
					"calendar.studentType.id");
			try {
				if (null != stdTypeList) {
					boolean inAuth = false;
					for (Iterator iter = stdTypeList.iterator(); iter.hasNext();) {
						StudentType one = (StudentType) iter.next();
						if (one.getId().toString().equals(stdTypeId)) {
							inAuth = true;
							break;
						}
					}
					if (inAuth) {
						String year = CookieUtils.getCookieValue(request,
								"calendar.year");
						String term = CookieUtils.getCookieValue(request,
								"calendar.term");
						if (StringUtils.isNotEmpty(stdTypeId)
								&& StringUtils.isNotEmpty(year)
								&& StringUtils.isNotEmpty(term)) {
							calendar = teachCalendarService.getTeachCalendar(
									Long.valueOf(stdTypeId), year, term);
							// 重置学生类别
							stdType = (StudentType) utilService.get(
									StudentType.class, Long.valueOf(stdTypeId));
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 直接查找最近的日历
		if (null == calendar) {
			utilService.initialize(stdType.getSubTypes());
			calendar = teachCalendarService.getNearestCalendar(stdType);
		}
		request.setAttribute("studentType", stdType);
		if (null == calendar) {
			throw new CalendarNotExistException(stdType.getName() + "教学日历不存在！");
		}
		request.setAttribute(Constants.CALENDAR, calendar);
	}

	public void setTeachCalendarService(
			TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

}
