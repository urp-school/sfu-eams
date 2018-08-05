//$Id: PreAnswerTutorAction.java,v 1.3 2007/01/26 10:02:13 cwx Exp $
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
 * chenweixiong              2006-10-24         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.preAnswer;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.User;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.service.degree.thesis.preAnswer.PreAnswerService;
import com.shufe.service.system.baseinfo.TeacherService;
import com.shufe.util.RequestUtil;

/**
 * 导师查看学生预答辩信息
 * 
 * @author chaostone
 */
public class PreAnswerTutorAction extends PreAnswerSearchAction {

	private TeacherService teacherService;

	private PreAnswerService preAnswerService;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Results.addObject("stdTypeList", baseInfoService.getBaseInfos(StudentType.class)).addObject(
				"departmentList", departmentService.getColleges());
		return forward(request);
	}

	/**
	 * list里面的实体必须继承entity实体
	 * 
	 * @param entityList
	 * @return
	 */
	public String getEntiryIdSeq(List entityList) {
		String idSeq = "";
		for (Iterator iter = entityList.iterator(); iter.hasNext();) {
			Entity element = (Entity) iter.next();
			if (iter.hasNext()) {
				idSeq += element.getEntityId() + ",";
			} else {
				idSeq += element.getEntityId();
			}
		}
		return idSeq;
	}

	/**
	 * 学生列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward stdList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = getUser(request.getSession());
		Teacher teacher = teacherService.getTeacherByNO(user.getName());
		Student student = (Student) RequestUtil.populate(request, Student.class, "student");
		ThesisManage thesisManage = new ThesisManage();
		thesisManage.setStudent(student);
		thesisManage.setTutor(teacher);
		String hasApply = request.getParameter("hasApply");
		String departmentIdSeq = request.getParameter("departmentIdSeq");
		Long stdTypeId = getLong(request, "student.type.id");
		String stdTypeIdSeq = null;
		if (null != stdTypeId) {
			stdTypeIdSeq = studentTypeService.getStdTypeIdSeqUnder(stdTypeId);
			student.setType(null);
		}
		if (StringUtils.isBlank(departmentIdSeq)) {
			List departments = departmentService.getAllColleges();
			departmentIdSeq = getEntiryIdSeq(departments);
		}
		if ("true".equals(hasApply)) {
			EntityQuery query = super.buildPreAnswerQuery(request);
			query.add(new Condition("preAnswer.thesisManage.tutor=:tutor", teacher));
			addCollection(request, "preAnswerPage", utilService.search(query));
		} else {
			Pagination preAnswerPage = preAnswerService.getPaginationOfNoApply(thesisManage,
					departmentIdSeq, stdTypeIdSeq, getPageNo(request), getPageSize(request));
			addOldPage(request, "preAnswerPage", preAnswerPage);
		}
		request.setAttribute("hasApplyFlag", hasApply);
		request.setAttribute("flag", "tutor");
		return forward(request, "stdList");
	}

	/**
	 * 教室同意是否可以参与预答辩
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doAffirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean isTutorAffirm = Boolean.valueOf(request.getParameter("isTutorAffirm"));
		String thesisManageIdSeq = request.getParameter("thesisManageIdSeq");
		preAnswerService.batchUpdateTutorAffirm(thesisManageIdSeq, isTutorAffirm);
		String parameters = request.getParameter("parameters");
		return redirect(request, "stdList", "field.answer.tutor.success", parameters);
	}

	public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return forward(request, new Action(PreAnswerAction.class, "export"));
	}

	/**
	 * @param answerService
	 *            The answerService to set.
	 */
	public void setPreAnswerService(PreAnswerService answerService) {
		this.preAnswerService = answerService;
	}

	/**
	 * @param teacherService
	 *            The teacherService to set.
	 */
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
}
