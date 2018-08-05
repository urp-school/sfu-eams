//$Id: BookRequireStatAction.java,v 1.1 2007-3-13 上午10:28:06 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-13         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.textbook;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.Press;
import com.ekingstar.eams.system.basecode.industry.TextbookAwardLevel;
import com.shufe.model.course.textbook.Textbook;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.textbook.BookRequireStatService;
import com.shufe.service.util.stat.StatItemComparator;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 教材需求统计
 * @author chaostone
 *
 */
public class BookRequireStatAction extends CalendarRestrictionSupportAction {

	private BookRequireStatService bookRequireStatService;

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setCalendarDataRealm(request, hasStdTypeDepart);
		return forward(request);
	}

	public ActionForward statBy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeachCalendar calendar = (TeachCalendar) utilService.get(
				TeachCalendar.class, getLong(request,
						"require.task.calendar.id"));
		List stats = null;
		String kind = request.getParameter("kind");
		if ("press".equals(kind)) {
			stats = bookRequireStatService.statRequireBy(calendar,
					getDataRealm(request), "require.textbook.press.id",
					Press.class);
		} else if ("awardLevel".equals(kind)) {
			stats = bookRequireStatService.statRequireBy(calendar,
					getDataRealm(request), "require.textbook.awardLevel.id",
					TextbookAwardLevel.class);
		} else if ("teachDepart".equals(kind)) {
			stats = bookRequireStatService
					.statRequireBy(calendar, getDataRealm(request),
							"require.task.arrangeInfo.teachDepart.id",
							Department.class);
		} else if ("courseType".equals(kind)) {
			stats = bookRequireStatService.statRequireBy(calendar,
					getDataRealm(request), "require.task.courseType.id",
					CourseType.class);
		} else if ("textBook".equals(kind)) {
            stats = bookRequireStatService.statRequireBy(calendar,
                    getDataRealm(request), "require.textbook.id",
                    Textbook.class);
        }else {
			throw new RuntimeException("unsuported task stat kind [" + kind
					+ "]!");
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
        if ("textBook".equals(kind)) {
            return forward(request, "statListWithTextBook");
        }
		return forward(request, "statList");
	}

	public ActionForward statCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeachCalendar calendar = (TeachCalendar) utilService.get(
				TeachCalendar.class, getLong(request,
						"require.task.calendar.id"));
		List stats = null;
		String kind = request.getParameter("kind");
		if ("teachDepart".equals(kind)) {
			stats = bookRequireStatService
					.statCheckBy(calendar, getDataRealm(request),
							"require.task.arrangeInfo.teachDepart.id",
							Department.class);
		} else if ("textBook".equals(kind)) {
            stats = bookRequireStatService
                    .statCheckBy(calendar, getDataRealm(request),
                            "require.textbook.id",
                            Textbook.class);
        }else {
			throw new RuntimeException("unsuported task stat kind [" + kind
					+ "]!");
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
        if ("textBook".equals(kind)) {
            return forward(request, "statCheckWithTextBook");
        }
		return forward(request, "statCheck");
	}

	public void setBookRequireStatService(
			BookRequireStatService bookRequireStatService) {
		this.bookRequireStatService = bookRequireStatService;
	}

}
