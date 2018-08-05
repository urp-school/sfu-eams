//$Id: ConfigRationWorkloadDAOHibernate.java,v 1.1 2006/08/18 05:08:01 cwx Exp $
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
 * chenweixiong              2005-11-16         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.ration.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.workload.ration.RationWorkloadConfigDAO;
import com.shufe.model.system.baseinfo.Teacher;

public class RationWorkloadConfigDAOHibernate extends BasicHibernateDAO implements RationWorkloadConfigDAO {
		
	/* (non-Javadoc)
	 * @see com.shufe.dao.rationWorkload.ConfigRationWorkloadDAO122#getTeachers(java.lang.Long)
	 */
	public Criteria getTeachers(Long departmentId){
		Criteria criteria = getSession().createCriteria(Teacher.class);
		if(departmentId!=null){
			criteria.add(Restrictions.eq("department.id", departmentId));
		}
		criteria.addOrder(Order.asc("name"));
		return criteria;
	}
}
