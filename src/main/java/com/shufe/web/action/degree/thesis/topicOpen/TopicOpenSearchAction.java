//$Id: TopicOpenSearchAction.java Mar 9, 2008 3:51:08 PM chaostone Exp $
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
package com.shufe.web.action.degree.thesis.topicOpen;

import javax.servlet.http.HttpServletRequest;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.security.Resource;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.topicOpen.TopicOpen;
import com.shufe.model.std.Student;
import com.shufe.model.system.security.DataRealm;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

public class TopicOpenSearchAction extends RestrictionSupportAction {

	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(ThesisManage.class, "thesisManager");
		query.add(new Condition("thesisManager.topicOpen is not null"));
		query.getConditions().addAll(
				QueryRequestSupport.extractConditions(request, TopicOpen.class,
						"topicOpen", ""));
		query.getConditions().addAll(
				QueryRequestSupport.extractConditions(request, Student.class,
						"student", "student.type.id"));
		// 权限
		Long stdTypeId = RequestUtils.getLong(request, "student.type.id");
		Resource resource = getResource(request);
		if (!resource.getPatterns().isEmpty()) {
			DataRealmUtils.addDataRealms(query, new String[] {
					"thesisManager.student.type.id",
					"thesisManager.student.department.id" }, getDataRealmsWith(
					stdTypeId, request));
		} else {
			if (null != stdTypeId) {
				DataRealmUtils.addDataRealm(query,
						new String[] { "thesisManager.student.type.id" },
						new DataRealm(studentTypeService
								.getStdTypeIdSeqUnder(stdTypeId), null));
			}
		}
		QueryRequestSupport.addDateIntervalCondition(request, query,
				"topicOpen", "finishOn", "beginOn", "endOn");
		query.setLimit(getPageLimit(request));
		query
				.addOrder(OrderUtils.parser(request
						.getParameter(Order.ORDER_STR)));
		return query;
	}
}
