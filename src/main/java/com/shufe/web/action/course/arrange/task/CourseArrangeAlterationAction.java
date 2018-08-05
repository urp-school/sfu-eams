//$Id: CourseArrangeAlterationAction.java,v 1.1 2008-3-3 下午02:30:50 zhouqi Exp $
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
 * zhouqi              2008-3-3         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.arrange.task;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.course.arrange.task.CourseArrangeAlteration;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * @author zhouqi
 */
public class CourseArrangeAlterationAction extends CalendarRestrictionSupportAction {
    
    /**
     * 调课记录首页
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
        setCalendarDataRealm(request, hasStdType);
        return forward(request);
    }
    
    /**
     * 调课记录查询
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
        Long calendarId = getLong(request, "calendar.id");
        String teacherName = get(request, "teacherName");
        Date alterationBeginAt = RequestUtils.getDate(request, "alterationBeginAt");
        Date alterationEndAt = RequestUtils.getDate(request, "alterationEndAt");
        EntityQuery query = new EntityQuery(CourseArrangeAlteration.class, "alteration");
        populateConditions(request, query);
        query.add(new Condition("alteration.task.calendar.id = (:calendarId)", calendarId));
        if (StringUtils.isNotEmpty(teacherName)) {
            query.join("alteration.task.arrangeInfo.teachers", "teacher");
            query.add(new Condition("teacher.name like (:name)", teacherName));
        }
        if (null != alterationBeginAt) {
            query
                    .add(new Condition(
                            "to_date(to_char(alteration.alterationAt, 'yyyy-MM-dd'), 'yyyy-MM-dd') >= (:alterationBeginDate)",
                            alterationBeginAt));
        }
        if (null != alterationEndAt) {
            query
                    .add(new Condition(
                            "to_date(to_char(alteration.alterationAt, 'yyyy-MM-dd'), 'yyyy-MM-dd') <= (:alterationEndDate)",
                            alterationEndAt));
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "alterations", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long alterationId = getLong(request, "alterationId");
        CourseArrangeAlteration alteration = (CourseArrangeAlteration) utilService.load(
                CourseArrangeAlteration.class, alterationId);
        request.setAttribute("alteration", alteration);
        return forward(request);
    }
    
}
