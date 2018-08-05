//$Id: RoleAction.java,v 1.8 2006/12/30 01:29:02 duanth Exp $
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
 * dell                                     Created
 * chaostone            2005-09-29          refactor
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.system.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.model.Entity;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.security.model.EamsRole;
import com.ekingstar.security.UserCategory;
import com.ekingstar.security.access.CachedResourceAccessor;
import com.ekingstar.security.model.AbstractAuthorityObject;

/**
 * 角色信息维护响应类
 * 
 * @author chaostone 2005-9-29
 */
public class RoleAction extends com.ekingstar.security.web.action.RoleAction {
	protected void indexSetting(HttpServletRequest request) throws Exception {
		addCollection(request, "categories", utilService.loadAll(UserCategory.class));
		addCollection(request, "stdTypes", utilService.loadAll(StudentType.class));
		request.setAttribute("STDCATEGORYID",  EamsRole.STD_USER);
	}
    
	protected void editSetting(HttpServletRequest request, Entity entity)
			throws Exception {
		addCollection(request, "categories", utilService.loadAll(UserCategory.class));
		addCollection(request, "stdTypes", utilService.loadAll(StudentType.class));
		request.setAttribute("STDCATEGORYID",  EamsRole.STD_USER);
		
		AbstractAuthorityObject authority = (AbstractAuthorityObject)entity;
		request.setAttribute("authority", authority);
	}
	/**
	 * 访问记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward accessLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CachedResourceAccessor ra = (CachedResourceAccessor) request.getSession()
				.getServletContext().getAttribute("ResourceAccessor");
		List accessLogs = null;
		if (null == ra) {
			accessLogs = Collections.EMPTY_LIST;
		} else {
			accessLogs = new ArrayList(ra.getAccessLogs());
		}
		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isNotEmpty(orderBy)) {
			Collections.sort(accessLogs, new PropertyComparator(orderBy));
		}
		request.setAttribute("accessLogs", accessLogs);
		return forward(request);
	}

}
