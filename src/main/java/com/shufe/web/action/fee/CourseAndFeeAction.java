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
 * chaostone            2006-11-28          Created
 * zq                   2007-10-17          修复了search()方法中的大类查询。
 ********************************************************************************/

package com.shufe.web.action.fee;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.std.Student;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 学生选课和收费信息
 * 
 * @author chaostone
 */
public class CourseAndFeeAction extends CalendarRestrictionSupportAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 查询学生
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
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        QueryRequestSupport.populateConditions(request, entityQuery, "student.type.id");
        entityQuery.join("left", "student.firstMajor", "firstMajor");
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        Long stdTypeId = getLong(request, "student.type.id");
        if (stdTypeId == null) {
            stdTypeId = getLong(request, "calendar.studentType.id");
        }
        restrictionHelper.addStdTypeTreeDataRealm(entityQuery, stdTypeId, "student.type.id", request);
        entityQuery.add(new Condition(
                "student.type in (:stdTypes) and student.department in(:departs)", getStdTypesOf(
                        stdTypeId, request), getDeparts(request)));
        addCollection(request, "stdUsers", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 查询学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        Long stdId = getLong(request, "std.id");
        EntityQuery entityQuery = new EntityQuery(CourseTake.class, "take");
        entityQuery.add(new Condition("take.student.id=" + stdId));
        entityQuery.add(new Condition("take.task.calendar.id=" + calendarId));
        Collection courseTakes = utilService.search(entityQuery);
        addCollection(request, "courseTakes", courseTakes);
        
        entityQuery = new EntityQuery(FeeDetail.class, "fee");
        entityQuery.add(new Condition("fee.std.id=" + stdId));
        entityQuery.add(new Condition("fee.calendar.id=" + calendarId));
        Collection fees = utilService.search(entityQuery);
        addCollection(request, "fees", fees);
        Student std = (Student) utilService.get(Student.class, stdId);
        request.setAttribute("std", std);
        return forward(request);
    }
}
