//$Id: OccupyUnit.java,v 1.3 2006/12/11 03:40:06 duanth Exp $
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ekingstar.commons.lang.BitStringUtil;
import com.ekingstar.eams.system.time.TimeUnit;
import com.ekingstar.eams.system.time.TimeUnitUtil;
import com.shufe.model.system.calendar.TeachCalendar;

/**
 * 教学资源占用时间单元
 * 
 * @author chaostone
 * 
 */
public class OccupyUnit implements Comparable {
	public static final long fullOccupy = BitStringUtil.BinValueOf(
			StringUtils.repeat("1", TeachCalendar.OVERALLWEEKS)).intValue();

	int year;

	long validWeeksNum;

	int weekId;

	int startUnit;

	int endUnit;

	public OccupyUnit() {

	}

	public OccupyUnit(int year, long validWeeksNum, int weekId, int startUnit,
			int endUnit) {
		this.year = year;
		this.validWeeksNum = validWeeksNum;
		this.weekId = weekId;
		this.startUnit = startUnit;
		this.endUnit = endUnit;
	}

	public int getStartUnit() {
		return startUnit;
	}

	public void setStartUnit(int startUnit) {
		this.startUnit = startUnit;
	}

	public int getEndUnit() {
		return endUnit;
	}

	public void setEndUnit(int endUnit) {
		this.endUnit = endUnit;
	}

	public long getValidWeeksNum() {
		return validWeeksNum;
	}

	public void setValidWeeksNum(long validWeeksNum) {
		this.validWeeksNum = validWeeksNum;
	}

	public int getWeekId() {
		return weekId;
	}

	public void setWeekId(int weekId) {
		this.weekId = weekId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * 
	 * @param from
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getOccupyString(int from, int begin, int end, boolean occupy) {

		if (occupy && validWeeksNum == 0)
			return "";
		if (!occupy && validWeeksNum == TimeUnit.FULL_OCCUPY_WEEKNUM)
			return "";
		StringBuffer occupyString = new StringBuffer();
		// digest valid weeks
		String binaryStr = null;
		if (occupy) {
			binaryStr = Long.toBinaryString(validWeeksNum);
		} else {
			long free = ~TimeUnit.FULL_OCCUPY_WEEKNUM;
			free &= fullOccupy;
			binaryStr = Long.toBinaryString(free);
		}
		String validWeeks = StringUtils.repeat("0", TeachCalendar.OVERALLWEEKS
				- binaryStr.length())
				+ binaryStr;

		String weekDigest = TimeUnitUtil.digest(validWeeks, from, begin, end,
				null, null, " -  ");
		// if no week was occupied then return empty string
		if (StringUtils.isEmpty(weekDigest))
			return "";

		occupyString.append(weekId);
		occupyString.append("(").append(startUnit).append("-").append(endUnit);
		occupyString.append(")");
		occupyString.append("[" + weekDigest.trim() + "]");
		return occupyString.toString();
	}

	public boolean needShiftWeeks(boolean endAtSat, int year) {
		return (!endAtSat && year == (year + 1));
	}

	public void leftShiftWeeks() {
		validWeeksNum *= 2;
		validWeeksNum &= TimeUnit.FULL_OCCUPY_WEEKNUM;
	}

	public void reverse() {
		validWeeksNum = ~validWeeksNum & TimeUnit.FULL_OCCUPY_WEEKNUM;
	}

	/**
	 * 判断是否该时间段能否与其它时间段进行"相同"合并或者"相邻"合并<br>
	 * 主要是合并占用周.所有同一个小节可以不考虑占用周.<br>
	 * 不同小结则要判断占用周,决定是否需要合并.
	 * 
	 * @param other
	 * @return
	 */
	public boolean canMergerWith(OccupyUnit other) {
		if (weekId == other.weekId) {
			if ((startUnit == other.startUnit) && (endUnit == other.endUnit))
				return true;
			else {
				if (validWeeksNum == other.validWeeksNum)
					return (startUnit == other.endUnit + 1)
							|| (other.startUnit == endUnit + 1);
				return false;
			}
		} else
			return false;
	}

	/**
	 * 相邻小节的时间点合并成大时间点
	 * 
	 * @see #TimeUnit.canMergerWith
	 * @param other
	 * @param forOccupy
	 *            如果是为了占用合并采用相或,为了空闲合并采用相与(也就是说这是的1表示空闲)
	 */
	public void mergeWith(OccupyUnit other, boolean forOccupy) {
		// 接在前头
		if (this.startUnit == other.endUnit + 1) {
			this.startUnit = other.startUnit;
		} else {
			// 接在后头
			this.endUnit = other.endUnit;
		}
		if (forOccupy) {
			this.validWeeksNum |= other.validWeeksNum;
		} else {
			this.validWeeksNum &= other.validWeeksNum;
		}
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		OccupyUnit myClass = (OccupyUnit) object;
		return new CompareToBuilder().append(this.weekId, myClass.weekId)
				.append(this.startUnit, myClass.startUnit).append(this.endUnit,
						myClass.endUnit).toComparison();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("validWeeks",
						TimeUnit.weekOccupyString(this.validWeeksNum)).append(
						"weekId", this.weekId).append("endUnit", this.endUnit)
				.append("startUnit", this.startUnit).append("year", this.year)
				.toString();
	}
}
