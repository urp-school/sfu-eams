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
 * chaostone            2006-05-21          Created
 * zq                   2007-09-18          在saveActivities()方法中，替换掉两个info，
 *                                          添加了两个logHelp.info(...)方法；
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.CourseArrangeAlteration;
import com.shufe.model.course.arrange.task.TaskInDepartment;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.AvailTimeService;
import com.shufe.service.course.arrange.TaskActivityService;
import com.shufe.service.course.arrange.resource.TeachResourceService;
import com.shufe.service.course.arrange.task.CourseActivityDigestor;
import com.shufe.service.course.arrange.task.CourseActivityService;
import com.shufe.service.course.arrange.task.CourseTakeService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.baseinfo.ClassroomService;
import com.shufe.service.system.baseinfo.importer.CourseImportListener;
import com.shufe.service.util.ImporterCodeGenListener;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.TeachTaskSearchHelper;

public class ManualArrangeAction extends CalendarRestrictionSupportAction {

  protected CourseActivityService courseActivityService;

  protected TeachTaskService teachTaskService;

  protected AvailTimeService availTimeService;

  protected TeachResourceService teachResourceService;

  protected TaskActivityService taskActivityService;

  protected CourseTakeService courseTakeService;

  protected ClassroomService classroomService;

  protected TeachTaskSearchHelper teachTaskSearchHelper;

  /**
   * 手动排课主界面
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
    setCalendarDataRealm(request, hasStdTypeCollege);
    return arrangeHome(mapping, form, request, response);
  }

  /**
   * 查看预排和实排的情况
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward willAndBeenArrange(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long calendarId = getLong(request, "task.calendar.id");
    EntityQuery query = new EntityQuery(TeachTask.class, "task");
    query.add(new Condition("task.calendar.id = (:calendarId)", calendarId));
    query.add(new Condition("task.arrangeInfo.isArrangeComplete = true"));
    Collection activities = utilService.search(query);
    Collection tasks = new ArrayList();
    for (Iterator it = activities.iterator(); it.hasNext();) {
      TeachTask task = (TeachTask) it.next();
      double overallUnits = task.getArrangeInfo().getWeeks().floatValue()
          * task.getArrangeInfo().getWeekUnits().floatValue();
      if (overallUnits != task.getActivityWeeks() * task.getActivityWeekUnits()// 总课时
          || task.getArrangeInfo().getWeekStart().intValue() != task.getActivityStartWeek()
              - task.getCalendar().getWeekStart().intValue() + 1// 起始周
          || task.getArrangeInfo().getWeeks().intValue() != task.getActivityWeeks()// 周数
          || task.getArrangeInfo().getWeekUnits().floatValue() != task.getActivityWeekUnits()) {
        tasks.add(task);
      }
    }
    addCollection(request, "tasks", tasks);
    return forward(request);
  }

  /**
   * 列举手动排课中全部教学任务
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
    EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
    addTaskInQuery(request, query);
    Collection tasks = utilService.search(query);
    addCollection(request, "tasks", tasks);

    Map arrangeInfo = new HashMap();
    CourseActivityDigestor.setDelimeter("<br>");
    for (Iterator iter = tasks.iterator(); iter.hasNext();) {
      TeachTask oneTask = (TeachTask) iter.next();
      arrangeInfo.put(oneTask.getId().toString(),
          CourseActivityDigestor.digest(oneTask, getResources(request), getLocale(request)));
    }

    addSingleParameter(request, "arrangeInfo", arrangeInfo);
    getTaskInDepartment(request, tasks);
    return forward(request);
  }

  /**
   * 获得教学任务归属的院系（仅华政使用）
   * 
   * @param request
   * @param tasks
   */
  protected void getTaskInDepartment(HttpServletRequest request, Collection tasks) {
    EntityQuery query = new EntityQuery(TaskInDepartment.class, "taskIn");
    query.join("taskIn.tasks", "task");
    Collection teachTasks = (tasks instanceof SinglePage) ? ((SinglePage) tasks).getPageDatas() : tasks;
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
   * 列举手动排课中（已安排和未安排的）教学任务
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward taskList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
    addTaskInQuery(request, query);
    Boolean isArrangeCompleted = getBoolean(request, "task.arrangeInfo.isArrangeComplete");
    if (null == isArrangeCompleted) {
      isArrangeCompleted = Boolean.FALSE;
    }
    query.add(new Condition("task.arrangeInfo.isArrangeComplete = :isComplete", isArrangeCompleted));
    Collection tasks = utilService.search(query);
    addCollection(request, "tasks", tasks);

    Map arrangeInfo = new HashMap();
    if (null != isArrangeCompleted && isArrangeCompleted.equals(Boolean.TRUE)) {
      CourseActivityDigestor.setDelimeter("<br>");
      for (Iterator iter = tasks.iterator(); iter.hasNext();) {
        TeachTask oneTask = (TeachTask) iter.next();
        arrangeInfo.put(oneTask.getId().toString(),
            CourseActivityDigestor.digest(oneTask, getResources(request), getLocale(request)));
      }
    }
    addSingleParameter(request, "arrangeInfo", arrangeInfo);
    getTaskInDepartment(request, tasks);
    return forward(request);
  }

