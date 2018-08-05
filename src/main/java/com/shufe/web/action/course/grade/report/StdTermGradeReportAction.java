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
 * chaostone             2007-1-5            Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade.report;

import java.util.ArrayList;
import java.util.Collections;
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
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.report.MultiStdGrade;
import com.shufe.model.course.grade.report.StdTermGrade;
import com.shufe.model.course.grade.stat.StdTermCredit;
import com.shufe.model.std.Student;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.election.CreditConstraintService;
import com.shufe.service.system.calendar.TeachCalendarService;
import com.shufe.util.DataRealmUtils;
import com.shufe.util.RequestUtil;
import com.shufe.web.helper.TeachCalendarHelper;

/**
 * 学生每学期成绩
 * 
 * @author chaostone
 */
public class StdTermGradeReportAction extends StdGradeReportAction {
    
    protected TeachCalendarService teachCalendarService;
    
    protected CreditConstraintService creditConstraintService;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setDataRealm(request, hasStdTypeCollege);
        new TeachCalendarHelper(teachCalendarService, utilService).setCalendar(request, null);
        List stdTypeList = (List) getStdTypesOf(((TeachCalendar) request.getAttribute("calendar"))
                .getStudentType().getId(), request);
        Collections.sort(stdTypeList);
        addCollection(request, "calendarStdTypes", stdTypeList);
        return forward(request);
    }
    
    /**
     * 生成每学期成绩表
     */
    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 找到参数
        GradeReportSetting setting = new GradeReportSetting();
        RequestUtil.populate(request, setting, "reportSetting");
        
        String stdIds = request.getParameter("stdIds");
        List stdList = utilService.load(Student.class, "id", SeqStringUtil.transformToLong(stdIds));
        // 成绩样板
        CourseGrade example = new CourseGrade();
        example.setMajorType(setting.getMajorType());
        if (Boolean.TRUE.equals(setting.published)) {
            example.setStatus(new Integer(Grade.PUBLISHED));
        }
        // example.setIsPass(GradeReportSetting.ALL_GRADE.equals(setting.gradePrintType)
        // ? null
        // : Boolean.TRUE);
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        example.setCalendar(calendar);
        // 制作报表
        List stdGradeReports = new ArrayList();
        for (Iterator iter = stdList.iterator(); iter.hasNext();) {
            Student std = (Student) iter.next();
            example.setStd(std);
            StdTermGrade stdTermGrade = new StdTermGrade(std,
                    gradeService.getCourseGrades(example), null, setting.gradePrintType);
            // 是否打印奖励学分
            if (Boolean.TRUE.equals(setting.printAwardCredit)) {
                List constraints = creditConstraintService.getStdCreditConstraints(std, Collections
                        .singletonList(calendar));
                if (!constraints.isEmpty()) {
                    StdCreditConstraint scc = (StdCreditConstraint) constraints.get(0);
                    stdTermGrade.setAwardedCredit(scc.getAwardedCredit());
                }
            }
            stdGradeReports.add(stdTermGrade);
        }
        request.setAttribute("setting", setting);
        // 对多个报表进行排序
        List orders = OrderUtils.parser(request.getParameter("orderBy"));
        if (!orders.isEmpty()) {
            Order order = (Order) orders.get(0);
            PropertyComparator orderCmp = new PropertyComparator(order.getProperty(),
                    Order.ASC == order.getDirection());
            Collections.sort(stdGradeReports, orderCmp);
        }
        addCollection(request, "stdGradeReports", stdGradeReports);
        request.setAttribute("calendar", calendar);
        return forward(request);
    }
    
    /**
     * 多个学生在一张表格上
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward multiStdReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        String stdIdSeq = request.getParameter("stdIds");
        List stds = utilService.load(Student.class, "id", SeqStringUtil.transformToLong(stdIdSeq));
        // 找到参数和成绩比较器
        GradeReportSetting setting = new GradeReportSetting();
        RequestUtil.populate(request, setting, "reportSetting");
        if (StringUtils.isEmpty(setting.getOrder().getProperty())) {
            setting.getOrder().setProperty("calendar.GPA");
            setting.getOrder().setDirection(Order.DESC);
        }
        // 默认20
        if (setting.getPageSize().intValue() < 0) {
            setting.setPageSize(new Integer(20));
        }
        // 找到显示共同课程的人数下限比
        Float ratio = getFloat(request, "ratio");
        if (null == ratio || ratio.floatValue() < 0 || ratio.floatValue() >= 1) {
            ratio = new Float(0.05);
        }
        List grades = gradeService.getCourseGrades(calendar, stds, setting.getMajorType(), null);
        MultiStdGrade multiStdGrade = new MultiStdGrade(calendar, grades, ratio);
        multiStdGrade.sortStdGrades(setting.getOrder().getProperty(), Order.ASC == setting
                .getOrder().getDirection());
        
        request.setAttribute("setting", setting);
        request.setAttribute("multiStdGrades", Collections.singletonList(multiStdGrade));
        return forward(request);
    }
    
    /**
     * 统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward lessHalfStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        EntityQuery query = new EntityQuery(StdTermCredit.class, "stdTC");
        query.add(new Condition("2 * stdTC.credits < stdTC.totalCredits"));
        query.add(new Condition("stdTC.calendar.id = (:calendarId)", calendarId));
        DataRealmUtils.addDataRealms(query, new String[] { "stdTC.std.type.id",
                "stdTC.std.department.id" }, getDataRealmsWith(getLong(request, "std.type.id"),
                request));
        query.setLimit(getPageLimit(request));
        query.addOrder(OrderUtils.parser(get(request, "orderBy")));
        addCollection(request, "stdTCs", utilService.search(query));
        return forward(request, "lessHalfStat");
    }
    
    public void setTeachCalendarService(TeachCalendarService teachCalendarService) {
        this.teachCalendarService = teachCalendarService;
    }
    
    public void setCreditConstraintService(CreditConstraintService creditConstraintService) {
        this.creditConstraintService = creditConstraintService;
    }
    
}
