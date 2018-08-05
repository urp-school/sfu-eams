//$Id: StudentRegisterOperation.java,v 1.5 2006/11/30 10:20:52 yd Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-21         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.registration.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.std.registration.Register;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 学生查看自己注册信息
 * 
 * @author chaostone,yangdong,dell
 */
public class StdRegisterAction extends DispatchBasicAction {

	/**
	 * 学生查看注册信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String code = getUser(request.getSession()).getName();
		EntityQuery query = new EntityQuery(Register.class, "register");
		query.add(new Condition("register.std.code =:code", code));
		addCollection(request, "registers", utilService.search(query));
		return forward(request);
	}
}
