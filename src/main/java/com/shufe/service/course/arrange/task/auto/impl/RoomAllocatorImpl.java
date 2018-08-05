package com.shufe.service.course.arrange.task.auto.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.course.arrange.task.CourseActivity;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.service.course.arrange.task.auto.RoomAllocator;
import com.shufe.service.course.arrange.task.predicate.ValidRoomPredicate;

public class RoomAllocatorImpl implements RoomAllocator {
	private TeachResourceDAO teachResourceDAO;

	/**
	 * @see com.shufe.service.course.arrange.task.auto.RoomAllocator#alloc(com.ekingstar.eams.system.time.TimeUnit,
	 *      java.util.Collection)
	 */
	public Classroom alloc(TimeUnit time, Collection roomScope,
			Classroom example, boolean considerAvailTime) {
		if (null == time)
			return null;
		Collection validRooms = CollectionUtils.select(roomScope,
				new ValidRoomPredicate(time, example.getConfigType(), example
						.getCapacityOfCourse().intValue(), considerAvailTime));
		if (validRooms.isEmpty())
			return null;
		else {
			return teachResourceDAO.getFreeRoomIn(EntityUtils.extractIds(validRooms),
					new TimeUnit[] { time }, example, CourseActivity.class);
		}
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.RoomAllocator#alloc(com.ekingstar.eams.system.time.TimeUnit[],
	 *      java.util.Collection)
	 */
	public Classroom[] alloc(TimeUnit[] times, Collection roomScope,
			Classroom example, boolean considerAvailTime) {
		Classroom[] allocated = new Classroom[times.length];
		Collection validRooms = CollectionUtils.select(roomScope,
				new ValidRoomPredicate(times, example.getConfigType(), example
						.getCapacityOfCourse().intValue(), considerAvailTime));

		if (validRooms.isEmpty()) {
			return allocated;
		}
		Classroom firstTryRs = teachResourceDAO.getFreeRoomIn(EntityUtils
				.extractIds(validRooms), times, example, CourseActivity.class);

		if (null != firstTryRs) {
			for (int i = 0; i < times.length; i++)
				allocated[i] = firstTryRs;
		} else {
			for (int i = 0; i < times.length; i++)
				allocated[i] = alloc(times[i], roomScope, example,
						considerAvailTime);
		}
		return allocated;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.RoomAllocator#alloc(com.ekingstar.eams.system.time.TimeUnit[],
	 *      Long[],Classroom)
	 */
	public Classroom[] alloc(TimeUnit[] times, Long[] roomIds,
			Classroom example, boolean considerAvailTime) {
		List rooms = teachResourceDAO.getClassrooms(roomIds);
		return alloc(times, rooms, example, considerAvailTime);
	}

	public Collection getFreeClassIds(Collection adminClassIds,TimeUnit[] times){
		return teachResourceDAO.getFreeClassIdsIn(adminClassIds, times);
	}
	/**
	 * @see com.shufe.service.course.arrange.task.auto.RoomAllocator#allocIgnoreOccupy(com.ekingstar.eams.system.time.TimeUnit[],
	 *      java.util.Collection, com.shufe.model.system.baseinfo.Classroom)
	 */
	public Classroom[] allocIgnoreOccupy(TimeUnit[] times, Collection rooms,
			Classroom room, boolean considerAvailTime) {
		Classroom[] allocated = new Classroom[times.length];
		for (int i = 0; i < times.length; i++)
			allocated[i] = allocIgnoreOccupy(times[i], rooms, room,
					considerAvailTime);
		return allocated;
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.RoomAllocator#allocIgnoreOccupy(com.ekingstar.eams.system.time.TimeUnit,
	 *      java.util.Collection, com.shufe.model.system.baseinfo.Classroom)
	 */
	public Classroom allocIgnoreOccupy(TimeUnit time, Collection rooms,
			Classroom example, boolean considerAvailTime) {
		if (null == time)
			return null;
		List validRooms = (List) CollectionUtils.select(rooms,
				new ValidRoomPredicate(time, example.getConfigType(), example
						.getCapacityOfCourse().intValue(), considerAvailTime));
		if (validRooms.isEmpty())
			return null;
		Collections.sort(validRooms, new BeanComparator("capacityOfCourse"));

		return (Classroom) validRooms.get(0);
	}

	/**
	 * @see com.shufe.service.course.arrange.RoomAllocater#isOccupied(com.shufe.model.system.baseinfo.Classroom,
	 *      com.ekingstar.eams.system.time.TimeUnit)
	 */
	public boolean isOccupied(TimeUnit time, Classroom room) {
		return teachResourceDAO.isRoomOccupied(time, room);
	}

	/**
	 * @see com.shufe.service.course.arrange.RoomAllocater#isOccupied(java.lang.String,
	 *      com.ekingstar.eams.system.time.TimeUnit)
	 */
	public boolean isOccupied(TimeUnit time, Serializable roomId) {
		return teachResourceDAO.isRoomOccupied(time, roomId);
	}

	/**
	 * @see com.shufe.service.course.arrange.resource.TeachResourceService#isRoomOccupied(com.ekingstar.eams.system.time.TimeUnit[],
	 *      java.io.Serializable)
	 */
	public boolean isOccupied(TimeUnit[] times, Classroom room) {
		return isOccupied(times, room.getId());
	}

	/**
	 * @see com.shufe.service.course.arrange.task.auto.RoomAllocator#isOccupied(com.ekingstar.eams.system.time.TimeUnit[],
	 *      java.lang.String)
	 */
	public boolean isOccupied(TimeUnit[] times, Serializable roomId) {
		for (int i = 0; i < times.length; i++)
			if (teachResourceDAO.isRoomOccupied(times[i], roomId))
				return true;
		return false;
	}

	public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO) {
		this.teachResourceDAO = teachResourceDAO;
	}

	public TeachResourceDAO getTeachResourceDAO() {
		return teachResourceDAO;
	}
	
}