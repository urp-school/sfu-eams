//$Id: TeachWorkloadMultiTermStatAction.java,v 1.1 2008-6-3 下午03:07:27 zhouqi Exp $
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
 * zhouqi              2008-6-3             Created
 * 
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.course;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.CalendarNotExistException;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * @author zhouqi
 * 
 */
public class TeachWorkloadMultiTermStatAction extends TeachWorkloadStatAction {
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setAttribute("stdTypes", getStdTypes(request));
        request.setAttribute("departments", departmentService
                .getTeachDeparts(getDepartmentIdSeq(request)));
        return forward(request);
    }
    
    /**
     * 全校统计—－教师
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward mutiCalendarStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String departIdSeq = getDepartmentIdSeq(request);
        List stdTypeList = getStdTypes(request);
        Long stdTypeId = getLong(request, "stdTypeId");
        String yearStart = request.getParameter("yearStart");
        String termStart = request.getParameter("termStart");
        String yearEnd = request.getParameter("yearEnd");
        String termEnd = request.getParameter("termEnd");
        
        if (StringUtils.isEmpty(yearStart) || StringUtils.isEmpty(termStart)
                || StringUtils.isEmpty(yearEnd) || StringUtils.isEmpty(termEnd)) {
            throw new CalendarNotExistException(((StudentType) utilService.load(StudentType.class,
                    stdTypeId)).getName()
                    + "教学日历不存在！");
        } else {
            if (StringUtils.isBlank(termStart)) {
                StudentType studentType = (StudentType) stdTypeList.get(0);
                TeachCalendar teachaCalendar = teachCalendarService.getNearestCalendar(studentType);
                stdTypeId = teachaCalendar.getStudentType().getId();
                yearEnd = yearStart = teachaCalendar.getYear();
                termStart = termEnd = teachaCalendar.getTerm();
            }
            List condtionCalendars = teachCalendarService.getTeachCalendars(stdTypeId, yearStart,
                    termStart, yearEnd, termEnd);
            String departmentId = request.getParameter("teachWorkload.college.id");
            if (StringUtils.isBlank(departmentId)) {
                departmentId = departIdSeq;
            }
            String stdTypeIds = getStdTypeIdSeqOf(stdTypeId, request);
            List collegeList = departmentService.getColleges(departmentId);
            stdTypeList = studentTypeService.getStudentTypes(stdTypeIds);
            Map departAndStdTypeMap = teachWorkloadStatService.getWorkloadByDataRealm(departmentId,
                    stdTypeIds, condtionCalendars);
            request.setAttribute("collegeList", collegeList);
            request.setAttribute("stdTypeList", stdTypeList);
            request.setAttribute("condtionCalendars", condtionCalendars);
            request.setAttribute("departAndStdTypeMap", departAndStdTypeMap);
        }
        return forward(request);
    }
    
    /**
     * 显示开课院系地详细细节
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        String selectDepartId = request.getParameter("selectDepartId");
        String allStdTypeIdSeq = request.getParameter("stdTypeIdSeq");
        String allCalendarIdSeq = request.getParameter("calendarIdSeq");
        List teachCalendars = utilService.load(TeachCalendar.class, "id", SeqStringUtil
                .transformToLong(allCalendarIdSeq));
        Map techerAndCalendarsMap = teachWorkloadStatService.getWorkloadByTeacherAndCalendar(
                selectDepartId, allStdTypeIdSeq, teachCalendars);
        Department depart = (Department) utilService.get(Department.class, Long
                .valueOf(selectDepartId));
        request.setAttribute("teachCalendars", teachCalendars);
        request.setAttribute("techerAndCalendarsMap", techerAndCalendarsMap);
        request.setAttribute("depart", depart);
        return forward(request);
    }
}
