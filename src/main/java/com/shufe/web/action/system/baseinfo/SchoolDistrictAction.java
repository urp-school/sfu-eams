//$Id: SchoolDistrictAction.java,v 1.5 2006/12/30 01:29:01 duanth Exp $
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
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;

public class SchoolDistrictAction extends BaseInfoAction {

	/**
	 * 校区信息管理主界面
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
	 * 查找信息action. 参数是从formbean中的校区信息取得的.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EntityQuery query = new EntityQuery(SchoolDistrict.class, "schoolDistrict");
		populateConditions(request, query);
		query.setLimit(getPageLimit(request));
		addCollection(request, "schoolDistricts", utilService.search(query));
		return forward(request);
	}

	protected Collection getExportDatas(HttpServletRequest request) {
		EntityQuery query = new EntityQuery(SchoolDistrict.class, "schoolDistrict");
		populateConditions(request, query);
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
		SchoolDistrict district = (SchoolDistrict) getEntity(request, SchoolDistrict.class,
				"schoolDistrict");
		addEntity(request, district);
		return forward(request);
	}

	/**
	 * 保存校区信息，新建的校区或修改的校区.<br>
	 * 接受主键冲突异常，跳转到异常页面.
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
		Long districtId = getLong(request, "schoolDistrict.id");

		// 检查是否重复
		if (utilService.duplicate(SchoolDistrict.class, districtId, "code", request
				.getParameter("schoolDistrict.code"))) {
			return forward(request, new Action("", "edit"), "error.code.existed");
		}

		SchoolDistrict district = (SchoolDistrict) populateEntity(request, SchoolDistrict.class,
				"schoolDistrict");
		try {
			if (district.isVO()) {
				logHelper.info(request, "Create a School District named" + district.getName());
			} else {
				logHelper.info(request, "Update a School District named" + district.getName());
			}
			ActionForward f = super.saveOrUpdate(request, district);
			if (null != f)
				return f;
		} catch (PojoExistException e) {
			logHelper.info(request, "Failure save or update a School District named"
					+ district.getName(), e);
			return forward(mapping, request, new String[] { "entity.schoolDistrict",
					"error.model.existed" }, "error");
		} catch (Exception e) {
			logHelper.info(request, "Failure save or update a School District named"
					+ district.getName(), e);
			return forward(mapping, request, "error.occurred", "error");
		}
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
			utilService.remove(utilService.get(SchoolDistrict.class, id));
		} catch (Exception e) {
			return redirect(request, "search", "info.delete.failure");
		}
		return redirect(request, "search", "info.delete.success");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String districtId = request.getParameter("schoolDistrict.id");
		if (StringUtils.isEmpty(districtId))
			return forward(mapping, request, new String[] { "entity.schoolDistrict",
					"error.model.id.needed" }, "error");
		SchoolDistrict district = (SchoolDistrict) baseInfoService.getBaseInfo(
				SchoolDistrict.class, Long.valueOf(districtId));
		addEntity(request, district);
		return forward(request);
	}
}
