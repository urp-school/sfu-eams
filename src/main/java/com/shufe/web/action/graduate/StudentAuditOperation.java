//$Id: StudentAuditOperation.java,v 1.22 2007/01/18 09:31:03 yd Exp $
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
 * pippo             2005-11-16         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.graduate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.std.Student;

/**
 * 学生毕业审核操作
 */
public class StudentAuditOperation extends StudentAuditSupportAction {
    
    /**
     * 2. 批量手动审核<br>
     * 3. 撤销通过选中学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchAudit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] stdId = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        studentService.batchUpdateGraduateAuditStatus(stdId, Boolean.valueOf(request
                .getParameter("status")));
        return redirect(request, new Action("studentAuditManager", "search"), "info.action.success");
    }
    
    /**
     * 2. 审核通过选中学生(双)<br>
     * 3. 撤销通过选中学生(双)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchAuditSecond(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] stdId = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        studentService.batchUpdateSecondGraduateAuditStatus(stdId, Boolean.valueOf(request
                .getParameter("status")));
        return redirect(request, new Action("secondSpecialityStudentAuditManager", "search"),
                "info.action.success");
    }
    
    /**
     * 1. 批量自动审核
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward batchAutoAudit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)throws Exception {
        Long[] stdId = SeqStringUtil.transformToLong(get(request, "stdIds"));
        
        Long auditStandardId = getLong(request, "auditStandardId");
        /* 审核通过 */
        MajorType majorType = new MajorType(MajorType.FIRST);
        List parseStudent = null;
        try {
            parseStudent = graduateAuditService
                    .batchAuditStudent(stdId, majorType, auditStandardId);
        } catch (Exception e) {
            e.printStackTrace();
            return this.forwardError(mapping, request, e.getMessage());
        }
        if (!CollectionUtils.isEmpty(parseStudent)) {
            studentService.batchUpdateGraduateAuditStatus(parseStudent, new Boolean(true));
        }
        
