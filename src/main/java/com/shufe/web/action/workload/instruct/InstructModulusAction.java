//$Id: InstructModulusAction.java,v 1.3 2006/12/19 13:08:42 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-17         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.instruct;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.workload.instruct.InstructModulus;
import com.shufe.service.workload.ModulusService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * @author hj
 * 
 */
public class InstructModulusAction extends RestrictionExampleTemplateAction {

	private ModulusService InstructModulusService;

	/**
	 * 列出查询得到的所有的结果<br>
	 * 跳转页面是index.ftl
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EntityQuery query = new EntityQuery(InstructModulus.class, "instructModulus");
		DataRealmUtils.addDataRealms(query, new String[] { "instructModulus.studentType.id",
				"instructModulus.department.id" }, getDataRealms(request));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "instructModuli", utilService.search(query));
		return forward(request);
	}

	/**
	 * 与 home 方法的过程一样，只作浏览，不作修改，<br>
	 * 跳转页面不一样，为index.ftl
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EntityQuery query = new EntityQuery(InstructModulus.class, "instructModulus");
		DataRealmUtils.addDataRealms(query, new String[] { "instructModulus.studentType.id",
				"instructModulus.department.id" }, getDataRealms(request));
		query.setLimit(getPageLimit(request));
		addCollection(request, "instructModuli", utilService.search(query));
		return forward(request);
	}

	/**
	 * 添加一个教学任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InstructModulus instructModulus = (InstructModulus) populateEntity(request,
				InstructModulus.class, "instructModulus");
		if (InstructModulusService.checkModulus(instructModulus)) {
			utilService.saveOrUpdate(instructModulus);
			return redirect(request, "index", "info.save.success");
		} else {
			return redirect(request, "index", "instructWorkload.errorsModulus");
		}
	}

	/**
	 * 得到一个更新系数的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String instructModulusId = request.getParameter("instructModulusId");
		InstructModulus instructModulus = null;
		if (StringUtils.isNotEmpty(instructModulusId)) {
			instructModulus = (InstructModulus) utilService.load(InstructModulus.class, Long
					.valueOf(instructModulusId));
		} else {
			instructModulus = new InstructModulus();
		}
		setDataRealm(request, hasStdTypeDepart);
		request.setAttribute("instructModulus", instructModulus);
		return forward(request);
	}

	/**
	 * 删除一个或者一堆教学系数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String instructModulusIds = request.getParameter("instructModulusIds");
		List instructModulusList = utilService.load(InstructModulus.class, "id",
				SeqStringUtil.transformToLong(instructModulusIds));
		for (int i = 0; i < instructModulusList.size(); i++) {
			try {
				InstructModulus instructModulus = (InstructModulus) instructModulusList.get(i);
				List deparmentList = getDeparts(request);
				if (deparmentList.contains(instructModulus.getDepartment())) {
					utilService.remove(instructModulus);
				} else {
					Results.addObject("departmentName", instructModulus.getDepartment().getName());
					return forward(mapping, request, "workloadModuleUpdateOrDeleteErrors");
				}
			} catch (Exception e) {
                return redirect(request, "index",  "field.teachModulus.errorsByDelete");
			}
		}
		return redirect(request, "index", "info.delete.success");
	}

	/**
	 * @param instructModulusService
	 *            The instructModulusService to set.
	 */
	public void setInstructModulusService(ModulusService instructModulusService) {
		this.InstructModulusService = instructModulusService;
	}

}
