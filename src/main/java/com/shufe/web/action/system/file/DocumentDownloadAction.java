//$Id: FileDownloadAction.java,v 1.8 2007/01/19 06:29:13 duanth Exp $
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
 * chenweixiong              2006-3-24         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.file;

import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.User;
import com.shufe.model.std.Student;
import com.shufe.model.system.file.Document;
import com.shufe.model.system.file.FilePath;
import com.shufe.model.system.file.StudentDocument;

/**
 * 系统文档下载类
 * 
 * @author chaostone
 */
public class DocumentDownloadAction extends FileAction {
    
    /**
     * 下载文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    public ActionForward download(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long documentId = getLong(request, "document.id");
        Document document = null;
        if (null != documentId)
            document = (Document) utilService.get(Document.class, documentId);
        if (null == document) {
            request.setAttribute("documentId", documentId);
            return forward(request, "noFile");
        }
        String fileName = document.getName();
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && -1 != agent.indexOf("MSIE")) {
            fileName = URLEncoder.encode(fileName, "UTF8");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
            fileName = MimeUtility.encodeText(fileName, "UTF8", "B");
        }
        download(request, response, getFileRealPath(FilePath.DOC, request) + document.getPath(),
                fileName);
        return null;
    }
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        User user = getUser(request.getSession());
        StringBuffer classCondition = new StringBuffer("");
        EntityQuery query = null;
        Long curCategory = getUserCategoryId(request);
        if (curCategory.equals(EamsRole.STD_USER)) {
            classCondition.append("or document.class = StudentDocument ");
            query = new EntityQuery(StudentDocument.class, "document");
            Student std = (Student) utilService.load(Student.class, "code", user.getName()).get(0);
            query.join("document.studentTypes", "stdType");
            query
                    .add(new Condition(
                            "current_date()>document.startDate and current_date()<document.finishDate or document.startDate is null or document.finishDate is null"));
            query.add(new Condition("stdType.id = (:stdTypeId)", std.getType().getId()));
            query.addOrder(new Order("document.uploadOn DESC"));
        } else if (curCategory.equals(EamsRole.TEACHER_USER)) {
            classCondition.append("or document.class = TeacherDocument ");
            query = new EntityQuery(Document.class, "document");
            query
                    .add(new Condition(
                            "current_date()>document.startDate and current_date()<document.finishDate or document.startDate is null or document.finishDate is null"));
            query.addOrder(new Order("document.uploadOn DESC"));
        } else {
            classCondition.append("or document.class = ManagerDocument ");
            query = new EntityQuery(Document.class, "document");
            query
                    .add(new Condition(
                            "current_date()>document.startDate and current_date()<document.finishDate or document.startDate is null or document.finishDate is null"));
            query.addOrder(new Order("document.uploadOn DESC"));
        }
        classCondition.delete(0, 2);
        query.setLimit(getPageLimit(request));
        query.add(new Condition(classCondition.toString()));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        addCollection(request, "documents", utilService.search(query));
        return forward(request, "list");
    }
}
