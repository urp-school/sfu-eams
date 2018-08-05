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
 * chaostone             2006-11-4            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.task;

import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.Component;
import com.shufe.model.course.arrange.AvailableTime;

/**
 * 排课建议
 * 
 * @author chaostone
 * 
 */
public class ArrangeSuggest implements Component {
	/**
	 * 预备使用的教室
	 */
	private Set rooms = new HashSet();

	/**
	 * 建议上课时间
	 */
	private AvailableTime time = new AvailableTime();

	public Set getRooms() {
		return rooms;
	}

	public void setRooms(Set rooms) {
		this.rooms = rooms;
	}

	public AvailableTime getTime() {
		return time;
	}

	public void setTime(AvailableTime time) {
		this.time = time;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		ArrangeSuggest suggest = new ArrangeSuggest();
		suggest.getRooms().addAll(getRooms());

		if (null != getTime()) {
			suggest.getTime().setAvailable(getTime().getAvailable());
			suggest.getTime().setRemark(getTime().getRemark());
		} else {
			suggest.setTime(null);
		}
		return suggest;
	}

	public void mergeRoom(ArrangeSuggest other) {
		getRooms().addAll(other.getRooms());
	}

	public void mergeTime(ArrangeSuggest other) {
		getTime().mergeWith(other.getTime());
	}

	public void merge(ArrangeSuggest other) {
		mergeRoom(other);
		mergeTime(other);
	}

	public void detachRoom(ArrangeSuggest other) {
		getRooms().removeAll(other.getRooms());
	}

	public void detachTime(ArrangeSuggest other) {
		if (null != getTime())
			getTime().detachWith(other.getTime());
	}
	
	 public boolean hasRooms() {
	 return (null != rooms && !rooms.isEmpty());
	 }
}
