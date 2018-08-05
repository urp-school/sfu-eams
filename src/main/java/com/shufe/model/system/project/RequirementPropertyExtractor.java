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
 * chaostone             2006-12-3            Created
 *  
 ********************************************************************************/
package com.shufe.model.system.project;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;

public class RequirementPropertyExtractor extends DefaultPropertyExtractor {
	Locale locale = null;

	MessageResources resources = null;

	String[] priorities = new String[] { "低", "中", "高" };

	String[] types = new String[] { "新增", "补充增加" };

	String[] status = new String[] { "待处理", "相应中", "已处理" };

	public Object getPropertyValue(Object target, String property)
			throws Exception {
		Requirement require = (Requirement) target;
		if ("priority".equals(property)) {
			return priorities[require.getPriority().intValue() - 1];
		} else if ("type".equals(property)) {
			return types[require.getType().intValue() - 1];
		} else if ("status".equals(property)) {
			return status[require.getStatus().intValue() - 1];
		} else {
			return super.getPropertyValue(require, property);
		}
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setMessageResources(MessageResources resources) {
		this.resources = resources;
	}

}
