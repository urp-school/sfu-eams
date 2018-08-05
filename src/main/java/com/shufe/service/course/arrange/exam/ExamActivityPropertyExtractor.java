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
 * chaostone             2006-11-29            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.mvc.struts.misc.StrutsMessageResource;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.shufe.model.course.arrange.exam.ExamActivity;

public class ExamActivityPropertyExtractor extends DefaultPropertyExtractor {
	MessageResources resources;

	EntityQuery query = null;
	UtilService utilService;

	public Object getPropertyValue(Object target, String property) throws Exception {
		ExamActivity activity = (ExamActivity) target;
		if ("time".equals(property)) {
			return activity.digest(locale, resources);
		} else if ("date".equals(property)) {
			return dateFormat.format(activity.getDate());
		} else if ("examTakeCount".equals(property)) {
			Map params = new HashMap();
			params.put("activityId", activity.getId());
			query.setParams(params);
			List rs = (List) utilService.search(query);
			if (rs.isEmpty())
				return new Integer(0);
			else {
				return rs.get(0);
			}
		} else {
			return super.getPropertyValue(target, property);
		}
	}

	public ExamActivityPropertyExtractor(UtilService utilService,Locale locale, MessageResources resources) {
		this.locale = locale;
		this.resources = resources;
		this.setBuddle(new StrutsMessageResource(resources));
		this.utilService = utilService;
		query = new EntityQuery(
				"select count(*) from ExamTake take where take.activity.id=:activityId");
	}

}
