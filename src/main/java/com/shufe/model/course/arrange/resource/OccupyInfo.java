//$Id: OccupyInfo.java,v 1.2 2006/12/03 06:59:48 duanth Exp $
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
 * chaostone             2005-12-25         Created
 *  
 ********************************************************************************/

package com.shufe.model.course.arrange.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学资源占用表
 * 
 * <pre>
 * 主要用来输出占用字符串描述
 * </pre>
 * 
 * @author chaostone
 * 
 */
public class OccupyInfo {

	private TeachCalendar calendar;

	private List occupyList = new ArrayList();

	public List getOccupyList() {
		return occupyList;
	}

	public void setOccupyList(List occupyList) {
		this.occupyList = occupyList;
	}

	public String getOccupyString() {
		return "";
	}

	public TeachCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(TeachCalendar calendar) {
		this.calendar = calendar;
	}

	public OccupyInfo(List occupyList, TeachCalendar calendar) {
		this.occupyList = occupyList;
		this.calendar = calendar;
	}

	/**
	 * 查询占用情况
	 * 
	 * @param from
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getOccupy(int from, int begin, int end) {
		return digestTime(from, begin, end, true);
	}

	/**
	 * 查询空闲情况
	 * 
	 * @param from
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getFree(int from, int begin, int end) {
		return digestTime(from, begin, end, false);
	}
	/**
	 * 
	 * @param from
	 * @param begin
	 * @param end
	 * @param occupy
	 * @return
	 */
	public String digestTime(int from, int begin, int end, boolean occupy) {
		if (occupy) {
			mergeOccupyUnits(occupy);
		} else {
			reverse();
			mergeOccupyUnits(occupy);
		}
		StringBuffer occupyStrBuf = new StringBuffer(60);
		String defaultStr = "[" + begin + "-" + end + "]";
		for (Iterator iter = occupyList.iterator(); iter.hasNext();) {
			OccupyUnit unit = (OccupyUnit) iter.next();
			String unitOccupyStr = unit.getOccupyString(from, begin, end, true);
			if (!"".equals(unitOccupyStr)) {
				if (StringUtils.contains(unitOccupyStr, defaultStr)) {
					occupyStrBuf.append(
							unitOccupyStr.substring(0, unitOccupyStr
									.indexOf(defaultStr))).append(",");
				} else {
					occupyStrBuf.append(unitOccupyStr).append(",");
				}
			}
		}
		if (occupyStrBuf.length() > 0)
			occupyStrBuf.setCharAt(occupyStrBuf.length() - 1, ' ');
		return occupyStrBuf.toString();
	}


	public void mergeOccupyUnits(boolean forOccupy) {
		if (null == occupyList || occupyList.isEmpty())
			return;
		Collections.sort(occupyList);
		List mergedOccupyUnits = new ArrayList();
		Iterator unitIter = occupyList.iterator();
		OccupyUnit toMerged = (OccupyUnit) unitIter.next();
		mergedOccupyUnits.add(toMerged);
		boolean endAtSat = calendar.endAtSat();
		int year = calendar.getStartYear();
		while (unitIter.hasNext()) {
			OccupyUnit unit = (OccupyUnit) unitIter.next();
			if (toMerged.canMergerWith(unit)) {
				if (unit.needShiftWeeks(endAtSat, year)) {
					unit.leftShiftWeeks();
				}
				toMerged.mergeWith(unit, forOccupy);
			} else {
				toMerged = unit;
				mergedOccupyUnits.add(toMerged);
			}
		}
		occupyList = mergedOccupyUnits;
	}

	/**
	 * 对占用时间取反
	 * 
	 */
	public void reverse() {
		Collections.sort(occupyList);
		Iterator iter = occupyList.iterator();
		List added = new ArrayList();
		// 找到第一个占用时间
		OccupyUnit occupyUnit = null;
		int year = calendar.getStartYear();
		if (iter.hasNext()) {
			occupyUnit = (OccupyUnit) iter.next();
		} else {
			occupyUnit = new OccupyUnit(year, OccupyUnit.fullOccupy, 8, 1, 1);
		}
		// 逐周逐节进行循环
		for (int week = 1; week <= WeekInfo.MAX; week++) {
			for (int unit = 1; unit <= TeachCalendar.MAXUNITS; unit++) {
				// 是否需要判断下一个已有的占用时间
				boolean move = false;
				if (week < occupyUnit.getWeekId()) {
					added.add(new OccupyUnit(year, OccupyUnit.fullOccupy, week,
							unit, unit));
				} else if (week == occupyUnit.getWeekId()) {
					if (unit < occupyUnit.getStartUnit()) {
						added.add(new OccupyUnit(year, OccupyUnit.fullOccupy,
								week, unit, unit));
					} else if (unit > occupyUnit.getEndUnit()) {
						move = true;
					}
				} else {
					move = true;
				}
				if (move) {
					if (!iter.hasNext()) {
						occupyUnit = new OccupyUnit(year,
								OccupyUnit.fullOccupy, 8, unit, unit);
					} else {
						occupyUnit = (OccupyUnit) iter.next();
					}
					// 重复检查一次
					unit--;
				}
			}
		}
		for (Iterator iterator = occupyList.iterator(); iterator.hasNext();) {
			OccupyUnit unit = (OccupyUnit) iterator.next();
			// 取反
			unit.reverse();
		}
		occupyList.addAll(added);
	}
}
