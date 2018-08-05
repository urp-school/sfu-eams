//$Id: FeeDefaultDAOHibernate.java,v 1.6 2006/10/12 12:19:44 duanth Exp $
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
 * chenweixiong              2006-7-11         Created
 *  
 ********************************************************************************/

package com.shufe.dao.fee.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.ekingstar.eams.system.basecode.industry.FeeType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.fee.FeeDefaultDAO;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.baseinfo.Speciality;

public class FeeDefaultDAOHibernate extends BasicHibernateDAO implements FeeDefaultDAO {

	public List getFeeDefaults(FeeType feeType, List stdTypes, Department depart,
			Speciality speciality) {
		String hql = "select feeDefault from FeeDefault feeDefault "
				+ " where feeDefault.type=:feeType  and feeDefault.department=:depart"
				+ " and feeDefault.studentType in(:stdTypes)";
		if (null != speciality) {
			hql += " and (feeDefault.speciality is null or feeDefault.speciality=:speciality)";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter("feeType", feeType);
		query.setParameter("depart", depart);
		query.setParameterList("stdTypes", stdTypes);
		if (null != speciality) {
			query.setParameter("speciality", speciality);
		}
		return query.list();
	}
}
