//$Id: AuditStandardAction.java,v 1.1 2008-4-9 下午02:56:43 zhouqi Exp $
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
 * zhouqi              2008-4-9         	Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.std.graduation.audit.web.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.std.graduation.audit.model.AuditStandard;
import com.ekingstar.eams.system.basecode.industry.AuditType;
import com.ekingstar.eams.system.basecode.industry.CourseType;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.industry.PunishmentType;
import com.shufe.model.std.Student;

/**
 * 毕业审核－标准维护
 * 
 * @author zhouqi
 */
public class AuditStandardAction extends AuditStandardSearchAction {

	/**
	 * 新增毕业审核标准
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Long auditStandardId = getLong(request, "auditStandardId");
		if (null != auditStandardId) {
			Results.addObject("auditStandard", utilService.load(AuditStandard.class,
					auditStandardId));
		}
		setDataRealm(request, hasStdType);
		initBaseCodes(request, "courseTypes", CourseType.class);
		return forward(request);
	}

	/**
	 * FIXME 没有使用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateFrame(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String auditStandardIdString = request.getParameter("auditStandardId");
		Long auditStandardId = (auditStandardIdString == null || auditStandardIdString.equals("")) ? (request
				.getAttribute("auditStandardId") == null ? new Long(0) : (Long) request
				.getAttribute("auditStandardId"))
				: Long.valueOf(auditStandardIdString);
		Results.addObject("auditStandard", utilService.load(AuditStandard.class, auditStandardId));
		return this.forward(mapping, request, "updateFrame");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AuditStandard auditStandard = (AuditStandard) populateEntity(request, AuditStandard.class,
				"auditStandard");
		// 不审核课程类别
		Set disauditCourseTypeSet = new HashSet();
		String[] disauditCourseTypeIdArray = StringUtils.split(request
				.getParameter("disauditCourseTypeId"), ",");
		for (int i = 0; i < disauditCourseTypeIdArray.length; i++) {
			disauditCourseTypeSet.add(new CourseType(new Long(disauditCourseTypeIdArray[i])));
		}
		auditStandard.setDisauditCourseTypes(disauditCourseTypeSet);

		// 转换课程类别
		Set convertableCourseTypeSet = new HashSet();
		String[] convertableCourseTypeIdArray = StringUtils.split(request
				.getParameter("convertableCourseTypeId"), ",");
		for (int i = 0; i < convertableCourseTypeIdArray.length; i++) {
			convertableCourseTypeSet.add(new CourseType(new Long(convertableCourseTypeIdArray[i])));
		}
		auditStandard.setConvertableCourseTypes(convertableCourseTypeSet);

		utilService.saveOrUpdate(auditStandard);

		Results.addObject("auditTypeList", utilService.loadAll(AuditType.class));
		Results.addObject("punishmentTypeList", utilService.loadAll(PunishmentType.class));
		Results.addObject("auditStandard", auditStandard);
		Results.addObject("courseTypeDescriptions", request.getParameter("courseTypeDescriptions"));
		return redirect(request, "search", "info.update.success");
	}

	/**
	 * 删除毕业审核标准
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long[] auditStandardIds = SeqStringUtil.transformToLong(get(request, "auditStandardIds"));
		List auditStandardList = utilService.load(AuditStandard.class, "id", auditStandardIds);
		for (Iterator iter = auditStandardList.iterator(); iter.hasNext();) {
			AuditStandard auditStandard = (AuditStandard) iter.next();
			utilService.remove(auditStandard);
		}
		return redirect(request, "search", "info.action.success");
	}

	/**
	 * 管理人员查看
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward detailById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AuditStandard auditStandard = (AuditStandard) utilService.load(AuditStandard.class, Long
				.valueOf(request.getParameter("auditStandardId")));
		Results.addObject("auditStandard", auditStandard);
		return forward(mapping, request, "detail");
	}

	/**
	 * 学生查看
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Student student = (Student) utilService.load(Student.class, "code", getLoginName(request))
				.get(0);

		Map auditStandardMap = this.auditStandardService.searchStudentAuditStandard(student,
				AuditType.PLAN_AUDIT);
		if (!auditStandardMap.isEmpty())
			Results.addObject("auditStandard", auditStandardMap.get(MajorType.FIRST));
		return this.forward(mapping, request, "detail");
	}

}
