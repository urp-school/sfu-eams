//$Id: TeachTaskServiceImpl.java,v 1.34 2007/01/15 01:03:43 duanth Exp $
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
 * chaostone             2005-10-22         Created
 *
 ********************************************************************************/

package com.shufe.service.course.task.impl;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ResettableIterator;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.course.plan.PlanCourseDAO;
import com.shufe.dao.course.task.TaskSeqNoGenerator;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.dao.system.baseinfo.AdminClassDAO;
import com.shufe.dao.system.calendar.TermCalculator;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.arrange.task.TaskInDepartment;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.plan.CourseGroup;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.course.task.TaskElectInfo;
import com.shufe.model.course.task.TaskOfCourseType;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachClass;
import com.shufe.model.course.task.TeachCommon;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.OnCampusTimeNotFoundException;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TaskCopyParams;
import com.shufe.service.course.task.TaskGenParams;
import com.shufe.service.course.task.TeachClassSplitMode;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.course.task.TeachTaskUtil;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.OutputMessage;
import com.shufe.web.OutputWebObserver;
import com.shufe.web.action.course.task.TaskGenObserver;

/**
 * 教学任务管理服务实现
 * 
 * @author chaostone
 */
public class TeachTaskServiceImpl extends BasicService implements TeachTaskService {
    
    protected TeachTaskDAO taskDAO;
    
    protected TeachCalendarService calendarService;
    
    protected AdminClassDAO adminClassDAO;
    
    protected PlanCourseDAO planCourseDAO;
    
    protected TeachPlanService teachPlanService;
    
    private TaskSeqNoGenerator generator;
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTask(java.lang.Long)
     */
    public TeachTask getTeachTask(final Long taskId) {
        if (null == taskId || taskId.intValue() == 0) {
            return null;
        } else {
            return taskDAO.getTeachTask(taskId);
        }
    }
    
    /**
     * 根据id加载对应的教学任务.
     * 
     * @param taskId
     * @return
     */
    public TeachTask loadTeachTask(final Long taskId) {
        return taskDAO.loadTeachTask(taskId);
    }
    
