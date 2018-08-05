// $Id: Activity.java,v 1.3 2008-5-21 15:06:30 maple Exp $
/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved. This source code is the property of
 * KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is
 * not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2008-5-21         Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 学期排课发布 对应数据表是 JXRW_PKJGFB_T
 * 
 * @author cheneystar 2008-5-21
 */
public class CourseArrangeSwitch extends LongIdObject {
    
    private static final long serialVersionUID = -1830248177687127758L;
    
    /** 学期* */
    private TeachCalendar calendar;
    
    /** 是否发布* */
    private Boolean isPublished;
    
    public CourseArrangeSwitch() {
    }
    public CourseArrangeSwitch(TeachCalendar teachCalendar) {
        this.isPublished = Boolean.FALSE;
        this.calendar = teachCalendar;
    }
    
    public TeachCalendar getCalendar() {
        return calendar;
    }
    
    public void setCalendar(TeachCalendar calendar) {
        this.calendar = calendar;
    }
    
    public Boolean getIsPublished() {
        return isPublished;
    }
    
    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }
    
}
