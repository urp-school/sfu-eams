//$Id: CourseTableAction.java,v 1.13 2007/01/23 01:14:10 duanth Exp $
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
 * chaostone            2005-12-14          Created
 * zq                   2007-09-13          在buildQuery()中，增加了addStdTypeTreeDataRealm(...)
 *                                          语句，因学生类别权限不能相交成空集，故报错
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.arrange.task.CourseTableCheck;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.course.arrange.task.CourseTableCheckService;
import com.shufe.service.std.stat.StdStatService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 管理人员查看课程核对 <br>
 * 
 * @author chaostone 2005-12-14
 */
public class CourseTableCheckAction extends CalendarRestrictionSupportAction {
    
    private CourseTableCheckService courseTableCheckService;
    
    private StdStatService stdStatService;
    
    /**
     * 管理人员查看课表核对入口（主界面）
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
     * 查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        addCollection(request, "courseTableChecks", utilService.search(query));
        return forward(request);
    }
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(CourseTableCheck.class, "check");
        populateConditions(request, query, "check.std.type.id,check.std.secondMajor.department.id");
        String depart = "check.std.department.id";
        Long majorTypeId = getLong(request, "majorTypeId");
        if (ObjectUtils.equals(majorTypeId, new Long(2))) {
            depart = "check.std.secondMajor.department.id";
        }
        StdSearchHelper.addSpecialityConditon(request, query, "check.std");
        query
                .add(new Condition("check.calendar.id=(:calendarId)", getLong(request,
                        "calendar.id")));
        DataRealmUtils.addDataRealms(query, new String[] { "check.std.type.id", depart },
                getDataRealmsWith(getLong(request, "check.std.type.id"), request));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return query;
    }
    
    public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
                "calendar.id"));
        List stats = courseTableCheckService.statCheckByDepart(calendar, getDataRealm(request),
                new MajorType(getLong(request, "majorTypeId")));
        request.setAttribute("stats", stats);
        DataRealm realm = getDataRealm(request);
        realm.setStudentTypeIdSeq(getStdTypeIdSeqOf(calendar.getStudentType().getId(), request));
        request.setAttribute("onCampusStats", stdStatService.statOnCampusByStdTypeDepart(realm));
        return forward(request);
    }
    
    /**
     * 学生确认自己的课表,之后返回课表界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirmCourseTable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long courseTableCheckId = getLong(request, "courseTableCheckId");
        CourseTableCheck check = (CourseTableCheck) utilService.get(CourseTableCheck.class,
                courseTableCheckId);
        Student std = getStudentFromSession(request.getSession());
        if (check.getStd().equals(std)) {
            check.setIsConfirm(Boolean.TRUE);
            check.setConfirmAt(new Date(System.currentTimeMillis()));
            utilService.saveOrUpdate(check);
        }
        return redirect(request, new Action("courseTableForStd", "stdHome"), "info.action.success");
    }
    
    public void setCourseTableCheckService(CourseTableCheckService courseTableCheckService) {
        this.courseTableCheckService = courseTableCheckService;
    }
    
    public void setStdStatService(StdStatService stdStatService) {
        this.stdStatService = stdStatService;
    }
    
}
