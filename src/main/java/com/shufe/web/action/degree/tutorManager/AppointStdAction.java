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
 * 塞外狂人             2006-8-16            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.tutorManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.TutorType;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Tutor;
import com.shufe.service.std.StudentService;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 指定学生
 * 
 * @author 塞外狂人
 */
public class AppointStdAction extends RestrictionSupportAction {
    
    private StudentService studentService;
    
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
        EntityQuery entityQuery = new EntityQuery(Tutor.class, "tutor");
        populateConditions(request, entityQuery);
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        entityQuery.setLimit(getPageLimit(request));
        addCollection(request, "tutors", utilService.search(entityQuery));
        initBaseCodes(request, "tutorTypeList", TutorType.class);
        return forward(request);
    }
    
    /**
     * 指定学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward appointStd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long tutorId = getLong(request, "tutorId");
        Tutor tutor = (Tutor) utilService.load(Tutor.class, tutorId);
        setDataRealm(request, hasStdTypeCollege);
        Results.addObject("tutor", tutor);
        return forward(request);
    }
    
    /**
     * 查询学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        populateConditions(request, entityQuery);
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        entityQuery.setLimit(getPageLimit(request));
        request.setAttribute("tutorId", request.getParameter("tutorId"));
        addCollection(request, "stdPage", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 设置学生和导师的关联
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward setStdtoTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdIds = request.getParameter("stdIds");
        Long tutorId = getLong(request, "tutorId");
        studentService.batchSetStdToTutor(stdIds, tutorId);
        return this.redirect(request, "list", "info.set.success");
    }
    
    /**
     * 查询所带学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchStd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long tutorId = getLong(request, "tutorId");
        Tutor tutor = (Tutor) utilService.load(Tutor.class, tutorId);
        setDataRealm(request, hasStdTypeCollege);
        Results.addObject("tutor", tutor);
        return forward(request);
    }
    
    /**
     * 查询导师所带学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward tutorStdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        populateConditions(request, entityQuery);
        entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        entityQuery.setLimit(getPageLimit(request));
        addCollection(request, "stdPage", utilService.search(entityQuery));
        return forward(request);
    }
    
    /**
     * 取消学生和导师的关联
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward unSetStdtoTutor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdIds = request.getParameter("stdIds");
        studentService.batchUnSetStdToTutor(stdIds);
        return this.redirect(request, "list", "info.set.success");
    }
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
