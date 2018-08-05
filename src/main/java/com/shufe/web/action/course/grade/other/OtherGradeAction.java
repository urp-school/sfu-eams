//$Id: OtherGradeAction.java,v 1.1 2007-2-24 下午06:35:31 chaostone Exp $
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
 *chaostone      2007-2-24         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.course.grade.other;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.eams.course.grade.converter.ConverterFactory;
import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.shufe.model.course.grade.Grade;
import com.shufe.model.course.grade.other.OtherGrade;
import com.shufe.service.course.grade.other.OtherGradeImportListener;

/**
 * 其他成绩维护响应类
 * 
 * @author chaostone
 * 
 */
public class OtherGradeAction extends OtherGradeSearchAction {

	/**
	 * 修改其他成绩
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long gradeId = getLong(request, "otherGradeId");
		OtherGrade grade = null;
		if (null != gradeId) {
			grade = (OtherGrade) utilService.get(OtherGrade.class, gradeId);
		} else {
			grade = new OtherGrade();
		}
		request.setAttribute("otherGrade", grade);
		addCollection(request, "otherExamCategories", baseCodeService
				.getCodes(OtherExamCategory.class));
		addCollection(request, "markStyles", baseCodeService.getCodes(MarkStyle.class));
		setCalendarDataRealm(request, hasStdType);
		return forward(request);
	}

	/**
	 * 保存其他成绩
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long gradeId = getLong(request, "otherGrade.id");
		OtherGrade grade = null;
		if (null != gradeId) {
			grade = (OtherGrade) utilService.get(OtherGrade.class, gradeId);
		} else {
			grade = new OtherGrade();
			grade.setStatus(new Integer(Grade.PUBLISHED));
		}
		grade.setCalendar(teachCalendarService.getTeachCalendar(getLong(request,
				"calendar.studentType.id"), request.getParameter("calendar.year"), request
				.getParameter("calendar.term")));
		Map params = getParams(request, "otherGrade");
		populate(params, grade);
		String score = request.getParameter("score");
		MarkStyle markStyle = (MarkStyle) utilService.get(MarkStyle.class, grade.getMarkStyle()
				.getId());
		grade.setScore(ConverterFactory.getConverter().convert(score, markStyle));
		utilService.saveOrUpdate(grade);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 删除其他成绩
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long[] gradeIds = SeqStringUtil.transformToLong(request.getParameter("otherGradeIds"));
		List grades = utilService.load(OtherGrade.class, "id", gradeIds);
		utilService.remove(grades);
		return redirect(request, "search", "info.delete.success");
	}

	/**
	 * 导入校外考试成绩
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TransferResult tr = new TransferResult();
		Transfer transfer = ImporterServletSupport.buildEntityImporter(request, OtherGrade.class,
				tr);
		if (null == transfer) {
			return forward(request, "/pages/components/importData/error");
		}
		transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
				new OtherGradeImportListener(utilService.getUtilDao(), teachCalendarService
						.getTeachCalendarDAO()));
		transfer.transfer(tr);
		if (tr.hasErrors()) {
			return forward(request, "/pages/components/importData/error");
		} else {
			return redirect(request, "search", "info.import.success");
		}
	}
}
