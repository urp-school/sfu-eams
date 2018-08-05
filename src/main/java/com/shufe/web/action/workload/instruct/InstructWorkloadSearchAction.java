//$Id: GraduateStatSearchAction.java,v 1.1 2007-3-29 15:49:26 Administrator Exp $
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
 * chenweixiong         2007-03-29          Created
 * zq                   2007-09-14          在search()中，修改了populateConditions(...)，
 *                                          方法，添加了addStdTypeTreeDataRealm(...)方法；
 ********************************************************************************/

package com.shufe.web.action.workload.instruct;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.shufe.model.workload.instruct.InstructWorkload;
import com.shufe.service.workload.instruct.InstructWorkloadService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class InstructWorkloadSearchAction extends CalendarRestrictionSupportAction {
	protected InstructWorkloadService instructWorkloadService;

	/**
	 * @param instructWorkloadService
	 *            The instructWorkloadService to set.
	 */
	public void setInstructWorkloadService(InstructWorkloadService instructWorkloadService) {
		this.instructWorkloadService = instructWorkloadService;
	}

	/**
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setDataRealm(request, hasStdTypeDepart);
		request.setAttribute("genderList", baseCodeService.getCodes(Gender.class));
		request.setAttribute("teacherTitleList", baseCodeService.getCodes(TeacherTitle.class));
		request.setAttribute("eduDegreeList", baseCodeService.getCodes(EduDegree.class));
		request.setAttribute("teacherTypeList", baseCodeService.getCodes(TeacherType.class));
		return forward(request);
	}

	/**
	 * 查询
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
		EntityQuery query = new EntityQuery(InstructWorkload.class, "instructWorkload");
		populateConditions(request, query, "instructWorkload.studentType.id");
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		query.setLimit(getPageLimit(request));

		DataRealmUtils.addDataRealms(query, new String[] { "instructWorkload.studentType.id",
				"instructWorkload.teacherInfo.teachDepart.id" }, getDataRealmsWith(getLong(request,
				"instructWorkload.studentType.id"), request));
		addCollection(request, "instructWorkloads", utilService.search(query));
		return forward(request);
	}

	/**
	 * 查看
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward loadDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("instructWorkload", utilService.load(InstructWorkload.class,
				getLong(request, "instructWorkloadId")));
		return forward(request, "detail");
	}

	/**
	 * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
	 */
	protected Collection getExportDatas(HttpServletRequest request) {
		EntityQuery entityQuery = new EntityQuery(InstructWorkload.class, "instructWorkload");
		populateConditions(request, entityQuery);
		DataRealmUtils.addDataRealms(entityQuery,
				new String[] { "instructWorkload.studentType.id" }, getDataRealms(request));
		return utilService.search(entityQuery);
	}
}
