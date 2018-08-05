//$Id: ArrangeSuite.java,v 1.6 2006/12/06 03:37:25 duanth Exp $
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.ArrangeInfo;
import com.shufe.model.course.arrange.AvailableTime;
import com.shufe.model.course.arrange.task.ArrangeParams;
import com.shufe.model.course.task.TaskRequirement;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.service.course.arrange.task.auto.impl.RoomAllocFailure;

/**
 * 一组课程的安排
 * 
 * @author chaostone
 * 
 */
public class ArrangeSuite implements Arrange {

	/**
	 * 排课组的名称，一般等同于课程组
	 */
	private String name;

	/**
	 * 可用的教室
	 */
	private Collection classrooms;

	/**
	 * 课程组内可用的时间
	 */
	private AvailableTime availableTime;

	/**
	 * 上级排课组
	 */
	private ArrangeSuite suite;

	/**
	 * 一组课程安排
	 */
	private Vector arranges = new Vector(10);

	private int priority;

	/**
	 * 排课的外部环境（参数、教室、时间分配、规则等）
	 */
	private ArrangeFixture fixture;

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#arrange()
	 */
	public void arrange(ArrangeResult result) {
		Collections.sort(arranges);
		for (Iterator iter = arranges.iterator(); iter.hasNext();) {
			Arrange arrange = (Arrange) iter.next();
			runArrange(arrange, result);
		}
	}

