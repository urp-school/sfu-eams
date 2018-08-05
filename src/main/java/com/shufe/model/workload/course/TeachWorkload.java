//$Id: TeachWorkload.java,v 1.6 2006/12/19 13:08:40 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.course;

import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.workload.Workload;

/**
 * 
 * @author chaostone
 * 
 */
public class TeachWorkload extends Workload {
    
    /**
     * 
     */
    private static final long serialVersionUID = -210424795148934424L;
    
    /** 教学任务 */
    private TeachTask teachTask = new TeachTask();
    
    private Course course = new Course(); // 授课信息
    
    private CourseType courseType = new CourseType(); // 课程类别
    
    private String courseName; // 课程名字
    
    private CourseCategory courseCategory = new CourseCategory();// 课程种类
    
    private TeachCategory teachCategory = new TeachCategory(); // 教学性质代码.
    
    private String classIds; // 行政班Id串
    
    private String classNames; // 对应的行政班名字串
    
    private TeachModulus teachModulus = new TeachModulus(); // 授课工作量系数
    
    private Float classNumberOfWeek; // 周课时
    
    private Float weeks; // 上课周数
    
    private Float totleCourses; // 总课时
    
    private Boolean collegeAffirm /* =new Boolean(false) */; // 院系确认
    
    private Department college = new Department(); // 课程所属院系,开课院系
    
    private String courseSeq; // 课程序号
    
    public TeachWorkload() {
        
    }
    
    public TeachWorkload(Teacher teacher) {
        super(teacher);
        this.isTeachWorkload = Boolean.TRUE;
        this.collegeAffirm = Boolean.FALSE;
    }
    
    public TeachWorkload(Teacher teacher, TeachTask task) {
        this(teacher);
        this.course = task.getCourse();
        this.teachTask = task;
        this.courseType = task.getCourseType();
        this.courseName = task.getCourse().getName();
        this.studentType = task.getTeachClass().getStdType();
        this.college = task.getArrangeInfo().getTeachDepart();
        this.teachCalendar = task.getCalendar();
        this.courseSeq = task.getSeqNo();
        this.remark = task.getRemark();
    }
    
    /**
     * @return Returns the classIds.
     */
    public String getClassIds() {
        return classIds;
    }
    
    /**
     * @param classIds
     *            The classIds to set.
     */
    public void setClassIds(String classIds) {
        this.classIds = classIds;
    }
    
    /**
     * @return Returns the classNames.
     */
    public String getClassNames() {
        return classNames;
    }
    
    /**
     * @param classNames
     *            The classNames to set.
     */
    public void setClassNames(String classNames) {
        this.classNames = classNames;
    }
    
    /**
     * @return Returns the classNumberOfWeek.
     */
    public Float getClassNumberOfWeek() {
        return classNumberOfWeek;
    }
    
    /**
     * @param classNumberOfWeek
     *            The classNumberOfWeek to set.
     */
    public void setClassNumberOfWeek(Float classNumberOfWeek) {
        this.classNumberOfWeek = classNumberOfWeek;
    }
    
    /**
     * @return Returns the collegeAffirm.
     */
    public Boolean getCollegeAffirm() {
        return collegeAffirm;
    }
    
    /**
     * @param collegeAffirm
     *            The collegeAffirm to set.
     */
    public void setCollegeAffirm(Boolean collegeAffirm) {
        this.collegeAffirm = collegeAffirm;
    }
    
    /**
     * @return Returns the course.
     */
    public Course getCourse() {
        return course;
    }
    
    /**
     * @param course
     *            The course to set.
     */
    public void setCourse(Course course) {
        this.course = course;
    }
    
    /**
     * @return Returns the courseCategory.
     */
    public CourseCategory getCourseCategory() {
        return courseCategory;
    }
    
    /**
     * @param courseCategory
     *            The courseCategory to set.
     */
    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }
    
    /**
     * @return Returns the teachModulus.
     */
    public TeachModulus getTeachModulus() {
        return teachModulus;
    }
    
    /**
     * @param teachModulus
     *            The teachModulus to set.
     */
    public void setTeachModulus(TeachModulus teachModulus) {
        this.teachModulus = teachModulus;
    }
    
    /**
     * @return Returns the totleCourses.
     */
    public Float getTotleCourses() {
        return totleCourses;
    }
    
    /**
     * @param totleCourses
     *            The totleCourses to set.
     */
    public void setTotleCourses(Float totleCourses) {
        this.totleCourses = totleCourses;
    }
    
    /**
     * @return Returns the weeks.
     */
    public Float getWeeks() {
        return weeks;
    }
    
    /**
     * @param weeks
     *            The weeks to set.
     */
    public void setWeeks(Float weeks) {
        this.weeks = weeks;
    }
    
    /**
     * @return Returns the teachTask.
     */
    public TeachTask getTeachTask() {
        return teachTask;
    }
    
    /**
     * @param teachTask
     *            The teachTask to set.
     */
    public void setTeachTask(TeachTask teachTask) {
        this.teachTask = teachTask;
    }
    
    /**
     * @return Returns the courseName.
     */
    public String getCourseName() {
        return courseName;
    }
    
    /**
     * @param courseName
     *            The courseName to set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    /**
     * @return Returns the college.
     */
    public Department getCollege() {
        return college;
    }
    
    /**
     * @param college
     *            The college to set.
     */
    public void setCollege(Department college) {
        this.college = college;
    }
    
    /**
     * @return Returns the teachCategory.
     */
    public TeachCategory getTeachCategory() {
        return teachCategory;
    }
    
    /**
     * @param teachCategory
     *            The teachCategory to set.
     */
    public void setTeachCategory(TeachCategory teachCategory) {
        this.teachCategory = teachCategory;
    }
    
    /**
     * @return Returns the courseType.
     */
    public CourseType getCourseType() {
        return courseType;
    }
    
    /**
     * @param courseType
     *            The courseType to set.
     */
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    
    /**
     * @return Returns the courseSeq.
     */
    public String getCourseSeq() {
        return courseSeq;
    }
    
    /**
     * @param courseSeq
     *            The courseSeq to set.
     */
    public void setCourseSeq(String courseSeq) {
        this.courseSeq = courseSeq;
    }
    
}
