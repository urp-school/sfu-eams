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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-11-4            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.arrange;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.AvailTimeService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.calendar.TimeSettingService;
import com.shufe.web.action.common.CalendarSupportAction;

/**
 * 教师时间设置响应类<br>
 * 1,教师用户的平时常用时间的偏好设置.<br>
 * 2,每一个教学任务的排课建议时间设置.<br>
 * 
 * @author chaostone
 * 
 */
public class TeacherTimeAction extends CalendarSupportAction {
    
    private TimeSettingService timeSettingService;
    
    private TeachTaskService teachTaskService;
    
    private AvailTimeService availTimeService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        List stdTypeList = teachTaskService.getStdTypesForTeacher(teacher);
        if (stdTypeList.isEmpty()) {
            return forward(mapping, request, "error.teacher.noTask", "error");
        }
        StudentType stdType = (StudentType) stdTypeList.get(0);
        if (stdTypeList.isEmpty()) {
            return forward(mapping, request, "error.teacher.noTask", "error");
        }
        addEntity(request, stdType);
        setCalendar(request, stdType);
        request.setAttribute("stdTypeList", stdTypeList);
        request.setAttribute(Constants.TEACHER, teacher);
        return forward(request);
    }
    
    /**
     * 显示教师可用时间
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward availTimeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        request.setAttribute(Constants.TEACHER, teacher);
        request.setAttribute("unitList", timeSettingService.getDefaultSetting().getCourseUnits());
        request.setAttribute("weekList", WeekInfo.WEEKS);
        return forward(request);
    }
    
    public ActionForward suggest(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("arrangeSuggest", "edit"));
    }
    
    /**
     * 修改教师可用时间
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editAvailTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        if (null == teacher) {
            return forwardError(mapping, request, "error.parameters.illegal");
        }
        
        if (null == teacher.getAvailableTime()) {
            AvailableTime time = new AvailableTime(AvailableTime.commonTeacherAvailTime);
            availTimeService.saveTeacherAvailTime(teacher.getId(), time);
        }
        request.setAttribute("weekList", WeekInfo.WEEKS);
        request.setAttribute("unitList", timeSettingService.getDefaultSetting().getCourseUnits());
        request.setAttribute("teacher", teacher);
        return forward(request);
    }
    
    /**
     * 保存可用时间偏好
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveAvailTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String availTime = request.getParameter("availTime");
        String vailTimeId = request.getParameter("availTime.id");
        String remark = request.getParameter("availTime.remark");
        // 备注一百字
        remark = StringUtils.abbreviate(remark, 100);
        AvailableTime time = new AvailableTime(availTime);
        time.setRemark(remark);
        time.setId(Long.valueOf(vailTimeId));
        if (!time.isValid()) {
            throw new Exception("availTime.not.corrected");
        }
        availTimeService.updateAvailTime(time);
        return redirect(request, "availTimeInfo", "info.save.success");
    }
    
    /**
     * 教学任务列表
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
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "task.calendar.id"));
        List tasks = teachTaskService.getTeachTasksByCategory(getTeacherFromSession(
                request.getSession()).getId(), TeachTaskFilterCategory.TEACHER, calendar);
        addCollection(request, "taskList", tasks);
        return forward(request);
    }
    
    public void setTimeSettingService(TimeSettingService timeSettingService) {
        this.timeSettingService = timeSettingService;
    }
    
    public void setAvailTimeService(AvailTimeService availTimeService) {
        this.availTimeService = availTimeService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
}
