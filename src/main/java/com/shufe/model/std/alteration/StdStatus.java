//$Id: StdStatus.java,v 1.1 2007-9-27 上午10:14:25 zhouqi Exp $
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
 * Name                Date                	Description 
 * ============        ============        	============
 * zhouqi              2007-09-27         	Created
 * zq                  2007-09-28           增加“毕业时间”属性 
 ********************************************************************************/

package com.shufe.model.std.alteration;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;

/**
 * 学生学籍变动记录
 * 
 * @author zhouqi
 */
public class StdStatus extends LongIdObject {
    
    private static final long serialVersionUID = 2006810209542942937L;
    
    /** 所在年级 */
    private String enrollYear;
    
    /** 学生类别 */
    private StudentType stdType;
    
    /** 部门 */
    private Department department;
    
    /** 专业 */
    private Speciality speciality;
    
    /** 专业方向 */
    private SpecialityAspect aspect;
    
    /** 学籍状态 */
    private StudentState state;
    
    /** 是否在校 */
    private Boolean isInSchool;
    
    /** 是否在籍 */
    private Boolean isActive;
    
    /** 毕业时间 */
    private Date graduateOn;
    
    /** 班级 */
    private AdminClass adminClass;
    
    public SpecialityAspect getAspect() {
        return aspect;
    }
    
    public void setAspect(SpecialityAspect aspect) {
        this.aspect = aspect;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public String getEnrollYear() {
        return enrollYear;
    }
    
    public void setEnrollYear(String enrollYear) {
        this.enrollYear = enrollYear;
    }
    
    public Speciality getSpeciality() {
        return speciality;
    }
    
    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public StudentState getState() {
        return state;
    }
    
    public void setState(StudentState state) {
        this.state = state;
    }
    
    public Date getGraduateOn() {
        return graduateOn;
    }
    
    public void setGraduateOn(Date graduateOn) {
        this.graduateOn = graduateOn;
    }
    
    public AdminClass getAdminClass() {
        return adminClass;
    }
    
    public void setAdminClass(AdminClass adminClass) {
        this.adminClass = adminClass;
    }
    
    public Boolean getIsInSchool() {
        return isInSchool;
    }
    
    public void setIsInSchool(Boolean isInSchool) {
        this.isInSchool = isInSchool;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
