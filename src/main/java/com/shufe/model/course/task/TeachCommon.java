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
 * chaostone             2006-1-6            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.task;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.model.Component;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.plan.TeachPlan;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 涉及到学生或与学生相关的教学信息的公共部分
 * 
 * @author chaostone
 * 
 */
public class TeachCommon implements Component {
    
    /**
     * 所在年级
     */
    private String enrollTurn;
    
    /**
     * 学生类别
     */
    private StudentType stdType;
    
    /**
     * 部门
     */
    private Department depart = new Department();
    
    /**
     * 专业
     */
    private Speciality speciality = new Speciality();
    
    /**
     * 专业方向
     */
    private SpecialityAspect aspect = new SpecialityAspect();
    
    /**
     * 教学日历
     */
    private TeachCalendar calendar = new TeachCalendar();
    
    public TeachCommon() {
    }
    
    public TeachCommon(TeachPlan plan) {
        this.enrollTurn = plan.getEnrollTurn();
        this.stdType = plan.getStdType();
        this.depart = plan.getDepartment();
        this.speciality = plan.getSpeciality();
        this.aspect = plan.getAspect();
    }
    
    public SpecialityAspect getAspect() {
        return aspect;
    }
    
    public void setAspect(SpecialityAspect aspect) {
        this.aspect = aspect;
    }
    
    public Department getDepart() {
        return depart;
    }
    
    public void setDepart(Department depart) {
        this.depart = depart;
    }
    
    public String getEnrollTurn() {
        return enrollTurn;
    }
    
    public void setEnrollTurn(String enrollTurn) {
        this.enrollTurn = enrollTurn;
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
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(30);
        if (null != enrollTurn)
            buffer.append(" " + enrollTurn);
        if (null != stdType)
            buffer.append(" " + stdType.getName());
        if (null != depart)
            buffer.append(" " + depart.getName());
        if (null != speciality && StringUtils.isNotEmpty(speciality.getName()))
            buffer.append(" " + speciality.getName());
        if (null != aspect && StringUtils.isNotEmpty(aspect.getName()))
            buffer.append(" " + aspect.getName());
        return buffer.toString();
    }
    
}
