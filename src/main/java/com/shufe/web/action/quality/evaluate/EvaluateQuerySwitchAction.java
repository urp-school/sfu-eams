//$Id: EvaluateQuerySwitchAction.java,v 1.1 2007-6-4 9:45:31 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong         2007-06-04          Created
 * zq                   2007-09-18          修改了search()方法中的学生类别查询；
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.quality.evaluate.EvaluateQuerySwitch;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

public class EvaluateQuerySwitchAction extends RestrictionSupportAction {

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List teachCalendars = utilService.load(TeachCalendar.class, "studentType.id",
				SeqStringUtil.transformToLong(getStdTypeIdSeq(request)));
		Date date = new Date(System.currentTimeMillis());
		for (int i = teachCalendars.size() - 1; i >= 0; i--) {
			TeachCalendar element = (TeachCalendar) teachCalendars.get(i);
			if (element.getStart().after(date)) {
				teachCalendars.remove(i);
			}
		}
		List queryButtons = utilService.load(EvaluateQuerySwitch.class, "teachCalendar",
				teachCalendars);
		Map tempMap = new HashMap();
		for (Iterator iter = queryButtons.iterator(); iter.hasNext();) {
			EvaluateQuerySwitch element = (EvaluateQuerySwitch) iter.next();
			tempMap.put(element.getTeachCalendar().getId(), element);
		}
		List tempButtons = new ArrayList();
		for (Iterator iter = teachCalendars.iterator(); iter.hasNext();) {
			TeachCalendar element = (TeachCalendar) iter.next();
			if (!tempMap.containsKey(element.getId())) {
				EvaluateQuerySwitch button = new EvaluateQuerySwitch(element);
				tempButtons.add(button);
			}
		}
		utilService.saveOrUpdate(tempButtons);
		return redirect(request, "index", "");
	}

	/**
	 * 进入评教查询开关界面
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setDataRealm(request, hasStdTypeDepart);
		return forward(request);
	}

	/**
	 * 评教查询的开关添加页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EntityQuery entityQuery = new EntityQuery(EvaluateQuerySwitch.class, "switch");
		populateConditions(request, entityQuery, "switch.teachCalendar.studentType.id");
		DataRealmUtils.addDataRealms(entityQuery, new String[] {
				"switch.teachCalendar.studentType.id", "" }, getDataRealmsWith(getLong(request,
				"switch.teachCalendar.studentType.id"), request));
		// entityQuery.add(new Condition("switch.teachCalendar.studentType.id in
		// (:typIds)",
		// SeqStringUtil.transformToLong(getStdTypeIdSeq(request))));
		entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		entityQuery.setLimit(getPageLimit(request));
		addCollection(request, "querySwitchs", utilService.search(entityQuery));
		request.setAttribute("isOpen", request.getParameter("switch.isOpen"));
		return forward(request);
	}

	/**
	 * 评教查询开关
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward operate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean state = Boolean.valueOf(request.getParameter("isOpen"));
		String isAll = request.getParameter("isAll");
		Date date = new Date(System.currentTimeMillis());
		Collection querySwitchs = new ArrayList();
		if ("select".equals(isAll)) {
			String idSeq = request.getParameter("idSeq");
			querySwitchs = utilService.load(EvaluateQuerySwitch.class, "id", SeqStringUtil
					.transformToLong(idSeq));
		} else {
			EntityQuery entityQuery = new EntityQuery(EvaluateQuerySwitch.class, "switch");
			populateConditions(request, entityQuery);
			querySwitchs = utilService.search(entityQuery);
		}
		for (Iterator iter = querySwitchs.iterator(); iter.hasNext();) {
			EvaluateQuerySwitch element = (EvaluateQuerySwitch) iter.next();
			element.setIsOpen(state);
			element.setOpenAt(date);
		}
		utilService.saveOrUpdate(querySwitchs);
		return redirect(request, "search", "info.action.success");
	}
}
