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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-11-5            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.textbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.TextbookAwardLevel;
import com.shufe.model.course.textbook.BookRequirement;
import com.shufe.service.course.textbook.BookRequirementService;

/**
 * 教材需求审核
 * 
 * @author chaostone
 */
public class BookRequireAuditAction extends RequirementSearchAction {
    
    private BookRequirementService bookRequirementService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        addCollection(request, "teachDeparts", getColleges(request));
        addCollection(request, "awardLevels", baseCodeService.getCodes(TextbookAwardLevel.class));
        return forward(request);
    }
    
    /**
     * 查找教材需求登记
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = buildRequireQuery(request);
        addCollection(request, "requires", utilService.search(entityQuery));
        return forward(request);
        
    }
    
    public ActionForward setPass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ids = request.getParameter("requireIds");
        Boolean pass = getBoolean(request, "pass");
        if (null != pass) {
            Long[] passids = SeqStringUtil.transformToLong(ids);
            for (int x = 0; x < passids.length; x++) {
                BookRequirement requirement = bookRequirementService.getBookRequirement(passids[x]);
                requirement.setChecked(pass);
                utilService.saveOrUpdate(requirement);
            }
        }
        return redirect(request, "search", "info.update.success");
    }
    
    public void setBookRequirementService(BookRequirementService bookRequirementService) {
        this.bookRequirementService = bookRequirementService;
    }
    
}
