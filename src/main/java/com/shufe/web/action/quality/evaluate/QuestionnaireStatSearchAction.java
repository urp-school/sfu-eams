//$Id: QuestionnaireStatResultAction.java,v 1.1 2007-3-19 下午01:24:52 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-19         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.shufe.model.quality.evaluate.EvaluationCriteria;
import com.shufe.model.quality.evaluate.QuestionnaireStat;
import com.shufe.model.quality.evaluate.QuestionnaireStatPropertyExtractor;
import com.shufe.service.quality.evaluate.QuestionnaireStatService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 评教结果的查询/导出
 * 
 * @author chaostone
 */
public class QuestionnaireStatSearchAction extends CalendarRestrictionSupportAction {
    
    protected QuestionnaireStatService questionnaireStatService;
    
    public void setQuestionnaireStatService(QuestionnaireStatService questionnaireStatService) {
        this.questionnaireStatService = questionnaireStatService;
    }
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeTeachDepart);
        List questionTypeList = questionnaireStatService.getQuestionTypes(getStdTypeIdSeq(request),
                getDepartmentIdSeq(request), null);
        if (!questionTypeList.isEmpty()) {
            Collections.sort(questionTypeList);
        }
        request.setAttribute("questionTypeList", questionTypeList);
        addCollection(request, "evaluationCriterias", utilService.loadAll(EvaluationCriteria.class));
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = buildEntityQuery(request);
        Collection list = utilService.search(entityQuery);
        addCollection(request, "questionnaireStats", list);
        List questionTypeList = questionnaireStatService.getQuestionTypes(getStdTypeIdSeq(request),
                getDepartmentIdSeq(request), null);
        Collections.sort(questionTypeList);
        request.setAttribute("questionTypeList", questionTypeList);
        Long criteriaId = getLong(request, "evaluationCriteriaId");
        EvaluationCriteria criteria = (EvaluationCriteria) utilService.load(
                EvaluationCriteria.class, criteriaId);
        request.setAttribute("criteria", criteria);
        return forward(request);
    }
    
    public EntityQuery buildEntityQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(QuestionnaireStat.class, "questionnaireStat");
        populateConditions(request, entityQuery, "questionnaireStat.stdType.id");
        DataRealmUtils.addDataRealms(entityQuery, new String[] { "questionnaireStat.stdType.id",
                "questionnaireStat.depart.id" }, restrictionHelper.getDataRealmsWith(getLong(request,
                "questionnaireStat.stdType.id"), request));
        Map optionNameMap = getOptionMap();
        String typeId = request.getParameter("selectTypeId");
        if (StringUtils.isNotBlank(typeId)) {
            String mark = request.getParameter("selectMark");
            if (StringUtils.isNotBlank(mark)) {
                Float mark1 = new Float(110);
                Float mark2 = new Float(0);
                if ("A".equals(mark)) {
                    mark2 = (Float) optionNameMap.get("A");
                } else if ("B".equals(mark)) {
                    mark1 = (Float) optionNameMap.get("A");
                    mark2 = (Float) optionNameMap.get("B");
                } else if ("C".equals(mark)) {
                    mark1 = (Float) optionNameMap.get("B");
                    mark2 = (Float) optionNameMap.get("C");
                } else {
                    mark1 = (Float) optionNameMap.get("C");
                }
                if ("0".equals(typeId)) {
                    entityQuery.add(new Condition(mark2 + "<=score"));
                    entityQuery.add(new Condition("score<" + mark1));
                } else {
                    entityQuery.join("questionnaireStat.questionTypeStats", "questionTypeStat");
                    entityQuery.add(new Condition("questionTypeStat.type.id=" + typeId));
                    entityQuery.add(new Condition(mark2 + "<=(questionTypeStat.score)"));
                    entityQuery.add(new Condition("(questionTypeStat.score)<" + mark1));
                }
            }
        }
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        entityQuery.setLimit(getPageLimit(request));
        return entityQuery;
    }
    
    /**
     * @return
     */
    protected Map getOptionMap() {
        Map optionNameMap = new HashMap();
        optionNameMap.put("A", new Float(90));
        optionNameMap.put("B", new Float(80));
        optionNameMap.put("C", new Float(60));
        optionNameMap.put("D", new Float(0));
        return optionNameMap;
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery entityQuery = buildEntityQuery(request);
        entityQuery.setLimit(null);
        return utilService.search(entityQuery);
    }
    
    protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
        Long criteriaId = getLong(request, "evaluationCriteriaId");
        return new QuestionnaireStatPropertyExtractor((EvaluationCriteria) utilService.load(
                EvaluationCriteria.class, criteriaId));
    }
    
}
