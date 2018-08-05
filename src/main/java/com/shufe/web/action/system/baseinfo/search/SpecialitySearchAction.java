//$Id: SpecialitySearchAction.java,v 1.1 2008-1-30 下午05:17:42 zhouqi Exp $
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
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.state.SubjectCategory;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.service.system.baseinfo.SpecialityService;
import com.shufe.web.action.system.baseinfo.BaseInfoAction;

/**
 * @author zhouqi
 */
public class SpecialitySearchAction extends BaseInfoAction {
    
    protected SpecialityService specialityService;
    
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
        addCollection(request, "departments", departmentService.getColleges());
        addCollection(request, "stdTypes",baseInfoService.getBaseInfos(StudentType.class));
        addCollection(request, "subjectCategories", baseCodeService.getCodes(SubjectCategory.class));
        addCollection(request, "majorTypes", baseCodeService.getCodes(MajorType.class));
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
        addCollection(request, "specialities", utilService.search(baseInfoSearchHelper
                .buildSpecialityQuery(request)));
        return forward(request);
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = baseInfoSearchHelper.buildSpecialityQuery(request);
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
        String specialityId = request.getParameter("speciality.id");
        if (StringUtils.isEmpty(specialityId))
            return forward(mapping, request, new String[] { "entity.speciality",
                    "error.model.id.needed" }, "error");
        Speciality speciality = specialityService.getSpeciality(Long.valueOf(specialityId));
        addEntity(request, speciality);
        return forward(request);
    }
    
    /**
     * @param specialityService The specialityService to set.
     */
    public void setSpecialityService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
}
