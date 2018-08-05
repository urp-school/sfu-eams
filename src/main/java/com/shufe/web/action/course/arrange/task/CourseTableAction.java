//$Id: CourseTableAction.java,v 1.13 2007/01/23 01:14:10 duanth Exp $
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
 * chaostone             2005-12-14         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.MultiPropertyComparator;
import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.ekingstar.eams.system.time.WeekInfo;
import com.ekingstar.security.User;
import com.ekingstar.security.model.UserCategory;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.CourseArrangeSwitch;
import com.shufe.model.course.arrange.TaskActivity;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.CourseTable;
import com.shufe.model.course.arrange.task.CourseTableCheck;
import com.shufe.model.course.arrange.task.CourseTableSetting;
import com.shufe.model.course.arrange.task.MultiCourseTable;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.OnCampusTime;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.std.StudentService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.BaseInfoSearchHelper;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 课程表显示界面相应类. 可以显示 <br>
 * 1)管理人员对班级、学生和教师的课程复杂查询管理界面<br>
 * 2)学生对自己个人课表和班级（包括双专业班级）的课表<br>
 * 3)教师对自己个人课表的浏览.<br>
 * <p>
 * 所有的课表均用一个课表显示界面.上部为课表，下部为教学任务列表.
 * </p>
 * 
 * @author chaostone 2005-12-14
 */
public class CourseTableAction extends CalendarRestrictionSupportAction {
    
    protected TeachTaskService teachTaskService;
    
    protected BaseInfoSearchHelper baseInfoSearchHelper;
    
    protected StudentService studentService;
    
    protected TeachResourceService teachResourceService;
    
    protected TeachPlanService teachPlanService;
    
    protected StdSearchHelper stdSearchHelper;
    
