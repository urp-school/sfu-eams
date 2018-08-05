package com.shufe.web.action.course.attend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.system.baseinfo.Teacher;

public class AttendReportForTaskInstructorAction extends AttendReportForTeachTaskAction{
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Teacher teacher = getTeacherFromSession(request.getSession());
    	EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
    	if (teacher==null) {
    		query.add(new Condition("task is null"));
		}else {
			query.join("task.teachClass.adminClasses", "ac");
			query.add(new Condition("ac.instructor = :instructor", teacher));
		}
    	addCollection(request, "tasks", utilService.search(query));
        String order = request.getParameter("order");
        addSingleParameter(request, "order", order);
        return forward(request);
    }
	
}
