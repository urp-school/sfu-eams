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
 * chaostone             2006-11-6            Created
 *  
 ********************************************************************************/
package com.shufe.model.course.arrange.exam;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.eams.system.time.CourseUnit;

/**
 * 考试场次<br>
 * 
 * <pre>
 *  考试场次是定义每场考试的具体时间段。
 *  含有stdType时为了支持不同学生类别的有不同的考试时间要求。
 * </pre>
 * 
 * @author chaostone
 * 
 */
public class ExamTurn extends LongIdObject implements Comparable {
	private static final long serialVersionUID = -5591926891640269453L;

	/** 场次中文名 */
	private String name;

	/** 场次英文名 */
	private String engName;

	/** 开始时间,格式采用数字.800,表示8:00 */
	private Integer beginTime;

	/** 结束时间,格式采用数字.1400,表示14:00 */
	private Integer endTime;

	/** 学生类别 */
	private StudentType stdType;

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Integer startTime) {
		this.beginTime = startTime;
	}

	public StudentType getStdType() {
		return stdType;
	}

	public void setStdType(StudentType stdType) {
		this.stdType = stdType;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		ExamTurn myClass = (ExamTurn) object;
		return new CompareToBuilder().append(getBeginTime(),
				myClass.getBeginTime()).toComparison();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName() + "[" + CourseUnit.getTimeStr(getBeginTime()) + "-"
				+ CourseUnit.getTimeStr(getEndTime()) + "]";
	}

}
