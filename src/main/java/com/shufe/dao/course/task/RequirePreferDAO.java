//$Id: RequirePreferDAO.java,v 1.1 2006/08/22 10:14:48 duanth Exp $
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
 * chaostone             2005-12-18         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.task;

import java.util.List;

import com.shufe.dao.BasicDAO;
import com.shufe.model.course.task.RequirePrefer;

public interface RequirePreferDAO extends BasicDAO {
	/**
	 * 
	 * @param preferenceId
	 * @return
	 */
	public RequirePrefer getPreference(Long preferenceId);

	/**
	 * 查询教师的使用偏好
	 * 
	 * @param teacher
	 * @return
	 */
	public List getPreferences(RequirePrefer preference);

	/**
	 * 保存教师的使用偏好
	 * 
	 * @param preference
	 */
	public void savePreference(RequirePrefer preference);

	/**
	 * 更新教师的使用偏好
	 */
	public void updatePreference(RequirePrefer preference);
}
