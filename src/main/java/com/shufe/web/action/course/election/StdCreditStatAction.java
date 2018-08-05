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
 * chaostone             2006-10-10            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.election;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.election.StdTotalCreditStat;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.course.arrange.task.CourseTakeService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.graduate.StudentSearchAction;

/**
 * 
 * @author 学分统计action
 * 
 */
public class StdCreditStatAction extends StudentSearchAction {

	private CourseTakeService courseTakeService;

	public void setCourseTakeService(CourseTakeService courseTakeService) {
		this.courseTakeService = courseTakeService;
	}

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initSearchBar(form, request);
		initBaseCodes("studentStateList", StudentState.class);
		return forward(request);
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        EntityQuery entityQuery = buildQuery(request);
        addCollection(request, "studentList", utilService.search(entityQuery));
		return forward(request);
	}

	/**
	 * @param form
	 * @param request
	 * @param studentTypeIds
	 * @param departmentIds
	 */
	protected void searchBar(ActionForm form, HttpServletRequest request,
			String studentTypeIds, String departmentIds) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		int pageNo = ((Integer) (request.getAttribute("pageNo") == null ? dynaForm
				.get("pageNo")
				: request.getAttribute("pageNo"))).intValue();
		int pageSize = ((Integer) (request.getAttribute("pageSize") == null ? dynaForm
				.get("pageSize")
				: request.getAttribute("pageSize"))).intValue();
		Pagination stds = studentService.searchStudent(
				populateStudent(request), pageNo, pageSize, studentTypeIds,
				departmentIds);
		addOldPage(request, "studentList", stds);
	}

	/**
	 * populate页面提供的学生数据
	 * 
	 * @param request
	 * @return
	 */
	protected Student populateStudent(HttpServletRequest request) {
		Student student = (Student) populateEntity(request, Student.class,
				"student");
		String adminClasssIdString = request.getParameter("adminClasssId1");
		AdminClass adminClass = null;
		if (StringUtils.isNotEmpty(adminClasssIdString)) {
			adminClass = new AdminClass(Long.valueOf(adminClasssIdString));
		}
		Set adminClassSet = new HashSet();
		adminClassSet.add(adminClass);
		student.setAdminClasses(adminClassSet);
		return student;
	}

	public ActionForward viewDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long stdId = getLong(request, "stdId");
		// 学生上课记录
		List courseTakeList = courseTakeService
				.getCourseTakes((Student) utilService.get(Student.class, stdId));
		addCollection(request, "courseTakeList", courseTakeList);
		EntityQuery query = new EntityQuery(CourseTake.class, "courseTake");
		query.add(new Condition("courseTake.student.id in (:stdId)", stdId));
		query.groupBy("courseTake.task.calendar.id");
		query.setSelect("max(courseTake.student), max(courseTake.task.calendar), sum(courseTake.task.course.credits)");
		query.addOrder(new Order("courseTake.task.calendar",Order.ASC));
		Collection stdCreditTotalList = utilService.search(query);
		addCollection(request, "stdCreditTotalList",
				stdCreditTotalList);
		return forward(request);
	}
	
	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(CourseTake.class, "courseTake");
		populateConditions(request, query, "courseTake.student.type.id");
		DataRealmUtils.addDataRealms(query, new String[] { "courseTake.student.type.id",
				"courseTake.student.department.id" }, restrictionHelper.getDataRealmsWith(
				getLong(request, "courseTake.student.type.id"), request));
        query.setSelect("distinct courseTake.student");
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		return query;
	}

	protected Collection getExportDatas(HttpServletRequest request) {
		Long[] stdIds = SeqStringUtil.transformToLong(get(request, "stdIds"));
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        if (null != stdIds && stdIds.length != 0) {
            query.add(new Condition("courseTake.student.id in (:stdIds)", stdIds));
        }
        query.setSelect("new com.shufe.model.course.election.StdTotalCreditStat(courseTake.student.id, sum(courseTake.task.course.credits))");
        query.groupBy("courseTake.student.id");
        Collection list = utilService.search(query);
        fillStudent(list);
        return list;

	}
	
	/**
	 * 组建StdTotalCreditStat对象
	 * 
	 * @param accounts
	 */
	private void fillStudent(Collection accounts) {
		for (Iterator it = accounts.iterator(); it.hasNext();) {
			StdTotalCreditStat stat = (StdTotalCreditStat) it.next();
			stat.setStd((Student) utilService.get(Student.class, stat.getStd().getId()));
		}
	}

}
