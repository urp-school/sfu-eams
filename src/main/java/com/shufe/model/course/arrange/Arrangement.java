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
 * chaostone             2006-6-18            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange;


/**
 * 可安排的任务或者别的,代表对教学资源分配的一个分配
 * @author chaostone
 *
 */
public interface Arrangement {
	/**
	 * @return Returns the arrangeInfo.
	 */
	public ArrangeInfo getArrangeInfo();
}

