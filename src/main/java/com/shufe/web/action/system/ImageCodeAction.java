package com.shufe.web.action.system;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DispatchActionSupport;

import com.ekingstar.commons.security.utils.AuthenticationCodeGenerator;

public class ImageCodeAction extends DispatchActionSupport {
	/*
	 * private static final String CONTENT_TYPE = "image/jpeg;charset=GB2312";
	 * private Color[] color = { Color.red, Color.black, Color.orange,
	 * Color.green }; private int baseAng = 30;
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws java.io.IOException, ServletException {
		HttpSession session = request.getSession(true);
		ServletOutputStream outStream = response.getOutputStream();
		String rand = AuthenticationCodeGenerator.genCodeImage(outStream);
		session.setAttribute("Rand", rand);
		outStream.close();
		return null;
	}

	protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
}
