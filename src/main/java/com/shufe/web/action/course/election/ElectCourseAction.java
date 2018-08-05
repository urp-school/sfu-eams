//$Id: ElectCourseAction.java,v 1.31 2007/01/16 03:41:58 duanth Exp $
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
 * chaostone             2005-12-12         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.election;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.baseinfo.model.SchoolDistrict;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.ekingstar.eams.system.time.WeekInfo;
import com.ekingstar.eams.teach.program.SubstituteCourse;
import com.ekingstar.eams.teach.program.service.SubstituteCourseService;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.CourseArrangeSwitch;
import com.shufe.model.course.arrange.resource.TimeTable;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.CourseTable;
import com.shufe.model.course.arrange.task.CourseTableSetting;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.course.election.ElectState;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.service.course.election.CreditConstraintService;
import com.shufe.service.course.election.ElectCourseService;
import com.shufe.service.course.election.ElectParamsService;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.DispatchBasicAction;
import com.shufe.web.action.course.arrange.task.TableTaskGroup;

/**
 * 学生选课界面响应类
 * 
 * @author chaostone 2005-12-14
 */
public class ElectCourseAction extends DispatchBasicAction {
    
    protected ElectCourseService electService;
    
    protected ElectParamsService paramsService;
    
    protected TeachCalendarService teachCalendarService;
    
    protected TeachCalendarService calendarService;
    
    protected TeachTaskService teachTaskService;
    
    protected TeachResourceService teachResourceService;
    
    protected TeachPlanService teachPlanService;
    
    protected CreditConstraintService creditConstraintService;
    
    protected TeachPlanService planService;
    
    protected GradeService gradeService;
    
    protected SubstituteCourseService substituteCourseService;
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
    public void setTeachResourceService(TeachResourceService teachResourceService) {
        this.teachResourceService = teachResourceService;
    }
    
