//$Id: StudyProductStdAction.java,v 1.1 2007-3-6 14:18:50 Administrator Exp $
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
 * chenweixiong              2007-3-6         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.study;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.degree.study.StudyProduct;

public class StudyProductStdAction extends StudyProductHelper {

	/**
	 * 得到学生列表页面
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
		return forward(request);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("student", getStudentFromSession(request
				.getSession()));
		super.edit(mapping, form, request, response);
		String productType = request.getParameter("productType");
		return forward(request, "../studyProduct/" + productType + "Form");
	}

	/**
	 * 查询学生自己的科研成果信息
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
		String productType = request.getParameter("productType");
		request.setAttribute("productType", productType);
		EntityQuery query = new EntityQuery(StudyProduct
				.getStudyProductType(productType), "studyProduct");
		populateConditions(request, query);
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		query.add(new Condition("studyProduct.student.id="
				+ getStudentFromSession(request.getSession()).getId()));
		addCollection(request, "studyProducts", utilService.search(query));
		return forward(request, "studyProductList");
	}

	public ActionForward saveAward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return forward(request, new Action(StudyAwardAction.class, "save"));
	}

	/* (non-Javadoc)
	 * @see com.shufe.web.action.degree.study.StudyProductHelper#configExportContext(javax.servlet.http.HttpServletRequest, com.ekingstar.commons.transfer.exporter.Context)
	 */
	protected void configExportContext(HttpServletRequest request,
			Context context) {
		setExportDatas(request, context, getStudentFromSession(request.getSession()), Boolean.TRUE);
	}
	
}
