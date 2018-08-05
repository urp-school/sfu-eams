//$Id: OtherExamSignUpSettingAction.java,v 1.1 2007-2-24 下午06:05:57 chaostone Exp $
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.OtherExamCategory;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.grade.other.OtherExamSignUpSetting;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 其他校内外考试报名设置
 * 
 * @author chaostone
 * 
 */
public class OtherExamSignUpSettingAction extends RestrictionSupportAction {

	/**
	 * 列出主界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return forward(request);
	}

	/**
	 * 按条件查询校外考试种类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EntityQuery query = new EntityQuery(OtherExamSignUpSetting.class,
				"setting");
		populateConditions(request, query);
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "settings", utilService.search(query));
		return forward(request);
	}

	/**
	 * 添加活修改其他考试控制页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long settingId = getLong(request, "otherExamSignUpSettingId");
		OtherExamSignUpSetting setting = null;
		if (null != settingId) {
			setting = (OtherExamSignUpSetting) utilService.load(
					OtherExamSignUpSetting.class, settingId);
		} else {
			setting = new OtherExamSignUpSetting();
		}
		request.setAttribute("setting", setting);
		EntityQuery query = new EntityQuery(OtherExamCategory.class, "category");
		query
				.add(new Condition(
						"not exists(from OtherExamSignUpSetting setting where setting.examCategory.id=category.id)"));
		List availExamCategories = (List) utilService.search(query);
		if (setting.isPO()) {
			availExamCategories.add(setting.getExamCategory());
		}
		addCollection(request, "availExamCategories", availExamCategories);
		addCollection(request, "otherExamCategories", baseCodeService
				.getCodes(OtherExamCategory.class));
		List stdTypes = baseInfoService.getBaseInfos(StudentType.class);
		addCollection(request, "stdTypes", stdTypes);
		return forward(request);
	}

	/**
	 * 其他考试种类保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long settingId = getLong(request, "otherExamSignUpSetting.id");
		OtherExamSignUpSetting setting = null;
		if (null == settingId) {
			setting = new OtherExamSignUpSetting();
		} else {
			setting = (OtherExamSignUpSetting) utilService.load(
					OtherExamSignUpSetting.class, settingId);
		}
		Map params = getParams(request, "setting");
		populate(params, setting);
		String freeStdTypeIds = request.getParameter("freeStdTypeIds");
		List freeStdTypes = studentTypeService.getStudentTypes(freeStdTypeIds);
		setting.setFreeStdTypes(new HashSet(freeStdTypes));
		
		//不设定具体时间，则会出现超出预定时间的情况
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String starttime=get(request, "setting.startAt");
		String endtime = get(request, "setting.endAt");
		setting.setStartAt(sdf.parse(starttime + " 00:00:00"));
		setting.setEndAt(sdf.parse(endtime + " 23:59:59"));
		utilService.saveOrUpdate(setting);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 其他考试种类保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String settingIds = request.getParameter("otherExamSignUpSettingIds");
		List settings = utilService.load(OtherExamSignUpSetting.class,
				"id", SeqStringUtil.transformToLong(settingIds));
		utilService.remove(settings);
		return redirect(request, "search", "info.delete.success");
	}
}
