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
 * chaostone             2006-11-7            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.exam;

import java.text.SimpleDateFormat;
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

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.basecode.industry.ExamMode;
import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitComparator;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.exam.ExamActivity;
import com.shufe.model.course.arrange.exam.ExamParams;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.exam.ExamTurn;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamArrangeService;
import com.shufe.service.course.arrange.exam.ExamTime;
import com.shufe.service.course.arrange.exam.ExamTurnService;
import com.shufe.service.course.arrange.exam.comparator.ExamClassroomComparator;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.service.course.task.TaskPerClassPropertyExtractor;
import com.shufe.service.course.task.TeachTaskPropertyExtractor;
import com.shufe.service.system.baseinfo.ClassroomService;
import com.shufe.service.system.baseinfo.SpecialityService;
import com.shufe.util.DataRealmLimit;
import com.shufe.util.RequestUtil;

/**
 * 排考响应类
 * 
 * @author chaostone
 */
public class ExamArrangeAction extends ExamAction {
    
    protected ExamArrangeService examArrangeService;
    
    protected ClassroomService classroomService;
    
    protected TeachResourceService teachResourceService;
    
    protected ExamTurnService examTurnService;
    
    protected SpecialityService specialityService;
    
    public void setSpecialityService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
    
    /**
     * 安排考试主界面
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
        return super.index(mapping, form, request, response);
    }
    
    /**
     * 已经安排的教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward arrangeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "examType.id");
        Collection teachTasks = getTasks(request, Boolean.TRUE, null);
        addCollection(request, "tasks", teachTasks);
        request.setAttribute("examTakeCountOfTasks", getExamTakeCountOfTasks(request, teachTasks));
        request.setAttribute("examType", new ExamType(examTypeId));
        return forward(request);
    }
    
    /**
     * 没有进行安排的教学任务
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
        Collection teachTasks = getTasks(request, Boolean.FALSE, null);
        addCollection(request, "tasks", teachTasks);
        request.setAttribute("examTakeCountOfTasks", getExamTakeCountOfTasks(request, teachTasks));
        return forward(request);
        
    }
    
    /**
     * 得到教学任务对应的考试人数(对于补缓考使用)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public Map getExamTakeCountOfTasks(HttpServletRequest request, Collection teachTasks) {
        Long examTypeId = getLong(request, "examType.id");
        Map examTakeCountMap = new HashMap();
        for (Iterator iterator = teachTasks.iterator(); iterator.hasNext();) {
            TeachTask task = (TeachTask) iterator.next();
            EntityQuery query = new EntityQuery(ExamTake.class, "examTake");
            if (ExamType.FINAL.equals(examTypeId)) {
                query.add(new Condition("examTake.examType.id in (:examTypeId)", new Object[] { ExamType.FINAL }));
            } else if (ExamType.AGAIN.equals(examTypeId)) {
                query.add(new Condition("examTake.examType.id in (:examTypeId)", new Object[] { ExamType.AGAIN }));
            } else if (ExamType.DELAY.equals(examTypeId)) {
                query.add(new Condition("examTake.examType.id in (:examTypeId)", new Object[] { ExamType.DELAY }));
            } else {
                query.add(new Condition("examTake.examType.id in (:examTypeId)", new Object[] { ExamType.DELAY_AGAIN, ExamType.AGAIN, ExamType.DELAY }));
            }
            query.add(new Condition(" examTake.task.id=:taskId", task.getId()));
            query.setSelect("count(*)");
            List rs = (List) utilService.search(query);
            examTakeCountMap.put(task.getId().toString(), rs.get(0));
        }
        return examTakeCountMap;
        
    }
    
    /**
     * 排考参数设置
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward arrangeSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List rooms = classroomService.getClassrooms(getDepartmentIdSeq(request));
        // 过滤掉不能使用的教室
        for (Iterator iterator = rooms.iterator(); iterator.hasNext();) {
            Classroom room = (Classroom) iterator.next();
            if (null == room.getCapacityOfExam() || room.getCapacityOfExam().intValue() == 0)
                iterator.remove();
        }
        Collections.sort(rooms, new ExamClassroomComparator());
        addCollection(request, "rooms", rooms);
        ExamParams examParams = new ExamParams();
        Long examTypeId = getLong(request, "examParams.examType.id");
        examParams.setExamType((ExamType) baseCodeService.getCode(ExamType.class, examTypeId));
        request.setAttribute("params", examParams);
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        StudentType stdType = studentTypeService.getStudentType(stdTypeId);
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        request.setAttribute("delayAgain", Boolean.FALSE);
        if (StringUtils.contains(ExamType.delayAndAgainExamTypeIds, "," + examTypeId + ",")) {
            request.setAttribute("calendar", calendar.getNext());
            request.setAttribute("delayAgain", Boolean.TRUE);
        } else {
            request.setAttribute("calendar", calendar);
        }
        request.setAttribute("stdTypeList", Collections.singletonList(calendar.getStudentType()));
        
        addCollection(request, "examTurns", examTurnService.getExamTurns(stdType));
        addCollection(request, "weeks", WeekInfo.WEEKS);
        return forward(request);
    }
    
    /**
     * 排考安排
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
        ExamParams examParams = new ExamParams();
        RequestUtil.populate(request, examParams, "examParams");
        examParams.setFromCalendar(teachCalendarService.getTeachCalendar(getLong(request,
                "fromCalendar.id")));
        examParams.setToCalendar(teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.studentType.id"), get(request, "calendar.year"), get(request,
                "calendar.term")));
        String roomIds = get(request, "roomIds");
        if (!StringUtils.isBlank(roomIds)) {
            examParams.setRooms(utilService.load(Classroom.class, "id",
                    SeqStringUtil.transformToLong(roomIds)));
        }
        examParams.setLastWeek(examParams.getFromWeek() + getInteger(request, "weeks").intValue()
                - 1);
        
        // 组成每个星期对应几个考试场次的结构
        String weekAndTurnSeq = get(request, "weekAndTurns");
        String[] weekAndTurns = StringUtils.split(weekAndTurnSeq, ";");
        List examTimes = new ArrayList();
        for (int i = 0; i < weekAndTurns.length; i++) {
            String[] weekAndTurn = StringUtils.split(weekAndTurns[i], ",");
            ExamTurn turn = (ExamTurn) utilService.get(ExamTurn.class, Long.valueOf(weekAndTurn[2]));
            examTimes.add(new ExamTime(null, examParams.getFromWeek()
                    + NumberUtils.toInt(weekAndTurn[0]), WeekInfo.get(NumberUtils.toInt(weekAndTurn[1])), turn));
        }
        examParams.setExamTimes(examTimes);
        examParams.sortExamTimes();
        List tasks = utilService.load(TeachTask.class, "id",
                SeqStringUtil.transformToLong(request.getParameter("taskIds")));
        Long examModeId = getLong(request, "task.examMode.id");
        if (null != examModeId) {
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask teachTask = (TeachTask) iter.next();
                ExamMode examMode = (ExamMode) baseCodeService.getCode(ExamMode.class, examModeId);
                teachTask.setExamMode(examMode);
                utilService.saveOrUpdate(teachTask);
            }
        }
        response.setContentType("text/html; charset=utf-8");
        ExamArrangeWebObserver observer = new ExamArrangeWebObserver();
        observer.setLocale(getLocale(request));
        observer.setResourses(getResources(request));
        observer.setWriter(response.getWriter());
        examArrangeService.arrange(tasks, examParams, observer);
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }
    
    /**
     * 删除排考结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeArrange(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter("taskIds");
        
        Long examTypeId = getLong(request, "examType.id");
        if (StringUtils.isEmpty(taskIds) || null == examTypeId)
            return forwardError(mapping, request, "error.parameters.needed");
        List tasks = utilService.load(TeachTask.class, "id", SeqStringUtil.transformToLong(taskIds));
        examArrangeService.removeExamArranges(tasks, new ExamType(examTypeId));
        
        return redirect(request, "arrangeList", "info.delete.success");
    }
    
    /**
     * 考试安排报表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter("taskIds");
        List tasks = null;
        if (StringUtils.isEmpty(taskIds)) {
            EntityQuery query = buildExamTaskQuery(request, Boolean.TRUE, null);
            query.setLimit(null);
            tasks = (List) utilService.search(query);
        } else {
            tasks = teachTaskService.getTeachTasksByIds(taskIds);
        }
        boolean forRoom = false; // 是否为了分配教室
        if (request.getParameter("forRoom") != null)
            forRoom = true;
        List timeUnits = new ArrayList();
        Long examTypeId = getLong(request, "examType.id");
        ExamType examType = (ExamType) utilService.load(ExamType.class, examTypeId);
        Map activityTakeMap = new HashMap();
        EntityQuery query = new EntityQuery(ExamTake.class, "take");
        Condition activityCondition = new Condition("take.activity.id=:activityId");
        query.add(activityCondition);
        query.setSelect("count(*)");
        for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
            TeachTask task = (TeachTask) iterator.next();
            Collection examActivities = task.getArrangeInfo().getExamActivities(examType);
            for (Iterator iterator2 = examActivities.iterator(); iterator2.hasNext();) {
                ExamActivity examActivity = (ExamActivity) iterator2.next();
                if (null == examActivity.getRoom() || !forRoom) {
                    activityCondition.setValues(Collections.singletonList(examActivity.getId()));
                    List rs = (List) utilService.search(query);
                    activityTakeMap.put(examActivity.getId().toString(), rs.get(0));
                }
                if (!timeUnits.contains(examActivity.getTime())) {
                    timeUnits.add(examActivity.getTime());
                }
            }
        }
        Collections.sort(timeUnits, new TimeUnitComparator());
        addCollection(request, "timeUnits", timeUnits);
        Collections.sort(tasks, new PropertyComparator("course.code"));
        addCollection(request, "tasks", tasks);
        request.setAttribute("examType", examType);
        addCollection(request, "weeks", WeekInfo.WEEKS);
        request.setAttribute("activityTakeMap", activityTakeMap);
        if (forRoom) {
            return forward(request, "examReportForRoom");
        } else {
            return forward(request);
        }
    }
    
    /**
     * 显示空闲教室界面<br>
     * 为没有分配教室的考务活动分配教室
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward allocateRoomSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeUnit time = (TimeUnit) populate(request, TimeUnit.class, "timeUnit");
        PageLimit limit = getPageLimit(request);
        Classroom room = (Classroom) populate(request, Classroom.class, "classroom");
        Pagination rooms = teachResourceService.getFreeRoomsOf(
                SeqStringUtil.transformToLong(getDepartmentIdSeq(request)),
                new TimeUnit[] { time }, room, ExamActivity.class, limit.getPageNo(),
                limit.getPageSize());
        addOldPage(request, "rooms", rooms);
        addCollection(request, "configTypeList", baseCodeService.getCodes(ClassroomType.class));
        return forward(request);
    }
    
    /**
     * 期末考试日程安排报表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward examActivityReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "examActivity.calendar.id"));
        if (null == calendar) {
            return forward(mapping, request, "error.parameters.needed", "error");
        }
        addSingleParameter(request, "calendar", calendar);
        
        // 得到安排结果
        EntityQuery query1 = new EntityQuery(ExamActivity.class, "examActivity");
        populateConditions(request, query1);
        query1.add(new Condition("examActivity.examType.id = :examTypeId", ExamType.FINAL));
        query1.join("examActivity.task.teachClass.adminClasses", "adminClass");
        query1.add(new Condition("adminClass.department.id is not null"));
        Collection results1 = utilService.search(query1);
        addCollection(request, "result1", results1);
        
        Set adminClassSet = new HashSet();
        for (Iterator it = results1.iterator(); it.hasNext();) {
            ExamActivity activity = (ExamActivity) it.next();
            adminClassSet.addAll(activity.getTask().getTeachClass().getAdminClasses());
        }
        EntityQuery query2 = new EntityQuery(AdminClass.class, "adminClass");
        populateConditions(request, query2);
        query2.add(new Condition("adminClass in (:adminClasses)", adminClassSet));
        query2.join("adminClass.students", "student");
        String groupBy = "adminClass.department.id,adminClass.speciality.id";
        query2.groupBy(groupBy);
        query2.addOrder(OrderUtils.parser(groupBy));
        query2.setSelect("select " + groupBy + ",count(*)");
        Collection result2 = utilService.search(query2);
        
        Map departMap = new HashMap();
        Map majorMap = new HashMap();
        for (Iterator it = result2.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            if (!departMap.containsKey(obj[0])) {
                departMap.put(obj[0], departmentService.getDepartment((Long) obj[0]));
            }
            obj[0] = departMap.get(obj[0]);
            if (!majorMap.containsKey(obj[1])) {
                majorMap.put(obj[1], specialityService.getSpeciality((Long) obj[1]));
            }
            obj[1] = majorMap.get(obj[1]);
        }
        addCollection(request, "result2", result2);
        
        // 时间段
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map timeMap = new HashMap();
        for (Iterator it1 = results1.iterator(); it1.hasNext();) {
            ExamActivity activity = (ExamActivity) it1.next();
            timeMap.put(
                    sdf.format(activity.getStartDay()) + "-" + sdf.format(activity.getEndDay()),
                    activity);
        }
        List times = new ArrayList(timeMap.keySet());
        Collections.sort(times);
        addSingleParameter(request, "times", times);
        addSingleParameter(request, "timeMap", timeMap);
        addSingleParameter(request, "weekValues", WeekInfo.WEEKS);
        return forward(request);
    }
    
    /**
     * 显示空闲教室界面<br>
     * 为没有分配教室的考务活动分配教室
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward allocateRoom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String noRoomTaskIds = request.getParameter("noRoomTaskIds");
        // 取出教学任务，并且按照课程代码进行排序
        List tasks = teachTaskService.getTeachTasksByIds(noRoomTaskIds);
        Collections.sort(tasks, new PropertyComparator("course.code"));
        
        String classIds = request.getParameter("classroomIds");
        List classrooms = utilService.load(Classroom.class, "id",
                SeqStringUtil.transformToLong(classIds));
        
        Long examTypeId = getLong(request, "examType.id");
        ExamType examType = (ExamType) utilService.load(ExamType.class, examTypeId);
        // 默认是合并教学任务，不分校区
        Boolean canCombineTask = getBoolean(request, "canCombineTask");
        if (null == canCombineTask) {
            canCombineTask = Boolean.TRUE;
        }
        Boolean sameDistrictWithCourse = getBoolean(request, "sameDistrictWithCourse");
        if (null == sameDistrictWithCourse) {
            sameDistrictWithCourse = Boolean.FALSE;
        }
        boolean success = examArrangeService.allocateRooms(tasks, examType, classrooms,
                canCombineTask, sameDistrictWithCourse);
        String msg = "arrange.exam.allocRoomFailure";
        if (success) {
            msg = "info.action.success";
            utilService.saveOrUpdate(tasks);
        }
        saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { msg }));
        return forward(request, new Action("examArrange", "examReport", "&forRoom=1"));
    }
    
    /**
     * 考试座位表<br>
     * 以教学任务为参数或者以排考活动为参数
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward seatReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter("taskIds");
        Long examTypeId = getLong(request, "examType.id");
        ExamType type = (ExamType) utilService.load(ExamType.class, examTypeId);
        if (StringUtils.isNotEmpty(taskIds)) {
            List activities = new ArrayList();
            List tasks = utilService.load(TeachTask.class, "id",
                    SeqStringUtil.transformToLong(taskIds));
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                activities.addAll(task.getArrangeInfo().getExamActivities(type));
            }
            addCollection(request, "activities", activities);
        } else {
            EntityQuery entityQuery = new EntityQuery(ExamActivity.class, "activity");
            String examActivityIds = request.getParameter("examActivityIds");
            entityQuery.add(new Condition("activity.id in (:examActivityIds)", SeqStringUtil.transformToLong(examActivityIds)));
            entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
            addCollection(request, "activities", utilService.search(entityQuery));
        }
        return forward(request);
    }
    
    /**
     * 补缓考考试座位表<br>
     * 以教学任务为参数或者以排考活动为参数
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward seatReportByClassroom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter("taskIds");
        Long examTypeId = getLong(request, "examType.id");
        Long calendarId = getLong(request, "calendar.id");
        ExamType type = (ExamType) utilService.load(ExamType.class, examTypeId);
        List activities = new ArrayList();
        if (StringUtils.isNotEmpty(taskIds)) {
            List tasks = utilService.load(TeachTask.class, "id",
                    SeqStringUtil.transformToLong(taskIds));
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                activities.addAll(task.getArrangeInfo().getExamActivities(type));
            }
        } else {
            String examActivityIds = request.getParameter("examActivityIds");
            String examActivityId[] = StringUtils.split(examActivityIds, ",");
            for (int i = 0; i < examActivityId.length; i++) {
                String examActivity[] = StringUtils.split(examActivityId[i], "@");
                String jsid = examActivity[0];
                String year = examActivity[1];
                String validWeeksNum = examActivity[2];
                String weekId = examActivity[3];
                String startUnit = examActivity[4];
                String endUnit = examActivity[5];
                Map activitieMap = new HashMap();
                Set stds = new HashSet();
                List examActivities = utilService.searchHQLQuery("from ExamActivity activity where activity.calendar.id="
                        + calendarId
                        + " and activity.examType.id="
                        + examTypeId
                        + " and activity.room.id="
                        + jsid
                        + " and activity.time.year="
                        + year
                        + " and activity.time.validWeeksNum="
                        + validWeeksNum
                        + " and activity.time.weekId="
                        + weekId
                        + " and activity.time.startUnit="
                        + startUnit + " and activity.time.endUnit=" + endUnit);
                
                for (Iterator iter = examActivities.iterator(); iter.hasNext();) {
                    ExamActivity exam = (ExamActivity) iter.next();
                    for (Iterator iterator = exam.getExamTakes().iterator(); iterator.hasNext();) {
                        ExamTake element = (ExamTake) iterator.next();
                        stds.add(element);
                    }
                    
                }
                activitieMap.put("room", ((ExamActivity) examActivities.get(0)).getRoom());
                activitieMap.put("time", ((ExamActivity) examActivities.get(0)).getTime());
                activitieMap.put("student", stds);
                activities.add(activitieMap);
            }
        }
        addCollection(request, "activities", activities);
        return forward(request);
    }
    
    /**
     * 排考教室合并设定
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward mergeSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter("taskIds");
        List tasks = utilService.load(TeachTask.class, "id", SeqStringUtil.transformToLong(taskIds));
        addCollection(request, "taskList", tasks);
        
        ExamType examType = (ExamType) utilService.get(ExamType.class, getLong(request,
                "examType.id"));
        request.setAttribute("examType", examType);
        // 判断是否在统一时间考试
        java.util.Date examDate = null;
        String examTimeZone = null;
        boolean sameTime = true;
        for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
            TeachTask task = (TeachTask) iterator.next();
            Collection examActivities = task.getArrangeInfo().getExamActivities(examType);
            for (Iterator iterator2 = examActivities.iterator(); iterator2.hasNext();) {
                ExamActivity activity = (ExamActivity) iterator2.next();
                if (null == examDate) {
                    examDate = activity.getTime().getFirstDay();
                } else {
                    if (!examDate.equals(activity.getTime().getFirstDay())) {
                        sameTime = false;
                        break;
                    }
                }
                if (null == examTimeZone) {
                    examTimeZone = activity.getTime().getTimeZone();
                } else {
                    if (!examTimeZone.equals(activity.getTime().getTimeZone())) {
                        sameTime = false;
                        break;
                    }
                }
            }
        }
        request.setAttribute("sameTime", Boolean.valueOf(sameTime));
        return forward(request);
    }
    
    /**
     * 排考教室合并
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward mergeRoom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TeachTask reserved = teachTaskService.getTeachTask(getLong(request, "reservedId"));
        List tasks = utilService.load(TeachTask.class, "id",
                SeqStringUtil.transformToLong(request.getParameter("taskIds")));
        tasks.remove(reserved);
        ExamType examType = (ExamType) utilService.get(ExamType.class, getLong(request,
                "examType.id"));
        
        // 整理剩余容量
        List reminderList = new ArrayList();
        Collection activities = reserved.getArrangeInfo().getExamActivities(examType);
        for (Iterator iterator = activities.iterator(); iterator.hasNext();) {
            ExamActivity activity = (ExamActivity) iterator.next();
            if (null != activity.getRoom()) {
                int reminder = activity.getRoom().getCapacityOfExam().intValue()
                        - activity.getExamTakes().size();
                if (reminder > 0) {
                    reminderList.add(new ReminderRoom(activity.getRoom(), reminder));
                }
            }
        }
        
        // 尝试分配
        List needList = new ArrayList();
        for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
            TeachTask one = (TeachTask) iterator.next();
            if (!one.getId().equals(reserved.getId())) {
                Collection myActivities = one.getArrangeInfo().getExamActivities(examType);
                for (Iterator actvityIter = myActivities.iterator(); actvityIter.hasNext();) {
                    ExamActivity activity = (ExamActivity) actvityIter.next();
                    int needed = activity.getExamTakes().size();
                    if (needed > 0) {
                        needList.add(new NeedActvity(activity, needed));
                    }
                }
            }
        }
        Collections.sort(reminderList);
        Collections.sort(needList);
        int reminderIndex = 0;
        boolean success = true;
        for (Iterator iterator = needList.iterator(); iterator.hasNext();) {
            NeedActvity needActivity = (NeedActvity) iterator.next();
            needActivity.activity.setRoom(null);
            // 找到合适的
            while (reminderIndex < reminderList.size()) {
                ReminderRoom reminderRoom = (ReminderRoom) reminderList.get(reminderIndex);
                reminderIndex++;
                if (reminderRoom.reminder >= needActivity.needed) {
                    needActivity.activity.setRoom(reminderRoom.room);
                    break;
                }
            }
            if (null == needActivity.activity.getRoom()) {
                success = false;
                break;
            }
        }
        if (success) {
            utilService.saveOrUpdate(tasks);
            return redirect(request, "arrangeList", "info.action.success");
        } else {
            return redirect(request, "arrangeList", "info.action.failure");
        }
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        String taskIdSeq = request.getParameter("taskIds");
        Long[] taskIds = SeqStringUtil.transformToLong(taskIdSeq);
        Map<Long, Integer> taskIdMap = new HashMap<Long, Integer>();
        for (int i = 0; i < taskIds.length; i++) {
            taskIdMap.put(taskIds[i], i);
        }
        List tasks = utilService.load(TeachTask.class, "id", taskIds);
        Boolean byClass = getBoolean(request, "byClass");
        if (Boolean.TRUE.equals(byClass)) {
            Map courseAndClassMap = new HashMap();
            TeachTask[] tempTasks = new TeachTask[taskIdSeq.length()];
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                Set adminClassSet = task.getTeachClass().getAdminClasses();
                for (Iterator iterator = adminClassSet.iterator(); iterator.hasNext();) {
                    AdminClass adminClass = (AdminClass) iterator.next();
                    if (null == courseAndClassMap.get(adminClass.getId() + ","
                            + task.getCourse().getId())) {
                        courseAndClassMap.put(adminClass.getId() + "," + task.getCourse().getId(),
                                Boolean.TRUE);
                        tempTasks[taskIdMap.get(task.getId())] = task;
                    } else {
                        break;
                    }
                }
            }
            List resultTasks = new ArrayList();
            for (int i = 0; i < tempTasks.length; i++) {
                resultTasks.add(i, tempTasks[i]);
            }
            return resultTasks;
        } else {
            TeachTask[] tempTasks = new TeachTask[taskIds.length];
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                tempTasks[taskIdMap.get(task.getId())] = task;
            }
            List resultTasks = new ArrayList();
            for (int i = 0; i < tempTasks.length; i++) {
                resultTasks.add(i, tempTasks[i]);
            }
            return resultTasks;
        }
    }
    
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        Boolean byClass = getBoolean(request, "byClass");
        TeachTaskPropertyExtractor exporter = new TeachTaskPropertyExtractor(getLocale(request), getResources(request), utilService);
        if (Boolean.TRUE.equals(byClass)) {
            exporter = new TaskPerClassPropertyExtractor(getLocale(request), getResources(request), utilService);
        }
        Long examTypeId = getLong(request, "examType.id");
        exporter.setExamType(new ExamType(examTypeId));
        String activityFormat = request.getParameter("activityFormat");
        exporter.setExamActivityFormat(activityFormat);
        return exporter;
    }
    
    /**
     * 修改排考结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "task.id");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        Long examTypeId = getLong(request, "examType.id");
        ExamType examType = (ExamType) utilService.load(ExamType.class, examTypeId);
        request.setAttribute("examType", examType);
        request.setAttribute("task", task);
        Collection examActivites = task.getArrangeInfo().getExamActivities(examType);
        addCollection(request, "examActivites", examActivites);
        // 查找没有参加考试的名单
        List noExamCourseTakes = new ArrayList();
        if (ExamType.delayAndAgainExamTypeIds.indexOf("," + examTypeId + ",") == -1) {
            for (Iterator iter = task.getTeachClass().getCourseTakes().iterator(); iter.hasNext();) {
                CourseTake courseTake = (CourseTake) iter.next();
                if (null == courseTake.getExamTake(examType)) {
                    noExamCourseTakes.add(courseTake);
                }
            }
        } else {
            for (Iterator iter = task.getTeachClass().getCourseTakes().iterator(); iter.hasNext();) {
                CourseTake courseTake = (CourseTake) iter.next();
                if (null != courseTake.getExamTake(examType)
                        && courseTake.getExamTake(examType).getActivity() == null) {
                    noExamCourseTakes.add(courseTake);
                }
            }
        }
        
        addCollection(request, "noExamCourseTakes", noExamCourseTakes);
        // 如果有这样的学生,则默认查找有空的排考活动
        if (!noExamCourseTakes.isEmpty()) {
            List unsaturatedActivites = new ArrayList();
            for (Iterator iter = examActivites.iterator(); iter.hasNext();) {
                ExamActivity examActivity = (ExamActivity) iter.next();
                if (null != examActivity.getRoom()
                        && examActivity.getExamTakes().size() < examActivity.getRoom().getCapacityOfExam().intValue()) {
                    unsaturatedActivites.add(examActivity);
                }
            }
            addCollection(request, "unsaturatedActivites", unsaturatedActivites);
        }
        return forward(request);
    }
    
    /**
     * 保存教室之间的调整.<br>
     * 教室调整为0的活动将遭到删除.<br>
     * 保存为为参加考试的学生指定的排考活动功能.noTakedStdCount
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
        Long taskId = getLong(request, "task.id");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        Long examTypeId = getLong(request, "examType.id");
        Collection activities = task.getArrangeInfo().getExamActivities(new ExamType(examTypeId));
        
        // 调整中,需要调出的学生
        List remainders = new ArrayList();
        // 原来人数少,需要增加的教学活动
        List lessActivities = new ArrayList();
        
        // 对每个排考活动，考虑是否有不够人数和超过人数的.
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            Integer size = getInteger(request, "size" + activity.getId());
            if (null == size)
                continue;
            if (size.intValue() > activity.getExamTakes().size()) {
                lessActivities.add(activity);
            } else if (size.intValue() < activity.getExamTakes().size()) {
                int extra = activity.getExamTakes().size() - size.intValue();
                Iterator extraIter = activity.getExamTakes().iterator();
                while (extra > 0) {
                    ExamTake take = (ExamTake) extraIter.next();
                    remainders.add(take);
                    extra--;
                }
                activity.getExamTakes().removeAll(remainders);
            }
        }
        
        Iterator remainderIter = remainders.iterator();
        for (Iterator iter = lessActivities.iterator(); iter.hasNext();) {
            ExamActivity activity = (ExamActivity) iter.next();
            Integer size = getInteger(request, "size" + activity.getId());
            int need = size.intValue() - activity.getExamTakes().size();
            while (need > 0 && remainders.size()>0) {
                activity.addExamTake((ExamTake) remainderIter.next());
                need--;
            }
        }
        
        // 没有分配进考场的学生
        Integer noTakedStdCount = getInteger(request, "noTakedStdCount");
        
        for (int i = 0; i < ((null == noTakedStdCount) ? 0 : noTakedStdCount.intValue()); i++) {
            Long courseTakeId = getLong(request, "courseTakeId" + i);
            CourseTake courseTake = (CourseTake) utilService.get(CourseTake.class, courseTakeId);
            ExamActivity examActivity = (ExamActivity) utilService.get(ExamActivity.class, getLong(
                    request, "examActivityId" + i));
            
            if (null == examActivity)
                continue;
            examActivity.addExamTake(new ExamTake(courseTake, examActivity));
        }
        
        utilService.saveOrUpdate(activities);
        List removedActivites = new ArrayList();
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            ExamActivity element = (ExamActivity) iter.next();
            if (element.getExamTakes().size() == 0) {
                removedActivites.add(element);
            }
        }
        task.getArrangeInfo().getExamActivities().removeAll(removedActivites);
        utilService.remove(removedActivites);
        return redirect(request, "arrangeList", "info.save.success");
    }
    
    /**
     * 查询同一安排的其他可用空闲教室
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward freeRoomList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examActivityId = getLong(request, "examActivity.id");
        ExamActivity examActivity = (ExamActivity) utilService.get(ExamActivity.class,
                examActivityId);
        Classroom room = (Classroom) populate(request, Classroom.class, "classroom");
        PageLimit limit = getPageLimit(request);
        
        Pagination rooms = teachResourceService.getFreeRoomsOf(
                SeqStringUtil.transformToLong(getDepartmentIdSeq(request)),
                new TimeUnit[] { examActivity.getTime() }, room, ExamActivity.class,
                limit.getPageNo(), limit.getPageSize());
        addOldPage(request, "rooms", rooms);
        request.setAttribute("examActivity", examActivity);
        addCollection(request, "configTypeList", baseCodeService.getCodes(ClassroomType.class));
        return forward(request);
    }
    
    /**
     * 教室调整中增加空闲教室
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addRoom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examActivityId = getLong(request, "examActivity.id");
        ExamActivity examActivity = (ExamActivity) utilService.get(ExamActivity.class,
                examActivityId);
        
        List newActivities = new ArrayList();
        Long[] roomIds = SeqStringUtil.transformToLong(request.getParameter("roomIds"));
        for (int i = 0; i < roomIds.length; i++) {
            ExamActivity newActivity = (ExamActivity) examActivity.clone();
            newActivity.setRoom((Classroom) utilService.get(Classroom.class, roomIds[i]));
            newActivities.add(newActivity);
        }
        utilService.saveOrUpdate(newActivities);
        return forward(request, new Action(ExamArrangeAction.class, "edit"));
    }
    
    /**
     * 打印试卷带标签
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printPaperLabelForTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskIds = request.getParameter("taskIds");
        List tasks = utilService.load(TeachTask.class, "id", SeqStringUtil.transformToLong(taskIds));
        Map activityMap = new HashMap();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            activityMap.put(task.getId().toString(), task.getLastCourseActivity());
        }
        request.setAttribute("activityMap", activityMap);
        addCollection(request, "tasks", tasks);
        // 每个考场多加的试卷份数
        Integer extraCount = getInteger(request, "extraCount");
        if (null == extraCount)
            extraCount = new Integer(0);
        request.setAttribute("extraCount", extraCount);
        // 每页的标签数目
        request.setAttribute("labelCount", getInteger(request, "labelCount"));
        return forward(request);
    }
    
    /**
     * 参考学生名单以及考试情况
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "taskId");
        TeachTask task = (TeachTask) utilService.get(TeachTask.class, taskId);
        addEntity(request, task);
        Long examTypeId = getLong(request, "examType.id");
        addEntity(request, new ExamType(examTypeId));
        return forward(request);
    }
    
    /**
     * 统计教室利用率
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward roomUtilization(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long examTypeId = getLong(request, "examType.id");
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        ExamType examType = (ExamType) utilService.get(ExamType.class, examTypeId);
        Float ratio = getFloat(request, "ratio");
        DataRealmLimit limit = getDataRealmLimit(request);
        addCollection(request, "utilizations", teachResourceService.getRoomUtilizationsOfExam(
                calendar, examType, limit, ratio));
        return forward(request);
    }
    
    /**
     * 开课院系权限限制范围
     * 
     * @param stdTypes
     * @param departs
     * @return
     */
    protected Condition getAuthorityCondition(Collection stdTypes, Collection departs) {
        return new Condition("task.teachClass.stdType in (:stdTypes) and task.arrangeInfo.teachDepart in(:teachDeparts)", stdTypes, departs);
    }
    
    public void setExamArrangeService(ExamArrangeService examArrangeService) {
        this.examArrangeService = examArrangeService;
    }
    
    public void setClassroomService(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }
    
    public void setTeachResourceService(TeachResourceService teachResourceService) {
        this.teachResourceService = teachResourceService;
    }
    
    public void setExamTurnService(ExamTurnService examTurnService) {
        this.examTurnService = examTurnService;
    }
    
}

class ReminderRoom implements Comparable {
    
    Classroom room;
    
    int reminder;
    
    public ReminderRoom(Classroom room, int reminder) {
        super();
        this.room = room;
        this.reminder = reminder;
    }
    
    public int compareTo(Object arg0) {
        return this.reminder - ((ReminderRoom) arg0).reminder;
    }
}

class NeedActvity implements Comparable {
    
    ExamActivity activity;
    
    int needed;
    
    public NeedActvity(ExamActivity activity, int needed) {
        super();
        this.activity = activity;
        this.needed = needed;
    }
    
    public int compareTo(Object arg0) {
        return this.needed - ((NeedActvity) arg0).needed;
    }
}
