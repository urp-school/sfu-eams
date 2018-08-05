//$Id: RationWorkloadAction.java,v 1.2 2006/08/18 03:25:59 cwx Exp $
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
 * chenweixiong              2005-11-15         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.ration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.model.workload.ration.RationWorkload;
import com.shufe.service.workload.ration.RationWorkloadService;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * @author hj
 * 
 */
public class RationWorkloadAction extends RestrictionSupportAction {

	/**
	 * 列出列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String departmentIds = getDepartmentIdSeq(request);
		Pagination rationWorkloadPagi = rationWorkloadService.findRations(rationWorkloadService
				.getRationWorkloads(departmentIds), getPageNo(request), getPageSize(request));
		addOldPage(request, "rationWorkloads", rationWorkloadPagi);
		return forward(request);
	}

	/**
	 * 添加一个对象
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
		RationWorkload rationWorkload = (RationWorkload) populateEntity(request,
				RationWorkload.class, "rationWorkload");
		utilService.saveOrUpdate(rationWorkload);
		return redirect(request, "index", "info.save.success");
	}

	/**
	 * 得到更新对象
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Long workloadId = getLong(request, "rationWorkloadId");
		RationWorkload rationWorkload = null;
		if (null != workloadId) {
			rationWorkload = (RationWorkload) utilService.load(RationWorkload.class, workloadId);
		} else {
			rationWorkload = new RationWorkload();
		}
		String departmentIds = getDepartmentIdSeq(request);
		List departmentList = departmentService.getDepartments(departmentIds);
		addCollection(request, "departmentList", departmentList);
		request.setAttribute("rationWorkload", rationWorkload);
		return forward(request);
	}

	/**
	 * 删除对象
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
		String rationWorkloadIds = request.getParameter("rationWorkloadIds");
		String[] rationWorkloadId = rationWorkloadIds.split(",");
		for (int i = 0; i < rationWorkloadId.length; i++) {
			try {
				RationWorkload workload = (RationWorkload) utilService.get(RationWorkload.class,
						Long.valueOf(rationWorkloadId[i]));
				utilService.remove(workload);
			} catch (Exception e) {
				return redirect(request, "index", "info.delete.failure");
			}
		}
		return redirect(request, "index", "info.delete.success");
	}

	/**
	 * <code>rationWorkloadService<code>
	 *rationWorkloadService RationWorkloadService
	 */
	private RationWorkloadService rationWorkloadService;

	/**
	 * @param rationWorkloadService
	 *            The rationWorkloadService to set.
	 */
	public void setRationWorkloadService(RationWorkloadService rationWorkloadService) {
		this.rationWorkloadService = rationWorkloadService;
	}
}
