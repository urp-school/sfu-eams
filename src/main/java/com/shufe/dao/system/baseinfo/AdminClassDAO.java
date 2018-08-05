//$Id: AdminClassDAO.java,v 1.3 2006/09/10 12:17:51 duanth Exp $
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
 * chaostone             2005-9-14         Created
 *  
 ********************************************************************************/

package com.shufe.dao.system.baseinfo;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.BasicDAO;
import com.shufe.model.system.baseinfo.AdminClass;

/**
 * 学生行政班级信息存取类 <code>com.shufe.model.system.baseinfo.AdminClass</code>
 * 
 * @author chaostone 2005-9-14
 */
public interface AdminClassDAO extends BasicDAO {
	/**
	 * 返回指定班级号码的学生行政班级信息. 若指定的班级不存在,则抛出异常.
	 * 
	 * @param id
	 * @return
	 */
	public AdminClass getAdminClass(Long id);

	/**
	 * 返回指定条件的班级信息
	 * 
	 * @param adminClass
	 * @return
	 */
	public List getAdminClasses(AdminClass adminClass);

	/**
	 * 精确查找行政班级名称
	 * 
	 * @param enrollTurn
	 * @param stdTypeId
	 * @param departId
	 * @param specialityId
	 * @param aspectId
	 * @return
	 */
	List getAdminClassNamesExactly(String enrollTurn, Long stdTypeId,
			Long departId, Long specialityId, Long aspectId);

	List getAdminClassIdAndNames(String enrollTurn, String stdTypeId,
			String departId, String specialityId, String aspectId);
	
	/**
	 * 
	 * @param classIds
	 * @return
	 */
	public List getAdminClassesById(Long[] classIds);

	/**
	 * 返回指定部门和学生类别范围的班级
	 * 
	 * @param adminClass
	 * @param departIds
	 * @param stdTypeIds
	 * @param PageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getAdminClasses(AdminClass adminClass, Long[] stdTypeIds,
			Long[] departIds, int pageNo, int pageSize);

	/**
	 * 返回指定部门和学生类别范围的班级
	 * 
	 * @param adminClass
	 * @param departIds
	 * @param stdTypeIds
	 * @return
	 */
	public List getAdminClasses(AdminClass adminClass, Long[] stdTypeIds,
			Long[] departIds);

	/**
	 * cwx added on september 7
	 * 
	 * @param adminClass
	 * @param departmentIds
	 * @param stdTypeIds
	 * @return
	 */
	public List getAdminClassIds(AdminClass adminClass, String departmentIds,
			String stdTypeIds);

	/**
	 * 根据部门、专业、专业方向、所在年级和学生类别精确查找对应的班级. 专业和方向为空，将按照null查找.
	 * 
	 * @param adminClass
	 * @return
	 */
	public List getAdminClassesExactly(AdminClass adminClass);

	/**
	 * 删除指定班级号码的行政班信息，如果不存在这样的班级，则抛出异常. 谨慎调用.
	 * 
	 * @param id
	 */
	public void removeAdminClass(Long id);

	/**
	 * 计算并更新班级内实际在校人数
	 * 
	 * @param adminClassId
	 */
	public int updateStdCount(Long adminClassId);

	/**
	 * 计算并更新班级内实际在校人数
	 * 
	 * @param adminClassId
	 * @return
	 */
	public int updateActualStdCount(Long adminClassId);

	/**
	 * 根据参数得到可选班级<br>
	 * 使用位置：页面dwr
	 * 
	 * @param enrollYear
	 * @param stdTypeId
	 * @param departmentId
	 * @param specialityId
	 * @param aspectId
	 * @return
	 */
	public Long[] getAdminClassIds(String enrollYear, Long stdTypeId,
			Long departmentId, Long specialityId, Long aspectId);
}
