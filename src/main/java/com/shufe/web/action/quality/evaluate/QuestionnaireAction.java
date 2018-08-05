//$Id: QuestionnaireAction.java,v 1.1 2007-6-2 下午01:53:45 chaostone Exp $
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
 *chaostone      2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.quality.evaluate.OptionGroup;
import com.shufe.model.quality.evaluate.Question;
import com.shufe.model.quality.evaluate.Questionnaire;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class QuestionnaireAction extends RestrictionExampleTemplateAction {
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.common.ExampleTemplateAction#editSetting(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.model.Entity)
     */
    protected void editSetting(HttpServletRequest request, Entity entity) throws Exception {
        request.setAttribute("departments", departmentService
                .getDepartments(getDepartmentIdSeq(request)));
        EntityQuery entityQuery = new EntityQuery(OptionGroup.class, "optionGroup");
        entityQuery.add(new Condition("optionGroup.depart.id in (:departs)", SeqStringUtil
                .transformToLong(getDepartmentIdSeq(request))));
        request.setAttribute("optionGroups", utilService.search(entityQuery));
        Questionnaire questionnaire = (Questionnaire) entity;
        List questions = new ArrayList();
        questions.addAll(questionnaire.getQuestions());
        Collections.sort(questions);
        request.setAttribute("questions", questions);
        super.editSetting(request, entity);
    }
    
    /**
     * 根据条件得到问题的列表.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward searchQuestion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String questionSeq = request.getParameter("questionSeq");
        Long typeId = getLong(request, "questionTypeId");
        EntityQuery entityQuery = new EntityQuery(Question.class, "question");
        entityQuery.add(new Condition("question.state=(:state)", Boolean.TRUE));
        if (null != typeId) {
            entityQuery.add(Condition.eq("question.type.id", typeId));
        }
        if (StringUtils.isNotBlank(questionSeq)) {
            entityQuery.add(new Condition("question.id not in (:questionIds)", SeqStringUtil
                    .transformToLong(questionSeq)));
        }
        request.setAttribute("questions", utilService.search(entityQuery));
        return forward(request);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.web.action.common.ExampleTemplateAction#buildQuery(javax.servlet.http.HttpServletRequest)
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery entityQuery = super.buildQuery(request);
        entityQuery.add(new Condition(getEntityName() + ".depart.id in(:departIds)", SeqStringUtil
                .transformToLong(getDepartmentIdSeq(request))));
        return entityQuery;
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Long questionnaireId = getLong(request, "questionnaireId");
        Questionnaire questionnaire = (Questionnaire) utilService.load(Questionnaire.class,
                questionnaireId);
        List questionList = new ArrayList();
        questionList.addAll(questionnaire.getQuestions());
        Collections.sort(questionList);
        request.setAttribute("questionnaire", questionnaire);
        request.setAttribute("questionList", questionList);
        return forward(request);
    }
}
