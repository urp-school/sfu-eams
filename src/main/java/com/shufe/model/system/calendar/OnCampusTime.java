//$Id: OnCampusTime.java,v 1.2 2006/10/12 12:04:25 duanth Exp $
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
 * chaostone             2005-10-31         Created
 *  
 ********************************************************************************/

package com.shufe.model.system.calendar;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;

/**
 * 学生的所在年级和入学学期<br>
 * 在校日历用以表述某一年月的学生类别入学和毕业的教学日历跨度。
 * 
 * @author chaostone 2005-10-31
 */
public class OnCampusTime extends LongIdObject {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1919574585023447458L;
    
    /** 学生类别 */
    private StudentType stdType;
    
    /** 所在年级 */
    private String enrollTurn;
    
    /** 入学日历时间 */
    private TeachCalendar enrollCalendar = new TeachCalendar();
    
    /** 毕业日历时间 */
    private TeachCalendar graduateCalendar = new TeachCalendar();
    
    /**
     * @return Returns the enrollCalendar.
     */
    public TeachCalendar getEnrollCalendar() {
        return enrollCalendar;
    }
    
    /**
     * @param enrollCalendar
     *            The enrollCalendar to set.
     */
    public void setEnrollCalendar(TeachCalendar enrollCalendar) {
        this.enrollCalendar = enrollCalendar;
    }
    
    /**
     * @return Returns the enrollTurn.
     */
    public String getEnrollTurn() {
        return enrollTurn;
    }
    
    /**
     * @param enrollTurn
     *            The enrollTurn to set.
     */
    public void setEnrollTurn(String enrollTurn) {
        this.enrollTurn = enrollTurn;
    }
    
    /**
     * @return Returns the stdType.
     */
    public StudentType getStdType() {
        return stdType;
    }
    
    /**
     * @param stdType
     *            The stdType to set.
     */
    public void setStdType(StudentType stdType) {
        this.stdType = stdType;
    }
    
    /**
     * @return Returns the graduateCalendar.
     */
    public TeachCalendar getGraduateCalendar() {
        return graduateCalendar;
    }
    
    /**
     * @param graduateCalendar
     *            The graduateCalendar to set.
     */
    public void setGraduateCalendar(TeachCalendar graduateCalendar) {
        this.graduateCalendar = graduateCalendar;
    }
    
}
