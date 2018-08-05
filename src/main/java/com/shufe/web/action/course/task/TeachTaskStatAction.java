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
 * ============         ============        ====================================
 * chaostone            2006-04-06          Created
 * zq                   2007-09-14          见 FIXME 处，因为一个teachTaskStatService
 *                                          所调用的方法有多个查询，且没有 request ，所以
 *                                          addStdTypeTreeDataRealm()方法无处添加，故
 *                                          没有修改；
 ********************************************************************************/
package com.shufe.web.action.course.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.data.general.DefaultPieDataset;

import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.course.task.TeachTaskStatService;
import com.shufe.service.util.stat.CountItem;
import com.shufe.service.util.stat.GeneralDatasetProducer;
import com.shufe.service.util.stat.StatGroup;
import com.shufe.service.util.stat.StatItemComparator;

/**
 * 教学任务统计界面响应类
 * 
 * @author chaostone
 * 
 */
public class TeachTaskStatAction extends TeachTaskSearchAction {
	TeachTaskService teachTaskService;

	TeachTaskStatService teachTaskStatService;

	/**
	 * 教学任务统计
	 * 
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
		request.setAttribute("kind", request.getParameter("kind"));
		return forward(request);
	}

	// 按照课程类型
	public ActionForward statByCourseType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, getLong(
				request, "teachTask.calendar.id"));

		List courseTypes = teachTaskService.getCourseTypesOfTask(getStdTypeIdSeq(request),
				getDepartmentIdSeq(request), calendar);

		List countResult = new ArrayList();
		for (Iterator iter = courseTypes.iterator(); iter.hasNext();) {
			CourseType courseType = (CourseType) iter.next();
			countResult.add(new CountItem(new Integer(utilService.count(TeachTask.class,
					new String[] { "calendar.id", "courseType.id" }, new Object[] {
							calendar.getId(), courseType.getId() }, null)), courseType));
		}
		Collections.sort(countResult);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Iterator iter = countResult.iterator(); iter.hasNext();) {
			CountItem item = (CountItem) iter.next();
			dataset.setValue(((CourseType) item.getWhat()).getName(), item.getCount().intValue());
		}
		request.setAttribute("pageViews", new GeneralDatasetProducer("test", dataset));
		addCollection(request, "countResult", countResult);
		return forward(request);
	}

	/**
	 * 挂牌课程列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gpList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeachTask task = (TeachTask) populate(request, TeachTask.class);
		task.getRequirement().setIsGuaPai(Boolean.TRUE);
		Pagination tasks = teachTaskService.getTeachTasksOfTeachDepart(task,
				getStdTypeIdSeq(request), getDepartmentIdSeq(request), 1, Integer.MAX_VALUE);
		addCollection(request, "tasks", tasks.getItems());
		return forward(request);
	}

	/**
	 * 合班汇总
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward multiClassTaskList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TeachTask task = (TeachTask) populate(request, TeachTask.class);
		Collection tasks = teachTaskService.getTeachTasksWithMultiClass(task,
				getStdTypeIdSeq(request), getDepartmentIdSeq(request));
		addCollection(request, "tasks", tasks);
		return forward(request);
	}

	/**
	 * 统计班级在单个学年度的上课学分,课时,总课时
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, getLong(
				request, "teachTask.calendar.id"));
		List stats = null;
		String kind = request.getParameter("kind");
		// FIXME here
		if ("class".equals(kind)) {
			stats = teachTaskStatService.statClassTask(calendar, getDataRealmLimit(request)
					.getDataRealm());
		} else if ("teacher".equals(kind)) {
			stats = teachTaskStatService.statTeacherTask(calendar, getDataRealmLimit(request)
					.getDataRealm());
		} else if ("courseType".equals(kind)) {
			stats = teachTaskStatService.statCourseTypeTask(calendar, getDataRealmLimit(request)
					.getDataRealm());
		} else if ("depart".equals(kind)) {
			stats = teachTaskStatService.statDepartTask(calendar, getDataRealmLimit(request)
					.getDataRealm());
		} else if ("teachDepart".equals(kind)) {
			stats = teachTaskStatService.statTeachDepartTask(calendar, getDataRealmLimit(request)
					.getDataRealm());
		} else if ("studentType".equals(kind)) {
			stats = teachTaskStatService.statStdTypeTask(calendar, getDataRealmLimit(request)
					.getDataRealm());
		} else {
			throw new RuntimeException("unsuported task stat kind [" + kind + "]!");
		}
		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "what.name";
		}
		List orders = OrderUtils.parser(orderBy);
		if (!orders.isEmpty()) {
			Order order = (Order) orders.get(0);
			if (!"null".equals(order.getProperty())) {
				StatItemComparator comparator = new StatItemComparator(order);
				Collections.sort(stats, comparator);
			}
		}
		addCollection(request, "stats", stats);
		request.setAttribute("kind", kind);
		return forward(request);
	}

	/**
	 * 统计教学任务确认情况
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, getLong(
				request, "teachTask.calendar.id"));
		List stats = null;
		String kind = request.getParameter("kind");
		if ("teachDepart".equals(kind)) {
			stats = teachTaskStatService.statTeachDepartConfirm(calendar,
					getDataRealmLimit(request).getDataRealm());
		} else if ("courseType".equals(kind)) {
			stats = teachTaskStatService.statCourseTypeConfirm(calendar, getDataRealmLimit(request)
					.getDataRealm());
		} else {
			throw new RuntimeException("unsuported confirm stat kind [" + kind + "]!");
		}
		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "what.name";
		}
		List orders = OrderUtils.parser(orderBy);
		if (!orders.isEmpty()) {
			Order order = (Order) orders.get(0);
			if (!"null".equals(order.getProperty())) {
				StatItemComparator comparator = new StatItemComparator(order);
				Collections.sort(stats, comparator);
			}
		}
		addCollection(request, "stats", stats);
		return forward(request);
	}

	/**
	 * 统计教学任务双语教学情况
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statBilingual(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, getLong(
				request, "teachTask.calendar.id"));
		StatGroup group = teachTaskStatService.statBilingual(calendar, getDataRealm(request));
		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "countors[0] desc";
		}
		List orders = OrderUtils.parser(orderBy);
		if (!orders.isEmpty()) {
			Order order = (Order) orders.get(0);
			if (!"null".equals(order.getProperty())) {
				StatItemComparator comparator = new StatItemComparator(order);
				Collections.sort(group.getItems(), comparator);
			}
		}
		request.setAttribute("stat", group);
		return forward(request);
	}

	/**
	 * 统计教学任务上课教室类型情况
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statRoomConfigType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, getLong(
				request, "teachTask.calendar.id"));
		StatGroup group = teachTaskStatService.statRoomConfigType(calendar, getDataRealm(request));
		request.setAttribute("stat", group);
		return forward(request);
	}

	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

	public void setTeachTaskStatService(TeachTaskStatService teachTaskStatService) {
		this.teachTaskStatService = teachTaskStatService;
	}

}
