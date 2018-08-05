//$Id: SpecialityAspectService.java,v 1.1 2006/08/02 00:52:49 duanth Exp $
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
import com.shufe.dao.system.baseinfo.SpecialityAspectDAO;
import com.shufe.model.system.baseinfo.SpecialityAspect;

public interface SpecialityAspectService {
    
    /**
     * 设置专业方向信息存取对象.
     * 
     * @param aspectDAO
     */
    public void setSpecialityAspectDAO(SpecialityAspectDAO aspectDAO);
    
    /**
     * 返回指定专业方向代码的详细信息，若指定的专业方向不存在，则返回
     * 
     * @param id
     * @return
     */
    public SpecialityAspect getSpecialityAspect(Long id);
    
    /**
     * 返回所有专业方向信息的数据.
     * 
     * @return
     */
    public List getSpecialityAspects();
    
    /**
     * 返回指定条件的专业方向信息
     * 
     * @param aspect
     * @return
     */
    public List getSpecialityAspects(SpecialityAspect aspect);
    
    public Collection getSpecialityAspects(MajorType majorType);
    
    public Collection getSpecialityAspects(Boolean state, MajorType majorType);
    
    /**
     * 根据专业id取出所有的专业方向
     * 
     * @param id
     * @return
     */
    public List getSpecialityAspects(Long specialityId);
    
    /**
     * 返回指定条件和页码、页长的数据
     * 
     * @param aspect
     * @param PageNo
     * @param pageSize
     * @return
     */
    public Pagination getSpecialityAspects(SpecialityAspect aspect, int pageNo, int pageSize);
    
    /**
     * 
     * @param aspect
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Pagination getAllSpecialityAspects(SpecialityAspect aspect, int pageNo, int pageSize);
    
    /**
     * 保存新建的专业方向信息，如果已经存在相同专业方向代码的专业方向，则抛出异常.
     * 
     * @param aspect
     */
    public void saveOrUpdate(SpecialityAspect aspect) throws PojoExistException;
    
    /**
     * 删除制定专业方向代码的专业方向，谨慎调用. 如果要删除的专业信息并不存在，则返回.
     * 
     * @param id
     */
    public void removeSpecialityAspect(Long id);
    
    /**
     * 列出所有第二专业方向
     * 
     * @return
     */
    public List listSecondSpecialityAspect();
    
    /**
     * 列出权限内所有第二专业方向
     * 
     * @return
     */
    public List listSecondSpecialityAspect(String departmentIds);
}
