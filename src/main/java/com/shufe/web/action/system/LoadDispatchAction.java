//$Id: IndexAction.java,v 1.1 2008-3-26 下午03:43:59 zhouqi Exp $
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
 * zhouqi              2008-3-26         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.monitor.SecurityMonitor;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * load dispatch
 * 
 * @author chaostone
 */
public class LoadDispatchAction extends DispatchBasicAction {
    
    private SecurityMonitor securityMonitor;
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (securityMonitor.getSessionController().getOnlineCount(EamsRole.STD_USER) >= securityMonitor
                .getSessionController().getMax(EamsRole.STD_USER)) {
            return mapping.findForward(FAILURE);
        } else {
            return mapping.findForward(SUCCESS);
        }
    }
    
    public void setSecurityMonitor(SecurityMonitor securityMonitor) {
        this.securityMonitor = securityMonitor;
    }
}
