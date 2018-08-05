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
 * zq                   2007-09-20          在buildStatFeeTypeQuery()和
 *                                          buildCreditFeeQuery()中，增加了学生大类
 *                                          查询语句；另为了学生大类查询方便，新建了一个
 *                                          buildAllStdTypeIdToQuery()方法
 *  
 ********************************************************************************/
package com.shufe.web.action.fee;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.model.fee.FeeDetail;
import com.shufe.model.fee.stat.CreditFeeStat;
import com.shufe.model.fee.stat.FeeTypeStat;
import com.shufe.model.std.Student;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 收费统计
 * 
 * @author chaostone
 * 
 */
public class FeeStatAction extends CalendarRestrictionSupportAction {

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statFeeType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EntityQuery query = buildQuery(request);
		Collection accounts = (Collection) utilService.search(query);
		fillStudentAndFeeType(accounts);
		addCollection(request, "accounts", accounts);
		return forward(request);
	}

	/**
	 * 统计学费与学分不符的记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward creditFeeStats(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EntityQuery query = buildQuery(request);
		addCollection(request, "creditFeeStats", utilService.search(query));
		return forward(request);
	}

	/**
	 * 获得“应-实 缴费不符统计”的查询条件
	 * 
	 * @param request
	 * @return
	 */
	protected EntityQuery buildStatFeeTypeQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(FeeDetail.class, "feeDetail");
		populateConditions(request, query, "calendar.studentType.id,feeDetail.std.type.id");
		addAllStdTypeIdToQuery(request, query, "feeDetail.std.type.id", "calendar.studentType.id");

		String className = null;
		if (!StringUtils.isEmpty((className = request.getParameter("feeDetail_className")))) {
			query.join("feeDetail.std.adminClasses", "adminClass");
			query.add(Condition.like("adminClass.name", className));
		}
		query.add(new Condition("feeDetail.calendar.id=:calendarId",
				getLong(request, "calendar.id")));
		query
				.setSelect("new com.shufe.model.fee.stat.FeeTypeStat(feeDetail.std.id, feeDetail.type.id, sum(feeDetail.shouldPay), sum(feeDetail.payed))");
		query
				.groupBy("feeDetail.std.id, feeDetail.type.id having sum(feeDetail.shouldPay) <> sum(feeDetail.payed)");
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		return query;
	}

	/**
	 * 获得“学费－学分 不符统计”的查询条件
	 * 
	 * @param request
	 * @return
	 */
	protected EntityQuery buildCreditFeeQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(CreditFeeStat.class, "creditFeeStat");
		populateConditions(request, query, "calendar.studentType.id,creditFeeStat.student.type.id");
		addAllStdTypeIdToQuery(request, query, "creditFeeStat.student.type.id",
				"calendar.studentType.id");
		String className = request.getParameter("creditFeeStat_className");
		if (!StringUtils.isEmpty(className)) {
			query.join("std.adminClasses", "adminClass");
			query.add(Condition.like("adminClass.name", className));
		}
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		Long stdTypeId = getLong(request, "calendar.studentType.id");
		query.add(new Condition("creditFeeStat.student.type.id in(:stdTypeTreeIds)", SeqStringUtil
				.transformToLong(getStdTypeIdSeqOf(stdTypeId, request))));
		DataRealmUtils.addDataRealms(query, new String[] { "creditFeeStat.student.type.id",
				"creditFeeStat.student.department.id" }, getDataRealms(request));
		return query;
	}

	/**
	 * 根据indexPage选择查询条件收集的方法
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected EntityQuery buildQuery(HttpServletRequest request) throws Exception {
		if (StringUtils.isEmpty(request.getParameter("indexPage"))
				|| request.getParameter("indexPage").equals("statFeeType")) {
			return buildStatFeeTypeQuery(request);
		} else {
			return buildCreditFeeQuery(request);
		}
	}

	/**
	 * 为学生大类查询收集所查学生类别id的全集学生类别
	 * 
	 * @param request
	 * @param query
	 * @param stdTypeIdAttr
	 * @param stdTypeIdParam
	 */
	protected void addAllStdTypeIdToQuery(HttpServletRequest request, EntityQuery query,
			String stdTypeIdAttr, String stdTypeIdParam) {
		Long stdTypeId = getLong(request, stdTypeIdAttr);
		if (stdTypeId == null) {
			stdTypeId = getLong(request, stdTypeIdParam);
		}
		if (stdTypeId != null) {
			restrictionHelper.addStdTypeTreeDataRealm(query, stdTypeId, stdTypeIdAttr, request);
		}
	}

	/**
	 * 收集要导出的数据
	 * 
	 * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
	 */
	protected Collection getExportDatas(HttpServletRequest request) {
		EntityQuery query;
		try {
			query = buildQuery(request);
			query.setLimit(null);
			List datas = (List) utilService.search(query);
			fillStudentAndFeeType(datas);
			return datas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 组建FeeTypeStat对象
	 * 
	 * @param accounts
	 */
	private void fillStudentAndFeeType(Collection accounts) {
		for (Iterator it = accounts.iterator(); it.hasNext();) {
			FeeTypeStat stat = (FeeTypeStat) it.next();
			stat.setStd((Student) utilService.get(Student.class, stat.getStd().getId()));
			stat.setType((FeeType) utilService.get(FeeType.class, stat.getType().getId()));
		}
	}
}
