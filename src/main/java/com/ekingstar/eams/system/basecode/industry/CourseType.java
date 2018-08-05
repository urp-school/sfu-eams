//$Id: CourseType.java,v 1.2 2006/09/29 11:52:02 duanth Exp $
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
 * chaostone             2005-9-7         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.system.basecode.industry;

import com.ekingstar.eams.system.basecode.BaseCode;

/**
 * 课程类别代码 对应数据库表BZHB_KCLB_T
 * 
 * @author chaostone 2005-9-7
 */
public class CourseType extends BaseCode implements Comparable {
    
    private static final long serialVersionUID = 8232522018765348618L;
    
    public static final Long PUBLIC_COURSID = new Long(93);
    
    /** 上级类别 */
    private CourseType superType;
    
    /** 课程类别显示优先级 */
    private Float priority;
    
    /** 是否模块课课程类别 */
    private Boolean isModuleType;
    
    /** 是否必修课课程类别 */
    private Boolean isCompulsory;
    
    /** 是否是学位课 */
    private Boolean isDegree;
    
    /** 是否实践课 */
    private Boolean isPractice;
    
    public CourseType() {
        super();
    }
    
    public CourseType(Long id) {
        super(id);
    }
    
    public Float getPriority() {
        return priority;
    }
    
    public void setPriority(Float priority) {
        this.priority = priority;
    }
    
    public int compareTo(Object object) {
        CourseType myClass = (CourseType) object;
        return (getPriority().compareTo(myClass.getPriority()));
    }
    
    public CourseType getSuperType() {
        return superType;
    }
    
    public void setSuperType(CourseType superType) {
        this.superType = superType;
    }
    
    public Boolean getIsModuleType() {
        return isModuleType;
    }
    
    public Boolean getIsCompulsory() {
        return isCompulsory;
    }
    
    public Boolean getIsDegree() {
        return isDegree;
    }
    
    public Boolean getIsPractice() {
        return isPractice;
    }
    
    public void setIsModuleType(Boolean isModuleType) {
        this.isModuleType = isModuleType;
    }
    
    public void setIsCompulsory(Boolean isCompulsory) {
        this.isCompulsory = isCompulsory;
    }
    
    public void setIsDegree(Boolean isDegree) {
        this.isDegree = isDegree;
    }
    
    public void setIsPractice(Boolean isPractice) {
        this.isPractice = isPractice;
    }
}
