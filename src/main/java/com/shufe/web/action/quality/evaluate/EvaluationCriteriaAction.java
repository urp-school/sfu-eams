//$Id: EvaluationCriteriaAction.java,v 1.1 2007-6-2 下午01:50:37 chaostone Exp $
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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.quality.evaluate.EvaluationCriteria;
import com.shufe.model.quality.evaluate.EvaluationCriteriaItem;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class EvaluationCriteriaAction extends RestrictionExampleTemplateAction {

	protected void editSetting(HttpServletRequest request, Entity entity)
			throws Exception {
		setDataRealm(request,  hasDepart);
		super.editSetting(request, entity);
	}

	/**
	 * 不能删除默认对照标准
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String evaluationCriteriaIds = request
				.getParameter("evaluationCriteriaIds");
		if (StringUtils.contains("," + evaluationCriteriaIds + ",", ","
				+ EvaluationCriteria.DEFAULTID + ",")) {
			return redirect(request, "search", "info.delete.failure");
		} else {
			return super.remove(mapping, form, request, response);
		}
	}

	protected ActionForward saveAndForwad(HttpServletRequest request,
			Entity entity) throws Exception {
		EvaluationCriteria evalutionCriteria = (EvaluationCriteria) entity;
		evalutionCriteria.getCriteriaItems().clear();
		Integer criteriaCount = getInteger(request, "criteriaItemCount");
		if (null != criteriaCount) {
			for (int i = 0; i < criteriaCount.intValue(); i++) {
				String criteriaItemName = get(request, "criteriaItem" + i
						+ ".name");
				if (StringUtils.isNotEmpty(criteriaItemName)) {
					evalutionCriteria
							.addItem((EvaluationCriteriaItem) populateEntity(
									request, EvaluationCriteriaItem.class,
									"criteriaItem" + i));
				}
			}
		}
		return super.saveAndForwad(request, entity);
	}

	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery entityQuery = super.buildQuery(request);
		entityQuery.add(new Condition(getEntityName()
				+ ".depart.id in (:departIds)", SeqStringUtil
				.transformToLong(getDepartmentIdSeq(request))));
		return entityQuery;
	}
}
