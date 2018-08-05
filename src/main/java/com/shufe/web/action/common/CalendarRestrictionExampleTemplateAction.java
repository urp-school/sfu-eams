//$Id: CalendarDataRealmExampleTemplateAction.java,v 1.1 2007-5-6 上午11:23:03 chaostone Exp $
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
 *chaostone      2007-5-6         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.helper.TeachCalendarHelper;

public class CalendarRestrictionExampleTemplateAction extends RestrictionExampleTemplateAction {
    
    /**
     * 教学日历服务对象
     */
    protected TeachCalendarService teachCalendarService;
    
    /**
     * 管理信息主页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        TeachCalendar calendar = (TeachCalendar) request.getAttribute("calendar");
        if (null != calendar) {
            request.setAttribute("stdTypesOfCalendar", getStdTypesOf(calendar.getStudentType()
                    .getId(), request));
        }
        return forward(request);
    }
    
    /**
     * 设置数据权限和教学日历
     */
    public void setCalendarDataRealm(HttpServletRequest request, int realmScope) throws Exception {
        setDataRealm(request, realmScope);
        setCalendar(request, null);
    }
    
    /**
     * 
     * 加载时间日历 根据学生类别查找最近的教学日历.<br>
     * 如果cookie中保留,则采用cookie中的日历.<br>
     * 系统检查cookie中的学生类别是否在权限范围,主要检查"calendar.studentType.id"的值,<br>
     * 是否在request.getAttribute("stdTypeList")中<br>
     * 
     * @param request
     * @param stdType
     *            建议使用的学生类别
     */
    public void setCalendar(HttpServletRequest request, StudentType stdType) {
        new TeachCalendarHelper(teachCalendarService, utilService).setCalendar(request, stdType);
    }
    
    public void setTeachCalendarService(TeachCalendarService calendarService) {
        this.teachCalendarService = calendarService;
    }
}
