//$Id: ModulusService.java,v 1.4 2006/12/19 13:08:42 duanth Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-17         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.workload.ModulusDAO;
import com.shufe.model.workload.Modulus;

public interface ModulusService {
	
	public void setModulusDAO(ModulusDAO modulusDAO);
	
	/**
	 * 根据criteria对象和分页条件得到分页对象
	 * @param modulus TODO
	 * @param stdTypeIdSeq TODO
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination findModulus(Modulus modulus,String stdTypeIdSeq,int pageNo, int pageSize);
	
	/**
	 * @param modulus
	 * @param stdTypeIdSeq
	 * @return
	 */
	public List getModulus(Modulus modulus,String stdTypeIdSeq);
	
	/**
	 * 检查对象的合法性
	 * @param modulus
	 * @return
	 */
	public boolean checkModulus(Modulus modulus);
	
	/**
	 * 根据条件得到当个的系数
	 * @param modulus
	 * @param studentTypeId
	 * @return
	 */
	public Modulus  getUniqueModulus(Modulus modulus,String studentTypeId);
	
	/**
	 * 根据条件得到对应系数的属性值
	 * @param mdulus
	 * @param stdTypeIdSeq
	 * @param properties
	 * @return
	 */
	public List getPropertyOfModulus(Object mdulus, String stdTypeIdSeq,
			String properties);

}
