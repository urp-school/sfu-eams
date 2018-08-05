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
 * chaostone             2006-11-18            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.AbstractQuery;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.TaskActivity;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.calendar.TimeSetting;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.CollisionInfo;
import com.shufe.service.course.arrange.TaskActivityService;
import com.shufe.service.system.calendar.TeachCalendarService;

public class TaskActivityServiceImpl extends BasicService implements TaskActivityService {
    
    private TeachCalendarService teachCalendarService;
    
    public void adjustTimeUnit(TeachCalendar from, TeachCalendar to) {
        // 加载所有from学期的教学活动
        EntityQuery entityQuery = new EntityQuery(TaskActivity.class, "activity");
        entityQuery.add(new Condition("activity.calendar.id=" + from.getId()));
        Collection activities = utilDao.search(entityQuery);
        
        // 准备教学日历的迁移的年份/起始周数据
        int fromWeekStart = from.getWeekStart().intValue() - 1;
        int fromWeekEnd = from.getWeekStart().intValue() + from.getWeekLength() - 1;
        int fromYear = from.getStartYear();
        
        int toWeekStart = to.getWeekStart().intValue() - 1;
        int toWeekEnd = to.getWeekStart().intValue() + to.getWeekLength() - 1;
        int toYear = to.getStartYear();
        
        // 是否需要进行更换时间设置.
        TimeSetting timeSetting = (from.getTimeSetting().equals(to.getTimeSetting())) ? null : to
                .getTimeSetting();
        if (null != timeSetting) {
            timeSetting = (TimeSetting) utilDao.load(TimeSetting.class, timeSetting.getId());
        }
        Collection keeped = shift(activities, fromYear, fromWeekStart, fromWeekEnd, toYear,
                toWeekStart, toWeekEnd, timeSetting, to);
        utilDao.saveOrUpdate(keeped);
    }
    
    public void shift(TeachTask task, int offset) {
        if (0 == offset)
            return;
        TeachCalendar from = task.getCalendar();
        // 准备教学日历的迁移的年份/起始周数据
        int fromWeekStart = from.getWeekStart().intValue() - 1;
        int fromWeekEnd = from.getWeekStart().intValue() + from.getWeekLength() - 1;
        int fromYear = from.getStartYear();
        Collection shifted = shift(task.getArrangeInfo().getActivities(), fromYear, fromWeekStart,
                fromWeekEnd, fromYear, fromWeekStart + offset, fromWeekEnd + offset, null, from);
        utilDao.saveOrUpdate(shifted);
        // 更新任务
        task.getArrangeInfo().setWeekStart(
                new Integer(task.getArrangeInfo().getWeekStart().intValue() + offset));
        // 如果开始周调整后小于或等于0,则置为1
        if (task.getArrangeInfo().getWeekStart().intValue() <= 0) {
            task.getArrangeInfo().setWeekStart(new Integer(1));
        }
        utilDao.saveOrUpdate(task);
    }
    
