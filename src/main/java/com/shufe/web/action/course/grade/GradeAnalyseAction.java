package com.shufe.web.action.course.grade;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.std.Student;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;


public class GradeAnalyseAction extends CalendarRestrictionSupportAction {
    
    GradeService gradeService;
    
    GradePointService gradePointService;
    
    
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }

    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    

    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EntityQuery query = new EntityQuery(CourseGrade.class,"grade");
        populateConditions(request, query, "department.id");
        Long departmentId = getLong(request, "department.id");
        populateConditions(request, query, "speciality.id");
        Long specialityId = getLong(request, "speciality.id");
        populateConditions(request, query, "specialityAspect.id");
        Long specialityAspectId = getLong(request, "specialityAspect.id");
        String enrollYear = request.getParameter("std.enrollYear");
        String year = request.getParameter("calendar.year");
        String term = request.getParameter("calendar.term");
        if(null!=departmentId){
            query.add(new Condition("grade.std.department.id ="+departmentId));
        }
        if(null!=specialityId){
            query.add(new Condition("grade.std.firstMajor.id ="+specialityId));
        }
        if(null!=specialityAspectId){
            query.add(new Condition("grade.std.firstAspect.id ="+specialityAspectId));
        }
        if(null!=enrollYear&&enrollYear!=""){
            query.add(new Condition("grade.std.enrollYear like '%"+enrollYear+"%'"));
        }
        if(null!=year&&(null!=term&&!"".equals(term))){
            Long calendarId = (Long) utilService.searchHQLQuery("select tc.id from TeachCalendar tc" +
            		" where tc.year='"+year+"' and tc.term='"+term+"'").get(0);
            query.add(new Condition("grade.calendar.id ="+calendarId));
        }if((null!=year&&!"".equals(year))&&(null==term||"".equals(term))){
            query.add(new Condition("grade.calendar.year ='"+year+"'"));
        }
        query.setSelect("grade.std.id,sum(grade.credit*grade.GP)/sum(grade.credit)");
        query.groupBy("grade.std.id");
        query.addOrder(new Order("sum(grade.credit*grade.GP)/sum(grade.credit)", Order.DESC));
        query.setLimit(getPageLimit(request));
        Collection list = utilService.search(query);
        new StatHelper(utilService).replaceIdWith(list, new Class[] {Student.class});
        addCollection(request, "stdList", list);
        return forward(request);
    }
}
