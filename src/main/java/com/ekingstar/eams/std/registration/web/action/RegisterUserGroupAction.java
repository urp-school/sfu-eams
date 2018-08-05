//$Id: RegisterUserGroupAction.java,v 1.1 2008-1-30 下午04:39:37 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * Administrator              2008-1-30         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.registration.web.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.std.registration.model.RegisterUserGroup;
import com.ekingstar.security.User;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class RegisterUserGroupAction extends RestrictionExampleTemplateAction {

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RegisterUserGroup userGroup = (RegisterUserGroup) populateEntity(
				request, RegisterUserGroup.class, "registerUserGroup");
		String beginOn = get(request, "registerUserGroup.beginAt");
		String beginHour = get(request, "beginHour");
		String beginMinute = get(request, "beginMinute");
		String endOn = get(request, "registerUserGroup.endAt");
		String endHour = get(request, "endHour");
		String endMinute = get(request, "endMinute");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sdf.parse(beginOn + " " + beginHour + ":" + beginMinute);
		Timestamp beginAt = new Timestamp(sdf.getCalendar().getTimeInMillis());
		sdf.parse(endOn + " " + endHour + ":" + endMinute);
		Timestamp endAt = new Timestamp(sdf.getCalendar().getTimeInMillis());
		userGroup.setBeginAt(beginAt);
		userGroup.setEndAt(endAt);
		userGroup.setName(get(request, "registerUserGroup.name"));
		utilService.saveOrUpdate(userGroup);
		return redirect(request, "search", "info.save.success");
	}
	/**
	 * 人员维护
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward maintenance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RegisterUserGroup userGroup = (RegisterUserGroup) utilService.get(
				RegisterUserGroup.class, getLong(request, "id"));
		Set users = userGroup.getUsers();
		addCollection(request, "users", users);
		return forward(request);
	}
	/**
	 * 用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityQuery query = new EntityQuery(User.class,"user");
		populateConditions(request, query);
		query.addOrder(OrderUtils.parser(request.getParameter(Order.ORDER_STR)));
		query.setLimit(getPageLimit(request));
		addCollection(request, "users", utilService.search(query));
		return forward(request);
	}
	/**
	 * 增加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = get(request, "ids");
		Long id=getLong(request, "id");
		RegisterUserGroup userGroup = (RegisterUserGroup) utilService.get(
				RegisterUserGroup.class, id);
		List users= utilService.load(User.class, "id", SeqStringUtil.transformToLong(ids));
		userGroup.addUser(users);
		utilService.saveOrUpdate(userGroup);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 删除权限用户
	 */
	public ActionForward removeUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = get(request, "ids");
		RegisterUserGroup userGroup = (RegisterUserGroup) utilService.get(
				RegisterUserGroup.class, getLong(request, "id"));
		List users= utilService.load(User.class, "id", SeqStringUtil.transformToLong(ids));
		userGroup.removeUsers(users);
		utilService.saveOrUpdate(userGroup);
		return redirect(request, "search", "info.delete.success");
	}

}
