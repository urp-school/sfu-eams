//$Id: TeachCategory.java,v 1.2 2006/12/19 13:08:40 duanth Exp $
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
 * chenweixiong              2006-3-13         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.course;

import java.sql.Date;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;

/**
 * @author Administrator
 * @deprecated 教学性质表：描述学生的上课情况.
 * 
 */
public class TeachCategory extends LongIdObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4215782354987038824L;
    
    private StudentType studentType;
    
    private CourseType courseType = new CourseType();
    
    private String code;
    
    private String name;
    
    private Date makeTime;
    
    private Date modifyTime;
    
    private Boolean status;
    
    private String remark;
    
    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code
     *            The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return Returns the courseType.
     */
    public CourseType getCourseType() {
        return courseType;
    }
    
    /**
     * @param courseType
     *            The courseType to set.
     */
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
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
    
    /**
     * @return Returns the makeTime.
     */
    public Date getMakeTime() {
        return makeTime;
    }
    
    /**
     * @param makeTime
     *            The makeTime to set.
     */
    public void setMakeTime(Date makeTime) {
        this.makeTime = makeTime;
    }
    
    /**
     * @return Returns the modifyTime.
     */
    public Date getModifyTime() {
        return modifyTime;
    }
    
    /**
     * @param modifyTime
     *            The modifyTime to set.
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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
     * @return Returns the status.
     */
    public Boolean getStatus() {
        return status;
    }
    
    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
