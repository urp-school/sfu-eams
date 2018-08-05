//$Id: StudentTypeSearchAction.java,v 1.1 2008-4-7 下午04:53:20 zhouqi Exp $
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
 * zhouqi              2008-4-7         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo.search;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.web.action.system.baseinfo.BaseInfoAction;

/**
 * @author zhouqi
 */
public class StudentTypeSearchAction extends BaseInfoAction {
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return forward(request);
    }
    
    /**
     * 查找信息action.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        EntityQuery query = new EntityQuery(StudentType.class, "studentType");
        populateConditions(request, query);
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "studentTypes", utilService.search(query));
        return forward(request);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String stdTypeId = request.getParameter("studentType.id");
        if (StringUtils.isEmpty(stdTypeId)) {
            stdTypeId = get(request, "studentTypeId");
        }
        if (StringUtils.isEmpty(stdTypeId)) {
            return forward(mapping, request, new String[] { "entity.studentType",
                    "error.model.id.needed" }, "error");
        }
        StudentType stdType = studentTypeService.getStudentType(Long.valueOf(stdTypeId));
        addEntity(request, stdType);
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(StudentType.class, "studentType");
        populateConditions(request, query);
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return utilService.search(query);
    }
}
