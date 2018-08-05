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
 * yangdong             2005-11-9         	Created
 *  
 ********************************************************************************/
package com.shufe.web.action.course.task;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.model.course.task.TaskAlterRequest;
import com.shufe.web.action.common.CalendarRestrictionExampleTemplateAction;

public class AlterRequestAction extends CalendarRestrictionExampleTemplateAction {

	/**
	 * 进入教学课程变更申请管理模块
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setCalendarDataRealm(request, hasStdTypeCollege);
		return forward(request);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String availability = request.getParameter("availability");
		String taskAlterRequestIds = get(request, "taskAlterRequestIds");
		List requests = utilService.load(TaskAlterRequest.class, "id",
				SeqStringUtil.transformToLong(taskAlterRequestIds));
		for (Iterator iter = requests.iterator(); iter.hasNext();) {
			TaskAlterRequest alterRequest = (TaskAlterRequest) iter.next();
			alterRequest.setAvailability(availability);
		}
		utilService.saveOrUpdate(requests);
		return redirect(request, "search", "info.action.success");
	}

}