  /**
   * 华政：将排课结果同步于教学任务
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward updateTeachTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long[] taskIds = SeqStringUtil.transformToLong(get(request, "taskIds"));
    if (null == taskIds || taskIds.length == 0) { return forward(mapping, request,
        "error.teachTask.id.needed", "error"); }
    Collection tasks = utilService.load(TeachTask.class, "id", taskIds);
    for (Iterator it = tasks.iterator(); it.hasNext();) {
      TeachTask task = (TeachTask) it.next();
      // 起始周
      task.getArrangeInfo().setWeekStart(
          new Integer(task.getActivityStartWeek() - task.getCalendar().getWeekStart().intValue() + 1));
      // 周课时
      task.getArrangeInfo().setWeekUnits(new Float(task.getActivityWeekUnits()));
      // 周数
      task.getArrangeInfo().setWeeks(new Integer(task.getActivityWeeks()));
      task.getArrangeInfo().calcOverallUnits();
    }
    utilService.saveOrUpdate(tasks);
    return redirect(request, "willAndBeenArrange", "info.action.success");
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

  /**
   * 安排课程主界面
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @param moduleName
   * @return
   * @throws Exception
   */
  protected ActionForward arrangeHome(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    setCalendarDataRealm(request, hasStdType);
    // 获得开课院系和上课教师列表
    String stdTypeDataRealm = getStdTypeIdSeq(request);
    String departDataRealm = getDepartmentIdSeq(request);
    List departList = teachTaskService.getDepartsOfTask(stdTypeDataRealm, departDataRealm,
        (TeachCalendar) request.getAttribute(Constants.CALENDAR));
    addCollection(
        request,
        "courseTypes",
        teachTaskService.getCourseTypesOfTask(stdTypeDataRealm, departDataRealm,
            (TeachCalendar) request.getAttribute(Constants.CALENDAR)));
    addCollection(
        request,
        "teachDepartList",
        teachTaskService.getTeachDepartsOfTask(stdTypeDataRealm, departDataRealm,
            (TeachCalendar) request.getAttribute(Constants.CALENDAR)));
    addCollection(request, Constants.DEPARTMENT_LIST, departList);
    return forward(request);
  }

