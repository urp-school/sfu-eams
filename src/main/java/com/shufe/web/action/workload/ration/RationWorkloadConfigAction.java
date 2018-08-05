//$Id: ConfigRationWorkloadAction.java,v 1.2 2006/08/18 03:25:59 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-16         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.ration;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.ration.RationWorkload;
import com.shufe.model.workload.ration.RationWorkloadConfig;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.service.workload.ration.RationWorkloadConfigService;
import com.shufe.service.workload.ration.RationWorkloadService;
import com.shufe.web.action.common.RestrictionSupportAction;

public class RationWorkloadConfigAction extends RestrictionSupportAction {

	/**
	 * <code>configRationWorkloadService<code>
	 *configRationWorkloadService ConfigRationWorkloadService
	 */
	private RationWorkloadConfigService rationWorkloadConfigService;

	private RationWorkloadService rationWorkloadService;

	private TeachCalendarService teachCalendarService;

	/**
	 * @param teachCalendarService
	 *            The teachCalendarService to set.
	 */
	public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
		this.teachCalendarService = teachCalendarService;
	}

	/**
	 * @param rationWorkloadService
	 *            The rationWorkloadService to set.
	 */
	public void setRationWorkloadService(RationWorkloadService rationWorkloadService) {
		this.rationWorkloadService = rationWorkloadService;
	}

	/**
	 * @param configRationWorkloadService
	 *            The configRationWorkloadService to set.
	 */
	public void setRationWorkloadConfigService(
			RationWorkloadConfigService configRationWorkloadService) {
		this.rationWorkloadConfigService = configRationWorkloadService;
	}

	/**
	 * 得到设置页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doLoadConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String departmentIds = getDepartmentIdSeq(request);
		List collegeList = departmentService.getColleges(departmentIds);
		String studentTypeIds = getStdTypeIdSeq(request);
		List studentTypeList = studentTypeService.getStudentTypes(studentTypeIds);
		List rationWorkloadList = rationWorkloadService.getRationWorkloadList(departmentIds);
		Results.addList("collegeList", collegeList).addList("studentTypeList", studentTypeList)
				.addList("rationWorkloadList", rationWorkloadList);
		return forward(request);
	}

	/**
	 * 把相应的陪住数据放入数据库
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long studentTypeId = Long.valueOf(request.getParameter("studentTypeId"));
		TeachCalendar teachCalendar = teachCalendarService.getCurTeachCalendar(studentTypeId);
		String departmentIds = getDepartmentIdSeq(request);
		List collegeList = departmentService.getColleges(departmentIds);
		for (int i = 0; i < collegeList.size(); i++) {
			RationWorkloadConfig configRationWorkload = new RationWorkloadConfig();
			Department college = (Department) collegeList.get(i);
			String rationWorkloadId = request.getParameter("rationWorkloadId" + i);
			RationWorkload rationWorkload = (RationWorkload) utilService.load(RationWorkload.class,
					Long.valueOf(rationWorkloadId));
			List teacherList = rationWorkloadConfigService.getTeacherCriteria(college.getId())
					.list();
			for (int j = 0; j < teacherList.size(); j++) {
				Teacher teacher = (Teacher) teacherList.get(j);
				teacher.setRatingWorkload(new Float(rationWorkload.getValue().intValue()));
			}
			configRationWorkload.setConfigTime(new Date(System.currentTimeMillis()));
			configRationWorkload.setDepartment(college);
			configRationWorkload.setRationWorkload(rationWorkload);
			configRationWorkload.setTeachCalendar(teachCalendar);
			utilService.saveOrUpdate(configRationWorkload);
		}
		return this.forward(mapping, request, "field.configRationWorkload.errorsRationWorkload",
				"doConfig");
	}

	/**
	 * 更新单个教师的额定工作量 如果没有
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doModifyForTeacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String teacherId = request.getParameter("teacherId");
		if (StringUtils.isEmpty(teacherId)) {
			String departmentIds = getDepartmentIdSeq(request);
			List collegeList = departmentService.getColleges(departmentIds);
			Results.addList("collegeList", collegeList);
			return forward(request);
		}
		String workloadValue = request.getParameter("workloadValue");
		Teacher teacher = (Teacher) utilService.load(Teacher.class, Long.valueOf(teacherId));
		teacher.setRatingWorkload(Float.valueOf(workloadValue));
		utilService.saveOrUpdate(teacher);
		return forward(mapping, request, "field.configRationWorkload.modifySuccess",
				"doModifyForTeacher");
	}

	/**
	 * 根据院系Id 得到教师列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doGetTeacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long collegeId = Long.valueOf(request.getParameter("collegeId"));
		Pagination teacherPagi = rationWorkloadConfigService.getTeacherPagi(
				rationWorkloadConfigService.getTeacherCriteria(collegeId), getPageNo(request),
				getPageSize(request));
		this.Results.addPagination("teacherPagi", teacherPagi);
		return this.forward(mapping, request, "doGetTeacher");
	}
}
