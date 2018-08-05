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
 * chaostone            2007-01-04          Created
 * zq                   2007-09-13          在stdList()中，添加了addStdTypeTreeDataRealm(...)方法F
 ********************************************************************************/

package com.shufe.web.action.course.grade.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.MultiPropertyComparator;
import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.system.basecode.industry.GradeType;
import com.ekingstar.eams.teach.program.service.GradeFilter;
import com.ekingstar.eams.teach.program.service.SubstituteCourseService;
import com.ekingstar.eams.teach.program.service.impl.SubstituteGradeFilter;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.grade.CourseGradeComparator;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.model.course.grade.report.StdGrade;
import com.shufe.model.std.Student;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.grade.gp.GradePointService;
import com.shufe.service.course.grade.other.OtherGradeService;
import com.shufe.util.RequestUtil;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

/**
 * 学生个人成绩总表界面响应类
 * 
 * @author chaostone
 */
public class StdGradeReportAction extends RestrictionSupportAction {
    
    protected GradeService gradeService;
    
    protected StdSearchHelper stdSearchHelper;
    
    protected OtherGradeService otherGradeService;
    
    protected GradePointService gradePointService;
    
    protected SubstituteCourseService substituteCourseService;
    
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
        EntityQuery query = stdSearchHelper.buildStdQuery(request);
        addCollection(request, "students", utilService.search(query));
        addCollection(request, "gradeTypes", baseCodeService.getCodes(GradeType.class));
        request.setAttribute("printAt", new Date());
        return forward(request);
    }
    
    /**
     * 生成成绩总表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 找到参数和成绩比较器
        GradeReportSetting setting = new GradeReportSetting();
        RequestUtil.populate(request, setting, "reportSetting");
        if (StringUtils.isEmpty(setting.getOrder().getProperty())) {
            setting.getOrder().setProperty("calendar.yearTerm");
            setting.getOrder().setDirection(Order.ASC);
        }
        // 默认80
        if (setting.getPageSize().intValue() < 0) {
            setting.setPageSize(new Integer(80));
        }
        
        CourseGradeComparator cmp = new CourseGradeComparator(setting.getOrder().getProperty(),
                Order.ASC == setting.getOrder().getDirection(), baseCodeService
                        .getCodes(GradeType.class));
        
        // 找到学生
        
        // 得到学生毕业审核数据
        Long[] studentIds = SeqStringUtil.transformToLong(get(request, "stdIds"));
        EntityQuery query = new EntityQuery(AuditResult.class, "auditResult");
        query.add(new Condition("auditResult.std.id in (:stdIds)", studentIds));
        Map auditResultMap = new HashMap();
        for (Iterator it = utilService.search(query).iterator(); it.hasNext();) {
            AuditResult auditResult = (AuditResult) it.next();
            auditResultMap.put(auditResult.getStd().getId().toString(), auditResult);
        }
        
        // 成绩样板
        CourseGrade example = new CourseGrade();
        example.setMajorType(setting.getMajorType());
        if (Boolean.TRUE.equals(setting.published)) {
            example.setStatus(new Integer(Grade.PUBLISHED));
        }
        // 制作报表
        List stdGradeReports = new ArrayList();
        Map otherExamReportMap = new HashMap();
        Map otherReportMap = new HashMap();
        Map stdGPMap = new HashMap();
        // 学生论文集合
        Map thesisSubjectMap = new HashMap();
        List stdList = utilService.load(Student.class, "id", studentIds);
        for (Iterator iter = stdList.iterator(); iter.hasNext();) {
            Student std = (Student) iter.next();
            example.setStd(std);
            List grades = gradeService.getCourseGrades(example);
            if (setting.getGradePrintType().equals(GradeReportSetting.BEST_GRADE)) {
                GradeFilter filter = new SubstituteGradeFilter(substituteCourseService
                        .getStdSubstituteCourses(std, setting.getMajorType()));
                grades = filter.filter(grades);
                setting.setGradePrintType(GradeReportSetting.PASS_GRADE);
            }
            StdGrade stdGrade = new StdGrade(std, grades, cmp, setting.getGradePrintType());
            stdGradeReports.add(stdGrade);
            String bz = "";
            if (Boolean.TRUE.equals(setting.getBz())) {
                bz = "Y";
            } else {
                bz = "N";
            }
            request.setAttribute("bz", bz);
            // 学生论文
            List thesisSubjectList = utilService
                    .searchHQLQuery("select thesis.name from Thesis thesis where thesis.student.id="
                            + std.getId() +" and thesis.thesisManage.majorType.id="+setting.getMajorType().getId());
            Object thesisSubject = "";
            if (thesisSubjectList.size() != 0) {
                thesisSubject = thesisSubjectList.get(0);
            }
            thesisSubjectMap.put(std.getCode(), thesisSubject);
            
            // 校外考试
            if (Boolean.TRUE.equals(setting.getPrintOtherGrade())) {
                otherExamReportMap.put(std.getId().toString(), otherGradeService.getOtherGrades(
                        std, Boolean.TRUE));
            }
            List otherGradeList = (List) utilService.load(OtherGrade.class, "std.id", std.getId());
            if (!CollectionUtils.isEmpty(otherGradeList)) {
                otherReportMap.put(std.getId().toString(), otherGradeList.get(0));
            }
            
            // 学期绩点;
            if (Boolean.TRUE.equals(setting.getPrintTermGP())) {
                stdGPMap.put(std.getId().toString(), gradePointService.statStdGPA(std, stdGrade
                        .getGrades()));
            }
        }
        request.setAttribute("setting", setting);
        
        // 对多个报表进行排序
        List orders = OrderUtils.parser(request.getParameter("orderBy"));
        if (!orders.isEmpty()) {
            Order order = (Order) orders.get(0);
            PropertyComparator orderCmp = new PropertyComparator(order.getProperty(),
                    Order.ASC == order.getDirection());
            Collections.sort(stdGradeReports, orderCmp);
        } else {
            String properties = "std.firstMajor.name, std.firstMajorClass.name, std.code";
            MultiPropertyComparator multiCmp = new MultiPropertyComparator(properties);
            Collections.sort(stdGradeReports, multiCmp);
        }
        addCollection(request, "stdGradeReports", stdGradeReports);
        request.setAttribute("otherExamReportMap", otherExamReportMap);
        request.setAttribute("otherReportMap", otherReportMap);
        request.setAttribute("thesisSubjectMap", thesisSubjectMap);
        request.setAttribute("stdGPMap", stdGPMap);
        request.setAttribute("auditResultMap", auditResultMap);
        request.setAttribute("useragent",request.getHeader("USER-AGENT"));
        request.setAttribute("now", new Date());
        return forward(request);
    }
    
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
        this.stdSearchHelper = stdSearchHelper;
    }
    
    public void setOtherGradeService(OtherGradeService otherGradeService) {
        this.otherGradeService = otherGradeService;
    }
    
    public void setGradePointService(GradePointService gradePointService) {
        this.gradePointService = gradePointService;
    }
    
    public void setSubstituteCourseService(SubstituteCourseService substituteCourseService) {
        this.substituteCourseService = substituteCourseService;
    }
    
}
