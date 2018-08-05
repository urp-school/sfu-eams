//$Id: TutorStdManagerAction.java,v 1.1 2006/08/15 09:04:25 hc Exp $
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
 * hc             2005-11-24         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.tutorManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.service.degree.tutorManager.TutorService;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 导师带学生
 * 
 * @author hc
 */
public class TutorStdManagerAction extends DispatchBasicAction {

	private TutorService tutorService;

	private DepartmentService departmentService;

	/**
	 * 加载查询导师带学生页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doDefault(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List departmentList = departmentService.getAllColleges();
		Results.addObject("departmentList", departmentList).addObject("doctorType",
				TutorType.DOCTOR_TUTOR).addObject("masterType", TutorType.MASTER_TUTOR);
		return forward(request, "../tutorStd/loadDefault");
	}

	/**
	 * 查询导师信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
		int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();

		Tutor tutor = (Tutor) dynaForm.get("tutor");
		Pagination tutorPage = tutorService.getAllTutor(tutor, null, pageNo, pageSize);
		addOldPage(request, "tutors", tutorPage);
		return forward(request, "../tutorStd/tutorStdList");
	}

	public void setTutorService(TutorService tutorService) {
		this.tutorService = tutorService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

}
