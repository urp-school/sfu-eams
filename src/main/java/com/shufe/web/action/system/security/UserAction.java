//$Id: UserAction.java,v 1.1 2009-5-14 下午05:58:21 zhouqi Exp $
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
 * zhouqi              2009-5-14             Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.security.model.User;
import com.shufe.service.system.security.UserImporterListener;

/**
 * @author zhouqi
 * 
 */
public class UserAction extends com.ekingstar.security.web.action.UserAction {
    
    public ActionForward importForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("importAction", request.getRequestURI());
        return forward(request, "/pages/components/importData/form");
    }
    
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, User.class, tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new UserImporterListener(utilService));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success");
        }
    }
}
