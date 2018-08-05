//$Id: TutorManagerAction.java,v 1.6 2007/01/19 09:34:31 cwx Exp $
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
 * hc             2005-11-26         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.tutorManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.EngagementType;
import com.ekingstar.eams.system.basecode.industry.School;
import com.ekingstar.eams.system.basecode.industry.TeacherType;
import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.ekingstar.eams.system.basecode.state.Country;
import com.ekingstar.eams.system.basecode.state.Degree;
import com.ekingstar.eams.system.basecode.state.EduDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.basecode.state.Nation;
import com.ekingstar.eams.system.basecode.state.TeacherTitle;
import com.ekingstar.eams.system.basecode.state.TeacherTitleLevel;
import com.shufe.dao.OldPagination;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.baseinfo.TeacherDegreeInfo;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.service.degree.tutorManager.TutorService;
import com.shufe.service.system.baseinfo.TeacherService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

public class TutorManagerAction extends RestrictionSupportAction {
    
    private TutorService tutorService;
    
    private TeacherService teacherService;
    
    /**
     * 导师查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "tutors", utilService.search(buildQuery(request)));
        initBaseCodes(request, "tutorTypeList", TutorType.class);
        return forward(request);
    }
    
    /**
     * 组建导师查询条件
     * 
     * @param request
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Tutor.class, "tutor");
        populateConditions(request, entityQuery);
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        entityQuery.setLimit(getPageLimit(request));
        return entityQuery;
    }
    
    /**
     * 查询导师个人信息
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
        Long tutorId = getLong(request, "tutorId");
        Boolean isTutor = getBoolean(request, "isTutor");
        if (tutorId != null) {
            Results.addObject("tutor", (Tutor) utilService.load(Tutor.class, tutorId));
        } else if (isTutor != null) {
            if (isTutor.booleanValue()) {
                Results.addObject("tutor", getTutorFromSession(request.getSession()));
            }
        }
        
        prepareEditData(request);
        return forward(request);
    }
    
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addSingleParameter(request, "teacher", getTeacherFromSession(request.getSession()));
        return forward(request);
    }
    
    public ActionForward saveTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = getTeacherFromSession(request.getSession());
        if (null == teacher.getDegreeInfo()) {
            teacher.setDegreeInfo(new TeacherDegreeInfo());
            teacher.getDegreeInfo().setEduDegreeInside(null);
            teacher.getDegreeInfo().setDegree(null);
            teacher.getDegreeInfo().setGraduateSchool(null);
        }
        teacher.getDegreeInfo().setExperience(get(request, "experience"));
        teacher.getDegreeInfo().setAchievements(get(request, "achievements"));
        teacher.getDegreeInfo().setPartTimeJob(get(request, "partTimeJob"));
        utilService.saveOrUpdate(teacher);
        return redirect(request, "info", "info.save.success");
    }
    
    /**
     * 编辑教师个人信息，当然只提供简介的修改。
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editDescription(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addSingleParameter(request, "teacher", getTeacherFromSession(request.getSession()));
        return forward(request, "descriptionForm");
    }
    
    private void prepareEditData(HttpServletRequest request) {
        initBaseCodes("departmentList", Department.class);
        initBaseCodes("genderList", Gender.class);
        initBaseCodes("countryList", Country.class);
        initBaseCodes("nationList", Nation.class);
        initBaseCodes("titleList", TeacherTitle.class);
        initBaseCodes("titleLevelList", TeacherTitleLevel.class);
        initBaseCodes("teacherTypeList", TeacherType.class);
        initBaseCodes("tutorTypeList", TutorType.class);
        initBaseCodes("eduDegreeList", EduDegree.class);
        initBaseCodes("degreeList", Degree.class);
        initBaseCodes("schoolList", School.class);
        initBaseCodes("engagementTypeList", EngagementType.class);
        Results.addObject("doctorType", TutorType.DOCTOR_TUTOR).addObject("masterType",
                TutorType.MASTER_TUTOR);
    }
    
    /**
     * 添加或修改导师信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveOrUpdateTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Tutor tutor = (Tutor) RequestUtil.populate(request, Tutor.class, "tutor");
        Map tutorMap = RequestUtils.getParams(request, "tutor");
        if ((tutor.getId() == null) || (tutor.getId().equals(new Long(0)))) {
            EntityUtils.evictEmptyProperty(tutor);
            utilService.saveOrUpdate(tutor);
            return redirect(request, "list", "info.save.success");
        } else {
            Tutor tutorPO = (Tutor) utilService.load(Tutor.class, tutor.getId());
            EntityUtils.populate(tutorMap, tutorPO);
            utilService.saveOrUpdate(tutorPO);
            return redirect(request, "list", "info.update.success");
        }
    }
    
    /**
     * 删除导师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tutorIds = request.getParameter("tutorId");
        utilService.remove(Tutor.class, "id", tutorIds.split(","));
        String parameters = request.getParameter("parameters");
        return redirect(request, "list", "tutor.operation.success", parameters);
    }
    
    public ActionForward doTeacherList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String doCovert = request.getParameter("doList");
        if (StringUtils.isBlank(doCovert)) {
            Results.addObject("departmentList", departmentService.getDepartments());
            List teacherTypes = baseCodeService.getCodes(TeacherType.class);
            addCollection(request, "teacherTypes", teacherTypes);
            return forward(request, "covert/teacherCondition");
        }
        Teacher teacher = (Teacher) populate(request, Teacher.class, "teacher");
        // 准备数据
        addOldPage(request, "teacherList", teacherService.getTeachers(teacher, getPageNo(request),
                getPageSize(request)));
        return forward(request, "covert/teacherList");
    }
    
    public ActionForward doCovertTeacherToTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teacherId = request.getParameter("teacherId");
        if (StringUtils.isNotBlank(teacherId)) {
            tutorService.changeTutor(Long.valueOf(teacherId), true);
        }
        String parameters = request.getParameter("parameters");
        return redirect(request, "doTeacherList", "tutor.operation.success", parameters);
    }
    
    public ActionForward doCovertTutorToTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String teacherId = request.getParameter("teacherId");
        if (StringUtils.isNotBlank(teacherId)) {
            tutorService.changeTutor(Long.valueOf(teacherId), false);
        }
        return redirect(request, "list", "tutor.operation.success");
    }
    
    /**
     * 加载查询导师带学生页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exceptions
     */
    public ActionForward doDefault(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List departmentList = departmentService.getAllColleges();
        Results.addObject("departmentList", departmentList).addObject("doctorType",
                TutorType.DOCTOR_TUTOR).addObject("masterType", TutorType.MASTER_TUTOR);
        return forward(request, "../tutorStd/loadDefault");
    }
    
