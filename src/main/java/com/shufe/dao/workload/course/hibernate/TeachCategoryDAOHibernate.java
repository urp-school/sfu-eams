//$Id: TeachCategoryDAOHibernate.java,v 1.1 2006/08/18 05:31:28 cwx Exp $
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
 * chenweixiong              2006-3-13         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.course.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.workload.course.TeachCategoryDAO;
import com.shufe.model.workload.course.TeachCategory;

public class TeachCategoryDAOHibernate extends BasicHibernateDAO implements TeachCategoryDAO {

	/* (non-Javadoc)
	 * @see com.shufe.dao.workload.TeachCategoryDAO12#getAlllegality()
	 */
	public List getAlllegality(){ 
		Criteria  criteria = getExampleOfCategory(true);
		return criteria.list();
	}
	
	/* (non-Javadoc)
	 * @see com.shufe.dao.workload.TeachCategoryDAO12#getExampleOfCategory(boolean)
	 */
	public Criteria getExampleOfCategory(boolean b){
		Criteria criteria = getSession().createCriteria(TeachCategory.class);
		if(b){
			criteria.add(Restrictions.eq("status", new Boolean(true)));
		}
		criteria.addOrder(Order.asc("code"));
		return criteria;
	}
	/* (non-Javadoc)
	 * @see com.shufe.dao.workload.TeachCategoryDAO12#getTeachCategoryBystdTypeAndcourseType(java.lang.Long, java.lang.Long)
	 */
	public TeachCategory getTeachCategoryBystdTypeAndcourseType(Long stdTypeId,Long courseTypeId){
		Criteria criteria = getExampleOfCategory(true);
		criteria.add(Restrictions.eq("studentType.id", stdTypeId));
		if(courseTypeId.equals(new Long(0))){
			criteria.add(Restrictions.isNull("courseType"));
		}else{
			criteria.add(Restrictions.eq("courseType.id", courseTypeId));
		}
		if(criteria.list().size()>0)
		return (TeachCategory)criteria.list().get(0);
		return null;
	}
}
