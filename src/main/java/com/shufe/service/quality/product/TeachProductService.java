//$Id: TeachProductService.java,v 1.2 2006/12/19 10:07:07 duanth Exp $
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

package com.shufe.service.quality.product;


public interface TeachProductService {
	/**
	 * 根据id串来批量删除对象
	 * 
	 * @param ids
	 */
	public void batchDelete(String ids);
}