	public void runArrange(Arrange arrange, ArrangeResult result) {
		arrange.arrange(result);
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#countArrangeCase()
	 */
	public int countArrangeCases() {
		int count = 0;
		for (Enumeration e = arranges(); e.hasMoreElements();) {
			Arrange arrange = (Arrange) e.nextElement();
			count = count + arrange.countArrangeCases();
		}
		return count;
	}

	/**
	 * 添加一个安排
	 * 
	 * @param arrange
	 */
	public void addArrange(Arrange arrange) {
		arranges.add(arrange);
	}

	/**
	 * 分配教室 如果该排课组中没有可以安排的教室，则查找上级的安排组.
	 * 
	 * @return
	 */
	public Classroom[] allocRooms(TimeUnit[] times, Classroom room) {

		ArrangeSuite parentSuite = this;
		Classroom[] rooms = null;

		while (null != parentSuite) {
			Collection roomScope = parentSuite.getClassrooms();
			if (roomScope == null || roomScope.isEmpty()) {
				parentSuite = parentSuite.getSuite();
				continue;
			}
			if (fixture.getParams().getDetectCollision()[ArrangeParams.ROOM]){
				rooms = fixture.getRoomAlloc().alloc(times, roomScope, room,
						getParams().getConsiderAvailTime()[ArrangeParams.ROOM]);
            }
			else{
				rooms = fixture.getRoomAlloc().allocIgnoreOccupy(times, roomScope, room,
						getParams().getConsiderAvailTime()[ArrangeParams.ROOM]);
            }
			// 满足或者部分满足
			if (null != rooms[0]) {
				return rooms;
			}
			// 否则向上查找
			else {
				parentSuite = parentSuite.getSuite();
			}
		}
		if (null == rooms) {
			return new Classroom[times.length];
		} else
			return rooms;
	}

	/**
	 * 分配一个教室,若无法分配则返回null
	 * 
	 * @param time
	 * @return
	 */
	public Classroom allocRoom(TimeUnit time, Classroom room) {
		ArrangeSuite parentSuite = suite;
		while (classrooms == null || classrooms.isEmpty()) {
			if (null == parentSuite)
				throw new RoomAllocFailure("info.arrange.noClassroom.avaliable");
			classrooms = parentSuite.getClassrooms();
			parentSuite = parentSuite.getSuite();
		}
		if (fixture.getParams().getDetectCollision()[ArrangeParams.ROOM])
			return fixture.getRoomAlloc().alloc(time, classrooms, room,
					getParams().getConsiderAvailTime()[ArrangeParams.ROOM]);
		else
			return fixture.getRoomAlloc().allocIgnoreOccupy(time, classrooms, room,
					getParams().getConsiderAvailTime()[ArrangeParams.ROOM]);
	}

	/**
	 * 分配时间
	 * 
	 * @return
	 */
	public TimeUnit[] allocTimes(Arrange arrange) {
		AvailableTime available = arrange.getAvailableTime();
		if (null == available) {
			available = getAvailableTime();
			ArrangeSuite parentSuite = suite;
			while (available == null) {
				available = availableTime = parentSuite.getAvailableTime();
				parentSuite = parentSuite.getSuite();
			}
			arrange.setAvailableTime(available);
		}
		return fixture.getTimeAlloc().allocTimes(arrange);
	}

	/**
	 * 为指定的教室分配时间
	 * 
	 * @param room
	 * @return
	 */
	public TimeUnit[] allocTimes(Arrange arrange, Serializable roomId) {
		TimeUnit times[] = allocTimes(arrange);
		while (fixture.getRoomAlloc().isOccupied(times, roomId))
			times = allocTimes(arrange);
		return times;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the priority.
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            The priority to set.
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return Returns the arranges.
	 */
	public Vector getArranges() {
		return arranges;
	}

	/**
	 * @param arranges
	 *            The arranges to set.
	 */
	public void setArranges(Vector arranges) {
		this.arranges = arranges;
	}

	/**
	 * @return Returns the availableTime.
	 */
	public AvailableTime getAvailableTime() {
		return availableTime;
	}

	/**
	 * @param availableTime
	 *            The availableTime to set.
	 */
	public void setAvailableTime(AvailableTime availableTime) {
		this.availableTime = availableTime;
	}

	/**
	 * @return Returns the classrooms.
	 */
	public Collection getClassrooms() {
		return classrooms;
	}

	/**
	 * @param classrooms
	 *            The classrooms to set.
	 */
	public void setClassrooms(Collection classrooms) {
		this.classrooms = classrooms;
	}

	/**
	 * @return Returns the suite.
	 */
	public ArrangeSuite getSuite() {
		return suite;
	}

	/**
	 * @param suite
	 *            The suite to set.
	 */
	public void setSuite(ArrangeSuite suite) {
		this.suite = suite;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getTasks()
	 */
	public Collection getTasks() {
		List tasks = new ArrayList();
		for (Iterator iter = arranges.iterator(); iter.hasNext();) {
			Arrange arrange = (Arrange) iter.next();
			tasks.addAll(arrange.getTasks());
		}
		return tasks;
	}

	/**
	 * Returns the tests as an enumeration
	 */
	public Enumeration arranges() {
		return arranges.elements();
	}

	/**
	 * @return Returns the fixture.
	 */
	public ArrangeFixture getFixture() {
		return fixture;
	}

	public ArrangeParams getParams() {
		return fixture.getParams();
	}

	public RoomAllocator getRoomAllocator() {
		return fixture.getRoomAlloc();
	}

	public TimeAllocator getTimeAllocator() {
		return fixture.getTimeAlloc();
	}

	/**
	 * @param fixture
	 *            The fixture to set.
	 */
	public void setFixture(ArrangeFixture fixture) {
		this.fixture = fixture;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		return ((Arrange) o).getPriority() - getPriority();
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getAdminClasses()
	 */
	public Collection getAdminClasses() {
		return null;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getArrangeInfo()
	 */
	public ArrangeInfo getArrangeInfo() {
		return null;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getTeachers()
	 */
	public Collection getTeachers() {
		return null;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getAvailUnitBitMap()
	 */
	public String getAvailUnitBitMap() {
		return null;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#setUnitOccupy(java.lang.String)
	 */
	public void setUnitOccupy(String occupy) {
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getCalendar()
	 */
	public TeachCalendar getCalendar() {
		return null;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#getRequirement()
	 */
	public TaskRequirement getRequirement() {
		return null;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.Arrange#setArrangeInfo(com.shufe.model.course.arrange.ArrangeInfo)
	 */
	public void setArrangeInfo(ArrangeInfo arrangeInfo) {

	}

	/**
	 * 
	 */
	public TeachTask getCurTask() {
		return null;
	}

}
