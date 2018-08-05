//$Id: AutoArrangeAction.java,v 1.4 2006/11/13 03:03:28 duanth Exp $
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
 * chaostone            2005-11-12          Created
 * zq                   2007-09-18          在arrange()中添加了logHelp.info(...)方法；
 *                                          在removeLastArrange()方法中替换掉两个info；
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.arrange.task.ArrangeParams;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;
import com.shufe.service.course.arrange.task.CourseActivityService;
import com.shufe.service.course.arrange.task.TaskGroupService;
import com.shufe.service.course.arrange.task.auto.Arrange;
import com.shufe.service.course.arrange.task.auto.ArrangeManager;
import com.shufe.service.course.arrange.task.auto.ArrangeTaskValidator;
import com.shufe.service.course.arrange.task.auto.ArrangeValidateMessages;
import com.shufe.service.course.arrange.task.auto.impl.ArrangeWebListener;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.baseinfo.ClassroomService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * (自动排课)的界面响应类
 * 
 * @author chaostone 2005-11-12
 */
public class AutoArrangeAction extends CalendarRestrictionSupportAction {
    
    private TeachTaskService teachTaskService;
    
    private ArrangeManager arrangeManager;
    
    private TaskGroupService groupService;
    
    private ClassroomService roomService;
    
    private ArrangeTaskValidator validateService;
    
    private CourseActivityService courseActivityService;
    
