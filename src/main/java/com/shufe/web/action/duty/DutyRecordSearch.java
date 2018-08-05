//$Id: DutyRecordSearch.java,v 1.12 2007/01/19 10:27:43 yd Exp $
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
 * pippo             2005-12-5         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.duty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.AttendanceType;
import com.ekingstar.eams.system.time.CalendarNotExistException;
import com.shufe.model.duty.DutyRecord;
import com.shufe.model.duty.RecordDetail;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.duty.DutyService;
import com.shufe.util.RequestUtil;

/**
 * @author dell
 */
public class DutyRecordSearch extends DutyRecordSupportAction {
    
    private DutyService dutyService;
    
    /**
     * 检索学生学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return studentManager.ftl
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        searchStudent(form, request, "SearchStudent");
        return forward(mapping, request, "recordByStudentList");
    }
    
    /**
     * 检索学生学籍信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return studentManager.ftl
     */
    public ActionForward manage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        searchStudent(form, request, "SearchStudent");
        return forward(mapping, request, "manageByStudentList");
    }
    
    public ActionForward recordByStudentForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long stdId = getLong(request, "stdId");
        Student student = (Student) utilService.get(Student.class, stdId);
        Results.addObject("student", student);
        return this.forward(request);
    }
    
    /**
     * 按学号搜索考勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward recordByStudent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long stdId = getLong(request, "stdId");
        TeachCalendar example = (TeachCalendar) RequestUtil.populate(request, TeachCalendar.class,
                "calendar");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(example.getStudentType(),
                example.getYear(), example.getTerm());
        if (null == calendar) {
            throw new CalendarNotExistException(stdId + "");
        }
        List recordList = utilService.load(DutyRecord.class, new String[] { "student.id",
                "teachTask.calendar.id" }, new Object[] { stdId, calendar.getId() });
        Results.addObject("recordList", recordList);
        if (utilService.exist(Student.class, "id", stdId))
            Results.addObject("student", utilService.load(Student.class, stdId));
        return this.forward(mapping, request, "recordList");
    }
    
    public ActionForward manageByStudentForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long stdId = getLong(request, "stdId");
        Student student = (Student) utilService.get(Student.class, stdId);
        Results.addObject("student", student);
        return this.forward(request);
    }
    
    /**
     * 按学号管理考勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward manageByStudent(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        // if ("search".equals(request.getParameter("flag"))) {
        Long stdId = getLong(request, "stdId");
        TeachCalendar example = (TeachCalendar) RequestUtil.populate(request, TeachCalendar.class,
                "calendar");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(example.getStudentType(),
                example.getYear(), example.getTerm());
        if (null == calendar) {
            return this.forwardError(mapping, request, "error.parameters.illegal");
        }
        List calendarList = teachCalendarService.getTeachCalendarsOfOverlapped(calendar);
        EntityQuery recordQuery = new EntityQuery(DutyRecord.class, "record");
        recordQuery.add(new Condition("record.student.id = :stdId", stdId));
        recordQuery
                .add(new Condition("record.teachTask.calendar in (:calendarList)", calendarList));
        Collection recordList = utilService.search(recordQuery);
        Results.addObject("recordList", recordList);
        if (utilService.exist(Student.class, "id", stdId)) {
            Results.addObject("student", utilService.load(Student.class, stdId));
        }
        // }
        return this.forward(mapping, request, "manageRecordList");
    }
    
    /**
     * 查看考勤记录详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward recordDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long dutyRecordId = Long.valueOf(request.getParameter("dutyRecordId"));
        List recordDetailList = utilService.load(RecordDetail.class, "dutyRecord.id", dutyRecordId);
        DutyRecord dutyRecord = (DutyRecord) utilService.load(DutyRecord.class, dutyRecordId);
        Results.addObject("recordDetailList", recordDetailList);
        Results.addObject("dutyRecord", dutyRecord);
        return this.forward(mapping, request, "recordDetailList");
    }
    
    public ActionForward studentRecordForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        String code = this.getUser(request.getSession()).getName();
        Student student = studentService.getStd(code);
        Results.addObject("student", student);
        return this.forward(request);
    }
    
    /**
     * 当前登陆学生考勤记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward studentRecord(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // FIXME 以下先不删
        // TeachCalendar example = (TeachCalendar) RequestUtil.populate(request,
        // TeachCalendar.class,
        // "calendar");
        // TeachCalendar calendar = teachCalendarService.getTeachCalendar(example.getStudentType(),
        // example.getYear(), example.getTerm());
        // if (null == calendar) {
        // return this.forwardError(mapping, request, "error.parameters.illegal");
        // }
        // List calendarList = teachCalendarService.getTeachCalendarsOfOverlapped(calendar);
        String code = this.getUser(request.getSession()).getName();
        EntityQuery recordQuery = new EntityQuery(DutyRecord.class, "record");
        recordQuery.add(new Condition("record.student.code = :stdCode", code));
        recordQuery.add(new Condition("record.teachTask.calendar.year in (:year)", get(request,
                "calendar.year")));
        recordQuery.add(new Condition("record.teachTask.calendar.term in (:term)", get(request,
                "calendar.term")));
        // recordQuery.add(new Condition("teachTask.calendar in (:calendarList)", calendarList));
        Collection recordList = utilService.search(recordQuery);
        Results.addObject("recordList", recordList);
        return this.forward(mapping, request, "recordList");
    }
    
    public ActionForward manageByStudentDateForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String beginDateString = request.getParameter("beginDate");
        String endDateString = request.getParameter("endDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotEmpty(beginDateString)) {
            try {
                beginDate = simpleDateFormat.parse(beginDateString);
            } catch (ParseException e) {
                throw new RuntimeException("beginDate ParseException");
            }
        }
        if (StringUtils.isNotEmpty(endDateString)) {
            try {
                endDate = simpleDateFormat.parse(endDateString);
            } catch (ParseException e) {
                throw new RuntimeException("endDate ParseException");
            }
        }
        Long[] stdIds = SeqStringUtil.transformToLong(request.getParameter("ids"));
        
        List recordDetailList = dutyService.searchRecordDetail(stdIds, beginDate, endDate);
        
        Results.addObject("recordDetailList", recordDetailList);
        
        Results.addObject("dutyStatusList", utilService.loadAll(AttendanceType.class));
        
        return this.forward(request);
        
    }
    
    /**
     * @param dutyService
     *            要设置的 dutyService.
     */
    public void setDutyService(DutyService dutyService) {
        this.dutyService = dutyService;
    }
    
}
