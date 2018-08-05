//$Id: TimeOccupy.java,v 1.1 2007-7-28 下午07:38:25 chaostone Exp $
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
 * chenweixiong              2007-7-28         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.exam.impl.time;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import com.ekingstar.eams.system.time.TimeUnit;
import com.shufe.model.course.arrange.exam.ExamArrangeGroup;

/**
 * 一组排考组占用的时间
 * 
 * @author chaostone
 * 
 */
public class TimeOccupy {

	List groups;

	BitSet stdOccupy;

	TimeUnit unit;

	int stdNum;

	int maxStdNum;

	public TimeOccupy(ExamArrangeGroup group, TimeUnit unit) {
		super();
		groups = new ArrayList();
		groups.add(group);
		stdOccupy = (BitSet) group.getStdOccupy().clone();
		this.unit = unit;
		this.stdNum = group.getStdCount();
	}

	public boolean isCompatible(ExamArrangeGroup group) {
		BitSet dd = (BitSet) getStdOccupy().clone();
		dd.and(group.getStdOccupy());
		if (dd.isEmpty())
			return true;
		else
			return false;
	}

	public boolean isHaveCapacityForGroup(ExamArrangeGroup group) {
		if (0 < maxStdNum) {
			return maxStdNum >= (stdNum + group.getStdCount());
		} else {
			return true;
		}
	}

	public void addGroup(ExamArrangeGroup group) {
		getStdOccupy().or(group.getStdOccupy());
		getGroups().add(group);
		stdNum += group.getStdCount();
	}

	public List getGroups() {
		return groups;
	}

	public void setGroups(List groups) {
		this.groups = groups;
	}

	public BitSet getStdOccupy() {
		return stdOccupy;
	}

	public void setStdOccupy(BitSet stdOccupy) {
		this.stdOccupy = stdOccupy;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public int getStdNum() {
		return stdNum;
	}

	public void setStdNum(int stdNum) {
		this.stdNum = stdNum;
	}

	public int getMaxStdNum() {
		return maxStdNum;
	}

	public void setMaxStdNum(int maxStdNum) {
		this.maxStdNum = maxStdNum;
	}

}
