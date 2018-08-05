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
 * chaostone             2006-3-26            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.selector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.OldPagination;
import com.shufe.model.Constants;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.system.baseinfo.StudentTypeService;
import com.shufe.web.action.common.DispatchBasicAction;

public class CourseSelector extends DispatchBasicAction {

	StudentTypeService studentTypeService;

	/**
	 * 查找课程
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
		String pageNo = request.getParameter("pageNo");
		EntityQuery query = new EntityQuery(Course.class, "course");
		QueryRequestSupport.populateConditions(request, query);
		if (StringUtils.isEmpty(pageNo)) {
			request.setAttribute(Constants.COURSE_LIST, utilService.search(query));
		} else {
			query.setLimit(getPageLimit(request));
			addOldPage(request, Constants.COURSE_LIST, OldPagination.buildOldPagination((SinglePage) utilService.search(query)));
		}
		request.setAttribute("stdTypeList", baseInfoService.getBaseInfos(StudentType.class));
		return forward(mapping, request, "list");

	}

	/**
	 * @param studenTypeService
	 *            The studenTypeService to set.
	 */
	public void setStudentTypeService(StudentTypeService studentTypeService) {
		this.studentTypeService = studentTypeService;
	}

}