    public Collection getTeachTasks(final List conditions, final PageLimit limit, final List orders) {
        return taskDAO.getTeachTasks(conditions, limit, orders);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTaskByStd(java.lang.String,
     *      java.lang.String, com.shufe.model.system.calendar.TeachCalendar)
     */
    public TeachTask getTeachTaskByStd(String stdNo, Long teachTaskId, TeachCalendar calendar) {
        if (StringUtils.isEmpty(stdNo) || null == teachTaskId || !calendar.checkId()) {
            return null;
        }
        List tasks = getTeachTasksByCategory(stdNo, TeachTaskFilterCategory.STD, calendar);
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (teachTaskId.equals(task.getId())) {
                return task;
            }
        }
        return null;
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTasksByCategory(java.lang.String,
     *      com.shufe.dao.course.task.TeachTaskFilterCategory,
     *      com.shufe.model.system.calendar.TeachCalendar, int, int)
     */
    public Pagination getTeachTasksByCategory(Serializable id, TeachTaskFilterCategory category,
            TeachCalendar calendar, int pageNo, int pageSize) {
        if (null == id || null == calendar.getId() || pageNo < 1 || pageSize < 1) {
            return null;
        } else {
            return taskDAO.getTeachTasksByCategory(id, category, calendar, pageNo, pageSize);
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTasksByCategory(java.lang.String,
     *      com.shufe.dao.course.task.TeachTaskFilterCategory,
     *      com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachTasksByCategory(Serializable id, TeachTaskFilterCategory category,
            TeachCalendar calendar) {
        if (null == id || null == calendar.getId()) {
            return Collections.EMPTY_LIST;
        } else {
            return taskDAO.getTeachTasksByCategory(id, category, Collections
                    .singletonList(calendar));
        }
    }
    
    public List getTeachTasksByCategory(Serializable id, TeachTaskFilterCategory category,
            Collection calendars) {
        if (null == id || calendars.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            return taskDAO.getTeachTasksByCategory(id, category, calendars);
        }
    }
    
    public List getTeachTasksOfStd(Serializable stdId, List calendars) {
        if (!calendars.isEmpty()) {
            return taskDAO.getTeachTasksOfStd(stdId, calendars);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTasks(com.shufe.model.course.task.TeachTask)
     */
    public List getTeachTasks(TeachTask task) {
        return taskDAO.getTeachTasks(task);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTasksByIds(java.lang.Long[])
     */
    public List getTeachTasksByIds(Long[] taskIds) {
        if (null == taskIds || taskIds.length < 1)
            return Collections.EMPTY_LIST;
        else
            return taskDAO.getTeachTasksByIds(taskIds);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTasksByIds(java.lang.String)
     */
    public List getTeachTasksByIds(String taskIds) {
        if (StringUtils.isEmpty(taskIds)) {
            return null;
        } else {
            return taskDAO.getTeachTasksByIds(SeqStringUtil.transformToLong(taskIds));
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTasks(com.shufe.model.course.task.TeachTask,
     *      int, int)
     */
    public Pagination getTeachTasks(TeachTask task, int pageNo, int pageSize) {
        if (pageNo < 1 || pageSize < 1) {
            return null;
        } else {
            return taskDAO.getTeachTasks(task, pageNo, pageSize);
        }
    }
    
    /**
     * 返回开课部门列表范围内的教学任务
     * 
     * @param task
     * @param teachDepartIdSeq
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getTeachTasksOfTeachDepart(TeachTask task, String stdTypeIdSeq,
            String teachDepartIdSeq, int pageNo, int pageSize) {
        if (StringUtils.isEmpty(teachDepartIdSeq) || StringUtils.isEmpty(stdTypeIdSeq)
                || pageNo < 1 || pageSize < 1) {
            return new Pagination(new Result(0, Collections.EMPTY_LIST));
        } else {
            return taskDAO.getTeachTasksOfTeachDepart(task, SeqStringUtil
                    .transformToLong(stdTypeIdSeq),
                    SeqStringUtil.transformToLong(teachDepartIdSeq), pageNo, pageSize);
        }
    }
    
    public Pagination getTeachTasksOfLonely(TeachTask task, Long[] stdTypeIds,
            Long[] teachDepartIds, int pageNo, int pageSize) {
        return taskDAO.getTeachTasksOfLonely(task, stdTypeIds, teachDepartIds, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachTasksOfTeachPlan(java.lang.String,
     *      com.shufe.model.course.task.TeachTask, int, int)
     */
    public Pagination getTeachTasksOfTeachPlan(String teachPlanIdSeq, TeachTask task, int pageNo,
            int pageSize) {
        return taskDAO.getTeachTasksOfTeachPlan(SeqStringUtil.transformToLong(teachPlanIdSeq),
                task, pageNo, pageSize);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#teachTasksConfirm(java.lang.String[],
     *      boolean)
     */
    public void teachTasksConfirm(Long[] taskIds, boolean isConfirm) {
        if (null == taskIds || taskIds.length < 1)
            return;
        taskDAO.updateTeachTaskByIds("isConfirm", new Boolean(isConfirm), taskIds);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#teachTasksConfirm(java.lang.String,
     *      boolean)
     */
    public void teachTasksConfirm(String taskIds, boolean isConfirm) {
        if (StringUtils.isEmpty(taskIds)) {
            return;
        }
        try {
            taskDAO.updateTeachTaskByIds("isConfirm", new Boolean(isConfirm), SeqStringUtil
                    .transformToLong(taskIds));
        } catch (Exception e) {
            return;
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#teachTasksConfirmOfDepart(java.lang.String,
     *      boolean, com.shufe.model.system.calendar.TeachCalendar)
     */
    public void teachTasksConfirmOfDepart(Long departId, boolean isConfirm, TeachCalendar calendar) {
        if (null == departId) {
            return;
        } else {
            taskDAO.updateTeachTaskByCategory("isConfirm", new Boolean(isConfirm), departId,
                    TeachTaskFilterCategory.DEPART, calendar);
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#teachTasksConfirm(com.shufe.model.course.task.TeachTask,
     *      boolean)
     */
    public void teachTasksConfirm(TeachTask task, boolean isConfirm) {
        try {
            taskDAO.updateTeachTaskByCriteria("isConfirm", new Boolean(isConfirm), task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#merge(java.lang.Long[],int)
     */
    private TeachTask merge(Long[] taskIds, int target) {
        if (null == taskIds || taskIds.length < 1 || target >= taskIds.length || target < 0) {
            return null;
        }
        List taskList = taskDAO.getTeachTasksByIds(taskIds);
        if (taskList.isEmpty()) {
            return null;
        }
        TeachTask[] tasks = new TeachTask[taskList.size()];
        taskList.toArray(tasks);
        return merge(tasks, target);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#merge(java.lang.Long[])
     */
    public TeachTask merge(Long[] taskIds) {
        return merge(taskIds, 0);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#merge(java.lang.Long[], java.lang.Long)
     */
    public TeachTask merge(Long[] taskIds, Long reservedId) {
        if (null == taskIds || taskIds.length < 1 || null == reservedId) {
            return null;
        } else {
            for (int i = 0; i < taskIds.length; i++) {
                if (reservedId.equals(taskIds[i])) {
                    return merge(taskIds, i);
                }
            }
            return null;
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#merge(com.shufe.model.course.task.TeachTask[])
     */
    private TeachTask merge(TeachTask[] tasks, int target) {
        for (int i = 0; i < tasks.length; i++) {
            if (i == target) {
                continue;
            }
            TeachTaskUtil.merge(tasks[target], tasks[i]);
        }
        tasks[target].getTeachClass().processTaskForClass();
        tasks[target].getTeachClass().reNameByClass();
        taskDAO.saveMergeResult(tasks, target);
        return tasks[target];
    }
    
    /**
     * 技术实现上,保留要拆分的教学任务
     * 
     * @see com.shufe.service.course.task.TeachTaskService#split(com.shufe.model.course.task.TeachTask,
     *      int, com.shufe.service.course.task.TeachClassSplitMode)
     */
    public TeachTask[] split(TeachTask task, int num, TeachClassSplitMode mode,
            Integer[] splitUnitNums) {
        if (num < 2) {
            return new TeachTask[] { task };
        } else {
            TeachTask tasks[] = new TeachTask[num];
            tasks[0] = task;
            for (int i = 1; i < tasks.length; i++) {
                tasks[i] = (TeachTask) task.clone();
                tasks[i].setCreateAt(new Date(System.currentTimeMillis()));
                tasks[i].setModifyAt(tasks[i].getCreateAt());
                tasks[i].setId(null);
            }
            TeachClass teachClass = task.getTeachClass();
            mode.setStdNums(splitUnitNums);
            
            TeachClass[] splitClasses = mode.splitClass(teachClass, num);
            
            teachClass.setName(splitClasses[0].getName());
            teachClass.setPlanStdCount(splitClasses[0].getPlanStdCount());
            teachClass.setStdCount(splitClasses[0].getStdCount());
            teachClass.setAdminClasses(splitClasses[0].getAdminClasses());
            teachClass.getCourseTakes().clear();
            teachClass.addCourseTakes(splitClasses[0].getCourseTakes());
            // teachClass.reNameByClass();
            teachClass.processTaskForClass();
            for (int j = 1; j < tasks.length; j++) {
                // 生成教学班的名字
                // splitClasses[j].reNameByClass();
                splitClasses[j].processTaskForClass();
                tasks[j].setTeachClass(splitClasses[j]);
                EntityUtils.evictEmptyProperty(tasks[j]);
                tasks[j].setGradeState(new GradeState(tasks[j]));
                if (null != tasks[j].getTeachClass().getCourseTakes()) {
                    for (Iterator iter = tasks[j].getTeachClass().getCourseTakes().iterator(); iter
                            .hasNext();) {
                        CourseTake take = (CourseTake) iter.next();
                        take.setTask(tasks[j]);
                    }
                }
            }
//            for(TeachTask t:tasks){
//                if(t.getSeqNo() == null || t.getSeqNo().isEmpty()){
//                	t.setSeqNo("xxx");
//                }
////                generator.genTaskSeqNo(task);
//            }
            taskDAO.saveOrUpdate(tasks);
            return tasks;
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#statTeachTask(java.lang.String[],
     *      com.shufe.dao.course.task.TeachTaskFilterCategory,
     *      com.shufe.model.system.calendar.TeachCalendar)
     */
    public int[] statTeachTask(String[] ids, TeachTaskFilterCategory category,
            TeachCalendar calendar) {
        return null;
    }
    
    /**
     * 同时更新坐在组的信息
     * 
     * @see com.shufe.service.course.task.TeachTaskService#removeTeachTask(java.lang.Long)
     */
    public void removeTeachTask(Long taskId) {
        if (null == taskId) {
            return;
        }
        TeachTask task = taskDAO.getTeachTask(taskId);
        taskDAO.removeTeachTask(task);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#removeTeachTask(com.shufe.model.course.task.TeachTask)
     */
    public void removeTeachTask(TeachTask task) {
        // 华政排课分配模块，不影响其它学校
        EntityQuery query = new EntityQuery(TaskInDepartment.class, "taskIn");
        query.add(new Condition("taskIn.calendar = :calendar", task.getCalendar()));
        String hql = "exists (from TaskInDepartment taskIn1 join taskIn1.tasks as task where taskIn1 = taskIn and task = :task)";
        query.add(new Condition(hql, task));
        Collection taskIns = utilService.search(query);
        if (CollectionUtils.isNotEmpty(taskIns)) {
            TaskInDepartment taskIn = (TaskInDepartment) taskIns.iterator().next();
            taskIn.getTasks().remove(task);
            utilService.saveOrUpdate(taskIn);
        }
        taskDAO.removeTeachTask(task);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#removeTeachTask(java.lang.Long[])
     */
    public void removeTeachTaskByIds(Long[] taskIds) {
        if (null == taskIds || taskIds.length < 1) {
            return;
        } else {
            for (int i = 0; i < taskIds.length; i++) {
                removeTeachTask(taskIds[i]);
            }
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#removeTeachTask(java.lang.String)
     */
    public void removeTeachTaskByIds(String taskIds) {
        if (StringUtils.isEmpty(taskIds)) {
            return;
        }
        removeTeachTaskByIds(SeqStringUtil.transformToLong(taskIds));
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#saveTeachTask(com.shufe.model.course.task.TeachTask)
     */
    public void saveTeachTask(TeachTask task) {
        saveOrUpdateTask(task);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#updateTeachTask(com.shufe.model.course.task.TeachTask)
     */
    public void updateTeachTask(TeachTask task) {
        saveOrUpdateTask(task);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#batchUpdateTeachTask(java.lang.String[],
     *      java.lang.String, java.lang.Object)
     */
    public void batchUpdateTeachTask(Long[] taskIds, String attr, Object value) {
        if (null == taskIds && taskIds.length < 1 || StringUtils.isBlank(attr)) {
            return;
        }
        taskDAO.updateTeachTaskByIds(attr, value, taskIds);
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#genTeachTask(java.lang.String[],com.shufe.model.system.calendar.TeachCalendar)
     */
    public int genTeachTasks(Long[] schemeIds, TeachCalendar calendar) {
        return genTeachTasks(schemeIds, calendar, null, TaskGenParams.getDefault());
    }
    
    /**
     * 生成教学任务
     */
    public int genTeachTasks(Long[] planIds, TeachCalendar calendar, TaskGenObserver observer,
            TaskGenParams params) {
        int count = 0;
        if (null != observer) {
            observer.notifyStart(observer.messageOf("info.taskGenInit.start") + "("
                    + planIds.length + ")", planIds.length, null);
        }
        TermCalculator termCalc = new TermCalculator(calendarService, calendar);
        for (int i = 0; i < planIds.length; i++) {
            TeachPlan plan = (TeachPlan) utilDao.load(TeachPlan.class, planIds[i]);
            // 是否自动计算学期
            int term = (null == params.getForTerm()) ? 0 : params.getForTerm().intValue();
            if (term == 0) {
                term = termCalc.getTerm(plan.getStdType(), plan.getEnrollTurn(), params
                        .getIsOmitSmallTerm());
            }
            debug("calculate terms for [" + new TeachCommon(plan).toString() + "] result:" + term);
            
            if (term < 1 || term > plan.getTermsCount().intValue()) {
                continue;
            }
            try {
                int countOfScheme = genTeachTasksForTerm(plan, calendar, observer, term, params);
                if (null != observer) {
                    observer.outputNotify(term, countOfScheme, plan);
                }
                count += countOfScheme;
            } catch (Exception e) {
                e.printStackTrace();
                observer.outputNotify(OutputWebObserver.error,
                        new OutputMessage("", e.getMessage()), true);
                info("Failure to gen Task for scheme id:" + planIds[i]);
            }
        }
        if (null != observer) {
            try {
                observer.notifyGenResult(planIds.length, count);
                observer.notifyFinish();
            } catch (Exception e) {
                info("Failure to gen Task for :" + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        return count;
    }
    
    /**
     * 生成具体学期的教学任务，并保存任务和更新计划
     * 
     * @param plan
     * @param calendar
     * @param term
     * @return
     */
    protected int genTeachTasksForTerm(TeachPlan plan, TeachCalendar calendar,
            TaskGenObserver observer, int term, TaskGenParams params) throws Exception {
        /*--------------------在必要时，删除已经生成的任务------------------*/
                if (params.isRemoveGenerated()) {
                    observer.outputNotify(term, plan, "info.plan.removeGenTask", false);
                    try {
                        taskDAO.removeTeachTaskOfPlan(plan, calendar);
                    } catch (Exception e) {
                        observer.outputNotify(term, plan, "info.plan.failure.removeGenTask", false);
                        return 0;
                    }
                }
        /*--------------------查询培养计划中的课程--------------------------------*/
        List planCourses = planCourseDAO.getPlanCourseByTerm(plan.getId(), new Integer(term));
        if (planCourses.isEmpty()) {
            info("Generate 0 task for No Course in Plan[" + plan.getId() + "] of term [" + term
                    + "]");
            return 0;
        }
        /*-------------------------查找对应培养计划的行政班级----------------------*/
        AdminClass exampleClass = new AdminClass();
        exampleClass.setEnrollYear(plan.getEnrollTurn());
        Object object = EntityUtils.getEntity(StudentType.class, plan.getStdType().getId());
        exampleClass.setStdType((StudentType) object);
        exampleClass.getDepartment().setId(plan.getDepartment().getId());
        exampleClass.getSpeciality().setId(
                (null == plan.getSpeciality()) ? null : plan.getSpeciality().getId());
        exampleClass.getAspect()
                .setId((null == plan.getAspect()) ? null : plan.getAspect().getId());
        List adminClasses = adminClassDAO.getAdminClassesExactly(exampleClass);
        /*----------如果不能忽略行政班级、并且行政班级为空，则直接返回-------------*/
        if (params.getIgnoreClass().equals(Boolean.FALSE) && adminClasses.isEmpty()) {
            info("Generate 0 task for No AdminClass correspond Plan[" + plan.getId()
                    + "] of term [" + term + "]");
            return 0;
        }
        ResettableIterator adminClassIterator = new ArrayListIterator(adminClasses.toArray());
        /*---------------------------指定要生成的课程-----------------------------*/
        Set courseCodeSet = new HashSet(utilService.load(Course.class, "code", StringUtils.split(
                params.getCourseCodeSeq(), ",")));
        /*-------------------------指定要生成的课程类别---------------------------*/
        Long[] courseTypeIds = SeqStringUtil.transformToLong(params.getCourseTypeIds());
        Set courseTypeSet = new HashSet(utilService.load(CourseType.class, "id", courseTypeIds));
        /*-----------------------------生成教学任务-------------------------------*/
        List tasks = new ArrayList();
        int count = 0;
        for (Iterator iter = planCourses.iterator(); iter.hasNext();) {
            PlanCourse planCourse = (PlanCourse) iter.next();
            // 检查是否该课程可以不生成
            if (StringUtils.contains(params.getOmmitedCourseCodeSeq(), ","
                    + planCourse.getCourse().getCode() + ",")) {
                continue;
            }
            if (StringUtils.isNotBlank(params.getOmmitedCourseName())) {
                if (StringUtils.contains(planCourse.getCourse().getName(), params
                        .getOmmitedCourseName())) {
                    continue;
                }
            }
            if (null == planCourse.getTeachDepart()) {
                debug("planCourse id:" + planCourse.getId() + " course name:"
                        + planCourse.getCourse().getName() + " in group:"
                        + planCourse.getCourseGroup().getId()
                        + " has no department.It will be  Ingored in generating TeachTask");
                continue;
            }
            if (CollectionUtils.isNotEmpty(courseCodeSet)
                    && !courseCodeSet.contains(planCourse.getCourse())) {
                continue;
            }
            if (CollectionUtils.isNotEmpty(courseTypeSet)
                    && !courseTypeSet.contains(planCourse.getCourseGroup().getCourseType())) {
                continue;
            }
            
            TeachTask task = null;
            if (!adminClasses.isEmpty()) {
                adminClassIterator.reset();
                while (adminClassIterator.hasNext()) {
                    count++;
                    AdminClass adminClass = (AdminClass) adminClassIterator.next();
                    task = genTeachTaskDetail(planCourse, calendar, plan, adminClass, params);
                    tasks.add(task);
                }
            } else {
                count++;
                task = genTeachTaskDetail(planCourse, calendar, plan, null, params);
                tasks.add(task);
            }
        }
        // 批量存储教学任务和更新培养计划
            taskDAO.saveGenResult(plan, tasks);
        return count;
    }
    
    /**
     * 产生一个教学任务
     * 
     * @param planCourse
     * @param calendar
     * @param plan
     * @param adminClass
     * @return
     */
    public TeachTask genTeachTaskDetail(PlanCourse planCourse, TeachCalendar calendar,
            TeachPlan plan, AdminClass adminClass, TaskGenParams params) {
        TeachTask task = TeachTask.getDefault();
        // 填充参数指认的值
        task.getRequirement().setRoomConfigType(params.getConfigType());
        if (null != planCourse.getCourse().getCategory()) {
            task.getRequirement().setCourseCategory(planCourse.getCourse().getCategory());
        }
        if (params.getUnitStatus().equals(new Integer(1))) {
            // 2.5学分算3节
            task.getArrangeInfo()
                    .setCourseUnits(
                            new Integer((int) Math.round(planCourse.getCourse().getCredits()
                                    .doubleValue())));
        } else {
            task.getArrangeInfo().setCourseUnits(params.getCourseUnits());
        }
        task.getArrangeInfo().setWeekStart(params.getWeekStart());
        task.getArrangeInfo().setWeeks(params.getWeeks());
        
        task.setCourse(planCourse.getCourse());
        task.setCourseType(planCourse.getCourseGroup().getCourseType());
        task.setCalendar(calendar);
        /*------------------设置默认安排信息--------------------------------*/
        ArrangeInfo arrangeInfo = task.getArrangeInfo();
        arrangeInfo.setTeachDepart(planCourse.getTeachDepart());
        arrangeInfo.setWeekUnits(planCourse.getCourse().getWeekHour());
        // modified 2005-4-13
        arrangeInfo.setOverallUnits(planCourse.getCourse().getExtInfo().getPeriod());
        if (null == arrangeInfo.getOverallUnits()) {
            arrangeInfo.calcOverallUnits();
        }
        if (null == arrangeInfo.getWeeks()) {
            arrangeInfo.setWeeks(calendar.getWeeks());
        }
        // TODO just for ignore null value
        if (null == arrangeInfo.getWeekUnits())
            arrangeInfo.setWeekUnits(new Float(arrangeInfo.getOverallUnits().intValue()
                    / calendar.getWeeks().intValue()));
        /*------------------设置教学班信息----------------------------------*/
        TeachClass teachClass = task.getTeachClass();
        teachClass.setDepart(plan.getDepartment());
        teachClass.setSpeciality(plan.getSpeciality());
        teachClass.setAspect(plan.getAspect());
        teachClass.setStdType(plan.getStdType());
        teachClass.setEnrollTurn(plan.getEnrollTurn());
        if (null != adminClass) {
            teachClass.getAdminClasses().add(adminClass);
            teachClass.setName(adminClass.getName());
            teachClass.setPlanStdCount(adminClass.getActualStdCount());
        } else {
            teachClass.setName(planCourse.getCourse().getName());
        }
        teachClass.processTaskForClass();
        /*-------------------设置选课信息------------------------------*/
        task.getElectInfo().setHSKDegree(null);

        /*-------------------设置生成和修改时间------------------------------*/
        task.setCreateAt(new Date(System.currentTimeMillis()));
        task.setModifyAt(new Date(System.currentTimeMillis()));
        task.setFromPlan(plan);
        return task;
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getDepartsOfTask(java.lang.String,
     *      java.lang.String, com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getDepartsOfTask(String stdTypeIdSeq, String departIdSeq, TeachCalendar calendar) {
        if (StringUtils.isNotEmpty(stdTypeIdSeq) && StringUtils.isNotEmpty(departIdSeq)
                && calendar.checkId()) {
            return taskDAO.getDepartsOfTask(SeqStringUtil.transformToLong(stdTypeIdSeq),
                    SeqStringUtil.transformToLong(departIdSeq), calendar);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachDepartsOfTask(java.lang.String,
     *      java.lang.String, com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachDepartsOfTask(String stdTypeIdSeq, String departIdSeq,
            TeachCalendar calendar) {
        if (StringUtils.isNotEmpty(stdTypeIdSeq) && StringUtils.isNotEmpty(departIdSeq)
                && calendar.checkId()) {
            return taskDAO.getTeachDepartsOfTask(SeqStringUtil.transformToLong(stdTypeIdSeq),
                    SeqStringUtil.transformToLong(departIdSeq), calendar);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
    
    public List getTeachDepartsOfTask(String stdTypeIdSeq, String departIdSeq, Collection calendars) {
        if (StringUtils.isNotEmpty(stdTypeIdSeq) && StringUtils.isNotEmpty(departIdSeq)
                && calendars.isEmpty()) {
            return taskDAO.getTeachDepartsOfTask(SeqStringUtil.transformToLong(stdTypeIdSeq),
                    SeqStringUtil.transformToLong(departIdSeq), calendars);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getCourseTypesOfTask(java.lang.String,
     *      java.lang.String, com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getCourseTypesOfTask(String stdTypeIdSeq, String departIdSeq, TeachCalendar calendar) {
        if (StringUtils.isNotEmpty(stdTypeIdSeq) && StringUtils.isNotEmpty(departIdSeq)
                && calendar.checkId())
            return taskDAO.getCourseTypesOfTask(SeqStringUtil.transformToLong(stdTypeIdSeq),
                    SeqStringUtil.transformToLong(departIdSeq), calendar);
        else
            return Collections.EMPTY_LIST;
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getTeachersOfTask(java.lang.String,
     *      java.lang.String, com.shufe.model.system.calendar.TeachCalendar)
     */
    public List getTeachersOfTask(String stdTypeIdSeq, String departIdSeq, TeachCalendar calendar) {
        if (StringUtils.isEmpty(stdTypeIdSeq) || StringUtils.isEmpty(departIdSeq)
                || !calendar.checkId()) {
            return Collections.EMPTY_LIST;
        } else {
            return taskDAO.getTeachersOfTask(SeqStringUtil.transformToLong(stdTypeIdSeq),
                    SeqStringUtil.transformToLong(departIdSeq), calendar);
        }
    }
    
    /**
     * @see com.shufe.service.course.task.TeachTaskService#getStdTypesForTeacher(com.shufe.model.system.baseinfo.Teacher)
     */
    public List getStdTypesForTeacher(Teacher teacher) {
        if (null == teacher) {
            return Collections.EMPTY_LIST;
        } else {
            return taskDAO.getStdTypesForTeacher(teacher);
        }
    }
    
    /**
     * 得到指定教学日历下的所有教学任务ID串
     * 
     * @param stdTypeIds
     * @param departIds
     * @param calendar
     * @return
     */
    public List getTeachTasksByCalendar(String stdTypeIds, String departIds, TeachCalendar calendar) {
        if (StringUtils.isEmpty(stdTypeIds) || StringUtils.isEmpty(departIds)
                || !ValidEntityPredicate.getInstance().evaluate(calendar)) {
            return Collections.EMPTY_LIST;
        } else {
            return taskDAO.getTeachTasksByCalendar(calendar, SeqStringUtil
                    .transformToLong(stdTypeIds), SeqStringUtil.transformToLong(departIds));
        }
        
    }
    
    public List getTaskOfCourseTypes(TeachCalendar calendar, String stdTypeIds, String departIds,
            Collection courseTypes) {
        Set courseTypeSet = new HashSet(courseTypes);
        Collection plans = teachPlanService.getTeachPlansOfActive(calendar, stdTypeIds, departIds,
                Boolean.TRUE);
        TermCalculator termCalc = new TermCalculator(calendarService, calendar);
        List taskOfCourseTypes = new LinkedList();
        long start = System.currentTimeMillis();
        for (Iterator iter = plans.iterator(); iter.hasNext();) {
            TeachPlan plan = (TeachPlan) iter.next();
            /*-------------------查找对应培养计划的行政班级----------------------------*/
            AdminClass exampleClass = new AdminClass();
            exampleClass.setEnrollYear(plan.getEnrollTurn());
            exampleClass.setStdType((StudentType) EntityUtils.getEntity(StudentType.class, plan
                    .getStdType().getId()));
            exampleClass.getDepartment().setId(plan.getDepartment().getId());
            exampleClass.getSpeciality().setId(
                    (null == plan.getSpeciality()) ? null : plan.getSpeciality().getId());
            exampleClass.getAspect().setId(
                    (null == plan.getAspect()) ? null : plan.getAspect().getId());
            List adminClasses = adminClassDAO.getAdminClassesExactly(exampleClass);
            // 小学期忽略掉了
            int term = 0;
            try {
                term = termCalc.getTerm(plan.getStdType(), plan.getEnrollTurn(), Boolean.TRUE);
            } catch (OnCampusTimeNotFoundException e) {
                continue;
            }
            if (term < 0 || term > plan.getTermsCount().intValue()) {
                continue;
            }
            
            for (Iterator iterator = plan.getCourseGroups().iterator(); iterator.hasNext();) {
                CourseGroup group = (CourseGroup) iterator.next();
                if (!courseTypeSet.contains(group.getCourseType())) {
                    continue;
                }
                if (!group.getPlanCourses().isEmpty()) {
                    continue;
                }
                String[] creditPerTerm = StringUtils.split(group.getCreditPerTerms(), ",");
                if (NumberUtils.toFloat(creditPerTerm[term - 1]) == 0) {
                    continue;
                }
                for (Iterator adminIter = adminClasses.iterator(); adminIter.hasNext();) {
                    AdminClass adminClass = (AdminClass) adminIter.next();
                    taskOfCourseTypes.add(new TaskOfCourseType(group.getCourseType(), adminClass,
                            Float.valueOf(creditPerTerm[term - 1])));
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("getTaskOfCourseTypes time is" + (System.currentTimeMillis() - start));
        }
        return taskOfCourseTypes;
    }
    
    public void batchUpdateArrangeInfo(Collection tasks, ArrangeInfo info) {
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (null != info.getWeeks()) {
                task.getArrangeInfo().setWeeks(info.getWeeks());
            }
            if (null != info.getWeekStart()) {
                task.getArrangeInfo().setWeekStart(info.getWeekStart());
            }
            if (null != info.getWeekCycle()) {
                task.getArrangeInfo().setWeekCycle(info.getWeekCycle());
            }
            if (null != info.getWeekUnits()) {
                task.getArrangeInfo().setWeekUnits(info.getWeekUnits());
            }
            if (null != info.getCourseUnits()) {
                task.getArrangeInfo().setCourseUnits(info.getCourseUnits());
            }
            if (null != info.getOverallUnits()) {
                task.getArrangeInfo().setOverallUnits(info.getOverallUnits());
            }
            task.getTeachClass().processTaskForClass();
        }
        utilDao.saveOrUpdate(tasks);
    }
    
    /**
     * 
     */
    public Collection copy(Collection tasks, TaskCopyParams params) {
        List copied = new ArrayList();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            // 任务不能同一个学期覆盖相同序号的自己
            if (task.getCalendar().getId().equals(params.getCalendar().getId())
                    && Boolean.TRUE.equals(params.getCopySeqNo())) {
                continue;
            }
            
            TeachTask copy = new TeachTask();
            boolean needAdd = true;
            // 拷贝课程序号
            if (Boolean.TRUE.equals(params.getCopySeqNo())) {
                copy.setSeqNo(task.getSeqNo());
                List exsits = utilDao.searchHQLQuery("from TeachTask where seqNo='"
                        + task.getSeqNo() + "' and calendar.id=" + params.getCalendar().getId());
                if (!exsits.isEmpty()) {
                    needAdd = false;
                    TeachTask existed = (TeachTask) exsits.get(0);
                    // 是否要删除目标任务
                    if (Boolean.TRUE.equals(params.getDeleteExistedTask())) {
                        utilDao.remove(existed);
                        needAdd = true;
                    } // 如果不删除任务,是否删除学生名单
                    else if (Boolean.TRUE.equals(params.getDeleteExistedCourseTakes())) {
                        Map deleteParams = new HashMap();
                        deleteParams.put("task.id", existed.getId());
                        utilService.remove(CourseTake.class, deleteParams);
                        existed.getTeachClass().setStdCount(new Integer(0));
                    }
                    // 如果任务没有删除,是否需要拷贝学生名单
                    if (!needAdd && Boolean.TRUE.equals(params.getCopyCourseTakes())) {
                        for (Iterator iterator = task.getTeachClass().getCourseTakes().iterator(); iterator
                                .hasNext();) {
                            CourseTake take = (CourseTake) iterator.next();
                            existed.getTeachClass().addCourseTake((CourseTake) take.clone());
                        }
                        utilDao.saveOrUpdate(existed);
                    }
                }
            }
            if (!needAdd) {
                continue;
            }
            copy.setArrangeInfo((ArrangeInfo) task.getArrangeInfo().clone());
            // 拷贝行政班
            copy.setTeachClass((TeachClass) task.getTeachClass().clone());
            copy.getTeachClass().getAdminClasses().addAll(task.getTeachClass().getAdminClasses());
            // 拷贝学生
            if (Boolean.TRUE.equals(params.getCopyCourseTakes())) {
                for (Iterator iterator = task.getTeachClass().getCourseTakes().iterator(); iterator
                        .hasNext();) {
                    CourseTake take = (CourseTake) iterator.next();
                    copy.getTeachClass().addCourseTake((CourseTake) take.clone());
                }
                task.getTeachClass().processTaskForClass();
            }
            copy.setRequirement((TaskRequirement) task.getRequirement().clone());
            copy.setElectInfo((TaskElectInfo) task.getElectInfo().clone());
            copy.getElectInfo().setIsElectable(Boolean.FALSE);
            copy.setCourse(task.getCourse());
            copy.setCourseType(task.getCourseType());
            copy.setExamMode(task.getExamMode());
            copy.setCalendar(params.getCalendar());
            copy.setRemark(task.getRemark());
            copy.setIsConfirm(Boolean.FALSE);
            copy.setQuestionnaire(null);
            copy.setTaskGroup(null);
            copy.setFromPlan(null);
            
            copy.setGradeState(new GradeState(copy));
            
            Date createAt = new Date(System.currentTimeMillis());
            copy.setCreateAt(createAt);
            copy.setModifyAt(createAt);
            copied.add(copy);
        }
        taskDAO.saveOrUpdate(copied);
        return copied;
    }
    
    public Map getTeachTaskDWR(String taskSeqNo, String stdTypeId, String year, String term) {
        if (!StringUtils.isEmpty(taskSeqNo) && !StringUtils.isEmpty(taskSeqNo)
                && !StringUtils.isEmpty(year) && !StringUtils.isEmpty(term)) {
            Long studentTypeId = new Long(stdTypeId);
            TeachCalendar calendar = calendarService.getTeachCalendar(studentTypeId, year, term);
            EntityQuery query = new EntityQuery(TeachTask.class, "task");
            query.add(new Condition("task.seqNo = (:seqNo)", taskSeqNo));
            query.add(new Condition("task.calendar.id = (:calendarId)", calendar.getId()));
            List list = (List) utilDao.search(query);
            if (list == null || list.isEmpty()) {
                return null;
            }
            TeachTask task = (TeachTask) (list).get(0);
            Map taskMap = new HashMap();
            taskMap.put("id", task.getId());
            taskMap.put("course.code", task.getCourse().getCode());
            taskMap.put("course.name", task.getCourse().getName());
            taskMap.put("task.gradeState.precision", task.getGradeState().getPrecision());
            taskMap
                    .put("task.gradeState.markStyle.id", task.getGradeState().getMarkStyle()
                            .getId());
            taskMap.put("task.gradeState.markStyle.name", task.getGradeState().getMarkStyle()
                    .getName());
            return taskMap;
        }
        return null;
    }
    
    public Collection getTeachTasksWithMultiClass(TeachTask task, String stdTypeIds,
            String departIds) {
        return taskDAO.getTeachTasksWithMultiClass(task, stdTypeIds, departIds);
    }
    
    public int statStdCount(TeachCalendar calendar) {
        return taskDAO.statStdCount(calendar);
    }
    
    public int statStdCount(String taskIdSeq) {
        return taskDAO.statStdCount(taskIdSeq);
    }
    
    /**
     * @param taskDAO
     *            The taskDAO to set.
     */
    public void setTeachTaskDAO(TeachTaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }
    
    public void setTaskSeqNoGenerator(TaskSeqNoGenerator generator) {
		this.generator = generator;
	}

	/**
     * @param teachCalendarService
     *            The teachCalendarService to set.
     */
    public void setCalendarService(TeachCalendarService calendarService) {
        this.calendarService = calendarService;
    }
    
    /**
     * @param adminClassDAO
     *            The adminClassDAO to set.
     */
    public void setAdminClassDAO(AdminClassDAO adminClassDAO) {
        this.adminClassDAO = adminClassDAO;
    }
    
    /**
     * @param planCourseDAO
     *            The planCourseDAO to set.
     */
    public void setPlanCourseDAO(PlanCourseDAO planCourseDAO) {
        this.planCourseDAO = planCourseDAO;
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
    public void saveOrUpdateTask(TeachTask task) {
        taskDAO.saveOrUpdateTask(prepareTask(task));
    }
    
    /**
     * @param task
     * @return
     */
    protected TeachTask prepareTask(TeachTask task) {
        if (null == task.getId()) {
            if (!task.selfCheck()) {
                return null;
            } else {
                Date createAt = new Date(System.currentTimeMillis());
                task.setCreateAt(createAt);
                task.setModifyAt(createAt);
                task.getTeachClass().reNameByClass();
            }
        } else {
            task.setModifyAt(new Date(System.currentTimeMillis()));
        }
        task.getTeachClass().processTaskForClass();
        return task;
    }
    
    public void saveOrUpdateTasks(Collection tasks) {
        for (Iterator it = tasks.iterator(); it.hasNext();) {
            TeachTask task = (TeachTask) it.next();
            if (null == prepareTask(task)) {
                continue;
            }
            task.getTeachClass().processTaskForClass();
            taskDAO.saveOrUpdateTask(task);
        }
    }
    
    public TeachTask getTeachTask(String seqNo, TeachCalendar calendar) {
        EntityQuery query = new EntityQuery(TeachTask.class, "task");
        query.add(new Condition("task.seqNo = :seqNo", seqNo));
        query.add(new Condition("task.calendar = :calendar", calendar));
        return (TeachTask) utilDao.search(query).iterator().next();
    }
}
