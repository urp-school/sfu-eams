//$Id: TeachWorkloadAction.java,v 1.48 2007/01/22 03:20:21 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.workload.course.TeachCategory;
import com.shufe.model.workload.course.TeachModulus;
import com.shufe.model.workload.course.TeachWorkload;
import com.shufe.model.workload.course.TeachWorkloadAlteration;
import com.shufe.model.workload.course.TeachWorkloadStatus;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.service.workload.ModulusService;
import com.shufe.service.workload.course.TeachWorkloadImporterListener;

public class TeachWorkloadAction extends TeachWorkloadSearchAction {
    
    private TeachTaskService teachTaskService;
    
    private TeachCalendarService teachCalendarService;
    
    private ModulusService teachModulusService;
    
    
    /**
     * @param teachTaskService
     *            The teachTaskService to set.
     */
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    /**
     * @param teachCalendarService
     *            The teachCalendarService to set.
     */
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    /**
     * @param teachModulusService
     *            The teachModulusService to set.
     */
    public void setTeachModulusService(ModulusService teachModulusService) {
        this.teachModulusService = teachModulusService;
    }
    
    /**
     * 得到统计页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward statHome(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        String year = get(request, "calendar.year");
        String term = get(request, "calendar.term");
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(stdTypeId, year, term);
        String stdTypeIdSeq = getStdTypeIdSeq(request);
        if (null == teachCalendar) {
            List stdTypes = studentTypeService.getStudentTypes(stdTypeIdSeq);
            teachCalendar = teachCalendarService.getNearestCalendar((StudentType) stdTypes.get(0));
        }
        StudentType studentType = teachCalendar.getStudentType();
        request.setAttribute("calendar", teachCalendar);
        if (null != stdTypeId) {
            studentType = studentTypeService.getStudentType(stdTypeId);
            request.setAttribute("stdType", studentType);
        }
        List stdTypes = studentType.getDescendants();
        stdTypes.add(studentType);
        List filterStdTypes = new ArrayList();
        for (Iterator iter = stdTypes.iterator(); iter.hasNext();) {
            StudentType element = (StudentType) iter.next();
            if (("," + stdTypeIdSeq + ",").indexOf("," + element.getId() + ",") != -1) {
                filterStdTypes.add(element);
            }
        }
        request.setAttribute("stdTypes", filterStdTypes);
        setDataRealm(request, hasStdTypeDepart);
        return forward(request);
    }
    
    /**
     * 统计功能
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward statistic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String departIdSeq = get(request, "departIdSeq");
        String stdTypeIdSeq = get(request, "stdTypeIdSeq");
        String calendarIdSeq = get(request, "calendarIdSeq");
        Map resultMap = teachWorkloadService.statTeachWorkload(stdTypeIdSeq, departIdSeq,
                calendarIdSeq);
        request.setAttribute("statisticSuccess", resultMap.get("successNum"));
        request.setAttribute("statisticTasks", resultMap.get("statisticTaskNum"));
        if (resultMap.containsKey("noArrangeInfos")) {
            request.setAttribute("noArrangeInfos", resultMap.get("noArrangeInfos"));
        }
        request.setAttribute("noArrangeInfo", resultMap.get("noArrangeInfoNum"));
        if (resultMap.containsKey("noTeachModulus")) {
            request.setAttribute("noTeachModulus", resultMap.get("noTeachModulus"));
        }
        request.setAttribute("noModuleNum", resultMap.get("noModuleNum"));
        return forward(request);
    }
    
    /**
     * 保存一个手工添加的教学工作量.
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
        String flag = get(request, "flag");
        if (StringUtils.isEmpty(flag)) {
            request.setAttribute("departmentList", departmentService
                    .getColleges(getDepartmentIdSeq(request)));
            return forward(request, "form");
        }
        String teachTaskId = get(request, "teachTeakId");
        TeachTask teachTask = (TeachTask) utilService.get(TeachTask.class, Long
                .valueOf(teachTaskId));
        String teacherId = get(request, "teacherId");
        Teacher teacher = (Teacher) utilService.get(Teacher.class, Long.valueOf(teacherId));
        List teachModulus = utilService.loadAll(TeachModulus.class);
        List categorys = utilService.loadAll(TeachCategory.class);
        TeachWorkload teachWorkload = teachWorkloadService.buildTeachWorkload(teacher,
                teachTask, categorys, teachModulus);
        String workloadValue = get(request, "workloadValue");
        String remark = get(request, "remark");
        teachWorkload.setTotleWorkload(Float.valueOf(workloadValue));
        teachWorkload.setRemark(remark);
        // 标示手工输入
        teachWorkload.setIsHandInput(Boolean.TRUE);
        utilService.saveOrUpdate(teachWorkload);
        return redirect(request, "search", "info.add.success");
    }
    
    /**
     * 根据查巡条件得到对应的教学任务.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward taskList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        TeachTask teachTask = new TeachTask();
        String departId = get(request, "departmentSeq");
        if (StringUtils.isNotEmpty(departId)) {
            teachTask.getArrangeInfo().getTeachDepart().setId(Long.valueOf(departId));
        }
        String courseName = get(request, "courseName");
        if (StringUtils.isNotEmpty(courseName)) {
            teachTask.getCourse().setName(courseName);
        }
        String kcxh = get(request, "courseSeq");
        if (StringUtils.isNotEmpty(kcxh)) {
            teachTask.setSeqNo(kcxh);
        }
        Pagination pagination = teachTaskService.getTeachTasksOfTeachDepart(teachTask,
                getStdTypeIdSeq(request), getDepartmentIdSeq(request), getPageNo(request),
                getPageSize(request));
        Results.addPagination("pagination", pagination);
        return forward(request);
    }
    
    /**
     * 得到更新的页面信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        TeachWorkload teachWorkload = (TeachWorkload) utilService.get(TeachWorkload.class, getLong(
                request, "teachWorkloadId"));
        request.setAttribute("teachWorkload", teachWorkload);
        request.setAttribute("teachModulus", teachModulusService.getModulus(new TeachModulus(),
                getStdTypeIdSeq(request)));
        request.setAttribute("departmentList", departmentService
                .getDepartments(getDepartmentIdSeq(request)));
        return forward(request);
    }
    
    /**
     * 跟新一个统计的工作量
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String name = getUser(request.getSession()).getName();
        
        TeachWorkload teachWorkload = (TeachWorkload) utilService.get(TeachWorkload.class, getLong(
                request, "teachWorkloadId"));
        // 记录修改前的内容
        TeachWorkloadStatus workloadBefore = buildWorkloadStatus(teachWorkload, Boolean.FALSE);
        Map modifyMap = getParams(request, "teachWorkload");
        EntityUtils.populate(modifyMap, teachWorkload);
        String modifyPerson = teachWorkload.getModifyPerson();
        if (null != modifyPerson && modifyPerson.length() > 450) {
            teachWorkload.setModifyPerson(name);
        } else {
            modifyPerson = modifyPerson + "," + name;
            teachWorkload.setModifyPerson(modifyPerson);
        }
        utilService.saveOrUpdate(teachWorkload);
        
        // 记录修改后的内容
        TeachWorkloadStatus workloadAfter = buildWorkloadStatus(teachWorkload, Boolean.TRUE);
        // 保存记录变动的信息
        TeachWorkloadAlteration workloadLog = new TeachWorkloadAlteration();
        workloadLog.setTask(teachWorkload.getTeachTask());
        workloadLog.setTeacher(teachWorkload.getTeacherInfo().getTeacher());
        workloadLog.setIsHandInput(teachWorkload.getIsHandInput());
        workloadLog.setCourseCategory(teachWorkload.getCourseCategory());
        workloadLog.setTeacherAffirm(teachWorkload.getTeacherAffirm());
        workloadLog.setCollegeAffirm(teachWorkload.getCollegeAffirm());
        workloadLog.setAlterBefore(workloadBefore);
        workloadLog.setAlterAfter(workloadAfter);
        workloadLog.setWorkloadBy(getUser(request.getSession()));
        workloadLog.setWorkloadFrom(RequestUtils.getIpAddr(request));
        workloadLog.setWorkloadAt(new Date());
        utilService.saveOrUpdate(workloadLog);
        
        return redirect(request, "search", "field.evaluate.updateSuccess");
    }
    
    /**
     * 组建记录变动的状态
     * 
     * @param teachWorkload
     * @param isAfter
     * @return
     */
    protected TeachWorkloadStatus buildWorkloadStatus(TeachWorkload teachWorkload, Boolean isAfter) {
        TeachWorkloadStatus workloadStatus = new TeachWorkloadStatus();
        workloadStatus.setStudentNumber(teachWorkload.getStudentNumber());
        if (Boolean.TRUE.equals(isAfter)) {
            TeachModulus modulus = (TeachModulus) utilService.load(TeachModulus.class,
                    teachWorkload.getTeachModulus().getId());
            workloadStatus.setModulus(modulus);
        } else {
            workloadStatus.setModulus(teachWorkload.getTeachModulus());
        }
        workloadStatus.setWeeks(teachWorkload.getWeeks());
        workloadStatus.setTotleCourses(teachWorkload.getTotleCourses());
        workloadStatus.setClassNumberOfWeek(teachWorkload.getClassNumberOfWeek());
        workloadStatus.setTotleWorkload(teachWorkload.getTotleWorkload());
        workloadStatus.setPayReward(teachWorkload.getPayReward());
        workloadStatus.setCalcWorkload(teachWorkload.getCalcWorkload());
        workloadStatus.setRemark(teachWorkload.getRemark());
        return workloadStatus;
    }
    
