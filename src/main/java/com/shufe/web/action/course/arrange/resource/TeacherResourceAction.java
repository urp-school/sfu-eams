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
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.system.baseinfo.TeacherService;

public class TeacherResourceAction extends ResourceAction {
	private TeacherService teacherService;

	/**
	 * 教师占用查询主界面
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
	 * 加载查询教师
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
		EntityQuery entityQuery = new EntityQuery(Teacher.class, "teacher");
		QueryRequestSupport.populateConditions(request, entityQuery);
		entityQuery.add(new Condition("teacher.department in(:departs)",
				getDeparts(request)));
		entityQuery.setLimit(getPageLimit(request));
		entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "teachers", utilService.search(entityQuery));
		return forward(request);
	}

	protected List getActivities(Object id, TimeUnit timeUnit) {
		return teachResourceService.getTeacherActivities((Long) id, timeUnit,
				Activity.class, null);
	}

	protected List getOccupyInfos(Object id, Long validWeeksNum, Integer year) {
		return teachResourceService.getTeacherOccupyInfos((Long) id, validWeeksNum,
				year);
	}

	protected Entity getResourceEntity(HttpServletRequest request) {
		Long teacherId = getLong(request, "teacher.id");
		return teacherService.getTeacherById(teacherId);
	}

	protected List getResourceList(HttpServletRequest request) {
		String teacherIds = request.getParameter("teacherIds");
		return teacherService.getTeachersById(SeqStringUtil
				.transformToLong(teacherIds));
	}

	/**
	 * @param teacherService
	 *            The teacherService to set.
	 */
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
}
