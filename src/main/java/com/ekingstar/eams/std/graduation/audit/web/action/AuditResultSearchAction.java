//$Id: AuditResultSearchAction.java,v 1.1 2007-4-11 下午09:34:13 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-11         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.graduation.audit.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.ExcelTools;
import com.ekingstar.eams.std.graduation.audit.model.AuditResult;
import com.ekingstar.eams.std.graduation.audit.model.DegreeAuditStandard;
import com.shufe.model.course.grade.CourseGrade;
import com.shufe.model.course.plan.PlanCourse;
import com.shufe.service.course.grade.GradeService;
import com.shufe.service.course.plan.TeachPlanService;
import com.shufe.service.std.graduation.audit.DegreeAuditService;
import com.shufe.web.action.common.RestrictionSupportAction;
import com.shufe.web.helper.StdSearchHelper;

public class AuditResultSearchAction extends RestrictionSupportAction {

	protected StdSearchHelper stdSearchHelper;

	protected DegreeAuditService degreeAuditService;
	
	 /** 其它考试服务类 */
    private GradeService gradeService;
    
    /** 培养计划服务类 */
    private TeachPlanService teachPlanService;

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setTeachPlanService(TeachPlanService teachPlanService) {
		this.teachPlanService = teachPlanService;
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setDataRealm(request, hasStdTypeCollege);
		addCollection(request, "standards", degreeAuditService
				.getAuditStandards(getStdTypes(request)));
		return forward(request);
	}

	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long resultId = getLong(request, "auditResultId");
		Long standardId = getLong(request, "standard.id");
		DegreeAuditStandard standard = (DegreeAuditStandard) utilService.get(
				DegreeAuditStandard.class, standardId);
		AuditResult result = (AuditResult) utilService.load(AuditResult.class, resultId);
		request.setAttribute("auditResult", result);
		if (standard == null) {
			request.setAttribute("auditMsg", "");
		} else {
			String msg = standard.audit(result);
			request.setAttribute("auditMsg", msg);
		}
		List planCourseList = new ArrayList();
		Map degreeGradeMap = new HashMap();
		List degreePlanCourse = teachPlanService.getPlanCourseOfDegree(result.getStd(), result
                .getMajorType());
		for (Iterator iter = degreePlanCourse.iterator(); iter.hasNext();) {
            // 查询各学位课程对应成绩
            PlanCourse planCourse = (PlanCourse) iter.next();
            List gradeList = gradeService.getDegreeCourseGrade(result.getStd().getId(),
                    planCourse.getCourse().getId());
            if(null != gradeList&&gradeList.size()!=0){
            	Float courseGrade = (Float)gradeList.get(0);
            	degreeGradeMap.put(planCourse.getCourse().getId().toString(),courseGrade);
            	planCourseList.add(planCourse);
            }else{
            	planCourseList.add(planCourse);
            }
		}
		request.setAttribute("planCourseList", planCourseList);
		request.setAttribute("degreeGradeMap", degreeGradeMap);
		return forward(request);
	}

	/**
	 * 查询学生注册信息
	 * 
	 * @param form
	 * @param request
	 * @param moduleName
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Boolean audited = getBoolean(request, "audited");
		EntityQuery query = buildQuery(request, audited);
		if (Boolean.TRUE.equals(audited)) {
			addCollection(request, "auditResults", utilService.search(query));
			return forward(request);
		} else {
			addCollection(request, "students", utilService.search(query));
			return forward(request, "stdList");
		}
	}

	/**
	 * @param request
	 * @param audited
	 * @return
	 */
	protected EntityQuery buildQuery(HttpServletRequest request, Boolean audited) {
		Long majorTypeId = getLong(request, "stdMajorTypeId");
		if (null == majorTypeId) {
			majorTypeId = new Long(1);
		}
		EntityQuery query = stdSearchHelper.buildStdQuery(request);
		if (Boolean.TRUE.equals(audited)) {
			query.setAlias("auditResult");
			query.setSelect("auditResult");
			query.setEntityClass(AuditResult.class);
			populateConditions(request, query);
			String from = query.getFrom();
			from = "from AuditResult  auditResult join auditResult.std std "
					+ StringUtils.substringAfter(from, " std");
			Float startCredits = getFloat(request, "startCredits");
			if (null != startCredits) {
				query.add(new Condition("auditResult.credits>=:startCredits", startCredits));
			}
			Float endCredits = getFloat(request, "endCredits");
			if (null != endCredits) {
				query.add(new Condition("auditResult.credits<=:endCredits", endCredits));
			}
			query.setFrom(from);
		} else {
			query
					.add(new Condition(
							"not exists( from AuditResult auditResult where auditResult.std.id=std.id and auditResult.majorType.id=:majorTypeId)",
							majorTypeId));

		}
		return query;
	}

	protected Collection getExportDatas(HttpServletRequest request) {
		Boolean audited = getBoolean(request, "audited");
		EntityQuery query = buildQuery(request, audited);
		query.setLimit(null);
		return utilService.search(query);
	}

	/**
	 * 按审核规则导出学生详细审核结果
	 */
	public ActionForward exportDataWithInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean audited = getBoolean(request, "audited");

		String attrs = get(request, "attrs");
		String attrNames = get(request, "attrNames");
		Long majorTypeId = getLong(request, "stdMajorTypeId");
		if (null == majorTypeId) {
			majorTypeId = new Long(1);
		}
		EntityQuery query = stdSearchHelper.buildStdQuery(request);
		if (Boolean.TRUE.equals(audited)) {
			query.setAlias("degreeAuditInfo");
			query.setSelect("degreeAuditInfo");
			query.setEntityClass(AuditResult.class);
			populateConditions(request, query);
			String from = query.getFrom();
			from = "from DegreeAuditInfo  degreeAuditInfo join degreeAuditInfo.auditResult.std std "
					+ StringUtils.substringAfter(from, " std");
			query.setFrom(from);
		} else {
			query
					.add(new Condition(
							"not exists( from DegreeAuditInfo degreeAuditInfo where degreeAuditInfo.auditResult.std.id=std.id and degreeAuditInfo.auditResult.majorType.id=:majorTypeId)",
							majorTypeId));

		}
		query.setSelect("select " + attrs + " ");
		query.addOrder(OrderUtils.parser("degreeAuditInfo.auditResult.std.code"));
		query.setLimit(null);
		ExcelTools et = new ExcelTools();
		HSSFWorkbook wb = et.toExcel(utilService.search(query), attrNames);
		response.setContentType("application/vnd.ms-excel;charset=GBK");
		response.setHeader("Content-Disposition", "attachment;filename=" + "stdAuditInfo.xls");
		ServletOutputStream sos = response.getOutputStream();
		wb.write(sos);
		sos.close();
		return null;
	}

	public void setStdSearchHelper(StdSearchHelper stdSearchHelper) {
		this.stdSearchHelper = stdSearchHelper;
	}

	public void setDegreeAuditService(DegreeAuditService degreeAuditService) {
		this.degreeAuditService = degreeAuditService;
	}
}
