//$Id: ThesisDownLoadAction.java,v 1.1 2007-4-23 10:50:46 Administrator Exp $
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
 * chenweixiong              2007-4-23         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.ThesisStore;
import com.shufe.model.std.Student;
import com.shufe.model.system.file.DegreeDocument;
import com.shufe.model.system.file.FilePath;
import com.shufe.web.action.system.file.FileAction;

/**
 * 这个类 主要用来完成 学生的论文各个环节的文档的上传 下载 同时 老师管理员可以通过这个模块来下载学生的文件
 * 
 * @author Administrator
 * 
 */
public class ThesisDownloadAction extends FileAction {
    /**
     * 下载和学生具体的论文有关的文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public ActionForward doDownLoad(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long thesisManageId = getLong(request, "thesisManageId");
        ThesisManage thesisManage = (ThesisManage) utilService.load(
                ThesisManage.class, thesisManageId);
        String storeId = request.getParameter("storeId");
        ThesisStore thesisStore = thesisManage.getProcessById(storeId);
        String filePath = getFileRealPath(FilePath.THESIS, request);
        if (StringUtils.isEmpty(thesisStore.getDownloadName())) {
            return forward(request, "nofile");
        } else {
            download(request, response, filePath
                    + thesisStore.getDownloadName(), thesisStore
                    .getDisplayName());
            return null;
        }

    }

    /**
     * 下载文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void doDownLoadSystemFile(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long documentId = getLong(request, "document.id");
        if (!ValidEntityKeyPredicate.getInstance().evaluate(documentId)) {
            response.getWriter()
                    .write("without docuemnt with id:" + documentId);
            return;
        }
        DegreeDocument degreeDocument = (DegreeDocument) utilService.load(
                DegreeDocument.class, documentId);
        if (null == degreeDocument) {
            response.getWriter()
                    .write("without docuemnt with id:" + documentId);
            return;
        }
        String filePath = getFileRealPath(FilePath.DEGREE, request);
        download(request, response, filePath + degreeDocument.getPath(),
                degreeDocument.getName());
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
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.setAttribute("thesisId", request.getParameter("thesisId"));
        request.setAttribute("thesisManageId", request
                .getParameter("thesisManageId"));
        return forward(request, "loadUploadPage");
    }

    /**
     * 上传 文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doUpload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward f=  upload(request, getFileRealPath(FilePath.THESIS, request));
        if (f != null)
            return f;
        return redirect(request, new Action(ThesisManageStdAction.class,
                "doLoadProcessPage"), "info.upload.success");
    }

    /**
     * 上传文件后做的操作
     * 
     * @see com.shufe.web.action.system.file.FileAction
     */
    protected void afterUpload(HttpServletRequest request, File file,
            String updloadFilePath) {
        super.afterUpload(request, file, updloadFilePath);
        Student student = getStudentFromSession(request.getSession());
        updloadFilePath = updloadFilePath.substring(updloadFilePath
                .lastIndexOf("\\") + 1);
        updloadFilePath = updloadFilePath.substring(updloadFilePath
                .lastIndexOf("/") + 1);
        updloadFilePath = student.getName() + updloadFilePath;
        Long thesisManageId = getLong(request, "thesisManageId");
        ThesisManage thesisManage = (ThesisManage) utilService.get(
                ThesisManage.class, thesisManageId);
        String storeId = request.getParameter("storeId");
        ThesisStore thesisStore = thesisManage.getProcessById(storeId);
        if (!(file.getName().equals(thesisStore.getDownloadName()))) {
            String newPath = file.getPath();
            String oldFilePath = "";
            // 为了符合windows和linux;
            oldFilePath = newPath.substring(0, newPath.lastIndexOf("\\") + 1);
            oldFilePath = newPath.substring(0, newPath.lastIndexOf("/") + 1);
            File oldFile = new File(oldFilePath);
            oldFile.delete();
            thesisStore.setDownloadName(file.getName());
        }
        thesisStore.setDisplayName(updloadFilePath);
        utilService.saveOrUpdate(thesisStore);
    }

    /**
     * 重新上传的文件名称 以学生的学号作为文件名 同时添加一个文件的存储类别
     * 
     * @see com.shufe.web.action.system.file.FileAction
     */
    protected String getFileName(HttpServletRequest request,
            String uploadAbsolutePath) {
        uploadAbsolutePath = uploadAbsolutePath.substring(uploadAbsolutePath
                .lastIndexOf("."));
        String storeId = request.getParameter("storeId");
        Student student = getStudentFromSession(request.getSession());
        String fileName = storeId + "_" + student.getCode()
                + uploadAbsolutePath;
        return fileName;
    }
}
