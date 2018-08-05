//$Id: AvailTimeAction.java,v 1.9 2008/5/21 10:26:02 maple Exp $
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
 * @author maple
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone            2005-11-16          Created
 * zq                   2007-09-18          
 ********************************************************************************/

package com.shufe.web.action.course.arrange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.course.arrange.CourseArrangeSwitch;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 学期排课发布
 * 
 * @author chaostone 2008-5-21
 */
public class CourseArrangeSwitchAction extends CalendarRestrictionSupportAction {
    
    public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List teachCalendars = utilService.load(TeachCalendar.class, "studentType.id", SeqStringUtil
                .transformToLong(getStdTypeIdSeq(request)));
        List courseArrangeSwitchS = utilService.load(CourseArrangeSwitch.class, "calendar",
                teachCalendars);
        Map tempMap = new HashMap();
        for (Iterator iter = courseArrangeSwitchS.iterator(); iter.hasNext();) {
            CourseArrangeSwitch courseArrange = (CourseArrangeSwitch) iter.next();
            tempMap.put(courseArrange.getCalendar().getId(), courseArrange);
        }
        List arrangeSwitchList = new ArrayList();
        for (Iterator iter = teachCalendars.iterator(); iter.hasNext();) {
            TeachCalendar element = (TeachCalendar) iter.next();
            if (!tempMap.containsKey(element.getId())) {
                CourseArrangeSwitch arrange = new CourseArrangeSwitch(element);
                arrangeSwitchList.add(arrange);
            }
        }
        utilService.saveOrUpdate(arrangeSwitchList);
        return redirect(request, "index", "");
    }
    
    /**
     * 排课发布首页
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeDepart);
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(CourseArrangeSwitch.class, "courseArrangeSwitch");
        populateConditions(request, query);
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "courseArrangeSwitchList", utilService.search(query));
        request.setAttribute("isOpen", request.getParameter("courseArrangeSwitch.isPublished"));
        return forward(request);
    }
    
    public ActionForward openedOrClosed(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] courseArrangeSwitchIds = SeqStringUtil.transformToLong(get(request,
                "courseArrangeSwitchIds"));
        List courseArrangeSwitchList = utilService.load(CourseArrangeSwitch.class, "id",
                courseArrangeSwitchIds);
        for (Iterator iter = courseArrangeSwitchList.iterator(); iter.hasNext();) {
            CourseArrangeSwitch courseArrangeSwitch = (CourseArrangeSwitch) iter.next();
            if (Boolean.TRUE.equals(courseArrangeSwitch.getIsPublished())) {
                courseArrangeSwitch.setIsPublished(Boolean.FALSE);
            } else {
                courseArrangeSwitch.setIsPublished(Boolean.TRUE);
            }
            utilService.saveOrUpdate(courseArrangeSwitch);
        }
        return redirect(request, "search", "info.save.success");
    }
    
}
