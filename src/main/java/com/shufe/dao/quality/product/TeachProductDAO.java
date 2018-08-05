//$Id: TeachProductDAO.java,v 1.2 2006/12/19 10:09:10 duanth Exp $
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

package com.shufe.dao.quality.product;

import com.shufe.dao.BasicDAO;

public interface TeachProductDAO extends BasicDAO {
	/**
	 * 根据序号串批量删除相应的对象信息.
	 * 
	 * @param ids
	 */
	public abstract void batchDelete(String ids);

}