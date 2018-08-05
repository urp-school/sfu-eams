//$Id: BuildingAction.java,v 1.4 2006/12/30 01:29:01 duanth Exp $
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
 * chaostone            2005-11-12          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.baseinfo.Building;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;

/**
 * 教学楼基础信息管理响应类
 * 
 * @author chaostone
 */
public class BuildingAction extends BaseInfoAction {

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		addCollection(request, "districts", baseInfoService.getBaseInfos(SchoolDistrict.class));
		return forward(request);
	}

	/**
	 * 查找信息action.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		addCollection(request, "buildings", utilService.search(baseInfoSearchHelper
				.buildBuildingQuery(request)));
		return forward(request);
	}

	protected Collection getExportDatas(HttpServletRequest request) {
		EntityQuery query = baseInfoSearchHelper.buildBuildingQuery(request);
		query.setLimit(null);
		return utilService.search(query);
	}

	/**
	 * 修改和新建校区时调用的动作.
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
		Building building = (Building) getEntity(request, Building.class, "building");
		addEntity(request, building);
		addCollection(request, "districtList", baseInfoService.getBaseInfos(SchoolDistrict.class));
		return forward(request);
	}

	/**
	 * 保存校区信息，新建的校区或修改的校区.<br>
	 * 接受主键冲突异常，跳转到异常页面
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
		Long buildingId = getLong(request, "building.id");

		// 检查是否重复
		if (utilService.duplicate(Building.class, buildingId, "code", request
				.getParameter("building.code"))) {
			return forward(request, new Action("", "edit"), "error.code.existed");
		}

		Building building = (Building) populateEntity(request, Building.class, "building");
		try {
			if (building.isVO()) {
				logHelper.info(request, "create a building named " + building.getName());
			} else {
				logHelper.info(request, "update  building named " + building.getName());
			}
			ActionForward f = super.saveOrUpdate(request, building);
			if (null != f)
				return f;
		} catch (PojoExistException e) {
			logHelper.info(request, "Failure occured in save or update  building named "
					+ building.getName(), e);
			return forward(mapping, request, new String[] { "entity.building",
					"error.model.existed" }, "error");
		} catch (Exception e) {
			logHelper.info(request, "Failure occured in save or update  building named "
					+ building.getName(), e);
			return forward(mapping, request, "error.occurred", "error");
		}
		if (null != request.getParameter("addAnother")) {
			return redirect(request, "edit", "info.save.success");
		} else
			return redirect(request, "search", "info.save.success");
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
		Long id = getLong(request, "id");
		try {
			utilService.remove(utilService.get(Building.class, id));
		} catch (Exception e) {
			return redirect(request, "search", "info.delete.failure");
		}
		return redirect(request, "search", "info.delete.success");
	}

	/**
	 * 查看教学楼信息
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
		String buildingId = request.getParameter("building.id");
		if (StringUtils.isEmpty(buildingId))
			return forward(mapping, request, "building.id.NotExist", "failure");
		Building building = (Building) baseInfoService.getBaseInfo(Building.class, Long
				.valueOf(buildingId));
		addEntity(request, building);
		return forward(request);
	}
}
