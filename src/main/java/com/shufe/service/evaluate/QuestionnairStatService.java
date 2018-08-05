//$Id: EvaluateTeacherStatService.java,v 1.1 2008-5-19 上午09:16:40 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-5-19         	Created
 *  
 ********************************************************************************/

package com.shufe.service.evaluate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.shufe.model.quality.evaluate.stat.EvaluateCollegeStat;
import com.shufe.model.quality.evaluate.stat.EvaluateDepartmentStat;
import com.shufe.model.quality.evaluate.stat.EvaluateTeacherStat;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author zhouqi
 */
public interface QuestionnairStatService {
    
    /**
     * 返回EvaluateTeacherStat对象，根据evaluateTeacherId
     * 
     * @param evaluateTeacherId
     * @return
     */
    public EvaluateTeacherStat getEvaluateTeacherStat(Long evaluateTeacherId);
    
    /**
     * 根据业务主键（教学日历，教学任务，教师），找到对象
     * 
     * @param calendar
     * @param course
     * @param teacher
     * @return
     */
    public EvaluateTeacherStat getEvaluateTeacherStat(TeachCalendar calendar, Course course,
            Teacher teacher);
    
    public Boolean isEmptyOfEvaluateTeacherStat(TeachCalendar calendar, Course course,
            Teacher teacher);
    
    /**
     * 得到当前学期的所有教师被评教的统计结果
     * 
     * @param calendar
     * @return
     */
    public List getEvaluateTeachers(TeachCalendar calendar);
    
    /**
     * 得到当前学期、指定部门的所有教师被评教的统计结果
     * 
     * @param calendar
     * @param department
     * @return
     */
    public List getEvaluateTeachers(TeachCalendar calendar, Department department);
    
    /**
     * 得到当前学及某部门的所有教师被评教的统计结果
     * 
     * @param calendar
     * @param teacher
     * @return
     */
    public List getEvaluateTeachers(TeachCalendar calendar, Teacher teacher);
    
    /**
     * 返回所有和当前学期的教师被评教统计结果
     * 
     * @param calendar
     * @param isAllCalendar
     *            <font color="green">true</font>：历史学期到当前学期；<font color="red">false</font>：仅当前学期
     * @return
     */
    public List getEvaluateTeachers(TeachCalendar calendar, boolean isAllCalendar);
    
    /**
     * 返回EvaluateDepartmentStat对象，根据evaluateDepartmentId
     * 
     * @param evaluateDepartmentId
     * @return
     */
    public EvaluateDepartmentStat getEvaluateDepartmentStat(Long evaluateDepartmentId);
    
    /**
     * 根据业务主键（教学日历，开课院系），找到对象
     * 
     * @param calendar
     * @param department
     * @return
     */
    public EvaluateDepartmentStat getEvaluateDepartmentStat(TeachCalendar calendar,
            Department department);
    
    /**
     * 得到当前学期的所有院系被评教的统计结果
     * 
     * @param calendar
     * @return
     */
    public List getEvaluateDepartments(TeachCalendar calendar);
    
    /**
     * 得到某部门所有学期的统计结果
     * 
     * @param department
     * @return
     */
    public List getEvaluateDepartments(Department department);
    
    /**
     * 返回EvaluateCollegeStat对象，根据evaluateCollegeId
     * 
     * @param evaluateCollegeId
     * @return
     */
    public EvaluateCollegeStat getEvaluateCollegeStat(Long evaluateCollegeId);
    
    /**
     * 根据业务主键（教学日历），找到对象
     * 
     * @param calendar
     * @return
     */
    public EvaluateCollegeStat getEvaluateCollegeStat(TeachCalendar calendar);
    
    /**
     * 得到所有学期全校评教情况
     * 
     * @return
     */
    public List getAllEvaluateColleges();
    
    /**
     * 保存教师个人评教统计结果
     * 
     * @param stdTypeIds
     * @param departmentIds
     * @param calendar
     * @return
     */
    public List buildTeacherStatResult(Long[] stdTypeIds, Long[] departmentIds,
            TeachCalendar calendar);
    
    /**
     * 保存所在院系评教统计结果
     * 
     * @param departmentIds
     * @param calendar
     * @return
     */
    public List buildDepartmentStatResult(Long[] departmentIds, TeachCalendar calendar);
    
    /**
     * 统计全部评教结果
     * 
     * @param stdTypeIds
     * @param departmentIds
     * @param calendar
     * @return
     */
    public List buildStatResult(Long[] stdTypeIds, Long[] departmentIds, TeachCalendar calendar);
    
    /**
     * 保存全校评教统计结果
     * 
     * @param calendar
     * @return
     */
    public List buildCollegeStatResult(TeachCalendar calendar);
    
    /**
     * 统计当前学期的全校参评课程总数
     * 
     * @param calendar
     * @return
     */
    public List evaluateCoursesCount(TeachCalendar calendar);
    
    /**
     * 统计当前学期的参评院系总数
     * 
     * @param calendar
     * @return
     */
    public List evaluateDepartmentsCount(TeachCalendar calendar);
    
    /**
     * 统计当前学期的个人在全校的排名
     * 
     * @param calendar
     * @return
     */
    public List evaluateTeacherScorePlace(TeachCalendar calendar);
    
    /**
     * 统计当前学期的个人的所属院系在全校的排名
     * 
     * @param calendar
     * @return
     */
    public List evaluateDepartmentScorePlace(TeachCalendar calendar);
    
    /**
     * 汇总评教统计明细排名
     * 
     * @param calendar
     * @return
     */
    public List buildEvaluateRanks(TeachCalendar calendar);
    
    /**
     * 按统计分值评教统计，返回全校当前学期评教统计结果
     * 
     * @param departmentResults
     * 
     * @return
     */
    public Object[] getCollegeResultList(List departmentResults);
    
    /**
     * 按统计分值评教统计，返回所有学期的评教统计结果
     * 
     * @param calendar
     * @param criteriaItems
     * @return
     */
    public List getCollegeResultList(TeachCalendar calendar, List criteriaItems);
    
    /**
     * 返回某个院系的学校统计结果
     * 
     * @param department
     * @param criteriaItems
     * @return
     */
    public List getCollegeResultList(Collection criteriaItems);
    
    /**
     * 按问题类别分项统计的全校统计
     * 
     * @param calendar
     * @return
     */
    public Map getCollegeResultList(TeachCalendar calendar);
    
    /**
     * 返回按统计分值的院系评教统计结果
     * 
     * @param criteriaItems
     * @param calendar
     * @return
     */
    public List getDepartResultList(List criteriaItems, TeachCalendar calendar);
    
    /**
     * 返回某个院系的院系统计结果
     * 
     * @param criteriaItems
     * @param department
     * @return
     */
    public List getDepartResultList(List criteriaItems, Department department);
    
    /**
     * 按问题类别分项统计的院系统计<br>
     * <br>
     * 若20分为满分，则按百分比90、80、70、60和60以下，则分值为：<br>
     * 好：18分以上; 较好：16分--17.9分; 中：14分--15.9分; 较差：12分--12.9分; 差：12分以下
     * 
     * @param calendar
     * @param department
     * @return
     */
    public Map getDepartResultList(TeachCalendar calendar, Department department);
    
    /**
     * 按问卷有序输出
     * 
     * @param evaluateTeacher
     */
    public void sortQuestionsStat(EvaluateTeacherStat evaluateTeacher);
    
    /**
     * 汇总院系评教结果
     * 
     * @param teacherResults
     * @return
     */
    public double[] getDepartmentResults(Collection teacherResults);
}
