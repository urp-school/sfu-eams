//$Id: DutyRecordManager.java,v 1.35 2007/01/23 03:56:37 yd Exp $
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
 * pippo             2005-11-30         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.duty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;
import org.apache.commons.collections.functors.EqualPredicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberRange;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.BaseCode;
import com.ekingstar.eams.system.basecode.industry.AttendanceType;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.CalendarNotExistException;
import com.ekingstar.eams.system.time.CourseUnit;
import com.shufe.model.Constants;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.duty.DutyRecord;
import com.shufe.model.duty.DutyRecordStatisticsCourse;
import com.shufe.model.duty.DutyRecordStatisticsStatus;
import com.shufe.model.duty.DutyRecordStatisticsStd;
import com.shufe.model.duty.RecordDetail;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.predicate.BeanPredicateWrapper;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.util.RequestUtil;

/**
 * @author dell
 */
public class DutyRecordManager extends DutyRecordSupportAction {
    
    private AdminClassService adminClassService;
    
    /**
     * 检索教学任务
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward manager(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.searchTeachTask(form, request);
        Results.addObject("courseTypeList", utilService.loadAll(CourseType.class));
        return this.forward(mapping, request, "search");
    }
    
    /**
     * 列出某一教学任务的所有考勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward maintainRecordByTeachTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        Long teachTaskId = Long.valueOf(request.getParameter("teachTaskId"));
        if (null == teachTaskId) {
            teachTaskId = (request.getAttribute("teachTaskId") == null) ? (new Long(0))
                    : ((Long) request.getAttribute("teachTaskId"));
        }
        TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
        Results.addObject("teachTask", teachTask);
        List recordList = utilService.load(DutyRecord.class, "teachTask.id",
                new Object[] { teachTaskId });
        Results.addObject("recordList", recordList);
        return this.forward(mapping, request, "recordList");
    }
    
    /**
     * 列出某一教学任务的所有考勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward modifyRecordByTeachTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        Long teachTaskId = (request.getParameter("teachTaskId") == null) ? null : (Long
                .valueOf(request.getParameter("teachTaskId")));
        if (null == teachTaskId) {
            teachTaskId = (request.getAttribute("teachTaskId") == null) ? (new Long(0))
                    : ((Long) request.getAttribute("teachTaskId"));
        }
        TeachTask teachTask = (TeachTask) utilService.load(TeachTask.class, teachTaskId);
        List dutyRecordDetailBatchList = dutyService
                .loadDutyRecordDetailsByTeachTaskId(teachTaskId);
        Results.addObject("teachTaskId", teachTaskId);
        Results.addObject("teachTask", teachTask);
        Results.addObject("dutyRecordDetailBatchList", dutyRecordDetailBatchList);
        return this.forward(mapping, request, "dutyRecordBatchManage");
    }
    
    /**
     * 修改学生出勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward recordUpdateForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long dutyRecordId = Long.valueOf(request.getParameter("recordId"));
        List recordDetailList = utilService.load(RecordDetail.class, "dutyRecord.id",
                new Object[] { dutyRecordId });
        DutyRecord dutyRecord = (DutyRecord) utilService.load(DutyRecord.class, dutyRecordId);
        String from = request.getParameter("from");
        request.setAttribute("from", from);
        Results.addObject("recordDetailList", recordDetailList);
        Results.addObject("dutyRecord", dutyRecord);
        Results.addObject("dutyStatusList", utilService.loadAll(AttendanceType.class));
        return this.forward(mapping, request, "recordUpdateForm");
    }
    
    /**
     * 更新单个学生考勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateRecord(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        updateDetailOfRecord(request);
        
        Long stdId = getLong(request, "stdId");
        request.setAttribute("stdId", stdId);
        String from = request.getParameter("from");
        if (from.equals("dutyRecordList")) {
            request.setAttribute("actionName", "dutyRecordManager.do");
            request.setAttribute("methodValue", "maintainRecordByTeachTask");
        } else {
            request.setAttribute("actionName", "dutyRecordSearch.do");
            request.setAttribute("methodValue", "manageByStudentForm");
        }
        return mapping.findForward("recordUpdateRedirector");
    }
    
    /**
     * 进入更新单个学生考勤记录页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateDutyRecordForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
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
    
    /**
     * 批量删除一个教学任务的一次考勤
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward deleteDutyRecord(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String dutyDate = request.getParameter("recordDetail.dutyDate");
        Long beginUnitId = getLong(request, "recordDetail.beginUnit.id");
        Long endUnitId = getLong(request, "recordDetail.endUnit.id");
        Long teachTaskId = Long.valueOf(request.getParameter("teachTaskId"));
        List studentDutyRecordDetailsList = dutyService.loadStudentDutyRecordDetails(teachTaskId,
                dutyDate, beginUnitId, endUnitId);
        for (Iterator iter = studentDutyRecordDetailsList.iterator(); iter.hasNext();) {
            RecordDetail element = (RecordDetail) iter.next();
            dutyService.deleteRecordDetail(element.getId());
        }
        request.setAttribute("teachTaskId", teachTaskId);
        String from = request.getParameter("from");
        if (((from == null) ? "" : from).equals("inputDutyRecord")) {
            request.setAttribute("flag", "search");
            return this.forward(mapping, request, "fromInputRecordUpdateSuccess");
        }
        return this.forward(mapping, request, "recordUpdateSuccess");
    }
    
    /**
     * 批量修改一个教学任务的一次考勤
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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
        String actionName = request.getParameter("actionName");
        if (((actionName == null) ? "" : actionName).equals("dutyRecordManagerWithTeacher.do")) {
            return this.redirect(request, "managerByTeacherForm", "flag=search");
        }
        String from = request.getParameter("from");
        if (((from == null) ? "" : from).equals("inputDutyRecord")) {
            return this.redirect(request, new Action("inputDutyRecord", "searchTeachTask"),
                    "attr.updateSuccess", "flag=search");
        }
        return this.forward(mapping, request, "recordUpdateSuccess");
    }
    
    /**
     * 更新一个学生一门课一次考勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateDutyRecordDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long recordDetailId = getLong(request, "recordDetail.id");
        Long dutyStatusId = getLong(request, "recordDetail.dutyStatus.id");
        dutyService.updateRecordDetail(recordDetailId, dutyStatusId);
        return this.redirect(request, new Action("dutyRecordSearch", "manageByStudentDateForm"),
                "attr.updateSuccess", request.getParameter("params"));
    }
    
    /**
     * 删除一个学生一门课一次考勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteRecordDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long recordDetailId = getLong(request, "recordDetail.id");
        dutyService.deleteRecordDetail(recordDetailId);
        return this.redirect(request, new Action("dutyRecordSearch", "manageByStudentDateForm"),
                "info.delete.success", request.getParameter("params"));
    }
    
    /**
     * 进入考勤统计主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward dutyRecordStatisticsFrame(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        return this.forward(mapping, request, "dutyRecordStatisticsFrame");
    }
    
    /**
     * 进入以学生为单位进行考勤统计界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentStatisticsForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(request.getParameter("flag"))
                || !request.getParameter("flag").equals("doStatistics")) {
            List stdTypeList = studentTypeService.getStudentTypes(getStdTypeIdSeq(request));
            String departmentIds = getDepartmentIdSeq(request);
            if (StringUtils.isNotEmpty(departmentIds))
                Results.addObject("departmentList", departmentService.getColleges(departmentIds));
            else
                Results.addObject("departmentList", Collections.EMPTY_SET);
            Collections.sort(stdTypeList, new PropertyComparator("code"));
            Results.addObject("stdTypeList", stdTypeList);
            return this.forward(mapping, request, "studentStatisticsForm");
        } else {
            return studentRecordStatistics(mapping, form, request, response);
        }
    }
    
    /**
     * 进入以行政班为单位进行考勤统计界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward adminClassStatisticsForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(request.getParameter("flag"))
                || !request.getParameter("flag").equals("doStatistics")) {
            List stdTypeList = studentTypeService.getStudentTypes(getStdTypeIdSeq(request));
            String departmentIds = getDepartmentIdSeq(request);
            if (StringUtils.isNotEmpty(departmentIds))
                Results.addObject("departmentList", departmentService.getColleges(departmentIds));
            else
                Results.addObject("departmentList", Collections.EMPTY_SET);
            Collections.sort(stdTypeList, new PropertyComparator("code"));
            Results.addObject("stdTypeList", stdTypeList);
            return this.forward(mapping, request, "adminClassStatisticsForm");
        } else {
            return studentRecordStatistics(mapping, form, request, response);
        }
    }
    
    /**
     * FIXME 以学生为单位进行考勤统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentRecordStatistics(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String adminClasssIdString = request.getParameter("adminClasssId1");
        AdminClass adminClass = null;
        if (StringUtils.isNotEmpty(adminClasssIdString)) {
            adminClass = new AdminClass(Long.valueOf(adminClasssIdString));
        }
        Student student = (Student) RequestUtil.populate(request, Student.class, "student");
        Set adminClassSet = new HashSet();
        adminClassSet.add(adminClass);
        student.setAdminClasses(adminClassSet);
        TeachCalendar example = (TeachCalendar) RequestUtil.populate(request, TeachCalendar.class,
                "calendar");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(example.getStudentType(),
                example.getYear(), example.getTerm());
        if (null == calendar) {
            throw new RuntimeException("no calendar found!\n");
        }
        if (StringUtils.isEmpty(adminClasssIdString)) {
            student.setType(example.getStudentType());
        }
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        List studentIdList = new ArrayList();
        if (StringUtils.isEmpty(adminClasssIdString)) {
            studentIdList = studentService.searchStudentId(student, studentTypeIds, departmentIds);
        } else {
            studentIdList = studentService.searchStudentId(student);
        }
        Long[] stdIds = new Long[studentIdList.size()];
        int limit = 500;
        if (stdIds.length > limit) {
            return this.forwardError(mapping, request, "error.duty.studentDataOverflow",
                    new String[] { String.valueOf(limit) });
        }
        stdIds = (Long[]) studentIdList.toArray(stdIds);
        List calendarList = new ArrayList();
        calendarList.add(calendar);
        Predicate predicate = AndPredicate.getInstance(new BeanPredicateWrapper("state",
                new EqualPredicate(Boolean.TRUE)), new BeanPredicateWrapper("inSchool",
                new EqualPredicate(Boolean.TRUE)));
        
        String dateBegin = request.getParameter("dateBegin");
        String dateEnd = request.getParameter("dateEnd");
        Map partReslutMap = dutyService.dutyRecordStatistics(stdIds, calendarList, dateBegin,
                dateEnd, true, predicate);
        
        String totalDateBegin = request.getParameter("totalDateBegin");
        String totalDateEnd = request.getParameter("totalDateEnd");
        Map totalReslutMap = dutyService.dutyRecordStatistics(stdIds, calendarList, totalDateBegin,
                totalDateEnd, true, predicate);
        
        request.setAttribute("totalStd", DutyRecordStatisticsStd.totalNo);
        request.setAttribute("totalCourse", DutyRecordStatisticsCourse.totalId);
        request.setAttribute("courseTakeType", DutyRecordStatisticsCourse.courseTakeType);
        request.setAttribute("reStudy", CourseTakeType.RESTUDY);
        request.setAttribute("reExam", CourseTakeType.REEXAM);
        Results.addObject("totalResult", totalReslutMap.get("dutyRecordStatisticsMap"));
        Results.addObject("partResult", partReslutMap.get("dutyRecordStatisticsMap"));
        Results.addObject("stdMap", totalReslutMap.get("stdMap"));
        Map courseMap = (Map) totalReslutMap.get("courseMap");
        courseMap.putAll((Map) partReslutMap.get("courseMap"));
        Results.addObject("courseMap", courseMap);
        
        return this.forward(mapping, request, "stdDutyRecordStatistics");
    }
    
    /**
     * 以行政班为单位进行考勤统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward adminClassRecordStatistics(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        AdminClass adminClass = (AdminClass) RequestUtil.populate(request, AdminClass.class,
                "adminClass");
        
        TeachCalendar example = (TeachCalendar) RequestUtil.populate(request, TeachCalendar.class,
                "calendar");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(example.getStudentType(),
                example.getYear(), example.getTerm());
        if (null == calendar) {
            throw new RuntimeException("calendar");
        }
        List calendarList = new ArrayList();
        calendarList.add(calendar);
        adminClass.setStdType(example.getStudentType());
        String departmentIds = getDepartmentIdSeq(request);
        String studentTypeIds = getStdTypeIdSeq(request);
        List adminClassList = adminClassService.getAdminClasses(adminClass, studentTypeIds,
                departmentIds);
        int limit = 50;
        if (adminClassList.size() > limit) {
            return this.forwardError(mapping, request, "error.duty.adminClassDataOverflow",
                    new String[] { String.valueOf(limit) });
        }
        Map partReslutMap = new HashMap();
        Map totalReslutMap = new HashMap();
        Map adminClassMap = new HashMap();
        Map totalClassReslutMap = new HashMap();
        Integer weeks = calendar.getWeeks();
        int weekBegin = getWeekIndex(request, "weekBegin", 1);
        int weekEnd = getWeekIndex(request, "weekEnd", weeks.intValue());
        NumberRange weekRange = new NumberRange(new Integer(weekBegin), new Integer(weekEnd));
        int totalWeekBegin = getWeekIndex(request, "totalWeekBegin", 1);
        int totalWeekEnd = getWeekIndex(request, "totalWeekEnd", weeks.intValue());
        for (Iterator iter = adminClassList.iterator(); iter.hasNext();) {
            AdminClass adminClassElement = (AdminClass) iter.next();
            adminClassMap.put(String.valueOf(adminClassElement.getId()), adminClassElement);
            Set studentSet = adminClassElement.getStudents();
            if (CollectionUtils.isEmpty(studentSet)) {
                continue;
            }
            Long[] stdIdArray = new Long[studentSet.size()];
            int i = 0;
            for (Iterator iterator = studentSet.iterator(); iterator.hasNext();) {
                Student studentElement = (Student) iterator.next();
                stdIdArray[i] = studentElement.getId();
                i++;
            }
            studentSet = null;
            Map weekPartReslutMap = new HashMap();
            Map weekTotalReslutMap = new HashMap();
            if (weeks == null) {
                throw new RuntimeException("weeks");
            } else {
                Predicate predicate = AndPredicate.getInstance(new BeanPredicateWrapper(
                        "state", new EqualPredicate(Boolean.TRUE)), new BeanPredicateWrapper(
                        "inSchool", new EqualPredicate(Boolean.TRUE)));
                for (int j = 0; j < weeks.intValue(); j++) {
                    if (!weekRange.containsInteger(j + 1)) {
                        continue;
                    }
                    Date[] dates = calendar.getWeekTime(j + 1);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateBegin = simpleDateFormat.format(dates[0]);
                    String dateEnd = simpleDateFormat.format(dates[1]);
                    Map weekPartReslutTempMap = dutyService.dutyRecordStatistics(stdIdArray,
                            calendarList, dateBegin, dateEnd, false, predicate);
                    if (weekPartReslutTempMap.get("dutyRecordStatisticsMap") instanceof Map) {
                        Object totalMapObject = ((Map) weekPartReslutTempMap
                                .get("dutyRecordStatisticsMap"))
                                .get(DutyRecordStatisticsStd.totalNo);
                        if ((totalMapObject instanceof Map)
                                && (((Map) totalMapObject).get(DutyRecordStatisticsCourse.totalId) instanceof DutyRecordStatisticsStatus)) {
                            Map totalMap = (Map) totalMapObject;
                            DutyRecordStatisticsStatus totlStatus = (DutyRecordStatisticsStatus) totalMap
                                    .get(DutyRecordStatisticsCourse.totalId);
                            weekPartReslutMap.put(String.valueOf(new Integer(j + 1)), totlStatus);
                            
                            DutyRecordStatisticsStatus dutyRecordStatisticsStatus = (totalClassReslutMap
                                    .get(String.valueOf(new Integer(j + 1))) instanceof DutyRecordStatisticsStatus) ? (DutyRecordStatisticsStatus) totalClassReslutMap
                                    .get(String.valueOf(new Integer(j + 1)))
                                    : new DutyRecordStatisticsStatus();
                            dutyRecordStatisticsStatus
                                    .setAbsenteeismCount(dutyRecordStatisticsStatus
                                            .getAbsenteeismCount(null) == null ? totlStatus
                                            .getAbsenteeismCount(null) : new Integer(
                                            (dutyRecordStatisticsStatus.getAbsenteeismCount(null)
                                                    .intValue() + (totlStatus
                                                    .getAbsenteeismCount(null) == null ? 0
                                                    : totlStatus.getAbsenteeismCount(null)
                                                            .intValue()))));
                            dutyRecordStatisticsStatus
                                    .setAskedForLeaveCount(dutyRecordStatisticsStatus
                                            .getAskedForLeaveCount() == null ? totlStatus
                                            .getAskedForLeaveCount()
                                            : new Integer(
                                                    (dutyRecordStatisticsStatus
                                                            .getAskedForLeaveCount().intValue() + (totlStatus
                                                            .getAskedForLeaveCount() == null ? 0
                                                            : totlStatus.getAskedForLeaveCount()
                                                                    .intValue()))));
                            dutyRecordStatisticsStatus.setLateCount(dutyRecordStatisticsStatus
                                    .getLateCount() == null ? totlStatus.getLateCount()
                                    : new Integer((dutyRecordStatisticsStatus.getLateCount()
                                            .intValue() + (totlStatus.getLateCount() == null ? 0
                                            : totlStatus.getLateCount().intValue()))));
                            dutyRecordStatisticsStatus
                                    .setLeaveEarlyCount(dutyRecordStatisticsStatus
                                            .getLeaveEarlyCount() == null ? totlStatus
                                            .getLeaveEarlyCount()
                                            : new Integer((dutyRecordStatisticsStatus
                                                    .getLeaveEarlyCount().intValue() + (totlStatus
                                                    .getLeaveEarlyCount() == null ? 0 : totlStatus
                                                    .getLeaveEarlyCount().intValue()))));
                            dutyRecordStatisticsStatus.setPresenceCount(dutyRecordStatisticsStatus
                                    .getPresenceCount(null) == null ? totlStatus
                                    .getPresenceCount(null)
                                    : new Integer((dutyRecordStatisticsStatus
                                            .getPresenceCount(null).intValue() + (totlStatus
                                            .getPresenceCount(null) == null ? 0 : totlStatus
                                            .getPresenceCount(null).intValue()))));
                            dutyRecordStatisticsStatus.setTotalCount(dutyRecordStatisticsStatus
                                    .getTotalCount() == null ? totlStatus.getTotalCount()
                                    : new Integer((dutyRecordStatisticsStatus.getTotalCount()
                                            .intValue() + (totlStatus.getTotalCount() == null ? 0
                                            : totlStatus.getTotalCount().intValue()))));
                            totalClassReslutMap.put(String.valueOf(new Integer(j + 1)),
                                    dutyRecordStatisticsStatus);
                        }
                    }
                    weekPartReslutTempMap = null;
                }
                Date[] totalWeekBeginDates = calendar.getWeekTime(totalWeekBegin);
                Date[] totalWeekEndDates = calendar.getWeekTime(totalWeekEnd);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Map weekTotalReslutTempMap = dutyService.dutyRecordStatistics(stdIdArray,
                        calendarList, simpleDateFormat.format(totalWeekBeginDates[0]),
                        simpleDateFormat.format(totalWeekEndDates[1]), false, predicate);
                if (weekTotalReslutTempMap instanceof Map) {
                    Object totalMapObject = ((Map) weekTotalReslutTempMap
                            .get("dutyRecordStatisticsMap")).get(DutyRecordStatisticsStd.totalNo);
                    if ((totalMapObject instanceof Map)
                            && (((Map) totalMapObject).get(DutyRecordStatisticsCourse.totalId) instanceof DutyRecordStatisticsStatus)) {
                        Map totalMap = (Map) totalMapObject;
                        DutyRecordStatisticsStatus totlStatus = (DutyRecordStatisticsStatus) totalMap
                                .get(DutyRecordStatisticsCourse.totalId);
                        weekTotalReslutMap.put(String.valueOf(new Integer(0)), totlStatus);
                        DutyRecordStatisticsStatus dutyRecordStatisticsStatus = (totalClassReslutMap
                                .get(String.valueOf(new Integer(0))) instanceof DutyRecordStatisticsStatus) ? (DutyRecordStatisticsStatus) totalClassReslutMap
                                .get(String.valueOf(new Integer(0)))
                                : new DutyRecordStatisticsStatus();
                        dutyRecordStatisticsStatus
                                .setAbsenteeismCount(dutyRecordStatisticsStatus
                                        .getAbsenteeismCount(null) == null ? totlStatus
                                        .getAbsenteeismCount(null)
                                        : new Integer((dutyRecordStatisticsStatus
                                                .getAbsenteeismCount(null).intValue() + (totlStatus
                                                .getAbsenteeismCount(null) == null ? 0 : totlStatus
                                                .getAbsenteeismCount(null).intValue()))));
                        dutyRecordStatisticsStatus.setAskedForLeaveCount(dutyRecordStatisticsStatus
                                .getAskedForLeaveCount() == null ? totlStatus
                                .getAskedForLeaveCount() : new Integer((dutyRecordStatisticsStatus
                                .getAskedForLeaveCount().intValue() + (totlStatus
                                .getAskedForLeaveCount() == null ? 0 : totlStatus
                                .getAskedForLeaveCount().intValue()))));
                        dutyRecordStatisticsStatus.setLateCount(dutyRecordStatisticsStatus
                                .getLateCount() == null ? totlStatus.getLateCount() : new Integer(
                                (dutyRecordStatisticsStatus.getLateCount().intValue() + (totlStatus
                                        .getLateCount() == null ? 0 : totlStatus.getLateCount()
                                        .intValue()))));
                        dutyRecordStatisticsStatus.setLeaveEarlyCount(dutyRecordStatisticsStatus
                                .getLeaveEarlyCount() == null ? totlStatus.getLeaveEarlyCount()
                                : new Integer((dutyRecordStatisticsStatus.getLeaveEarlyCount()
                                        .intValue() + (totlStatus.getLeaveEarlyCount() == null ? 0
                                        : totlStatus.getLeaveEarlyCount().intValue()))));
                        dutyRecordStatisticsStatus
                                .setPresenceCount(dutyRecordStatisticsStatus.getPresenceCount(null) == null ? totlStatus
                                        .getPresenceCount(null)
                                        : new Integer((dutyRecordStatisticsStatus.getPresenceCount(
                                                null).intValue() + (totlStatus
                                                .getPresenceCount(null) == null ? 0 : totlStatus
                                                .getPresenceCount(null).intValue()))));
                        dutyRecordStatisticsStatus.setTotalCount(dutyRecordStatisticsStatus
                                .getTotalCount() == null ? totlStatus.getTotalCount()
                                : new Integer((dutyRecordStatisticsStatus.getTotalCount()
                                        .intValue() + (totlStatus.getTotalCount() == null ? 0
                                        : totlStatus.getTotalCount().intValue()))));
                        totalClassReslutMap.put(String.valueOf(new Integer(0)),
                                dutyRecordStatisticsStatus);
                    }
                }
                weekTotalReslutTempMap = null;
                partReslutMap.put(String.valueOf(adminClassElement.getId()), weekPartReslutMap);
                totalReslutMap.put(String.valueOf(adminClassElement.getId()), weekTotalReslutMap);
            }
            
        }
        
        // TODO
        
        // Results.addObject("weeks", weeks);
        Results.addObject("weekBegin", weekBegin < totalWeekBegin ? new Integer(weekBegin)
                : new Integer(totalWeekBegin));
        Results.addObject("weekEnd", weekEnd > totalWeekEnd ? new Integer(weekEnd) : new Integer(
                totalWeekEnd));
        Results.addObject("totalClassReslutMap", totalClassReslutMap);
        Results.addObject("totalResult", totalReslutMap);
        Results.addObject("partResult", partReslutMap);
        Results.addObject("adminClassMap", adminClassMap);
        
        return this.forward(mapping, request, "adminClassRecordStatistics");
    }
    
    /**
     * 获取周次
     * 
     * @param request
     * @param name
     * @param defaultInt
     * @return
     */
    private int getWeekIndex(HttpServletRequest request, String name, int defaultInt) {
        int week = NumberUtils.toInt(request.getParameter(name));
        if (week < 1) {
            return defaultInt;
        }
        return week;
    }
    