  /**
   * 保存手动排课设置的排课结果 <br>
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward saveActivities(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    /* 下面被注释的代码，是为了当教室发生变动时弹出提示修改人数上限的页面。 */
    /* 请暂不要删除 */
    String taskId = get(request, "taskId");
    if (StringUtils.isEmpty(taskId)) { return forward(mapping, request, "error.teachTask.id.needed", "error"); }
    Integer count = getInteger(request, "activityCount");
    TeachTask task = null;
    /* 请暂不要删除 */
    // Set rooms = new HashSet();
    String forward = get(request, "forward");
    if (count.intValue() == 0) {
      try {
        logHelper.info(request, "Delete all Activity of task with id:" + taskId);
        courseActivityService.removeActivities(new Long[] { Long.valueOf(taskId) });
      } catch (Exception e) {
        logHelper.info(request, "Failure in deleting all Activity of task with id:" + taskId, e);
        return forward(mapping, request, "error.occurred", "error");
      }
    } else {
      task = teachTaskService.getTeachTask(Long.valueOf(taskId));
      /* 请暂不要删除 */
      // for (Iterator it = task.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
      // CourseActivity activity = (CourseActivity) it.next();
      // rooms.add(activity.getRoom());
      // }
      String alterationBefore = "";
      String alterationAfter = "";

      int calendarWeekStartIndex = task.getCalendar().getWeekStart().intValue() - 1;
      Integer nextYear = Integer.valueOf(task.getCalendar().getYear().substring(5));
      List activityList = new ArrayList(count.intValue());
      for (int i = 0; i < count.intValue(); i++) {
        CourseActivity activity = (CourseActivity) populate(request, CourseActivity.class, "activity" + i);
        // 如果教学活动延伸到来年，则生成新的教学活动
        if (activity.getTime().getValidWeeks().indexOf("1") < calendarWeekStartIndex) {
          activity.getTime().setYear(nextYear);
        }
        activityList.add(activity);
      }
      // 按照同一学年，教师，教室，周几,起始小节排序,合并相近的小节
      List mergedActivityList = CourseActivity.mergeActivites(activityList);
      for (Iterator iter = mergedActivityList.iterator(); iter.hasNext();) {
        CourseActivity one = (CourseActivity) iter.next();
        one.setCalendar(task.getCalendar());
//        Classroom room = (Classroom) utilService.load(Classroom.class, one.getRoom().getId());
//        if (null != one.getRoom() && Boolean.TRUE.equals(room.getIsCheckActivity())
//            && teachResourceService.isRoomOccupied(one.getTime(), one.getRoom().getId(), task)) { 
//        	return forward(request, "activityCollision"); 
//        }
      }

      // 检测冲突
      Boolean detectCollision = getBoolean(request, "detectCollision");
      if (null == detectCollision) {
        detectCollision = Boolean.TRUE;
      }
      if (Boolean.TRUE.equals(detectCollision)) {
        Collection collisionTakes = courseTakeService.collisionTakes(task, mergedActivityList);
        if (!collisionTakes.isEmpty()) {
          addCollection(request, "courseTakes", collisionTakes);
          addCollection(request, "activities", mergedActivityList);
          addSingleParameter(request, "task", task);
          return forward(request, "collisionStdList");
        }
      }
      // 尝试保存
      try {
        logHelper.info(request,
            "Delete activities Before SAVE new activity and task seqNo:" + task.getSeqNo());
        alterationBefore = CourseActivityDigestor.digest(task.getCalendar(), task.getArrangeInfo(), null,
            null, CourseActivityDigestor.defaultFormat);
        Set hashSet = new HashSet();
        hashSet.addAll(task.getArrangeInfo().getActivities());
        task.getArrangeInfo().setActivities(new HashSet());
        for (Iterator iter = hashSet.iterator(); iter.hasNext();) {
          CourseActivity activity = (CourseActivity) iter.next();
          utilService.remove(activity);
        }
        utilService.saveOrUpdate(task);
        // for any activity has no teacher 2006-3-8
        for (Iterator iter = mergedActivityList.iterator(); iter.hasNext();) {
          CourseActivity activity = (CourseActivity) iter.next();
          if (null != activity.getRoom()) {
            activity.setRoom((Classroom) utilService.get(Classroom.class, activity.getRoom().getId()));
          }
          EntityUtils.evictEmptyProperty(activity);
          activity.getTime().initTime(task.getCalendar().getTimeSetting());
        }

        task.addActivities(mergedActivityList);
        task.getArrangeInfo().setIsArrangeComplete(Boolean.TRUE);

        teachTaskService.updateTeachTask(task);
        if (StringUtils.isNotEmpty(alterationBefore)) {
          // 因为下面需要缩略排课结果，但是新的排课结果中教师只有id，没有姓名所以refresh
          utilService.refresh(task);
          CourseArrangeAlteration alteration = new CourseArrangeAlteration();
          alteration.setTask(task);
          alteration.setAlterationBefore(alterationBefore);
          alterationAfter = CourseActivityDigestor.digest(task.getCalendar(), task.getArrangeInfo(), null,
              null, CourseActivityDigestor.defaultFormat);
          if (StringUtils.isEmpty(forward)) {
            alteration.setHappenPlace(new Integer(0));
          } else {
            alteration.setHappenPlace(new Integer(1));
          }
          if (!ObjectUtils.equals(alterationBefore, alterationAfter)) {
            alteration.setAlterationAfter(alterationAfter);
            alteration.setAlterBy(getUser(request.getSession()));
            alteration.setAlterFrom(RequestUtils.getIpAddr(request));
            alteration.setAlterationAt(new Date());
            utilService.saveOrUpdate(alteration);
          }
        }
      } catch (Exception e) {
        logHelper.info(request, "Failure in deleting activities of task with id:" + taskId, e);
        return forward(mapping, request, "error.occurred", "error");
      }
    }

    /* 请暂不要删除 */
    // Set savedRooms = new HashSet();
    // for (Iterator it = task.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
    // CourseActivity activity = (CourseActivity) it.next();
    // savedRooms.add(activity.getRoom());
    // }
    // Collection list1 = CollectionUtils.subtract(rooms, savedRooms);
    // Collection list2 = CollectionUtils.subtract(savedRooms, rooms);
    // boolean isNotSameRooms = CollectionUtils.isNotEmpty(list1)
    // || CollectionUtils.isNotEmpty(list2);
    /* ************* */

