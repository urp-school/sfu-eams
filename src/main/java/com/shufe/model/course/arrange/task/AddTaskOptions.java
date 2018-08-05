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
 * 向课程组内添加任务时需要设置的参数
 * 
 * @author chaostone
 * 
 */
public class AddTaskOptions {
	/**
	 * 把要添加的任务的排课建议中的教室添加进组内
	 */
	boolean addSuggestRoom = false;

	/**
	 * 把要添加的任务的排课建议中的时间添加进组内
	 */
	boolean addSuggestTime = false;

	/**
	 * 是否合并教师相同的任务
	 */
	boolean mergeTeacher = false;

	/**
	 * 是否共享班级，并设置共同的选课范围
	 */
	boolean shareAdminClass = false;

	/**
	 * @return Returns the addSuggestRoom.
	 */
	public boolean isAddSuggestRoom() {
		return addSuggestRoom;
	}

	/**
	 * @param addSuggestRoom The addSuggestRoom to set.
	 */
	public void setAddSuggestRoom(boolean addSuggestRoom) {
		this.addSuggestRoom = addSuggestRoom;
	}

	/**
	 * @return Returns the addSuggestTime.
	 */
	public boolean isAddSuggestTime() {
		return addSuggestTime;
	}

	/**
	 * @param addSuggestTime The addSuggestTime to set.
	 */
	public void setAddSuggestTime(boolean addSuggestTime) {
		this.addSuggestTime = addSuggestTime;
	}

	/**
	 * @return Returns the mergeTeacher.
	 */
	public boolean isMergeTeacher() {
		return mergeTeacher;
	}

	/**
	 * @param mergeTeacher The mergeTeacher to set.
	 */
	public void setMergeTeacher(boolean mergeTeacher) {
		this.mergeTeacher = mergeTeacher;
	}

	/**
	 * @return Returns the shareAdminClass.
	 */
	public boolean isShareAdminClass() {
		return shareAdminClass;
	}

	/**
	 * @param shareAdminClass The shareAdminClass to set.
	 */
	public void setShareAdminClass(boolean shareAdminClass) {
		this.shareAdminClass = shareAdminClass;
	}
	
}
