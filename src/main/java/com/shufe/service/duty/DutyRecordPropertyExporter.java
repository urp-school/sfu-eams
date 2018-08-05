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
 * chaostone             2006-4-18            Created
 *  
 ********************************************************************************/
package com.shufe.service.duty;

import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.shufe.model.duty.DutyRecord;


public class DutyRecordPropertyExporter implements PropertyExtractor {
	protected MessageResources resourses;

	protected Locale locale;

	public Object getPropertyValue(Object target, String property)
			throws Exception {
		DutyRecord record = (DutyRecord) target;
		String[] subProperties = StringUtils.split(property, '.');
		StringBuffer passedProperty = new StringBuffer(subProperties[0]);
		for (int i = 0; i < subProperties.length - 1; i++) {
			if (null != PropertyUtils.getProperty(record, passedProperty
					.toString()))
				passedProperty.append(".").append(subProperties[i + 1]);
			else
				return "";
		}
		try{
		Object value = PropertyUtils.getProperty(target, property);		
		if (value instanceof Boolean && null != locale && null != resourses) {
			if (value.equals(Boolean.TRUE))
				return resourses.getMessage(locale, "common.yes");
			else
				return resourses.getMessage(locale, "common.no");
		} else
			return value;
		}catch (Exception e) {
			System.out.println(target+"::"+property);
			return null;
		}
	}

	/**
	 * @param locale
	 *            The locale to set.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @param resourses
	 *            The resourses to set.
	 */
	public void setMessageResources(MessageResources resourses) {
		this.resourses = resourses;
	}
}
