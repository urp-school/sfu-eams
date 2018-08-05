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
 * chaostone             2007-1-19            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.system.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.shufe.model.system.file.Document;
import com.shufe.model.system.file.FilePath;
/**
 * 下载数据模板
 * @author chaostone
 *
 */
public class DataTemplateAction extends FileAction {
	/**
	 * 下载文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long documentId = getLong(request, "document.id");
		if (!ValidEntityKeyPredicate.getInstance().evaluate(documentId)) {
			response.getWriter()
					.write("without template with id:" + documentId);
			return;
		}
		Document document = (Document) utilService.get(Document.class,
				documentId);
		if (null == document) {
			response.getWriter()
					.write("without template with id:" + documentId);
			return;
		}
		download(request, response,
				getFileRealPath(FilePath.TEMPLATE_UPLOAD, request)
						+ document.getPath());

	}
}
