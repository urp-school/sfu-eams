//$Id: DefaultCoursePlan.java 2008-4-17 下午06:22:24 cheneystar Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2008. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author cheneystar
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============   ============  ============
 * cheneystar     2008-11-07  Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.teach.program.major.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.User;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;

public class MajorSubstituteCourse extends LongIdObject implements
        com.ekingstar.eams.teach.program.major.MajorSubstituteCourse {
    
    private static final long serialVersionUID = 5820298588618272410L;
    
    private StudentType stdType;
    
    private String enrollTurn;
    
    private Speciality major;
    
    private Department department;
    
    private SpecialityAspect majorField;
    
    /** 被替代的课程 */
    private Set origins = new HashSet();
    
    /** 已替代的课程 */
    private Set substitutes = new HashSet();
    
    /** 制定时间 */
    private Date createAt;
    
    /** 修改时间 */
    private Date modifyAt;
    
    private User operateBy;
    
    /** 备注 */
    private String remark;
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public String getEnrollTurn() {
        return enrollTurn;
    }
    
    public void setEnrollTurn(String enrollTurn) {
        this.enrollTurn = enrollTurn;
    }
    
    public Speciality getMajor() {
        return major;
    }
    
    public void setMajor(Speciality major) {
        this.major = major;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public SpecialityAspect getMajorField() {
        return majorField;
    }
    
    public void setMajorField(SpecialityAspect majorField) {
        this.majorField = majorField;
    }
    
    public Set getOrigins() {
        return origins;
    }
    
    public void setOrigins(Set origins) {
        this.origins = origins;
    }
    
    public Set getSubstitutes() {
        return substitutes;
    }
    
    public void setSubstitutes(Set substitutes) {
        this.substitutes = substitutes;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public Date getModifyAt() {
        return modifyAt;
    }
    
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public void addOrigin(Course course) {
        this.origins.add(course);
    }
    
    public void addOrigin(Collection courses) {
        this.origins.addAll(courses);
    }
    
    public void addSubstitute(Course course) {
        this.substitutes.add(course);
    }
    
    public void addSubstitute(Collection courses) {
        this.substitutes.addAll(courses);
    }
    
    public User getOperateBy() {
        return operateBy;
    }
    
    public void setOperateBy(User operateBy) {
        this.operateBy = operateBy;
    }
}
