//$Id: SpecialArrangeGroup.java May 8, 2008 11:12:48 AM chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2008. All rights reserved.
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
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      May 8, 2008  Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.task.auto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

public class SpecialArrangeGroup extends AbstractArrangeGroup {
    
    private Map teacherMap = new HashMap();
    
    private Map classMap = new HashMap();
    
    public SpecialArrangeGroup(TaskGroup group) {
        super(group);
    }
    
    public void arrange(ArrangeResult result) {
        if (null == tasks || tasks.isEmpty())
            return;
        result.startArrange(this);
        arrangeBare(result);
        result.endArrange(this);
    }
    
    public void arrangeBare(ArrangeResult result) {
        unitOccupy = BitStringUtil.convertToBoolStr(availableTime.getAvailable());
        TeachResourceDAO teachResourceDAO = getSuite().getFixture().getRoomAlloc()
                .getTeachResourceDAO();
        // 1.查找可用的时间
        List timeSlots = getTimeSlots();
        // 2.所定总体资源
        for (Iterator iter = group.getDirectTasks().iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if(task.getArrangeInfo().getTeachers().isEmpty()){
                result.notify("教学任务里没有安排老师");
                return;
            }
            for (Iterator iterator = task.getArrangeInfo().getTeachers().iterator(); iterator
                    .hasNext();) {
                Teacher teacher = (Teacher) iterator.next();
                teacherMap.put(teacher.getId(), teacher);
            }
            for (Iterator iterator = task.getTeachClass().getAdminClasses().iterator(); iterator
                    .hasNext();) {
                AdminClass adminClass = (AdminClass) iterator.next();
                classMap.put(adminClass.getId(), adminClass);
            }
        }
        Set teacherIdSet = teacherMap.keySet();
        Set classIdSet = classMap.keySet();
        // 3.初始化资源时间占用情况
        Map teacherTimeMap = new HashMap();
        Map classTimeMap = new HashMap();
        for (Iterator iterator = timeSlots.iterator(); iterator.hasNext();) {
            TimeSlot slot = (TimeSlot) iterator.next();
            // TODO room config
            Collection teacherIds = teachResourceDAO.getFreeTeacherIdsIn(teacherIdSet, slot.times,
                    null, CourseActivity.class);
            for (Iterator iterator2 = teacherIds.iterator(); iterator2.hasNext();) {
                Long teacherId = (Long) iterator2.next();
                TeacherTime teacherTime = (TeacherTime) teacherTimeMap.get(teacherId);
                if (null == teacherTime) {
                    teacherTime = new TeacherTime((Teacher) teacherMap.get(teacherId));
                    teacherTimeMap.put(teacherId, teacherTime);
                }
                teacherTime.slots.add(slot);
            }
            Collection classIds = teachResourceDAO.getFreeClassIdsIn(classIdSet, slot.times);
            for (Iterator iterator3 = classIds.iterator(); iterator3.hasNext();) {
                Long classId = (Long) iterator3.next();
                ClassTime classTime = (ClassTime) classTimeMap.get(classId);
                if (null == classTime) {
                    classTime = new ClassTime((AdminClass) classMap.get(classId));
                    classTimeMap.put(classId, classTime);
                }
                classTime.slots.add(slot);
            }
        }
        if (teacherTimeMap.size() != teacherMap.size()) {
            result.notify("授课教师不空闲");
            return;
        }
        if (classTimeMap.size() != classMap.size()) {
            result.notify("上课班级不空闲");
            return;
        }
        // 将老师对应的任务放入教师时间
        for (Iterator iter = getTasks().iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            for (Iterator iterator = task.getArrangeInfo().getTeachers().iterator(); iterator
                    .hasNext();) {
                Teacher teacher = (Teacher) iterator.next();
                TeacherTime teacherTime = (TeacherTime) teacherTimeMap.get(teacher.getId());
                teacherTime.tasks.add(task);
            }
        }
        List teacherTimes = new ArrayList(teacherTimeMap.values());
        List classTimes = new ArrayList(classTimeMap.values());
        Collections.sort(teacherTimes, new PropertyComparator("slotSize"));
        Collections.sort(classTimes, new PropertyComparator("slotSize"));
        // 4.产生分配方案
        Map schemeMap = new HashMap();// slotid->scheme
        for (Iterator iterator = teacherTimes.iterator(); iterator.hasNext();) {
            TeacherTime teacherTime = (TeacherTime) iterator.next();
            if (teacherTime.getSlotSize() < teacherTime.tasks.size()) {
                result.notify("老师空闲时间段小于教学任务空闲时间段");
                return;
            }
            teacherTime.sortSlots();
            Iterator slotIter = teacherTime.slots.iterator();
            for (Iterator iterator2 = teacherTime.tasks.iterator(); iterator2.hasNext();) {
                TeachTask task = (TeachTask) iterator2.next();
                boolean success=false;
                while (slotIter.hasNext()) {
                    TimeSlot slot = (TimeSlot) slotIter.next();
                    if (slot.count > slot.used) {
                        Scheme scheme = (Scheme) schemeMap.get(slot.getId());
                        if (null == scheme) {
                            scheme = new Scheme(slot);
                            schemeMap.put(slot.getId(), scheme);
                        }
                        slot.use();
                        scheme.addTask(task);
                        success=true;
                        break;
                    }
                }
                if(!success){
                    result.notify("没有空闲时间可以分配给教学任务");
                    return;
                }
            }
        }
        //清空分配数量
        for (Iterator iterator = schemeMap.values().iterator(); iterator.hasNext();) {
            Scheme scheme = (Scheme) iterator.next();
            scheme.slot.used=0;
        }
        for (Iterator iterator = classTimes.iterator(); iterator.hasNext();) {
            ClassTime classTime = (ClassTime) iterator.next();
            if (classTime.getSlotSize() == 0) {
                result.notify("班级沒有空闲时间");
                return;
            }
            classTime.sortSlots();
            TimeSlot slot = (TimeSlot) classTime.slots.iterator().next();
            Scheme scheme = (Scheme) schemeMap.get(slot.getId());
            if (null == scheme) {
                scheme = new Scheme(slot);
                schemeMap.put(slot.getId(), scheme);
            }
            slot.use();
            scheme.addClass(classTime.adminClass);
        }
        // 5.分配班级和教室 产生教学活动
        for (Iterator iterator = schemeMap.values().iterator(); iterator.hasNext();) {
            Scheme scheme = (Scheme) iterator.next();
            scheme.arrangeClass();
            for (Iterator iterator2 = scheme.getTasks().iterator(); iterator2.hasNext();) {
                TeachTask task = (TeachTask) iterator2.next();
                Classroom example = new Classroom();
                example.setConfigType(task.getRequirement().getRoomConfigType());
                example.setCapacityOfCourse(task.getTeachClass().getPlanStdCount());
                Classroom[] rooms = getSuite().allocRooms(scheme.slot.times, example);
                if (null==rooms[scheme.slot.times.length-1]) {
                    result.notify("没有足够的空闲教室");
                    return;
                }
                /** generator activities */
                ArrayList activities = new ArrayList();
                for (int i = 0; i < scheme.slot.times.length; i++) {
                    CourseActivity activity = new CourseActivity(false);
                    if (rooms.length == 1)
                        activity.setRoom(rooms[0]);
                    else
                        activity.setRoom(rooms[i]);
                    activity.setTeacher((Teacher) task.getArrangeInfo().getTeachers().iterator()
                            .next());
                    activity.setTime(scheme.slot.times[i]);
                    activity.setCalendar(task.getCalendar());
                    // scheme.slot.times[i].initTime(task.getCalendar().getTimeSetting());
                    activities.add(activity);
                }
                task.addActivities(activities);
                ArrangeUtil.updateTeachTaskState(task);
            }
            // if (getSuite().getFixture().getParams().getDetectCollision()[ArrangeParams.ROOM]) {
        }
    }