    /**
     * 学生查看课表的主界面响应
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = getUser(request.getSession());
        Student std = studentService.getStudent(user.getName());
        if (null == std) {
            return forward(mapping, request, "error.std.stdNo.needed", "error");
        }
        std.getType().getSubTypes().size();
        // std.getType().getTeachCalendars().size();
        utilService.initialize(std.getAdminClasses());
        TeachCalendar calendar = null;
        String year = get(request, "calendar.year");
        String term = get(request, "calendar.term");
        if (StringUtils.isEmpty(year)) {
            calendar = teachCalendarService.getTeachCalendar(std.getType());
        } else {
            calendar = teachCalendarService.getTeachCalendar(std.getType().getId(), year, term);
        }
        if (null == calendar) {
            return forward(mapping, request, "error.calendar.notMaded", "error");
        }
        
        request.setAttribute("std", std);
        request.setAttribute("studentType", std.getType());
        request.setAttribute("stdTypeList", Collections.singleton(std.getType()));
        request.setAttribute(Constants.CALENDAR, calendar);
        // 查询课表核对数据
        EntityQuery query = new EntityQuery(CourseTableCheck.class, "check");
        query.add(new Condition("check.std=:std", std));
        query.add(new Condition("check.calendar=:calendar", calendar));
        List rs = (List) utilService.search(query);
        CourseTableCheck check = null;
        // 查询已有的课程
        List taskList = teachTaskService.getTeachTasksByCategory(std.getId(),
                TeachTaskFilterCategory.STD, teachCalendarService
                        .getTeachCalendarsOfOverlapped(calendar));
        if (rs.isEmpty()) {
            check = new CourseTableCheck(std, calendar);
        } else {
            check = (CourseTableCheck) rs.get(0);
        }
        if (check.updateCredit(taskList)) {
            check.setIsConfirm(Boolean.FALSE);
            check.setConfirmAt(null);
        }
        utilService.saveOrUpdate(check);
        request.setAttribute("courseTableCheck", check);
        return forward(request);
    }
    
    /**
     * 教师查看课表的主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward teacherHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        List stdTypeList = teachTaskService.getStdTypesForTeacher(teacher);
        if (stdTypeList.isEmpty())
            return forward(mapping, request, "error.teacher.noTask", "error");
        request.setAttribute("stdTypeList", stdTypeList);
        
        request.setAttribute(Constants.CALENDAR, teachCalendarService
                .getTeachCalendar((StudentType) stdTypeList.iterator().next()));
        request.setAttribute(Constants.TEACHER, teacher);
        return forward(request);
    }
    
    /**
     * 管理人员查看课表入口（主界面）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward managerHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        List classroomConfigTypeList = baseCodeService.getCodes(ClassroomType.class);
        addCollection(request, "classroomConfigTypeList", classroomConfigTypeList);
        initBaseCodes(request, "districtList", SchoolDistrict.class);
        List teacherDeparts = getDeparts(request);
        addCollection(request, "teacherDeparts", teacherDeparts);
        return forward(request, "index");
    }
    
    public ActionForward publicHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List departList = departmentService.getColleges();
        request.setAttribute(Constants.DEPARTMENT_LIST, departList);
        List stdTypeList = baseInfoService.getBaseInfos(StudentType.class);
        request.setAttribute("stdTypeList", stdTypeList);
        
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        StudentType stdType = null;
        if (null != stdTypeId) {
            stdType = studentTypeService.getStudentType(stdTypeId);
        } else {
            stdType = (StudentType) stdTypeList.get(0);
        }
        setCalendar(request, stdType);
        stdType = (StudentType) request.getAttribute(Constants.STUDENTTYPE);
        List calendarStdTypes = teachCalendarService.getCalendarStdTypes(stdType.getId());
        Collections.sort(calendarStdTypes, new PropertyComparator("code"));
        addCollection(request, CalendarRestrictionSupportAction.CALENDAR_STDTYPES_KEY,
                calendarStdTypes);
        
        List classroomConfigTypeList = baseCodeService.getCodes(ClassroomType.class);
        addCollection(request, "classroomConfigTypeList", classroomConfigTypeList);
        initBaseCodes(request, "districtList", SchoolDistrict.class);
        addCollection(request, "teacherDeparts", departmentService.getDepartments());
        return forward(request, "index");
    }
    
    /**
     * 获得某一教学任务的课程安排
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskTable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskId = request.getParameter(Constants.TEACHTASK_KEY);
        if (StringUtils.isEmpty(taskId)) {
            taskId = get(request, "taskId");
        }
        TeachTask task = teachTaskService.getTeachTask(Long.valueOf(taskId));
        Set activities = task.getArrangeInfo().getActivities();
        request.setAttribute("startWeek", new Integer(1));
        request.setAttribute("endWeek", task.getCalendar().getWeeks());
        request.setAttribute("weekList", WeekInfo.WEEKS);
        request.setAttribute("activityList", activities);
        request.setAttribute("task", task);
        request.setAttribute(Constants.CALENDAR, task.getCalendar());
        
        List courseArranges = utilService.load(CourseArrangeSwitch.class, "calendar.id", task.getCalendar()
                .getId());
        //控制排课结果的可见性
        Boolean arrangeSwitch = Boolean.TRUE;
        if (!CollectionUtils.isEmpty(courseArranges)
                && Boolean.FALSE.equals(((CourseArrangeSwitch) courseArranges.get(0))
                        .getIsPublished())) {
            arrangeSwitch = Boolean.FALSE;
        }
        request.setAttribute("arrangeSwitch", arrangeSwitch);
        
        //确定用户身份
        User user = getUser(request.getSession());
        addSingleParameter(request, "user", user);
        Boolean isAdmin = Boolean.FALSE;
        Set categories = user.getCategories();
        if (null != categories) {
            Iterator it = categories.iterator();
            while (it.hasNext()) {
                UserCategory temp = (UserCategory) it.next();
                if (temp.getId().equals(EamsRole.MANAGER_USER)) {
                    isAdmin = Boolean.TRUE;
                    break;
                }
            }
        }
        request.setAttribute("isAdmin", isAdmin);
        
        return forward(request);
    }
    
    /**
     * 新课表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward courseTableOfTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] adminClassIds = SeqStringUtil.transformToLong(get(request, "adminClassIds"));
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        Map courseTables = new HashMap();
        List adminClasses = new ArrayList();
        for (int i = 0; i < adminClassIds.length; i++) {
            EntityQuery query = new EntityQuery(CourseActivity.class, "courseActivity");
            query.add(new Condition("courseActivity.calendar = :calendar", calendar));
            query.join("courseActivity.task.teachClass.adminClasses", "adminClass");
            query.add(new Condition("adminClass.id = :adminClassId", adminClassIds[i]));
            courseTables.put(adminClassIds[i].toString(), utilService.search(query));
            adminClasses.add(baseInfoService.getBaseInfo(AdminClass.class, adminClassIds[i]));
        }
        request.setAttribute("courseTables", courseTables);
        request.setAttribute("adminClasses", adminClasses);
        Collections.sort(adminClasses, new MultiPropertyComparator(
                "department.code,aspect.code,code"));
        request.setAttribute("weeks", WeekInfo.WEEKS);
        return forward(request);
    }
    /**
     * 查询教学资源对象（教师、学生、班级,教室）
     * <p>
     * courseTableType为页面回传的对象类别参数<br>
     * 1)class 班级 2)std 学生 3)teacher 教师 4)room
     * </p>
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
        String kind = get(request, "courseTableType");
        if (StringUtils.isEmpty(kind))
            return forward(mapping, request, "error.courseTable.unknown", "error");
        request.setAttribute(Constants.CALENDAR, teachCalendarService.getTeachCalendar(getLong(
                request, "calendar.id")));
        if (CourseTable.CLASS.equals(kind)) {
            EntityQuery query = baseInfoSearchHelper.buildAdminClassQuery(request);
            addCollection(request, "adminClasses", utilService.search(query));
            return forward(request, "adminClassList");
        } else if (CourseTable.STD.equals(kind)) {
            EntityQuery query = stdSearchHelper.buildStdQuery(request);
            addCollection(request, "students", utilService.search(query));
            return forward(request, "stdList");
        } else if (CourseTable.ROOM.equals(kind)) {
            EntityQuery query = baseInfoSearchHelper.buildClassroomQuery(request);
            addCollection(request, "classrooms", utilService.search(query));
            return forward(request, "classroomList");
        } else {
            EntityQuery query = baseInfoSearchHelper.buildTeacherQuery(request);
            addCollection(request, "teachers", utilService.search(query));
            return forward(request, "teacherList");
        }
    }
    
    /**
     * 课程表
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @return
     * @throws Exception
     */
    public ActionForward courseTable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 查找课表设置参数
        TeachCalendar calendar = getCalendar(request);
        if (calendar == null) {
            return forwardError(mapping, request, "error.calendar.id.notExists");
        }
        CourseTableSetting setting = new CourseTableSetting(calendar);
        setting.setTimes(getTimesFormPage(request, calendar));
        RequestUtil.populate(request, setting, "setting");
        if (StringUtils.isEmpty(setting.getKind()))
            return forward(mapping, request, "error.courseTable.unknown", "error");
        // 查找课表对象
        String ids = RequestUtils.get(request, "ids");
        if (StringUtils.isEmpty(ids)) {
            request.setAttribute("prompt", "common.lessOneSelectPlease");
            return forward(request, "prompt");
        }
        EntityQuery entityQuery = new EntityQuery(CourseTable.getResourceClass(setting.getKind()),
                "resource");
        entityQuery.add(new Condition("instr('," + ids + ",', ','||resource.id||',')>0"));
        List resources = (List) utilService.search(entityQuery);
        
