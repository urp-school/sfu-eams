//$Id: CreditFeeDefaultAction.java,v 1.1 2007 六月 18 16:22:54 Administrator Exp $
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
 * chenweixiong         2007-06-18          Created
 * zq                   2007-09-19          buildQuery()方法中的查询改为：学生大类
 *                                          查询
 * zq                   2007-10-16          修复saveAndForwad()保存错误EntityQuery query
 *                                          处
 ********************************************************************************/

package com.shufe.web.action.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.shufe.model.fee.CreditFeeDefault;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

public class CreditFeeDefaultAction extends RestrictionExampleTemplateAction {

	/**
	 * 组建查询条件
	 * 
	 * @see com.shufe.web.action.common.ExampleTemplateAction#buildQuery(javax.servlet.http.HttpServletRequest)
	 */
	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(CreditFeeDefault.class, "default");
		DataRealmUtils.addDataRealms(query, new String[] { "default.stdType.id", null },
				getDataRealmsWith(getLong(request, "stdType.id"), request));
		query.setLimit(getPageLimit(request));
		query.setOrders(OrderUtils.parser(request.getParameter("orderBy")));
		return query;
	}

	/**
	 * 打印预览
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		addCollection(request, "creditFeeDefaults", utilService.loadAll(CreditFeeDefault.class));
		return forward(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.web.action.common.ExampleTemplateAction#saveAndForwad(javax.servlet.http.HttpServletRequest,
	 *      com.ekingstar.commons.model.Entity)
	 */
	protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
			throws Exception {
		CreditFeeDefault creditFeeDefault = (CreditFeeDefault) entity;
		EntityQuery query = new EntityQuery(CreditFeeDefault.class, "creditFeeDefault");
		if (creditFeeDefault.getCourseType() == null
				|| creditFeeDefault.getCourseType().getId() == null
				|| creditFeeDefault.getCourseType().getId().longValue() == 0) {
			creditFeeDefault.setCourseType(null);
			query.add(new Condition(
					"creditFeeDefault.stdType.id = :id and creditFeeDefault.courseType is null",
					creditFeeDefault.getStdType().getId()));
		} else {
			query
					.add(new Condition(
							"creditFeeDefault.stdType.id = :id and creditFeeDefault.courseType = :courseType",
							creditFeeDefault.getStdType().getId(), creditFeeDefault.getCourseType()));
		}
		List list = (List) utilService.search(query);
		if ((list.size() == 0 && entity.getEntityId() == null)
				|| (list.size() == 0 || (list.size() == 1 && ((CreditFeeDefault) list.get(0))
						.getId().equals(entity.getEntityId())))) {
			return super.saveAndForwad(request, entity);
		} else {
			saveErrors(request.getSession(), ForwardSupport
					.buildMessages(new String[] { "error.code.existed" }));
			return forward(request, new Action(this, "edit"));
		}
	}

	protected void editSetting(HttpServletRequest request, Entity entity) throws Exception {
		addCollection(request, "courseTypes", baseCodeService.getCodes(CourseType.class));
		setDataRealm(request, hasStdType);
	}
}
