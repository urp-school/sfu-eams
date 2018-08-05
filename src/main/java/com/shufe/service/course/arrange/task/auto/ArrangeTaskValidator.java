//$Id: ArrangeTaskValidator.java,v 1.2 2006/11/11 06:14:07 duanth Exp $
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
 * chaostone             2005-12-20         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import com.shufe.model.course.arrange.task.ArrangeParams;
import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.model.course.task.TeachTask;

/**
 * 教学任务可以自动安排的验证类
 * 
 * @author chaostone
 * 
 */
public interface ArrangeTaskValidator {
	/**
	 * 验证教学任务是否自动可排,若可排则返回没有任何消息的messages.
	 * 
	 * @param task
	 * @return
	 */
	ArrangeValidateMessages validateTask(TeachTask task,ArrangeParams params);

	/**
	 * 验证教学任务课程组是否自动可排,若可排则返回没有任何消息的messages.
	 * 
	 * @param group
	 * @return
	 */
	ArrangeValidateMessages validateTaskGroup(TaskGroup group,ArrangeParams params);

}
