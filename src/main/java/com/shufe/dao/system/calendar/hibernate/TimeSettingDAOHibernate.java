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
 * chaostone             2006-7-13            Created
 *  
 ********************************************************************************/
package com.shufe.dao.system.calendar.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.system.calendar.TimeSettingDAO;
import com.shufe.model.system.calendar.TimeSetting;

public class TimeSettingDAOHibernate extends BasicHibernateDAO implements TimeSettingDAO {

	public List getTimeSettingOf(Long[] stdTypes) {
		String hql = "from TimeSetting as timeSetting "
				+ "where timeSetting.stdType.id in (:stdTypeIds)";
		Map params = new HashMap();
		params.put("stdTypeIds", stdTypes);
		return utilDao.searchHQLQuery(hql, params);
	}

	public void saveTimeSetting(TimeSetting setting) {
		saveOrUpdate(setting);
	}

	public void removeTimeSetting(TimeSetting setting) {
		remove(setting);
	}

}
