//$Id: TeachWorkloadLog.java,v 1.1 2008-3-4 上午11:01:58 zhouqi Exp $
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
 * zhouqi              2008-3-4         	Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.course;

import java.util.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.security.User;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;

/**
 * @author zhouqi
 */
public class TeachWorkloadAlteration extends LongIdObject {
    
    private static final long serialVersionUID = 1L;
    
    /** 教学任务 */
    private TeachTask task;
    
    /** 教师 */
    private Teacher teacher;
    
    /** 是否手工输入 */
    private Boolean isHandInput;
    
    /** 课程种类 */
    private CourseCategory courseCategory;
    
    /** 教师确认 */
    private Boolean teacherAffirm;
    
    /** 院系确认 */
    private Boolean collegeAffirm;
    
    /** 修改前信息 */
    private TeachWorkloadStatus alterBefore;
    
    /** 修改后信息 */
    private TeachWorkloadStatus alterAfter;
    
    /** 修改人 */
    private User workloadBy;
    
    /** 访问路径 */
    private String workloadFrom;
    
    /** 修改发生时间 */
    private Date workloadAt;
    
    /**
     * @return the collegeAffirm
     */
    public Boolean getCollegeAffirm() {
        return collegeAffirm;
    }
    
    /**
     * @param collegeAffirm the collegeAffirm to set
     */
    public void setCollegeAffirm(Boolean collegeAffirm) {
        this.collegeAffirm = collegeAffirm;
    }
    
    /**
     * @return the courseCategory
     */
    public CourseCategory getCourseCategory() {
        return courseCategory;
    }
    
    /**
     * @param courseCategory the courseCategory to set
     */
    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }
    
    /**
     * @return the isHandInput
     */
    public Boolean getIsHandInput() {
        return isHandInput;
    }
    
    /**
     * @param isHandInput the isHandInput to set
     */
    public void setIsHandInput(Boolean isHandInput) {
        this.isHandInput = isHandInput;
    }
    
    /**
     * @return the task
     */
    public TeachTask getTask() {
        return task;
    }
    
    /**
     * @param task the task to set
     */
    public void setTask(TeachTask task) {
        this.task = task;
    }
    
    /**
     * @return the teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }
    
    /**
     * @param teacher the teacher to set
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    /**
     * @return the teacherAffirm
     */
    public Boolean getTeacherAffirm() {
        return teacherAffirm;
    }
    
    /**
     * @param teacherAffirm the teacherAffirm to set
     */
    public void setTeacherAffirm(Boolean teacherAffirm) {
        this.teacherAffirm = teacherAffirm;
    }
    
    /**
     * @return the workloadAt
     */
    public Date getWorkloadAt() {
        return workloadAt;
    }
    
    /**
     * @param workloadAt the workloadAt to set
     */
    public void setWorkloadAt(Date workloadAt) {
        this.workloadAt = workloadAt;
    }
    
    /**
     * @return the workloadBy
     */
    public User getWorkloadBy() {
        return workloadBy;
    }
    
    /**
     * @param workloadBy the workloadBy to set
     */
    public void setWorkloadBy(User workloadBy) {
        this.workloadBy = workloadBy;
    }
    
    
    /**
     * @return the workloadAfter
     */
    public TeachWorkloadStatus getAlterAfter() {
        return alterAfter;
    }

    
    /**
     * @param workloadAfter the workloadAfter to set
     */
    public void setAlterAfter(TeachWorkloadStatus workloadAfter) {
        this.alterAfter = workloadAfter;
    }

    
    /**
     * @return the workloadBefore
     */
    public TeachWorkloadStatus getAlterBefore() {
        return alterBefore;
    }

    
    /**
     * @param workloadBefore the workloadBefore to set
     */
    public void setAlterBefore(TeachWorkloadStatus workloadBefore) {
        this.alterBefore = workloadBefore;
    }

    /**
     * @return the workloadFrom
     */
    public String getWorkloadFrom() {
        return workloadFrom;
    }
    
    /**
     * @param workloadFrom the workloadFrom to set
     */
    public void setWorkloadFrom(String workloadFrom) {
        this.workloadFrom = workloadFrom;
    }
}
