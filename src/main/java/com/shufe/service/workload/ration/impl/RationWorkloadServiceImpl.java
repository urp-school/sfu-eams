//$Id: RationWorkloadServiceImpl.java,v 1.3 2006/08/18 05:03:18 cwx Exp $
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

package com.shufe.service.workload.ration.impl;

import java.util.List;

import net.ekingstar.common.detail.Pagination;

import org.hibernate.Criteria;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.workload.ration.RationWorkloadDAO;
import com.shufe.service.BasicService;
import com.shufe.service.workload.ration.RationWorkloadService;

public class RationWorkloadServiceImpl extends BasicService implements RationWorkloadService {

	private RationWorkloadDAO rationWorkloadDAO;

	/**
	 * @param rationWorkloadDAO The rationWorkloadDAO to set.
	 */
	public void setRationWorkloadDAO(RationWorkloadDAO rationWorkloadDAO) {
		this.rationWorkloadDAO = rationWorkloadDAO;
	}

	/* (non-Javadoc)
	 * @see com.shufe.service.rationWorkload.RationWorkloadService#findRations(org.hibernate.Criteria, int, int)
	 */
	public Pagination findRations(Criteria criteria, int pageNo, int pageSize) {
		return rationWorkloadDAO.dynaSearch(criteria, pageNo, pageSize);
	}

	/* (non-Javadoc)
	 * @see com.shufe.service.rationWorkload.RationWorkloadService#getRationWorkloads(java.lang.String)
	 */
	public Criteria getRationWorkloads(String departmentIds) {
		Long[] departmentId = SeqStringUtil.transformToLong(departmentIds);
		return rationWorkloadDAO.getRationWorkloads(departmentId);
	}

	/* (non-Javadoc)
	 * @see com.shufe.service.rationWorkload.RationWorkloadService#getRationWorkloadList(java.lang.String)
	 */
	public List getRationWorkloadList(String departmentIds) {
		return getRationWorkloads(departmentIds).list();
	}
	
	
}
