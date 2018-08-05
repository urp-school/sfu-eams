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

import java.io.File;
import java.net.URLEncoder;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.shufe.model.std.BasicInfo;
import com.shufe.model.std.Student;
import com.shufe.web.action.system.file.FileAction;

public class StudentPhotoFileAction extends FileAction {
	
	private static final String DEFAULTPHOTO = "/images/defaultPhoto.JPG";

	public ActionForward uploadForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("studentId", request.getParameter("student.id"));
		return forward(request);
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
		return forward(request, "uploadSuccessRedirector");
	}
	
	protected String getFileName(HttpServletRequest request,
			String uploadAbsolutePath) {
		uploadAbsolutePath = super.getFileName(request, uploadAbsolutePath);
		uploadAbsolutePath = getStdCode(request) + uploadAbsolutePath.substring(uploadAbsolutePath.lastIndexOf("."));		
		try {
			return URLEncoder.encode(uploadAbsolutePath, "UTF-8");
		} catch (Exception e) {
			log.info("[FileAction->getFileName]error in encode");
			return uploadAbsolutePath;
		}

	}

	/**
	 * @param request
	 * @return
	 */
	protected String getStdCode(HttpServletRequest request) {
		Long studentId = getLong(request, "student.id");
		if (!ValidEntityKeyPredicate.getInstance().evaluate(studentId)) {
			throw new RuntimeException("without student with id:" + studentId);
		}
		return ((Student) utilService.get(Student.class, studentId)).getCode();
	}
	
	/**
	 * 将上传文档信息记录到数据库中.
	 */
	protected void afterUpload(HttpServletRequest request, File file,String updloadFilePath) {
		Student student = (Student) utilService.load(Student.class, "code", getStdCode(request)).iterator().next();
		BasicInfo basicInfo =student.getBasicInfo();
		basicInfo.setPhotoName(file.getName());
		student.setModifyAt(new Date(System.currentTimeMillis()));
		utilService.saveOrUpdate(student);
		super.afterUpload(request, file, updloadFilePath);
	}
	
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long studentId = getStudentId(request);
		if (!ValidEntityKeyPredicate.getInstance().evaluate(studentId)) {
			response.getWriter()
					.write("without student with id:" + studentId);
			return;
		}
		Student student = (Student) utilService.get(Student.class,
				studentId);
		String photoName = student.getBasicInfo().getPhotoName();
		if (StringUtils.isEmpty(photoName)) {
			/*response.getWriter()
					.write("no photoName of student with id:" + studentId);*/
			download(request,response, request.getSession().getServletContext()
					.getRealPath("")
					+ DEFAULTPHOTO);
			return;
		}
		download(request,response, getFileRealPath("stdPhoto", request)
				+ photoName);

	}
	
	public void downloadError(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		download(request,response, getFileRealPath("stdPhoto", request)
				+ DEFAULTPHOTO);

	}
	
	protected Long getStudentId(HttpServletRequest request) {
		return getLong(request, "student.id");
	}

}