    /**
     * 选课入口
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward prompt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ElectState state = (ElectState) request.getSession().getAttribute("electState");
        // 没有选课状态或者选课状态中的数据不是当前用户的
        if (null == state || !state.std.getStdNo().equals(getLoginName(request))) {
            Student std = getStudentFromSession(request.getSession());
            // 查找选课参数
            ElectParams params = paramsService.getAvailElectParams(std);
            if (null == params) {
                return forward(request, "notReady");
            }
            request.setAttribute("electParams", params);
            // 更新评教信息
            if (params.getIsCheckEvaluation().equals(Boolean.TRUE)) {
                request.setAttribute("isEvaluated", Boolean.valueOf(electService
                        .isPassEvaluation(std.getId())));
            }
        } else {
            if (state.params.getIsCheckEvaluation().equals(Boolean.TRUE)
                    && (null == state.isEvaluated || state.isEvaluated.equals(Boolean.FALSE))) {
                state.isEvaluated = Boolean.valueOf(electService
                        .isPassEvaluation(state.std.getId()));
            }
        }
        
        request.setAttribute("now", new java.util.Date(System.currentTimeMillis()));
        return forward(request, "prompt");
    }
    
    /**
     * 构建一个课程表
     * 
     * @param setting
     * @param resource
     * @return
     */
    private CourseTable buildCourseTable(CourseTableSetting setting, Entity resource) {
        CourseTable table = new CourseTable(resource, setting.getKind());
        List taskList = null;
        if (CourseTable.CLASS.equals(setting.getKind())) {
            for (int j = 0; j < setting.getTimes().length; j++) {
                table.addActivities(teachResourceService.getAdminClassActivities(resource
                        .getEntityId(), setting.getTimes()[j], CourseActivity.class));
            }
            if (setting.getIgnoreTask()) {
                return table;
            }
            Map taskGroupMap = new HashMap();
            // group by course with PlanCourse
            Map courseTypeMap = new HashMap();
            // 查询培养计划
            try {
                AdminClass adminClass = (AdminClass) resource;
                TeachPlan plan = teachPlanService.getTeachPlan(adminClass);
                OnCampusTime time = teachCalendarService.getOnCampusTime(adminClass.getStdType(),
                        adminClass.getEnrollYear());
                TermCalculator calc = new TermCalculator(teachCalendarService, setting
                        .getCalendar());
                int term = calc.getTermBetween(time.getEnrollCalendar(), setting.getCalendar(),
                        Boolean.TRUE);
                
                if (plan != null) {
                    for (Iterator iter = plan.getCourseGroups().iterator(); iter.hasNext();) {
                        CourseGroup group = (CourseGroup) iter.next();
                        String typeId = group.getCourseType().getId().toString();
                        for (Iterator it2 = group.getPlanCourses(String.valueOf(term)).iterator(); it2
                                .hasNext();) {
                            PlanCourse planCourse = (PlanCourse) it2.next();
                            courseTypeMap.put(planCourse.getCourse(), typeId);
                        }
                        TableTaskGroup taskGroup = (TableTaskGroup) taskGroupMap.get(typeId);
                        String[] credits = StringUtils.split(group.getCreditPerTerms(), ",");
                        if (!credits[term - 1].equals("0")) {
                            if (null == taskGroup) {
                                taskGroup = new TableTaskGroup(group.getCourseType());
                                taskGroupMap.put(typeId, taskGroup);
                            }
                            taskGroup.setCredit(Float.valueOf(credits[term - 1]));
                        }
                    }
                }
            } catch (Exception e) {
                log.debug("maybe some studentType with no plan,or on campusTime");
            }
            // 查询教学任务
            taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                    TeachTaskFilterCategory.ADMINCLASS, setting.getCalendar());
            // 不显示重复课程代码的课程
            Set courses = new HashSet();
            for (Iterator iter = taskList.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                if (!courses.contains(task.getCourse())) {
                    courses.add(task.getCourse());
                } else {
                    continue;
                }
                
                String typeId = task.getCourseType().getId().toString();
                if (MapUtils.isNotEmpty(courseTypeMap)
                        && null != courseTypeMap.get(task.getCourse())) {
                    typeId = (String) courseTypeMap.get(task.getCourse());
                }
                
                TableTaskGroup group = (TableTaskGroup) taskGroupMap.get(typeId);
                if (null == group) {
                    group = new TableTaskGroup(task.getCourseType());
                    taskGroupMap.put(typeId, group);
                }
                group.addTask(task);
            }
            taskList = new ArrayList();
            float credits = 0;
            for (Iterator iter = taskGroupMap.values().iterator(); iter.hasNext();) {
                TableTaskGroup taskGroup = (TableTaskGroup) iter.next();
                taskList.addAll(taskGroup.getTasks());
                credits += taskGroup.getCredit().floatValue();
            }
            table.setCredits(new Float(credits));
            List dd = new ArrayList(taskGroupMap.values());
            Collections.sort(dd);
            table.setTasksGroups(dd);
        }
        if (null == table.getTasks()) {
            table.setTasks(taskList);
        }
        
