package com.shufe.service.system.baseinfo;

import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.system.baseinfo.DepartmentDAO;
import com.shufe.model.system.baseinfo.Department;

public interface DepartmentService {
    /**
     * 设置部门信息存取对象
     * 
     * @param departmentDAO
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO);
    
    /**
     * 根据指定的部门代码，返回部门详细信息. 没有对应的部门代码，则返回null.
     * 
     * @param id
     * @return
     */
    public Department getDepartment(Long id);
    
    /**
     * 返回所有有效的部门信息
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
    
    /**
     * 返回部门代码字符串中指定的部门列表.形式如"xx,xx,xx"
     * 
     * @param idSeq
     * @return
     */
    public List getDepartments(String idSeq);
    
    /**
     * 返回部门代码数组中指定的部门列表
     * 
     * @param ids
     * @return
     */
    public List getDepartments(Long ids[]);
    
    /**
     * 返回指定条件和页码、页长的数据
     * 
     * @param Department
     * @param PageNo
     * @param pageSize
     * @return
     */
    public Pagination getDepartments(Department department, int pageNo, int pageSize);
    
    /**
     * 返回所有有效的学院信息
     * 
     * @return
     */
    public List getColleges();
    
    /**
     * 返回部门代码字符串中指定的院系部门列表
     * 
     * @param idSeq
     * @return
     */
    public List getColleges(String idSeq);
    
    /**
     * 返回部门代码字符串中指定的院系部门列表
     * 
     * @param ids
     * @return
     */
    public List getColleges(Long ids[]);
    /**
     * 得到所有的开课院系
     * @return
     */
    public List getTeachDeparts();
    
    /**
     * 查询开课院系
     * 
     * @param idSeq
     * @return
     */
    public List getTeachDeparts(String idSeq);
    
    /**
     * 返回所有有效的管理部门信息
     * 
     * @return
     */
    public List getAdministatives();
    
    /**
     * 返回部门代码字符串中指定的管理部门列表
     * 
     * @param idSeq
     * @return
     */
    public List getAdministatives(String idSeq);
    
    /**
     * 返回部门代码字符串中指定的管理部门列表
     * 
     * @param ids
     * @return
     */
    public List getAdministatives(Long ids[]);
    
    /**
     * 删除制定部门代码的部门，谨慎调用. 如果要删除的部门信息并不存在，则返回null.
     * 
     * @param id
     */
    public void removeDepartment(Long id);
    
    /**
     * 返回所有所有的（忽略有效无效）管理部门列表
     * 
     * @return
     */
    public List getAllAdministatives();
    
    /**
     * 返回所有所有的（忽略有效无效）院系列表
     * 
     * @return
     */
    public List getAllColleges();
    
    /**
     * 返回数据库中所有的（忽略有效无效）部门信息
     * 
     * @param department
     * @return
     */
    public List getAllDepartments();
    
    /**
     * 按照条件，返回数据库中所有的（忽略有效无效）部门信息
     * 
     * @param department
     * @return
     */
    public List getAllDepartments(Department department);
    
    /**
     * 分页查找所有的（忽略有效无效）部门信息
     * 
     * @param department
     * @param PageNo
     * @param pageSize
     * @return
     */
    public Pagination getAllDepartments(Department department, int pageNo, int pageSize);
    
    public Collection getRelatedDeparts(String stdTypeIds);
    
    /**
     * 保存新建的部门信息，如果已经存在相同部门代码的部门，则抛出异常.
     * 
     * @param Department
     */
    public void saveOrUpdate(Department department) throws PojoExistException;
}
