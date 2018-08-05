//$Id: SecondSpecialityStudentAuditManager.java,v 1.1 2008-3-21 下午02:27:57 zhouqi Exp $
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
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2008-3-21         	Created
 *  
 ********************************************************************************/

package com.shufe.web.action.graduate;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.web.helper.StdSearchHelper;

/**
 * @author zhouqi
 */
public class SecondSpecialityStudentAuditManagerAction extends StudentAuditManagerAction {
    
    private StdSearchHelper stdSearchHelper;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        initSearchStudentBar(request, departmentIds, studentTypeIds);
        Results.addObject("studentStateList", utilService.loadAll(StudentState.class));
        setOtherSearch(request, studentTypeIds, departmentIds);
        return forward(request);
    }
    
    /**
     * 第二专业待审学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        
        initSearchStudentBar(request, departmentIds, studentTypeIds);
        Results.addObject("studentStateList", utilService.loadAll(StudentState.class));
        setOtherSearch(request, studentTypeIds, departmentIds);
        
        String searchFalg = get(request, "searchFalg");
        if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
            EntityQuery query = stdSearchHelper.buildStdQuery(request);
            if (StringUtils.equals("null", get(request, "std.graduateAuditStatus"))) {
                query.add(new Condition("std.graduateAuditStatus is null"));
            }
            if (StringUtils.equals("null", get(request, "std.secondGraduateAuditStatus"))) {
                query.add(new Condition("std.secondGraduateAuditStatus is null"));
            }
            
            addCollection(request, "studentList", utilService.search(query));
        }
        return forward(request);
    }
    
    /**
     * 4. 批量审核条件内学生
     * 
     * @see com.shufe.web.action.graduate.StudentAuditManagerAction#batchAuditWithCondition(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward batchAuditWithCondition(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        StudentAuditProcessObserver observer = (StudentAuditProcessObserver) getOutputProcessObserver(
                mapping, request, response, StudentAuditProcessObserver.class);
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        String studenGraduateAuditStatus = get(request, "studentGraduateAuditStatus");
        String secondGraduateAuditStatus = get(request, "secondGraduateAuditStatus");
        Long auditStandardId = getLong(request, "auditStandardId");
        Student student = (Student) populateEntity(request, Student.class, "std");
        String adminClasssId1String = get(request, "adminClasssId1");
        String adminClasssId2String = get(request, "adminClasssId2");
        Set adminClassSet = new HashSet();
        if (StringUtils.isNotEmpty(adminClasssId1String)) {
            AdminClass adminClass = new AdminClass(Long.valueOf(adminClasssId1String));
            adminClassSet.add(adminClass);
        }
        if (StringUtils.isNotEmpty(adminClasssId2String)) {
            AdminClass adminClass = new AdminClass(Long.valueOf(adminClasssId2String));
            adminClassSet.add(adminClass);
        }
        student.setAdminClasses(adminClassSet);
        Pagination stdPagination = studentService.searchSecondSpecialityStudent(student, 1,
                StudentAuditProcessObserver.pageSize, studentTypeIds, departmentIds, new String[] {
                        studenGraduateAuditStatus, secondGraduateAuditStatus });
        observer.notifyStart("双专业毕业审核", stdPagination.getItemCount());
        MajorType majorType = new MajorType(MajorType.SECOND);
        graduateAuditService.batchGraduateAudit(stdPagination.getItems(), majorType,
                auditStandardId, null, observer);
        int maxPageNo = stdPagination.getMaxPageNumber();
        for (int i = 2; i <= maxPageNo; i++) {
            stdPagination = studentService.searchSecondSpecialityStudent(student, i,
                    StudentAuditProcessObserver.pageSize, studentTypeIds, departmentIds,
                    new String[] { studenGraduateAuditStatus, secondGraduateAuditStatus });
            graduateAuditService.batchGraduateAudit(stdPagination.getItems(), majorType,
                    auditStandardId, null, observer);
        }
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
}
