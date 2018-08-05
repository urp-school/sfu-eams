//$Id: TaskGroupInDataRealmPredicate.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
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
 * chaostone             2005-12-3         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.predicate;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.shufe.model.course.arrange.task.TaskGroup;
import com.shufe.util.DataAuthorityPredicate;
/**
 * 课程组数据权限判定类
 * @author chaostone
 *
 */
public class TaskGroupInDataRealmPredicate extends DataAuthorityPredicate {

	public boolean evaluate(Object arg0) {
		TaskGroup group = (TaskGroup)arg0;
		List tasks = group.getTaskList();
		if (!tasks.isEmpty()) {
			CollectionUtils.filter(tasks, new DataAuthorityPredicate(
					stdTypeDataRealm, departDataRealm, "teachClass.stdType",
					"arrangeInfo.teachDepart"));
			if(tasks.isEmpty())return false;
		}
		return true;
	}

}
