package com.shufe.service.course.arrange.exam.impl.time;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.eams.system.time.TimeUnit;

public class ExamCollisionInfo implements Comparable {

	TimeUnit time;
	int collisionCount;

	public ExamCollisionInfo(TimeUnit time, int collisionCount) {
		super();
		this.time = time;
		this.collisionCount = collisionCount;
	}

	/**
	 * 按照冲突最小人数进行升序排列
	 */
	public int compareTo(Object object) {
		ExamCollisionInfo myClass = (ExamCollisionInfo) object;
		return new CompareToBuilder().append(getCollisionCount(), myClass.getCollisionCount())
				.toComparison();
	}

	public TimeUnit getTime() {
		return time;
	}

	public int getCollisionCount() {
		return collisionCount;
	}

	public void setTime(TimeUnit time) {
		this.time = time;
	}

	public void setCollisionCount(int collisionCount) {
		this.collisionCount = collisionCount;
	}

}
