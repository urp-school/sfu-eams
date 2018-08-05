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
package com.shufe.service.course.grade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.security.User;
import com.shufe.dao.course.grade.GradeDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.GradeState;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 成绩管理服务
 * 
 * @author chaostone
 * 
 */
public interface GradeService {
    
    /**
     * 查询学生完成的学分(忽略发布状态)
     * 
     * @param stdCode
     * @return
     */
    public Float getCredit(final String stdCode);
    
    /**
     * 查询学生学位课学分(忽略发布状态)
     * 
     * @param stdCode
     * @return
     */
    public Float getDegreeCredit(final String stdCode);
    
    /**
     * 判断是否学生对与某一门课程是否需要进行重修
     * 
     * @param std
     * @param course
     * @return
     */
    public boolean needReStudy(final Student std, final Course course);
    
    /**
     * 判断是否学生对与某一门课程已经修读通过
     * 
     * @param std
     * @param course
     * @return
     */
    public boolean isPass(final Student std, final Course course);
    
    /**
     * 查找学生在指定学期的课程或考试成绩<br>
     * 1.如果课程成绩不存在,则返回空对象.并且根据培养计划和上课名单决定:<br>
     * 2.如果存在是什么课程类别,多少学分以及是什么修读类别.<br>
     * 如果类型的考试成绩不存在,则新建一个空对象.并根据考试情况,决定是什么考试情况.<br>
     * 
     * 新增的空成绩的总成绩默认为发布，但是考试成绩默认发布状态为确认.<br>
     * 如果上课名单中没有该生,则返回的成绩中上课对象为null<br>
     * 如果课程序号在库中存在,则返回的成绩中总有教学任务对象.
     * 
     * @param stdCode
     * @param calendar
     * @param courseCode
     * @param gradeTypeId
     * @return
     */
    public CourseGrade getCourseGrade(final String stdCode, final TeachCalendar calendar,
            final String courseCode, final Long gradeTypeId);
    
    /**
     * 查询学生所有的最终成绩 的课程id和是否通过
     * 
     * @param std
     * @return
     */
    public Map getGradeCourseMap(final Long stdId);
    
    /**
     * 查询教学任务相关的成绩
     * 
     * @param task
     * @return
     */
    public List getCourseGrades(final TeachTask task);
    
    public List getCourseGrades(Collection tasks);
    
    /**
     * 查询学生成绩<br>
     * 除stdId之外均可以为空,空值忽略查询<br>
     * 
     * @param stdId
     * @param majorType
     * @param published
     * @param isPass
     * @return
     */
    public List getCourseGrades(final Long stdId, final MajorType majorType,
            final Boolean published, final Boolean isPass);
    
    /**
     * 按照样例查询学生成绩.
     * 
     * @return
     */
    public List getCourseGrades(final CourseGrade gradeExample);
    
    /**
     * 按照样例查询学生成绩不包括成绩记录方式为中文两级制的。
     * 
     * @param gradeExample
     * @return
     */
    public List getCourseGradesWithoutChinese(final CourseGrade gradeExample);
    
    /**
     * 查询指定学期,学生的课程成绩
     * 
     * @param calendar
     * @param stds
     * @param majorType
     *            可以为null
     * @param isPublish
     *            TODO
     * @return
     */
    public List getCourseGrades(final TeachCalendar calendar, final Collection stds,
            final MajorType majorType, final Boolean isPublish);
    
    /**
     * 查询教学任务中已经登记的考试成绩的成绩类型
     * 
     * @param task
     * @return
     */
    public List getGradeTypesOfExam(final TeachTask task);
    
    /**
     * 查询可以在成绩状态中可以让老师输入的成绩类别<br>
     * 不判断是否老师对某类成绩已经输入确认 返回结果将按照成绩类别的优先级排序,平时,期中,期末
     * 
     * @param state
     * @return
     */
    public List getGradeTypes(final GradeState state);
    
    /**
     * 查詢學位課程成績
     * 
     * @param stdId
     * @param degreeCourseId
     * @return
     */
    public List getDegreeCourseGrade(final Long stdId, Long degreeCourseId);
    
    /**
     * 查詢學位課程成績
     * 
     * @param stdId
     * @param degreeCourseId
     * @return
     */
    public List getCourseGradeOfDegree(final Long stdId, Long degreeCourseId);
    
    /**
     * 按照任务成绩分段统计
     * 
     * @param taskIdSeq
     *            教学任务id串
     * @param scoreSegments
     *            分段统计标准
     * @param gradeTypes
     *            要分段统计的成绩类别,可以同时对多个成绩类别进行分段统计
     * @param teacher
     *            要限制的教师
     * 
     * @return [CourseGradeSegStat]列表
     */
    public List statTask(final String taskIdSeq, final List scoreSegments, final List gradeTypes,
            final Teacher teacher);
    
