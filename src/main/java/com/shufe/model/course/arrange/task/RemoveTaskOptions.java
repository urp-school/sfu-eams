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
 * chaostone             2006-5-26            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.task;

/**
 * 从课程组内删除任务时需要设置的参数
 * 
 * @author chaostone
 * 
 */
public class RemoveTaskOptions {
	/**
	 * 把要添加的任务的排课建议中的教室从组内去除
	 */
	boolean removeSuggestRoom = true;

	/**
	 * 把要添加的任务的排课建议中的时间从组内去除
	 */
	boolean removeSuggestTime = true;

	public RemoveTaskOptions() {
		super();
	}

	public RemoveTaskOptions(boolean removeSuggestRoom,
			boolean removeSuggestTime) {
		this.removeSuggestRoom = removeSuggestRoom;
		this.removeSuggestTime = removeSuggestTime;
	}

}
