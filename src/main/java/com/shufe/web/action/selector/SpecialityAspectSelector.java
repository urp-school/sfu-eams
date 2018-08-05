package com.shufe.web.action.selector;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.service.system.baseinfo.SpecialityAspectService;
import com.shufe.web.action.common.RestrictionSupportAction;

public class SpecialityAspectSelector extends RestrictionSupportAction {

	private SpecialityAspectService specialityAspectService;

	/**
	 * 列出专业下所有专业方向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward withSpeciality(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List specialitAspectsList = specialityAspectService.getSpecialityAspects(getLong(request,
				"specialityId"));
		Results.addObject("specialitAspectsList", specialitAspectsList);
		return forward(mapping, request, "success");
	}

	/**
	 * 列出所有第二专业方向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward allSecondSpecialityAspects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String departmentIds = getDepartmentIdSeq(request);
		List specialitAspectsList = specialityAspectService
				.listSecondSpecialityAspect(departmentIds);
		Results.addObject("secondSpecialitAspectsList", specialitAspectsList);
		return forward(mapping, request, "secondSuccess");
	}

	/**
	 * @return Returns the specialityAspectService.
	 */
	public SpecialityAspectService getSpecialityAspectService() {
		return specialityAspectService;
	}

	/**
	 * @param specialityAspectService
	 *            The specialityAspectService to set.
	 */
	public void setSpecialityAspectService(SpecialityAspectService specialityAspectService) {
		this.specialityAspectService = specialityAspectService;
	}

}