        /* 审核未通过 */
        List unParseStudent = new ArrayList();
        for (int i = 0; i < stdId.length; i++) {
            if (!parseStudent.contains(stdId[i]))
                unParseStudent.add(stdId[i]);
        }
        if (!CollectionUtils.isEmpty(unParseStudent)) {
            studentService.batchUpdateGraduateAuditStatus(unParseStudent, new Boolean(false));
        }
        return redirect(request,new Action("studentAuditManager", "search"), "info.audit.complete");
    }
    
    /**
     * 1.自动审核选中学生（双）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchAutoSecondSpecialityAudit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] stdId = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        Long auditStandardId = getLong(request, "auditStandardId");
        /* 审核通过 */
        List parseStudent = null;
        MajorType majorType = new MajorType(MajorType.SECOND);
        try {
            parseStudent = graduateAuditService
                    .batchAuditStudent(stdId, majorType, auditStandardId);
        } catch (Exception e) {
            return this.forwardError(mapping, request, e.getMessage());
        }
        studentService.batchUpdateGraduateAuditStatus(parseStudent, majorType, new Boolean(true));
        /* 审核未通过 */
        List unParseStudent = new ArrayList(stdId.length - parseStudent.size());
        for (int i = 0; i < stdId.length; i++) {
            if (!parseStudent.contains(stdId[i]))
                unParseStudent.add(stdId[i]);
        }
        studentService
                .batchUpdateGraduateAuditStatus(unParseStudent, majorType, new Boolean(false));
        return redirect(request, new Action("secondSpecialityStudentAuditManager", "search"),
                "info.action.success");
    }
    
    /**
     * 根据学号读取审核结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Long stdId = getLong(request, "stdId");
        MajorType majorType = new MajorType(getLong(request, "majorTypeId"));
        Object studentObject = utilService.load(Student.class, stdId);
        if (studentObject == null) {
            throw new RuntimeException("No such student!");
        }
        Student student = (Student) studentObject;
        Long auditStandardId = null;
        if (NumberUtils.isNumber(request.getParameter("auditStandardId"))) {
            auditStandardId = Long.valueOf(request.getParameter("auditStandardId"));
        }
        try {
            getStudentTeachPlanAuditDetail(student, majorType, auditStandardId, request
                    .getParameter("auditTerm"), null, Boolean.TRUE);
        } catch (PojoNotExistException e) {
            if (e.getMessage() != null
                    && e.getMessage().equals(com.shufe.model.Constants.TEACHPLAN)) {
                return this.forwardError(mapping, request, "error.teachPlan.notExists");
            }
        }
        return this.forward(request, "../auditResultDetail");
    }
    
    public ActionForward changeCourseType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        addCollection(request, "courseTypeList", baseCodeService.getCodes(CourseType.class));
        return this.forward(request, "../changeCourseType");
    }
    
    public ActionForward saveCourseType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long courseId = getLong(request, "courseId");
        Long studentId = getLong(request, "studentId");
        Long courseTypeId = getLong(request, "courseType.id");
        List grades = utilService.load(CourseGrade.class, new String[] { "std.id", "course.id" },
                new Object[] { studentId, courseId });
        for (Iterator iter = grades.iterator(); iter.hasNext();) {
            CourseGrade grade = (CourseGrade) iter.next();
            if (null != courseTypeId) {
                grade.setCourseType(new CourseType(courseTypeId));
            }
        }
        utilService.saveOrUpdate(grades);
        // 提示成功信息
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.save.success"));
        saveErrors(request, messages);
        return mapping.findForward("actionResult");
    }
    
    /**
     * 根据学号批量读取审核结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward batchDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String stdIdString = request.getParameter("stdId");
        Long[] stdIdArray = SeqStringUtil.transformToLong(stdIdString);
        MajorType majorType = new MajorType(getLong(request, "majorTypeId"));
        List stdList = utilService.load(Student.class, "id", stdIdArray);
        if (CollectionUtils.isEmpty(stdList)) {
            throw new RuntimeException("No such student!");
        }
        Long auditStandardId = null;
        if (NumberUtils.isNumber(request.getParameter("auditStandardId"))) {
            auditStandardId = Long.valueOf(request.getParameter("auditStandardId"));
        }
        Collections.sort(stdList, new BeanComparator("code"));
        List resultList = new ArrayList();
        for (Iterator iter = stdList.iterator(); iter.hasNext();) {
            Student student = (Student) iter.next();
            try {
                resultList.addAll(graduateAuditService.getStudentTeachPlanAuditDetail(student,
                        majorType, auditStandardId, graduateAuditService.getAuditTermList(request
                                .getParameter("auditTerm")), null, Boolean.TRUE));
            } catch (PojoNotExistException e) {
                if (e.getMessage() != null
                        && e.getMessage().equals(com.shufe.model.Constants.TEACHPLAN)) {
                    return this.forwardError(mapping, request, "error.teachPlan.notExists");
                }
            }
        }
        Results.addObject("resultList", resultList);
        return this.forward(request, "../batchAuditResultDetail");
    }
    
    /**
     * 单个修改毕业审核结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateResultDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // FIXME
        String stdNo = request.getParameter("stdNo");
        String remark = request.getParameter("remark");
        Boolean status = Boolean.valueOf(request.getParameter("status"));
        
        Map parameterMap = new HashMap(2);
        parameterMap.put("remark", remark);
        parameterMap.put("status", status);
        utilService.update(AuditResult.class, "stdNo", new Object[] { stdNo }, parameterMap);
        
        parameterMap.clear();
        parameterMap.put("graduateAuditStatus", status);
        utilService.update(Student.class, "stdNo", new Object[] { stdNo }, parameterMap);
        
        Results
                .addObject("auditResult", utilService.load(AuditResult.class, "stdNo", stdNo)
                        .get(0));
        return this.forward(mapping, request, "detail");
    }
    
}
