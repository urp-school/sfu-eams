//$Id: OtherGradeSearchAction.java,v 1.1 2007-2-24 下午06:34:58 chaostone Exp $
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
 * Name             Date            Description 
 * ============     ============    ============
 * chaostone        2007-02-24      Created
 * zq               2007-09-13      在buildQuery中，增加了addStdTypeTreeDataRealm(...)方法
 ********************************************************************************/

package com.shufe.web.action.course.grade.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.ekingstar.eams.system.baseinfo.Department;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.service.course.grade.other.OtherGradeService;
import com.shufe.service.course.grade.other.impl.OtherGradeServiceImpl;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

/**
 * 其他成绩查询
 * 
 * @author chaostone
 */
public class OtherGradeSearchAction extends CalendarRestrictionSupportAction {

    OtherGradeService otherGradeService = new OtherGradeServiceImpl();

    /**
     * 其他考试主界面
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdTypeCollege);
        addCollection(request, "otherExamCategories", baseCodeService
                .getCodes(OtherExamCategory.class));
        addCollection(request, "markStyles", baseCodeService.getCodes(MarkStyle.class));
        List teachCalendarList = utilService
        .searchHQLQuery("from TeachCalendar tc where tc.studentType = 5 order by id");
        request.setAttribute("teachCalendarList", teachCalendarList);
        String calendarId = request.getParameter("calendar.id");
        request.setAttribute("calendarId", calendarId);
        return forward(request);
    }

    public void setOtherGradeService(OtherGradeService otherGradeService) {
        this.otherGradeService = otherGradeService;
    }

    /**
     * 查询其他考试成绩
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
        EntityQuery query = buildQuery(request);
        addCollection(request, "otherGrades", utilService.search(query));
        List teachCalendarList = utilService
        .searchHQLQuery("from TeachCalendar tc where tc.studentType = 5 order by id");
        request.setAttribute("teachCalendarList", teachCalendarList);
        String calendarId = request.getParameter("calendar.id");
        request.setAttribute("calendarId", null!=calendarId?calendarId:"0");
        return forward(request);
    }

    /**
     * 校外考试分段统计
     * 
     * @param request
     * @return
     */
    public ActionForward searchOtherGrade(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String category = request.getParameter("category");
        Map otherGradeMap = new MultiValueMap();
        String hgbz = request.getParameter("otherBz");
        Department department = null;
        List ortherGradeDepartmentList = utilService
                .searchHQLQuery("select distinct(og.std.department) from OtherGrade og where og.category.code="
                        + category);
        for (Iterator iter = ortherGradeDepartmentList.iterator(); iter.hasNext();) {
            department = (Department) iter.next();
            List ortherGradeList = utilService
                    .searchHQLQuery("select distinct(og.std.enrollYear) from OtherGrade og where og.category.code="
                            + category
                            + " and og.std.department.id ="
                            + department.getId()
                            + "order by og.std.enrollYear");
            for (Iterator iter_ortherGradeList = ortherGradeList.iterator(); iter_ortherGradeList
                    .hasNext();) {
                String enrollYear = (String) iter_ortherGradeList.next();
                Long gradeCount = null;
                Long gradeIspassCount = null;
                gradeCount = (Long) utilService.searchHQLQuery(
                        "select count(*) from OtherGrade og where og.category.code=" + category
                                + " and og.std.department.id =" + department.getId()
                                + "and og.std.enrollYear ='" + enrollYear
                                + "' and og.score <> 0").get(0);
                gradeIspassCount = (Long) utilService
                        .searchHQLQuery(
                                "select count(*) from OtherGrade og where og.category.code="
                                        + category + " and og.std.department.id ="
                                        + department.getId() + " and og.std.enrollYear ='"
                                        + enrollYear + "' and og.score >="+hgbz).get(0);

                List Tjlist = new ArrayList();
                Tjlist.add(enrollYear);
                Tjlist.add(gradeCount);
                Tjlist.add(gradeIspassCount);
                otherGradeMap.put(department.getName(), Tjlist);
            }
        }
        request.setAttribute("otherGradeMap", otherGradeMap);
        String otherExamCategoryName = (String) utilService
        .searchHQLQuery(
                "select name from OtherExamCategory oec where oec.code ='"+category+"'").get(0);
        request.setAttribute("otherExamCategoryName", otherExamCategoryName);
        return forward(request);
    }
    
