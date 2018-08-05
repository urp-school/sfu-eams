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
 * chaostone             2006-12-13            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.grade.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.ConditionUtils;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.dao.BaseCodeDao;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.ExamStatus;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.security.User;
import com.shufe.dao.course.arrange.task.CourseTakeDAO;
import com.shufe.dao.course.grade.GradeDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.model.course.arrange.exam.ExamTake;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.ExamGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.grade.gp.GradePointRule;
import com.shufe.model.course.grade.stat.CourseSegStat;
import com.shufe.model.course.grade.stat.TaskSegStat;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.BasicService;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointRuleService;
import com.shufe.service.course.plan.TeachPlanService;

/**
 * 成绩服务实现类
 * 
 * @author chaostone
 * 
 */
public class GradeServiceImpl extends BasicService implements GradeService {
    
    protected GradeDAO gradeDAO;
    
    protected TeachTaskDAO teachTaskDAO;
    
    protected BaseCodeDao baseCodeDao;
    
    protected GradePointRuleService gradePointRuleService;
    
    protected TeachPlanService teachPlanService;
    
    protected CourseTakeDAO courseTakeDAO;
    
//    protected GradeLogService gradeLogService;
    
    public boolean needReStudy(Student std, Course course) {
        return gradeDAO.needReStudy(std, course);
    }
    
    public boolean isPass(Student std, Course course) {
        return gradeDAO.isPass(std, course);
    }
    
    public Map getGradeCourseMap(Long stdId) {
        if (null != stdId)
            return gradeDAO.getGradeCourseMap(stdId);
        else
            return Collections.EMPTY_MAP;
    }
    
