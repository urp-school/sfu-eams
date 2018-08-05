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
 * chaostone             2006-6-3            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.election;

import com.shufe.model.course.task.TeachTask;

/**
 * 限制学生的选课上下限
 * 
 * @author chaostone
 * 
 */
public interface StdCountLimitedPolicy {

	public void updateStdCountLimited(TeachTask task);

	public Integer getStdCountLimited();
}
