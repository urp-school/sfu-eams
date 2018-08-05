//$Id: GradeInputSwitch.java,v 1.1 2008-1-4 下午04:25:00 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-1-4         	Created
 *  
 ********************************************************************************/

package com.shufe.model.course.grade;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author zhouqi
 */
public class GradeInputSwitch extends LongIdObject {
    
    private static final long serialVersionUID = 6765368922449105678L;
    
    /** 教学日历 */
    private TeachCalendar calendar;
    
    /** 开始时间 */
    private Date startAt;
    
    /** 关闭时间 */
    private Date endAt;
    
    /** 允许录入成绩类型 */
    private Set gradeTypes = new HashSet();
    
    /** 成绩录入开关 */
    private Boolean isOpen = Boolean.FALSE;
    /**
	 * 检查该评教开关是否开放
	 * 
	 * @param date
	 * @return
	 */
	public boolean checkOpen(Date date) {
		if (null == getStartAt() || null == getEndAt())
			return false;
		if (date.after(getEndAt()) || getStartAt().after(date))
			return false;
		else {
			return this.isOpen.booleanValue();
		}
	}
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Date getEndAt() {
        return endAt;
    }
    
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }
    
    public Boolean getIsOpen() {
        return isOpen;
    }
    
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    public Date getStartAt() {
        return startAt;
    }
    
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }
    
    public Set getGradeTypes() {
        return gradeTypes;
    }
    
    public void setGradeTypes(Set gradeTypes) {
        this.gradeTypes = gradeTypes;
    }
}