    /**
     * 获得时间槽
     * @return
     */
    private List getTimeSlots() {
        List timeSlots = new ArrayList();
        int slotId = 0;
        while (true) {
            TimeUnit[] times = getSuite().allocTimes(this);  //获得分配时间
            if (null == times || null == times[0]) {
                break;
            } else {
                for (int i = 0; i < times.length; i++) {
                    times[i].initTime(getCalendar().getTimeSetting());
                }
                TimeSlot slot = new TimeSlot(new Integer(slotId), times);
                int index = ((slot.times[0].getWeekId().intValue() - 1) * TeachCalendar.MAXUNITS
                        + slot.times[0].getStartUnit().intValue() - 1);
                int count = NumberUtils.toInt(availableTime.getAvailable().charAt(index) + "");
                slot.count = count;
                timeSlots.add(slot);
                slotId++;
            }
        }
        return timeSlots;
    }
    
    public TeachTask getCurTask() {
        return null;
    }
    
    public TaskRequirement getRequirement() {
        return null;
    }
    
    public int compareTo(Object arg0) {
        return 0;
    }
    
    public Collection getAdminClasses() {
        return Collections.EMPTY_LIST;
    }
    
    public Collection getTeachers() {
        return Collections.EMPTY_LIST;
    }
    
}

