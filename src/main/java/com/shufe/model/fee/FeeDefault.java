//$Id: FeeDefault.java,v 1.4 2006/10/12 12:20:13 duanth Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-7-11         Created
 *  
 ********************************************************************************/

package com.shufe.model.fee;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;

/**
 * 收费缺省值
 * 
 * @author chenweixiong,chaostone
 * 
 */
public class FeeDefault extends LongIdObject {
    
    private static final long serialVersionUID = -3198565526508585146L;
    
    /** 学生类别 */
    private StudentType studentType;
    
    /** 系 */
    private Department department = new Department();
    
    /** 所属的专业 */
    private Speciality speciality = new Speciality();
    
    /** 收费类型 */
    private FeeType type = new FeeType();
    
    /** 对应的值 */
    private Integer value = new Integer(0);
    
    /** remark */
    private String remark;
    
    /**
     * @return Returns the value.
     */
    public Integer getValue() {
        return value;
    }
    
    /**
     * @param value
     *            The value to set.
     */
    public void setValue(Integer defaultValue) {
        this.value = defaultValue;
    }
    
    /**
     * @return Returns the type.
     */
    public FeeType getType() {
        return type;
    }
    
    /**
     * @param type
     *            The type to set.
     */
    public void setType(FeeType feeType) {
        this.type = feeType;
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
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public Speciality getSpeciality() {
        return speciality;
    }
    
    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
    
}
