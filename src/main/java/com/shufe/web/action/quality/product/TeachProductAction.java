//$Id: TeachProductAction.java,v 1.8 2006/12/30 03:30:12 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-11         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.eams.system.basecode.industry.ProductionAwardLevel;
import com.ekingstar.eams.system.basecode.industry.ProductionAwardType;
import com.ekingstar.eams.system.basecode.industry.ProductionType;
import com.shufe.model.quality.product.RankOfTeachProduct;
import com.shufe.model.quality.product.TeachProduct;
import com.shufe.service.quality.product.TeachProductService;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * 教学成果
 * 
 * @author chenweixiong chaostone
 * 
 */
public class TeachProductAction extends RestrictionExampleTemplateAction {
    
    protected TeachProductService teachProductService;
    
    protected void indexSetting(HttpServletRequest request) {
        prepare(request);
    }
    
    /**
     * 管理信息主页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeDepart);
        indexSetting(request);
        return forward(request);
    }
    
    protected void editSetting(HttpServletRequest request, Entity entity) {
        prepare(request);
    }
    
    /**
     * 准备数据
     */
    private void prepare(HttpServletRequest request) {
        request.setAttribute("productionTypes", baseCodeService.getCodes(ProductionType.class));
        request.setAttribute("productionAwardTypes", baseCodeService
                .getCodes(ProductionAwardType.class));
        request.setAttribute("productionAwardLevels", baseCodeService
                .getCodes(ProductionAwardLevel.class));
        setDataRealm(request, hasStdTypeDepart);
    }
    
    protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
            throws Exception {
        TeachProduct teachProduct = (TeachProduct) entity;
        teachProduct.getTeacherRank().clear();
        String teacherIds = request.getParameter("teacherIds");
        String[] teacherId = StringUtils.split(teacherIds, ",");
        for (int i = 0; i < teacherId.length; i++) {
            RankOfTeachProduct rankOfTeachProduct = new RankOfTeachProduct();
            rankOfTeachProduct.setRank(new Integer(i + 1));
            rankOfTeachProduct.getTeacher().setId(Long.valueOf(teacherId[i]));
            teachProduct.addTeacherRank(rankOfTeachProduct);
        }
        utilService.saveOrUpdate(teachProduct);
        return redirect(request, "search", "info.save.success");
    }
    
    public void setTeachProductService(TeachProductService teachProductService) {
        this.teachProductService = teachProductService;
    }
}
