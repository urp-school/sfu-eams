//$Id: ElectScopeAction.java,v 1.14 2006/12/31 03:42:23 duanth Exp $
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
 * chaostone             2005-12-5         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.election;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.HSKDegree;
import com.ekingstar.eams.system.basecode.state.Gender;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.Constants;
import com.shufe.model.course.election.ElectInitParams;
import com.shufe.model.course.election.ElectStdScope;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.election.ElectStdScopeService;
import com.shufe.service.course.election.ValidElectStdScopePredicate;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.service.system.baseinfo.SpecialityService;
import com.shufe.web.OutputProcessObserver;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;
import com.shufe.web.helper.TeachTaskSearchHelper;

/**
 * 选课范围界面相应类
 * 
 * @author chaostone
 * 
 */
public class ElectScopeAction extends CalendarRestrictionSupportAction {
    
    private SpecialityService specialityService;
    
    private AdminClassService classService;
    
    private TeachTaskService teachTaskService;
    
    private ElectStdScopeService electStdScopeService;
    
    private TeachTaskSearchHelper teachTaskSearchHelper;
    
    /**
     * 选课范围主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String stdTypeDataRealm = getStdTypeIdSeq(request);
        String departDataRealm = getDepartmentIdSeq(request);
        setCalendarDataRealm(request, hasStdTypeCollege);
        addCollection(request, "courseTypes", teachTaskService.getCourseTypesOfTask(
                stdTypeDataRealm, departDataRealm, (TeachCalendar) request
                        .getAttribute(Constants.CALENDAR)));
        /*----------------加载上课院系和教师列表--------------------*/
        List departList = teachTaskService.getDepartsOfTask(stdTypeDataRealm, departDataRealm,
                (TeachCalendar) request.getAttribute(Constants.CALENDAR));
        addCollection(request, Constants.DEPARTMENT_LIST, departList);
        addCollection(request, "teachDepartList", departmentService.getColleges(departDataRealm));
        /*----------------加载上课学生性别列表--------------------*/
        initBaseCodes(request, "genderList", Gender.class);
        return forward(request);
    }
    
    /**
     * 列举教学任务（全部）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        addCollection(request, "tasks", teachTaskSearchHelper.searchTask(request));
        return forward(request);
    }
    
    /**
     * 列举教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward taskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "tasks", teachTaskSearchHelper.searchTask(request));
        return forward(request);
    }
    
    public ActionForward editTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "taskId");
        TeachTask task = null;
        if (null != taskId) {
            task = teachTaskService.loadTeachTask(taskId);
        }
        request.setAttribute("task", task);
        initBaseCodes(request, "HSKDegreeList", HSKDegree.class);
        return forward(request);
    }
    
    public ActionForward saveTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = getLong(request, "task.id");
        Map params = getParams(request, "task");
        TeachTask task = null;
        if (null != taskId) {
            task = teachTaskService.loadTeachTask(taskId);
        }
        populate(params, task);
        utilService.saveOrUpdate(task);
        return redirect(request, "taskList", "info.set.success");
    }
    
    /**
     * 设置为可选/不可选
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward batchUpdateEelectInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean updateSelected = getBoolean(request, "updateSelected");
        List tasks = getTaskSearched(request, updateSelected);
        if (null == tasks) {
            return forwardError(mapping, request, "error.model.id.needed");
        }
        
        ElectInitParams setting = (ElectInitParams) populate(request, ElectInitParams.class,
                "setting");
        ElectStdScope scope = (ElectStdScope) populate(request, ElectStdScope.class, "scope");
        setting.setScope(scope);
        if (setting.getScope().isEmptyScope()) {
            setting.setBatchAction("null");
            setting.setScope(null);
        }
        /*
         * 不能进行默认，依照用户的选择进行。 else { if (StringUtils.isEmpty(setting.getScope().getDepartIds())) {
         * setting.getScope().setDepartIds("," + Department.schoolId + ","); } }
         */
        response.setContentType("text/html; charset=utf-8");
        ActionForward processDisplay = mapping.findForward("processDisplay");
        String path = request.getSession().getServletContext().getRealPath("")
                + processDisplay.getPath();
        
        OutputProcessObserver observer = new OutputProcessObserver(response.getWriter(),
                getResources(request), getLocale(request), path);
        
        electStdScopeService.batchUpdateTasksElectInfo(tasks, setting, observer);
        return null;
    }
    
    /**
     * 设置为可选/不可选
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward setCancelable(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Boolean updateSelected = getBoolean(request, "updateSelected");
        List tasks = getTaskSearched(request, updateSelected);
        if (null == tasks)
            return forwardError(mapping, request, "error.model.id.needed");
        
        ElectInitParams setting = new ElectInitParams();
        setting.setScope(null);
        Boolean isCancelable = getBoolean(request, "isCancelable");
        if (null == isCancelable) {
            isCancelable = Boolean.FALSE;
        }
        setting.setIsCancelable(isCancelable);
        electStdScopeService.batchUpdateTasksElectInfo(tasks, setting, null);
        return redirect(request, "taskList", "info.set.success", "task.electInfo.isElectable="
                + request.getParameter("task.electInfo.isElectable"));
    }
    
    /**
     * 设置课程选课参数
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward electSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "stdTypeList", baseInfoService.getBaseInfos(StudentType.class));
        String stdTypeIdSeq = getStdTypeIdSeq(request);
        String departIdSeq = getDepartmentIdSeq(request);
        Department school = departmentService.getDepartment(Department.SCHOOLID);
        
        List specialityList = specialityService.getSpecialities(null, stdTypeIdSeq, departIdSeq);
        List departments = departmentService.getColleges(departIdSeq);
        if (!departments.contains(school)) {
            departments.add(school);
        }
        request.setAttribute(Constants.DEPARTMENT_LIST, departments);
        request.setAttribute(Constants.SPECIALITY_LIST, specialityList);
        
        List specialityAspectList = new ArrayList();
        for (Iterator iter = specialityList.iterator(); iter.hasNext();) {
            Speciality speciality = (Speciality) iter.next();
            specialityAspectList.addAll(speciality.getAspects());
        }
        request.setAttribute(Constants.SPECIALITYASPECT_LIST, specialityAspectList);
        List a = classService.getAdminClasses(null, stdTypeIdSeq, departIdSeq);
        request.setAttribute(Constants.ADMINCLASS_LIST, a);
        initBaseCodes(request, "genders", Gender.class);
        return forward(request);
    }
    
    /**
     * 修改教学任务选课范围
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
        String taskId = request.getParameter(Constants.TEACHTASK_KEY);
        if (StringUtils.isEmpty(taskId))
            return forward(mapping, request, "error.teachTask.id.needed", "failure");
        TeachTask task = null;
        if (taskId.indexOf(",") != 0) {
            String[] taskIds = StringUtils.split(taskId, ",");
            for (int i = 0; i < taskIds.length; i++) {
                task = teachTaskService.loadTeachTask(Long.valueOf(taskIds[i]));
                // 参选任务有一个默认的开课范围
                if (task.getElectInfo().getElectScopes().size() > 1)
                    break;
            }
        }
        String stdTypeIdSeq = getStdTypeIdSeq(request);
        String departIdSeq = getDepartmentIdSeq(request);
        departIdSeq += Department.SCHOOLID + "," + departIdSeq;
        request.setAttribute("taskIds", taskId);
        request.setAttribute(Constants.STUDENTTYPE_LIST, getStdTypes(request));
        request.setAttribute(Constants.TEACHTASK, task);
        request.setAttribute(Constants.DEPARTMENT_LIST, departmentService.getColleges(departIdSeq));
        List specialityList = specialityService.getSpecialitiesByDepartmentIds(departIdSeq);
        request.setAttribute(Constants.SPECIALITY_LIST, specialityList);
        
        List specialityAspectList = new ArrayList();
        for (Iterator iter = specialityList.iterator(); iter.hasNext();) {
            Speciality speciality = (Speciality) iter.next();
            specialityAspectList.addAll(speciality.getAspects());
            
        }
        request.setAttribute(Constants.SPECIALITYASPECT_LIST, specialityAspectList);
        request.setAttribute(Constants.ADMINCLASS_LIST, classService.getAdminClasses(null,
                stdTypeIdSeq, departIdSeq));
        return forward(request);
    }
    
    /**
     * 教学任务的选课人数上限和排课的中使用的教室容量的对比
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward maxStdCountCheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        Collection rs = electStdScopeService.getMaxStdCountNotEqualRoomCapacity(calendarId);
        Long[] taskIds = new Long[rs.size()];
        int i = 0;
        Map capacityMap = new HashMap();
        for (Iterator iter = rs.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            taskIds[i] = new Long(((Number) element[0]).longValue());
            capacityMap.put(taskIds[i].toString(), element[1]);
            i++;
        }
        Collection tasks = teachTaskService.getTeachTasksByIds(taskIds);
        addCollection(request, "tasks", tasks);
        request.setAttribute("capacityMap", capacityMap);
        return forward(request);
    }
    
    /**
     * 依据排课的中使用的教室容量的设置教学任务的选课人数上限
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward setMaxStdCountByRoomCapacity(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long[] taskIds = SeqStringUtil.transformToLong(request.getParameter("taskIds"));
        Collection tasks = null;
        if (taskIds != null)
            tasks = teachTaskService.getTeachTasksByIds(taskIds);
        ElectInitParams setting = new ElectInitParams();
        setting.setScope(null);
        setting.setIsElectable(true);
        setting.setUpdateStdMaxCountByRoomConfig(true);
        electStdScopeService.batchUpdateTasksElectInfo(tasks, setting, null);
        return redirect(request, "maxStdCountCheck", "info.set.success");
    }
    
    /**
     * 显示任务的选课范围
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
        Long taskId = getLong(request, "taskId");
        TeachTask task = teachTaskService.getTeachTask(taskId);
        request.setAttribute("task", task);
        List scopes = new ArrayList();
        for (Iterator iter = task.getElectInfo().getElectScopes().iterator(); iter.hasNext();) {
            ElectStdScope scope = (ElectStdScope) iter.next();
            Map scopeMap = new HashMap();
            scopeMap.put("enrollTurns", scope.getEnrollTurns());
            scopeMap.put("stdTypes", utilService.load(StudentType.class, "id", SeqStringUtil
                    .transformToLong(scope.getStdTypeIds())));
            scopeMap.put("departs", utilService.load(Department.class, "id", SeqStringUtil
                    .transformToLong(scope.getDepartIds())));
            scopeMap.put("specialities", utilService.load(Speciality.class, "id", SeqStringUtil
                    .transformToLong(scope.getSpecialityIds())));
            scopeMap.put("aspects", utilService.load(SpecialityAspect.class, "id", SeqStringUtil
                    .transformToLong(scope.getAspectIds())));
            scopeMap.put("adminClasses", utilService.load(AdminClass.class, "id", SeqStringUtil
                    .transformToLong(scope.getAdminClassIds())));
            scopeMap.put("startNo", scope.getStartNo());
            scopeMap.put("endNo", scope.getEndNo());
            scopes.add(scopeMap);
        }
        addCollection(request, "scopes", scopes);
        return forward(request);
    }
    
    /**
     * 保存教学任务设置范围
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Integer newOrModifies = getInteger(request, "newOrModifies");
        String taskId = request.getParameter(Constants.TEACHTASK_KEY);
        // 先行保存样板任务的选课信息
        if (StringUtils.isEmpty(taskId) || newOrModifies == null) {
            return forwardError(mapping, request, new String[] { "entity.teachTask",
                    "error.model.id.needed" });
        }
        TeachTask task = teachTaskService.loadTeachTask(Long.valueOf(taskId));
        task.getElectInfo().setMaxStdCount(getInteger(request, "task.electInfo.maxStdCount"));
        task.getElectInfo().setMinStdCount(getInteger(request, "task.electInfo.minStdCount"));
        teachTaskService.updateTeachTask(task);
        if (null == task) {
            return forwardError(mapping, request, new String[] { "entity.teachTask",
                    "error.model.notExist" });
        }
        Enumeration paramNames = request.getParameterNames();
        // 获得要保存和更新的选课范围
        for (int i = 1; i <= newOrModifies.intValue(); i++) {
            boolean hasThis = false;
            while (paramNames.hasMoreElements()) {
                String scopeName = (String) paramNames.nextElement();
                if (scopeName.indexOf(Constants.ELECTSTDSCOPE + i) == 0) {
                    hasThis = true;
                    break;
                }
            }
            if (hasThis) {
                ElectStdScope scope = (ElectStdScope) populate(request, ElectStdScope.class,
                        Constants.ELECTSTDSCOPE + i);
                scope.setTask(task);
                if (ValidElectStdScopePredicate.getInstance().evaluate(scope)) {
                    utilService.saveOrUpdate(scope);
                }
            }
        }
        // 获得要删除的选课范围
        String deleteScopeIdSeq = request.getParameter("deleteScopeIds");
        if (StringUtils.isNotEmpty(deleteScopeIdSeq)) {
            String[] deleteScopeIds = StringUtils.split(deleteScopeIdSeq, ",");
            utilService.remove(ElectStdScope.class, "id", SeqStringUtil
                    .transformToLong(deleteScopeIds));
        }
        // 更新选择的教学任务
        String taskIdSeq = request.getParameter(Constants.TEACHTASK_KEYSEQ);
        String[] taskIds = StringUtils.split(taskIdSeq, ",");
        String myId = task.getId().toString();
        
        for (int i = 0; i < taskIds.length; i++) {
            if (!taskIds[i].equals(myId)) {
                TeachTask other = teachTaskService.loadTeachTask(Long.valueOf(taskIds[i]));
                if (null != other) {
                    other.getElectInfo().setMaxStdCount(task.getElectInfo().getMaxStdCount());
                    other.getElectInfo().setMinStdCount(task.getElectInfo().getMinStdCount());
                    other.getElectInfo().getElectScopes().addAll(
                            task.getElectInfo().getElectScopes());
                    teachTaskService.updateTeachTask(other);
                }
            }
        }
        return redirect(request, "taskList", "info.save.success");
    }
    
    private List getTaskSearched(HttpServletRequest request, Boolean updateSelected) {
        List tasks = null;
        if (updateSelected.equals(Boolean.TRUE)) {
            Long[] taskIds = SeqStringUtil.transformToLong(request.getParameter("taskIds"));
            if (taskIds != null)
                tasks = teachTaskService.getTeachTasksByIds(taskIds);
        } else {
            EntityQuery query = teachTaskSearchHelper.buildTaskQuery(request, Boolean.TRUE);
            query.setLimit(null);
            tasks = (List) utilService.search(query);
        }
        return tasks;
    }
    
    /**
     * @param teachTaskService
     *            The teachTaskService to set.
     */
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    /**
     * @param classService
     *            The classService to set.
     */
    public void setClassService(AdminClassService classService) {
        this.classService = classService;
    }
    
    /**
     * @param specialityService
     *            The specialityService to set.
     */
    public void setSpecialityService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
    
    /**
     * @param electService
     *            The electService to set.
     */
    public void setElectStdScopeService(ElectStdScopeService electStdScopeService) {
        this.electStdScopeService = electStdScopeService;
    }
    
    public void setTeachTaskSearchHelper(TeachTaskSearchHelper teachTaskSearchHelper) {
        this.teachTaskSearchHelper = teachTaskSearchHelper;
    }
    
}
