//$Id: SpecialityAspectDAO.java,v 1.1 2006/08/02 00:53:07 duanth Exp $
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
import com.shufe.model.system.baseinfo.SpecialityAspect;

/**
 * 学校专业方向基础信息存取类 <code>com.shufe.model.system.baseinfo.SpecialityAspect</code>
 * 
 * @author chaostone 2005-9-14
 */
public interface SpecialityAspectDAO extends BasicDAO {
  /**
   * 返回指定专业方向代码的详细信息，若指定的专业方向不存在，则抛出异常
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

  /**
   * 返回专业方向的名称
   * 
   * @param specialityId
   * @return
   */
  List getSpecialityAspectNames(Long specialityId);

  List getSpecialityAspectNames2(Long specialityId, Long stdTypeId);

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
   * @param aspect
   * @param PageNo
   * @param pageSize
   * @return
   */
  public Pagination getAllSpecialityAspects(SpecialityAspect aspect, int pageNo, int pageSize);

  /**
   * 删除制定专业方向代码的专业方向，谨慎调用.
   * 如果要删除的专业信息并不存在，则抛出异常.
   * 
   * @param id
   */
  public void removeSpecialityAspect(Long id);
}
