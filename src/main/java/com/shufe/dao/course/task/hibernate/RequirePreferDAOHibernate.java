//$Id: RequirePreferDAOHibernate.java,v 1.1 2006/08/22 10:14:49 duanth Exp $
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
 * chaostone             2005-12-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.task.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.task.RequirePreferDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.course.task.RequirePrefer;

public class RequirePreferDAOHibernate extends BasicHibernateDAO
        implements RequirePreferDAO {

    /**
     * @see com.shufe.dao.course.task.RequirePreferDAO#getPreference(java.lang.Long)
     */
	public RequirePrefer getPreference(Long preferenceId) {
        RequirePrefer preference = (RequirePrefer) getHibernateTemplate()
                .get(RequirePrefer.class, preferenceId);
        if (null == preference)
            preference = (RequirePrefer) load(
                    RequirePrefer.class, preferenceId);
        return preference;
    }

    /**
     * @see com.shufe.dao.course.task.RequirePreferDAO#getPreferences(com.shufe.model.course.task.RequirePrefer)
     */
    public List getPreferences(RequirePrefer preference) {
        Criteria criteria = getSession().createCriteria(
                RequirePrefer.class);
        List criterions = CriterionUtils.getEntityCriterions(preference);
        for (Iterator iter = criterions.iterator(); iter.hasNext();)
            criteria.add((Criterion) iter.next());
        return criteria.list();
    }

    /**
     * @see com.shufe.dao.course.task.RequirePreferDAO#savePreference(com.shufe.model.course.task.RequirePrefer)
     */
    public void savePreference(RequirePrefer preference) {
        save(preference);
    }

    /**
     * @see com.shufe.dao.course.task.RequirePreferDAO#updatePreference(com.shufe.model.course.task.RequirePrefer)
     */
    public void updatePreference(RequirePrefer preference) {
        update(preference);
    }

}
