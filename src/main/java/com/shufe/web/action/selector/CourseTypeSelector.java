package com.shufe.web.action.selector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.service.system.baseinfo.StudentTypeService;
import com.shufe.web.action.common.DispatchBasicAction;

public class CourseTypeSelector extends DispatchBasicAction {

	private StudentTypeService studentTypeService;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward withAuthority(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// String studentTypeIds = this.getStudentTypeDataRelation(request
		// .getSession(), request.getParameter("moduleName"));
		// if (StringUtils.isNotEmpty(studentTypeIds)) {
		// Results.addObject("studentTypeList", utilService.loadByKeys(
		// StudentType.class, "id", StringUtil
		// .transformToLong(studentTypeIds)));
		// }
		return forward(mapping, request, "success");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward withoutAuthority(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Results.addObject("courseTypeList", utilService.loadAll(CourseType.class));
		return this.forward(mapping, request, "success");
	}

	/**
	 * @return Returns the studentTypeService.
	 */
	public StudentTypeService getStudentTypeService() {
		return studentTypeService;
	}

	/**
	 * @param studentTypeService
	 *            The studentTypeService to set.
	 */
	public void setStudentTypeService(StudentTypeService studentTypeService) {
		this.studentTypeService = studentTypeService;
	}

}
