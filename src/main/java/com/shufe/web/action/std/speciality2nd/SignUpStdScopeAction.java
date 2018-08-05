//$Id: SignUpStdScopeAction.java,v 1.1 2007-5-6 下午04:54:57 chaostone Exp $
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

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.std.speciality2nd.SignUpStdScope;
import com.shufe.web.action.common.RestrictionExampleTemplateAction;

/**
 * 二专业报名范围修改响应类
 * 
 * @author chaostone
 * 
 */
public class SignUpStdScopeAction extends RestrictionExampleTemplateAction {

	protected void editSetting(HttpServletRequest request, Entity entity) throws Exception {
		setDataRealm(request, hasStdTypeCollege);
	}

	protected ActionForward saveAndForwad(HttpServletRequest request, Entity entity)
			throws Exception {
		try {
			// 检查重复性
			EntityQuery query = new EntityQuery(SignUpStdScope.class, "stdScope");
			populateConditions(request, query, "stdScope.id");
			List list = (List) utilService.search(query);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SignUpStdScope scope = (SignUpStdScope) iterator.next();
				if (null == entity.getEntityId()
						|| (!entity.getEntityId().equals(scope.getId()) && scope.isSame(entity))) {
					return forward(request, new Action("", "edit"), "error.model.existed");
				}
			}
			utilService.saveOrUpdate(entity);
		} catch (Exception e) {
			return redirect(request, "search", "info.save.failure");
		}
		return redirect(request, "search", "info.save.success");
	}

}
