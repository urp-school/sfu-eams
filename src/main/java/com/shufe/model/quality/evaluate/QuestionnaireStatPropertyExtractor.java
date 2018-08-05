//$Id: QuestionnaireStatPropertyExporter.java,v 1.1 2007-3-19 下午01:35:35 chaostone Exp $
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

package com.shufe.model.quality.evaluate;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;

public class QuestionnaireStatPropertyExtractor extends DefaultPropertyExtractor {
	private EvaluationCriteria criteria;

	public QuestionnaireStatPropertyExtractor(EvaluationCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public Object getPropertyValue(Object target, String property)
			throws Exception {
		QuestionnaireStat questionnaireStat = (QuestionnaireStat) target;
		if (property.startsWith("questionTypeStat")) {
			Long typeId = Long.valueOf(StringUtils.substringAfterLast(property,
					"_"));
			return questionnaireStat.getTypeScoreDisplay(criteria, typeId);
		} else if ("scoreDisplay".equals(property)) {
			return questionnaireStat.getScoreDisplay(criteria);
		} else {
			return super.getPropertyValue(target, property);
		}
	}
}
