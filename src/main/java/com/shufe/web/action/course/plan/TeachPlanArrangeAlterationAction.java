//$Id: TeachPlanArrangeAlterationAction.java,v 1.1 2009-2-1 上午10:38:43 zhouqi Exp $
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
 * zhouqi              2009-2-1             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.plan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.Constants;
import com.shufe.model.course.plan.TeachPlanArrangeAlteration;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 培养计划日志查询
 * 
 * @author zhouqi
 * 
 */
public class TeachPlanArrangeAlterationAction extends RestrictionSupportAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "stdTypeList", getStdTypes(request));
        addCollection(request, Constants.DEPARTMENT_LIST, departmentService.getColleges());
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(TeachPlanArrangeAlteration.class, "alteration");
        populateConditions(request, query);
        java.sql.Date beginAt = RequestUtils.getDate(request, "beginAt");
        if (null != beginAt) {
            query.add(new Condition("alteration.alterationAt >= (:beginAt)", beginAt));
        }
        java.sql.Date endAt = RequestUtils.getDate(request, "endAt");
        if (null != endAt) {
            Calendar c = Calendar.getInstance();
            c.setTime(endAt);
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
            query.add(new Condition("alteration.alterationAt <= (:endAt)", c.getTime()));
        }
        
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "alterations", utilService.search(query));
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addSingleParameter(request, "alteration", utilService.load(
                TeachPlanArrangeAlteration.class, getLong(request, "alterationId")));
        return forward(request);
    }
    
    public ActionForward groupsInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addSingleParameter(request, "alteration", utilService.load(
                TeachPlanArrangeAlteration.class, getLong(request, "alterationId")));
        return forward(request);
    }
    
    public ActionForward planCoursesInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addSingleParameter(request, "alteration", utilService.load(
                TeachPlanArrangeAlteration.class, getLong(request, "alterationId")));
        return forward(request);
    }
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long[] alterationIds = SeqStringUtil.transformToLong(get(request, "alterationIds"));
        Collection alterations = utilService.load(TeachPlanArrangeAlteration.class, "id",
                alterationIds);
        StringBuffer content = new StringBuffer();
        content.append("The logs of the operation of Teach Plan been removed.");
        content.append("<br>");
        content.append("The been removed (with time) is:");
        content.append("<br>");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        for (Iterator it = alterations.iterator(); it.hasNext();) {
            TeachPlanArrangeAlteration alteration = (TeachPlanArrangeAlteration) it.next();
            content.append(sdf.format(alteration.getAlterationAt()));
            if (it.hasNext()) {
                content.append(", ");
            }
        }
        utilService.remove(alterations);
        logHelper.info(request, content.toString());
        return redirect(request, "search", "info.delete.success");
    }
}
