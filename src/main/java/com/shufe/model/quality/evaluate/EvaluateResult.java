//$Id: EvaluateResult.java,v 1.1 2007-6-2 18:53:56 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 问卷评教结果
 * 
 * @author chaostone
 * 
 */
public class EvaluateResult extends LongIdObject {
    
    private static final long serialVersionUID = -5263816130578552012L;
    
    /** 教学任务 */
    private TeachTask task = new TeachTask();
    
    /** 教师 */
    private Teacher teacher = new Teacher();
    
    /** 评教时间 */
    private Date evaluateAt;
    
    /** 学生 */
    private Student student = new Student();
    
    /** 统计状态 */
    private Boolean statState;
    
    /** 学生的所属部门 */
    private Department stdDepart = new Department();
    
    /** 教学任务中的开课院系 */
    private Department department = new Department();
    
    /** 学生类别 */
    private StudentType stdType;
    
    /** 教学日历 */
    private TeachCalendar teachCalendar = new TeachCalendar();
    
    /** 问卷信息 */
    private Questionnaire questionnaire = new Questionnaire();
    
    /** 问题评教结果 */
    private Set questionResultSet = new HashSet();
    
    public EvaluateResult() {
        
    }
    
    public EvaluateResult(TeachTask task, Student student, Teacher teacher) {
        this.task = task;
        this.evaluateAt = new Date(System.currentTimeMillis());
        this.statState = Boolean.TRUE;
        this.stdDepart = student.getDepartment();
        this.department = task.getArrangeInfo().getTeachDepart();
        this.stdType = task.getTeachClass().getStdType();
        this.teachCalendar = task.getCalendar();
        this.teacher = teacher;
        this.student = student;
        this.questionnaire = task.getQuestionnaire();
    }
    
    public void addQuestionResult(QuestionResult questionResult) {
        this.getQuestionResultSet().add(questionResult);
        questionResult.setResult(this);
    }
    
    public void addAll(Collection questionResultSet) {
        for (Iterator iter = questionResultSet.iterator(); iter.hasNext();) {
            QuestionResult element = (QuestionResult) iter.next();
            this.addQuestionResult(element);
        }
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    /**
     * @return Returns the evaluateAt.
     */
    public Date getEvaluateAt() {
        return evaluateAt;
    }
    
    /**
     * @param evaluateAt
     *            The evaluateAt to set.
     */
    public void setEvaluateAt(Date evaluateAt) {
        this.evaluateAt = evaluateAt;
    }
    
    /**
     * @return Returns the statState.
     */
    public Boolean getStatState() {
        return statState;
    }
    
    /**
     * @param statState
     *            The statState to set.
     */
    public void setStatState(Boolean statState) {
        this.statState = statState;
    }
    
    /**
     * @return Returns the stdDepart.
     */
    public Department getStdDepart() {
        return stdDepart;
    }
    
    /**
     * @param stdDepart
     *            The stdDepart to set.
     */
    public void setStdDepart(Department stdDepart) {
        this.stdDepart = stdDepart;
    }
    
    /**
     * @return Returns the stdType.
     */
    public StudentType getStdType() {
        return stdType;
    }
    
    /**
     * @param stdType
     *            The stdType to set.
     */
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    /**
     * @return Returns the student.
     */
    public Student getStudent() {
        return student;
    }
    
    /**
     * @param student
     *            The student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }
    
    /**
     * @return Returns the task.
     */
    public TeachTask getTask() {
        return task;
    }
    
    /**
     * @param task
     *            The task to set.
     */
    public void setTask(TeachTask task) {
        this.task = task;
    }
    
    /**
     * @return Returns the teachCalendar.
     */
    public TeachCalendar getTeachCalendar() {
        return teachCalendar;
    }
    
    /**
     * @param teachCalendar
     *            The teachCalendar to set.
     */
    public void setTeachCalendar(TeachCalendar teachCalendar) {
        this.teachCalendar = teachCalendar;
    }
    
    /**
     * @return Returns the teacher.
     */
    public Teacher getTeacher() {
        return teacher;
    }
    
    /**
     * @param teacher
     *            The teacher to set.
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    /**
     * @return Returns the questionnaire.
     */
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }
    
    /**
     * @param questionnaire
     *            The questionnaire to set.
     */
    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
    
    /**
     * @return Returns the questionResultSet.
     */
    public Set getQuestionResultSet() {
        return questionResultSet;
    }
    
    /**
     * @param questionResultSet
     *            The questionResultSet to set.
     */
    public void setQuestionResultSet(Set questionResultSet) {
        this.questionResultSet = questionResultSet;
    }
    
}
