//$Id: TeachTaskStatDAOHibernate.java,v 1.1 2007-5-9 下午02:38:03 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-9         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.task.hibernate;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import com.ekingstar.commons.model.EntityUtils;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.task.TeachTaskStatDAO;

/**
 * 教学任务统计
 * 
 * @author chaostone
 * 
 */
public class TeachTaskStatDAOHibernate extends BasicHibernateDAO implements
		TeachTaskStatDAO {

	public List statTeacherTitle(List calendars) {
		String queryString = "select jxrlid,jszcid,count(*) as num from ("
				+ "				select distinct teachtask0_.JXRLID as JXRLID, teacher2_.id as jzgid,teacher2_.JSZCID as JSZCID"
				+ "				from JXRW_T teachtask0_ inner join JXRW_LS_T teachers1_ on teachtask0_.id=teachers1_.JXRWID"
				+ "				inner join JCXX_JZG_T teacher2_ on teachers1_.LSID=teacher2_.id where"
				+ "				(teachtask0_.JXRLID in (:calendarIds))"
				+ "				)group by jxrlid,jszcid";
		SQLQuery query = getSession().createSQLQuery(queryString);
		query
				.setParameterList("calendarIds", EntityUtils
						.extractIds(calendars));
		query.addScalar("jxrlid", Hibernate.LONG);
		query.addScalar("jszcid", Hibernate.LONG);
		query.addScalar("num", Hibernate.INTEGER);
		return query.list();
	}
}
