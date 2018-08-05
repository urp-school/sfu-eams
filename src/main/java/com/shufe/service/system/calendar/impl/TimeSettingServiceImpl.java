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
package com.shufe.service.system.calendar.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.shufe.dao.system.calendar.TimeSettingDAO;
import com.shufe.model.system.calendar.TimeSetting;
import com.shufe.service.BasicService;
import com.shufe.service.system.calendar.TimeSettingService;

public class TimeSettingServiceImpl extends BasicService implements
		TimeSettingService {

	private TimeSettingDAO settingDAO;

	public TimeSetting getDefaultSetting() {
		return (TimeSetting) utilService.get(TimeSetting.class,
				TimeSetting.DEFAULT_ID);
	}

	public List getTimeSettingOf(String stdTypeSeq) {
		if (StringUtils.isEmpty(stdTypeSeq))
			return Collections.EMPTY_LIST;
		else
			return getTimeSettingOf(SeqStringUtil.transformToLong(stdTypeSeq));
	}

	public List getTimeSettingOf(Long[] stdTypes) {
		if (stdTypes.length == 0)
			return Collections.EMPTY_LIST;
		else {
			return settingDAO.getTimeSettingOf(stdTypes);
		}
	}

	public void removeTimeSetting(TimeSetting setting) {
		if (ValidEntityPredicate.getInstance().evaluate(setting)) {
			settingDAO.removeTimeSetting(setting);
		}
	}

	public void saveTimeSetting(TimeSetting setting) {
		if (null != setting)
			settingDAO.saveTimeSetting(setting);
	}

	public void setTimeSettingDAO(TimeSettingDAO settingDAO) {
		this.settingDAO=settingDAO;
	}
}
