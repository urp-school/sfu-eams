//$Id: StdAwardPunishAction.java,v 1.1 2007-5-30 上午11:50:48 chaostone Exp $
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
 *chaostone      2007-5-30         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.awardPunish;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.std.awardPunish.Award;
import com.shufe.model.std.awardPunish.Punishment;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 学生个人奖惩响应类
 * 
 * @author chaostone
 * 
 */
public class StdAwardPunishAction extends DispatchBasicAction {

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return forward(request);
	}

	public ActionForward awardList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityQuery query = new EntityQuery(Award.class, "award");
		query.add(new Condition("award.std.code=:code", getUser(
				request.getSession()).getName()));
		addCollection(request, "awards", utilService.search(query));
		return forward(request);
	}

	public ActionForward punishList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityQuery query = new EntityQuery(Punishment.class, "punish");
		query.add(new Condition("punish.std.code=:code", getUser(
				request.getSession()).getName()));
		addCollection(request, "punishs", utilService.search(query));
		return forward(request);
	}
}
