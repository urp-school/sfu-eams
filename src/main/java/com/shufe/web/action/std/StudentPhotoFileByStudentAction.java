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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.web.action.std;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.shufe.model.std.Student;

public class StudentPhotoFileByStudentAction extends StudentPhotoFileAction {
	
	public ActionForward uploadForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return forward(request, "../studentPhotoFile/uploadForm");
	}
	
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        ActionForward f=upload(request, getFileRealPath("stdPhoto", request));
        if (f != null)
            return f;
        ActionMessages actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.upload.success"));
		addErrors(request, actionMessages);
		return forward(request, "../studentPhotoFile/uploadSuccessRedirector");
	}
	
	protected String getStdCode(HttpServletRequest request) {
		return getUser(request.getSession()).getName();
	}
	
	protected Long getStudentId(HttpServletRequest request) {
		return ((Student)utilService.load(Student.class, "code", getStdCode(request)).iterator().next()).getId();
	}

}
