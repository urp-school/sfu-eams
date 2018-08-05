//$Id: SpecialityDAO.java,v 1.2 2007/01/05 01:22:42 duanth Exp $
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
import com.shufe.model.system.baseinfo.Speciality;

/**
 * 学校专业基础信息存取类 <code>com.shufe.model.system.baseinfo.Speciality</code>
 * 
 * @author chaostone 2005-9-14
 */
public interface SpecialityDAO extends BasicDAO {
	/**
	 * 返回指定专业代码的详细信息，若指定的专业不存在，则抛出异常
	 * 
	 * @param id
	 * @return
	 */
	public Speciality getSpeciality(Long id);

	/**
	 * 返回所有专业信息的数据.
	 * 
	 * @return
	 */
	public List getSpecialities();

	/**
	 * 返回指定条件的专业信息
	 * 
	 * @param speciality
	 * @return
	 */
	public List getSpecialities(Speciality speciality);

	/**
	 * 返回专业的名称 如果给定的学生类别没有对应的专业，则递归查找父类
	 * 
	 * @param departId
	 * @param stdTypeId
	 *            为空则忽略
	 * @return
	 */
	List getSpecialityNames(Long departId, Long stdTypeId,Long majorTypeId);

	/**
	 * 返回指定条件和页码、页长的数据
	 * 
	 * @param speciality
	 * @param PageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getSpecialities(Speciality speciality, int pageNo,
			int pageSize);

	public Pagination getSpecialities(Speciality speciality, Long[] stdTypeIds,
			Long[] departIds, int pageNo, int pageSize);

	public List getSpecialities(Speciality speciality, Long[] stdTypeIds,
			Long[] departIds);

	/**
	 * 
	 * @param speciality
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getAllSpecialities(Speciality speciality, int pageNo,
			int pageSize);

	/**
	 * 删除制定专业代码的专业，谨慎调用. 如果要删除的专业信息并不存在，则抛出异常.
	 * 
	 * @param id
	 */
	public void removeSpeciality(Long id);

	/**
	 * 返回指定部门的专业列表
	 * 
	 * @param departmentIds
	 * @return
	 */
	public List getSpecialitiesByDepartmentIds(Long[] departmentIds);
}
