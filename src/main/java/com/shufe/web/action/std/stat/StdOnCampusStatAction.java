//$Id: OnCampusStdStat.java,v 1.1 2007-4-2 上午09:26:12 chaostone Exp $
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
 *chaostone      2007-4-2         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.stat;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.security.Authentication;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.std.stat.StdStatService;
import com.shufe.service.util.stat.StatGroup;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 在校学生统计
 * 
 * @author chaostone
 * 
 */
public class StdOnCampusStatAction extends RestrictionSupportAction {
    
    private StdStatService stdStatService;
    
    /**
     * 学生类别统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statByStdType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataRealm realm = null;
        if (request.getSession().getAttribute(Authentication.USERID) != null) {
            realm = getDataRealm(request);
        }
        List stats = stdStatService.statOnCampusByStdTypeDepart(realm);
        List stdTypeStats = stdStatService.statOnCampusByStdType(realm);
        addCollection(request, "stats", stats);
        request.setAttribute("stdTypeStat", new StatGroup(null, stdTypeStats));
        return forward(request);
    }
    
    public ActionForward statByDepart(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataRealm realm = null;
        if (request.getSession().getAttribute(Authentication.USERID) != null) {
            realm = getDataRealm(request);
        }
        List stats = stdStatService.statOnCampusByDepartStdType(realm);
        List departStat = stdStatService.statOnCampusByDepart(realm);
        addCollection(request, "stats", stats);
        request.setAttribute("departStat", new StatGroup(null, departStat));
        return forward(request);
    }
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public void setStdStatService(StdStatService stdStatService) {
        this.stdStatService = stdStatService;
    }
    
}
