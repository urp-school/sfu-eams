package com.shufe.model.course.attend;


import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 考勤统计报表（教务处） 
 * @author SongXiangwen
 *
 */
public class AttendStaticReport extends LongIdObject{
	
	 private static final long serialVersionUID = 1071972497531228229L;
	 
	/**
     * 教学日历
     */
    private TeachCalendar calendar = new TeachCalendar();
    
    /**
     * 教学任务
     */
    private TeachTask task;
    
    /**
     * 学生
     */
    private Student student;
    
    
    /**
     * 辅导员
     */
    private Teacher teacher;
    
    /**
     * 考勤年
     */
    private Integer attendYear;
    
    /**
     * 考勤月
     */
    private Integer attendMonth;
    
    /**
     * 月缺勤率
     */
    private Float monthAbesence;
    
    /**
     * 学期缺勤率
     */
    private Float termAbesence;

    
    
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public TeachTask getTask() {
		return task;
	}

	public void setTask(TeachTask task) {
		this.task = task;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}


	public Integer getAttendYear() {
		return attendYear;
	}

	public void setAttendYear(Integer attendYear) {
		this.attendYear = attendYear;
	}

	public Integer getAttendMonth() {
		return attendMonth;
	}

	public void setAttendMonth(Integer attendMonth) {
		this.attendMonth = attendMonth;
	}

	public Float getMonthAbesence() {
		return monthAbesence;
	}

	public void setMonthAbesence(Float monthAbesence) {
		this.monthAbesence = monthAbesence;
	}

	public Float getTermAbesence() {
		return termAbesence;
	}

	public void setTermAbesence(Float termAbesence) {
		this.termAbesence = termAbesence;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
}
