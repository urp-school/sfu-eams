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
 * chaostone             2006-11-22            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.system.file;

import java.io.File;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import com.ekingstar.commons.mvc.web.download.DownloadHelper;
import com.ekingstar.commons.security.utils.EncryptUtil;
import com.ekingstar.eams.system.config.SystemConfig;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.system.file.FilePath;
import com.shufe.web.action.common.DispatchBasicAction;

public class FileAction extends DispatchBasicAction {

	/**
	 * 下载文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void download(HttpServletRequest request, HttpServletResponse response,
			String fileAbsolutePath) throws Exception {
		download(request, response, fileAbsolutePath, getDownloadFileName(fileAbsolutePath));

	}

	/**
	 * 添加一个显示的下载的名字
	 * 
	 * @param response
	 * @param fileAbsolutePath
	 * @param displayFileName
	 * @throws Exception
	 */
	protected void download(HttpServletRequest request, HttpServletResponse response,
			String fileAbsolutePath, String displayFileName) throws Exception {
		File file = new File(fileAbsolutePath);
		if (!file.exists()) {
			response.getWriter().write("without file path:[" + fileAbsolutePath + "]");
			return;
		}
		DownloadHelper.download(request, response, file, displayFileName);
	}

	/**
	 * 上传文件(模版方法)
	 * 
	 * @param request
	 * @param absolutePath
	 *            上传路径
	 * 
	 * <pre>
	 *        子类需要重载afterUpload以实施上传后的工作
	 *        重载getFileName以决定上传文件保存的文件名
	 * </pre>
	 * 
	 * @return
	 */
	protected ActionForward upload(HttpServletRequest request, String storeAbsolutePath)
			throws Exception {
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		upload.setHeaderEncoding("utf-8");
		List items = upload.parseRequest(request);
		if (!FilePath.isPathExists(storeAbsolutePath)) {
			request.setAttribute("filePath", storeAbsolutePath);
			return forward(request, "/pages/system/systemConfig/filePathError");
		}
		if (!storeAbsolutePath.endsWith(File.separator)) {
			storeAbsolutePath += File.separator;
		}
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			FileItem element = (FileItem) iter.next();
			FileItem itemtemp = element;
			if (!element.isFormField()) {
				String fileName = element.getName();
				if (StringUtils.isEmpty(fileName))
					continue;
				File newFile = new File(storeAbsolutePath + getFileName(request, fileName));
				FileUtils.touch(newFile);
				itemtemp.write(newFile);
				afterUpload(request, newFile, fileName);
			}
		}
		return null;
	}

	/**
	 * 上传后处理
	 * 
	 * @param file
	 */
	protected void afterUpload(HttpServletRequest request, File file, String updloadFilePath) {
		log.info(" user upload file from [" + updloadFilePath + "] and store at["
				+ file.getAbsolutePath() + "] on" + new Date(System.currentTimeMillis()));

	}

	/**
	 * 默认将文件的路径作为一个key生成md5的编码(保留扩展名).<br>
	 * 不要从上传路径总截取最后一个元素做为文件名,可能长度无法在服务上存储
	 * 
	 * @param uploadAbsolutePath
	 * @return
	 */
	protected String getFileName(HttpServletRequest request, String uploadAbsolutePath) {
		int commaIndex = uploadAbsolutePath.lastIndexOf(".");
		if (-1 != commaIndex) {
			return EncryptUtil.encodePassword(uploadAbsolutePath)
					+ uploadAbsolutePath.substring(commaIndex, uploadAbsolutePath.length());
		} else
			return EncryptUtil.encodePassword(uploadAbsolutePath);
	}

	/**
	 * 从绝对路径中找出最后的文件名
	 * 
	 * @param fileAbsolutePath
	 * @return
	 */
	public String getDownloadFileName(String fileAbsolutePath) throws Exception {
		String attch_name = DownloadHelper.getAttachName(fileAbsolutePath);
		return URLEncoder.encode(attch_name, "utf-8");
	}

	protected String getFileRealPath(String kind, HttpServletRequest request) {
		SystemConfig config = SystemConfigLoader.getConfig();
		String defaultPath = request.getSession().getServletContext().getRealPath(
				FilePath.fileDirectory);
		return FilePath.getRealPath(config, kind, defaultPath);
	}

}
