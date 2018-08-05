/**
 * 
 */

package com.shufe.web.action.course.arrange.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.shufe.model.course.arrange.resource.RoomPriceCatalogue;
import com.shufe.model.course.arrange.resource.RoomPriceConfig;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.web.action.common.RestrictionSupportAction;

/**
 * @author zq
 */
public class RoomPriceCatalogueAction extends RestrictionSupportAction {

	/**
	 * 教室价目差价表主界面
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
		addCollection(request, "roomPrices", utilService.loadAll(RoomPriceCatalogue.class));
		return forward(request);
	}

	/**
	 * 教室价目差价表主界面
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
		request.setAttribute("roomPriceCatalogue", utilService.load(RoomPriceCatalogue.class,
				getLong(request, "roomPriceId")));
		addCollection(request, "roomConfigTypes", baseCodeService.getCodes(ClassroomType.class));
		return forward(request);
	}

	/**
	 * 教室价目差价表主界面
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
		Long roomPriceId = getLong(request, "roomPriceCatalogueId");
		// 发布部门
		List departments = departmentService.getDepartments();
		addCollection(request, "departments", departments);
		if (null == roomPriceId) {
			addCollection(request, "toSelectDeparts", departments);
			request.setAttribute("isDefaultSetting", getBoolean(request, "defaultSetting"));
		} else {
			RoomPriceCatalogue roomPrice = (RoomPriceCatalogue) utilService.load(
					RoomPriceCatalogue.class, roomPriceId);
			request.setAttribute("roomPriceCatalogue", roomPrice);
			if (null == roomPrice.getSchoolDistrict()) {
				request.setAttribute("isDefaultSetting", Boolean.TRUE);
			}
			// 审核部门
			addCollection(request, "toSelectDeparts", CollectionUtils.subtract(departments,
					roomPrice.getAuditDeparts()));
		}
		addCollection(request, "schoolDistricts", baseInfoService
				.getBaseInfos(SchoolDistrict.class));
		request.setAttribute("nowAt", new Date());
		return forward(request);
	}

	/**
	 * 查看指定价目表信息
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
		request.setAttribute("roomPriceCatalogue", utilService.load(RoomPriceCatalogue.class,
				getLong(request, "roomPriceCatalogueId")));
		return forward(request);
	}

	/**
	 * 删除指定价目表
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
		utilService.remove(utilService.load(RoomPriceCatalogue.class, getLong(request,
				"roomPriceCatalogueId")));
		return redirect(request, "index", "info.action.success");
	}

	/**
	 * 保存差价表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveCatalogue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RoomPriceCatalogue catalogue = (RoomPriceCatalogue) populateEntity(request,
				RoomPriceCatalogue.class, "catalogue");
		Long[] selectedDepartmentIds = SeqStringUtil.transformToLong(request
				.getParameter("selectedDepartmentIds"));
		catalogue.getAuditDeparts().clear();
		catalogue.getAuditDeparts().addAll(
				utilService.load(Department.class, "id", selectedDepartmentIds));

		utilService.saveOrUpdate(catalogue);
		return redirect(request, "index", "info.save.success");
	}

	/**
	 * 保存每类教室的价目配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RoomPriceConfig config = (RoomPriceConfig) populateEntity(request, RoomPriceConfig.class,
				"roomPriceConfig");
		utilService.saveOrUpdate(config);

		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 删除指定的教室价目配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RoomPriceConfig config = (RoomPriceConfig) populateEntity(request, RoomPriceConfig.class,
				"roomPriceConfig");
		utilService.remove(config);
		return redirect(request, "search", "info.action.success");
	}

	/**
	 * 打印的教室价目配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward priceReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List catalogues = utilService.loadAll(RoomPriceCatalogue.class);
		addCollection(request, "catalogues", catalogues);
		return forward(request);
	}
}
