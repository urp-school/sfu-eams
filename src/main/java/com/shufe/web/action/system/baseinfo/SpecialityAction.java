//$Id: SpecialityAction.java,v 1.5 2006/12/30 01:29:01 duanth Exp $
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
 * chaostone            2005-09-15          Created
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package com.shufe.web.action.system.baseinfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.mvc.struts.misc.ImporterServletSupport;
import com.ekingstar.commons.transfer.Transfer;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.ekingstar.eams.system.basecode.state.SubjectCategory;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.service.system.baseinfo.importer.SpecialityImportListener;
import com.shufe.util.DataAuthorityUtil;
import com.shufe.web.action.system.baseinfo.search.SpecialitySearchAction;

/**
 * 专业信息管理的action.包括专业信息的增改查.
 * 
 * @author chaostone 2005-9-22
 */
public class SpecialityAction extends SpecialitySearchAction {

	/**
	 * 修改和新建专业时调用的动作
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
		String departDataRealm = getDepartmentIdSeq(request);
		String stdTypeDataRealm = getStdTypeIdSeq(request);
		Speciality speciality = (Speciality) getEntity(request, Speciality.class, "speciality");
		if (ValidEntityPredicate.INSTANCE.evaluate(speciality)) {
			if (!DataAuthorityUtil.isInDataRealm("Speciality", speciality, stdTypeDataRealm,
					departDataRealm))
				return forwardError(mapping, request, "error.dataRealm.insufficient");
		}
		addEntity(request, speciality);
		addCollection(request, "departments", departmentService.getColleges(departDataRealm));
		addCollection(request, "stdTypes", getStdTypes(request));
		addCollection(request, "subjectCategories", baseCodeService.getCodes(SubjectCategory.class));
        addCollection(request, "majorTypes", baseCodeService.getCodes(MajorType.class));
		return forward(request);
	}

	/**
	 * 保存专业信息，新建的专业或修改的专业. <br>
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
		Long specialityId = getLong(request, "speciality.id");

		// 检查是否重复
		if (utilService.duplicate(Speciality.class, specialityId, "code", request
				.getParameter("speciality.code"))) {
			return forward(request, new Action("", "edit"), "error.code.existed");
		}

		Speciality speciality = null;
		Map params = getParams(request, "speciality");
		try {
			if (null == specialityId) {
				speciality = new Speciality();
				populate(params, speciality);
				EntityUtils.evictEmptyProperty(speciality);
				logHelper.info(request, "Create a speciality with name:" + speciality.getName());
			} else {
				speciality = specialityService.getSpeciality(specialityId);
				logHelper.info(request, "Update a speciality with name:" + speciality.getName());
				populate(params, speciality);
			}
			ActionForward f = super.saveOrUpdate(request, speciality);
			if (null != f)
				return f;
		} catch (PojoExistException e) {
			logHelper.info(request, "Failure save or update a speciality with name:"
					+ speciality.getName(), e);
			return forward(mapping, request, new String[] { "entity.speciality",
					"error.model.existed" }, "error");
		} catch (Exception e) {
			logHelper.info(request, "Failure save or update a speciality with name:"
					+ speciality.getName(), e);
			return forward(mapping, request, "error.occurred", "error");
		}
		return redirect(request, "search", "info.save.success");
	}

	/**
	 * 导入班级信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TransferResult tr = new TransferResult();
		Transfer transfer = ImporterServletSupport.buildEntityImporter(request, Speciality.class,
				tr);
		if (null == transfer) {
			return forward(request, "/pages/components/importData/error");
		}
		transfer.addListener(new ImporterForeignerListener(utilService)).addListener(
				new SpecialityImportListener(specialityService));
		transfer.transfer(tr);
		if (tr.hasErrors()) {
			return forward(request, "/pages/components/importData/error");
		} else {
			return redirect(request, "search", "info.import.success");
		}
	}
}