        String orderBy = get(request, "setting.orderBy");
        if (StringUtils.equals(setting.getKind(), CourseTable.CLASS)) {
            orderBy = StringUtils.replace(orderBy, "adminClass.", "");
        } else if (StringUtils.equals(setting.getKind(), CourseTable.STD)) {
            orderBy = StringUtils.replace(orderBy, "student.", "");
        } else if (StringUtils.equals(setting.getKind(), CourseTable.ROOM)) {
            orderBy = StringUtils.replace(orderBy, "classroom.", "");
        } else if (StringUtils.equals(setting.getKind(), CourseTable.TEACHER)) {
            orderBy = StringUtils.replace(orderBy, "teacher.", "");
        }
        List orders = OrderUtils.parser(orderBy);
        if (orders.isEmpty()) {
            orders.add(new Order("code asc"));
        }
        Order order = (Order) orders.get(0);
        Collections.sort(resources, new PropertyComparator(order.getProperty(), order
                .getDirection() == Order.ASC));
        
        // 组装课表，区分单个课表和每页多个课表两种情况
        List courseTableList = new ArrayList();
        // 是否发布排课结果
        if (setting.getTablePerPage() == 1) {
            for (Iterator reourcesIter = resources.iterator(); reourcesIter.hasNext();) {
                Entity resource = (Entity) reourcesIter.next();
                courseTableList.add(buildCourseTable(setting, resource));
                // closedCourseArrange(courseTable, arrangeSwitch);
            }
        } else {
            int i = 0;
            MultiCourseTable multiTable = null;
            for (Iterator reourcesIter = resources.iterator(); reourcesIter.hasNext();) {
                if (i % setting.getTablePerPage() == 0) {
                    multiTable = new MultiCourseTable();
                    courseTableList.add(multiTable);
                }
                Entity resource = (Entity) reourcesIter.next();
                multiTable.getResources().add(resource);
                CourseTable courseTable = buildCourseTable(setting, resource);
                multiTable.getTables().add(courseTable);
                // closedCourseArrange(courseTable, arrangeSwitch);
                i++;
            }
        }
        setting.setWeekInfos(WeekInfo.WEEKS);
        setting.setDisplayCalendarTime(true);
        request.setAttribute("courseTableList", courseTableList);
        request.setAttribute("setting", setting);
        request.setAttribute("userCategory", getUserCategoryId(request));
        
