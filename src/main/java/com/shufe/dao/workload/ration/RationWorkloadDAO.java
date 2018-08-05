//$Id: RationWorkloadDAO.java,v 1.2 2006/08/18 05:03:41 cwx Exp $
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

package com.shufe.dao.workload.ration;

import org.hibernate.Criteria;

import com.shufe.dao.BasicDAO;

public interface RationWorkloadDAO extends BasicDAO {

	/**
	 * @param departmentId
	 * @return
	 */
	public abstract Criteria getRationWorkloads(Long[] departmentId);

}