    /**
     * 移动教学活动的安排
     * 
     * @param activities
     *            活动列表
     * @param fromYear
     *            起始年份
     * @param fromWeekStart
     *            该年份内的起始周
     * @param fromWeekEnd
     *            结束周
     * @param toYear
     *            调整到的年份
     * @param toWeekStart
     *            该年份内的起始周
     * @param toWeekEnd
     *            结束周
     * @param timeSetting
     *            要设置的调整后的时间设置
     * @param to
     *            要设置的调整后的日历
     * @return
     */
    private Collection shift(Collection activities, int fromYear, int fromWeekStart,
            int fromWeekEnd, int toYear, int toWeekStart, int toWeekEnd, TimeSetting timeSetting,
            TeachCalendar to) {
        boolean fromYearEndAtSat = TeachCalendar.yearEndAtSat(fromYear);
        boolean toEndAtSat = TeachCalendar.yearEndAtSat(toYear);
        List keeped = new ArrayList();
        
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            TaskActivity activity = (TaskActivity) iter.next();
            // 获得教学周占用分布
            // 因为学年度大多跨两年，因此可以简单的将第二年的占用重复一次，在截取其中的占用段。
            String validWeeks = null;
            if (activity.getTime().getYear().intValue() == fromYear) {
                validWeeks = activity.getTime().getValidWeeks();
            } else {
                if (activity.getTime().needShiftWeeks(fromYearEndAtSat, fromYear)) {
                    activity.getTime().leftShiftWeeks();
                }
                validWeeks = activity.getTime().getValidWeeks()
                        + activity.getTime().getValidWeeks();
            }
            // 截取"有效"的时间占用周
            String weeks = StringUtils.substring(validWeeks, fromWeekStart, fromWeekEnd);
            if (weeks.length() < toWeekEnd - toWeekStart) {
                weeks += StringUtils.repeat("0", toWeekEnd - toWeekStart - weeks.length());
            } else {
                weeks = weeks.substring(0, toWeekEnd - toWeekStart);
            }
            String toWeeks = StringUtils.repeat("0", toWeekStart) + weeks;
            
            // 组装新的教学活动
            if (!StringUtils.contains(toWeeks, '1')) {
                activity.getTime().setYear(new Integer(toYear));
                activity.setCalendar(to);
                activity.getTime().setValidWeeks(
                        StringUtils.repeat("0", TeachCalendar.OVERALLWEEKS));
                keeped.add(activity);
            } else {
                if (toWeeks.length() <= TeachCalendar.OVERALLWEEKS) {
                    activity.getTime().setValidWeeks(
                            toWeeks
                                    + StringUtils.repeat("0", TeachCalendar.OVERALLWEEKS
                                            - toWeeks.length()));
                    if (null != timeSetting) {
                        activity.getTime().setTimeByTimeSetting(timeSetting);
                    }
                    activity.getTime().setYear(new Integer(toYear));
                    activity.setCalendar(to);
                    keeped.add(activity);
                } else {
                    activity.getTime().setValidWeeks(
                            toWeeks.substring(0, TeachCalendar.OVERALLWEEKS));
                    if (null != timeSetting) {
                        activity.getTime().setTimeByTimeSetting(timeSetting);
                    }
                    activity.getTime().setYear(new Integer(toYear));
                    activity.setCalendar(to);
                    keeped.add(activity);
                    
                    // 处理第二部分
                    if (-1 != toWeeks.indexOf('1', TeachCalendar.OVERALLWEEKS)) {
                        TaskActivity newActivity = null;
                        if (!StringUtils.contains(activity.getTime().getValidWeeks(), '1')) {
                            // 重用已有的
                            newActivity = activity;
                        } else {
                            try {
                                newActivity = (TaskActivity) activity.clone();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        newActivity.getTime().setValidWeeks(
                                toWeeks.substring(TeachCalendar.OVERALLWEEKS)
                                        + StringUtils.repeat("0", 2 * TeachCalendar.OVERALLWEEKS
                                                - toWeeks.length()));
                        newActivity.getTime().setYear(new Integer(to.getStartYear() + 1));
                        if (newActivity.getTime().needShiftWeeks(toEndAtSat, toYear)) {
                            newActivity.getTime().rightShiftWeeks();
                        }
                        if (!newActivity.isPO())
                            keeped.add(newActivity);
                    }
                }
            }
        }
        return keeped;
    }
    
    /**
     * 检测班级中的教学任务时间冲突<br>
     * 主要适用范围:<br>
     * 班级非挂牌上课时间冲突.
     * 
     * <pre>
     *      ps 如果按照课程而不是教学任务检查冲突则会出现,
     *      检查不出没有区分单双周上课的课程---他们同一个课程代码,同一个时间.
     * </pre>
     */
    public List detectClassCollision(TeachCalendar calendar, Class activityClass) {
        // 首先查找出该日历相关的排课时间段
        Collection activityTimes = getActivityTimes(calendar, activityClass);
        // 首先查找出该日历相关的班级
        EntityQuery classQuery = new EntityQuery(activityClass, "activity");
        classQuery.add(new Condition("activity.calendar =:calendar", calendar));
        classQuery.join("activity.task.teachClass.adminClasses", "adminClass");
        classQuery.setSelect("select  distinct adminClass.id");
        Collection classIds = utilDao.search(classQuery);
        
        // 进行冲突检测
        EntityQuery collisionQuery = new EntityQuery(activityClass, "activity");
        collisionQuery.join("activity.task.teachClass.adminClasses", "adminClass");
        collisionQuery.join("activity.task.teachClass.adminClasses", "adminClass");
        collisionQuery.setSelect("select adminClass.id");
        collisionQuery.add(new Condition("activity.calendar =:calendar", calendar));
        Condition timeCondition = new Condition("activity.time.year=:year"
                + " and bitand(activity.time.validWeeksNum,:validWeeksNum)>0"
                + " and activity.time.weekId=:weekId " + " and activity.time.endUnit>=:startUnit"
                + " and activity.time.startUnit<=:endUnit "
                + " and activity.task.requirement.isGuaPai=false");
        
        collisionQuery.add(timeCondition);
        for (int i = 0; i < 5; i++) {
            timeCondition.getValues().add(null);
        }
        collisionQuery.setGroups(Collections.singletonList("adminClass.id having count(*)>1"));
        
        Map collisionInfos = getCollisionInfos(activityTimes, collisionQuery, classIds);
        return fillCollisionEntities(collisionInfos, AdminClass.class);
    }
    
    /**
     * 检测教室时间冲突<br>
     * 不限制在指定的日历，同时检测相同学期的其他学生类别对应的日历
     */
    public List detectRoomCollision(TeachCalendar calendar, Class activityClass) {
        List calendars = teachCalendarService.getTeachCalendarsOfOverlapped(calendar);
        // 首先查找出该日历相关的排课时间段
        Collection activityTimes = getActivityTimes(calendars, activityClass);
        // 首先查找出该日历相关的教室
        EntityQuery roomQuery = new EntityQuery(activityClass, "activity");
        roomQuery.add(new Condition("activity.calendar =:calendar", calendar));
        roomQuery.setSelect("select  distinct activity.room.id");
        Collection roomIds = utilDao.search(roomQuery);
        
        // 进行冲突检测
        EntityQuery collisionQuery = new EntityQuery(activityClass, "activity");
        collisionQuery.setSelect("select activity.room.id");
        collisionQuery.add(new Condition("activity.calendar in(:calendars)", calendars));
        Condition timeCondition = new Condition("activity.time.year=:year"
                + " and bitand(activity.time.validWeeksNum,:validWeeksNum)>0"
                + " and activity.time.weekId=:weekId " + " and activity.time.endUnit>=:startUnit"
                + " and activity.time.startUnit<=:endUnit");
        collisionQuery.add(new Condition("activity.room.isCheckActivity = true"));
        for (int i = 0; i < 5; i++) {
            timeCondition.getValues().add(null);
        }
        collisionQuery.add(timeCondition);
        collisionQuery.setGroups(Collections.singletonList("activity.room.id having count(*)>1"));
        Map collisionInfos = getCollisionInfos(activityTimes, collisionQuery, roomIds);
        return fillCollisionEntities(collisionInfos, Classroom.class);
    }
    
    /**
     * 检测排课教师时间冲突<br>
     * 主要支持范围:<br>
     * 1)排课的教师<br>
     * 2)排考的主考教师<br>
     * 不限制在指定的日历，同时检测相同学期的其他学生类别对应的日历
     */
    public List detectTeacherCollision(TeachCalendar calendar, Class activityClass) {
        List calendars = teachCalendarService.getTeachCalendarsOfOverlapped(calendar);
        // 首先查找出该日历相关的排课时间段
        Collection activityTimes = getActivityTimes(calendars, activityClass);
        // 首先查找出该日历相关的教师
        EntityQuery teacherQuery = new EntityQuery(activityClass, "activity");
        teacherQuery.add(new Condition("activity.calendar =:calendar", calendar));
        teacherQuery.setSelect("select  distinct activity.teacher.id");
        Collection teacherIds = utilDao.search(teacherQuery);
        
        // 进行冲突检测
        EntityQuery collisionQuery = new EntityQuery(activityClass, "activity");
        collisionQuery.setSelect("select activity.teacher.id");
        collisionQuery.add(new Condition("activity.calendar in(:calendars)", calendars));
        Condition timeCondition = new Condition("activity.time.year=:year"
                + " and bitand(activity.time.validWeeksNum,:validWeeksNum)>0 "
                + " and activity.time.weekId=:weekId " + " and activity.time.endUnit>=:startUnit"
                + " and activity.time.startUnit<=:endUnit ");
        collisionQuery.add(timeCondition);
        for (int i = 0; i < 5; i++) {
            timeCondition.getValues().add(null);
        }
        collisionQuery
                .setGroups(Collections.singletonList("activity.teacher.id having count(*)>1"));
        Map collisionInfos = getCollisionInfos(activityTimes, collisionQuery, teacherIds);
        return fillCollisionEntities(collisionInfos, Teacher.class);
    }
    
    /**
     * 检测排课时间冲突的学生
     * 
     * @param calendar
     * @param activityClass
     * @return
     */
    public List collisionCourseTakes(TeachCalendar calendar) {
        // 首先查找出该日历相关的排课时间段
        Collection activityTimes = getActivityTimes(calendar, CourseActivity.class);
        // 进行冲突检测
        EntityQuery entityQuery = new EntityQuery(CourseTake.class, "take");
        entityQuery.setSelect("select take.student.id");
        entityQuery.setFrom("from CourseTake as take ,CourseActivity as activity ");
        entityQuery.add(new Condition("take.task=activity.task"));
        Condition timeCondition = new Condition("activity.calendar.id=" + calendar.getId()
                + " and activity.time.year=:year and "
                + " bitand(activity.time.validWeeksNum,:validWeeksNum)>0"
                + " and  activity.time.weekId=:weekId "
                + " and  activity.time.endUnit>=:startUnit "
                + " and  activity.time.startUnit<=:endUnit");
        entityQuery.add(timeCondition);
        entityQuery.setGroups(Collections.singletonList("take.student having count(*)>1"));
        
        EntityQuery courseTakeQuery = new EntityQuery(CourseTake.class, "take");
        courseTakeQuery.add(new Condition("take.task.calendar=:calendar", calendar));
        courseTakeQuery.join("take.task.arrangeInfo.activities", "activity");
        courseTakeQuery.add(timeCondition);
        Condition stdCondition = new Condition("take.student.id in(:stdIds)");
        courseTakeQuery.add(stdCondition);
        Set courseTakes = new HashSet();
        for (Iterator iter = activityTimes.iterator(); iter.hasNext();) {
            TimeUnit time = (TimeUnit) iter.next();
            timeCondition.getValues().clear();
            timeCondition.getValues().add(time.getYear());
            timeCondition.getValues().add(time.getValidWeeksNum());
            timeCondition.getValues().add(time.getWeekId());
            timeCondition.getValues().add(time.getStartUnit());
            timeCondition.getValues().add(time.getEndUnit());
            Collection collisionStdIds = utilDao.search(entityQuery);
            if (!collisionStdIds.isEmpty()) {
                stdCondition.getValues().clear();
                stdCondition.getValues().add(collisionStdIds);
                courseTakes.addAll(utilDao.search(courseTakeQuery));
            }
        }
        return new ArrayList(courseTakes);
    }
    
    private Collection getActivityTimes(TeachCalendar teachCalendar, Class activityClass) {
        return getActivityTimes(Collections.singletonList(teachCalendar), activityClass);
    }
    
    /**
     * 获得排课的时间分布
     * 
     * @param calendar
     * @param activityClass
     * @return
     */
    private Collection getActivityTimes(List calendars, Class activityClass) {
        // 首先查找出该日历相关的排课时间段
        EntityQuery timeQuery = new EntityQuery(activityClass, "activity");
        timeQuery.add(new Condition("activity.calendar in (:calendars)", calendars));
        timeQuery.setSelect("select  distinct new com.ekingstar.eams.system.time.TimeUnit("
                + "activity.time.year," + "activity.time.weekId," + "activity.time.startUnit,"
                + "activity.time.endUnit," + "activity.time.validWeeks,"
                + "activity.time.validWeeksNum)");
        timeQuery
                .addOrder(OrderUtils
                        .parser("activity.time.year,activity.time.weekId,activity.time.startUnit,activity.time.endUnit,activity.time.validWeeks"));
        List[] timeLists = new ArrayList[WeekInfo.MAX * TeachCalendar.MAXUNITS];
        
        // 将查询出来的时间按照教学周和小结进行最小化处理
        List times = (List) utilDao.search(timeQuery);
        for (int i = 0; i < times.size(); i++) {
            TimeUnit timeUnit = (TimeUnit) times.get(i);
            int start = timeUnit.getStartUnit().intValue();
            int end = timeUnit.getEndUnit().intValue();
            for (int j = start; j <= end; j++) {
                TimeUnit time = new TimeUnit(timeUnit.getYear(), timeUnit.getWeekId(), new Integer(
                        j), new Integer(j), timeUnit.getValidWeeks(), timeUnit.getValidWeeksNum());
                int index = (time.getWeekId().intValue() - 1) * TeachCalendar.MAXUNITS + (j - 1);
                List timeList = timeLists[index];
                if (null == timeList) {
                    timeList = new ArrayList();
                    timeLists[index] = timeList;
                    timeList.add(time);
                    continue;
                }
                List newTimeUnits = new ArrayList();
                boolean add = false;
                for (Iterator iter = timeList.iterator(); iter.hasNext();) {
                    TimeUnit unit = (TimeUnit) iter.next();
                    
                    if (unit.getYear().equals(time.getYear())) {
                        // 冲突时间段
                        long collisionWeekNum = unit.getValidWeeksNum().longValue()
                                & time.getValidWeeksNum().longValue();
                        if (collisionWeekNum > 0) {
                            // 组内时间点 差异段
                            long unitWeekNum = unit.getValidWeeksNum().longValue()
                                    ^ collisionWeekNum;
                            if (0 == unitWeekNum) {
                                unit.setValidWeeks(TimeUnit.weekOccupyString(collisionWeekNum));
                            } else {
                                unit.setValidWeeks(TimeUnit.weekOccupyString(unitWeekNum));
                            }
                            
                            // 要加入的时间点
                            long timeWeekNum = time.getValidWeeksNum().longValue()
                                    ^ collisionWeekNum;
                            if (0 == timeWeekNum) {
                                if (0 != unitWeekNum) {
                                    time.setValidWeeks(TimeUnit.weekOccupyString(collisionWeekNum));
                                } else {
                                    time.setValidWeeks(TimeUnit.EMPTY_OCCUPY_WEEK);
                                }
                            } else {
                                time.setValidWeeks(TimeUnit.weekOccupyString(timeWeekNum));
                            }
                            // 公共的时间段
                            if (0 != unitWeekNum && 0 != timeWeekNum && 0 != collisionWeekNum) {
                                addTimeUnit(newTimeUnits, new TimeUnit(time.getYear(), time
                                        .getWeekId(), time.getStartUnit(), time.getEndUnit(),
                                        TimeUnit.weekOccupyString(collisionWeekNum), new Long(
                                                collisionWeekNum)));
                            }
                            // 是否还有利用价值
                            if (time.getValidWeeksNum().longValue() != 0) {
                                add = true;
                            } else {
                                break;
                            }
                        } else {
                            add = true;
                        }
                    } else {
                        add = true;
                    }
                }
                if (add) {
                    addTimeUnit(timeList, time);
                }
                timeList.addAll(newTimeUnits);
            }
        }
        
        // 返回没有重复的线性时间列表
        List res = new ArrayList();
        for (int j = 0; j < timeLists.length; j++) {
            if (null != timeLists[j]) {
                Set weekNumSet = new HashSet();
                for (Iterator iter = timeLists[j].iterator(); iter.hasNext();) {
                    TimeUnit unit = (TimeUnit) iter.next();
                    if (!weekNumSet.contains(unit.getValidWeeksNum())) {
                        res.add(unit);
                        weekNumSet.add(unit.getValidWeeksNum());
                    }
                }
            }
        }
        return res;
    }
    
    private void addTimeUnit(List times, TimeUnit unit) {
        if (unit.getValidWeeksNum().longValue() != 0) {
            times.add(unit);
        }
    }
    
    private Map getCollisionInfos(Collection activityTimes, AbstractQuery collisionQuery,
            Collection resourceIds) {
        Map params = collisionQuery.getParams();
        collisionQuery.setParams(params);
        Map collisionInfos = new HashMap();
        for (Iterator iter = activityTimes.iterator(); iter.hasNext();) {
            TimeUnit time = (TimeUnit) iter.next();
            params.put("year", time.getYear());
            params.put("validWeeksNum", time.getValidWeeksNum());
            params.put("weekId", time.getWeekId());
            params.put("startUnit", time.getStartUnit());
            params.put("endUnit", time.getEndUnit());
            Collection collisionResourceIds = utilDao.search(collisionQuery);
            collisionResourceIds = CollectionUtils.intersection(resourceIds, collisionResourceIds);
            for (Iterator iterator = collisionResourceIds.iterator(); iterator.hasNext();) {
                Object id = (Object) iterator.next();
                // 有些活动里面没有教师或者教室
                if (null == id) {
                    continue;
                }
                CollisionInfo collisionInfo = (CollisionInfo) collisionInfos.get(id);
                if (null == collisionInfo) {
                    collisionInfo = new CollisionInfo(id, time);
                    collisionInfos.put(id, collisionInfo);
                } else {
                    collisionInfo.add(time);
                }
            }
        }
        return collisionInfos;
    }
    
    /**
     * 将冲突检测中的实体类填充
     * 
     * @param collisionInfos
     * @return
     */
    private List fillCollisionEntities(Map collisionInfos, Class entityClass) {
        Collection collisionResources = utilService
                .load(entityClass, "id", collisionInfos.keySet());
        for (Iterator iter = collisionResources.iterator(); iter.hasNext();) {
            Entity entity = (Entity) iter.next();
            CollisionInfo collisionInfo = (CollisionInfo) collisionInfos.get(entity.getEntityId());
            collisionInfo.setResource(entity);
            collisionInfo.mergeTimes();
        }
        return new ArrayList(collisionInfos.values());
    }
    
    public boolean isBeenTaskForTeacher(Long calendarId, Long teacherId, Long thisTaskId) {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        Teacher teacher = (Teacher) utilService.load(Teacher.class, teacherId);
        TeachTask task = (TeachTask) utilService.load(TeachTask.class, thisTaskId);
        if (task.getArrangeInfo().getTeachers().contains(teacher)) {
            return false;
        }
        StringBuffer activityHql = new StringBuffer();
        for (Iterator it = task.getArrangeInfo().getActivities().iterator(); it.hasNext();) {
            Activity activity = (Activity) it.next();
            activityHql.append("(");
            activityHql.append("activity.time.weekId = " + activity.getTime().getWeekId());
            activityHql.append(" and activity.time.year = " + activity.getTime().getYear());
            activityHql.append(" and activity.time.endUnit >= " + 5);
            activityHql.append(" and activity.time.startUnit <= " + 6);
            activityHql.append(")");
            if (it.hasNext()) {
                activityHql.append(" or ");
            }
        }
        
        EntityQuery query = new EntityQuery(CourseActivity.class, "activity");
        query.add(new Condition("activity.teacher.id = :teacherId", teacher.getId()));
        Collection teachers = task.getArrangeInfo().getTeachers();
        if (CollectionUtils.isNotEmpty(teachers)) {
            query.add(new Condition("activity.teacher not in (:teachers)", teachers));
        }
        if (CollectionUtils.isNotEmpty(task.getArrangeInfo().getActivities())) {
            query.add(new Condition(activityHql.toString()));
        }
        if (null != task) {
            query.add(new Condition("activity.task.id != :taskId", task.getId()));
        }
        query.add(new Condition("activity.calendar.id = :calendarId", calendar.getId()));
        return CollectionUtils.isNotEmpty(utilService.search(query));
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
}
