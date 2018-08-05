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

package com.shufe.web.action.duty;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.AttendanceType;
import com.ekingstar.eams.system.baseinfo.model.StudentType;
import com.ekingstar.eams.system.time.CourseUnit;
import com.shufe.dao.course.task.TeachTaskFilterCategory;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.duty.DutyRecord;
import com.shufe.model.duty.RecordDetail;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;

public class DutyRecordManagerByTeacher extends DutyRecordSupportAction {
    
    protected InputDutyRecord inputDutyRecord;
    
    protected DutyRecordManager dutyRecordManager;
    
    public ActionForward maintainStudentRecordByTeachTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Teacher teacher = getTeacherFromSession(request.getSession());
        Long teachTaskId = getLong(request, "teachTaskId");
        TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
        Results.addObject("teachTask", teachTask);
        List recordList = null;
        if (checkAuthorityWithCalendar(teacher, teachTask)) {
            recordList = utilService.load(DutyRecord.class, "teachTask.id", teachTaskId);
        } else {
            return this.forwardError(mapping, request, "error.notEnoughAuthority");
        }
        Results.addObject("recordList", recordList);
        return this.forward(mapping, request, "recordList");
    }
    
    public ActionForward listStudentRecordByTeachTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Teacher teacher = getTeacherFromSession(request.getSession());
        Long teachTaskId = getLong(request, "teachTaskId");
        TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
        Results.addObject("teachTask", teachTask);
        List recordList = null;
        if (checkAuthority(teacher, teachTask)) {
            recordList = utilService.load(DutyRecord.class, "teachTask.id", teachTaskId);
        } else {
            return this.forwardError(mapping, request, "error.notEnoughAuthority");
        }
        Results.addObject("recordList", recordList);
        return this.forward(request);
    }
    
    public ActionForward managerByTeacherFrame(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * Teacher teacher = (Teacher) utilService.loadByKey(Teacher.class, "code",
         * this.getUserFromSession(request.getSession()) .getName()).get(0);
         */
        Teacher teacher = getTeacherFromSession(request.getSession());
        List stdTypeList = teachTaskService.getStdTypesForTeacher(teacher);
        Results.addObject("stdTypeList", stdTypeList);
        return this.forward(request);
    }
    
    public ActionForward managerByTeacherForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("currentdate", new java.sql.Date(System.currentTimeMillis()));
        /*
         * Teacher teacher = (Teacher) utilService.loadByKey(Teacher.class, "code",
         * this.getUserFromSession(request.getSession()) .getName()).get(0);
         */
        Teacher teacher = getTeacherFromSession(request.getSession());
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        String year = request.getParameter("calendar.year");
        String term = request.getParameter("calendar.term");
        TeachCalendar calendar = null;
        if (StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(term)) {
            calendar = getTeachCalendarService().getTeachCalendar(
                    (StudentType) EntityUtils.getEntity(StudentType.class, stdTypeId), year, term);
        } else {
            /*
             * return forward(mapping, request, "error.parameters.needed", "error");
             */
        }
        if (null == calendar) {
            /*
             * return forward(mapping, request, "error.model.notExist", "error");
             */
            Results.addObject("teachTaskList", Collections.EMPTY_LIST);
        } else {
            Results.addObject("teachTaskList", getTaskService().getTeachTasksByCategory(
                    teacher.getId(), TeachTaskFilterCategory.TEACHER, calendar));
        }
        return this.forward(request);
    }
    
    public ActionForward updateDutyRecordForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Teacher teacher = getTeacherFromSession(request.getSession());
        String dutyDate = request.getParameter("recordDetail.dutyDate");
        Long beginUnitId = getLong(request, "recordDetail.beginUnit.id");
        Long endUnitId = getLong(request, "recordDetail.endUnit.id");
        Long teachTaskId = getLong(request, "teachTaskId");
        List studentDutyRecordDetailsList = dutyService.loadStudentDutyRecordDetails(teachTaskId,
                dutyDate, beginUnitId, endUnitId);
        TeachTask teachTask = new TeachTask();
        if (studentDutyRecordDetailsList.size() >= 1) {
            teachTask = ((RecordDetail) studentDutyRecordDetailsList.get(0)).getDutyRecord()
                    .getTeachTask();
        }
        if (!checkAuthorityWithCalendar(teacher, teachTask)) {
            return this.forwardError(mapping, request, "error.notEnoughAuthority");
        }
        Results.addObject("teachTask", teachTask);
        request.setAttribute("dutyDate", dutyDate);
        request.setAttribute("teachTaskId", teachTaskId);
        request.setAttribute("studentDutyRecordDetailsList", studentDutyRecordDetailsList);
        Results.addObject("dutyStatusList", utilService.loadAll(AttendanceType.class));
        // TODO 替代为该教学任务的小节安排
        List courseUnitList = utilService.loadAll(CourseUnit.class);
        Results.addObject("courseUnitList", courseUnitList);
        Results.addObject("beginUnitId", beginUnitId);
        Results.addObject("endUnitId", endUnitId);
        return this.forward(mapping, request, "dutyRecordUpdateForm");
    }
    
    public ActionForward inputForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Results.addObject("teachTask", utilService.load(TeachTask.class, Long.valueOf(request
                .getParameter("teachTaskId"))));
        Results.addObject("dutyStatusList", utilService.loadAll(AttendanceType.class));
        return this.forward(mapping, request, "inputForm");
    }
    
    public ActionForward input(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long teachTaskId = getLong(request, "teachTaskId");
        TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
        Teacher teacher = getTeacherFromSession(request.getSession());
        if (!checkAuthorityWithCalendar(teacher, teachTask)) {
            return this.forwardError(mapping, request, "error.notEnoughAuthority");
        }
        return inputDutyRecord.input(mapping, form, request, response);
    }
    
    public ActionForward updateDutyRecordDetails(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dutyDate = request.getParameter("recordDetail.dutyDate");
        String beginUnitIdString = request.getParameter("recordDetail.beginUnit.id");
        String endUnitIdString = request.getParameter("recordDetail.endUnit.id");
        String oldDutyDate = request.getParameter("oldDutyDate");
        String oldBeginUnit = request.getParameter("oldBeginUnit");
        String oldEndUnit = request.getParameter("oldEndUnit");
        String allRecordStudent = request.getParameter("stdId");
        Long[] stdIdArray = SeqStringUtil.transformToLong(allRecordStudent);
        Long teachTaskId = Long.valueOf(request.getParameter("teachTaskId"));
        if (oldDutyDate.equals(dutyDate) && oldBeginUnit.equals(beginUnitIdString)
                && oldEndUnit.equals(endUnitIdString)) {
            
        } else {
            for (int i = 0; i < stdIdArray.length; i++) {
                dutyService.deleteRecordDetail(stdIdArray[i], teachTaskId, java.sql.Date
                        .valueOf(oldDutyDate), new Long(oldBeginUnit), new Long(oldEndUnit));
            }
        }
        for (int i = 0; i < stdIdArray.length; i++) {
            String dutyStatusStdNo = request.getParameter("dutyStatus" + stdIdArray[i]);
            if (StringUtils.isNotBlank(dutyStatusStdNo)) {
                dutyService.updateOrSaveRecordDetail(stdIdArray[i], teachTaskId, java.sql.Date
                        .valueOf(dutyDate), new Long(beginUnitIdString), new Long(endUnitIdString),
                        new Long(dutyStatusStdNo));
            } else {
                dutyService.deleteRecordDetail(stdIdArray[i], teachTaskId, java.sql.Date
                        .valueOf(dutyDate), new Long(beginUnitIdString), new Long(endUnitIdString));
            }
            
        }
        request.setAttribute("teachTaskId", teachTaskId);
        return this.redirect(request, "managerByTeacherForm", "info.action.success");
    }
    
    public ActionForward updateRecord(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        updateDetailOfRecord(request);
        
        Long stdId = getLong(request, "stdId");
        request.setAttribute("stdId", stdId);
        String from = request.getParameter("from");
        if (from.equals("dutyRecordList")) {
            request.setAttribute("actionName", "dutyRecordManagerWithTeacher.do");
            request.setAttribute("methodValue", "maintainStudentRecordByTeachTask");
        } else {
            request.setAttribute("actionName", "dutyRecordSearch.do");
            request.setAttribute("methodValue", "manageByStudentForm");
        }
        return mapping.findForward("recordUpdateRedirector");
    }
    
    public ActionForward maintainRecordByTeachTaskForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        Long teachTaskId = getLong(request, "teachTaskId");
        TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
        Teacher teacher = getTeacherFromSession(request.getSession());
        if (!checkAuthorityWithCalendar(teacher, teachTask)) {
            return this.forwardError(mapping, request, "error.notEnoughAuthority");
        }
        Results.addObject("teachTask", teachTask);
        return this.forward(mapping, request, "maintainRecordByTeachTaskForm");
    }
    
    public ActionForward recordUpdateForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long dutyRecordId = getLong(request, "recordId");
        List recordDetailList = utilService.load(RecordDetail.class, "dutyRecord.id",
                new Object[] { dutyRecordId });
        DutyRecord dutyRecord = (DutyRecord) utilService.load(DutyRecord.class, dutyRecordId);
        Teacher teacher = getTeacherFromSession(request.getSession());
        if (!checkAuthorityWithCalendar(teacher, dutyRecord.getTeachTask())) {
            return this.forwardError(mapping, request, "error.notEnoughAuthority");
        }
        Long stdId = getLong(request, "stdId");
        request.setAttribute("stdId", stdId);
        String recordId = request.getParameter("recordId");
        request.setAttribute("recordId", recordId);
        String dutyCount = request.getParameter("dutyCount");
        request.setAttribute("dutyCount", dutyCount);
        String totalCount = request.getParameter("totalCount");
        request.setAttribute("totalCount", totalCount);
        String teachTaskId = request.getParameter("teachTaskId");
        request.setAttribute("teachTaskId", teachTaskId);
        String from = request.getParameter("from");
        request.setAttribute("from", from);
        Results.addObject("recordDetailList", recordDetailList);
        Results.addObject("dutyRecord", dutyRecord);
        Results.addObject("dutyStatusList", utilService.loadAll(AttendanceType.class));
        return this.forward(mapping, request, "recordUpdateForm");
    }
    
    public ActionForward maintainRecordByTeachTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        return inputDutyRecord.maintainRecordByTeachTask(mapping, form, request, response);
        
    }
    
    public ActionForward doMaintainRecordByTeachTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        return inputDutyRecord.doMaintainRecordByTeachTask(mapping, form, request, response);
    }
    
    /**
     * 检测指定教师对指定教学任务在此时的管理权限
     * 
     * @param teacher
     * @param teachTask
     * @return
     */
    private boolean checkAuthorityWithCalendar(Teacher teacher, TeachTask teachTask) {
        return checkAuthority(teacher, teachTask)
                && teachTask.getCalendar().contains(new Date(System.currentTimeMillis()));
        
    }
    
    public ActionForward exportData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forward(request, new Action("studentFile", "exportDutyRecordExcel"));
    }
    
    /**
     * 检测指定教师对指定教学任务的权限
     * 
     * @param teacher
     * @param teachTask
     * @return
     */
    private boolean checkAuthority(Teacher teacher, TeachTask teachTask) {
        return teachTask.getArrangeInfo().getTeachers().contains(teacher);
    }
    
    /**
     * @param dutyRecordManager
     *            要设置的 dutyRecordManager
     */
    public void setDutyRecordManager(DutyRecordManager dutyRecordManager) {
        this.dutyRecordManager = dutyRecordManager;
    }
    
    /**
     * @param inputDutyRecord
     *            要设置的 inputDutyRecord
     */
    public void setInputDutyRecord(InputDutyRecord inputDutyRecord) {
        this.inputDutyRecord = inputDutyRecord;
    }
    
}
