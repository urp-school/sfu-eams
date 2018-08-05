//$Id: TeacherTaskAlterRequestAction.java,v 1.1 May 13, 2007 5:44:14 PM chaostone Exp $
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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      May 13, 2007         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.course.task.TaskAlterRequest;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TaskAlterReqestService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarSupportAction;

public class TeacherAlterRequestAction extends CalendarSupportAction {
    
    private TaskAlterReqestService taskAlterRequestService;
    
    private TeachTaskService teachTaskService;
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        List stdTypeList = teachTaskService.getStdTypesForTeacher(teacher);
        if (stdTypeList.isEmpty()) {
            return forward(mapping, request, "error.teacher.noTask", "error");
        }
        setCalendar(request, (StudentType) stdTypeList.iterator().next());
        request.setAttribute("stdTypeList", stdTypeList);
        request.setAttribute("teacher", teacher);
        return forward(request);
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward alterRequestList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        List taskAlterRequests = taskAlterRequestService.getTaskAlterRequests(calendar,
                getTeacherFromSession(request.getSession()));
        addCollection(request, "taskAlterRequests", taskAlterRequests);
        return forward(request);
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        List tasklist = teachTaskService.getTeachTasksByCategory(teacher.getId(),
                TeachTaskFilterCategory.TEACHER, calendar);
        request.setAttribute("taskList", tasklist);
        return forward(request);
    }
    
    /**
     * 保存新增或者修改的变更需求。<br>
     * 如果该申请已经被批准，则在界面上进行拦截。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        TaskAlterRequest taskAlterRequest = (TaskAlterRequest) populateEntity(request,
                TaskAlterRequest.class, "taskAlterRequest");
        taskAlterRequestService.save(taskAlterRequest);
        return redirect(request, "alterRequestList", "info.save.success");
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        TaskAlterRequest taskAlterRequest = (TaskAlterRequest) utilService.get(
                TaskAlterRequest.class, getLong(request, "taskAlterRequestId"));
        if (!taskAlterRequest.isApproved()) {
            utilService.remove(taskAlterRequest);
            return redirect(request, "alterRequestList", "info.delete.success");
        } else {
            return redirect(request, "alterRequestList", "info.delete.failure");
        }
    }
    
    public void setTaskAlterRequestService(TaskAlterReqestService taskAlterRequestService) {
        this.taskAlterRequestService = taskAlterRequestService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
}
