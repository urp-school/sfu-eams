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
 * chaostone             2006-7-13            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.system.calendar;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.predicate.ValidEntityKeyPredicate;
import com.ekingstar.eams.system.baseinfo.model.StudentType;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.security.User;
import com.ekingstar.security.management.RoleManager;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.calendar.TimeSetting;
import com.shufe.service.system.calendar.TimeSettingService;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 教学日历时间设置响应类
 * 
 * @author chaostone
 * 
 */
public class TimeSettingAction extends RestrictionSupportAction {

	private TimeSettingService settingService;

	/**
	 * 浏览和管理时间设置列表
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
		List settingList = utilService.loadAll(TimeSetting.class);
		request.setAttribute("settingList", settingList);
		return forward(request);
	}

	/**
	 * 修改一个时间设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long settingId = getLong(request, "timeSetting.id");
		TimeSetting setting = null;
		if (null == settingId) {
			List stdTypeList = studentTypeService.getStudentTypes(getStdTypeIdSeq(request));
			request.setAttribute("stdTypeList", stdTypeList);
			setting = new TimeSetting();
			TimeSetting defaultSetting = settingService.getDefaultSetting();
			for (Iterator iter = defaultSetting.getCourseUnits().iterator(); iter.hasNext();) {
				CourseUnit element = (CourseUnit) iter.next();
				CourseUnit newUnit = (CourseUnit) BeanUtilsBean.getInstance().cloneBean(element);
				newUnit.setId(null);
				setting.getCourseUnits().add(newUnit);
			}
		} else {
			setting = (TimeSetting) utilService.get(TimeSetting.class, settingId);
			if (null == setting)
				return forwardError(mapping, request, "error.model.notExist");
			if (!inAuthority(request, setting))
				return forwardError(mapping, request, "error.dataRealm.insufficient");
		}
		request.setAttribute("timeSetting", setting);
		request.setAttribute("stdTypeList", getStdTypes(request));
		return forward(request);
	}

	/**
	 * 查看时间设置的详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long settingId = getLong(request, "timeSetting.id");
		if (null == settingId)
			return forwardError(mapping, request, "error.model.id.needed");
		else {
			TimeSetting setting = (TimeSetting) utilService.get(TimeSetting.class, settingId);
			if (null == setting)
				return forwardError(mapping, request, "error.model.notExist");
			request.setAttribute("timeSetting", setting);
			Boolean inAuthority = Boolean.valueOf(inAuthority(request, setting));
			request.setAttribute("inAuthority", inAuthority);
			return forward(request);
		}
	}

	/**
	 * 保存或更新时间设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long settingId = getLong(request, "timeSetting.id");
		TimeSetting setting = null;
		boolean isNew = false;
		if (!ValidEntityKeyPredicate.INSTANCE.evaluate(settingId)) {
			setting = new TimeSetting();
			isNew = true;
		} else {
			setting = (TimeSetting) utilService.get(TimeSetting.class, settingId);
			if (null == setting)
				return forwardError(mapping, request, "error.model.notExist");
			if (!inAuthority(request, setting))
				return forwardError(mapping, request, "error.dataRealm.insufficient");
		}
		// 保存每一个小节
		for (int i = 0; i < TeachCalendar.MAXUNITS; i++) {
			Long unitId = getLong(request, i + "id");
			CourseUnit unit = null;
			if (ValidEntityKeyPredicate.INSTANCE.evaluate(unitId)) {
				unit = (CourseUnit) utilService.get(CourseUnit.class, unitId);
			} else {
				unit = new CourseUnit();
			}
			unit.setName(request.getParameter(i + "name"));
			unit.setIndex(Integer.valueOf(request.getParameter(i + "index")));
			unit.setEngName(request.getParameter(i + "engName"));
			unit.setStartTime(CourseUnit.getTimeNumber(request.getParameter(i + "startTime")));
			unit.setFinishTime(CourseUnit.getTimeNumber(request.getParameter(i + "finishTime")));
			unit.setSegNo(getInteger(request, i + "segNo"));
			if (unit.getId() == null) {
				setting.getCourseUnits().add(unit);
				unit.setTimeSetting(setting);
			}
		}
		// 设置名称和相关的学生类别
		setting.setName(request.getParameter("timeSetting.name"));
		Long stdTypeId = getLong(request, "timeSetting.stdType.id");
		if (null == stdTypeId)
			setting.setStdType(null);
		else {
			setting.setStdType(new StudentType(stdTypeId));
		}
		settingService.saveTimeSetting(setting);

		if (isNew) {
			return redirect(request, "index", "info.save.success");
		} else {
			return redirect(request, "info", "info.save.success", "&timeSetting.id="
					+ setting.getId());
		}
	}

	/**
	 * 删除时间设置
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
		Long settingId = getLong(request, "timeSetting.id");
		TimeSetting setting = null;
		if (null == settingId) {
			return forwardError(mapping, request, "error.model.id.needed");
		} else {
			setting = (TimeSetting) utilService.get(TimeSetting.class, settingId);
			if (null == setting)
				return forwardError(mapping, request, "error.model.notExist");
			if (!inAuthority(request, setting))
				return forwardError(mapping, request, "error.dataRealm.insufficient");
		}
		String deleteMsg;
		if (settingId.equals(TimeSetting.DEFAULT_ID))
			deleteMsg = "error.timeSetting.defaultCannotbeDeleted";
		else {
			deleteMsg = "info.delete.success";
			try {
				settingService.removeTimeSetting(setting);
			} catch (Exception e) {
				deleteMsg = "info.delete.failure";
			}
		}
		return redirect(request, "index", deleteMsg);
	}

	public void setSettingService(TimeSettingService settingService) {
		this.settingService = settingService;
	}

	private boolean inAuthority(HttpServletRequest request, TimeSetting setting) {
		if (setting.getStdType() == null || setting.getId().equals(TimeSetting.DEFAULT_ID)) {
			Long userId = getUserId(request.getSession());
			User user = (User) utilService.get(User.class, userId);
			if (!((RoleManager)user).isRoleAdmin())
				return false;
		} else {
			if (!DataAuthorityUtil.isInDataRealm(DataAuthorityUtil.predicateWithSimpleName,
					setting, getStdTypeIdSeq(request), ""))
				return false;
		}
		return true;
	}
}
