//$Id: CodeScriptAction.java,v 1.1 2007-2-4 下午08:04:51 chaostone Exp $
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
 *chaostone      2007-2-4         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.system.codeGen;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.system.codeGen.CodeScript;
import com.shufe.service.system.codeGen.CodeGenerator;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 代码生成规则响应类
 * 
 * @author chaostone
 * 
 */
public class CodeScriptAction extends DispatchBasicAction {

	private CodeGenerator codeGenerator;

	/**
	 * 主页面
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
		return forward(request);
	}

	/**
	 * 搜索
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EntityQuery entityQuery = new EntityQuery(CodeScript.class, "codeScript");
		populateConditions(request, entityQuery);
		entityQuery.setLimit(getPageLimit(request));
		entityQuery.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		addCollection(request, "codeScripts", utilService.search(entityQuery));
		return forward(request);
	}

	/**
	 * 编辑
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
		Long codeScriptId = getLong(request, "codeScriptId");
		CodeScript codeScript = null;
		if (null == codeScriptId) {
			codeScript = (CodeScript) populate(request, CodeScript.class);
		} else {
			codeScript = (CodeScript) utilService.get(CodeScript.class, codeScriptId);
		}
		addEntity(request, codeScript);
		return forward(request);
	}

	/**
	 * 查看
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
		Long codeScriptId = getLong(request, "codeScriptId");
		CodeScript codeScript = (CodeScript) utilService.get(CodeScript.class, codeScriptId);
		addEntity(request, codeScript);
		return forward(request);
	}

	/**
	 * 保存
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
		Long codeScriptId = getLong(request, "codeScript.id");
		CodeScript codeScript = null;
		Map params = getParams(request, "codeScript");
		if (null == codeScriptId) {
			codeScript = new CodeScript();
			codeScript.setCreateAt(new Date(System.currentTimeMillis()));
		} else {
			codeScript = (CodeScript) utilService.get(CodeScript.class, codeScriptId);
		}
		codeScript.setModifyAt(new Date(System.currentTimeMillis()));
		populate(params, codeScript);
		// String testResult = codeGenerator.test(null, codeScript);
		// if (!codeGenerator.isValidCode(testResult)) {
		// if (null == testResult) {
		// request.setAttribute("testResult", "null");
		// } else {
		// request.setAttribute("testResult", testResult);
		// }
		// return forward(request, new Action(this.getClass(), "edit"));
		// }
		utilService.saveOrUpdate(codeScript);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward test(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CodeScript codeScript = new CodeScript();
		Map params = getParams(request, "codeScript");
		populate(params, codeScript);
		String testResult = codeGenerator.test(null, codeScript);
		if (null == testResult) {
			testResult = "null";
		}
		request.setAttribute("testResult", testResult);
		return forward(request, new Action(this.getClass(), "edit"));
	}

	/**
	 * 删除
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
		Long codeScriptId = getLong(request, "codeScriptId");
		utilService.remove(utilService.get(CodeScript.class, codeScriptId));
		return redirect(request, "search", "info.delete.success");
	}

	public void setCodeGenerator(CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

}
