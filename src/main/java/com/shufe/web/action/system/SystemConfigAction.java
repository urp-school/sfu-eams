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
 * chaostone             2006-10-19            Created
 *  
 ********************************************************************************/
package com.shufe.web.action.system;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.eams.system.config.model.SystemConfigItem;
import com.ekingstar.eams.system.config.service.SystemConfigCustomLoader;
import com.shufe.web.action.common.DispatchBasicAction;

/**
 * 系统参数设置action
 * 
 * @author tx
 * 
 */
public class SystemConfigAction extends DispatchBasicAction {

	private SystemConfigCustomLoader systemConfigLoader;

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EntityQuery query = new EntityQuery(SystemConfigItem.class, "item");
		query.addOrder(new Order("item.name"));
		addCollection(request, "systemConfigItems", utilService.search(query));
		return forward(request);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List SystemConfigItems = utilService.loadAll(SystemConfigItem.class);
		for (Iterator iter = SystemConfigItems.iterator(); iter.hasNext();) {
			SystemConfigItem item = (SystemConfigItem) iter.next();
			String value = request.getParameter("configItem" + item.getId() + ".value");
			if (null != value) {
				item.setValue(value);
			}
		}
		systemConfigLoader.loadConfigItem();
		utilService.saveOrUpdate(SystemConfigItems);
		return redirect(request, "index", "info.save.success");
	}

	public void setSystemConfigLoader(SystemConfigCustomLoader systemConfigLoader) {
		this.systemConfigLoader = systemConfigLoader;
	}

}
