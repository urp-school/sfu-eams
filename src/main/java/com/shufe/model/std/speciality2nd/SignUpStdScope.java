//$Id: SignUpStdScope.java,v 1.1 2007-3-26 下午10:20:23 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-26         Created
 *  
 ********************************************************************************/

package com.shufe.model.std.speciality2nd;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;

public class SignUpStdScope extends LongIdObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = 813785902198831867L;
    
    /** 学生类别 */
    private StudentType stdType;
    
    /** 入学年份 */
    private String enrollTurn;
    
    /** 院系部门 */
    private Department department;
    
    /** 专业 */
    private Speciality speciality;
    
    /** 专业方向 */
    private SpecialityAspect aspect;
    
    /** 双专业报名设置 */
    private SignUpSetting setting;
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public String getEnrollTurn() {
        return enrollTurn;
    }
    
    public void setEnrollTurn(String enrollYear) {
        this.enrollTurn = enrollYear;
    }
    
    public SignUpSetting getSetting() {
        return setting;
    }
    
    public void setSetting(SignUpSetting signUpSetting) {
        this.setting = signUpSetting;
    }
    
    public Speciality getSpeciality() {
        return speciality;
    }
    
    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
    
    public SpecialityAspect getAspect() {
        return aspect;
    }
    
    public void setAspect(SpecialityAspect specialityAspect) {
        this.aspect = specialityAspect;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public boolean inScope(Student std) {
        if (null != getStdType()) {
            if (!(getStdType().getId()).equals(std.getType().getId())) {
                return false;
            }
        }
        if (null != getEnrollTurn()) {
            if (!getEnrollTurn().equals(std.getEnrollYear())) {
                return false;
            }
        }
        if (null != getDepartment()) {
            if (!getDepartment().equals(std.getDepartment())) {
                return false;
            }
        }
        if (null != getSpeciality()) {
            if (!ObjectUtils.equals(getSpeciality(), std.getFirstMajor())) {
                return false;
            }
        }
        if (null != getAspect()) {
            if (!ObjectUtils.equals(getAspect(), std.getFirstAspect())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean isSame(Object object) {
        if (!(object instanceof SignUpStdScope)) {
            return false;
        }
        SignUpStdScope rhs = (SignUpStdScope) object;
        return new EqualsBuilder().append(getAspect(), rhs.getAspect()).append(getEnrollTurn(),
                rhs.getEnrollTurn()).append(getSpeciality(), rhs.getSpeciality()).append(
                getDepartment(), rhs.getDepartment()).append(getStdType(), rhs.getStdType())
                .isEquals();
    }
    
}
