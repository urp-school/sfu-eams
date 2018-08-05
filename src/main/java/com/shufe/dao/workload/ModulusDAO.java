//$Id: ModulusDAO.java,v 1.4 2006/08/25 06:48:40 cwx Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2006-8-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import com.shufe.dao.BasicDAO;

public interface ModulusDAO extends BasicDAO {


	/**
	 * 根据条件得到对应的系数列表
	 * @param modulus
	 * @param StdTypeIdSeq
	 * @return
	 */
	public List getModulus(Object modulus,String stdTypeIdSeq);
	
	/**
	 * 根据条件得到对应的系数分页列表
	 * @param modulus
	 * @param pageNo TODO
	 * @param pageSize TODO
	 * @param StdTypeIdSeq
	 * @return
	 */
	public Pagination getPagiOfModulus(Object modulus,String stdTypeIdSeq, int pageNo, int pageSize);
	
	
	/**
	 * 得到系数的属性值
	 * @param mdulus
	 * @param stdTypeIdSeq
	 * @param properties
	 * @return
	 */
	public List getPropertyOfModulus(Object mdulus,String stdTypeIdSeq,String properties);
}