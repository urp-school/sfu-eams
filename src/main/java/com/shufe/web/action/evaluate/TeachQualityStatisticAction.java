//$Id: TeachQualityStatisticAction.java,v 1.2 2006/12/19 13:08:40 duanth Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-5-11         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.evaluate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.service.evaluate.TeachQualityStatisticService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.service.util.stat.FloatSegment;
import com.shufe.web.action.common.RestrictionSupportAction;

public class TeachQualityStatisticAction extends RestrictionSupportAction {
    
    public TeachQualityStatisticService teachQualityStatisticService;
    
    private TeachCalendarService teachCalendarService;
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    public void setTeachQualityStatisticService(
            TeachQualityStatisticService teachQualityStatisticService) {
        this.teachQualityStatisticService = teachQualityStatisticService;
    }
    
    public ActionForward statEvaluatePage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String startFlag = request.getParameter("start");
        if (StringUtils.isBlank(startFlag)) {
            request.setAttribute("stdTypeList", baseInfoService.getBaseInfos(StudentType.class));
            return forward(request, "loadEvaluatePage");
        }
        int start = getInteger(request, "start").intValue();
        int span = getInteger(request, "span").intValue();
        int count = getInteger(request, "count").intValue();
        List segs = new ArrayList();
        segs.add(new FloatSegment(0, start - 1));
        segs.addAll(FloatSegment.buildSegments(start, span, count));
        segs.add(new FloatSegment(start + (span * count) - 1, Integer.MAX_VALUE));
        List newSegs = new ArrayList();
        for (int i = segs.size() - 1; i >= 0; i--) {
            newSegs.add(segs.get(i));
        }
        List condtionCalendars = teachCalendarService.getTeachCalendars(getLong(request,
                "stdTypeId"), request.getParameter("yearStart"), request.getParameter("termStart"),
                request.getParameter("yearEnd"), request.getParameter("termEnd"));
        if (condtionCalendars.isEmpty()) {
            request.setAttribute("displayMap", Collections.EMPTY_MAP);
        } else {
            String excellentMark = request.getParameter("excellentMark");
            Map displayMap = teachQualityStatisticService.statEvaluatePageByNum(newSegs,
                    condtionCalendars, studentTypeService.getStdTypeIdSeqUnder(getLong(request,
                            "stdTypeId")), null, Float.valueOf(excellentMark).floatValue());
            request.setAttribute("displayMap", displayMap);
        }
        request.setAttribute("markSegments", newSegs);
        request.setAttribute("condtionCalendars", condtionCalendars);
        return forward(request, "statPage");
    }
}
