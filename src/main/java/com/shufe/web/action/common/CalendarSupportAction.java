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
 * chaostone             2006-11-4            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.common;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.helper.TeachCalendarHelper;

public class CalendarSupportAction extends DispatchBasicAction {
    
    /**
     * 教学日历服务对象
     */
    protected TeachCalendarService teachCalendarService;
    
    /**
     * 
     * 加载时间日历 根据学生类别查找最近的教学日历.<br>
     * 如果cookie中保留,则采用cookie中的日历.<br>
     * 系统检查cookie中的学生类别是否在权限范围,主要检查"calendar.studentType.id"的值,<br>
     * 是否在request.getAttribute("stdTypeList")中<br>
     * 
     * @param request
     * @param stdType
     */
    public void setCalendar(HttpServletRequest request, StudentType stdType) {
        new TeachCalendarHelper(teachCalendarService, utilService).setCalendar(request, stdType);
    }
    
    public TeachCalendar setNewCalendar(HttpServletRequest request) {
        return new TeachCalendarHelper(teachCalendarService, utilService).setNewCalendar(request);
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
}
