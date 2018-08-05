//$Id: FeeDefaultAction.java,v 1.7 2006/11/30 07:40:23 duanth Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-7-11         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.fee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.model.fee.FeeDefault;
import com.shufe.service.fee.FeeDefaultService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 交费缺省值
 * 
 * @author chenweixiong,chaostone
 * 
 */
public class FeeDefaultAction extends CalendarRestrictionSupportAction {
    
    protected FeeDefaultService feeDefaultService;
    
    /**
     * 转向配置收费默认值页面
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
        EntityQuery query = new EntityQuery(FeeDefault.class, "default");
        populateConditions(request, query);
        DataRealmUtils.addDataRealms(query, new String[] { "default.studentType.id",
                "default.department.id" }, getDataRealmsWith(getLong(request, "stdType.id"),
                request));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "feeDefaults", utilService.search(query));
        return forward(request);
    }
    
    /**
     * 修改和添加
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FeeDefault feeDefault = (FeeDefault) populate(request, FeeDefault.class);
        if (feeDefault.isPO()) {
            feeDefault = (FeeDefault) utilService.get(FeeDefault.class, feeDefault.getId());
            addEntity(request, feeDefault);
        }
        addCollection(request, "feeTypeList", baseCodeService.getCodes(FeeType.class));
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 保存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FeeDefault feeDefault = (FeeDefault) populateEntity(request, FeeDefault.class, "feeDefault");
        
        utilService.saveOrUpdate(feeDefault);
        return redirect(request, "search", "info.save.success");
        
    }
    
    /**
     * 删除
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String feeDefaultIdSeq = request.getParameter("feeDefaultIds");
        Long[] feeDefaultIds = SeqStringUtil.transformToLong(feeDefaultIdSeq);
        
        try {
            utilService.remove(FeeDefault.class, "id", feeDefaultIds);
        } catch (Exception e) {
            return forward(mapping, request, "deleteErrors");
        }
        return redirect(request, "search", "field.evaluate.deleteSuccess");
    }
    
    /**
     * 打印
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printReview(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(FeeDefault.class, "feeDefault");
        query.addOrder(new Order("feeDefault.studentType.code"));
        query.addOrder(new Order("feeDefault.department.name"));
        query.addOrder(new Order("feeDefault.type.name"));
        addCollection(request, "feeDefaults", utilService.search(query));
        return forward(request);
    }
    
    /**
     * @param feeDefaultService
     *            The feeDefaultService to set.
     */
    public void setFeeDefaultService(FeeDefaultService feeDefaultService) {
        this.feeDefaultService = feeDefaultService;
    }
    
}
