//$Id: Arrange.java,v 1.2 2006/11/11 08:13:31 duanth Exp $
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
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import java.util.Collection;

import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.calendar.TeachCalendar;

public abstract interface Arrange extends Comparable{

    public static int defaultPriority =1;
	/**
	 * 在这次安排中有多少门课
	 * @return
	 */
	public abstract int countArrangeCases();
	/**
	 * 实施安排
	 *
	 */
	public abstract void arrange(ArrangeResult result);
	/**
	 * 安排的名称
	 *
	 */
	public abstract String getName();
	/**
	 * 安排的优先级别
	 * @return
	 */
	public abstract int getPriority();
    /**
     * 设置排课的优先级
     * @param priority
     */
    public abstract void  setPriority(int priority);
    /**
     * 所在的排课组
     * @return
     */
    public abstract ArrangeSuite getSuite();
    /**
     * 设置排课组的上级组
     * @param suite
     * @return
     */
    public abstract void setSuite(ArrangeSuite suite);    
    /**
     * 返回安排的任务课程
     * @return
     */
    public abstract Collection getTasks();
    /**
     * 返回正在安排或者刚刚安排的课程
     * @return
     */
    public abstract TeachTask getCurTask();
    /**
     * 这次安排中涉及到的行政班
     * @return
     */
    public abstract Collection getAdminClasses();
    /**
     * 这次安排中涉及到的教师
     * @return
     */
    public abstract Collection getTeachers();
    /**
     * 获得安排的具体参数
     * @return
     */
    public abstract ArrangeInfo getArrangeInfo();
    /**
     * 
     * @param arrangeInfo
     */
    public abstract void setArrangeInfo(ArrangeInfo arrangeInfo);
    /**
     * 获得可用时间
     * @return
     */
    public abstract AvailableTime getAvailableTime();
    /**
     * 获得可用时间
     * @return
     */
    public abstract  void setAvailableTime(AvailableTime time);
    /**
     * 获得安排的已使用时间的位图
     * @return
     */
    public abstract String getAvailUnitBitMap();
    /**
     * 设置安排的已使用时间
     */
    public abstract void setUnitOccupy(String occupy);
    /**
     * 返回这次安排发生的时间 
     * @return
     */
    public abstract TeachCalendar getCalendar();
    /**
     * 
     * @return
     */
    public abstract TaskRequirement getRequirement();
    
}
