//$Id: InstructWorkloadAction.java,v 1.6 2006/12/19 13:08:41 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload.instruct;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.security.User;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.model.workload.instruct.InstructWorkload;

/**
 * 指导工作量管理统计
 * 
 * @author chaostone
 * 
 */
public class InstructWorkloadAction extends InstructWorkloadSearchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		setCalendarDataRealm(request, hasStdTypeCollege);
		return forward(request, "statHome");
	}

	/**
	 * 统计指导工作量
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward statistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String modulusType = get(request, "instructModulus.itemType");
		TeachCalendar calendar = teachCalendarService.getTeachCalendar(getLong(request,
				"calendarId"));
		DataRealm realm = new DataRealm(request.getParameter("stdTypeIdSeq"), request
				.getParameter("departIdSeq"));
		User user = getUser(request.getSession());
		info(user.getName() + " removed  workload with stdTypeId:" + realm.getStudentTypeIdSeq()
				+ "calendarId:" + calendar.getId());
		instructWorkloadService.statWorkload(calendar, realm, modulusType);
		return forward(request, "statisticFinish");
	}

	/**
	 * 得到更新页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward loadUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("instructWorkload", utilService.load(InstructWorkload.class, getLong(
				request, "instructWorkloadId")));
		return forward(request, "loadUpdate");
	}

	/**
	 * 更新
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		InstructWorkload instructWorkload = (InstructWorkload) utilService.get(
				InstructWorkload.class, getLong(request, "instructWorkloadId"));
		Map propertiesMap = RequestUtils.getParams(request, "instructWorkload");
		EntityUtils.populate(propertiesMap, instructWorkload);
		utilService.saveOrUpdate(instructWorkload);
		return redirect(request, "search", "info.update.success");
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
	public ActionForward doDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String instructWorkloadIds = request.getParameter("instructWorkloadIds");
		List instructWorkloads = utilService.load(InstructWorkload.class, "id", SeqStringUtil
				.transformToLong(instructWorkloadIds));
		utilService.remove(instructWorkloads);
		return redirect(request, "search", "info.delete.success");
	}

	/**
	 * 
	 * 确认或者取消选择的类别
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doAffirmSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String instructWorkloadIds = request.getParameter("instructWorkloadIds");
		String affirmType = request.getParameter("affirmType");
		String affirmEstate = request.getParameter("affirmEstate");
		instructWorkloadService.affirmType(affirmType, instructWorkloadIds, Boolean.valueOf(
				affirmEstate).booleanValue());
		return redirect(request, "search", "info.confirm.success");
	}

	/**
	 * 
	 * 确认或者取消所有的类别
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doAffirmAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String affirmType = request.getParameter("affirmType");
		String affirmEstate = request.getParameter("affirmEstate");
		instructWorkloadService.affirmALl(getStdTypeIdSeq(request), affirmType, Boolean
				.getBoolean(affirmEstate));
		return redirect(request, "search", "info.confirm.success");
	}
}
