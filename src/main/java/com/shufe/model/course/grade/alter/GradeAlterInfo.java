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
 * chaostone             2007-1-3            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade.alter;

import java.util.Date;

import com.ekingstar.security.User;

/**
 * 成绩新增/修改记录
 * 
 * @author chaostone
 * 
 */
public interface GradeAlterInfo {
	public Long getId();

	/**
	 * 修改前分数
	 * 
	 * @return
	 */
	public Float getScoreBefore();

	/**
	 * 修改后分数
	 * 
	 * @return
	 */
	public Float getScoreAfter();

	/**
	 * 修改时间
	 * 
	 * @return
	 */
	public Date getModifyAt();

	/**
	 * 修改人
	 * 
	 * @return
	 */
	public User getModifyBy();

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark();
	// public Grade getGrade(); 因为jdk1.4不能实现重载时的返回类型多态

}
