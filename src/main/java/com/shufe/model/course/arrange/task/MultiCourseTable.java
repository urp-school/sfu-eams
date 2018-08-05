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
 * chaostone             2007-1-21            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.query.Order;

/**
 * 多个资源一张课程表
 * 
 * @author chaostone
 * 
 */
public class MultiCourseTable {

	List tables = new ArrayList();

	List resources = new ArrayList();

	Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List getTables() {
		return tables;
	}

	public void setTables(List tables) {
		this.tables = tables;
	}

	public List getResources() {
		return resources;
	}

	public void setResources(List resources) {
		this.resources = resources;
	}

	public void sortTables() {
		if (null != order) {
			Collections.sort(tables, new PropertyComparator(order.getProperty(), order
					.getDirection() == Order.ASC));
		}
	}
}
