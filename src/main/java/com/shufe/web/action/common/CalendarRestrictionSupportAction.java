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
 * chaostone             2006-1-12            Created
 * chaostone             2006-3-8           fixed bug in setCalendarDataRealm
 *  
 ********************************************************************************/

package com.shufe.web.action.common;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.helper.TeachCalendarHelper;

/**
 * 日历和数据权限支持类
 * 
 * @author chaostone
 */
public class CalendarRestrictionSupportAction extends RestrictionSupportAction {
    
    public static final String CALENDAR_STDTYPES_KEY = "calendarStdTypes";
    
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
        return forward(request);
    }
    
    /**
     * 1)设置数据权限和教学日历<br>
     * 2)设置教学日历内包含的学生类别
     */
    public void setCalendarDataRealm(HttpServletRequest request, int realmScope) throws Exception {
        setDataRealm(request, realmScope);
        setCalendar(request, null);
        Object obj = getAttribute(request, "studentType");
        if (obj == null || StringUtils.isEmpty(obj.toString())) {
            return;
        }
        StudentType stdType = (StudentType) obj;
        // 与教学日历相关的子类学生类别
        addSingleParameter(request, CALENDAR_STDTYPES_KEY, // 该值为calendarStdTypes
                getCalendarStdTypesOf(stdType.getId(), request));
    }
    
    /**
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
    
    /**
     * 返回在权限内的指定学生类别及其子类别的列表串.
     * 
     * @param parentStdTypeId
     * @param moduleName
     * @param request
     * @return
     */
    public String getCalendarStdTypeIdSeqOf(Long parentStdTypeId, HttpServletRequest request) {
        List rs = getCalendarStdTypesOf(parentStdTypeId, request);
        StringBuffer buf = new StringBuffer("");
        for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
            StudentType name = (StudentType) iterator.next();
            if (buf.length() != 0) {
                buf.append(",");
            }
            buf.append(name.getId());
        }
        return buf.toString();
    }
    
    public List getCalendarStdTypesOf(Long parentStdTypeId, HttpServletRequest request) {
        List calendarStdTypes = teachCalendarService.getCalendarStdTypes(parentStdTypeId);
        List stdTypes = getStdTypes(request);
        List rs = (List) CollectionUtils.intersection(calendarStdTypes, stdTypes);
        Collections.sort(rs, new PropertyComparator("code"));
        return rs;
    }
    
    public TeachCalendarService getTeachCalendarService() {
        return teachCalendarService;
    }
    
    public void setTeachCalendarService(TeachCalendarService calendarService) {
        this.teachCalendarService = calendarService;
    }
    
}
