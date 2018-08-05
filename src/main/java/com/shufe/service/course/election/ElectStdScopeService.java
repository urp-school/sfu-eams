//$Id: ElectStdScopeService.java,v 1.2 2006/12/29 14:04:03 duanth Exp $
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
 * chaostone             2005-12-5         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.election;

import java.util.Collection;
import java.util.List;

import com.shufe.dao.course.election.ElectStdScopeDAO;
import com.shufe.dao.course.task.TeachTaskDAO;
import com.shufe.model.course.election.ElectInitParams;
import com.shufe.web.OutputProcessObserver;

/**
 * 选课范围设置服务接口
 * 
 * @author chaostone 2005-12-10
 */
public interface ElectStdScopeService {
	/**
	 * 批量设置选课信息
	 * @param tasks
	 * @param isEelectable
	 */
	public void batchUpdateTasksElectInfo(Collection tasks,
			ElectInitParams setting, OutputProcessObserver observer);

	/**
	 * 查询某学期教室的容量不等于参选任务的人数上限
	 * 
	 * @param calendar
	 * @return [task.id,roomCapacity]
	 */
	public List getMaxStdCountNotEqualRoomCapacity(Long calendarId);
	/**
	 * 
	 * @param electStdScopeDAO
	 */
	public void setElectStdScopeDAO(ElectStdScopeDAO electStdScopeDAO);
	/**
	 * 
	 * @param taskDAO
	 */
	public void setTeachTaskDAO(TeachTaskDAO taskDAO);
}
