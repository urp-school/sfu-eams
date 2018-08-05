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
 * chaostone             2006-8-25            Created
 *  
 ********************************************************************************/
package com.shufe.dao.system.message.hibernate;

import org.hibernate.Query;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.message.SystemMessageDAO;

public class SystemMessageDAOHibernate extends BasicHibernateDAO implements
		SystemMessageDAO {

	public int getMessageCount(Long recipientId, Integer status) {
		String hql = "select count(*) from SystemMessage where recipient.id=:recipientId and status=:status";
		Query query = getSession().createQuery(hql);
		query.setParameter("recipientId", recipientId);
		query.setParameter("status", status);
		return ((Number) (query.list().iterator().next())).intValue();
	}

}
