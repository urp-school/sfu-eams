//$Id: SpecialityService.java,v 1.1 2006/08/02 00:52:49 duanth Exp $
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

import java.util.Collection;
import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.eams.system.basecode.industry.MajorType;
import com.shufe.dao.system.baseinfo.SpecialityDAO;
import com.shufe.model.system.baseinfo.Speciality;

public interface SpecialityService {
    
    /**
     * 设置专业信息管理的数据存取对象
     */
    public void setSpecialityDAO(SpecialityDAO specialityDAO);
    
    /**
     * 返回指定专业代码的详细信息，若指定的专业不存在，则返回
     * 
     * @param id
     * @return
     */
    public Speciality getSpeciality(Long id);
    
    /**
     * 返回指定专业代码的详细信息，若指定的专业不存在，则返回
     * 
     * @param id
     * @return
     */
    public Speciality getSpeciality(String code);
    
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
    
    public Collection getSpecialities(MajorType majorType);
    
    public Collection getSpecialities(Boolean state, MajorType majorType);
    
    /**
     * 返回指定条件和页码、页长的数据
     * 
     * @param speciality
     * @param PageNo
     * @param pageSize
     * @return
     */
    public Pagination getSpecialities(Speciality speciality, int pageNo, int pageSize);
    
    /**
     * 返回限制权限的专业列表
     * 
     * @param speciality
     * @param stdTypeIdSeq
     * @param departIdSeq
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getSpecialities(Speciality speciality, String stdTypeIdSeq,
            String departIdSeq, int pageNo, int pageSize);
    
    public List getSpecialities(Speciality speciality, String stdTypeIdSeq, String departIdSeq);
    
    /**
     * 
     * @param speciality
     * @param PageNo
     * @param pageSize
     * @return
     */
    public Pagination getAllSpecialities(Speciality speciality, int pageNo, int pageSize);
    
    /**
     * 保存新建的专业信息，如果已经存在相同专业代码的专业，则抛出异常<code>PojoExistException</code>
     * 
     * @param speciality
     */
    public void saveOrUpdate(Speciality speciality) throws PojoExistException;
    
    /**
     * 删除制定专业代码的专业，谨慎调用. 如果要删除的专业信息并不存在，则返回.
     * 
     * @param id
     */
    public void removeSpeciality(Long id);
    
    /**
     * depredicated api
     * 
     * @param departmentIds
     *            TODO
     * @return
     */
    public List getUserSpecialitiesByDepartment(String departmentIds);
    
    /**
     * 
     * @param departmentIds
     * @return
     */
    public List getSpecialitiesByDepartmentIds(String departmentIds);
}
