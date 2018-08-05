//$Id: StudentAuditManager.java,v 1.20 2007/01/18 10:00:32 yd Exp $
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
 * pippo                2005-11-15          Created
 * zq                   2007-10-16          把secondSpecialityStudentList()方法
 *                                          的Results.addObject()改成addOldPage()。
 ********************************************************************************/

package com.shufe.web.action.graduate;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.StudentState;
import com.shufe.model.std.PlanAuditResult;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.service.graduate.AuditStandardService;
import com.shufe.service.std.StudentService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.OutputProcessObserver;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 毕业审核<br>
 * 1. 批量自动审核：<br>
 * 位置： StudentAuditOperation<br>
 * 2. 批量手动审核：<br>
 * 位置： StudentAuditOperation<br>
 * 3. 批量手动审核：<br>
 * 位置： StudentAuditOperation<br>
 * 4. 批量审核给定条件内的学生：<br>
 * 位置： this<br>
 * 
 * @author dell
 */
public class StudentAuditManagerAction extends StudentAuditSupportAction {
    
    private AuditStandardService auditStandardService;
    
    private StdSearchHelper stdSearchHelper;
    
    /**
     * @param auditStandardService
     *            要设置的 auditStandardService.
     */
    public void setAuditStandardService(AuditStandardService auditStandardService) {
        this.auditStandardService = auditStandardService;
    }
    
    /**
     * 待审核学生列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return studentAuditManager.ftl
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        initSearchBar(form, request);
        initBaseCodes("studentStateList", StudentState.class);
        return forward(request);
    }
    
    public ActionForward studentList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        initSearchBar(form, request);
        initBaseCodes("studentStateList", StudentState.class);
        return this.forward(mapping, request, "studentList");
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        // searchBar(form, request, studentTypeIds, departmentIds);
        setOtherSearch(request, studentTypeIds, departmentIds);
        
        EntityQuery query = stdSearchHelper.buildStdQuery(request);

        Collection studentList =utilService.search(query);
        addCollection(request, "studentList", studentList);
        Map<String,PlanAuditResult> resultMap = new HashMap<String,PlanAuditResult>();
		if (!studentList.isEmpty()) {
			EntityQuery query2 = new EntityQuery(PlanAuditResult.class, "r");
			query2.add(new Condition("r.std in(:stds)", studentList));
			Collection resultList = utilService.search(query2);
			for (Object obj : resultList) {
				PlanAuditResult r = (PlanAuditResult) obj;
				resultMap.put(r.getStd().getId().toString(), r);
			}
		}
        request.setAttribute("resultMap", resultMap);
        return forward(request);
    }
    
    /**
     * @param form
     * @param request
     * @param studentTypeIds
     * @param departmentIds
     */
    protected void searchBar(ActionForm form, HttpServletRequest request, String studentTypeIds,
            String departmentIds) {
        DynaActionForm dynaForm = (DynaActionForm) form;
        
        String studenGraduateAuditStatus = request.getParameter("studentGraduateAuditStatus");
        int pageNo = ((Integer) (request.getAttribute("pageNo") == null ? dynaForm.get("pageNo")
                : request.getAttribute("pageNo"))).intValue();
        int pageSize = ((Integer) (request.getAttribute("pageSize") == null ? dynaForm
                .get("pageSize") : request.getAttribute("pageSize"))).intValue();
        Pagination stds = studentService.searchStudent(populateStudent(request), pageNo, pageSize,
                studentTypeIds, departmentIds, studenGraduateAuditStatus);
        addOldPage(request, "studentList", stds);
        Results.addObject("studenGraduateAuditStatus", studenGraduateAuditStatus);
    }
    
    /**
     * populate页面提供的学生数据
     * 
     * @param request
     * @return
     */
    protected Student populateStudent(HttpServletRequest request) {
        Student student = (Student) populateEntity(request, Student.class, "std");
        String adminClasssIdString = get(request, "adminClasssId1");
        String adminClassName = get(request, "adminClassName");
        AdminClass adminClass = null;
        if (StringUtils.isNotEmpty(adminClasssIdString)) {
            adminClass = new AdminClass(Long.valueOf(adminClasssIdString));
        }
        if (StringUtils.isNotEmpty(adminClassName)) {
            if (null == adminClass) {
                adminClass = new AdminClass();
            }
            adminClass.setName(adminClassName);
        }
        Set adminClassSet = new HashSet();
        adminClassSet.add(adminClass);
        student.setAdminClasses(adminClassSet);
        return student;
    }
    
    /**
     * @param studentTypeIds
     * @param departmentIds
     */
    protected void setOtherSearch(HttpServletRequest request, String studentTypeIds,
            String departmentIds) {
        Speciality speciality = new Speciality();
        speciality.setMajorType(new MajorType(MajorType.SECOND));
        Results.addObject("secondSpecialityList", specialityService.getSpecialities(speciality,
                studentTypeIds, departmentIds));
        AuditStandard auditStandard = (AuditStandard) populate(request, AuditStandard.class,
                "auditStandard");
        Results.addObject("auditStandardList", auditStandardService.searchAuditStandard(
                auditStandard, studentTypeIds));
    }
    
