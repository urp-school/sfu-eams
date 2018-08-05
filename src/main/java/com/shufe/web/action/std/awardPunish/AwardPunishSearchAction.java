//$Id: AwardPunishSearchAction.java,v 1.1 2007-5-30 下午02:00:42 chaostone Exp $
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
 *chaostone      2007-5-30         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.awardPunish;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.shufe.model.std.Student;
import com.shufe.model.std.awardPunish.Award;
import com.shufe.model.std.awardPunish.Punishment;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class AwardPunishSearchAction extends RestrictionExampleTemplateAction {
    
    protected TeachCalendarService teachCalendarService;
    
    protected void detectAwardPunish(HttpServletRequest request) {
        Boolean isAward = getBoolean(request, "isAward");
        if (isAward == null)
            isAward = Boolean.TRUE;
        if (Boolean.TRUE.equals(isAward)) {
            setEntityClass(Award.class);
            setEntityName("award");
        } else {
            setEntityClass(Punishment.class);
            setEntityName("punish");
        }
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = buildQuery(request);
        addCollection(request, getEntityName() + "s", utilService.search(query));
        return forward(request);
    }
    
    /**
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        detectAwardPunish(request);
        EntityQuery query = new EntityQuery(getEntityClass(), getEntityName());
        query.getConditions()
                .addAll(
                        QueryRequestSupport.extractConditions(request, Student.class, "std",
                                "std.type.id"));
        populateConditions(request, query);
        String adminClassName = request.getParameter("adminClassName");
        if (StringUtils.isNotEmpty(adminClassName)) {
            query.join(getEntityName() + ".std.adminClasses", "adminClass");
            query.add(Condition.like("adminClass.name", adminClassName));
        }
        Long stdTypeId = getLong(request, "std.type.id");
        String year = get(request, "calendar.year");
        if (StringUtils.isNotEmpty(year)) {
            String term = get(request, "calendar.term");
            TeachCalendar calendar = teachCalendarService.getTeachCalendar(stdTypeId, year, term);
            if (null != calendar) {
                query.add(new Condition(getEntityName() + ".calendar = (:calendar)", calendar));
            }
        }
        query.setLimit(getPageLimit(request));
        
        DataRealmUtils.addDataRealms(query, new String[] { getEntityName() + ".std.type.id",
                getEntityName() + ".std.department.id" }, getDataRealmsWith(stdTypeId, request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
}
