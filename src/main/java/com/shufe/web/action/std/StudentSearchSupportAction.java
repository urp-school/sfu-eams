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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.query.limit.PageLimit;
import com.ekingstar.commons.query.limit.SinglePage;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.baseinfo.model.StudentType;
import com.ekingstar.security.Resource;
import com.shufe.model.Constants;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.std.StudentService;
import com.shufe.service.system.baseinfo.SpecialityService;
import com.shufe.util.DataRealmUtils;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 学生搜索支持类
 */
public class StudentSearchSupportAction extends CalendarRestrictionSupportAction {
    
    public static final String ext = ".ftl";
    
    public static final String srcPackage = "com.shufe.web.action.";
    
    public static final char separator = '/';
    
    public static final String pageDir = separator + "pages" + separator;
    
    public static final String actionPostfix = "Action";
    
    public static final String urlPostfix = ".do";
    
    public static final Map action_pages;
    static {
        action_pages = new HashMap();
        action_pages.put("search", "list");
        action_pages.put("query", "list");
        action_pages.put("edit", "form");
        action_pages.put("home", "index");
        action_pages.put("execute", "index");
        action_pages.put("add", "new");
    }
    
    public static final String OR = "or";
    
    public static final String AND = "and";
    
    protected StudentService studentService;
    
    protected SpecialityService specialityService;
    
    protected TeachTaskService teachTaskService;
    
