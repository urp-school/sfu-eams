//$Id: DegreeStdAction.java,v 1.10 2007/01/17 07:21:56 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-10-26         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.apply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.shufe.model.degree.apply.DegreeApply;
import com.shufe.model.degree.study.StudyThesis;
import com.shufe.model.degree.subject.Level1Subject;
import com.shufe.model.degree.subject.Level2Subject;
import com.shufe.model.degree.thesis.ThesisManage;
import com.shufe.model.std.Student;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.degree.apply.DegreeService;
import com.shufe.service.degree.study.StudyProductService;
import com.shufe.service.degree.thesis.ThesisManageService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

public class DegreeApplyStdAction extends RestrictionSupportAction {
    
    private ThesisManageService thesisManageService;
    
    private GradeService gradeService;
    
    private StudyProductService studyProductService;
    
    private DegreeService degreeService;
    
    /**
     * 学生填写学位申请表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward degreeApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Student student = getStudentFromSession(request.getSession());
        ThesisManage thesisManage = thesisManageService.getThesisManage(student);
        if (null != thesisManage) {
            request.setAttribute("thesisManage", thesisManage);
        }
        request.setAttribute("student", student);
        if (null == student.getFirstMajor()) {
            request.setAttribute("reason", "error.degreeApply.noFirstMajor");
            return forward(request, "error");
        }
        List subject2s = utilService.load(Level2Subject.class, "speciality.id", student
                .getFirstMajor().getId());
        Level1Subject level1 = new Level1Subject();
        if (subject2s.size() > 0) {
            level1 = ((Level2Subject) subject2s.get(0)).getLevel1Subject();
        }
        request.setAttribute("level1Subject", level1);
        request.setAttribute("flag", "std");
        // 总学分
        request.setAttribute("totleMark", gradeService.getCredit(student.getCode()));
        // 学位课学分
        request.setAttribute("speciaMark", gradeService.getCredit(student.getCode()));// 学位课学分
        request.setAttribute("integrationGrad", new Float(0));// TODO 综合考试成绩.
        request.setAttribute("studyResults", studyProductService.getStudyProducts(student.getId(),
                Boolean.FALSE, null));
        
        DegreeApply degreeApply = null;
        List degreeApplys = utilService.load(DegreeApply.class, "student.id", student.getId());
        if (degreeApplys.size() > 0) {
            degreeApply = (DegreeApply) degreeApplys.get(0);
        }
        if (null != degreeApply) {
            request.setAttribute("degreeApply", degreeApply);
        }
        
        EntityQuery query = new EntityQuery(Level2Subject.class, "level2Subject");
        query.add(new Condition("level2Subject.speciality.id = (:specialityId)", student
                .getFirstMajor().getId()));
        List list = (List) utilService.search(query);
        if (!list.isEmpty()) {
            Level2Subject level2Subject = (Level2Subject) list.get(0);
            request.setAttribute("level2Subject", level2Subject);
        }
        String returnPage = degreeService.returnDegreeApplyDataPage(student.getType());
        if (StringUtils.isEmpty(returnPage)) {
            request.setAttribute("reason", "error.degreeApply.nostdType");
            returnPage = "error";
        }
        return forward(request, returnPage);
    }
    
    /**
     * 查看博士学位申请人情况表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward degreeCheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Student student = getStudentFromSession(request.getSession());
        List list = (List) utilService.load(DegreeApply.class, "student.id", student.getId());
        DegreeApply degreeApply = (DegreeApply) list.get(0);
        request.setAttribute("student", student);
        request.setAttribute("degreeApply", degreeApply);
        request.setAttribute("studyThesises", utilService.load(StudyThesis.class, new String[] {
                "student.id", "isPassCheck" }, new Object[] { student.getId(), Boolean.TRUE }));
        return forward(request, "doctorDegreeCheck");
    }
    
    /**
     * 保存学生的学位申请信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doSaveDegreeApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long degreeApplyId = getLong(request, "degreeApply.id");
        DegreeApply degreeApply = new DegreeApply();
        if (null != degreeApplyId) {
            degreeApply = (DegreeApply) utilService.load(DegreeApply.class, degreeApplyId);
        }
        Map valueMap = RequestUtils.getParams(request, "degreeApply");
        EntityUtils.populate(valueMap, degreeApply);
        degreeApply.setCommitOn(new java.sql.Date(System.currentTimeMillis()));
        degreeApply.setIsAgree(Boolean.FALSE);
        EntityUtils.evictEmptyProperty(degreeApply);
        utilService.saveOrUpdate(degreeApply);
        return redirect(request, "degreeApply", "field.answer.std.applySuccess");
    }
    
    protected void configExportContext(HttpServletRequest request, Context context) {
        String templateType = request.getParameter("templateType");
        context.put("systemConfig", getSystemConfig());
        if ("degreeTemplate".equals(templateType)) {
            EntityQuery query = buildQuery(request);
            query.setLimit(null);
            context.put("degreeApplys", utilService.search(query));
            
        } else if ("degreeInfo".equals(templateType) || "doctorDegreeCheck".equals(templateType)) {
            Long degreeApplyId = getLong(request, "degreeApplyId");
            DegreeApply degreeApply = (DegreeApply) utilService.load(DegreeApply.class,
                    degreeApplyId);
            Student student = degreeApply.getStudent();
            context.put("student", student);
            context.put("thesisManage", degreeApply.getThesisManage());
            if ("doctorDegreeCheck".equals(templateType)) {
                context.put("studyThesiss", utilService.load(StudyThesis.class, new String[] {
                        "student.id", "isPassCheck" },
                        new Object[] { student.getId(), Boolean.TRUE }));
            }
            EntityQuery query = new EntityQuery(Level2Subject.class, "level2Subject");
            query.add(new Condition("level2Subject.speciality.id = (:specialityId)", student
                    .getFirstMajor().getId()));
            List list = (List) utilService.search(query);
            if (!list.isEmpty()) {
                Level2Subject level2Subject = (Level2Subject) list.get(0);
                context.put("level2Subject", level2Subject);
            }
        }
    }
    
    private EntityQuery buildQuery(HttpServletRequest request) {
        EntityQuery query = new EntityQuery(DegreeApply.class, "degreeApply");
        populateConditions(request, query, "degreeApply.student.type.id");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date sdate = null, edate = null;
        try {
            if (StringUtils.isNotBlank(startTime)) {
                sdate = df.parse(startTime);
            }
            // 截至日期增加一天
            if (StringUtils.isNotBlank(endTime)) {
                edate = df.parse(endTime);
                Calendar gc = new GregorianCalendar();
                gc.setTime(edate);
                gc.set(Calendar.DAY_OF_YEAR, gc.get(Calendar.DAY_OF_YEAR) + 1);
                edate = gc.getTime();
            }
        } catch (ParseException e) {
            log.error(e);
        }
        
        if (null != sdate && null == edate) {
            query.add(new Condition("degreeApply.commitOn >=:sdate", sdate));
        } else if (null != sdate && null != edate) {
            query
                    .add(new Condition(
                            "degreeApply.commitOn >=:sdate and degreeApply.commitOn <:edate",
                            sdate, edate));
        } else if (null == sdate && null != edate) {
            query.add(new Condition("degreeApply.commitOn <:edate", edate));
        }
        
        DataRealmUtils.addDataRealms(query, new String[] { "degreeApply.student.type.id",
                "degreeApply.student.department.id" }, getDataRealmsWith(getLong(request,
                "degreeApply.student.type.id"), request));
        query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
        query.setLimit(getPageLimit(request));
        return query;
    }
    
    /**
     * @param gradeService The gradeService to set.
     */
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    
    /**
     * @param thesisManageService The thesisManageService to set.
     */
    public void setThesisManageService(ThesisManageService thesisManageService) {
        this.thesisManageService = thesisManageService;
    }
    
    /**
     * @param studyProductService The studyProductService to set.
     */
    public void setStudyProductService(StudyProductService studyProductService) {
        this.studyProductService = studyProductService;
    }

    
    public void setDegreeService(DegreeService degreeService) {
        this.degreeService = degreeService;
    }
    
}
