//$Id: FineCourseAction.java,v 1.6 2006/12/30 03:30:12 duanth Exp $
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
 * hc             2005-11-19         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.fineCourse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.eams.system.basecode.industry.FineCourseLevel;
import com.shufe.model.quality.fineCourse.FineCourse;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.service.quality.fineCourse.FineCourseImportListener;
import com.shufe.service.quality.fineCourse.FineCourseService;
import com.shufe.web.action.common.ExampleTemplateAction;

/**
 * 精品课程
 * 
 * @author chaostone
 * 
 */
public class FineCourseAction extends ExampleTemplateAction {
    
    private FineCourseService fineCourseService;
    
    protected void editSetting(HttpServletRequest request, Entity entity) {
        prepare(request);
        addCollection(request, "levels", baseCodeService.getCodes(FineCourseLevel.class));
    }
    
    private void prepare(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(Department.class, "depart");
        // 在这里去除了开课院系的限制
        query.addOrder(new Order("depart.code"));
        addCollection(request, "departmentList", utilService.search(query));
    }
    
    protected void indexSetting(HttpServletRequest request) {
        prepare(request);
        addCollection(request, "levels", baseCodeService.getCodes(FineCourseLevel.class));
    }
    
    /**
     * 导入班级信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request, FineCourse.class,
                tr);
        if (null == transfer) {
            return forward(request, "/pages/components/importData/error");
        }
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                new FineCourseImportListener(fineCourseService, utilService));
        transfer.transfer(tr);
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success");
        }
    }
    
    public void setFineCourseService(FineCourseService fineCourseService) {
        this.fineCourseService = fineCourseService;
    }
    
}
