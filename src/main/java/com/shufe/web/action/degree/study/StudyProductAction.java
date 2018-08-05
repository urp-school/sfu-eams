//$Id: StudyProductAction.java,v 1.1 2007-3-5 17:32:15 Administrator Exp $
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
 * chenweixiong         2007-03-05          Created
 * zq                   2007-09-17          见下面buildStudyProductQuery()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.study;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.LiteratureType;
import com.ekingstar.eams.system.basecode.industry.MeetingType;
import com.ekingstar.eams.system.basecode.industry.ProjectType;
import com.ekingstar.eams.system.basecode.industry.PublicationLevel;
import com.shufe.model.degree.study.StudyProduct;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.util.DataRealmUtils;

public class StudyProductAction extends StudyProductHelper {

    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
        initBaseInfos(request, "specialityList", Speciality.class); //得到专业初始
        return forward(request);
    }

    /**
     * 学生查询
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
        String productType = request.getParameter("productType");
        request.setAttribute("productType", productType);
        EntityQuery query = buildStudyProductQuery(request, productType);
        addCollection(request, "studyProducts", utilService.search(query));
        return forward(request, "studyProductList");
    }

    /**
     * 组建 query
     * 
     * @param request
     * @param productType
     * @return
     */
    private EntityQuery buildStudyProductQuery(HttpServletRequest request, String productType) {
        EntityQuery query = new EntityQuery(StudyProduct.getStudyProductType(productType),
                "studyProduct");
        populateConditions(request, query, "studyProduct.student.type.id");
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        DataRealmUtils.addDataRealms(query, new String[] { "studyProduct.student.type.id",
                "studyProduct.student.department.id" }, getDataRealmsWith(getLong(request,
                "studyProduct.student.type.id"), request));
        // FIXME 上面一句代替下面的一句
        // DataRealmUtils.addDataRealms(query, new String[] { "studyProduct.student.type.id",
        // "studyProduct.student.department.id" }, getDataRealms(request));
        Boolean isAwarded = getBoolean(request, "isAwarded");
        if (null != isAwarded) {
            if (Boolean.TRUE.equals(isAwarded)) {
                query.add(new Condition("studyProduct.awards.size >0"));
            } else {
                query.add(new Condition("studyProduct.awards.size =0"));
            }
        }
        return query;
    }

    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String productIds = request.getParameter("studyProductIds");
        String productType = request.getParameter("productType");
        List products = utilService.load(StudyProduct.getStudyProductType(productType), "id",
                SeqStringUtil.transformToLong(productIds));
        utilService.remove(products);
        return redirect(request, "search", "info.delete.success");
    }

    public ActionForward check(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String productIds = request.getParameter("studyProductIds");
        String productType = request.getParameter("productType");
        List products = utilService.load(StudyProduct.getStudyProductType(productType), "id",
                SeqStringUtil.transformToLong(productIds));
        Boolean isPassCheck = getBoolean(request, "isPassCheck");
        for (Iterator iter = products.iterator(); iter.hasNext();) {
            StudyProduct product = (StudyProduct) iter.next();
            product.setIsPassCheck(isPassCheck);
        }
        utilService.saveOrUpdate(products);
        return redirect(request, "search", "info.action.success");
    }

    /**
     * @see com.shufe.web.action.degree.study.StudyProductHelper#configExportContext(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.transfer.exporter.Context)
     */
    protected void configExportContext(HttpServletRequest request, Context context) {
        String excelType = request.getParameter("excelType");
        if ("excelList".equals(excelType)) {
            String productType = request.getParameter("productType");
            EntityQuery query = buildStudyProductQuery(request, productType);
            query.setLimit(null);
            context.put("items", utilService.search(query));
        } else {
            super.configExportContext(request, context);
        }
    }

    /**
     * 批量修改
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchEdit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String productIds = request.getParameter("studyProductIds");
        String productType = request.getParameter("productType");
        List products = utilService.load(StudyProduct.getStudyProductType(productType), "id",
                SeqStringUtil.transformToLong(productIds));
        if ("studyThesis".equals(productType)) {
            request.setAttribute("studyProductTypes", utilService.load(PublicationLevel.class,
                    "state", Boolean.TRUE));
        } else if ("literature".equals(productType)) {
            request.setAttribute("studyProductTypes", utilService.load(LiteratureType.class,
                    "state", Boolean.TRUE));
        } else if ("project".equals(productType)) {
            request.setAttribute("studyProductTypes", utilService.load(ProjectType.class,
                    "state", Boolean.TRUE));
        } else if ("studyMeeting".equals(productType)) {
            request.setAttribute("studyProductTypes", utilService.load(MeetingType.class,
                    "state", Boolean.TRUE));
        }
        request.setAttribute("productType", productType);
        request.setAttribute("studyProducts", products);
        request.setAttribute("productIds", productIds);
        return forward(request, "batchEditForm");
    }

    /**
     * 批量保存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchSave(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String productIds = request.getParameter("studyProductIds");
        String productType = request.getParameter("productType");
        List products = utilService.load(StudyProduct.getStudyProductType(productType), "id",
                SeqStringUtil.transformToLong(productIds));
        Map valueMap = RequestUtils.getParams(request, "product");
        for (Iterator iter = valueMap.keySet().iterator(); iter.hasNext();) {
            String keyValue = (String) iter.next();
            Object entity = valueMap.get(keyValue);
            if (null == entity || StringUtils.isBlank(entity.toString())) {
                valueMap.remove(keyValue);
            }
        }
        for (Iterator iter = products.iterator(); iter.hasNext();) {
            StudyProduct element = (StudyProduct) iter.next();
            EntityUtils.populate(valueMap, element);
        }
        utilService.saveOrUpdate(products);
        return redirect(request, "search", "info.action.success");
    }

 
    /**
     * 按照如许年份统计学生的科研成果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward statisticProducts(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map valueMap = RequestUtils.getParams(request, "studyProduct");
        request.setAttribute("publicTypes", utilService.load(PublicationLevel.class, "state",
                Boolean.TRUE));
        request.setAttribute("projectTypes", utilService.load(ProjectType.class, "state",
                Boolean.TRUE));
        request.setAttribute("literatureTypes", utilService.load(LiteratureType.class,
                "state", Boolean.TRUE));
        request.setAttribute("resultMap", studyProductService.getStdPropertyOfProduct(valueMap,
                getDepartmentIdSeq(request), getStdTypeIdSeq(request)));
        request.setAttribute("enrollYear", request.getParameter("enrollYear"));
        return forward(request);
    }
}
