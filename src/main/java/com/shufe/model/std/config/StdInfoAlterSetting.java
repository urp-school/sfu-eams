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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.model.std.config;

import com.ekingstar.eams.system.baseinfo.StudentType;

/**
 * 允许修改的个人信息设定
 * 
 * @author chaostone
 * 
 */
public class StdInfoAlterSetting {
    
    private Long id;
    
    /**
     * 使用的学生类别
     */
    private StudentType stdType;
    
    /**
     * 基础信息是否容许修改
     */
    private Boolean isBasicInfoAlterable;
    
    /**
     * 基础信息是否容许修改
     */
    private Boolean isEnrollInfoAlterable;
    
    /**
     * 学位信息是否容许修改
     */
    private Boolean isDegreeInfoAlterable;
    
    public Boolean getIsBasicInfoAlterable() {
        return isBasicInfoAlterable;
    }
    
    public void setIsBasicInfoAlterable(Boolean isBasicInfoAlterable) {
        this.isBasicInfoAlterable = isBasicInfoAlterable;
    }
    
    public Boolean getIsDegreeInfoAlterable() {
        return isDegreeInfoAlterable;
    }
    
    public void setIsDegreeInfoAlterable(Boolean isDegreeInfoAlterable) {
        this.isDegreeInfoAlterable = isDegreeInfoAlterable;
    }
    
    public Boolean getIsEnrollInfoAlterable() {
        return isEnrollInfoAlterable;
    }
    
    public void setIsEnrollInfoAlterable(Boolean isEnrollInfoAlterable) {
        this.isEnrollInfoAlterable = isEnrollInfoAlterable;
    }
    
    public StudentType getStdType() {
        return stdType;
    }
    
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    /**
     * @return id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param id
     *            要设置的 id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
}
