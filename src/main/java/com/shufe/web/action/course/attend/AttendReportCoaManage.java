package com.shufe.web.action.course.attend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AttendReportCoaManage extends AttendReportCoaAction {
	
	@Override
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.showDetailList(mapping, form, request, response);
	}
	
	@Override
	public ActionForward resumption(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.resumption(mapping, form, request, response);
		return redirect(request, "index", "info.action.success");
	}
	
}
