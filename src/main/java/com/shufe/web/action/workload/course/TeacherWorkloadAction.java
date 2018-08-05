//$Id: TeacherWorkloadAction.java,v 1.1 2007-3-30 13:36:06 Administrator Exp $
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
 * chenweixiong              2007-3-30         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.security.DataRealm;
import com.shufe.model.workload.Workload;
import com.shufe.model.workload.course.TeachWorkload;
import com.shufe.model.workload.instruct.InstructWorkload;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

public class TeacherWorkloadAction extends RestrictionSupportAction {

	/**
	 * 教师查询主界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Teacher teacher = getTeacherFromSession(request.getSession());
		EntityQuery query = new EntityQuery(Workload.class, "workload");
		query.setSelect("distinct workload.studentType");
		query.add(new Condition("workload.teacherInfo.teacher.id=:teacherId", teacher.getId()));
		List stdTypeList = (List) utilService.search(query);

		request.setAttribute("stdTypeList", stdTypeList);
		return forward(request);
	}

	/**
	 * 得到教师对应的详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Teacher teacher = getTeacherFromSession(request.getSession());
		EntityQuery query = new EntityQuery(Workload.class, "workload");
		query.setFrom("Workload workload,WorkloadOpenSwitch openSwitch");
		query.add(new Condition(
				"workload.teachCalendar=openSwitch.teachCalendar and openSwitch.isOpen=true"));
		query.add(new Condition("workload.teacherInfo.teacher.id=:teacherId", teacher.getId()));
		populateConditions(request, query, "workload.studentType.id");
		Long stdTypeId = getLong(request, "workload.studentType.id");
		if (null != stdTypeId) {
			DataRealmUtils.addDataRealm(query, new String[] { "workload.studentType.id", null },
					new DataRealm(studentTypeService.getStdTypeIdSeqUnder(stdTypeId), null));
		}
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "workloads", utilService.search(query));
		return forward(request);
	}

	/**
	 * 教师确认自己的工作量
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward affirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String workloadIds = request.getParameter("workloadIds");
		String estate = request.getParameter("estate");
		Map updateMap = new HashMap();
		updateMap.put("teacherAffirm", Boolean.valueOf(estate));
		utilService.update(Workload.class, "id", SeqStringUtil.transformToLong(workloadIds),
				updateMap);
		return redirect(request, "search", "info.action.success");
	}

	/**
	 * 查看单个统计工作量的详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Long teachWorkloadId = getLong(request, "workloadId");
		Workload workload = (Workload) utilService.load(Workload.class, teachWorkloadId);
		if (Boolean.TRUE.equals(workload.getIsTeachWorkload())) {
			workload = (TeachWorkload) utilService.load(TeachWorkload.class, teachWorkloadId);
		} else {
			workload = (InstructWorkload) utilService.load(InstructWorkload.class, teachWorkloadId);
		}
		request.setAttribute("workload", workload);
		if (workload instanceof TeachWorkload) {
			return forward(request, "teachWorkloadInfo");
		} else if (workload instanceof InstructWorkload) {
			return forward(request, "instructWorkloadInfo");
		} else {
			return forward(request, "teachWorkloadInfo");
		}
	}

}
