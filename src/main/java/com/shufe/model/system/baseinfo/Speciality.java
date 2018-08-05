//$Id: Speciality.java,v 1.5 2007/01/07 06:27:11 duanth Exp $
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
 * pippo                2005-9-3            Created
 * chaostone            2005-9-13           modified
 *  
 ********************************************************************************/
package com.shufe.model.system.baseinfo;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.state.SubjectCategory;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.baseinfo.model.BaseInfo;

/**
 * 学校的专业设置基本信息.
 * 
 * @author dell,chaostone
 */
public class Speciality extends BaseInfo implements com.ekingstar.eams.system.baseinfo.Major {
    
    private static final long serialVersionUID = 1015660455436352028L;
    
    /** 所属部门 */
    private Department department = new com.shufe.model.system.baseinfo.Department();
    
    /** 所属学生类别 */
    private StudentType stdType;
    
    /** 对应的专业方向 */
    private Set aspects = new HashSet();
    
    /** 对应学科门类 */
    private SubjectCategory subjectCategory;
    
    private MajorType majorType;
    
    public MajorType getMajorType() {
        return majorType;
    }
    
    public void setMajorType(MajorType majorType) {
        this.majorType = majorType;
    }
    
    public Set getMajorFields() {
        return aspects;
    }
    
    public Speciality() {
        super();
    }
    
    public Speciality(Long id) {
        super(id);
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public Set getAspects() {
        return aspects;
    }
    
    public void setAspects(Set aspects) {
        this.aspects = aspects;
    }
    
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", this.getId())
                .append("name", this.getName()).toString();
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public SubjectCategory getSubjectCategory() {
        return subjectCategory;
    }
    
    public void setSubjectCategory(SubjectCategory subjectCategory) {
        this.subjectCategory = subjectCategory;
    }
    
    public void addSpecialityAspect(SpecialityAspect aspect) {
        getAspects().add(aspect);
    }
    
    public void removeSpecialityAspect(SpecialityAspect aspect) {
        getAspects().remove(aspect);
    }
}
