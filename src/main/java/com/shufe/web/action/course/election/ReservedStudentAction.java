package com.shufe.web.action.course.election;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.election.ReservedStudent;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;

public class ReservedStudentAction extends CalendarRestrictionExampleTemplateAction {

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long calendarId = getLong(request, "calendar.id");
		EntityQuery reservedQuery = new EntityQuery(ReservedStudent.class, "rs");
		reservedQuery.add(new Condition("rs.calendar.id=:calendarId", calendarId));
		request.setAttribute("reservedStudents", utilService.search(reservedQuery));
		request.setAttribute("majors", baseInfoService.getBaseInfos(Speciality.class));
		request.setAttribute("majorFields", baseInfoService.getBaseInfos(SpecialityAspect.class));
		return forward(request);
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return index(mapping, form, request, response);
	}
}
