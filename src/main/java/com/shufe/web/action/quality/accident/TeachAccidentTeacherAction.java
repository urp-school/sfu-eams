//$Id: TeachAccidentTeacherAction.java,v 1.1 2007-7-14 上午11:51:32 chaostone Exp $
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

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.quality.accident.TeachAccident;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.quality.accident.TeachAccidentService;

/**
 * 教学事故教师查询界面响应类
 * 
 * @author chaostone
 * 
 */
public class TeachAccidentTeacherAction extends TeachAccidentSearchAction {

	/**
	 * 为了老师的查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Teacher teacher = (Teacher) getTeacherFromSession(request.getSession());

		EntityQuery query = new EntityQuery(TeachAccident.class,
				"teachAccident");
		query.add(new Condition("teachAccident.teacher.id=:teacherId", teacher
				.getId()));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "teachAccidents", utilService.search(query));
		request.setAttribute("requestAction", RequestUtils
				.getRequestAction(request));
		return forward(request);
	}

	public void setTeachAccidentService(
			TeachAccidentService teachAccidentService) {
		this.teachAccidentService = teachAccidentService;
	}

}