    public ActionForward searchOtherGradeByTerm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String year = request.getParameter("year");
        String term = request.getParameter("term");
        String hgbz = request.getParameter("otherBz");
        String category = request.getParameter("category");
        Map otherGradeMap = new MultiValueMap();
        Department department = null;
        List ortherGradeDepartmentList = utilService
                .searchHQLQuery("select distinct(og.std.department) from OtherGrade og where og.category.code="
                        + category+" and og.calendar.year ='"+year+"' and og.calendar.term ='"+term+"'");
        for (Iterator iter = ortherGradeDepartmentList.iterator(); iter.hasNext();) {
            department = (Department) iter.next();
            List ortherGradeList = utilService
                    .searchHQLQuery("select distinct(og.std.enrollYear) from OtherGrade og where og.category.code="
                            + category
                            + " and og.std.department.id ="
                            + department.getId()+" and og.calendar.year ='"+year+"' and og.calendar.term ='"+term+
                             "' order by og.std.enrollYear");
            for (Iterator iter_ortherGradeList = ortherGradeList.iterator(); iter_ortherGradeList
                    .hasNext();) {
                String enrollYear = (String) iter_ortherGradeList.next();
                Long gradeCount = null;
                Long gradeIspassCount = null;
                gradeCount = (Long) utilService.searchHQLQuery(
                        "select count(*) from OtherGrade og where og.category.code=" + category
                                + " and og.std.department.id =" + department.getId()
                                + "and og.std.enrollYear ='" + enrollYear+"' and og.calendar.year ='"+year+"' and og.calendar.term='"+term
                                + "' and og.score <> 0").get(0);
                gradeIspassCount = (Long) utilService
                        .searchHQLQuery(
                                "select count(*) from OtherGrade og where og.category.code="
                                        + category + " and og.std.department.id ="
                                        + department.getId() + " and og.std.enrollYear ='"
                                        + enrollYear +"' and og.calendar.year ='"+year+"' and og.calendar.term='"+term+"' and og.score >="+hgbz).get(0);

                List Tjlist = new ArrayList();
                Tjlist.add(enrollYear);
                Tjlist.add(gradeCount);
                Tjlist.add(gradeIspassCount);
                otherGradeMap.put(department.getName(), Tjlist);
            }
        }
        request.setAttribute("otherGradeMap", otherGradeMap);
        String otherExamCategoryName = (String) utilService
        .searchHQLQuery(
                "select name from OtherExamCategory oec where oec.code ='"+category+"'").get(0);
        String Xnd = year+"学年第"+term+"学期";
        request.setAttribute("otherExamCategoryName", otherExamCategoryName);
        request.setAttribute("Xnd", Xnd);
        return forward(request);
    }

    protected EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(OtherGrade.class, "otherGrade");
        populateConditions(request, query, "otherGrade.std.type.id,otherGrade.isPass");
        query.setLimit(getPageLimit(request));
        Integer pass = getInteger(request, "otherGrade.isPass");
        if (null != pass) {
            if (pass.equals(new Integer(-1))) {
                query.add(new Condition("otherGrade.isPass = (:isPass)", Boolean.FALSE));
                query
                        .add(new Condition(
                                "not exists(from "
                                        + OtherGrade.class.getName()
                                        + " otherGrade1 where otherGrade1.std = otherGrade.std and otherGrade1.category = otherGrade.category and otherGrade1.isPass = (:isPass1))",
                                Boolean.TRUE));
            } else {
                query.add(new Condition("otherGrade.isPass = (:isPass)", RequestUtils.getBoolean(
                        request, "otherGrade.isPass")));
            }
        }
        DataRealmUtils.addDataRealms(query, new String[] { "otherGrade.std.type.id",
                "otherGrade.std.department.id" }, getDataRealmsWith(getLong(request,
                "otherGrade.std.type.id"), request));
        String stdAdminClassName = get(request, "stdAdminClass");
        if (StringUtils.isNotEmpty(stdAdminClassName)) {
            query.join("otherGrade.std.adminClasses", "adminClass");
            query.add(Condition.like("adminClass.name", stdAdminClassName));
        }
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        return query;
    }

    protected Collection getExportDatas(HttpServletRequest request) {
        EntityQuery query = buildQuery(request);
        query.setLimit(null);
        return utilService.search(query);
    }

}
