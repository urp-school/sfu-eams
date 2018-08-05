//$Id: FilePrintManageAction.java,v 1.21 2006/11/29 01:31:31 duanth Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * liuzhuoshan              2008-5-21         Created
 *  
 ********************************************************************************/

package com.ekingstar.eams.misc.fileprint.web.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.misc.fileprint.model.FilePrintApplication;
import com.ekingstar.eams.misc.fileprint.model.FilePrintMaterial;
import com.ekingstar.eams.system.basecode.industry.CurrencyCategory;
import com.ekingstar.eams.system.basecode.industry.FeeMode;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.task.TeachTaskService;
import com.shufe.web.action.common.CalendarRestrictionSupportAction;

public class FilePrintManageAction extends CalendarRestrictionSupportAction {

protected TeachTaskService teachTaskService;
	
	/**
     * 管理信息主页面
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
    	setCalendarDataRealm(request, hasStdTypeCollege);
		/*String stdTypeDataRealm = getStdTypeIdSeq(request);
		String departDataRealm = getDepartmentIdSeq(request);
    	addCollection(request, "teachDepartList", teachTaskService
				.getTeachDepartsOfTask(stdTypeDataRealm, departDataRealm,
						(TeachCalendar) request
								.getAttribute(Constants.CALENDAR)));*/
    	addCollection(request, "teachDepartList", departmentService.getAllDepartments());
        return forward(request);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Long calendarId = getLong(request, "calendar.id");
    	EntityQuery query = new EntityQuery(FilePrintApplication.class, "filePrintApplication");
		populateConditions(request, query);
		query.add(new Condition(" filePrintApplication.calendar.id=:calendarId",
				calendarId));
		query.setLimit(getPageLimit(request));
		query.addOrder(OrderUtils.parser(request.getParameter("orderBy")));
		request.setAttribute("calendar", utilService.load(TeachCalendar.class, calendarId));
    	addCollection(request, "filePrintApplicationList", utilService.search(query));
        return forward(request);
    }
    // 查看所选任务的 耗材列表
    public ActionForward materialList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintApplicationIdSeq = request.getParameter("filePrintApplicationId");
    	if (!"".equals(filePrintApplicationIdSeq)) {
			FilePrintApplication filePrintApplication = (FilePrintApplication) utilService
					.get(FilePrintApplication.class, Long.valueOf(filePrintApplicationIdSeq));
			addCollection(request, "filePrintMaterials", filePrintApplication.getMaterials());
		}
    	request.setAttribute("filePrintApplicationId", filePrintApplicationIdSeq);
		return forward(request);
    }
    // 增加或者 更新 耗材
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintMaterialId = request
					.getParameter("filePrintMaterialId");
		addCollection(request, "modes", utilService.loadAll(FeeMode.class));
		addCollection(request, "currencyCategorys", utilService.loadAll(CurrencyCategory.class));
		if (!"".equals(filePrintMaterialId)) {
			FilePrintMaterial filePrintMaterial = (FilePrintMaterial) utilService
					.get(FilePrintMaterial.class, Long.valueOf(filePrintMaterialId));
			request.setAttribute("filePrintMaterial", filePrintMaterial);
		}
		String filePrintApplicationId = request.getParameter("filePrintApplicationId");
		request.setAttribute("filePrintApplicationId", filePrintApplicationId);
		return forward(request);
    }
    // 保存
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Long filePrintApplicationId = getLong(request, "filePrintApplicationId");
    	FilePrintApplication filePrintApplication = (FilePrintApplication) utilService
		.get(FilePrintApplication.class, filePrintApplicationId);
    	FilePrintMaterial filePrintMaterial = (FilePrintMaterial) populateEntity(request, FilePrintMaterial.class, "filePrintMaterial");
    	filePrintMaterial.setPayed(new Float((filePrintMaterial.getPayedOne().floatValue())*(filePrintMaterial.getValue().intValue())));
    	filePrintMaterial.setFilePrint(filePrintApplication);
    	
    	utilService.saveOrUpdate(filePrintMaterial);
		return redirect(request, new Action("", "materialList"), "info.save.success",
				new String[] { "filePrintApplicationId" });
    }
    // 删除
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	//String filePrintApplicationIdSeq = request.getParameter("filePrintApplicationIds");
    	String filePrintMaterialIds = request.getParameter("filePrintMaterialIds");
		Long[] filePrintMaterialId = SeqStringUtil.transformToLong(filePrintMaterialIds);
		utilService.remove(FilePrintMaterial.class, "id", filePrintMaterialId);
		return redirect(request, new Action("", "materialList"), "info.delete.success",
				new String[] { "filePrintApplicationId" });
    }
    // 标记 请印申请 已完成
    public ActionForward closeFilePrint(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintApplicationIdSeq = request.getParameter("filePrintApplicationIds");
		Long[] filePrintApplicationIds = SeqStringUtil.transformToLong(filePrintApplicationIdSeq);
		for (int i = 0; i < filePrintApplicationIds.length; i++) {
			Long filePrintApplicationId = filePrintApplicationIds[i];
			FilePrintApplication filePrintApplication = (FilePrintApplication)utilService.get(FilePrintApplication.class, filePrintApplicationId);
			filePrintApplication.setManagerBy(getUser(request.getSession()));
			filePrintApplication.setFilePrintState(Boolean.TRUE);
			utilService.saveOrUpdate(filePrintApplication);
		}
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "calendar.id" });
    }
    // 标记 请印申请 未完成
    public ActionForward openFilePrint(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintApplicationIdSeq = request.getParameter("filePrintApplicationIds");
		Long[] filePrintApplicationIds = SeqStringUtil.transformToLong(filePrintApplicationIdSeq);
		for (int i = 0; i < filePrintApplicationIds.length; i++) {
			Long filePrintApplicationId = filePrintApplicationIds[i];
			FilePrintApplication filePrintApplication = (FilePrintApplication)utilService.get(FilePrintApplication.class, filePrintApplicationId);
			filePrintApplication.setManagerBy(getUser(request.getSession()));
			filePrintApplication.setFilePrintState(Boolean.FALSE);
			utilService.saveOrUpdate(filePrintApplication);
		}
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "calendar.id" });
    }
    // 计算请印 总费用
    public ActionForward computeTotal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String filePrintApplicationIdSeq = request.getParameter("filePrintApplicationIds");
		Long[] filePrintApplicationIds = SeqStringUtil.transformToLong(filePrintApplicationIdSeq);
		for (int i = 0; i < filePrintApplicationIds.length; i++) {
			Long filePrintApplicationId = filePrintApplicationIds[i];
			FilePrintApplication filePrintApplication = (FilePrintApplication)utilService.get(FilePrintApplication.class, filePrintApplicationId);
			float payed = 0.0f;
			for (Iterator iter = filePrintApplication.getMaterials().iterator(); iter.hasNext();) {
				FilePrintMaterial filePrintMaterial = (FilePrintMaterial) iter.next();
				payed += (filePrintMaterial.getPayedOne().floatValue())*(filePrintMaterial.getValue().intValue());
				filePrintMaterial.setPayed(new Float((filePrintMaterial.getPayedOne().floatValue())*(filePrintMaterial.getValue().intValue())));
				
			}
			filePrintApplication.setPayed(new Float(payed));
			utilService.saveOrUpdate(filePrintApplication);
		}
		return redirect(request, new Action("", "search"), "info.save.success",
				new String[] { "calendar.id" });
    }
    
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	return forward(request, new Action(FilePrintApplyAction.class, "download"));

    }
	
	public void setTeachTaskService(TeachTaskService teachTaskService) {
		this.teachTaskService = teachTaskService;
	}

}
