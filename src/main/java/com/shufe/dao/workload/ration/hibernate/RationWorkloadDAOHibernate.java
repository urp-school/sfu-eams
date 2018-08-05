//$Id: RationWorkloadDAOHibernate.java,v 1.1 2006/08/18 05:08:01 cwx Exp $
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

package com.shufe.dao.workload.ration.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.workload.ration.RationWorkloadDAO;
import com.shufe.model.workload.ration.RationWorkload;

public class RationWorkloadDAOHibernate extends BasicHibernateDAO implements RationWorkloadDAO {

    /*
     * (non-Javadoc)
     * 
     * @see com.shufe.dao.rationWorkload.RationWorkloadDAO12#getRationWorkloads(java.lang.Long[])
     */
    public Criteria getRationWorkloads(Long[] departmentId) {
        Criteria criteria = getSession().createCriteria(RationWorkload.class);
        criteria.add(Restrictions.in("department.id", departmentId));
        return criteria;
    }

}
