//$Id: Modulus.java,v 1.1 2006/08/02 00:52:56 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-17         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;

public class Modulus extends LongIdObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = 8395775742716824383L;
    
    private StudentType studentType; // 学生类别
    
    private Float modulusValue; // 系数值
    
    private Department department = new Department(); // 部门或者院系
    
    private String remark; // 备注
    
    /**
     * @return Returns the department.
     */
    public Department getDepartment() {
        return department;
    }
    
    /**
     * @param department
     *            The department to set.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    /**
     * @return Returns the modulusValue.
     */
    public Float getModulusValue() {
        return modulusValue;
    }
    
    /**
     * @param modulusValue
     *            The modulusValue to set.
     */
    public void setModulusValue(Float modulusValue) {
        this.modulusValue = modulusValue;
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
}