    /**
     * 查询导师信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm) form;
        int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
        int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();
        
        Tutor tutor = (Tutor) dynaForm.get("tutor");
        Pagination tutorPage = tutorService.getAllTutor(tutor, null, pageNo, pageSize);
        Results.addObject("tutorPage", tutorPage);
        return forward(request, "../tutorStd/tutorStdList");
    }
    
    /**
     * 加载学生列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long tutorId = getLong(request, "tutorId");
        EntityQuery query = new EntityQuery(Student.class, "std");
        query.add(new Condition("std.teacher.id=:teacherId", tutorId));
        query.setLimit(getPageLimit(request));
        
        Pagination studentPage = OldPagination.buildOldPagination((SinglePage) utilService
                .search(query));
        addOldPage(request, "students", studentPage);
        Results.addObject("tutor", (Tutor) utilService.load(Tutor.class, tutorId));
        return forward(request, "../tutorStd/stdList.ftl");
    }
    
    /**
     * 添加学生到指定的导师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doAddStdToTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String studentIds = request.getParameter("studentIds");
        Long tutorId = getLong(request, "tutorId");
        
        tutorService.batchModifyTeacherOfStd(studentIds, tutorId);
        ActionMessages actionMessges = new ActionMessages();
        actionMessges.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "filed.gradeNod.addSuccess"));
        saveErrors(request, actionMessges);
        
        return doStdList(mapping, form, request, response);
    }
    
    /**
     * 删除学生和导师的关联
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doDelStdFromTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String studentIds = request.getParameter("studentId");
        
        tutorService.batchModifyTeacherOfStd(studentIds, null);
        return doStdList(mapping, form, request, response);
    }
    
    /**
     * 按导师类别导出导师记录
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#getExportDatas(javax.servlet.http.HttpServletRequest)
     */
    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }
    
    public void setTutorService(TutorService tutorService) {
        this.tutorService = tutorService;
    }
    
    /**
     * @param teacherService
     *            The teacherService to set.
     */
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    
}
