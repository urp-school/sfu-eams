//$Id: ThesisManageTutorAction.java,v 1.1 2007-5-10 11:43:06 Administrator Exp $
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
 * chenweixiong              2007-5-10         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.thesis.ThesisManage;

public class ThesisManageTutorAction extends ThesisManageAction {

	/**
	 * 得到论文管理的查询页面
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
		request.setAttribute("departmentList", departmentService.getColleges());
		request.setAttribute("stdTypeList", baseInfoService.getBaseInfos(StudentType.class));
		request.setAttribute("teacher", getTeacherFromSession(request
				.getSession()));
		return forward(request);
	}

	/**
	 * 根据条件查询对应的信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityQuery entityQuery = new EntityQuery(ThesisManage.class,
				"thesisManage");
		populateConditions(request, entityQuery);
		entityQuery
				.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		entityQuery.setLimit(getPageLimit(request));
		addCollection(request, "thesisManages", utilService.search(entityQuery));
		return forward(request, "thesisManageList");
	}
}
