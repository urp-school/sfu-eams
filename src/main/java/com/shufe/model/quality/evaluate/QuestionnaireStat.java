//$Id: QuestionnaireStat.java,v 1.1 2007-3-19 上午09:16:42 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 评教问卷统计结果
 * 
 * @author chaostone
 */
public class QuestionnaireStat extends LongIdObject implements Cloneable {
    
    private static final long serialVersionUID = 1416090442769917717L;
    
    /** 教学任务 */
    private TeachTask task = new TeachTask();
    
    /** 教学日历 */
    private TeachCalendar calendar = new TeachCalendar();
    
    /** 学生类别 */
    private StudentType stdType;
    
    /** 学生评教的课程 */
    private Course course = new Course();
    
    /** 任课教师 */
    private Teacher teacher = new Teacher();
    
    /** 开课院系 */
    private Department depart = new Department();
    
    /** 统计时间 */
    private Calendar statAt;
    
    /** 课程序号 */
    private String taskSeqNo;
    
    /** 总得分 */
    private Float score;
    
    /** 有效票数 */
    private Integer validTickets;
    
    /** 问题类别得分 */
    private Set questionTypeStats = new HashSet();
    
    public QuestionnaireStat(TeachTask task, Teacher teacher, Integer validTicket) {
        this.task = task;
        this.teacher = teacher;
        this.validTickets = validTicket;
        this.calendar = task.getCalendar();
        this.course = task.getCourse();
        this.depart = task.getArrangeInfo().getTeachDepart();
        this.taskSeqNo = task.getSeqNo();
        this.stdType = task.getTeachClass().getStdType();
        this.statAt = Calendar.getInstance();
    }
    
    public QuestionnaireStat() {
    }
    
    public Object clone() {
        QuestionnaireStat stat = new QuestionnaireStat();
        stat.setTask(this.task);
        stat.setCalendar(this.calendar);
        stat.setStdType(this.stdType);
        stat.setCourse(this.course);
        stat.setTeacher(this.teacher);
        stat.setDepart(this.depart);
        stat.setStatAt(this.statAt);
        stat.setTaskSeqNo(this.taskSeqNo);
        stat.setScore(this.score);
        stat.setValidTickets(this.validTickets);
        for (Iterator it = this.questionTypeStats.iterator(); it.hasNext();) {
            QuestionTypeStat typeMark = (QuestionTypeStat) it.next();
            stat.addQuestionTypeStat((QuestionTypeStat) typeMark.clone());
        }
        return stat;
    }
    
    public void addQuestionTypeStat(QuestionTypeStat questionTypeStat) {
        this.questionTypeStats.add(questionTypeStat);
        questionTypeStat.setQuestionnaireStat(this);
    }
    
    public void calcScore() {
        int i = questionTypeStats.size();
        float mark = 0;
        for (Iterator iter = questionTypeStats.iterator(); iter.hasNext();) {
            QuestionTypeStat stat = (QuestionTypeStat) iter.next();
            mark += stat.getScore().floatValue();
        }
        if (i == 0) {
            this.score = new Float(mark);
        } else {
            this.score = new Float(mark / i);
        }
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public Department getDepart() {
        return depart;
    }
    
    public void setDepart(Department depart) {
        this.depart = depart;
    }
    
    public Calendar getStatAt() {
        return statAt;
    }
    
    public void setStatAt(Calendar statisticAt) {
        this.statAt = statisticAt;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public TeachTask getTask() {
        return task;
    }
    
    public void setTask(TeachTask task) {
        this.task = task;
    }
    
    public String getTaskSeqNo() {
        return taskSeqNo;
    }
    
    public void setTaskSeqNo(String taskSeqNo) {
        this.taskSeqNo = taskSeqNo;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public Float getScore() {
        return score;
    }
    
    public void setScore(Float totleScore) {
        this.score = totleScore;
    }
    
    public Set getQuestionTypeStats() {
        return questionTypeStats;
    }
    
    public void setQuestionTypeStats(Set typeMarks) {
        this.questionTypeStats = typeMarks;
    }
    
    public Integer getValidTickets() {
        return validTickets;
    }
    
    public void setValidTickets(Integer validTicket) {
        this.validTickets = validTicket;
    }
    
    public String getTypeScoreDisplay(EvaluationCriteria critria, Long typeId) {
        Set typeMarks = this.getQuestionTypeStats();
        float mark = -1;
        for (Iterator iter = typeMarks.iterator(); iter.hasNext();) {
            QuestionTypeStat element = (QuestionTypeStat) iter.next();
            if (typeId.equals(element.getType().getId())) {
                mark = element.getScore().floatValue();
                break;
            }
        }
        if (null == critria) {
            return String.valueOf(mark);
        } else {
            if (mark >= 0)
                return critria.getEvaluation(mark);
            else {
                return "";
            }
        }
    }
    
    public String getScoreDisplay(EvaluationCriteria criteria) {
        return criteria.getEvaluation(getScore().floatValue());
    }
}
