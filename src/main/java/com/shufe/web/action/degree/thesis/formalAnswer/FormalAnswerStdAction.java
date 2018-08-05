//$Id: FormalAnswerStdAction.java,v 1.1 2007-1-31 16:32:29 Administrator Exp $
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
 * chenweixiong              2007-1-31         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.formalAnswer;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.answer.FormalAnswer;
import com.shufe.model.std.Student;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.web.action.system.file.FileAction;

public class FormalAnswerStdAction extends FileAction {
	public ThesisManageService thesisManageService;
	
	/**
	 * @param thesisManageService The thesisManageService to set.
	 */
	public void setThesisManageService(ThesisManageService thesisManageService) {
		this.thesisManageService = thesisManageService;
	}

	/**
	 * @see com.shufe.web.action.common.DispatchBasicAction#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String apply = request.getParameter("apply");
		Student student = getStudentFromSession(request.getSession());
		ThesisManage thesisManage = thesisManageService
				.getThesisManage(student);
		request.setAttribute("thesisManage", thesisManage);
		if (StringUtils.isBlank(apply) || "load".equals(apply)) {
			if (null == thesisManage
					|| !thesisManage.checkObjectId(thesisManage.getTopicOpen())) {
				request
						.setAttribute("reason",
								"field.formalAnswer.noOpenTopic");
				request.setAttribute("flag", "topicOpen");
				return forward(request, "error");
			}
			return forward(request, "loadApply");
		} else if ("apply".equals(apply)) {
			if (!thesisManage.checkObjectId(thesisManage.getFormalAnswer())) {
				FormalAnswer formalAnswer = new FormalAnswer();
				formalAnswer.setStudent(student);
				formalAnswer.setThesisManage(thesisManage);
				utilService.saveOrUpdate(formalAnswer);
				thesisManage.setFormalAnswer(formalAnswer);
				utilService.saveOrUpdate(thesisManage);
			}
			return redirect(request, "doApply", "info.action.success");
		} else {
			return null;
		}
	}

	/**
	 * 确认论文答辩完成
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward affirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long formaAnswerId = getLong(request, "formalAnswerId");
		FormalAnswer formalAnswer = (FormalAnswer) utilService.load(
				FormalAnswer.class, formaAnswerId);
		formalAnswer.setFinishOn(new Date(System.currentTimeMillis()));
		utilService.saveOrUpdate(formalAnswer);
		return redirect(request, "doApply", "info.action.success");
	}
}