    /**
     * @param stdTypeId
     * @param request
     * @return
     */
    public Map searchTeachTaskForDuty(Long stdTypeId, HttpServletRequest request) {
        Map resultMap = new HashMap(4);
        try {
            setCalendar(request, (StudentType) utilService.load(StudentType.class, stdTypeId));
        } catch (CalendarNotExistException e) {
            resultMap.put("courseType", Collections.EMPTY_LIST);
            resultMap.put("teachDep", Collections.EMPTY_LIST);
            resultMap.put("studyDep", Collections.EMPTY_LIST);
            resultMap.put("teacher", Collections.EMPTY_LIST);
            return resultMap;
        }
        String studentTypeIds = getStdTypeIdSeq(request);
        String departmentIds = getDepartmentIdSeq(request);
        List courseTypeList = teachTaskService.getCourseTypesOfTask(studentTypeIds, departmentIds,
                (TeachCalendar) request.getAttribute(Constants.CALENDAR));
        List courseTypes = new ArrayList();
        for (Iterator iter = courseTypeList.iterator(); iter.hasNext();) {
            CourseType ourseTypeElement = (CourseType) iter.next();
            BaseCode baseCode = new BaseCode();
            baseCode.setId(ourseTypeElement.getId());
            baseCode.setName(ourseTypeElement.getName());
            baseCode.setEngName(ourseTypeElement.getEngName());
            courseTypes.add(baseCode);
        }
        courseTypeList = null;
        resultMap.put("courseType", courseTypes);
        List teachDepartList = teachTaskService.getTeachDepartsOfTask(studentTypeIds,
                departmentIds, (TeachCalendar) request.getAttribute(Constants.CALENDAR));
        List teachDeps = new ArrayList();
        for (Iterator iter = teachDepartList.iterator(); iter.hasNext();) {
            Department teachDep = (Department) iter.next();
            BaseCode baseCode = new BaseCode();
            baseCode.setId(teachDep.getId());
            baseCode.setName(teachDep.getName());
            baseCode.setEngName(teachDep.getEngName());
            teachDeps.add(baseCode);
        }
        teachDepartList = null;
        resultMap.put("teachDep", teachDeps);
        List departmentList = teachTaskService.getDepartsOfTask(studentTypeIds, departmentIds,
                (TeachCalendar) request.getAttribute(Constants.CALENDAR));
        List studyDeps = new ArrayList();
        for (Iterator iter = departmentList.iterator(); iter.hasNext();) {
            Department studyDep = (Department) iter.next();
            BaseCode baseCode = new BaseCode();
            baseCode.setId(studyDep.getId());
            baseCode.setName(studyDep.getName());
            baseCode.setEngName(studyDep.getEngName());
            studyDeps.add(baseCode);
        }
        departmentList = null;
        resultMap.put("studyDep", studyDeps);
        List teacherList = teachTaskService.getTeachersOfTask(studentTypeIds, departmentIds,
                (TeachCalendar) request.getAttribute(Constants.CALENDAR));
        List teachers = new ArrayList();
        for (Iterator iter = teacherList.iterator(); iter.hasNext();) {
            Teacher teacher = (Teacher) iter.next();
            BaseCode baseCode = new BaseCode();
            baseCode.setId(teacher.getId());
            baseCode.setName(teacher.getName());
            baseCode.setEngName(teacher.getEngName());
            teachers.add(baseCode);
        }
        teacherList = null;
        resultMap.put("teacher", teachers);
        return resultMap;
        
    }
    
    /**
     * @param teachTaskService
     *            The teachTaskService to set.
     */
    public void setTeachTaskService(TeachTaskService teachTaskService) {
        this.teachTaskService = teachTaskService;
    }
    
    /**
     * @param adminClassService
     *            要设置的 adminClassService。
     */
    public void setAdminClassService(AdminClassService adminClassService) {
        this.adminClassService = adminClassService;
    }
    
}
