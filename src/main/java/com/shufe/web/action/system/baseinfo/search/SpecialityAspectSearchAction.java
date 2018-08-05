//$Id: SpecialityAspectSearchAction.java,v 1.1 2008-1-30 下午05:40:29 zhouqi Exp $
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
 * zhouqi              2008-1-30         	Created
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
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.service.system.baseinfo.SpecialityAspectService;
import com.shufe.web.action.system.baseinfo.BaseInfoAction;

/**
 * @author zhouqi
 */
public class SpecialityAspectSearchAction extends BaseInfoAction {
    
    protected SpecialityAspectService specialityAspectService;
    
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
        request.setAttribute("departmentList", departmentService.getAllColleges());
        request.setAttribute("stdTypeList", baseInfoService.getBaseInfos(StudentType.class));
        return forward(request);
    }
    
    /**
     * 查找信息action. 参数是从formbean中的专业方向信息取得的.<br>
     * 可以从菜单/直接的URL或从专业方向详细页面中跳转到该方法. 以<code>Constants.SPECIALITY</code>为标识.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        addCollection(request, "specialityAspects", utilService.search(baseInfoSearchHelper
                .buildSpecialityAspectQuery(request)));
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = baseInfoSearchHelper.buildSpecialityAspectQuery(request);
        query.setLimit(null);
        return utilService.search(query);
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
        String specialityAspectId = request.getParameter("specialityAspect.id");
        if (StringUtils.isEmpty(specialityAspectId))
            return forward(mapping, request, new String[] { "entity.specialityAspect",
                    "error.model.id.needed" }, "error");
        SpecialityAspect specialityAspect = specialityAspectService.getSpecialityAspect(Long
                .valueOf(specialityAspectId));
        addEntity(request, specialityAspect);
        return forward(request);
    }
    
    /**
     * @param specialityAspectService The specialityAspectService to set.
     */
    public void setSpecialityAspectService(SpecialityAspectService specialityAspectService) {
        this.specialityAspectService = specialityAspectService;
    }
    
}
