//$Id: WorkloadButtonDAOHibernate.java,v 1.4 2007/01/10 06:17:46 cwx Exp $
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
 * chenweixiong              2005-11-19         Created
 *  
 ********************************************************************************/

package com.shufe.dao.workload.course.hibernate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.workload.course.WorkloadOpenSwitchDAO;
import com.shufe.model.workload.course.WorkloadOpenSwitch;

public class WorkloadOpenSwitchDAOHibernate extends BasicHibernateDAO implements WorkloadOpenSwitchDAO {
	
	/**
	 * @see com.shufe.dao.workload.course.WorkloadOpenSwitchDAO#getCalendarsOfOpenByStdSeq(java.lang.String)
	 */
	public List getCalendarsOfOpenByStdSeq(String stdTypeSeq) {
		Criteria criteria = getWorkloadsByStdSeq(stdTypeSeq, Boolean.TRUE);
		criteria.setProjection(Projections.groupProperty("teachCalendar"));
		return criteria.list();
	}
	private Criteria getWorkloadsByStdSeq(String stdTypeSeq, Boolean boo) {
		Criteria criteria = getSession().createCriteria(WorkloadOpenSwitch.class);
		if(StringUtils.isNotBlank(stdTypeSeq)){
		criteria.createCriteria("teachCalendar", "teachCalendar");
		criteria.add(Restrictions.in("teachCalendar.studentType.id",
				SeqStringUtil.transformToLong(stdTypeSeq)));
		}
		if (null != boo) {
			criteria.add(Restrictions.eq("isOpen", boo));
		}
		return criteria;
	}

	/**
	 * @see com.shufe.dao.workload.course.WorkloadOpenSwitchDAO#getWorkloadButtonByCalendarSeq(java.lang.String)
	 */
	public List getWorkloadButtonByCalendarSeq(String teachCalendarIdSeq) {
		if (StringUtils.isBlank(teachCalendarIdSeq)) {
			return Collections.EMPTY_LIST;
		}
		Criteria criteria = getSession().createCriteria(WorkloadOpenSwitch.class);
		criteria.add(Restrictions.in("teachCalendar.id", SeqStringUtil
				.transformToLong(teachCalendarIdSeq)));
		return criteria.list();
	}

	/**
	 * @see com.shufe.dao.workload.course.WorkloadOpenSwitchDAO#getWorkloadButtonsByStdSeq(java.lang.String)
	 */
	public List getWorkloadButtonsByStdSeq(String stdTypeSeq) {
		if(StringUtils.isBlank(stdTypeSeq)){
			return Collections.EMPTY_LIST;
		}
		Criteria criteria = getWorkloadsByStdSeq(stdTypeSeq,null);
		return criteria.list();
	}
	/**
	 * @see com.shufe.dao.workload.course.WorkloadOpenSwitchDAO#getCalendarOpenedByCalendars(java.util.Collection)
	 */
	public List getCalendarOpenedByCalendars(Collection calendars) {
		if (null == calendars && calendars.size() < 1) {
			return Collections.EMPTY_LIST;
		}
		Criteria criteria = getSession().createCriteria(WorkloadOpenSwitch.class);
		criteria.add(Restrictions.in("teachCalendar", calendars));
		criteria.setProjection(Projections.property("teachCalendar"));
		return criteria.list();
	}
}
