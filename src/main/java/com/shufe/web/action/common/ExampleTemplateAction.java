//$Id: DAction.java,v 1.1 2007-4-10 下午03:03:48 chaostone Exp $
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
 *chaostone      2007-4-10         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;

public class ExampleTemplateAction extends DispatchBasicAction {

	protected String entityName;

	protected Class entityClass;

	protected Long getEntityLongId(HttpServletRequest request) {
		Long id = getLong(request, entityName + "Id");
		if (null == id) {
			return getLong(request, entityName + ".id");
		} else {
			return id;
		}
	}

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
		indexSetting(request);
		return forward(request);
	}

	/**
	 * 查找标准
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
		addCollection(request, entityName + "s", utilService.search(buildQuery(request)));
		return forward(request);
	}

	protected Collection getExportDatas(HttpServletRequest request) {
		EntityQuery query = buildQuery(request);
		query.setLimit(null);
		return utilService.search(query);
	}

	/**
	 * 修改标准
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
		Long entityId = getLong(request, entityName + "Id");
		Entity entity = null;
		if (null == entityId) {
			Map params = getParams(request, entityName);
			entity = (Entity) populate(request, entityClass, entityName);
			populate(params, entity);
		} else {
			entity = (Entity) utilService.get(entityClass, entityId);
		}
		request.setAttribute(entityName, entity);
		editSetting(request, entity);
		return forward(request);
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
		Long entityId = getLong(request, entityName + "Id");
		Collection entities = null;
		if (null == entityId) {
			String entityIdSeq = request.getParameter(entityName + "Ids");
			entities = utilService.load(entityClass, "id", SeqStringUtil
					.transformToLong(entityIdSeq));
		} else {
			Entity entity = (Entity) utilService.get(entityClass, entityId);
			entities = Collections.singletonList(entity);
		}
		return removeAndForwad(request, entities);
	}

	/**
	 * 保存修改后的标准
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
		Entity entity = populateEntity(request, entityClass, entityName);
		return saveAndForwad(request, entity);
	}

	/**
	 * 查看标准信息
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
		Long entityId = getLong(request, entityName + "Id");
		Entity entity = (Entity) utilService.get(entityClass, entityId);
		request.setAttribute(entityName, entity);
		return forward(request);
	}

	protected void indexSetting(HttpServletRequest request) throws Exception {
		;
	}

	protected void editSetting(HttpServletRequest request, Entity entity) throws Exception {
		;
	}

	protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
			throws Exception {
		try {
			utilService.saveOrUpdate(entity);
		} catch (Exception e) {
			log.error("save error",e);
			return redirect(request, "search", "info.save.failure");
		}
		return redirect(request, "search", "info.save.success");
	}

	protected ActionForward removeAndForwad(HttpServletRequest request, Collection entities)
			throws Exception {
		try {
			utilService.remove(entities);
		} catch (Exception e) {
			return redirect(request, "search", "info.delete.failure");
		}
		return redirect(request, "search", "info.delete.success");
	}

	/**
	 * 模板学生查询
	 * 
	 * @param request
	 * @return
	 */
	protected EntityQuery buildQuery(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(entityClass, getEntityName());
		populateConditions(request, query);
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		query.setLimit(getPageLimit(request));
		return query;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
