//$Id: ArrangeParams.java,v 1.4 2006/11/13 03:03:28 duanth Exp $
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
 * chaostone             2005-10-16         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.task;

/**
 * 自动安排时的参数设置
 * 
 * @author chaostone 2005-10-16
 */
public class ArrangeParams {
	public static final int ROOM = 0;

	public static final int TIME = 1;

	public static final int TEACHER = 1;

	public static final int CLASS = 2;

	public static final int STD = 3;

	public static int defaultDensity = 12;

	/**
	 * 教师一天可上课的最大小节数
	 */
	private Integer maxUnitOfTeacher;

	/**
	 * 一天可上课最大小节数
	 */
	private Integer maxUnitOfDay;

	/**
	 * 一学期可上课最大周数
	 */
	private Integer maxWeek;

	/**
	 * 课程安排的密度
	 */
	private int density = defaultDensity;

	/**
	 * 可用时间
	 */
	private boolean[] considerAvailTime = new boolean[] { true, true };

	/**
	 * 时间冲突
	 */
	private boolean[] detectCollision = new boolean[] { true, true, true,
			false };

	/**
	 * 排课建议
	 */
	private boolean[] bySuggest = new boolean[] { true, true };
	/**
	 * @return Returns the maxUnitOfDay.
	 */
	public Integer getMaxUnitOfDay() {
		return maxUnitOfDay;
	}

	/**
	 * @param maxNode
	 *            The maxNode to set.
	 */
	public void setMaxUnitOfDay(Integer maxNode) {
		this.maxUnitOfDay = maxNode;
	}

	/**
	 * @return Returns the maxWeek.
	 */
	public Integer getMaxWeek() {
		return maxWeek;
	}

	/**
	 * @param maxWeek
	 *            The maxWeek to set.
	 */
	public void setMaxWeek(Integer maxWeek) {
		this.maxWeek = maxWeek;
	}

	/**
	 * @return Returns the teacherMaxUnit.
	 */
	public Integer getMaxUnitOfTeacher() {
		return maxUnitOfTeacher;
	}

	/**
	 * @param teacherMaxUnit
	 *            The teacherMaxUnit to set.
	 */
	public void setMaxUnitOfTeacher(Integer teacherMaxUnit) {
		this.maxUnitOfTeacher = teacherMaxUnit;
	}

	/**
	 * @return Returns the density.
	 */
	public int getDensity() {
		return density;
	}

	/**
	 * @param density
	 *            The density to set.
	 */
	public void setDensity(int density) {
		this.density = density;
	}
	public boolean[] getBySuggest() {
		return bySuggest;
	}

	public void setBySuggest(boolean[] bySuggest) {
		this.bySuggest = bySuggest;
	}

	public boolean[] getConsiderAvailTime() {
		return considerAvailTime;
	}

	public void setConsiderAvailTime(boolean[] considerAvailTime) {
		this.considerAvailTime = considerAvailTime;
	}

	public boolean[] getDetectCollision() {
		return detectCollision;
	}

	public void setDetectCollision(boolean[] detectCollisioin) {
		this.detectCollision = detectCollisioin;
	}

}