        List switches = utilService
                .load(CourseArrangeSwitch.class, "calendar.id", calendar.getId());
        if (CollectionUtils.isNotEmpty(switches)) {
            request.setAttribute("switch", switches.get(0));
        }
        if (1 == setting.getTablePerPage()) {
            return forward(request);
        } else {
            return forward(request, "courseTable_" + setting.getStyle());
        }
    }
    
    /**
     * 处理学生界面未发布排课的记录
     * 
     * @param setting
     * @param courseTableList
     * @param arrangeSwitch
     * @param resource
     */
    protected void closedCourseArrange(CourseTable courseTable, CourseArrangeSwitch arrangeSwitch) {
        if (Boolean.FALSE.equals(arrangeSwitch.getIsPublished())) {
            courseTable.getActivities().clear();
            for (Iterator it2 = courseTable.getTasks().iterator(); it2.hasNext();) {
                TeachTask task = (TeachTask) it2.next();
                task.setCalendar(null);
            }
        }
    }
    
    /**
     * 教学活动教室占用情况汇总表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward classroomActivityStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] classroomIds = SeqStringUtil.transformToLong(get(request, "classroomIds"));
        EntityQuery query = new EntityQuery(CourseActivity.class, "activity");
        populateConditions(request, query);
        if (!(null == classroomIds || classroomIds.length == 0)) {
            query.add(new Condition("activity.room.id in (:roomIds)", classroomIds));
        }
        Collection activities = utilService.search(query);
        
        Map roomMap = new HashMap();
        Set rooms = new HashSet();
        for (Iterator it = activities.iterator(); it.hasNext();) {
            CourseActivity activity = (CourseActivity) it.next();
            if (null == activity.getRoom()) {
                continue;
            }
            for (int i = activity.getTime().getStartUnit().intValue(); i <= activity.getTime()
                    .getEndUnit().intValue(); i++) {
                String key = activity.getRoom().getId() + "_";
                key += activity.getTime().getWeekId().intValue() + (i < 10 ? "0" + i : "" + i);
                roomMap.put(key, activity);
                rooms.add(activity.getRoom());
            }
        }
        request.setAttribute("roomMap", roomMap);
        
        List classrooms = new ArrayList(rooms);
        String orderBy = get(request, "setting.orderBy");
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = "building.code,name";
        } else {
            orderBy = StringUtils.replace(orderBy, "classroom.", "");
        }
        Collections.sort(classrooms, new MultiPropertyComparator(orderBy));
        addCollection(request, "classrooms", classrooms);
        request.setAttribute("calendar", teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id")));
        return forward(request);
    }
    
    /**
     * 教室明细汇总
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward roomActivityDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "activities", getCourseActivities(request));
        request.setAttribute("calendar", teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id")));
        return forward(request);
    }
    
    /**
     * @param request
     * @return
     */
    protected Collection getCourseActivities(HttpServletRequest request) {
        Long[] classroomIds = SeqStringUtil.transformToLong(get(request, "classroomIds"));
        EntityQuery query = new EntityQuery(CourseActivity.class, "activity");
        populateConditions(request, query);
        String orderBy = get(request, "orderBy");
        if (!(null == classroomIds || classroomIds.length == 0)) {
            query.add(new Condition("activity.room.id in (:roomIds)", classroomIds));
            StringUtils.replace(orderBy, "classroom.", "room.");
        } else {
            orderBy = "activity.room.building.code,activity.time.weekId,activity.time.startUnit,activity.time.endUnit,activity.room.name";
        }
        query.addOrder(OrderUtils.parser(orderBy));
        Collection temp = utilService.search(query);
        return temp;
    }
    
    /**
     * 教室明细导出
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#configExportContext(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.transfer.exporter.Context)
     */
    protected void configExportContext(HttpServletRequest request, Context context) {
        Collection activities = getCourseActivities(request);
        context.put("activities", activities);
        context.put("calendar", teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id")));
        Map map = new HashMap();
        int i = 1;
        for (Iterator it = activities.iterator(); it.hasNext();) {
            map.put(it.next(), new Integer(i++));
        }
        context.put("map", map);
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
                taskList.addAll(taskGroup.tasks);
                credits += taskGroup.getCredit().floatValue();
            }
            table.setCredits(new Float(credits));
            List dd = new ArrayList(taskGroupMap.values());
            Collections.sort(dd);
            table.setTasksGroups(dd);
        } else if (CourseTable.TEACHER.equals(setting.getKind())) {
            for (int j = 0; j < setting.getTimes().length; j++) {
                table.addActivities(teachResourceService.getTeacherActivities(resource
                        .getEntityId(), setting.getTimes()[j], CourseActivity.class, setting
                        .getForCalendar() ? setting.getCalendar() : null));
            }
            // 
            if (setting.getIgnoreTask()) {
                return table;
            }
            if (setting.getForCalendar()) {
                taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                        TeachTaskFilterCategory.TEACHER, setting.getCalendar());
            } else {
                taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                        TeachTaskFilterCategory.TEACHER, teachCalendarService
                                .getTeachCalendarsOfOverlapped(setting.getCalendar()));
            }
            Teacher teacher = (Teacher) utilService.load(Teacher.class, "id",
                    resource.getEntityId()).get(0);
            Set taskSet = new HashSet();
            taskSet.addAll(taskList);
            for (Iterator it = table.getActivities().iterator(); it.hasNext();) {
                TaskActivity activity = (TaskActivity) it.next();
                if (taskSet.contains(activity.getTask())) {
                    taskSet.remove(activity.getTask());
                }
            }
            for (Iterator it = taskSet.iterator(); it.hasNext();) {
                TeachTask task = (TeachTask) it.next();
                if (task.getArrangeInfo().getTeachers().contains(teacher)) {
                    table.addActivities(task.getArrangeInfo().getActivities());
                }
            }
        } else if (CourseTable.STD.equals(setting.getKind())) {
            for (int j = 0; j < setting.getTimes().length; j++) {
                table.addActivities(teachResourceService.getStdActivities((Long) resource
                        .getEntityId(), setting.getTimes()[j], CourseActivity.class, setting
                        .getForCalendar() ? setting.getCalendar() : null));
            }
            if (setting.getIgnoreTask())
                return table;
            if (setting.getForCalendar()) {
                taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                        TeachTaskFilterCategory.STD, setting.getCalendar());
            } else {
            	List<TeachCalendar> calendar=teachCalendarService
                .getTeachCalendarsOfOverlapped(setting.getCalendar());
                taskList = teachTaskService.getTeachTasksByCategory(resource.getEntityId(),
                        TeachTaskFilterCategory.STD, teachCalendarService
                                .getTeachCalendarsOfOverlapped(setting.getCalendar()));
            }
        } else if (CourseTable.ROOM.equals(setting.getKind())) {
            for (int j = 0; j < setting.getTimes().length; j++) {
                table.addActivities(teachResourceService.getRoomActivities(resource.getEntityId(),
                        setting.getTimes()[j], CourseActivity.class,
                        setting.getForCalendar() ? setting.getCalendar() : null));
            }
            if (setting.getIgnoreTask())
                return table;
            // 教室的教学任务列表从教学活动中抽取
            table.extractTaskFromActivity();
        }
        if (null == table.getTasks())
            table.setTasks(taskList);
        
        return table;
    }
    
    /**
     * 如果课程表要扩展到支持每周的课表显示， 则可以用次函数和resoureceService服务 查询、显示教学活动.
     * 
     * @param request
     * @param calendar
     * @return
     */
    private TimeUnit[] getTimesFormPage(HttpServletRequest request, TeachCalendar calendar) {
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
    
    private TeachCalendar getCalendar(HttpServletRequest request) {
        TeachCalendar calendar = (TeachCalendar) RequestUtil.populate(request, TeachCalendar.class,
                Constants.CALENDAR, false);
        if (null != calendar.getId() && 0 != calendar.getId().intValue()) {
            calendar = teachCalendarService.getTeachCalendar(calendar.getId());
        } else {
            calendar = teachCalendarService.getTeachCalendar(calendar.getStudentType(), calendar
                    .getYear(), calendar.getTerm());
        }
        request.setAttribute(Constants.CALENDAR, calendar);
        return calendar;
    }
    
    /**
     * @param teachTaskService
     *            The teachTaskService to set.
     */
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    public void setTeachResourceService(TeachResourceService teachResourceService) {
        this.teachResourceService = teachResourceService;
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
    public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
        this.baseInfoSearchHelper = baseInfoSearchHelper;
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
}