    /**
     * 4.批量审核给定条件内的学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward batchAuditWithCondition(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String auditTerm = request.getParameter("auditTerm");
        StudentAuditProcessObserver observer = (StudentAuditProcessObserver) getOutputProcessObserver(
                mapping, request, response, StudentAuditProcessObserver.class);
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        Long auditStandardId = getLong(request, "auditStandardId");
        
        String studenGraduateAuditStatus = request.getParameter("studentGraduateAuditStatus");
        Student student = populateStudent(request);
        /* long startTime = System.currentTimeMillis(); */
        Pagination stdPagination = studentService.searchStudent(student, 1,
                StudentAuditProcessObserver.pageSize, studentTypeIds, departmentIds,
                studenGraduateAuditStatus);
        MajorType majorType = new MajorType(MajorType.FIRST);
        if (StringUtils.isEmpty(auditTerm)) {
            observer.notifyStart("毕业审核", stdPagination.getItemCount());
            graduateAuditService.batchGraduateAudit(stdPagination.getItems(), majorType,
                    auditStandardId, null, observer);
            int maxPageNo = stdPagination.getMaxPageNumber();
            for (int i = 2; i <= maxPageNo; i++) {
                stdPagination = studentService.searchStudent(student, i,
                        StudentAuditProcessObserver.pageSize, studentTypeIds, departmentIds,
                        studenGraduateAuditStatus);
                graduateAuditService.batchGraduateAudit(stdPagination.getItems(), majorType,
                        auditStandardId, null, observer);
            }
        } else {
            observer.notifyStart("按学期审核培养计划" + graduateAuditService.getAuditTermList(auditTerm),
                    stdPagination.getItemCount());
            graduateAuditService.batchGraduateAudit(stdPagination.getItems(), majorType,
                    auditStandardId, auditTerm, observer);
            int maxPageNo = stdPagination.getMaxPageNumber();
            for (int i = 2; i <= maxPageNo; i++) {
                stdPagination = studentService.searchStudent(student, i,
                        StudentAuditProcessObserver.pageSize, studentTypeIds, departmentIds,
                        studenGraduateAuditStatus);
                graduateAuditService.batchGraduateAudit(stdPagination.getItems(), majorType,
                        auditStandardId, auditTerm, observer);
            }
        }
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }
    
    protected OutputProcessObserver getOutputProcessObserver(ActionMapping mapping,
            HttpServletRequest request, HttpServletResponse response, Class observerClass)
            throws Exception {
        return getOutputProcessObserver(mapping, request, response, "processDisplay", observerClass);
    }
    
    protected OutputProcessObserver getOutputProcessObserver(ActionMapping mapping,
            HttpServletRequest request, HttpServletResponse response, String forwardName,
            Class observerClass) throws Exception {
        String ext = ".ftl";
        String srcPackage = "com.shufe.web.action.";
        char separator = '/';
        String pageDir = separator + "pages" + separator;
        String actionPostfix = "Action";
        Map action_pages = new HashMap();
        action_pages.put("search", "list");
        action_pages.put("query", "list");
        action_pages.put("edit", "form");
        action_pages.put("home", "index");
        action_pages.put("execute", "index");
        action_pages.put("add", "new");
        
        StringBuffer buf = new StringBuffer();
        buf.append(pageDir);
        buf.append(StringUtils.substring(clazz.getPackage().getName(), srcPackage.length())
                .replace('.', separator));
        String simpleName = clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1);
        if (simpleName.indexOf(actionPostfix) != -1) {
            buf.append(separator).append(
                    StringUtils.uncapitalize(simpleName.substring(0, simpleName.length()
                            - actionPostfix.length())));
        } else {
            buf.append(separator).append(
                    StringUtils.uncapitalize(simpleName.substring(0, simpleName.length())));
        }
        buf.append(separator);
        if (StringUtils.isEmpty(forwardName)) {
            String method = request.getParameter("method");
            if (StringUtils.isEmpty(method))
                throw new RuntimeException("need method parameter in dispatch action!");
            if (null == action_pages.get(method))
                buf.append(method);
            else
                buf.append(action_pages.get(method));
        } else {
            buf.append(forwardName);
        }
        buf.append(ext);
        if (Results != null) {
            request.setAttribute(DETAIL_RESULT, Results.getDetail());
        }
        if (log.isDebugEnabled()) {
            log.debug(buf.toString());
        }
        response.setContentType("text/html; charset=utf-8");
        // response.setHeader("title","name");
        ActionForward processDisplay = new ActionForward(buf.toString());
        String path = request.getSession().getServletContext().getRealPath("")
                + processDisplay.getPath();
        OutputProcessObserver observer = (OutputProcessObserver) observerClass.newInstance();
        observer.setResourses(getResources(request));
        observer.setLocale(getLocale(request));
        observer.setWriter(response.getWriter());
        observer.setPath(path);
        observer.outputTemplate();
        return observer;
    }
    
    protected Collection getExportDatas(HttpServletRequest request) {
        Long[] stdIds = SeqStringUtil.transformToLong(get(request, "stdIds"));
        if (null == stdIds || stdIds.length == 0) {
            EntityQuery stdQuery = buildQuery(request);
            stdQuery.setSelect("id");
            stdQuery.setLimit(null);
            Collection students = utilService.search(stdQuery);
            stdIds = (Long[]) students.toArray();
        }
        EntityQuery query = new EntityQuery(AuditResult.class, "auditResult");
        query.add(new Condition("auditResult.std.id in (:stdIds)", stdIds));
        Collection list = utilService.search(query);
        return list;
    }
    
    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(Student.class, "std");
        populateConditions(request, query, "std.type.id");
        DataRealmUtils.addDataRealms(query, new String[] { "std.type.id", "std.department.id" },
                restrictionHelper.getDataRealmsWith(getLong(request, "std.type.id"), request));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }
    
    /**
     * 批量审核确认/取消确认
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchAuditAffirm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] stdId = SeqStringUtil.transformToLong(request.getParameter("stdIds"));
        // 更新审核确认状态
        studentService.batchUpdateGraduateAuditStatus(stdId, Boolean.valueOf(request
                .getParameter("status")));
        return redirect(request, new Action("", "search"), "info.action.success");
    }
    
    /**
     * @return Returns the studentService.
     */
    public StudentService getStudentService() {
        return studentService;
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
    
}
