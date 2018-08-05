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
 * chaostone            2006-03-26          Created
 * zq                   2007-09-13          重载addStdTypeTreeDataRealm()
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

import com.shufe.model.system.security.DataRealm;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.service.system.baseinfo.StudentTypeService;
import com.shufe.util.DataRealmLimit;
import com.shufe.web.helper.RestrictionHelper;

/**
 * 数据级权限支持类
 * 
 * @author chaostone
 * 
 */
public class RestrictionSupportAction extends DispatchBasicAction {
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
		restrictionHelper.setDataRealm(request, hasStdTypeCollege);
		return forward(request);
	}

	/**
	 * 加在数据权限
	 * @param request
	 * @param realmScope
	 * @param mapping
	 * @param form
	 */
	public void setDataRealm(HttpServletRequest request, int realmScope) {
		restrictionHelper.setDataRealm(request, realmScope);
	}

	public List getStdTypes(HttpServletRequest request) {
		return restrictionHelper.getStdTypes(request);
	}

	public List getDeparts(HttpServletRequest request) {
		return restrictionHelper.getDeparts(request);
	}

	public List getColleges(HttpServletRequest request) {
		return restrictionHelper.getColleges(request);
	}
	public List getAdminDeparts(HttpServletRequest request) {
		return restrictionHelper.getAdminDeparts(request);
	}

	public List getTeachDeparts(HttpServletRequest request) {
		return restrictionHelper.getTeachDeparts(request);
	}

	/**
	 * 查询部门类别权限<br>
	 * 首先查询session中是否已经存储了于moduleName对应的DataRealm<br>
	 * 如果没有查询到，则查询数据库，等放入session后，在返回使用者<br>
	 * 如果查询的部门权限为空,则抛出部门权限异常<br>
	 * 
	 * @param request
	 * @param moduleDefine
	 * @return
	 */
	protected String getDepartmentIdSeq(HttpServletRequest request) {
		return restrictionHelper.getDepartmentIdSeq(request);
	}

	/**
	 * 查询学生类别权限<br>
	 * 首先查询session中是否已经存储了于moduleName对应的DataRealm<br>
	 * 如果没有查询到，则查询数据库，等放入session后，在返回使用者<br>
	 * 如果查询的学生类别权限为空,则抛出学生类别权限异常<br>
	 * 
	 * @param request
	 * @param moduleDefine
	 * @return
	 */
	protected String getStdTypeIdSeq(HttpServletRequest request) {
		return restrictionHelper.getStdTypeIdSeq(request);
	}

	/**
	 * 获得数据权限
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	protected DataRealmLimit getDataRealmLimit(HttpServletRequest request) {
		DataRealmLimit limit = new DataRealmLimit();
		limit.getDataRealm().setStudentTypeIdSeq(getStdTypeIdSeq(request));
		limit.getDataRealm().setDepartmentIdSeq(
				getDepartmentIdSeq(request));
		limit.setPageLimit(getPageLimit(request));
		return limit;
	}

	protected DataRealm getDataRealm(HttpServletRequest request) {
		return DataRealm.mergeAll(restrictionHelper.getDataRealms(request));
	}

	protected List getDataRealms(HttpServletRequest request) {
		return restrictionHelper.getDataRealms(request);
	}

	/**
	 * 将用户的学生类别权限限制在一个选定的学生类别上。<br>
	 * 例如用户拥有10个学生类别的权限，如果他选择了其中一个，则根据这个以及他的子类<br>
	 * 进行重新限定，但不改变他原来的权限。
	 * 
	 * @param stdTypeId
	 * @param request
	 * @return
	 */
	protected List getDataRealmsWith(Long stdTypeId, HttpServletRequest request) {
		return restrictionHelper.getDataRealmsWith(stdTypeId,request);
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


	public List getStdTypesOf(Long parentStdTypeId, String moduleName, HttpServletRequest request) {
		return restrictionHelper.getStdTypesOf(parentStdTypeId, request);
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

	public void setRestrictionHelper(RestrictionHelper dataRealmHelper) {
		this.restrictionHelper = dataRealmHelper;
	}

}
