//$Id: TeacherApplyAction.java,v 1.1 2007-2-25 13:41:38 Administrator Exp $
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
 * chenweixiong              2007-2-25         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.tutorManager.tutorChoose;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.shufe.model.degree.tutorManager.tutor.TutorApply;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.file.FilePath;
import com.shufe.service.degree.tutorManager.TutorApplyService;
import com.shufe.web.action.system.file.FileAction;

public class TeacherApplyAction extends FileAction {

	private TutorApplyService tutorApplyService;
	/**
	 * 导师申请页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward tutorApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String apply = request.getParameter("apply");
		Teacher teacher = getTeacherFromSession(request.getSession());
		if (StringUtils.isBlank(apply)) {
			TutorApply tutorApply = new TutorApply();
			tutorApply.setTeacher(teacher);
			List tutorApplys = tutorApplyService.getTutorApplyList(tutorApply, teacher
					.getDepartment().getId().toString());
			request.setAttribute("firstTutorApply",
					tutorApplys.size() > 0 ? (TutorApply) tutorApplys.get(0) : new TutorApply());
			request.setAttribute("tutorApplys", tutorApplys);
			return forward(request, "../tutorApply");
		}
		String tutorTypeId = request.getParameter("tutorTypeId");
		TutorType tutorType = (TutorType) utilService.get(TutorType.class, Long
				.valueOf(tutorTypeId));
		TutorApply tutorApply = new TutorApply();
		tutorApply.setTeacher(teacher);
		tutorApply.setTutorType(tutorType);
		tutorApply.setIsPass(Boolean.FALSE);
		tutorApply.setApplyTime(new Date(System.currentTimeMillis()));
		utilService.saveOrUpdate(tutorApply);
		return redirect(request, "tutorApply", "");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void doDownloadApplyDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = request.getParameter("fileName");
		String fileAbsolutePath = getFileRealPath(FilePath.TEMPLATE_DOWNLOAD, request) + fileName;
		String displayName = request.getParameter("displayName");
		download(request, response, fileAbsolutePath, displayName);
	}

	public void setTutorApplyService(TutorApplyService tutorApplyService) {
		this.tutorApplyService = tutorApplyService;
	}

 
}
