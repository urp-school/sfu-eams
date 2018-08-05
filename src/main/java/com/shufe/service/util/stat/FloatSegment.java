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
 * chaostone             2006-12-27            Created
 *  
 ********************************************************************************/
package com.shufe.service.util.stat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 统计段<br>
 * 排序按照降序进行
 * [min,max]
 * @author chaostone
 * 
 */
public class FloatSegment implements Comparable {
	float min;

	float max;

	int count;

	public FloatSegment() {
		this(0, 0);
	}

	public FloatSegment(float min, float max) {
		this.min = min;
		this.max = max;
		count = 0;
	}

	public boolean add(Float score) {
		return add(score.floatValue());
	}

	public boolean add(float score) {
		if (score <= max && score >= min) {
			count++;
			return true;
		} else {
			return false;
		}
	}

	public int getCount() {
		return count;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		FloatSegment myClass = (FloatSegment) object;
		return new CompareToBuilder().append(myClass.getMin(), this.getMin())
				.toComparison();
	}

	public Object clone() {
		return new FloatSegment(getMin(), getMax());
	}

	public boolean emptySeg() {
		if (min == 0 && max == 0)
			return true;
		else
			return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("min", this.getMin()).append("max", this.getMax())
				.append("count", this.count).toString();
	}

	/**
	 * 构造分段区[start,start+span-1],[start+span,start+2*span-1]..
	 * @param start 
	 * @param span
	 * @param count
	 * @return
	 */
	public static List buildSegments(int start, int span, int count) {
		List segmentList = new ArrayList();
		for (int i = 0; i < count; i++) {
			segmentList.add(new FloatSegment(start, start + span - 1));
			start += span;
		}
		return segmentList;
	}

	public static void countSegments(List segs, List numbers) {
		for (Iterator iter = numbers.iterator(); iter.hasNext();) {
			Number number = (Number) iter.next();
			if (null == number)
				continue;
			for (Iterator iterator = segs.iterator(); iterator.hasNext();) {
				FloatSegment element = (FloatSegment) iterator.next();
				if (element.add(number.floatValue()))
					break;
			}
		}
	}
}