        return table;
    }
    
    /**
     * 选课主页面
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
        ElectState state = (ElectState) request.getSession().getAttribute("electState");
        StdCreditConstraint constraint = null;
        Student std = getStudentFromSession(request.getSession());
        request.setAttribute("std", std);
        // 获得行政班
        utilService.initialize(std.getAdminClasses());
        
        // 如果还没有选课状态,或者不是当前用户的选课状态
        if (null == state || !state.std.getStdNo().equals(getLoginName(request))) {
            // 查找选课参数
            Long paramsId = getLong(request, "electParams.id");
            if (null == paramsId) {
                return forwardError(mapping, request, ElectCourseService.noParamsNotExists);
            }
            ElectParams params = paramsService.getElectParams(paramsId);
            if (null == params) {
                return forwardError(mapping, request, ElectCourseService.noParamsNotExists);
            }
            
            // 检查日期
            if (!params.isTimeSuitable() || !params.isScopeSuitable(std) ) {
                request.setAttribute("now", new Date());
                request.setAttribute("electResultInfo", ElectCourseService.noTimeSuitable);
                return forward(request, "electResult");
            }
            // hibernet对象初始化，在使用之前
            utilService.initialize(params.getCalendar());
            utilService.initialize(params.getCalendar().getStudentType());
            utilService.initialize(params.getNotCurrentCourseTypes());
            // 查找学分上限
            constraint = (StdCreditConstraint) creditConstraintService.getStdCreditConstraint(std
                    .getId(), params.getCalendar());
            if (null == constraint) {
                return forwardError(mapping, request, ElectCourseService.notExistsCreditConstraint);
            }
            
            // 准备学生选课状态数据
            state = new ElectState(constraint, params);
            utilService.initialize(params.getCalendar().getTimeSetting().getCourseUnits());
            
            // 选课学期内重叠的其他学期
            List calendars = calendarService.getTeachCalendarsOfOverlapped(params.getCalendar());
            state.calendars = calendars;
            
            // 计算第一专业的选课学分(第二专业不选课)
            try {
                TeachPlan plan = planService.getTeachPlan(constraint.getStd(), Boolean.TRUE, null);
                OnCampusTime time = calendarService.getOnCampusTime(constraint.getStd().getType(),
                        constraint.getStd().getEnrollYear());
                TermCalculator calc = new TermCalculator(calendarService, state.params
                        .getCalendar());
                int term = calc.getTermBetween(time.getEnrollCalendar(), params.getCalendar(),
                        Boolean.TRUE);
                
                if (plan != null) {
                    state.getPlanCourses().clear();
                    for (Iterator iter = plan.getCourseGroups().iterator(); iter.hasNext();) {
                        CourseGroup group = (CourseGroup) iter.next();
                        String[] credits = StringUtils.split(group.getCreditPerTerms(), ",");
                        if (!credits[term - 1].equals("0")) {
                            state.addCreditNeeded(group.getCourseType(), Float
                                    .valueOf(credits[term - 1]));
                            utilService.initialize(group.getCourseType());
                        }
                        Set courses = new HashSet();
                        state.getPlanCourses().put(group.getCourseType(), courses);
                        for (Iterator it = group.getPlanCourses().iterator(); it.hasNext();) {
                            PlanCourse planCourse = (PlanCourse) it.next();
                            courses.add(planCourse.getCourse());
                        }
                    }
                }
            } catch (Exception e) {
                log.debug("maybe somebody with no plan,or on campusTime");
            }
            // 加载已经上过的课程(ID)和是否通过
            state.hisCourses = gradeService.getGradeCourseMap(state.std.getId());
            // 添加替代课程
            addSubstitueCourse(state);
            // 添加必修课
            electService.addCompulsoryCourse(state, constraint.getStd(), calendars);
            // 设置选课状态数据
            request.getSession().setAttribute("electState", state);
        } else {
            if (!state.getParams().isTimeSuitable()) {
                request.setAttribute("electResultInfo", ElectCourseService.noTimeSuitable);
                return forward(request, "electResult");
            }
        }
        
        // 检查评教状态
        if (state.params.getIsCheckEvaluation().equals(Boolean.TRUE)
                && (null == state.isEvaluated || state.isEvaluated.equals(Boolean.FALSE))) {
            state.isEvaluated = Boolean.valueOf(electService.isPassEvaluation(state.std.getId()));
        }
        
        request.setAttribute("weekList", WeekInfo.WEEKS);
        List taskList = teachTaskService.getTeachTasksOfStd(state.std.getId(), state.calendars);
        // 现在计算已选学分和已选的课程id
        float electedStat = 0;
        for (Iterator iter = taskList.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            utilService.initialize(task.getArrangeInfo().getActivities());
            utilService.initialize(task.getArrangeInfo().getTeachers());
            electedStat += task.getCourse().getCredits().floatValue();
            state.electedCourseIds.add(task.getCourse().getId());
        }
        //20091217 add by duantihua
        //考虑当前学期的选课记录
        TeachCalendar calendar =teachCalendarService.getCurTeachCalendar(state.std.getStdTypeId());
        if(!state.calendars.contains(calendar)){
        	List curTasks = teachTaskService.getTeachTasksOfStd(state.std.getId(),Collections.singletonList(calendar));
        	for (Iterator iter = curTasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                state.electedCourseIds.add(task.getCourse().getId());
            }
        }
        
        // 如果已选学分和实际不一样,则更新
        if (electedStat != state.electedCredit) {
            if (null == constraint) {
                constraint = (StdCreditConstraint) creditConstraintService.getStdCreditConstraint(
                        state.std.getId(), state.params.getCalendar());
                if (null == constraint) {
                    return forwardError(mapping, request,
                            ElectCourseService.notExistsCreditConstraint);
                }
            }
            constraint.setElectedCredit(new Float(electedStat));
            utilService.saveOrUpdate(constraint);
            state.electedCredit = electedStat;
        }
        // 构建课表
        state.table = new TimeTable(taskList);
        request.setAttribute("taskList", taskList);
        request.setAttribute("nowAt", new Date());
        return forward(request);
    }
    
    /**
     * 选课-学生班级课表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskListOfClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendarId"));
        CourseTableSetting setting = new CourseTableSetting(calendar);
        setting.setTimes(getTimesFormPage(request, calendar));
        setting.setWeekInfos(WeekInfo.WEEKS);
        setting.setDisplayCalendarTime(true);
        RequestUtil.populate(request, setting, "setting");
        
        request.setAttribute("courseTable", buildCourseTable(setting, getStudentFromSession(
                request.getSession()).getFirstMajorClass()));
        request.setAttribute("userCategory", getUserCategoryId(request));
        request.setAttribute("setting", setting);
        request.setAttribute("calendar", calendar);
        List switches = utilService
                .load(CourseArrangeSwitch.class, "calendar.id", calendar.getId());
        if (CollectionUtils.isNotEmpty(switches)) {
            request.setAttribute("switch", switches.get(0));
        }
        return forward(request);
    }
    
    protected TimeUnit[] getTimesFormPage(HttpServletRequest request, TeachCalendar calendar) {
        Integer startWeek = getInteger(request, "startWeek");
        Integer endWeek = getInteger(request, "endWeek");
        if (null == startWeek)
            startWeek = new Integer(1);
        if (null == endWeek)
            endWeek = calendar.getWeeks();
        if (startWeek.intValue() < 1)
            startWeek = new Integer(1);
        if (endWeek.intValue() > calendar.getWeeks().intValue())
            endWeek = calendar.getWeeks();
        request.setAttribute("startWeek", startWeek);
        request.setAttribute("endWeek", endWeek);
        return TimeUnitUtil.buildTimeUnits(calendar.getStartYear(), calendar.getWeekStart()
                .intValue(), startWeek.intValue(), endWeek.intValue(), TimeUnit.CONTINUELY);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ElectState state = (ElectState) request.getSession().getAttribute("electState");
        if (null == state)
            return prompt(mapping, form, request, response);
        TimeUnit time = (TimeUnit) populate(request, TimeUnit.class, "timeUnit");
        TeachTask task = (TeachTask) populate(request, TeachTask.class, Constants.TEACHTASK);
        task.getRequirement().setCourseCategory(null); 
        String teacherName = request.getParameter("teacher.name");
        if (StringUtils.isNotEmpty(teacherName)) {
            Teacher teacher = new Teacher();
            teacher.setName(teacherName);
            task.getArrangeInfo().getTeachers().add(teacher);
        }
        Pagination taskPage = null;
        if (Boolean.TRUE.equals(state.getParams().getIsSchoolDistrictRestrict())) {
            task.getArrangeInfo().setSchoolDistrict(
                    new SchoolDistrict(state.getStd().getSchoolDistrictId()));
        } else {
            task.getArrangeInfo().setSchoolDistrict(null);
        }
        boolean reChoosePassedCourse = getSystemConfig().getBooleanParam(
                "election.reChoosePassedCourse");
        Boolean isRestudy = getBoolean(request, "isRestudy");
        if (null != isRestudy && Boolean.TRUE.equals(isRestudy)) {
            Set unPassedCourseIds = null;
            if (reChoosePassedCourse) {
                unPassedCourseIds = state.hisCourses.keySet();
            } else {
                unPassedCourseIds = state.getUnPassedCourseIds();
            }
            if (unPassedCourseIds.isEmpty()) {
                taskPage = new Pagination(getPageNo(request), getPageSize(request), new Result(0,
                        Collections.EMPTY_LIST));
            } else {
                // 重修是否限制范围,依照选课参数
                taskPage = electService.getElectableTasks(task, state, unPassedCourseIds, time,
                        state.getParams().getIsCheckScopeForReSturdy().booleanValue(),
                        getPageNo(request), getPageSize(request));
            }
        } else {
            // 非重修限制选课范围
            taskPage = electService.getElectableTasks(task, state, null, time, true,
                    getPageNo(request), getPageSize(request));
        }
        addOldPage(request, "taskList", taskPage);
        return forward(request);
    }
    
    private void addSubstitueCourse(ElectState state) {
        Set unPassedCourseIds = state.getUnPassedCourseIds();
        Student student = (Student) utilService.load(Student.class, state.std
                .getId());
        List planCoursesList = substituteCourseService
                .getStdSubstituteCourses(student);
        for (Iterator iterator = planCoursesList.iterator(); iterator.hasNext();) {
            SubstituteCourse subCourse = (SubstituteCourse) iterator.next();
            boolean isPass = false;
            Set originIds = new HashSet();
            for (Iterator it2 = subCourse.getOrigins().iterator(); it2.hasNext();) {
                Course course = (Course) it2.next();
                originIds.add(course.getId());
                boolean pass = gradeService.isPass(student, course);
                if (pass) {
                    isPass = pass;
                }
            }
            Set substituteIds = new HashSet();
            for (Iterator it2 = subCourse.getSubstitutes().iterator(); it2.hasNext();) {
                Course course = (Course) it2.next();
                substituteIds.add(course.getId());
                boolean pass = gradeService.isPass(student, course);
                if (pass) {
                    isPass = pass;
                }
            }
            if (unPassedCourseIds.containsAll(originIds)) {
                unPassedCourseIds.addAll(substituteIds);
            }
            state.getSubstituteIds().addAll(substituteIds);
            
            if (isPass) {
                state.getPassSubstituteIds().addAll(originIds);
                state.getPassSubstituteIds().addAll(substituteIds);
            }
        }
        for (Iterator iter = unPassedCourseIds.iterator(); iter.hasNext();) {
            Long courseId = (Long) iter.next();
            state.getHisCourses().put(courseId, Boolean.FALSE);
        }
        // 已通过的有替代课程的课程成绩
        for (Iterator iter = state.getPassSubstituteIds().iterator(); iter.hasNext();) {
            Long courseId = (Long) iter.next();
            state.getHisCourses().put(courseId, Boolean.TRUE);
        }
    }
    
    /**
     * 选课
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward elect(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ElectState state = (ElectState) request.getSession().getAttribute("electState");
        if (null == state) {
            return prompt(mapping, form, request, response);
        }
        if (!state.getParams().isTimeSuitable()) {
            request.setAttribute("electResultInfo", ElectCourseService.noTimeSuitable);
            return forward(request, "electResult");
        }
        if (getLoginName(request).equals(state.getStd().getStdNo())) {
            String taskId = request.getParameter(Constants.TEACHTASK_KEY);
            if (StringUtils.isEmpty(taskId)) {
                return forward(mapping, request, "error.teachTask.id.needed", "error");
            }
            TeachTask task = teachTaskService.getTeachTask(Long.valueOf(taskId));
            Long courseTakeTypeId = getLong(request, "courseTakeType.id");
            CourseTakeType takeType = new CourseTakeType(CourseTakeType.ELECTIVE);
            if (null != courseTakeTypeId) {
                takeType.setId(courseTakeTypeId);
            }
            
            request.setAttribute("electResultInfo", electService.elect(task, state, takeType));
        } else {
            request.setAttribute("electResultInfo", "error.elect.wrongStdCode");
        }
        return forward(request, "electResult");
    }
    
    /**
     * 退课
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ElectState state = (ElectState) request.getSession().getAttribute("electState");
        if (null == state) {
            return prompt(mapping, form, request, response);
        }
        if (!state.getParams().isTimeSuitable()) {
            request.setAttribute("electResultInfo", ElectCourseService.noTimeSuitable);
            return forward(request, "electResult");
        }
        if (getLoginName(request).equals(state.getStd().getStdNo())) {
            String taskId = request.getParameter(Constants.TEACHTASK_KEY);
            if (StringUtils.isEmpty(taskId)) {
                return forward(mapping, request, "error.teachTask.id.needed", "error");
            }
            
            TeachTask task = teachTaskService.getTeachTask(Long.valueOf(taskId));
            String info = electService.cancel(task, state);
            request.setAttribute("electResultInfo", info);
        } else {
            request.setAttribute("electResultInfo", "error.elect.wrongStdCode");
        }
        return forward(request, "electResult");
    }
    
    public void setElectService(ElectCourseService electService) {
        this.electService = electService;
    }
    
    public void setParamsService(ElectParamsService paramsService) {
        this.paramsService = paramsService;
    }
    
    public void setCalendarService(TeachCalendarService calendarService) {
        this.calendarService = calendarService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public void setCreditConstraintService(CreditConstraintService creditConstraintService) {
        this.creditConstraintService = creditConstraintService;
    }
    
    public void setPlanService(TeachPlanService planService) {
        this.planService = planService;
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    public void setSubstituteCourseService(SubstituteCourseService substituteCourseService) {
        this.substituteCourseService = substituteCourseService;
    }
    
}
