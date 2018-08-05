//$Id: QuestionAction.java,v 1.1 2007-6-2 下午01:53:16 chaostone Exp $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.service.quality.evaluate.QuestionTypeService;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * 问题维护响应类
 * 
 * @author chaostone
 * 
 */
public class QuestionAction extends RestrictionExampleTemplateAction {

	QuestionTypeService questionTypeService;

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		addCollection(request, "questionTypes", questionTypeService.getQuestionTypes());
		return super.search(mapping, form, request, response);
	}

	protected void editSetting(HttpServletRequest request, Entity entity) throws Exception {
		addCollection(request, "questionTypes", questionTypeService.getQuestionTypes());
		setDataRealm(request, hasDepart);
	}

	public void setQuestionTypeService(QuestionTypeService questionTypeService) {
		this.questionTypeService = questionTypeService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.web.action.common.ExampleTemplateAction#buildQuery(javax.servlet.http.HttpServletRequest)
	 */
	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery entityQuery = super.buildQuery(request);
		entityQuery.add(new Condition(getEntityName() + ".department.id in (:departIds)",
				SeqStringUtil.transformToLong(getDepartmentIdSeq(request))));
		return entityQuery;
	}
}