    /**
     * 自动排课主界面
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
        /*-------------设置数据----------------------*/
        String departDataRealm = getDepartmentIdSeq(request);
        // 包含公有教室
        if (departDataRealm.indexOf(String.valueOf(Department.SCHOOLID)) == -1)
            departDataRealm += "," + Department.SCHOOLID;
        request.setAttribute("roomList", roomService.getClassrooms(departDataRealm));
        setCalendarDataRealm(request, hasStdType);
        return forward(request);
    }
    
    /**
     * 列举教学任务和课程组（为安排和已安排的）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String type = request.getParameter("arrangeType");
        if (StringUtils.isEmpty(type))
            return forward(mapping, request, "error.unarrangedType.needed", "error");
        String teachDepartIdSeq = getDepartmentIdSeq(request);
        String calendarId = request.getParameter(Constants.CALENDAR_KEY);
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(Long.valueOf(calendarId));
        if (null == calendar)
            return forward(mapping, request, new String[] { "entity.calendar",
                    "error.model.notExist" }, "error");
        request.setAttribute("calendar", calendar);
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        String stdTypeIdSeq = getStdTypeIdSeqOf(stdTypeId, request);
        if (type.equals("task")) {
            TeachTask task = (TeachTask) populate(request, TeachTask.class, Constants.TEACHTASK);
            task.getRequirement().setIsGuaPai(Boolean.FALSE);
            
            Boolean isArrangeCompleted = getBoolean(request, "task.arrangeInfo.isArrangeComplete");
            // 添加教师查询条件
            Teacher teacher = (Teacher) populate(request, Teacher.class);
            task.getArrangeInfo().getTeachers().add(teacher);
            Pagination taskPage = teachTaskService.getTeachTasksOfLonely(task, SeqStringUtil
                    .transformToLong(stdTypeIdSeq),
                    SeqStringUtil.transformToLong(teachDepartIdSeq), getPageNo(request),
                    getPageSize(request));
            addOldPage(request, "taskList", taskPage);
            if (isArrangeCompleted.equals(Boolean.FALSE))
                prepareTimeInfo(request, new AvailableTime(AvailableTime.commonTeacherAvailTime));
            else {
                setArrangeInfo(request, taskPage.getItems());
            }
            return forward(request, "taskList");
        } else {
            TaskGroup group = new TaskGroup();
            group.setIsSameTime(null);
            group.setPriority(null);
            group = (TaskGroup) RequestUtil.populate(request, group, Constants.TASKGROUP);
            
            addOldPage(request, "groupList", groupService.getTaskGroups(group, stdTypeIdSeq,
                    teachDepartIdSeq, calendar, getPageNo(request), getPageSize(request),
                    getBoolean(request, "task.arrangeInfo.isArrangeComplete")));
            return forward(request, "groupList");
        }
    }
    
    /**
     * 准备时间信息
     * 
     * @param request
     * @param time
     */
    private void prepareTimeInfo(HttpServletRequest request, AvailableTime time) {
        request.setAttribute("weekList", WeekInfo.WEEKS);
        request.setAttribute("availTime", time);
    }
    
    /**
     * 进行自动排课
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward arrange(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("arrangeType");
        if (null == type)
            type = "task";
        /*--------------设置排课参数-------------------------*/
        ArrangeParams params = (ArrangeParams) RequestUtil.populate(request, ArrangeParams.class,
                Constants.ARRANGEPARAMS);
        arrangeManager.getFixture().setParams(params);
        
        String roomIdSeq = request.getParameter("roomIds");
        /*--------------制定临时课程组---------------------------*/
        TaskGroup group = (TaskGroup) RequestUtil.populate(request, TaskGroup.class,
                Constants.TASKGROUP);
        
        if (type.equals("task")) {
            /*--------添加单独课程----------------------*/
            String taskIds = request.getParameter(Constants.TEACHTASK_KEYSEQ);
            if (StringUtils.isEmpty(taskIds))
                return forward(mapping, request, "error.teachTask.id.needed", "error");
            List taskList = teachTaskService.getTeachTasksByIds(taskIds);
            group.getDirectTasks().addAll(taskList);
        } else {
            /*----------添加课程组----------------------*/
            String groupIds = request.getParameter(Constants.TASKGROUP_KEYSEQ);
            if (StringUtils.isEmpty(groupIds))
                return forward(mapping, request, "error.taskGroup.id.needed", "error");
            List groupList = groupService.getTaskGroups(groupIds);
            group.getGroups().addAll(groupList);
            group.getSuggest().getTime().setAvailable(
                    StringUtils.repeat("1", AvailableTime.MAX_LENGTH));
        }
        /*--------------检查教学任务是否满足自动排课条件----*/
        ArrangeValidateMessages msgs = validateService.validateTaskGroup(group, params);
        if (!msgs.isEmpty()) {
            Map arrangeValidateMessages = new HashMap();
            for (Iterator iter = msgs.properties(); iter.hasNext();) {
                String property = (String) iter.next();
                arrangeValidateMessages.put(property, msgs.getMassageList(property));
            }
            Results.addObject("arrangeValidateMessages", arrangeValidateMessages);
            request.getSession().setAttribute("arrangeValidateMessages", arrangeValidateMessages);
            return forward(request, "checkInfo");
        }
        response.setContentType("text/html; charset=utf-8");
        
        if (StringUtils.isNotEmpty(roomIdSeq))
            group.getSuggest().getRooms().addAll(
                    roomService.getClassrooms(Arrays.asList(SeqStringUtil
                            .transformToLong(roomIdSeq))));
        
        Arrange arrange = arrangeManager.makeArrange(group, params);
        ArrangeWebListener webListener = new ArrangeWebListener(response.getWriter());
        // added 2006-2-5 by chaostone
        webListener.setResourses(getResources(request));
        webListener.setLocale(getLocale(request));
        try {
            arrangeManager.doArrange(arrange, webListener);
        } catch (Exception e) {
            e.printStackTrace();
            logHelper.info(request, "Failure Arrangement named:" + arrange.getName(), e);
            return forward(mapping, request, "error.occurred", "error");
        }
        
        Iterator iter = group.getAllGroups().iterator();
        try {
            while (iter.hasNext())
                groupService.updateTaskGroup((TaskGroup) iter.next());
        } catch (Exception e) {
            logHelper.info(request, "Failure Update group in after auto arrange :", e);
            return forward(mapping, request, "error.occurred", "error");
        }
        request.getSession().setAttribute("lastArrange", arrange);
        request.getSession().setAttribute("reloadedArrangeTaskList", null);
        return null;
    }
    
    /**
     * 查询上次安排的教学任务列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward lastArrangeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 查询重新加载的教学任务列表
        List teachTaskList = (List) request.getSession().getAttribute("reloadedArrangeTaskList");
        // 重新加载的只为了hibernate的延迟加载特性
        if (null == teachTaskList) {
            // 重新加载教学任务
            Arrange arrange = (Arrange) request.getSession().getAttribute("lastArrange");
            Collection taskList = arrange == null ? Collections.EMPTY_LIST : arrange.getTasks();
            if (!taskList.isEmpty()) {
                Iterator iter = taskList.iterator();
                Long[] taskIds = new Long[taskList.size()];
                for (int i = 0; i < taskIds.length; i++)
                    taskIds[i] = ((TeachTask) iter.next()).getId();
                teachTaskList = teachTaskService.getTeachTasksByIds(taskIds);
                request.getSession().setAttribute("reloadedArrangeTaskList", teachTaskList);
            } else {
                teachTaskList = Collections.EMPTY_LIST;
            }
        }
        setArrangeInfo(request, teachTaskList);
        request.setAttribute("taskList", teachTaskList);
        return forward(request);
    }
    
    private void setArrangeInfo(HttpServletRequest request, List teachTaskList) {
        Map arrangeInfo = new HashMap();
        for (Iterator iter = teachTaskList.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            arrangeInfo.put(task.getId().toString(), CourseActivityDigestor.digest(task,
                    getResources(request), getLocale(request)));
        }
        request.setAttribute("arrangeInfo", arrangeInfo);
    }
    
    /**
     * 从上次排考结果中删除部分排考信息. 实现上将session中保存的教学任务清除.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeLastArrange(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter(Constants.TEACHTASK_KEYSEQ);
        if (StringUtils.isEmpty(taskIds))
            return forward(mapping, request, "error.teachTask.ids.needed");
        try {
            logHelper.info(request, "Delete activity of task ids:" + taskIds);
            courseActivityService.removeActivities(SeqStringUtil.transformToLong(taskIds));
        } catch (Exception e) {
            logHelper.info(request, "Failure in deleting activity of task ids:" + taskIds);
        }
        request.getSession().setAttribute("reloadedArrangeTaskList", null);
        return redirect(request, "lastArrangeList", "info.delete.success");
    }
    
    public ActionForward removeGroupArrangeResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] groupIds = SeqStringUtil.transformToLong(request.getParameter("groupIds"));
        if (null != groupIds) {
            List groups = utilService.load(TaskGroup.class, "id", groupIds);
            for (Iterator iter = groups.iterator(); iter.hasNext();) {
                TaskGroup group = (TaskGroup) iter.next();
                courseActivityService.removeActivities(group.getTaskList());
            }
            
        }
        return redirect(request, "list", "info.delete.success");
    }
    
    /**
     * @param arrangeManager
     *            The arrangeManager to set.
     */
    public void setArrangeManager(ArrangeManager arrangeManager) {
        this.arrangeManager = arrangeManager;
    }
    
    /**
     * @param teachTaskService
     *            The teachTaskService to set.
     */
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    /**
     * @param groupService
     *            The groupService to set.
     */
    public void setGroupService(TaskGroupService groupService) {
        this.groupService = groupService;
    }
    
    /**
     * @param roomService
     *            The roomService to set.
     */
    public void setRoomService(ClassroomService roomService) {
        this.roomService = roomService;
    }
    
    /**
     * 
     * @param validateService
     */
    public void setValidateService(ArrangeTaskValidator validateService) {
        this.validateService = validateService;
    }
    
    public void setCourseActivityService(CourseActivityService courseActivityService) {
        this.courseActivityService = courseActivityService;
    }
    
}
