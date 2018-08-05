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
 * chaostone             2006-3-2            Created
 *  
 ********************************************************************************/
package com.shufe.dao.course.election.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.election.ElectStdScopeDAO;

public class ElectStdScopeDAOHibernate extends BasicHibernateDAO implements ElectStdScopeDAO {
	public List getMaxStdCountNotEqualRoomCapacity(Long calendarId) {
		String sql = "select rw.id,sx.rntkrs from jxrw_t rw,"
				+ " (SELECT hd.jxrwid as rwid,  MIN (js.rntkrs) as rntkrs"
				+ "    FROM jxhd_t hd, jcxx_js_t js  WHERE js.id = hd.jsid AND hd.zyyy = 1"
				+ "     AND hd.jxrlid =  " + calendarId + " GROUP BY hd.jxrwid) sx"
				+ " where rw.id= sx.rwid and rw.xkrssx <> sx.rntkrs"
				+ " and rw.sfcx=1 and rw.jxrlid=" + calendarId;
		Query query = getSession().createSQLQuery(sql);
		return query.list();
	}

}
