//$Id: TeachAccidentAction.java,v 1.4 2006/12/30 03:30:12 duanth Exp $
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
 * chenweixiong              2005-11-9         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.quality.accident;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.web.dispatch.Action;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.quality.accident.TeachAccident;
import com.shufe.web.helper.TeachTaskSearchHelper;

public class TeachAccidentAction extends TeachAccidentSearchAction {

	private TeachTaskSearchHelper teachTaskSearchHelper;

	/**
	 * 添加相应的教学事故对象
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeachAccident teachAccident = (TeachAccident) populateEntity(request,
				TeachAccident.class, "teachAccident");
		teachAccident.setModifyAt(new Date(System.currentTimeMillis()));
		utilService.saveOrUpdate(teachAccident);
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 根据得到的序号得到该需要更新的对象
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long entityId = getLong(request, "teachAccidentId");
		TeachAccident entity = null;
		if (null == entityId) {
			Long taskId = getLong(request, "teachAccident.task.id");
			if (null == taskId) {
				return forward(request, new Action(this, "taskList"));
			} else {
				TeachTask task = (TeachTask) utilService.get(
						TeachTask.class, taskId);
				entity = new TeachAccident();
				entity.setTask(task);
			}
		} else {
			entity = (TeachAccident) utilService.get(TeachAccident.class,
					entityId);
		}
		request.setAttribute("teachAccident", entity);
		setDataRealm(request, hasStdTypeDepart);
		return forward(request);
	}

	/**
	 * 删除一个或者多个教学事故对象
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = request.getParameter("teachAccidentIds");
		utilService.remove(utilService.load(TeachAccident.class, "id",
				SeqStringUtil.transformToLong(ids)));
		return redirect(request, "search", "info.delete.success");
	}

	/**
	 * 查询教学任务窗口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward taskList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		addCollection(request, "tasks", teachTaskSearchHelper.searchTask(
				request));
		return forward(request);
	}


	public void setTeachTaskSearchHelper(
			TeachTaskSearchHelper teachTaskSearchHelper) {
		this.teachTaskSearchHelper = teachTaskSearchHelper;
	}

}