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
 * chaostone             2006-12-9            Created
 *  
 ********************************************************************************/
package com.shufe.service.util.stat;

import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;

import com.ekingstar.commons.query.Order;

/**
 * 统计选项的比较
 * 
 * @author chaostone
 * 
 */
public class StatItemComparator implements Comparator {

	private Order order;

	public StatItemComparator(Order order) {
		this.order = order;
	}

	public int compare(Object arg0, Object arg1) {
		StatItem item0, item1;
		if (order.getDirection()== Order.ASC) {
			item0 = (StatItem) arg0;
			item1 = (StatItem) arg1;
		} else {
			item1 = (StatItem) arg0;
			item0 = (StatItem) arg1;
		}
		try {
			if (null == item0.getWhat() || null == item1.getWhat()) {
				if (null == item0) {
					return 1;
				} else {
					return -1;
				}
			}
			Comparable c0 = (Comparable) PropertyUtils.getProperty(item0, order
					.getProperty());
			Comparable c1 = (Comparable) PropertyUtils.getProperty(item1, order
					.getProperty());

			//			if (c0 instanceof String) {
			//				return new String(((String) c0).getBytes("utf-8"), "GB2312")
			//						.compareTo(new String(((String) c1).getBytes("utf-8"),
			//								"GB2312"));
			//			} else {
			if (null == c0 || null == c1) {
				if (null == c0) {
					return 1;
				} else {
					return -1;
				}
			}
			return c0.compareTo(c1);
			//			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
