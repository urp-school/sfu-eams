//$Id: ThesisDocumentAction.java,v 1.1 2007-4-10 13:42:30 Administrator Exp $
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
 * chenweixiong              2007-4-10         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.MultiPropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.security.User;
import com.shufe.dao.degree.thesis.TacheSettingDao;
import com.shufe.model.degree.thesis.process.TacheSetting;
import com.shufe.model.system.file.DegreeDocument;
import com.shufe.model.system.file.FilePath;
import com.shufe.web.action.system.file.FileAction;

public class ThesisDocumentAction extends FileAction {
	TacheSettingDao tacheSettingDao;

	/**
	 * 进入文档的查询现实列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return forward(request);
	}

	/**
	 * 进入文档环节关联界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward tacheSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultiPropertyComparator pcc = new MultiPropertyComparator(
				"schedule.enrollYear,schedule.studentType.code,tache.code");
		List allTaches = utilService.loadAll(TacheSetting.class);

		Long documentId = getLong(request, "documentId");
		EntityQuery query = new EntityQuery(TacheSetting.class, "tacheSetting");
		query.join("tacheSetting.thesisModels", "thesisModel");
		query.add(new Condition("thesisModel.id=:documentId", documentId));

		List modelTaches = (List) utilService.search(query);
		List availModelTaches = new ArrayList(CollectionUtils.subtract(allTaches, modelTaches));
		Collections.sort(modelTaches, pcc);
		Collections.sort(availModelTaches, pcc);
		request.setAttribute("modelTaches", modelTaches);
		request.setAttribute("availModelTaches", availModelTaches);

		query = new EntityQuery(TacheSetting.class, "tacheSetting");
		query.join("tacheSetting.thesisDocuments", "thesisDocument");
		query.add(new Condition("thesisDocument.id=:documentId", documentId));

		List documentTaches = (List) utilService.search(query);
		List availDocumentTaches = new ArrayList(CollectionUtils
				.subtract(allTaches, documentTaches));
		Collections.sort(modelTaches, pcc);
		Collections.sort(availDocumentTaches, pcc);
		Collections.sort(documentTaches, pcc);
		request.setAttribute("documentTaches", documentTaches);
		request.setAttribute("availDocumentTaches", availDocumentTaches);

		return forward(request);
	}

	/**
	 * 进入文档环节关联界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTacheSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long documentId = getLong(request, "documentId");
		Long[] documentTacheSettingIds = SeqStringUtil.transformToLong(request
				.getParameter("documentTacheSettingIds"));
		Long[] modelTacheSettingIds = SeqStringUtil.transformToLong(request
				.getParameter("modelTacheSettingIds"));
		tacheSettingDao.updateTacheDocuments(documentId, documentTacheSettingIds,
				modelTacheSettingIds);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 得到论文管理的文档列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EntityQuery entityQuery = new EntityQuery(DegreeDocument.class, "degreeDocument");
		populateConditions(request, entityQuery);
		entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		entityQuery.setLimit(getPageLimit(request));
		addCollection(request, "thesisDocuments", utilService.search(entityQuery));
		return forward(request);
	}

	/**
	 * 得到上传页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadUploadPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String documentId = request.getParameter("documentId");
		if (null != documentId) {
			request.setAttribute("documentId", documentId);
		}
		return forward(request);
	}

	/**
	 * 上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward f = upload(request, getFileRealPath(FilePath.DEGREE, request));
		if (f != null)
			return f;
		return redirect(request, "search", "info.upload.success");
	}

	/**
	 * 上传文件后做的操作
	 * 
	 * @see com.shufe.web.action.system.file.FileAction#afterUpload(javax.servlet.http.HttpServletRequest,
	 *      java.io.File, java.lang.String)
	 */
	protected void afterUpload(HttpServletRequest request, File file, String updloadFilePath) {
		super.afterUpload(request, file, updloadFilePath);
		updloadFilePath = updloadFilePath.substring(updloadFilePath.lastIndexOf("\\") + 1);
		updloadFilePath = updloadFilePath.substring(updloadFilePath.lastIndexOf("/") + 1);
		DegreeDocument degreeDocument = new DegreeDocument();
		Long dcoumentId = getLong(request, "documentId");
		// 如果是重载的话 先删除现有的文件
		// 然后更新要重载的存储对象
		if (null != dcoumentId) {
			degreeDocument = (DegreeDocument) utilService.get(DegreeDocument.class, dcoumentId);
			String filePath = getFileRealPath(FilePath.DEGREE, request);
			File oldFile = new File(filePath + degreeDocument.getPath());
			log.info("user [" + getUser(request.getSession()).getName()
					+ "] delete file[" + oldFile.getAbsolutePath() + "]");
			oldFile.delete();
		}
		degreeDocument.setPath(file.getName());
		degreeDocument.setName(updloadFilePath);
		degreeDocument.setUploaded(getUser(request.getSession()));
		degreeDocument.setUploadOn(new Date(System.currentTimeMillis()));
		utilService.saveOrUpdate(degreeDocument);
	}

	/**
	 * @see com.shufe.web.action.system.file.FileAction#download(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.String,
	 *      java.lang.String)
	 */
	public void doDownLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long documentId = getLong(request, "document.id");
		if (!ValidEntityKeyPredicate.getInstance().evaluate(documentId)) {
			response.getWriter().write("without docuemnt with id:" + documentId);
			return;
		}
		DegreeDocument degreeDocument = (DegreeDocument) utilService.load(DegreeDocument.class,
				documentId);
		if (null == degreeDocument) {
			response.getWriter().write("without docuemnt with id:" + documentId);
			return;
		}
		String filePath = getFileRealPath(FilePath.DEGREE, request);
		download(request, response, filePath + degreeDocument.getPath(), degreeDocument.getName());
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String documentIds = request.getParameter("documentIds");
		List documents = utilService.load(DegreeDocument.class, "id", SeqStringUtil
				.transformToLong(documentIds));
		String filePath = getFileRealPath(FilePath.DEGREE, request);
		User user = getUser(request.getSession());
		for (Iterator iter = documents.iterator(); iter.hasNext();) {
			DegreeDocument degreeDocument = (DegreeDocument) iter.next();
			try {
				utilService.remove(degreeDocument);
			} catch (Exception e) {
				return null;
			}
			File file = new File(filePath + degreeDocument.getPath());
			log.info("user [" + user.getName() + "] delete file[" + file.getAbsolutePath()
					+ "]");

			file.delete();
		}
		return redirect(request, "search", "info.delete.success");
	}

	public void setTacheSettingDao(TacheSettingDao tacheSettingDao) {
		this.tacheSettingDao = tacheSettingDao;
	}
	
}
