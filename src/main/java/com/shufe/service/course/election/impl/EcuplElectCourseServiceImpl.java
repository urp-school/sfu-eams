//$Id: ElectCourseServiceImpl.java,v 1.20 2007/01/16 03:41:58 duanth Exp $
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

package com.shufe.service.course.election.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.course.arrange.task.CourseTakeDAO;
import com.shufe.dao.course.election.ElectRecordDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.course.election.ElectRecord;
import com.shufe.model.course.election.ElectState;
import com.shufe.model.course.election.ElectStdScope;
import com.shufe.model.course.election.SimpleStdInfo;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.std.alteration.StudentAlteration;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.service.BasicService;
import com.shufe.service.course.election.ElectCourseService;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.quality.evaluate.EvaluateSwitchService;

/**
 * @author zhouqi
 * 
 */
public class EcuplElectCourseServiceImpl extends BasicService implements ElectCourseService {
    
    /** 港澳台的可不选课程 */
    protected static Set HMT_COURSE_IDS = new HashSet();
    
    protected TeachTaskDAO taskDAO;
    
    protected ElectRecordDAO electRecordDAO;
    
    protected CourseTakeDAO courseTakeDAO;
    
    protected EvaluateSwitchService evaluateSwitchService;
    
    protected TeachPlanService planService;
    
    protected TeachTaskService teachTaskService;
    
    protected GradeService gradeService;
    
    /** 指定港澳台可不选的课程 */
    static {
        HMT_COURSE_IDS.add(new Long(17));
        HMT_COURSE_IDS.add(new Long(18));
        HMT_COURSE_IDS.add(new Long(19));
        HMT_COURSE_IDS.add(new Long(109));
        HMT_COURSE_IDS.add(new Long(110));
        HMT_COURSE_IDS.add(new Long(111));
        HMT_COURSE_IDS.add(new Long(112));
        HMT_COURSE_IDS.add(new Long(156));
        HMT_COURSE_IDS.add(new Long(157));
        HMT_COURSE_IDS.add(new Long(360));
        HMT_COURSE_IDS.add(new Long(1227));
        HMT_COURSE_IDS.add(new Long(1228));
        HMT_COURSE_IDS.add(new Long(1961));
        
    }
    
    /**
     * o 查询已选的教学任务
     */
    public Pagination getElectableTasks(TeachTask task, ElectState state, Collection courseIds,
            TimeUnit time, boolean isScopeConstraint, int pageNo, int pageSize) {
        return taskDAO.getTeachTasksOfElectable(task, state, courseIds, time, isScopeConstraint,
                pageNo, pageSize);
    }
    
    public Pagination getElectableTasks(TeachTask task, TimeUnit time, Collection calendars,
            int pageNo, int pageSize) {
        if (pageNo > 0 && pageSize > 0)
            return taskDAO.getTeachTasksOfElectable(task, time, calendars, pageNo, pageSize);
        else
            return new Pagination(new Result(0, Collections.EMPTY_LIST));
    }
    
