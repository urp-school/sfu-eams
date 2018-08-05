//$Id: DataRealmExampleTemplateAction.java,v 1.1 2007-4-10 下午05:27:27 chaostone Exp $
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
 *chaostone      2007-4-10         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.common;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.service.system.baseinfo.StudentTypeService;
import com.shufe.web.helper.RestrictionHelper;

public class RestrictionExampleTemplateAction extends ExampleTemplateAction {
	public static int hasStdType = 1;

	public static int hasDepart = 2;

	public static int hasCollege = 4;

	public static int hasAdminDepart = 8;

	public static int hasStdTypeDepart = 3;

	public static int hasStdTypeCollege = 5;

	public static int hasStdTypeAdminDepart = 9;

	public static int hasStdTypeTeachDepart = 11;
	/**
	 * 部门服务对象
	 */
	protected DepartmentService departmentService;

	/**
	 * 学生类别服务对象
	 */
	protected StudentTypeService studentTypeService;

	protected RestrictionHelper restrictionHelper;

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
		setDataRealm(request, hasStdTypeCollege);
		indexSetting(request);
		return forward(request);
	}

	/**
	 * 加在数据权限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param moduleName
	 * @param realmScope
	 */
	public void setDataRealm(HttpServletRequest request, int realmScope) {
		restrictionHelper.setDataRealm(request,  realmScope);
	}

	public List getStdTypes(HttpServletRequest request) {
		return restrictionHelper.getStdTypes(request);
	}

	public List getDeparts(HttpServletRequest request) {
		return restrictionHelper.getDeparts(request);
	}

	public List getTeachDeparts(HttpServletRequest request) {
		return restrictionHelper.getTeachDeparts(request);
	}

	public List getAdminDeparts(HttpServletRequest request) {
		return restrictionHelper.getAdminDeparts(request);
	}
	
	public List getColleges(HttpServletRequest request) {
		return restrictionHelper.getColleges(request);
	}

	protected List getDataRealmsWith(Long stdTypeId, HttpServletRequest request) {
		return restrictionHelper.getDataRealmsWith(stdTypeId, request);
	}


	protected List getDataRealms(HttpServletRequest request) {
		return restrictionHelper.getDataRealms(request);
	}

	protected List getDataRealms(HttpServletRequest request, String moduleName) {
		return restrictionHelper.getDataRealms(request);
	}

	/**
	 * 查找部门权限串
	 * 
	 * @param session
	 * @return
	 */
	protected String getDepartmentIdSeq(HttpServletRequest request) {
		return restrictionHelper.getDepartmentIdSeq(request);
	}
	protected String getStdTypeIdSeq(HttpServletRequest request) {
		return restrictionHelper.getStdTypeIdSeq(request);
	}

	/**
	 * 返回在权限内的指定学生类别及其子类别的列表串.
	 * 
	 * @param parentStdTypeId
	 * @param request
	 * @return
	 */
	public String getStdTypeIdSeqOf(Long parentStdTypeId, HttpServletRequest request) {
		return restrictionHelper.getStdTypeIdSeqOf(parentStdTypeId, request);
	}

	public Collection getStdTypesOf(Long parentStdTypeId, HttpServletRequest request) {
		return restrictionHelper.getStdTypesOf(parentStdTypeId, request);
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public StudentTypeService getStudentTypeService() {
		return studentTypeService;
	}

	public void setStudentTypeService(StudentTypeService studentTypeService) {
		this.studentTypeService = studentTypeService;
	}

	public void setDataRealmHelper(RestrictionHelper dataRealmHelper) {
		this.restrictionHelper = dataRealmHelper;
	}

	public void setRestrictionHelper(RestrictionHelper restrictionHelper) {
		this.restrictionHelper = restrictionHelper;
	}

}
