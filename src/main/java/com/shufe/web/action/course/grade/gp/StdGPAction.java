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
 * chaostone             2007-1-2            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.grade.gp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.gp.GradePointRule;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.grade.gp.GradePointRuleService;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 学生个人绩点(查询统计)
 * 
 * @author chaostone
 * 
 */
public class StdGPAction extends RestrictionSupportAction {
    
    protected GradePointService gradePointService;
    
    protected StdSearchHelper stdSearchHelper;
    
    protected GradePointRuleService gradePointRuleService;
    
    protected TeachCalendarService teachCalendarService;
    
    /**
     * 学生个人成绩总表主界面
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
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 查询学生
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "students", utilService.search(stdSearchHelper
                .buildStdQuery(request)));
        return forward(request);
    }
    
    /**
     * 学生个人成绩总表主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdGPReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!buildStdReport(mapping, request)) {
            return forwardError(mapping, request, "error.parameters.needed");
        }
        return forward(request);
    }
    
    /**
     * @param mapping
     * @param request
     */
    protected boolean buildStdReport(ActionMapping mapping, HttpServletRequest request) {
        String stdIds = get(request, "stdIds");
        if (StringUtils.isEmpty(stdIds)) {
            return false;
        }
        List stds = utilService.load(Student.class, "id", SeqStringUtil.transformToLong(stdIds));
        MajorType majorType = new MajorType(getLong(request, "majorTypeId"));
        request.setAttribute("majorType", majorType);
        List stdGPs = new ArrayList();
        for (Iterator iter = stds.iterator(); iter.hasNext();) {
            Student std = (Student) iter.next();
            stdGPs.add(gradePointService.statStdGPA(std, majorType, Boolean.TRUE));
        }
        addCollection(request, "stdGPs", stdGPs);
        return true;
    }
    
    /**
     * 查看学生学年绩点
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward stdCalendarGPReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stdIds = get(request, "stdIds");
        List stds = utilService.load(Student.class, "id", SeqStringUtil.transformToLong(stdIds));
        MajorType majorType = new MajorType(getLong(request, "majorTypeId"));
        request.setAttribute("majorType", majorType);
        List stdGPs = new ArrayList();
        for (Iterator iter = stds.iterator(); iter.hasNext();) {
            Student std = (Student) iter.next();
            stdGPs.add(gradePointService.statStdGPA(std, majorType, Boolean.TRUE));
        }
        addCollection(request, "stdGPs", stdGPs);
        return forward(request);
    }
    
    /**
     * 学生绩点重算设置界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reStatGPSetting(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long stdTypeId = getLong(request, "std.type.id");
        StudentType stdType = studentTypeService.getStudentType(stdTypeId);
        List calendars = teachCalendarService.getTeachCalendars(stdType);
        Date now = new Date();
        for (Iterator iter = calendars.iterator(); iter.hasNext();) {
            TeachCalendar calendar = (TeachCalendar) iter.next();
            if (calendar.getStart().after(now)) {
                iter.remove();
            }
        }
        Collections.sort(calendars, new PropertyComparator("start", false));
        addCollection(request, "calendars", calendars);
        return forward(request);
    }
    
    /**
     * 学生绩点重算
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reStatGP(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String calendarIds = request.getParameter("calendarIds");
        Long stdTypeId = getLong(request, "calendar.studentType.id");
        StudentType stdType = studentTypeService.getStudentType(stdTypeId);
        
        EntityQuery markStyleQuery = new EntityQuery(CourseGrade.class, "grade");
        markStyleQuery.add(new Condition("instr('," + calendarIds
                + ",',','||grade.calendar.id||',')>0"));
        markStyleQuery.setSelect("select distinct grade.markStyle");
        
        List markStyles = (List) utilService.search(markStyleQuery);
        for (Iterator iter = markStyles.iterator(); iter.hasNext();) {
            MarkStyle markStyle = (MarkStyle) iter.next();
            GradePointRule gradePointRule = gradePointRuleService.getGradePointRule(stdType,
                    markStyle);
            gradePointService.reStatGP(gradePointRule, calendarIds);
        }
        return redirect(request, "index", "info.update.success");
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
    
    public void setGradePointRuleService(GradePointRuleService gradePointRuleService) {
        this.gradePointRuleService = gradePointRuleService;
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
}
