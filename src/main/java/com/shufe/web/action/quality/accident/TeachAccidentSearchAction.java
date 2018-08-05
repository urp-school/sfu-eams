//$Id: TeachAccidentSearchAction.java,v 1.1 2007-7-14 上午11:49:20 chaostone Exp $
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
 * chenweixiong              2007-7-14         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.accident;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.quality.accident.TeachAccident;
import com.shufe.service.quality.accident.TeachAccidentService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 教学事故查询界面响应类
 * 
 * @author chaostone
 * 
 */
public class TeachAccidentSearchAction extends CalendarRestrictionSupportAction {
	protected TeachAccidentService teachAccidentService;

	/**
	 * 根据查询条件得到相应的查询结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EntityQuery query = new EntityQuery(TeachAccident.class,
				"teachAccident");
		populateConditions(request, query);
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		DataRealmUtils.addDataRealms(query, new String[] { "",
				"teachAccident.task.arrangeInfo.teachDepart.id" },
				getDataRealms(request));
		addCollection(request, "teachAccidents", utilService.search(query));
		request.setAttribute("requestAction", RequestUtils
				.getRequestAction(request));
		return forward(request);
	}

	/**
	 * 显示查询条件和显示数据页面. 主要用于管理
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
		setCalendarDataRealm(request, hasStdTypeTeachDepart);
		return forward(request);
	}

	/**
	 * 显示某一个具体的事故的详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward info(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("teachAccidentId");
		TeachAccident teachAccident = (TeachAccident) utilService.load(
				TeachAccident.class, Long.valueOf(id));
		request.setAttribute("teachAccident", teachAccident);
		return forward(request);
	}

	/**
	 * @param teachAccidentService
	 *            The teachAccidentService to set.
	 */
	public void setTeachAccidentService(
			TeachAccidentService teachAccidentService) {
		this.teachAccidentService = teachAccidentService;
	}
}
