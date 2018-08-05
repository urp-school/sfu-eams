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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.std.statResult;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;
import com.ekingstar.eams.system.basecode.state.Gender;
/**
 * 性别数量统计类
 * @author chaostone
 *
 */
public class StudentGenderCount {

	/* 男性人数 */
	private Integer maleCount;

	/* 女性人数 */
	private Integer femaleCount;

	/* 总人数 */
	private Integer totalCount;

	public StudentGenderCount() {
		super();
	}

	public boolean isEmpty() {
		return !NotZeroNumberPredicate.getInstance().evaluate(totalCount)
				&& !NotZeroNumberPredicate.getInstance().evaluate(maleCount)
				&& !NotZeroNumberPredicate.getInstance().evaluate(femaleCount);
	}

	public void add(StudentGenderCount studentGenderCount) {
		maleCount = new Integer((maleCount == null ? 0 : maleCount.intValue())
				+ (studentGenderCount.getMaleCount() == null ? 0
						: studentGenderCount.getMaleCount().intValue()));
		femaleCount = new Integer((femaleCount == null ? 0 : femaleCount
				.intValue())
				+ (studentGenderCount.getFemaleCount() == null ? 0
						: studentGenderCount.getFemaleCount().intValue()));
		totalCount = new Integer((totalCount == null ? 0 : totalCount
				.intValue())
				+ (studentGenderCount.getTotalCount() == null ? 0
						: studentGenderCount.getTotalCount().intValue()));
	}

	public StudentGenderCount(Integer maleCount, Integer femaleCount,
			Integer otherCount) {
		this.maleCount = maleCount;
		this.femaleCount = femaleCount;
		if (NotZeroNumberPredicate.getInstance().evaluate(otherCount)) {
			this.totalCount = new Integer(maleCount.intValue()
					+ femaleCount.intValue() + otherCount.intValue());
		} else {
			this.totalCount = new Integer(maleCount.intValue()
					+ femaleCount.intValue());
		}
	}

	public void add(Gender gender, Number count) {
		if (gender == null) {
			/* this.totalCount = this.add(this.totalCount,count); */
		} else if (Gender.MALE.equals(gender.getId())) {
			this.maleCount = this.add(this.maleCount, count);
		} else if (Gender.FEMALE.equals(gender.getId())) {
			this.femaleCount = this.add(this.femaleCount, count);
		} else {
			/* this.totalCount = this.add(this.totalCount,count); */
		}
		this.totalCount = this.add(this.totalCount, count);
	}

	public Integer add(Integer a, Number b) {
		if (a == null) {
			if (b == null)
				return new Integer(0);
			else {
				return new Integer(b.intValue());
			}
		} else {
			return new Integer(a.intValue() + (b == null ? 0 : b.intValue()));
		}
	}

	/**
	 * @return 返回 femaleCount.
	 */
	public Integer getFemaleCount() {
		return femaleCount;
	}

	/**
	 * @param femaleCount
	 *            要设置的 femaleCount.
	 */
	public void setFemaleCount(Integer femaleCount) {
		this.femaleCount = femaleCount;
	}

	/**
	 * @return 返回 maleCount.
	 */
	public Integer getMaleCount() {
		return maleCount;
	}

	/**
	 * @param maleCount
	 *            要设置的 maleCount.
	 */
	public void setMaleCount(Integer maleCount) {
		this.maleCount = maleCount;
	}

	/**
	 * @return 返回 totalCount.
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            要设置的 totalCount.
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("totalCount", this.totalCount)
				.append("maleCount", this.maleCount).append("femaleCount",
						this.femaleCount).toString();
	}

}
