package com.shufe.web.action.degree.tutorManager.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.degree.tutorManager.Assistant;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * 助教管理
 * 
 * @author 塞外狂人,chaostone
 * 
 */
public class AssistantStdAction extends RestrictionSupportAction {
	/**
	 * 学生登陆时查看自己的助教信息
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
		EntityQuery query = new EntityQuery(Assistant.class, "assistant");
		query.add(new Condition("assistant.std.code =:code",
				getUser(request.getSession()).getName()));
		query.setLimit(getPageLimit(request));
		addCollection(request, "assistants", utilService.search(query));
		return forward(request);
	}

}
