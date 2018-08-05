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
 * chaostone             2006-12-27            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.grade;

import java.util.Date;
import java.util.Set;

import com.ekingstar.eams.system.basecode.industry.MarkStyle;
import com.ekingstar.security.User;

/**
 * 成绩接口
 * 
 * @author chaostone
 * 
 */
public interface Grade extends Comparable {

	public static final int NEW = 0;// 新录入

	public static final int CONFIRMED = 1;// 确认

	public static final int PUBLISHED = 2;// 发布

	/**
	 * 分数
	 * 
	 * @return
	 */
	public Float getScore();

	/**
	 * 修改信息
	 * 
	 * @return
	 */
	public Set getAlterInfos();

	/**
	 * 在特定记录方式下显示的成绩分数
	 * 
	 * @param markStyle
	 * @return
	 */
	public String getScoreDisplay(MarkStyle markStyle);

	/**
	 * 根据成绩本身的记录方式显示成绩分数
	 * 
	 * @return
	 */
	public String getScoreDisplay();

	/**
	 * 是否合格
	 * 
	 * @return
	 */
	public Boolean getIsPass();

	/**
	 * 是否发布
	 * 
	 * @return
	 */
	public Boolean getIsPublished();

	/**
	 * 是否确认
	 * 
	 * @return
	 */
	public Boolean getIsConfirmed();

	/**
	 * 返回成绩状态
	 * 
	 * @return
	 */
	public Integer getStatus();

	/**
	 * 更新学分,如果成绩和已有的不一致,则增加一个变更记录
	 * 
	 * @param score
	 */
	public void updateScore(Float score, User who);

	public Date getCreateAt();
}