    if (null != task && Boolean.TRUE.equals(task.getElectInfo().getIsElectable())) { return redirect(request,
        "editTask", "info.save.success", "&taskId=" + task.getId() + "&params=" + get(request, "params")
            + "&forward=" + forward); }

    if (StringUtils.isEmpty(forward)) {
      String params = get(request, "params");
      boolean isTaskList = false;
      String[] paramsValue = StringUtils.split(params, "&");
      for (int i = 0; i < paramsValue.length; i++) {
        String[] values = StringUtils.split(paramsValue[i], "=");
        if (StringUtils.equals(values[0], "task.arrangeInfo.isArrangeComplete") && values.length > 1) {
          isTaskList = true;
          break;
        }
      }
      return redirect(request, isTaskList ? "taskList" : "search", "info.save.success");
    } else {
      saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { "info.save.success" }));
      return mapping.findForward(forward);
    }
  }

  public ActionForward editTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    request.setAttribute("task", utilService.load(TeachTask.class, getLong(request, "taskId")));
    return forward(request);
  }

  public ActionForward saveTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    TeachTask task = teachTaskService.getTeachTask(getLong(request, "taskId"));
    if (CollectionUtils.isEmpty(task.getArrangeInfo().getActivities())) { return redirect(request,
        "taskList", "info.save.success"); }
    Integer countType = getInteger(request, "countType");
    switch (countType.intValue()) {
    case 0:
      Integer minCourseCount = null;
      for (Iterator it = task.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
        CourseActivity activity = (CourseActivity) it.next();
        if (null == minCourseCount
            || minCourseCount.intValue() > activity.getRoom().getCapacityOfCourse().intValue()) {
          minCourseCount = activity.getRoom().getCapacityOfCourse();
        }
      }
      task.getElectInfo().setMaxStdCount(minCourseCount);
      break;
    case 1:
      Integer minCount = null;
      for (Iterator it = task.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
        CourseActivity activity = (CourseActivity) it.next();
        if (null == minCount || minCount.intValue() > activity.getRoom().getCapacity().intValue()) {
          minCount = activity.getRoom().getCapacity();
        }
      }
      task.getElectInfo().setMaxStdCount(minCount);
      break;
    case 2:
      task.getElectInfo().setMaxStdCount(getInteger(request, "maxStdCount"));
      break;
    }
    utilService.saveOrUpdate(task);
    String forward = get(request, "forward");
    if (StringUtils.isEmpty(forward)) {
      return redirect(request, "taskList", "info.save.success");
    } else {
      saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { "info.save.success" }));
      return mapping.findForward(forward);
    }
  }

  /**
   * 平移教学任务中的教学活动
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward shift(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Integer offset = getInteger(request, "offset");
    String taskIds = get(request, Constants.TEACHTASK_KEYSEQ);
    if (null != offset) {
      List tasks = teachTaskService.getTeachTasksByIds(taskIds);
      for (Iterator iter = tasks.iterator(); iter.hasNext();) {
        TeachTask task = (TeachTask) iter.next();
        taskActivityService.shift(task, offset.intValue());
      }
    }
    return redirect(request, "taskList", "info.save.success");
  }

  /**
   * 删除一个或多个教学任务的排课结果
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward removeArrangeResult(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    String taskIds = get(request, Constants.TEACHTASK_KEYSEQ);
    if (StringUtils.isEmpty(taskIds)) { return forward(request, new Action("", "taskList"),
        "error.teachTask.id.needed"); }
    // TODO
    courseActivityService.removeActivities(SeqStringUtil.transformToLong(taskIds));
    return redirect(request, (null == getBoolean(request, "task.arrangeInfo.isArrangeComplete")) ? "search"
        : "taskList", "info.action.success");
  }

  /**
   * 手工调课、排课
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward manualArrange(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // 获取要排课的教学任务
    String taskId = get(request, Constants.TEACHTASK_KEY);
    TeachTask task = teachTaskService.getTeachTask(Long.valueOf(taskId));
    if (null == task) { return forward(mapping, request, "error.teachTask.notExists", "error"); }

    // 得到该任务已排课的结果
    Set taskActivities = task.getArrangeInfo().getActivities();

    // 转换时间串
    TimeUnit[] times = TimeUnitUtil.buildTimeUnits(task.getCalendar().getStartYear(), task.getCalendar()
        .getWeekStart().intValue(), task.getArrangeInfo().getWeekStart().intValue(), task.getArrangeInfo()
        .getWeeks().intValue()
        + task.getArrangeInfo().getWeekStart().intValue() - 1, TimeUnit.CONTINUELY);

    // 获得教学班的行政班
    Set adminClasses = null;
    if (task.getRequirement().getIsGuaPai().equals(Boolean.TRUE) && null != task.getTaskGroup()) {
      adminClasses = task.getTaskGroup().getAdminClasses();
    } else {
      adminClasses = task.getTeachClass().getAdminClasses();
    }

    // 过滤要移除的排课结果
    List adminClassActivities = new ArrayList();
    if (null != adminClasses && !adminClasses.isEmpty()) {
      for (Iterator iter = adminClasses.iterator(); iter.hasNext();) {
        AdminClass adminClass = (AdminClass) iter.next();
        for (int i = 0; i < times.length; i++) {
          adminClassActivities.addAll(teachResourceService.getAdminClassActivities(adminClass.getId(),
              times[i], CourseActivity.class));
        }
      }
    }
    adminClassActivities.removeAll(taskActivities);

    // 获得教师占用时间信息
    List teacherActivities = new ArrayList();
    AvailableTime availableTime = new AvailableTime();
    availableTime.setAvailable(StringUtils.repeat("1", WeekInfo.MAX * TeachCalendar.MAXUNITS));
    // 如果教学任务中指定了教师
    if (task.getArrangeInfo().hasTeachers()) {

      for (Iterator iter = task.getArrangeInfo().getTeachers().iterator(); iter.hasNext();) {
        // 合并教师的惯用时间
        Teacher teacher = (Teacher) iter.next();
        AvailableTime other = availTimeService.getTeacherAvailTime(teacher);
        if (null == other) {
          other = new AvailableTime(AvailableTime.commonTeacherAvailTime);
          try {
            availTimeService.saveTeacherAvailTime(teacher, other);
          } catch (Exception e) {
            return forward(mapping, request, "error.teacher.notExists", "error");
          }
        }
        availableTime.setAvailable(BitStringUtil.and(availableTime.getAvailable(), other.getAvailable()));
        // 获得教师参与的教学活动
        for (int i = 0; i < times.length; i++) {
          teacherActivities.addAll(teachResourceService.getTeacherActivities(teacher.getId(), times[i],
              CourseActivity.class, null));
        }
      }
    }

    teacherActivities.removeAll(taskActivities);
    if (null == task.getArrangeInfo().getSuggest().getTime()) {
      task.getArrangeInfo().getSuggest().setTime(new AvailableTime(AvailableTime.commonTaskAvailTime));
    }
    addSingleParameter(request, "availableTime", availableTime.getAvailable());
    addCollection(request, "adminClassActivities", adminClassActivities);
    addCollection(request, "teacherActivities", teacherActivities);
    addCollection(request, "taskActivities", task.getArrangeInfo().getActivities());
    addCollection(request, "weekList", WeekInfo.WEEKS);
    addCollection(request, "rooms", baseInfoService.getBaseInfos(Classroom.class));
    addSingleParameter(request, Constants.TEACHTASK, task);
    return forward(request);

  }

  /**
   * 列出空闲教室
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward freeRoomList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // 是否检测冲突
    Boolean detectCollision = getBoolean(request, "detectCollision");
    if (null == detectCollision) {
      detectCollision = Boolean.TRUE;
    }
    // 部门权限
    String departDataRealm = getDepartmentIdSeq(request);

    Boolean containPublicRoom = getBoolean(request, "containPublicRoom");
    if (containPublicRoom.equals(Boolean.TRUE)) {
      departDataRealm += "," + Department.SCHOOLID;
    }
    // 取得任务的日历
    Long calendarId = getLong(request, "task.calendar.id");
    TeachCalendar teachCalendar = null;
    if (null != calendarId) {
      teachCalendar = teachCalendarService.getTeachCalendar(calendarId);
    }
    // 教室条件
    Classroom room = (Classroom) populate(request, Classroom.class, Constants.CLASSROOM);
    if (Boolean.FALSE.equals(detectCollision)) {
      Results.addPagination("classroomList",
          classroomService.getClassrooms(room, departDataRealm, getPageNo(request), getPageSize(request)));
    } else {
      Integer taskWeekStart = getInteger(request, "taskWeekStart");
      Integer calendarStart = getInteger(request, "calendarStart");
      Integer year = getInteger(request, "year");
      String LastDay = year.toString() + "-12-31";
      GregorianCalendar gregorianCalendar = new GregorianCalendar();
      gregorianCalendar.setTime(java.sql.Date.valueOf(LastDay));
      // 本年度以周六结尾
      boolean endAtSat = false;
      if (gregorianCalendar.get(Calendar.WEEK_OF_YEAR) == 31) {
        endAtSat = true;
      }
      String selectedWeekUnitSeq = request.getParameter("selectedWeekUnits");

      String selectedWeeks = request.getParameter("selectedWeeks");
      String vaildWeeks[] = new String[] { "", "" };

      vaildWeeks[0] = StringUtils.repeat("0", calendarStart.intValue() + taskWeekStart.intValue() - 2);
      vaildWeeks[0] += selectedWeeks;

      if (vaildWeeks[0].length() <= TeachCalendar.OVERALLWEEKS) {
        vaildWeeks[0] += StringUtils.repeat("0", TeachCalendar.OVERALLWEEKS - vaildWeeks[0].length());
      } else {
        vaildWeeks[1] = vaildWeeks[0].substring(TeachCalendar.OVERALLWEEKS);
        vaildWeeks[0] = vaildWeeks[0].substring(0, TeachCalendar.OVERALLWEEKS);
        String delta = "";
        if (!endAtSat) {
          delta = "0";
        }
        vaildWeeks[1] = delta + vaildWeeks[1];
        vaildWeeks[1] += StringUtils.repeat("0", TeachCalendar.OVERALLWEEKS - vaildWeeks[1].length());
      }

      ArrayList timeList = new ArrayList();
      ArrayList firstTimeList = new ArrayList();
      ArrayList secondTimeList = new ArrayList();

      String[] selectedWeeksUnits = selectedWeekUnitSeq.split(";");
      Arrays.sort(selectedWeeksUnits, new Comparator() {

        public int compare(Object arg0, Object arg1) {
          String weekUnit0 = (String) arg0;
          String weekUnit1 = (String) arg1;
          return NumberUtils.toInt(StringUtils.remove(weekUnit0, ','))
              - NumberUtils.toInt(StringUtils.remove(weekUnit1, ','));
        }
      });

      Integer nextYear = new Integer(year.intValue() + 1);

      // for each weekunits
      for (int j = 0; j < selectedWeeksUnits.length; j++) {
        int weekId = Integer.valueOf(selectedWeeksUnits[j].substring(0, 1)).intValue();
        int unitId = Integer.valueOf(selectedWeeksUnits[j].substring(2)).intValue();
        // for each validWeeks
        for (int i = 0; i < vaildWeeks.length; i++) {
          if ("" == vaildWeeks[i]) continue;
          List atimeList = null;
          Integer aYear = null;
          if (i == 0) {
            atimeList = firstTimeList;
            aYear = year;
          } else if (i == 1) {
            atimeList = secondTimeList;
            aYear = nextYear;
          }
          // find 有重迭的时间段
          boolean found = false;
          for (Iterator iter = atimeList.iterator(); iter.hasNext();) {
            TimeUnit atime = (TimeUnit) iter.next();
            if (atime.getYear().equals(aYear) && atime.getWeekId().intValue() == weekId) {
              if (unitId == atime.getStartUnit().intValue() - 1) {
                atime.setStartUnit(new Integer(unitId));
                atime.setStartTime(teachCalendar.getTimeSetting().getCourseUnit(unitId).getStartTime());
                found = true;
              } else if (unitId == atime.getEndUnit().intValue() + 1) {
                atime.setEndUnit(new Integer(unitId));
                atime.setEndTime(teachCalendar.getTimeSetting().getCourseUnit(unitId).getFinishTime());
                found = true;
              }
            }
          }
          if (found == false) {
            TimeUnit newTime = new TimeUnit();
            newTime.setValidWeeks(vaildWeeks[i]);
            newTime.setWeekId(new Integer(weekId));
            newTime.setStartUnit(new Integer(unitId));
            newTime.setEndUnit(new Integer(unitId));
            if (null != teachCalendar) {
              newTime.setStartTime(teachCalendar.getTimeSetting().getCourseUnit(unitId).getStartTime());
              newTime.setEndTime(teachCalendar.getTimeSetting().getCourseUnit(unitId).getFinishTime());
            }
            newTime.setYear(aYear);
            atimeList.add(newTime);
          }
        }
      }
      timeList.addAll(firstTimeList);
      timeList.addAll(secondTimeList);

      Results.addPagination("classroomList", teachResourceService.getFreeRoomsOf(
          SeqStringUtil.transformToLong(departDataRealm),
          (TimeUnit[]) timeList.toArray(new TimeUnit[timeList.size()]), room, CourseActivity.class,
          getPageNo(request), getPageSize(request)));
    }
    request.setAttribute("configTypeList", baseCodeService.getCodes(ClassroomType.class));
    return forward(request);
  }

  /**
   * 平移教学日历的教学周.
   */
  public ActionForward shiftCalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Integer offset = getInteger(request, "offset");
    TeachCalendar calendar = teachCalendarService.getTeachCalendar(
        getLong(request, "calendar.studentType.id"), get(request, "calendar.year"),
        get(request, "calendar.term"));
    if (offset.intValue() != 0) {
      TeachCalendar newCalendar = new TeachCalendar();
      newCalendar.setId(calendar.getId());
      newCalendar.setWeekSpan(calendar.getWeekSpan());
      newCalendar.setTimeSetting(calendar.getTimeSetting());

      newCalendar.setWeekStart(new Integer(calendar.getWeekStart().intValue() + offset.intValue()));
      Calendar c = Calendar.getInstance();
      c.setTime(calendar.getStart());
      c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + offset.intValue() * 7);
      newCalendar.setStart(new java.sql.Date(c.getTimeInMillis()));

      taskActivityService.adjustTimeUnit(calendar, newCalendar);
    }
    return redirect(request, "index", "info.save.success", "&calendar.id=" + calendar.getId());
  }

  /**
   * 显示可以被更换老师界面
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward displayTeachers(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    addSingleParameter(request, "task", teachTaskService.getTeachTask(getLong(request, "taskId")));
    return forward(request);
  }

  /**
   * 显示可被替换的教室
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward displayClassrooms(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    addSingleParameter(request, "task", teachTaskService.getTeachTask(getLong(request, "taskId")));
    return forward(request);
  }

  /**
   * 显示可以更换的老师<br>
   * 列举对于单个教学任务中的时间内的可用教师
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward freeTeacherList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long taskId = getLong(request, "taskId");
    TeachTask task = teachTaskService.getTeachTask(taskId);
    Long fromTeacherId = getLong(request, "fromTeacherId");
    Teacher replaceTeacher = (null == fromTeacherId) ? null : (Teacher) utilService.get(Teacher.class,
        fromTeacherId);
    List times = new ArrayList();
    for (Iterator iter = task.getArrangeInfo().getActivities().iterator(); iter.hasNext();) {
      CourseActivity activity = (CourseActivity) iter.next();
      if (null == replaceTeacher || ObjectUtils.equals(activity.getTeacher(), replaceTeacher)) {
        times.add(activity.getTime());
      }
    }
    Teacher teacher = new Teacher();
    populate(getParams(request, "teacher"), teacher);
    TimeUnit[] units = new TimeUnit[times.size()];
    times.toArray(units);
    // 此处默认不限制部门权限，可以查找全校所有的空闲老师
    Long[] departIds = null;
    Boolean allTeacher = getBoolean(request, "allTeacher");
    if (null == allTeacher || Boolean.FALSE.equals(allTeacher)) {
      departIds = SeqStringUtil.transformToLong(getDepartmentIdSeq(request));
    }
    Collection teachers = teachResourceService.getFreeTeachersOf(departIds, units, teacher,
        CourseActivity.class, getPageLimit(request));
    addCollection(request, "teachers", teachers);
    return forward(request);
  }

  public ActionForward freeClassroomList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    CourseActivity activity = (CourseActivity) utilService.load(CourseActivity.class,
        getLong(request, "activityId"));
    TimeUnit time = activity.getTime();
    Classroom room = activity.getRoom();

    EntityQuery query = new EntityQuery(Classroom.class, "room");
    query.add(new Condition("room.state = true"));
    query.add(new Condition("room.id != :roomId", room.getId()));
    String hql = "not exists (from com.shufe.model.course.arrange.Activity activity where activity.room = room and activity.time.year = "
        + time.getYear()
        + " and activity.time.startUnit <= "
        + time.getEndUnit()
        + " and "
        + time.getStartUnit()
        + " <= activity.time.endUnit and activity.time.weekId = "
        + time.getWeekId()
        + " and BITAND (activity.time.validWeeksNum, " + time.getValidWeeksNum() + ") > 0)";
    query.add(new Condition(hql));
    query.setLimit(getPageLimit(request));
    query.addOrder(OrderUtils.parser(get(request, "orderBy")));
    addCollection(request, "rooms", utilService.search(query));
    return forward(request);
  }

  /**
   * 显示可以被更换老师界面
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward changeTeacher(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long taskId = getLong(request, "taskId");
    TeachTask task = teachTaskService.getTeachTask(taskId);
    Long fromTeacherId = getLong(request, "fromTeacherId");
    Long toTeacherId = getLong(request, "toTeacherId");
    Teacher newTeacher = new Teacher(toTeacherId);
    // 更新教学任务老师
    if (null != fromTeacherId) {
      task.getArrangeInfo().getTeachers().remove(new Teacher(fromTeacherId));
    }
    if (null != toTeacherId) {
      task.getArrangeInfo().getTeachers().add(newTeacher);
    }
    // 更新教学活动老师
    for (Iterator iter = task.getArrangeInfo().getActivities().iterator(); iter.hasNext();) {
      CourseActivity activity = (CourseActivity) iter.next();
      if (null == fromTeacherId) {
        activity.setTeacher(newTeacher);
      } else {
        if (null != activity.getTeacher() && activity.getTeacher().getId().equals(fromTeacherId)) {
          activity.setTeacher(newTeacher);
        }
      }
    }
    utilService.saveOrUpdate(task);
    return redirect(request, "taskList", "info.save.success");
  }

  /**
   * 保存教室的替换
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward changeClassroom(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    CourseActivity activity = (CourseActivity) utilService.load(CourseActivity.class,
        getLong(request, "activityId"));
    Long toRoomId = getLong(request, "toRoomId");
    Classroom toRoom = classroomService.getClassroom(toRoomId);
    activity.setRoom(toRoom);
    activity.getTask().getElectInfo().setMaxStdCount(getInteger(request, "maxStdCount"));
    utilService.saveOrUpdate(activity);
    String requestURI = get(request, "requestURI");
    if (StringUtils.equals(requestURI, "courseTakeForTask")) { return redirect(request, new Action(
        "courseTakeForTask", "taskList"), "info.save.success"); }
    return redirect(request, null == getBoolean(request, "task.arrangeInfo.isArrangeComplete") ? "search"
        : "taskList", "info.save.success");
  }

  /**
   * 排课冲突检测<br>
   * kind="class"标识检测班级冲突.<br>
   * kind="teacher"标识检测教师冲突.<br>
   * kind="room"标识检测教室冲突.
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward detectCollision(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class,
        getLong(request, "calendar.id"));
    String kind = get(request, "kind");
    List collisions = null;
    if ("class".equals(kind)) {
      collisions = taskActivityService.detectClassCollision(calendar, CourseActivity.class);
    } else if ("room".equals(kind)) {
      collisions = taskActivityService.detectRoomCollision(calendar, CourseActivity.class);
    } else if ("teacher".equals(kind)) {
      collisions = taskActivityService.detectTeacherCollision(calendar, CourseActivity.class);
    } else {
      throw new RuntimeException("unsurported collisioin detect type:" + kind);
    }
    addCollection(request, "collisions", collisions);
    return forward(request);
  }

  /**
   * 上课教室利用率
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward roomUtilizations(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class,
        getLong(request, "calendar.id"));
    Float ratio = getFloat(request, "ratio");
    List orders = OrderUtils.parser(request.getParameter("orderBy"));
    Collection utils = teachResourceService.getRoomUtilizationOfCourse(calendar, getDataRealmLimit(request),
        orders, ratio);
    addCollection(request, "utilizations", utils);
    return forward(request);
  }

  public ActionForward importScheduleForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return forward(request);
  }

  public ActionForward importSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class,
        getLong(request, "task.calendar.id"));
    TransferResult tr = new TransferResult();
    Transfer transfer = ImporterServletSupport.buildEntityImporter(request, SimpleCourseActivity.class, tr);
    if (null == transfer) { return forward(request, "/pages/components/importData/error"); }
    transfer.addListener(new SimpleCourseActivityImporterListener(utilService,calendar));
    transfer.transfer(tr);
    if (tr.hasErrors()) {
      return forward(request, "/pages/components/importData/error");
    } else {
      return redirect(request, "search", "info.import.success");
    }
  }

  public void setCourseActivityService(CourseActivityService courseActivityService) {
    this.courseActivityService = courseActivityService;
  }

  public void setTeachTaskService(TeachTaskService teachTaskService) {
    this.teachTaskService = teachTaskService;
  }

  public void setAvailTimeService(AvailTimeService availTimeService) {
    this.availTimeService = availTimeService;
  }

  public void setClassroomService(ClassroomService classroomService) {
    this.classroomService = classroomService;
  }

  public void setTeachResourceService(TeachResourceService teachResourceService) {
    this.teachResourceService = teachResourceService;
  }

  public void setCourseTakeService(CourseTakeService courseTakeService) {
    this.courseTakeService = courseTakeService;
  }

  public void setTaskActivityService(TaskActivityService taskActivityService) {
    this.taskActivityService = taskActivityService;
  }

  public void setTeachTaskSearchHelper(TeachTaskSearchHelper teachTaskSearchHelper) {
    this.teachTaskSearchHelper = teachTaskSearchHelper;
  }
}
