//$Id: AllocateTaskForArrangeAction.java,v 1.1 2009-4-3 上午09:11:50 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2009-4-3             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.task.TaskInDepartment;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.TeachTaskSearchHelper;

/**
 * @author zhouqi
 * 
 */
public class AllocateTaskForArrangeAction extends CalendarRestrictionSupportAction {
    
    protected TeachTaskSearchHelper teachTaskSearchHelper;
    
    protected TeachTaskService teachTaskService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "teachCalendarId");
        TeachCalendar calendar = (TeachCalendar) getAttribute(request, Constants.CALENDAR);
        if (null == calendarId) {
            setCalendarDataRealm(request, hasStdTypeCollege);
            calendar = (TeachCalendar) getAttribute(request, Constants.CALENDAR);
        } else {
            calendar = teachCalendarService.getTeachCalendar(calendarId);
            addSingleParameter(request, "calendar", calendar);
            addSingleParameter(request, "studentType", calendar.getStudentType());
            addCollection(request, "stdTypeList", getStdTypes(request));
            addSingleParameter(request, CALENDAR_STDTYPES_KEY, // 该值为calendarStdTypes
                    getCalendarStdTypesOf(((StudentType) getAttribute(request, "studentType"))
                            .getId(), request));
        }
        // calendarStdTypes
        Set departments = new HashSet();
        departments.addAll(baseInfoService.getBaseInfos(Department.class));
        Set allocations = new HashSet();
        allocations.addAll(utilService
                .load(TaskInDepartment.class, "calendar.id", calendar.getId()));
        for (Iterator it = allocations.iterator(); it.hasNext();) {
            TaskInDepartment taskIn = (TaskInDepartment) it.next();
            if (CollectionUtils.isEmpty(taskIn.getTasks())) {
                continue;
            }
            departments.remove(taskIn.getDepartment());
        }
        addCollection(request, "departments", departments);
        addCollection(request, "allocations", allocations);
        
        EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        query.add(new Condition("task.calendar.id = :calendarId", calendar.getId()));
        query.setSelect("count(*)");
        addSingleParameter(request, "taskCount", utilService.search(query).iterator().next());
        String taskCondition = "not exists (from TaskInDepartment taskIn join taskIn.tasks as teachTask where task.id = teachTask.id)";
        query.add(new Condition(taskCondition));
        addSingleParameter(request, "taskOutCount", utilService.search(query).iterator().next());
        
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        List departList = teachTaskService.getDepartsOfTask(stdTypeDataRealm, departDataRealm,
                (TeachCalendar) request.getAttribute(Constants.CALENDAR));
        addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR)));
        addCollection(request, "teachDepartList", teachTaskService.getTeachDepartsOfTask(
                stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR)));
        addCollection(request, Constants.DEPARTMENT_LIST, departList);
        
        return forward(request);
    }
    
    /**
     * @param calendar
     * @return
     */
    protected EntityQuery buildTaskQuery(HttpServletRequest request, TeachCalendar calendar) {
        EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        // new EntityQuery(TeachTask.class, "teachTask");
        query.add(new Condition("task.calendar.id = :calendarId", calendar.getId()));
        String taskCondition = "not exists (from TaskInDepartment taskIn join taskIn.tasks as teachTask where task.id = teachTask.id)";
        query.add(new Condition(taskCondition));
        return query;
    }
    
    public ActionForward searchAll(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
        query
                .add(new Condition("task.calendar.id = (:calendarId)", getLong(request,
                        "calendarId")));
        addTaskInQuery(request, query);
        Collection tasks = utilService.search(query);
        addCollection(request, "tasks", tasks);
        
        getTaskInDepartment(request, tasks);
        
        initBaseInfos(request, "departments", Department.class);
        return forward(request, "listAll");
    }
    
    protected void getTaskInDepartment(HttpServletRequest request, Collection tasks) {
        EntityQuery query = new EntityQuery(TaskInDepartment.class, "taskIn");
        query.join("taskIn.tasks", "task");
        Collection teachTasks = (tasks instanceof SinglePage) ? ((SinglePage) tasks).getPageDatas()
                : tasks;
        if (CollectionUtils.isEmpty(teachTasks)) {
            query.add(new Condition("task is null"));
        } else {
            query.add(new Condition("task in (:task)", teachTasks));
        }
        
        Map taskInMap = new HashMap();
        for (Iterator it1 = utilService.search(query).iterator(); it1.hasNext();) {
            TaskInDepartment taskIn = (TaskInDepartment) it1.next();
            for (Iterator it2 = taskIn.getTasks().iterator(); it2.hasNext();) {
                TeachTask task = (TeachTask) it2.next();
                taskInMap.put(task.getId().toString(), taskIn);
            }
        }
        
        addSingleParameter(request, "taskInMap", taskInMap);
    }
    
    /**
     * <font color="blue">查询排课院系分配情况</font>
     * 
     * @param request
     * @param query
     */
    protected void addTaskInQuery(HttpServletRequest request, EntityQuery query) {
        String taskInDepart = get(request, "taskInDepart");
        // if (StringUtils.isNotEmpty(taskInDepart)) {}
        Boolean isTaskIn = getBoolean(request, "isTaskIn");
        if (null != isTaskIn) {
            String condition1 = (Boolean.TRUE.equals(isTaskIn) ? "" : "not ")
                    + "exists (from TaskInDepartment taskIn join taskIn.tasks tTask where tTask.id = task.id)";
            query.add(new Condition(condition1));
        }
        if (StringUtils.isNotEmpty(taskInDepart)) {
            String condition2 = "exists (from TaskInDepartment taskIn join taskIn.tasks tTask where tTask.id = task.id and taskIn.department.name like :departName)";
            query.add(new Condition(condition2, taskInDepart));
        }
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildTaskQuery(request, teachCalendarService.getTeachCalendar(getLong(
                request, "calendarId")));
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "allocations", utilService.search(query));
        
        initBaseInfos(request, "departments", Department.class);
        // 传递当指定一个院系时的院系
        Long departmentId = getLong(request, "departmentId");
        if (null != departmentId) {
            addSingleParameter(request, "department", departmentService.getDepartment(departmentId));
        }
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // primaryKey : {calendar.id, department.id}
        Long[] primaryKey = SeqStringUtil.transformToLong(StringUtils.split(get(request,
                "primaryKey"), "_"));
        EntityQuery query = new EntityQuery(TaskInDepartment.class, "taskIn");
        query.add(new Condition("taskIn.calendar.id = :calendarId", primaryKey[0]));
        query.add(new Condition("taskIn.department.id = :departmentId", primaryKey[1]));
        Collection taskIns = utilService.search(query);
        addSingleParameter(request, "department", departmentService.getDepartment(primaryKey[1]));
        
        if (CollectionUtils.isEmpty(taskIns)) {
            addSingleParameter(request, "taskIn", null);
            query.setLimit(getPageLimit(request));
            addCollection(request, "tasks", utilService.search(query));
            
        } else {
            TaskInDepartment taskIn = (TaskInDepartment) taskIns.iterator().next();
            addSingleParameter(request, "taskIn", taskIn);
            EntityQuery queryTask = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
            if (CollectionUtils.isEmpty(taskIn.getTasks())) {
                queryTask.add(new Condition("task is null"));
            } else {
                queryTask.add(new Condition("task in (:tasks)", taskIn.getTasks()));
            }
            queryTask.setLimit(getPageLimit(request));
            queryTask.addOrder(OrderUtils.parser(get(request, "orderBy")));
            addCollection(request, "tasks", utilService.search(queryTask));
        }
        return forward(request);
    }
    
    /**
     * 对选定院系添加指定未归属任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskInner(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        Set tasks = new HashSet();
        tasks.addAll(utilService.load(TeachTask.class, "id", taskIds));
        Long taskInId = getLong(request, "taskInId");
        TaskInDepartment taskIn = null;
        if (null == taskInId) {
            Long calendarId = getLong(request, "calendarId");
            Long departmentId = getLong(request, "departmentId");
            EntityQuery query = new EntityQuery(TaskInDepartment.class, "taskIn");
            query.add(new Condition("taskIn.calendar.id = :calendarId", calendarId));
            query.add(new Condition("taskIn.department.id = :departmentId", departmentId));
            Collection taskIns = utilService.search(query);
            if (CollectionUtils.isNotEmpty(taskIns)) {
                taskIn = (TaskInDepartment) taskIns.iterator().next();
            } else {
                taskIn = new TaskInDepartment();
                taskIn.setCalendar(teachCalendarService.getTeachCalendar(calendarId));
                taskIn.setDepartment(departmentService.getDepartment(departmentId));
            }
        } else {
            taskIn = (TaskInDepartment) utilService.get(TaskInDepartment.class, taskInId);
        }
        taskIn.addAllTask(tasks);
        utilService.saveOrUpdate(taskIn);
        return redirect(request, "index", "info.action.success");
    }
    
    /**
     * 自动分配开课院系与上课院系相同的任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward autoInner(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendarId"));
        Collection taskIns = utilService.load(TaskInDepartment.class, "calendar.id", calendar
                .getId());
        Map taskInMap = new HashMap();
        for (Iterator it = taskIns.iterator(); it.hasNext();) {
            TaskInDepartment taskIn = (TaskInDepartment) it.next();
            // System.out.println(taskIn.getDepartment().getId());
            taskInMap.put(taskIn.getDepartment(), taskIn);
        }
        
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        query.add(new Condition("task.calendar = (:calendar)", calendar));
        query.add(new Condition("task.arrangeInfo.teachDepart = task.teachClass.depart"));
        Collection tasks = utilService.search(query);
        
        for (Iterator it = tasks.iterator(); it.hasNext();) {
            TeachTask task = (TeachTask) it.next();
            Department department = task.getArrangeInfo().getTeachDepart();
            if (!taskInMap.containsKey(department)) {
                taskInMap.put(department, new TaskInDepartment());
            }
            TaskInDepartment taskIn = (TaskInDepartment) taskInMap.get(department);
            if (null == taskIn.getCalendar()) {
                taskIn.setCalendar(calendar);
                taskIn.setDepartment(department);
            }
            taskIn.addTask(task);
        }
        utilService.saveOrUpdate(taskInMap.values());
        return redirect(request, "index", "info.action.success");
    }
    
    /**
     * 对选定院系取消指定已归属任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskOutter(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
        Set tasks = new HashSet();
        tasks.addAll(utilService.load(TeachTask.class, "id", taskIds));
        Long taskInId = getLong(request, "taskInId");
        TaskInDepartment taskIn = (TaskInDepartment) utilService.get(TaskInDepartment.class,
                taskInId);
        taskIn.getTasks().removeAll(tasks);
        utilService.saveOrUpdate(taskIn);
        return redirect(request, "index", "info.action.success");
    }
    
    public void setTeachTaskSearchHelper(TeachTaskSearchHelper teachTaskSearchHelper) {
        this.teachTaskSearchHelper = teachTaskSearchHelper;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
}
