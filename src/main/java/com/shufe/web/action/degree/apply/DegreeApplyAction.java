//$Id: DegreeAction.java,v 1.5 2006/12/30 03:30:12 duanth Exp $
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
 * chenweixiong         2006-10-26          Created
 * zq                   2007-09-17          修改了stdList()方法
 ********************************************************************************/

package com.shufe.web.action.degree.apply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
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
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.eams.system.config.SystemConfigLoader;
import com.shufe.model.degree.apply.DegreeApply;
import com.shufe.model.degree.study.StudyThesis;
import com.shufe.model.degree.subject.Level2Subject;
import com.shufe.model.std.Student;
import com.shufe.service.degree.apply.DegreeService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * @author Administrator 学位申请类 目标：管理学生申请学位数据
 */
public class DegreeApplyAction extends RestrictionSupportAction {
    
    private DegreeService degreeService;
    
    /**
     * 准备查询条件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        setDataRealm(request, hasStdTypeCollege);
        return forward(request);
    }
    
    /**
     * 根据条件得到已经申请学位的学生列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    public ActionForward stdList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        EntityQuery query = buildQuery(request);
        addCollection(request, "degreeApplys", utilService.search(query));
        return forward(request);
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
     * 查看详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        Long degreeApplyId = getLong(request, "degreeApplyId");
        DegreeApply degreeApply = (DegreeApply) utilService.load(DegreeApply.class, degreeApplyId);
        Student student = degreeApply.getStudent();
        request.setAttribute("degreeApply", degreeApply);
        request.setAttribute("flag", "admin");
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
        Long degreeApplyId = getLong(request, "degreeApplyId");
        DegreeApply degreeApply = (DegreeApply) utilService.load(DegreeApply.class, degreeApplyId);
        Student student = degreeApply.getStudent();
        request.setAttribute("student", student);
        request.setAttribute("degreeApply", degreeApply);
        request.setAttribute("studyThesises", utilService.load(StudyThesis.class, new String[] {
                "student.id", "isPassCheck" }, new Object[] { student.getId(), Boolean.TRUE }));
        return forward(request, "doctorDegreeCheck");
    }
    
    /**
     * 学位申请 的确认
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward affirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String degreeApplyIdSeq = get(request, "degreeApplyIds");
        String affirm = get(request, "affirm");
        List degreeApplys = utilService.load(DegreeApply.class, "id", SeqStringUtil
                .transformToLong(degreeApplyIdSeq));
        for (Iterator iter = degreeApplys.iterator(); iter.hasNext();) {
            DegreeApply element = (DegreeApply) iter.next();
            element.setIsAgree(Boolean.valueOf(affirm));
        }
        utilService.saveOrUpdate(degreeApplys);
        return redirect(request, "stdList", "info.action.success");
    }
    
    /**
     * @see com.shufe.web.action.common.DispatchBasicAction#configExportContext(javax.servlet.http.HttpServletRequest,
     *      com.ekingstar.commons.transfer.exporter.Context)
     */
    protected void configExportContext(HttpServletRequest request, Context context) {
        String templateType = request.getParameter("templateType");
        context.put("systemConfig", getSystemConfig());
        if ("degreeTemplate".equals(templateType)) {
            EntityQuery query = buildQuery(request);
            query.setLimit(null);
            context.put("degreeApplys", utilService.search(query));
            context.put("systemConfig", SystemConfigLoader.getConfig());
            
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
    
    /**
     * @param degreeService The degreeService to set.
     */
    public void setDegreeService(DegreeService degreeService) {
        this.degreeService = degreeService;
    }
}