    /**
     * 批量删除几个教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List teachWorkloads = utilService.load(TeachWorkload.class, "id", SeqStringUtil
                .transformToLong(get(request, "teachWorkloadIds")));
        utilService.remove(teachWorkloads);
        return redirect(request, "search", "system.operation.success");
    }
    
    /**
     * 得到添加无教学任务的工作量页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addWorkloadNoTaskForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdType);
        request.setAttribute("departmentList", departmentService.getAllDepartments());
        request.setAttribute("openColleges", departmentService
                .getDepartments(getDepartmentIdSeq(request)));
        request.setAttribute("teachModulus", teachModulusService.getModulus(new TeachModulus(),
                getStdTypeIdSeq(request)));
        request.setAttribute("courseTypes", baseCodeService.getCodes(CourseType.class));
        return forward(request);
    }
    
    /**
     * 保存无任务的工作量
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveWorkloadNoTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Teacher teacher = (Teacher) utilService.get(Teacher.class, getLong(request, "teacherIds"));
        TeachWorkload teachWorkload = new TeachWorkload(teacher);
        // 添加教学日历
        Long stdTypeId = getLong(request, "teachWorkload.studentType.id");
        String year = get(request, "year");
        String term = get(request, "term");
        teachWorkload
                .setTeachCalendar(teachCalendarService.getTeachCalendar(stdTypeId, year, term));
        // 添加课程种类
        Long teachModuluId = getLong(request, "teachWorkload.teachModulus.id");
        TeachModulus teachModulus = (TeachModulus) utilService.get(TeachModulus.class,
                teachModuluId);
        teachWorkload.setCourseCategory(teachModulus.getCourseCategory());
        teachWorkload.setIsHandInput(Boolean.TRUE);
        teachWorkload.setCollegeAffirm(Boolean.TRUE);
        // 导入页面数据
        EntityUtils.populate(RequestUtils.getParams(request, "teachWorkload"), teachWorkload);
        EntityUtils.evictEmptyProperty(teachWorkload);
        utilService.saveOrUpdate(teachWorkload);
        return redirect(request, "search", "system.operation.success");
    }
    
    /**
     * 导入工作量
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransferResult tr = new TransferResult();
        Transfer transfer = ImporterServletSupport.buildEntityImporter(request,
                TeachWorkload.class, tr);
        if (null == transfer) {
            return forward(request, "/pages/component/importData/error");
        }
        
        TeachWorkloadImporterListener workloadListener = new TeachWorkloadImporterListener();
        workloadListener.setUtilService(utilService);
        workloadListener.setTeachCalendarDAO(teachCalendarService.getTeachCalendarDAO());
        // foreignerListener.addForeigerKey("code");
        transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
                workloadListener);
        transfer.transfer(tr);
        
        if (tr.hasErrors()) {
            return forward(request, "/pages/components/importData/error");
        } else {
            return redirect(request, "search", "info.import.success");
        }
    }
}
