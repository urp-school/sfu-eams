//$Id: ElectParamsDAOHibernate.java,v 1.1 2006/08/02 00:53:11 duanth Exp $
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
 * chaostone             2005-12-12         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.election.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.election.ElectParamsDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.course.election.ElectParams;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Speciality;
import com.shufe.model.system.baseinfo.SpecialityAspect;
import com.shufe.model.system.calendar.TeachCalendar;

public class ElectParamsDAOHibernate extends BasicHibernateDAO implements
		ElectParamsDAO {

	private static final Speciality null_Speciality = new Speciality(new Long(
			-1));

	private static final SpecialityAspect null_SpecialityAspect = new SpecialityAspect(
			new Long(-1));

	/**
	 * @see com.shufe.dao.course.election.ElectParamsDAO#getElectParams(java.lang.String[],
	 *      com.shufe.model.system.calendar.TeachCalendar)
	 */
	public List getElectParams(Long[] stdTypeIds, TeachCalendar calendar) {
		Map params = new HashMap();
		params.put("stdTypeIds", stdTypeIds);
		params.put("calendar", calendar);
		return find("getElectParams", params);
	}

	/**
	 * @see com.shufe.dao.course.election.ElectParamsDAO#getElectParams(com.shufe.model.course.election.ElectParams)
	 */
	public List getElectParams(ElectParams params) {
		Criteria criteria = getSession().createCriteria(ElectParams.class);
		if (null != params) {
			List criterions = CriterionUtils.getEntityCriterions(params);
			for (Iterator iter = criterions.iterator(); iter.hasNext();)
				criteria.add((Criterion) iter.next());
		}
		List rsParams = criteria.list();
		// filter other params
		if (null != params.getStdTypes() && !params.getStdTypes().isEmpty())
			for (Iterator iter = rsParams.iterator(); iter.hasNext();) {
				ElectParams oneParams = (ElectParams) iter.next();
				if (!oneParams.getStdTypes().containsAll(params.getStdTypes()))
					iter.remove();
			}
		if (null != params.getDeparts() && !params.getDeparts().isEmpty())
			for (Iterator iter = rsParams.iterator(); iter.hasNext();) {
				ElectParams oneParams = (ElectParams) iter.next();
				if (!oneParams.getDeparts().containsAll(params.getDeparts()))
					iter.remove();
			}
		if (null != params.getEnrollTurns()
				&& !params.getEnrollTurns().isEmpty())
			for (Iterator iter = rsParams.iterator(); iter.hasNext();) {
				ElectParams oneParams = (ElectParams) iter.next();
				if (!oneParams.getEnrollTurns().containsAll(
						params.getEnrollTurns()))
					iter.remove();
			}
		return rsParams;
	}

	public List getAvailElectParams(Student std) {
		Map params = new HashMap();
		params.put("stdTypeId", std.getType().getId());
		params.put("departId", std.getDepartment().getId());
		if (null == std.getFirstMajor()) {
			params.put("major", null_Speciality);
		} else {
			params.put("major", std.getFirstMajor());
		}
		if (null == std.getFirstAspect()) {
			params.put("majorField", null_SpecialityAspect);
		} else {
			params.put("majorField", std.getFirstAspect());
		}
		params.put("enrollTurn", std.getEnrollYear());
		params.put("stdId", std.getId());
		return utilDao.searchNamedQuery("getAvailElectParams", params, true);
	}

	public void saveElectParams(ElectParams params) {
		getSessionFactory().evict(ElectParams.class);
		saveOrUpdate(params);
	}

}
