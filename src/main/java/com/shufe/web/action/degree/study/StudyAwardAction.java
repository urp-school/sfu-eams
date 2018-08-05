//$Id: StudyAwardAction.java,v 1.1 2007-3-7 下午02:26:54 chaostone Exp $
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
 * chaostone            2007-03-07          Created
 * zq                   2007-09-17          见下面的search()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.study;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.ResearchAwardType;
import com.shufe.model.degree.study.StudyAward;
import com.shufe.model.degree.study.StudyProduct;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 学生科研获奖管理
 * 
 * @author chaostone
 * 
 */
public class StudyAwardAction extends RestrictionSupportAction {

    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
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
        EntityQuery query = new EntityQuery(StudyAward.getStudyAwardType(productType), "studyAward");
        populateConditions(request, query, "studyAward.student.type.id");
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        DataRealmUtils.addDataRealms(query, new String[] { "studyAward.student.type.id",
                "studyAward.student.department.id" }, getDataRealmsWith(getLong(request,
                "studyAward.student.type.id"), request));
        // FIXME 上面一句代替下面的一句
        // DataRealmUtils.addDataRealms(query, new String[] { "studyAward.student.type.id",
        // "studyAward.student.department.id" }, getDataRealms(request));
        addCollection(request, "studyAwards", utilService.search(query));
        return forward(request, "studyAwardList");
    }

    public ActionForward check(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String studyAwardIds = request.getParameter("studyAwardIds");
        String productType = request.getParameter("productType");
        List awards = utilService.load(StudyAward.getStudyAwardType(productType), "id",
                SeqStringUtil.transformToLong(studyAwardIds));
        Boolean isPassCheck = getBoolean(request, "isPassCheck");
        for (Iterator iter = awards.iterator(); iter.hasNext();) {
            StudyAward studyAward = (StudyAward) iter.next();
            studyAward.setIsPassCheck(isPassCheck);
        }
        utilService.saveOrUpdate(awards);
        return redirect(request, "search", "info.action.success");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long studyAwardId = getLong(request, "studyAwardId");
        String productType = request.getParameter("productType");
        StudyAward award = null;
        if (null == studyAwardId) {
            Long studyProductId = getLong(request, "studyProductId");
            if (null == studyProductId) {
                return forwardError(mapping, request, "error.parameters.needed");
            }
            StudyProduct product = (StudyProduct) utilService.load(StudyProduct
                    .getStudyProductType(productType), studyProductId);
            award = StudyAward.getStudyAward(productType);
            award.setStudyProduct(product);
        } else {
            award = (StudyAward) utilService.load(StudyAward.getStudyAwardType(productType),
                    studyAwardId);
        }
        request.setAttribute("studyAward", award);
        addCollection(request, "awardTypeList", baseCodeService.getCodes(ResearchAwardType.class));
        request.setAttribute("fromAction", RequestUtils.getRequestAction(request));
        return forward(request);
    }

    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String productType = request.getParameter("productType");
        Long awardId = getLong(request, "studyAward.id");
        Long studyProductId = getLong(request, "studyProductId");
        StudyAward award = null;
        if (null == awardId) {
            award = StudyAward.getStudyAward(productType);
        } else {
            award = (StudyAward) utilService.get(StudyAward.getStudyAwardType(productType),
                    awardId);
        }
        award.getStudyProduct().setId(studyProductId);
        populate(getParams(request, "studyAward"), award);
        if (!award.isPO()) {
            EntityUtils.evictEmptyProperty(award);
        }
        String fromAction = request.getParameter("fromAction");
        utilService.saveOrUpdate(award);
        return redirect(request, new Action(fromAction.substring(0, fromAction.indexOf(".")),
                "search"), "info.save.success");
    }

    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String awardIds = request.getParameter("studyAwardIds");
        String productType = request.getParameter("productType");
        List awards = utilService.load(StudyAward.getStudyAwardType(productType), "id",
                SeqStringUtil.transformToLong(awardIds));
        utilService.remove(awards);
        return redirect(request, "search", "info.delete.success");
    }

    protected Collection getExportDatas(HttpServletRequest request) {
        String productType = request.getParameter("productType");
        request.setAttribute("productType", productType);
        EntityQuery query = new EntityQuery(StudyAward.getStudyAwardType(productType), "studyAward");
        populateConditions(request, query);
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        DataRealmUtils.addDataRealms(query, new String[] { "studyAward.student.type.id",
                "studyAward.student.department.id" }, getDataRealms(request));
        return utilService.search(query);
    }
}
