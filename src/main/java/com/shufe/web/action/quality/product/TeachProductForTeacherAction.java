//$Id: TeachProductForTeacherAction.java,v 1.1 2007-4-25 下午02:36:17 chaostone Exp $
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
 *chaostone      2007-4-25         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.web.action.common.ExampleTemplateAction;

public class TeachProductForTeacherAction extends ExampleTemplateAction {

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String teacherNo = getUser(request.getSession())
				.getName();
		EntityQuery query = buildQuery(request);
		query.join(getEntityName() + ".teacherRank", "teacherRank");
		query.add(Condition.eq("teacherRank.teacher.code", teacherNo));
		addCollection(request, getEntityName() + "s", utilService.search(query));
		return forward(request);
	}
    
}
