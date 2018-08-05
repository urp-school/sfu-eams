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
 * chaostone             2006-11-5            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.exam;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.struts.util.MessageResources;

import com.ekingstar.eams.system.basecode.industry.ExamType;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.TaskActivity;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.exam.ExamActivityDigestor;

/**
 * 排考活动
 * 
 * @author chaostone
 */
public class ExamActivity extends TaskActivity {

    private static final long serialVersionUID = -6748665397101838909L;

    /** 主考教师 */
    protected Teacher teacher = new Teacher();

    protected  Department department=new  Department();

    /** 考试类型 */
    private ExamType examType = new ExamType();

    /** 应考学生记录 */
    private Set examTakes = new HashSet();

    /** 监考信息 */
    private ExamMonitor examMonitor;

    public ExamActivity() {
        examMonitor = new ExamMonitor();
        examMonitor.setExamActivity(this);
    }

    /**
     * 排考日期
     * 
     * @return
     */
    public Date getDate() {
        return time.getFirstDay();
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public Set getExamTakes() {
        return examTakes;
    }

    public void setExamTakes(Set examTakes) {
        this.examTakes = examTakes;
    }

    public void addExamTake(ExamTake take) {
        take.setActivity(this);
        getExamTakes().add(take);
    }

    /**
     * 不计较年份,比较是否是相近的教学活动.<br>
     * 
     * @param other
     * @param teacher
     *            是否考虑教师
     * @param room
     *            是否考虑教室
     * @return
     */
    public boolean isSameActivityExcept(ExamActivity other, boolean teacher, boolean room) {
        if (teacher) {
            boolean sameTeacher = getExamMonitor().isSameMonitor(other.getExamMonitor());
            if (!sameTeacher)
                return false;
        }

        if (room) {
            boolean sameRoom = ObjectUtils.equals(getRoom(), other.getRoom());
            if (!sameRoom)
                return false;
        }
        if (!getTime().getStartUnit().equals(other.getTime().getStartUnit()))
            return false;
        if (!getTime().getEndUnit().equals(other.getTime().getEndUnit()))
            return false;
        if (!getTime().getWeekId().equals(other.getTime().getWeekId()))
            return false;
        return true;
    }

    /**
     * 把所有的信息克隆一遍<br>
     * 不包括examTakes
     */
    public Object clone() {
        ExamActivity activity = new ExamActivity();
        activity.setRoom(room);
        activity.setCalendar(calendar);
        activity.setTask(task);
        // task.getArrangeInfo().getExamActivities().add(activity);
        activity.setTeacher(getTeacher());
        if (null != getExamMonitor()) {
            activity.setExamMonitor((ExamMonitor) getExamMonitor().clone());
            activity.getExamMonitor().setExamActivity(activity);
        }
        activity.setDepartment(task.getArrangeInfo().getTeachDepart());
        activity.setTime((TimeUnit) time.clone());
        activity.setExamType(getExamType());
        return activity;
    }

    public String digest(Locale locale, MessageResources resources) {
        return ExamActivityDigestor.digest(calendar, Collections.singletonList(this), resources, locale);
    }

    public String digest(Locale locale, MessageResources resources, String format) {
        return ExamActivityDigestor.digest(calendar, Collections.singletonList(this), resources, locale,
                format);
    }

    public String digest(TeachCalendar calendar, Locale locale, MessageResources resources, String format) {
        return ExamActivityDigestor.digest(calendar, Collections.singletonList(this), resources, locale,
                format);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public ExamMonitor getExamMonitor() {
        return examMonitor;
    }

    public void setExamMonitor(ExamMonitor examMonitor) {
        this.examMonitor = examMonitor;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
