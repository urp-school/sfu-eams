//$Id: RegisterStatAction.java,v 1.6 2006/12/01 06:21:00 duanth Exp $
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
 * @author maomao
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * maomao               2007-07-11          Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.registration.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.std.registration.service.RegisterService;

/**
 * 注册统计界面响应类
 * 
 * @author chaostone
 * 
 */
public class RegisterStatAction_bak extends RegisterSearchAction {
	RegisterService registerService;
    /**
     * 日统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward dailyStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List calendars = teachCalendarService.getTeachCalendars();
    	addCollection(request, "teachCalendars", calendars);
        return forward(request);
    }
    /**
     * 总统计
     */
    
    public ActionForward totalStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request);
    }
}
