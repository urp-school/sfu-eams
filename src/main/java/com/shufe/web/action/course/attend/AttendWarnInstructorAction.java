package com.shufe.web.action.course.attend;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.system.baseinfo.Teacher;

public class AttendWarnInstructorAction extends AttendWarnAction{
	
	@Override
	protected EntityQuery buildQuery(HttpServletRequest request) {
		Teacher teacher = getTeacherFromSession(request.getSession());
		EntityQuery query = super.buildQuery(request);
		if (teacher==null) {
			query.add(new Condition("1=2"));
		}else {
			query.join("attendWarn.std.adminClasses", "ac");
			query.add(new Condition("ac.instructor = :instructor", teacher));
		}
		return query;
	}
	
}
