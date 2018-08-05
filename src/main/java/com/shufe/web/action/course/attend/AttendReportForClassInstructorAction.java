package com.shufe.web.action.course.attend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.system.baseinfo.Teacher;

public class AttendReportForClassInstructorAction extends AttendReportForAdminClassAction{
	
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
		Teacher teacher = getTeacherFromSession(request.getSession());
		EntityQuery query = baseInfoSearchHelper.buildAdminClassQuery(request);
		if (teacher==null) {
    		query.add(new Condition("adminClass is null"));
		}else {
			query.add(new Condition("adminClass.instructor = :instructor", teacher));
		}
		addCollection(request, "adminClasses", utilService.search(query));
        return forward(request);
    }
	
}
