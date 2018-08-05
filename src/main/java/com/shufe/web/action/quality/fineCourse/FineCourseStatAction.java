//$Id: FineCourseStatAction.java,v 1.1 2007-4-16 上午12:13:59 chaostone Exp $
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
 *chaostone      2007-4-16         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.fineCourse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.FineCourseLevel;
import com.shufe.model.quality.fineCourse.FineCourse;
import com.shufe.web.action.common.RestrictionSupportAction;

public class FineCourseStatAction extends RestrictionSupportAction {
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		addCollection(request, "levels", baseCodeService.getCodes(FineCourseLevel.class));
		return forward(request);
	}

	public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List stats = new ArrayList();
		EntityQuery entityQuery = new EntityQuery(FineCourse.class, "fineCourse");
		StringBuffer selectClause = new StringBuffer();
		String[] levelIds = StringUtils.split(request.getParameter("levelIds"), ",");
		List levels = new ArrayList();
		if (null != levelIds) {
			for (int i = 0; i < levelIds.length; i++) {
				selectClause.append(",sum(Case  WHEN (fineCourse.level.id=" + levelIds[i]
						+ ") THEN 1 ELSE 0 END)");
				levels.add(utilService.get(FineCourseLevel.class, NumberUtils.createLong(levelIds[i])));
			}
		}

		Condition condition = new Condition("fineCourse.passedYear=:year");
		entityQuery.groupBy("fineCourse.passedYear");

		String[] years = StringUtils.split(request.getParameter("years"), ",");
		try {
			for (int i = 0; i < years.length; i++) {
				entityQuery.setQueryStr(null);
				entityQuery.setSelect(years[i] + selectClause.toString());
				condition.setValues(Collections.singletonList(NumberUtils.createInteger(years[i])));
				stats.addAll(utilService.search(entityQuery));
			}
		} catch (Exception e) {
			stats.clear();
		}
		addCollection(request, "levels", levels);
		addCollection(request, "stats", stats);
		return forward(request);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EntityQuery query = new EntityQuery(FineCourse.class, "fineCourse");
		populateConditions(request, query);
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));

		String[] years = StringUtils.split(request.getParameter("years"), ",");
		if (null != years) {
			Integer[] iyears = SeqStringUtil.transformToInteger(years);
			if (iyears.length != 0) {
				query.add(new Condition("fineCourse.passedYear in(:years)", iyears));
			}
		}
		addCollection(request, "fineCourses", utilService.search(query));
		return forward(request);
	}

}
