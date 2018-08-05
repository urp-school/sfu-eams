//$Id: BookRequireStatServiceImpl.java,v 1.1 2007-3-13 上午10:46:45 chaostone Exp $
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
 *chaostone      2007-3-13         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.textbook.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.course.textbook.BookRequirement;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.BasicService;
import com.shufe.service.course.textbook.BookRequireStatService;
import com.shufe.service.util.stat.StatHelper;
import com.shufe.util.DataRealmUtils;

public class BookRequireStatServiceImpl extends BasicService implements
		BookRequireStatService {

	public List statRequireBy(TeachCalendar calendar, DataRealm dataRealm,
			String attr, Class clazz) {
		EntityQuery entityQuery = new EntityQuery(BookRequirement.class,
				"require");
		entityQuery
				.setSelect("select new  com.shufe.service.util.stat.StatItem("
						+ attr
						+ ",count(*),sum(require.countForStd+require.countForTeacher))");
		entityQuery.add(new Condition("require.task.calendar=:calendar",
				calendar));
		DataRealmUtils.addDataRealm(entityQuery, new String[] {
				"require.task.teachClass.stdType.id",
				"require.task.arrangeInfo.teachDepart.id" }, dataRealm);
		entityQuery.setGroups(Collections.singletonList(attr));
		Collection stats = utilDao.search(entityQuery);
		return new StatHelper(utilService).setStatEntities(stats, clazz);
	}

	public List statCheckBy(TeachCalendar calendar, DataRealm dataRealm,
			String attr, Class clazz) {
		EntityQuery entityQuery = new EntityQuery(BookRequirement.class,
				"require");
		entityQuery
				.setSelect("select new  com.shufe.service.util.stat.StatItem("
						+ attr
						+ ",count(*),sum(Case  WHEN (require.checked=true) THEN 1 ELSE 0 END),sum(Case  WHEN (require.checked=false) THEN 1 ELSE 0 END),sum(Case  WHEN (require.checked is null) THEN 1 ELSE 0 END))");
		entityQuery.add(new Condition("require.task.calendar=:calendar",
				calendar));
		DataRealmUtils.addDataRealm(entityQuery, new String[] {
				"require.task.teachClass.stdType.id",
				"require.task.arrangeInfo.teachDepart.id" }, dataRealm);
		entityQuery.setGroups(Collections.singletonList(attr));
		Collection stats = utilDao.search(entityQuery);
		return new StatHelper(utilService).setStatEntities(stats, clazz);
	}

}
