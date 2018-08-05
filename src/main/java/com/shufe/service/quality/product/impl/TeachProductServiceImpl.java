//$Id: TeachProductServiceImpl.java,v 1.1 2006/10/24 11:09:43 duanth Exp $
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
 * chenweixiong              2005-11-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.quality.product.impl;

import com.shufe.dao.quality.product.TeachProductDAO;
import com.shufe.service.BasicService;
import com.shufe.service.quality.product.TeachProductService;

public class TeachProductServiceImpl extends BasicService implements
		TeachProductService {
	private TeachProductDAO teachProductDAO;
	/**
	 * @param teachProductDAO The teachProductDAO to set.
	 */
	public void setTeachProductDAO(TeachProductDAO teachProductDAO) {
		this.teachProductDAO = teachProductDAO;
	}
	/**
	 * @param ids
	 */
	public void batchDelete(String ids){
		teachProductDAO.batchDelete(ids);
	}
}
