//$Id: TeachProductDAOHibernate.java,v 1.1 2006/10/24 11:09:06 duanth Exp $
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

package com.shufe.dao.quality.product.hibernate;

import org.hibernate.Query;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.quality.product.TeachProductDAO;

public class TeachProductDAOHibernate extends BasicHibernateDAO implements TeachProductDAO {
	
	/* (non-Javadoc)
	 * @see com.shufe.dao.teachProducts.TeachProductDAO12#batchDelete(java.lang.String)
	 */
	public void batchDelete(String ids){
		String stringsql = "delete from TeachProduct where id in("+ids+")";
		if(ids.split(",").length>0){
			Query query = getSession().createQuery(stringsql);
			query.executeUpdate();
		}
	}
}
