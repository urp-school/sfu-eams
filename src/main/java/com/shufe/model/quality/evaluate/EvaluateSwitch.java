//$Id: EvaluateSwitch.java,v 1.1 2007-6-4 9:13:08 Administrator Exp $
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
 * chenweixiong              2007-6-4         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 评教开关
 * 
 * @author chaostone
 * 
 */
public class EvaluateSwitch extends LongIdObject {
    
    private static final long serialVersionUID = 7399753713524516111L;
    
    /** 教学日历 */
    private TeachCalendar calendar;
    
    /** 课程面向学生类别 */
    private Set stdTypes = new HashSet();
    
    /** 开课院系 */
    private Set departs = new HashSet();
    
    /** 评教状态 */
    private Boolean isOpen;
    
    /** 开始时间 */
    private Date openAt;
    
    /** 关闭时间 */
    private Date closeAt;
    
    public Date getCloseAt() {
        return closeAt;
    }
    
    public void setCloseAt(Date closeTime) {
        this.closeAt = closeTime;
    }
    
    public Date getOpenAt() {
        return openAt;
    }
    
    public void setOpenAt(Date createTime) {
        this.openAt = createTime;
    }
    
    public Boolean getIsOpen() {
        return isOpen;
    }
    
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public Set getStdTypes() {
        return stdTypes;
    }
    
    public Set getDeparts() {
        return departs;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public void setStdTypes(Set stdTypes) {
        this.stdTypes = stdTypes;
    }
    
    public void setDeparts(Set departs) {
        this.departs = departs;
    }
    
    /**
     * 检查该评教开关是否开放
     * 
     * @param date
     * @return
     */
    public boolean checkOpen(Date date) {
        if (null == getOpenAt() || null == getCloseAt()) {
            return false;
        }
        if (date.after(getCloseAt()) || getOpenAt().after(date)) {
            return false;
        } else {
            return this.isOpen.booleanValue();
        }
    }
    
}
