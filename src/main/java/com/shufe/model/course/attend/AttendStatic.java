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
 * 考勤统计 
 * @author SongXiangwen
 *
 */
public class AttendStatic extends LongIdObject{
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
     * 上课院系
     */
    private Department department;
    
    /**
     * 课程编号
     */
    private Course course = new Course();
    
    /**
     * 设备id
     */
    private Long devid;
    
    /**
     * 考勤时间
     */
    private String attendtime;
    
    /**
     * 考勤日期
     */
    private Date attenddate;
    
    /**
     * 考勤类型
     */
    private String attendtype;
    
    /**
     * 课时
     * @return
     */
    private Integer ks;
    
    

	public Integer getKs() {
		return ks;
	}

	public void setKs(Integer ks) {
		this.ks = ks;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Long getDevid() {
		return devid;
	}

	public void setDevid(Long devid) {
		this.devid = devid;
	}

	public String getAttendtime() {
		return attendtime;
	}

	public void setAttendtime(String attendtime) {
		this.attendtime = attendtime;
	}

	public Date getAttenddate() {
		return attenddate;
	}

	public void setAttenddate(Date attenddate) {
		this.attenddate = attenddate;
	}

	public String getAttendtype() {
		return attendtype;
	}

	public void setAttendtype(String attendtype) {
		this.attendtype = attendtype;
	}
    
}
