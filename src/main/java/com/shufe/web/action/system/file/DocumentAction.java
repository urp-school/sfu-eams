/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is
 * intended only for the use of KINGSTAR MEDIA application development.
 * Reengineering, reproduction arose from modification of the original source,
 * or other redistribution of this source is not permitted without written
 * permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
/*******************************************************************************
 * @author chaostone 
 * MODIFICATION DESCRIPTION Name Date Description
 *  ============ ============ ============ 
 *  chaostone 2006-9-29 Created
 ******************************************************************************/

package com.shufe.web.action.system.file;

import java.io.File;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.User;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.file.Document;
import com.shufe.model.system.file.ManagerDocument;
import com.shufe.model.system.file.StudentDocument;
import com.shufe.model.system.file.TeacherDocument;

/**
 * 系统文档管理，下载，删除
 * 
 * @author chaostone
 */
public class DocumentAction extends FileAction {

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return forward(request);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String kind = request.getParameter("kind");
		if (null == kind) {
			kind = "manager";
		}
		EntityQuery entityQuery = null;
		if (kind.equals("std")) {
			entityQuery = new EntityQuery(StudentDocument.class, "document");
		} else if (kind.equals("teacher")) {
			entityQuery = new EntityQuery(TeacherDocument.class, "document");
		} else if (kind.equals("manager")) {
			entityQuery = new EntityQuery(ManagerDocument.class, "document");
		} else {
			throw new RuntimeException("unspported Document kind");
		}
		entityQuery.addOrder(new Order("document.uploadOn DESC"));
		entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "documents", utilService.search(entityQuery));
		return forward(request);
	}

	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = getLong(request, "documentId");
		String kind = get(request, "kind");
		if (null == kind) {
			kind = "manager";
		}
		if (kind.equals("std")) {
			request.setAttribute("document", utilService.load(StudentDocument.class, id));
		} else if (kind.equals("teacher")) {
			request.setAttribute("document", utilService.load(TeacherDocument.class, id));
		} else if (kind.equals("manager")) {
			request.setAttribute("document", utilService.load(ManagerDocument.class, id));
		} else {
			throw new RuntimeException("unspported Document kind");
		}
		return forward(request);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String DocumentIds = request.getParameter("documentIds");
		List documents = utilService.load(Document.class, "id", SeqStringUtil
				.transformToLong(DocumentIds));
		String contextPath = getFileRealPath("doc", request);
		User user = getUser(request.getSession());
		for (Iterator iter = documents.iterator(); iter.hasNext();) {
			Document document = (Document) iter.next();
			utilService.remove(document);
			File a = new File(contextPath + "\\" + document.getPath());
			if (a.delete()) {
				log.info("user [" + user.getName() + "] delete file[" + a.getAbsolutePath()
						+ "]");
			}

		}
		return redirect(request, "list", "info.delete.success", "&kind="
				+ request.getParameter("kind"));
	}

	/**
	 * 上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward f = upload(request, getFileRealPath("doc", request));
		if (f != null)
			return f;
		return redirect(request, new Action(DocumentAction.class, "list"), "info.upload.success",
				new String[] { "kind" });
	}

	/**
	 * 将上传文档信息记录到数据库中.
	 */
	protected void afterUpload(HttpServletRequest request, File file, String updloadFilePath) {
		String kind = request.getParameter("kind");
		Document document = null;
		if (StringUtils.equals(kind, "std")) {
			Long[] stdTypeIds = SeqStringUtil.transformToLong(get(request, "stdTypeIds"));
			Long[] departmentIds = SeqStringUtil.transformToLong(get(request, "departmentIds"));
			StudentDocument stdDocument = new StudentDocument();
			if (null != stdTypeIds) {
				List list = utilService.load(StudentType.class, "id", stdTypeIds);
				for (Iterator it = list.iterator(); it.hasNext();) {
					StudentType stdType = (StudentType) it.next();
					stdDocument.getStudentTypes().add(stdType);
				}
			}
			if (null != departmentIds) {
				List list = utilService.load(Department.class, "id", departmentIds);
				for (Iterator it = list.iterator(); it.hasNext();) {
					Department department = (Department) it.next();
					stdDocument.getDeparts().add(department);
				}
			}
			document = stdDocument;
		} else if (StringUtils.equals(kind, "teacher")) {
			document = new TeacherDocument();
		} else if (StringUtils.equals(kind, "manager")) {
			document = new ManagerDocument();
		} else {
			throw new RuntimeException("unspported kind");
		}
		updloadFilePath = updloadFilePath.substring(updloadFilePath.lastIndexOf("\\") + 1);
		updloadFilePath = updloadFilePath.substring(updloadFilePath.lastIndexOf("/") + 1);
		document.setName(updloadFilePath);
		document.setPath(file.getName());
		document.setUploaded(getUser(request.getSession()));
		document.setUploadOn(new Date(System.currentTimeMillis()));
        document.setIsUp(Boolean.valueOf((String)request.getParameter("isUp")));
		utilService.saveOrUpdate(document);
		log.info("user [" + document.getUploaded().getName() + "] upload file["
				+ file.getAbsolutePath() + "]");
	}

	/**
	 * 上传设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		initBaseCodes(request, "stdTypes", StudentType.class);
		initBaseCodes(request, "departments", Department.class);
		return forward(request);
	}
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	    Document document = new Document();
        Long paramsId = getLong(request, "document.id");
        if (null != paramsId) {
            document = (Document) utilService.load(Document.class, paramsId);
        }
        request.setAttribute("document", document);
        return forward(request);
    }
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	    Document params = (Document) populateEntity(request, Document.class,
                "document");
	    String kind = request.getParameter("kind");
        try {
            utilService.saveOrUpdate(params);;
        } catch (PojoExistException e) {
            logHelper.info(request, "save params", e);
            return forwardError(mapping, request, new String[] { "entity.electParams",
                    "error.model.existed" });
        }
        return redirect(request, "list", "info.save.success","&kind="
                + kind);
    }
}
