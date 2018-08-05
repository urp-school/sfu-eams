//$Id: StudentClassOperation.java,v 1.15 2006/12/19 13:08:42 duanth Exp $
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
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-24         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.util.RequestUtil;

/**
 * @author dell
 */
public class StudentClassOperation extends StudentSearchSupportAction {
    
    private AdminClassService adminClassService;
    
    /**
     * 自动分班
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward autoSplitClassForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        searchStudent(form, request, "AdminClassManager");
        Results.addObject("adminClassList", utilService.load(AdminClass.class, "id",
                new Object[] { SeqStringUtil.transformToLong(request.getParameter("classId")) }));
        return this.forward(mapping, request, "autoSplitForm");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward autoSplitClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdId"));
        Long[] adminClassIdArray = SeqStringUtil.transformToLong(request.getParameter("classId"));
        studentService.autoSplitClass(stdIdArray, adminClassIdArray);
        return mapping.findForward("adminSuccess");
    }
    
    /**
     * 手动分班
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward splitClassForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        searchStudent(form, request, "AdminClassManager");
        Results.addObject("adminClassList", utilService.load(AdminClass.class, "id",
                new Object[] { SeqStringUtil.transformToLong(StringUtils.split(request
                        .getParameter("classId"), ",")) }));
        return this.forward(mapping, request, "splitForm");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward splitClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdId"));
        Long[] adminClassIdArray = SeqStringUtil.transformToLong(request.getParameter("classId"));
        studentService.batchUpdateStudentClass(stdIdArray, adminClassIdArray);
        return mapping.findForward("adminSuccess");
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Set adminClasses = new HashSet();
        adminClasses.add(studentService.loadAdminClassById(getLong(request, "classId")));
        Results.addObject("adminClasses", adminClasses);
        request.setAttribute("user", getUser(request.getSession()));
        return this.forward(mapping, request, "detail");
    }
    
    /**
     * 学生班级信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward detailByStudent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String code =getLoginName(request);
        Student student = studentService.getStudent(code);
        Set adminClasses = student.getAdminClasses();
        Results.addObject("adminClasses", adminClasses);
        Results.addObject("student", student);
        return this.forward(mapping, request, "detailByStudent");
    }
    
    /**
     * 设置学生班级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward assignStudentClassForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        DynaActionForm dynaForm = (DynaActionForm) form;
        AdminClass adminClass = (AdminClass) RequestUtil.populate(request, AdminClass.class,
                "adminClass");
        int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
        int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();
        Pagination adminClassList = studentService.searchStudentClass(adminClass, pageNo, pageSize,
                studentTypeIds, departmentIds);
        if ("search".equals(request.getParameter("flag"))) {
           addOldPage(request, "adminClassList", adminClassList);
        } else {
        	addOldPage(request, "adminClassList", adminClassList);
        }
        EntityQuery query = new EntityQuery(Student.class, "std");
        query.setSelect("new map(std.id as id,std.code as code,std.name as name)");
        query.add(new Condition("std.id in(:ids)", SeqStringUtil.transformToLong(request
                .getParameter("stdId"))));
        List nameList = (List) utilService.search(query);
        
        Results.addObject("nameList", nameList);
        return this.forward(mapping, request, "assingClassForm");
    }
    
    /**
     * 设置学生班级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward assignStudentClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdId"));
        Long[] adminClassIdArray = SeqStringUtil.transformToLong(request.getParameter("classId"));
        studentService.batchUpdateStudentClass(stdIdArray, adminClassIdArray);
        return mapping.findForward("assingSuccess");
    }
    
    /**
     * 维护学生班级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward maintainStudentClassForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List studentList = utilService.load(Student.class, "id", SeqStringUtil
                .transformToLong(request.getParameter("stdIds")));
        Results.addObject("studentList", studentList);
        return this.forward(request, "../../studentClass/maintainStudentClassForm");
    }
    
    public ActionForward maintainStudentClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        studentService.updateStudentAdminClass(getLong(request, "stdId"), SeqStringUtil
                .transformToLong(request.getParameter("adminClassIds")), null);
        Action action = new Action("studentClassManager", "studentClassManager");
        return this.redirect(request, action, null, "");
    }
    
    /**
     * 设置学生班级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelStudentClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdId"));
        Long adminClassId = getLong(request, "adminClass.id");
        studentService.batchCancelStudentClass(stdIdArray, new Long[] { adminClassId });
        return redirect(request, new Action("adminClassManager", "classManager"),
                "info.delete.success", (String[]) null);
    }
    
    /**
     * 设置学生班级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateStudentClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("studentIds"));
        Long adminClassId = getLong(request, "adminClass.id");
        AdminClass adminClass = (AdminClass) utilService.get(AdminClass.class, adminClassId);
        List studentList = utilService.load(Student.class, "id", stdIdArray);
        Set studentSet = new HashSet(studentList);
        adminClass.setStudents(studentSet);
        int stateValidCount = 0;
        int onCampusStatusCount = 0;
        for (Iterator iter = studentSet.iterator(); iter.hasNext();) {
            Student element = (Student) iter.next();
            if (element.isActive()) {
                stateValidCount++;
            }
            if (element.isInSchool()) {
                onCampusStatusCount++;
            }
        }
        adminClass.setStdCount(new Integer(stateValidCount));
        adminClass.setActualStdCount(new Integer(onCampusStatusCount));
        utilService.saveOrUpdate(adminClass);
        return redirect(request, new Action("adminClassManager", "classManager"),
                "info.save.success", (String[]) null);
    }
    
    public ActionForward resetStudentClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] stdIdArray = SeqStringUtil.transformToLong(request.getParameter("stdId"));
        studentService.batchResetStudentClass(stdIdArray);
        return redirect(request, new Action("studentClassManager", "studentClassManager"),
                "info.delete.success", (String[]) null);
    }
    
    /**
     * 设置班级学生页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward setClassStudentForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        adminClassService.batchUpdateStdCountOfClass(request.getParameter("classId"));
        Results.addObject("adminClass", utilService.load(AdminClass.class, getLong(request,
                "classId")));
        return forward(mapping, request, "setClassStudentForm");
    }
    
    /**
     * 增加班级学生页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward addClassStudentForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        AdminClass adminClass = (AdminClass) RequestUtil.populate(request, AdminClass.class,
                "adminClass");
        request.setAttribute("classId", adminClass.getId());
        Results.addObject("adminClass", utilService.load(AdminClass.class, adminClass.getId()));
        return this.forward(mapping, request, "splitForm");
    }
    
    /**
     * @return Returns the studentService.
     */
    public StudentService getStudentService() {
        return studentService;
    }
    
    /**
     * @param adminClassService
     *            要设置的 adminClassService.
     */
    public void setAdminClassService(AdminClassService adminClassService) {
        this.adminClassService = adminClassService;
    }
    
}