/**
 * 方案
 * 
 * @author chaostone
 * 
 */
class Scheme {
    List tasks = new ArrayList(); // 教学任务
    
    List classes = new ArrayList(); // 所放班级
    
    TimeSlot slot;
    
    public Scheme(TimeSlot slot) {
        super();
        this.slot = slot;
    }
    
    public List getTasks() {
        return tasks;
    }
    
    public void setTasks(List tasks) {
        this.tasks = tasks;
    }
    
    public void addTask(TeachTask task) {
        tasks.add(task);
    }
    
    public void addClass(AdminClass adminClass) {
        classes.add(adminClass);
    }
    
    public void arrangeClass() {
        int average = classes.size() / getTasks().size();
        List[] adminClasses = new List[getTasks().size()];
        int start = 0;
        for (int i = 1; i < getTasks().size(); i++) {
            adminClasses[i - 1] = classes.subList(start, start + average);
            start += average;
        }
        adminClasses[getTasks().size() - 1] = classes.subList(start, classes.size());
        
        for (int i = 0; i < getTasks().size(); i++) {
            TeachTask task = (TeachTask) getTasks().get(i);
            task.getTeachClass().getAdminClasses().clear();
            task.getTeachClass().getAdminClasses().addAll(adminClasses[i]);
            task.getTeachClass().reNameByClass();
            // TODO 按照班级计划人数还是班级实际人数计算任务的计划人数
            task.getTeachClass().calcPlanStdCount(false);
        }
    }
}

/**
 * 教师空闲时间
 * 
 * @author chaostone
 * 
 */
class TeacherTime extends ResourceTime {
    Teacher teacher; // 教师
    
    List tasks = new ArrayList();
    
    public TeacherTime(Teacher teacher) {
        super();
        this.teacher = teacher;
    }
}

class ResourceTime {
    
    List slots = new ArrayList();
    
    public int getSlotSize() {
        return slots.size();
    }
    
    public void sortSlots() {
        Collections.sort(slots);
    }
}

/**
 * 班级空闲时间
 * 
 * @author chaostone
 * 
 */
class ClassTime extends ResourceTime {
    
    AdminClass adminClass; // 行政班级
    
    public ClassTime(AdminClass adminClass) {
        super();
        this.adminClass = adminClass;
    }
    
}

/**
 * 时间槽
 * 
 * @author chaostone
 * 
 */
class TimeSlot implements Comparable {
    Integer id;
    
    TimeUnit[] times; // 可用时间段
    
    int count; // 个数
    
    int used; // 使用数
    
    public TimeSlot(Integer id, TimeUnit[] times) {
        super();
        this.id = id;
        this.times = times;
        count = times.length;
        used = 0;
    }
    
    public TimeSlot(Integer id) {
        super();
        this.id = id;
    }
    
    public void use() {
        used++;
    }
    
    public boolean equals(Object obj) {
        return id.equals(((TimeSlot) obj).id);
    }
    
    public int hashCode() {
        return id.intValue();
    }
    
    public int compareTo(Object arg0) {
        return used / count;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
}
