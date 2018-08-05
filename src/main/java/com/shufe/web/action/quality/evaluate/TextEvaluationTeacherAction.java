//$Id: TextEvaluationAction.java,v 1.15 2007/01/09 07:54:47 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-10-21         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.evaluate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.quality.evaluate.TextEvaluation;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 教师查询自己的文字评教结果页面
 * 
 * @author hj 2005-10-21 TextEvaluationAction.java has been created
 */
public class TextEvaluationTeacherAction extends RestrictionSupportAction {

	/**
	 * 文字评教查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EntityQuery query = new EntityQuery(TextEvaluation.class, "text");
		query.add(new Condition("text.teacher.code=:teacherCode", getLoginName(request)));
		query.add(new Condition("text.isAffirm=true"));
		query.setSelect("distinct text.std.type");
		List stdTypeList = (List) utilService.search(query);
		request.setAttribute("stdTypeList", stdTypeList);
		return forward(request);
	}

	/**
	 * 文字评教详细信息页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EntityQuery query = new EntityQuery(TextEvaluation.class, "textEvaluation");
		populateConditions(request, query);
		Teacher self = getTeacherFromSession(request.getSession());
		query.add(new Condition("textEvaluation.teacher = :teacher", self));
		query.add(new Condition("textEvaluation.isAffirm=true"));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		query.setLimit(getPageLimit(request));
		addCollection(request, "textEvaluations", utilService.search(query));
		return forward(request);
	}
}