    public List getCourseGrades(TeachTask task) {
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "courseGrade");
        entityQuery.add(new Condition("courseGrade.task.id=:taskId", task.getId()));
        return (List) utilDao.search(entityQuery);
    }
    
    public List getCourseGrades(Collection tasks) {
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "courseGrade");
        entityQuery.add(new Condition("courseGrade.task in (:tasks)", tasks));
        return (List) utilDao.search(entityQuery);
    }
    
    public List statTask(String taskIdSeq, List scoreSegments, List gradeTypes, Teacher teacher) {
        Collection tasks = teachTaskDAO
                .getTeachTasksByIds(SeqStringUtil.transformToLong(taskIdSeq));
        
        List stats = new ArrayList();
        for (Iterator iter = tasks.iterator(); iter.hasNext();) {
            TeachTask task = (TeachTask) iter.next();
            if (null == teacher || task.getArrangeInfo().getTeachers().contains(teacher)) {
                List grades = getCourseGrades(task, gradeTypes);
                TaskSegStat stat = new TaskSegStat(task, teacher, grades);
                stat.setScoreSegments(scoreSegments);
                stat.stat(gradeTypes);
                stats.add(stat);
            }
        }
        return stats;
    }
    
    public List statCourse(List courses, List scoreSegments, List gradeTypes, TeachCalendar calendar) {
        List stats = new ArrayList();
        for (Iterator iter = courses.iterator(); iter.hasNext();) {
            Course course = (Course) iter.next();
            List grades = getCourseGrades(course, calendar);
            CourseSegStat stat = new CourseSegStat(course, calendar, grades);
            stat.setScoreSegments(scoreSegments);
            stat.stat(gradeTypes);
            stats.add(stat);
        }
        return stats;
    }
    
    public List getCourseGrades(CourseGrade gradeExample) {
        gradeExample.setCreateAt(null);
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "grade");
        entityQuery.getConditions().addAll(ConditionUtils.extractConditions("grade", gradeExample));
        return (List) utilDao.search(entityQuery);
    }
    
    public List getCourseGradesWithoutChinese(CourseGrade gradeExample) {
        gradeExample.setCreateAt(null);
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "grade");
        entityQuery.add(new Condition("grade.markStyle.id <>5"));
        entityQuery.getConditions().addAll(ConditionUtils.extractConditions("grade", gradeExample));
        return (List) utilDao.search(entityQuery);
    }
    
    public List getCourseGradesWithoutChineseForStd(Student std) {
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "grade");
        entityQuery.add(new Condition("grade.markStyle.id <>5"));
        entityQuery.add(new Condition("grade.std.id =" + std.getId()));
        return (List) utilDao.search(entityQuery);
    }
    
    public List getCourseGrades(TeachCalendar calendar, Collection stds, MajorType majorType,
            Boolean isPublish) {
        if (stds.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "grade");
        entityQuery.add(new Condition("grade.calendar.id=" + calendar.getId()));
        entityQuery.add(new Condition("grade.std  in(:stds)", stds));
        if (null != majorType) {
            entityQuery.add(new Condition("grade.majorType=:majorType", majorType));
        }
        if (Boolean.TRUE.equals(isPublish)) {
            entityQuery.add(new Condition("grade.status = 2"));
        }
        return (List) utilDao.search(entityQuery);
    }
    
    public List getGradeTypesOfExam(TeachTask task) {
        EntityQuery entityQuery = new EntityQuery(ExamGrade.class, "grade");
        entityQuery.add(new Condition("grade.courseGrade.task.id=" + task.getId()));
        entityQuery.setSelect("select distinct grade.gradeType");
        return (List) utilDao.search(entityQuery);
    }
    
    /**
     * 得到可以输入的成绩类型
     * 
     * @param state
     * @return
     */
    public List getGradeTypes(GradeState state) {
        List gradeTypeInPercent = state.getGradeTypeInPercent();
        List canInputTypes = null;
        if (!gradeTypeInPercent.isEmpty()) {
            canInputTypes = new ArrayList();
            for (Iterator iter = gradeTypeInPercent.iterator(); iter.hasNext();) {
                GradeType gradeType = (GradeType) iter.next();
                gradeType = (GradeType) baseCodeDao.getCode(GradeType.class, gradeType.getId());
                canInputTypes.add(gradeType);
            }
            Collections.sort(canInputTypes, new BeanComparator("priority"));
        } else {
            GradeType gradeType = new GradeType();
            gradeType.setTeacherCanInputGrade(Boolean.TRUE);
            canInputTypes = (List) baseCodeDao.getCodes(gradeType);
        }
        
        return canInputTypes;
    }
    
    public List getCourseGrades(Long stdId, MajorType majorType, Boolean published, Boolean isPass) {
        EntityQuery query = new EntityQuery(CourseGrade.class, "grade");
        query.add(new Condition("grade.std.id=" + stdId));
        if (null != majorType) {
            query.add(new Condition("grade.majorType=:majorType", majorType));
        }
        if (null != published) {
            query.add(new Condition("bitand(grade.status," + Grade.PUBLISHED + ")>0"));
        }
        if (null != isPass) {
            query.add(new Condition("grade.isPass=:isPass", isPass));
        }
        return (List) utilDao.search(query);
    }
    
    public void removeExamGrades(TeachTask task, GradeType gradeType, User user) {
        gradeDAO.removeExamGrades(task, gradeType, user);
        task.getGradeState().removeConfirm(gradeType.getMark().intValue());
        GradeType gAGradeType = (GradeType) baseCodeDao.getCode(GradeType.class, GradeType.GA);
        task.getGradeState().removeInput(gradeType.getMark().intValue());
        task.getGradeState().removeConfirm(gradeType.getMark().intValue());
        task.getGradeState().removeInput(gAGradeType.getMark().intValue());
        task.getGradeState().removeConfirm(gAGradeType.getMark().intValue());
        utilDao.saveOrUpdate(task.getGradeState());
        reCalcGrade(task, null);
    }
    public void removeExamGrades(TeachTask task,GradeType gradeType){
    	removeExamGrades(task, gradeType, null);
    }
    public void removeGrades(TeachTask task){
    	removeGrades(task, null);
    }
    public void removeGrades(TeachTask task, User user) {
        gradeDAO.removeGrades(task, user);
        task.getGradeState().setInputStatus(GradeState.INIT);
        task.getGradeState().setConfirmStatus(GradeState.INIT);
        task.getGradeState().setPublishStatus(GradeState.INIT);
        task.getGradeState().setPercents(null);
        utilDao.saveOrUpdate(task.getGradeState());
        reCalcGrade(task, null);
    }
    
    /**
     * 计算教师录入成绩时，对于一种特定的成绩类型，现在的应该录入次序
     * 
     * @param task
     * @param grades
     * @param type
     * @return
     */
    public Integer calcEnrolTime(TeachTask task, GradeType gradeType) {
        GradeState state = task.getGradeState();
        if (state.isConfirmed(gradeType)) {
            return new Integer(3);
        }
        if (state.isInput(gradeType)) {
            return new Integer(2);
        } else
            return new Integer(1);
    }
    
    public void reCalcGrade(TeachTask task, User user) {
        if (null == task.getGradeState()) {
            return;
        }
        Collection tasks = new ArrayList();
        tasks.add(task);
        utilDao.saveOrUpdate(reCalcGrade(tasks, user));
    }
    
    public List reCalcGrade(Collection tasks, User user) {
        if (CollectionUtils.isEmpty(tasks)) {
            return Collections.EMPTY_LIST;
        }
        Map taskMap = new HashMap();
        for (Iterator it = tasks.iterator(); it.hasNext();) {
            TeachTask task = (TeachTask) it.next();
            taskMap.put(task.getId(), task.getGradeState().getMarkStyle());
        }
        List grades = getCourseGrades(tasks);
        for (Iterator iter = grades.iterator(); iter.hasNext();) {
            CourseGrade grade = (CourseGrade) iter.next();
            // 设置新的成绩记录方式
            grade.setMarkStyle((MarkStyle) taskMap.get(grade.getTask().getId()));
            grade.calcGA();
            grade.calcScore();
            grade.updatePass();
            // 计算平均绩点
            GradePointRule rule = gradePointRuleService.getGradePointRule(grade.getStd().getType(),
                    grade.getMarkStyle());
            if (null != rule) {
                grade.setGP(rule.calcGP(grade));
            }
        }
        // 日志的是某门课刚刚开始录成绩
        if (null != user) {
//            grades.addAll(gradeLogService.buildGradeCatalogInfo(user, grades, null, null));
        }
        return grades;
    }
    
    public void publishGrade(String taskIdSeq, GradeType gradeType, Boolean isPublished) {
        Long[] taskIds = SeqStringUtil.transformToLong(taskIdSeq);
        List gradeStates = new ArrayList();
        if (null == gradeType) {
            List gradeTypes = baseCodeDao.getCodes(GradeType.class);
            int mark = 0;
            if (Boolean.TRUE.equals(isPublished)) {
                for (Iterator iter = gradeTypes.iterator(); iter.hasNext();) {
                    GradeType elem = (GradeType) iter.next();
                    mark += elem.getMark().intValue();
                }
            }
            for (int i = 0; i < taskIds.length; i++) {
                TeachTask task = (TeachTask) utilDao.get(TeachTask.class, taskIds[i]);
                gradeDAO.publishCourseGrade(task, isPublished);
                gradeDAO.publishExamGrade(task, gradeTypes, isPublished);
                task.getGradeState().setPublishStatus(new Integer(mark));
                gradeStates.add(task.getGradeState());
            }
        } else {
            for (int i = 0; i < taskIds.length; i++) {
                TeachTask task = (TeachTask) utilDao.get(TeachTask.class, taskIds[i]);
                if (gradeType.getId().equals(GradeType.FINAL)
                        || gradeType.getId().equals(GradeType.GA)) {
                    gradeDAO.publishCourseGrade(task, isPublished);
                } else {
                    gradeDAO.publishExamGrade(task, gradeType, isPublished);
                }
                if (Boolean.TRUE.equals(isPublished)) {
                    task.getGradeState().addPublish(gradeType.getMark().intValue());
                } else {
                    task.getGradeState().removePublish(gradeType.getMark().intValue());
                }
                gradeStates.add(task.getGradeState());
            }
        }
        utilDao.saveOrUpdate(gradeStates);
    }
    
    public CourseGrade getCourseGrade(String stdCode, TeachCalendar calendar, String courseCode,
            Long gradeTypeId) {
        CourseGrade grade = new CourseGrade();
        // 1.查找成绩类型
        GradeType gradeType = (GradeType) baseCodeDao.getCode(GradeType.class, gradeTypeId);
        grade.setStatus(new Integer(Grade.PUBLISHED));
        grade.setCalendar(calendar);
        grade.setMajorType(new MajorType(MajorType.FIRST));
        // 2.查找课程
        List courses = utilService.load(Course.class, "code", courseCode);
        Course course = null;
        if (courses.size() == 1) {
            course = (Course) courses.get(0);
        }
        if (null == course) {
            return grade;
        }
        // 3.查找学生
        EntityQuery stdQuery = new EntityQuery(Student.class, "std");
        stdQuery.add(new Condition("std.code=:code", stdCode));
        List stds = (List) utilService.search(stdQuery);
        if (stds.isEmpty() || stds.size() != 1) {
            return grade;
        }
        grade.setStd((Student) stds.get(0));
        // 4.查找已有成绩
        EntityQuery gradeQuery = new EntityQuery(CourseGrade.class, "grade");
        gradeQuery.add(new Condition("grade.calendar.id=" + calendar.getId()));
        gradeQuery.add(new Condition("grade.std.id=" + grade.getStd().getId()));
        gradeQuery.add(new Condition("grade.course.code=:courseCode", courseCode));
        
        List grades = (List) utilService.search(gradeQuery);
        if (grades.size() > 0) {
            grade = (CourseGrade) grades.get(0);
        }
        if (null != grade.getExamGrade(gradeType)) {
            return grade;
        } else {
            // 查找上课记录
            List takes = Collections.EMPTY_LIST;
            EntityQuery takeQuery = new EntityQuery(CourseTake.class, "take");
            takeQuery.add(new Condition("take.task.course.code=:courseCode", courseCode));
            takeQuery.add(new Condition("take.student.id=" + grade.getStd().getId()));
            takeQuery.add(new Condition("take.task.calendar.id=" + calendar.getId()));
            takes = (List) utilService.search(takeQuery);
            
            if (takes.size() == 1) {
                CourseTake take = (CourseTake) takes.get(0);
                if (grade.isPO()) {
                    grade.setCourseTake(take);
                } else {
                    grade.setCourseTakeType(take.getCourseTakeType());
                    grade.setCourseTake(take);
                    grade.setTask(take.getTask());
                    grade.setCourse(take.getTask().getCourse());
                    grade.setTaskSeqNo(take.getTask().getSeqNo());
                    grade.setCourseType(take.getTask().getCourseType());
                    if (Boolean.TRUE.equals(course.getIs2ndSpeciality())) {
                        grade.setMajorType(new MajorType(MajorType.SECOND));
                    }
                }
            } else {
                grade.setCourse(course);
                grade.setCourseTakeType((CourseTakeType) baseCodeDao.getCode(CourseTakeType.class,
                        CourseTakeType.ELECTIVE));
                grade.setCourseType((CourseType) baseCodeDao.getCode(CourseType.class,
                        CourseType.PUBLIC_COURSID));
            }
            if (!grade.isPO()) {
                // 依据计划查找课程类别和学分
                CourseType planCourseType = teachPlanService.getCourseTypeOfStd(grade.getStd()
                        .getCode(), (com.shufe.model.system.baseinfo.Course) grade.getCourse(),
                        grade.getCourseType(), grade.getMajorType());
                grade.setCourseType(planCourseType);
                grade.setCredit(course.getCredits());
            }
            // 判断考试情况
            ExamGrade examGrade = new ExamGrade();
            examGrade.setGradeType(gradeType);
            examGrade.setStatus(new Integer(Grade.CONFIRMED));
            examGrade.setExamStatus((ExamStatus) baseCodeDao.getCode(ExamStatus.class,
                    ExamStatus.NORMAL));
            if (null != grade.getTask()) {
                EntityQuery examTakeQuery = new EntityQuery(ExamTake.class, "examTake");
                examTakeQuery.add(new Condition("examTake.calendar.id=" + calendar.getId()));
                examTakeQuery.add(new Condition("examTake.courseTake.student.id="
                        + grade.getStd().getId()));
                examTakeQuery.add(new Condition("examTake.examType.id="
                        + gradeType.getExamType().getId()));
                examTakeQuery.add(new Condition("examTake.courseTake.task.id="
                        + grade.getTask().getId()));
                List examTakes = (List) utilService.search(examTakeQuery);
                if (!examTakes.isEmpty()) {
                    ExamTake take = (ExamTake) examTakes.get(0);
                    examGrade.setExamStatus(take.getExamStatus());
                }
            }
            grade.addExamGrade(examGrade);
        }
        return grade;
    }
    
    public void linkGradeAndTakeAsPossible(TeachTask task) {
        EntityQuery query = new EntityQuery(CourseGrade.class, "grade");
        query.add(new Condition("grade.task=:task", task));
        query
                .add(new Condition(
                        "exists( from CourseTake take where take.task=:task and take.student=grade.std and take.courseGrade is null)",
                        task));
        
        List rs = (List) utilDao.search(query);
        List takes = new ArrayList();
        for (Iterator iter = rs.iterator(); iter.hasNext();) {
            CourseGrade grade = (CourseGrade) iter.next();
            CourseTake take = courseTakeDAO.getCourseTask(grade.getTask().getId(), grade.getStd()
                    .getId());
            if (null != take) {
                take.setCourseGrade(grade);
                takes.add(take);
            }
        }
        utilDao.saveOrUpdate(takes);
    }
    
    public Boolean hasSpeciality2ndGrade(Student std) {
        EntityQuery query = new EntityQuery(CourseGrade.class, "grade");
        query.add(new Condition("grade.majorType=:majorType and grade.std.id=:stdId",
                new MajorType(MajorType.SECOND), std.getId()));
        Collection rs = utilDao.search(query);
        return Boolean.valueOf(!rs.isEmpty());
    }
    
    public Float getCredit(String stdCode) {
        EntityQuery query = new EntityQuery(CourseGrade.class, "grade");
        query.add(Condition.eq("grade.std.code", stdCode));
        query.add(new Condition("grade.isPass=true"));
        query.setSelect(" sum(grade.credit)");
        List rs = (List) utilDao.search(query);
        if (rs.isEmpty()) {
            return new Float(0);
        } else {
            if (null == rs.get(0)) {
                return new Float(0);
            } else {
                return new Float(((Number) rs.get(0)).floatValue());
            }
        }
    }
    
    public Float getDegreeCredit(String stdCode) {
        EntityQuery query = new EntityQuery(CourseGrade.class, "grade");
        query.add(Condition.eq("grade.std.code", stdCode));
        query.add(new Condition("grade.isPass=true and courseType.isDegree=true"));
        query.setSelect(" sum(grade.credit)");
        List rs = (List) utilDao.search(query);
        if (rs.isEmpty()) {
            return new Float(0);
        } else {
            if (null == rs.get(0)) {
                return new Float(0);
            } else {
                return new Float(((Number) rs.get(0)).floatValue());
            }
        }
    }
    
    /**
     * 按照任务\成绩类型查找成绩
     * 
     * @param task
     * @param gradeTypes
     * @return
     */
    private List getCourseGrades(TeachTask task, List gradeTypes) {
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "courseGrade");
        entityQuery.add(new Condition("courseGrade.task.id=:taskId", task.getId()));
        entityQuery.add(new Condition("exists(from ExamGrade grade "
                + "where grade.courseGrade.id=courseGrade.id "
                + "and grade.gradeType in(:gradeType))", gradeTypes));
        List rs = (List) utilDao.search(entityQuery);
        if (rs.isEmpty()
                && (gradeTypes.contains(new GradeType(GradeType.GA)) || gradeTypes
                        .contains(new GradeType(GradeType.FINAL)))) {
            return getCourseGrades(task);
        } else {
            return rs;
        }
    }
    
    /**
     * 按照课程查找成绩
     * 
     * @param course
     * @return
     */
    private List getCourseGrades(Course course, TeachCalendar calendar) {
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "courseGrade");
        entityQuery.add(new Condition("courseGrade.course=:course", course));
        entityQuery.add(new Condition("courseGrade.calendar=:calendar", calendar));
        return (List) utilDao.search(entityQuery);
    }
    
    public void setGradeDAO(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }
    
    public void setTeachTaskDAO(TeachTaskDAO teachTaskDAO) {
        this.teachTaskDAO = teachTaskDAO;
    }
    
    public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
        this.baseCodeDao = baseCodeDao;
    }
    
    public void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
    
    public void setTeachPlanService(TeachPlanService teachPlanService) {
        this.teachPlanService = teachPlanService;
    }
    
    public void setCourseTakeDAO(CourseTakeDAO courseTakeDAO) {
        this.courseTakeDAO = courseTakeDAO;
    }
    
    public String getScoreDisplay(Float score, Long markStyleId) {
        if (null == score || null == markStyleId) {
            return null;
        }
        MarkStyle markStyle = (MarkStyle) utilService.load(MarkStyle.class, markStyleId);
        if (null == markStyle) {
            return null;
        }
        return ConverterFactory.getConverter().convert(score, markStyle);
    }
    
    public List getDegreeCourseGrade(Long stdId, Long degreeCourseId) {
        List degreeCourseGradeList = new ArrayList();
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "courseGrade");
        entityQuery.add(new Condition("courseGrade.course.id=:degreeCourseId", degreeCourseId));
        entityQuery.add(new Condition("courseGrade.std.id=:stdId", stdId));
        entityQuery.setSelect("select max(courseGrade.GA)");
        degreeCourseGradeList = (List) utilDao.search(entityQuery);
        return degreeCourseGradeList;
    }
    
    public List getCourseGradeOfDegree(Long stdId, Long degreeCourseId) {
        List degreeCourseGradeList = new ArrayList();
        EntityQuery entityQuery = new EntityQuery(CourseGrade.class, "courseGrade");
        entityQuery.add(new Condition("courseGrade.course.id=:degreeCourseId", degreeCourseId));
        entityQuery.add(new Condition("courseGrade.std.id=:stdId", stdId));
        degreeCourseGradeList = (List) utilDao.search(entityQuery);
        return degreeCourseGradeList;
    }
    
    public List getGradeRank(String courseSeq, String courseName, String yearfrom, String termfrom,
            String yearto, String termto, String searchType, Long departmentId) {
        List list = new ArrayList();
        EntityQuery queryA = new EntityQuery(CourseGrade.class, "grade");
        if (null != departmentId) {
            if (searchType.equals("A")) {
                queryA.add(new Condition("grade.std.department.id =" + departmentId));
            } else if (searchType.equals("B")) {
                queryA.add(new Condition("grade.task.arrangeInfo.teachDepart.id =" + departmentId));
            }
            
        }
        if (null != courseName && !"".equals(courseName)) {
            queryA.add(new Condition("grade.course.name ='" + courseName + "'"));
        }
        if (null != courseSeq && !"".equals(courseSeq)) {
            queryA.add(new Condition("grade.taskSeqNo ='" + courseSeq + "'"));
        }
        if (null != yearfrom && null != termfrom) {
            queryA.add(new Condition("grade.calendar.year||grade.calendar.term >='" + yearfrom
                    + termfrom + "'"));
        }
        if (null != yearto && null != termto) {
            queryA.add(new Condition("grade.calendar.year||grade.calendar.term <='" + yearto
                    + termto + "'"));
        }
        queryA.setSelect("select count(*)");
        queryA.add(new Condition("grade.score<=100 and grade.score>=85"));
        List listA = (List) utilService.search(queryA);
        Long countA = (Long) listA.get(0);
        list.add(countA);
        
        EntityQuery queryB = new EntityQuery(CourseGrade.class, "grade");
        if (null != departmentId) {
            if (searchType.equals("A")) {
                queryB.add(new Condition("grade.std.department.id =" + departmentId));
            } else if (searchType.equals("B")) {
                queryB.add(new Condition("grade.task.arrangeInfo.teachDepart.id =" + departmentId));
            }
        }
        if (null != courseName && !"".equals(courseName)) {
            queryB.add(new Condition("grade.course.name ='" + courseName + "'"));
        }
        if (null != courseSeq && !"".equals(courseSeq)) {
            queryB.add(new Condition("grade.taskSeqNo ='" + courseSeq + "'"));
        }
        if (null != yearfrom && null != termfrom) {
            queryB.add(new Condition("grade.calendar.year||grade.calendar.term >='" + yearfrom
                    + termfrom + "'"));
        }
        if (null != yearto && null != termto) {
            queryB.add(new Condition("grade.calendar.year||grade.calendar.term <='" + yearto
                    + termto + "'"));
        }
        queryB.setSelect("select count(*)");
        queryB.add(new Condition("grade.score>=75 and grade.score<85"));
        List listB = (List) utilService.search(queryB);
        Long countB = (Long) listB.get(0);
        list.add(countB);
        
        EntityQuery queryC = new EntityQuery(CourseGrade.class, "grade");
        if (null != departmentId) {
            if (searchType.equals("A")) {
                queryC.add(new Condition("grade.std.department.id =" + departmentId));
            } else if (searchType.equals("B")) {
                queryC.add(new Condition("grade.task.arrangeInfo.teachDepart.id =" + departmentId));
            }
        }
        if (null != courseName && !"".equals(courseName)) {
            queryC.add(new Condition("grade.course.name ='" + courseName + "'"));
        }
        if (null != courseSeq && !"".equals(courseSeq)) {
            queryC.add(new Condition("grade.taskSeqNo ='" + courseSeq + "'"));
        }
        if (null != yearfrom && null != termfrom) {
            queryC.add(new Condition("grade.calendar.year||grade.calendar.term >='" + yearfrom
                    + termfrom + "'"));
        }
        if (null != yearto && null != termto) {
            queryC.add(new Condition("grade.calendar.year||grade.calendar.term <='" + yearto
                    + termto + "'"));
        }
        queryC.setSelect("select count(*)");
        queryC.add(new Condition("grade.score>=62 and grade.score<75"));
        List listC = (List) utilService.search(queryC);
        Long countC = (Long) listC.get(0);
        list.add(countC);
        
        EntityQuery queryD = new EntityQuery(CourseGrade.class, "grade");
        if (null != departmentId) {
            if (searchType.equals("A")) {
                queryD.add(new Condition("grade.std.department.id =" + departmentId));
            } else if (searchType.equals("B")) {
                queryD.add(new Condition("grade.task.arrangeInfo.teachDepart.id =" + departmentId));
            }
        }
        if (null != courseName && !"".equals(courseName)) {
            queryD.add(new Condition("grade.course.name ='" + courseName + "'"));
        }
        if (null != courseSeq && !"".equals(courseSeq)) {
            queryD.add(new Condition("grade.taskSeqNo ='" + courseSeq + "'"));
        }
        if (null != yearfrom && null != termfrom) {
            queryD.add(new Condition("grade.calendar.year||grade.calendar.term >='" + yearfrom
                    + termfrom + "'"));
        }
        if (null != yearto && null != termto) {
            queryD.add(new Condition("grade.calendar.year||grade.calendar.term <='" + yearto
                    + termto + "'"));
        }
        queryD.setSelect("select count(*)");
        queryD.add(new Condition("grade.score>=60 and grade.score<62"));
        List listD = (List) utilService.search(queryD);
        Long countD = (Long) listD.get(0);
        list.add(countD);
        
        EntityQuery queryE = new EntityQuery(CourseGrade.class, "grade");
        if (null != departmentId) {
            if (searchType.equals("A")) {
                queryE.add(new Condition("grade.std.department.id =" + departmentId));
            } else if (searchType.equals("B")) {
                queryE.add(new Condition("grade.task.arrangeInfo.teachDepart.id =" + departmentId));
            }
        }
        if (null != courseName && !"".equals(courseName)) {
            queryE.add(new Condition("grade.course.name ='" + courseName + "'"));
        }
        if (null != yearfrom && null != termfrom) {
            queryE.add(new Condition("grade.calendar.year||grade.calendar.term >='" + yearfrom
                    + termfrom + "'"));
        }
        if (null != courseSeq && !"".equals(courseSeq)) {
            queryE.add(new Condition("grade.taskSeqNo ='" + courseSeq + "'"));
        }
        if (null != yearto && null != termto) {
            queryE.add(new Condition("grade.calendar.year||grade.calendar.term <='" + yearto
                    + termto + "'"));
        }
        queryE.setSelect("select count(*)");
        queryE.add(new Condition("grade.score>=0 and grade.score<60 or grade.score is null"));
        List listE = (List) utilService.search(queryE);
        Long countE = (Long) listE.get(0);
        list.add(countE);
        Long countAmont = new Long(countA.longValue() + countB.longValue() + countC.longValue()
                + countD.longValue() + countE.longValue());
        list.add(countAmont);
        return list;
    }
    
    public Collection getWithExamStatus(String courseSeq, String courseName, String yearfrom,
            String termfrom, String yearto, String termto, String searchType, Long departmentId,
            HttpServletRequest request) {
        EntityQuery queryWithExamStatus = new EntityQuery(CourseGrade.class, "grade");
        if (null != departmentId) {
            if (searchType.equals("A")) {
                queryWithExamStatus.add(new Condition("grade.std.department.id =" + departmentId));
            } else if (searchType.equals("B")) {
                queryWithExamStatus.add(new Condition("grade.task.arrangeInfo.teachDepart.id ="
                        + departmentId));
            }
        }
        if (null != courseName && !"".equals(courseName)) {
            queryWithExamStatus.add(new Condition("grade.course.name ='" + courseName + "'"));
        }
        if (null != courseSeq && !"".equals(courseSeq)) {
            queryWithExamStatus.add(new Condition("grade.taskSeqNo ='" + courseSeq + "'"));
        }
        if (null != yearfrom && null != termfrom) {
            queryWithExamStatus.add(new Condition("grade.calendar.year||grade.calendar.term >='"
                    + yearfrom + termfrom + "'"));
        }
        if (null != yearto && null != termto) {
            queryWithExamStatus.add(new Condition("grade.calendar.year||grade.calendar.term <='"
                    + yearto + termto + "'"));
        }
        queryWithExamStatus
                .add(new Condition(
                        "exists (from ExamGrade eg where eg.courseGrade.id=grade.id and eg.examStatus.id <>1 and eg.gradeType.id = 2)"));
        if (null != request) {
            queryWithExamStatus.addOrder(OrderUtils.parser(RequestUtils.get(request, "orderBy")));
            queryWithExamStatus.setLimit(QueryRequestSupport.getPageLimit(request));
        }
        Collection stdWithExamStatusList = utilService.search(queryWithExamStatus);
        return stdWithExamStatusList;
    }
    
    public Collection getWithExamIsNotPass(String courseSeq, String courseName, String yearfrom,
            String termfrom, String yearto, String termto, String searchType, Long departmentId,
            HttpServletRequest request) {
        EntityQuery queryWithExamIsNotPass = new EntityQuery(CourseGrade.class, "grade");
        if (null != departmentId) {
            if (searchType.equals("A")) {
                queryWithExamIsNotPass
                        .add(new Condition("grade.std.department.id =" + departmentId));
            } else if (searchType.equals("B")) {
                queryWithExamIsNotPass.add(new Condition("grade.task.arrangeInfo.teachDepart.id ="
                        + departmentId));
            }
        }
        if (null != courseName && !"".equals(courseName)) {
            queryWithExamIsNotPass.add(new Condition("grade.course.name ='" + courseName + "'"));
        }
        if (null != courseSeq && !"".equals(courseSeq)) {
            queryWithExamIsNotPass.add(new Condition("grade.taskSeqNo ='" + courseSeq + "'"));
        }
        if (null != yearfrom && null != termfrom) {
            queryWithExamIsNotPass.add(new Condition("grade.calendar.year||grade.calendar.term >='"
                    + yearfrom + termfrom + "'"));
        }
        if (null != yearto && null != termto) {
            queryWithExamIsNotPass.add(new Condition("grade.calendar.year||grade.calendar.term <='"
                    + yearto + termto + "'"));
        }
        queryWithExamIsNotPass.add(new Condition("grade.isPass =0"));
        if (null != request) {
            queryWithExamIsNotPass
                    .addOrder(OrderUtils.parser(RequestUtils.get(request, "orderBy")));
            queryWithExamIsNotPass.setLimit(QueryRequestSupport.getPageLimit(request));
        }
        Collection stdWithExamIsNotPassList = utilService.search(queryWithExamIsNotPass);
        return stdWithExamIsNotPassList;
    }
    
    public Collection getWithMakeUpExamIsNotPass(Student std, MajorType majorType) {
        EntityQuery queryWithMakeUpExamIsNotPass = new EntityQuery(ExamGrade.class, "examGrade");
        queryWithMakeUpExamIsNotPass.add(new Condition("examGrade.courseGrade in (:courseGrades)",
                getCourseGrades(std.getId(), majorType, null, null).toArray()));
        queryWithMakeUpExamIsNotPass.add(new Condition("examGrade.gradeType.id in (:gradeTypeIds)",
                new Object[] { GradeType.MAKEUP, GradeType.DELAY }));
        queryWithMakeUpExamIsNotPass.add(new Condition("examGrade.isPass=false"));
        return utilService.search(queryWithMakeUpExamIsNotPass);
    }
    
    public Collection getWithMakeUpExamIsPass(Student std, MajorType majorType) {
        EntityQuery queryWithMakeUpExamIsPass = new EntityQuery(ExamGrade.class, "examGrade");
        queryWithMakeUpExamIsPass.add(new Condition("examGrade.courseGrade in (:courseGrades)",
                getCourseGrades(std.getId(), majorType, null, null).toArray()));
        queryWithMakeUpExamIsPass.add(new Condition("examGrade.gradeType.id in (:gradeTypeIds)",
                new Object[] { GradeType.MAKEUP }));
        queryWithMakeUpExamIsPass.add(new Condition("examGrade.isPass=true"));
        return utilService.search(queryWithMakeUpExamIsPass);
    }
    
    public Long getGradeMaxCalendarId(Student std) {
        Long calendarId = gradeDAO.getGradeMaxCalendarId(std);
        return calendarId;
    }
    
//    public final void setGradeLogService(GradeLogService gradeLogService) {
//        this.gradeLogService = gradeLogService;
//    }
}
