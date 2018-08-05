//$Id: TaskGroup.java,v 1.4 2006/12/19 10:08:45 duanth Exp $
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
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.course.arrange.task.auto.Arrange;
import com.shufe.service.course.task.TeachTaskUtil;

/**
 * 排课分组表
 * 
 * @author zhouqi
 */
public class TaskGroup extends LongIdObject {
    
    private static final long serialVersionUID = -5536369171462414070L;
    
    /**
     * 课程组的名称
     */
    private String name;
    
    /**
     * 是否要求在同一时间安排所有组内课程
     */
    private Boolean isSameTime = new Boolean(false);
    
    /**
     * 组内课程
     */
    private Set directTasks = new HashSet();
    
    /**
     * 包含的课程组
     */
    private Set groups = new HashSet();
    
    /**
     * 父类组
     */
    private TaskGroup parent;
    
    /**
     * 课程组排课的优先级
     */
    private Integer priority = new Integer(Arrange.defaultPriority);
    
    /**
     * 排课建议
     */
    private ArrangeSuggest suggest = new ArrangeSuggest();
    
    /**
     * 该组的课程
     */
    private Course course;
    
    /**
     * 该组的课程性质
     */
    private CourseType courseType;
    
    /**
     * 是否允许排课时班级变动
     */
    private Boolean isClassChange = new Boolean(false);
    
    /**
     * @return Returns the isSameTime.
     */
    public Boolean getIsSameTime() {
        return isSameTime;
    }
    
    public void setIsSameTime(Boolean isSameTime) {
        this.isSameTime = isSameTime;
    }
    
    public Set getDirectTasks() {
        return directTasks;
    }
    
    public void setDirectTasks(Set tasks) {
        this.directTasks = tasks;
    }
    
    public ArrangeSuggest getSuggest() {
        return suggest;
    }
    
    public void setSuggest(ArrangeSuggest suggest) {
        this.suggest = suggest;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public Set getGroups() {
        return groups;
    }
    
    public void setGroups(Set groups) {
        this.groups = groups;
    }
    
    public TaskGroup getParent() {
        return parent;
    }
    
    public void setParent(TaskGroup parent) {
        this.parent = parent;
    }
    
    public CourseType getCourseType() {
        return courseType;
    }
    
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public Boolean getIsClassChange() {
        return isClassChange;
    }
    
    public void setIsClassChange(Boolean isClassChange) {
        this.isClassChange = isClassChange;
    }
    
    /**
     * 获得教学任务列表
     * 
     * @return
     */
    public List getTaskList() {
        List taskList = new ArrayList();
        if (null != directTasks && !directTasks.isEmpty())
            taskList.addAll(directTasks);
        if (null != groups && !groups.isEmpty()) {
            for (Iterator iter = groups.iterator(); iter.hasNext();) {
                TaskGroup group = (TaskGroup) iter.next();
                taskList.addAll(group.getTaskList());
            }
        }
        return taskList;
    }
    
    public Set getTeachers() {
        Set set = new HashSet();
        for (Iterator iterator = directTasks.iterator(); iterator.hasNext();) {
            TeachTask task = (TeachTask) iterator.next();
            set.addAll(task.getArrangeInfo().getTeachers());
        }
        return set;
    }
    
    public Set getAdminClasses() {
        Set AdminClasses = new HashSet();
        for (Iterator iter = getTaskList().iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (!task.getTeachClass().getAdminClasses().isEmpty()) {
                AdminClasses.addAll(task.getTeachClass().getAdminClasses());
            }
        }
        return AdminClasses;
    }
    
    /**
     * 向组内添加下一级组
     * 
     * @param child
     */
    public void addGroup(TaskGroup child) {
        if (null == getGroups()) {
            setGroups(new HashSet());
        }
        child.setParent(this);
        getGroups().add(child);
    }
    
    /**
     * 获得所有组，不包括自身
     * 
     * @return
     */
    public List getAllGroups() {
        List childGroups = new ArrayList();
        if (null != getGroups() && getGroups().size() > 0) {
            childGroups.addAll(getGroups());
            for (Iterator iter = getGroups().iterator(); iter.hasNext();) {
                TaskGroup one = (TaskGroup) iter.next();
                childGroups.addAll(one.getAllGroups());
            }
        }
        return childGroups;
    }
    
    public void addTasks(Collection tasks) {
        addTasks(tasks, new AddTaskOptions());
    }
    
    public void addTasks(Collection tasks, AddTaskOptions options) {
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.setTaskGroup(this);
            getDirectTasks().add(task);
            // 添加建议时间和教室
            if (options.addSuggestRoom) {
                getSuggest().mergeRoom(task.getArrangeInfo().getSuggest());
            }
            if (options.addSuggestTime) {
                getSuggest().mergeTime(task.getArrangeInfo().getSuggest());
            }
        }
        if (options.mergeTeacher) {
            mergeTaskOfSameTeacher();
        }
        if (options.shareAdminClass) {
            shareElectScopeAndAdminClass();
        }
    }
    