    /**
     * 按照课程分段统计
     * 
     * @param courses
     * @param scoreSegments
     * @param gradeTypes
     * @param calendar
     * @return
     */
    public List statCourse(final List courses, final List scoreSegments, final List gradeTypes,
            final TeachCalendar calendar);
    
    /**
     * 按照成绩状态，重新计算成绩的<br>
     * 1、首先更改成绩的成绩记录方式<br>
     * 2、GA和score以及是否通过和绩点四项<br>
     * 
     * @param task
     * @param user
     *            TODO
     * @return
     */
    public void reCalcGrade(final TeachTask task, final User user);
    
    public List reCalcGrade(Collection tasks, User user);
    
    /**
     * 删除考试成绩<br>
     * 同时将该成绩和总评成绩的教师确认位置为0
     * 
     * @param task
     * @param gradeType
     * @param user
     *            TODO
     */
    public void removeExamGrades(final TeachTask task, final GradeType gradeType, final User user);
    
    /**
     * 计算教师录入成绩时，对于一种特定的成绩类型，现在的应该录入次序
     * 
     * @param task
     * @param grades
     * @param type
     * @return
     */
    public Integer calcEnrolTime(final TeachTask task, final GradeType gradeType);
    
    /**
     * 删除所有任务相关的成绩
     * 
     * @param task
     * @param user
     *            TODO
     */
    public void removeGrades(final TeachTask task, final User user);
    
    /**
     * 发布或取消发布成绩
     * 
     * @param taskIds
     * @param gradeType
     *            如果为空,则发布影响总评和最终
     * @param isPublished
     */
    public void publishGrade(final String taskIds, final GradeType gradeType,
            final Boolean isPublished);
    
    /**
     * 将成绩连接到courseTake中,如果有成绩，但是courseTake中的courseGrade是空的.
     * 这种情况出现在，管理人员在没有courseTake的情况下添加了成绩，后来又在教学班中添加了名单。 此时又想进入批量录入界面，但是两者的关联并没有建立起来。导致有些学生的成绩显示不出来。
     * 
     * @param task
     */
    public void linkGradeAndTakeAsPossible(final TeachTask task);
    
    /**
     * 是否拥有二专成绩
     * 
     * @param std
     * @return
     */
    public Boolean hasSpeciality2ndGrade(final Student std);
    
    public void setGradeDAO(final GradeDAO gradeDAO);
    
    public void setTeachTaskDAO(final TeachTaskDAO teachTaskDAO);
    
    /**
     * 按成绩和成绩记录方式Id，得到对应的显示值<br>
     * 仅在dwr中使用
     * 
     * @param score
     * @param markStyleId
     * @return
     */
    public String getScoreDisplay(final Float score, final Long markStyleId);
    
    /**
     * 根据查询条件得到成绩分析
     * 
     * @param courseSeq
     * @param courseName
     * @param yearfrom
     * @param termfrom
     * @param yearto
     * @param termto
     * @param searchType
     * @param departmentId
     * @return
     */
    public List getGradeRank(final String courseSeq, final String courseName,
            final String yearfrom, final String termfrom, final String yearto, final String termto,
            final String searchType, final Long departmentId);
    
    /**
     * 根据查询条件查询非正常考试成绩
     */
    public Collection getWithExamStatus(final String courseSeq, final String courseName,
            final String yearfrom, final String termfrom, final String yearto, final String termto,
            final String searchType, Long departmentId, final HttpServletRequest request);
    
    /**
     * 根据查询条件查询考试不及格成绩
     * 
     * @param courseSeq
     * @param courseName
     * @param yearfrom
     * @param termfrom
     * @param yearto
     * @param termto
     * @param searchType
     * @param departmentId
     * @param request
     * @return
     */
    public Collection getWithExamIsNotPass(final String courseSeq, final String courseName,
            final String yearfrom, final String termfrom, final String yearto, final String termto,
            final String searchType, final Long departmentId, final HttpServletRequest request);
    
    /**
     * 根据查询条件查询补缓考试不及格成绩
     * 
     * @param std
     * @param majorType
     * @return
     */
    public Collection getWithMakeUpExamIsNotPass(Student std, MajorType majorType);
    
    /**
     * 根据查询条件查询由补考考试获得的及格成绩
     * 
     * @param std
     * @param majorType
     * @return
     */
    public Collection getWithMakeUpExamIsPass(Student std, MajorType majorType);
    
    public Long getGradeMaxCalendarId(final Student std);
    
    
    public void removeExamGrades(TeachTask task,GradeType gradeType);
    
    public void removeGrades(TeachTask task);
}
