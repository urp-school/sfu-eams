//$Id: RationWorkloadService.java,v 1.3 2006/08/18 05:02:45 cwx Exp $
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
 * chenweixiong              2005-11-15         Created
 *  
 ********************************************************************************/

package com.shufe.service.workload.ration;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;

import com.shufe.dao.workload.ration.RationWorkloadDAO;

public interface RationWorkloadService {

	/**
	 * @param rationWorkloadDAO
	 */
	public void setRationWorkloadDAO(RationWorkloadDAO rationWorkloadDAO);
	/**
	 * 根据条件得到一个分页对象
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination findRations(Criteria criteria,int pageNo,int pageSize);
	
	/**
	 * 根据部门Id串得到一个criteria对象
	 * @param departmentIds
	 * @return
	 */
	public Criteria getRationWorkloads(String departmentIds);
	
	
	/**
	 * @param departmentIds
	 * @return
	 */
	public List getRationWorkloadList(String departmentIds);
}
