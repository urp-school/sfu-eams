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
 * chaostone             2006-12-2            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.web.helper.BaseInfoSearchHelper;

public class ClassResourceAction extends ResourceAction {
	private AdminClassService adminClassService;

	private BaseInfoSearchHelper baseInfoSearchHelper;

	/**
	 * 班级占用情况查询注界面
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
		setCalendarDataRealm(request, hasStdTypeCollege);
		return forward(request);
	}

	/**
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
		addCollection(request, "adminClasses", baseInfoSearchHelper
				.searchAdminClass(request));
		return forward(request);
	}

	/**
	 * @param adminClassService
	 *            The adminClassService to set.
	 */
	public void setAdminClassService(AdminClassService adminClassService) {
		this.adminClassService = adminClassService;
	}

	protected List getOccupyInfos(Object id, Long validWeeksNum, Integer year) {
		return teachResourceService.getAdminClassOccupyInfos((Long) id,
				validWeeksNum, year);
	}

	protected List getActivities(Object id, TimeUnit timeUnit) {
		return teachResourceService.getAdminClassActivities((Long) id, timeUnit,
				Activity.class);
	}

	protected Entity getResourceEntity(HttpServletRequest request) {
		Long adminClassId = getLong(request, Constants.ADMINCLASS_KEY);
		if (!ValidEntityKeyPredicate.INSTANCE.evaluate(adminClassId)) {
			return null;
		} else {
			return adminClassService.getAdminClass(adminClassId);
		}
	}

	protected List getResourceList(HttpServletRequest request) {
		String adminClassIds = request.getParameter("adminClassIds");
		return utilService.load(AdminClass.class, "id", SeqStringUtil
				.transformToLong(adminClassIds));
	}

	public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
		this.baseInfoSearchHelper = baseInfoSearchHelper;
	}

}
