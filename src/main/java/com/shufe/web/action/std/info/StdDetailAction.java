//$Id: StdDetailAction.java,v 1.1 2007-5-22 下午02:29:53 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-22         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.info;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.eams.system.basecode.industry.CorporationKind;
import com.ekingstar.eams.system.basecode.industry.EducationMode;
import com.ekingstar.eams.system.basecode.industry.EnrollMode;
import com.ekingstar.eams.system.basecode.industry.MaritalStatus;
import com.ekingstar.eams.system.basecode.industry.School;
import com.ekingstar.eams.system.basecode.industry.StudyType;
import com.ekingstar.eams.system.basecode.state.Country;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.Nation;
import com.ekingstar.eams.system.basecode.state.PoliticVisage;
import com.ekingstar.eams.system.basecode.state.SubjectCategory;
import com.shufe.model.std.BasicInfo;
import com.shufe.model.std.Student;
import com.shufe.model.std.StudentStatusInfo;
import com.shufe.model.std.degree.DegreeInfo;
import com.shufe.model.std.degree.EquivalentDegreeInfo;
import com.shufe.model.std.degree.MasterDegreeInfo;
import com.shufe.model.std.degree.UndergraduateDegreeInfo;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 学生维护和查询个人信息
 * 
 * @author chaostone
 * 
 */
public class StdDetailAction extends DispatchBasicAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setAttribute("std", this.getStudentFromSession(request.getSession()));
        return forward(request);
    }
    
    /**
     * 查看信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Student std = getStudentFromSession(request.getSession());
        request.setAttribute("std", std);
        String kind = request.getParameter("kind");
        if (StringUtils.isEmpty(kind)) {
            kind = "stdInfo";
        }
        /*if (Degree.EQUIVALENTMASTER.equals(std.getType().getDegree().getId())) {
            request.setAttribute("couldEdit", Boolean.TRUE);
        }
        if (Degree.EQUIVALENTDOCTOR.equals(std.getType().getDegree().getId())) {
            request.setAttribute("couldEdit", Boolean.TRUE);
        }*/
        return forward(request, kind);
    }
    
    /**
     * 修改导师信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editStdInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("std", getStudentFromSession(request.getSession()));
        request.setAttribute("tutorList", utilService.loadAll(Tutor.class));
        return forward(request);
    }
    
    /**
     * 修改基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editBasicInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("std", getStudentFromSession(request.getSession()));
        addCollection(request, "genders", baseCodeService.getCodes(Gender.class));
        addCollection(request, "politicVisages", baseCodeService.getCodes(PoliticVisage.class));
        addCollection(request, "nations", baseCodeService.getCodes(Nation.class));
        addCollection(request, "countries", baseCodeService.getCodes(Country.class));
        addCollection(request, "maritalStatuses", baseCodeService.getCodes(MaritalStatus.class));
        return forward(request);
    }
    
    /**
     * 修改基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editStatusInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("std", getStudentFromSession(request.getSession()));
        addCollection(request, "eduDegrees", baseCodeService.getCodes(EduDegree.class));
        addCollection(request, "enrollModes", baseCodeService.getCodes(EnrollMode.class));
        addCollection(request, "educationModes", baseCodeService.getCodes(EducationMode.class));
        return forward(request);
    }
    
    /**
     * 修改学位信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editDegreeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student std = getStudentFromSession(request.getSession());
        request.setAttribute("std", std);
        if (null == std.getDegreeInfo()) {
            std.setDegreeInfo(new DegreeInfo());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            std.getDegreeInfo().setSchool(getSystemConfig().getSchool());
            std.getDegreeInfo().setGraduateOn(
                    (null == std.getGraduateOn()) ? "" : format.format(std.getGraduateOn()));
        }
        Long degreeId = std.getType().getDegree().getId();
        // 如果不是本科生，初始化学士学位和硕士学位信息
        if (!degreeId.equals(Degree.BACHELOR)) {
            if (null == std.getDegreeInfo().getUndergraduate()) {
                std.getDegreeInfo().setUndergraduate(new UndergraduateDegreeInfo());
            }
            // 博士生有硕士部分信息
            if (degreeId.equals(Degree.DOCTOR)) {
                if (null == std.getDegreeInfo().getMaster()) {
                    std.getDegreeInfo().setMaster(new MasterDegreeInfo());
                }
            }
            // 如果是同等学力
            if (degreeId.equals(Degree.EQUIVALENTMASTER)) {
                if (null == std.getDegreeInfo().getEquivalent()) {
                    std.getDegreeInfo().setEquivalent(new EquivalentDegreeInfo());
                    std.getDegreeInfo().getEquivalent().setApplyNo(std.getCode());
                }
            }
        }
        
        utilService.saveOrUpdate(std);
        addCollection(request, "schools", baseCodeService.getCodes(School.class));
        addCollection(request, "categories", baseCodeService.getCodes(SubjectCategory.class));
        addCollection(request, "studyTypes", baseCodeService.getCodes(StudyType.class));
        addCollection(request, "corporationKinds", baseCodeService.getCodes(CorporationKind.class));
        return forward(request);
    }
    
    /**
     * 修改基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveDegreeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DegreeInfo info = (DegreeInfo) populateEntity(request, DegreeInfo.class, "degreeInfo");
        EntityUtils.evictEmptyProperty(info);
        EntityUtils.evictEmptyProperty(info.getUndergraduate());
        EntityUtils.evictEmptyProperty(info.getMaster());
        utilService.saveOrUpdate(info);
        return redirect(request, "info", "info.save.success", "&kind=degreeInfo");
    }
    
    /**
     * 修改基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveBasicInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        BasicInfo info = (BasicInfo) populateEntity(request, BasicInfo.class, "basicInfo");
        utilService.saveOrUpdate(info);
        return redirect(request, "info", "info.save.success", "&kind=basicInfo");
    }
    
    /**
     * 修改基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveStatusInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        StudentStatusInfo info = (StudentStatusInfo) populateEntity(request,
                StudentStatusInfo.class, "statusInfo");
        utilService.saveOrUpdate(info);
        return redirect(request, "info", "info.save.success", "&kind=statusInfo");
    }
    
    /**
     * 修改导师信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward savaStdInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long tutorId = getLong(request, "std.teacher.id");
        Teacher tutor = (Teacher) utilService.load(Teacher.class, tutorId);
        Student student = (Student) getStudentFromSession(request.getSession());
        student.setTeacher(tutor);
        utilService.saveOrUpdate(student);
        return redirect(request, "info", "info.save.success");
    }
    
}
