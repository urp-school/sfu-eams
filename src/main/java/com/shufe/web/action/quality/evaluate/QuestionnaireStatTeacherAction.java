//$Id: QuestionnaireStatTeacher.java,v 1.1 2007-3-20 下午02:28:43 chaostone Exp $
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
 *chaostone      2007-3-20         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.quality.evaluate.EvaluateQuerySwitch;
import com.shufe.model.quality.evaluate.EvaluationCriteria;
import com.shufe.model.quality.evaluate.QuestionType;
import com.shufe.model.quality.evaluate.QuestionnaireStat;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.evaluate.QuestionnairStatService;
import com.shufe.util.DataRealmUtils;

/**
 * 教师查看评教结果响应类
 * 
 * @author chaostone
 */
public class QuestionnaireStatTeacherAction extends QuestionnaireStatSearchAction {

	protected QuestionnairStatService questionnairStatService;

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Teacher teacher = getTeacherFromSession(request.getSession());
		request.setAttribute("teacher", teacher);
		EntityQuery query = new EntityQuery(QuestionnaireStat.class, "questionnaireStat");
		query.setSelect("distinct questionnaireStat.stdType");
		query.add(new Condition("questionnaireStat.teacher.id=:teacherId", teacher.getId()));
		List stdTypeList = (List) utilService.search(query);
		request.setAttribute("stdTypeList", stdTypeList);
		return forward(request);
	}

	public EntityQuery buildEntityQuery(HttpServletRequest request) {
		EntityQuery entityQuery = new EntityQuery(QuestionnaireStat.class, "questionnaireStat");
		populateConditions(request, entityQuery, "questionnaireStat.stdType.id");
		Long stdTypeId = getLong(request, "questionnaireStat.stdType.id");
		if (null != stdTypeId) {
			DataRealmUtils.addDataRealm(entityQuery,
					new String[] { "questionnaireStat.stdType.id" }, new DataRealm(
							studentTypeService.getStdTypeIdSeqUnder(stdTypeId), null));
		}
		EntityQuery query = new EntityQuery(EvaluateQuerySwitch.class, "querySwitch");
		query.add(new Condition("querySwitch.isOpen =(:isOpen)", Boolean.TRUE));
		Collection querySwitchs = utilService.search(query);
		List teachCalendars = new ArrayList();
		for (Iterator iter = querySwitchs.iterator(); iter.hasNext();) {
			EvaluateQuerySwitch element = (EvaluateQuerySwitch) iter.next();
			teachCalendars.add(element.getTeachCalendar());
		}
		if (teachCalendars.isEmpty()) {
			entityQuery.add(new Condition("questionnaireStat.calendar is null"));
		} else {
			entityQuery.add(new Condition("questionnaireStat.calendar in (:calendars)",
					teachCalendars));
		}
		return entityQuery;
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EntityQuery entityQuery = buildEntityQuery(request);
		entityQuery.setLimit(getPageLimit(request));
		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			entityQuery.addOrder(new Order("questionnaireStat.calendar.year desc"));
		}
		Collection questionnaireStatTeachers = utilService.search(entityQuery);
		Map evaluateDetailMap = new HashMap();
		for (Iterator it = questionnaireStatTeachers.iterator(); it.hasNext();) {
			QuestionnaireStat teacherStat = (QuestionnaireStat) it.next();
			evaluateDetailMap.put(teacherStat.getId().toString(), questionnairStatService
					.isEmptyOfEvaluateTeacherStat(teacherStat.getCalendar(), teacherStat
							.getCourse(), teacherStat.getTeacher()));
		}
		request.setAttribute("evaluateDetailMap", evaluateDetailMap);
		addCollection(request, "questionnaireStatTeachers", questionnaireStatTeachers);
		List questionTypeList = utilService.load(QuestionType.class, "state", Boolean.TRUE);
		Collections.sort(questionTypeList);
		request.setAttribute("questionTypeList", questionTypeList);
		request.setAttribute("criteria", utilService.get(EvaluationCriteria.class,
				EvaluationCriteria.DEFAULTID));
		return forward(request);
	}

	/**
	 * 教师个人查询自己被评教的详细情况
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward evaluatePersonInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forward(request, new Action(EvaluateDetailStatAction.class, "info"));
	}

	public void setQuestionnairStatService(QuestionnairStatService questionnairStatService) {
		this.questionnairStatService = questionnairStatService;
	}
}
