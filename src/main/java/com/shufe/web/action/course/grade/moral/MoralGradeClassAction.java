package com.shufe.web.action.course.grade.moral;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.model.system.calendar.TeachCalendar;

public class MoralGradeClassAction extends MoralGradeInstructorAction {
  public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    setCalendarDataRealm(request, hasStdTypeCollege);
    TeachCalendar calendar = (TeachCalendar) request.getAttribute("calendar");
    if (null != calendar) {
      request.setAttribute("stdTypesOfCalendar", getStdTypesOf(calendar.getStudentType().getId(), request));
    }
    return forward(request);
  }

  public ActionForward classList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    addCollection(request, "adminClasses", baseInfoSearchHelper.searchAdminClass(request));
    return forward(request);
  }
}
