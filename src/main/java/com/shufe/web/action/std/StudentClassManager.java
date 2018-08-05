//$Id: StudentClassManager.java,v 1.20 2006/12/30 03:30:38 duanth Exp $
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.service.system.baseinfo.SpecialityService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.std.adminClass.SubClass;

/**
 * @author dell
 */
public class StudentClassManager extends StudentSearchSupportAction {
    
    AdminClassService adminClassService;
    
    /**
     * @param specialityService
     *            要设置的 specialityService.
     */
    public void setSpecialityService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
    
    /**
     * 班级管理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward classManager(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // String moduleName = "AdminClassManager";
        searchAdminClass(form, request);
        return forward(mapping, request, SUCCESS);
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
    public ActionForward studentClassManager(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        searchStudentWith2ndSpeciality(form, request, new Boolean(false), null);
        return forward(mapping, request, SUCCESS);
    }
    
    public ActionForward batchPrintClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        AdminClass adminClass = (AdminClass) RequestUtil.populate(request, AdminClass.class,
                "adminClass");
        List adminClassList = studentService.findStudentClass(adminClass, studentTypeIds,
                departmentIds);
        request.setAttribute("adminClassList", adminClassList);
        Map stdMap = new HashMap();
        for (Iterator iter = adminClassList.iterator(); iter.hasNext();) {
            AdminClass admin = (AdminClass) iter.next();
            Student[] stds = new Student[admin.getStudents().size()];
            int i = 0;
            for (Iterator iter1 = admin.getStudents().iterator(); iter1.hasNext();) {
                Student std = (Student) iter1.next();
                if (std.isActive() && std.isInSchool()) {
                    stds[i++] = std;
                }
            }
            Arrays.sort(stds, new BeanComparator("code"));
            stdMap.put(admin.getId().toString(), stds);
        }
        request.setAttribute("stdMap", stdMap);
        return this.forward(request, "../studentClassManager/batchPrintClass");
    }
    
    /**
     * 打印班级学生名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward printClass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        adminClassService.batchUpdateStdCountOfClass(request.getParameter("ids"));
        Long[] ids = SeqStringUtil.transformToLong(request.getParameter("ids"));
        List adminClassList = utilService.load(AdminClass.class, "id", ids);
        request.setAttribute("adminClassList", adminClassList);
        Boolean isAll = getBoolean(request, "isAll");
        Map stdMap = new HashMap();
        for (Iterator iter = adminClassList.iterator(); iter.hasNext();) {
            AdminClass admin = (AdminClass) iter.next();
            Collection selectStds = null;
            if (!Boolean.TRUE.equals(isAll)) {
                selectStds = CollectionUtils.select(admin.getStudents(), new Predicate() {
                    
                    public boolean evaluate(Object arg0) {
                        Student std = (Student) arg0;
                        return (std.isActive() && std.isInSchool());
                    }
                });
            } else {
                selectStds = new ArrayList(admin.getStudents());
            }
            Student[] stds = new Student[selectStds.size()];
            selectStds.toArray(stds);
            Arrays.sort(stds, new BeanComparator("code"));
            stdMap.put(admin.getId().toString(), stds);
        }
        request.setAttribute("stdMap", stdMap);
        return this.forward(request, "../studentClassManager/batchPrintClass");
    }
    
    /**
     * 批量更新班级的学生数量
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateStdCount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String adminClassIdSeq = request.getParameter("adminClassIds");
        adminClassService.batchUpdateStdCountOfClass(adminClassIdSeq);
        // return this.forward(request,
        // "../../duty/maintainRecordByTeachTaskSuccess");
        return redirect(request, new Action("adminClassManager", "classManager"),
                "info.update.success", new String[] { "pageNo" });
    }
    
    public ActionForward addClassStudentList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Student student = (Student) RequestUtil.populate(request, Student.class, "student");
        if (StringUtils.isNotBlank(request.getParameter("adminClass.name"))) {
            AdminClass adminClass = new AdminClass();
            adminClass.setName(request.getParameter("adminClass.name"));
            student.getAdminClasses().add(adminClass);
        }
        Pagination page = studentService.searchStudent(student, getPageNo(request),
                getPageSize(request), getStdTypeIdSeq(request), getDepartmentIdSeq(request));
        addOldPage(request, "studentList", page);
        return this.forward(request);
    }
    
    /**
     * 将一个或多个班级学生名单导入到xls中
     * 
     * @see com.shufe.web.action.common.DispatchBasicAction#configExportContext(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.transfer.exporter.Context)
     */
    protected void configExportContext(HttpServletRequest request, Context context) {
        Integer stdPerClass = getInteger(request, "stdPerClass");
        Boolean isOnCompus = getBoolean(request, "isOnCompus");
        if (null == stdPerClass)
            stdPerClass = new Integer(60);
        List adminClassList = (List) utilService.load(AdminClass.class, "id", SeqStringUtil
                .transformToLong(get(request, "ids")));
        List subClasses = new ArrayList();
        for (Iterator iter = adminClassList.iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            subClasses
                    .addAll(SubClass.buildClasses(adminClass, stdPerClass.intValue(), isOnCompus));
        }
        // indexes perClasss
        List indexes = new ArrayList();
        for (int i = 0; i < stdPerClass.intValue() / 2; i++) {
            indexes.add(new Integer(i));
        }
        context.put("indexes", indexes);
        context.put("classes", subClasses);
        context.put("semiStdCount", new Integer(stdPerClass.intValue() / 2));
        context.put("systemConfig", getSystemConfig());
    }
    
    /**
     * @return Returns the studentService.
     */
    public StudentService getStudentService() {
        return studentService;
    }
    
    /**
     * @param studentService
     *            The studentService to set.
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * @param adminClassService
     *            要设置的 adminClassService.
     */
    public void setAdminClassService(AdminClassService adminClassService) {
        this.adminClassService = adminClassService;
    }
    
}
