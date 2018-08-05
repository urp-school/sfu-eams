//$Id: EvaluateSwitchService.java,v 1.1 2007-6-4 10:07:17 Administrator Exp $
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

package com.shufe.service.quality.evaluate;

import java.util.List;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.quality.evaluate.EvaluateSwitch;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;

public interface EvaluateSwitchService {
    
    /**
     * 查询一个评教开关
     * 
     * @param calendar
     * @param stdType
     *            上课学生类别
     * @param depart
     *            开课院系
     * @return
     */
    public EvaluateSwitch getEvaluateSwitch(TeachCalendar calendar, StudentType stdType,
            Department depart);
    
    /**
     * 查询现在开放评教的教学日历
     * 
     * @return
     */
    public List getOpenCalendars();
    
    public List getOpenCalendars(boolean isSwitch);
}
