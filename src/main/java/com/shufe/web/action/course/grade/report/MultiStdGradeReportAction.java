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
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.shufe.model.course.grade.report.MultiStdGrade;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.util.RequestUtil;
import com.shufe.web.helper.BaseInfoSearchHelper;

/**
 * 多个学生的成绩报表
 * 
 * @author chaostone
 * 
 */
public class MultiStdGradeReportAction extends StdTermGradeReportAction {
    
    private BaseInfoSearchHelper baseInfoSearchHelper;
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return super.index(mapping, form, request, response);
    }
    
    /**
     * 查找行政班
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward adminClassList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCollection(request, "adminClasses", baseInfoSearchHelper.searchAdminClass(request));
        addCollection(request, "gradeTypes", baseCodeService.getCodes(GradeType.class));
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
    public ActionForward classGradeReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = teachCalendarService.getTeachCalendar(calendarId);
        String adminClassIds = request.getParameter("adminClassIds");
        
        // 找到参数和成绩比较器
        GradeReportSetting setting = new GradeReportSetting();
        RequestUtil.populate(request, setting, "reportSetting");
        if (StringUtils.isEmpty(setting.getOrder().getProperty())) {
            setting.getOrder().setProperty("GPA");
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
        List adminClasses = utilService.load(AdminClass.class, "id", SeqStringUtil
                .transformToLong(adminClassIds));
        List multiStdGrades = new ArrayList();
        for (Iterator iter = adminClasses.iterator(); iter.hasNext();) {
            AdminClass adminClass = (AdminClass) iter.next();
            List grades = gradeService.getCourseGrades(calendar, adminClass.getStudents(), setting
                    .getMajorType(), setting.getPublished());
            MultiStdGrade multiStdGrade = new MultiStdGrade(calendar, grades, ratio);
            multiStdGrade.sortStdGrades(setting.getOrder().getProperty(), Order.ASC == setting
                    .getOrder().getDirection());
            multiStdGrade.setAdminClass(adminClass);
            multiStdGrades.add(multiStdGrade);
        }
        request.setAttribute("setting", setting);
        // 对多个报表进行排序
        List orders = OrderUtils.parser(request.getParameter("orderBy"));
        if (!orders.isEmpty()) {
            Order order = (Order) orders.get(0);
            PropertyComparator orderCmp = new PropertyComparator(order.getProperty(),
                    Order.ASC == order.getDirection());
            Collections.sort(multiStdGrades, orderCmp);
        }
        request.setAttribute("multiStdGrades", multiStdGrades);
        return forward(request);
    }
    
    public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
        this.baseInfoSearchHelper = baseInfoSearchHelper;
    }
    
}
