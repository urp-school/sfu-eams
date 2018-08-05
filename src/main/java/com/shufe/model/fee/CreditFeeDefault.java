//$Id: CreditFeeDefault.java,v 1.1 2007-6-18 下午02:59:12 chaostone Exp $
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
 * chenweixiong              2007-6-18         Created
 *  
 ********************************************************************************/

package com.shufe.model.fee;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;

/**
 * 学分收费标准
 * 
 * @author chaostone
 * 
 */
public class CreditFeeDefault extends LongIdObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8094625916977015539L;
    
    /** 学生类别 */
    private StudentType stdType;
    
    /** 课程类别 */
    private CourseType courseType;
    
    /** 收费金额 */
    private Float value;
    
    public CourseType getCourseType() {
        return courseType;
    }
    
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    public Float getValue() {
        return value;
    }
    
    public void setValue(Float value) {
        this.value = value;
    }
    
}
