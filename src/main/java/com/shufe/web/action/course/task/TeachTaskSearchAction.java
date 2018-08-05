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
 * chaostone            2006-04-06          Created
 * zq                   2007-09-12          search() 由于EntityQuery是在
 *                                          teachTaskSearchHelper.searchTask里面的，
 *                                          所以addStdTypeTreeDataRealm无法使用
 * zq                   2007-09-13          删除多余的注释"//"
 ********************************************************************************/

package com.shufe.web.action.course.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.time.WeekInfo;
import com.ekingstar.security.User;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.CourseArrangeSwitch;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;
import com.shufe.service.course.task.TeachTaskPropertyExtractor;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.action.course.grade.TeacherGradeAction;
import com.shufe.web.action.system.baseinfo.CourseAction;
import com.shufe.web.helper.TeachTaskSearchHelper;

/**
 * 教学任务查询服务
 * 
 * @author chaostone
 */
public class TeachTaskSearchAction extends CalendarRestrictionSupportAction {
    
    protected TeachTaskService teachTaskService;
    
    protected TeachTaskSearchHelper teachTaskSearchHelper;
    
    /**
     * 进入教学任务管理模块
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List stdTypes = baseInfoService.getBaseInfos(StudentType.class);
        User user = getUser(request.getSession());
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        
        // 超找最适合用户的学生类别
        StudentType stdType = (StudentType) stdTypes.get(0);
        if (null == stdTypeId) {
            if (user != null && user.isCategory(EamsRole.STD_USER)) {
                Student std = getStudentFromSession(request.getSession());
                if (null != std) {
                    stdType = std.getType();
                }
            }
        } else {
            stdType = (StudentType) utilService.get(StudentType.class, stdTypeId);
        }
        addCollection(request, "stdTypeList", stdTypes);
        setCalendar(request, stdType);
        // 设置日历中使用的学生类别
        StudentType studentType = (StudentType) request.getAttribute("studentType");
        List calendarStdTypes = teachCalendarService.getCalendarStdTypes(studentType.getId());
        List rs = (List) CollectionUtils.intersection(calendarStdTypes, stdTypes);
        Collections.sort(rs, new PropertyComparator("code"));
        addCollection(request, CALENDAR_STDTYPES_KEY, rs);
        
        TeachCalendar calenadar = (TeachCalendar) request.getAttribute(Constants.CALENDAR);
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        query.setSelect("distinct task.arrangeInfo.teachDepart");
        query.add(new Condition("task.calendar=:calendar", calenadar));
        
        addCollection(request, "teachDepartList", utilService.search(query));
        addCollection(request, Constants.DEPARTMENT_LIST, departmentService.getColleges());
        addCollection(request, "weeks", WeekInfo.WEEKS);
        initBaseCodes(request, "courseTypes", CourseType.class);
        return forward(request);
    }
    
    /**
     * 查找教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 暂不要删除，因页面中有ignoreAuthority参数
        // Boolean ignoreAuthority = getBoolean(request, "ignoreAuthority");
        // Collection tasks = null;
        // if (null != ignoreAuthority && ignoreAuthority.equals(Boolean.TRUE)) {
        // tasks = teachTaskSearchHelper.searchTask(request);
        // } else {
        // tasks = teachTaskSearchHelper.searchTask(request);
        // }
        addCollection(request, "tasks", teachTaskSearchHelper.searchTask(request));
        return forward(request);
    }
    
    public ActionForward arrangeInfoList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Collection tasks = teachTaskSearchHelper.searchTask(request);
        addCollection(request, "tasks", tasks);
        CourseActivityDigestor.setDelimeter("<br>");
        Map arrangeInfo = new HashMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            arrangeInfo.put(task.getId().toString(), CourseActivityDigestor.digest(task,
                    getResources(request), getLocale(request)));
        }
        addSingleParameter(request, "arrangeInfo", arrangeInfo);
        addSingleParameter(request, "userCategory", getUserCategoryId(request));
        List switches = utilService.load(CourseArrangeSwitch.class, "calendar.id", getLong(request,
                "calendar.id"));
        if (CollectionUtils.isNotEmpty(switches)) {
            addSingleParameter(request, "switch", switches.get(0));
        }
        return forward(request);
    }
    
    /**
     * 查询课程基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward courseInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action(CourseAction.class, "info"));
    }
    
    /**
     * 查看单个教学任务的详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "task.id");
        if (null == taskId) {
            taskId = getLong(request, "taskId");
        }
        if (null == taskId) {
            return forward(mapping, request, "error.teachTask.id.needed", "error");
        }
        
        TeachTask task = teachTaskService.getTeachTask(taskId);
        try {
            task.getCourse().getId();
        } catch (Exception e) {
            return forward(mapping, request, "error.teachTask.notExists", "error");
        }
        request.setAttribute("task", task);
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        query.setLimit(null);
        query.getOrders().clear();
        return utilService.search(query);
    }
    
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        return new TeachTaskPropertyExtractor(getLocale(request), getResources(request), utilService);
    }
    
    /**
     * 教学任务确认
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter("taskIds");
        Boolean isConfirm = getBoolean(request, "isConfirm");
        if (StringUtils.isNotEmpty(taskIds)) {
            logHelper.info(request, isConfirm.equals(Boolean.FALSE) ? "un" : ""
                    + "Confirm task of " + taskIds);
            teachTaskService.teachTasksConfirm(taskIds, isConfirm.booleanValue());
        } else {
            logHelper.info(request, isConfirm.equals(Boolean.FALSE) ? "un" : ""
                    + "Confirm batchly!");
            teachTaskService.teachTasksConfirm((TeachTask) RequestUtil.populate(request,
                    TeachTask.class, Constants.TEACHTASK, true), isConfirm.booleanValue());
        }
        if (isConfirm.equals(Boolean.TRUE)) {
            return redirect(request, "search", "info.confirm.success");
        } else {
            return redirect(request, "search", "info.unconfirm.success");
        }
    }
    
    public ActionForward printTaskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        query.add(new Condition("task.id in (:ids)", SeqStringUtil.transformToLong(teachTaskIds)));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        query.setLimit(null);
        addCollection(request, "tasks", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward printStdListForDuty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        
        List tasks = utilService.load(TeachTask.class, "id",
                SeqStringUtil.transformToLong(teachTaskIds));
        
        Map arrangeInfos = new HashMap();
        Map courseTakes = new HashMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            arrangeInfos.put(task.getId().toString(), CourseActivityDigestor.digest(task,
                    getResources(request), getLocale(request)));
            ArrayList myCourseTakes = new ArrayList();
            myCourseTakes.addAll(task.getTeachClass().getCourseTakes());
            courseTakes.put(task.getId().toString(), myCourseTakes);
        }
        addCollection(request, "tasks", tasks);
        addSingleParameter(request, "arrangeInfos", arrangeInfos);
        addSingleParameter(request, "courseTakes", courseTakes);
        return forward(request);
    }
    
    public ActionForward printStdListForGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action(TeacherGradeAction.class, "printEmptyGradeTable"));
    }
    
    public ActionForward taskTemplate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        addSingleParameter(request, "systemConfig", SystemConfigLoader.getConfig());
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        addSingleParameter(request, "taskCount",
                new Integer(StringUtils.split(teachTaskIds, ",").length));
        return forward(request);
    }
    
    public ActionForward printStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teachTaskIds = get(request, "teachTaskIds");
        if (StringUtils.isEmpty(teachTaskIds)) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        printStdListPrepare(teachTaskIds, request);
        return forward(request);
    }
    
    public void printStdListPrepare(String taskIds, HttpServletRequest request) throws Exception {
        List tasks = utilService.load(TeachTask.class, "id", SeqStringUtil.transformToLong(taskIds));
        Map courseTakes = new HashMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            ArrayList myCourseTakes = new ArrayList();
            myCourseTakes.addAll(task.getTeachClass().getCourseTakes());
            courseTakes.put(task.getId().toString(), myCourseTakes);
        }
        addCollection(request, "tasks", tasks);
        addSingleParameter(request, "courseTakes", courseTakes);
    }
    
    /**
     * @param teachTaskService
     *            The teachTaskService to set.
     */
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public void setTeachTaskSearchHelper(TeachTaskSearchHelper teachTaskSearchHelper) {
        this.teachTaskSearchHelper = teachTaskSearchHelper;
    }
}
