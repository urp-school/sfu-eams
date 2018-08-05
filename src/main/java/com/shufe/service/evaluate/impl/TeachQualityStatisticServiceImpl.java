//$Id: TeachQualityStatisticServiceImpl.java,v 1.1 2006/08/02 00:52:58 duanth Exp $
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
 * chenweixiong              2006-5-11         Created
 *  
 ********************************************************************************/

package com.shufe.service.evaluate.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.shufe.model.quality.evaluate.QuestionnaireStat;
import com.shufe.service.BasicService;
import com.shufe.service.evaluate.TeachQualityStatisticService;
import com.shufe.service.util.stat.FloatSegment;
import com.shufe.service.util.stat.StatUtils;

public class TeachQualityStatisticServiceImpl extends BasicService implements
		TeachQualityStatisticService {

	/**
	 * 统计得到优良中差的人数
	 * 
	 * @see com.shufe.service.evaluate.TeachQualityStatisticService#statEvaluatePageByNum(java.util.List,
	 *      java.util.List, String, String, float)
	 */
	public Map statEvaluatePageByNum(List floatSegments, List calendars, String stdTypeIdSeq,
			String departIdSeq, float excellentMark) {
		Map requestMap = new HashMap();
		EntityQuery entityQuery = new EntityQuery(QuestionnaireStat.class, "questionnaireStat");
		if (StringUtils.isNotEmpty(stdTypeIdSeq)) {
			entityQuery.add(new Condition("instr('," + stdTypeIdSeq
					+ ",',','||questionnaireStat.stdType.id||',',1,1)>0"));
		}
		if (StringUtils.isNotEmpty(departIdSeq)) {
			entityQuery.add(new Condition("instr('," + departIdSeq
					+ ",',','||questionnaireStat.depart.id||',',1,1)>0"));
		}
		entityQuery.add(new Condition("questionnaireStat.calendar in (:calendars)", calendars));
		entityQuery.setSelect("select questionnaireStat.score,questionnaireStat.calendar.id");
		Collection markAndyears = utilService.search(entityQuery);
		for (Iterator iter = markAndyears.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			Float score = (Float) element[0];// 第一个分数
			Long calendarId = (Long) element[1];// 第二个年份
			FloatSegment tempSegment = null;
			int flag = 0;
			for (int i = 0; i < floatSegments.size(); i++) {
				FloatSegment floatSegment = (FloatSegment) floatSegments.get(i);
				if (score.floatValue() <= floatSegment.getMax()
						&& score.floatValue() >= floatSegment.getMin()) {
					tempSegment = floatSegment;
					flag = i;
					break;
				}
			}
			if (null == tempSegment) {
				continue;
			}
			StatUtils.setValueToMap(calendarId + "-" + flag, new Integer(1), "integer", requestMap);
			StatUtils.setValueToMap("0-" + flag, new Integer(1), "integer", requestMap);
			StatUtils.setValueToMap(calendarId + "all", new Integer(1), "integer", requestMap);
			StatUtils.setValueToMap("0-all", new Integer(1), "integer", requestMap);
			if (score.floatValue() >= excellentMark) {
				StatUtils.setValueToMap(calendarId + "excellent", new Integer(1), "integer",
						requestMap);
				StatUtils.setValueToMap("0-excellent", new Integer(1), "integer", requestMap);
			}
		}
		return requestMap;
	}
}
