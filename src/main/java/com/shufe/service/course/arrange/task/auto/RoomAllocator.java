//$Id: RoomAllocator.java,v 1.5 2006/12/05 04:43:11 duanth Exp $
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

import java.io.Serializable;
import java.util.Collection;

import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * 教室分配接口
 * 
 * @author chaostone
 * 
 */
public interface RoomAllocator {

	/**
	 * 查询教室在几个时间点上是否被占用
	 * 
	 * @param room
	 *            id不能为空
	 * @param time
	 * @return
	 */
	public boolean isOccupied(TimeUnit[] times, Classroom room);

	/**
	 * 查询指定id的教室在几个时间点上是否被占用
	 * 
	 * @param roomId
	 * @param time
	 * @return
	 */
	public boolean isOccupied(TimeUnit[] times, Serializable roomId);

	/**
	 * 查询教室在该时间点上是否被占用
	 * 
	 * @param room
	 *            id不能为空
	 * @param time
	 * @return
	 */
	public boolean isOccupied(TimeUnit time, Classroom room);

	/**
	 * 查询指定id的教室在该时间点上是否被占用
	 * 
	 * @param roomId
	 * @param time
	 * @return
	 */
	public boolean isOccupied(TimeUnit time, Serializable roomId);

	/**
	 * 根据指定的时间段,分配一个教室(ID),若不能分配则返回null;
	 * 
	 * @param time
	 * @return
	 */
	public Classroom alloc(TimeUnit time, Collection rooms, Classroom room,
			boolean considerAvailTime);

	/**
	 * allocate room from roomList no matter whether the room is occupied.
	 * 
	 * @param times
	 * @param rooms
	 * @param room
	 * @return null,if roomScope doen't has required room. for two reason:<br>
	 *         1) roomScope is empty. <br>
	 *         2) all room in roomScope is not avaliable in given time
	 * @see RoomAllocator#allocIgnoreOccupy(TimeUnit[], Collection, Classroom)
	 */
	public Classroom allocIgnoreOccupy(TimeUnit time, Collection rooms,
			Classroom room, boolean considerAvailTime);

	/**
	 * 根据指定的一组时间段,分配一个教室(ID)<br>
	 * 若不能分配则返回包含times长度的null;<br>
	 * 教室的条件在room里设定<br>
	 * 
	 * @param times
	 * @param rooms
	 * @param example
	 * @return
	 */
	public Classroom[] alloc(TimeUnit[] times, Collection rooms,
			Classroom example, boolean considerAvailTime);

	/**
	 * 根据指定的一组时间段,分配一个教室(ID),若不能分配则返回null;<br>
	 * 
	 * @param times
	 * @param roomIds
	 * @return
	 */
	public Classroom[] alloc(TimeUnit[] times, Long[] roomIds,
			Classroom example, boolean considerAvailTime);

	/**
	 * allocate rooms from roomList no matter whether the room is occupied.
	 * 
	 * @param TimeUnit[]
	 *            times indicate allocate count and times to allocate it will be
	 *            used in filting classroom according available time.
	 * @param rooms
	 *            allocate scope
	 * @param example
	 *            contain room condition
	 * @see RoomAllocator#allocIgnoreOccupy(TimeUnit, Collection, Classroom)
	 * @return classrooms of designation,if not enough fill null.<br>
	 *         for two reason:<br>
	 *         1) roomScope is empty. <br>
	 *         2) all room in roomScope is not avaliable in given time
	 */
	public Classroom[] allocIgnoreOccupy(TimeUnit[] times, Collection rooms,
			Classroom example,boolean considerAvailTime);
	
	Collection getFreeClassIds(Collection adminClassIds, TimeUnit[] times);
	/**
	 * @param teachResourceDAO
	 */
	public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO);
	
	public TeachResourceDAO getTeachResourceDAO();
}
