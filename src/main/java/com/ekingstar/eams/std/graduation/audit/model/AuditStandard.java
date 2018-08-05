//$Id: AuditStandard.java,v 1.5 2007/01/16 12:01:58 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-11         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.graduation.audit.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;

/**
 * 毕业审核标准
 * 
 * @author dell
 */
public class AuditStandard extends LongIdObject implements
        com.ekingstar.eams.std.graduation.audit.PlanAuditSetting {
    
    private static final long serialVersionUID = 6626030014033925927L;
    
    private String name;
    
    /* 学生类别 */
    private StudentType studentType;
    
    /* 备注 */
    private String remark;
    
    /* 不审核的课程类别 */
    private Set disauditCourseTypes = new HashSet(0);
    
    /* 多出学分可以冲抵学分的课程类别 */
    private Set convertableCourseTypes = new HashSet(0);
    
    /** 是否convertableCourseTypeSet内的课程类别之间可以相互抵。 */
    private Boolean isDualConvert;
    
    /** 任意选修课 */
    private CourseType publicCourseType;
    
    public Boolean getIsDualConvert() {
        return isDualConvert;
    }
    
    public void setIsDualConvert(Boolean isDualConvert) {
        this.isDualConvert = isDualConvert;
    }
    
    public com.ekingstar.eams.system.baseinfo.StudentType getStdType() {
        return null;
    }
    
    public void setStdType(com.ekingstar.eams.system.baseinfo.StudentType arg0) {
        
    }
    
    /**
     * @return 返回 convertableCourseTypes.
     */
    public Set getConvertableCourseTypes() {
        return convertableCourseTypes;
    }
    
    /**
     * @param convertableCourseTypes
     *            要设置的 convertableCourseTypes.
     */
    public void setConvertableCourseTypes(Set convertableCourseTypeSet) {
        this.convertableCourseTypes = convertableCourseTypeSet;
    }
    
    /**
     * @return 返回 disauditCourseTypes.
     */
    public Set getDisauditCourseTypes() {
        return disauditCourseTypes;
    }
    
    /**
     * @param disauditCourseTypes
     *            要设置的 disauditCourseTypes.
     */
    public void setDisauditCourseTypes(Set disauditCourseTypeSet) {
        this.disauditCourseTypes = disauditCourseTypeSet;
    }
    
    /**
     * 组装引用到的对象
     * 
     * @return
     */
    public AuditStandard populateEntity() {
        
        if (this.studentType.isVO()) {
            this.studentType = null;
        }
        return this;
    }
    
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark
     *            The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return Returns the studentType.
     */
    public StudentType getStudentType() {
        return studentType;
    }
    
    /**
     * @param studentType
     *            The studentType to set.
     */
    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 是否无需审核的课程类别
     * 
     * @param courseType
     * @return
     */
    public boolean isDisaudit(CourseType courseType) {
        if (CollectionUtils.isNotEmpty(this.getDisauditCourseTypes())) {
            return getDisauditCourseTypes().contains(courseType);
        } else {
            return false;
        }
    }
    
    /**
     * 是否多出学分可以冲抵任意选修课学分的课程类别
     * 
     * @param courseType
     * @return
     */
    public boolean isConvertable(CourseType courseType) {
        if (CollectionUtils.isNotEmpty(this.getConvertableCourseTypes())) {
            return getConvertableCourseTypes().contains(courseType);
        } else {
            return false;
        }
    }
    
    public CourseType getPublicCourseType() {
        return publicCourseType;
    }
    
    public void setPublicCourseType(CourseType publicCourseType) {
        this.publicCourseType = publicCourseType;
    }
    
    public String getAuditScheme() {
        // TODO 自动生成方法存根
        return null;
    }
    
    public boolean getIsSave() {
        // TODO 自动生成方法存根
        return false;
    }
    
    public void setAuditScheme(String arg0) {
        // TODO 自动生成方法存根
        
    }
    
    public void setIsSave(boolean arg0) {
        // TODO 自动生成方法存根
        
    }
    
}
