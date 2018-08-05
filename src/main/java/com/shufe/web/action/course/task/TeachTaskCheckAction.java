//$Id: TaskPlanCompare.java,v 1.1 2008-11-10 上午09:39:30 zhouqi Exp $
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
 * zhouqi               2008-11-10          Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.User;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.OnCampusTimeNotFoundException;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TaskGenParams;

/**
 * 任务与计划核对
 * 
 * @author zhouqi
 * 
 */
public class TeachTaskCheckAction extends TeachTaskCollegeAction {
    
    protected TeachTaskDAO teachTaskDAO;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List stdTypes = baseInfoService.getBaseInfos(StudentType.class);
        User user = getUser(request.getSession());
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        
        // 找最适合用户的学生类别
        StudentType stdType = null;
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
        addCollection(request, "departmentList", getTeachDeparts(request));
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "adminClasses", utilService.search(baseInfoSearchHelper
                .buildAdminClassQuery(request)));
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] adminClassIds = SeqStringUtil.transformToLong(get(request, "adminClassIds"));
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "task.calendar.id"));
        Collection adminClasses = utilService.load(AdminClass.class, "id", adminClassIds);
        addCollection(request, "adminClasses", adminClasses);
        Map adminPlanMap = new HashMap();
        // 所选班级
        Map adminClassMap = new HashMap();
        for (Iterator it1 = adminClasses.iterator(); it1.hasNext();) {
            AdminClass adminClass = (AdminClass) it1.next();
            
            Set courses = new HashSet();
            // courses of current term
            TermCalculator termCalc = new TermCalculator(teachCalendarService, calendar);
            Boolean onCampusTimeNotFound = Boolean.FALSE;
            TeachPlan plan = null;
            try {
                int term = termCalc.getTerm(adminClass.getStdType(), adminClass.getEnrollYear(),
                        Boolean.FALSE);
                
                plan = (TeachPlan) adminPlanMap.get(adminClass);
                if (null == plan) {
                    plan = teachPlanService.getTeachPlan(adminClass);
                    adminPlanMap.put(adminClass, plan);
                }
                
                courses.addAll(plan.getCourses(term + ""));
            } catch (OnCampusTimeNotFoundException e) {
                onCampusTimeNotFound = Boolean.TRUE;
            }
            
            EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.FALSE);
            query.join("task.teachClass.adminClasses", "adminClass");
            query.add(new Condition("adminClass.id in (:adminClassIds)", new Long[] { adminClass
                    .getId() }));
            query.setSelect("distinct task.course");
            query.setLimit(null);
            Set taskCourses = new HashSet(utilService.search(query));
            Set all = new HashSet();
            all.addAll(courses);
            all.addAll(taskCourses);
            adminClassMap.put(adminClass.getId().toString(), new Object[] { courses, taskCourses,
                    all, onCampusTimeNotFound });
        }
        addSingleParameter(request, "adminClassMap", adminClassMap);
        return forward(request);
    }
    
    public ActionForward gen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendarId"));
        String adminCourse = get(request, "autoSetting");
        if (null == calendar || StringUtils.isEmpty(adminCourse)) {
            return forward(mapping, request, "error.parameters.illegal", "error");
        }
        TaskGenParams params = TaskGenParams.getDefault();
        params.setConfigType((ClassroomType) utilService.load(ClassroomType.class, new Long(57)));
        params.setWeeks(calendar.getWeeks());
        params.setUnitStatus(new Integer(1));
        params.setWeekStart(new Integer(1));
        
        String[] grouping = StringUtils.split(adminCourse, ",");
        Map planMap = new HashMap();
        List tasks = new ArrayList();
        // P表示生成指定的课程生成任务
        // A表示生成这个班级对应的培养计划生成教学任务
        for (int i = 0; i < grouping.length; i++) {
            String[] element = StringUtils.split(grouping[i], "_");
            AdminClass adminClass = (AdminClass) utilService.load(AdminClass.class, new Long(
                    element[1]));
            String key = adminClass.getEnrollYear()
                    + "_"
                    + adminClass.getStdType().getId()
                    + "_"
                    + adminClass.getDepartment().getId()
                    + "_"
                    + adminClass.getSpeciality().getId()
                    + "_"
                    + ((null == adminClass.getAspect()) ? "null" : adminClass.getAspect().getId()
                            + "");
            TeachPlan plan = (TeachPlan) planMap.get(key);
            if (null == plan) {
                plan = teachPlanService.getTeachPlan(adminClass);
                planMap.put(key, plan);
            }
            // 开始生成教学任务
            if (StringUtils.equals(element[0], "P")) {
                Course course = (Course) utilService.load(Course.class, new Long(element[2]));
                params.setCourseUnits(course.getExtInfo().getPeriod());
                TeachTask task = teachTaskService.genTeachTaskDetail(plan.getPlanCourse(course),
                        calendar, plan, adminClass, params);
                task.getTeachClass().processTaskForClass();
                tasks.add(task);
            } else if (StringUtils.equals(element[0], "A")) {
                tasks.addAll(transferPlanToTasks(plan, adminClass, calendar, params));
            } else {
                return forward(mapping, request, "error.parameters.illegal", "error");
            }
            teachTaskDAO.saveGenResult(plan, tasks);
        }
        
        return redirect(request, "search", "info.action.success");
    }
    
    protected List transferPlanToTasks(TeachPlan plan, AdminClass adminClass,
            TeachCalendar calendar, TaskGenParams params) {
        List tasks = new ArrayList();
        TermCalculator termCalc = new TermCalculator(teachCalendarService, calendar);
        int term = termCalc.getTerm(adminClass.getStdType(), adminClass.getEnrollYear(),
                Boolean.FALSE);
        for (Iterator iter = plan.getCourses(term + "").iterator(); iter.hasNext();) {
            tasks.add(teachTaskService.genTeachTaskDetail(plan.getPlanCourse((Course) iter.next()),
                    calendar, plan, adminClass, params));
        }
        return tasks;
    }
    
    public void setTeachTaskDAO(TeachTaskDAO teachTaskDAO) {
        this.teachTaskDAO = teachTaskDAO;
    }
}