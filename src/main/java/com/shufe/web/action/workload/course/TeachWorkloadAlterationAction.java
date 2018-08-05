//$Id: TeachWorkloadLogAction.java,v 1.1 2008-3-4 下午01:37:12 zhouqi Exp $
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
 * zhouqi              2008-3-4         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.workload.course.TeachWorkloadAlteration;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 教学工作量日志
 * 
 * @author zhouqi
 */
public class TeachWorkloadAlterationAction extends CalendarRestrictionSupportAction {
    
    /**
     * 教学工作量日志查询
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
        Date workloadAt = RequestUtils.getDate(request, "workloadAt");
        
        EntityQuery query = new EntityQuery(TeachWorkloadAlteration.class, "alteration");
        populateConditions(request, query);
        query.add(new Condition("alteration.task.calendar.id = (:calendarId)", calendarId));
        if (null != workloadAt) {
            query
                    .add(new Condition(
                            "to_date(to_char(alteration.workloadAt, 'yyyy-MM-dd'), 'yyyy-MM-dd') = (:workloadDate)",
                            workloadAt));
        }
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "alterations", utilService.search(query));
        
        return forward(request);
    }
    
    /**
     * 查看工作量记录变动的详细情况
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
        Long alterationId = getLong(request, "alterationId");
        TeachWorkloadAlteration alteration = (TeachWorkloadAlteration) utilService.load(
                TeachWorkloadAlteration.class, alterationId);
        request.setAttribute("alteration", alteration);
        return forward(request);
    }
    
}
