//$Id: OptionGroupAction.java,v 1.1 2007-6-2 下午01:52:12 chaostone Exp $
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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.quality.evaluate.Option;
import com.shufe.model.quality.evaluate.OptionGroup;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class OptionGroupAction extends RestrictionExampleTemplateAction {

	protected void editSetting(HttpServletRequest request, Entity entity)
			throws Exception {
		setDataRealm(request, hasDepart);
		super.editSetting(request, entity);
	}

	protected ActionForward saveAndForwad(HttpServletRequest request,
			Entity entity) throws Exception {
		OptionGroup optionGroup = (OptionGroup) entity;
		// TODO for somebody has evaluated questionnaire;
		optionGroup.getOptions().clear();
		Integer optionCount = getInteger(request, "optionCount");
		if (null != optionCount) {
			for (int i = 0; i < optionCount.intValue(); i++) {
				String optionName = get(request, "option" + i + ".name");
				if (StringUtils.isNotEmpty(optionName)) {
					optionGroup.addOption((Option) populateEntity(request,
							Option.class, "option" + i));
				}
			}
		}
		return super.saveAndForwad(request, entity);
	}

	/* (non-Javadoc)
	 * @see com.shufe.web.action.common.ExampleTemplateAction#buildQuery(javax.servlet.http.HttpServletRequest)
	 */
	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery entityQuery = super.buildQuery(request);
		entityQuery.add(new Condition(getEntityName()
				+ ".depart.id in (:departIds)", SeqStringUtil
				.transformToLong(getDepartmentIdSeq(request))));
		return entityQuery;
	}
}
