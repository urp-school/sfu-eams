//$Id: PreAnswerSearchAction.java Mar 9, 2008 2:46:48 PM chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2008. All rights reserved.
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
 * ============   ============  ============
 * chaostone      Mar 9, 2008  Created
 *  
 ********************************************************************************/
package com.shufe.web.action.degree.thesis.preAnswer;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.security.Resource;
import com.shufe.model.degree.thesis.answer.PreAnswer;
import com.shufe.model.std.Student;
import com.shufe.model.system.security.DataRealm;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

public class PreAnswerSearchAction extends RestrictionSupportAction {

	protected EntityQuery buildPreAnswerQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(PreAnswer.class, "preAnswer");
		populateConditions(request, query);
		query.getConditions().addAll(
				QueryRequestSupport.extractConditions(request, Student.class,
						"student", "student.type.id"));
		query.join("preAnswer.thesisManage.student", "student");
		// 权限
		Long stdTypeId = RequestUtils.getLong(request, "student.type.id");

		Resource resource = getResource(request);
		if (null != resource && !resource.getPatterns().isEmpty()) {
			DataRealmUtils.addDataRealms(query, new String[] {
					"student.type.id", "student.department.id" },
					restrictionHelper.getDataRealmsWith(stdTypeId, request));
		} else {
			if (null != stdTypeId) {
				DataRealmUtils
						.addDataRealm(query,
								new String[] { "student.type.id" },
								new DataRealm(studentTypeService
										.getStdTypeIdSeqUnder(stdTypeId), null));
			}
		}
		QueryRequestSupport.addDateIntervalCondition(request, query,
				"finishOn", "beginOn", "endOn");
		query.setLimit(getPageLimit(request));
		query
				.addOrder(OrderUtils.parser(request
						.getParameter(Order.ORDER_STR)));
		return query;
	}
}
