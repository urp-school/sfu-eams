package com.shufe.web.action.degree.tutorManager.assistant;

import java.util.HashMap;
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
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.degree.tutorManager.Assistant;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 助教管理
 * 
 * @author 塞外狂人
 * 
 */
public class AssistantTutorAction extends RestrictionSupportAction {

	/**
	 * 助教信息
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
		List stdTypeList = baseInfoService.getBaseInfos(StudentType.class);
		addCollection(request, "stdTypeList", stdTypeList);
		return forward(request);
	}

	/**
	 * 助教信息详细查询
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
		EntityQuery query = new EntityQuery(Assistant.class, "assistant");
		query.add(new Condition("assistant.tutor.code=:code", getUser(
				request.getSession()).getName()));
		query.setLimit(getPageLimit(request));
		addCollection(request, "assistants", utilService.search(query));
		return forward(request);
	}

	/**
	 * 确认和取消确认
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long[] assistantIds = SeqStringUtil.transformToLong(request
				.getParameter("assistantIds"));
		Boolean isConfirm = getBoolean(request, "isConfirm");
		Map valuesMap = new HashMap(1);
		valuesMap.put("isConfirm", isConfirm);
		utilService.update(Assistant.class, "id", assistantIds, valuesMap);
		return redirect(request, "search",
				isConfirm.booleanValue() ? "info.confirm.success"
						: "info.unconfirm.success");
	}
}
