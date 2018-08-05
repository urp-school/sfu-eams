//$Id: SpecialityStatAction.java,v 1.1 2007-4-3 下午04:47:22 chaostone Exp $
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
 *chaostone      2007-4-3         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo.stat;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.state.SubjectCategory;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.service.system.baseinfo.stat.SpecialityStatService;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 专业统计
 * 
 * @author chaostone
 * 
 */
public class SpecialityStatAction extends RestrictionSupportAction {
	SpecialityStatService specialityStatService;

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return forward(request);
	}

	/**
	 * 专业设置列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityQuery query = new EntityQuery(Speciality.class, "speciality");
		populateConditions(request, query);
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "specialities", utilService.search(query));
		return forward(request);
	}

	/**
	 * 专业分布
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward distribution(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityQuery query = new EntityQuery(Speciality.class, "speciality");
		populateConditions(request, query);
		query.addOrder(new Order("speciality.department.name"));
		addCollection(request, "specialities", utilService.search(query));
		return forward(request);
	}

	/**
	 * 专业结构
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward structure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityQuery query = new EntityQuery(Speciality.class, "speciality");
		query
				.setSelect("new  com.shufe.service.util.stat.StatItem(speciality.subjectCategory.id,count(*))");
		populateConditions(request, query);
		query.groupBy("speciality.subjectCategory.id");
		Collection stats = utilService.search(query);
		new StatHelper(utilService).setStatEntities(stats,
				SubjectCategory.class);
		addCollection(request, "stats", stats);
		return forward(request);
	}

	public void setSpecialityStatService(
			SpecialityStatService specialityStatService) {
		this.specialityStatService = specialityStatService;
	}

}
