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
package com.shufe.service.system.calendar;

import java.util.List;

import com.shufe.dao.system.calendar.TimeSettingDAO;
import com.shufe.model.system.calendar.TimeSetting;

public interface TimeSettingService {
	public List getTimeSettingOf(Long[] stdTypes);

	public List getTimeSettingOf(String stdTypeSeq);

	public void saveTimeSetting(TimeSetting setting);

	public void removeTimeSetting(TimeSetting setting);

	public TimeSetting getDefaultSetting();
	
	public void setTimeSettingDAO(TimeSettingDAO settingDAO);
}
