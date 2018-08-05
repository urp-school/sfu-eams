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
 * chaostone             2006-12-10            Created
 *  
 ********************************************************************************/
package com.shufe.service.course.arrange;

import java.util.ArrayList;
import java.util.List;

import com.ekingstar.eams.system.time.TimeUnit;

public class CollisionInfo {

	private Object resource;

	private List times = new ArrayList();

	public CollisionInfo() {
		super();
	}

	public CollisionInfo(Object resource, TimeUnit time) {
		this.resource = resource;
		TimeUnit unit = (TimeUnit) time.clone();
		unit.setValidWeeksNum(new Long(0));
		this.times.add(unit);
	}

	public void add(TimeUnit time) {
		TimeUnit unit = (TimeUnit) time.clone();
		unit.setValidWeeksNum(new Long(0));
		this.times.add(unit);
	}

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public List getTimes() {
		return times;
	}

	public void setTimes(List times) {
		this.times = times;
	}

	public void mergeTimes() {
		setTimes(TimeUnit.mergeTimeUnits(getTimes()));
	}
}