    /**
     * 搜索学生页面操作，当含搜索标示时向Results添加学生搜索结果
     * 
     * @param form
     * @param request
     * @param moduleName
     */
    protected void searchStudent(ActionForm form, HttpServletRequest request, String moduleName) {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        initSearchStudentBar(departmentIds, studentTypeIds);
        String searchFalg = get(request, "searchFalg");
        if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
            EntityQuery query = buildQuery(request, moduleName);
            SinglePage rs = (SinglePage) utilService.search(query);
            Pagination stds = new Pagination(query.getLimit().getPageNo(), query.getLimit()
                    .getPageSize(), new Result(rs.getTotal(), (List) rs.getPageDatas()));
            addOldPage(request, "studentList", stds);
        }
    }
    
    /**
     * 收集学生查询条件
     * 
     * @param request
     * @param moduleName
     * @return
     */
    protected EntityQuery buildQuery(HttpServletRequest request, String moduleName) {
        EntityQuery query = new EntityQuery(Student.class, "student");
        populateConditions(request, query, "student.type.id");
        Long stdTypeId = getLong(request, "student.type.id");
        DataRealmUtils.addDataRealms(query, new String[] { "student.type.id",
                "student.department.id" }, getDataRealmsWith(stdTypeId, request));
        Long adminClasssId = getLong(request, "adminClasssId1");
        if (null == adminClasssId) {
            adminClasssId = getLong(request, "adminClasssId2");
        }
        query.setLimit(getPageLimit(request));
        if (null != adminClasssId) {
            query.join("student.adminClasses", "adminClass");
            query.add(new Condition("adminClass.id=" + adminClasssId));
        }
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        return query;
    }
    
    /**
     * 获取学生搜索样例对象
     * 
     * @param request
     * @return
     */
    protected Student getStdExample(HttpServletRequest request) {
        Student std = (Student) RequestUtil.populate(request, Student.class, "student");
        String adminClasssId1String = request.getParameter("adminClasssId1");
        String adminClasssId2String = request.getParameter("adminClasssId2");
        Set adminClassSet = new HashSet();
        if (StringUtils.isNotEmpty(adminClasssId1String)) {
            AdminClass adminClass = new AdminClass(Long.valueOf(adminClasssId1String));
            adminClassSet.add(adminClass);
        }
        if (StringUtils.isNotEmpty(adminClasssId2String)) {
            AdminClass adminClass = new AdminClass(Long.valueOf(adminClasssId2String));
            adminClassSet.add(adminClass);
        }
        std.setAdminClasses(adminClassSet);
        return std;
    }
    
    /**
     * 通过request获取学生<code>EntityQuery</code>对象
     * 
     * @param request
     * @return
     */
    protected EntityQuery getStdQuery(HttpServletRequest request) {
        EntityQuery entityQuery = new EntityQuery(Student.class, "student");
        
        Resource resource = getResource(request);
        if (null != resource && !resource.getPatterns().isEmpty()) {
            entityQuery.add(new Condition("student.department in (:departDataRealm)",
                    getDeparts(request)));
            entityQuery.add(new Condition("student.type in (:departDataRealm)",
                    getStdTypes(request)));
        }
        QueryRequestSupport.populateConditions(request, entityQuery);
        Long adminClasssId1 = getLong(request, "adminClasssId1");
        Long adminClasssId2 = getLong(request, "adminClasssId2");
        if (adminClasssId1 != null && adminClasssId2 != null) {
            entityQuery.add(new Condition("exists (select class" + adminClasssId1
                    + " from AdminClass class" + adminClasssId1 + " join class" + adminClasssId1
                    + ".students std where std.id=student.id and class" + adminClasssId1
                    + ".id = :classId" + adminClasssId1 + ")" + " and exists (select class"
                    + adminClasssId2 + " from AdminClass class" + adminClasssId2 + " join class"
                    + adminClasssId2 + ".students std where std.id=student.id and class"
                    + adminClasssId2 + ".id = :classId" + adminClasssId2 + ")", adminClasssId1,
                    adminClasssId2));
        } else if (adminClasssId1 != null) {
            addAdminClassCondtion(entityQuery, adminClasssId1);
        } else if (adminClasssId2 != null) {
            addAdminClassCondtion(entityQuery, adminClasssId2);
        }
        return entityQuery;
    }
    
    /**
     * 添加一个班级条件
     * 
     * @param entityQuery
     * @param adminClasssId
     */
    private void addAdminClassCondtion(EntityQuery entityQuery, Long adminClasssId) {
        entityQuery.add(new Condition("exists (select class" + adminClasssId
                + " from AdminClass class" + adminClasssId + " join class" + adminClasssId
                + ".students std where std.id=id and class" + adminClasssId + ".id = :classId"
                + adminClasssId + ")", adminClasssId));
    }
    
    /**
     * 搜索双专业学生页面操作，当含搜索标示时向Results添加双专业学生搜索结果
     * 
     * @param form
     * @param request
     * @param is2ndMajor
     * @param andOr
     */
    protected void searchStudentWith2ndSpeciality(ActionForm form, HttpServletRequest request,
            Boolean is2ndMajor, String andOr) {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        initSearchStudentBar(departmentIds, studentTypeIds);
        Speciality speciality = new Speciality();
        speciality.setMajorType(new MajorType(MajorType.FIRST));
        if (Boolean.TRUE.equals(is2ndMajor)) {
            speciality.setMajorType(new MajorType(MajorType.SECOND));
        }
        Results.addObject("secondSpecialityList", specialityService.getSpecialities(speciality,
                studentTypeIds, departmentIds));
        String searchFalg = get(request, "searchFalg");
        if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
            DynaActionForm dynaForm = (DynaActionForm) form;
            int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
            int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();
            Student std = getStdExample(request);
            if (Boolean.TRUE.equals(is2ndMajor)) {
                Collection stds = studentService.search2ndSpecialityStudent(std, new PageLimit(
                        pageNo, pageSize), studentTypeIds, "", departmentIds, "");
                addCollection(request, "studentList", stds);
            } else if (Boolean.FALSE.equals(is2ndMajor)) {
                Pagination stds = null;
                stds = studentService.searchStudent(std, pageNo, pageSize, studentTypeIds,
                        departmentIds);
                addOldPage(request, "studentList", stds);
            } else {
                Collection stds = studentService.search2ndSpecialityStudent(std, new PageLimit(
                        pageNo, pageSize), studentTypeIds, departmentIds, departmentIds,
                        StringUtils.isBlank(andOr) ? "or" : andOr);
                addCollection(request, "studentList", stds);
            }
            
        }
    }
    
    /**
     * 初始化学生信息搜索框
     * 
     * @param departmentIds
     * @param studentTypeIds
     */
    protected void initSearchStudentBar(String departmentIds, String studentTypeIds) {
        List stdTypes = studentTypeService.getStudentTypes(studentTypeIds);
        Collections.sort(stdTypes, new PropertyComparator("code"));
        Results.addObject("stdTypeList", stdTypes);
        List departs = departmentService.getColleges(departmentIds);
        Collections.sort(departs, new PropertyComparator("code"));
        Results.addObject("departmentList", departs);
    }
    
    /**
     * 搜索Form页面初始化
     * 
     * @param form
     * @param request
     */
    protected void initSearchBar(ActionForm form, HttpServletRequest request) {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        initSearchStudentBar(departmentIds, studentTypeIds);
        setOtherSearch(request, studentTypeIds, departmentIds);
        String searchFalg = request.getParameter("searchFalg");
        if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
            searchBar(form, request, studentTypeIds, departmentIds);
        }
    }
    
    /**
     * 搜索学生并向Results添加学生搜索结果
     * 
     * @param form
     * @param request
     * @param studentTypeIds
     * @param departmentIds
     */
    protected void searchBar(ActionForm form, HttpServletRequest request, String studentTypeIds,
            String departmentIds) {
        EntityQuery entityQuery = getStdQuery(request);
        Results.addObject("studentList", utilService.search(entityQuery));
    }
    
    /**
     * 添加额外搜索项数据<br>
     * 供子类重写
     * 
     * @param request
     * @param studentTypeIds
     * @param departmentIds
     */
    protected void setOtherSearch(HttpServletRequest request, String studentTypeIds,
            String departmentIds) {
    }
    
    /**
     * @param specialityService
     *            要设置的 specialityService.
     */
    public void setSpecialityService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
    
    /**
     * @param studentService
     *            要设置的 studentService.
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * 搜索行政班级页面操作，当含搜索标示时向Results添加班级搜索结果
     * 
     * @param form
     * @param request
     */
    protected void searchAdminClass(ActionForm form, HttpServletRequest request) {
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        Results.addObject("stdTypeList", studentTypeService.getStudentTypes(studentTypeIds));
        Results.addObject("departmentList", departmentService.getColleges(departmentIds));
        initSearchStudentBar(departmentIds, studentTypeIds);
        String searchFalg = request.getParameter("searchFalg");
        if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
            DynaActionForm dynaForm = (DynaActionForm) form;
            int pageNo = ((Integer) dynaForm.get("pageNo")).intValue();
            int pageSize = ((Integer) dynaForm.get("pageSize")).intValue();
            AdminClass adminClass = (AdminClass) RequestUtil.populate(request, AdminClass.class,
                    "adminClass");
            Pagination adminClassList = studentService.searchStudentClass(adminClass, pageNo,
                    pageSize, studentTypeIds, departmentIds);
            addOldPage(request, "adminClassList", adminClassList);
        }
    }
    
    /**
     * 搜索教学任务页面操作，当含搜索标示时向Results添加教学任务搜索结果
     * 
     * @param form
     * @param request
     * @throws Exception
     */
    protected void searchTeachTask(ActionForm form, HttpServletRequest request) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        
        String studentTypeIds = getStdTypeIdSeq(request);
        String departmentIds = getDepartmentIdSeq(request);
        Object obj = request.getAttribute(Constants.CALENDAR);
        if (obj != null) {
            Results.addList("courseTypeList", teachTaskService.getCourseTypesOfTask(studentTypeIds,
                    departmentIds, (TeachCalendar) obj));
            addCollection(request, "teachDepartList", getTeachDeparts(request));
            request.removeAttribute(Constants.DEPARTMENT_LIST);
            Results.addList(Constants.DEPARTMENT_LIST, getColleges(request));
            Results.addList("teacherList", teachTaskService.getTeachersOfTask(studentTypeIds,
                    departmentIds, (TeachCalendar) obj));
            
            String searchFalg = request.getParameter("searchFalg");
            if (StringUtils.isNotEmpty(searchFalg) && searchFalg.equals("search")) {
                int pageNo = QueryRequestSupport.getPageNo(request);
                int pageSize = QueryRequestSupport.getPageSize(request);
                TeachTask task = (TeachTask) RequestUtil.populate(request, TeachTask.class,
                        "teachTask");
                Teacher teacher = (Teacher) RequestUtil.populate(request, Teacher.class,
                        Constants.TEACHER);
                task.getArrangeInfo().getTeachers().add(teacher);
                /*-------------获得学年度学期信息--------------------*/
                Long stdTypeId = getLong(request, "calendar.studentType.id");
                task.getTeachClass().setStdType(
                        (StudentType) EntityUtils.getEntity(StudentType.class, stdTypeId));
                if (task.getCalendar().getId() == null) {
                    String year = request.getParameter("calendar.year");
                    String term = request.getParameter("calendar.term");
                    TeachCalendar calendar = null;
                    if (StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(term)) {
                        calendar = getTeachCalendarService().getTeachCalendar(
                                new StudentType(stdTypeId), year, term);
                        task.setCalendar(calendar);
                    } else {
                    }
                    if (null == calendar) {
                    }
                }
                
                Pagination tasks = null;
                /*-------------查询数据--------------------------*/
                if (task.getArrangeInfo().getTeachDepart().isVO()) {
                    String departIds = getDepartmentIdSeq(request);
                    
                    tasks = getTaskService().getTeachTasksOfTeachDepart(task,
                            getStdTypeIdSeqOf(stdTypeId, request), departIds, pageNo, pageSize);
                } else
                    tasks = getTaskService().getTeachTasks(task, pageNo, pageSize);
                addOldPage(request, "teachTaskList", tasks);
                // 查询页面需要日历信息
                Results.addObject("calendar", task.getCalendar());
                addEntity(request, task);
            }
        }
    }
    
    protected ActionForward forward(HttpServletRequest request) {
        return this.forward(request, (String) null);
    }
    
    /**
     * @deprecated
     */
    protected ActionForward forward(HttpServletRequest request, String pagePath) {
        StringBuffer buf = new StringBuffer();
        if (pagePath != null && pagePath.startsWith("/")) {
            buf.append(pagePath).append(ext);
            addResults(request);
            loggerDebug(buf);
            return new ActionForward(buf.toString());
        }
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
        if (StringUtils.isEmpty(pagePath)) {
            String method = request.getParameter("method");
            if (StringUtils.isEmpty(method))
                throw new RuntimeException("need method parameter in dispatch action!");
            if (null == action_pages.get(method))
                buf.append(method);
            else
                buf.append(action_pages.get(method));
        } else {
            buf.append(pagePath);
        }
        buf.append(ext);
        addResults(request);
        loggerDebug(buf);
        return new ActionForward(buf.toString());
    }
    
    /**
     * @param buf
     */
    private void loggerDebug(StringBuffer buf) {
        if (log.isDebugEnabled()) {
            log.debug(buf.toString());
        }
    }
    
    /**
     * @param request
     */
    protected void addResults(HttpServletRequest request) {
        if (Results != null) {
            request.setAttribute(DETAIL_RESULT, Results.getDetail());
        }
    }
    
    protected ActionForward forwardError(ActionMapping mapping, HttpServletRequest request,
            String errorKey, String[] errorValues) {
        addResults(request);
        ActionMessages actionMessages = new ActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey, errorValues));
        addErrors(request, actionMessages);
        debug("Action forwarding [" + mapping.findForward(errorForward) + "]");
        return mapping.findForward(errorForward);
    }
    
    protected TeachTaskService getTaskService() {
        return teachTaskService;
    }
    
    /**
     * @param teachTaskService
     *            要设置的 teachTaskService.
     */
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    /**
     * <pre>
     * 获取学生的BeanMap,不限制权限
     * TODO 现在的BeanMap为手动添加的
     * </pre>
     * 
     * @param stdNoMap
     * @param request
     * @return
     */
    public Map getStdBeanMap(Map stdNoMap, HttpServletRequest request) {
        String stdCodes = (String) stdNoMap.get("record.student.code");
        String[] stdCodeArray = StringUtils.split(stdCodes, ",");
        if (ArrayUtils.isEmpty(stdCodeArray)) {
            return Collections.EMPTY_MAP;
        }
        List stdList = utilService.load(Student.class, "code", stdCodeArray);
        if (stdList.size() == 0) {
            return Collections.EMPTY_MAP;
        } else {
            Map resultMap = new HashMap();
            for (Iterator iter = stdList.iterator(); iter.hasNext();) {
                Student std = (Student) iter.next();
                Map stdMap = new HashMap();
                stdMap.put("student.id", std.getId());
                stdMap.put("student.code", std.getCode());
                stdMap.put("student.name", std.getName());
                stdMap.put("student.engName", std.getEngName());
                stdMap.put("student.enrollYear", std.getEnrollYear());
                stdMap.put("student.type.name", std.getType().getName());
                stdMap.put("student.type.engName", std.getType().getEngName());
                stdMap.put("student.state.name", std.isActive() ? "有效" : "无效");
                stdMap.put("student.state.engName", std.isActive() ? "有效" : "无效");
                stdMap.put("student.department.name", std.getDepartment().getName());
                stdMap.put("student.department.engName", std.getDepartment().getEngName());
                stdMap.put("student.firstMajor.name", (null == std.getFirstMajor()) ? null : std
                        .getFirstMajor().getName());
                stdMap.put("student.firstMajor.engName", (null == std.getFirstMajor()) ? null : std
                        .getFirstMajor().getEngName());
                stdMap.put("student.firstAspect.name", (null == std.getFirstAspect()) ? null : std
                        .getFirstAspect().getName());
                stdMap.put("student.firstAspect.engName", (null == std.getFirstAspect()) ? null
                        : std.getFirstAspect().getEngName());
                stdMap.put("student.basicInfo.homeAddress", (null == std.getBasicInfo()
                        .getHomeAddress()) ? null : std.getBasicInfo().getHomeAddress());
                stdMap.put("student.basicInfo.postCode",
                        (null == std.getBasicInfo().getPostCode()) ? null : std.getBasicInfo()
                                .getPostCode());
                stdMap.put("student.basicInfo.phone",
                        (null == std.getBasicInfo().getPhone()) ? null : std.getBasicInfo()
                                .getPhone());
                resultMap.put(std.getId(), stdMap);
            }
            return resultMap;
        }
    }
}