    /**
     * 选课
     * 
     * @param std
     * @param task
     * @param params
     * @return
     */
    public String elect(TeachTask task, ElectState state, CourseTakeType takeType) {
        SimpleStdInfo std = state.std;
        Student student = (Student) utilService.load(Student.class, std.getId());
        
        ElectParams params = state.params;
        // 检查 学生性别与课程要求上课学生性别 是否一致
        Gender gender = task.getTeachClass().getGender();
        if ((null != gender) && (!gender.getId().equals(student.getGender().getId()))) {
            return notGenderDistrict;
        }
        // 检查校区
        if (Boolean.TRUE.equals(params.getIsSchoolDistrictRestrict())
                && null != task.getArrangeInfo().getSchoolDistrict()) {
            if (null == student.getSchoolDistrict()
                    || !student.getSchoolDistrict().getId().equals(
                            task.getArrangeInfo().getSchoolDistrict().getId())) {
                return notInSchoolDistrict;
            }
        }
        // 1.检查评教
        if (params.getIsCheckEvaluation().equals(Boolean.TRUE)
                && state.isEvaluated.equals(Boolean.FALSE)) {
            if (!isPassEvaluation(std.getId())) {
                return notEvaluateComplete;
            } else {
                state.isEvaluated = Boolean.TRUE;
            }
        }
        // 2. 检查人数上限
        if (params.getIsOverMaxAllowed().equals(Boolean.FALSE)) {
            if (task.getTeachClass().getStdCount().intValue() + 1 > task.getElectInfo()
                    .getMaxStdCount().intValue()) {
                return overMaxStdCount;
            }
        }
        // 3.检查学分上限
        if (state.electedCredit + task.getCourse().getCredits().floatValue() > state.maxCredit) {
            return overCeilCreditConstraint;
        }
        
        // 4.是否已经选过该课
        Boolean coursePass = (Boolean) state.hisCourses.get(task.getCourse().getId());
        if (null != coursePass) {
            if (Boolean.FALSE.equals(params.getIsRestudyAllowed())) {
                return reStudiedNotAllowed;
            }
            // 选过的再选只能是"重修" 华政可重复修读已考核通过课程
            if (!takeType.getId().equals(CourseTakeType.RESTUDY)
                    && !takeType.getId().equals(CourseTakeType.REEXAM)) {
                return elected;
            }
        } else {
            takeType.setId(CourseTakeType.ELECTIVE);
        }
        // 5.是否重复选课
        if (state.electedCourseIds.contains(task.getCourse().getId())) {
            return elected;
        }
        
        // 6.检查学生范围
        boolean checkScope = true;
        if (null != coursePass) {
            checkScope = state.getParams().getIsCheckScopeForReSturdy().booleanValue();
        }
        if (checkScope) {
            boolean inScope = false;
            for (Iterator iter = task.getElectInfo().getElectScopes().iterator(); iter.hasNext();) {
                if (((ElectStdScope) iter.next()).isIn(std)) {
                    inScope = true;
                    break;
                }
            }
            if (!inScope) {
                return notInElectScope;
            }
        }
        // 5.检查HSK
        if (null != task.getElectInfo().getHSKDegree() && null != std.getHSKDegree()) {
            if (std.getHSKDegree().intValue() < task.getElectInfo().getHSKDegree().getDegree()
                    .intValue()) {
                return HSKNotSatisfied;
            }
        }
        if (null != task.getCourse().getLanguageAbility()) {
            if (!task.getCourse().getLanguageAbility().sameLevel(std.getLanguageAbility())) {
                return languageAbilityNotSatisfied;
            }
        }
        // 6.检查先修课程
        if (!task.getElectInfo().getPrerequisteCourses().isEmpty()) {
            for (Iterator iter = task.getElectInfo().getPrerequisteCourses().iterator(); iter
                    .hasNext();) {
                Course course = (Course) iter.next();
                Boolean isPass = (Boolean) state.hisCourses.get(course.getId());
                if (null == isPass) {
                    return prerequisteCoursesNotMeeted;
                } else if (Boolean.FALSE.equals(isPass) || gradeService.isPass(student, course)) {
                    return prerequisteCoursesNotMeeted;
                }
            }
        }
        // 9. 如果不是免修不免试的学生，均检查是否时间冲突
        if (!takeType.getId().equals(CourseTakeType.REEXAM)) {
            if (state.table.isTimeConflict(task)) {
                return timeCollision;
            }
        }
        // 10.替代课程&&培养计划
        if (params.getIsInPlanOfCourse().booleanValue()) {
            if (!isInPlan(task, state, params) && !isSubstitute(task, state)) {
                return courseNotInPlan;
            }
        }
        // 11.先选必修课
        // 20091217 段体华修改如下
        if (Boolean.FALSE.equals(task.getCourseType().getIsCompulsory())) {
            Collection s = CollectionUtils.subtract(state.compulsoryCourseIds,
                    state.electedCourseIds);
            for (Iterator iter = s.iterator(); iter.hasNext();) {
                Long courseId = (Long) iter.next();
                if (!state.hisCourses.keySet().contains(courseId)) {
                    return "error.elect.compulsoryFirst";
                }
            }
        }
        
        // 是否需要检测人数上限
        boolean checkMaxLimit = state.params.getIsOverMaxAllowed().equals(Boolean.FALSE);
        if (takeType.getId().equals(CourseTakeType.REEXAM)) {
            checkMaxLimit = false;
        }
        int rs = electRecordDAO.saveElection(task, takeType, state, checkMaxLimit);
        if (rs == 0) {
            state.electedCourseIds.add(task.getCourse().getId());
            state.table.addArrangement(task);
            state.electedCredit += task.getCourse().getCredits().floatValue();
            logger.debug("Elect Success std:" + state.getStd().getStdNo() + " task:" + task.getId()
                    + " credit:" + task.getCourse().getCredits() + " elected :"
                    + state.electedCredit);
            return selectSuccess;
        } else {
            logger.debug("Elect Failure(" + rs + ") std:" + state.getStd().getStdNo() + " task:"
                    + task.getId() + " credit:" + task.getCourse().getCredits() + " elected :"
                    + state.electedCredit);
            if (rs == -2) {
                return overCeilCreditConstraint;
            }
            // -3 or other is overMaxStdCount
            return overMaxStdCount;
        }
    }
    
