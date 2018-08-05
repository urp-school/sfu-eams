//$Id: SubstitutionCourse.java Apr 16, 2008 9:34:35 PM chaostone Exp $
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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      Apr 16, 2008  Created
 *  
 ********************************************************************************/
package com.ekingstar.eams.teach.program.major;

import java.util.Collection;

import com.ekingstar.commons.model.LongIdEntity;
import com.ekingstar.eams.system.baseinfo.Department;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.teach.program.SubstituteCourse;
import com.shufe.model.system.baseinfo.Course;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;

/**
 * 专业替代课程.
 * 
 * @author new
 * 
 */
public interface MajorSubstituteCourse extends SubstituteCourse, LongIdEntity {
    
    public StudentType getStdType();
    
    public void setStdType(StudentType stdType);
    
    public String getEnrollTurn();
    
    public void setEnrollTurn(String enrollTurn);
    
    public Speciality getMajor();
    
    public void setMajor(Speciality major);
    
    public Department getDepartment();
    
    public void setDepartment(Department department);
    
    public SpecialityAspect getMajorField();
    
    public void setMajorField(SpecialityAspect majorField);
    
    public String getRemark();
    
    public void setRemark(String remark);
    
    public void addOrigin(Course course);
    
    public void addOrigin(Collection courses);
    
    public void addSubstitute(Course course);
    
    public void addSubstitute(Collection courses);
}
