//$Id: SignUpSpecialitySettingAction.java,v 1.1 2007-5-6 下午02:38:58 chaostone Exp $
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
 *chaostone      2007-5-6         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std.speciality2nd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.model.std.speciality2nd.SignUpSetting;
import com.shufe.model.std.speciality2nd.SignUpSpecialitySetting;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.service.std.speciality2nd.Speciality2ndSignUpService;
import com.shufe.web.action.common.ExampleTemplateAction;

public class SignUpSpecialitySettingAction extends ExampleTemplateAction {

	Speciality2ndSignUpService speciality2ndSignUpService;

	/**
	 * 查找
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
		Collection specialitySettings = utilService.search(buildQuery(request));
		speciality2ndSignUpService.statStdCountOf(specialitySettings);
		addCollection(request, entityName + "s", specialitySettings);
		return forward(request);
	}

	protected void editSetting(HttpServletRequest request, Entity entity)
			throws Exception {
		EntityQuery entityQuery = new EntityQuery(SpecialityAspect.class,
				"aspect");
		entityQuery
				.add(new Condition(
						"aspect.speciality.majorType.id ="+MajorType.SECOND
								+ " and aspect.id not in "
								+ "(select distinct specialitySetting.aspect.id"
								+ " from SignUpSpecialitySetting specialitySetting where specialitySetting.setting.id=:settingId) ",
						getLong(request, "specialitySetting.setting.id")));
		List aspects = (List) utilService.search(entityQuery);
		CollectionUtils.transform(aspects, new Transformer() {
			public Object transform(Object arg0) {
				SpecialityAspect aspect = (SpecialityAspect) arg0;
				return new SignUpSpecialitySetting(aspect.getSpeciality(),
						aspect);
			}
		});
		addCollection(request, "specialitySettings", aspects);
	}

	/**
	 * 修改一个或多个专业的招生人数上限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editLimits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		addCollection(request, "specialitySettings", utilService.load(
				SignUpSpecialitySetting.class, "id", SeqStringUtil
						.transformToLong(request
								.getParameter("specialitySettingIds"))));
		return forward(request, "form");
	}

	/**
	 * 保存一个或多个专业的招生人数上限<br>
	 * 如果人数上限为null则不保存（不新建或者保存）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveLimits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int count = getInteger(request, "count").intValue();
		Long signUpSettingId = getLong(request, "specialitySetting.setting.id");
		List settings = new ArrayList();
		for (int i = 0; i < count; i++) {
			Long specialitySettingId = getLong(request, "specialitySetting" + i
					+ ".id");
			SignUpSpecialitySetting setting = null;
			if (null != specialitySettingId) {
				setting = (SignUpSpecialitySetting) utilService.get(
						SignUpSpecialitySetting.class, specialitySettingId);
			} else {
				setting = new SignUpSpecialitySetting();
				setting.setSetting(new SignUpSetting(signUpSettingId));
				setting.getSpeciality().setId(
						getLong(request, "specialitySetting" + i
								+ ".speciality.id"));
				setting.getAspect()
						.setId(
								getLong(request, "specialitySetting" + i
										+ ".aspect.id"));
			}
			Integer limit = getInteger(request, "specialitySetting" + i
					+ ".limit");
			if (null != limit) {
				setting.setLimit(limit);
				settings.add(setting);
			}
		}
		utilService.saveOrUpdate(settings);
		return redirect(request, "search", "info.save.success");
	}

	public void setSpeciality2ndSignUpService(
			Speciality2ndSignUpService speciality2ndSignUpService) {
		this.speciality2ndSignUpService = speciality2ndSignUpService;
	}
}