    /**
     * 添加教学任务
     * 
     * @param task
     * @param options
     */
    public void addTask(TeachTask task, AddTaskOptions options) {
        task.setTaskGroup(this);
        getDirectTasks().add(task);
        // 添加建议时间和教室
        if (options.addSuggestRoom) {
            getSuggest().mergeRoom(task.getArrangeInfo().getSuggest());
        }
        if (options.addSuggestTime) {
            getSuggest().mergeTime(task.getArrangeInfo().getSuggest());
        }
        if (options.mergeTeacher) {
            mergeTaskOfSameTeacher();
        }
        if (options.shareAdminClass) {
            shareElectScopeAndAdminClass();
        }
    }
    
    public void removeTasks(Collection tasks, RemoveTaskOptions options) {
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            removeTask((TeachTask) iter.next(), options);
        }
    }
    
    public void removeTask(TeachTask task, RemoveTaskOptions options) {
        if (getTaskList().contains(task)) {
            if (!getDirectTasks().remove(task)) {
                for (Iterator iter = getGroups().iterator(); iter.hasNext();) {
                    TaskGroup group = (TaskGroup) iter.next();
                    group.removeTask(task, options);
                }
            } else {
                task.setTaskGroup(null);
                // 剥离建议时间和教室
                if (options.removeSuggestRoom) {
                    getSuggest().detachRoom(task.getArrangeInfo().getSuggest());
                }
                if (options.removeSuggestTime) {
                    getSuggest().detachTime(task.getArrangeInfo().getSuggest());
                }
            }
        }
    }
    
    /**
     * 对每一个教学任务为组内所有的上课班级之和.<br>
     */
    public void shareElectScopeAndAdminClass() {
        Set adminClasses = getAdminClasses();
        for (Iterator iter = this.getTaskList().iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.getTeachClass().setAdminClasses(adminClasses);
            task.getTeachClass().reNameByClass();
        }
    }
    
    /**
     * 合并教师相同的任务
     */
    private void mergeTaskOfSameTeacher() {
        Set sortedTasks = getDirectTasks();
        HashMap taskMap = new HashMap();
        for (Iterator iterator = sortedTasks.iterator(); iterator.hasNext();) {
            TeachTask task = (TeachTask) iterator.next();
            if (taskMap.containsKey(task.getArrangeInfo().getTeachers())) {
                TeachTask tobeMergedTask = (TeachTask) taskMap.get(task.getArrangeInfo()
                        .getTeachers());
                TeachTaskUtil.merge(tobeMergedTask, task);
            } else {
                taskMap.put(task.getArrangeInfo().getTeachers(), task);
            }
        }
        this.directTasks.clear();
        this.directTasks.addAll(taskMap.values());
    }
    
    public Long getDirectTaskStdCount() {
        long count = 0;
        for (Iterator it = directTasks.iterator(); it.hasNext();) {
            TeachTask task = (TeachTask) it.next();
            count += task.getTeachClass().getPlanStdCount().longValue();
        }
        return new Long(count);
    }
    
    public Integer getArrangedTaskCount() {
        int arrangedCount = 0;
        List tasks = getTaskList();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (task.getArrangeInfo().getIsArrangeComplete().equals(Boolean.TRUE)) {
                arrangedCount++;
            }
        }
        return new Integer(arrangedCount);
    }
    
    public List getCourses() {
        List courses = new ArrayList();
        Set courseSet = new HashSet();
        for (Iterator iter = getTaskList().iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            courseSet.add(task.getCourse());
        }
        courses.addAll(courseSet);
        return courses;
    }
    
    public List getCourseTypes() {
        List courseTypes = new ArrayList();
        Set courseTypeSet = new HashSet();
        for (Iterator iter = getTaskList().iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            courseTypeSet.add(task.getCourseType());
        }
        courseTypes.addAll(courseTypeSet);
        return courseTypes;
    }
    
    /**
     * 删除行政班级
     * 
     * @param adminClasses
     * @param removeEmptyTask
     *            是否删除没有行政班级的教学任务
     */
    public void removeAdminClass(Collection adminClasses, boolean removeEmptyTask) {
        for (Iterator iter = getTaskList().iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            task.getTeachClass().getAdminClasses().removeAll(adminClasses);
            task.getTeachClass().reNameByClass();
            if (removeEmptyTask && task.getTeachClass().getAdminClasses().isEmpty()) {
                removeTask(task, new RemoveTaskOptions());
            }
        }
    }
    
    /**
     * 返回课程组内的实际人数
     * 
     * @return
     */
    public int getStdCountInClass() {
        int stdCount = 0;
        Set adminClasses = getAdminClasses();
        for (Iterator iter = adminClasses.iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            stdCount += adminClass.getActualStdCount().intValue();
        }
        return stdCount;
    }
}
