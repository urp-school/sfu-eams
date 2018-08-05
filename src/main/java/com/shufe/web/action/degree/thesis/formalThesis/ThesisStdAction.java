//$Id: ThesisStdAction.java,v 1.1 2007-5-23 15:27:57 Administrator Exp $
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
 * chenweixiong              2007-5-23         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.thesis.formalThesis;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.ThesisTopicSource;
import com.ekingstar.eams.system.basecode.industry.ThesisType;
import com.shufe.model.degree.thesis.Thesis;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;
import com.shufe.model.system.file.FilePath;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.web.action.degree.thesis.ThesisDownloadAction;

public class ThesisStdAction extends ThesisDownloadAction {
    
    private ThesisManageService thesisManageService;
    
    private DepartmentService departmentService;
    
    /**
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student student = getStudentFromSession(request.getSession());
        ThesisManage thesisManage = thesisManageService.getThesisManage(student.getId());
        request.setAttribute("student", student);
        if (null == thesisManage || null == thesisManage.getTopicOpen()) {
            return forward(request, "noscheduleErrors");
        }
        Thesis myThesis = thesisManage.getThesis();
        if (null == myThesis) {
            myThesis = new Thesis();
        }
        request.setAttribute("teachDepartList", departmentService.getDepartments());
        request.setAttribute("thesisManage", thesisManage);
        request.setAttribute("myThesis", myThesis);
        request.setAttribute("editType", "home");
        return forward(request);
    }
    
    /**
     * 编辑
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
        ThesisManage thesisManage = thesisManageService.getThesisManage(student.getId());
        request.setAttribute("student", student);
        Thesis myThesis = thesisManage.getThesis();
        if (null == myThesis) {
            myThesis = new Thesis();
        }
        request.setAttribute("thesisSources", utilService.load(ThesisTopicSource.class, "state",
                Boolean.TRUE));
        request.setAttribute("thesisTypes", utilService.load(ThesisType.class, "state",
                Boolean.TRUE));
        request.setAttribute("teachDepartList", departmentService.getDepartments());
        request.setAttribute("thesisManage", thesisManage);
        request.setAttribute("myThesis", myThesis);
        request.setAttribute("editType", "edit");
        return forward(request, "index");
    }
    
    /**
     * 保存信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveThesis(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long thesisId = getLong(request, "thesis.id");
        Thesis thesis = new Thesis();
        if (null != thesisId) {
            thesis = (Thesis) utilService.load(Thesis.class, thesisId);
        }
        Map valueMpa = RequestUtils.getParams(request, "thesis");
        EntityUtils.populate(valueMpa, thesis);
        Student student = getStudentFromSession(request.getSession());
        ThesisManage thesisManage = thesisManageService.getThesisManage(student.getId());
        thesis.setFinishOn(new Date(System.currentTimeMillis()));
        if (null == thesisId) {
            thesis.setAffirm(Boolean.FALSE);
            thesis.setThesisManage(thesisManage);
            utilService.saveOrUpdate(thesis);
            thesisManage.setThesis(thesis);
            utilService.saveOrUpdate(thesisManage);
        } else {
            utilService.saveOrUpdate(thesis);
        }
        return redirect(request, "index", "info.action.success");
    }
    
    /**
     * @param thesisManageService
     *            The thesisManageService to set.
     */
    public void setThesisManageService(ThesisManageService thesisManageService) {
        this.thesisManageService = thesisManageService;
    }
    
    /**
     * @see com.shufe.web.action.degree.thesis.ThesisDownloadAction#doUpload(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doUpload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward f = upload(request, getFileRealPath(FilePath.THESIS, request));
        if (f != null)
            return f;
        return redirect(request, "index", "info.upload.success");
    }
    
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
}
