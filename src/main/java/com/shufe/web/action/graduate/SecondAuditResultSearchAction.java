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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/

package com.shufe.web.action.graduate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.service.graduate.AuditStandardService;
import com.shufe.util.RequestUtil;

public class SecondAuditResultSearchAction extends AuditResultSupportAction {
    
    private AuditStandardService auditStandardService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        initSearchBar(form, request);
        initBaseCodes("studentStateList", StudentState.class);
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        EntityQuery query = buildQuery(request);
        query.add(new Condition("std.secondMajor is not null"));
        query.add(new Condition("std.secondMajor.majorType.id = (:majorTypeId)",
                MajorType.SECOND));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        query.setLimit(getPageLimit(request));
        addCollection(request, "studentList", utilService.search(query));
        return this.forward(request);
    }
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        initSearchBar(form, request);
        initBaseCodes("studentStateList", StudentState.class);
        return this.forward(request);
    }
    
    protected MajorType getMajorType() {
        return new MajorType(MajorType.SECOND);
    }
    
    protected void setOtherSearch(HttpServletRequest request, String studentTypeIds,
            String departmentIds) {
        Speciality speciality = new Speciality();
        speciality.setMajorType(new MajorType(MajorType.SECOND));
        Results.addObject("secondSpecialityList", specialityService.getSpecialities(speciality,
                studentTypeIds, departmentIds));
        AuditStandard auditStandard = (AuditStandard) RequestUtil.populate(request,
                AuditStandard.class, "auditStandard");
        Results.addObject("auditStandardList", auditStandardService.searchAuditStandard(
                auditStandard, studentTypeIds));
    }
    
    protected void searchBar(ActionForm form, HttpServletRequest request, String studentTypeIds,
            String departmentIds) {
        searchStudentWith2ndSpeciality(form, request, "SecondAuditResultSearch", Boolean.TRUE, null);
    }
    
    /**
     * @param auditStandardService
     *            要设置的 auditStandardService
     */
    public void setAuditStandardService(AuditStandardService auditStandardService) {
        this.auditStandardService = auditStandardService;
    }
}
