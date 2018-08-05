package com.shufe.dao.system.baseinfo;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.BasicDAO;
import com.shufe.model.system.baseinfo.Department;

public interface DepartmentDAO extends BasicDAO {
	/**
	 * 根据指定的部门代码，返回部门详细信息. 没有对应的部门代码，则抛出异常.
	 * 
	 * @param id
	 * @return
	 */
	public Department getDepartment(Long id);

	/**
	 * 返回所有部门信息的第一页数据.使用默认页码<code>Pagination.DEFAULT_PAGE_NUM</code>和 默认<code>Pagination.DEFAULT_PAGE_SIZE</code>页长.
	 * 
	 * @return
	 */
	public List getDepartments();

	/**
	 * 返回指定条件的部门列表
	 * 
	 * @param Department
	 * @return
	 */
	public List getDepartments(Department department);

	public List getDepartmentNames(String departIdSeq);

	/**
	 * 返回部门代码数组中指定的部门列表
	 * 
	 * @param ids
	 * @return
	 */
	public List getDepartments(Long[] ids);

	/**
	 * 返回部门代码字符串中指定的管理部门列表
	 * 
	 * @param ids
	 * @return
	 */
	public List getAdministatives(Long[] ids);

	/**
	 * 查询指定id的开课院系
	 * 
	 * @param ids
	 * @return
	 */
	public List getTeachDeparts(Long[] ids);

	/**
	 * 返回部门代码字符串中指定的院系部门列表
	 * 
	 * @param ids
	 * @return
	 */
	public List getColleges(Long[] ids);

	/**
	 * 返回指定条件和页码、页长的数据
	 * 
	 * @param Department
	 * @param PageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getDepartments(Department department, int pageNo,
			int pageSize);

	/**
	 * 返回所有(含有效无效)部门
	 * 
	 * @return
	 */
	public List getAllDepartments();

	/**
	 * 按条件分页查找所有(含有效无效)部门
	 * 
	 * @return
	 */
	public List getAllDepartments(Department department);

	/**
	 * 按条件分页查找所有(含有效无效)部门
	 * 
	 * @param department
	 * @param PageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getAllDepartments(Department department, int pageNo,
			int pageSize);

	/**
	 * 删除制定部门代码的部门，谨慎调用. 如果要删除的部门信息并不存在，则抛出异常.
	 * 
	 * @param id
	 */
	public void removeDepartment(Long id);
}
