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
 * chaostone             2006-11-12            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.Order;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.model.course.arrange.exam.ExamTurn;
import com.shufe.service.BasicService;
import com.shufe.service.course.arrange.exam.ExamTurnService;

public class ExamTurnServiceImpl extends BasicService implements
		ExamTurnService {

	public Collection getExamTurns(StudentType stdType) {
		EntityQuery entityQuery = new EntityQuery(ExamTurn.class, "turn");
		Condition stdTypeCondition = new Condition("turn.stdType=:stdType");
		entityQuery.add(stdTypeCondition);
		entityQuery.addOrder(new Order("beginTime"));
		Collection examTurns = new ArrayList();
		if (null != stdType && stdType.isPO()) {
			do {
				stdTypeCondition.setValues(Collections.singletonList(stdType));
				examTurns = utilDao.search(entityQuery);
				if (examTurns.isEmpty())
					stdType = stdType.getSuperType();
			} while (stdType != null && examTurns.isEmpty());

		}
		if (examTurns.isEmpty()) {
			entityQuery = new EntityQuery(ExamTurn.class, "turn");
			entityQuery.add(new Condition("turn.stdType =null"));
			entityQuery.addOrder(new Order("beginTime"));
			return utilDao.search(entityQuery);
		} else {
			return examTurns;
		}
	}
}
