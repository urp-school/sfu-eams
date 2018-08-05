//$Id: TaskRequirement.java,v 1.5 2006/12/30 01:28:21 duanth Exp $
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
 * chaostone            2005-10-21          Created
 * zq                   2007-10-17          添加了“授课语言类型”属性；
 *                                          在对应的“教学任务要求的缺省要求”方
 *                                          法中，添加了对“授课语言类型”初始化
 *                                          的语句；
 ********************************************************************************/

package com.shufe.model.course.task;

import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.Component;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.industry.TeachLangType;

/**
 * 教学任务要求<br>
 * 主要包含教室要求、教材要求、双语挂牌要求以及评教要求。<br>
 * 除去课程种类和评教要求之外的信息，一般有教师和教务秘书<br>
 * 进行填写。
 * 
 * @author chaostone 2005-10-21
 */
public class TaskRequirement implements Component {
    
    /**
     * 教室设备要求
     * 
     * @supplierCardinality 1
     */
    private ClassroomType roomConfigType = new ClassroomType();
    
    /**
     * capacityOfCourse,donot persist it.
     */
    private Integer capacityOfCourse;
    
    /**
     * 教材
     */
    private Set textbooks = new HashSet();
    
    /**
     * 参考书
     */
    private String referenceBooks;
    
    /**
     * 案例
     */
    private String cases;
    
    /** 授课语言类型 */
    private TeachLangType teachLangType;
    
    /**
     * 是否挂牌
     */
    private Boolean isGuaPai;
    
    /**
     * 课程要求备注
     */
    private String requireRemark;
    
    /**
     * 是否需要按照教师进行评教
     */
    private Boolean evaluateByTeacher;
    /**
     * 课程种类
     */
    private CourseCategory courseCategory = new CourseCategory(CourseCategory.COMMON);
    
    public TaskRequirement() {
    }
    
    /**
     * @see java.lang.Object#clone() 仅仅作为新的教学任务复制之用
     */
    public Object clone() {
        TaskRequirement require = new TaskRequirement();
        require.setCases(getCases());
        require.setTeachLangType(getTeachLangType());
        require.setIsGuaPai(getIsGuaPai());
        require.setReferenceBooks(getReferenceBooks());
        require.getTextbooks().addAll(getTextbooks());
        require.setRoomConfigType(getRoomConfigType());
        require.setRequireRemark(getRequireRemark());
        require.setEvaluateByTeacher(getEvaluateByTeacher());
        return require;
    }
    
    /**
     * 教学任务要求的缺省要求
     * 
     * @return
     */
    public static TaskRequirement getDefault() {
        TaskRequirement require = new TaskRequirement();
        require.isGuaPai = new Boolean(false);
        require.roomConfigType = new ClassroomType(ClassroomType.COMMON);
        require.evaluateByTeacher = Boolean.FALSE;
        require.teachLangType = new TeachLangType(TeachLangType.CHINESE);
        return require;
    }
    
    /**
     * @return Returns the cases.
     */
    public String getCases() {
        return cases;
    }
    
    /**
     * @param cases The cases to set.
     */
    public void setCases(String cases) {
        this.cases = cases;
    }
    
    /**
     * @return Returns the isGuaPai.
     */
    public Boolean getIsGuaPai() {
        return isGuaPai;
    }
    
    /**
     * @param isGuaPai The isGuaPai to set.
     */
    public void setIsGuaPai(Boolean isGuaPai) {
        this.isGuaPai = isGuaPai;
    }
    
    /**
     * @return Returns the referenceBooks.
     */
    public String getReferenceBooks() {
        return referenceBooks;
    }
    
    /**
     * @param referenceBooks The referenceBooks to set.
     */
    public void setReferenceBooks(String referenceBooks) {
        this.referenceBooks = referenceBooks;
    }
    
    /**
     * @return Returns the roomConfigType.
     */
    public ClassroomType getRoomConfigType() {
        return roomConfigType;
    }
    
    /**
     * @param roomConfigType The roomConfigType to set.
     */
    public void setRoomConfigType(ClassroomType roomConfigType) {
        this.roomConfigType = roomConfigType;
    }
    
    public Set getTextbooks() {
        return textbooks;
    }
    
    public void setTextbooks(Set textbooks) {
        this.textbooks = textbooks;
    }
    
    /**
     * @return Returns the capacityOfCourse.
     */
    public Integer getCapacityOfCourse() {
        return capacityOfCourse;
    }
    
    /**
     * @param capacityOfCourse The capacityOfCourse to set.
     */
    public void setCapacityOfCourse(Integer capacityOfCourse) {
        this.capacityOfCourse = capacityOfCourse;
    }
    
    /**
     * @return Returns the requireRemark.
     */
    public String getRequireRemark() {
        return requireRemark;
    }
    
    /**
     * @param requireRemark The requireRemark to set.
     */
    public void setRequireRemark(String requireRemark) {
        this.requireRemark = requireRemark;
    }
    
    public Boolean getEvaluateByTeacher() {
        return evaluateByTeacher;
    }
    
    public void setEvaluateByTeacher(Boolean evaluateByTeacher) {
        this.evaluateByTeacher = evaluateByTeacher;
    }
    
    /**
     * @return the teachLangType
     */
    public TeachLangType getTeachLangType() {
        return teachLangType;
    }
    
    /**
     * @param teachLangType the teachLangType to set
     */
    public void setTeachLangType(TeachLangType teachLangType) {
        this.teachLangType = teachLangType;
    }

    
    public CourseCategory getCourseCategory() {
        return courseCategory;
    }

    
    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }
    
}
