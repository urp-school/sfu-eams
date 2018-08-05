//$Id: RoomPriceCatalogue.java,v 1.1 2007-3-10 下午06:05:57 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-10         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.resource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.shufe.model.system.baseinfo.Department;

/**
 * 教室级差价目表
 * 
 * @author chaostone
 * 
 */
public class RoomPriceCatalogue extends LongIdObject {
    
    private static final long serialVersionUID = -1874263595170393296L;
    
    /** 校区 */
    private SchoolDistrict schoolDistrict;
    
    private Department department;
    
    private String remark;
    
    private Date publishedOn;
    
    private Set prices = new HashSet();
    
    /** 审核部门 */
    private Set auditDeparts = new HashSet();
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public Set getPrices() {
        return prices;
    }
    
    public void setPrices(Set prices) {
        this.prices = prices;
    }
    
    public Date getPublishedOn() {
        return publishedOn;
    }
    
    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Set getAuditDeparts() {
        return auditDeparts;
    }
    
    public void setAuditDeparts(Set auditDeparts) {
        this.auditDeparts = auditDeparts;
    }
    
    public SchoolDistrict getSchoolDistrict() {
        return schoolDistrict;
    }
    
    public void setSchoolDistrict(SchoolDistrict schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }
    
}
