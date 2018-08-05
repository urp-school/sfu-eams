//$Id: TextEvaluationSearchAction.java,v 1.1 2007-6-19 上午10:10:48 chaostone Exp $
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
 * chenweixiong              2007-6-19         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.mvc.struts.misc.StrutsMessageResource;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.shufe.model.quality.evaluate.TextEvaluation;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 文字评教查询
 * 
 * @author chaostone
 * 
 */
public class TextEvaluationSearchAction extends CalendarRestrictionSupportAction {
    
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
        setCalendarDataRealm(request, hasStdTypeTeachDepart);
        return forward(request);
    }
    
    /**
     * 查询文字评教
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        addCollection(request, "textEvaluations", utilService.search(buildQuery(request)));
        return forward(request);
    }
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(TextEvaluation.class, "textEvaluation");
        entityQuery.join("left", "textEvaluation.teacher", "teacher");
        DataRealmUtils.addDataRealms(entityQuery, new String[] { "textEvaluation.std.type.id",
                "textEvaluation.task.arrangeInfo.teachDepart.id" }, getDataRealms(request));
        populateConditions(request, entityQuery);
        String isAffirmValue = get(request, "isAffirm");
        String textEvaluationId = get(request, "textEvaluationId");
        if (StringUtils.equals("null", isAffirmValue)) {
            entityQuery.add(new Condition("textEvaluation.isAffirm is null"));
        } else if (StringUtils.isNotEmpty(isAffirmValue)) {
            entityQuery.add(new Condition("textEvaluation.isAffirm = (:isAffirmValue)", getBoolean(
                    request, "isAffirm")));
        }else if(StringUtils.isNotEmpty(textEvaluationId)){
            entityQuery.add(new Condition("textEvaluation.id in ("+textEvaluationId+")"));
        }
        entityQuery.setLimit(getPageLimit(request));
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return entityQuery;
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = buildQuery(request);
        entityQuery.setLimit(null);
        return utilService.search(entityQuery);
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#getPropertyExtractor(javax.servlet.http.HttpServletRequest)
     */
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        return new CharacterExtractor(getResources(request), getLocale(request));
    }
    
    class CharacterExtractor extends DefaultPropertyExtractor {
        
        public CharacterExtractor() {
            super();
        }
        
        public CharacterExtractor(MessageResources resource, Locale locale) {
            this.setBuddle(new StrutsMessageResource(resource));
            this.locale = locale;
        }
        
        protected Object extract(Object target, String property) throws Exception {
            Object value = null;
            try {
                value = PropertyUtils.getProperty(target, property);
            } catch (Exception e) {
                return "";
            }
            if ("isCourseEvaluate".equals(property)) {
                if (Boolean.TRUE.equals(value))
                    return getMessage(locale, "attr.evaluate.teacher");
                else
                    return getMessage(locale, "attr.evaluate.course");
            }
            if ("affirm".equals(property)) {
                if (Boolean.TRUE.equals(value))
                    return getMessage(locale, "common.yes");
                else
                    return getMessage(locale, "common.no");
            }
            return super.extract(target, property);
        }
    }
}
