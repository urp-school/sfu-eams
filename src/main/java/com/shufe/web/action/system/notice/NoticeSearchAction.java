//$Id: NotictSearchAction.java,v 1.1 2008-5-8 下午02:36:31 zhouqi Exp $
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
 * zhouqi              2008-5-8         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.system.notice.ManagerNotice;
import com.shufe.model.system.notice.StudentNotice;
import com.shufe.model.system.notice.TeacherNotice;
import com.shufe.web.action.common.RestrictionSupportAction;

public class NoticeSearchAction extends RestrictionSupportAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String kind = request.getParameter("kind");
        if (null == kind) {
            kind = "manager";
        }
        EntityQuery entityQuery = null;
        if (kind.equals("std")) {
            entityQuery = new EntityQuery(StudentNotice.class, "notice");
        } else if (kind.equals("teacher")) {
            entityQuery = new EntityQuery(TeacherNotice.class, "notice");
        } else if (kind.equals("manager")) {
            entityQuery = new EntityQuery(ManagerNotice.class, "notice");
        } else {
            throw new RuntimeException("unspported notice kind");
        }
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(new Order("notice.modifyAt DESC"));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "notices", utilService.search(entityQuery));
        return forward(request);
    }
}
