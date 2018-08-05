//$Id: GradeCatalog.java,v 1.1 2009-7-6 下午02:38:13 zhouqi Exp $
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
 * zhouqi              2009-7-6             Created
 *  
 ********************************************************************************/

package com.shufe.model.course.grade;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.User;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author zhouqi
 * 
 */
public class GradeLog extends LongIdObject {
    
    private static final long serialVersionUID = -2931113126349392547L;
    
    public static final Long START = new Long(0);
    
    public static final Long ADD = new Long(1);
    
    public static final Long EDIT = new Long(2);
    
    public static final Long REMOVE = new Long(3);
    
    private StudentType stdType;
    
    private String stdTypeCode;
    
    private String stdTypeName;
    
    private Student student;
    
    private String stdCode;
    
    private String stdName;
    
    private TeachCalendar calendar;
    
    /** 教学日历备用值 */
    /** 内容为：学生类别ID + 学年度 + 学期，之间用空格分隔 */
    private String calendarValue;
    
    private TeachTask task;
    
    private String taskSeqNo;
    
    private Course course;
    
    private String courseCode;
    
    private String courseName;
    
    /** 生产记录的状态：1 新增 2 修改 3 删除 */
    private Long status;
    
    /** 具体的信息：比如，成绩及状态等 */
    private String context;
    
    private Date generateAt;
    
    private User generateBy;
    
    private String userCode;
    
    private String userName;
    
    public GradeLog() {
        this.context = "";
        this.generateAt = new Date();
    }
    
    public GradeLog(final Student student, final TeachCalendar calendar, final TeachTask task,
            final Long status, User generateBy, final String context) {
        this();
        addStudent(student);
        addCalendar(calendar);
        addTeachTask(task);
        addGenerateBy(generateBy);
        this.status = status;
        this.context = context;
    }
    
    /**
     * @param generateBy
     */
    public void addGenerateBy(final User generateBy) {
    	if(generateBy!=null){
	        this.generateBy = generateBy;
	        this.userCode = this.generateBy.getName();
	        this.userName = this.generateBy.getUserName();
    	}
    }
    
    /**
     * @param task
     */
    public void addTeachTask(final TeachTask task) {
        if (null != task.getId()) {
            this.task = task;
            this.taskSeqNo = this.task.getSeqNo();
            this.course = this.task.getCourse();
            this.courseCode = this.task.getCourse().getCode();
            this.courseName = this.task.getCourse().getName();
        } else {
            this.task = null;
            this.taskSeqNo = null;
            this.course = task.getCourse();
            this.courseCode = task.getCourse().getCode();
            this.courseName = task.getCourse().getName();
        }
    }
    
    /**
     * @param calendar
     */
    public void addCalendar(final TeachCalendar calendar) {
        this.calendar = calendar;
        this.stdType = this.calendar.getStudentType();
        this.stdTypeCode = this.stdType.getCode();
        this.stdTypeName = this.stdType.getName();
        this.calendarValue = this.calendar.getStudentType().getName() + " " + calendar.getYear()
                + " " + calendar.getTerm();
    }
    
    /**
     * @param student
     */
    public void addStudent(final Student student) {
        this.student = student;
        this.stdCode = this.student.getCode();
        this.stdName = this.student.getName();
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public String getStdCode() {
        return stdCode;
    }
    
    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }
    
    public String getStdName() {
        return stdName;
    }
    
    public void setStdName(String stdName) {
        this.stdName = stdName;
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public String getCalendarValue() {
        return calendarValue;
    }
    
    public void setCalendarValue(String calendarValue) {
        this.calendarValue = calendarValue;
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
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public Long getStatus() {
        return status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public Date getGenerateAt() {
        return generateAt;
    }
    
    public void setGenerateAt(Date generateAt) {
        this.generateAt = generateAt;
    }
    
    public User getGenerateBy() {
        return generateBy;
    }
    
    public void setGenerateBy(User generateBy) {
        this.generateBy = generateBy;
    }
    
    public String getUserCode() {
        return userCode;
    }
    
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public String getStdTypeCode() {
        return stdTypeCode;
    }
    
    public void setStdTypeCode(String stdTypeCode) {
        this.stdTypeCode = stdTypeCode;
    }
    
    public String getStdTypeName() {
        return stdTypeName;
    }
    
    public void setStdTypeName(String stdTypeName) {
        this.stdTypeName = stdTypeName;
    }
}
