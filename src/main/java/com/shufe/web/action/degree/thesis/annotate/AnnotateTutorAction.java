//$Id: AnnotateTutorAction.java,v 1.8 2006/12/19 05:02:29 cwx Exp $
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
 * chenweixiong              2006-11-7         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.annotate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.std.StudentService;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 导师查看和审阅学生的论文评阅类
 * 
 * @author chaostone
 * 
 */
public class AnnotateTutorAction extends RestrictionSupportAction {

	private StudentService studentService;

	private ThesisManageService thesisManageService;

	/**
	 * 加载论文评阅首页面
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
		Results.addObject("stdTypeList",
				baseInfoService.getBaseInfos(StudentType.class)).addObject(
				"departmentList", departmentService.getAllColleges());
		return forward(request);
	}

	/**
	 * 根据条件查询学生
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward stdList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Student student = (Student) populate(request, Student.class);
		String name = getUser(request.getSession()).getName();
		Teacher teacher = (Teacher) utilService.load(Teacher.class, "code",
				name).get(0);
		student.setTeacher(teacher);
		ThesisManage thesisManage = new ThesisManage();
		thesisManage.setStudent(student);

		String hasWrite = request.getParameter("hasWrite");
		if (StringUtils.isBlank(hasWrite)) {
			hasWrite = "true";
		}
		String notNulls = "";
		String nulls = "";
		if ("true".equals(hasWrite)) {
			notNulls += "annotate";
		} else {
			nulls += "annotate";
		}
		String orderString = request.getParameter("orderString");
		Pagination studentPage = thesisManageService
				.getThesissPaginaByNullNotNull(thesisManage, null, null,
						getPageNo(request), getPageSize(request), notNulls,
						nulls, orderString);
		Results.addObject("studentPage", studentPage).addObject("hasWrite",
				hasWrite);
		request.setAttribute("flag", "tutor");
		return forward(request);
	}

	/**
	 * 得到一个学生的自评表信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward annotateInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String stdId = request.getParameter("studentId");
		Student student = studentService.getStudent(Long.valueOf(stdId));
		StudentType stdType = student.getType();
		List thesisManagerList = utilService.load(ThesisManage.class,
				"student.id", student.getId());
		if (thesisManagerList.size() > 0) {
			ThesisManage thesisManage = (ThesisManage) thesisManagerList.get(0);
			if (null != thesisManage.getAnnotate()
					&& null != thesisManage.getAnnotate().getId()
					&& new Long(0) != thesisManage.getAnnotate().getId()) {
				request.setAttribute("thesisAnnotate", thesisManage
						.getAnnotate());
			}
			request.setAttribute("thesisManage", thesisManage);
		}
		request.setAttribute("student", student);
		request.setAttribute("flag", "tutor");
		if (Degree.MASTER.equals(stdType.getDegree().getId())) {
			request.setAttribute("stdDegree", "master");
			return forward(request, "../annotateInfo");
		} else if (Degree.DOCTOR.equals(stdType.getDegree().getId())) {
			request.setAttribute("stdDegree", "doctor");
			return forward(request, "../annotateInfo");
		} else {
			return forward(request, "error");
		}
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setThesisManageService(ThesisManageService thesisManageService) {
		this.thesisManageService = thesisManageService;
	}

}
