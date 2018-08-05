//$Id: AdminClassService.java,v 1.4 2006/12/04 12:59:30 yd Exp $
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
 * chaostone             2005-9-15         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.system.baseinfo.AdminClassDAO;
import com.shufe.model.system.baseinfo.AdminClass;

/**
 * getAll...意味着忽略班级的有效性进行查找
 * 
 * @author chaostone
 * 
 */
public interface AdminClassService {
    
    /**
     * 设置行政班级信息存取对象.
     * 
     * @param adminClassDAO
     */
    public void setAdminClassDAO(AdminClassDAO adminClassDAO);
    
    /**
     * 返回指定班级号码的学生行政班级信息. 若指定的班级不存在,则返回.
     * 
     * @param id
     * @return
     */
    public AdminClass getAdminClass(Long id);
    
    /**
     * 根据班级代码找到班级
     * 
     * @param code
     * @return
     */
    public AdminClass getAdminClass(String code);
    
    /**
     * 返回指定条件的班级信息
     * 
     * @param adminClass
     * @return
     */
    public List getAdminClasses(AdminClass adminClass);
    
    /**
     * 
     * @param classIdSeq
     * @return
     */
    public List getAdminClassesById(String classIdSeq);
    
    /**
     * 返回指定部门和学生类别范围与其他条件和页码、页长的行政班级信息.
     * 
     * @param adminClass
     * @param PageNo
     * @param pageSize
     * @return
     */
    public Pagination getAdminClasses(AdminClass adminClass, String stdTypeIdSeq,
            String departIdSeq, int pageNo, int pageSize);
    
    public List getAdminClasses(AdminClass adminClass, String stdTypeIdSeq, String departIdSeq);
    
    /**
     * 根据部门、专业、专业方向精确查找对应的班级. 专业和方向为空，将按照null查找.
     * 
     * @param depart
     * @param speciality
     * @param aspect
     * @return
     */
    public List getAdminClassesExactly(AdminClass adminClass);
    
    /**
     * 保存新建的行政班级信息，如果已经存在同样的班级，则抛出异常<code>PojoExistException</code>. 1)记录班级的创建时间和更新时间
     * 
     * @param adminClass
     */
    public void saveOrUpdate(AdminClass adminClass) throws PojoExistException;
    
    /**
     * 删除指定班级号码的行政班信息，如果不存在这样的班级，则返回. 谨慎调用.
     * 
     * @param id
     */
    public void removeAdminClass(Long id);
    
    /**
     * 计算并更新班级内学籍有效人数
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
     * 批量计算并更新班级内实际在校人数和学籍有效人数
     * 
     * @param adminClassIdSeq
     */
    public void batchUpdateStdCountOfClass(String adminClassIdSeq);
    
    /**
     * 批量计算并更新班级内实际在校人数和学籍有效人数
     * 
     * @param adminClassIdSeq
     */
    public void batchUpdateStdCountOfClass(Long[] adminClassIds);
    
    /**
     * cwx added on september 7 根据班级得到相关班级的所有id串
     * 
     * @param adminClass
     * @param departmentIds
     * @param stdTypeIds
     * @return
     */
    public List getAdminClassIds(AdminClass adminClass, String departmentIds, String stdTypeIds);
}