    /**
     * @param task
     * @param state
     * @param params
     */
    protected boolean isInPlan(TeachTask task, ElectState state, ElectParams params) {
        if (state.getPlanCourses().isEmpty()) {
            return false;
        }
        Set planCourses = (Set) state.getPlanCourses().get(task.getCourseType());
        if (null == planCourses) {
            return isInPlanGroup(task, state);
        } else {
            if (planCourses.isEmpty()) {
                return true;
            } else {
                if (planCourses.contains(task.getCourse())) {
                    return true;
                }
                return isInPlanGroup(task, state);
            }
        }
    }
    
    /**
     * @param task
     * @param state
     * @return
     */
    protected boolean isInPlanGroup(TeachTask task, ElectState state) {
        for (Iterator it = state.getPlanCourses().values().iterator(); it.hasNext();) {
            Set courses = (Set) it.next();
            if (courses.contains(task.getCourse())) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean isSubstitute(TeachTask task, ElectState state) {
        return CollectionUtils.isNotEmpty(state.substituteIds)
                && state.substituteIds.contains(task.getCourse().getId());
    }
    
    public void addCompulsoryCourse(ElectState state, Student student, List calendars) {
        boolean isHMT = StringUtils.contains(student.getName(), "港澳台");
        if (!isHMT) {
            isHMT = StringUtils.contains(student.getName(), "留学生");
        }
        state.getStd().setHMT(isHMT);
        
        AdminClass adminClass = student.getFirstMajorClass();
        if (null != adminClass) {
            List adminClassTasks = teachTaskService.getTeachTasksByCategory(adminClass.getId(),
                    TeachTaskFilterCategory.ADMINCLASS, calendars);
            for (Iterator iter = adminClassTasks.iterator(); iter.hasNext();) {
                TeachTask task = (TeachTask) iter.next();
                if (task.getCourseType().getIsCompulsory().equals(Boolean.TRUE)) {
                    state.getCompulsoryCourseIds().add(task.getCourse().getId());
                }
            }
            // 过虑港澳台的可不选课程
            if (isHMT) {
                CollectionUtils.filter(state.getCompulsoryCourseIds(), new Predicate() {
                    
                    public boolean evaluate(Object arg0) {
                        return !HMT_COURSE_IDS.contains(arg0);
                    }
                });
            }
        }
    }
    
    /**
     * 退课
     */
    public String cancel(TeachTask task, ElectState state) {
        // 不能退没有选过的课程
        if (!state.getElectedCourseIds().contains(task.getCourse().getId())) {
            return cancelUnElected;
        }
        
        // 是否有转专业的记录 2010-02-25
        EntityQuery query = new EntityQuery(StudentAlteration.class, "stdAlteration");
        query.add(new Condition("stdAlteration.std.id = :stdId", state.getStd().getId()));
        query.add(new Condition("stdAlteration.mode.id = :alterModeId", new Long(24)));
        boolean isStdAlteration = CollectionUtils.isNotEmpty(utilService.search(query));
        
        if (!isStdAlteration) {
            ElectParams params = state.params;
            // 该课不允许退
            if (task.getElectInfo().getIsCancelable().equals(Boolean.FALSE)) {
                return courseIsNotCancelable;
            }
            ElectRecord record = electRecordDAO.getLatestElectRecord(task, state.std.getId());
            // 本轮退课
            CourseTake take = courseTakeDAO.getCourseTask(task.getId(), state.getStd().getId());
            if (params.getIsCancelAnyTime().equals(Boolean.FALSE)) {
                // 指定学生没有选课纪录
                // 如果前几轮中有选中的,则报告不允许自由退课
                if (null != record) {
                    if (Boolean.TRUE.equals(record.getIsPitchOn())
                            && record.getTurn().compareTo(params.getTurn()) < 0) {
                        return cancelCourseOfPreviousTurn;
                    }
                } else {
                    if (take.getCourseTakeType().getId().equals(CourseTakeType.COMPULSORY)) {
                        return "error.cancelElect.cancelAssigned";
                    }
                }
            } else {
                // 指定课程不能退课
                if (take.getCourseTakeType().getId().equals(CourseTakeType.COMPULSORY)) {
                    return "error.cancelElect.cancelAssigned";
                }
                // 非本轮所退之课，不在指定范围之内
                if (null != record
                        && Boolean.TRUE.equals(record.getIsPitchOn())
                        && record.getTurn().compareTo(params.getTurn()) < 0
                        && !params.getNotCurrentCourseTypes().contains(
                                record.getTask().getCourseType())) {
                    return cancelCourseOfPointTurn;
                }
            }
        }
        // 依据参数检查人数下限
        int rs = electRecordDAO.removeElection(task, state);
        if (rs == 0) {
            state.electedCredit -= task.getCourse().getCredits().floatValue();
            state.table.removeArrangement(task);
            state.electedCourseIds.remove(task.getCourse().getId());
            logger.debug("Withdraw Success std:" + state.getStd().getStdNo() + " task "
                    + task.getId() + " credit: " + task.getCourse().getCredits() + " elected: "
                    + state.electedCredit);
            return cancelSuccess;
        } else {
            logger.debug("Withdraw Failure(" + rs + ") std:" + state.getStd().getStdNo() + " task "
                    + task.getId() + " credit: " + task.getCourse().getCredits() + " elected: "
                    + state.electedCredit);
            if (rs == -2) {
                return cancelUnElected;
            }
            if (rs == -3) {
                return underMinStdCount;
            }
            // TODO define unknown error
            return underMinStdCount;
        }
    }
    
    public boolean isPassEvaluation(Long stdId) {
        List courseTakeList = courseTakeDAO.getCourseTakeIdsNeedEvaluate(stdId,
                evaluateSwitchService.getOpenCalendars(false));
        return courseTakeList.isEmpty();
    }
    
    /**
     * @param taskDAO
     *            The taskDAO to set.
     */
    public void setTeachTaskDAO(TeachTaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }
    
    public void setElectRecordDAO(ElectRecordDAO electRecordDAO) {
        this.electRecordDAO = electRecordDAO;
    }
    
    public void setCourseTakeDAO(CourseTakeDAO courseTakeDAO) {
        this.courseTakeDAO = courseTakeDAO;
    }
    
    public void setEvaluateSwitchService(EvaluateSwitchService evaluateSwitchService) {
        this.evaluateSwitchService = evaluateSwitchService;
    }
    
    public void setTaskDAO(TeachTaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }
    
    public void setPlanService(TeachPlanService planService) {
        this.planService = planService;
    }
    
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
}
