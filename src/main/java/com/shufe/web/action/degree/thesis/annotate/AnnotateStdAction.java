//$Id: AnnotateStdAction.java,v 1.14 2006/12/19 13:08:40 duanth Exp $
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
 * chenweixiong              2006-11-7         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.annotate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.Tache;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.study.StudyThesis;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.degree.thesis.annotate.Annotate;
import com.shufe.model.std.Student;
import com.shufe.model.system.file.FilePath;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.web.action.system.file.FileAction;

public class AnnotateStdAction extends FileAction {
    
    public ThesisManageService thesisManageService;
    
    /**
     * 得到一个学生的论文评阅信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student student = getStudentFromSession(request.getSession());
        StudentType stdType = student.getType();
        ThesisManage thesisManage = thesisManageService.getThesisManage(student.getId());
        if (null == thesisManage) {
            request.setAttribute("reason", "error.annotate.noTopicOpen");
            request.setAttribute("flag", "topicOpen");
            return forward(request, "error");
        }
        if (!thesisManage.containTache(Tache.ANNOTATE)) {
            request.setAttribute("reason", "error.annotate.notInSchedule");
            return forward(request, "error");
        }
        request.setAttribute("student", student);
        request.setAttribute("annotate", thesisManage.getAnnotate());
        request.setAttribute("thesisManage", thesisManage);
        if (Degree.MASTER.equals(stdType.getDegree().getId())) {
            request.setAttribute("stdTypeFlag", "master");
        } else if (Degree.EQUIVALENTMASTER.equals(stdType.getDegree().getId())) {
            request.setAttribute("stdTypeFlag", "master");
        } else if (Degree.DOCTOR.equals(stdType.getDegree().getId())) {
            request.setAttribute("stdTypeFlag", "doctor");
        } else if (Degree.EQUIVALENTDOCTOR.equals(stdType.getDegree().getId())) {
            request.setAttribute("stdTypeFlag", "doctor");
        } else {
            request.setAttribute("reason", "error.annotate.notneed");
            return forward(request, "error");
        }
        return forward(request, "loadStdThesisAnnotate");
    }
    
    /**
     * 得到论文评阅的修改页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student student = getStudentFromSession(request.getSession());
        Long annotateId = getLong(request, "annotate.id");
        ThesisManage thesisManage = thesisManageService.getThesisManage(student.getId());
        Annotate annotate = new Annotate();
        if (null != annotateId) {
            annotate = (Annotate) utilService.load(Annotate.class, annotateId);
        }
        List thesiss = utilService.load(StudyThesis.class, new String[] { "student.id",
                "isPassCheck" }, new Object[] { student.getId(), Boolean.TRUE });
        request.setAttribute("stdyThesiss", thesiss);
        request.setAttribute("annotate", annotate);
        request.setAttribute("student", student);
        request.setAttribute("thesisManage", thesisManage);
        return forward(request);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student student = getStudentFromSession(request.getSession());
        Long annotateId = getLong(request, "annotate.id");
        Annotate annotate = new Annotate();
        if (null != annotateId) {
            annotate = (Annotate) utilService.load(Annotate.class, annotateId);
        }
        String publicThesisSeq = request.getParameter("publicThesisSeq");
        List publicThesiss = new ArrayList();
        if (StringUtils.isNotEmpty(publicThesisSeq)) {
            publicThesiss = utilService.load(StudyThesis.class, "id", SeqStringUtil
                    .transformToLong(publicThesisSeq));
        }
        Map valueMap = RequestUtils.getParams(request, "annotate");
        Set publishThesisSet = new HashSet();
        publishThesisSet.addAll(publicThesiss);
        EntityUtils.populate(valueMap, annotate);
        annotate.setFinishOn(new Date(System.currentTimeMillis()));
        annotate.setPublishThesisSet(publishThesisSet);
        if (Degree.DOCTOR.equals(student.getType().getDegree().getId())) {
            annotate.setIsDoubleBlind(Boolean.TRUE);
        }
        utilService.saveOrUpdate(annotate);
        if (null == annotateId) {
            ThesisManage thesisManage = thesisManageService.getThesisManage(student.getId());
            thesisManage.setAnnotate(annotate);
            utilService.saveOrUpdate(thesisManage);
        }
        return redirect(request, "doLoad", "info.action.success");
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void doDownloadAnnotateDoc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = request.getParameter("fileName");
        String fileAbsolutePath = getFileRealPath(FilePath.THESIS, request) + fileName;
        String displayName = request.getParameter("displayName");
        download(request, response, fileAbsolutePath, displayName);
    }
    
    /**
     * @param thesisManageService
     *            The thesisManageService to set.
     */
    public void setThesisManageService(ThesisManageService thesisManageService) {
        this.thesisManageService = thesisManageService;
    }
}
