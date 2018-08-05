//$Id: QuestionnaireTaskAction.java,v 1.1 2007-6-4 20:37:10 Administrator Exp $
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
 * chenweixiong              2007-6-4         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.quality.evaluate.Questionnaire;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class QuestionnaireTaskAction extends CalendarRestrictionSupportAction {
    
    /**
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
        initBaseCodes(request, "courseTypeList", CourseType.class);
        initBaseCodes(request, "categoryList", CourseCategory.class);
        initBaseCodes(request, "courseCategoryList", CourseCategory.class);
        return forward(request);
    }
    
    /**
     * 根据条件得到对应的教学任务的列表.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward taskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String estate = request.getParameter("questionnaireEstate");
        EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
        populateConditions(request, entityQuery);
        if ("1".equals(estate)) {
            entityQuery.add(new Condition("task.questionnaire is not null"));
        } else {
            entityQuery.add(new Condition("task.questionnaire is null"));
        }
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        entityQuery.setLimit(getPageLimit(request));
        addCollection(request, "tasks", utilService.search(entityQuery));
        List questionnaireList = utilService.load(Questionnaire.class, "depart.id", SeqStringUtil
                .transformToLong(getDepartmentIdSeq(request)));
        request.setAttribute("questionnaireList", questionnaireList);
        return forward(request);
    }
    
    /**
     * 修改课程问卷
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward courseTaskModify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String isAll = request.getParameter("isAll");
        Collection tasks = null;
        if ("all".equals(isAll)) {
            String estate = request.getParameter("questionnaireEstate");
            EntityQuery entityQuery = new EntityQuery(TeachTask.class, "task");
            populateConditions(request, entityQuery);
            if ("true".equals(estate)) {
                entityQuery.add(new Condition("task.questionnaire is not null"));
            } else {
                entityQuery.add(new Condition("task.questionnaire is null"));
            }
            tasks = utilService.search(entityQuery);
        } else {
            String taskSeq = request.getParameter("taskSeq");
            tasks = utilService.load(TeachTask.class, "id", SeqStringUtil.transformToLong(taskSeq));
        }
        String isType = request.getParameter("isType");
        if ("questionnaire".equals(isType)) {
            Questionnaire questionaire = null;
            Long questionnaireId = getLong(request, "questionnaireId");
            if (null != questionnaireId) {
                questionaire = (Questionnaire) utilService.load(Questionnaire.class,
                        questionnaireId);
            }
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask element = (TeachTask) iter.next();
                element.setQuestionnaire(questionaire);
            }
        } else if ("evaluate".equals(isType)) {
            Boolean isEvaluate = Boolean.valueOf(request.getParameter("isEvaluate"));
            for (Iterator iter = tasks.iterator(); iter.hasNext();) {
                TeachTask element = (TeachTask) iter.next();
                element.getRequirement().setEvaluateByTeacher(isEvaluate);
            }
        }
        utilService.saveOrUpdate(tasks);
        return redirect(request, "taskList", "info.action.success");
    }
}
