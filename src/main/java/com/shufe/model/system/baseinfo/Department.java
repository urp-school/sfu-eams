//$Id: Department.java,v 1.1 2006/08/02 00:53:00 duanth Exp $
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
 * chaostone             2005-9-6         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.baseinfo;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.baseinfo.model.BaseInfo;

/**
 * 部门院系
 * 
 * @author chaostone 2005-9-7
 */
public class Department extends BaseInfo implements Comparable,
        com.ekingstar.eams.system.baseinfo.Department {
    
    private static final long serialVersionUID = -887667041971609502L;
    
    /** 全校 */
    public static Long SCHOOLID = new Long(1);
    
    /** 体教部 */
    public static final Long PEDEPARTMENTID = new Long(41);
    
    /** 是否是院系 */
    private Boolean isCollege;
    
    /** 是否开课 */
    private Boolean isTeaching;
    
    /** 相关的学生类别 */
    private Set stdTypes = new HashSet(0);
    
    /** 专业列表 */
    private Set specialitis = new HashSet();
    
    /** 该院系的教师 */
    private Set teachers = new HashSet();
    
    /** 部门可用的教室 */
    private Set classrooms = new HashSet();
    
    /** 设立时间 */
    private Date dateEstablished;
    
    public Department() {
        super();
    }
    
    public String getDepartNo() {
        return "";
    }
    
    public Set getSubDeparts() {
        return null;
    }
    
    public Integer getLevel() {
        return null;
    }
    
    public com.ekingstar.eams.system.baseinfo.Department getSuperDepart() {
        return null;
    }
    
    public Department(Long id) {
        super(id);
    }
    
    public Date getDateEstablished() {
        return dateEstablished;
    }
    
    public void setDateEstablished(Date dateEstablished) {
        this.dateEstablished = dateEstablished;
    }
    
    public Set getSpecialitis() {
        return specialitis;
    }
    
    public void setSpecialitis(Set specialitis) {
        this.specialitis = specialitis;
    }
    
    public Boolean getIsCollege() {
        return isCollege;
    }
    
    public void setIsCollege(Boolean isCollege) {
        this.isCollege = isCollege;
    }
    
    /**
     * @return Returns the isTeaching.
     */
    public Boolean getIsTeaching() {
        return isTeaching;
    }
    
    /**
     * @param isTeaching
     *            The isTeaching to set.
     */
    public void setIsTeaching(Boolean isTeaching) {
        this.isTeaching = isTeaching;
    }
    
    public boolean selfCheck() {
        return true;
    }
    
    /**
     * @return Returns the teachers.
     */
    public Set getTeachers() {
        return teachers;
    }
    
    /**
     * @param teachers
     *            The teachers to set.
     */
    public void setTeachers(Set teachers) {
        this.teachers = teachers;
    }
    
    /**
     * @return Returns the classrooms.
     */
    public Set getClassrooms() {
        return classrooms;
    }
    
    /**
     * @param classrooms
     *            The classrooms to set.
     */
    public void setClassrooms(Set classrooms) {
        this.classrooms = classrooms;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name",
                this.getName()).append("id", this.getId()).toString();
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof Department)) {
            return false;
        }
        Department rhs = (Department) object;
        return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
    }
    
    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object object) {
        Department rhs = (Department) object;
        return getName().compareTo(rhs.getName());
    }
    
    public void addTeacher(Teacher teacher) {
        getTeachers().remove(teacher);
    }
    
    public void removeTeacher(Teacher teacher) {
        getTeachers().remove(teacher);
    }
    
    public void addSpeciality(Speciality speciality) {
        getSpecialitis().add(speciality);
    }
    
    public void removeSpeciality(Speciality speciality) {
        getSpecialitis().remove(speciality);
    }
    
    public Set getStdTypes() {
        return stdTypes;
    }
    
    public void setStdTypes(Set stdTypes) {
        this.stdTypes = stdTypes;
    }
    
    public void addStdType(StudentType stdType) {
        getStdTypes().add(stdType);
    }
    
    public void removeStdType(StudentType stdType) {
        getStdTypes().remove(stdType);
    }
    
    public com.ekingstar.eams.system.baseinfo.Department getSuperDepart(Integer level) {
        // TODO Auto-generated method stub
        return null;
    }
}
