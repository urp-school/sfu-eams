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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone            2006-11-28          Created
 * zq                   2007-09-18          在buildQuery()中，添加了学生大类查询；
 * zq                   2007-09-19          同上
 ********************************************************************************/

package com.shufe.web.action.fee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.std.Student;
import com.shufe.service.fee.FeeDetailService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 收费查询
 * 
 * @author chaostone
 */
public class FeeSearchAction extends CalendarRestrictionSupportAction {

	protected FeeDetailService feeDetailService;

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		addCollection(request, "stdTypeList", getStdTypes(request));
		initBaseCodes(request, "feeTypes", FeeType.class);
		return forward(request);
	}

	/**
	 * 查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EntityQuery query = buildQuery(request);
		Collection fees = (Collection) utilService.search(query);
		addCollection(request, "fees", fees);

		Set stds = new HashSet();
		for (Iterator it = fees.iterator(); it.hasNext();) {
			FeeDetail fee = (FeeDetail) it.next();
			stds.add(fee.getStd());
		}
		addCollection(request, "stdList", new ArrayList(stds));
		return forward(request);
	}

	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(FeeDetail.class, "feeDetail");
		populateConditions(request, query,
				"feeDetail.std.type.id,feeDetail.calendar.studentType.id");
		Long stdTypeId = getLong(request, "feeDetail.std.type.id");
		if (stdTypeId == null) {
			stdTypeId = getLong(request, "feeDetail.calendar.studentType.id");
		}
		DataRealmUtils.addDataRealms(query, new String[] { "feeDetail.std.type.id",
				"feeDetail.std.department.id" }, getDataRealmsWith(stdTypeId, request));
		List conditions = QueryRequestSupport
				.extractConditions(request, Student.class, "std", null);
		String className = request.getParameter("className");
		if (!conditions.isEmpty() || !StringUtils.isEmpty(className)) {
			query.join(query.getAlias() + ".std", "std");
		}
		query.getConditions().addAll(conditions);
		if (!StringUtils.isEmpty(className)) {
			query.join("std.adminClasses", "adminClass");
			query.add(Condition.like("adminClass.name", className));
		}
		query.setLimit(getPageLimit(request));
		String orderBy = request.getParameter("byWhat");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = request.getParameter("orderBy");
		}
		query.setOrders(OrderUtils.parser(orderBy));
		return query;
	}

	protected Collection getExportDatas(HttpServletRequest request) {
		Long[] ids = SeqStringUtil.transformToLong(request.getParameter("ids"));
		if (ids == null || ids.length == 0) {
			EntityQuery query = buildQuery(request);
			query.setLimit(null);
			return utilService.search(query);
		} else {
			return utilService.load(FeeDetail.class, "id", ids);
		}

	}

	public void setFeeDetailService(FeeDetailService feeDetailService) {
		this.feeDetailService = feeDetailService;
	}

}
