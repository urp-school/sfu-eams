//$Id: QuestionTypeAction.java,v 1.1 2007-6-2 下午01:52:47 chaostone Exp $
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

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

import com.ekingstar.commons.model.Entity;
import com.shufe.model.quality.evaluate.QuestionType;
import com.shufe.web.action.common.ExampleTemplateAction;

public class QuestionTypeAction extends ExampleTemplateAction {

	protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
			throws Exception {
		QuestionType questionType = (QuestionType) entity;
		if (!questionType.isPO()) {
			questionType.setCreateAt(new Timestamp(System.currentTimeMillis()));
		}
		return super.saveAndForwad(request, entity);
	}

}
